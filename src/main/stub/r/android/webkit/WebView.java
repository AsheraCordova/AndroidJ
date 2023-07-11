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
