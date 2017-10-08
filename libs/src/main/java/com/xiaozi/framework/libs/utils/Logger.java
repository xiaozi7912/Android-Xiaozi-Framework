package com.xiaozi.framework.libs.utils;

import android.util.Log;

import com.xiaozi.framework.libs.BuildConfig;

/**
 * Created by xiaoz on 2017-10-08.
 */

public class Logger {
    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG) Log.i(tag, message);
    }

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) Log.d(tag, message);
    }
}
