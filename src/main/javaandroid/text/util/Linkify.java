package r.android.text.util;
import java.util.regex.Matcher;
public class Linkify {
  public static final int WEB_URLS=0x01;
  public static final int EMAIL_ADDRESSES=0x02;
  public static final int PHONE_NUMBERS=0x04;
  public static final int MAP_ADDRESSES=0x08;
  public static final int ALL=WEB_URLS | EMAIL_ADDRESSES | PHONE_NUMBERS| MAP_ADDRESSES;
  private static final int PHONE_NUMBER_MINIMUM_DIGITS=5;
  public static final MatchFilter sUrlMatchFilter=new MatchFilter(){
    public final boolean acceptMatch(    CharSequence s,    int start,    int end){
      if (start == 0) {
        return true;
      }
      if (s.charAt(start - 1) == '@') {
        return false;
      }
      return true;
    }
  }
;
  public static final MatchFilter sPhoneNumberMatchFilter=new MatchFilter(){
    public final boolean acceptMatch(    CharSequence s,    int start,    int end){
      int digitCount=0;
      for (int i=start; i < end; i++) {
        if (Character.isDigit(s.charAt(i))) {
          digitCount++;
          if (digitCount >= PHONE_NUMBER_MINIMUM_DIGITS) {
            return true;
          }
        }
      }
      return false;
    }
  }
;
  public static final TransformFilter sPhoneNumberTransformFilter=new TransformFilter(){
    public final String transformUrl(    final Matcher match,    String url){
      return Patterns.digitsAndPlusOnly(match);
    }
  }
;
public interface MatchFilter {
    boolean acceptMatch(    CharSequence s,    int start,    int end);
  }
public interface TransformFilter {
    String transformUrl(    final Matcher match,    String url);
  }
}
