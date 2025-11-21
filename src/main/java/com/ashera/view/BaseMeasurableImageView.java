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

import com.ashera.widget.IWidget;
import com.ashera.widget.IsImage;

import r.android.view.View;

public abstract class BaseMeasurableImageView extends View {
	private @com.google.j2objc.annotations.Weak IWidget widget;
	private boolean mAdjustViewBoundsCompat;
	private boolean mAdjustViewBounds = false;
	private Object mDrawable;
	private int mDrawableWidth;
	private int mDrawableHeight;
	private int mMaxWidth = Integer.MAX_VALUE;
    private int mMaxHeight = Integer.MAX_VALUE;
    private int mBaseline = -1;
    private boolean mBaselineAlignBottom = false;
    private boolean cropToPadding;
    private boolean usePaddingForBounds = true;
    private String scaleType = SCALETYPE_FITCENTER;
	private int scaleTypeInt;
    
	public int getScaleTypeInt() {
		return scaleTypeInt;
	}

	public String getScaleType() {
		return scaleType;
	}

	public void setScaleType(String scaleType, int scaleTypeInt) {
		this.scaleType = scaleType;
		this.scaleTypeInt = scaleTypeInt;
	}

	public void setUsePaddingForBounds(boolean usePaddingForBounds) {
		this.usePaddingForBounds = usePaddingForBounds;
	}

	public void setCropToPadding(boolean objValue) {
		this.cropToPadding = objValue;
	}

	public boolean getCropToPadding() {
		return cropToPadding;
	}
    public BaseMeasurableImageView(IWidget widget) {
		this.widget = widget;
	}

	public IWidget getWidget() {
		return widget;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        resolveUri();
        int w;
        int h;
        
        // Desired aspect ratio of the view's contents (not including padding)
        float desiredAspect = 0.0f;
        
        // We are allowed to change the view's width
        boolean resizeWidth = false;
        
        // We are allowed to change the view's height
        boolean resizeHeight = false;
        
        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (mDrawable == null) {
            // If no drawable, its intrinsic size is 0.
            mDrawableWidth = -1;
            mDrawableHeight = -1;
            w = h = 0;
        } else {
            w = mDrawableWidth;
            h = mDrawableHeight;
            if (w <= 0) w = 1;
            if (h <= 0) h = 1;

            // We are supposed to adjust view bounds to match the aspect
            // ratio of our drawable. See if that is possible.
            if (mAdjustViewBounds) {
                resizeWidth = widthSpecMode != MeasureSpec.EXACTLY;
                resizeHeight = heightSpecMode != MeasureSpec.EXACTLY;
                
                desiredAspect = (float) w / (float) h;
            }
        }
        
        int pleft = mPaddingLeft;
        int pright = mPaddingRight;
        int ptop = mPaddingTop;
        int pbottom = mPaddingBottom;

        int widthSize;
        int heightSize;
        
        if (resizeWidth || resizeHeight) {
           /* If we get here, it means we want to resize to match the
                drawables aspect ratio, and we have the freedom to change at
                least one dimension. 
            */

            // Get the max possible width given our constraints
            widthSize = resolveAdjustedSize(w + pleft + pright, mMaxWidth, widthMeasureSpec);

            // Get the max possible height given our constraints
            heightSize = resolveAdjustedSize(h + ptop + pbottom, mMaxHeight, heightMeasureSpec);

            if (desiredAspect != 0.0f) {
                // See what our actual aspect ratio is
                float actualAspect = (float)(widthSize - pleft - pright) /
                                        (heightSize - ptop - pbottom);
                
                if (Math.abs(actualAspect - desiredAspect) > 0.0000001) {
                    
                    boolean done = false;
                    
                    // Try adjusting width to be proportional to height
                    if (resizeWidth) {
                        int newWidth = (int)(desiredAspect * (heightSize - ptop - pbottom)) +
                                pleft + pright;

                        // Allow the width to outgrow its original estimate if height is fixed.
                        if (!resizeHeight && !mAdjustViewBoundsCompat) {
                            widthSize = resolveAdjustedSize(newWidth, mMaxWidth, widthMeasureSpec);
                        }

                        if (newWidth <= widthSize) {
                            widthSize = newWidth;
                            done = true;
                        } 
                    }
                    
                    // Try adjusting height to be proportional to width
                    if (!done && resizeHeight) {
                        int newHeight = (int)((widthSize - pleft - pright) / desiredAspect) +
                                ptop + pbottom;

                        // Allow the height to outgrow its original estimate if width is fixed.
                        if (!resizeWidth && !mAdjustViewBoundsCompat) {
                            heightSize = resolveAdjustedSize(newHeight, mMaxHeight,
                                    heightMeasureSpec);
                        }

                        if (newHeight <= heightSize) {
                            heightSize = newHeight;
                        }
                    }
                }
            }
        } else {
           /* We are either don't want to preserve the drawables aspect ratio,
               or we are not allowed to change view dimensions. Just measure in
               the normal way.
            */
            w += pleft + pright;
            h += ptop + pbottom;
                
            w = Math.max(w, getSuggestedMinimumWidth());
            h = Math.max(h, getSuggestedMinimumHeight());
            widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
            heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);
        }

        setMeasuredDimension(widthSize, heightSize);
    }

	private void resolveUri() {
//		if (mDrawable != null) {
//			return;
//		}
		updateDrawable();
	}
	
    private void updateDrawable() {

    	IsImage image = (com.ashera.widget.IsImage) widget;
        int[] dimension = image.getImageDimension();
    		
        mDrawableWidth = (int) dimension[0];
        mDrawableHeight = (int) dimension[1];
        mDrawable = image.getImage();
    }

	private int resolveAdjustedSize(int desiredSize, int maxSize,
            int measureSpec) {
		int result = desiredSize;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize =  MeasureSpec.getSize(measureSpec);
		switch (specMode) {
		case MeasureSpec.UNSPECIFIED:
		/* Parent says we can be as big as we want. Just don't be larger
		than max size imposed on ourselves.
		*/
		result = Math.min(desiredSize, maxSize);
		break;
		case MeasureSpec.AT_MOST:
		// Parent says we can be as big as we want, up to specSize. 
		// Don't be larger than specSize, and don't be larger than 
		// the max size imposed on ourselves.
		result = Math.min(Math.min(desiredSize, specSize), maxSize);
		break;
		case MeasureSpec.EXACTLY:
		// No choice. Do what we are told.
		result = specSize;
		break;
		}
		return result;
	}
	
	public void setAdjustViewBounds(boolean mAdjustViewBounds) {
		this.mAdjustViewBounds = mAdjustViewBounds;
	}
	
	public boolean getAdjustViewBounds() {
		return mAdjustViewBounds;
	}
	public int getMaxWidth() {
		return mMaxWidth;
	}
	public int getMaxHeight() {
		return mMaxHeight;
	}
    public void setMaxWidth(int mMaxWidth) {
        this.mMaxWidth = mMaxWidth;
    }

    public void setMaxHeight(int mMaxHeight) {
        this.mMaxHeight = mMaxHeight;
    }
    
    @Override
    public int getBaseline() {
        if (mBaselineAlignBottom) {
            return getMeasuredHeight();
        } else {
            return mBaseline;
        }
    }

   /**
     * <p>Set the offset of the widget's text baseline from the widget's top
     * boundary.  This value is overridden by the {@link #setBaselineAlignBottom(boolean)}
     * property.</p>
     *
     * @param baseline The baseline to use, or -1 if none is to be provided.
     *
     * @see #setBaseline(int)
     * @attr ref android.R.styleable#ImageView_baseline
     */
    public void setBaseline(int baseline) {
        if (mBaseline != baseline) {
            mBaseline = baseline;
            requestLayout();
        }
    }

   /**
     * Sets whether the baseline of this view to the bottom of the view.
     * Setting this value overrides any calls to setBaseline.
     *
     * @param aligned If true, the image view will be baseline aligned by its bottom edge.
     *
     * @attr ref android.R.styleable#ImageView_baselineAlignBottom
     */
    public void setBaselineAlignBottom(boolean aligned) {
        if (mBaselineAlignBottom != aligned) {
            mBaselineAlignBottom = aligned;
            requestLayout();
        }
    }

   /**
     * Checks whether this view's baseline is considered the bottom of the view.
     *
     * @return True if the ImageView's baseline is considered the bottom of the view, false if otherwise.
     * @see #setBaselineAlignBottom(boolean)
     */
    public boolean getBaselineAlignBottom() {
        return mBaselineAlignBottom;
    }
    
    private static final String SCALETYPE_FITXY = "fitXY";
	private static final String SCALETYPE_CENTERINSIDE = "centerInside";
	private static final String SCALETYPE_CENTER = "center";
	private static final String SCALETYPE_CENTERCROP = "centerCrop";
	private static final String SCALETYPE_FITEND = "fitEnd";
	private static final String SCALETYPE_FITSTART = "fitStart";
	private static final String SCALETYPE_FITCENTER = "fitCenter";
	
	public com.ashera.model.RectM getCropPaddingClipBounds() {
		int pLeft = 0;
        int pTop = 0;
		int pRight = 0;
		int pBottom = 0; 
		
		if (usePaddingForBounds) {
			pLeft = getPaddingStart();
			pRight = getPaddingEnd();
			pTop = getPaddingTop();
			pBottom = getPaddingBottom();
		}
		
		final int vwidth = getMeasuredWidth() - pLeft - pRight;
		final int vheight = getMeasuredHeight() - pTop - pBottom;
		
		return new com.ashera.model.RectM(pLeft, pTop, vwidth, vheight);
	}
	/*
	 * to be called only after measure phase
	 */
	public com.ashera.model.RectM getImageBounds() {
		int measuredWidth = getMeasuredWidth();
		int measuredHeight = getMeasuredHeight();

		return getImageBounds(measuredWidth, measuredHeight);
	}

	public com.ashera.model.RectM getImageBounds(int measuredWidth, int measuredHeight) {
		if (mDrawableWidth == 0 && mDrawableHeight == 0) // Can by JPEG using CMYK colour space etc.
            return null;

		int dwidth = mDrawableWidth;
		int dheight = mDrawableHeight;
        int pLeft = 0;
        int pTop = 0;
		int pRight = 0;
		int pBottom = 0; 
		
		if (usePaddingForBounds) {
			pLeft = getPaddingStart();
			pRight = getPaddingEnd();
			pTop = getPaddingTop();
			pBottom = getPaddingBottom();
		}
        
        float dx = 0, dy = 0;

		final int vwidth = measuredWidth - pLeft - pRight;
		final int vheight = measuredHeight - pTop - pBottom;

        int newHeight = vheight;
        int newWidth = vwidth;

        switch (scaleType) {
        case SCALETYPE_FITXY: {
            newWidth = vwidth;
            newHeight = vheight;
            break;
        }
        case SCALETYPE_FITCENTER: {
            newWidth = (dwidth * vheight) / dheight;
            newHeight = vheight;
            
            if (newWidth > vwidth) {
            	newWidth = vwidth;
            	 newHeight = (dheight * vwidth) / dwidth;
            }

            dx = Math.abs(newWidth - vwidth) * 0.5f;
            dy = Math.abs(newHeight - vheight) * 0.5f;

            break;
        }
        case SCALETYPE_FITSTART: {
            newWidth = (dwidth * vheight) / dheight;
            newHeight = vheight;
            if (newWidth > vwidth) {
                newWidth = vwidth;
                newHeight = (dheight * vwidth) / dwidth;
            }

            break;
        }
        case SCALETYPE_FITEND: {
            newWidth = (dwidth * vheight) / dheight;
            newHeight = vheight;
            if (newWidth > vwidth) {
                newWidth = vwidth;
                newHeight = (dheight * vwidth) / dwidth;
            }

            dx = Math.abs(newWidth - vwidth);
            dy = Math.abs(newHeight - vheight);
        }

            break;
        case SCALETYPE_CENTERCROP: {
            float scale;

            if (dwidth * vheight > vwidth * dheight) {
                scale = (float) vheight / (float) dheight;
                dx = (vwidth - dwidth * scale) * 0.5f;
            } else {
                scale = (float) vwidth / (float) dwidth;
                dy = (vheight - dheight * scale) * 0.5f;
            }
            newWidth = (int) (dwidth * scale);
            newHeight = (int) (dheight * scale);
            break;
        }
        case SCALETYPE_CENTER: {
            dx = Math.round((vwidth - dwidth) * 0.5f);
            dy = Math.round((vheight - dheight) * 0.5f);
            newWidth = dwidth;
            newHeight = dheight;
            break;
        }
        case SCALETYPE_CENTERINSIDE: {
            float scale;
            if (dwidth <= vwidth && dheight <= vheight) {
                scale = 1.0f;
            } else {
                scale = Math.min((float) vwidth / (float) dwidth, (float) vheight / (float) dheight);
            }
            dx = Math.round((vwidth - dwidth * scale) * 0.5f);
            dy = Math.round((vheight - dheight * scale) * 0.5f);
            newWidth = (int) (dwidth * scale);
            newHeight = (int) (dheight * scale);
            break;
        }
        default:
            break;
        }
        dx += pLeft;
        dy += pTop;
        
        return new com.ashera.model.RectM((int) dx, (int) dy, newWidth, newHeight);
	}
	
	public boolean hasDrawables() {
		return false; 
	}

	private r.android.graphics.drawable.Drawable imageDrawable;
    public r.android.graphics.drawable.Drawable getImageDrawable() {
		return imageDrawable;
	}
	public void setImageDrawable(r.android.graphics.drawable.Drawable imageDrawable) {
		this.imageDrawable = imageDrawable;
	}

}
