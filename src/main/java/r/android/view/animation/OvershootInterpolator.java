/*
 * Copyright (C) 2009 The Android Open Source Project
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
 * An interpolator where the change flings forward and overshoots the last value
 * then comes back.
 */
//@HasNativeInterpolator
public class OvershootInterpolator extends BaseInterpolator /*implements NativeInterpolator*/ {
    private final float mTension;

    public OvershootInterpolator() {
        mTension = 2.0f;
    }

    /**
     * @param tension Amount of overshoot. When tension equals 0.0f, there is
     *                no overshoot and the interpolator becomes a simple
     *                deceleration interpolator.
     */
    public OvershootInterpolator(float tension) {
        mTension = tension;
    }

    //

    /** @hide */
    public float getInterpolation(float t) {
        // _o(t) = t * t * ((tension + 1) * t + tension)
        // o(t) = _o(t - 1) + 1
        t -= 1.0f;
        return t * t * ((mTension + 1) * t + mTension) + 1.0f;
    }

    /** @hide */
    //@Override
    public long createNativeInterpolator() {
        return 0;
    }
}
