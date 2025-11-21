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
package r.android.widget;
import r.android.os.SystemClock;
import r.android.text.format.DateUtils;
import r.android.util.Log;
import r.android.view.View;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.IllegalFormatException;
import java.util.Locale;
public abstract class Chronometer extends TextView {
  private static final String TAG="Chronometer";
public interface OnChronometerTickListener {
    void onChronometerTick(    Chronometer chronometer);
  }
  private long mBase;
  private long mNow;
  private boolean mVisible;
  private boolean mStarted;
  private boolean mRunning;
  private boolean mLogged;
  private String mFormat;
  private Formatter mFormatter;
  private Locale mFormatterLocale;
  private Object[] mFormatterArgs=new Object[1];
  private StringBuilder mFormatBuilder;
  private OnChronometerTickListener mOnChronometerTickListener;
  private StringBuilder mRecycle=new StringBuilder(8);
  private boolean mCountDown;
  private void init(){
    mBase=SystemClock.elapsedRealtime();
    updateText(mBase);
  }
  public void setCountDown(  boolean countDown){
    mCountDown=countDown;
    updateText(SystemClock.elapsedRealtime());
  }
  public boolean isCountDown(){
    return mCountDown;
  }
  public void setBase(  long base){
    mBase=base;
    dispatchChronometerTick();
    updateText(SystemClock.elapsedRealtime());
  }
  public long getBase(){
    return mBase;
  }
  public void setFormat(  String format){
    mFormat=format;
    if (format != null && mFormatBuilder == null) {
      mFormatBuilder=new StringBuilder(format.length() * 2);
    }
  }
  public String getFormat(){
    return mFormat;
  }
  public void setOnChronometerTickListener(  OnChronometerTickListener listener){
    mOnChronometerTickListener=listener;
  }
  public OnChronometerTickListener getOnChronometerTickListener(){
    return mOnChronometerTickListener;
  }
  public void start(){
    mStarted=true;
    updateRunning();
  }
  public void stop(){
    mStarted=false;
    updateRunning();
  }
  public void setStarted(  boolean started){
    mStarted=started;
    updateRunning();
  }
  private synchronized void updateText(  long now){
    mNow=now;
    long seconds=mCountDown ? mBase - now : now - mBase;
    seconds/=1000;
    boolean negative=false;
    if (seconds < 0) {
      seconds=-seconds;
      negative=true;
    }
    String text=DateUtils.formatElapsedTime(mRecycle,seconds);
    if (negative) {
      text=getResources().getString(r.android.R.string.negative_duration,text);
    }
    if (mFormat != null) {
      Locale loc=Locale.getDefault();
      if (mFormatter == null || !loc.equals(mFormatterLocale)) {
        mFormatterLocale=loc;
        mFormatter=new Formatter(mFormatBuilder,loc);
      }
      mFormatBuilder.setLength(0);
      mFormatterArgs[0]=text;
      try {
        mFormatter.format(mFormat,mFormatterArgs);
        text=mFormatBuilder.toString();
      }
 catch (      IllegalFormatException ex) {
        if (!mLogged) {
          Log.w(TAG,"Illegal format string: " + mFormat);
          mLogged=true;
        }
      }
    }
    setText(text);
  }
  private void updateRunning(){
    boolean running=mVisible && mStarted && isShown();
    if (running != mRunning) {
      if (running) {
        updateText(SystemClock.elapsedRealtime());
        dispatchChronometerTick();
        postDelayed(mTickRunnable,1000);
      }
 else {
        removeCallbacks(mTickRunnable);
      }
      mRunning=running;
    }
  }
  void dispatchChronometerTick(){
    if (mOnChronometerTickListener != null) {
      mOnChronometerTickListener.onChronometerTick(this);
    }
  }
  private static final int MIN_IN_SEC=60;
  private static final int HOUR_IN_SEC=MIN_IN_SEC * 60;
  public void postInit(){
    mVisible=true;
    init();
  }
@com.google.j2objc.annotations.WeakOuter private final class TickableRunnable implements Runnable {
    public void run(){
      if (mRunning) {
        updateText(SystemClock.elapsedRealtime());
        dispatchChronometerTick();
        postDelayed(mTickRunnable,1000);
      }
    }
  }
  private final Runnable mTickRunnable=new TickableRunnable();
  public Chronometer(  com.ashera.widget.IWidget widget){
    super(widget);
  }
}
