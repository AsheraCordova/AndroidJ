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
import r.android.util.SparseBooleanArray;
import r.android.view.View;
import r.android.view.ViewGroup;
public class TableLayout extends LinearLayout {
  private int[] mMaxWidths;
  private SparseBooleanArray mStretchableColumns;
  private SparseBooleanArray mShrinkableColumns;
  private SparseBooleanArray mCollapsedColumns;
  private boolean mShrinkAllColumns;
  private boolean mStretchAllColumns;
  private boolean mInitialized;
  private void requestRowsLayout(){
    if (mInitialized) {
      final int count=getChildCount();
      for (int i=0; i < count; i++) {
        getChildAt(i).requestLayout();
      }
    }
  }
  public void setShrinkAllColumns(  boolean shrinkAllColumns){
    mShrinkAllColumns=shrinkAllColumns;
  }
  public void setStretchAllColumns(  boolean stretchAllColumns){
    mStretchAllColumns=stretchAllColumns;
  }
  public void setColumnCollapsed(  int columnIndex,  boolean isCollapsed){
    mCollapsedColumns.put(columnIndex,isCollapsed);
    int count=getChildCount();
    for (int i=0; i < count; i++) {
      final View view=getChildAt(i);
      if (view instanceof TableRow) {
        ((TableRow)view).setColumnCollapsed(columnIndex,isCollapsed);
      }
    }
    requestRowsLayout();
  }
  public void setColumnStretchable(  int columnIndex,  boolean isStretchable){
    mStretchableColumns.put(columnIndex,isStretchable);
    requestRowsLayout();
  }
  public void setColumnShrinkable(  int columnIndex,  boolean isShrinkable){
    mShrinkableColumns.put(columnIndex,isShrinkable);
    requestRowsLayout();
  }
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    measureVertical(widthMeasureSpec,heightMeasureSpec);
  }
  protected void onLayout(  boolean changed,  int l,  int t,  int r,  int b){
    layoutVertical(l,t,r,b);
  }
  void measureChildBeforeLayout(  View child,  int childIndex,  int widthMeasureSpec,  int totalWidth,  int heightMeasureSpec,  int totalHeight){
    if (child instanceof TableRow) {
      ((TableRow)child).setColumnsWidthConstraints(mMaxWidths);
    }
    super.measureChildBeforeLayout(child,childIndex,widthMeasureSpec,totalWidth,heightMeasureSpec,totalHeight);
  }
  void measureVertical(  int widthMeasureSpec,  int heightMeasureSpec){
    findLargestCells(widthMeasureSpec,heightMeasureSpec);
    shrinkAndStretchColumns(widthMeasureSpec);
    super.measureVertical(widthMeasureSpec,heightMeasureSpec);
  }
  private void findLargestCells(  int widthMeasureSpec,  int heightMeasureSpec){
    boolean firstRow=true;
    final int count=getChildCount();
    for (int i=0; i < count; i++) {
      final View child=getChildAt(i);
      if (child.getVisibility() == GONE) {
        continue;
      }
      if (child instanceof TableRow) {
        final TableRow row=(TableRow)child;
        final ViewGroup.LayoutParams layoutParams=row.getLayoutParams();
        layoutParams.height=LayoutParams.WRAP_CONTENT;
        final int[] widths=row.getColumnsWidths(widthMeasureSpec,heightMeasureSpec);
        final int newLength=widths.length;
        if (firstRow) {
          if (mMaxWidths == null || mMaxWidths.length != newLength) {
            mMaxWidths=new int[newLength];
          }
          System.arraycopy(widths,0,mMaxWidths,0,newLength);
          firstRow=false;
        }
 else {
          int length=mMaxWidths.length;
          final int difference=newLength - length;
          if (difference > 0) {
            final int[] oldMaxWidths=mMaxWidths;
            mMaxWidths=new int[newLength];
            System.arraycopy(oldMaxWidths,0,mMaxWidths,0,oldMaxWidths.length);
            System.arraycopy(widths,oldMaxWidths.length,mMaxWidths,oldMaxWidths.length,difference);
          }
          final int[] maxWidths=mMaxWidths;
          length=Math.min(length,newLength);
          for (int j=0; j < length; j++) {
            maxWidths[j]=Math.max(maxWidths[j],widths[j]);
          }
        }
      }
    }
  }
  private void shrinkAndStretchColumns(  int widthMeasureSpec){
    if (mMaxWidths == null) {
      return;
    }
    int totalWidth=0;
    for (    int width : mMaxWidths) {
      totalWidth+=width;
    }
    int size=MeasureSpec.getSize(widthMeasureSpec) - mPaddingLeft - mPaddingRight;
    if ((totalWidth > size) && (mShrinkAllColumns || mShrinkableColumns.size() > 0)) {
      mutateColumnsWidth(mShrinkableColumns,mShrinkAllColumns,size,totalWidth);
    }
 else     if ((totalWidth < size) && (mStretchAllColumns || mStretchableColumns.size() > 0)) {
      mutateColumnsWidth(mStretchableColumns,mStretchAllColumns,size,totalWidth);
    }
  }
  private void mutateColumnsWidth(  SparseBooleanArray columns,  boolean allColumns,  int size,  int totalWidth){
    int skipped=0;
    final int[] maxWidths=mMaxWidths;
    final int length=maxWidths.length;
    final int count=allColumns ? length : columns.size();
    final int totalExtraSpace=size - totalWidth;
    int extraSpace=totalExtraSpace / count;
    final int nbChildren=getChildCount();
    for (int i=0; i < nbChildren; i++) {
      View child=getChildAt(i);
      if (child instanceof TableRow) {
        child.forceLayout();
      }
    }
    if (!allColumns) {
      for (int i=0; i < count; i++) {
        int column=columns.keyAt(i);
        if (columns.valueAt(i)) {
          if (column < length) {
            maxWidths[column]+=extraSpace;
          }
 else {
            skipped++;
          }
        }
      }
    }
 else {
      for (int i=0; i < count; i++) {
        maxWidths[i]+=extraSpace;
      }
      return;
    }
    if (skipped > 0 && skipped < count) {
      extraSpace=skipped * extraSpace / (count - skipped);
      for (int i=0; i < count; i++) {
        int column=columns.keyAt(i);
        if (columns.valueAt(i) && column < length) {
          if (extraSpace > maxWidths[column]) {
            maxWidths[column]=0;
          }
 else {
            maxWidths[column]+=extraSpace;
          }
        }
      }
    }
  }
  protected LinearLayout.LayoutParams generateDefaultLayoutParams(){
    return new LayoutParams();
  }
  protected boolean checkLayoutParams(  ViewGroup.LayoutParams p){
    return p instanceof TableLayout.LayoutParams;
  }
  protected LinearLayout.LayoutParams generateLayoutParams(  ViewGroup.LayoutParams p){
    return new LayoutParams(p);
  }
public static class LayoutParams extends LinearLayout.LayoutParams {
    public LayoutParams(    int w,    int h){
      super(MATCH_PARENT,h);
    }
    public LayoutParams(){
      super(MATCH_PARENT,WRAP_CONTENT);
    }
    public LayoutParams(    ViewGroup.LayoutParams p){
      super(p);
      width=MATCH_PARENT;
    }
  }
  public TableLayout(){
    if (mCollapsedColumns == null) {
      mCollapsedColumns=new SparseBooleanArray();
    }
    if (mStretchableColumns == null) {
      mStretchableColumns=new SparseBooleanArray();
    }
    if (mShrinkableColumns == null) {
      mShrinkableColumns=new SparseBooleanArray();
    }
    setOrientation(VERTICAL);
    mInitialized=true;
  }
}
