package com.airbitz.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbitz.R;
import com.airbitz.activities.NavigationActivity;
import com.airbitz.adapters.TransactionAdapter;
import com.airbitz.api.AirbitzAPI;
import com.airbitz.api.CoreAPI;
import com.airbitz.models.BusinessDetail;
import com.airbitz.models.Transaction;
import com.airbitz.models.Wallet;
import com.airbitz.objects.HighlightOnPressImageButton;
import com.airbitz.objects.ResizableImageView;
import com.airbitz.utils.Common;
import com.airbitz.utils.ListViewUtility;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created on 2/13/14.
 */
public class WalletFragment extends Fragment
        implements CoreAPI.OnExchangeRatesChange,
        NavigationActivity.OnWalletUpdated {
    private final String TAG = getClass().getSimpleName();

    private EditText mSearchField;
    private LinearLayout mSearchLayout;

    private HighlightOnPressImageButton mExportButton;
    private HighlightOnPressImageButton mHelpButton;
    private HighlightOnPressImageButton mBackButton;
    private HighlightOnPressImageButton mSearchButton;

    private boolean searchPage = false;

    private ResizableImageView mRequestButton;
    private ResizableImageView mSendButton;

    private TextView mTitleTextView;

    private int SEARCH_ANIMATION_DURATION = 500;
    private float mSearchBarHeight;
    private float mScrollViewY;

    private ImageView mMoverCoin;
    private TextView mMoverType;
    private TextView mBottomType;
    private TextView mTopType;

    private View mDummyFocus;

    private RelativeLayout exportLayout;
    private LinearLayout sendRequestLayout;

    private RelativeLayout mParentLayout;

    private ScrollView mScrollView;

    private Button mButtonBitcoinBalance;
    private Button mButtonFiatBalance;
    private Button mButtonMover;

    private RelativeLayout switchable;
    private RelativeLayout switchContainer;

    private boolean mOnBitcoinMode = true;

    private EditText mWalletNameEditText;

    private ListView mListTransaction;
    private View mProgressView;

    private TransactionAdapter mTransactionAdapter;

    private List<Transaction> mTransactions;
    private List<Transaction> mAllTransactions;
    private LinkedHashMap<String, Uri> mCombinedPhotos;

    private Wallet mWallet;
    private CoreAPI mCoreAPI;
    private View mView;

    static final String TXS = "TXS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoreAPI = CoreAPI.getApi();

        Bundle bundle = getArguments();
        if (bundle != null) {
            if(bundle.getString(WalletsFragment.FROM_SOURCE)!=null) {
                String walletUUID = bundle.getString(Wallet.WALLET_UUID);
                if (walletUUID == null || walletUUID.isEmpty()) {
                    Log.d("WalletFragment", "no detail info");
                } else {
                    mWallet = mCoreAPI.getWalletFromUUID(walletUUID);
                }
                if (mTransactions == null) {
                    mTransactions = new ArrayList<Transaction>();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView==null) {
            mView = inflater.inflate(R.layout.fragment_wallet, container, false);
            mOnBitcoinMode = true;
            searchPage=false;
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mParentLayout = (RelativeLayout) mView.findViewById(R.id.fragment_wallet_parent_layout);
        mScrollView = (ScrollView) mView.findViewById(R.id.fragment_wallet_scrollview);

        mAllTransactions = new ArrayList<Transaction>();
        mAllTransactions.addAll(mTransactions);

        mCombinedPhotos = Common.GetMatchedContactsList(getActivity(), null);
        mTransactionAdapter = new TransactionAdapter(getActivity(), mWallet, mTransactions, mCombinedPhotos);

        mSearchField = (EditText) mView.findViewById(R.id.fragment_search_edittext);
        mSearchButton = (HighlightOnPressImageButton) mView.findViewById(R.id.fragment_wallet_search_button);
        mSearchLayout = (LinearLayout) mView.findViewById(R.id.fragment_wallet_search_layout);

        mSendButton = (ResizableImageView) mView.findViewById(R.id.fragment_wallet_send_button);
        mRequestButton = (ResizableImageView) mView.findViewById(R.id.fragment_wallet_request_button);
        mWalletNameEditText = (EditText) mView.findViewById(R.id.fragment_wallet_walletname_edittext);

        mExportButton = (HighlightOnPressImageButton) mView.findViewById(R.id.fragment_wallet_export_button);
        mBackButton = (HighlightOnPressImageButton) mView.findViewById(R.id.fragment_wallet_back_button);
        mButtonMover = (Button) mView.findViewById(R.id.button_mover);
        exportLayout = (RelativeLayout) mView.findViewById(R.id.fragment_wallet_export_layout);
        sendRequestLayout = (LinearLayout) mView.findViewById(R.id.fragment_wallet_sendrequest_layout);

        mDummyFocus = mView.findViewById(R.id.fragment_wallet_dummy_focus);

        switchable = (RelativeLayout) mView.findViewById(R.id.switchable);
        switchContainer = (RelativeLayout) mView.findViewById(R.id.layout_balance);

        mMoverCoin = (ImageView) mView.findViewById(R.id.button_mover_coin);
        mMoverType = (TextView) mView.findViewById(R.id.button_mover_type);
        mBottomType = (TextView) mView.findViewById(R.id.bottom_type);
        mTopType = (TextView) mView.findViewById(R.id.top_type);

        mHelpButton = (HighlightOnPressImageButton) mView.findViewById(R.id.fragment_wallet_help_button);
        mTitleTextView = (TextView) mView.findViewById(R.id.fragment_wallet_title_textview);

        mButtonBitcoinBalance = (Button) mView.findViewById(R.id.back_button_top);
        mButtonFiatBalance = (Button) mView.findViewById(R.id.back_button_bottom);

        mListTransaction = (ListView) mView.findViewById(R.id.listview_transaction);
        mListTransaction.setAdapter(mTransactionAdapter);
        ListViewUtility.setTransactionListViewHeightBasedOnChildren(mListTransaction, mTransactions.size());

        mProgressView = (View) mView.findViewById(android.R.id.empty);
        mProgressView.setVisibility(View.GONE);

        mTitleTextView.setTypeface(NavigationActivity.montserratBoldTypeFace);
        mWalletNameEditText.setTypeface(NavigationActivity.latoBlackTypeFace);
        mSearchField.setTypeface(NavigationActivity.helveticaNeueTypeFace);
        mButtonBitcoinBalance.setTypeface(NavigationActivity.latoRegularTypeFace);
        mButtonFiatBalance.setTypeface(NavigationActivity.latoRegularTypeFace);
        mButtonMover.setTypeface(NavigationActivity.latoRegularTypeFace, Typeface.BOLD);

        mWalletNameEditText.setText(mWallet.getName());
        mWalletNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    ((NavigationActivity) getActivity()).showSoftKeyboard(mWalletNameEditText);
                } else {
                    if (!Common.isBadWalletName(mWalletNameEditText.getText().toString())) {
                        mWallet.setName(mWalletNameEditText.getText().toString());
                        mCoreAPI.renameWallet(mWallet);
                    }
                }
            }
        });

        mWalletNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (Common.isBadWalletName(mWalletNameEditText.getText().toString())) {
                        Common.alertBadWalletName(WalletFragment.this.getActivity());
                        return false;
                    } else {
                        ((NavigationActivity) getActivity()).hideSoftKeyboard(mWalletNameEditText);
                        return true;
                    }
                }
                return false;
            }
        });

        mSearchField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    ((NavigationActivity) getActivity()).showSoftKeyboard(mSearchField);
                }
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetSearchVisibility(true);
            }
        });

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (mSearchLayout.getVisibility() == View.GONE) {
                    return;
                }

                try {
                    // Only include cached searches if text is empty.
                    if (mSearchTask != null && mSearchTask.getStatus() == AsyncTask.Status.RUNNING) {
                        mSearchTask.cancel(true);
                    }
                    if (editable.toString().isEmpty()) {
                        updateTransactionsListView(mAllTransactions);
                    } else {
                        mSearchTask = new SearchTask();
                        mSearchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, editable.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    ((NavigationActivity) getActivity()).hideSoftKeyboard(mSearchField);
                    return true;
                }
                return false;
            }
        });

        mButtonBitcoinBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBitcoinMode = true;
                animateBar();
            }
        });

        mButtonFiatBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBitcoinMode = false;
                animateBar();
            }
        });

        mButtonMover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBitcoinMode = !mOnBitcoinMode;
                animateBar();
            }
        });
        mExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationActivity) getActivity()).hideSoftKeyboard(mExportButton);
                Fragment fragment = new ExportFragment();
                ((NavigationActivity) getActivity()).pushFragment(fragment, NavigationActivity.Tabs.WALLET.ordinal());
            }
        });

        mRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationActivity) getActivity()).hideSoftKeyboard(mSendButton);
                mSendButton.setClickable(false);
                Bundle bundle = new Bundle();
                bundle.putString(RequestFragment.FROM_UUID, mWallet.getUUID());
                ((NavigationActivity) getActivity()).resetFragmentThreadToBaseFragment(NavigationActivity.Tabs.REQUEST.ordinal());
                ((NavigationActivity) getActivity()).switchFragmentThread(NavigationActivity.Tabs.REQUEST.ordinal(), bundle);
                mSendButton.setClickable(true);
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationActivity) getActivity()).hideSoftKeyboard(mSendButton);
                mRequestButton.setClickable(false);
                Bundle bundle = new Bundle();
                bundle.putString(SendFragment.UUID, mWallet.getUUID());
                ((NavigationActivity) getActivity()).resetFragmentThreadToBaseFragment(NavigationActivity.Tabs.SEND.ordinal());
                ((NavigationActivity) getActivity()).switchFragmentThread(NavigationActivity.Tabs.SEND.ordinal(), bundle);
                mRequestButton.setClickable(true);
            }
        });

        mListTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((NavigationActivity) getActivity()).hideSoftKeyboard(mSendButton);
                TransactionAdapter a = (TransactionAdapter) adapterView.getAdapter();
                a.selectItem(view, i);
                Bundle bundle = new Bundle();
                bundle.putString(Wallet.WALLET_UUID, mWallet.getUUID());
                Transaction trans = mTransactions.get(i);
                bundle.putString(Transaction.TXID, trans.getID());
                Fragment fragment = new TransactionDetailFragment();
                fragment.setArguments(bundle);
                ((NavigationActivity) getActivity()).pushFragment(fragment, NavigationActivity.Tabs.WALLET.ordinal());

            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchPage){
                    SetSearchVisibility(false);
                    mTransactionAdapter.setSearch(false);
                    startTransactionTask();
                }else{
                    getActivity().onBackPressed();
                }
            }
        });

        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationActivity)getActivity()).pushFragment(new HelpFragment(HelpFragment.TRANSACTIONS), NavigationActivity.Tabs.WALLET.ordinal());
            }
        });

        ((NavigationActivity) getActivity()).hideSoftKeyboard(mSendButton);
        return mView;
    }

    private TransactionTask mTransactionTask;
    class TransactionTask extends AsyncTask<Wallet, Integer, List<Transaction>> {

        public TransactionTask() {
        }

        @Override
        protected void onPreExecute() {
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Transaction> doInBackground(Wallet...wallet) {
            return mCoreAPI.loadAllTransactions(wallet[0]);
        }

        @Override
        protected void onPostExecute(List<Transaction> transactions) {
            if (getActivity() == null) {
                return;
            }
            updateTransactionsListView(transactions);

            mTransactionTask = null;
            mProgressView.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled(){
            mTransactionTask = null;
            super.onCancelled();
            mProgressView.setVisibility(View.GONE);
        }
    }

    private SearchTask mSearchTask;
    class SearchTask extends AsyncTask<String, Integer, List<Transaction>> {

        public SearchTask() { }

        @Override protected List<Transaction> doInBackground(String... strings) {
            return searchTransactions(strings[0]);
        }

        @Override protected void onPostExecute(List<Transaction> transactions) {
            if (getActivity()==null) {
                return;
            }
            updateTransactionsListView(transactions);
            mSearchTask = null;
        }

        @Override protected void onCancelled(){
            mSearchTask = null;
            super.onCancelled();
        }
    }

    private void SetSearchVisibility(boolean visible) {
        if(visible) {
            mSearchLayout.setX(mParentLayout.getWidth());
            mSearchLayout.setVisibility(View.VISIBLE);
            mSearchLayout.animate().translationX(0).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSearchLayout.setVisibility(View.VISIBLE);
                }
            });
            switchContainer.animate().alpha(0f).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    switchContainer.setVisibility(View.GONE);
                }
            });
            exportLayout.animate().alpha(0f).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    exportLayout.setVisibility(View.INVISIBLE);
                }
            });
            sendRequestLayout.animate().alpha(0f).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    sendRequestLayout.setVisibility(View.GONE);
                }
            });
            mSearchBarHeight = getActivity().getResources().getDimension(R.dimen.fragment_wallet_search_scroll_animation_height);
            mScrollViewY = getActivity().getResources().getDimension(R.dimen.fragment_wallet_search_scroll_animation_top);

            mScrollView.animate().translationY(-mScrollViewY+mSearchBarHeight).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    mScrollView.setVisibility(View.VISIBLE);
                }
            });
            mSearchField.requestFocus();
        } else {
            ((NavigationActivity) getActivity()).hideSoftKeyboard(mSearchField);
            mSearchLayout.animate().translationX(mParentLayout.getWidth()).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSearchLayout.setVisibility(View.GONE);
                }
            });
            switchContainer.animate().alpha(1f).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    switchContainer.setVisibility(View.VISIBLE);
                }
            });
            exportLayout.animate().alpha(1f).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    exportLayout.setVisibility(View.VISIBLE);
                }
            });
            sendRequestLayout.animate().alpha(1f).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    sendRequestLayout.setVisibility(View.VISIBLE);
                }
            });
            mScrollView.animate().translationY(mScrollViewY-mSearchBarHeight).setDuration(SEARCH_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mScrollView.setVisibility(View.VISIBLE);
                }
            });
        }
        searchPage = visible;
        mTransactionAdapter.setSearch(visible);
        mTransactionAdapter.notifyDataSetChanged();
    }

    private void startTransactionTask() {
        if (mTransactionTask != null) {
            mTransactionTask.cancel(false);
        }
        mTransactionTask = new TransactionTask();
        mTransactionTask.execute(mWallet);
    }

    private void updateTransactionsListView(List<Transaction> transactions) {
        mTransactions.clear();
        mTransactions.addAll(transactions);
        mTransactionAdapter.createRunningSatoshi();
        mTransactionAdapter.notifyDataSetChanged();
        ListViewUtility.setTransactionListViewHeightBasedOnChildren(
            mListTransaction, transactions.size());

        FindBizIdThumbnails();
    }

    @Override
    public void onResume(){
        super.onResume();
        mCoreAPI.addExchangeRateChangeListener(this);
        ((NavigationActivity) getActivity()).setOnWalletUpdated(this);

        mWallet = mCoreAPI.getWalletFromUUID(mWallet.getUUID());
        if (!mTransactions.isEmpty()) {
            List<Transaction> txs = new ArrayList<Transaction>(mTransactions);
            updateTransactionsListView(txs);
        }
        startTransactionTask();

        UpdateBalances();
        mRequestButton.setPressed(false);
        mSendButton.setPressed(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mCoreAPI.removeExchangeRateChangeListener(this);
        ((NavigationActivity) getActivity()).setOnWalletUpdated(null);
    }

    // Sum all transactions and show in total
    private void UpdateBalances() {
        long totalSatoshis = mWallet.getBalanceSatoshi();

        mBottomType.setText((mCoreAPI.getCurrencyAcronyms())[mCoreAPI.CurrencyIndex(mWallet.getCurrencyNum())]);
        mTopType.setText(mCoreAPI.getDefaultBTCDenomination());
        mButtonBitcoinBalance.setText(mCoreAPI.formatSatoshi(totalSatoshis, true));
        String temp = mCoreAPI.FormatCurrency(totalSatoshis, mWallet.getCurrencyNum(), false, true);
        mButtonFiatBalance.setText(temp);
        if(mOnBitcoinMode) {
            mButtonMover.setText(mButtonBitcoinBalance.getText());
            mMoverCoin.setImageResource(R.drawable.ico_coin_btc_white);
            mMoverType.setText(mTopType.getText());
        } else {
            mButtonMover.setText(mButtonFiatBalance.getText());
            mMoverCoin.setImageResource(0);
            mMoverType.setText(mBottomType.getText());
        }
    }


    private List<Transaction> searchTransactions(String term) {
        return mCoreAPI.searchTransactionsIn(mWallet, term);
    }

    private void animateBar(){
        if(mOnBitcoinMode) {
            mHandler.post(animateSwitchUp);
        }else{
            mHandler.post(animateSwitchDown);
        }
        UpdateBalances();
    }

    private Handler mHandler = new Handler();
    Runnable animateSwitchUp = new Runnable() {
        @Override
        public void run() {
            ObjectAnimator animator = ObjectAnimator.ofFloat(switchable, "translationY", (getActivity().getResources().getDimension(R.dimen.currency_switch_height)), 0);
            animator.setDuration(100);
            animator.addListener(endListener);
            animator.start();
        }
    };

    Runnable animateSwitchDown = new Runnable() {
        @Override
        public void run() {
            ObjectAnimator animator = ObjectAnimator.ofFloat(switchable,"translationY",0,(getActivity().getResources().getDimension(R.dimen.currency_switch_height)));
            animator.setDuration(100);
            animator.addListener(endListener);
            animator.start();
        }
    };

    Animator.AnimatorListener endListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationEnd(Animator animator) {
            mTransactionAdapter.setIsBitcoin(mOnBitcoinMode);
            mTransactionAdapter.notifyDataSetChanged();
        }

        @Override public void onAnimationCancel(Animator animator) { }
        @Override public void onAnimationStart(Animator animator) { }
        @Override public void onAnimationRepeat(Animator animator) { }
    };

    @Override
    public void OnExchangeRatesChange() {
        UpdateBalances();
    }

    @Override
    public void onWalletUpdated() {
        if (mWallet!=null) {
            Common.LogD(TAG, "Reloading wallet");
            mCoreAPI.reloadWallet(mWallet);
            mWalletNameEditText.setText(mWallet.getName());

            startTransactionTask();
        }
    }

    private void FindBizIdThumbnails() {
        for(Transaction transaction : mTransactions) {
            if(!mCombinedPhotos.containsKey(transaction.getName()) && transaction.getmBizId()!=0) {
                GetBizIdThumbnailAsyncTask task = new GetBizIdThumbnailAsyncTask(transaction.getName(), transaction.getmBizId());
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    class GetBizIdThumbnailAsyncTask extends AsyncTask<Void, Void, BusinessDetail> {
        private AirbitzAPI api = AirbitzAPI.getApi();
        private String mName;
        private long mBizId;

        GetBizIdThumbnailAsyncTask(String name, long id) {
            mName = name;
            mBizId = id;
        }

        @Override
        protected BusinessDetail doInBackground(Void... voids) {
            return api.getHttpBusiness((int) mBizId);
        }

        @Override
        protected void onPostExecute(BusinessDetail business) {
            if(business!=null && business.getSquareImageLink()!=null) {
                Uri uri = Uri.parse(business.getSquareImageLink());
                Common.LogD(TAG, "Got "+uri);
                mCombinedPhotos.put(mName, uri);
                mTransactionAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
