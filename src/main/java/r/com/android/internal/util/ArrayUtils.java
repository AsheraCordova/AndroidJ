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
package r.com.android.internal.util;

public class ArrayUtils {

   /**
     * Throws {@link ArrayIndexOutOfBoundsException} if the index is out of bounds.
     *
     * @param len length of the array. Must be non-negative
     * @param index the index to check
     * @throws ArrayIndexOutOfBoundsException if the {@code index} is out of bounds of the array
     */
    public static void checkBounds(int len, int index) {
        if (index < 0 || len <= index) {
            throw new ArrayIndexOutOfBoundsException("length=" + len + "; index=" + index);
        }
    }

	public static int[] newUnpaddedIntArray(int initialCapacity) {
		return new int[initialCapacity];
	}

	public static long[] newUnpaddedLongArray(int initialCapacity) {
		return new long[initialCapacity];
	}
}
