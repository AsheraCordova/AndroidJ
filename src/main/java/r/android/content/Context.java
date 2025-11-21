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
package r.android.content;

import java.io.File;

import r.android.content.pm.PackageManager;
import r.android.content.res.AssetManager;
import r.android.content.res.Resources;
import r.android.graphics.drawable.Drawable;

public class Context {
	public static final int MODE_PRIVATE = 0;
	public static final Class ACTIVITY_SERVICE = r.android.app.ActivityManager.class;
	public static final Class WINDOW_SERVICE = r.android.view.WindowManager.class;
	public r.android.content.pm.ApplicationInfo getApplicationInfo() {
		return new r.android.content.pm.ApplicationInfo();
	}

	public Resources getResources() {
		return new Resources();
	}
	public String getPackageName() {
		// TODO Auto-generated method stub
		return "";
	}
	public void registerReceiver(BroadcastReceiver telephonyReceiver, IntentFilter intentFilter) {
		// TODO Auto-generated method stub
		
	}
	public void unregisterReceiver(BroadcastReceiver telephonyReceiver) {
		// TODO Auto-generated method stub
		
	}
	public ContentResolver getContentResolver() {
		// TODO Auto-generated method stub
		return null;
	}
	public AssetManager getAssets() {
		// TODO Auto-generated method stub
		return null;
	}
	public Context getApplicationContext() {
		// TODO Auto-generated method stub
		return this;
	}
	public File getDir(String string, int modePrivate) {
		// TODO Auto-generated method stub
		return new File(".");
	}

	public Drawable getDrawable(int resId) {
		return null;
	}

	public <T> T getSystemService(Class<T> class1) {
		if (class1 == r.android.app.ActivityManager.class) {
			return (T) new r.android.app.ActivityManager(this);
		}
		return null;
	}

	public File getExternalFilesDir(Object object) {
		return com.ashera.widget.PluginInvoker.getExternalFilesDir(this);
	}

	public File getCacheDir() {
		return com.ashera.widget.PluginInvoker.getExternalFilesDir(this);
	}

	public PackageManager getPackageManager() {
		return new PackageManager();
	}

	public CharSequence getText(int hintId) {
		return null;
	}

}
