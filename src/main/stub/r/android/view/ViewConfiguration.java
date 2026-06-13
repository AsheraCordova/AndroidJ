//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
package r.android.view;

import r.android.content.Context;

public class ViewConfiguration {
	private static final float SCROLL_FRICTION = 0.015f;
	private static final int MAXIMUM_FLING_VELOCITY = 8000;
	private static final int MINIMUM_FLING_VELOCITY = 50;
	private static final int TOUCH_SLOP = 8;
	private static final int TAP_TIMEOUT = 100;
	private static final int PRESSED_STATE_DURATION = 64;
	public static final int DEFAULT_LONG_PRESS_TIMEOUT = 400;
	private static final int DOUBLE_TAP_TIMEOUT = 300;
	private static final int DOUBLE_TAP_MIN_TIME = 40;
	private static final int DOUBLE_TAP_SLOP = 100;
	private static final int DOUBLE_TAP_TOUCH_SLOP = TOUCH_SLOP;
	private static final float AMBIGUOUS_GESTURE_MULTIPLIER = 2f;
	public static float getScrollFriction() {
		return SCROLL_FRICTION;
	}
	public int getScaledTouchSlop() {
		return TOUCH_SLOP;
	}
	public static ViewConfiguration get(Context context) {
		return new ViewConfiguration();
	}
	public int getScaledMaximumFlingVelocity() {
		return MAXIMUM_FLING_VELOCITY;
	}
	public int getScaledMinimumFlingVelocity() {
		return MINIMUM_FLING_VELOCITY;
	}
	public static int getTapTimeout() {
		return TAP_TIMEOUT;
	}
	public static int getLongPressTimeout() {
		return DEFAULT_LONG_PRESS_TIMEOUT;
	}
	public static long getPressedStateDuration() {
		return PRESSED_STATE_DURATION;
	}
	public static int getDoubleTapTimeout() {
		return DOUBLE_TAP_TIMEOUT;
	}
	public static int getDoubleTapMinTime() {
		return DOUBLE_TAP_MIN_TIME;
	}
	public static int getTouchSlop() {
		return TOUCH_SLOP;
	}
	public static int getDoubleTapSlop() {
		return DOUBLE_TAP_SLOP;
	}
	public static int getMinimumFlingVelocity() {
		return MINIMUM_FLING_VELOCITY;
	}
	public static int getMaximumFlingVelocity() {
		return MAXIMUM_FLING_VELOCITY;
	}
	public static float getAmbiguousGestureMultiplier() {
		return AMBIGUOUS_GESTURE_MULTIPLIER;
	}
	public int getScaledDoubleTapTouchSlop() {
		return DOUBLE_TAP_TOUCH_SLOP;
	}
	public int getScaledDoubleTapSlop() {
		return DOUBLE_TAP_TOUCH_SLOP;
	}
	public float getScaledAmbiguousGestureMultiplier() {
		return AMBIGUOUS_GESTURE_MULTIPLIER;
	}

}
