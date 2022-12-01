package r.android.content;

import java.io.Serializable;
import java.util.HashMap;

import r.android.net.Uri;
import r.android.os.Bundle;

public class Intent {
	public static final int ACTION_VIEW = 0;
	public static final int CATEGORY_BROWSABLE = 0;
	public static final int ACTION_GET_CONTENT = 0;
	public static final int CATEGORY_OPENABLE = 0;
    public static final String ACTION_CONFIGURATION_CHANGED = "android.intent.action.CONFIGURATION_CHANGED";
	
	/**
     * If set, the recipient of this Intent will be granted permission to
     * perform read operations on the URI in the Intent's data and any URIs
     * specified in its ClipData.  When applying to an Intent's ClipData,
     * all URIs as well as recursive traversals through data or other ClipData
     * in Intent items will be granted; only the grant flags of the top-level
     * Intent are used.
     */
    public static final int FLAG_GRANT_READ_URI_PERMISSION = 0x00000001;
    /**
     * If set, the recipient of this Intent will be granted permission to
     * perform write operations on the URI in the Intent's data and any URIs
     * specified in its ClipData.  When applying to an Intent's ClipData,
     * all URIs as well as recursive traversals through data or other ClipData
     * in Intent items will be granted; only the grant flags of the top-level
     * Intent are used.
     */
    public static final int FLAG_GRANT_WRITE_URI_PERMISSION = 0x00000002;
    /**
     * Can be set by the caller to indicate that this Intent is coming from
     * a background operation, not from direct user interaction.
     */
    public static final int FLAG_FROM_BACKGROUND = 0x00000004;
    
    /**
     * When combined with {@link #FLAG_GRANT_READ_URI_PERMISSION} and/or
     * {@link #FLAG_GRANT_WRITE_URI_PERMISSION}, the URI permission grant can be
     * persisted across device reboots until explicitly revoked with
     * {@link Context#revokeUriPermission(Uri, int)}. This flag only offers the
     * grant for possible persisting; the receiving application must call
     * {@link ContentResolver#takePersistableUriPermission(Uri, int)} to
     * actually persist.
     *
     * @see ContentResolver#takePersistableUriPermission(Uri, int)
     * @see ContentResolver#releasePersistableUriPermission(Uri, int)
     * @see ContentResolver#getPersistedUriPermissions()
     * @see ContentResolver#getOutgoingPersistedUriPermissions()
     */
    public static final int FLAG_GRANT_PERSISTABLE_URI_PERMISSION = 0x00000040;
    /**
     * When combined with {@link #FLAG_GRANT_READ_URI_PERMISSION} and/or
     * {@link #FLAG_GRANT_WRITE_URI_PERMISSION}, the URI permission grant
     * applies to any URI that is a prefix match against the original granted
     * URI. (Without this flag, the URI must match exactly for access to be
     * granted.) Another URI is considered a prefix match only when scheme,
     * authority, and all path segments defined by the prefix are an exact
     * match.
     */
    public static final int FLAG_GRANT_PREFIX_URI_PERMISSION = 0x00000080;
    public static final String EXTRA_ALLOW_MULTIPLE =
            "android.intent.extra.ALLOW_MULTIPLE";
    public static final String EXTRA_MIME_TYPES = "android.intent.extra.MIME_TYPES";
	private HashMap<String, Object> mExtras;

	public Intent(int actionView) {
		// TODO Auto-generated constructor stub
	}

	public Intent() {
		// TODO Auto-generated constructor stub
	}

	public Intent putExtra(String name, Object value) {
        if (mExtras == null) {
            mExtras = new HashMap<String, Object>();
        }
        mExtras.put(name, value);
        return this;
    }

	public Serializable getSerializableExtra(
			String localIntentOptionKey) {
		if (mExtras == null) {
			return null;
		}
		return (Serializable) mExtras.get(localIntentOptionKey);
	}
	

	public Object getExtra(
			String localIntentOptionKey) {
		if (mExtras == null) {
			return null;
		}
		return mExtras.get(localIntentOptionKey);
	}

	public String getStringExtra(String localDataKey) {
		if (mExtras == null) {
			return null;
		}
		Object object = mExtras.get(localDataKey);
		
		if (object instanceof String) {
			return  (String) object;
		}
		return null;
	}

	public Bundle getExtras() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAction() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasExtra(String extraState) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addCategory(int categoryBrowsable) {
		// TODO Auto-generated method stub
		
	}

	public void setDataAndType(Uri uri, String mimeType) {
		// TODO Auto-generated method stub
		
	}

	public void setData(Uri uri) {
		// TODO Auto-generated method stub
		
	}

	public void setType(String string) {
		// TODO Auto-generated method stub
		
	}

	public Uri getData() {
		// TODO Auto-generated method stub
		return null;
	}

	public ClipData getClipData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class ClipData {

		public int getItemCount() {
			return 0;
		}

		public Item getItemAt(int i) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public class Item {

		public Uri getUri() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
