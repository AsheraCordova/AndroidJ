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
 * Copyright (C) 2007 The Android Open Source Project
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
import r.android.util.SparseIntArray;
import r.android.view.Gravity;
import r.android.view.View;
import r.android.view.ViewGroup;
public class TableRow extends LinearLayout {
  private int mNumColumns=0;
  private int[] mColumnWidths;
  private int[] mConstrainedColumnWidths;
  private SparseIntArray mColumnToChildIndex;
  void setColumnCollapsed(  int columnIndex,  boolean collapsed){
    final View child=getVirtualChildAt(columnIndex);
    if (child != null) {
      child.setVisibility(collapsed ? GONE : VISIBLE);
    }
  }
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    measureHorizontal(widthMeasureSpec,heightMeasureSpec);
  }
  protected void onLayout(  boolean changed,  int l,  int t,  int r,  int b){
    layoutHorizontal(l,t,r,b);
  }
  public View getVirtualChildAt(  int i){
    if (mColumnToChildIndex == null) {
      mapIndexAndColumns();
    }
    final int deflectedIndex=mColumnToChildIndex.get(i,-1);
    if (deflectedIndex != -1) {
      return getChildAt(deflectedIndex);
    }
    return null;
  }
  public int getVirtualChildCount(){
    if (mColumnToChildIndex == null) {
      mapIndexAndColumns();
    }
    return mNumColumns;
  }
  private void mapIndexAndColumns(){
    if (mColumnToChildIndex == null) {
      int virtualCount=0;
      final int count=getChildCount();
      mColumnToChildIndex=new SparseIntArray();
      final SparseIntArray columnToChild=mColumnToChildIndex;
      for (int i=0; i < count; i++) {
        final View child=getChildAt(i);
        final LayoutParams layoutParams=(LayoutParams)child.getLayoutParams();
        if (layoutParams.column >= virtualCount) {
          virtualCount=layoutParams.column;
        }
        for (int j=0; j < layoutParams.span; j++) {
          columnToChild.put(virtualCount++,i);
        }
      }
      mNumColumns=virtualCount;
    }
  }
  int measureNullChild(  int childIndex){
    return mConstrainedColumnWidths[childIndex];
  }
  void measureChildBeforeLayout(  View child,  int childIndex,  int widthMeasureSpec,  int totalWidth,  int heightMeasureSpec,  int totalHeight){
    if (mConstrainedColumnWidths != null) {
      final LayoutParams lp=(LayoutParams)child.getLayoutParams();
      int measureMode=MeasureSpec.EXACTLY;
      int columnWidth=0;
      final int span=lp.span;
      final int[] constrainedColumnWidths=mConstrainedColumnWidths;
      for (int i=0; i < span; i++) {
        columnWidth+=constrainedColumnWidths[childIndex + i];
      }
      final int gravity=lp.gravity;
      final boolean isHorizontalGravity=Gravity.isHorizontal(gravity);
      if (isHorizontalGravity) {
        measureMode=MeasureSpec.AT_MOST;
      }
      int childWidthMeasureSpec=MeasureSpec.makeMeasureSpec(Math.max(0,columnWidth - lp.leftMargin - lp.rightMargin),measureMode);
      int childHeightMeasureSpec=getChildMeasureSpec(heightMeasureSpec,mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin+ totalHeight,lp.height);
      child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
      if (isHorizontalGravity) {
        final int childWidth=child.getMeasuredWidth();
        lp.mOffset[LayoutParams.LOCATION_NEXT]=columnWidth - childWidth;
        final int layoutDirection=getLayoutDirection();
        final int absoluteGravity=Gravity.getAbsoluteGravity(gravity,layoutDirection);
switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
case Gravity.LEFT:
          break;
case Gravity.RIGHT:
        lp.mOffset[LayoutParams.LOCATION]=lp.mOffset[LayoutParams.LOCATION_NEXT];
      break;
case Gravity.CENTER_HORIZONTAL:
    lp.mOffset[LayoutParams.LOCATION]=lp.mOffset[LayoutParams.LOCATION_NEXT] / 2;
  break;
}
}
 else {
lp.mOffset[LayoutParams.LOCATION]=lp.mOffset[LayoutParams.LOCATION_NEXT]=0;
}
}
 else {
super.measureChildBeforeLayout(child,childIndex,widthMeasureSpec,totalWidth,heightMeasureSpec,totalHeight);
}
}
int getChildrenSkipCount(View child,int index){
LayoutParams layoutParams=(LayoutParams)child.getLayoutParams();
return layoutParams.span - 1;
}
int getLocationOffset(View child){
return ((TableRow.LayoutParams)child.getLayoutParams()).mOffset[LayoutParams.LOCATION];
}
int getNextLocationOffset(View child){
return ((TableRow.LayoutParams)child.getLayoutParams()).mOffset[LayoutParams.LOCATION_NEXT];
}
int[] getColumnsWidths(int widthMeasureSpec,int heightMeasureSpec){
final int numColumns=getVirtualChildCount();
if (mColumnWidths == null || numColumns != mColumnWidths.length) {
mColumnWidths=new int[numColumns];
}
final int[] columnWidths=mColumnWidths;
for (int i=0; i < numColumns; i++) {
final View child=getVirtualChildAt(i);
if (child != null && child.getVisibility() != GONE) {
final LayoutParams layoutParams=(LayoutParams)child.getLayoutParams();
if (layoutParams.span == 1) {
int spec;
switch (layoutParams.width) {
case LayoutParams.WRAP_CONTENT:
  spec=getChildMeasureSpec(widthMeasureSpec,0,LayoutParams.WRAP_CONTENT);
break;
case LayoutParams.MATCH_PARENT:
spec=MeasureSpec.makeSafeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec),MeasureSpec.UNSPECIFIED);
break;
default :
spec=MeasureSpec.makeMeasureSpec(layoutParams.width,MeasureSpec.EXACTLY);
}
child.measure(spec,spec);
final int width=child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
columnWidths[i]=width;
}
 else {
columnWidths[i]=0;
}
}
 else {
columnWidths[i]=0;
}
}
return columnWidths;
}
void setColumnsWidthConstraints(int[] columnWidths){
if (columnWidths == null || columnWidths.length < getVirtualChildCount()) {
throw new IllegalArgumentException("columnWidths should be >= getVirtualChildCount()");
}
mConstrainedColumnWidths=columnWidths;
}
protected LinearLayout.LayoutParams generateDefaultLayoutParams(){
return new LayoutParams();
}
protected boolean checkLayoutParams(ViewGroup.LayoutParams p){
return p instanceof TableRow.LayoutParams;
}
protected LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p){
return new LayoutParams(p);
}
public static class LayoutParams extends LinearLayout.LayoutParams {
public int column;
public int span;
private static final int LOCATION=0;
private static final int LOCATION_NEXT=1;
private int[] mOffset=new int[2];
public LayoutParams(int w,int h){
super(w,h);
column=-1;
span=1;
}
public LayoutParams(){
super(MATCH_PARENT,WRAP_CONTENT);
column=-1;
span=1;
}
public LayoutParams(ViewGroup.LayoutParams p){
super(p);
}
}
}
