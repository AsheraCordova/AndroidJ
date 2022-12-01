package r.android.graphics.drawable;

import java.util.Arrays;

import r.android.graphics.Canvas;
import r.android.graphics.Insets;
import r.android.graphics.Rect;

public class Drawable {
	protected static final Rect ZERO_BOUNDS_RECT = new Rect();
	private Rect mBounds = ZERO_BOUNDS_RECT;
	public static final int[] WILD_CARD = new int[0];
	private int[] mStateSet = WILD_CARD;
	private int minimumWidth;
	private Object drawable;
	private boolean redraw; 
	
    public boolean isRedraw() {
		return redraw;
	}

	public void setRedraw(boolean redraw) {
		this.redraw = redraw;
	}

	/**
     * Specify a bounding rectangle for the Drawable. This is where the drawable
     * will draw when its draw() method is called.
     */
    public void setBounds(Rect bounds) {
        setBounds(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }
    
	public Rect getBounds() {
		return mBounds;
	}

    /**
     * Specify a bounding rectangle for the Drawable. This is where the drawable
     * will draw when its draw() method is called.
     */
    public void setBounds(int left, int top, int right, int bottom) {
        Rect oldBounds = mBounds;

        if (oldBounds == ZERO_BOUNDS_RECT) {
            oldBounds = mBounds = new Rect();
        }

        if (oldBounds.left != left || oldBounds.top != top ||
                oldBounds.right != right || oldBounds.bottom != bottom) {
            if (!oldBounds.isEmpty()) {
                // first invalidate the previous bounds
                invalidateSelf();
            }
            mBounds.set(left, top, right, bottom);
            onBoundsChange(mBounds);
        }
    }
    
    public void invalidateSelf() {
    	
    }
    
    protected void onBoundsChange(Rect bounds) {
    }

	public int getTop() {
		return mBounds.top;
	}

	public int getLeft() {
		return mBounds.left;
	}

	public int getRight() {
		return mBounds.right;
	}


	public int getBottom() {
		return mBounds.bottom;
	}

	
    public Object getDrawable() {
		return drawable;
	}

	public void setDrawable(Object drawable) {
		this.drawable = drawable;
	}

	public boolean setState(final int[] stateSet) {
        if (!Arrays.equals(mStateSet, stateSet)) {
            mStateSet = stateSet;
            return onStateChange(stateSet);
        }
        return false;
    }

    /**
     * Describes the current state, as a union of primitve states, such as
     * {@link android.R.attr#state_focused},
     * {@link android.R.attr#state_selected}, etc.
     * Some drawables may modify their imagery based on the selected state.
     * @return An array of resource Ids describing the current state.
     */
    public int[] getState() {
        return mStateSet;
    }

	public void setMinimumWidth(int minimumWidth) {
		this.minimumWidth = minimumWidth;
	}

	public void setMinimumHeight(int minimumHeight) {
		this.minimumHeight = minimumHeight;
	}

	private int minimumHeight;

	public void setLayoutDirection(int layoutDirection) {
	}

	public int getMinimumHeight() {
		return minimumHeight;
	}

	public int getMinimumWidth() {
		return minimumWidth;
	}

	public boolean isProjected() {
		return false;
	}

	public boolean getPadding(Rect padding) {
		return false;
	}

	public Insets getOpticalInsets() {
		return null;
	}

    public void setVisible(boolean b, boolean c) {
        
    }

	public void setCallback(Object object) {
		
	}

	public boolean isStateful() {
		return false;
	}

    protected boolean onStateChange(int[] state) {
        return false;
    }

	public void setState(Object drawableState) {
		
	}

	public int getIntrinsicWidth() {
		return this.minimumWidth;
	}

	public int getIntrinsicHeight() {
		return this.minimumHeight;
	}


	public void draw(Canvas canvas) {
		canvas.draw(this);
	}

	public void setHotspotBounds(int left2, int top2, int right2, int bottom2) {
		
	}
}
