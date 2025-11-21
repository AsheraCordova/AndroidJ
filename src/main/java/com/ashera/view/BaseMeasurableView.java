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
package com.ashera.view;

import com.ashera.model.RectM;
import com.ashera.widget.HasWidgets;
import com.ashera.widget.ICustomMeasureHeight;
import com.ashera.widget.IWidget;
import com.ashera.widget.PluginInvoker;

import r.android.graphics.drawable.Drawable;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.widget.FrameLayout;

public abstract class BaseMeasurableView extends View {
	private @com.google.j2objc.annotations.Weak IWidget widget;
	private r.android.graphics.drawable.Drawable leftDrawable;
	private r.android.graphics.drawable.Drawable rightDrawable;
	private r.android.graphics.drawable.Drawable topDrawable;
	private r.android.graphics.drawable.Drawable bottomDrawable;
	private boolean isCompoundHorizontalPaddingConsumed;
	public enum VerticalAligment {middle, bottom, top};
	public enum HorizonantalAligment {center, left, right};
	public VerticalAligment verticalAligment;
	public HorizonantalAligment horizonantalAligment;
	private int drawablePadding;
	private boolean ignoreDrawableHeight;
	protected boolean mHorizontallyScrolling;
    public static int VERY_WIDE; // XXX should be much larger

	public boolean isIgnoreDrawableHeight() {
		return ignoreDrawableHeight;
	}
	
	public boolean isCompoundHorizontalPaddingConsumed() {
		return isCompoundHorizontalPaddingConsumed;
	}

	public void setCompoundHorizontalPaddingConsumed(boolean isCompoundHorizontalPaddingConsumed) {
		this.isCompoundHorizontalPaddingConsumed = isCompoundHorizontalPaddingConsumed;
	}


	public void setHorizontallyScrolling(boolean horizontallyScrolling) {
		this.mHorizontallyScrolling = horizontallyScrolling;
	}

	public void setIgnoreDrawableHeight(boolean ignoreDrawableHeight) {
		this.ignoreDrawableHeight = ignoreDrawableHeight;
	}

	public VerticalAligment getVerticalAligment() {
		return verticalAligment;
	}

	public void setVerticalAligment(VerticalAligment verticalAligment) {
		this.verticalAligment = verticalAligment;
	}
	
	public HorizonantalAligment getHorizonantalAligment() {
		return horizonantalAligment;
	}

	public void setHorizonantalAligment(HorizonantalAligment horizonantalAligment) {
		this.horizonantalAligment = horizonantalAligment;
	}

	public int getDrawablePadding() {
		return drawablePadding;
	}

	public void setDrawablePadding(int drawablePadding) {
		this.drawablePadding = drawablePadding;
	}

	public r.android.graphics.drawable.Drawable getBottomDrawable() {
		return bottomDrawable;
	}

	public void setBottomDrawable(r.android.graphics.drawable.Drawable bottomDrawable) {
		this.bottomDrawable = bottomDrawable;
	}

	public r.android.graphics.drawable.Drawable getLeftDrawable() {
		return leftDrawable;
	}

	public void setLeftDrawable(r.android.graphics.drawable.Drawable leftDrawable) {
		this.leftDrawable = leftDrawable;
	}

	public r.android.graphics.drawable.Drawable getRightDrawable() {
		return rightDrawable;
	}

	public void setRightDrawable(r.android.graphics.drawable.Drawable rightDrawable) {
		this.rightDrawable = rightDrawable;
	}

	public r.android.graphics.drawable.Drawable getTopDrawable() {
		return topDrawable;
	}

	public void setTopDrawable(r.android.graphics.drawable.Drawable topDrawable) {
		this.topDrawable = topDrawable;
	}


	public BaseMeasurableView(IWidget widget) {
		this.widget = widget;
		int width = PluginInvoker.getScreenWidth();
		int height = PluginInvoker.getScreenHeight();
		VERY_WIDE = height > width ? height * 2 : width * 2;
	}

	public IWidget getWidget() {
		return widget;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
	    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

	    int width;
	    int height;

	    if (widthMode == MeasureSpec.EXACTLY) {
	        // Parent has told us how big to be. So be it.
	        width = widthSize;
	    } else {
	    	width = mPaddingLeft + mPaddingRight;
			width += measureWidth();
			
			if (!ignoreDrawableHeight) {
				width += getDrawableWidth(leftDrawable) + getDrawableWidth(rightDrawable);
			}

	    	if (widget instanceof com.ashera.widget.IMeasureWidth) {
	    	    width = ((com.ashera.widget.IMeasureWidth) widget).measureWidth(widthMode, widthSize, width);
            }
	    	
	    	if (this instanceof com.ashera.widget.IMeasureWidth) {
	    	    width = ((com.ashera.widget.IMeasureWidth) this).measureWidth(widthMode, widthSize, width);
            }
	    	
            if (widget instanceof com.ashera.widget.IMaxWidth) {
                int maxWidth = ((com.ashera.widget.IMaxWidth) widget).getMaxWidth();
                if (maxWidth != -1) {
                    width = Math.min(width, maxWidth);
                }
            }
            // Check against our minimum width
            width = Math.max(width, getSuggestedMinimumWidth());

            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(widthSize, width);
            }
	    }
      
	    if (mHorizontallyScrolling) {
	    	width = VERY_WIDE;
	    }
	    
	    if (heightMode == MeasureSpec.EXACTLY) {
	        // Parent has told us how big to be. So be it.
	        height = heightSize;
	    } else {
	    	
	    	height = measureWidgetHeight(width);
	    	
	    	if (this instanceof com.ashera.widget.IMeasureHeight) {
	    	    height = ((com.ashera.widget.IMeasureHeight) this).measureHeight(heightMode, heightSize, height);
            }

	    	if (widget instanceof com.ashera.widget.IMeasureHeight) {
	    	    height = ((com.ashera.widget.IMeasureHeight) widget).measureHeight(heightMode, heightSize, height);
            }

	    	height += mPaddingTop + mPaddingBottom + (ignoreDrawableHeight ? 0 : getDrawableHeight(topDrawable, true) + getDrawableHeight(bottomDrawable, true));
	    	
	        if (widget instanceof com.ashera.widget.IMaxHeight) {
	            int maxHeight = ((com.ashera.widget.IMaxHeight) widget).getMaxHeight();
	            if (maxHeight != -1) {
	                height = Math.min(height, maxHeight);
	            }
	        }
	        
	        height = Math.max(height, getDrawableHeight(leftDrawable, false) + mPaddingTop + mPaddingBottom);
	        height = Math.max(height, getDrawableHeight(rightDrawable, false) + mPaddingTop + mPaddingBottom);

	        height = Math.max(getSuggestedMinimumHeight(), height);
            
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
	    }

	    
	    setMeasuredDimension(width, height);	
	}

	private int measureWidth() {
		int width;
		if (widget instanceof com.ashera.widget.ICustomMeasureWidth) {
			width = ((com.ashera.widget.ICustomMeasureWidth) widget).measureWidth();
		} else {
			width = nativeMeasureWidth(widget.asNativeWidget());
		}
		return width;
	}

	private int measureWidgetHeight(int width, Object... nativeWidgets) {
		int height;
		int textViewWidth = width - (ignoreDrawableHeight ? 0 : (getCompoundPaddingLeft() + getCompoundPaddingRight()));

		// hack to ensure that height is not returned zero swt/ios seem to return 0 for width 0   
		if (textViewWidth == 0) {
			textViewWidth = 1;
		}
		
		if (nativeWidgets != null && nativeWidgets.length > 0) {
			height = 0;
			for (Object object : nativeWidgets) {
				height += nativeMeasureHeight(object, textViewWidth);
			}
		} else if (widget instanceof ICustomMeasureHeight) {
			height = ((ICustomMeasureHeight) widget).measureHeight(textViewWidth);
		} else {
			height = nativeMeasureHeight(widget.asNativeWidget(), textViewWidth);
		}
		return height;
	}

	private int getDrawableWidth(Drawable drawable) {
		if (drawable == null) {
			return 0;
		}
		return drawable.getMinimumWidth() + drawablePadding;
	}
	
	private int getDrawableHeight(Drawable drawable, boolean considerPadding) {
		if (drawable == null || !drawable.hasDrawable()) {
			return 0;
		}
		return drawable.getMinimumHeight() + (considerPadding ? drawablePadding : 0);
	}

    public abstract int nativeMeasureWidth(Object uiView);		
    public abstract int nativeMeasureHeight(Object uiView, int width);
	
    
	public static void measureWidgetWithNoParent(HasWidgets parent, IWidget widget, int level, int initialWidth, int indent) {		
		measure(parent, widget, level, indent, initialWidth);
	}
	
	private static void measure(HasWidgets parent, IWidget widget, int level, int indent, int initialWidth) {
		ViewGroup frameLayout = new FrameLayout();
		View view = (View) ((IWidget) widget).asWidget();		
		if (view instanceof ViewGroup) {
			frameLayout = (ViewGroup) view;
		} else {
			((ViewGroup) view.getParent()).removeView(view);
			frameLayout.addView(view);
		}
		frameLayout.setLayoutParams(new ViewGroup.LayoutParams(nativeGetWidth(parent, level, indent, initialWidth), ViewGroup.LayoutParams.WRAP_CONTENT));

		int w = frameLayout.getLayoutParams().width;
		int h = frameLayout.getLayoutParams().height ;
		

		int wmeasureSpec = MeasureSpec.UNSPECIFIED;
		int hmeasureSpec = MeasureSpec.UNSPECIFIED;
		if (h >= 0) {
			hmeasureSpec = MeasureSpec.EXACTLY;
		}
		if (w >= 0) {
			wmeasureSpec = MeasureSpec.EXACTLY;
		}
		frameLayout.measure(View.MeasureSpec.makeMeasureSpec( w, wmeasureSpec ),
			View.MeasureSpec.makeMeasureSpec( h, hmeasureSpec  ));
		frameLayout.layout(0, 0, w, view.getMeasuredHeight());
	}
	
	private static int nativeGetWidth(HasWidgets parent, int level, int indent, int initialWidth) {		
		return ((View)parent.asWidget()).getMeasuredWidth() - initialWidth - (level * indent);
	}
	
    public int getBaseline() {
        return widget.getBaseLine();
    }
    
    public RectM getWidgetBounds(int width, int height, Object... nativeWidgets) {
		int compoundPaddingLeft = 0;
		int compoundPaddingRight = 0;
		int compoundPaddingTop = 0;
		int compoundPaddingBottom = 0;
		
		if (!ignoreDrawableHeight) {
			if (!isCompoundHorizontalPaddingConsumed) {
				if (isLayoutRtl()) {
					compoundPaddingLeft = getCompoundPaddingRight();
					compoundPaddingRight = getCompoundPaddingLeft();
				}
				else {
					compoundPaddingLeft = getCompoundPaddingLeft();
					compoundPaddingRight = getCompoundPaddingRight();
				}
			}
			compoundPaddingTop = getCompoundPaddingTop();
			compoundPaddingBottom = getCompoundPaddingBottom();
		}
	    
		int boundHeight = height - compoundPaddingTop - compoundPaddingBottom;
		
	    int alignY = 0;
	    if (verticalAligment != null) {
	    	int sizeToFitHeight = measureWidgetHeight(width, nativeWidgets);
			switch (verticalAligment)
	        {
	            case top:
	                break;
	                
	            case middle:
	            	alignY = (height - compoundPaddingTop - compoundPaddingBottom - sizeToFitHeight) / 2;
	                break;
	                
	            case bottom:
	            	alignY = (height - compoundPaddingTop - compoundPaddingBottom - sizeToFitHeight);
	                break;
	                
	            default:
	                break;
	        }
			boundHeight = sizeToFitHeight;
	    }
	    int boundWidth = width - compoundPaddingLeft - compoundPaddingRight;
	    int alignX = 0;
		if (horizonantalAligment != null) {
			int sizeToFitWidth = measureWidth();
			if (sizeToFitWidth < boundWidth) {
				switch (horizonantalAligment) {
				case left:
					break;

				case center:
					alignX = (boundWidth - sizeToFitWidth) / 2;
					break;

				case right:
					alignX = (boundWidth - sizeToFitWidth);
					break;

				default:
					break;
				}

				boundWidth = sizeToFitWidth;
			}
		}
	    

	    return new RectM(compoundPaddingLeft + alignX, compoundPaddingTop + alignY, boundWidth, boundHeight);
    }

	public int getCompoundPaddingBottom() {
		int compoundPaddingBottom = getPaddingBottom();
	    
	    if (getBottomDrawable() != null) {
	        compoundPaddingBottom += getBottomDrawable().getMinimumHeight() + drawablePadding;
	    }
		return compoundPaddingBottom;
	}

	public int getCompoundPaddingTop() {
		int compoundPaddingTop = getPaddingTop();
	    
	    if (getTopDrawable() != null) {
	        compoundPaddingTop += getTopDrawable().getMinimumHeight() + drawablePadding;
	    }
		return compoundPaddingTop;
	}

	public int getCompoundPaddingRight() {
		int compoundPaddingRight = getPaddingRight();
	    if (rightDrawable != null) {
	        compoundPaddingRight += getRightDrawable().getMinimumWidth() + drawablePadding;
	    }
		return compoundPaddingRight;
	}

	public int getCompoundPaddingLeft() {
		int compoundPaddingLeft = getPaddingLeft();

		if (leftDrawable != null) {
	        compoundPaddingLeft += leftDrawable.getMinimumWidth() + drawablePadding;
	    }
		return compoundPaddingLeft;
	}
	
	public int getCompoundPaddingLeftWithIgnoreCheck() {
		if (isCompoundHorizontalPaddingConsumed) {
			return 0;
		}
		return getCompoundPaddingLeft();
	}
	
	public int getCompoundPaddingRightWithIgnoreCheck() {
		if (isCompoundHorizontalPaddingConsumed) {
			return 0;
		}
		return getCompoundPaddingRight();
	}
	
	public RectM getLeftDrawableBounds(int x, int y, int width, int height) {
	    if (leftDrawable != null) {
	    	int dWidth = leftDrawable.getMinimumWidth();
	    	int dHeight = leftDrawable.getMinimumHeight();
	        int dy = getPaddingTop();
	        int dx = 0;
	        
	        if (isLayoutRtl()) {
	        	dx = width - dWidth - getPaddingLeft();
	        } else {
	        	dx = getPaddingLeft();
	        }
	        
	        if (height > dHeight) {
	            dy += (height - getPaddingTop() - getPaddingBottom() - dHeight)/2.0;
	        }
	        return new RectM(dx, dy, dWidth, dHeight);
	    }
	    
	    return new RectM(0, 0, 0, 0);
	}
	
	public RectM getRightDrawableBounds(int x, int y, int width, int height) {
		if (rightDrawable != null) {
			int dWidth = rightDrawable.getMinimumWidth();
			int dHeight = rightDrawable.getMinimumHeight();
			int dy = getPaddingTop();
			int dx = 0;
	        
	        if (isLayoutRtl()) {
	        	dx = getPaddingRight();
	        } else {
	        	dx = width - dWidth - getPaddingRight();
	        }

			if (height > dHeight) {
				dy += (height - getPaddingTop() - getPaddingBottom() - dHeight) / 2.0;
			}
			return new RectM(dx, dy, dWidth, dHeight);
		}

		return new RectM(0, 0, 0, 0);
	}
	
	public RectM getTopDrawableBounds(int x, int y, int width, int height) {
		if (topDrawable != null) {
			int dWidth = topDrawable.getMinimumWidth();
			int dHeight = topDrawable.getMinimumHeight();
			int dy = getPaddingTop();
			int dx = getPaddingLeft();
			if (width > dWidth) {
				dx += ((width - getPaddingLeft() - getPaddingRight() - dWidth) / 2.0);
			}
			return new RectM(dx, dy, dWidth, dHeight);
		}

		return new RectM(0, 0, 0, 0);
	}
	
	public RectM getBottomDrawableBounds(int x, int y, int width, int height) {
		if (bottomDrawable != null) {
			int dWidth = bottomDrawable.getMinimumWidth();
			int dHeight = bottomDrawable.getMinimumHeight();
			int dy = height - dHeight - getPaddingBottom();
			int dx = getPaddingLeft();
			if (width > dWidth) {
				dx += ((width - getPaddingLeft() - getPaddingRight() - dWidth) / 2.0);
			}
			return new RectM(dx, dy, dWidth, dHeight);
		}

		return new RectM(0, 0, 0, 0);
	}
	
	public boolean isVerticalAlignTop() {
		return verticalAligment == VerticalAligment.top;
	}
	
	public boolean isVerticalAlignBottom() {
		return verticalAligment == VerticalAligment.bottom;
	}
	
	public boolean isVerticalAlignMiddle() {
		return verticalAligment == VerticalAligment.middle;
	}
	
	public boolean hasDrawables() {
		return leftDrawable != null || rightDrawable != null || topDrawable != null || bottomDrawable != null; 
	}
}
