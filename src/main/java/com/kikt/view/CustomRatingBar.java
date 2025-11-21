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
package com.kikt.view;
import r.android.content.Context;
import r.android.view.MotionEvent;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
public class CustomRatingBar extends ViewGroup {
  protected int mMaxStar;
  private int mPadding;
  protected int mStarWidth;
  protected int mStarHeight;
  protected LayoutParams childParams;
  protected float stars;
  private float lastStars;
  protected float mMinStar;
  protected Object mEmptyStar;
  protected Object mFullStar;
  protected Object mHalfStar;
  List<ImageView> list=new ArrayList<ImageView>();
  private ImageView createChild(){
    ImageView imageView=createImageView();
    //setEmptyView(imageView);;
    childParams=generateDefaultLayoutParams();
    childParams.width=mStarWidth;
    childParams.height=mStarHeight;
    imageView.setLayoutParams(childParams);
    return imageView;
  }
  private boolean isCanChange;
  public boolean onTouchEvent(  MotionEvent event){
switch (event.getAction()) {
case MotionEvent.ACTION_DOWN:
case MotionEvent.ACTION_MOVE:
      if (isCanChange) {
        float x=event.getX();
        int current=checkX(x);
        stars=fixStars(current);
        checkState();
        break;
      }
 else {
        return false;
      }
  }
  return true;
}
public OnRatingBarChangeListener getOnRatingBarChangeListener(){
  return onRatingChangedListener;
}
public void setOnRatingBarChangeListener(OnRatingBarChangeListener onRatingChangedListener){
  this.onRatingChangedListener=onRatingChangedListener;
}
public void removeOnStarChangeLisetener(){
  this.onRatingChangedListener=null;
}
public interface OnRatingBarChangeListener {
  void onRatingChanged(  CustomRatingBar ratingBar,  float star);
}
private OnRatingBarChangeListener onRatingChangedListener;
private void checkState(){
  if (lastStars != stars) {
    lastStars=stars;
    if (onRatingChangedListener != null) {
      onRatingChangedListener.onRatingChanged(this,stars / 2);
    }
    setView();
  }
}
private void setView(){
  if (stars < mMinStar * 2) {
    stars=mMinStar * 2;
  }
  int stars=(int)this.stars;
  if (stars % 2 == 0) {
    for (int i=0; i < mMaxStar; i++) {
      if (i < stars / 2) {
        setFullView(list.get(i));
      }
 else {
        setEmptyView(list.get(i));
      }
    }
  }
 else {
    for (int i=0; i < mMaxStar; i++) {
      if (i < stars / 2) {
        setFullView(list.get(i));
      }
 else       if (i == stars / 2) {
        setHalfView(list.get(i));
      }
 else {
        setEmptyView(list.get(i));
      }
    }
  }
}
protected void setEmptyView(ImageView view){
  view.setMyAttribute("src", mEmptyStar); if (mEmptyStarTint != null) { view.setMyAttribute("tint", mEmptyStarTint); }
}
protected void setHalfView(ImageView view){
  view.setMyAttribute("src", mHalfStar); if (mHalfStarTint != null) { view.setMyAttribute("tint", mHalfStarTint); }
}
protected void setFullView(ImageView view){
  view.setMyAttribute("src", mFullStar); if (mFullStarTint != null) { view.setMyAttribute("tint", mFullStarTint); }
}
public int getMax(){
  return mMaxStar;
}
public void setMax(int mCount){
  this.mMaxStar=mCount;
}
public int getPadding(){
  return mPadding;
}
public void setPadding(int mPadding){
  this.mPadding=mPadding;
}
public int getStarWidth(){
  return mStarWidth;
}
public void setStarWidth(int mStarWidth){
  this.mStarWidth=mStarWidth;
}
public int getStarHeight(){
  return mStarHeight;
}
public void setStarHeight(int mStarHeight){
  this.mStarHeight=mStarHeight;
}
public float getMinStar(){
  return mMinStar;
}
public void setMinStar(float mMinStar){
  this.mMinStar=mMinStar;
}
public void setStars(float stars){
  this.stars=stars;
}
public float getStars(){
  return stars;
}
public void setCanChange(boolean canChange){
  isCanChange=canChange;
}
public boolean isCanChange(){
  return isCanChange;
}
private float fixStars(int current){
  if (current > mMaxStar * 2) {
    return mMaxStar * 2;
  }
 else   if (current < mMinStar * 2) {
    return mMinStar * 2;
  }
  return current;
}
private int checkX(float x){
  int width=getWidth();
  int per=width / mMaxStar / 2;
  return (int)(x / per) + 1;
}
protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
  measureChildren(widthMeasureSpec,heightMeasureSpec);
  int childCount=getChildCount();
  int width=0;
  int height=0;
  for (int i=0; i < childCount; i++) {
    View child=getChildAt(i);
    int measuredWidth=child.getMeasuredWidth();
    int measuredHeight=child.getMeasuredHeight();
    width+=measuredWidth;
    height=measuredHeight;
    if (i != childCount - 1) {
      width+=mPadding;
    }
  }
  setMeasuredDimension(width,height);
}
protected void onLayout(boolean changed,int l,int t,int r,int b){
  t=0;
  l=0;
  int childCount=getChildCount();
  for (int i=0; i < childCount; i++) {
    View child=getChildAt(i);
    r=l + child.getMeasuredWidth();
    b=t + child.getMeasuredHeight();
    child.layout(l,t,r,b);
    l=r + mPadding;
  }
}
public CustomRatingBar(){
  mMaxStar=5;
  mPadding=(int)com.ashera.widget.PluginInvoker.convertDpToPixel("10dp");
  mStarWidth=(int)com.ashera.widget.PluginInvoker.convertDpToPixel("40dp");
  mStarHeight=(int)com.ashera.widget.PluginInvoker.convertDpToPixel("40dp");
  mMinStar=0;
  stars=0 * 2;
  mEmptyStar="@drawable/star_empty";
  mHalfStar="@drawable/star_half";
  mFullStar="@drawable/star_full";
  isCanChange=true;
}
public void initStars(){
  list.clear();
  ((com.ashera.widget.HasWidgets)((com.ashera.widget.ILifeCycleDecorator)this).getWidget()).clear();
  for (int i=0; i < mMaxStar; i++) {
    ImageView child=createChild();
    if (!hasChild(child)) {
      addView(child);
    }
    list.add(child);
  }
  setView();
}
private ImageView createImageView(){
  return (ImageView)com.ashera.widget.WidgetFactory.createWidget("ImageView","ImageView",(com.ashera.widget.HasWidgets)((com.ashera.widget.ILifeCycleDecorator)this).getWidget(),false).asWidget();
}
public void setEmptyStar(Object mEmptyStar){
  this.mEmptyStar=mEmptyStar;
}
public void setFullStar(Object mFullStar){
  this.mFullStar=mFullStar;
}
public void setHalfStar(Object mHalfStar){
  this.mHalfStar=mHalfStar;
}
private Object mEmptyStarTint;
private Object mFullStarTint;
private Object mHalfStarTint;
public void setEmptyStarTint(Object mEmptyStarTint){
  this.mEmptyStarTint=mEmptyStarTint;
}
public void setFullStarTint(Object mFullStarTint){
  this.mFullStarTint=mFullStarTint;
}
public void setHalfStarTint(Object mHalfStarTint){
  this.mHalfStarTint=mHalfStarTint;
}
public void setRating(float stars){
  this.stars=stars * 2;
}
public float getRating(){
  return stars / 2;
}
}
