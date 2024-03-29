/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package r.android.view.animation;

import r.android.content.Context;
import r.android.content.res.Resources;
//import r.android.content.res.Resources.Theme;
//import r.android.content.res.TypedArray;
//import r.android.graphics.animation.HasNativeInterpolator;
//import r.android.graphics.animation.NativeInterpolator;
//import r.android.graphics.animation.NativeInterpolatorFactory;
import r.android.util.AttributeSet;

//import r.com.android.internal.R;

/**
 * An interpolator where the rate of change starts out slowly and
 * and then accelerates.
 *
 */
//@HasNativeInterpolator
public class AccelerateInterpolator extends BaseInterpolator /*implements NativeInterpolator*/ {
    private final float mFactor;
    private final double mDoubleFactor;

    public AccelerateInterpolator() {
        mFactor = 1.0f;
        mDoubleFactor = 2.0;
    }

    /**
     * Constructor
     *
     * @param factor Degree to which the animation should be eased. Seting
     *        factor to 1.0f produces a y=x^2 parabola. Increasing factor above
     *        1.0f  exaggerates the ease-in effect (i.e., it starts even
     *        slower and ends evens faster)
     */
    public AccelerateInterpolator(float factor) {
        mFactor = factor;
        mDoubleFactor = 2 * mFactor;
    }

    //

    /** @hide */
    public float getInterpolation(float input) {
        if (mFactor == 1.0f) {
            return input * input;
        } else {
            return (float)Math.pow(input, mDoubleFactor);
        }
    }

    /** @hide */
    //@Override
    public long createNativeInterpolator() {
        return 0;
    }
}
