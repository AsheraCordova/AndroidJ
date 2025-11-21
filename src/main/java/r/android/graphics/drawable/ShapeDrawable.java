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
package r.android.graphics.drawable;

public class ShapeDrawable extends Drawable{
	private String type;
	private Stroke stroke = new Stroke();
	private Size size = new Size();
	class Stroke {
		int width = -1;
		int height = -1;
		Object color;
		int dashWidth = -1;
		int dashGap = -1;
	}
	class Size {
		int height = -1;
		int width = -1;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStrokeWidth() {
		return stroke.width;
	}
	public void setStrokeWidth(int width) {
		stroke.width = width;
	}
	public int getStrokeHeight() {
		return stroke.height;
	}
	public void setStrokeHeight(int height) {
		stroke.height = height;
	}
	public Object getStrokeColor() {
		return stroke.color;
	}
	public void setStrokeColor(Object color) {
		stroke.color = color;
	}
	public int getStrokeDashWidth() {
		return stroke.dashWidth;
	}
	public void setStrokDashWidth(int dashWidth) {
		stroke.dashWidth = dashWidth;
	}
	public int getStrokeDashGap() {
		return stroke.dashGap;
	}
	public void setStrokDashGap(int dashGap) {
		stroke.dashGap = dashGap;
	}
	
	public int getHeight() {
		return size.height;
	}
	public void setHeight(int height) {
		size.height = height;
	}
	public int getWidth() {
		return size.width;
	}
	public void setWidth(int width) {
		size.width = width;
	}	
	
	@Override
	public int getMinimumWidth() {
		return Math.max(super.getMinimumWidth(), size.width);
	}
	
	@Override
	public int getMinimumHeight() {
		return Math.max(super.getMinimumHeight(), size.height);
	}
	
	@Override
	public void invalidateSelf() {
		super.invalidateSelf();
		setRedraw(true);
	}
}
