package r.android.graphics;

import r.android.graphics.drawable.Drawable;

public interface Canvas {
	void draw(Drawable mDivider);
	void reset();
	default void translate(int scrollX, int scrollY) {
		
	}
}
