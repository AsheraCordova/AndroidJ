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
package r.android.content.pm;

public class PackageManager {

	public static class NameNotFoundException extends RuntimeException{

	}

	public static final int PERMISSION_GRANTED = 0;
	public static final String GET_META_DATA = null;
	public ApplicationInfo getApplicationInfo(String packageName, String getMetaData) {
		return new ApplicationInfo();
	}
	public PackageInfo getPackageInfo(String packageName, int i) {
		return new PackageInfo();
	}
	public class PackageInfo {
		public int versionCode;
		
	}
}
