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
/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package r.android.os;

import r.android.annotation.NonNull;
import r.android.annotation.Nullable;
import r.android.compat.annotation.UnsupportedAppUsage;

public final class Looper {
   /*
     * API Implementation Note:
     *
     * This class contains the code required to set up and manage an event loop
     * based on MessageQueue.  APIs that affect the state of the queue should be
     * defined on MessageQueue or Handler rather than on Looper itself.  For example,
     * idle handlers and sync barriers are defined on the queue whereas preparing the
     * thread, looping, and quitting are defined on the looper.
     */
    @UnsupportedAppUsage
    private static Looper sMainLooper = new Looper(true);  // guarded by Looper.class
    @UnsupportedAppUsage
    final MessageQueue mQueue;

   /** Initialize the current thread as a looper.
      * This gives you a chance to create handlers that then reference
      * this looper, before actually starting the loop. Be sure to call
      * {@link #loop()} after calling this method, and end it by calling
      * {@link #quit()}.
      */
    public static void prepare() {
    }

   /**
     * Initialize the current thread as a looper, marking it as an
     * application's main looper. See also: {@link #prepare()}
     *
     * @deprecated The main looper for your application is created by the Android environment,
     *   so you should never need to call this function yourself.
     */
    //@Deprecated
    public static void prepareMainLooper() {
    }

   /**
     * Returns the application's main looper, which lives in the main thread of the application.
     */
    public static Looper getMainLooper() {
        synchronized (Looper.class) {
            return sMainLooper;
        }
    }


   /**
     * Run the message queue in this thread. Be sure to call
     * {@link #quit()} to end the loop.
     */
    public static void loop() {
    }

   /**
     * Return the Looper object associated with the current thread.  Returns
     * null if the calling thread is not associated with a Looper.
     */
    public static @Nullable Looper myLooper() {
        return new Looper(false);
    }

   /**
     * Return the {@link MessageQueue} object associated with the current
     * thread.  This must be called from a thread running a Looper, or a
     * NullPointerException will be thrown.
     */
    public static @NonNull MessageQueue myQueue() {
        return myLooper().mQueue;
    }

    private Looper(boolean quitAllowed) {
        mQueue = new MessageQueue(quitAllowed);
    }

   /**
     * Returns true if the current thread is this looper's thread.
     */
    public boolean isCurrentThread() {
        return true;
    }

    public void quit() {
        mQueue.quit(false);
    }

   /**
     * Quits the looper safely.
     * <p>
     * Causes the {@link #loop} method to terminate as soon as all remaining messages
     * in the message queue that are already due to be delivered have been handled.
     * However pending delayed messages with due times in the future will not be
     * delivered before the loop terminates.
     * </p><p>
     * Any attempt to post messages to the queue after the looper is asked to quit will fail.
     * For example, the {@link Handler#sendMessage(Message)} method will return false.
     * </p>
     */
    public void quitSafely() {
        mQueue.quit(true);
    }

   /**
     * Gets this looper's message queue.
     *
     * @return The looper's message queue.
     */
    public @NonNull MessageQueue getQueue() {
        return mQueue;
    }

	public Thread getThread() {
		return Thread.currentThread();
	}
}
