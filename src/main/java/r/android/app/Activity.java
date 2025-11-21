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
package r.android.app;

import r.android.content.Context;
import r.android.content.Intent;
import r.android.content.pm.PackageManager;
import r.android.content.res.Configuration;
import r.android.os.Bundle;
import r.android.view.Menu;
import r.android.view.MenuItem;
import r.android.view.View;
import r.android.view.Window;

public class Activity extends Context{
	public static final int RESULT_CANCELED = 0;
	public static final int RESULT_OK = 0;
	private Intent intent = new Intent();

	public Intent getIntent() {
		return intent;
	}
	public void runOnUiThread(Runnable runnable) {
		com.ashera.widget.PluginInvoker.runOnMainThread(runnable);
	}
	public void startActivityForResult(Intent intent2, int requestCode) {
		// TODO Auto-generated method stub
		
	}
	public void finish() {
		// TODO Auto-generated method stub
		
	}
	public void requestPermissions(String[] permissions, int mappedRequestCode) {
		// TODO Auto-generated method stub
		
	}
	public int checkSelfPermission(String permission) {
		// TODO Auto-generated method stub
		return 0;
	}
	public void startActivity(Intent intent2) throws r.android.content.ActivityNotFoundException {
		// TODO Auto-generated method stub
		
	}
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}
	public r.android.view.Window getWindow() {
		// TODO Auto-generated method stub
		return new Window();
	}
	public void setVolumeControlStream(int streamMusic) {
		// TODO Auto-generated method stub
		
	}
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		
	}
	protected void onNewIntent(Intent intent2) {
		// TODO Auto-generated method stub
		
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		
	}
	protected void onStop() {
		// TODO Auto-generated method stub
		
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		
	}
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		
	}
	public void startActivityForResult(Intent intent2, int requestCode, Bundle options) {
		// TODO Auto-generated method stub
		
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent intent2) {
		// TODO Auto-generated method stub
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		
	}
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		// TODO Auto-generated method stub
		
	}
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		
	}
	public PackageManager getPackageManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
