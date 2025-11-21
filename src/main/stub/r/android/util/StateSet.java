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
package r.android.util;

public class StateSet {
	private static final int[][] VIEW_STATE_SETS;
   /** @hide */
    public static final int VIEW_STATE_WINDOW_FOCUSED = 1;
   /** @hide */
    public static final int VIEW_STATE_SELECTED = 1 << 1;
   /** @hide */
    public static final int VIEW_STATE_FOCUSED = 1 << 2;
   /** @hide */
    public static final int VIEW_STATE_ENABLED = 1 << 3;
   /** @hide */
    public static final int VIEW_STATE_PRESSED = 1 << 4;
   /** @hide */
    public static final int VIEW_STATE_ACTIVATED = 1 << 5;
   /** @hide */
    public static final int VIEW_STATE_ACCELERATED = 1 << 6;
   /** @hide */
    public static final int VIEW_STATE_HOVERED = 1 << 7;
   /** @hide */
    public static final int VIEW_STATE_DRAG_CAN_ACCEPT = 1 << 8;
   /** @hide */
    public static final int VIEW_STATE_DRAG_HOVERED = 1 << 9;
    static final int[] ViewDrawableStates = {
            r.android.R.attr.state_window_focused,    
            r.android.R.attr.state_selected,          
            r.android.R.attr.state_focused,           
            r.android.R.attr.state_enabled,          
            r.android.R.attr.state_pressed,          
            r.android.R.attr.state_activated,       
            r.android.R.attr.state_accelerated,   
            r.android.R.attr.state_hovered,      
            r.android.R.attr.state_drag_can_accept,   
            r.android.R.attr.state_drag_hovered
    };
    static final int[] VIEW_STATE_IDS = new int[] {
            r.android.R.attr.state_window_focused,    VIEW_STATE_WINDOW_FOCUSED,
            r.android.R.attr.state_selected,          VIEW_STATE_SELECTED,
            r.android.R.attr.state_focused,           VIEW_STATE_FOCUSED,
            r.android.R.attr.state_enabled,           VIEW_STATE_ENABLED,
            r.android.R.attr.state_pressed,           VIEW_STATE_PRESSED,
            r.android.R.attr.state_activated,         VIEW_STATE_ACTIVATED,
            r.android.R.attr.state_accelerated,       VIEW_STATE_ACCELERATED,
            r.android.R.attr.state_hovered,           VIEW_STATE_HOVERED,
            r.android.R.attr.state_drag_can_accept,   VIEW_STATE_DRAG_CAN_ACCEPT,
            r.android.R.attr.state_drag_hovered,      VIEW_STATE_DRAG_HOVERED
    };
    static {
        final int[] orderedIds = new int[VIEW_STATE_IDS.length];
        for (int i = 0; i < ViewDrawableStates.length; i++) {
            final int viewState = ViewDrawableStates[i];
            for (int j = 0; j < VIEW_STATE_IDS.length; j += 2) {
                if (VIEW_STATE_IDS[j] == viewState) {
                    orderedIds[i * 2] = viewState;
                    orderedIds[i * 2 + 1] = VIEW_STATE_IDS[j + 1];
                }
            }
        }

        final int NUM_BITS = VIEW_STATE_IDS.length / 2;
        VIEW_STATE_SETS = new int[1 << NUM_BITS][];
        for (int i = 0; i < VIEW_STATE_SETS.length; i++) {
            final int numBits = Integer.bitCount(i);
            final int[] set = new int[numBits];
            int pos = 0;
            for (int j = 0; j < orderedIds.length; j += 2) {
                if ((i & orderedIds[j + 1]) != 0) {
                    set[pos++] = orderedIds[j];
                }
            }
            VIEW_STATE_SETS[i] = set;
        }
    }
    
    public static int[] get(int mask) {
        if (mask >= VIEW_STATE_SETS.length) {
            throw new IllegalArgumentException("Invalid state set mask");
        }
        return VIEW_STATE_SETS[mask];
    }
    
    public static boolean containsAttribute(int[][] stateSpecs, int attr) {
        if (stateSpecs != null) {
            for (int[] spec : stateSpecs) {
                if (spec == null) {
                    break;
                }
                for (int specAttr : spec) {
                    if (specAttr == attr || -specAttr == attr) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean stateSetMatches(int[] stateSpec, int[] stateSet) {
        if (stateSet == null) {
            return (stateSpec == null || isWildCard(stateSpec));
        }
        int stateSpecSize = stateSpec.length;
        int stateSetSize = stateSet.length;
        for (int i = 0; i < stateSpecSize; i++) {
            int stateSpecState = stateSpec[i];
            if (stateSpecState == 0) {
                // We've reached the end of the cases to match against.
                return true;
            }
            final boolean mustMatch;
            if (stateSpecState > 0) {
                mustMatch = true;
            } else {
                // We use negative values to indicate must-NOT-match states.
                mustMatch = false;
                stateSpecState = -stateSpecState;
            }
            boolean found = false;
            for (int j = 0; j < stateSetSize; j++) {
                final int state = stateSet[j];
                if (state == 0) {
                    // We've reached the end of states to match.
                    if (mustMatch) {
                        // We didn't find this must-match state.
                        return false;
                    } else {
                        // Continue checking other must-not-match states.
                        break;
                    }
                }
                if (state == stateSpecState) {
                    if (mustMatch) {
                        found = true;
                        // Continue checking other other must-match states.
                        break;
                    } else {
                        // Any match of a must-not-match state returns false.
                        return false;
                    }
                }
            }
            if (mustMatch && !found) {
                // We've reached the end of states to match and we didn't
                // find a must-match state.
                return false;
            }
        }
        return true;
    }
   /**
     * Return whether the state matches the desired stateSpec.
     *
     * @param stateSpec an array of required (if positive) or
     *        prohibited (if negative) {@link android.view.View} states.
     * @param state a {@link android.view.View} state
     */
    public static boolean stateSetMatches(int[] stateSpec, int state) {
        int stateSpecSize = stateSpec.length;
        for (int i = 0; i < stateSpecSize; i++) {
            int stateSpecState = stateSpec[i];
            if (stateSpecState == 0) {
                // We've reached the end of the cases to match against.
                return true;
            }
            if (stateSpecState > 0) {
                if (state != stateSpecState) {
                   return false;
                }
            } else {
                // We use negative values to indicate must-NOT-match states.
                if (state == -stateSpecState) {
                    // We matched a must-not-match case.
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isWildCard(int[] stateSetOrSpec) {
        return stateSetOrSpec.length == 0 || stateSetOrSpec[0] == 0;
    }

    public static int[] trimStateSet(int[] states, int newSize) {
        if (states.length == newSize) {
            return states;
        }

        int[] trimmedStates = new int[newSize];
        System.arraycopy(states, 0, trimmedStates, 0, newSize);
        return trimmedStates;
    }
}
