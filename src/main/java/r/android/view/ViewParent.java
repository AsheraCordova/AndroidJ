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
import r.android.graphics.Rect;
public interface ViewParent {
  public void requestLayout();
  public boolean isLayoutRequested();
  public void invalidateChild(  View child,  Rect r);
  public ViewParent getParent();
  public void recomputeViewAttributes(  View child);
  public void focusableViewAvailable(  View v);
  public void childDrawableStateChanged(  View child);
  public boolean canResolveLayoutDirection();
  public boolean isLayoutDirectionResolved();
  public int getLayoutDirection();
  public boolean canResolveTextDirection();
  public boolean isTextDirectionResolved();
  public int getTextDirection();
  public boolean canResolveTextAlignment();
  public boolean isTextAlignmentResolved();
  public int getTextAlignment();
  public boolean onStartNestedScroll(  View child,  View target,  int nestedScrollAxes);
  public void onNestedScrollAccepted(  View child,  View target,  int nestedScrollAxes);
  public void onStopNestedScroll(  View target);
  public void onNestedScroll(  View target,  int dxConsumed,  int dyConsumed,  int dxUnconsumed,  int dyUnconsumed);
  public void onNestedPreScroll(  View target,  int dx,  int dy,  int[] consumed);
  public boolean onNestedFling(  View target,  float velocityX,  float velocityY,  boolean consumed);
  public boolean onNestedPreFling(  View target,  float velocityX,  float velocityY);
}
