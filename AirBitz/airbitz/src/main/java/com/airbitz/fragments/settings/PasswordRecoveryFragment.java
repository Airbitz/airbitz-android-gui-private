/**
 * Copyright (c) 2014, Airbitz Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted provided that
 * the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Redistribution or use of modified source code requires the express written
 *    permission of Airbitz Inc.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the Airbitz Project.
 */

package com.airbitz.fragments.settings;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbitz.AirbitzApplication;
import com.airbitz.R;
import com.airbitz.activities.NavigationActivity;
import com.airbitz.adapters.PasswordRecoveryAdapter;
import com.airbitz.api.CoreAPI;
import com.airbitz.api.tABC_CC;
import com.airbitz.fragments.BaseFragment;
import com.airbitz.fragments.login.SignUpFragment;
import com.airbitz.fragments.request.ContactPickerFragment;
import com.airbitz.fragments.settings.twofactor.TwoFactorMenuFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2/10/14.
 */
public class PasswordRecoveryFragment extends BaseFragment implements
        NavigationActivity.OnBackPress,
        TwoFactorMenuFragment.OnTwoFactorMenuResult {
    public static final String MODE = "com.airbitz.passwordrecovery.type";
    public static final String QUESTIONS = "com.airbitz.passwordrecovery.questions";
    public static final String USERNAME = "com.airbitz.passwordrecovery.username";
    public static final String PASSWORD = "com.airbitz.passwordrecovery.password";
    public static final String PIN = "com.airbitz.passwordrecovery.pin";
    public static int SIGN_UP = 0;
    public static int CHANGE_QUESTIONS = 1;
    public static int FORGOT_PASSWORD = 2;
    private final String TAG = getClass().getSimpleName();
    String mAnswers = "";
    String mQuestions = "";
    boolean ignoreSelected = false;
    private int mMode;
    private boolean mReturnFromTwoFactorScan = false;
    private boolean mTwoFactorSuccess = false;
    private String mTwoFactorSecret;
    private EditText mPasswordEditText;
    private Button mDoneSignUpButton;

    private LinearLayout mPasswordRecoveryListView;
    private ArrayList<QuestionView> mQuestionViews;

    private GetRecoveryQuestions mFetchAllQuestionsTask;
    private AttemptAnswerVerificationTask mAttemptAnswerVerificationTask;
    private SaveQuestionsTask mSaveQuestionsTask;

    private Map<String, Integer> mStringCategory = new HashMap<String, Integer>(); // Question, MinLength
    private List<String> mStringQuestions;
    private Map<String, Integer> mNumericCategory = new HashMap<String, Integer>(); // Question, MinLength
    private List<String> mNumericQuestions;
    private Map<String, Integer> mMustCategory = new HashMap<String, Integer>();
    private List<String> mMustQuestions;
    private boolean mSaved = false;

    private CoreAPI mCoreAPI;
    private NavigationActivity mActivity;
    private View mView;
    private Toolbar mToolbar;

    public PasswordRecoveryFragment() {
        mFragmentType = this.getClass().getName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCoreAPI = CoreAPI.getApi();
        mActivity = (NavigationActivity) getActivity();
        setHasOptionsMenu(true);
        setDrawerEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater i = getThemedInflater(inflater, R.style.AppTheme_Blue);
        mView = i.inflate(R.layout.fragment_password_recovery, container, false);

        mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.activity_recovery_title);
        getBaseActivity().setSupportActionBar(mToolbar);
        getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBaseActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPasswordEditText = (EditText) mView.findViewById(R.id.activity_password_recovery_password_edittext);
        mPasswordEditText.setTypeface(Typeface.DEFAULT);
        mDoneSignUpButton = (Button) mView.findViewById(R.id.activity_recovery_complete_button);

        if (getArguments() != null) {
            mMode = getArguments().getInt(MODE);
            if (mMode == CHANGE_QUESTIONS) {
                mPasswordEditText.setVisibility(mCoreAPI.PasswordExists() ? View.VISIBLE : View.GONE);
                mDoneSignUpButton.setText(getResources().getString(R.string.activity_recovery_complete_button_change_questions));
            } else if (mMode == FORGOT_PASSWORD) {
                mPasswordEditText.setVisibility(View.GONE);
                mDoneSignUpButton.setText(getResources().getString(R.string.string_done));
            } else {
                // defaults for signup
            }
        }

        mStringQuestions = new ArrayList<String>();
        mNumericQuestions = new ArrayList<String>();
        mMustQuestions = new ArrayList<String>();

        mDoneSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptSignupOrChange();
            }
        });

        mPasswordRecoveryListView = (LinearLayout) mView.findViewById(R.id.activity_recovery_question_listview);
        return mView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return onBackPress();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] answers = {"", "", "", "", "", ""};
        if (mAnswers.isEmpty()) {
            if (mMode == SIGN_UP || mMode == CHANGE_QUESTIONS) {
                mFetchAllQuestionsTask = new GetRecoveryQuestions();
                mFetchAllQuestionsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
            } else {
                mToolbar.setTitle(getString(R.string.activity_recovery_title));
                String questionString = getArguments().getString(QUESTIONS);
                if (questionString != null) {
                    String[] questions = questionString.split("\n");
                    InitializeRecoveryViews(questions, answers);
                }
            }
        } else { // coming back from signup page
            answers = mAnswers.split("\n");
            mToolbar.setTitle(getString(R.string.activity_recovery_title));
            String questionString = getArguments().getString(QUESTIONS);
            if (questionString != null) {
                String[] questions = questionString.split("\n");
                InitializeRecoveryViews(questions, answers);
            }
        }
        if(mReturnFromTwoFactorScan) {
            mReturnFromTwoFactorScan = false;
            mActivity.hideSoftKeyboard(mQuestionViews.get(0));
            mActivity.showModalProgress(true);
            if (mTwoFactorSuccess) {
                if (mCoreAPI.OtpKeySet(getArguments().getString(USERNAME), mTwoFactorSecret) == tABC_CC.ABC_CC_Ok) {
                    // Try again with OTP
                    AttemptSignupOrChange();
                }
            }
            else {
                mActivity.ShowOkMessageDialog(getString(R.string.fragment_two_factor_scan_unable_import_title),
                        getString(R.string.twofactor_unable_import_token));
            }
        }
    }

    @Override
    public boolean onBackPress() {
        boolean dirty = false;
        if(mQuestionViews != null) {
            for (View view : mQuestionViews) {
                QuestionView qaView = (QuestionView) view;
                if (!qaView.getSelectedQuestion().equals(getString(R.string.activity_recovery_question_default)) ||
                        !qaView.getText().isEmpty()) {
                    dirty = true;
                    break;
                }
            }
        }

        if (dirty && !mSaved) {
            String message;
            String title;
            if(mMode == CHANGE_QUESTIONS) {
                message = getString(R.string.activity_recovery_warning_dirty_change_message);
                title = getString(R.string.activity_recovery_warning_dirty_change_title);
            }
            else if (mMode == FORGOT_PASSWORD) {
                message = getString(R.string.activity_recovery_warning_dirty_forgot_message);
                title = getString(R.string.activity_recovery_warning_dirty_forgot_title);
            }
            else {
                return true;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
            builder.setMessage(message)
                    .setTitle(title)
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.string_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    mActivity.hideSoftKeyboard(getView());
                                    if (mMode == CHANGE_QUESTIONS) {
                                        mActivity.popFragment();
                                    } else if (mMode == FORGOT_PASSWORD) {
                                        mActivity.popFragment();
                                        mActivity.showNavBar();
                                        mActivity.Logout();
                                    }
                                }
                            }
                    )
                    .setNegativeButton(getString(R.string.string_no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            mActivity.hideSoftKeyboard(getView());
            if (mMode == CHANGE_QUESTIONS) {
                mActivity.popFragment();
            } else if (mMode == FORGOT_PASSWORD) {
                mActivity.popFragment();
                mActivity.showNavBar();
                mActivity.Logout();
            }
        }

        return true;
    }

    private void AttemptSignupOrChange() {
        //verify that all six questions have been selected
        boolean allQuestionsSelected = true;
        boolean allAnswersValid = true;
        mQuestions = "";
        mAnswers = "";

        if (mMode == CHANGE_QUESTIONS && !mCoreAPI.PasswordOK(AirbitzApplication.getUsername(), mPasswordEditText.getText().toString())) {
            mActivity.ShowFadingDialog(getResources().getString(R.string.activity_recovery_error_incorrect_password));
            return;
        }

        int count = 0;
        for (View view : mQuestionViews) {
            QuestionView qaView = (QuestionView) view;
            if (mMode != FORGOT_PASSWORD && qaView.getSelectedQuestion().equals(getString(R.string.activity_recovery_question_default))) {
                allQuestionsSelected = false;
                break;
            }
            //verify that all six answers have achieved their minimum character limit
            if (qaView.getText().length() < qaView.getMinimumCharacters() || qaView.getText().isEmpty()) {
                allAnswersValid = false;
            } else {
                //add question and answer to arrays
                if (count != 0) {
                    mQuestions += "\n";
                    mAnswers += "\n";
                }
                mQuestions += qaView.getSelectedQuestion();
                mAnswers += qaView.getText();
            }
            count++;
        }
        if (allQuestionsSelected) {
            if (allAnswersValid) {
                if (mMode == SIGN_UP) {
                    signIn();
                }
                if (mMode == FORGOT_PASSWORD) {
                    mAttemptAnswerVerificationTask = new AttemptAnswerVerificationTask();
                    mAttemptAnswerVerificationTask.execute(mAnswers, getArguments().getString(USERNAME));
                } else {
                    attemptCommitQuestions();
                }
            } else {
                mActivity.ShowFadingDialog(getResources().getString(R.string.activity_recovery_answer_questions_alert));
            }
        } else {
            mActivity.ShowFadingDialog(getResources().getString(R.string.activity_recovery_pick_questions_alert));
        }
    }

    private void attemptCommitQuestions() {
        String password = null;
        if(mMode == CHANGE_QUESTIONS) {
            password = mPasswordEditText.getText().toString();
        }
        else {
            password = AirbitzApplication.getPassword();
        }
        if(!mCoreAPI.PasswordExists() && !mCoreAPI.PasswordOK(AirbitzApplication.getUsername(), password)) {
            mActivity.ShowOkMessageDialog(getResources().getString(R.string.fragment_recovery_mismatch_title),
                    getResources().getString(R.string.fragment_recovery_mismatch_message));
            return;
        }
        mSaveQuestionsTask = new SaveQuestionsTask(mQuestions, mAnswers);
        mSaveQuestionsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
    }


    private void InitializeQuestionViews() {
        mQuestionViews = new ArrayList<QuestionView>();
        int position = 0;
        mQuestionViews.add(new QuestionView(getActivity(), mStringQuestions, "", QuestionType.STRING, position++));
        mQuestionViews.add(new QuestionView(getActivity(), mStringQuestions, "", QuestionType.STRING, position++));
        mQuestionViews.add(new QuestionView(getActivity(), mNumericQuestions, "", QuestionType.NUMERIC, position++));
        mQuestionViews.add(new QuestionView(getActivity(), mNumericQuestions, "", QuestionType.NUMERIC, position++));
        mQuestionViews.add(new QuestionView(getActivity(), mMustQuestions, "", QuestionType.MUST, position++));
        mQuestionViews.add(new QuestionView(getActivity(), mMustQuestions, "", QuestionType.MUST, position++));

        setListWithQuestionViews(mQuestionViews);
    }

    private void setListWithQuestionViews(List<QuestionView> views) {
        mPasswordRecoveryListView.removeAllViews();
        for (View v : views) {
            mPasswordRecoveryListView.addView(v);
        }
        mPasswordRecoveryListView.invalidate();
        mQuestionViews.get(0).getEditText().requestFocus();
    }

    private void InitializeRecoveryViews(String[] questions, String[] answers) {
        mQuestionViews = new ArrayList<QuestionView>();
        int position = 0;
        for (String question : questions) {
            List<String> qs = new ArrayList<String>();
            qs.add(question);
            qs.add(getString(R.string.activity_recovery_question_default));
            QuestionView qv = new QuestionView(getActivity(), qs, answers[position], QuestionType.STRING, position++);
            mQuestionViews.add(qv);
        }

        setListWithQuestionViews(mQuestionViews);
    }

    private List<String> getUnchosenQuestions(List<String> questions) {
        List<String> unusedQuestions = new ArrayList<String>(questions);

        for (QuestionView qv : mQuestionViews) {
            String selected = qv.chosenQuestion;
            if (!selected.equals(getString(R.string.activity_recovery_question_default))) {
                unusedQuestions.remove(selected);
            }
        }

        return unusedQuestions;
    }

    private void updateQuestionLists(QuestionView notThis) {
        for (QuestionView qv : mQuestionViews) {
            if (qv != notThis) {
                List<String> unchosen;
                if (qv.mType == QuestionType.STRING) {
                    unchosen = getUnchosenQuestions(mStringQuestions);
                } else if (qv.mType == QuestionType.NUMERIC) {
                    unchosen = getUnchosenQuestions(mNumericQuestions);
                } else {
                    unchosen = getUnchosenQuestions(mMustQuestions);
                }
                qv.setAvailableQuestions(unchosen);

                if (!qv.chosenQuestion.equals(getString(R.string.activity_recovery_question_default))) {
                    unchosen.add(0, qv.chosenQuestion);
                    qv.mSpinner.setSelection(0);
                } else {
                    qv.setAvailableQuestions(unchosen);
                    int index = unchosen.indexOf(getString(R.string.activity_recovery_question_default));
                    qv.mSpinner.setSelection(index);
                }
            }
        }
    }

    private void signIn() {
    }

    public void ShowSkipQuestionsAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
        alertDialogBuilder.setTitle(getResources().getString(R.string.activity_recovery_prompt_title))
                .setMessage(getResources().getString(R.string.activity_recovery_prompt_skip))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.string_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        signIn();
                        mActivity.popFragment();
                        mActivity.finishSignup();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.string_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFetchAllQuestionsTask != null) {
            mFetchAllQuestionsTask.cancel(true);
        }
        if (mSaveQuestionsTask != null) {
            mSaveQuestionsTask.cancel(true);
        }
        if (mAttemptAnswerVerificationTask != null) {
            mAttemptAnswerVerificationTask.cancel(true);
        }
    }

    private enum QuestionType {STRING, NUMERIC, MUST}

    /**
     * Attempt to verify answers
     */
    public class AttemptAnswerVerificationTask extends AsyncTask<String, Void, Boolean> {
        private String username;
        private String answers;

        @Override
        public void onPreExecute() {
            mActivity.showModalProgress(true);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            answers = params[0];
            username = params[1];
            boolean result = mCoreAPI.recoveryAnswers(answers, username);

            // If we have otp enabled, persist the token
            String secret = mCoreAPI.GetTwoFactorSecret();
            if (secret != null) {
                mCoreAPI.OtpKeySet(username, secret);
            }
            return result;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mActivity.showModalProgress(false);

            if (success) {
                Bundle bundle = new Bundle();
                bundle.putInt(SignUpFragment.MODE, SignUpFragment.CHANGE_PASSWORD_VIA_QUESTIONS);
                bundle.putString(PasswordRecoveryFragment.QUESTIONS, answers);
                bundle.putString(PasswordRecoveryFragment.USERNAME, getArguments().getString(USERNAME));
                Fragment frag = new SignUpFragment();
                frag.setArguments(bundle);
                mActivity.pushFragmentNoAnimation(frag, NavigationActivity.Tabs.BD.ordinal());
            } else {
                mCoreAPI.otpSetError(mCoreAPI.getRecoveryAnswersError().getCode());
                if (tABC_CC.ABC_CC_InvalidOTP  == mCoreAPI.getRecoveryAnswersError().getCode()) {
                    launchTwoFactorMenu();
                } else {
                    mActivity.ShowFadingDialog(getString(R.string.activity_recovery_error_wrong_answers_message));
                }
            }
        }

        @Override
        protected void onCancelled() {
            mActivity.showModalProgress(false);
        }
    }

    private void launchTwoFactorMenu() {
        TwoFactorMenuFragment fragment = new TwoFactorMenuFragment();
        fragment.setOnTwoFactorMenuResult(this);
        Bundle bundle = new Bundle();
        bundle.putBoolean(TwoFactorMenuFragment.STORE_SECRET, false);
        bundle.putBoolean(TwoFactorMenuFragment.TEST_SECRET, false);
        bundle.putString(TwoFactorMenuFragment.USERNAME, getArguments().getString(USERNAME));
        fragment.setArguments(bundle);
        mActivity.pushFragment(fragment);
        mActivity.updateViewPager();
    }

    @Override
    public void onTwoFactorMenuResult(boolean success, String secret) {
        // This occurs before view is shown, so pickup in onResume
        mReturnFromTwoFactorScan = true;
        mTwoFactorSecret = secret;
        mTwoFactorSuccess = success;
    }

    /**
     * Represents an asynchronous question fetch task
     */
    public class GetRecoveryQuestions extends AsyncTask<Void, Void, Boolean> {
        CoreAPI.QuestionChoice[] mChoices;

        @Override
        public void onPreExecute() {
            mActivity.showModalProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            mChoices = mCoreAPI.GetQuestionChoices();
            if (mChoices.length > 0) {

                for (CoreAPI.QuestionChoice choice : mChoices) {
                    String category = choice.getCategory();
                    if (category.equals("string")) {
                        mStringCategory.put(choice.getQuestion(), (int) choice.getMinLength());
                        mStringQuestions.add(choice.getQuestion());
                    } else if (category.equals("numeric")) {
                        mNumericCategory.put(choice.getQuestion(), (int) choice.getMinLength());
                        mNumericQuestions.add(choice.getQuestion());
                    } else if (category.equals("must")) {
                        mMustCategory.put(choice.getQuestion(), (int) choice.getMinLength());
                        mMustQuestions.add(choice.getQuestion());
                    }
                }
                mStringQuestions.add(getString(R.string.activity_recovery_question_default));
                mNumericQuestions.add(getString(R.string.activity_recovery_question_default));
                mMustQuestions.add(getString(R.string.activity_recovery_question_default));
                return true;
            } else {
                Log.d(TAG, "No Questions");
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mActivity.showModalProgress(false);

            if (success) {
                InitializeQuestionViews();
            }

            if (mMode == CHANGE_QUESTIONS)
                mPasswordEditText.requestFocus();

            mFetchAllQuestionsTask = null;
        }

        @Override
        protected void onCancelled() {
            mFetchAllQuestionsTask = null;
            mActivity.showModalProgress(false);
        }

    }

    /**
     * Save Questions if Signing up or Changing questions
     */
    public class SaveQuestionsTask extends AsyncTask<Void, Void, Boolean> {

        private final String mQuestions, mAnswers;

        SaveQuestionsTask(String questions, String answers) {
            mQuestions = questions;
            mAnswers = answers;
        }

        @Override
        public void onPreExecute() {
            mActivity.showModalProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            tABC_CC result = mCoreAPI.SaveRecoveryAnswers(mQuestions, mAnswers, mPasswordEditText.getText().toString());
            return result == tABC_CC.ABC_CC_Ok;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSaveQuestionsTask = null;
            mActivity.showModalProgress(false);
            if (!success) {
                mActivity.ShowFadingDialog(getResources().getString(R.string.activity_recovery_error_save_failed));
            } else {
                mSaved = true;
                mActivity.ShowMessageDialogBackPress(getResources().getString(R.string.activity_recovery_done_title), getString(R.string.activity_recovery_done_details));
                mCoreAPI.clearRecoveryReminder();
            }
        }

        @Override
        protected void onCancelled() {
            mSaveQuestionsTask = null;
            mActivity.showModalProgress(false);
        }
    }

    private class QuestionView extends LinearLayout {
        public QuestionType mType;
        public String chosenQuestion = "";
        Context mContext;
        int mPosition;
        private int mCharLimit = 0;
        private Spinner mSpinner;
        private EditText mText;
        private PasswordRecoveryAdapter mAdapter;
        private List<String> currentQuestionList;
        private QuestionView me = this;

        public QuestionView(Context context, List<String> questions, String answer, QuestionType type, int position) {
            super(context);
            mContext = context;
            mType = type;
            mPosition = position;
            currentQuestionList = questions;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.item_password_recovery, this);

            mText = (EditText) findViewById(R.id.item_recovery_answer_edittext);
            mText.setImeOptions(EditorInfo.IME_ACTION_NEXT);

            mText.setText(answer);

            mSpinner = (Spinner) findViewById(R.id.item_recovery_question_spinner);
            mSpinner.setFocusable(true);
            mSpinner.setFocusableInTouchMode(true);
            mAdapter = new PasswordRecoveryAdapter(context, currentQuestionList);
            mAdapter.setDropDownViewResource(R.layout.item_password_recovery_spinner_dropdown);
            mSpinner.setAdapter(mAdapter);

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG, "spinner selection");
                    if (ignoreSelected || mMode == FORGOT_PASSWORD) return;

                    chosenQuestion = currentQuestionList.get(i);
                    Log.d(TAG, "spinner selection not ignored=" + chosenQuestion);
                    if (mType == QuestionType.STRING) {
                        if (mStringCategory.containsKey(chosenQuestion))
                            mCharLimit = mStringCategory.get(chosenQuestion);
                    } else if (mType == QuestionType.NUMERIC) {
                        if (mNumericCategory.containsKey(chosenQuestion))
                            mCharLimit = mNumericCategory.get(chosenQuestion);
                    } else if (mType == QuestionType.MUST) {
                        if (mMustCategory.containsKey(chosenQuestion))
                            mCharLimit = mMustCategory.get(chosenQuestion);
                    }

                    if (mSpinner.getSelectedItemPosition() != mAdapter.getCount()) {
                        QuestionView qv = mQuestionViews.get(mPosition);
                        qv.mText.setFocusableInTouchMode(true);
                        qv.mText.requestFocus();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            mSpinner.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        ignoreSelected = false;
                        mSpinner.performClick();
                    } else {
                        ignoreSelected = true;
                        updateQuestionLists(me);
                    }
                }
            });

            mText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        mActivity.showSoftKeyboard(mText);
                    }
                }
            });

            mText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (mMode == SIGN_UP || mMode == CHANGE_QUESTIONS) {
                            if (mPosition < mQuestionViews.size() - 1) {
                                mQuestionViews.get(mPosition + 1).getSpinner().requestFocus();
                                return true;
                            } else if (mPosition == mQuestionViews.size() - 1) {
                                mActivity.hideSoftKeyboard(mText);
                                mDoneSignUpButton.requestFocus();
                                return true;
                            }
                        } else {
                            if (mPosition < mQuestionViews.size() - 1) {
                                mQuestionViews.get(mPosition + 1).getEditText().requestFocus();
                                return true;
                            } else if (mPosition == mQuestionViews.size() - 1) {
                                mActivity.hideSoftKeyboard(mText);
                                mDoneSignUpButton.requestFocus();
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });

            chosenQuestion = getString(R.string.activity_recovery_question_default);
            if (mMode == FORGOT_PASSWORD) {
                findViewById(R.id.item_recovery_question_down_arrow).setVisibility(View.GONE);
                mSpinner.setSelection(0);
                mSpinner.setClickable(false);
                mSpinner.setEnabled(false);
                mSpinner.setFocusable(false);
            } else {
                mSpinner.setSelection(currentQuestionList.size() - 1);
            }
        }

        public String getSelectedQuestion() {
            return chosenQuestion;
        }

        public void setAvailableQuestions(List<String> questions) {
            if (mAdapter != null) {
                currentQuestionList = questions;
                mAdapter = new PasswordRecoveryAdapter(mContext, currentQuestionList);
                mAdapter.setDropDownViewResource(R.layout.item_password_recovery_spinner_dropdown);
                mSpinner.setAdapter(mAdapter);
            }
        }

        public Spinner getSpinner() {
            return mSpinner;
        }

        public EditText getEditText() {
            return mText;
        }

        public void setAnswer(String answer) {
            mText.setText(answer);
        }

        public String getText() {
            return mText.getText().toString();
        }

        public int getMinimumCharacters() {
            return mCharLimit;
        }
    }
}

