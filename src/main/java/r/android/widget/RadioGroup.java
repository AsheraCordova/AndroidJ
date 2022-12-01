package r.android.widget;

public class RadioGroup extends LinearLayout{

	public interface OnCheckedChangeListener {
		public void onCheckedChanged(RadioGroup group, int checkedId);
	}
	
	public RadioGroup() {
		setOrientation(1);
	}

	public void setOnCheckedChangeListener(
			OnCheckedChangeListener onCheckedChangeListener) {
		
	}

}
