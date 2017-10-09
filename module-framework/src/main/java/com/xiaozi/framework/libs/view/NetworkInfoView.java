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
    private String mInterface = null;
    private String mIPAddress = null;
    private String mMACAddress = null;

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

    public String getIPAddress() {
        Logger.i(LOG_TAG, "getIPAddress");
        Logger.d(LOG_TAG, "getIPAddress mIPAddress : " + mIPAddress);
        return mIPAddress;
    }

    public String getMACAddress() {
        Logger.i(LOG_TAG, "getMACAddress");
        Logger.d(LOG_TAG, "getMACAddress mMACAddress : " + mMACAddress);
        return mMACAddress;
    }

    private void startCheckNetworkAddress() {
        Logger.i(LOG_TAG, "startCheckNetworkAddress");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mIsAttatched) {
                    final String displayString = String.format("%s\n%s", checkIPAdress(), checkMACAddress());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setText(displayString);
                        }
                    });

                    try {
                        Thread.sleep(TIME_CHECK_NETWORK_ADDRESS_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private boolean isEthernet() {
        Logger.i(LOG_TAG, "isEthernet");
        boolean result = false;

        try {
            String[] command = new String[]{"sh", "-c", "cat /sys/class/net/eth0/carrier"};
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String readLine = bufferedReader.readLine();

            if (readLine.equals("1")) result = true;
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String checkIPAdress() {
        Logger.i(LOG_TAG, "checkIPAdress");
        String result = "No Interface.";

        try {
            String[] command = null;

            if (isEthernet()) {
                command = new String[]{"sh", "-c", "ip -4 addr | grep -E 'inet .*eth'"};
                mInterface = "Ethernet";
            } else {
                command = new String[]{"sh", "-c", "ip -4 addr | grep -E 'inet .*wlan'"};
                mInterface = "Wi-Fi";
            }

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String readLine = bufferedReader.readLine();
            Logger.d(LOG_TAG, "checkIPAdress readLine : " + readLine);

            if (readLine != null) {
                Pattern pattern = Pattern.compile("([\\d\\.]+)\\/");
                Matcher matcher = pattern.matcher(readLine);
                Logger.d(LOG_TAG, "checkIPAdress matcher.groupCount : " + matcher.groupCount());
                if (matcher.find()) mIPAddress = matcher.group(1);
                result = String.format("%s : %s", mInterface, mIPAddress);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d(LOG_TAG, "checkIPAdress result : " + result);
        return result;
    }

    private String checkMACAddress() {
        Logger.i(LOG_TAG, "checkMACAddress");
        String result = "No Interface.";

        try {
            String[] command = null;

            if (isEthernet()) {
                command = new String[]{"sh", "-c", "ip addr show eth0 | grep -E 'ether'"};
            } else {
                command = new String[]{"sh", "-c", "ip addr show wlan0 | grep -E 'ether'"};
            }

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String readLine = bufferedReader.readLine();
            Logger.d(LOG_TAG, "checkMACAddress readLine : " + readLine);

            if (readLine != null) {
                Pattern pattern = Pattern.compile("ether (([\\w]+:?)+)");
                Matcher matcher = pattern.matcher(readLine);
                Logger.d(LOG_TAG, "checkIPAdress matcher.groupCount : " + matcher.groupCount());
                if (matcher.find()) mMACAddress = matcher.group(1);
                result = String.format("%s : %s", "MAC", mMACAddress);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d(LOG_TAG, "checkMACAddress result : " + result);
        return result;
    }
}
