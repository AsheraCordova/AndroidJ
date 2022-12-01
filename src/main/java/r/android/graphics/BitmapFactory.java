package r.android.graphics;

import java.io.InputStream;

public class BitmapFactory {
	public static class Options {
		public int outHeight;
		public int outWidth;
		public boolean inJustDecodeBounds;
		public int inSampleSize;

	}

	public static Bitmap decodeStream(InputStream stream, Object object, Options options) {
		return (Bitmap) com.ashera.widget.PluginInvoker.decodeBitmapStream(stream, options);
	}
}
