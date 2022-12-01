package r.android.content.pm;

public class PackageManager {

	public static class NameNotFoundException extends RuntimeException{

	}

	public static final int PERMISSION_GRANTED = 0;
	public static final String GET_META_DATA = null;
	public ApplicationInfo getApplicationInfo(String packageName, String getMetaData) {
		return new ApplicationInfo();
	}
	public PackageInfo getPackageInfo(String packageName, int i) {
		return new PackageInfo();
	}
	public class PackageInfo {
		public int versionCode;
		
	}
}
