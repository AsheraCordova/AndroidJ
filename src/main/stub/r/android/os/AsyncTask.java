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
package r.android.os;

public abstract class AsyncTask<Params, Progress, Result> {
	private boolean cancel;
	protected abstract Result doInBackground(Params... var1);
	protected void onPostExecute(Result result) {
    }
	

	public final AsyncTask<Params, Progress, Result> execute(Params... params) {
		new Thread(() -> {
			Result result = doInBackground(params);
			com.ashera.widget.PluginInvoker.runOnMainThread(() -> {
				onPostExecute(result);	
			});
						
		}).start();
		return this;
	}
	public void cancel(boolean cancel) {
		this.cancel = cancel;
	}
	
	public boolean isCancelled() {
		return cancel;
	}
}
