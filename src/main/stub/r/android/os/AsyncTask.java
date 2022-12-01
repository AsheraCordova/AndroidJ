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
