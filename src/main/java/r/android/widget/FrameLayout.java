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
import r.android.graphics.drawable.Drawable;
import r.android.view.Gravity;
import r.android.view.View;
import r.android.view.ViewGroup;
import java.util.ArrayList;
public class FrameLayout extends ViewGroup {
  private static final int DEFAULT_CHILD_GRAVITY=Gravity.TOP | Gravity.START;
  boolean mMeasureAllChildren=false;
  private int mForegroundPaddingLeft=0;
  private int mForegroundPaddingTop=0;
  private int mForegroundPaddingRight=0;
  private int mForegroundPaddingBottom=0;
  private final ArrayList<View> mMatchParentChildren=new ArrayList<>(1);
  public void setForegroundGravity(  int foregroundGravity){
    if (getForegroundGravity() != foregroundGravity) {
      super.setForegroundGravity(foregroundGravity);
      final Drawable foreground=getForeground();
      if (getForegroundGravity() == Gravity.FILL && foreground != null) {
        Rect padding=new Rect();
        if (foreground.getPadding(padding)) {
          mForegroundPaddingLeft=padding.left;
          mForegroundPaddingTop=padding.top;
          mForegroundPaddingRight=padding.right;
          mForegroundPaddingBottom=padding.bottom;
        }
      }
 else {
        mForegroundPaddingLeft=0;
        mForegroundPaddingTop=0;
        mForegroundPaddingRight=0;
        mForegroundPaddingBottom=0;
      }
      requestLayout();
    }
  }
  protected LayoutParams generateDefaultLayoutParams(){
    return new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
  }
  int getPaddingLeftWithForeground(){
    return isForegroundInsidePadding() ? Math.max(mPaddingLeft,mForegroundPaddingLeft) : mPaddingLeft + mForegroundPaddingLeft;
  }
  int getPaddingRightWithForeground(){
    return isForegroundInsidePadding() ? Math.max(mPaddingRight,mForegroundPaddingRight) : mPaddingRight + mForegroundPaddingRight;
  }
  private int getPaddingTopWithForeground(){
    return isForegroundInsidePadding() ? Math.max(mPaddingTop,mForegroundPaddingTop) : mPaddingTop + mForegroundPaddingTop;
  }
  private int getPaddingBottomWithForeground(){
    return isForegroundInsidePadding() ? Math.max(mPaddingBottom,mForegroundPaddingBottom) : mPaddingBottom + mForegroundPaddingBottom;
  }
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    int count=getChildCount();
    final boolean measureMatchParentChildren=MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY || MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
    mMatchParentChildren.clear();
    int maxHeight=0;
    int maxWidth=0;
    int childState=0;
    for (int i=0; i < count; i++) {
      final View child=getChildAt(i);
      if (mMeasureAllChildren || child.getVisibility() != GONE) {
        measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0);
        final LayoutParams lp=(LayoutParams)child.getLayoutParams();
        maxWidth=Math.max(maxWidth,child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
        maxHeight=Math.max(maxHeight,child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
        childState=combineMeasuredStates(childState,child.getMeasuredState());
        if (measureMatchParentChildren) {
          if (lp.width == LayoutParams.MATCH_PARENT || lp.height == LayoutParams.MATCH_PARENT) {
            mMatchParentChildren.add(child);
          }
        }
      }
    }
    maxWidth+=getPaddingLeftWithForeground() + getPaddingRightWithForeground();
    maxHeight+=getPaddingTopWithForeground() + getPaddingBottomWithForeground();
    maxHeight=Math.max(maxHeight,getSuggestedMinimumHeight());
    maxWidth=Math.max(maxWidth,getSuggestedMinimumWidth());
    final Drawable drawable=getForeground();
    if (drawable != null) {
      maxHeight=Math.max(maxHeight,drawable.getMinimumHeight());
      maxWidth=Math.max(maxWidth,drawable.getMinimumWidth());
    }
    setMeasuredDimension(resolveSizeAndState(maxWidth,widthMeasureSpec,childState),resolveSizeAndState(maxHeight,heightMeasureSpec,childState << MEASURED_HEIGHT_STATE_SHIFT));
    count=mMatchParentChildren.size();
    if (count > 1) {
      for (int i=0; i < count; i++) {
        final View child=mMatchParentChildren.get(i);
        final MarginLayoutParams lp=(MarginLayoutParams)child.getLayoutParams();
        final int childWidthMeasureSpec;
        if (lp.width == LayoutParams.MATCH_PARENT) {
          final int width=Math.max(0,getMeasuredWidth() - getPaddingLeftWithForeground() - getPaddingRightWithForeground()- lp.leftMargin- lp.rightMargin);
          childWidthMeasureSpec=MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);
        }
 else {
          childWidthMeasureSpec=getChildMeasureSpec(widthMeasureSpec,getPaddingLeftWithForeground() + getPaddingRightWithForeground() + lp.leftMargin+ lp.rightMargin,lp.width);
        }
        final int childHeightMeasureSpec;
        if (lp.height == LayoutParams.MATCH_PARENT) {
          final int height=Math.max(0,getMeasuredHeight() - getPaddingTopWithForeground() - getPaddingBottomWithForeground()- lp.topMargin- lp.bottomMargin);
          childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        }
 else {
          childHeightMeasureSpec=getChildMeasureSpec(heightMeasureSpec,getPaddingTopWithForeground() + getPaddingBottomWithForeground() + lp.topMargin+ lp.bottomMargin,lp.height);
        }
        child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
      }
    }
  }
  protected void onLayout(  boolean changed,  int left,  int top,  int right,  int bottom){
    layoutChildren(left,top,right,bottom,false);
  }
  void layoutChildren(  int left,  int top,  int right,  int bottom,  boolean forceLeftGravity){
    final int count=getChildCount();
    final int parentLeft=getPaddingLeftWithForeground();
    final int parentRight=right - left - getPaddingRightWithForeground();
    final int parentTop=getPaddingTopWithForeground();
    final int parentBottom=bottom - top - getPaddingBottomWithForeground();
    for (int i=0; i < count; i++) {
      final View child=getChildAt(i);
      if (child.getVisibility() != GONE) {
        final LayoutParams lp=(LayoutParams)child.getLayoutParams();
        final int width=child.getMeasuredWidth();
        final int height=child.getMeasuredHeight();
        int childLeft;
        int childTop;
        int gravity=lp.gravity;
        if (gravity == -1) {
          gravity=DEFAULT_CHILD_GRAVITY;
        }
        final int layoutDirection=getLayoutDirection();
        final int absoluteGravity=Gravity.getAbsoluteGravity(gravity,layoutDirection);
        final int verticalGravity=gravity & Gravity.VERTICAL_GRAVITY_MASK;
switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
case Gravity.CENTER_HORIZONTAL:
          childLeft=parentLeft + (parentRight - parentLeft - width) / 2 + lp.leftMargin - lp.rightMargin;
        break;
case Gravity.RIGHT:
      if (!forceLeftGravity) {
        childLeft=parentRight - width - lp.rightMargin;
        break;
      }
case Gravity.LEFT:
default :
    childLeft=parentLeft + lp.leftMargin;
}
switch (verticalGravity) {
case Gravity.TOP:
  childTop=parentTop + lp.topMargin;
break;
case Gravity.CENTER_VERTICAL:
childTop=parentTop + (parentBottom - parentTop - height) / 2 + lp.topMargin - lp.bottomMargin;
break;
case Gravity.BOTTOM:
childTop=parentBottom - height - lp.bottomMargin;
break;
default :
childTop=parentTop + lp.topMargin;
}
child.layout(childLeft,childTop,childLeft + width,childTop + height);
}
}
}
public void setMeasureAllChildren(boolean measureAll){
mMeasureAllChildren=measureAll;
}
public boolean getMeasureAllChildren(){
return mMeasureAllChildren;
}
protected boolean checkLayoutParams(ViewGroup.LayoutParams p){
return p instanceof LayoutParams;
}
protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp){
if (sPreserveMarginParamsInLayoutParamConversion) {
if (lp instanceof LayoutParams) {
return new LayoutParams((LayoutParams)lp);
}
 else if (lp instanceof MarginLayoutParams) {
return new LayoutParams((MarginLayoutParams)lp);
}
}
return new LayoutParams(lp);
}
public static class LayoutParams extends MarginLayoutParams {
public static final int UNSPECIFIED_GRAVITY=-1;
public int gravity=UNSPECIFIED_GRAVITY;
public LayoutParams(int width,int height){
super(width,height);
}
public LayoutParams(int width,int height,int gravity){
super(width,height);
this.gravity=gravity;
}
public LayoutParams(ViewGroup.LayoutParams source){
super(source);
}
public LayoutParams(LayoutParams source){
super(source);
this.gravity=source.gravity;
}
}
}
