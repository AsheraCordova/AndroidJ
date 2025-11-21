//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
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
