package com.xiaozi.framework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaozi.framework.libs.BaseActivity;
import com.xiaozi.framework.libs.activity.PlayerActivity;
import com.xiaozi.framework.libs.utils.Logger;
import com.xiaozi.framework.libs.view.DevInfoView;
import com.xiaozi.framework.libs.view.QRCodeView;

public class MainActivity extends BaseActivity {
    private Button mShowPlayerActivityButton = null;
    private QRCodeView mQRCodeView = null;
    private DevInfoView mDevInfoView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.init(BuildConfig.SHOW_DEV_INFO);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        mShowPlayerActivityButton = findViewById(R.id.main_show_player_activity_button);
        mQRCodeView = findViewById(R.id.main_qrcode_view);
        mDevInfoView = findViewById(R.id.main_dev_info_view);

        mQRCodeView.setContent("This is QRCodeView");
        mDevInfoView.setApplicationId(BuildConfig.APPLICATION_ID);
        mDevInfoView.setVersionName(BuildConfig.VERSION_NAME);
        mDevInfoView.setVersionCode(BuildConfig.VERSION_CODE);
        mDevInfoView.updateView();
        if (!BuildConfig.SHOW_DEV_INFO) mDevInfoView.setVisibility(View.GONE);

        mShowPlayerActivityButton.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, PlayerActivity.class);
            startActivity(intent);
        }
    };
}
