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
