package r.android.widget;

import r.android.content.Context;
import r.android.content.res.ColorStateList;
import r.android.view.KeyEvent;

public class EditText extends TextView {

	public EditText(Context context) {
		super(null);
	}
	
	public EditText(com.ashera.widget.IWidget widget) {
		super(widget);
	}
	
	public EditText() {
		super(null);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}


	@Override
	public String getText() {
		throw new RuntimeException("cannot be called by view.");
	}

	@Override
	public int computeSize(float width) {
		throw new RuntimeException("cannot be called by view.");
	}

	@Override
	public int nativeMeasureWidth(Object uiView) {
		return 0;
	}

	@Override
	public int nativeMeasureHeight(Object uiView, int width) {
		return 0;
	}

}
