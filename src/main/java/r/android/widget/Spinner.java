package r.android.widget;

import com.ashera.widget.IWidget;

public abstract class Spinner extends TextView{

	public Spinner(IWidget widget) {
		super(widget);
	}
	
	@Override
	public String getText() {
		throw new RuntimeException("cannot be called by view.");
	}


	@Override
	public int computeSize(float width) {
		throw new RuntimeException("cannot be called by view.");
	}


}
