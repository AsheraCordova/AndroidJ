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
package r.android.view;

import java.util.concurrent.atomic.AtomicInteger;

public class MotionEvent{
    public static final int ACTION_DOWN  = 0;
    public static final int ACTION_UP  = 1;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_CANCEL = 3;
    public static final int ACTION_OUTSIDE = 4;
    public static final int ACTION_POINTER_DOWN = 5;
    public static final int ACTION_POINTER_UP = 6;
    public static final int ACTION_HOVER_MOVE = 7;
    public static final int ACTION_SCROLL = 8;
   	public static final int ACTION_BUTTON_PRESS   = 11;
    public static final int ACTION_BUTTON_RELEASE  = 12;
    public static final int CLASSIFICATION_AMBIGUOUS_GESTURE = 1;
    public static final int CLASSIFICATION_DEEP_PRESS = 2;
    public static final int ACTION_MASK = 0xff;
    public static final int BUTTON_SECONDARY = 1 << 1;
    public static final int BUTTON_PRIMARY = 1 << 0;
    public static final int FLAG_IS_GENERATED_GESTURE = 0x8;
    private static final boolean TRACK_RECYCLED_LOCATION = false;
	
    private static final int MAX_RECYCLED = 10;
	private int x;
	private int y;
	private int rawX;
	private int rawY;
	private int action;
	private long downTime;
	private long eventTime;
	public int getAction(){
	return action;
	}
	public void setAction(int action){
	this.action=action;
	}
	public int getX(){
	return x;
	}
	public void setX(int x){
	this.x=x;
	}
	public int getY(){
	return y;
	}
	public void setY(int y){
	this.y=y;
	}
	public int getRawX() {
		return rawX;
	}
	public void setRawX(int rawX) {
		this.rawX = rawX;
	}
	public int getRawY() {
		return rawY;
	}
	public void setRawY(int rawY) {
		this.rawY = rawY;
	}
	public int getEventTime() {
		return 0;
	}
	public void offsetLocation(int i, int j) {
		x += i;
		y += j;
	}
	public static MotionEvent obtain(MotionEvent ev) {
		MotionEvent m = obtain();
		m.downTime = ev.downTime;
		m.eventTime = ev.eventTime;
		m.x = ev.x;
		m.y = ev.y;
		m.rawX = ev.rawX;
		m.rawY = ev.rawY;
		m.action = ev.action;
		return m;
	}

	public int findPointerIndex(int mActivePointerId) {
		return 0;
	}
	public int getActionMasked() {
		return action;
	}
	public int getPointerId(int i) {
		return 0;
	}
	public float getX(int pointerIndex) {
		return x;
	}
	
	public float getY(int pointerIndex) {
		return y;
	}
	public int getActionIndex() {
		return 0;
	}
	public boolean isTargetAccessibilityFocus() {
		return false;
	}
	public void setTargetAccessibilityFocus(boolean b) {
		
	}
	public int getButtonState() {
		return 0;
	}
	public int getSource() {
		return 0;
	}
	public boolean isFromSource(int source) {
		return false;
	}
	public long getDownTime() {
		return downTime;
	}
	public void offsetLocation(float deltaX, float deltaY) {
		if (deltaX != 0.0f || deltaY != 0.0f) {
            x += deltaX;
            y += deltaY;
            rawX += deltaX;
            rawY += deltaX;
        }		
	}
	public MotionEvent split(int newPointerIdBits) {
		return this;
	}
	public static  MotionEvent obtain(long downTime, long eventTime, int action,
            float x, float y, int metaState) {
		MotionEvent m = obtain();
		m.downTime = downTime;
		m.eventTime = eventTime;
		m.action = action;
		m.x = (int) x;
		m.y = (int) y;
		m.rawX = m.x;
		m.rawY = m.y;
		return m;
	}
	public void setSource(int sourceTouchscreen) {
		
	}
	public int getPointerIdBits() {
		return 1;
	}
	public boolean isButtonPressed(int buttonPrimary) {
		return false;
	}
	public int getPointerCount() {
		return 1;
	}
	public int getFlags() {
		return 0;
	}
	public int getClassification() {
		return 0;
	}
	
	private static final Object gRecyclerLock = new Object();
    private static int gRecyclerUsed;
    private static MotionEvent gRecyclerTop;
    private MotionEvent mNext;
    protected int mSeq;
    protected boolean mRecycled;
    private RuntimeException mRecycledLocation;
    private static final AtomicInteger mNextSeq = new AtomicInteger();
	static private MotionEvent obtain() {
        final MotionEvent ev;
        synchronized (gRecyclerLock) {
            ev = gRecyclerTop;
            if (ev == null) {
                return new MotionEvent();
            }
            gRecyclerTop = ev.mNext;
            gRecyclerUsed -= 1;
        }
        ev.mNext = null;
        ev.prepareForReuse();
        return ev;
    }
	
    public final void recycle() {
    	if (TRACK_RECYCLED_LOCATION) {
            if (mRecycledLocation != null) {
                throw new RuntimeException(toString() + " recycled twice!", mRecycledLocation);
            }
            mRecycledLocation = new RuntimeException("Last recycled here");
        } else {
            if (mRecycled) {
                throw new RuntimeException(toString() + " recycled twice!");
            }
            mRecycled = true;
        }

        synchronized (gRecyclerLock) {
            if (gRecyclerUsed < MAX_RECYCLED) {
                gRecyclerUsed++;
                mNext = gRecyclerTop;
                gRecyclerTop = this;
            }
        }
    }
	
    protected void prepareForReuse() {
        mRecycled = false;
        mRecycledLocation = null;
        mSeq = mNextSeq.getAndIncrement();
    }

}