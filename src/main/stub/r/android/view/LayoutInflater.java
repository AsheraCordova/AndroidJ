package r.android.view;

import r.android.content.Context;

public class LayoutInflater {
	public static LayoutInflater from(Context context) {
		return new LayoutInflater();
	}
	
	public View inflate(int layout, ViewGroup parent, boolean b) {
		// resource based inflation is not supported
		return null;
	}

	public View inflate(String layout, ViewGroup parent, boolean b) {
		return parent.inflateView(layout);
	}

	public static void recurseSet(ViewGroup parent, r.android.view.View.OnClickListener onClickListener) {
		if (com.ashera.widget.PluginInvoker.getOS().equalsIgnoreCase("swt")) {
			for (int i = 0; i < parent.getChildCount(); i++) {
				View child = parent.getChildAt(i);
				child.setMyAttribute("onClick", onClickListener);
				if (child instanceof ViewGroup) {
					recurseSet((ViewGroup) child, onClickListener);
				}
			}
		}
	}
}