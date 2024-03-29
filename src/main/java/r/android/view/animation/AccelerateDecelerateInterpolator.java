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
//import r.android.graphics.animation.HasNativeInterpolator;
//import r.android.graphics.animation.NativeInterpolator;
//import r.android.graphics.animation.NativeInterpolatorFactory;
import r.android.util.AttributeSet;

/**
 * An interpolator where the rate of change starts and ends slowly but
 * accelerates through the middle.
 */
//@HasNativeInterpolator
public class AccelerateDecelerateInterpolator extends BaseInterpolator
        /*implements NativeInterpolator*/ {
    public AccelerateDecelerateInterpolator() {
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public AccelerateDecelerateInterpolator(Context context, AttributeSet attrs) {
    }

    public float getInterpolation(float input) {
        return (float)(Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }

    /** @hide */
    //@Override
    public long createNativeInterpolator() {
        return 0;
    }
}
