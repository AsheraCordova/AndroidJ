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

//import static r.com.android.internal.R.styleable.AnticipateOvershootInterpolator;
//import static r.com.android.internal.R.styleable.AnticipateOvershootInterpolator_extraTension;
//import static r.com.android.internal.R.styleable.AnticipateOvershootInterpolator_tension;

import r.android.content.Context;
import r.android.content.res.Resources;
//import r.android.content.res.Resources.Theme;
//import r.android.content.res.TypedArray;
//import r.android.graphics.animation.HasNativeInterpolator;
//import r.android.graphics.animation.NativeInterpolator;
//import r.android.graphics.animation.NativeInterpolatorFactory;
import r.android.util.AttributeSet;


/**
 * An interpolator where the change starts backward then flings forward and overshoots
 * the target value and finally goes back to the final value.
 */
//@HasNativeInterpolator
public class AnticipateOvershootInterpolator extends BaseInterpolator
        /*implements NativeInterpolator*/ {
    private final float mTension;

    public AnticipateOvershootInterpolator() {
        mTension = 2.0f * 1.5f;
    }

    /**
     * @param tension Amount of anticipation/overshoot. When tension equals 0.0f,
     *                there is no anticipation/overshoot and the interpolator becomes
     *                a simple acceleration/deceleration interpolator.
     */
    public AnticipateOvershootInterpolator(float tension) {
        mTension = tension * 1.5f;
    }

    /**
     * @param tension Amount of anticipation/overshoot. When tension equals 0.0f,
     *                there is no anticipation/overshoot and the interpolator becomes
     *                a simple acceleration/deceleration interpolator.
     * @param extraTension Amount by which to multiply the tension. For instance,
     *                     to get the same overshoot as an OvershootInterpolator with
     *                     a tension of 2.0f, you would use an extraTension of 1.5f.
     */
    public AnticipateOvershootInterpolator(float tension, float extraTension) {
        mTension = tension * extraTension;
    }

    //

    /** @hide */
    private static float a(float t, float s) {
        return t * t * ((s + 1) * t - s);
    }

    private static float o(float t, float s) {
        return t * t * ((s + 1) * t + s);
    }

    public float getInterpolation(float t) {
        // a(t, s) = t * t * ((s + 1) * t - s)
        // o(t, s) = t * t * ((s + 1) * t + s)
        // f(t) = 0.5 * a(t * 2, tension * extraTension), when t < 0.5
        // f(t) = 0.5 * (o(t * 2 - 2, tension * extraTension) + 2), when t <= 1.0
        if (t < 0.5f) return 0.5f * a(t * 2.0f, mTension);
        else return 0.5f * (o(t * 2.0f - 2.0f, mTension) + 2.0f);
    }

    /** @hide */
    //@Override
    public long createNativeInterpolator() {
        return 0;
    }
}
