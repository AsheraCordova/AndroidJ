package r.android.webkit;

import r.android.content.Intent;
import r.android.net.Uri;
import r.android.view.View;
import r.android.webkit.GeolocationPermissions.Callback;
import r.android.webkit.WebStorage.QuotaUpdater;

public class WebChromeClient {

	public static class FileChooserParams {

		public static final String MODE_OPEN_MULTIPLE = null;

		public Intent createIntent() {
			// TODO Auto-generated method stub
			return null;
		}

		public static Uri[] parseResult(int resultCode, Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getMode() {
			// TODO Auto-generated method stub
			return null;
		}

		public String[] getAcceptTypes() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static class CustomViewCallback {

		public void onCustomViewHidden() {
			// TODO Auto-generated method stub
			
		}

	}

	public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onJsPrompt(WebView view, String origin, String message, String defaultValue, JsPromptResult result) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onConsoleMessage(String message, int lineNumber, String sourceID) {
		// TODO Auto-generated method stub
		
	}

	public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
		// TODO Auto-generated method stub
		
	}

	public void onShowCustomView(View view, CustomViewCallback callback) {
		// TODO Auto-generated method stub
		
	}

	public void onHideCustomView() {
		// TODO Auto-generated method stub
		
	}

	public View getVideoLoadingProgressView() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathsCallback,
			FileChooserParams fileChooserParams) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onPermissionRequest(PermissionRequest request) {
		// TODO Auto-generated method stub
		
	}

	public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize,
			long totalUsedQuota, QuotaUpdater quotaUpdater) {
		// TODO Auto-generated method stub
		
	}

}
