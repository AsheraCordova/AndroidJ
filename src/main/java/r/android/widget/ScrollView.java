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
 * Copyright (C) 2006 The Android Open Source Project
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
import r.android.os.Build.VERSION_CODES;
import r.android.view.View;
import r.android.view.ViewGroup;
public class ScrollView extends FrameLayout {
  static final int ANIMATED_SCROLL_GAP=250;
  private int mLastMotionY;
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
  private int mNestedYOffset;
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
    final int heightMode=MeasureSpec.getMode(heightMeasureSpec);
    if (heightMode == MeasureSpec.UNSPECIFIED) {
      return;
    }
    if (getChildCount() > 0) {
      final View child=getChildAt(0);
      final int widthPadding;
      final int heightPadding;
      final int targetSdkVersion=getContext().getApplicationInfo().targetSdkVersion;
      final FrameLayout.LayoutParams lp=(LayoutParams)child.getLayoutParams();
      if (targetSdkVersion >= VERSION_CODES.M) {
        widthPadding=mPaddingLeft + mPaddingRight + lp.leftMargin+ lp.rightMargin;
        heightPadding=mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin;
      }
 else {
        widthPadding=mPaddingLeft + mPaddingRight;
        heightPadding=mPaddingTop + mPaddingBottom;
      }
      final int desiredHeight=getMeasuredHeight() - heightPadding;
      if (child.getMeasuredHeight() < desiredHeight) {
        final int childWidthMeasureSpec=getChildMeasureSpec(widthMeasureSpec,widthPadding,lp.width);
        final int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(desiredHeight,MeasureSpec.EXACTLY);
        child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
      }
    }
  }
  protected int computeVerticalScrollRange(){
    final int count=getChildCount();
    final int contentHeight=getHeight() - mPaddingBottom - mPaddingTop;
    if (count == 0) {
      return contentHeight;
    }
    int scrollRange=getChildAt(0).getBottom();
    final int scrollY=mScrollY;
    final int overscrollBottom=Math.max(0,scrollRange - contentHeight);
    if (scrollY < 0) {
      scrollRange-=scrollY;
    }
 else     if (scrollY > overscrollBottom) {
      scrollRange+=scrollY - overscrollBottom;
    }
    return scrollRange;
  }
  protected void measureChild(  View child,  int parentWidthMeasureSpec,  int parentHeightMeasureSpec){
    ViewGroup.LayoutParams lp=child.getLayoutParams();
    int childWidthMeasureSpec;
    int childHeightMeasureSpec;
    childWidthMeasureSpec=getChildMeasureSpec(parentWidthMeasureSpec,mPaddingLeft + mPaddingRight,lp.width);
    final int verticalPadding=mPaddingTop + mPaddingBottom;
    childHeightMeasureSpec=MeasureSpec.makeSafeMeasureSpec(Math.max(0,MeasureSpec.getSize(parentHeightMeasureSpec) - verticalPadding),MeasureSpec.UNSPECIFIED);
    child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
  }
  protected void measureChildWithMargins(  View child,  int parentWidthMeasureSpec,  int widthUsed,  int parentHeightMeasureSpec,  int heightUsed){
    final MarginLayoutParams lp=(MarginLayoutParams)child.getLayoutParams();
    final int childWidthMeasureSpec=getChildMeasureSpec(parentWidthMeasureSpec,mPaddingLeft + mPaddingRight + lp.leftMargin+ lp.rightMargin+ widthUsed,lp.width);
    final int usedTotal=mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin+ heightUsed;
    final int childHeightMeasureSpec=MeasureSpec.makeSafeMeasureSpec(Math.max(0,MeasureSpec.getSize(parentHeightMeasureSpec) - usedTotal),MeasureSpec.UNSPECIFIED);
    child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
  }
  public void requestLayout(){
    mIsLayoutDirty=true;
    super.requestLayout();
  }
  private int originalRightPadding=0;
  public void adjustPaddingIfScrollBarPresent(  int widthMeasureSpec,  int heightMeasureSpec,  int thumbWidth){
    if (getChildCount() > 0) {
      final View child=getChildAt(0);
      final int widthPadding;
      final int heightPadding;
      final int targetSdkVersion=getContext().getApplicationInfo().targetSdkVersion;
      final FrameLayout.LayoutParams lp=(LayoutParams)child.getLayoutParams();
      if (targetSdkVersion >= VERSION_CODES.M) {
        widthPadding=mPaddingLeft + mPaddingRight + lp.leftMargin+ lp.rightMargin;
        heightPadding=mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin;
      }
 else {
        widthPadding=mPaddingLeft + mPaddingRight;
        heightPadding=mPaddingTop + mPaddingBottom;
      }
      final int desiredHeight=getMeasuredHeight() - heightPadding;
      if (desiredHeight >= child.getMeasuredHeight()) {
        if (mPaddingRight == thumbWidth && originalRightPadding != thumbWidth) {
          mPaddingRight=originalRightPadding;
          measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
      }
 else {
        if (mPaddingRight < thumbWidth) {
          originalRightPadding=mPaddingRight;
          mPaddingRight=thumbWidth;
          measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
      }
    }
  }
}
