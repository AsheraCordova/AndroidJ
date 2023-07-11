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

}
