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
		throw new RuntimeException("this should never be called");
	}

	public PackageManager getPackageManager() {
		return new PackageManager();
	}

}
