package r.android.widget;
import r.android.os.Build;
import r.android.os.Build.VERSION_CODES;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.widget.FrameLayout.LayoutParams;

public class HorizontalScrollView extends FrameLayout{
	private boolean mFillViewport;
    private boolean mIsLayoutDirty = true;

	public boolean isFillViewport(){
	    return mFillViewport;
	  }
	  public void setFillViewport(  boolean fillViewport){
	    if (fillViewport != mFillViewport) {
	      mFillViewport=fillViewport;
	      requestLayout();
	    }
	  }
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!mFillViewport) {
            return;
        }

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            return;
        }

        if (getChildCount() > 0) {
            final View child = getChildAt(0);
            final int widthPadding;
            final int heightPadding;
            final FrameLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            final int targetSdkVersion = getContext().getApplicationInfo().targetSdkVersion;
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                widthPadding = mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin;
                heightPadding = mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin;
            } else {
                widthPadding = mPaddingLeft + mPaddingRight;
                heightPadding = mPaddingTop + mPaddingBottom;
            }

            int desiredWidth = getMeasuredWidth() - widthPadding;
            if (child.getMeasuredWidth() < desiredWidth) {
                final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                        desiredWidth, MeasureSpec.EXACTLY);
                final int childHeightMeasureSpec = getChildMeasureSpec(
                        heightMeasureSpec, heightPadding, lp.height);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }
	
	protected int computeHorizontalScrollRange() {
        final int count = getChildCount();
        final int contentWidth = getWidth() - mPaddingLeft - mPaddingRight;
        if (count == 0) {
            return contentWidth;
        }

        int scrollRange = getChildAt(0).getRight();
        final int scrollX = mScrollX;
        final int overscrollRight = Math.max(0, scrollRange - contentWidth);
        if (scrollX < 0) {
            scrollRange -= scrollX;
        } else if (scrollX > overscrollRight) {
            scrollRange += scrollX - overscrollRight;
        }

        return scrollRange;
    }

	 protected void measureChild(View child, int parentWidthMeasureSpec,
	            int parentHeightMeasureSpec) {
	        ViewGroup.LayoutParams lp = child.getLayoutParams();

	        final int horizontalPadding = mPaddingLeft + mPaddingRight;
	        final int childWidthMeasureSpec = MeasureSpec.makeSafeMeasureSpec(
	                Math.max(0, MeasureSpec.getSize(parentWidthMeasureSpec) - horizontalPadding),
	                MeasureSpec.UNSPECIFIED);

	        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
	                mPaddingTop + mPaddingBottom, lp.height);
	        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
	    }

	    @Override
	    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
	            int parentHeightMeasureSpec, int heightUsed) {
	        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

	        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
	                mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin
	                        + heightUsed, lp.height);
	        final int usedTotal = mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin +
	                widthUsed;
	        final int childWidthMeasureSpec = MeasureSpec.makeSafeMeasureSpec(Integer.MAX_VALUE,
	                MeasureSpec.UNSPECIFIED);

	        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
	    }
	    
	    public void requestLayout() {
	        mIsLayoutDirty = true;
	        super.requestLayout();
	    }

	    private int originalBottomPadding = 0;
	    public void adjustPaddingIfScrollBarPresent(int widthMeasureSpec, int heightMeasureSpec, int thumbHeight) {
			if (getChildCount() > 0) {
				final View child = getChildAt(0);
				final int widthPadding;
				final int heightPadding;
				final int targetSdkVersion = getContext().getApplicationInfo().targetSdkVersion;
				final FrameLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
				if (targetSdkVersion >= VERSION_CODES.M) {
					widthPadding = mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin;
					heightPadding = mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin;
				} else {
					widthPadding = mPaddingLeft + mPaddingRight;
					heightPadding = mPaddingTop + mPaddingBottom;
				}
				final int desiredWidth = getMeasuredWidth() - widthPadding;

				if (desiredWidth >= child.getMeasuredWidth()) {
					if (mPaddingBottom == thumbHeight && originalBottomPadding != thumbHeight) {
						mPaddingBottom = originalBottomPadding;
						measureChild(child, widthMeasureSpec, heightMeasureSpec);
					}
				} else {
					if (mPaddingBottom < thumbHeight) {
						originalBottomPadding = mPaddingBottom;
						mPaddingBottom = thumbHeight;
						measureChild(child, widthMeasureSpec, heightMeasureSpec);
					}
				}
			}
		}
}
