package com.edmodo.rangebar.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.edmodo.rangebar.BaseBar;
import com.edmodo.rangebar.BaseConnectingLine;
import com.edmodo.rangebar.BaseRangeBar;
import com.edmodo.rangebar.BaseThumb;
import com.edmodo.rangebar.R;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/6 20:42
 * 邮箱：huangx@pycredit.cn
 */

public class CustomRangeBar extends BaseRangeBar {

    private static final float DEFAULT_TICK_HEIGHT_PX = 48;


    private static final float DEFAULT_TICK_PADDING_DP = 5;
    private static final float DEFAULT_SCALE_TEXT_SIZE_SP = 12;
    private static final int DEFAULT_SCALE_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_PIN_BG_COLOR = Color.BLUE;
    private static final int DEFAULT_PIN_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_THUMB_LINE_COLOR = Color.BLUE;
    private static final int DEFAULT_PIN_BG_RES = 0;
    private static final float DEFAULT_PIN_TEXT_PADDING_LEFT_DP = 3;
    private static final float DEFAULT_PIN_TEXT_PADDING_TOP_DP = 3;
    private static final float DEFAULT_PIN_TEXT_PADDING_RIGHT_DP = 3;
    private static final float DEFAULT_PIN_TEXT_PADDING_BOTTOM_DP = 10;
    private static final float DEFAULT_PIN_MARGIN_BOTTOM_DP = 10;
    private static final float DEFAULT_PIN_TEXT_SIZE_SP = 12;
    private static final int DEFAULT_PIN_TEXT_LENGTH = 4;

    private static final float DEFAULT_THUMB_LINE_HEIGHT_PX = 40;
    private static final float DEFAULT_THUMB_LINE_WEIGHT_PX = 2;

    private float mTickPaddingDP = DEFAULT_TICK_PADDING_DP;
    private float mScaleTextSizeSp = DEFAULT_SCALE_TEXT_SIZE_SP;
    private int mScaleTextColor = DEFAULT_SCALE_TEXT_COLOR;

    private int mPinBgRes = DEFAULT_PIN_BG_RES;
    private int mPinBgColor = DEFAULT_PIN_BG_COLOR;
    private float mPinTextPaddingLeftDp = DEFAULT_PIN_TEXT_PADDING_LEFT_DP;
    private float mPinTextPaddingTopDp = DEFAULT_PIN_TEXT_PADDING_TOP_DP;
    private float mPinTextPaddingRightDp = DEFAULT_PIN_TEXT_PADDING_RIGHT_DP;
    private float mPinTextPaddingBottomDp = DEFAULT_PIN_TEXT_PADDING_BOTTOM_DP;
    private float mPinMarginBottomDp = DEFAULT_PIN_MARGIN_BOTTOM_DP;
    private float mPinTextSizeSp = DEFAULT_PIN_TEXT_SIZE_SP;
    private int mPinTextColor = DEFAULT_PIN_TEXT_COLOR;
    private int maxPinTextLength = DEFAULT_PIN_TEXT_LENGTH;

    private float mThumbLineHeight = DEFAULT_THUMB_LINE_HEIGHT_PX;
    private float mThumbLineWeight = DEFAULT_THUMB_LINE_WEIGHT_PX;
    private int mThumbLineColor = DEFAULT_THUMB_LINE_COLOR;


    public CustomRangeBar(Context context) {
        this(context, null);
    }

    public CustomRangeBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRangeBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomRangeBar, 0, 0);
        try {
            mTickPaddingDP = ta.getDimension(R.styleable.CustomRangeBar_tickPadding, DEFAULT_TICK_PADDING_DP);
            mScaleTextSizeSp = ta.getDimension(R.styleable.CustomRangeBar_scaleTextSize, DEFAULT_SCALE_TEXT_SIZE_SP);
            mScaleTextColor = ta.getColor(R.styleable.CustomRangeBar_scaleTextColor, DEFAULT_SCALE_TEXT_COLOR);

            mPinBgRes = ta.getResourceId(R.styleable.CustomRangeBar_pinBgRes, DEFAULT_PIN_BG_RES);
            mPinBgColor = ta.getColor(R.styleable.CustomRangeBar_pinBgColor, DEFAULT_PIN_BG_COLOR);
            mPinTextPaddingLeftDp = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingLeft, DEFAULT_PIN_TEXT_PADDING_LEFT_DP);
            mPinTextPaddingTopDp = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingTop, DEFAULT_PIN_TEXT_PADDING_TOP_DP);
            mPinTextPaddingRightDp = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingRight, DEFAULT_PIN_TEXT_PADDING_RIGHT_DP);
            mPinTextPaddingBottomDp = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingBottom, DEFAULT_PIN_TEXT_PADDING_BOTTOM_DP);
            mPinMarginBottomDp = ta.getDimension(R.styleable.CustomRangeBar_pinMarginBottom, DEFAULT_PIN_MARGIN_BOTTOM_DP);
            mPinTextSizeSp = ta.getDimension(R.styleable.CustomRangeBar_pinTextSize, DEFAULT_PIN_TEXT_SIZE_SP);
            mPinTextColor = ta.getColor(R.styleable.CustomRangeBar_pinTextColor, DEFAULT_PIN_TEXT_COLOR);
            maxPinTextLength = ta.getInteger(R.styleable.CustomRangeBar_maxPinTextLength, DEFAULT_PIN_TEXT_LENGTH);

            mThumbLineHeight = ta.getDimension(R.styleable.CustomRangeBar_thumbLineHeight, mThumbLineHeight);
            mThumbLineWeight = ta.getDimension(R.styleable.CustomRangeBar_thumbLineWeight, DEFAULT_THUMB_LINE_WEIGHT_PX);
            mThumbLineColor = ta.getColor(R.styleable.CustomRangeBar_thumbLineColor, DEFAULT_THUMB_LINE_COLOR);
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected BaseBar createBar(Context ctx, float marginLeft, float yPos, float barLength, float barBulge, int tickCount, float tickHeightDP, float barWeight, int barColor, float tickWeight, int tickColor) {
        CustomBar bar = new CustomBar(ctx, marginLeft, yPos, barLength, barBulge, tickCount, tickHeightDP, barWeight, tickWeight, barColor, tickColor, mTickPaddingDP, mScaleTextSizeSp, mScaleTextColor);
        return bar;
    }

    @Override
    protected BaseConnectingLine createConnectingLine(Context ctx, float yPos, float connectingLineWeight, int connectingLineColor) {
        return new CustomConnectingLine(ctx, yPos, connectingLineWeight, connectingLineColor, mThumbLineWeight);
    }

    @Override
    protected BaseThumb createThumb(Context ctx, float yPos, int thumbColorNormal, int thumbColorPressed, float thumbRadiusDP, int thumbImageNormal, int thumbImagePressed) {
        return new CustomThumb(ctx,
                yPos,
                thumbColorNormal,
                thumbColorPressed,
                thumbRadiusDP,
                thumbImageNormal,
                thumbImagePressed,
                mPinBgRes,
                mPinBgColor,
                mPinTextPaddingLeftDp,
                mPinTextPaddingTopDp,
                mPinTextPaddingRightDp,
                mPinTextPaddingBottomDp,
                mPinMarginBottomDp,
                mPinTextSizeSp,
                mPinTextColor,
                maxPinTextLength,
                mThumbLineHeight,
                mThumbLineWeight,
                mThumbLineColor);
    }
}
