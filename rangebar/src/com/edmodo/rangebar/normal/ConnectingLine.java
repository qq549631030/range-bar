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
import android.graphics.Canvas;

import com.edmodo.rangebar.BaseConnectingLine;
import com.edmodo.rangebar.BaseThumb;

/**
 * Class representing the blue connecting line between the two thumbs.
 */
public class ConnectingLine extends BaseConnectingLine {

    public ConnectingLine(Context ctx, float y, float connectingLineWeight, int connectingLineColor) {
        super(ctx, y, connectingLineWeight, connectingLineColor);
    }

    /**
     * Draw the connecting line between the two thumbs.
     *
     * @param canvas     the Canvas to draw to
     * @param leftThumb  the left thumb
     * @param rightThumb the right thumb
     */
    public void draw(Canvas canvas, BaseThumb leftThumb, BaseThumb rightThumb) {
        canvas.drawLine(leftThumb.getX(), mY, rightThumb.getX(), mY, mPaint);
    }
}
