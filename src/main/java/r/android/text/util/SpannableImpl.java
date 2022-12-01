package r.android.text.util;

import r.android.text.util.Linkify.Spannable;
import r.android.text.util.Linkify.URLSpan;

public class SpannableImpl implements Spannable {
	String str;
	public SpannableImpl(String str) {
		super();
		this.str = str;
	}

	@Override
	public int length() {
		return this.str.length();
	}

	@Override
	public char charAt(int index) {
		return this.str.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return this.str.subSequence(start, end);
	}

	@Override
	public URLSpan[] getSpans(int i, int length, Class<URLSpan> class1) {
		return new URLSpan[0];
	}

	@Override
	public void removeSpan(URLSpan urlSpan) {
	}
	
	@Override
	public java.lang.String toString() {
		return str;
	}
	
}