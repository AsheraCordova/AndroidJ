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
 * Copyright (C) 2013 The Android Open Source Project
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
package r.android.view;
import r.android.animation.LayoutTransition;
import r.android.annotation.NonNull;
import r.android.content.Context;
import r.android.graphics.Canvas;
import r.android.graphics.Rect;
import r.android.graphics.drawable.Drawable;
import java.util.ArrayList;
public class ViewOverlay {
  OverlayViewGroup mOverlayViewGroup;
  ViewOverlay(  Context context,  View hostView){
    mOverlayViewGroup=new OverlayViewGroup(context,hostView);
  }
  ViewGroup getOverlayView(){
    return mOverlayViewGroup;
  }
  public void add(  Drawable drawable){
    mOverlayViewGroup.add(drawable);
  }
  public void remove(  Drawable drawable){
    mOverlayViewGroup.remove(drawable);
  }
  public void clear(){
    mOverlayViewGroup.clear();
  }
  boolean isEmpty(){
    return mOverlayViewGroup.isEmpty();
  }
static class OverlayViewGroup extends ViewGroup {
    final View mHostView;
    ArrayList<Drawable> mDrawables=null;
    OverlayViewGroup(    Context context,    View hostView){
      //super(context);
      mHostView=hostView;
      mAttachInfo=mHostView.mAttachInfo;
      mRight=hostView.getWidth();
      mBottom=hostView.getHeight();
      mRenderNode.setLeftTopRightBottom(0,0,mRight,mBottom);
    }
    public void add(    Drawable drawable){
      if (drawable == null) {
        throw new IllegalArgumentException("drawable must be non-null");
      }
      if (mDrawables == null) {
        mDrawables=new ArrayList<>();
      }
      if (!mDrawables.contains(drawable)) {
        mDrawables.add(drawable);
        invalidate(drawable.getBounds());
        drawable.setCallback(this);
      }
    }
    public void remove(    Drawable drawable){
      if (drawable == null) {
        throw new IllegalArgumentException("drawable must be non-null");
      }
      if (mDrawables != null) {
        mDrawables.remove(drawable);
        invalidate(drawable.getBounds());
        drawable.setCallback(null);
      }
    }
    protected boolean verifyDrawable(    Drawable who){
      return super.verifyDrawable(who) || (mDrawables != null && mDrawables.contains(who));
    }
    public void add(    View child){
      if (child == null) {
        throw new IllegalArgumentException("view must be non-null");
      }
      if (child.getParent() instanceof ViewGroup) {
        ViewGroup parent=(ViewGroup)child.getParent();
        if (parent != mHostView && parent.getParent() != null && parent.mAttachInfo != null) {
          int[] parentLocation=new int[2];
          int[] hostViewLocation=new int[2];
          parent.getLocationOnScreen(parentLocation);
          mHostView.getLocationOnScreen(hostViewLocation);
          child.offsetLeftAndRight(parentLocation[0] - hostViewLocation[0]);
          child.offsetTopAndBottom(parentLocation[1] - hostViewLocation[1]);
        }
        parent.removeView(child);
        if (parent.getLayoutTransition() != null) {
          parent.getLayoutTransition().cancel(LayoutTransition.DISAPPEARING);
        }
        if (child.getParent() != null) {
          child.mParent=null;
        }
      }
      super.addView(child);
    }
    public void remove(    View view){
      if (view == null) {
        throw new IllegalArgumentException("view must be non-null");
      }
      super.removeView(view);
    }
    public void clear(){
      removeAllViews();
      if (mDrawables != null) {
        for (        Drawable drawable : mDrawables) {
          drawable.setCallback(null);
        }
        mDrawables.clear();
      }
    }
    boolean isEmpty(){
      if (getChildCount() == 0 && (mDrawables == null || mDrawables.size() == 0)) {
        return true;
      }
      return false;
    }
    public void invalidateDrawable(    Drawable drawable){
      invalidate(drawable.getBounds());
    }
    protected void dispatchDraw(    Canvas canvas){
      //canvas.enableZ();
      //super.dispatchDraw(canvas);
      //canvas.disableZ();
      final int numDrawables=(mDrawables == null) ? 0 : mDrawables.size();
      for (int i=0; i < numDrawables; ++i) {
        mDrawables.get(i).draw(canvas);
      }
    }
    protected void onLayout(    boolean changed,    int l,    int t,    int r,    int b){
    }
    public void invalidate(    Rect dirty){
      super.invalidate(dirty);
      if (mHostView != null) {
        mHostView.invalidate(dirty);
      }
    }
    public void invalidate(    int l,    int t,    int r,    int b){
      super.invalidate(l,t,r,b);
      if (mHostView != null) {
        mHostView.invalidate(l,t,r,b);
      }
    }
    public void invalidate(){
      super.invalidate();
      if (mHostView != null) {
        mHostView.invalidate();
      }
    }
    public void invalidate(    boolean invalidateCache){
      super.invalidate(invalidateCache);
      if (mHostView != null) {
        mHostView.invalidate(invalidateCache);
      }
    }
    void invalidateViewProperty(    boolean invalidateParent,    boolean forceRedraw){
      super.invalidateViewProperty(invalidateParent,forceRedraw);
      if (mHostView != null) {
        mHostView.invalidateViewProperty(invalidateParent,forceRedraw);
      }
    }
    protected void invalidateParentCaches(){
      super.invalidateParentCaches();
      if (mHostView != null) {
        mHostView.invalidateParentCaches();
      }
    }
    protected void invalidateParentIfNeeded(){
      super.invalidateParentIfNeeded();
      if (mHostView != null) {
        mHostView.invalidateParentIfNeeded();
      }
    }
  }
  public java.util.List<Drawable> getDrawables(){
    return mOverlayViewGroup.mDrawables;
  }
}
