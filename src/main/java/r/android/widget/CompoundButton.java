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
import r.android.content.res.ColorStateList;
import r.android.graphics.Canvas;
import r.android.graphics.drawable.Drawable;
import r.android.view.Gravity;
public abstract class CompoundButton extends Button {
  private boolean mChecked;
  private boolean mBroadcasting;
  private Drawable mButtonDrawable;
  private ColorStateList mButtonTintList=null;
  private boolean mHasButtonTint=false;
  private boolean mHasButtonBlendMode=false;
  private OnCheckedChangeListener mOnCheckedChangeListener;
  private OnCheckedChangeListener mOnCheckedChangeWidgetListener;
  private boolean mCheckedFromResource=false;
  public void toggle(){
    setChecked(!mChecked);
  }
  public boolean isChecked(){
    return mChecked;
  }
  public void setChecked(  boolean checked){
    if (mChecked != checked) {
      mCheckedFromResource=false;
      mChecked=checked;
      refreshDrawableState();
      if (mBroadcasting) {
        return;
      }
      mBroadcasting=true;
      if (mOnCheckedChangeListener != null) {
        mOnCheckedChangeListener.onCheckedChanged(this,mChecked);
      }
      if (mOnCheckedChangeWidgetListener != null) {
        mOnCheckedChangeWidgetListener.onCheckedChanged(this,mChecked);
      }
      final AutofillManager afm=mContext.getSystemService(AutofillManager.class);
      if (afm != null) {
        afm.notifyValueChanged(this);
      }
      mBroadcasting=false;
    }
    setDefaultStateDescritption();
  }
  public void setOnCheckedChangeListener(  OnCheckedChangeListener listener){
    mOnCheckedChangeListener=listener;
  }
  void setOnCheckedChangeWidgetListener(  OnCheckedChangeListener listener){
    mOnCheckedChangeWidgetListener=listener;
  }
public static interface OnCheckedChangeListener {
    void onCheckedChanged(    CompoundButton buttonView,    boolean isChecked);
  }
  public void setButtonDrawable(  int resId){
    final Drawable d;
    if (resId != 0) {
      d=getContext().getDrawable(resId);
    }
 else {
      d=null;
    }
    setButtonDrawable(d);
  }
  public void setButtonDrawable(  Drawable drawable){
    if (mButtonDrawable != drawable) {
      if (mButtonDrawable != null) {
        mButtonDrawable.setCallback(null);
        unscheduleDrawable(mButtonDrawable);
      }
      mButtonDrawable=drawable;
      if (drawable != null) {
        drawable.setCallback(this);
        drawable.setLayoutDirection(getLayoutDirection());
        if (drawable.isStateful()) {
          drawable.setState(getDrawableState());
        }
        drawable.setVisible(getVisibility() == VISIBLE,false);
        setMinHeight(drawable.getIntrinsicHeight());
        applyButtonTint();
      }
    }
  }
  public Drawable getButtonDrawable(){
    return mButtonDrawable;
  }
  public void setButtonTintList(  ColorStateList tint){
    mButtonTintList=tint;
    mHasButtonTint=true;
    applyButtonTint();
  }
  public ColorStateList getButtonTintList(){
    return mButtonTintList;
  }
  public int getCompoundPaddingLeft(){
    int padding=super.getCompoundPaddingLeft();
    if (!isLayoutRtl()) {
      final Drawable buttonDrawable=mButtonDrawable;
      if (buttonDrawable != null) {
        padding+=buttonDrawable.getIntrinsicWidth();
      }
    }
    return padding;
  }
  public int getCompoundPaddingRight(){
    int padding=super.getCompoundPaddingRight();
    if (isLayoutRtl()) {
      final Drawable buttonDrawable=mButtonDrawable;
      if (buttonDrawable != null) {
        padding+=buttonDrawable.getIntrinsicWidth();
      }
    }
    return padding;
  }
  public int getHorizontalOffsetForDrawables(){
    final Drawable buttonDrawable=mButtonDrawable;
    return (buttonDrawable != null) ? buttonDrawable.getIntrinsicWidth() : 0;
  }
  protected void onDraw(  Canvas canvas){
    final Drawable buttonDrawable=mButtonDrawable;
    if (buttonDrawable != null) {
      final int verticalGravity=getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
      final int drawableHeight=buttonDrawable.getIntrinsicHeight();
      final int drawableWidth=buttonDrawable.getIntrinsicWidth();
      final int top;
switch (verticalGravity) {
case Gravity.BOTTOM:
        top=getHeight() - drawableHeight;
      break;
case Gravity.CENTER_VERTICAL:
    top=(getHeight() - drawableHeight) / 2;
  break;
default :
top=0;
}
final int bottom=top + drawableHeight;
final int left=isLayoutRtl() ? getWidth() - drawableWidth : 0;
final int right=isLayoutRtl() ? getWidth() : drawableWidth;
buttonDrawable.setBounds(left,top,right,bottom);
final Drawable background=getBackground();
if (background != null) {
background.setHotspotBounds(left,top,right,bottom);
}
}
super.onDraw(canvas);
if (buttonDrawable != null) {
final int scrollX=mScrollX;
final int scrollY=mScrollY;
if (scrollX == 0 && scrollY == 0) {
buttonDrawable.draw(canvas);
}
 else {
canvas.translate(scrollX,scrollY);
buttonDrawable.draw(canvas);
canvas.translate(-scrollX,-scrollY);
}
}
}
protected int[] onCreateDrawableState(int extraSpace){
final int[] drawableState=super.onCreateDrawableState(extraSpace + 1);
if (isChecked()) {
mergeDrawableStates(drawableState,CHECKED_STATE_SET);
}
return drawableState;
}
private static final int[] CHECKED_STATE_SET={r.android.R.attr.state_checked};
public CompoundButton(com.ashera.widget.IWidget widget){
super(widget);
mContext=new r.android.content.Context();
}
public void invalidateDrawable(Drawable drawable){
}
public void applyButtonTint(){
}
public void setDefaultStateDescritption(){
}
interface AutofillManager {
public void notifyValueChanged(CompoundButton compoundButton);
}
@Override public com.ashera.model.RectM getLeftDrawableBounds(int x,int y,int width,int height){
com.ashera.model.RectM leftBounds=super.getLeftDrawableBounds(x,y,width,height);
if (mButtonDrawable != null) {
leftBounds.x=leftBounds.x + mButtonDrawable.getMinimumWidth();
}
return leftBounds;
}
@Override public boolean hasDrawables(){
return mButtonDrawable != null || super.hasDrawables();
}
}
