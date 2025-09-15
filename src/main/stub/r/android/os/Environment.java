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
