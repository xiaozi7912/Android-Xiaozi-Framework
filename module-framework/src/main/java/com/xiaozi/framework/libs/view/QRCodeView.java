package com.xiaozi.framework.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xiaozi.framework.libs.R;
import com.xiaozi.framework.libs.utils.Logger;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by xiaoz on 2017-10-08.
 */

public class QRCodeView extends ImageView {
    private final String LOG_TAG = getClass().getSimpleName();

    private TypedArray mTypedArray = null;

    private int mViewWidth, mViewHeight;
    private int mForegroundColor, mBackgroundColor;
    private String mContent = null;

    public QRCodeView(Context context) {
        super(context);
    }

    public QRCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTypedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.QRCodeView,
                0, 0);

        try {
            mContent = mTypedArray.getString(R.styleable.QRCodeView_content);
            mForegroundColor = mTypedArray.getColor(R.styleable.QRCodeView_foreground_color, Color.BLACK);
            mBackgroundColor = mTypedArray.getColor(R.styleable.QRCodeView_background_color, Color.WHITE);
        } finally {
            mTypedArray.recycle();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.i(LOG_TAG, "onAttachedToWindow");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.i(LOG_TAG, "onMeasure");
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        Logger.d(LOG_TAG, "onMeasure mViewWidth : " + mViewWidth);
        Logger.d(LOG_TAG, "onMeasure mViewHeight : " + mViewHeight);
        setMeasuredDimension(mViewWidth, mViewHeight);
        setContent(mContent);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.i(LOG_TAG, "onDetachedFromWindow");
    }

    public void setContent(String content) {
        Logger.i(LOG_TAG, "setContent");
        Logger.d(LOG_TAG, "setContent content : " + content);
        mContent = content;
        setImageBitmap(createBitmap(content));
    }

    public String getContent() {
        Logger.i(LOG_TAG, "getContent");
        return mContent;
    }

    private Bitmap createBitmap(String content) {
        Logger.i(LOG_TAG, "createBitmap");
        Logger.d(LOG_TAG, "createBitmap content : " + content);
        Bitmap result = null;
        Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, mViewWidth, mViewHeight, hints);
            result = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);

            for (int y = 0; y < mViewHeight; y++) {
                for (int x = 0; x < mViewWidth; x++) {
                    result.setPixel(x, y, bitMatrix.get(x, y) ? mForegroundColor : mBackgroundColor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
