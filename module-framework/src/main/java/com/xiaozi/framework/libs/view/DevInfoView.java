package com.xiaozi.framework.libs.view;

import android.content.Context;
import android.util.AttributeSet;

import com.xiaozi.framework.libs.BaseTextView;
import com.xiaozi.framework.libs.utils.Logger;

/**
 * Created by xiaoz on 2017-10-04.
 */

public class DevInfoView extends BaseTextView {
    private String mApplicationId = null;
    private String mVersionName = null;
    private int mVersionCode = 0;

    public DevInfoView(Context context) {
        super(context);
    }

    public DevInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        Logger.i(LOG_TAG, "initView");
    }

    public void setApplicationId(String applicationId) {
        Logger.i(LOG_TAG, "setApplicationId");
        Logger.d(LOG_TAG, "setApplicationId mApplicationId : " + applicationId);
        mApplicationId = applicationId;
    }

    public void setVersionName(String versionName) {
        Logger.i(LOG_TAG, "setVersionName");
        Logger.d(LOG_TAG, "setVersionName versionName : " + versionName);
        mVersionName = versionName;
    }

    public void setVersionCode(int versionCode) {
        Logger.i(LOG_TAG, "setVersionCode");
        Logger.d(LOG_TAG, "setVersionCode versionCode : " + versionCode);
        mVersionCode = versionCode;
    }

    public void updateView() {
        Logger.i(LOG_TAG, "updateView");
        Logger.d(LOG_TAG, "updateView mApplicationId : " + mApplicationId);
        Logger.d(LOG_TAG, "updateView mVersionName : " + mVersionName);
        Logger.d(LOG_TAG, "updateView mVersionCode : " + mVersionCode);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("widthPixels : %s", mDisplayMetrics.widthPixels));
        builder.append(" ");
        builder.append(String.format("heightPixels : %s", mDisplayMetrics.heightPixels));
        builder.append(" ");
        builder.append(String.format("densityDpi : %s", mDisplayMetrics.densityDpi));
        builder.append("\n");
        builder.append(String.format("APPLICATION_ID : %s", mApplicationId));
        builder.append("\n");
        builder.append(String.format("VERSION_NAME : %s", mVersionName));
        builder.append("\n");
        builder.append(String.format("VERSION_CODE : %s", mVersionCode));
        setText(builder.toString());
    }
}
