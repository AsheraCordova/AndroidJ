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
 * Repeats the animation for a specified number of cycles. The
 * rate of change follows a sinusoidal pattern.
 *
 */
//@HasNativeInterpolator
public class CycleInterpolator extends BaseInterpolator /*implements NativeInterpolator*/ {
    public CycleInterpolator(float cycles) {
        mCycles = cycles;
    }

    //

    /** @hide */
    public float getInterpolation(float input) {
        return (float)(Math.sin(2 * mCycles * Math.PI * input));
    }

    private float mCycles;

    /** @hide */
    //@Override
    public long createNativeInterpolator() {
        return 0;
    }
}
