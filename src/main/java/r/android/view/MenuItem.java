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
import r.android.content.res.ColorStateList;
import r.android.graphics.drawable.Drawable;
public interface MenuItem {
  public static final int SHOW_AS_ACTION_NEVER=0;
  public static final int SHOW_AS_ACTION_IF_ROOM=1;
  public static final int SHOW_AS_ACTION_ALWAYS=2;
  public static final int SHOW_AS_ACTION_WITH_TEXT=4;
  public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW=8;
  public int getItemId();
  public int getGroupId();
  public CharSequence getTitle();
  public MenuItem setIcon(  Drawable icon);
  public Drawable getIcon();
  public default MenuItem setIconTintList(  ColorStateList tint){
    return this;
  }
  public MenuItem setCheckable(  boolean checkable);
  public MenuItem setChecked(  boolean checked);
  public boolean isChecked();
  public MenuItem setVisible(  boolean visible);
  public boolean isVisible();
  public MenuItem setEnabled(  boolean enabled);
  public boolean isEnabled();
  public boolean hasSubMenu();
  public SubMenu getSubMenu();
  public void setShowAsAction(  int actionEnum);
  public MenuItem setActionView(  View view);
  public View getActionView();
}
