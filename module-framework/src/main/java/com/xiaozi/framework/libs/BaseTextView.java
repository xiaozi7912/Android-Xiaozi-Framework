package com.xiaozi.framework.libs;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by xiaoz on 2017-10-07.
 */

public class BaseTextView extends TextView {
    protected final String LOG_TAG = getClass().getSimpleName();
    protected Context mContext = null;
    protected Handler mHandler = new Handler();
    protected DisplayMetrics mDisplayMetrics = null;

    public BaseTextView(Context context) {
        super(context);
        mContext = context;
        mDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    protected void initView() {

    }
}
