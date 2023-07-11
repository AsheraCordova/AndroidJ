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