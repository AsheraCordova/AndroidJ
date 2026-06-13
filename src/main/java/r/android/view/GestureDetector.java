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
 * Copyright (C) 2008 The Android Open Source Project
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
import r.android.content.Context;
import r.android.os.Handler;
import r.android.os.Message;
public class GestureDetector {
public interface OnGestureListener {
    boolean onDown(    MotionEvent e);
    void onShowPress(    MotionEvent e);
    boolean onSingleTapUp(    MotionEvent e);
    boolean onScroll(    MotionEvent e1,    MotionEvent e2,    float distanceX,    float distanceY);
    void onLongPress(    MotionEvent e);
    boolean onFling(    MotionEvent e1,    MotionEvent e2,    float velocityX,    float velocityY);
  }
public interface OnDoubleTapListener {
    boolean onSingleTapConfirmed(    MotionEvent e);
    boolean onDoubleTap(    MotionEvent e);
    boolean onDoubleTapEvent(    MotionEvent e);
  }
public interface OnContextClickListener {
    boolean onContextClick(    MotionEvent e);
  }
public static class SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener, OnContextClickListener {
    public boolean onSingleTapUp(    MotionEvent e){
      return false;
    }
    public void onLongPress(    MotionEvent e){
    }
    public boolean onScroll(    MotionEvent e1,    MotionEvent e2,    float distanceX,    float distanceY){
      return false;
    }
    public boolean onFling(    MotionEvent e1,    MotionEvent e2,    float velocityX,    float velocityY){
      return false;
    }
    public void onShowPress(    MotionEvent e){
    }
    public boolean onDown(    MotionEvent e){
      return false;
    }
    public boolean onDoubleTap(    MotionEvent e){
      return false;
    }
    public boolean onDoubleTapEvent(    MotionEvent e){
      return false;
    }
    public boolean onSingleTapConfirmed(    MotionEvent e){
      return false;
    }
    public boolean onContextClick(    MotionEvent e){
      return false;
    }
  }
  private int mTouchSlopSquare;
  private int mDoubleTapTouchSlopSquare;
  private int mDoubleTapSlopSquare;
  private float mAmbiguousGestureMultiplier;
  private int mMinimumFlingVelocity;
  private int mMaximumFlingVelocity;
  private static final int LONGPRESS_TIMEOUT=ViewConfiguration.getLongPressTimeout();
  private static final int TAP_TIMEOUT=ViewConfiguration.getTapTimeout();
  private static final int DOUBLE_TAP_TIMEOUT=ViewConfiguration.getDoubleTapTimeout();
  private static final int DOUBLE_TAP_MIN_TIME=ViewConfiguration.getDoubleTapMinTime();
  private static final int SHOW_PRESS=1;
  private static final int LONG_PRESS=2;
  private static final int TAP=3;
  private final Handler mHandler;
  private final OnGestureListener mListener;
  private OnDoubleTapListener mDoubleTapListener;
  private OnContextClickListener mContextClickListener;
  private boolean mStillDown;
  private boolean mDeferConfirmSingleTap;
  private boolean mInLongPress;
  private boolean mInContextClick;
  private boolean mAlwaysInTapRegion;
  private boolean mAlwaysInBiggerTapRegion;
  private boolean mIgnoreNextUpEvent;
  private boolean mHasRecordedClassification;
  private MotionEvent mCurrentDownEvent;
  private MotionEvent mCurrentMotionEvent;
  private MotionEvent mPreviousUpEvent;
  private boolean mIsDoubleTapping;
  private float mLastFocusX;
  private float mLastFocusY;
  private float mDownFocusX;
  private float mDownFocusY;
  private boolean mIsLongpressEnabled;
  private VelocityTracker mVelocityTracker;
  private int mVelocityTrackerStrategy;
private class GestureHandler extends Handler {
    GestureHandler(){
      super();
    }
    GestureHandler(    Handler handler){
      super(handler.getLooper());
    }
    public void handleMessage(    Message msg){
switch (msg.what) {
case SHOW_PRESS:
        mListener.onShowPress(mCurrentDownEvent);
      break;
case LONG_PRESS:
    recordGestureClassification(msg.arg1);
  dispatchLongPress();
break;
case TAP:
if (mDoubleTapListener != null) {
if (!mStillDown) {
  recordGestureClassification(TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__SINGLE_TAP);
  mDoubleTapListener.onSingleTapConfirmed(mCurrentDownEvent);
}
 else {
  mDeferConfirmSingleTap=true;
}
}
break;
default :
throw new RuntimeException("Unknown message " + msg);
}
}
}
public GestureDetector(OnGestureListener listener,Handler handler){
this(null,listener,handler);
}
public GestureDetector(OnGestureListener listener){
this(null,listener,null);
}
public GestureDetector(Context context,OnGestureListener listener){
this(context,listener,null);
}
public GestureDetector(Context context,OnGestureListener listener,Handler handler){
this(context,listener,handler,VelocityTracker.VELOCITY_TRACKER_STRATEGY_DEFAULT);
}
public GestureDetector(Context context,OnGestureListener listener,Handler handler,int velocityTrackerStrategy){
if (handler != null) {
mHandler=new GestureHandler(handler);
}
 else {
mHandler=new GestureHandler();
}
mListener=listener;
if (listener instanceof OnDoubleTapListener) {
setOnDoubleTapListener((OnDoubleTapListener)listener);
}
if (listener instanceof OnContextClickListener) {
setContextClickListener((OnContextClickListener)listener);
}
mVelocityTrackerStrategy=velocityTrackerStrategy;
init(context);
}
private void init(Context context){
if (mListener == null) {
throw new NullPointerException("OnGestureListener must not be null");
}
mIsLongpressEnabled=true;
int touchSlop, doubleTapSlop, doubleTapTouchSlop;
if (context == null) {
touchSlop=ViewConfiguration.getTouchSlop();
doubleTapTouchSlop=touchSlop;
doubleTapSlop=ViewConfiguration.getDoubleTapSlop();
mMinimumFlingVelocity=ViewConfiguration.getMinimumFlingVelocity();
mMaximumFlingVelocity=ViewConfiguration.getMaximumFlingVelocity();
mAmbiguousGestureMultiplier=ViewConfiguration.getAmbiguousGestureMultiplier();
}
 else {
//StrictMode.assertConfigurationContext(context,"GestureDetector#init");
final ViewConfiguration configuration=ViewConfiguration.get(context);
touchSlop=configuration.getScaledTouchSlop();
doubleTapTouchSlop=configuration.getScaledDoubleTapTouchSlop();
doubleTapSlop=configuration.getScaledDoubleTapSlop();
mMinimumFlingVelocity=configuration.getScaledMinimumFlingVelocity();
mMaximumFlingVelocity=configuration.getScaledMaximumFlingVelocity();
mAmbiguousGestureMultiplier=configuration.getScaledAmbiguousGestureMultiplier();
}
mTouchSlopSquare=touchSlop * touchSlop;
mDoubleTapTouchSlopSquare=doubleTapTouchSlop * doubleTapTouchSlop;
mDoubleTapSlopSquare=doubleTapSlop * doubleTapSlop;
}
public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener){
mDoubleTapListener=onDoubleTapListener;
}
public void setContextClickListener(OnContextClickListener onContextClickListener){
mContextClickListener=onContextClickListener;
}
public boolean onTouchEvent(MotionEvent ev){
if (false) {
//mInputEventConsistencyVerifier.onTouchEvent(ev,0);
}
final int action=ev.getAction();
if (mCurrentMotionEvent != null) {
mCurrentMotionEvent.recycle();
}
mCurrentMotionEvent=MotionEvent.obtain(ev);
if (mVelocityTracker == null) {
mVelocityTracker=VelocityTracker.obtain(mVelocityTrackerStrategy);
}
mVelocityTracker.addMovement(ev);
final boolean pointerUp=(action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP;
final int skipIndex=pointerUp ? ev.getActionIndex() : -1;
final boolean isGeneratedGesture=(ev.getFlags() & MotionEvent.FLAG_IS_GENERATED_GESTURE) != 0;
float sumX=0, sumY=0;
final int count=ev.getPointerCount();
for (int i=0; i < count; i++) {
if (skipIndex == i) continue;
sumX+=ev.getX(i);
sumY+=ev.getY(i);
}
final int div=pointerUp ? count - 1 : count;
final float focusX=sumX / div;
final float focusY=sumY / div;
boolean handled=false;
switch (action & MotionEvent.ACTION_MASK) {
case MotionEvent.ACTION_POINTER_DOWN:
mDownFocusX=mLastFocusX=focusX;
mDownFocusY=mLastFocusY=focusY;
cancelTaps();
break;
case MotionEvent.ACTION_POINTER_UP:
mDownFocusX=mLastFocusX=focusX;
mDownFocusY=mLastFocusY=focusY;
mVelocityTracker.computeCurrentVelocity(1000,mMaximumFlingVelocity);
final int upIndex=ev.getActionIndex();
final int id1=ev.getPointerId(upIndex);
final float x1=mVelocityTracker.getXVelocity(id1);
final float y1=mVelocityTracker.getYVelocity(id1);
for (int i=0; i < count; i++) {
if (i == upIndex) continue;
final int id2=ev.getPointerId(i);
final float x=x1 * mVelocityTracker.getXVelocity(id2);
final float y=y1 * mVelocityTracker.getYVelocity(id2);
final float dot=x + y;
if (dot < 0) {
mVelocityTracker.clear();
break;
}
}
break;
case MotionEvent.ACTION_DOWN:
if (mDoubleTapListener != null) {
boolean hadTapMessage=mHandler.hasMessages(TAP);
if (hadTapMessage) mHandler.removeMessages(TAP);
if ((mCurrentDownEvent != null) && (mPreviousUpEvent != null) && hadTapMessage&& isConsideredDoubleTap(mCurrentDownEvent,mPreviousUpEvent,ev)) {
mIsDoubleTapping=true;
recordGestureClassification(TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__DOUBLE_TAP);
handled|=mDoubleTapListener.onDoubleTap(mCurrentDownEvent);
handled|=mDoubleTapListener.onDoubleTapEvent(ev);
}
 else {
mHandler.sendEmptyMessageDelayed(TAP,DOUBLE_TAP_TIMEOUT);
}
}
mDownFocusX=mLastFocusX=focusX;
mDownFocusY=mLastFocusY=focusY;
if (mCurrentDownEvent != null) {
mCurrentDownEvent.recycle();
}
mCurrentDownEvent=MotionEvent.obtain(ev);
mAlwaysInTapRegion=true;
mAlwaysInBiggerTapRegion=true;
mStillDown=true;
mInLongPress=false;
mDeferConfirmSingleTap=false;
mHasRecordedClassification=false;
if (mIsLongpressEnabled) {
mHandler.removeMessages(LONG_PRESS);
mHandler.sendMessageAtTime(mHandler.obtainMessage(LONG_PRESS,TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__LONG_PRESS,0),mCurrentDownEvent.getDownTime() + ViewConfiguration.getLongPressTimeout());
}
mHandler.sendEmptyMessageAtTime(SHOW_PRESS,mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
handled|=mListener.onDown(ev);
break;
case MotionEvent.ACTION_MOVE:
if (mInLongPress || mInContextClick) {
break;
}
final int motionClassification=ev.getClassification();
final boolean hasPendingLongPress=mHandler.hasMessages(LONG_PRESS);
final float scrollX=mLastFocusX - focusX;
final float scrollY=mLastFocusY - focusY;
if (mIsDoubleTapping) {
recordGestureClassification(TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__DOUBLE_TAP);
handled|=mDoubleTapListener.onDoubleTapEvent(ev);
}
 else if (mAlwaysInTapRegion) {
final int deltaX=(int)(focusX - mDownFocusX);
final int deltaY=(int)(focusY - mDownFocusY);
int distance=(deltaX * deltaX) + (deltaY * deltaY);
int slopSquare=isGeneratedGesture ? 0 : mTouchSlopSquare;
final boolean ambiguousGesture=motionClassification == MotionEvent.CLASSIFICATION_AMBIGUOUS_GESTURE;
final boolean shouldInhibitDefaultAction=hasPendingLongPress && ambiguousGesture;
if (shouldInhibitDefaultAction) {
if (distance > slopSquare) {
mHandler.removeMessages(LONG_PRESS);
final long longPressTimeout=ViewConfiguration.getLongPressTimeout();
mHandler.sendMessageAtTime(mHandler.obtainMessage(LONG_PRESS,TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__LONG_PRESS,0),ev.getDownTime() + (long)(longPressTimeout * mAmbiguousGestureMultiplier));
}
slopSquare*=mAmbiguousGestureMultiplier * mAmbiguousGestureMultiplier;
}
if (distance > slopSquare) {
recordGestureClassification(TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__SCROLL);
handled=mListener.onScroll(mCurrentDownEvent,ev,scrollX,scrollY);
mLastFocusX=focusX;
mLastFocusY=focusY;
mAlwaysInTapRegion=false;
mHandler.removeMessages(TAP);
mHandler.removeMessages(SHOW_PRESS);
mHandler.removeMessages(LONG_PRESS);
}
int doubleTapSlopSquare=isGeneratedGesture ? 0 : mDoubleTapTouchSlopSquare;
if (distance > doubleTapSlopSquare) {
mAlwaysInBiggerTapRegion=false;
}
}
 else if ((Math.abs(scrollX) >= 1) || (Math.abs(scrollY) >= 1)) {
recordGestureClassification(TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__SCROLL);
handled=mListener.onScroll(mCurrentDownEvent,ev,scrollX,scrollY);
mLastFocusX=focusX;
mLastFocusY=focusY;
}
final boolean deepPress=motionClassification == MotionEvent.CLASSIFICATION_DEEP_PRESS;
if (deepPress && hasPendingLongPress) {
mHandler.removeMessages(LONG_PRESS);
mHandler.sendMessage(mHandler.obtainMessage(LONG_PRESS,TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__DEEP_PRESS,0));
}
break;
case MotionEvent.ACTION_UP:
mStillDown=false;
MotionEvent currentUpEvent=MotionEvent.obtain(ev);
if (mIsDoubleTapping) {
recordGestureClassification(TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__DOUBLE_TAP);
handled|=mDoubleTapListener.onDoubleTapEvent(ev);
}
 else if (mInLongPress) {
mHandler.removeMessages(TAP);
mInLongPress=false;
}
 else if (mAlwaysInTapRegion && !mIgnoreNextUpEvent) {
recordGestureClassification(TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__SINGLE_TAP);
handled=mListener.onSingleTapUp(ev);
if (mDeferConfirmSingleTap && mDoubleTapListener != null) {
mDoubleTapListener.onSingleTapConfirmed(ev);
}
}
 else if (!mIgnoreNextUpEvent) {
final VelocityTracker velocityTracker=mVelocityTracker;
final int pointerId=ev.getPointerId(0);
velocityTracker.computeCurrentVelocity(1000,mMaximumFlingVelocity);
final float velocityY=velocityTracker.getYVelocity(pointerId);
final float velocityX=velocityTracker.getXVelocity(pointerId);
if ((Math.abs(velocityY) > mMinimumFlingVelocity) || (Math.abs(velocityX) > mMinimumFlingVelocity)) {
handled=mListener.onFling(mCurrentDownEvent,ev,velocityX,velocityY);
}
}
if (mPreviousUpEvent != null) {
mPreviousUpEvent.recycle();
}
mPreviousUpEvent=currentUpEvent;
if (mVelocityTracker != null) {
mVelocityTracker.recycle();
mVelocityTracker=null;
}
mIsDoubleTapping=false;
mDeferConfirmSingleTap=false;
mIgnoreNextUpEvent=false;
mHandler.removeMessages(SHOW_PRESS);
mHandler.removeMessages(LONG_PRESS);
break;
case MotionEvent.ACTION_CANCEL:
cancel();
break;
}
if (!handled && false) {
//mInputEventConsistencyVerifier.onUnhandledEvent(ev,0);
}
return handled;
}
private void cancel(){
mHandler.removeMessages(SHOW_PRESS);
mHandler.removeMessages(LONG_PRESS);
mHandler.removeMessages(TAP);
if (mVelocityTracker != null) {
mVelocityTracker.recycle();
mVelocityTracker=null;
}
mIsDoubleTapping=false;
mStillDown=false;
mAlwaysInTapRegion=false;
mAlwaysInBiggerTapRegion=false;
mDeferConfirmSingleTap=false;
mInLongPress=false;
mInContextClick=false;
mIgnoreNextUpEvent=false;
}
private void cancelTaps(){
mHandler.removeMessages(SHOW_PRESS);
mHandler.removeMessages(LONG_PRESS);
mHandler.removeMessages(TAP);
mIsDoubleTapping=false;
mAlwaysInTapRegion=false;
mAlwaysInBiggerTapRegion=false;
mDeferConfirmSingleTap=false;
mInLongPress=false;
mInContextClick=false;
mIgnoreNextUpEvent=false;
}
private boolean isConsideredDoubleTap(MotionEvent firstDown,MotionEvent firstUp,MotionEvent secondDown){
if (!mAlwaysInBiggerTapRegion) {
return false;
}
final long deltaTime=secondDown.getEventTime() - firstUp.getEventTime();
if (deltaTime > DOUBLE_TAP_TIMEOUT || deltaTime < DOUBLE_TAP_MIN_TIME) {
return false;
}
int deltaX=(int)firstDown.getX() - (int)secondDown.getX();
int deltaY=(int)firstDown.getY() - (int)secondDown.getY();
final boolean isGeneratedGesture=(firstDown.getFlags() & MotionEvent.FLAG_IS_GENERATED_GESTURE) != 0;
int slopSquare=isGeneratedGesture ? 0 : mDoubleTapSlopSquare;
return (deltaX * deltaX + deltaY * deltaY < slopSquare);
}
private void dispatchLongPress(){
mHandler.removeMessages(TAP);
mDeferConfirmSingleTap=false;
mInLongPress=true;
mListener.onLongPress(mCurrentDownEvent);
}
private final static int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__SCROLL=1;
private final static int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__LONG_PRESS=0;
private final static int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__DEEP_PRESS=2;
private final static int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__DOUBLE_TAP=2;
private final static int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__SINGLE_TAP=3;
private void recordGestureClassification(int touchGestureClassifiedClassificationSingleTap){
}
}
