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
package com.ashera.layout;
public class SwipeHelper {
		private final int swipedThreshold;

		private SwipeListener swipeListener;
		private float xDown, xUp;
		private float yDown, yUp;

		public SwipeHelper(int swipedThreshold) {
			this.swipedThreshold = swipedThreshold;
		}

		public void setListener(SwipeListener swipeListener) {
			this.swipeListener = swipeListener;
		}

		public int getSwipedThreshold() {
			return swipedThreshold;
		}


		public void onActionDown(float x, float y) {
			xDown = x;
			yDown = y;
		}

		public boolean onActionUp(float x, float y) {
			xUp = x;
			yUp = y;
			final boolean swipedHorizontally = Math.abs(xUp - xDown) > getSwipedThreshold();
			final boolean swipedVertically = Math.abs(yUp - yDown) > getSwipedThreshold();
			boolean isEventConsumed = false;

			if (swipedHorizontally) {
				final boolean swipedRight = xUp > xDown;
				final boolean swipedLeft = xUp < xDown;

				if (swipedRight) {
					isEventConsumed = swipeListener.onSwiped("right");
				}
				if (swipedLeft) {
					isEventConsumed |= swipeListener.onSwiped("left");
				}
			}

			if (swipedVertically) {
				final boolean swipedDown = yDown < yUp;
				final boolean swipedUp = yDown > yUp;
				if (swipedDown) {
					isEventConsumed |= swipeListener.onSwiped("down");
				}
				if (swipedUp) {
					isEventConsumed |= swipeListener.onSwiped("up");
				}
			}

			return isEventConsumed;
		}
		
		public static interface SwipeListener {
			boolean onSwiped(String direction);
		}


	}