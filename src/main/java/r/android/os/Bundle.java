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
package r.android.os;

import java.util.HashMap;

public class Bundle {
	private HashMap<String, Object> mExtras;
	public Bundle(Bundle data) {
	}
	public Bundle() {
	}

	public void putSerializable(String name, Object value) {
        putObject(name, value);
    }


	public void putObject(String name, Object value) {
		if (mExtras == null) {
            mExtras = new HashMap<String, Object>();
        }
        mExtras.put(name, value);
	} 
	
	
	public Object get(String key) {
		if (mExtras == null) {
			return null;
		}
		return mExtras.get(key);
	}
	
	public Bundle getBundle(String name) {
		return (Bundle) get(name);
	}



	public void putInt(String name, int value) {
		putObject(name, value);
		
	}
	public void putString(String name, String value) {
		putObject(name, value);
		
	}

	public void putBundle(String name, String value) {
		putObject(name, value);
		
	}

	public String getString(String name) {
		return (String) get(name);
	}


	public void putBundle(String name, Bundle onSaveInstanceState) {
		
	}


	public Object getSerializable(String key) {
		return get(key);
	}


	public int getInt(String key) {
		Object object = get(key);
		return object == null ? 0 : (int) object;
	}

	public Object clone() {
		return null;
	}
	public void putFloat(String name, float value) {
		putObject(name, value);
	}
	public float getFloat(String key) {
		Object object = get(key);
		return object == null ? 0 : (float) object;
	}

}
