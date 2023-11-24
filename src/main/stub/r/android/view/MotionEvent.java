package r.android.view;
public class MotionEvent{
    public static final int ACTION_DOWN  = 0;
    public static final int ACTION_UP  = 1;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_CANCEL = 3;
    public static final int ACTION_OUTSIDE = 4;
    public static final int ACTION_SCROLL = 8;
   	public static final int ACTION_BUTTON_PRESS   = 11;
    public static final int ACTION_BUTTON_RELEASE  = 12;

	private int x;
	private int y;
	private int rawX;
	private int rawY;
	private int action;
	public int getAction(){
	return action;
	}
	public void setAction(int action){
	this.action=action;
	}
	public int getX(){
	return x;
	}
	public void setX(int x){
	this.x=x;
	}
	public int getY(){
	return y;
	}
	public void setY(int y){
	this.y=y;
	}
	public int getRawX() {
		return rawX;
	}
	public void setRawX(int rawX) {
		this.rawX = rawX;
	}
	public int getRawY() {
		return rawY;
	}
	public void setRawY(int rawY) {
		this.rawY = rawY;
	}
	public int getEventTime() {
		return 0;
	}

}