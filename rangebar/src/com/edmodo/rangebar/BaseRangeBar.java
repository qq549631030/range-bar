package com.edmodo.rangebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.edmodo.rangebar.normal.ConnectingLine;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/6 20:00
 * 邮箱：huangx@pycredit.cn
 */

public abstract class BaseRangeBar extends View {

    // Member Variables ////////////////////////////////////////////////////////

    private static final String TAG = "RangeBar";

    // Default values for variables
    private static final int DEFAULT_TICK_COUNT = 3;
    private static final float DEFAULT_TICK_HEIGHT_PX = 48;
    private static final float DEFAULT_BAR_BULGE_PX = 0;
    private static final float DEFAULT_BAR_WEIGHT_PX = 2;
    private static final float DEFAULT_TICK_WEIGHT_PX = 2;
    private static final int DEFAULT_BAR_COLOR = Color.DKGRAY;
    private static final int DEFAULT_TICK_COLOR = Color.LTGRAY;
    private static final float DEFAULT_CONNECTING_LINE_WEIGHT_PX = 2;
    private static final int DEFAULT_THUMB_IMAGE_NORMAL = -1;
    private static final int DEFAULT_THUMB_IMAGE_PRESSED = -1;

    // Corresponds to android.R.color.holo_blue_light.
    private static final int DEFAULT_CONNECTING_LINE_COLOR = 0xff33b5e5;

    // Indicator value tells Thumb.java whether it should draw the circle or not
    private static final float DEFAULT_THUMB_RADIUS_DP = 12;
    private static final int DEFAULT_THUMB_COLOR_NORMAL = 0xff33b5e5;
    private static final int DEFAULT_THUMB_COLOR_PRESSED = Color.BLUE;

    // Instance variables for all of the customizable attributes
    private int mTickCount = DEFAULT_TICK_COUNT;
    protected float mTickHeight = DEFAULT_TICK_HEIGHT_PX;
    protected float mBarBulge = DEFAULT_BAR_BULGE_PX;
    private float mBarWeight = DEFAULT_BAR_WEIGHT_PX;
    private float mTickWeight = DEFAULT_TICK_WEIGHT_PX;
    private int mBarColor = DEFAULT_BAR_COLOR;
    private int mTickColor = DEFAULT_TICK_COLOR;
    private float mConnectingLineWeight = DEFAULT_CONNECTING_LINE_WEIGHT_PX;
    private int mConnectingLineColor = DEFAULT_CONNECTING_LINE_COLOR;
    protected int mThumbImageNormal = DEFAULT_THUMB_IMAGE_NORMAL;
    protected int mThumbImagePressed = DEFAULT_THUMB_IMAGE_PRESSED;

    private float mThumbRadiusDP = DEFAULT_THUMB_RADIUS_DP;
    private int mThumbColorNormal = DEFAULT_THUMB_COLOR_NORMAL;
    private int mThumbColorPressed = DEFAULT_THUMB_COLOR_PRESSED;

    // setTickCount only resets indices before a thumb has been pressed or a
    // setThumbIndices() is called, to correspond with intended usage
    private boolean mFirstSetTickCount = true;

    private int mDefaultWidth = 500;
    private int mDefaultHeight = 100;

    private BaseThumb mLeftThumb;
    private BaseThumb mRightThumb;
    protected BaseBar mBar;
    private BaseConnectingLine mConnectingLine;

    private OnRangeBarChangeListener mListener;

    private IRangeBarFormatter mFormatter;

    private int mLeftIndex = 0;
    private int mRightIndex = mTickCount - 1;


    // Constructors ////////////////////////////////////////////////////////////

    public BaseRangeBar(Context context) {
        this(context, null);
    }

    public BaseRangeBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRangeBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        rangeBarInit(context, attrs);
    }

    // View Methods ////////////////////////////////////////////////////////////

    @Override
    public Parcelable onSaveInstanceState() {

        final Bundle bundle = new Bundle();

        bundle.putParcelable("instanceState", super.onSaveInstanceState());

        bundle.putInt("TICK_COUNT", mTickCount);
        bundle.putFloat("TICK_HEIGHT", mTickHeight);
        bundle.putFloat("BAR_WEIGHT", mBarWeight);
        bundle.putFloat("TICK_WEIGHT", mTickWeight);
        bundle.putInt("BAR_COLOR", mBarColor);
        bundle.putInt("TICK_COLOR", mTickColor);
        bundle.putFloat("CONNECTING_LINE_WEIGHT", mConnectingLineWeight);
        bundle.putInt("CONNECTING_LINE_COLOR", mConnectingLineColor);

        bundle.putInt("THUMB_IMAGE_NORMAL", mThumbImageNormal);
        bundle.putInt("THUMB_IMAGE_PRESSED", mThumbImagePressed);

        bundle.putFloat("THUMB_RADIUS_DP", mThumbRadiusDP);
        bundle.putInt("THUMB_COLOR_NORMAL", mThumbColorNormal);
        bundle.putInt("THUMB_COLOR_PRESSED", mThumbColorPressed);

        bundle.putInt("LEFT_INDEX", mLeftIndex);
        bundle.putInt("RIGHT_INDEX", mRightIndex);

        bundle.putBoolean("FIRST_SET_TICK_COUNT", mFirstSetTickCount);

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {

            final Bundle bundle = (Bundle) state;

            mTickCount = bundle.getInt("TICK_COUNT");
            mTickHeight = bundle.getFloat("TICK_HEIGHT");
            mBarWeight = bundle.getFloat("BAR_WEIGHT");
            mTickWeight = bundle.getFloat("TICK_WEIGHT");
            mBarColor = bundle.getInt("BAR_COLOR");
            mTickColor = bundle.getInt("TICK_COLOR");
            mConnectingLineWeight = bundle.getFloat("CONNECTING_LINE_WEIGHT");
            mConnectingLineColor = bundle.getInt("CONNECTING_LINE_COLOR");

            mThumbImageNormal = bundle.getInt("THUMB_IMAGE_NORMAL");
            mThumbImagePressed = bundle.getInt("THUMB_IMAGE_PRESSED");

            mThumbRadiusDP = bundle.getFloat("THUMB_RADIUS_DP");
            mThumbColorNormal = bundle.getInt("THUMB_COLOR_NORMAL");
            mThumbColorPressed = bundle.getInt("THUMB_COLOR_PRESSED");

            mLeftIndex = bundle.getInt("LEFT_INDEX");
            mRightIndex = bundle.getInt("RIGHT_INDEX");
            mFirstSetTickCount = bundle.getBoolean("FIRST_SET_TICK_COUNT");

            setThumbIndices(mLeftIndex, mRightIndex);

            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));

        } else {

            super.onRestoreInstanceState(state);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width;
        int height;

        // Get measureSpec mode and size values.
        final int measureWidthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        final int measureHeightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        final int measureWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        final int measureHeight = View.MeasureSpec.getSize(heightMeasureSpec);

        // The RangeBar width should be as large as possible.
        if (measureWidthMode == View.MeasureSpec.AT_MOST) {
            width = measureWidth;
        } else if (measureWidthMode == View.MeasureSpec.EXACTLY) {
            width = measureWidth;
        } else {
            width = mDefaultWidth;
        }

        // The RangeBar height should be as small as possible.
        if (measureHeightMode == View.MeasureSpec.AT_MOST) {
            height = Math.max(mDefaultHeight, measureHeight);
        } else if (measureHeightMode == View.MeasureSpec.EXACTLY) {
            height = measureHeight;
        } else {
            height = mDefaultHeight;
        }
        if (measureHeightMode != MeasureSpec.EXACTLY) {
            if (mBar != null) {
                int barMinHeight = mBar.measureMinHeight();
                height = Math.max(height, barMinHeight);
            }

            if (mLeftThumb != null) {
                int ThumbHeight = mLeftThumb.measureMinHeight();
                height = Math.max(height, ThumbHeight);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);

        final Context ctx = getContext();

        // This is the initial point at which we know the size of the View.

        // Create the two thumb objects.
        final float yPos = h / 2f;
        mLeftThumb = createThumb(ctx, yPos, mThumbColorNormal, mThumbColorPressed, mThumbRadiusDP, mThumbImageNormal, mThumbImagePressed);
        mRightThumb = createThumb(ctx, yPos, mThumbColorNormal, mThumbColorPressed, mThumbRadiusDP, mThumbImageNormal, mThumbImagePressed);

        // Create the underlying bar.
        final float marginLeft = mLeftThumb.getHalfWidth();
        final float barLength = w - 2 * marginLeft;
        mBar = createBar(ctx, marginLeft, yPos, barLength, mBarBulge, mTickCount, mTickHeight, mBarWeight, mBarColor, mTickWeight, mTickColor);
        mBar.setFormatter(mFormatter);
        // Initialize thumbs to the desired indices

        mLeftThumb.setX(marginLeft + mBarBulge + (mLeftIndex / (float) (mTickCount - 1)) * (barLength - mBarBulge * 2f));
        mLeftThumb.setText(getPinValue(mLeftIndex));
        mRightThumb.setX(marginLeft + mBarBulge + (mRightIndex / (float) (mTickCount - 1)) * (barLength - mBarBulge * 2f));
        mRightThumb.setText(getPinValue(mRightIndex));

        // Set the thumb indices.
        final int newLeftIndex = mBar.getNearestTickIndex(mLeftThumb);
        final int newRightIndex = mBar.getNearestTickIndex(mRightThumb);

        // Call the listener.
        if (newLeftIndex != mLeftIndex || newRightIndex != mRightIndex) {

            mLeftIndex = newLeftIndex;
            mRightIndex = newRightIndex;

            if (mListener != null) {
                mListener.onIndexChangeListener(this, mLeftIndex, mRightIndex);
            }
        }

        // Create the line connecting the two thumbs.
        mConnectingLine = createConnectingLine(ctx, yPos, mConnectingLineWeight, mConnectingLineColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        mBar.draw(canvas);

        mConnectingLine.draw(canvas, mLeftThumb, mRightThumb);

        mLeftThumb.draw(canvas, mLeftThumb.getX(), mRightThumb.getX());
        mRightThumb.draw(canvas, mLeftThumb.getX(), mRightThumb.getX());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // If this View is not enabled, don't allow for touch interactions.
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                onActionDown(event.getX(), event.getY());
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                onActionUp(event.getX(), event.getY());
                return true;

            case MotionEvent.ACTION_MOVE:
                onActionMove(event.getX());
                this.getParent().requestDisallowInterceptTouchEvent(true);
                return true;

            default:
                return false;
        }
    }

    // Public Methods //////////////////////////////////////////////////////////

    /**
     * Sets a listener to receive notifications of changes to the RangeBar. This
     * will overwrite any existing set listeners.
     *
     * @param listener the RangeBar notification listener; null to remove any
     *                 existing listener
     */
    public void setOnRangeBarChangeListener(OnRangeBarChangeListener listener) {
        mListener = listener;
    }

    /**
     * Sets a listener to modify the text
     *
     * @param formatter the RangeBar pin text notification listener; null to remove any
     *                  existing listener
     */
    public void setFormatter(IRangeBarFormatter formatter) {
        this.mFormatter = formatter;
        if (mBar != null) {
            mBar.setFormatter(formatter);
        }
        invalidate();
    }

    /**
     * Sets the number of ticks in the RangeBar.
     *
     * @param tickCount Integer specifying the number of ticks.
     */
    public void setTickCount(int tickCount) {

        if (isValidTickCount(tickCount)) {
            mTickCount = tickCount;

            // Prevents resetting the indices when creating new activity, but
            // allows it on the first setting.
            if (mFirstSetTickCount) {
                mLeftIndex = 0;
                mRightIndex = mTickCount - 1;

                if (mListener != null) {
                    mListener.onIndexChangeListener(this, mLeftIndex, mRightIndex);
                }
            }
            if (indexOutOfRange(mLeftIndex, mRightIndex)) {
                mLeftIndex = 0;
                mRightIndex = mTickCount - 1;

                if (mListener != null)
                    mListener.onIndexChangeListener(this, mLeftIndex, mRightIndex);
            }

            createBar();
            createThumbs();
        } else {
            Log.e(TAG, "tickCount less than 2; invalid tickCount.");
            throw new IllegalArgumentException("tickCount less than 2; invalid tickCount.");
        }
    }

    /**
     * Sets the height of the ticks in the range bar.
     *
     * @param tickHeight Float specifying the height of each tick mark in dp.
     */
    public void setTickHeight(float tickHeight) {

        mTickHeight = tickHeight;
        createBar();
    }

    /**
     * Set the weight of the bar line in the range bar.
     *
     * @param barWeight Float specifying the weight of the bar line in
     *                  px.
     */
    public void setBarWeight(float barWeight) {

        mBarWeight = barWeight;
        createBar();
    }

    /**
     * Set the weight of the tick lines in the range bar.
     *
     * @param tickWeight Float specifying the weight of the tick lines in
     *                   px.
     */
    public void setTickWeight(float tickWeight) {

        mTickWeight = tickWeight;
        createBar();
    }

    /**
     * Set the color of the bar line in the range bar.
     *
     * @param barColor Integer specifying the color of the bar line.
     */
    public void setBarColor(int barColor) {

        mBarColor = barColor;
        createBar();
    }

    /**
     * Set the color of the tick lines in the range bar.
     *
     * @param tickColor Integer specifying the color of the tick lines.
     */
    public void setTickColor(int tickColor) {

        mTickColor = tickColor;
        createBar();
    }

    /**
     * Set the weight of the connecting line between the thumbs.
     *
     * @param connectingLineWeight Float specifying the weight of the connecting
     *                             line.
     */
    public void setConnectingLineWeight(float connectingLineWeight) {

        mConnectingLineWeight = connectingLineWeight;
        createConnectingLine();
    }

    /**
     * Set the color of the connecting line between the thumbs.
     *
     * @param connectingLineColor Integer specifying the color of the connecting
     *                            line.
     */
    public void setConnectingLineColor(int connectingLineColor) {

        mConnectingLineColor = connectingLineColor;
        createConnectingLine();
    }

    /**
     * If this is set, the thumb images will be replaced with a circle of the
     * specified radius. Default width = 20dp.
     *
     * @param thumbRadius Float specifying the radius of the thumbs to be drawn.
     */
    public void setThumbRadius(float thumbRadius) {

        mThumbRadiusDP = thumbRadius;
        createThumbs();
    }

    /**
     * Sets the normal thumb picture by taking in a reference ID to an image.
     *
     * @param thumbImageNormalID Integer specifying the resource ID of the image to
     *                           be drawn as the normal thumb.
     */
    public void setThumbImageNormal(int thumbImageNormalID) {
        mThumbImageNormal = thumbImageNormalID;
        createThumbs();
    }

    /**
     * Sets the pressed thumb picture by taking in a reference ID to an image.
     *
     * @param thumbImagePressedID Integer specifying the resource ID of the image to
     *                            be drawn as the pressed thumb.
     */
    public void setThumbImagePressed(int thumbImagePressedID) {
        mThumbImagePressed = thumbImagePressedID;
        createThumbs();
    }

    /**
     * If this is set, the thumb images will be replaced with a circle. The
     * normal image will be of the specified color.
     *
     * @param thumbColorNormal Integer specifying the normal color of the circle
     *                         to be drawn.
     */
    public void setThumbColorNormal(int thumbColorNormal) {
        mThumbColorNormal = thumbColorNormal;
        createThumbs();
    }

    /**
     * If this is set, the thumb images will be replaced with a circle. The
     * pressed image will be of the specified color.
     *
     * @param thumbColorPressed Integer specifying the pressed color of the
     *                          circle to be drawn.
     */
    public void setThumbColorPressed(int thumbColorPressed) {
        mThumbColorPressed = thumbColorPressed;
        createThumbs();
    }

    /**
     * Sets the location of each thumb according to the developer's choice.
     * Numbered from 0 to mTickCount - 1 from the left.
     *
     * @param leftThumbIndex  Integer specifying the index of the left thumb
     * @param rightThumbIndex Integer specifying the index of the right thumb
     */
    public void setThumbIndices(int leftThumbIndex, int rightThumbIndex) {
        if (indexOutOfRange(leftThumbIndex, rightThumbIndex)) {

            Log.e(TAG, "A thumb index is out of bounds. Check that it is between 0 and mTickCount - 1");
            throw new IllegalArgumentException("A thumb index is out of bounds. Check that it is between 0 and mTickCount - 1");

        } else {

            if (mFirstSetTickCount == true)
                mFirstSetTickCount = false;

            mLeftIndex = leftThumbIndex;
            mRightIndex = rightThumbIndex;
            createThumbs();

            if (mListener != null) {
                mListener.onIndexChangeListener(this, mLeftIndex, mRightIndex);
            }
        }

        invalidate();
        requestLayout();
    }

    /**
     * Gets the index of the left-most thumb.
     *
     * @return the 0-based index of the left thumb
     */
    public int getLeftIndex() {
        return mLeftIndex;
    }

    /**
     * Gets the index of the right-most thumb.
     *
     * @return the 0-based index of the right thumb
     */
    public int getRightIndex() {
        return mRightIndex;
    }

    protected float getDefaultTickHeightPx() {
        return DEFAULT_TICK_HEIGHT_PX;
    }

    protected float getDefaultBarBulgePx() {
        return DEFAULT_BAR_BULGE_PX;
    }

    protected float getDefaultBarWeightPx() {
        return DEFAULT_BAR_WEIGHT_PX;
    }

    protected float getDefaultConnectingLineWeightPx() {
        return DEFAULT_CONNECTING_LINE_WEIGHT_PX;
    }

    protected float getDefaultThumbRadiusDp() {
        return DEFAULT_THUMB_RADIUS_DP;
    }

    protected float getDefaultTickWeightPx() {
        return DEFAULT_TICK_WEIGHT_PX;
    }

    protected int getDefaultBarColor() {
        return DEFAULT_BAR_COLOR;
    }

    protected int getDefaultConnectingLineColor() {
        return DEFAULT_CONNECTING_LINE_COLOR;
    }

    protected int getDefaultThumbColorNormal() {
        return DEFAULT_THUMB_COLOR_NORMAL;
    }

    protected int getDefaultThumbColorPressed() {
        return DEFAULT_THUMB_COLOR_PRESSED;
    }

    protected int getDefaultThumbImageNormal() {
        return DEFAULT_THUMB_IMAGE_NORMAL;
    }

    protected int getDefaultThumbImagePressed() {
        return DEFAULT_THUMB_IMAGE_PRESSED;
    }

    protected int getDefaultTickColor() {
        return DEFAULT_TICK_COLOR;
    }

    protected int getDefaultTickCount() {
        return DEFAULT_TICK_COUNT;
    }

    /**
     * Does all the functions of the constructor for RangeBar. Called by both
     * RangeBar constructors in lieu of copying the code for each constructor.
     *
     * @param context Context from the constructor.
     * @param attrs   AttributeSet from the constructor.
     * @return none
     */
    protected void rangeBarInit(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseRangeBar, 0, 0);

        try {

            // Sets the values of the user-defined attributes based on the XML
            // attributes.
            final Integer tickCount = ta.getInteger(R.styleable.BaseRangeBar_tickCount, getDefaultTickCount());

            if (isValidTickCount(tickCount)) {

                // Similar functions performed above in setTickCount; make sure
                // you know how they interact
                mTickCount = tickCount;
                mLeftIndex = 0;
                mRightIndex = mTickCount - 1;

                if (mListener != null) {
                    mListener.onIndexChangeListener(this, mLeftIndex, mRightIndex);
                }

            } else {

                Log.e(TAG, "tickCount less than 2; invalid tickCount. XML input ignored.");
            }

            mTickHeight = ta.getDimension(R.styleable.BaseRangeBar_tickHeight, getDefaultTickHeightPx());
            mBarBulge = ta.getDimension(R.styleable.BaseRangeBar_barBulge, getDefaultBarBulgePx());

            mBarWeight = ta.getDimension(R.styleable.BaseRangeBar_barWeight, getDefaultBarWeightPx());
            mBarColor = ta.getColor(R.styleable.BaseRangeBar_barColor, getDefaultBarColor());
            mTickWeight = ta.getDimension(R.styleable.BaseRangeBar_tickWeight, getDefaultTickWeightPx());
            mTickColor = ta.getColor(R.styleable.BaseRangeBar_tickColor, getDefaultTickColor());
            mConnectingLineWeight = ta.getDimension(R.styleable.BaseRangeBar_connectingLineWeight,
                    getDefaultConnectingLineWeightPx());
            mConnectingLineColor = ta.getColor(R.styleable.BaseRangeBar_connectingLineColor,
                    getDefaultConnectingLineColor());
            mThumbRadiusDP = ta.getDimension(R.styleable.BaseRangeBar_thumbRadius, getDefaultThumbRadiusDp());
            mThumbImageNormal = ta.getResourceId(R.styleable.BaseRangeBar_thumbImageNormal,
                    getDefaultThumbImageNormal());
            mThumbImagePressed = ta.getResourceId(R.styleable.BaseRangeBar_thumbImagePressed,
                    getDefaultThumbImagePressed());
            mThumbColorNormal = ta.getColor(R.styleable.BaseRangeBar_thumbColorNormal, getDefaultThumbColorNormal());
            mThumbColorPressed = ta.getColor(R.styleable.BaseRangeBar_thumbColorPressed,
                    getDefaultThumbColorPressed());
            initOtherAttribute(ta);
        } finally {

            ta.recycle();
        }

    }

    protected void initOtherAttribute(TypedArray ta) {

    }

    /**
     * Creates a new mBar
     */
    protected void createBar() {
        mBar = createBar(getContext(), getMarginLeft(), getYPos(), getBarLength(), mBarBulge, mTickCount, mTickHeight, mBarWeight, mBarColor, mTickWeight, mTickColor);
        mBar.setFormatter(mFormatter);
        invalidate();
    }

    protected abstract BaseBar createBar(Context ctx, float marginLeft, float yPos, float barLength, float barBulge, int tickCount,
                                         float tickHeightDP, float barWeight, int barColor, float tickWeight, int tickColor);

    /**
     * Creates a new ConnectingLine.
     */
    private void createConnectingLine() {

        mConnectingLine = createConnectingLine(getContext(), getYPos(), mConnectingLineWeight, mConnectingLineColor);
        invalidate();
    }

    protected abstract BaseConnectingLine createConnectingLine(Context ctx, float yPos, float connectingLineWeight, int connectingLineColor);

    /**
     * Creates two new Thumbs.
     */
    private void createThumbs() {

        Context ctx = getContext();
        float yPos = getYPos();

        mLeftThumb = createThumb(ctx, yPos, mThumbColorNormal, mThumbColorPressed, mThumbRadiusDP, mThumbImageNormal, mThumbImagePressed);
        mRightThumb = createThumb(ctx, yPos, mThumbColorNormal, mThumbColorPressed, mThumbRadiusDP, mThumbImageNormal, mThumbImagePressed);

        float marginLeft = getMarginLeft();
        float barLength = getBarLength();
        // Initialize thumbs to the desired indices
        mLeftThumb.setX(marginLeft + mBarBulge + (mLeftIndex / (float) (mTickCount - 1)) * (barLength - mBarBulge * 2f));
        mLeftThumb.setText(getPinValue(mLeftIndex));
        mRightThumb.setX(marginLeft + mBarBulge + (mRightIndex / (float) (mTickCount - 1)) * (barLength - mBarBulge * 2f));
        mRightThumb.setText(getPinValue(mRightIndex));
        invalidate();
    }

    protected abstract BaseThumb createThumb(Context ctx,
                                             float yPos,
                                             int thumbColorNormal,
                                             int thumbColorPressed,
                                             float thumbRadiusDP,
                                             int thumbImageNormal,
                                             int thumbImagePressed);

    /**
     * Get marginLeft in each of the public attribute methods.
     *
     * @return float marginLeft
     */
    private float getMarginLeft() {
        return ((mLeftThumb != null) ? mLeftThumb.getHalfWidth() : 0);
    }

    /**
     * Get yPos in each of the public attribute methods.
     *
     * @return float yPos
     */
    private float getYPos() {
        return (getHeight() / 2f);
    }

    /**
     * Get barLength in each of the public attribute methods.
     *
     * @return float barLength
     */
    private float getBarLength() {
        return (getWidth() - 2 * getMarginLeft());
    }

    /**
     * Returns if either index is outside the range of the tickCount.
     *
     * @param leftThumbIndex  Integer specifying the left thumb index.
     * @param rightThumbIndex Integer specifying the right thumb index.
     * @return boolean If the index is out of range.
     */
    private boolean indexOutOfRange(int leftThumbIndex, int rightThumbIndex) {
        return (leftThumbIndex < 0 || leftThumbIndex >= mTickCount
                || rightThumbIndex < 0
                || rightThumbIndex >= mTickCount);
    }

    /**
     * If is invalid tickCount, rejects. TickCount must be greater than 1
     *
     * @param tickCount Integer
     * @return boolean: whether tickCount > 1
     */
    private boolean isValidTickCount(int tickCount) {
        return (tickCount > 1);
    }

    /**
     * Handles a {@link MotionEvent#ACTION_DOWN} event.
     *
     * @param x the x-coordinate of the down action
     * @param y the y-coordinate of the down action
     */
    private void onActionDown(float x, float y) {

        if (!mLeftThumb.isPressed() && mLeftThumb.isInTargetZone(x, y)) {

            pressThumb(mLeftThumb);

        } else if (!mLeftThumb.isPressed() && mRightThumb.isInTargetZone(x, y)) {

            pressThumb(mRightThumb);
        }
    }

    /**
     * Handles a {@link MotionEvent#ACTION_UP} or
     * {@link MotionEvent#ACTION_CANCEL} event.
     *
     * @param x the x-coordinate of the up action
     * @param y the y-coordinate of the up action
     */
    private void onActionUp(float x, float y) {

        if (mLeftThumb.isPressed()) {

            releaseThumb(mLeftThumb);

        } else if (mRightThumb.isPressed()) {

            releaseThumb(mRightThumb);

        } else {

            float leftThumbXDistance = Math.abs(mLeftThumb.getX() - x);
            float rightThumbXDistance = Math.abs(mRightThumb.getX() - x);

            if (leftThumbXDistance < rightThumbXDistance) {
                mLeftThumb.setX(x);
                releaseThumb(mLeftThumb);
            } else {
                mRightThumb.setX(x);
                releaseThumb(mRightThumb);
            }

            // Get the updated nearest tick marks for each thumb.
            final int newLeftIndex = mBar.getNearestTickIndex(mLeftThumb);
            final int newRightIndex = mBar.getNearestTickIndex(mRightThumb);

            // If either of the indices have changed, update and call the listener.
            if (newLeftIndex != mLeftIndex || newRightIndex != mRightIndex) {

                mLeftIndex = newLeftIndex;
                mRightIndex = newRightIndex;

                if (mListener != null) {
                    mListener.onIndexChangeListener(this, mLeftIndex, mRightIndex);
                }
            }
        }
    }

    /**
     * Handles a {@link MotionEvent#ACTION_MOVE} event.
     *
     * @param x the x-coordinate of the move event
     */
    private void onActionMove(float x) {

        // Move the pressed thumb to the new x-position.
        if (mLeftThumb.isPressed()) {
            moveThumb(mLeftThumb, x);
        } else if (mRightThumb.isPressed()) {
            moveThumb(mRightThumb, x);
        }

        // If the thumbs have switched order, fix the references.
        if (mLeftThumb.getX() > mRightThumb.getX()) {
            final BaseThumb temp = mLeftThumb;
            mLeftThumb = mRightThumb;
            mRightThumb = temp;
        }

        // Get the updated nearest tick marks for each thumb.
        final int newLeftIndex = mBar.getNearestTickIndex(mLeftThumb);
        final int newRightIndex = mBar.getNearestTickIndex(mRightThumb);

        // If either of the indices have changed, update and call the listener.
        if (newLeftIndex != mLeftIndex || newRightIndex != mRightIndex) {

            mLeftIndex = newLeftIndex;
            mRightIndex = newRightIndex;
            mLeftThumb.setText(getPinValue(mLeftIndex));
            mRightThumb.setText(getPinValue(mRightIndex));
            if (mListener != null) {
                mListener.onIndexChangeListener(this, mLeftIndex, mRightIndex);
            }
        }
    }

    /**
     * Set the thumb to be in the pressed state and calls invalidate() to redraw
     * the canvas to reflect the updated state.
     *
     * @param thumb the thumb to press
     */
    private void pressThumb(BaseThumb thumb) {
        if (mFirstSetTickCount == true)
            mFirstSetTickCount = false;
        thumb.press();
        invalidate();
    }

    /**
     * Set the thumb to be in the normal/un-pressed state and calls invalidate()
     * to redraw the canvas to reflect the updated state.
     *
     * @param thumb the thumb to release
     */
    private void releaseThumb(BaseThumb thumb) {

        final float nearestTickX = mBar.getNearestTickCoordinate(thumb);
        thumb.setX(nearestTickX);
        int tickIndex = mBar.getNearestTickIndex(thumb);
        thumb.setText(getPinValue(tickIndex));
        thumb.release();
        invalidate();
    }

    /**
     * Moves the thumb to the given x-coordinate.
     *
     * @param thumb the thumb to move
     * @param x     the x-coordinate to move the thumb to
     */
    private void moveThumb(BaseThumb thumb, float x) {

        // If the user has moved their finger outside the range of the bar,
        // do not move the thumbs past the edge.
        if (x < mBar.getLeftX() || x > mBar.getRightX()) {
            // Do nothing.
        } else {
            thumb.setX(x);
            invalidate();
        }
    }


    /**
     * Set the value on the thumb pin, either from map or calculated from the tick intervals
     * Integer check to format decimals as whole numbers
     *
     * @param tickIndex the index to set the value for
     */
    private String getPinValue(int tickIndex) {
        if (mFormatter != null) {
            return mFormatter.format(tickIndex);
        }
        return String.valueOf(tickIndex);
    }

    // Inner Classes ///////////////////////////////////////////////////////////

    /**
     * A callback that notifies clients when the RangeBar has changed. The
     * listener will only be called when either thumb's index has changed - not
     * for every movement of the thumb.
     */
    public static interface OnRangeBarChangeListener {

        public void onIndexChangeListener(BaseRangeBar rangeBar, int leftThumbIndex, int rightThumbIndex);
    }
}
