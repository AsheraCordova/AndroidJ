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

public class VelocityTracker {
	public static final int VELOCITY_TRACKER_STRATEGY_DEFAULT = 0;

	public void addMovement(MotionEvent event) {
	}

	public static VelocityTracker obtain(int mVelocityTrackerStrategy) {
		return new VelocityTracker();
	}

	public float getYVelocity(int mActivePointerId) {
		return 0;
	}

	public float getXVelocity(int mActivePointerId) {
		return 0;
	}

	public void computeCurrentVelocity(int pixelsPerSecond, float swipeVelocityThreshold) {
	}

	public void recycle() {
	}

	public void clear() {
	}
}