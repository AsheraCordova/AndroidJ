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
