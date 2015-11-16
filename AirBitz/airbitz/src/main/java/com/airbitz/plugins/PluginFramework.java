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

package com.airbitz.plugins;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.airbitz.R;
import com.airbitz.models.Wallet;
import com.airbitz.api.CoreAPI;
import com.airbitz.AirbitzApplication;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PluginFramework {
    public static String TAG = PluginFramework.class.getSimpleName();
    public static String JS_BACK = "javascript:window.Airbitz.ui.back();";
    public static String JS_CALLBACK = "javascript:Airbitz._callbacks[%s]('%s');";
    public static String JS_EXCHANGE_UPDATE = "javascript:Airbitz._bridge.exchangeRateUpdate();";
    public static String JS_WALLET_UPDATE = "javascript:Airbitz._bridge.walletChanged('%s');";
    public static String JS_DENOM_UPDATE = "javascript:Airbitz._bridge.denominationUpdate('%s');";

    static public class Plugin {
        String pluginId;
        String sourceFile;
        String name;
        String provider;
        String country;
        Map<String, String> env;

        Plugin() {
            env = new HashMap<String, String>();
        }
    }

    static class PluginList {
        List<Plugin> mPlugins;

        PluginList() {
            CoreAPI api = CoreAPI.getApi();
            mPlugins = new LinkedList<Plugin>();

            Plugin plugin;
            plugin = new Plugin();
            plugin.pluginId = "com.glidera.us";
            plugin.sourceFile = "file:///android_asset/glidera.html";
            plugin.name = "Glidera US/Canada (beta)";
            plugin.provider = "glidera";
            plugin.country = "US";
            plugin.env.put("SANDBOX", String.valueOf(api.isTestNet()));
            plugin.env.put("GLIDERA_CLIENT_ID", AirbitzApplication.getContext().getString(R.string.glidera_client_id));
            plugin.env.put("REDIRECT_URI", "airbitz://plugin/glidera/" + plugin.country + "/");
            plugin.env.put("AIRBITZ_STATS_KEY", AirbitzApplication.getContext().getString(R.string.airbitz_business_directory_key));
            mPlugins.add(plugin);

            plugin = new Plugin();
            plugin.pluginId = "com.foldapp";
            plugin.sourceFile = "file:///android_asset/foldapp.html";
            plugin.name = "FoldApp (Alpha)";
            plugin.provider = "foldapp";
            mPlugins.add(plugin);
        }
    }

    private static PluginList mInstance;

    public static List<Plugin> getPlugins() {
        if (mInstance == null) {
            mInstance = new PluginList();
        }
        return mInstance.mPlugins;
    }

    public interface UiHandler {
        public void showAlert(String title, String message, boolean showSpinner);
        public void setTitle(String title);
        public void launchSend(final String cbid, final String uuid, final String address,
                               final long amountSatoshi, final double amountFiat,
                               final String label, final String category, final String notes);
        public void showNavBar();
        public void hideNavBar();
        public void back();
        public void exit();

        public void stackClear();
        public void stackPush(String path);
        public void stackPop();
    }

    private static abstract class CallbackTask extends AsyncTask<Void, Void, String> {
        String cbid;
        PluginFramework mFramework;
        CallbackTask(String cbid, PluginFramework framework) {
            this.cbid = cbid;
            this.mFramework = framework;
        }

        protected void onPostExecute(String data) {
            Log.d(TAG, cbid + " " + data);
            mFramework.loadUrl(String.format(JS_CALLBACK, cbid, data));
        }
    }

    interface ToJson {
        Object toJson() throws JSONException;
    }

    private static JSONObject jsonResult(ToJson result) {
        JSONObject object = new JSONObject();
        try {
            object.put("result", result.toJson());
            object.put("success", true);
        } catch (JSONException e) {
            return jsonError();
        }
        return object;
    }

    private static JSONObject jsonResponse(boolean v) {
        JSONObject object = new JSONObject();
        try {
            object.put("success", v);
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }
        return object;
    }

    public static JSONObject jsonSuccess() {
        return jsonResponse(true);
    }

    public static JSONObject jsonError() {
        return jsonResponse(false);
    }

    private static class ToArray extends JSONArray implements ToJson {
        public Object toJson() throws JSONException {
            return (Object) this;
        }
    }

    private static class ToObject extends JSONObject implements ToJson {
        public Object toJson() throws JSONException {
            return (Object) this;
        }
    }

    private static class JsonValue<T> implements ToJson {
        T l;

        JsonValue(T l) {
            this.l = l;
        }

        public Object toJson() throws JSONException {
            JSONObject object = new JSONObject();
            object.put("value", l);
            return object;
        }
    }

    private static class PluginWallet implements ToJson {
        Wallet wallet;

        PluginWallet(Wallet wallet) {
            this.wallet = wallet;
        }

        public Object toJson() throws JSONException {
            JSONObject object = new JSONObject();
            object.put("id", wallet.getUUID());
            object.put("name", wallet.getName());
            object.put("currencyNum", wallet.getCurrencyNum());
            object.put("balance", wallet.getBalanceSatoshi());
            return object;
        }
    }

    private static class PluginReceiveRequest implements ToJson {
        String requestId;
        String address;

        public PluginReceiveRequest(Wallet wallet, String name, String category, String notes, long amountSatoshi, double amountFiat) {
            CoreAPI api = CoreAPI.getApi();
            requestId = api.createReceiveRequestFor(wallet, name, notes, category, amountFiat, amountSatoshi);
            address = api.getRequestAddress(wallet.getUUID(), requestId);
        }

        public Object toJson() throws JSONException {
            JSONObject object = new JSONObject();
            object.put("requestId", requestId);
            object.put("address", address);
            return object;
        }
    }

    private static class PluginContext {
        Context context;
        CoreAPI api;
        UiHandler handler;
        Plugin plugin;
        PluginFramework framework;

        PluginContext(PluginFramework framework, Plugin plugin, UiHandler handler) {
            this.api = CoreAPI.getApi();
            this.framework = framework;
            this.plugin = plugin;
            this.handler = handler;
        }

        @JavascriptInterface
        public String bitidAddress(String uri, String message) {
            CoreAPI.BitidSignature bitid = api.bitidSignature(uri, message);
            return bitid.address;
        }

        @JavascriptInterface
        public String bitidSignature(String uri, String message) {
            CoreAPI.BitidSignature bitid = api.bitidSignature(uri, message);
            return bitid.signature;
        }

        @JavascriptInterface
        public void selectedWallet(String cbid) {
            CallbackTask task = new CallbackTask(cbid, framework) {
                @Override
                public String doInBackground(Void... v) {
                    return jsonResult(new PluginWallet(framework.mWallet)).toString();
                }
            };
            task.execute();
        }

        @JavascriptInterface
        public void wallets(String cbid) {
            CallbackTask task = new CallbackTask(cbid, framework) {
                @Override
                public String doInBackground(Void... v) {
                    List<Wallet> coreWallets = api.getCoreActiveWallets();
                    if (null == coreWallets) {
                        return jsonError().toString();
                    }
                    ToArray wallets = new ToArray();
                    try {
                        for (Wallet w : coreWallets) {
                            wallets.put(new PluginWallet(w).toJson());
                        }
                        return jsonResult(wallets).toString();
                    } catch (JSONException e) {
                        return jsonError().toString();
                    }
                }
            };
            task.execute();
        }

        @JavascriptInterface
        public void createReceiveRequest(final String cbid, final String walletUUID,
                                         final String name, final String category,
                                         final String notes, final long amountSatoshi,
                                         final double amountFiat) {
            CallbackTask task = new CallbackTask(cbid, framework) {
                @Override
                public String doInBackground(Void... v) {
                    Wallet wallet = api.getWalletFromUUID(walletUUID);
                    if (null != wallet) {
                        return jsonResult(new PluginReceiveRequest(wallet, name, category, notes, amountSatoshi, amountFiat)).toString();
                    } else {
                        return jsonError().toString();
                    }
                }
            };
            task.execute();
        }

        @JavascriptInterface
        public void requestSpend(String cbid, String uuid, String address, long amountSatoshi,
                                 double amountFiat, String label, String category, String notes) {
            handler.launchSend(cbid, uuid, address, amountSatoshi, amountFiat, label, category, notes);
        }

        @JavascriptInterface
        public String finalizeRequest(String walletUUID, String requestId) {
            JsonValue value = new JsonValue<Boolean>(api.finalizeRequest(walletUUID, requestId));
            return jsonResult(value).toString();
        }

        @JavascriptInterface
        public void writeData(String key, String value) {
            Log.d(TAG, "writeData: " + key + ": " + value);
            api.pluginDataSet(plugin.pluginId, key, value);
        }

        @JavascriptInterface
        public void clearData() {
            Log.d(TAG, "clearData");
            api.pluginDataClear(plugin.pluginId);
        }

        @JavascriptInterface
        public String readData(String key) {
            String s =  api.pluginDataGet(plugin.pluginId, key);
            Log.d(TAG, "readData: " + key + ": " + s);
            return s;
        }

        @JavascriptInterface
        public String getBtcDenomination() {
            return jsonResult(new JsonValue<String>(api.getDefaultBTCDenomination())).toString();
        }

        @JavascriptInterface
        public String satoshiToCurrency(long satoshi, int currencyNum) {
            double currency = api.SatoshiToCurrency(satoshi, currencyNum);
            return jsonResult(new JsonValue<Double>(currency)).toString();
        }

        @JavascriptInterface
        public String currencyToSatoshi(String currency, int currencyNum) {
            long satoshi = api.CurrencyToSatoshi(Double.parseDouble(currency), currencyNum);
            return jsonResult(new JsonValue<Long>(satoshi)).toString();
        }

        @JavascriptInterface
        public String formatSatoshi(long satoshi, boolean withSymbol) {
            String formatted = api.formatSatoshi(satoshi, withSymbol);
            return jsonResult(new JsonValue<String>(formatted)).toString();
        }

        @JavascriptInterface
        public String formatCurrency(String currency, int currencyNum, boolean withSymbol) {
            String formatted = api.formatCurrency(Double.parseDouble(currency), currencyNum, withSymbol);
            return jsonResult(new JsonValue<String>(formatted)).toString();
        }

        @JavascriptInterface
        public String getConfig(String key) {
            Log.d(TAG, "key/value " + key + ":" + plugin.env.get(key));
            return plugin.env.get(key);
        }

        @JavascriptInterface
        public void showAlert(String title, String message, boolean showSpinner) {
            handler.showAlert(title, message, showSpinner);
        }

        @JavascriptInterface
        public void title(String title) {
            handler.setTitle(title);
        }

        @JavascriptInterface
        public void showNavBar() {
            handler.showNavBar();
        }

        @JavascriptInterface
        public void hideNavBar() {
            handler.hideNavBar();
        }

        @JavascriptInterface
        public void exit() {
            handler.exit();
        }

        @JavascriptInterface
        public void navStackClear() {
            handler.stackClear();
        }

        @JavascriptInterface
        public void navStackPush(String path) {
            handler.stackPush(path);
        }

        @JavascriptInterface
        public void navStackPop() {
            handler.stackPop();
        }
    }

    private UiHandler handler;
    private WebView mWebView;
    private CoreAPI mCoreAPI;
    private Wallet mWallet;
    private String mLastUrl;

    public PluginFramework(UiHandler handler) {
        this.handler = handler;
        mCoreAPI = CoreAPI.getApi();
    }

    public void setup() {
    }

    public void cleanup() {
    }

    public void setWallet(Wallet wallet) {
        mWallet = wallet;
        loadUrl(String.format(JS_WALLET_UPDATE, jsonResult(new PluginWallet(wallet)).toString()));
    }

    public void updateDenomation() {
        String denomination = mCoreAPI.getDefaultBTCDenomination();
        loadUrl(String.format(JS_DENOM_UPDATE, jsonResult(new JsonValue(denomination)).toString()));
    }

    public static boolean isInsidePlugin(Stack<String> nav) {
        return !(nav.get(nav.size() - 1).contains("http://")
                || nav.get(nav.size() - 1).contains("https://"));
    }

    public void sendSuccess(String cbid, String walletUUID, String txId) {
        String hex = mCoreAPI.getRawTransaction(walletUUID, txId);
        Log.d(TAG, hex);
        loadUrl(String.format(JS_CALLBACK, cbid, jsonResult(new JsonValue(hex)).toString()));
    }

    public void sendBack(String cbid) {
        try {
            ToObject object = new ToObject();
            object.put("back", true);
            loadUrl(String.format(JS_CALLBACK, cbid, jsonResult(object).toString()));
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }

    public void sendError(String cbid) {
        loadUrl(String.format(JS_CALLBACK, cbid, jsonError().toString()));
    }

    public void back() {
        loadUrl(PluginFramework.JS_BACK);
    }

    public void loadUrl(final String url) {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(url);
            }
        });
    }

    public void buildPluginView(final Plugin plugin, final Activity activity, WebView webView) {
        final PluginContext pluginContext = new PluginContext(this, plugin, handler);
        mWebView = webView;
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                Log.d(TAG, message + " -- From line " + lineNumber);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, url);
                if (url.contains("airbitz://")) {
                    Uri uri = Uri.parse(url);
                    // If this is an airbitz URI plugin
                    if ("airbitz".equals(uri.getScheme()) && "plugin".equals(uri.getHost())) {
                        url = plugin.sourceFile + "?" + uri.getEncodedQuery();
                        view.loadUrl(url);
                        return true;
                    }
                } else if (url.contains("bitid://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(intent);
                    return false;
                } else if (url.contains("file://")) {
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setInitialScale(1);
        mWebView.clearFormData();
        mWebView.clearCache(true);
        mWebView.addJavascriptInterface(pluginContext, "_native");
    }

    public void onResume(WebView webView) {
        mWebView = webView;
    }

    public void onPause() {
        mWebView = null;
    }

    public void destroy() {
        if (null != mWebView) {
            mWebView.onPause();
            mWebView.pauseTimers();
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl("about:blank");
        }
        mWebView = null;
        mCoreAPI = null;
        mWallet = null;
    }
}
