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
package r.android.content.res;

import r.android.util.DisplayMetrics;

public class Resources {
	static DisplayMetrics displayMetric = new DisplayMetrics();
	public static class Theme {
		
	}
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