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
