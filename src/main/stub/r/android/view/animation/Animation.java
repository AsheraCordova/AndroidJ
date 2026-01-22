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
package r.android.view.animation;

import r.android.animation.Animator;
import r.android.animation.Animator.AnimatorListener;
import r.android.animation.ValueAnimator;
import r.android.view.View;

public class Animation {
	private ValueAnimator animator;
	private boolean fillAfter;
	protected Transformation transformation;
	private View target;

	public void applyTransformation(float interpolatedTime, Transformation t) {

	}

	public static interface AnimationListener {
		public void onAnimationStart(Animation animation);

		public void onAnimationEnd(Animation animation);

		public void onAnimationRepeat(Animation animation);
	}

	public Animation() {
		animator = createValueAnimator();
	}

	private ValueAnimator createValueAnimator() {
		ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
		animator.addUpdateListener(animation -> {
			float fraction = animation.getAnimatedFraction();
			applyTransformation(fraction, transformation);
		});

		return animator;
	}

	public static final int RELATIVE_TO_SELF = 0;

	public void reset() {
		if (animator != null) {
			animator.cancel();
		}
		animator = createValueAnimator();
	}

	public void setInterpolator(Interpolator interpolator) {
		animator.setInterpolator(interpolator);
	}

	public void setAnimationListener(AnimationListener listener) {
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				if (listener != null) {
					listener.onAnimationStart(Animation.this);
				}
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (listener != null) {
					listener.onAnimationEnd(Animation.this);
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				if (listener != null) {
					listener.onAnimationRepeat(Animation.this);
				}
			}
		});
	}

	public void setDuration(int duration) {
		animator.setDuration(duration);
	}

	public void setFillAfter(boolean fillAfter) {
		this.fillAfter = fillAfter;
	}

	public void start() {
		animator.start();
	}

	public void setTarget(View target) {
		this.target = target;
	}

	public View getTarget() {
		return target;
	}
}
