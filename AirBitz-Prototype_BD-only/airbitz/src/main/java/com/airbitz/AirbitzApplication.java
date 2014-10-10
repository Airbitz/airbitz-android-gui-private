package com.airbitz;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;

/**
 * Created by tom on 6/17/14.
 * Holds App statics for login info during the app lifecycle
 */
public class AirbitzApplication extends Application {

    public static String PREFS = "com.airbitz.prefs";
    public static String LOGIN_NAME = "com.airbitz.login_name";

    private static Login airbitzLogin = new Login();
    private static long mBackgroundedTime = 0;
    private static Context mContext;
    private static int mLastNavTab = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static boolean isLoggedIn() {
        return airbitzLogin.getUsername() != null;
    }

    public static boolean isDebugging() {
        ApplicationInfo appInfo = mContext.getApplicationInfo();
        return (appInfo != null && ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0));
    }

    public static void Login(String uname, String password) {
        if (uname != null && password != null) {
            airbitzLogin.setUsername(uname);
            airbitzLogin.setPassword(password);
            SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit();
            editor.putString(LOGIN_NAME, uname);
            editor.apply();
        }

        //TODO setup auto logout based on Settings. App being killed automatically forgets login,
        // like on reboot or force close.
    }

    public static void Logout() {
        airbitzLogin = new Login();
    }

    public static String getUsername() {
        return airbitzLogin.getUsername();
    }

    public static String getPassword() {
        return airbitzLogin.getPassword();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setLastNavTab(int tab) {
        mLastNavTab = tab;
    }

    public static int getLastNavTab() {
        return mLastNavTab;
    }

    public static void setBackgroundedTime(long time) {
        mBackgroundedTime = time;
    }

    public static long getmBackgroundedTime() {
        return mBackgroundedTime;
    }

    private static class Login {
        private String mUsername = null;
        private String mPassword = null;

        public String getUsername() {
            return mUsername;
        }

        public void setUsername(String name) {
            mUsername = name;
        }

        public String getPassword() {
            return mPassword;
        }

        public void setPassword(String word) {
            mPassword = word;
        }
    }
}
