package com.edmodo.rangebar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/7 10:37
 * 邮箱：huangx@pycredit.cn
 */

public abstract class BaseThumb {

    // The radius (in dp) of the touchable area around the thumb. We are basing
    // this value off of the recommended 48dp Rhythm. See:
    // http://developer.android.com/design/style/metrics-grids.html#48dp-rhythm
    protected static final float MINIMUM_TARGET_RADIUS_DP = 24;

    // Sets the default values for radius, normal, pressed if circle is to be
    // drawn but no value is given.
    protected static final float DEFAULT_THUMB_RADIUS_DP = 14;

    // Corresponds to android.R.color.holo_blue_light.
    protected static final int DEFAULT_THUMB_COLOR_NORMAL = 0xff33b5e5;
    protected static final int DEFAULT_THUMB_COLOR_PRESSED = 0xff33b5e5;


    // The normal and pressed images to display for the thumbs.
    protected Bitmap mImageNormal;
    protected Bitmap mImagePressed;


    // mPaint to draw the thumbs if attributes are selected
    protected Paint mPaintNormal;
    protected Paint mPaintPressed;

    // Radius of the new thumb if selected
    protected float mThumbRadiusPx;

    // Toggle to select bitmap thumbImage or not
    protected boolean mUseBitmap;

    // Colors of the thumbs if they are to be drawn
    private int mThumbColorNormal;
    private int mThumbColorPressed;

    // Indicates whether this thumb is currently pressed and active.
    protected boolean mIsPressed = false;

    // The y-position of the thumb in the parent view. This should not change.
    protected final float mY;

    // The current x-position of the thumb in the parent view.
    protected float mX;

    protected String mText;

    public BaseThumb(Context ctx, float y, int thumbColorNormal, int thumbColorPressed, float thumbRadiusDP, int thumbImageNormal, int thumbImagePressed) {
        mY = y;
        final Resources res = ctx.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        mImageNormal = BitmapFactory.decodeResource(res, thumbImageNormal, options);
        mImagePressed = BitmapFactory.decodeResource(res, thumbImagePressed, options);
        // If any of the attributes are set, toggle bitmap off
        if (thumbRadiusDP == -1 && thumbColorNormal == -1 && thumbColorPressed == -1) {

            mUseBitmap = true;

        } else {

            mUseBitmap = false;

            // If one of the attributes are set, but the others aren't, set the
            // attributes to default
            if (thumbRadiusDP == -1)
                mThumbRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        DEFAULT_THUMB_RADIUS_DP,
                        res.getDisplayMetrics());
            else
                mThumbRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        thumbRadiusDP,
                        res.getDisplayMetrics());

            if (thumbColorNormal == -1)
                mThumbColorNormal = DEFAULT_THUMB_COLOR_NORMAL;
            else
                mThumbColorNormal = thumbColorNormal;

            if (thumbColorPressed == -1)
                mThumbColorPressed = DEFAULT_THUMB_COLOR_PRESSED;
            else
                mThumbColorPressed = thumbColorPressed;

            // Creates the paint and sets the Paint values
            mPaintNormal = new Paint();
            mPaintNormal.setColor(mThumbColorNormal);
            mPaintNormal.setAntiAlias(true);

            mPaintPressed = new Paint();
            mPaintPressed.setColor(mThumbColorPressed);
            mPaintPressed.setAntiAlias(true);
        }
    }

    public abstract float getHalfWidth();

    public abstract float getHalfHeight();

    public void setX(float x) {
        mX = x;
    }

    public float getX() {
        return mX;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public boolean isPressed() {
        return mIsPressed;
    }

    public void press() {
        mIsPressed = true;
    }

    public void release() {
        mIsPressed = false;
    }

    /**
     * Determines if the input coordinate is close enough to this thumb to
     * consider it a press.
     *
     * @param x the x-coordinate of the user touch
     * @param y the y-coordinate of the user touch
     * @return true if the coordinates are within this thumb's target area;
     * false otherwise
     */
    public abstract boolean isInTargetZone(float x, float y);

    /**
     * Draws this thumb on the provided canvas.
     *
     * @param canvas Canvas to draw on; should be the Canvas passed into {#link
     *               View#onDraw()}
     */
    public void draw(Canvas canvas, float leftX, float rightX) {
        // If a bitmap is to be printed. Determined by thumbRadius attribute.
        if (mUseBitmap) {
            final Bitmap bitmap = (mIsPressed) ? mImagePressed : mImageNormal;
            drawByBitmap(canvas, bitmap, leftX, rightX, mIsPressed);
        } else {
            // Otherwise use a circle to display.
            drawByColor(canvas, leftX, rightX, mIsPressed);
        }
    }

    protected abstract void drawByBitmap(Canvas canvas, Bitmap bitmap, float leftX, float rightX, boolean isPressed);

    protected abstract void drawByColor(Canvas canvas, float leftX, float rightX, boolean isPressed);

    public int measureMinWidth() {
        return -1;
    }

    public int measureMaxWidth() {
        return -1;
    }

    public int measureMinHeight() {
        return -1;
    }
}
