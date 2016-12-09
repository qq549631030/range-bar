package com.edmodo.rangebar.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

import com.edmodo.rangebar.BaseBar;
import com.edmodo.rangebar.IRangeBarFormatter;

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

    private IRangeBarFormatter scaleFormatter;

    CustomBar(Context ctx, float x, float y, float length, float barBulge, int tickCount, float tickHeight, float barWeight, float tickWeight, int barColor, int tickColor, float tickPadding, float scaleTextSize, int scaleTextColor) {
        super(ctx, x, y, length, barBulge, tickCount, tickHeight, barWeight, tickWeight, barColor, tickColor);
        mScalePaint = new Paint();
        mScalePaint.setColor(scaleTextColor);
        mScalePaint.setAntiAlias(true);
        mScalePaint.setTextAlign(Paint.Align.CENTER);
        mScalePaint.setTextSize(scaleTextSize);
        mTickPadding = tickPadding;
        mTickStartY = mY - mTickHeight - mTickPadding;
        mTickEndY = mY - mTickPadding;
    }

    public void setScaleFormatter(IRangeBarFormatter scaleFormatter) {
        this.scaleFormatter = scaleFormatter;
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
        for (int i = 0; i <= mNumSegments; i++) {
            String scaleText = null;
            if (scaleFormatter != null) {
                scaleText = scaleFormatter.format(i);
            }
            if (scaleText != null) {
                final float x = i * mTickDistance + mLeftX;
                canvas.drawText(scaleText, x, mY - mTickHeight - mTickPadding * 2,
                        mScalePaint);
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
