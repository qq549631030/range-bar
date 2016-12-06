package com.edmodo.rangebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/6 19:46
 * 邮箱：huangx@pycredit.cn
 */

public class CustomBar extends BaseBar {

    private float mTickStartY;
    private float mTickEndY;

    protected final float mTickPadding;

    CustomBar(Context ctx, float x, float y, float length, float bulgeLength, int tickCount, float tickHeightDP, float barWeight, float tickWeight, int barColor, int tickColor, float tickPaddingDP) {
        super(ctx, x, y, length, bulgeLength, tickCount, tickHeightDP, barWeight, tickWeight, barColor, tickColor);
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
        // Loop through and draw each tick (except final tick).
        for (int i = 0; i < mNumSegments; i++) {
            final float x = i * mTickDistance + mLeftX;
            canvas.drawLine(x, mTickStartY, x, mTickEndY, mTickPaint);
        }
        // Draw final tick. We draw the final tick outside the loop to avoid any
        // rounding discrepancies.
        canvas.drawLine(mRightX, mTickStartY, mRightX, mTickEndY, mTickPaint);


    }
}
