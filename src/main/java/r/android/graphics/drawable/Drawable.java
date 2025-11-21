//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
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
	private Object tintColor; 
	private String tintMode;
	private MeasureTextHelper measureTextHelper;
	private boolean useGC;
	private boolean recycleable;
	private AttributeChangeListener attributeChangeListener; 
	
	public interface AttributeChangeListener {
		void onAttributeChange(String name, Object attribute);
	}
	
	public void setAttributeChangeListener(AttributeChangeListener attributeChangeListener) {
		this.attributeChangeListener = attributeChangeListener;
	}

	@com.google.j2objc.annotations.Weak private com.ashera.widget.IWidget overlay;
	
	public void setOverlay(com.ashera.widget.IWidget overlay) {
		this.overlay = overlay;
	}
	
	public void setUseGC(boolean useGC) {
		this.useGC = useGC;
	}


	public String getTintMode() {
		return tintMode;
	}

	public void setTintMode(String tintMode) {
		this.tintMode = tintMode;
	}

	public Object getTintColor() {
		return tintColor;
	}

	public void setTintColor(Object tintColor) {
		this.tintColor = tintColor;
	}

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
		if (overlay != null) {
			overlay.invokeMethod("updateBounds");
		}
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
    	if (this.attributeChangeListener != null) {
    		this.attributeChangeListener.onAttributeChange("bounds", bounds);
    	}
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

	public void jumpToCurrentState() {
		
	}
	
	public java.lang.String getSimulatedWidgetGroupName() {
		if (useGC) {
			return null;
		}
		return "ImageView";
	}
	
	public java.lang.String getSimulatedWidgetLocalName() {
		if (useGC) {
			return null;
		}
		return "ImageView";
	}
	
	public java.lang.String[] getSimulatedWidgetAttrs() {
		if (useGC) {
			return null;
		}
		return new String[]{"zIndex", "scaleType", "src"};
	}
	
	public java.lang.String[] getViewAttrs() {
		if (!useGC) {
			return null;
		}
		return new String[]{"swtGCImage"};
	}
	
	public java.lang.Object getAttribute(java.lang.String key) {
		switch (key) {
		case "zIndex":
			return "1000";
		case "src":
			return this;
		case "scaleType":
			return "fitXY";
		case "swtGCImage":
			return Arrays.asList(this);
		}
		return null;
	}
	
	public static interface MeasureTextHelper {
		public float getTextWidth();
		public float getTextHeight();
	}
	
	public void setMeasureTextHelper(MeasureTextHelper helper) {
		this.measureTextHelper = helper;
	}
	
    public MeasureTextHelper getMeasureTextHelper() {
		return measureTextHelper;
	}
    
	public boolean isRecycleable() {
		return recycleable;
	}

	public void setRecycleable(boolean recycleable) {
		this.recycleable = recycleable;
	}

	public boolean hasDrawable() {
		return drawable != null && !drawable.equals("@null");
	}

	public void setAlpha(int alpha) {
		if (this.attributeChangeListener != null) {
    		this.attributeChangeListener.onAttributeChange("alpha", alpha);
    	}		
	}
}
