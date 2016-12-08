package com.edmodo.rangebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/8 17:13
 * 邮箱：huangx@pycredit.cn
 */

public abstract class BaseConnectingLine {

    // Member Variables ////////////////////////////////////////////////////////

    protected final Paint mPaint;

    protected final float mConnectingLineWeight;
    protected final float mY;

    // Constructor /////////////////////////////////////////////////////////////

    public BaseConnectingLine(Context ctx, float y, float connectingLineWeight, int connectingLineColor) {
        mConnectingLineWeight = connectingLineWeight;
        // Initialize the paint, set values
        mPaint = new Paint();
        mPaint.setColor(connectingLineColor);
        mPaint.setStrokeWidth(mConnectingLineWeight);
        mPaint.setAntiAlias(true);
        mY = y;
    }
    // Package-Private Methods /////////////////////////////////////////////////

    /**
     * Draw the connecting line between the two thumbs.
     *
     * @param canvas     the Canvas to draw to
     * @param leftThumb  the left thumb
     * @param rightThumb the right thumb
     */
    public abstract void draw(Canvas canvas, BaseThumb leftThumb, BaseThumb rightThumb);
}
