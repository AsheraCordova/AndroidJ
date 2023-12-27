package r.android.animation;

public class ObjectAnimatorTest {
	private int test;
	public int getTest() {
		return test;
	}
	public void setTest(int test) {
		this.test = test;
		
		System.out.println("test" + test);
	}
	public static void main(String[] args) {
		ObjectAnimatorTest x = new ObjectAnimatorTest();
		ObjectAnimator ofInt = r.android.animation.ObjectAnimator.ofInt(x, "test",  0, 255);
		ofInt.setDuration(1000);
		ofInt.setRepeatCount(2);
		ofInt.start();
		
	}
}
