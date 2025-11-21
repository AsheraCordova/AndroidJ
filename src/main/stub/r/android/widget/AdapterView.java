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
package r.android.widget;

import r.android.view.View;

public class AdapterView<T> extends FrameLayout {

	public static final int ITEM_VIEW_TYPE_IGNORE = 0;
	public static final int INVALID_POSITION = -1;
	public boolean performItemClick(View view, int position, long id) {
		return false;
	}
    public interface OnItemLongClickListener {
    	public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id);
	}

	public interface OnItemClickListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }
}
