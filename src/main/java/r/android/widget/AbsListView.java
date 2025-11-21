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
package r.android.widget;
import r.android.graphics.Rect;
import r.android.graphics.drawable.Drawable;
import r.android.os.Trace;
import r.android.util.LongSparseArray;
import r.android.util.SparseArray;
import r.android.util.SparseBooleanArray;
import r.android.view.Gravity;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.view.accessibility.AccessibilityEvent;
import r.android.view.accessibility.AccessibilityManager;
import java.util.ArrayList;
import java.util.List;
public abstract class AbsListView extends AdapterView<ListAdapter> {
  public static final int TRANSCRIPT_MODE_DISABLED=0;
  public static final int TRANSCRIPT_MODE_NORMAL=1;
  public static final int TRANSCRIPT_MODE_ALWAYS_SCROLL=2;
  static final int TOUCH_MODE_REST=-1;
  static final int TOUCH_MODE_DOWN=0;
  static final int TOUCH_MODE_TAP=1;
  static final int TOUCH_MODE_DONE_WAITING=2;
  static final int TOUCH_MODE_SCROLL=3;
  static final int TOUCH_MODE_FLING=4;
  static final int TOUCH_MODE_OVERSCROLL=5;
  static final int TOUCH_MODE_OVERFLING=6;
  static final int LAYOUT_NORMAL=0;
  static final int LAYOUT_FORCE_TOP=1;
  static final int LAYOUT_SET_SELECTION=2;
  static final int LAYOUT_FORCE_BOTTOM=3;
  static final int LAYOUT_SPECIFIC=4;
  static final int LAYOUT_SYNC=5;
  static final int LAYOUT_MOVE_SELECTION=6;
  public static final int CHOICE_MODE_NONE=0;
  public static final int CHOICE_MODE_SINGLE=1;
  public static final int CHOICE_MODE_MULTIPLE=2;
  public static final int CHOICE_MODE_MULTIPLE_MODAL=3;
  int mChoiceMode=CHOICE_MODE_NONE;
  ActionMode mChoiceActionMode;
  MultiChoiceModeWrapper mMultiChoiceModeCallback;
  int mCheckedItemCount;
  SparseBooleanArray mCheckStates;
  LongSparseArray<Integer> mCheckedIdStates;
  int mLayoutMode=LAYOUT_NORMAL;
  ListAdapter mAdapter;
  boolean mAdapterHasStableIds;
  int mSelectorPosition=INVALID_POSITION;
  int mSelectionLeftPadding=0;
  int mSelectionTopPadding=0;
  int mSelectionRightPadding=0;
  int mSelectionBottomPadding=0;
  int mWidthMeasureSpec=0;
  int mMotionPosition;
  int mMotionViewOriginalTop;
  int mMotionViewNewTop;
  int mMotionX;
  int mMotionY;
  int mTouchMode=TOUCH_MODE_REST;
  int mLastY;
  int mMotionCorrection;
  int mSelectedTop=0;
  int mResurrectToPosition=INVALID_POSITION;
  int mOverscrollMax;
  static final int OVERSCROLL_LIMIT_DIVISOR=3;
  private static final int CHECK_POSITION_SEARCH_DISTANCE=20;
  private static final int TOUCH_MODE_UNKNOWN=-1;
  private static final int TOUCH_MODE_ON=0;
  private static final int TOUCH_MODE_OFF=1;
  private int mLastTouchMode=TOUCH_MODE_UNKNOWN;
  private int mTranscriptMode;
  private int mCacheColorHint;
  private int mFastScrollStyle;
  private int mTouchSlop;
  private int mMinimumVelocity;
  private int mMaximumVelocity;
  private int mNestedYOffset=0;
  private int mActivePointerId=INVALID_POINTER;
  private static final int INVALID_POINTER=-1;
  int mOverscrollDistance;
  int mOverflingDistance;
  private int mFirstPositionDistanceGuess;
  private int mLastPositionDistanceGuess;
  private int mDirection=0;
  private int mLastHandledItemCount;
  public void setAdapter(  ListAdapter adapter){
    if (adapter != null) {
      mAdapterHasStableIds=mAdapter.hasStableIds();
      if (mChoiceMode != CHOICE_MODE_NONE && mAdapterHasStableIds && mCheckedIdStates == null) {
        mCheckedIdStates=new LongSparseArray<Integer>();
      }
    }
    clearChoices();
  }
  public int getCheckedItemCount(){
    return mCheckedItemCount;
  }
  public boolean isItemChecked(  int position){
    if (mChoiceMode != CHOICE_MODE_NONE && mCheckStates != null) {
      return mCheckStates.get(position);
    }
    return false;
  }
  public int getCheckedItemPosition(){
    if (mChoiceMode == CHOICE_MODE_SINGLE && mCheckStates != null && mCheckStates.size() == 1) {
      return mCheckStates.keyAt(0);
    }
    return INVALID_POSITION;
  }
  public SparseBooleanArray getCheckedItemPositions(){
    if (mChoiceMode != CHOICE_MODE_NONE) {
      return mCheckStates;
    }
    return null;
  }
  public long[] getCheckedItemIds(){
    if (mChoiceMode == CHOICE_MODE_NONE || mCheckedIdStates == null || mAdapter == null) {
      return new long[0];
    }
    final LongSparseArray<Integer> idStates=mCheckedIdStates;
    final int count=idStates.size();
    final long[] ids=new long[count];
    for (int i=0; i < count; i++) {
      ids[i]=idStates.keyAt(i);
    }
    return ids;
  }
  public void clearChoices(){
    if (mCheckStates != null) {
      mCheckStates.clear();
    }
    if (mCheckedIdStates != null) {
      mCheckedIdStates.clear();
    }
    mCheckedItemCount=0;
  }
  public boolean performItemClick(  View view,  int position,  long id){
    boolean handled=false;
    boolean dispatchItemClick=true;
    if (mChoiceMode != CHOICE_MODE_NONE) {
      handled=true;
      boolean checkedStateChanged=false;
      if (mChoiceMode == CHOICE_MODE_MULTIPLE || (mChoiceMode == CHOICE_MODE_MULTIPLE_MODAL && mChoiceActionMode != null)) {
        boolean checked=!mCheckStates.get(position,false);
        mCheckStates.put(position,checked);
        if (mCheckedIdStates != null && mAdapter.hasStableIds()) {
          if (checked) {
            mCheckedIdStates.put(mAdapter.getItemId(position),position);
          }
 else {
            mCheckedIdStates.delete(mAdapter.getItemId(position));
          }
        }
        if (checked) {
          mCheckedItemCount++;
        }
 else {
          mCheckedItemCount--;
        }
        if (mChoiceActionMode != null) {
          mMultiChoiceModeCallback.onItemCheckedStateChanged(mChoiceActionMode,position,id,checked);
          dispatchItemClick=false;
        }
        checkedStateChanged=true;
      }
 else       if (mChoiceMode == CHOICE_MODE_SINGLE) {
        boolean checked=!mCheckStates.get(position,false);
        if (checked) {
          mCheckStates.clear();
          mCheckStates.put(position,true);
          if (mCheckedIdStates != null && mAdapter.hasStableIds()) {
            mCheckedIdStates.clear();
            mCheckedIdStates.put(mAdapter.getItemId(position),position);
          }
          mCheckedItemCount=1;
        }
 else         if (mCheckStates.size() == 0 || !mCheckStates.valueAt(0)) {
          mCheckedItemCount=0;
        }
        checkedStateChanged=true;
      }
      if (checkedStateChanged) {
        updateOnScreenCheckedViews();
      }
    }
    if (dispatchItemClick) {
      handled|=super.performItemClick(view,position,id);
    }
    return handled;
  }
  public int getChoiceMode(){
    return mChoiceMode;
  }
  public void setChoiceMode(  int choiceMode){
    mChoiceMode=choiceMode;
    if (mChoiceActionMode != null) {
      mChoiceActionMode.finish();
      mChoiceActionMode=null;
    }
    if (mChoiceMode != CHOICE_MODE_NONE) {
      if (mCheckStates == null) {
        mCheckStates=new SparseBooleanArray(0);
      }
      if (mCheckedIdStates == null && mAdapter != null && mAdapter.hasStableIds()) {
        mCheckedIdStates=new LongSparseArray<Integer>(0);
      }
      if (mChoiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
        clearChoices();
        setLongClickable(true);
      }
    }
  }
class ActionMode {
    void invalidate(){
    }
    public void finish(){
    }
  }
class MultiChoiceModeWrapper {
    public void onItemCheckedStateChanged(    ActionMode mode,    int position,    long id,    boolean checked){
    }
  }
  private void updateOnScreenCheckedViews(){
  }
public interface OnScrollListener {
    public void onScroll(    AbsListView view,    int firstVisibleItem,    int visibleItemCount,    int totalItemCount);
  }
}
