package com.edmodo.rangebar.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

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

    private static final float DEFAULT_BAR_BULGE_PX = 8;
    private static final float DEFAULT_BAR_WEIGHT_PX = 8;
    private static final float DEFAULT_TICK_WEIGHT_PX = 4;
    private static final int DEFAULT_BAR_COLOR = Color.LTGRAY;
    private static final int DEFAULT_TICK_COUNT = 11;
    private static final float DEFAULT_TICK_HEIGHT_PX = 12;
    private static final int DEFAULT_TICK_COLOR = Color.LTGRAY;
    private static final float DEFAULT_CONNECTING_LINE_WEIGHT_PX = 8;


    private static final float DEFAULT_TICK_PADDING_PX = 10;
    private static final float DEFAULT_SCALE_TEXT_SIZE_SP = 12;
    private static final int DEFAULT_SCALE_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_PIN_BG_COLOR = 0xff33b5e5;
    private static final int DEFAULT_PIN_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_THUMB_LINE_COLOR = Color.CYAN;
    private static final int DEFAULT_PIN_BG_RES = 0;
    private static final float DEFAULT_PIN_TEXT_PADDING_LEFT_PX = 6;
    private static final float DEFAULT_PIN_TEXT_PADDING_TOP_PX = 6;
    private static final float DEFAULT_PIN_TEXT_PADDING_RIGHT_PX = 6;
    private static final float DEFAULT_PIN_TEXT_PADDING_BOTTOM_PX = 16;
    private static final float DEFAULT_PIN_MARGIN_BOTTOM_PX = 20;
    private static final float DEFAULT_PIN_TEXT_SIZE_SP = 12;
    private static final int DEFAULT_PIN_TEXT_LENGTH = 4;

    private static final float DEFAULT_THUMB_LINE_HEIGHT_PX = 60;
    private static final float DEFAULT_THUMB_LINE_WEIGHT_PX = 4;

    private float mTickPadding = DEFAULT_TICK_PADDING_PX;
    private float mScaleTextSizeSp = DEFAULT_SCALE_TEXT_SIZE_SP;
    private int mScaleTextColor = DEFAULT_SCALE_TEXT_COLOR;

    private int mPinBgRes = DEFAULT_PIN_BG_RES;
    private int mPinBgColor = DEFAULT_PIN_BG_COLOR;
    private float mPinTextPaddingLeft = DEFAULT_PIN_TEXT_PADDING_LEFT_PX;
    private float mPinTextPaddingTop = DEFAULT_PIN_TEXT_PADDING_TOP_PX;
    private float mPinTextPaddingRight = DEFAULT_PIN_TEXT_PADDING_RIGHT_PX;
    private float mPinTextPaddingBottom = DEFAULT_PIN_TEXT_PADDING_BOTTOM_PX;
    private float mPinMarginBottom = DEFAULT_PIN_MARGIN_BOTTOM_PX;
    private float mPinTextSizeSp = DEFAULT_PIN_TEXT_SIZE_SP;
    private int mPinTextColor = DEFAULT_PIN_TEXT_COLOR;
    private int maxPinTextLength = DEFAULT_PIN_TEXT_LENGTH;

    private float mThumbLineHeight = DEFAULT_THUMB_LINE_HEIGHT_PX;
    private float mThumbLineWeight = DEFAULT_THUMB_LINE_WEIGHT_PX;
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
            mTickPadding = ta.getDimension(R.styleable.CustomRangeBar_tickPadding, DEFAULT_TICK_PADDING_PX);
            mScaleTextSizeSp = ta.getDimension(R.styleable.CustomRangeBar_scaleTextSize, DEFAULT_SCALE_TEXT_SIZE_SP);
            mScaleTextColor = ta.getColor(R.styleable.CustomRangeBar_scaleTextColor, DEFAULT_SCALE_TEXT_COLOR);

            mPinBgRes = ta.getResourceId(R.styleable.CustomRangeBar_pinBgRes, DEFAULT_PIN_BG_RES);
            mPinBgColor = ta.getColor(R.styleable.CustomRangeBar_pinBgColor, DEFAULT_PIN_BG_COLOR);
            mPinTextPaddingLeft = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingLeft, DEFAULT_PIN_TEXT_PADDING_LEFT_PX);
            mPinTextPaddingTop = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingTop, DEFAULT_PIN_TEXT_PADDING_TOP_PX);
            mPinTextPaddingRight = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingRight, DEFAULT_PIN_TEXT_PADDING_RIGHT_PX);
            mPinTextPaddingBottom = ta.getDimension(R.styleable.CustomRangeBar_pinTextPaddingBottom, DEFAULT_PIN_TEXT_PADDING_BOTTOM_PX);
            mPinMarginBottom = ta.getDimension(R.styleable.CustomRangeBar_pinMarginBottom, DEFAULT_PIN_MARGIN_BOTTOM_PX);
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
    public int getDefaultTickCount() {
        return DEFAULT_TICK_COUNT;
    }

    @Override
    public float getDefaultBarBulgePx() {
        return DEFAULT_BAR_BULGE_PX;
    }

    @Override
    public float getDefaultBarWeightPx() {
        return DEFAULT_BAR_WEIGHT_PX;
    }

    @Override
    public int getDefaultBarColor() {
        return DEFAULT_BAR_COLOR;
    }

    @Override
    public float getDefaultTickWeightPx() {
        return DEFAULT_TICK_WEIGHT_PX;
    }

    @Override
    public float getDefaultTickHeightPx() {
        return DEFAULT_TICK_HEIGHT_PX;
    }

    @Override
    public int getDefaultTickColor() {
        return DEFAULT_TICK_COLOR;
    }

    @Override
    public float getDefaultConnectingLineWeightPx() {
        return DEFAULT_CONNECTING_LINE_WEIGHT_PX;
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
        CustomBar bar = new CustomBar(ctx, marginLeft, yPos, barLength, barBulge, tickCount, tickHeight, barWeight, tickWeight, barColor, tickColor, mTickPadding, mScaleTextSizeSp, mScaleTextColor);
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
                        mPinTextSizeSp,
                        mPinTextColor,
                        maxPinTextLength,
                        mThumbLineHeight,
                        mThumbLineWeight,
                        mThumbLineColor);
        thumb.setPinFormatter(pinFormatter);
        return thumb;
    }
}
