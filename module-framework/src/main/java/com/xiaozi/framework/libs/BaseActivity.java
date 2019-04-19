package com.xiaozi.framework.libs;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

/**
 * Created by xiaoz on 2017-10-08.
 */

public class BaseActivity extends AppCompatActivity {
    protected final String LOG_TAG = getClass().getSimpleName();
    protected Handler mHandler = new Handler();
    protected Activity mActivity = this;
    protected DisplayMetrics mDisplayMetrics = null;

    protected static final int REQUEST_PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisplayMetrics = getResources().getDisplayMetrics();
    }

    protected void initialize() {

    }

    protected void initView() {

    }

    protected void initView(Bundle savedInstanceState) {

    }
}
