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