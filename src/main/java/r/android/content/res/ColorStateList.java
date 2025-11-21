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
package r.android.content.res;
import r.android.graphics.Color;
import r.android.util.SparseArray;
import r.android.util.StateSet;
import java.lang.ref.WeakReference;
import java.util.Arrays;
public class ColorStateList   {
  private static final int DEFAULT_COLOR=Color.RED;
  private static final int[][] EMPTY=new int[][]{new int[0]};
  private static final SparseArray<WeakReference<ColorStateList>> sCache=new SparseArray<>();
  private int[][] mThemeAttrs;
  private int mChangingConfigurations;
  private int[][] mStateSpecs;
  private int[] mColors;
  private int mDefaultColor;
  private boolean mIsOpaque;
  private ColorStateList(){
  }
  public ColorStateList(  int[][] states,  int[] colors){
    mStateSpecs=states;
    mColors=colors;
    onColorsChanged();
  }
  public static ColorStateList valueOf(  int color){
synchronized (sCache) {
      final int index=sCache.indexOfKey(color);
      if (index >= 0) {
        final ColorStateList cached=sCache.valueAt(index).get();
        if (cached != null) {
          return cached;
        }
        sCache.removeAt(index);
      }
      final int N=sCache.size();
      for (int i=N - 1; i >= 0; i--) {
        if (sCache.valueAt(i).get() == null) {
          sCache.removeAt(i);
        }
      }
      final ColorStateList csl=new ColorStateList(EMPTY,new int[]{color});
      sCache.put(color,new WeakReference<>(csl));
      return csl;
    }
  }
  public ColorStateList withAlpha(  int alpha){
    final int[] colors=new int[mColors.length];
    final int len=colors.length;
    for (int i=0; i < len; i++) {
      colors[i]=(mColors[i] & 0xFFFFFF) | (alpha << 24);
    }
    return new ColorStateList(mStateSpecs,colors);
  }
  public boolean isStateful(){
    return mStateSpecs.length >= 1 && mStateSpecs[0].length > 0;
  }
  public boolean hasFocusStateSpecified(){
    return StateSet.containsAttribute(mStateSpecs,r.android.R.attr.state_focused);
  }
  public boolean isOpaque(){
    return mIsOpaque;
  }
  public int getColorForState(  int[] stateSet,  int defaultColor){
    final int setLength=mStateSpecs.length;
    for (int i=0; i < setLength; i++) {
      final int[] stateSpec=mStateSpecs[i];
      if (StateSet.stateSetMatches(stateSpec,stateSet)) {
        return mColors[i];
      }
    }
    return defaultColor;
  }
  public int getDefaultColor(){
    return mDefaultColor;
  }
  public int[][] getStates(){
    return mStateSpecs;
  }
  public int[] getColors(){
    return mColors;
  }
  public boolean hasState(  int state){
    final int[][] stateSpecs=mStateSpecs;
    final int specCount=stateSpecs.length;
    for (int specIndex=0; specIndex < specCount; specIndex++) {
      final int[] states=stateSpecs[specIndex];
      final int stateCount=states.length;
      for (int stateIndex=0; stateIndex < stateCount; stateIndex++) {
        if (states[stateIndex] == state || states[stateIndex] == ~state) {
          return true;
        }
      }
    }
    return false;
  }
  public void onColorsChanged(){
    int defaultColor=DEFAULT_COLOR;
    boolean isOpaque=true;
    final int[][] states=mStateSpecs;
    final int[] colors=mColors;
    final int N=states.length;
    if (N > 0) {
      defaultColor=colors[0];
      for (int i=N - 1; i > 0; i--) {
        if (states[i].length == 0) {
          defaultColor=colors[i];
          break;
        }
      }
      for (int i=0; i < N; i++) {
        if (Color.alpha(colors[i]) != 0xFF) {
          isOpaque=false;
          break;
        }
      }
    }
    mDefaultColor=defaultColor;
    mIsOpaque=isOpaque;
  }
  public void setDefaultColor(  int mDefaultColor){
    this.mDefaultColor=mDefaultColor;
  }
}
