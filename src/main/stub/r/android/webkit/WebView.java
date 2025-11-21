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
package r.android.webkit;


import r.android.content.Context;
import r.android.util.AttributeSet;
import r.android.view.KeyEvent;

public class WebView extends r.android.view.ViewGroup{

	public WebView(Context context, AttributeSet attrs) {
	}
	
	public WebView() {
	}

	public void setWebViewClient(WebViewClient client) {
	}

	public void setWebChromeClient(WebChromeClient client) {
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		return false;
	}

	public void setNetworkAvailable(boolean value) {
	}

	public void setInitialScale(int i) {
	}

	public void setVerticalScrollBarEnabled(boolean b) {
	}

	public static void setWebContentsDebuggingEnabled(boolean b) {
	}

	public WebSettings getSettings() {
		return new WebSettings();
	}

	public void loadUrl(String url) {
	}

	public String getUrl() {
		return null;
	}

	public void clearCache(boolean b) {
	}

	public void stopLoading() {
	}

	public void clearHistory() {
	}

	public boolean canGoBack() {
		return false;
	}

	public void goBack() {
	}

	public void onPause() {
	}

	public void pauseTimers() {
	}

	public void onResume() {
	}

	public void resumeTimers() {
	}

	public void destroy() {
	}
	

	public void evaluateJavascript(String js, ValueCallback<String> callback) {
	}

	public void addJavascriptInterface(Object exposedJsApi, String string) {
	}
}
