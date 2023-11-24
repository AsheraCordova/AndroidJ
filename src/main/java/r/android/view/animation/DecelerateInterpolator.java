/*
 * Copyright (C) 2007 The Android Open Source Project
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
 * An interpolator where the rate of change starts out quickly and
 * and then decelerates.
 *
 */
//@HasNativeInterpolator
public class DecelerateInterpolator extends BaseInterpolator /*implements NativeInterpolator*/ {
    public DecelerateInterpolator() {
    }

    /**
     * Constructor
     *
     * @param factor Degree to which the animation should be eased. Setting factor to 1.0f produces
     *        an upside-down y=x^2 parabola. Increasing factor above 1.0f exaggerates the
     *        ease-out effect (i.e., it starts even faster and ends evens slower).
     */
    public DecelerateInterpolator(float factor) {
        mFactor = factor;
    }

    //

    /** @hide */
    public float getInterpolation(float input) {
        float result;
        if (mFactor == 1.0f) {
            result = (float)(1.0f - (1.0f - input) * (1.0f - input));
        } else {
            result = (float)(1.0f - Math.pow((1.0f - input), 2 * mFactor));
        }
        return result;
    }

    private float mFactor = 1.0f;

    /** @hide */
    //@Override
    public long createNativeInterpolator() {
        return 0;
    }
}
