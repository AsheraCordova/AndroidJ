package r.android.content.res;

import r.android.util.DisplayMetrics;

public class Resources {
	static DisplayMetrics displayMetric = new DisplayMetrics();
	public class NotFoundException extends RuntimeException {

	}

	public int getIdentifier(String string, String string2, String name) {
		return 1;
	}


	public String getResourceName(int id) throws NotFoundException{
		return "";
	}

	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return new Configuration();
	}


    public String getString(int resource, String text) {
    	if (resource == r.android.R.string.negative_duration) {
    		return String.format("-%s", text);
    	}
        return null;
    }


	public String getResourceEntryName(int id) throws r.android.content.res.Resources.NotFoundException{
		return null;
	}


	public DisplayMetrics getDisplayMetrics() {
		if (displayMetric.density == 0) {
			displayMetric.density = com.ashera.widget.PluginInvoker.getDisplayMetricDensity();
		}
		return displayMetric;
	}


	public CharSequence getString(int titleRes) {
		return null;
	}
	

}