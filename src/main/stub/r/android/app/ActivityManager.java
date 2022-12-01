package r.android.app;

public class ActivityManager {
	private r.android.content.Context context;
	public ActivityManager(r.android.content.Context context) {
		this.context = context;
	}
	public int getMemoryClass() {
		return com.ashera.widget.PluginInvoker.getMaxMemory(context);
	}

}
