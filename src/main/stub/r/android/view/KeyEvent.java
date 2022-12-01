package r.android.view;

public class KeyEvent {

	public static final int KEYCODE_BACK = 0;
	public static final int KEYCODE_VOLUME_UP = 1;
	public static final int KEYCODE_VOLUME_DOWN = 2;
	public static final int KEYCODE_MENU = 3;
	public static final int ACTION_DOWN = 1;
	public static final int ACTION_UP = 0;
	public static final int KEYCODE_SEARCH = 4;
	private int action;
	private int keyCode;

	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public int getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}


}
