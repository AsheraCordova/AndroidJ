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

public class ListView extends AbsListView{
	private int widthMeasureSpec;
	private int heightMeasureSpec;
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.widthMeasureSpec = widthMeasureSpec;
		this.heightMeasureSpec = heightMeasureSpec;
		
	}

	public void measureChild(r.android.view.View child) {
		super.measureChild(child, widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	public void setAdapter(r.android.widget.ListAdapter adapter) {
		mAdapter = adapter;
		super.setAdapter(adapter);
	}
}
