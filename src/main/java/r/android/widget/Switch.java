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
 * Copyright (C) 2010 The Android Open Source Project
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
import r.android.content.res.ColorStateList;
import r.android.graphics.Insets;
import r.android.graphics.Rect;
import r.android.graphics.drawable.Drawable;
import r.android.text.TextUtils;
import r.android.view.Gravity;
public abstract class Switch extends CompoundButton {
  private static final int THUMB_ANIMATION_DURATION=250;
  private static final int TOUCH_MODE_IDLE=0;
  private static final int TOUCH_MODE_DOWN=1;
  private static final int TOUCH_MODE_DRAGGING=2;
  private static final int SANS=1;
  private static final int SERIF=2;
  private static final int MONOSPACE=3;
  private Drawable mThumbDrawable;
  private ColorStateList mThumbTintList=null;
  private boolean mHasThumbTint=false;
  private boolean mHasThumbTintMode=false;
  private Drawable mTrackDrawable;
  private ColorStateList mTrackTintList=null;
  private boolean mHasTrackTint=false;
  private boolean mHasTrackTintMode=false;
  private int mThumbTextPadding;
  private int mSwitchMinWidth;
  private int mSwitchPadding;
  private boolean mSplitTrack;
  private CharSequence mTextOn;
  private CharSequence mTextOff;
  private boolean mShowText;
  private boolean mUseFallbackLineSpacing;
  private int mTouchMode;
  private int mTouchSlop;
  private int mMinFlingVelocity;
  private int mSwitchWidth;
  private int mSwitchHeight;
  private int mThumbWidth;
  private int mSwitchLeft;
  private int mSwitchTop;
  private int mSwitchRight;
  private int mSwitchBottom;
  private Layout mOnLayout;
  private Layout mOffLayout;
  private final Rect mTempRect=new Rect();
  public void setSwitchPadding(  int pixels){
    mSwitchPadding=pixels;
    requestLayout();
  }
  public int getSwitchPadding(){
    return mSwitchPadding;
  }
  public void setSwitchMinWidth(  int pixels){
    mSwitchMinWidth=pixels;
    requestLayout();
  }
  public int getSwitchMinWidth(){
    return mSwitchMinWidth;
  }
  public void setTrackTintList(  ColorStateList tint){
    mTrackTintList=tint;
    mHasTrackTint=true;
    applyTrackTint();
  }
  public ColorStateList getTrackTintList(){
    return mTrackTintList;
  }
  public void setThumbTintList(  ColorStateList tint){
    mThumbTintList=tint;
    mHasThumbTint=true;
    applyThumbTint();
  }
  public ColorStateList getThumbTintList(){
    return mThumbTintList;
  }
  public void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    if (mShowText) {
      if (mOnLayout == null) {
        mOnLayout=makeLayout(mTextOn);
      }
      if (mOffLayout == null) {
        mOffLayout=makeLayout(mTextOff);
      }
    }
    final Rect padding=mTempRect;
    final int thumbWidth;
    final int thumbHeight;
    if (mThumbDrawable != null) {
      mThumbDrawable.getPadding(padding);
      thumbWidth=mThumbDrawable.getIntrinsicWidth() - padding.left - padding.right;
      thumbHeight=mThumbDrawable.getIntrinsicHeight();
    }
 else {
      thumbWidth=0;
      thumbHeight=0;
    }
    final int maxTextWidth;
    if (mShowText) {
      maxTextWidth=Math.max(mOnLayout.getWidth(),mOffLayout.getWidth()) + mThumbTextPadding * 2;
    }
 else {
      maxTextWidth=0;
    }
    mThumbWidth=Math.max(maxTextWidth,thumbWidth);
    final int trackHeight;
    if (mTrackDrawable != null) {
      mTrackDrawable.getPadding(padding);
      trackHeight=mTrackDrawable.getIntrinsicHeight();
    }
 else {
      padding.setEmpty();
      trackHeight=0;
    }
    int paddingLeft=padding.left;
    int paddingRight=padding.right;
    if (mThumbDrawable != null) {
      final Insets inset=mThumbDrawable.getOpticalInsets();
      paddingLeft=Math.max(paddingLeft,inset.left);
      paddingRight=Math.max(paddingRight,inset.right);
    }
    final int switchWidth=Math.max(Math.max(mSwitchMinWidth,2 * mThumbWidth + paddingLeft + paddingRight), intrinsicWidth);
    final int switchHeight=Math.max(Math.max(trackHeight,thumbHeight), intrinsicHeight);
    mSwitchWidth=switchWidth;
    mSwitchHeight=switchHeight;
    super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    final int measuredHeight=getMeasuredHeight();
    if (measuredHeight < switchHeight) {
      setMeasuredDimension(getMeasuredWidthAndState(),switchHeight);
    }
  }
  protected void onLayout(  boolean changed,  int left,  int top,  int right,  int bottom){
    super.onLayout(changed,left,top,right,bottom);
    int opticalInsetLeft=0;
    int opticalInsetRight=0;
    if (mThumbDrawable != null) {
      final Rect trackPadding=mTempRect;
      if (mTrackDrawable != null) {
        mTrackDrawable.getPadding(trackPadding);
      }
 else {
        trackPadding.setEmpty();
      }
      final Insets insets=mThumbDrawable.getOpticalInsets();
      opticalInsetLeft=Math.max(0,insets.left - trackPadding.left);
      opticalInsetRight=Math.max(0,insets.right - trackPadding.right);
    }
    final int switchRight;
    final int switchLeft;
    if (isLayoutRtl()) {
      switchLeft=getPaddingLeft() + opticalInsetLeft;
      switchRight=switchLeft + mSwitchWidth - opticalInsetLeft - opticalInsetRight;
    }
 else {
      switchRight=getWidth() - getPaddingRight() - opticalInsetRight;
      switchLeft=switchRight - mSwitchWidth + opticalInsetLeft + opticalInsetRight;
    }
    final int switchTop;
    final int switchBottom;
switch (getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
default :
case Gravity.TOP:
      switchTop=getPaddingTop();
    switchBottom=switchTop + mSwitchHeight;
  break;
case Gravity.CENTER_VERTICAL:
switchTop=(getPaddingTop() + getHeight() - getPaddingBottom()) / 2 - mSwitchHeight / 2;
switchBottom=switchTop + mSwitchHeight;
break;
case Gravity.BOTTOM:
switchBottom=getHeight() - getPaddingBottom();
switchTop=switchBottom - mSwitchHeight;
break;
}
mSwitchLeft=switchLeft;
mSwitchTop=switchTop;
mSwitchBottom=switchBottom;
mSwitchRight=switchRight;
}
public int getCompoundPaddingLeft(){
if (!isLayoutRtl()) {
return super.getCompoundPaddingLeft();
}
int padding=super.getCompoundPaddingLeft() + mSwitchWidth;
if (!TextUtils.isEmpty(getText())) {
padding+=mSwitchPadding;
}
return padding;
}
public int getCompoundPaddingRight(){
if (isLayoutRtl()) {
return super.getCompoundPaddingRight();
}
int padding=super.getCompoundPaddingRight() + mSwitchWidth;
if (!TextUtils.isEmpty(getText())) {
padding+=mSwitchPadding;
}
return padding;
}
private String text;
private int intrinsicWidth;
private int intrinsicHeight;
public String getText(){
return text;
}
public void setText(String text){
this.text=text;
}
public void setIntrinsicWidth(int intrinsicWidth){
this.intrinsicWidth=intrinsicWidth;
}
public Switch(com.ashera.widget.IWidget widget){
super(widget);
}
public void setIntrinsicHeight(int intrinsicHeight){
this.intrinsicHeight=intrinsicHeight;
}
public r.android.graphics.Rect getSwitchBounds(){
return new r.android.graphics.Rect(mSwitchLeft,mSwitchTop,mSwitchRight,mSwitchBottom);
}
class Layout {
int getWidth(){
return 0;
}
}
Layout makeLayout(CharSequence text){
return new Layout();
}
@Override public com.ashera.model.RectM getRightDrawableBounds(int x,int y,int width,int height){
com.ashera.model.RectM rightBounds=super.getRightDrawableBounds(x,y,width,height);
rightBounds.x=rightBounds.x - intrinsicWidth;
return rightBounds;
}
@Override public com.ashera.model.RectM getBottomDrawableBounds(int x,int y,int width,int height){
com.ashera.model.RectM bottomBounds=super.getBottomDrawableBounds(x - intrinsicWidth,y,width - intrinsicWidth,height);
return bottomBounds;
}
@Override public com.ashera.model.RectM getTopDrawableBounds(int x,int y,int width,int height){
com.ashera.model.RectM topBounds=super.getTopDrawableBounds(x - intrinsicWidth,y,width - intrinsicWidth,height);
return topBounds;
}
private void applyTrackTint(){
}
private void applyThumbTint(){
}
}
