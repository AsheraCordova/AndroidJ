package r.android.webkit;


import r.android.content.Context;
import r.android.util.AttributeSet;
import r.android.view.KeyEvent;
import r.android.view.View;

public class WebView extends View{

	public WebView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
	}

	public void setWebViewClient(WebViewClient client) {
		// TODO Auto-generated method stub
		
	}

	public void setWebChromeClient(WebChromeClient client) {
		// TODO Auto-generated method stub
		
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setNetworkAvailable(boolean value) {
		// TODO Auto-generated method stub
		
	}

	public void setInitialScale(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setVerticalScrollBarEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public static void setWebContentsDebuggingEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	


	public WebSettings getSettings() {
		// TODO Auto-generated method stub
		return new WebSettings();
	}

	public void loadUrl(String url) {
		// TODO Auto-generated method stub
		
	}

	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public void clearCache(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void stopLoading() {
		// TODO Auto-generated method stub
		
	}

	public void clearHistory() {
		// TODO Auto-generated method stub
		
	}

	public boolean canGoBack() {
		// TODO Auto-generated method stub
		return false;
	}

	public void goBack() {
		// TODO Auto-generated method stub
		
	}

	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	public void pauseTimers() {
		// TODO Auto-generated method stub
		
	}

	public void onResume() {
		// TODO Auto-generated method stub
		
	}

	public void resumeTimers() {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	

	public void evaluateJavascript(String js, ValueCallback<String> callback) {
		// TODO Auto-generated method stub
		
	}

	public void addJavascriptInterface(Object exposedJsApi, String string) {
		// TODO Auto-generated method stub
		
	}


}
