package com.edmodo.rangebar.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.edmodo.rangebar.BaseConnectingLine;
import com.edmodo.rangebar.BaseThumb;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/8 14:55
 * 邮箱：huangx@pycredit.cn
 */

public class CustomConnectingLine extends BaseConnectingLine {

    private final float thumbLineWeight;

    public CustomConnectingLine(Context ctx, float y, float connectingLineWeight, int connectingLineColor, float thumbLineWeight) {
        super(ctx, y, connectingLineWeight, connectingLineColor);
        this.thumbLineWeight = thumbLineWeight;
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void draw(Canvas canvas, BaseThumb leftThumb, BaseThumb rightThumb) {
        canvas.drawLine(leftThumb.getX() + mConnectingLineWeight / 2f + thumbLineWeight / 2f, mY, rightThumb.getX() - mConnectingLineWeight / 2f - thumbLineWeight / 2f, mY, mPaint);
    }
}
