package com.xiaozi.framework.libs.view;

import android.content.Context;
import android.util.AttributeSet;

import com.xiaozi.framework.libs.BaseTextView;
import com.xiaozi.framework.libs.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoz on 2017-10-07.
 */

public class NetworkInfoView extends BaseTextView {
    private final static long TIME_CHECK_NETWORK_ADDRESS_DELAY = 2 * 1000L;

    private boolean mIsAttatched = false;
    private String mResultString = null;

    public NetworkInfoView(Context context) {
        super(context);
    }

    public NetworkInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.i(LOG_TAG, "onAttachedToWindow");
        mIsAttatched = true;
        startCheckNetworkAddress();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.i(LOG_TAG, "onDetachedFromWindow");
        mIsAttatched = false;
    }

    private void startCheckNetworkAddress() {
        Logger.i(LOG_TAG, "startCheckNetworkAddress");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mIsAttatched) {
                    checkNetworkAddress();

                    try {
                        Thread.sleep(TIME_CHECK_NETWORK_ADDRESS_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void checkNetworkAddress() {
        Logger.i(LOG_TAG, "checkNetworkAddress");
        try {
            String[] command = new String[]{"sh", "-c", "cat /sys/class/net/eth0/carrier"};
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String readLine = bufferedReader.readLine();
            String networkInterface = null;
            String networkAddress = null;
            Logger.d(LOG_TAG, "checkNetworkAddress readLine : " + readLine);

            if (readLine.equals("1")) {
                command = new String[]{"sh", "-c", "ip -4 addr | grep -E 'inet .*eth'"};
                networkInterface = "Ethernet";
            } else {
                command = new String[]{"sh", "-c", "ip -4 addr | grep -E 'inet .*wlan'"};
                networkInterface = "Wi-Fi";
            }

            process = Runtime.getRuntime().exec(command);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            readLine = bufferedReader.readLine();
            Logger.d(LOG_TAG, "checkNetworkAddress readLine : " + readLine);

            if (readLine == null) {
                mResultString = "No Interface.";
            } else {
                Pattern pattern = Pattern.compile("([\\d\\.]+)\\/");
                Matcher matcher = pattern.matcher(readLine);
                Logger.d(LOG_TAG, "checkNetworkAddress matcher.groupCount : " + matcher.groupCount());
                if (matcher.find()) networkAddress = matcher.group(1);
                mResultString = String.format("%s : %s", networkInterface, networkAddress);
            }

            Logger.d(LOG_TAG, "checkNetworkAddress mResultString : " + mResultString);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    setText(mResultString);
                }
            });
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
