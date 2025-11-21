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
public interface Menu {
  static final int USER_MASK=0x0000ffff;
  static final int USER_SHIFT=0;
  static final int CATEGORY_MASK=0xffff0000;
  static final int CATEGORY_SHIFT=16;
  static final int NONE=0;
  static final int FIRST=1;
  static final int CATEGORY_CONTAINER=0x00010000;
  static final int CATEGORY_SYSTEM=0x00020000;
  static final int CATEGORY_SECONDARY=0x00030000;
  static final int CATEGORY_ALTERNATIVE=0x00040000;
  static final int FLAG_APPEND_TO_GROUP=0x0001;
  static final int FLAG_PERFORM_NO_CLOSE=0x0001;
  static final int FLAG_ALWAYS_PERFORM_CLOSE=0x0002;
  public MenuItem add(  CharSequence title);
  public MenuItem add(  int titleRes);
  public MenuItem add(  int groupId,  int itemId,  int order,  CharSequence title);
  public MenuItem add(  int groupId,  int itemId,  int order,  int titleRes);
  SubMenu addSubMenu(  final CharSequence title);
}
