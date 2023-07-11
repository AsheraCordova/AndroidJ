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

package r.android.graphics;

/**
 * Rect holds four integer coordinates for a rectangle. The rectangle is
 * represented by the coordinates of its 4 edges (left, top, right bottom).
 * These fields can be accessed directly. Use width() and height() to retrieve
 * the rectangle's width and height. Note: most methods do not check to see that
 * the coordinates are sorted correctly (i.e. left <= right and top <= bottom).
 */
public final class Rect {
	public int left;
	public int top;
	public int right;
	public int bottom;

	/**
	 * Create a new empty Rect. All coordinates are initialized to 0.
	 */
	public Rect() {
	}

	/**
	 * Create a new rectangle with the specified coordinates. Note: no range
	 * checking is performed, so the caller must ensure that left <= right and
	 * top <= bottom.
	 *
	 * @param left
	 *            The X coordinate of the left side of the rectangle
	 * @param top
	 *            The Y coordinate of the top of the rectangle
	 * @param right
	 *            The X coordinate of the right side of the rectangle
	 * @param bottom
	 *            The Y coordinate of the bottom of the rectangle
	 */
	public Rect(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	/**
	 * Create a new rectangle, initialized with the values in the specified
	 * rectangle (which is left unmodified).
	 *
	 * @param r
	 *            The rectangle whose coordinates are copied into the new
	 *            rectangle.
	 */
	public Rect(Rect r) {
		if (r == null) {
			left = top = right = bottom = 0;
		} else {
			left = r.left;
			top = r.top;
			right = r.right;
			bottom = r.bottom;
		}
	}

	/**
	 * @return the rectangle's width. This does not check for a valid rectangle
	 *         (i.e. left <= right) so the result may be negative.
	 */
	public final int width() {
		return right - left;
	}

	/**
	 * @return the rectangle's height. This does not check for a valid rectangle
	 *         (i.e. top <= bottom) so the result may be negative.
	 */
	public final int height() {
		return bottom - top;
	}

	/**
	 * Set the rectangle's coordinates to the specified values. Note: no range
	 * checking is performed, so it is up to the caller to ensure that left <=
	 * right and top <= bottom.
	 *
	 * @param left
	 *            The X coordinate of the left side of the rectangle
	 * @param top
	 *            The Y coordinate of the top of the rectangle
	 * @param right
	 *            The X coordinate of the right side of the rectangle
	 * @param bottom
	 *            The Y coordinate of the bottom of the rectangle
	 */
	public void set(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public void set(Rect src) {
        this.left = src.left;
        this.top = src.top;
        this.right = src.right;
        this.bottom = src.bottom;
    }

	public void setEmpty() {
		left = right = top = bottom = 0;
		
	}

    public final boolean isEmpty() {
        return left >= right || top >= bottom;
    }
    
    /**
     * Offset the rectangle by adding dx to its left and right coordinates, and
     * adding dy to its top and bottom coordinates.
     *
     * @param dx The amount to add to the rectangle's left and right coordinates
     * @param dy The amount to add to the rectangle's top and bottom coordinates
     */
    public void offset(int dx, int dy) {
        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }
    
	public boolean contains(Rect r) {
		// check for empty first
		return this.left < this.right && this.top < this.bottom
		// now check for containment
				&& left <= r.left && top <= r.top && right >= r.right && bottom >= r.bottom;
	}
	
    public String toShortString() {
        return toShortString(new StringBuilder(32));
    }
    
    /**
     * Return a string representation of the rectangle in a compact form.
     * @hide
     */
    public String toShortString(StringBuilder sb) {
        sb.setLength(0);
        sb.append('['); sb.append(left); sb.append(',');
        sb.append(top); sb.append("]["); sb.append(right);
        sb.append(','); sb.append(bottom); sb.append(']');
        return sb.toString();
    }
}
