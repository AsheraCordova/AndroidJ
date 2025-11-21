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

import r.android.graphics.Bitmap;
import r.android.net.http.SslError;

public class WebViewClient {

	public static final int ERROR_HOST_LOOKUP = 0;
	public static final int ERROR_UNSUPPORTED_SCHEME = 0;

	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
		// TODO Auto-generated method stub
		
	}

	public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
		// TODO Auto-generated method stub
		
	}

	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		
	}

	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		
	}

	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		// TODO Auto-generated method stub
		
	}

	public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
		// TODO Auto-generated method stub
		
	}

	public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
		// TODO Auto-generated method stub
		return null;
	}

}
