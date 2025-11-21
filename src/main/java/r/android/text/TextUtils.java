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
package r.android.text;

import java.util.Locale;
import java.util.regex.Pattern;

import r.android.view.View;

public class TextUtils {
	/**
	 * A regular expression for matching right-to-left language codes. See
	 * {@link #isRtlLanguage} for the design.
	 */
	private static final Pattern RtlLocalesRe = Pattern
			.compile("^(ar|dv|he|iw|fa|nqo|ps|sd|ug|ur|yi|.*[-_](Arab|Hebr|Thaa|Nkoo|Tfng))"
					+ "(?!.*[-_](Latn|Cyrl)($|-|_))($|-|_)");

	public static int getLayoutDirectionFromLocale(Locale locale) {
		return ((locale != null && !locale.equals(Locale.ROOT) && ULocale.forLocale(locale).isRightToLeft())
				? View.LAYOUT_DIRECTION_RTL
				: View.LAYOUT_DIRECTION_LTR);
	}

	private static class ULocale {
		private Locale locale;

		public ULocale(Locale locale) {
			this.locale = locale;
		}

		public boolean isRightToLeft() {
			String languageString = locale.getLanguage();
			return languageString != null && RtlLocalesRe.matcher(languageString).find();
		}

		public static ULocale forLocale(Locale locale) {
			return new ULocale(locale);
		}
	}

	public static boolean isEmpty(String text) {
		return text == null || text.isEmpty();
	}
	
	public static boolean isEmpty(CharSequence text) {
		return text == null || text.length() == 0;
	}

	public static int getTrimmedLength(CharSequence s) {
        int len = s.length();

        int start = 0;
        while (start < len && s.charAt(start) <= ' ') {
            start++;
        }

        int end = len;
        while (end > start && s.charAt(end - 1) <= ' ') {
            end--;
        }

        return end - start;
    }

	public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }
}


