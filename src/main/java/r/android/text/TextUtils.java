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
}
