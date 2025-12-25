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
	public static float getScrollFriction() {
		return SCROLL_FRICTION;
	}
	public int getScaledTouchSlop() {
		return TOUCH_SLOP;
	}
	public static ViewConfiguration get(Context context) {
		return new ViewConfiguration();
	}
	public float getScaledMaximumFlingVelocity() {
		return MAXIMUM_FLING_VELOCITY;
	}
	public float getScaledMinimumFlingVelocity() {
		return MINIMUM_FLING_VELOCITY;
	}

}
