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
