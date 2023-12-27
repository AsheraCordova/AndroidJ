package r.android.view;

public class Choreographer {
	private static Choreographer choreographer = new Choreographer();
	public interface FrameCallback {
		public void doFrame(long frameTimeNanos);
	}

	public static final int CALLBACK_COMMIT = 0;

	public static Choreographer getInstance() {
		return choreographer;
	}

	public void postFrameCallbackDelayed(FrameCallback callback, long backgroundPauseDelay) {
		if (callback != null) {
			com.ashera.widget.PluginInvoker.postDelayed(() -> {
				callback.doFrame(getFrameTime());
			}, (int) backgroundPauseDelay);
		}
	}

	public void removeFrameCallback(FrameCallback mPauser) {
		System.out.println("removeFrameCallback");
	}

	public void postFrameCallback(FrameCallback callback) {
		postFrameCallbackDelayed(callback, 0);
	}

	public void postCallback(int callbackCommit, Runnable runnable, Object object) {
		System.out.println("postCallback");
	}

	public long getFrameTime() {
		return System.nanoTime()/1000000;
	}

	public static long getFrameDelay() {
		return 0;
	}

	public static void setFrameDelay(long delay) {
		
	}

}
