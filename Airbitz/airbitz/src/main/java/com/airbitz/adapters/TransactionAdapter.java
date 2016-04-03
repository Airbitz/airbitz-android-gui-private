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

package com.airbitz.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import co.airbitz.core.Account;
import co.airbitz.core.Transaction;
import co.airbitz.core.Utils;
import co.airbitz.core.Wallet;

import com.airbitz.AirbitzApplication;
import com.airbitz.R;
import com.airbitz.api.CoreWrapper;
import com.airbitz.fragments.directory.BusinessDirectoryFragment;
import com.airbitz.utils.RoundedTransformation;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private Wallet mWallet;
    private boolean mLoading;
    private boolean mSearch;
    private boolean mIsBitcoin = true;
    private String mCurrencyCode;
    private Account mAccount;
    private List<Transaction> mListTransaction;
    private long[] mRunningSatoshi;
    private int mRound, mDimen;

    private final Picasso mPicasso;
    private SimpleDateFormat mFormatter;
    private Typeface mBitcoinTypeface;

    public TransactionAdapter(Context context, List<Transaction> listTransaction) {
        super(context, R.layout.item_listview_transaction, listTransaction);
        mContext = context;
        mListTransaction = listTransaction;
//        createRunningSatoshi();
        mAccount = AirbitzApplication.getAccount();
        mPicasso = AirbitzApplication.getPicasso();

        mFormatter = new SimpleDateFormat("MMM dd h:mm aa", Locale.getDefault());
        mRound = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics());
        mDimen = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, mContext.getResources().getDisplayMetrics());

        mBitcoinTypeface = Typeface.createFromAsset(context.getAssets(), "font/Lato-Regular.ttf");
    }


    @Override
    public int getCount() {

        if (mListTransaction.size() == 0) {
            return 2;
        } else {
            return mListTransaction.size();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (mListTransaction.size() == 0) {
            return getEmptyView(position, convertView, parent);
//            if (mLoading) {
//                return getLoadingView(position, convertView, parent);
//            } else {
//            }
//        } else {
//            return getNormalView(position, convertView, parent);
//        }
    }

    private View getEmptyView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_listview_transaction_empty, parent, false);
        TextView siteNameTextView = (TextView) convertView.findViewById(R.id.textview_sitename);
        TextView tokenTextView = (TextView) convertView.findViewById(R.id.textview_token);

        siteNameTextView.setText("HellYeah.com");
        tokenTextView.setText("7TFG6HA");
        return convertView;
    }
//
//    private View getLoadingView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        return inflater.inflate(R.layout.item_listview_transaction_loading, parent, false);
//    }
//
//    private View getNormalView(int position, View convertView, ViewGroup parent) {
//        ViewHolderItem viewHolder;
//        if (convertView == null || null == convertView.getTag()) {
//            // well set up the ViewHolder
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.item_listview_transaction, parent, false);
//            viewHolder = new ViewHolderItem();
//            viewHolder.contactImageView = (ImageView) convertView.findViewById(R.id.imageview_contact_pic);
//            viewHolder.contactImageViewFrame = (FrameLayout) convertView.findViewById(R.id.imageview_contact_pic_frame);
//            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.textview_date);
//            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.textview_name);
//            viewHolder.runningTotalTextView = (TextView) convertView.findViewById(R.id.textview_amount_running_total);
//            viewHolder.creditAmountTextView = (TextView) convertView.findViewById(R.id.textview_amount_kredit);
//            viewHolder.confirmationsTextView = (TextView) convertView.findViewById(R.id.textview_confirmations);
//            viewHolder.dateTextView.setTypeface(BusinessDirectoryFragment.latoRegularTypeFace);
//            viewHolder.nameTextView.setTypeface(BusinessDirectoryFragment.latoRegularTypeFace);
//            viewHolder.runningTotalTextView.setTypeface(mBitcoinTypeface);
//            viewHolder.creditAmountTextView.setTypeface(mBitcoinTypeface);
//            // store the holder with the view.
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolderItem) convertView.getTag();
//        }
//
//        convertView.setBackground(mContext.getResources().getDrawable(R.drawable.wallet_list_standard));
//
//        Transaction transaction = mListTransaction.get(position);
//
//        String dateString = mFormatter.format(transaction.date());
//        viewHolder.dateTextView.setText(dateString);
//
//        long transactionSatoshis = transaction.amount();
//        long transactionSatoshisAbs = Math.abs(transactionSatoshis);
//        final int placeholder = transactionSatoshis > 0
//            ? R.drawable.ic_request : R.drawable.ic_send;
//        final int background = transactionSatoshis > 0
//            ? R.drawable.bg_icon_request : R.drawable.bg_icon_send;
//        viewHolder.contactImageViewFrame.setVisibility(View.VISIBLE);
//
//        String name = transaction.meta().name();
//        String uri = null;
//        if (0 < transaction.meta().bizid()) {
//            uri = "airbitz://business/" + transaction.meta().bizid();
//        } else {
//            uri = "airbitz://person/" + transaction.meta().name();
//        }
//        final ImageView img = viewHolder.contactImageView;
//        img.setImageResource(placeholder);
//        img.setBackgroundResource(background);
//        mPicasso.load(uri)
//                .placeholder(placeholder)
//                .transform(new RoundedTransformation(mRound, 0))
//                .into(viewHolder.contactImageView, new Callback.EmptyCallback() {
//                    @Override
//                    public void onSuccess() {
//                        img.setBackgroundResource(android.R.color.transparent);
//                    }
//                    @Override
//                    public void onError() {
//                        img.setImageResource(placeholder);
//                        img.setBackgroundResource(background);
//                    }
//                });
//        viewHolder.nameTextView.setText(name);
//
//        String btcSymbol;
//        String btcSymbolBalance = CoreWrapper.userBtcSymbol(mAccount);
//        Boolean bPositive;
//
//        if (transactionSatoshis < 0) {
//            btcSymbol = "-" + btcSymbolBalance;
//            bPositive = false;
//        } else {
//            btcSymbol = btcSymbolBalance;
//            bPositive = true;
//        }
//
//        if (mSearch) {
//            String btcCurrency = Utils.formatSatoshi(mAccount, transactionSatoshisAbs, true);
//            viewHolder.creditAmountTextView.setText(btcCurrency);
//
//            String fiatCurrency = "";
//            if (mWallet != null && mWallet.isSynced()) {
//                fiatCurrency = CoreWrapper.formatCurrency(mAccount, transactionSatoshis, mCurrencyCode, true);
//            } else {
//                fiatCurrency = "";
//            }
//            viewHolder.runningTotalTextView.setText(fiatCurrency);
//
//            if (bPositive) {
//                viewHolder.runningTotalTextView.setTextColor(mContext.getResources().getColor(R.color.green_text_dark));
//                viewHolder.creditAmountTextView.setTextColor(mContext.getResources().getColor(R.color.green_text_dark));
//            } else {
//                viewHolder.runningTotalTextView.setTextColor(mContext.getResources().getColor(R.color.red));
//                viewHolder.creditAmountTextView.setTextColor(mContext.getResources().getColor(R.color.red));
//            }
//        } else {
//            viewHolder.runningTotalTextView.setTextColor(mContext.getResources().getColor(R.color.gray_text));
//            if (bPositive) {
//                viewHolder.creditAmountTextView.setTextColor(mContext.getResources().getColor(R.color.green_text_dark));
//            } else {
//                viewHolder.creditAmountTextView.setTextColor(mContext.getResources().getColor(R.color.red));
//            }
//            if (mIsBitcoin) {
//                String walletCurrency = Utils.formatSatoshi(mAccount, transactionSatoshisAbs, false);
//                String totalCurrency = Utils.formatSatoshi(mAccount, mRunningSatoshi[position], false);
//
//                viewHolder.creditAmountTextView.setText(btcSymbol + " " + walletCurrency);
//                viewHolder.runningTotalTextView.setText(btcSymbolBalance + " " + totalCurrency);
//            } else {
//                String walletCurrency = CoreWrapper.formatCurrency(mAccount, transactionSatoshis, mCurrencyCode, true);
//                String totalCurrency = CoreWrapper.formatCurrency(mAccount, mRunningSatoshi[position], mCurrencyCode, true);
//
//                viewHolder.creditAmountTextView.setText(walletCurrency);
//                viewHolder.runningTotalTextView.setText(totalCurrency);
//            }
//        }
//        int bh = mWallet.blockHeight();
//        int th = transaction.height();
//        int confirmations = bh == 0 || th == 0 ? 0 : (bh - th) + 1;
//        if (mSearch) {
//            viewHolder.confirmationsTextView.setText(transaction.meta().category());
//            viewHolder.confirmationsTextView.setTextColor(mContext.getResources().getColor(R.color.gray_text));
//        } else {
//            viewHolder.confirmationsTextView.setTextColor(mContext.getResources().getColor(R.color.green_text_dark));
//            if (transaction.isSyncing()) {
//                viewHolder.confirmationsTextView.setText(mContext.getString(R.string.synchronizing));
//                viewHolder.confirmationsTextView.setTextColor(mContext.getResources().getColor(R.color.gray_text));
//            } else if (confirmations <= 0) {
//                viewHolder.confirmationsTextView.setText(mContext.getString(R.string.fragment_wallet_unconfirmed));
//                viewHolder.confirmationsTextView.setTextColor(mContext.getResources().getColor(R.color.red));
//            } else if (confirmations >= 6) {
//                viewHolder.confirmationsTextView.setText(mContext.getString(R.string.fragment_wallet_confirmed));
//            } else {
//                viewHolder.confirmationsTextView.setText(confirmations + mContext.getString(R.string.fragment_wallet_confirmations));
//            }
//        }
//        return convertView;
//    }

    public void selectItem(View convertView, int position) {
        if (0 == position && mListTransaction.size() == 1) {
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.wallet_list_solo_selected));
        } else if (0 == position) {
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.wallet_list_top_archive_selected));
        } else if (mListTransaction.size() - 1 == position) {
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.wallet_list_bottom_selected));
        } else {
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.wallet_list_standard_selected));
        }
    }

    static class ViewHolderItem {
        ImageView contactImageView;
        FrameLayout contactImageViewFrame;
        TextView dateTextView;
        TextView nameTextView;
        TextView runningTotalTextView;
        TextView creditAmountTextView;
        TextView confirmationsTextView;
    }
}
