package com.xiaozi.framework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaozi.framework.libs.BaseActivity;
import com.xiaozi.framework.libs.activity.PlayerActivity;
import com.xiaozi.framework.libs.view.QRCodeView;

public class MainActivity extends BaseActivity {
    private Button mShowPlayerActivityButton = null;
    private QRCodeView mQRCodeView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        mShowPlayerActivityButton = findViewById(R.id.main_show_player_activity_button);
        mQRCodeView = findViewById(R.id.main_qrcode_view);

        mQRCodeView.setContent("This is QRCodeView");

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
