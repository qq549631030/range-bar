package com.edmodo.rangebar.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.edmodo.rangebar.BaseBar;
import com.edmodo.rangebar.BaseConnectingLine;
import com.edmodo.rangebar.BaseRangeBar;
import com.edmodo.rangebar.BaseThumb;
import com.edmodo.rangebar.IRangeBarFormatter;
import com.edmodo.rangebar.R;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/6 20:42
 * 邮箱：huangx@pycredit.cn
 */

public class CustomRangeBar extends BaseRangeBar {

    private static final float DEFAULT_BAR_BULGE_DP = 4;
    private static final float DEFAULT_BAR_WEIGHT_DP = 4;
    private static final int DEFAULT_BAR_COLOR = Color.LTGRAY;

    private static final int DEFAULT_TICK_COUNT = 11;
    private static final float DEFAULT_TICK_HEIGHT_DP = 6;
    private static final float DEFAULT_TICK_WEIGHT_DP = 2;
    private static final float DEFAULT_TICK_PADDING_DP = 5;
    private static final int DEFAULT_TICK_COLOR = Color.LTGRAY;

    private static final float DEFAULT_SCALE_TEXT_SIZE_SP = 12;
    private static final int DEFAULT_SCALE_TEXT_COLOR = Color.WHITE;

    private static final float DEFAULT_CONNECTING_LINE_WEIGHT_DP = 4;

    private static final int DEFAULT_PIN_BG_RES = 0;
    private static final int DEFAULT_PIN_BG_COLOR = 0xff33b5e5;
    private static final float DEFAULT_PIN_TEXT_PADDING_LEFT_DP = 5;
    private static final float DEFAULT_PIN_TEXT_PADDING_TOP_DP = 5;
    private static final float DEFAULT_PIN_TEXT_PADDING_RIGHT_DP = 5;
    private static final float DEFAULT_PIN_TEXT_PADDING_BOTTOM_DP = 5;
    private static final float DEFAULT_PIN_MARGIN_BOTTOM_DP = 12;
    private static final float DEFAULT_PIN_TEXT_SIZE_SP = 12;
    private static final int DEFAULT_PIN_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_PIN_TEXT_LENGTH = 4;

    private static final float DEFAULT_THUMB_LINE_HEIGHT_DP = 30;
    private static final float DEFAULT_THUMB_LINE_WEIGHT_DP = 2;
    private static final int DEFAULT_THUMB_LINE_COLOR = Color.CYAN;

    private float mTickPadding;
    private float mScaleTextSize;
    private int mScaleTextColor = DEFAULT_SCALE_TEXT_COLOR;

    private int mPinBgRes = DEFAULT_PIN_BG_RES;
    private int mPinBgColor = DEFAULT_PIN_BG_COLOR;
    private float mPinTextPaddingLeft;
    private float mPinTextPaddingTop;
    private float mPinTextPaddingRight;
    private float mPinTextPaddingBottom;
    private float mPinMarginBottom;
    private float mPinTextSize;
    private int mPinTextColor = DEFAULT_PIN_TEXT_COLOR;
    private int maxPinTextLength = DEFAULT_PIN_TEXT_LENGTH;

    private float mThumbLineHeight;
    private float mThumbLineWeight;
    private int mThumbLineColor = DEFAULT_THUMB_LINE_COLOR;

    private IRangeBarFormatter scaleFormatter;

    private IRangeBarFormatter pinFormatter;

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
            mTickPadding = ta.getDimension(R.styleable.CustomRangeBar_tickPadding, getDefaultTickPaddingPx());
            mScaleTextSize = ta.getDimension(R.styleable.CustomRangeBar_scaleTextSize, getDefaultScaleTextSizePx());
            mScaleTextColor = ta.getColor(R.styleable.CustomRangeBar_scaleTextColor, getDefaultScaleTextColor());

            mPinBgRes = ta.getResourceId(R.styleable.CustomRangeBar_pinBgRes, getDefaultPinBgRes());
            mPinBgColor = ta.getColor(R.styleable.CustomRangeBar_pinBgColor, getDefaultPinBgColor());
            mPinTextPaddingLeft = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingLeft, getDefaultPinTextPaddingLeftPx());
            mPinTextPaddingTop = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingTop, getDefaultPinTextPaddingTopPx());
            mPinTextPaddingRight = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingRight, getDefaultPinTextPaddingRightPx());
            mPinTextPaddingBottom = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingBottom, getDefaultPinTextPaddingBottomPx());
            mPinMarginBottom = ta.getDimension(R.styleable.CustomRangeBar_pinMarginBottom, getDefaultPinMarginBottomPx());
            mPinTextSize = ta.getDimension(R.styleable.CustomRangeBar_pinTextSize, getDefaultPinTextSizePx());
            mPinTextColor = ta.getColor(R.styleable.CustomRangeBar_pinTextColor, getDefaultPinTextColor());
            maxPinTextLength = ta.getInteger(R.styleable.CustomRangeBar_maxPinTextLength, getDefaultPinTextLength());

            mThumbLineHeight = ta.getDimension(R.styleable.CustomRangeBar_thumbLineHeight, getDefaultThumbLineHeightPx());
            mThumbLineWeight = ta.getDimension(R.styleable.CustomRangeBar_thumbLineWeight, getDefaultThumbLineWeightPx());
            mThumbLineColor = ta.getColor(R.styleable.CustomRangeBar_thumbLineColor, getDefaultThumbLineColor());
        } finally {
            ta.recycle();
        }
    }


    @Override
    public float getDefaultBarBulgePx() {
        return convertDp(DEFAULT_BAR_BULGE_DP);
    }

    @Override
    public float getDefaultBarWeightPx() {
        return convertDp(DEFAULT_BAR_WEIGHT_DP);
    }

    @Override
    public int getDefaultBarColor() {
        return DEFAULT_BAR_COLOR;
    }

    @Override
    public int getDefaultTickCount() {
        return DEFAULT_TICK_COUNT;
    }

    public float getDefaultTickPaddingPx() {
        return convertDp(DEFAULT_TICK_PADDING_DP);
    }

    @Override
    public float getDefaultTickHeightPx() {
        return convertDp(DEFAULT_TICK_HEIGHT_DP);
    }

    @Override
    public float getDefaultTickWeightPx() {
        return convertDp(DEFAULT_TICK_WEIGHT_DP);
    }

    @Override
    public int getDefaultTickColor() {
        return DEFAULT_TICK_COLOR;
    }

    public float getDefaultScaleTextSizePx() {
        return convertSp(DEFAULT_SCALE_TEXT_SIZE_SP);
    }

    public int getDefaultScaleTextColor() {
        return DEFAULT_SCALE_TEXT_COLOR;
    }

    public int getDefaultPinBgRes() {
        return DEFAULT_PIN_BG_RES;
    }

    public int getDefaultPinBgColor() {
        return DEFAULT_PIN_BG_COLOR;
    }

    public float getDefaultPinTextPaddingLeftPx() {
        return convertDp(DEFAULT_PIN_TEXT_PADDING_LEFT_DP);
    }

    public float getDefaultPinTextPaddingTopPx() {
        return convertDp(DEFAULT_PIN_TEXT_PADDING_TOP_DP);
    }

    public float getDefaultPinTextPaddingRightPx() {
        return convertDp(DEFAULT_PIN_TEXT_PADDING_RIGHT_DP);
    }

    public float getDefaultPinTextPaddingBottomPx() {
        return convertDp(DEFAULT_PIN_TEXT_PADDING_BOTTOM_DP);
    }

    @Override
    public float getDefaultConnectingLineWeightPx() {
        return convertDp(DEFAULT_CONNECTING_LINE_WEIGHT_DP);
    }

    public float getDefaultPinMarginBottomPx() {
        return convertDp(DEFAULT_PIN_MARGIN_BOTTOM_DP);
    }

    public float getDefaultPinTextSizePx() {
        return convertSp(DEFAULT_PIN_TEXT_SIZE_SP);
    }

    public int getDefaultPinTextColor() {
        return DEFAULT_PIN_TEXT_COLOR;
    }

    public int getDefaultPinTextLength() {
        return DEFAULT_PIN_TEXT_LENGTH;
    }

    public float getDefaultThumbLineHeightPx() {
        return convertDp(DEFAULT_THUMB_LINE_HEIGHT_DP);
    }

    public float getDefaultThumbLineWeightPx() {
        return convertDp(DEFAULT_THUMB_LINE_WEIGHT_DP);
    }

    public int getDefaultThumbLineColor() {
        return DEFAULT_THUMB_LINE_COLOR;
    }

    public void setScaleFormatter(IRangeBarFormatter scaleFormatter) {
        this.scaleFormatter = scaleFormatter;
        if (mBar != null) {
            ((CustomBar) mBar).setScaleFormatter(scaleFormatter);
        }
        invalidate();
    }

    public void setPinFormatter(IRangeBarFormatter pinFormatter) {
        this.pinFormatter = pinFormatter;
        if (mLeftThumb != null) {
            ((CustomThumb) mLeftThumb).setPinFormatter(pinFormatter);
        }
        if (mRightThumb != null) {
            ((CustomThumb) mRightThumb).setPinFormatter(pinFormatter);
        }
        invalidate();
    }

    @Override
    protected BaseBar createBar(Context ctx, float marginLeft, float yPos, float barLength, float barBulge, int tickCount, float tickHeight, float barWeight, int barColor, float tickWeight, int tickColor) {
        CustomBar bar = new CustomBar(ctx, marginLeft, yPos, barLength, barBulge, tickCount, tickHeight, barWeight, tickWeight, barColor, tickColor, mTickPadding, mScaleTextSize, mScaleTextColor);
        bar.setScaleFormatter(scaleFormatter);
        return bar;
    }

    @Override
    protected BaseConnectingLine createConnectingLine(Context ctx, float yPos, float connectingLineWeight, int connectingLineColor) {
        return new CustomConnectingLine(ctx, yPos, connectingLineWeight, connectingLineColor, mThumbLineWeight);
    }

    @Override
    protected BaseThumb createThumb(Context ctx, float yPos, int thumbColorNormal, int thumbColorPressed, float thumbRadiusDP, int thumbImageNormal, int thumbImagePressed) {
        CustomThumb thumb =
                new CustomThumb(ctx,
                        yPos,
                        thumbColorNormal,
                        thumbColorPressed,
                        thumbRadiusDP,
                        thumbImageNormal,
                        thumbImagePressed,
                        mPinBgRes,
                        mPinBgColor,
                        mPinTextPaddingLeft,
                        mPinTextPaddingTop,
                        mPinTextPaddingRight,
                        mPinTextPaddingBottom,
                        mPinMarginBottom,
                        mPinTextSize,
                        mPinTextColor,
                        maxPinTextLength,
                        mThumbLineHeight,
                        mThumbLineWeight,
                        mThumbLineColor);
        thumb.setPinFormatter(pinFormatter);
        return thumb;
    }
}
