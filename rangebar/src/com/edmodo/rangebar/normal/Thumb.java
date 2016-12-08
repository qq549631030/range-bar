/*
 * Copyright 2013, Edmodo, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" 
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */

package com.edmodo.rangebar.normal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.TypedValue;

import com.edmodo.rangebar.BaseThumb;

/**
 * Represents a thumb in the RangeBar slider. This is the handle for the slider
 * that is pressed and slid.
 */
class Thumb extends BaseThumb {


    // Member Variables ////////////////////////////////////////////////////////

    // Radius (in pixels) of the touch area of the thumb.
    private final float mTargetRadiusPx;

    // Variables to store half the width/height for easier calculation.
    private final float mHalfWidthNormal;
    private final float mHalfHeightNormal;

    private final float mHalfWidthPressed;
    private final float mHalfHeightPressed;

    // Constructors ////////////////////////////////////////////////////////////

    Thumb(Context ctx,
          float y,
          int thumbColorNormal,
          int thumbColorPressed,
          float thumbRadiusDP,
          int thumbImageNormal,
          int thumbImagePressed) {
        super(ctx, y, thumbColorNormal, thumbColorPressed, thumbRadiusDP, thumbImageNormal, thumbImagePressed);
        final Resources res = ctx.getResources();

        mHalfWidthNormal = mImageNormal.getWidth() / 2f;
        mHalfHeightNormal = mImageNormal.getHeight() / 2f;

        mHalfWidthPressed = mImagePressed.getWidth() / 2f;
        mHalfHeightPressed = mImagePressed.getHeight() / 2f;

        // Sets the minimum touchable area, but allows it to expand based on
        // image size
        int targetRadius = (int) Math.max(MINIMUM_TARGET_RADIUS_DP, thumbRadiusDP);

        mTargetRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                targetRadius,
                res.getDisplayMetrics());

        mX = mHalfWidthNormal;
    }

    @Override
    public float getHalfWidth() {
        return mHalfWidthNormal;
    }

    @Override
    public float getHalfHeight() {
        return mHalfHeightNormal;
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
    @Override
    public boolean isInTargetZone(float x, float y) {

        if (Math.abs(x - mX) <= mTargetRadiusPx && Math.abs(y - mY) <= mTargetRadiusPx) {
            return true;
        }
        return false;
    }

    @Override
    protected void drawByBitmap(Canvas canvas, Bitmap bitmap, boolean isPressed) {
        if (isPressed) {
            final float topPressed = mY - mHalfHeightPressed;
            final float leftPressed = mX - mHalfWidthPressed;
            canvas.drawBitmap(bitmap, leftPressed, topPressed, null);
        } else {
            final float topNormal = mY - mHalfHeightNormal;
            final float leftNormal = mX - mHalfWidthNormal;
            canvas.drawBitmap(bitmap, leftNormal, topNormal, null);
        }
    }

    @Override
    protected void drawByColor(Canvas canvas, boolean isPressed) {
        if (isPressed)
            canvas.drawCircle(mX, mY, mThumbRadiusPx, mPaintPressed);
        else
            canvas.drawCircle(mX, mY, mThumbRadiusPx, mPaintNormal);
    }
}
