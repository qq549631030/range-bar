package com.edmodo.rangebar.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.edmodo.rangebar.BaseThumb;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/7 14:06
 * 邮箱：huangx@pycredit.cn
 */

public class CustomThumb extends BaseThumb {

    private Drawable mPinBg;

    private int mPinBgColor;

    private Rect mPinBounds = new Rect();

    private RectF mPinColorBounds = new RectF();

    private final float pinTextPaddingLeft;

    private final float pinTextPaddingTop;

    private final float pinTextPaddingRight;

    private final float pinTextPaddingBottom;

    private final float pinMarginBottom;

    private float pinMaxWidth;

    private float pinMaxHeight;

    private final float mThumbLineHeight;

    private final Paint mThumbPinBgPaint;

    private final Paint mThumbPinTextPaint;

    private final Paint mThumbLinePaint;

    private final Paint mThumbImagePaint;

    private final float maxHalfWidth;

    private final float maxHalfHeight;

    private final boolean pinBgUseColor;

    public CustomThumb(Context ctx, float y, int thumbColorNormal, int thumbColorPressed, float thumbRadiusDP, int thumbImageNormal, int thumbImagePressed, int pinBgRes, int pinBgColor, float pinTextPaddingLeftDp, float pinTextPaddingTopDp, float pinTextPaddingRightDp, float pinTextPaddingBottomDp, float pinMarginBottomDp, float pinTextSizeSp, int pinTextColor, int maxPinTextLength, float thumbLineHeight, float mThumbLineWeight, int mThumbLineColor) {
        super(ctx, y, thumbColorNormal, thumbColorPressed, thumbRadiusDP, thumbImageNormal, thumbImagePressed);

        if (pinBgRes > 0) {
            mPinBg = ContextCompat.getDrawable(ctx, pinBgRes);
            pinBgUseColor = false;
        } else {
            mPinBgColor = pinBgColor;
            pinBgUseColor = true;
        }

        this.pinTextPaddingLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pinTextPaddingLeftDp, ctx.getResources().getDisplayMetrics());
        this.pinTextPaddingTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pinTextPaddingTopDp, ctx.getResources().getDisplayMetrics());
        this.pinTextPaddingRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pinTextPaddingRightDp, ctx.getResources().getDisplayMetrics());
        this.pinTextPaddingBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pinTextPaddingBottomDp, ctx.getResources().getDisplayMetrics());
        this.pinMarginBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pinMarginBottomDp, ctx.getResources().getDisplayMetrics());
        float pinTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pinTextSizeSp, ctx.getResources().getDisplayMetrics());

        this.mThumbLineHeight = thumbLineHeight;

        mThumbPinBgPaint = new Paint();
        mThumbPinBgPaint.setStyle(Paint.Style.FILL);
        mThumbPinBgPaint.setAntiAlias(true);
        mThumbPinBgPaint.setColor(mPinBgColor);

        mThumbPinTextPaint = new Paint();
        mThumbPinTextPaint.setTextSize(pinTextSize);
        mThumbPinTextPaint.setColor(pinTextColor);
        mThumbPinTextPaint.setAntiAlias(true);
        mThumbPinTextPaint.setTextAlign(Paint.Align.CENTER);

        mThumbLinePaint = new Paint();
        mThumbLinePaint.setColor(mThumbLineColor);
        mThumbLinePaint.setStrokeWidth(mThumbLineWeight);
        mThumbLinePaint.setAntiAlias(true);

        mThumbImagePaint = new Paint();
        mThumbImagePaint.setAntiAlias(true);

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < maxPinTextLength; i++) {
            stringBuffer.append("%");
        }
        Rect rect = new Rect();
        mThumbPinTextPaint.getTextBounds(stringBuffer.toString(), 0, maxPinTextLength - 1, rect);
        int thumbImageWidth = mImageNormal != null ? mImageNormal.getWidth() : 0;
        int thumbImageHeight = mImageNormal != null ? mImageNormal.getHeight() : 0;
        pinMaxWidth = pinTextPaddingLeft + pinTextPaddingRight + rect.width();
        pinMaxHeight = pinTextPaddingTop + pinTextPaddingBottom + rect.height();
        maxHalfWidth = Math.max(thumbImageWidth, pinMaxWidth) / 2f;
        maxHalfHeight = Math.max(thumbImageHeight, pinMaxHeight + pinMarginBottom) + mThumbLineHeight / 2f;

        mX = maxHalfWidth;
    }

    @Override
    public float getHalfWidth() {
        return maxHalfWidth;
    }

    @Override
    public float getHalfHeight() {
        return maxHalfHeight;
    }

    @Override
    public boolean isInTargetZone(float x, float y) {
        if (Math.abs(x - mX) <= (mImageNormal.getWidth() / 2f) && Math.abs(y - (mY + (mThumbLineHeight / 2.0f) + (mImageNormal.getHeight() / 2f))) <= (mImageNormal.getHeight() / 2f)) {
            return true;
        }
        return false;
    }

    @Override
    protected void drawByBitmap(Canvas canvas, Bitmap bitmap, boolean isPressed) {
        drawPin(canvas);
        drawLine(canvas);
        drawImage(canvas, bitmap, isPressed);
    }

    @Override
    protected void drawByColor(Canvas canvas, boolean isPressed) {
        drawPin(canvas);
        drawLine(canvas);
        drawColor(canvas, isPressed);
    }

    protected void drawPin(Canvas canvas) {
        if (pinBgUseColor) {
            Path path = new Path();
            path.moveTo(mX, mY - mThumbLineHeight / 2f - pinMarginBottom);
            path.lineTo(mX + 7, mY - mThumbLineHeight / 2f - pinMarginBottom - 11);
            path.lineTo(mX - 7, mY - mThumbLineHeight / 2f - pinMarginBottom - 11);
            path.lineTo(mX, mY - mThumbLineHeight / 2f - pinMarginBottom);
            canvas.drawPath(path, mThumbPinBgPaint);
            mPinColorBounds.set((int) (mX - maxHalfWidth), (int) (mY - mThumbLineHeight / 2f - pinMarginBottom - pinMaxHeight), (int) (mX + maxHalfWidth), (int) (mY - mThumbLineHeight / 2f - pinMarginBottom - 11));
            canvas.drawRoundRect(mPinColorBounds, 4, 4, mThumbPinBgPaint);
        } else {
            mPinBounds.set((int) (mX - maxHalfWidth), (int) (mY - mThumbLineHeight / 2f - pinMarginBottom - pinMaxHeight), (int) (mX + maxHalfWidth), (int) (mY - mThumbLineHeight / 2f - pinMarginBottom));
            mPinBg.setBounds(mPinBounds);
            mPinBg.draw(canvas);
        }
        canvas.drawText(getText() == null ? "" : getText(), mX, mY - mThumbLineHeight / 2f - pinMarginBottom - pinTextPaddingBottom, mThumbPinTextPaint);


    }

    protected void drawLine(Canvas canvas) {
        canvas.drawLine(mX, mY - mThumbLineHeight / 2f, mX, mY + mThumbLineHeight / 2f, mThumbLinePaint);
    }

    protected void drawImage(Canvas canvas, Bitmap bitmap, boolean isPressed) {
        canvas.drawBitmap(bitmap, mX - bitmap.getWidth() / 2f, mY + mThumbLineHeight / 2f, null);
    }

    protected void drawColor(Canvas canvas, boolean isPressed) {
        if (isPressed)
            canvas.drawCircle(mX, mY, mThumbRadiusPx, mPaintPressed);
        else
            canvas.drawCircle(mX, mY, mThumbRadiusPx, mPaintNormal);
    }

    @Override
    public int measureMinHeight() {
        return (int) (getHalfHeight() * 2);
    }
}
