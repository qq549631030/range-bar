package com.edmodo.rangebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/6 20:42
 * 邮箱：huangx@pycredit.cn
 */

public class CustomRangeBar extends BaseRangeBar {

    private static final float DEFAULT_TICK_HEIGHT_DP = 6;

    private static final float DEFAULT_BAR_BULGE_DP = 8;
    private static final float DEFAULT_TICK_PADDING_DP = 3;

    private float mBarBulgeDP = DEFAULT_BAR_BULGE_DP;
    private float mTickPaddingDP = DEFAULT_TICK_PADDING_DP;

    public CustomRangeBar(Context context) {
        this(context, null);
    }

    public CustomRangeBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRangeBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void rangeBarInit(Context context, AttributeSet attrs) {
        super.rangeBarInit(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RangeBar, 0, 0);
        try {
            mTickHeightDP = ta.getDimension(R.styleable.RangeBar_tickHeight, DEFAULT_TICK_HEIGHT_DP);
            mBarBulgeDP = ta.getDimension(R.styleable.RangeBar_barBulge, DEFAULT_BAR_BULGE_DP);
            mTickPaddingDP = ta.getDimension(R.styleable.RangeBar_tickPadding, DEFAULT_TICK_PADDING_DP);
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected BaseBar createBar(Context ctx, float marginLeft, float yPos, float barLength, int tickCount, float tickHeightDP, float barWeight, int barColor, float tickWeight, int tickColor) {
        return new CustomBar(ctx, marginLeft, yPos, barLength, mBarBulgeDP, tickCount, tickHeightDP, barWeight, tickWeight, barColor, tickColor, mTickPaddingDP);
    }
}
