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

public class RotateAnimation extends Animation {

	private float fromDegrees;
	private float toDegrees;
	private int pivotXType;
	private float pivotXValue;
	private int pivotYType;
	private float pivotYValue;

	public RotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType,
			float pivotYValue) {
		super();
		this.fromDegrees = fromDegrees;
		this.toDegrees = toDegrees;
		this.pivotXType = pivotXType;
		this.pivotXValue = pivotXValue;
		this.pivotYType = pivotYType;
		this.pivotYValue = pivotYValue;
	}

	@Override
	public void applyTransformation(float fraction, r.android.view.animation.Transformation t) {
		float rotation = fromDegrees + (toDegrees - fromDegrees) * fraction;
//		getTarget().setMyAttribute("transformPivotX", pivotXValue);
//		getTarget().setMyAttribute("transformPivotY", pivotYValue);
		getTarget().setMyAttribute("rotation", rotation);

	}

}
