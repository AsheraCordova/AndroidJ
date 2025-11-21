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
package r.android.view;
import r.android.animation.LayoutTransition;
import r.android.graphics.Insets;
import r.android.graphics.Rect;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
public abstract class ViewGroup extends View implements ViewParent {
  private static final boolean DBG=false;
  protected ArrayList<View> mDisappearingChildren;
  protected OnHierarchyChangeListener mOnHierarchyChangeListener;
  private View mFocused;
  private View mDefaultFocus;
  View mFocusedInCluster;
  private DragEvent mCurrentDragStartEvent;
  private boolean mIsInterestedInDrag;
  private HashSet<View> mChildrenInterestedInDrag;
  private long mLastTouchDownTime;
  private int mLastTouchDownIndex=-1;
  private boolean mHoveredSelf;
  private boolean mTooltipHoveredSelf;
  protected int mGroupFlags;
  private int mLayoutMode=LAYOUT_MODE_UNDEFINED;
  static final int FLAG_CLIP_CHILDREN=0x1;
  private static final int FLAG_CLIP_TO_PADDING=0x2;
  static final int FLAG_INVALIDATE_REQUIRED=0x4;
  private static final int FLAG_RUN_ANIMATION=0x8;
  static final int FLAG_ANIMATION_DONE=0x10;
  private static final int FLAG_PADDING_NOT_NULL=0x20;
  private static final int FLAG_ANIMATION_CACHE=0x40;
  static final int FLAG_OPTIMIZE_INVALIDATE=0x80;
  static final int FLAG_CLEAR_TRANSFORMATION=0x100;
  private static final int FLAG_NOTIFY_ANIMATION_LISTENER=0x200;
  protected static final int FLAG_USE_CHILD_DRAWING_ORDER=0x400;
  protected static final int FLAG_SUPPORT_STATIC_TRANSFORMATIONS=0x800;
  private static final int FLAG_ADD_STATES_FROM_CHILDREN=0x2000;
  private static final int FLAG_ALWAYS_DRAWN_WITH_CACHE=0x4000;
  private static final int FLAG_CHILDREN_DRAWN_WITH_CACHE=0x8000;
  private static final int FLAG_NOTIFY_CHILDREN_ON_DRAWABLE_STATE_CHANGE=0x10000;
  private static final int FLAG_MASK_FOCUSABILITY=0x60000;
  public static final int FOCUS_BEFORE_DESCENDANTS=0x20000;
  public static final int FOCUS_AFTER_DESCENDANTS=0x40000;
  public static final int FOCUS_BLOCK_DESCENDANTS=0x60000;
  protected static final int FLAG_DISALLOW_INTERCEPT=0x80000;
  private static final int FLAG_SPLIT_MOTION_EVENTS=0x200000;
  private static final int FLAG_PREVENT_DISPATCH_ATTACHED_TO_WINDOW=0x400000;
  private static final int FLAG_LAYOUT_MODE_WAS_EXPLICITLY_SET=0x800000;
  static final int FLAG_IS_TRANSITION_GROUP=0x1000000;
  static final int FLAG_IS_TRANSITION_GROUP_SET=0x2000000;
  static final int FLAG_TOUCHSCREEN_BLOCKS_FOCUS=0x4000000;
  private static final int FLAG_START_ACTION_MODE_FOR_CHILD_IS_TYPED=0x8000000;
  private static final int FLAG_START_ACTION_MODE_FOR_CHILD_IS_NOT_TYPED=0x10000000;
  private static final int FLAG_SHOW_CONTEXT_MENU_WITH_COORDS=0x20000000;
  protected int mPersistentDrawingCache;
  public static final int PERSISTENT_NO_CACHE=0x0;
  public static final int PERSISTENT_ANIMATION_CACHE=0x1;
  public static final int PERSISTENT_SCROLLING_CACHE=0x2;
  public static final int PERSISTENT_ALL_CACHES=0x3;
  private static final int LAYOUT_MODE_UNDEFINED=-1;
  public static final int LAYOUT_MODE_CLIP_BOUNDS=0;
  public static final int LAYOUT_MODE_OPTICAL_BOUNDS=1;
  public static int LAYOUT_MODE_DEFAULT=LAYOUT_MODE_CLIP_BOUNDS;
  protected static final int CLIP_TO_PADDING_MASK=FLAG_CLIP_TO_PADDING | FLAG_PADDING_NOT_NULL;
  private static final int CHILD_LEFT_INDEX=0;
  private static final int CHILD_TOP_INDEX=1;
  private int mChildrenCount;
  boolean mSuppressLayout=false;
  private boolean mLayoutCalledWhileSuppressed=false;
  private static final int ARRAY_INITIAL_CAPACITY=12;
  private static final int ARRAY_CAPACITY_INCREMENT=12;
  private LayoutTransition mTransition;
  private ArrayList<View> mTransitioningViews;
  private ArrayList<View> mVisibilityChangingChildren;
  private int mChildCountWithTransientState=0;
  private int mNestedScrollAxes;
  private List<Integer> mTransientIndices=null;
  private List<View> mTransientViews=null;
  int mChildUnhandledKeyListeners=0;
  protected void onChildVisibilityChanged(  View child,  int oldVisibility,  int newVisibility){
    if (mTransition != null) {
      if (newVisibility == VISIBLE) {
        mTransition.showChild(this,child,oldVisibility);
      }
 else {
        mTransition.hideChild(this,child,newVisibility);
        if (mTransitioningViews != null && mTransitioningViews.contains(child)) {
          if (mVisibilityChangingChildren == null) {
            mVisibilityChangingChildren=new ArrayList<View>();
          }
          mVisibilityChangingChildren.add(child);
          addDisappearingView(child);
        }
      }
    }
    if (newVisibility == VISIBLE && mCurrentDragStartEvent != null) {
      if (!mChildrenInterestedInDrag.contains(child)) {
        notifyChildOfDragStart(child);
      }
    }
  }
  void dispatchAttachedToWindow(  AttachInfo info,  int visibility){
    mGroupFlags|=FLAG_PREVENT_DISPATCH_ATTACHED_TO_WINDOW;
    super.dispatchAttachedToWindow(info,visibility);
    mGroupFlags&=~FLAG_PREVENT_DISPATCH_ATTACHED_TO_WINDOW;
    final int count=mChildrenCount;
    final View[] children=mChildren;
    for (int i=0; i < count; i++) {
      final View child=children[i];
      child.dispatchAttachedToWindow(info,combineVisibility(visibility,child.getVisibility()));
    }
    final int transientCount=mTransientIndices == null ? 0 : mTransientIndices.size();
    for (int i=0; i < transientCount; ++i) {
      View view=mTransientViews.get(i);
      view.dispatchAttachedToWindow(info,combineVisibility(visibility,view.getVisibility()));
    }
  }
  boolean isLayoutModeOptical(){
    return mLayoutMode == LAYOUT_MODE_OPTICAL_BOUNDS;
  }
  public void setClipToPadding(  boolean clipToPadding){
    if (hasBooleanFlag(FLAG_CLIP_TO_PADDING) != clipToPadding) {
      setBooleanFlag(FLAG_CLIP_TO_PADDING,clipToPadding);
      invalidate(true);
    }
  }
  public boolean getClipToPadding(){
    return hasBooleanFlag(FLAG_CLIP_TO_PADDING);
  }
  protected void dispatchSetPressed(  boolean pressed){
    final View[] children=mChildren;
    final int count=mChildrenCount;
    for (int i=0; i < count; i++) {
      final View child=children[i];
      if (!pressed || (!child.isClickable() && !child.isLongClickable())) {
        child.setPressed(pressed);
      }
    }
  }
  protected <T extends View>T findViewTraversal(  int id){
    if (id == mID) {
      return (T)this;
    }
    final View[] where=mChildren;
    final int len=mChildrenCount;
    for (int i=0; i < len; i++) {
      View v=where[i];
      if ((v.mPrivateFlags & PFLAG_IS_ROOT_NAMESPACE) == 0) {
        v=v.findViewById(id);
        if (v != null) {
          return (T)v;
        }
      }
    }
    return null;
  }
  public void addView(  View child){
    addView(child,-1);
  }
  public void addView(  View child,  int index){
    if (child == null) {
      throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
    }
    LayoutParams params=child.getLayoutParams();
    if (params == null) {
      params=generateDefaultLayoutParams();
      if (params == null) {
        throw new IllegalArgumentException("generateDefaultLayoutParams() cannot return null");
      }
    }
    addView(child,index,params);
  }
  public void addView(  View child,  int width,  int height){
    final LayoutParams params=generateDefaultLayoutParams();
    params.width=width;
    params.height=height;
    addView(child,-1,params);
  }
  public void addView(  View child,  LayoutParams params){
    addView(child,-1,params);
  }
  public void addView(  View child,  int index,  LayoutParams params){
    if (DBG) {
      System.out.println(this + " addView");
    }
    if (child == null) {
      throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
    }
    requestLayout();
    invalidate(true);
    addViewInner(child,index,params,false);
  }
  protected boolean checkLayoutParams(  ViewGroup.LayoutParams p){
    return p != null;
  }
public interface OnHierarchyChangeListener {
    void onChildViewAdded(    View parent,    View child);
    void onChildViewRemoved(    View parent,    View child);
  }
  public void setOnHierarchyChangeListener(  OnHierarchyChangeListener listener){
    mOnHierarchyChangeListener=listener;
  }
  void dispatchViewAdded(  View child){
    onViewAdded(child);
    if (mOnHierarchyChangeListener != null) {
      mOnHierarchyChangeListener.onChildViewAdded(this,child);
    }
  }
  public void onViewAdded(  View child){
  }
  void dispatchViewRemoved(  View child){
    onViewRemoved(child);
    if (mOnHierarchyChangeListener != null) {
      mOnHierarchyChangeListener.onChildViewRemoved(this,child);
    }
  }
  public void onViewRemoved(  View child){
  }
  protected boolean addViewInLayout(  View child,  int index,  LayoutParams params){
    return addViewInLayout(child,index,params,false);
  }
  protected boolean addViewInLayout(  View child,  int index,  LayoutParams params,  boolean preventRequestLayout){
    if (child == null) {
      throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
    }
    child.mParent=null;
    addViewInner(child,index,params,preventRequestLayout);
    child.mPrivateFlags=(child.mPrivateFlags & ~PFLAG_DIRTY_MASK) | PFLAG_DRAWN;
    return true;
  }
  protected void cleanupLayoutState(  View child){
    child.mPrivateFlags&=~View.PFLAG_FORCE_LAYOUT;
  }
  private void addViewInner(  View child,  int index,  LayoutParams params,  boolean preventRequestLayout){
    if (mTransition != null) {
      mTransition.cancel(LayoutTransition.DISAPPEARING);
    }
    if (child.getParent() != null) {
      if(child.getParent() == this) {child.mLayoutParams=params;return;}throw new IllegalStateException("The specified child already has a parent. " + "You must call removeView() on the child's parent first.");
    }
    if (mTransition != null) {
      mTransition.addChild(this,child);
    }
    if (!checkLayoutParams(params)) {
      params=generateLayoutParams(params);
    }
    if (preventRequestLayout) {
      child.mLayoutParams=params;
    }
 else {
      child.setLayoutParams(params);
    }
    if (index < 0) {
      index=mChildrenCount;
    }
    addInArray(child,index);
    if (preventRequestLayout) {
      child.assignParent(this);
    }
 else {
      child.mParent=this;
    }
    if (child.hasUnhandledKeyListener()) {
      incrementChildUnhandledKeyListeners();
    }
    final boolean childHasFocus=child.hasFocus();
    if (childHasFocus) {
      requestChildFocus(child,child.findFocus());
    }
    AttachInfo ai=mAttachInfo;
    if (ai != null && (mGroupFlags & FLAG_PREVENT_DISPATCH_ATTACHED_TO_WINDOW) == 0) {
      boolean lastKeepOn=ai.mKeepScreenOn;
      ai.mKeepScreenOn=false;
      child.dispatchAttachedToWindow(mAttachInfo,(mViewFlags & VISIBILITY_MASK));
      if (ai.mKeepScreenOn) {
        needGlobalAttributesUpdate(true);
      }
      ai.mKeepScreenOn=lastKeepOn;
    }
    if (child.isLayoutDirectionInherited()) {
      child.resetRtlProperties();
    }
    dispatchViewAdded(child);
    if ((child.mViewFlags & DUPLICATE_PARENT_STATE) == DUPLICATE_PARENT_STATE) {
      mGroupFlags|=FLAG_NOTIFY_CHILDREN_ON_DRAWABLE_STATE_CHANGE;
    }
    if (child.hasTransientState()) {
      childHasTransientStateChanged(child,true);
    }
    if (child.getVisibility() != View.GONE) {
      notifySubtreeAccessibilityStateChangedIfNeeded();
    }
    if (mTransientIndices != null) {
      final int transientCount=mTransientIndices.size();
      for (int i=0; i < transientCount; ++i) {
        final int oldIndex=mTransientIndices.get(i);
        if (index <= oldIndex) {
          mTransientIndices.set(i,oldIndex + 1);
        }
      }
    }
    if (mCurrentDragStartEvent != null && child.getVisibility() == VISIBLE) {
      notifyChildOfDragStart(child);
    }
    if (child.hasDefaultFocus()) {
      setDefaultFocus(child);
    }
    touchAccessibilityNodeProviderIfNeeded(child);
  }
  private void addInArray(  View child,  int index){
    View[] children=mChildren;
    final int count=mChildrenCount;
    final int size=children.length;
    if (index == count) {
      if (size == count) {
        mChildren=new View[size + ARRAY_CAPACITY_INCREMENT];
        System.arraycopy(children,0,mChildren,0,size);
        children=mChildren;
      }
      children[mChildrenCount++]=child;
    }
 else     if (index < count) {
      if (size == count) {
        mChildren=new View[size + ARRAY_CAPACITY_INCREMENT];
        System.arraycopy(children,0,mChildren,0,index);
        System.arraycopy(children,index,mChildren,index + 1,count - index);
        children=mChildren;
      }
 else {
        System.arraycopy(children,index,children,index + 1,count - index);
      }
      children[index]=child;
      mChildrenCount++;
      if (mLastTouchDownIndex >= index) {
        mLastTouchDownIndex++;
      }
    }
 else {
      throw new IndexOutOfBoundsException("index=" + index + " count="+ count);
    }
  }
  private void removeFromArray(  int index){
    final View[] children=mChildren;
    if (!(mTransitioningViews != null && mTransitioningViews.contains(children[index]))) {
      children[index].mParent=null;
    }
    final int count=mChildrenCount;
    if (index == count - 1) {
      children[--mChildrenCount]=null;
    }
 else     if (index >= 0 && index < count) {
      System.arraycopy(children,index + 1,children,index,count - index - 1);
      children[--mChildrenCount]=null;
    }
 else {
      throw new IndexOutOfBoundsException();
    }
    if (mLastTouchDownIndex == index) {
      mLastTouchDownTime=0;
      mLastTouchDownIndex=-1;
    }
 else     if (mLastTouchDownIndex > index) {
      mLastTouchDownIndex--;
    }
  }
  private void removeFromArray(  int start,  int count){
    final View[] children=mChildren;
    final int childrenCount=mChildrenCount;
    start=Math.max(0,start);
    final int end=Math.min(childrenCount,start + count);
    if (start == end) {
      return;
    }
    if (end == childrenCount) {
      for (int i=start; i < end; i++) {
        children[i].mParent=null;
        children[i]=null;
      }
    }
 else {
      for (int i=start; i < end; i++) {
        children[i].mParent=null;
      }
      System.arraycopy(children,end,children,start,childrenCount - end);
      for (int i=childrenCount - (end - start); i < childrenCount; i++) {
        children[i]=null;
      }
    }
    mChildrenCount-=(end - start);
  }
  public void removeView(  View view){
    if (removeViewInternal(view)) {
      requestLayout();
      invalidate(true);
    }
  }
  public void removeViewAt(  int index){
    removeViewInternal(index,getChildAt(index));
    requestLayout();
    invalidate(true);
  }
  private boolean removeViewInternal(  View view){
    final int index=indexOfChild(view);
    if (index >= 0) {
      removeViewInternal(index,view);
      return true;
    }
    return false;
  }
  private void removeViewInternal(  int index,  View view){
    if (mTransition != null) {
      mTransition.removeChild(this,view);
    }
    boolean clearChildFocus=false;
    if (view == mFocused) {
      view.unFocus(null);
      clearChildFocus=true;
    }
    if (view == mFocusedInCluster) {
      clearFocusedInCluster(view);
    }
    view.clearAccessibilityFocus();
    cancelTouchTarget(view);
    cancelHoverTarget(view);
    if (view.getAnimation() != null || (mTransitioningViews != null && mTransitioningViews.contains(view))) {
      addDisappearingView(view);
    }
 else     if (view.mAttachInfo != null) {
      view.dispatchDetachedFromWindow();
    }
    if (view.hasTransientState()) {
      childHasTransientStateChanged(view,false);
    }
    needGlobalAttributesUpdate(false);
    removeFromArray(index);
    if (view.hasUnhandledKeyListener()) {
      decrementChildUnhandledKeyListeners();
    }
    if (view == mDefaultFocus) {
      clearDefaultFocus(view);
    }
    if (clearChildFocus) {
      clearChildFocus(view);
      if (!rootViewRequestFocus()) {
        notifyGlobalFocusCleared(this);
      }
    }
    dispatchViewRemoved(view);
    if (view.getVisibility() != View.GONE) {
      notifySubtreeAccessibilityStateChangedIfNeeded();
    }
    int transientCount=mTransientIndices == null ? 0 : mTransientIndices.size();
    for (int i=0; i < transientCount; ++i) {
      final int oldIndex=mTransientIndices.get(i);
      if (index < oldIndex) {
        mTransientIndices.set(i,oldIndex - 1);
      }
    }
    if (mCurrentDragStartEvent != null) {
      mChildrenInterestedInDrag.remove(view);
    }
  }
  public void setLayoutTransition(  LayoutTransition transition){
    if (mTransition != null) {
      LayoutTransition previousTransition=mTransition;
      previousTransition.cancel();
      previousTransition.removeTransitionListener(mLayoutTransitionListener);
    }
    mTransition=transition;
    if (mTransition != null) {
      mTransition.addTransitionListener(mLayoutTransitionListener);
    }
  }
  public LayoutTransition getLayoutTransition(){
    return mTransition;
  }
  public void removeAllViews(){
    removeAllViewsInLayout();
    requestLayout();
    invalidate(true);
  }
  public void removeAllViewsInLayout(){
    final int count=mChildrenCount;
    if (count <= 0) {
      return;
    }
    final View[] children=mChildren;
    mChildrenCount=0;
    final View focused=mFocused;
    final boolean detach=mAttachInfo != null;
    boolean clearChildFocus=false;
    needGlobalAttributesUpdate(false);
    for (int i=count - 1; i >= 0; i--) {
      final View view=children[i];
      if (mTransition != null) {
        mTransition.removeChild(this,view);
      }
      if (view == focused) {
        view.unFocus(null);
        clearChildFocus=true;
      }
      view.clearAccessibilityFocus();
      cancelTouchTarget(view);
      cancelHoverTarget(view);
      if (view.getAnimation() != null || (mTransitioningViews != null && mTransitioningViews.contains(view))) {
        addDisappearingView(view);
      }
 else       if (detach) {
        view.dispatchDetachedFromWindow();
      }
      if (view.hasTransientState()) {
        childHasTransientStateChanged(view,false);
      }
      dispatchViewRemoved(view);
      view.mParent=null;
      children[i]=null;
    }
    if (mDefaultFocus != null) {
      clearDefaultFocus(mDefaultFocus);
    }
    if (mFocusedInCluster != null) {
      clearFocusedInCluster(mFocusedInCluster);
    }
    if (clearChildFocus) {
      clearChildFocus(focused);
      if (!rootViewRequestFocus()) {
        notifyGlobalFocusCleared(focused);
      }
    }
  }
  protected void attachViewToParent(  View child,  int index,  LayoutParams params){
    child.mLayoutParams=params;
    if (index < 0) {
      index=mChildrenCount;
    }
    addInArray(child,index);
    child.mParent=this;
    child.mPrivateFlags=(child.mPrivateFlags & ~PFLAG_DIRTY_MASK & ~PFLAG_DRAWING_CACHE_VALID) | PFLAG_DRAWN | PFLAG_INVALIDATED;
    this.mPrivateFlags|=PFLAG_INVALIDATED;
    if (child.hasFocus()) {
      requestChildFocus(child,child.findFocus());
    }
    dispatchVisibilityAggregated(isAttachedToWindow() && getWindowVisibility() == VISIBLE && isShown());
    notifySubtreeAccessibilityStateChangedIfNeeded();
  }
  protected void detachViewFromParent(  View child){
    removeFromArray(indexOfChild(child));
  }
  protected void detachViewFromParent(  int index){
    removeFromArray(index);
  }
  protected void detachViewsFromParent(  int start,  int count){
    removeFromArray(start,count);
  }
  protected void detachAllViewsFromParent(){
    final int count=mChildrenCount;
    if (count <= 0) {
      return;
    }
    final View[] children=mChildren;
    mChildrenCount=0;
    for (int i=count - 1; i >= 0; i--) {
      children[i].mParent=null;
      children[i]=null;
    }
  }
  public void offsetChildrenTopAndBottom(  int offset){
    final int count=mChildrenCount;
    final View[] children=mChildren;
    boolean invalidate=false;
    for (int i=0; i < count; i++) {
      final View v=children[i];
      v.mTop+=offset;
      v.mBottom+=offset;
      if (v.mRenderNode != null) {
        invalidate=true;
        v.mRenderNode.offsetTopAndBottom(offset);
      }
    }
    if (invalidate) {
      invalidateViewProperty(false,false);
    }
    notifySubtreeAccessibilityStateChangedIfNeeded();
  }
  private boolean hasBooleanFlag(  int flag){
    return (mGroupFlags & flag) == flag;
  }
  private void setBooleanFlag(  int flag,  boolean value){
    if (value) {
      mGroupFlags|=flag;
    }
 else {
      mGroupFlags&=~flag;
    }
  }
  private void setLayoutMode(  int layoutMode,  boolean explicitly){
    mLayoutMode=layoutMode;
    setBooleanFlag(FLAG_LAYOUT_MODE_WAS_EXPLICITLY_SET,explicitly);
  }
  void invalidateInheritedLayoutMode(  int layoutModeOfRoot){
    if (mLayoutMode == LAYOUT_MODE_UNDEFINED || mLayoutMode == layoutModeOfRoot || hasBooleanFlag(FLAG_LAYOUT_MODE_WAS_EXPLICITLY_SET)) {
      return;
    }
    setLayoutMode(LAYOUT_MODE_UNDEFINED,false);
    for (int i=0, N=getChildCount(); i < N; i++) {
      getChildAt(i).invalidateInheritedLayoutMode(layoutModeOfRoot);
    }
  }
  public int getLayoutMode(){
    if (mLayoutMode == LAYOUT_MODE_UNDEFINED) {
      int inheritedLayoutMode=(mParent instanceof ViewGroup) ? ((ViewGroup)mParent).getLayoutMode() : LAYOUT_MODE_DEFAULT;
      setLayoutMode(inheritedLayoutMode,false);
    }
    return mLayoutMode;
  }
  public void setLayoutMode(  int layoutMode){
    if (mLayoutMode != layoutMode) {
      invalidateInheritedLayoutMode(layoutMode);
      setLayoutMode(layoutMode,layoutMode != LAYOUT_MODE_UNDEFINED);
      requestLayout();
    }
  }
  protected LayoutParams generateDefaultLayoutParams(){
    return new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
  }
  public int indexOfChild(  View child){
    final int count=mChildrenCount;
    final View[] children=mChildren;
    for (int i=0; i < count; i++) {
      if (children[i] == child) {
        return i;
      }
    }
    return -1;
  }
  public int getChildCount(){
    return mChildrenCount;
  }
  public View getChildAt(  int index){
    if (index < 0 || index >= mChildrenCount) {
      return null;
    }
    return mChildren[index];
  }
  protected void measureChildren(  int widthMeasureSpec,  int heightMeasureSpec){
    final int size=mChildrenCount;
    final View[] children=mChildren;
    for (int i=0; i < size; ++i) {
      final View child=children[i];
      if ((child.mViewFlags & VISIBILITY_MASK) != GONE) {
        measureChild(child,widthMeasureSpec,heightMeasureSpec);
      }
    }
  }
  protected void measureChild(  View child,  int parentWidthMeasureSpec,  int parentHeightMeasureSpec){
    final LayoutParams lp=child.getLayoutParams();
    final int childWidthMeasureSpec=getChildMeasureSpec(parentWidthMeasureSpec,mPaddingLeft + mPaddingRight,lp.width);
    final int childHeightMeasureSpec=getChildMeasureSpec(parentHeightMeasureSpec,mPaddingTop + mPaddingBottom,lp.height);
    child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
  }
  protected void measureChildWithMargins(  View child,  int parentWidthMeasureSpec,  int widthUsed,  int parentHeightMeasureSpec,  int heightUsed){
    final MarginLayoutParams lp=(MarginLayoutParams)child.getLayoutParams();
    final int childWidthMeasureSpec=getChildMeasureSpec(parentWidthMeasureSpec,mPaddingLeft + mPaddingRight + lp.leftMargin+ lp.rightMargin+ widthUsed,lp.width);
    final int childHeightMeasureSpec=getChildMeasureSpec(parentHeightMeasureSpec,mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin+ heightUsed,lp.height);
    child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
  }
  public static int getChildMeasureSpec(  int spec,  int padding,  int childDimension){
    int specMode=MeasureSpec.getMode(spec);
    int specSize=MeasureSpec.getSize(spec);
    int size=Math.max(0,specSize - padding);
    int resultSize=0;
    int resultMode=0;
switch (specMode) {
case MeasureSpec.EXACTLY:
      if (childDimension >= 0) {
        resultSize=childDimension;
        resultMode=MeasureSpec.EXACTLY;
      }
 else       if (childDimension == LayoutParams.MATCH_PARENT) {
        resultSize=size;
        resultMode=MeasureSpec.EXACTLY;
      }
 else       if (childDimension == LayoutParams.WRAP_CONTENT) {
        resultSize=size;
        resultMode=MeasureSpec.AT_MOST;
      }
    break;
case MeasureSpec.AT_MOST:
  if (childDimension >= 0) {
    resultSize=childDimension;
    resultMode=MeasureSpec.EXACTLY;
  }
 else   if (childDimension == LayoutParams.MATCH_PARENT) {
    resultSize=size;
    resultMode=MeasureSpec.AT_MOST;
  }
 else   if (childDimension == LayoutParams.WRAP_CONTENT) {
    resultSize=size;
    resultMode=MeasureSpec.AT_MOST;
  }
break;
case MeasureSpec.UNSPECIFIED:
if (childDimension >= 0) {
resultSize=childDimension;
resultMode=MeasureSpec.EXACTLY;
}
 else if (childDimension == LayoutParams.MATCH_PARENT) {
resultSize=View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
resultMode=MeasureSpec.UNSPECIFIED;
}
 else if (childDimension == LayoutParams.WRAP_CONTENT) {
resultSize=View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
resultMode=MeasureSpec.UNSPECIFIED;
}
break;
}
return MeasureSpec.makeMeasureSpec(resultSize,resultMode);
}
private void addDisappearingView(View v){
ArrayList<View> disappearingChildren=mDisappearingChildren;
if (disappearingChildren == null) {
disappearingChildren=mDisappearingChildren=new ArrayList<View>();
}
disappearingChildren.add(v);
}
public void startViewTransition(View view){
if (view.mParent == this) {
if (mTransitioningViews == null) {
mTransitioningViews=new ArrayList<View>();
}
mTransitioningViews.add(view);
}
}
public void endViewTransition(View view){
if (mTransitioningViews != null) {
mTransitioningViews.remove(view);
final ArrayList<View> disappearingChildren=mDisappearingChildren;
if (disappearingChildren != null && disappearingChildren.contains(view)) {
disappearingChildren.remove(view);
if (mVisibilityChangingChildren != null && mVisibilityChangingChildren.contains(view)) {
mVisibilityChangingChildren.remove(view);
}
 else {
if (view.mAttachInfo != null) {
view.dispatchDetachedFromWindow();
}
if (view.mParent != null) {
view.mParent=null;
}
}
invalidate();
}
}
}
private LayoutTransition.TransitionListener mLayoutTransitionListener=new LayoutTransition.TransitionListener(){
public void startTransition(LayoutTransition transition,ViewGroup container,View view,int transitionType){
if (transitionType == LayoutTransition.DISAPPEARING) {
startViewTransition(view);
}
}
public void endTransition(LayoutTransition transition,ViewGroup container,View view,int transitionType){
if (mLayoutCalledWhileSuppressed && !transition.isChangingLayout()) {
requestLayout();
mLayoutCalledWhileSuppressed=false;
}
if (transitionType == LayoutTransition.DISAPPEARING && mTransitioningViews != null) {
endViewTransition(view);
}
}
}
;
protected void drawableStateChanged(){
super.drawableStateChanged();
if ((mGroupFlags & FLAG_NOTIFY_CHILDREN_ON_DRAWABLE_STATE_CHANGE) != 0) {
if ((mGroupFlags & FLAG_ADD_STATES_FROM_CHILDREN) != 0) {
throw new IllegalStateException("addStateFromChildren cannot be enabled if a" + " child has duplicateParentState set to true");
}
final View[] children=mChildren;
final int count=mChildrenCount;
for (int i=0; i < count; i++) {
final View child=children[i];
if ((child.mViewFlags & DUPLICATE_PARENT_STATE) != 0) {
child.refreshDrawableState();
}
}
}
}
protected int[] onCreateDrawableState(int extraSpace){
if ((mGroupFlags & FLAG_ADD_STATES_FROM_CHILDREN) == 0) {
return super.onCreateDrawableState(extraSpace);
}
int need=0;
int n=getChildCount();
for (int i=0; i < n; i++) {
int[] childState=getChildAt(i).getDrawableState();
if (childState != null) {
need+=childState.length;
}
}
int[] state=super.onCreateDrawableState(extraSpace + need);
for (int i=0; i < n; i++) {
int[] childState=getChildAt(i).getDrawableState();
if (childState != null) {
state=mergeDrawableStates(state,childState);
}
}
return state;
}
public void setAddStatesFromChildren(boolean addsStates){
if (addsStates) {
mGroupFlags|=FLAG_ADD_STATES_FROM_CHILDREN;
}
 else {
mGroupFlags&=~FLAG_ADD_STATES_FROM_CHILDREN;
}
refreshDrawableState();
}
public boolean addStatesFromChildren(){
return (mGroupFlags & FLAG_ADD_STATES_FROM_CHILDREN) != 0;
}
public void childDrawableStateChanged(View child){
if ((mGroupFlags & FLAG_ADD_STATES_FROM_CHILDREN) != 0) {
refreshDrawableState();
}
}
public void requestTransitionStart(LayoutTransition transition){
ViewRootImpl viewAncestor=getViewRootImpl();
if (viewAncestor != null) {
viewAncestor.requestTransitionStart(transition);
}
}
public boolean onStartNestedScroll(View child,View target,int nestedScrollAxes){
return false;
}
public void onNestedScrollAccepted(View child,View target,int axes){
mNestedScrollAxes=axes;
}
public void onStopNestedScroll(View child){
stopNestedScroll();
mNestedScrollAxes=0;
}
public void onNestedScroll(View target,int dxConsumed,int dyConsumed,int dxUnconsumed,int dyUnconsumed){
dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,null);
}
public void onNestedPreScroll(View target,int dx,int dy,int[] consumed){
dispatchNestedPreScroll(dx,dy,consumed,null);
}
public boolean onNestedFling(View target,float velocityX,float velocityY,boolean consumed){
return dispatchNestedFling(velocityX,velocityY,consumed);
}
public boolean onNestedPreFling(View target,float velocityX,float velocityY){
return dispatchNestedPreFling(velocityX,velocityY);
}
protected void onSetLayoutParams(View child,LayoutParams layoutParams){
requestLayout();
}
public static class LayoutParams {
public static final int FILL_PARENT=-1;
public static final int MATCH_PARENT=-1;
public static final int WRAP_CONTENT=-2;
public int width;
public int height;
public LayoutParams(int width,int height){
this.width=width;
this.height=height;
}
public LayoutParams(LayoutParams source){
this.width=source.width;
this.height=source.height;
}
LayoutParams(){
}
public void resolveLayoutDirection(int layoutDirection){
}
protected static String sizeToString(int size){
if (size == WRAP_CONTENT) {
return "wrap-content";
}
if (size == MATCH_PARENT) {
return "match-parent";
}
return String.valueOf(size);
}
}
public static class MarginLayoutParams extends ViewGroup.LayoutParams {
public int leftMargin;
public int topMargin;
public int rightMargin;
public int bottomMargin;
private int startMargin=DEFAULT_MARGIN_RELATIVE;
private int endMargin=DEFAULT_MARGIN_RELATIVE;
public static final int DEFAULT_MARGIN_RELATIVE=Integer.MIN_VALUE;
byte mMarginFlags;
private static final int LAYOUT_DIRECTION_MASK=0x00000003;
private static final int LEFT_MARGIN_UNDEFINED_MASK=0x00000004;
private static final int RIGHT_MARGIN_UNDEFINED_MASK=0x00000008;
private static final int RTL_COMPATIBILITY_MODE_MASK=0x00000010;
private static final int NEED_RESOLUTION_MASK=0x00000020;
private static final int DEFAULT_MARGIN_RESOLVED=0;
private static final int UNDEFINED_MARGIN=DEFAULT_MARGIN_RELATIVE;
public MarginLayoutParams(int width,int height){
super(width,height);
mMarginFlags|=LEFT_MARGIN_UNDEFINED_MASK;
mMarginFlags|=RIGHT_MARGIN_UNDEFINED_MASK;
mMarginFlags&=~NEED_RESOLUTION_MASK;
mMarginFlags&=~RTL_COMPATIBILITY_MODE_MASK;
}
public MarginLayoutParams(MarginLayoutParams source){
this.width=source.width;
this.height=source.height;
this.leftMargin=source.leftMargin;
this.topMargin=source.topMargin;
this.rightMargin=source.rightMargin;
this.bottomMargin=source.bottomMargin;
this.startMargin=source.startMargin;
this.endMargin=source.endMargin;
this.mMarginFlags=source.mMarginFlags;
}
public MarginLayoutParams(LayoutParams source){
super(source);
mMarginFlags|=LEFT_MARGIN_UNDEFINED_MASK;
mMarginFlags|=RIGHT_MARGIN_UNDEFINED_MASK;
mMarginFlags&=~NEED_RESOLUTION_MASK;
mMarginFlags&=~RTL_COMPATIBILITY_MODE_MASK;
}
public final void copyMarginsFrom(MarginLayoutParams source){
this.leftMargin=source.leftMargin;
this.topMargin=source.topMargin;
this.rightMargin=source.rightMargin;
this.bottomMargin=source.bottomMargin;
this.startMargin=source.startMargin;
this.endMargin=source.endMargin;
this.mMarginFlags=source.mMarginFlags;
}
public void setMargins(int left,int top,int right,int bottom){
leftMargin=left;
topMargin=top;
rightMargin=right;
bottomMargin=bottom;
mMarginFlags&=~LEFT_MARGIN_UNDEFINED_MASK;
mMarginFlags&=~RIGHT_MARGIN_UNDEFINED_MASK;
if (isMarginRelative()) {
mMarginFlags|=NEED_RESOLUTION_MASK;
}
 else {
mMarginFlags&=~NEED_RESOLUTION_MASK;
}
}
public void setMarginsRelative(int start,int top,int end,int bottom){
startMargin=start;
topMargin=top;
endMargin=end;
bottomMargin=bottom;
mMarginFlags|=NEED_RESOLUTION_MASK;
}
public void setMarginStart(int start){
startMargin=start;
mMarginFlags|=NEED_RESOLUTION_MASK;
}
public int getMarginStart(){
if (startMargin != DEFAULT_MARGIN_RELATIVE) return startMargin;
if ((mMarginFlags & NEED_RESOLUTION_MASK) == NEED_RESOLUTION_MASK) {
doResolveMargins();
}
switch (mMarginFlags & LAYOUT_DIRECTION_MASK) {
case View.LAYOUT_DIRECTION_RTL:
return rightMargin;
case View.LAYOUT_DIRECTION_LTR:
default :
return leftMargin;
}
}
public void setMarginEnd(int end){
endMargin=end;
mMarginFlags|=NEED_RESOLUTION_MASK;
}
public int getMarginEnd(){
if (endMargin != DEFAULT_MARGIN_RELATIVE) return endMargin;
if ((mMarginFlags & NEED_RESOLUTION_MASK) == NEED_RESOLUTION_MASK) {
doResolveMargins();
}
switch (mMarginFlags & LAYOUT_DIRECTION_MASK) {
case View.LAYOUT_DIRECTION_RTL:
return leftMargin;
case View.LAYOUT_DIRECTION_LTR:
default :
return rightMargin;
}
}
public boolean isMarginRelative(){
return (startMargin != DEFAULT_MARGIN_RELATIVE || endMargin != DEFAULT_MARGIN_RELATIVE);
}
public void setLayoutDirection(int layoutDirection){
if (layoutDirection != View.LAYOUT_DIRECTION_LTR && layoutDirection != View.LAYOUT_DIRECTION_RTL) return;
if (layoutDirection != (mMarginFlags & LAYOUT_DIRECTION_MASK)) {
mMarginFlags&=~LAYOUT_DIRECTION_MASK;
mMarginFlags|=(layoutDirection & LAYOUT_DIRECTION_MASK);
if (isMarginRelative()) {
mMarginFlags|=NEED_RESOLUTION_MASK;
}
 else {
mMarginFlags&=~NEED_RESOLUTION_MASK;
}
}
}
public int getLayoutDirection(){
return (mMarginFlags & LAYOUT_DIRECTION_MASK);
}
public void resolveLayoutDirection(int layoutDirection){
setLayoutDirection(layoutDirection);
if (!isMarginRelative() || (mMarginFlags & NEED_RESOLUTION_MASK) != NEED_RESOLUTION_MASK) return;
doResolveMargins();
}
private void doResolveMargins(){
if ((mMarginFlags & RTL_COMPATIBILITY_MODE_MASK) == RTL_COMPATIBILITY_MODE_MASK) {
if ((mMarginFlags & LEFT_MARGIN_UNDEFINED_MASK) == LEFT_MARGIN_UNDEFINED_MASK && startMargin > DEFAULT_MARGIN_RELATIVE) {
leftMargin=startMargin;
}
if ((mMarginFlags & RIGHT_MARGIN_UNDEFINED_MASK) == RIGHT_MARGIN_UNDEFINED_MASK && endMargin > DEFAULT_MARGIN_RELATIVE) {
rightMargin=endMargin;
}
}
 else {
switch (mMarginFlags & LAYOUT_DIRECTION_MASK) {
case View.LAYOUT_DIRECTION_RTL:
leftMargin=(endMargin > DEFAULT_MARGIN_RELATIVE) ? endMargin : DEFAULT_MARGIN_RESOLVED;
rightMargin=(startMargin > DEFAULT_MARGIN_RELATIVE) ? startMargin : DEFAULT_MARGIN_RESOLVED;
break;
case View.LAYOUT_DIRECTION_LTR:
default :
leftMargin=(startMargin > DEFAULT_MARGIN_RELATIVE) ? startMargin : DEFAULT_MARGIN_RESOLVED;
rightMargin=(endMargin > DEFAULT_MARGIN_RELATIVE) ? endMargin : DEFAULT_MARGIN_RESOLVED;
break;
}
}
mMarginFlags&=~NEED_RESOLUTION_MASK;
}
public boolean isLayoutRtl(){
return ((mMarginFlags & LAYOUT_DIRECTION_MASK) == View.LAYOUT_DIRECTION_RTL);
}
}
private View[] mChildren=new View[ARRAY_INITIAL_CAPACITY];
boolean isViewTransitioning(View view){
return false;
}
public Rect getPaddingMaskBounds(){
int flags=mGroupFlags;
final boolean clipToPadding=(flags & FLAG_CLIP_TO_PADDING) == FLAG_CLIP_TO_PADDING;
Rect rect=new Rect();
if (clipToPadding) {
rect.left=mPaddingLeft;
rect.top=mPaddingTop;
rect.right=getWidth() - mPaddingRight;
rect.bottom=getHeight() - mPaddingBottom;
}
return rect;
}
public void bringToFront(){
}
public void incrementChildUnhandledKeyListeners(){
}
@Override public void invalidateChild(View child,Rect r){
}
@Override public void recomputeViewAttributes(View child){
}
@Override public void focusableViewAvailable(View v){
}
protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p){
return p;
}
private void requestChildFocus(View child,Object findFocus){
}
private void childHasTransientStateChanged(View child,boolean b){
}
private void notifyGlobalFocusCleared(Object viewGroup){
}
private boolean rootViewRequestFocus(){
return false;
}
private void clearChildFocus(View view){
}
private void cancelHoverTarget(View view){
}
private void cancelTouchTarget(View view){
}
private void touchAccessibilityNodeProviderIfNeeded(View child){
}
private void setDefaultFocus(View child){
}
private void notifyChildOfDragStart(View child){
}
private void dispatchVisibilityAggregated(boolean b){
}
private void clearFocusedInCluster(View view){
}
private void decrementChildUnhandledKeyListeners(){
}
private void clearDefaultFocus(View view){
}
class DragEvent {
}
public int measureHeightOfChildren(int widthMeasureSpec,int startPosition,int endPosition,int maxHeight,int disallowPartialChildPosition){
int height=0;
final int size=mChildrenCount;
final View[] children=mChildren;
for (int i=0; i < size; ++i) {
final View child=children[i];
if ((child.mViewFlags & VISIBILITY_MASK) != GONE) {
measureChild(child,widthMeasureSpec,-2);
}
height+=child.getMeasuredHeight();
}
return height;
}
public void setRedraw(boolean flag){
for (int i=0; i < getChildCount(); i++) {
final View child=getChildAt(i);
child.setMyAttribute("swtRedraw",flag);
}
}
public boolean hasChild(View child){
for (int i=0; i < getChildCount(); i++) {
View view=getChildAt(i);
if (view == child) {
return true;
}
}
return false;
}
}
