package r.android.widget;

import r.android.content.Context;
import r.android.widget.LinearLayout.LayoutParams;

public class ProgressBar extends com.ashera.view.BaseMeasurableView{
	public ProgressBar(com.ashera.widget.IWidget widget) {
		super(widget);
	}
	public ProgressBar(Context context) {
		super(null);
	}

	public void setLayoutParams(LayoutParams barLayoutParams) {
		
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
