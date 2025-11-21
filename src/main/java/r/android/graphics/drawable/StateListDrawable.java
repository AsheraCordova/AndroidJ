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
package r.android.graphics.drawable;

import java.util.ArrayList;
import java.util.List;

public class StateListDrawable extends Drawable{
	private int[][] mStateSets;
	private Drawable[] mDrawables;
	public List<Object> getAllDrawables() {
		if (mDrawables == null) {
			return null;
		}
		List<Object> objects = new ArrayList<>();
		for (int i = 0; i < mDrawables.length; i++) {
			Drawable drawable = mDrawables[i];
			if (drawable != null) {
				objects.add(drawable.getDrawable());
			} else {
				break;
			}
		}
		return objects;
	}

	private int mNumChildren;
    
    public StateListDrawable() {
    	mDrawables = new Drawable[10];
        mNumChildren = 0;
    	mStateSets = new int[getCapacity()][];
	}
    
    @Override
    public java.lang.Object getDrawable() {
    	Drawable currentDrawable = getCurrentDrawable();
		if (currentDrawable == null) {
    		return null;
    	}
		return currentDrawable.getDrawable();
    }

    @Override
	public void setDrawable(Object drawable) {
		super.setDrawable(drawable);
		Drawable currentDrawable = getCurrentDrawable();
		if (currentDrawable == null) {
    		return;
    	}
		currentDrawable.setDrawable(drawable);
	}

    
    final int getCapacity() {
        return mDrawables.length;
    }

	public void addState(int[] stateSet, Drawable drawable) {
        if (drawable != null) {
            addStateSet(stateSet, drawable);
            // in case the new state matches our current state...
            onStateChange(getState());
        }
    }
	
	public Drawable getCurrentDrawable() {
		int idx = indexOfStateSet(getState());
        if (idx < 0) {
            idx = indexOfStateSet(WILD_CARD);
        }
        return idx < 0 ? null : mDrawables[idx];
	}

    @Override
    public boolean isStateful() {
        return true;
    }
    
    public final int addChild(Drawable dr) {
        final int pos = mNumChildren;
        if (pos >= mDrawables.length) {
            growArray(pos, pos+10);
        }


        mDrawables[pos] = dr;
        mNumChildren++;

        return pos;
    }
    
    public void growArray(int oldSize, int newSize) {
        Drawable[] newDrawables = new Drawable[newSize];
        System.arraycopy(mDrawables, 0, newDrawables, 0, oldSize);
        mDrawables = newDrawables;
    }
    
    int addStateSet(int[] stateSet, Drawable drawable) {
        final int pos = addChild(drawable);
        if (pos >= mStateSets.length) {
            growArrayStateSet(pos, pos+10);
        }
        mStateSets[pos] = stateSet;
        return pos;
    }
    
    public void growArrayStateSet(int oldSize, int newSize) {
        final int[][] newStateSets = new int[newSize][];
        System.arraycopy(mStateSets, 0, newStateSets, 0, oldSize);
        mStateSets = newStateSets;
    }
    
    protected boolean onStateChange(int[] stateSet) {
        int idx = indexOfStateSet(stateSet);
        if (idx < 0) {
            idx = indexOfStateSet(WILD_CARD);
        }

        return selectDrawable(idx);
    }
    
    public boolean selectDrawable(int index) {
    	return true;
    }
    
    int indexOfStateSet(int[] stateSet) {
        final int[][] stateSets = mStateSets;
        final int N = getChildCount();
        for (int i = 0; i < N; i++) {
            if (stateSetMatches(stateSets[i], stateSet)) {
                return i;
            }
        }
        return -1;
    }
    
    public final int getChildCount() {
        return mNumChildren;
    }
    
    public static boolean isWildCard(int[] stateSetOrSpec) {
        return stateSetOrSpec.length == 0 || stateSetOrSpec[0] == 0;
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
    
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
    	super.setBounds(left, top, right, bottom);
    	
    	if (mDrawables != null) {
	    	for (Drawable drawable : mDrawables) {
	    		if (drawable != null) {
	    			drawable.setBounds(left, top, right, bottom);
	    		}
			}
    	}
    }
    
}
