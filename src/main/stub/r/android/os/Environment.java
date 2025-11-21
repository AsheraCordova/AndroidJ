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

import java.io.File;

public class Environment {

	public static final String MEDIA_MOUNTED = "MEDIA_MOUNTED";

	public static String getExternalStorageState() {
		return MEDIA_MOUNTED;
	}

	public static File getExternalStorageDirectory() {
		return com.ashera.widget.PluginInvoker.getExternalFilesDir(null);
	}

	public static boolean isExternalStorageEmulated() {
		return false;
	}

	public static Object getLegacyExternalStorageDirectory() {
		return null;
	}

}
