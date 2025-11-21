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
package com.ashera.drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ashera.core.IFragment;
import com.ashera.widget.PluginInvoker;

import r.android.graphics.drawable.Drawable;
import r.android.graphics.drawable.StateListDrawable;

public class DrawableFactory {
	private final static String[] DRAWABLE_STATE_LIST = {"@android:state_window_focused", "@android:state_selected", 
			"@android:state_focused", "@android:state_pressed", 
			"@android:state_hovered", "@android:state_activated", 
			"@android:state_accelerated", "@android:state_enabled", 
			"@android:state_drag_can_accept", "@android:state_drag_hovered", "@android:state_checked"};

	private final static int[] DRAWABLE_STATE_LIST_ATTR = {
			r.android.R.attr.state_window_focused, r.android.R.attr.state_selected, 
			r.android.R.attr.state_focused, r.android.R.attr.state_pressed, 
			r.android.R.attr.state_hovered, r.android.R.attr.state_activated,
			r.android.R.attr.state_accelerated, r.android.R.attr.state_enabled, 
			r.android.R.attr.state_drag_can_accept, r.android.R.attr.state_drag_hovered, r.android.R.attr.state_checked};

	public static Drawable getDrawable(String converterKey, Map<String, Object> drawableMap, Map<String, Object> dependentAttributesMap, IFragment fragment) {
		if (drawableMap.containsKey("shape")) {
			r.android.graphics.drawable.ShapeDrawable shapeDrawable = (r.android.graphics.drawable.ShapeDrawable)PluginInvoker.createDrawable("shape");
			createShapeDrawable(drawableMap, dependentAttributesMap, fragment, shapeDrawable);
			return shapeDrawable;
		}
		else if (drawableMap.containsKey("selector")) {
			StateListDrawable stateListDrawable = new StateListDrawable();
			Map<String, Object> selector = PluginInvoker.getMap(drawableMap.get("selector"));
			Object payLoad = selector.get("item");
			List<Object> items = PluginInvoker.getList(payLoad);
			
			if (items == null && payLoad != null) {
				Object item = PluginInvoker.getMap(payLoad);
				addStateListDrawable(converterKey, dependentAttributesMap, fragment, stateListDrawable, item);
			} else {
				for (Object item : items) {
					addStateListDrawable(converterKey, dependentAttributesMap, fragment, stateListDrawable, item);
				}
			}
			
			return stateListDrawable;
		}
		
		throw new RuntimeException("unknown drawable");
	}

	private static void addStateListDrawable(String converterKey, Map<String, Object> dependentAttributesMap,
			IFragment fragment, StateListDrawable stateListDrawable, Object item) {
		Map<String,Object> itemMap = PluginInvoker.getMap(item);
		List<Integer> stateList = new ArrayList<Integer>();
		
		for (int i = 0; i < DRAWABLE_STATE_LIST.length; i++) {
			String state = DRAWABLE_STATE_LIST[i];
			int attrState = DRAWABLE_STATE_LIST_ATTR[i];
			Object attrValue = itemMap.get(state);
			if (attrValue != null) {
				Boolean attrValueBool = PluginInvoker.getBoolean(attrValue);
				if (!attrValueBool.equals(Boolean.TRUE)) {
					 attrState = attrState * -1;
				}
				
				stateList.add(attrState);
			}
		}

		String drawableRes = (String) itemMap.get("@android:drawable");
		Object image = com.ashera.widget.PluginInvoker.convertFrom(com.ashera.widget.PluginInvoker.getConverter(converterKey), dependentAttributesMap, drawableRes, fragment);
		Drawable drawable;
		if (image instanceof Drawable) {
			drawable = (Drawable) image;
		} else {
			drawable = new Drawable();
			drawable.setDrawable(image);
		}
		int[] states = StateListDrawable.WILD_CARD;
		if (stateList.size() > 0) {
			states = stateList.stream().mapToInt((i) -> i).toArray();
		}
		stateListDrawable.addState(states, drawable);
	}

	private static void createShapeDrawable(Map<String, Object> drawableMap, Map<String, Object> dependentAttributesMap,
			IFragment fragment, r.android.graphics.drawable.ShapeDrawable shapeDrawable) {
		Map<String, Object> shape = PluginInvoker.getMap(drawableMap.get("shape"));
		if (shape.containsKey("@android:shape")) {
			String shapeType = PluginInvoker.getString(shape.get("@android:shape"));
			shapeDrawable.setType(shapeType);
		}
		
		if (shape.containsKey("size")) {
			Map<String, Object> size = PluginInvoker.getMap(shape.get("size"));
			if (size.containsKey("@android:width")) {
				Object value = size.get("@android:width");
				int width = dimensionConvert(value, dependentAttributesMap, fragment);
				shapeDrawable.setWidth(width);
			}
			
			if (size.containsKey("@android:height")) {
				Object value = size.get("@android:height");
				int height = dimensionConvert(value, dependentAttributesMap, fragment);
				shapeDrawable.setHeight(height);
			}
			
			shapeDrawable.setBounds(0, 0, shapeDrawable.getWidth(), shapeDrawable.getHeight());
		}
		
		if (shape.containsKey("stroke")) {
			Map<String, Object> stroke = PluginInvoker.getMap(shape.get("stroke"));
			if (stroke.containsKey("@android:width")) {
				Object value = stroke.get("@android:width");
				int width = dimensionConvert(value, dependentAttributesMap, fragment);
				shapeDrawable.setStrokeWidth(width);
			}
			
			if (stroke.containsKey("@android:height")) {
				Object value = stroke.get("@android:height");
				int height = dimensionConvert(value, dependentAttributesMap, fragment);
				shapeDrawable.setStrokeHeight(height);
			}
			
			if (stroke.containsKey("@android:color")) {
				String value = (String) stroke.get("@android:color");
				Object color = colorConvert(value, dependentAttributesMap, fragment);
				shapeDrawable.setStrokeColor(color);
			}
		}
	}

	private static Object colorConvert(Object value, Map<String, Object> dependentAttributesMap, IFragment fragment) {
		return com.ashera.widget.PluginInvoker.convertFrom(com.ashera.widget.PluginInvoker.getConverter("color"), 
				dependentAttributesMap, value, fragment);
	}
	private static int dimensionConvert(Object value, Map<String, Object> dependentAttributesMap, IFragment fragment) {
		return (int) com.ashera.widget.PluginInvoker.convertFrom(com.ashera.widget.PluginInvoker.getConverter(com.ashera.converter.CommonConverters.dimension), 
				dependentAttributesMap, value, fragment);
	}
}
