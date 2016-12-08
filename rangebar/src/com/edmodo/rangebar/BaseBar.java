package com.edmodo.rangebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/6 19:14
 * 邮箱：huangx@pycredit.cn
 */

public class BaseBar {
    // Member Variables ////////////////////////////////////////////////////////

    protected final Paint mBarPaint;
    protected final Paint mTickPaint;

    protected final float mBarLeftX;
    protected final float mBarRightX;

    // Left-coordinate of the horizontal bar.
    protected final float mLeftX;

    protected final float mRightX;

    protected final float mY;

    protected int mNumSegments;

    protected float mTickDistance;

    protected final float mTickHeight;

    protected IRangeBarFormatter formatter;

    // Constructor /////////////////////////////////////////////////////////////


    public BaseBar(Context ctx,
                   float x,
                   float y,
                   float length,
                   float barBulge,
                   int tickCount,
                   float tickHeight,
                   float barWeight,
                   float tickWeight,
                   int barColor,
                   int tickColor) {
        mBarLeftX = x;
        mBarRightX = x + length;
        mLeftX = x + barBulge;
        mRightX = x + length - barBulge;
        mY = y;

        mNumSegments = tickCount - 1;
        mTickDistance = (length - barBulge * 2) / mNumSegments;
        mTickHeight = tickHeight;
        // Initialize the paint.
        mBarPaint = new Paint();
        mBarPaint.setColor(barColor);
        mBarPaint.setStrokeWidth(barWeight);
        mBarPaint.setAntiAlias(true);
        initBarPaint(mBarPaint);
        mTickPaint = new Paint();
        mTickPaint.setColor(tickColor);
        mTickPaint.setStrokeWidth(tickWeight);
        mTickPaint.setAntiAlias(true);
        initTickPaint(mTickPaint);
    }

    protected void initBarPaint(Paint paint) {

    }

    protected void initTickPaint(Paint paint) {

    }

    public void setFormatter(IRangeBarFormatter formatter) {
        this.formatter = formatter;
    }

    public void draw(Canvas canvas) {

        drawBar(canvas);

        drawTicks(canvas);
    }


    /**
     * Get the x-coordinate of the left edge of the bar.
     *
     * @return x-coordinate of the left edge of the bar
     */
    public float getLeftX() {
        return mLeftX;
    }

    /**
     * Get the x-coordinate of the right edge of the bar.
     *
     * @return x-coordinate of the right edge of the bar
     */
    public float getRightX() {
        return mRightX;
    }

    /**
     * Gets the x-coordinate of the nearest tick to the given x-coordinate.
     *
     * @param x the x-coordinate to find the nearest tick for
     * @return the x-coordinate of the nearest tick
     */
    public float getNearestTickCoordinate(BaseThumb thumb) {

        final int nearestTickIndex = getNearestTickIndex(thumb);

        final float nearestTickCoordinate = mLeftX + (nearestTickIndex * mTickDistance);

        return nearestTickCoordinate;
    }

    /**
     * Gets the zero-based index of the nearest tick to the given thumb.
     *
     * @param thumb the Thumb to find the nearest tick for
     * @return the zero-based index of the nearest tick
     */
    public int getNearestTickIndex(BaseThumb thumb) {

        int index = (int) ((thumb.getX() - mLeftX + mTickDistance / 2f) / mTickDistance);
        if (index < 0) {
            index = 0;
        }
        if (index > mNumSegments) {
            index = mNumSegments;
        }
        return index;
    }

    /**
     * Set the number of ticks that will appear in the RangeBar.
     *
     * @param tickCount the number of ticks
     */
    public void setTickCount(int tickCount) {

        final float tickLength = mRightX - mLeftX;

        mNumSegments = tickCount - 1;
        mTickDistance = tickLength / mNumSegments;
    }

    protected void drawBar(Canvas canvas) {
        canvas.drawLine(mBarLeftX, mY, mBarRightX, mY, mBarPaint);
    }

    /**
     * Draws the tick marks on the bar.
     *
     * @param canvas Canvas to draw on; should be the Canvas passed into {#link
     *               View#onDraw()}
     */
    protected void drawTicks(Canvas canvas) {

    }

    public int measureMinHeight() {
        return -1;
    }
}
