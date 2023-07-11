package r.android.view;

import r.android.os.IBinder;

public interface WindowManager {

	public static class LayoutParams extends r.android.widget.RelativeLayout.LayoutParams{
        public static final int PRIVATE_FLAG_WILL_NOT_REPLACE_ON_RELAUNCH = 0x00008000;
        public static final int PRIVATE_FLAG_LAYOUT_CHILD_WINDOW_IN_PARENT_FRAME = 0x00010000;
		public Object accessibilityIdOfAnchor;
		public String accessibilityTitle;
		public String packageName;
		public int flags;
		public int type;
		public IBinder token;
        public int format;
        public int windowAnimations;
		public int softInputMode;
		public int privateFlags;
		public LayoutParams(int w, int h) {
			super(w, h);
		}
		
		public LayoutParams() {
			super(0, 0);
		}
		public static final int FLAG_FULLSCREEN = 0;
		public static final int FLAG_FORCE_NOT_FULLSCREEN = 0;
		public static final int SOFT_INPUT_STATE_UNCHANGED = 0;
		public static final int TYPE_APPLICATION_PANEL = 0;
		public void setSurfaceInsets(View mBackgroundView, boolean b, boolean c) {
		}

		public void setTitle(String string) {
			
		}
		
	}

	public void addView(View decorView, LayoutParams p);

}
