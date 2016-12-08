package com.edmodo.rangebar.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

import com.edmodo.rangebar.BaseBar;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/6 19:46
 * 邮箱：huangx@pycredit.cn
 */

public class CustomBar extends BaseBar {

    private final Paint mScalePaint;

    private float mTickStartY;
    private float mTickEndY;

    protected final float mTickPadding;
    protected final int scaleInterval = 5;

    CustomBar(Context ctx, float x, float y, float length, float barBulge, int tickCount, float tickHeightDP, float barWeight, float tickWeight, int barColor, int tickColor, float tickPaddingDP, float scaleTextSizeSp, int scaleTextColor) {
        super(ctx, x, y, length, barBulge, tickCount, tickHeightDP, barWeight, tickWeight, barColor, tickColor);
        mScalePaint = new Paint();
        mScalePaint.setColor(scaleTextColor);
        mScalePaint.setAntiAlias(true);
        mScalePaint.setTextAlign(Paint.Align.CENTER);
        float scaleTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, scaleTextSizeSp, ctx.getResources().getDisplayMetrics());
        mScalePaint.setTextSize(scaleTextSize);
        mTickPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                tickPaddingDP,
                ctx.getResources().getDisplayMetrics());
        mTickStartY = mY - mTickHeight - mTickPadding;
        mTickEndY = mY - mTickPadding;
    }

    @Override
    protected void initBarPaint(Paint paint) {
        super.initBarPaint(paint);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void drawTicks(Canvas canvas) {
        super.drawTicks(canvas);
        drawLines(canvas);
        drawScales(canvas);
    }

    protected void drawLines(Canvas canvas) {
        // Loop through and draw each tick (except final tick).
        for (int i = 0; i < mNumSegments; i++) {
            final float x = i * mTickDistance + mLeftX;
            canvas.drawLine(x, mTickStartY, x, mTickEndY, mTickPaint);
        }
        // Draw final tick. We draw the final tick outside the loop to avoid any
        // rounding discrepancies.
        canvas.drawLine(mRightX, mTickStartY, mRightX, mTickEndY, mTickPaint);
    }

    protected void drawScales(Canvas canvas) {
        if (scaleInterval > 0) {
            for (int i = 0; i < mNumSegments; i++) {
                if (i % scaleInterval == 0) {
                    String scaleText = String.valueOf(i);
                    if (formatter != null) {
                        scaleText = formatter.format(i);
                    }
                    final float x = i * mTickDistance + mLeftX;
                    canvas.drawText(scaleText, x, mY - mTickHeight - mTickPadding * 2,
                            mScalePaint);
                }
            }
        }
    }

    @Override
    public int measureMinHeight() {
        Rect rect = new Rect();
        mScalePaint.getTextBounds("test", 0, "test".length(), rect);
        float scaleHeight = rect.height();
        return (int) ((mTickHeight + mTickPadding * 2 + scaleHeight) * 2);
    }
}