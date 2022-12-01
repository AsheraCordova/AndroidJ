package r.android.text.format;

import java.util.Formatter;
import java.util.Locale;

public class DateUtils {
    public static final long SECOND_IN_MILLIS = 1000;
    private static String sElapsedFormatMMSS;
    private static String sElapsedFormatHMMSS;
    
    public static String formatElapsedTime(long elapsedSeconds) {
        return formatElapsedTime(null, elapsedSeconds);
    }
    /**
     * Formats an elapsed time in a format like "MM:SS" or "H:MM:SS" (using a form
     * suited to the current locale), similar to that used on the call-in-progress
     * screen.
     *
     * @param recycle {@link StringBuilder} to recycle, or null to use a temporary one.
     * @param elapsedSeconds the elapsed time in seconds.
     */
    public static String formatElapsedTime(StringBuilder recycle, long elapsedSeconds) {
        // Break the elapsed seconds into hours, minutes, and seconds.
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
            elapsedSeconds -= hours * 3600;
        }
        if (elapsedSeconds >= 60) {
            minutes = elapsedSeconds / 60;
            elapsedSeconds -= minutes * 60;
        }
        seconds = elapsedSeconds;
        // Create a StringBuilder if we weren't given one to recycle.
        // TODO: if we cared, we could have a thread-local temporary StringBuilder.
        StringBuilder sb = recycle;
        if (sb == null) {
            sb = new StringBuilder(8);
        } else {
            sb.setLength(0);
        }
        // Format the broken-down time in a locale-appropriate way.
        // TODO: use icu4c when http://unicode.org/cldr/trac/ticket/3407 is fixed.
        Formatter f = new Formatter(sb, Locale.getDefault());
        initFormatStrings();
        if (hours > 0) {
            return f.format(sElapsedFormatHMMSS, hours, minutes, seconds).toString();
        } else {
            return f.format(sElapsedFormatMMSS, minutes, seconds).toString();
        }
    }
    private static void initFormatStrings() {
        sElapsedFormatMMSS  = "%1$02d:%2$02d";
        sElapsedFormatHMMSS = "%1$d:%2$02d:%3$02d"; 
        
    }
}
