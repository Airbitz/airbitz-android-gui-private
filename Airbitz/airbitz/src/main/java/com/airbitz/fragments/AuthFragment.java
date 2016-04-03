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

package com.airbitz.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbitz.AirbitzApplication;
import com.airbitz.R;
import com.airbitz.activities.NavigationActivity;
import com.airbitz.adapters.TransactionAdapter;
import com.airbitz.api.Constants;
import com.airbitz.api.CoreWrapper;
import com.airbitz.fragments.request.RequestFragment;
import com.airbitz.fragments.send.SendFragment;
import com.airbitz.fragments.send.SuccessFragment;
import com.airbitz.fragments.wallet.ExportFragment;
import com.airbitz.fragments.wallet.TransactionDetailFragment;

import java.util.ArrayList;
import java.util.List;

import co.airbitz.core.Transaction;
import co.airbitz.core.Utils;
import co.airbitz.core.Wallet;

public class AuthFragment extends WalletBaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = getClass().getSimpleName();

    private SwipeRefreshLayout mSwipeLayout;
    private boolean mBarIsAnimating = false;


    private ListView mListTransaction;
    private TransactionAdapter mTransactionAdapter;
    private List<Transaction> mTransactions = new ArrayList<Transaction>();
    private View mView;
    private TransactionTask mTransactionTask;
    private Handler mHandler = new Handler();

    public AuthFragment() {
        mAllowArchived = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected String getSubtitle() {
        return mActivity.getString(R.string.fragment_wallet_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        }

        mSwipeLayout = (SwipeRefreshLayout) mView.findViewById(R.id.fragment_wallet_swipe_layout);
        mSwipeLayout.setOnRefreshListener(this);

        mTransactionAdapter = new TransactionAdapter(mActivity, mTransactions);
//        mTransactionAdapter.setIsBitcoin(mOnBitcoinMode);
        mListTransaction = (ListView) mView.findViewById(R.id.listview_transaction);
        mListTransaction.setAdapter(mTransactionAdapter);


        mListTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isAdded()) {
                    return;
                }

                int newIdx = i - 1;
//                mActivity.hideSoftKeyboard(mSendButton);
                // Make sure this is not the header view and offset i by 1
                if (i >= 0 && i < mTransactions.size() + 1) {
                    Transaction trans = mTransactions.get(newIdx);
                    mTransactionAdapter.selectItem(view, newIdx);

                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.WALLET_UUID, mWallet.id());
                    bundle.putString(Constants.WALLET_TXID, trans.id());
                    Fragment fragment = new TransactionDetailFragment();
                    fragment.setArguments(bundle);

                    mActivity.pushFragment(fragment, NavigationActivity.Tabs.WALLET.ordinal());
                }
            }
        });
//        updateBalanceBar();
//        updateSendRequestButtons();
        return mView;
    }

    @Override
    public void onResume() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.getBoolean(Constants.WALLET_CREATE)) {
            mPositionNavBar = false;
            bundle.putBoolean(Constants.WALLET_CREATE, false);
            buildFragments(bundle);
            super.onResume();
            mPositionNavBar = true;
        } else {
            super.onResume();
        }
    }

    @Override
    protected void onAddOptions(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_transaction_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isMenuExpanded()) {
            return super.onOptionsItemSelected(item);
        }
        switch (item.getItemId()) {
        case android.R.id.home:
            return onBackPress();
        case R.id.action_add:
            ExportFragment.pushFragment(mActivity);
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSearchQuery(String query) {
//        if (mLoading) {
//            return;
//        }
//        startTransactionTask();
    }

    @Override
    public boolean onBackPress() {
//        if (super.onBackPress()) {
//            return true;
//        }
//        return hideSearch();
        return true;
    }

    @Override
    public boolean hideSearch() {
//        if (super.hideSearch()) {
//            if (!mLoading) {
//                mTransactionAdapter.setSearch(false);
//                startTransactionTask();
//            }
            return true;
//        }
//        return false;
    }

    @Override
    public void showWalletList() {
//        if (mSearching || getActivity() == null || !finishedResume()) {
//            return;
//        }
//        mActivity.switchFragmentThread(NavigationActivity.Tabs.WALLETS.ordinal());
    }

    @Override
    public void hideWalletList() {
    }

    @Override
    protected void blockHeightUpdate() {
//        if (null != mTransactionAdapter) {
//            mTransactionAdapter.notifyDataSetChanged();
//        }
    }

    private void buildFragments(Bundle bundle) {
//        if (bundle.getString(Constants.WALLET_FROM).equals(SuccessFragment.TYPE_REQUEST)
//                || bundle.getString(Constants.WALLET_FROM).equals(SuccessFragment.TYPE_SEND)) {
//            Fragment details = new TransactionDetailFragment();
//            details.setArguments(bundle);
//            mActivity.pushFragment(details, NavigationActivity.Tabs.WALLET.ordinal());
//        }
    }

//    private void startTransactionTask() {
//        if (mWallet == null || !mWallet.isSynced()) {
//            return;
//        }
//        if (mTransactionTask != null) {
//            mTransactionTask.cancel(false);
//        }
//        mTransactionAdapter.setLoading(mLoading);
//        mTransactionAdapter.setWallet(mWallet);
//        mTransactionAdapter.setIsBitcoin(mOnBitcoinMode);
//
//        mTransactionTask = new TransactionTask(mSearchQuery, mWallet);
//        mTransactionTask.execute();
//    }

    private void updateTransactionsListView(List<Transaction> transactions) {
        mTransactions.clear();
        mTransactions.addAll(transactions);
//        mTransactionAdapter.createRunningSatoshi();
        mTransactionAdapter.notifyDataSetChanged();

//        updateBalanceBar();
//        updateSendRequestButtons();
    }

//    private void updateSendRequestButtons() {
//        if (mLoading) {
//            updateSendRequestButtons(false, 0.5f);
//            // mActivity.ShowFadingDialog(getString(R.string.wait_until_wallets_loaded));
//        } else {
//            updateSendRequestButtons(true, 1f);
//            if (mWallet != null && mWallet.isArchived()) {
//                updateSendRequestButtons(false, 0.5f);
//            }
//        }
//    }

//    private void updateSendRequestButtons(boolean enabled, float alpha) {
//        mRequestButton.setClickable(enabled);
//        mRequestButton.setEnabled(enabled);
//        mRequestButton.setAlpha(alpha);
//        mSendButton.setClickable(enabled);
//        mSendButton.setEnabled(enabled);
//        mSendButton.setAlpha(alpha);
//    }
//
//    private void positionBalanceBar() {
//        if (mOnBitcoinMode) {
//            mBalanceSwitchLayout.setY(mBitCoinBalanceButton.getY());
//        } else {
//            mBalanceSwitchLayout.setY(mBitCoinBalanceButton.getY() + mActivity.getResources().getDimension(R.dimen.currency_switch_height));
//        }
//    }

//    protected void updateBalanceBar() {
//        // super.updateBalanceBar();
//        positionBalanceBar();
//        mTransactionAdapter.setIsBitcoin(mOnBitcoinMode);
//        mTransactionAdapter.notifyDataSetChanged();
//        updateBalances();
//    }
//
    @Override
    public void onPause() {
        super.onPause();
        if (mTransactionTask != null) {
            mTransactionTask.cancel(true);
            mTransactionTask = null;
        }
    }

    // Sum all transactions and show in total
//    private void updateBalances() {
//        if (mTransactions != null && mWallet != null && mWallet.isSynced()) {
//            long totalSatoshis = 0;
//            for (Transaction t : mTransactions) {
//                totalSatoshis += t.amount();
//            }
//
//            mBottomType.setText(mWallet.currency().code);
//            mTopType.setText(CoreWrapper.defaultBTCDenomination(mAccount));
//            mBitCoinBalanceButton.setText(
//				Utils.formatSatoshi(mAccount, totalSatoshis, true));
//            mFiatBalanceButton.setText(
//				CoreWrapper.formatCurrency(mAccount, totalSatoshis, mWallet.currency().code, true));
//
//            if (mOnBitcoinMode) {
//                mButtonMover.setText(mBitCoinBalanceButton.getText());
//                mMoverCoin.setImageResource(R.drawable.ico_coin_btc_white);
//                mMoverType.setText(mTopType.getText());
//            } else {
//                mButtonMover.setText(mFiatBalanceButton.getText());
//                mMoverCoin.setImageResource(0);
//                mMoverType.setText(mBottomType.getText());
//            }
//        } else {
//            mBottomType.setText("");
//            mTopType.setText("");
//            mBitCoinBalanceButton.setText("");
//            mFiatBalanceButton.setText("");
//            mButtonMover.setText("");
//            mMoverType.setText("");
//        }
//    }

//    private void animateBar() {
//        if (mBarIsAnimating) {
//            return;
//        }
//        mBarIsAnimating = true;
//        if (mOnBitcoinMode) {
//            mHandler.post(animateSwitchUp);
//        } else {
//            mHandler.post(animateSwitchDown);
//        }
//        updateBalances();
//    }
//
//    @Override
//    public void onExchangeRatesChange() {
//        super.onExchangeRatesChange();
//        if (!mLoading) {
//            updateBalances();
//            updateSendRequestButtons();
//        }
//    }
//
//    @Override
//    public void onWalletsLoaded() {
//        super.onWalletsLoaded();
//        if (mWallet != null) {
//            updateBalances();
//            updateSendRequestButtons();
//            startTransactionTask();
//        }
//    }
//
//    @Override
//    protected void walletChanged(Wallet newWallet) {
//        super.walletChanged(newWallet);
//        updateBalances();
//        updateSendRequestButtons();
//        startTransactionTask();
//    }

    @Override
    public void onRefresh() {
//        if (mWallet != null) {
//            mWallet.walletReconnect();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mSwipeLayout.setRefreshing(false);
//                }
//            }, 1000);
//        }
    }
//
    class TransactionTask extends AsyncTask<Void, Integer, List<Transaction>> {
        String query;
        Wallet wallet;
        public TransactionTask(String query, Wallet wallet) {
            this.query = query;
            this.wallet = wallet;
        }

        @Override
        protected List<Transaction> doInBackground(Void... params) {
            if (TextUtils.isEmpty(query)) {
                return wallet.transactions();
            } else {
                return wallet.transactionsSearch(query);
            }
        }

        @Override
        protected void onPostExecute(List<Transaction> transactions) {
            if (!isAdded()) {
                return;
            }
            updateTransactionsListView(transactions);
            mTransactionTask = null;
        }

        @Override
        protected void onCancelled() {
            mTransactionTask = null;
            super.onCancelled();
        }
    }
}
