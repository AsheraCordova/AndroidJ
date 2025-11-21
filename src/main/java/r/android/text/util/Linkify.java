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
/*
 * Copyright (C) 2007 The Android Open Source Project
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
package r.android.text.util;
import r.android.content.Context;
import r.android.util.Log;
import r.android.util.Patterns;
import r.libcore.util.EmptyArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Linkify {
  private static final String LOG_TAG="Linkify";
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
  public static final boolean addLinks(  Spannable text,  int mask){
    return addLinks(text,mask,null,null);
  }
  public static final boolean addLinks(  Spannable text,  int mask,  Function<String,URLSpan> urlSpanFactory){
    return addLinks(text,mask,null,urlSpanFactory);
  }
  private static boolean addLinks(  Spannable text,  int mask,  Context context,  Function<String,URLSpan> urlSpanFactory){
    if (text != null && containsUnsupportedCharacters(text.toString())) {
      //r.android.util.EventLo.writeEvent(0x534e4554,"116321860",-1,"");
      return false;
    }
    if (mask == 0) {
      return false;
    }
    final URLSpan[] old=text.getSpans(0,text.length(),URLSpan.class);
    for (int i=old.length - 1; i >= 0; i--) {
      text.removeSpan(old[i]);
    }
    final ArrayList<LinkSpec> links=new ArrayList<LinkSpec>();
    if ((mask & WEB_URLS) != 0) {
      gatherLinks(links,text,Patterns.AUTOLINK_WEB_URL,new String[]{"http://","https://","rtsp://","ftp://"},sUrlMatchFilter,null);
    }
    if ((mask & EMAIL_ADDRESSES) != 0) {
      gatherLinks(links,text,Patterns.AUTOLINK_EMAIL_ADDRESS,new String[]{"mailto:"},null,null);
    }
    if ((mask & PHONE_NUMBERS) != 0) {
      gatherTelLinks(links,text,context);
    }
    if ((mask & MAP_ADDRESSES) != 0) {
      gatherMapLinks(links,text);
    }
    pruneOverlaps(links);
    if (links.size() == 0) {
      return false;
    }
    for (    LinkSpec link : links) {
      applyLink(link.url,link.start,link.end,text,urlSpanFactory);
    }
    return true;
  }
  public static boolean containsUnsupportedCharacters(  String text){
    if (text.contains("\u202C")) {
      Log.e(LOG_TAG,"Unsupported character for applying links: u202C");
      return true;
    }
    if (text.contains("\u202D")) {
      Log.e(LOG_TAG,"Unsupported character for applying links: u202D");
      return true;
    }
    if (text.contains("\u202E")) {
      Log.e(LOG_TAG,"Unsupported character for applying links: u202E");
      return true;
    }
    return false;
  }
  public static final boolean addLinks(  Spannable text,  Pattern pattern,  String scheme){
    return addLinks(text,pattern,scheme,null,null,null);
  }
  public static final boolean addLinks(  Spannable spannable,  Pattern pattern,  String scheme,  MatchFilter matchFilter,  TransformFilter transformFilter){
    return addLinks(spannable,pattern,scheme,null,matchFilter,transformFilter);
  }
  public static final boolean addLinks(  Spannable spannable,  Pattern pattern,  String defaultScheme,  String[] schemes,  MatchFilter matchFilter,  TransformFilter transformFilter){
    return addLinks(spannable,pattern,defaultScheme,schemes,matchFilter,transformFilter,null);
  }
  public static final boolean addLinks(  Spannable spannable,  Pattern pattern,  String defaultScheme,  String[] schemes,  MatchFilter matchFilter,  TransformFilter transformFilter,  Function<String,URLSpan> urlSpanFactory){
    if (spannable != null && containsUnsupportedCharacters(spannable.toString())) {
      //r.android.util.EventLo.writeEvent(0x534e4554,"116321860",-1,"");
      return false;
    }
    final String[] schemesCopy;
    if (defaultScheme == null)     defaultScheme="";
    if (schemes == null || schemes.length < 1) {
      schemes=EmptyArray.STRING;
    }
    schemesCopy=new String[schemes.length + 1];
    schemesCopy[0]=defaultScheme.toLowerCase(Locale.ROOT);
    for (int index=0; index < schemes.length; index++) {
      String scheme=schemes[index];
      schemesCopy[index + 1]=(scheme == null) ? "" : scheme.toLowerCase(Locale.ROOT);
    }
    boolean hasMatches=false;
    Matcher m=pattern.matcher(spannable);
    while (m.find()) {
      int start=m.start();
      int end=m.end();
      boolean allowed=true;
      if (matchFilter != null) {
        allowed=matchFilter.acceptMatch(spannable,start,end);
      }
      if (allowed) {
        String url=makeUrl(m.group(0),schemesCopy,m,transformFilter);
        applyLink(url,start,end,spannable,urlSpanFactory);
        hasMatches=true;
      }
    }
    return hasMatches;
  }
  private static final String makeUrl(  String url,  String[] prefixes,  Matcher matcher,  TransformFilter filter){
    if (filter != null) {
      url=filter.transformUrl(matcher,url);
    }
    boolean hasPrefix=false;
    for (int i=0; i < prefixes.length; i++) {
      if (url.regionMatches(true,0,prefixes[i],0,prefixes[i].length())) {
        hasPrefix=true;
        if (!url.regionMatches(false,0,prefixes[i],0,prefixes[i].length())) {
          url=prefixes[i] + url.substring(prefixes[i].length());
        }
        break;
      }
    }
    if (!hasPrefix && prefixes.length > 0) {
      url=prefixes[0] + url;
    }
    return url;
  }
  private static final void gatherLinks(  ArrayList<LinkSpec> links,  Spannable s,  Pattern pattern,  String[] schemes,  MatchFilter matchFilter,  TransformFilter transformFilter){
    Matcher m=pattern.matcher(s);
    while (m.find()) {
      int start=m.start();
      int end=m.end();
      if (matchFilter == null || matchFilter.acceptMatch(s,start,end)) {
        LinkSpec spec=new LinkSpec();
        String url=makeUrl(m.group(0),schemes,m,transformFilter);
        spec.url=url;
        spec.start=start;
        spec.end=end;
        links.add(spec);
      }
    }
  }
  private static final void pruneOverlaps(  ArrayList<LinkSpec> links){
    Comparator<LinkSpec> c=new Comparator<LinkSpec>(){
      public final int compare(      LinkSpec a,      LinkSpec b){
        if (a.start < b.start) {
          return -1;
        }
        if (a.start > b.start) {
          return 1;
        }
        if (a.end < b.end) {
          return 1;
        }
        if (a.end > b.end) {
          return -1;
        }
        return 0;
      }
    }
;
    Collections.sort(links,c);
    int len=links.size();
    int i=0;
    while (i < len - 1) {
      LinkSpec a=links.get(i);
      LinkSpec b=links.get(i + 1);
      int remove=-1;
      if ((a.start <= b.start) && (a.end > b.start)) {
        if (b.end <= a.end) {
          remove=i + 1;
        }
 else         if ((a.end - a.start) > (b.end - b.start)) {
          remove=i + 1;
        }
 else         if ((a.end - a.start) < (b.end - b.start)) {
          remove=i;
        }
        if (remove != -1) {
          links.remove(remove);
          len--;
          continue;
        }
      }
      i++;
    }
  }
  private static void gatherMapLinks(  ArrayList<LinkSpec> links,  Spannable text){
  }
  private static void gatherTelLinks(  ArrayList<LinkSpec> links,  Spannable text,  Context context){
  }
  private static void applyLink(  String url,  int start,  int end,  Spannable text,  Function<String,URLSpan> urlSpanFactory){
    URLSpan urlSpan=urlSpanFactory.apply(url);
    urlSpan.url=url;
    urlSpan.start=start;
    urlSpan.end=end;
  }
interface Spannable extends CharSequence {
    URLSpan[] getSpans(    int i,    int length,    Class<URLSpan> class1);
    void removeSpan(    URLSpan urlSpan);
  }
static class URLSpan {
    String url;
    int start;
    int end;
  }
  public static String linkify(  String url,  int mask,  boolean clickable){
    ArrayList<URLSpan> urlspans=new ArrayList<>();
    Linkify.addLinks(new SpannableImpl(url),mask,new Function<String,Linkify.URLSpan>(){
      @Override public URLSpan apply(      String t){
        URLSpan urlspan=new URLSpan();
        urlspans.add(urlspan);
        return urlspan;
      }
    }
);
    for (int i=urlspans.size() - 1; i >= 0; i--) {
      URLSpan urlSpan=urlspans.get(i);
      String href=String.format("<a href='%s'>%s</a>",clickable ? "#" : urlSpan.url,urlSpan.url);
      url=url.substring(0,urlSpan.start) + href + url.substring(urlSpan.end);
    }
    return url;
  }
}
class LinkSpec {
  String url;
  int start;
  int end;
}
