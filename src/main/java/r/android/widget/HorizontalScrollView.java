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
/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package r.android.widget;
import r.android.graphics.Rect;
import r.android.os.Build;
import r.android.view.View;
import r.android.view.ViewGroup;
public class HorizontalScrollView extends FrameLayout {
  private static final int ANIMATED_SCROLL_GAP=ScrollView.ANIMATED_SCROLL_GAP;
  private int mLastMotionX;
  private boolean mIsLayoutDirty=true;
  private boolean mIsBeingDragged=false;
  private boolean mFillViewport;
  private boolean mSmoothScrollingEnabled=true;
  private int mTouchSlop;
  private int mMinimumVelocity;
  private int mMaximumVelocity;
  private int mOverscrollDistance;
  private int mOverflingDistance;
  private int mActivePointerId=INVALID_POINTER;
  private static final int INVALID_POINTER=-1;
  public boolean isFillViewport(){
    return mFillViewport;
  }
  public void setFillViewport(  boolean fillViewport){
    if (fillViewport != mFillViewport) {
      mFillViewport=fillViewport;
      requestLayout();
    }
  }
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    if (!mFillViewport) {
      return;
    }
    final int widthMode=MeasureSpec.getMode(widthMeasureSpec);
    if (widthMode == MeasureSpec.UNSPECIFIED) {
      return;
    }
    if (getChildCount() > 0) {
      final View child=getChildAt(0);
      final int widthPadding;
      final int heightPadding;
      final FrameLayout.LayoutParams lp=(LayoutParams)child.getLayoutParams();
      final int targetSdkVersion=getContext().getApplicationInfo().targetSdkVersion;
      if (targetSdkVersion >= Build.VERSION_CODES.M) {
        widthPadding=mPaddingLeft + mPaddingRight + lp.leftMargin+ lp.rightMargin;
        heightPadding=mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin;
      }
 else {
        widthPadding=mPaddingLeft + mPaddingRight;
        heightPadding=mPaddingTop + mPaddingBottom;
      }
      int desiredWidth=getMeasuredWidth() - widthPadding;
      if (child.getMeasuredWidth() < desiredWidth) {
        final int childWidthMeasureSpec=MeasureSpec.makeMeasureSpec(desiredWidth,MeasureSpec.EXACTLY);
        final int childHeightMeasureSpec=getChildMeasureSpec(heightMeasureSpec,heightPadding,lp.height);
        child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
      }
    }
  }
  protected int computeHorizontalScrollRange(){
    final int count=getChildCount();
    final int contentWidth=getWidth() - mPaddingLeft - mPaddingRight;
    if (count == 0) {
      return contentWidth;
    }
    int scrollRange=getChildAt(0).getRight();
    final int scrollX=mScrollX;
    final int overscrollRight=Math.max(0,scrollRange - contentWidth);
    if (scrollX < 0) {
      scrollRange-=scrollX;
    }
 else     if (scrollX > overscrollRight) {
      scrollRange+=scrollX - overscrollRight;
    }
    return scrollRange;
  }
  protected void measureChildWithMargins(  View child,  int parentWidthMeasureSpec,  int widthUsed,  int parentHeightMeasureSpec,  int heightUsed){
    final MarginLayoutParams lp=(MarginLayoutParams)child.getLayoutParams();
    final int childHeightMeasureSpec=getChildMeasureSpec(parentHeightMeasureSpec,mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin+ heightUsed,lp.height);
    final int usedTotal=mPaddingLeft + mPaddingRight + lp.leftMargin+ lp.rightMargin+ widthUsed;
    final int childWidthMeasureSpec=MeasureSpec.makeSafeMeasureSpec(Math.max(0,MeasureSpec.getSize(parentWidthMeasureSpec) - usedTotal),MeasureSpec.UNSPECIFIED);
    child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
  }
  public void requestLayout(){
    mIsLayoutDirty=true;
    super.requestLayout();
  }
  public void scrollTo(  int x,  int y){
    if (getChildCount() > 0) {
      View child=getChildAt(0);
      x=clamp(x,getWidth() - mPaddingRight - mPaddingLeft,child.getWidth());
      y=clamp(y,getHeight() - mPaddingBottom - mPaddingTop,child.getHeight());
      if (x != mScrollX || y != mScrollY) {
        setMyAttribute("scrollX",x);
      }
    }
  }
  private static int clamp(  int n,  int my,  int child){
    if (my >= child || n < 0) {
      return 0;
    }
    if ((my + n) > child) {
      return child - my;
    }
    return n;
  }
  private int originalBottomPadding=0;
  public void adjustPaddingIfScrollBarPresent(  int widthMeasureSpec,  int heightMeasureSpec,  int thumbHeight){
    if (getChildCount() > 0) {
      final View child=getChildAt(0);
      final int widthPadding;
      final int heightPadding;
      final int targetSdkVersion=getContext().getApplicationInfo().targetSdkVersion;
      final FrameLayout.LayoutParams lp=(LayoutParams)child.getLayoutParams();
      if (targetSdkVersion >= r.android.os.Build.VERSION_CODES.M) {
        widthPadding=mPaddingLeft + mPaddingRight + lp.leftMargin+ lp.rightMargin;
        heightPadding=mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin;
      }
 else {
        widthPadding=mPaddingLeft + mPaddingRight;
        heightPadding=mPaddingTop + mPaddingBottom;
      }
      final int desiredWidth=getMeasuredWidth() - widthPadding;
      if (desiredWidth >= child.getMeasuredWidth()) {
        if (mPaddingBottom == thumbHeight && originalBottomPadding != thumbHeight) {
          mPaddingBottom=originalBottomPadding;
          measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
      }
 else {
        if (mPaddingBottom < thumbHeight) {
          originalBottomPadding=mPaddingBottom;
          mPaddingBottom=thumbHeight;
          measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
      }
    }
  }
}
