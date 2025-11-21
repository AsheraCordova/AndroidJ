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
package r.android.os;

import r.android.graphics.RectF;

public interface Parcelable {
	public class Creator<T> {

		public T createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return null;
		}

		public T[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}

	}
	int describeContents();
	void writeToParcel(Parcel out, int flags);
}
