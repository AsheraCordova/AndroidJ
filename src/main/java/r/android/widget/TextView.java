package r.android.widget;
import r.android.content.res.ColorStateList;
import r.android.graphics.RectF;
import r.android.graphics.drawable.Drawable;
import r.android.text.Layout;
import r.android.text.TextDirectionHeuristic;
import r.android.text.TextDirectionHeuristics;
import r.android.text.TextWatcher;
import r.android.util.DisplayMetrics;
import r.android.util.IntArray;
import r.android.view.Gravity;
import r.android.view.KeyEvent;
import r.android.view.View;
import r.libcore.util.EmptyArray;
import java.util.ArrayList;
import java.util.Arrays;
public abstract class TextView extends com.ashera.view.BaseMeasurableView implements com.ashera.widget.IMeasureWidth, com.ashera.widget.IMeasureHeight  {
  private static final int LINES=1;
  private static final int EMS=LINES;
  private static final int PIXELS=2;
  private static final RectF TEMP_RECTF=new RectF();
  private ColorStateList mTextColor;
  private ColorStateList mHintTextColor;
  private ColorStateList mLinkTextColor;
  private int mCurTextColor;
  private int mCurHintTextColor;
  Drawables mDrawables;
  private Layout mHintLayout;
  private ArrayList<TextWatcher> mListeners;
  private final TextPaint mTextPaint;
  private Layout mLayout;
  private int mGravity=Gravity.TOP | Gravity.START;
  private int mMaximum=Integer.MAX_VALUE;
  private int mMaxMode=LINES;
  private int mMinimum=0;
  private int mMinMode=LINES;
  private int mMaxWidth=Integer.MAX_VALUE;
  private int mMaxWidthMode=PIXELS;
  private int mMinWidth=0;
  private int mMinWidthMode=PIXELS;
  private Editor mEditor;
  public static final int AUTO_SIZE_TEXT_TYPE_NONE=0;
  public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM=1;
  private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP=12;
  private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP=112;
  private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX=1;
  private static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE=-1f;
  private int mAutoSizeTextType=AUTO_SIZE_TEXT_TYPE_NONE;
  private boolean mNeedsAutoSizeText=false;
  private float mAutoSizeStepGranularityInPx=UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
  private float mAutoSizeMinTextSizeInPx=UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
  private float mAutoSizeMaxTextSizeInPx=UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
  private int[] mAutoSizeTextSizesInPx=EmptyArray.INT;
  private boolean mHasPresetAutoSizeValues=false;
public interface OnEditorActionListener {
    boolean onEditorAction(    TextView v,    int actionId,    KeyEvent event);
  }
  public void setAutoSizeTextTypeUniformWithPresetSizes(  int[] presetSizes,  int unit){
    if (supportsAutoSizeText()) {
      final int presetSizesLength=presetSizes.length;
      if (presetSizesLength > 0) {
        int[] presetSizesInPx=new int[presetSizesLength];
        if (unit == TypedValue.COMPLEX_UNIT_PX) {
          presetSizesInPx=Arrays.copyOf(presetSizes,presetSizesLength);
        }
 else {
          final DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
          for (int i=0; i < presetSizesLength; i++) {
            presetSizesInPx[i]=Math.round(TypedValue.applyDimension(unit,presetSizes[i],displayMetrics));
          }
        }
        mAutoSizeTextSizesInPx=cleanupAutoSizePresetSizes(presetSizesInPx);
        if (!setupAutoSizeUniformPresetSizesConfiguration()) {
          throw new IllegalArgumentException("None of the preset sizes is valid: " + Arrays.toString(presetSizes));
        }
      }
 else {
        mHasPresetAutoSizeValues=false;
      }
      if (setupAutoSizeText()) {
        autoSizeText();
        invalidate();
      }
    }
  }
  public int getAutoSizeTextType(){
    return mAutoSizeTextType;
  }
  public int getAutoSizeStepGranularity(){
    return Math.round(mAutoSizeStepGranularityInPx);
  }
  public int getAutoSizeMinTextSize(){
    return Math.round(mAutoSizeMinTextSizeInPx);
  }
  public int getAutoSizeMaxTextSize(){
    return Math.round(mAutoSizeMaxTextSizeInPx);
  }
  private boolean setupAutoSizeUniformPresetSizesConfiguration(){
    final int sizesLength=mAutoSizeTextSizesInPx.length;
    mHasPresetAutoSizeValues=sizesLength > 0;
    if (mHasPresetAutoSizeValues) {
      mAutoSizeTextType=AUTO_SIZE_TEXT_TYPE_UNIFORM;
      mAutoSizeMinTextSizeInPx=mAutoSizeTextSizesInPx[0];
      mAutoSizeMaxTextSizeInPx=mAutoSizeTextSizesInPx[sizesLength - 1];
      mAutoSizeStepGranularityInPx=UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
    }
    return mHasPresetAutoSizeValues;
  }
  private void validateAndSetAutoSizeTextTypeUniformConfiguration(  float autoSizeMinTextSizeInPx,  float autoSizeMaxTextSizeInPx,  float autoSizeStepGranularityInPx){
    if (autoSizeMinTextSizeInPx <= 0) {
      throw new IllegalArgumentException("Minimum auto-size text size (" + autoSizeMinTextSizeInPx + "px) is less or equal to (0px)");
    }
    if (autoSizeMaxTextSizeInPx <= autoSizeMinTextSizeInPx) {
      throw new IllegalArgumentException("Maximum auto-size text size (" + autoSizeMaxTextSizeInPx + "px) is less or equal to minimum auto-size "+ "text size ("+ autoSizeMinTextSizeInPx+ "px)");
    }
    if (autoSizeStepGranularityInPx <= 0) {
      throw new IllegalArgumentException("The auto-size step granularity (" + autoSizeStepGranularityInPx + "px) is less or equal to (0px)");
    }
    mAutoSizeTextType=AUTO_SIZE_TEXT_TYPE_UNIFORM;
    mAutoSizeMinTextSizeInPx=autoSizeMinTextSizeInPx;
    mAutoSizeMaxTextSizeInPx=autoSizeMaxTextSizeInPx;
    mAutoSizeStepGranularityInPx=autoSizeStepGranularityInPx;
    mHasPresetAutoSizeValues=false;
  }
  private void clearAutoSizeConfiguration(){
    mAutoSizeTextType=AUTO_SIZE_TEXT_TYPE_NONE;
    mAutoSizeMinTextSizeInPx=UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
    mAutoSizeMaxTextSizeInPx=UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
    mAutoSizeStepGranularityInPx=UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
    mAutoSizeTextSizesInPx=EmptyArray.INT;
    mNeedsAutoSizeText=false;
  }
  private int[] cleanupAutoSizePresetSizes(  int[] presetValues){
    final int presetValuesLength=presetValues.length;
    if (presetValuesLength == 0) {
      return presetValues;
    }
    Arrays.sort(presetValues);
    final IntArray uniqueValidSizes=new IntArray();
    for (int i=0; i < presetValuesLength; i++) {
      final int currentPresetValue=presetValues[i];
      if (currentPresetValue > 0 && uniqueValidSizes.binarySearch(currentPresetValue) < 0) {
        uniqueValidSizes.add(currentPresetValue);
      }
    }
    return presetValuesLength == uniqueValidSizes.size() ? presetValues : uniqueValidSizes.toArray();
  }
  private boolean setupAutoSizeText(){
    if (supportsAutoSizeText() && mAutoSizeTextType == AUTO_SIZE_TEXT_TYPE_UNIFORM) {
      if (!mHasPresetAutoSizeValues || mAutoSizeTextSizesInPx.length == 0) {
        final int autoSizeValuesLength=((int)Math.floor((mAutoSizeMaxTextSizeInPx - mAutoSizeMinTextSizeInPx) / mAutoSizeStepGranularityInPx)) + 1;
        final int[] autoSizeTextSizesInPx=new int[autoSizeValuesLength];
        for (int i=0; i < autoSizeValuesLength; i++) {
          autoSizeTextSizesInPx[i]=Math.round(mAutoSizeMinTextSizeInPx + (i * mAutoSizeStepGranularityInPx));
        }
        mAutoSizeTextSizesInPx=cleanupAutoSizePresetSizes(autoSizeTextSizesInPx);
      }
      mNeedsAutoSizeText=true;
    }
 else {
      mNeedsAutoSizeText=false;
    }
    return mNeedsAutoSizeText;
  }
  public int getTotalPaddingLeft(){
    return getCompoundPaddingLeft();
  }
  public int getTotalPaddingRight(){
    return getCompoundPaddingRight();
  }
  public void setTextColor(  int color){
    mTextColor=ColorStateList.valueOf(color);
    updateTextColors();
  }
  public void setTextColor(  ColorStateList colors){
    if (colors == null) {
      throw new NullPointerException();
    }
    mTextColor=colors;
    updateTextColors();
  }
  public final int getCurrentTextColor(){
    return mCurTextColor;
  }
  public TextPaint getPaint(){
    return mTextPaint;
  }
  public final void setHintTextColor(  int color){
    mHintTextColor=ColorStateList.valueOf(color);
    updateTextColors();
  }
  public final void setHintTextColor(  ColorStateList colors){
    mHintTextColor=colors;
    updateTextColors();
  }
  public final ColorStateList getHintTextColors(){
    return mHintTextColor;
  }
  public final int getCurrentHintTextColor(){
    return mHintTextColor != null ? mCurHintTextColor : mCurTextColor;
  }
  public final void setLinkTextColor(  int color){
    mLinkTextColor=ColorStateList.valueOf(color);
    updateTextColors();
  }
  public final void setLinkTextColor(  ColorStateList colors){
    mLinkTextColor=colors;
    updateTextColors();
  }
  public final ColorStateList getLinkTextColors(){
    return mLinkTextColor;
  }
  public void setGravity(  int gravity){
    if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
      gravity|=Gravity.START;
    }
    if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
      gravity|=Gravity.TOP;
    }
    boolean newLayout=false;
    if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) != (mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK)) {
      newLayout=true;
    }
    if (gravity != mGravity) {
      invalidate();
    }
    mGravity=gravity;
    if (mLayout != null && newLayout) {
      int want=mLayout.getWidth();
      int hintWant=mHintLayout == null ? 0 : mHintLayout.getWidth();
      makeNewLayout(want,hintWant,UNKNOWN_BORING,UNKNOWN_BORING,mRight - mLeft - getCompoundPaddingLeft()- getCompoundPaddingRight(),true);
    }
  }
  public int getGravity(){
    return mGravity;
  }
  public void setMinLines(  int minLines){
    mMinimum=minLines;
    mMinMode=LINES;
    requestLayout();
    invalidate();
  }
  public int getMinLines(){
    return mMinMode == LINES ? mMinimum : -1;
  }
  public void setMinHeight(  int minPixels){
    mMinimum=minPixels;
    mMinMode=PIXELS;
    requestLayout();
    invalidate();
  }
  public int getMinHeight(){
    return mMinMode == PIXELS ? mMinimum : -1;
  }
  public void setMaxLines(  int maxLines){
    mMaximum=maxLines;
    mMaxMode=LINES;
    requestLayout();
    invalidate();
  }
  public int getMaxLines(){
    return mMaxMode == LINES ? mMaximum : -1;
  }
  public void setMaxHeight(  int maxPixels){
    mMaximum=maxPixels;
    mMaxMode=PIXELS;
    requestLayout();
    invalidate();
  }
  public int getMaxHeight(){
    return mMaxMode == PIXELS ? mMaximum : -1;
  }
  public void setLines(  int lines){
    mMaximum=mMinimum=lines;
    mMaxMode=mMinMode=LINES;
    requestLayout();
    invalidate();
  }
  public void setMinEms(  int minEms){
    mMinWidth=minEms;
    mMinWidthMode=EMS;
    requestLayout();
    invalidate();
  }
  public int getMinEms(){
    return mMinWidthMode == EMS ? mMinWidth : -1;
  }
  public void setMinWidth(  int minPixels){
    mMinWidth=minPixels;
    mMinWidthMode=PIXELS;
    requestLayout();
    invalidate();
  }
  public int getMinWidth(){
    return mMinWidthMode == PIXELS ? mMinWidth : -1;
  }
  public void setMaxEms(  int maxEms){
    mMaxWidth=maxEms;
    mMaxWidthMode=EMS;
    requestLayout();
    invalidate();
  }
  public int getMaxEms(){
    return mMaxWidthMode == EMS ? mMaxWidth : -1;
  }
  public void setMaxWidth(  int maxPixels){
    mMaxWidth=maxPixels;
    mMaxWidthMode=PIXELS;
    requestLayout();
    invalidate();
  }
  public int getMaxWidth(){
    return mMaxWidthMode == PIXELS ? mMaxWidth : -1;
  }
  public void setEms(  int ems){
    mMaxWidth=mMinWidth=ems;
    mMaxWidthMode=mMinWidthMode=EMS;
    requestLayout();
    invalidate();
  }
  public void setWidth(  int pixels){
    mMaxWidth=mMinWidth=pixels;
    mMaxWidthMode=mMinWidthMode=PIXELS;
    requestLayout();
    invalidate();
  }
  private void updateTextColors(){
    boolean inval=false;
    final int[] drawableState=getDrawableState();
    int color= 0; if (mTextColor != null) { color= mTextColor.getColorForState(drawableState,0);}
    if (mTextColor != null && color != mCurTextColor) {
      mCurTextColor=color;
      inval=true;
    }
    if (mLinkTextColor != null) {
      color=mLinkTextColor.getColorForState(drawableState,0);
      if (color != mTextPaint.linkColor) {
        mTextPaint.linkColor=color;
        inval=true;
      }
    }
    if (mHintTextColor != null) {
      color=mHintTextColor.getColorForState(drawableState,0);
      if (color != mCurHintTextColor) {
        mCurHintTextColor=color;
        if (mText.length() == 0) {
          inval=true;
        }
      }
    }
    if (inval) {
      if (mEditor != null)       mEditor.invalidateTextDisplayList();
      invalidate();
    }
  }
  protected void drawableStateChanged(){
    super.drawableStateChanged();
    if (mTextColor != null && mTextColor.isStateful() || (mHintTextColor != null && mHintTextColor.isStateful()) || (mLinkTextColor != null && mLinkTextColor.isStateful())) {
      updateTextColors();
    }
    if (mDrawables != null) {
      final int[] state=getDrawableState();
      for (      Drawable dr : mDrawables.mShowing) {
        if (dr != null && dr.isStateful() && dr.setState(state)) {
          invalidateDrawable(dr);
        }
      }
    }
  }
  private Layout.Alignment getLayoutAlignment(){
    Layout.Alignment alignment;
switch (getTextAlignment()) {
case TEXT_ALIGNMENT_GRAVITY:
switch (mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
case Gravity.START:
        alignment=Layout.Alignment.ALIGN_NORMAL;
      break;
case Gravity.END:
    alignment=Layout.Alignment.ALIGN_OPPOSITE;
  break;
case Gravity.LEFT:
alignment=Layout.Alignment.ALIGN_LEFT;
break;
case Gravity.RIGHT:
alignment=Layout.Alignment.ALIGN_RIGHT;
break;
case Gravity.CENTER_HORIZONTAL:
alignment=Layout.Alignment.ALIGN_CENTER;
break;
default :
alignment=Layout.Alignment.ALIGN_NORMAL;
break;
}
break;
case TEXT_ALIGNMENT_TEXT_START:
alignment=Layout.Alignment.ALIGN_NORMAL;
break;
case TEXT_ALIGNMENT_TEXT_END:
alignment=Layout.Alignment.ALIGN_OPPOSITE;
break;
case TEXT_ALIGNMENT_CENTER:
alignment=Layout.Alignment.ALIGN_CENTER;
break;
case TEXT_ALIGNMENT_VIEW_START:
alignment=(getLayoutDirection() == LAYOUT_DIRECTION_RTL) ? Layout.Alignment.ALIGN_RIGHT : Layout.Alignment.ALIGN_LEFT;
break;
case TEXT_ALIGNMENT_VIEW_END:
alignment=(getLayoutDirection() == LAYOUT_DIRECTION_RTL) ? Layout.Alignment.ALIGN_LEFT : Layout.Alignment.ALIGN_RIGHT;
break;
case TEXT_ALIGNMENT_INHERIT:
default :
alignment=Layout.Alignment.ALIGN_NORMAL;
break;
}
return alignment;
}
private void autoSizeText(){
if (!isAutoSizeEnabled()) {
return;
}
if (mNeedsAutoSizeText) {
if (getMeasuredWidth() <= 0 || getMeasuredHeight() <= 0) {
return;
}
final int availableWidth=mHorizontallyScrolling ? VERY_WIDE : getMeasuredWidth() - getTotalPaddingLeft() - getTotalPaddingRight();
final int availableHeight=getMeasuredHeight() - getExtendedPaddingBottom() - getExtendedPaddingTop();
if (availableWidth <= 0 || availableHeight <= 0) {
return;
}
synchronized (TEMP_RECTF) {
TEMP_RECTF.setEmpty();
TEMP_RECTF.right=availableWidth;
TEMP_RECTF.bottom=availableHeight;
final float optimalTextSize=findLargestTextSizeWhichFits(TEMP_RECTF);
if (optimalTextSize != getTextSize()) {
setTextSizeInternal(TypedValue.COMPLEX_UNIT_PX,optimalTextSize,false);
makeNewLayout(availableWidth,0,UNKNOWN_BORING,UNKNOWN_BORING,mRight - mLeft - getCompoundPaddingLeft()- getCompoundPaddingRight(),false);
}
}
}
mNeedsAutoSizeText=true;
}
private int findLargestTextSizeWhichFits(RectF availableSpace){
final int sizesCount=mAutoSizeTextSizesInPx.length;
if (sizesCount == 0) {
throw new IllegalStateException("No available text sizes to choose from.");
}
int bestSizeIndex=0;
int lowIndex=bestSizeIndex + 1;
int highIndex=sizesCount - 1;
int sizeToTryIndex;
while (lowIndex <= highIndex) {
sizeToTryIndex=(lowIndex + highIndex) / 2;
if (suggestedSizeFitsInSpace(mAutoSizeTextSizesInPx[sizeToTryIndex],availableSpace)) {
bestSizeIndex=lowIndex;
lowIndex=sizeToTryIndex + 1;
}
 else {
highIndex=sizeToTryIndex - 1;
bestSizeIndex=highIndex;
}
}
return mAutoSizeTextSizesInPx[bestSizeIndex];
}
public void addTextChangedListener(TextWatcher watcher){
if (mListeners == null) {
mListeners=new ArrayList<TextWatcher>();
}
mListeners.add(watcher);
}
public void removeTextChangedListener(TextWatcher watcher){
if (mListeners != null) {
int i=mListeners.indexOf(watcher);
if (i >= 0) {
mListeners.remove(i);
}
}
}
public enum BufferType {NORMAL, SPANNABLE, EDITABLE}
private boolean isAutoSizeEnabled(){
return supportsAutoSizeText() && mAutoSizeTextType != AUTO_SIZE_TEXT_TYPE_NONE;
}
protected boolean supportsAutoSizeText(){
return true;
}
public TextDirectionHeuristic getTextDirectionHeuristic(){
if (hasPasswordTransformationMethod()) {
return TextDirectionHeuristics.LTR;
}
if (isTypePhone()) {
final java.text.DecimalFormatSymbols symbols=java.text.DecimalFormatSymbols.getInstance(getTextLocale());
final String zero=getDigitStrings(symbols)[0];
final int firstCodepoint=zero.codePointAt(0);
final byte digitDirection=Character.getDirectionality(firstCodepoint);
if (digitDirection == Character.DIRECTIONALITY_RIGHT_TO_LEFT || digitDirection == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC) {
return TextDirectionHeuristics.RTL;
}
 else {
return TextDirectionHeuristics.LTR;
}
}
final boolean defaultIsRtl=(getLayoutDirection() == LAYOUT_DIRECTION_RTL);
switch (getTextDirection()) {
default :
case TEXT_DIRECTION_FIRST_STRONG:
return (defaultIsRtl ? TextDirectionHeuristics.FIRSTSTRONG_RTL : TextDirectionHeuristics.FIRSTSTRONG_LTR);
case TEXT_DIRECTION_ANY_RTL:
return TextDirectionHeuristics.ANYRTL_LTR;
case TEXT_DIRECTION_LTR:
return TextDirectionHeuristics.LTR;
case TEXT_DIRECTION_RTL:
return TextDirectionHeuristics.RTL;
case TEXT_DIRECTION_LOCALE:
return TextDirectionHeuristics.LOCALE;
case TEXT_DIRECTION_FIRST_STRONG_LTR:
return TextDirectionHeuristics.FIRSTSTRONG_LTR;
case TEXT_DIRECTION_FIRST_STRONG_RTL:
return TextDirectionHeuristics.FIRSTSTRONG_RTL;
}
}
public final static int UNKNOWN_BORING=0;
private String mText="";
public TextView(com.ashera.widget.IWidget widget){
super(widget);
mTextPaint=new TextPaint();
}
private int getExtendedPaddingBottom(){
return getCompoundPaddingBottom();
}
private int getExtendedPaddingTop(){
return getCompoundPaddingTop();
}
protected void makeNewLayout(int availableWidth,int i,int unknownBoring,int unknownBoring2,int j,boolean b){
}
public void setUpAutoSizeTextTypeUniform(int autoSizeMin,int autoSizeMax,int autoSizeGranular){
float autoSizeMinTextSizeInPx=autoSizeMin == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE ? com.ashera.widget.PluginInvoker.convertSpToPixel(DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP + "sp") : autoSizeMin;
float autoSizeMaxTextSizeInPx=autoSizeMax == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE ? com.ashera.widget.PluginInvoker.convertSpToPixel(DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP + "sp") : autoSizeMax;
float autoSizeStepGranularityInPx=autoSizeGranular == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE ? DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX : autoSizeGranular;
validateAndSetAutoSizeTextTypeUniformConfiguration(autoSizeMinTextSizeInPx,autoSizeMaxTextSizeInPx,autoSizeStepGranularityInPx);
setupAutoSizeText();
}
public boolean isAutoSizeTextTypeUniform(int autoTextType){
return autoTextType == AUTO_SIZE_TEXT_TYPE_UNIFORM;
}
public void clearAutoSizeTypeConfiguration(){
clearAutoSizeConfiguration();
}
public void autoResizeText(){
autoSizeText();
}
static class TypedValue {
public static int COMPLEX_UNIT_PX=0;
public static float applyDimension(int unit,int i,DisplayMetrics displayMetrics){
return 0;
}
}
private com.ashera.widget.WidgetAttribute widgetAttribute;
private Object handler;
private Runnable mTickRunnable;
public void postDelayed(Runnable mTickRunnable,int delay){
this.mTickRunnable=mTickRunnable;
try {
handler=com.ashera.widget.PluginInvoker.postDelayed(mTickRunnable,delay);
}
 catch (Exception e) {
e.printStackTrace();
}
}
public boolean removeCallbacks(Runnable mTickRunnable){
if (this.mTickRunnable != null && handler != null) {
com.ashera.widget.PluginInvoker.removeCallbacks(handler,mTickRunnable);
}
return true;
}
public boolean isShown(){
return true;
}
@Override public int measureWidth(int widthMode,int widthSize,int width){
if (mMaxWidth != Integer.MAX_VALUE) {
if (mMaxWidthMode == EMS) {
width=Math.min(width,mMaxWidth * getLineHeight());
}
 else {
width=Math.min(width,mMaxWidth);
}
}
if (mMinWidth != 0) {
if (mMinWidthMode == EMS) {
width=Math.max(width,mMinWidth * getLineHeight());
}
 else {
width=Math.max(width,mMinWidth);
}
}
return width;
}
@Override public int measureHeight(int heightMode,int heightSize,int height){
if (mMaximum != Integer.MAX_VALUE) {
if (mMaxMode != LINES) {
height=Math.min(height,mMaximum);
}
 else {
int lineHeightWithPad=getLineHeight() + getLineHeightPadding();
int borderHeightPadding=(getBorderWidth() * 2) + getBorderPadding();
int calc=(mMaximum * lineHeightWithPad) + borderHeightPadding;
if (height < ((lineHeightWithPad) + borderHeightPadding)) {
height=(lineHeightWithPad) + borderHeightPadding;
}
height=Math.min(height,calc);
}
}
if (mMinimum != 0) {
if (mMinMode == LINES) {
height=Math.max(height,(mMinimum * (getLineHeight() + getLineHeightPadding())) + getBorderPadding() + (getBorderWidth() * 2));
}
 else {
height=Math.max(height,mMinimum);
}
}
return height;
}
public int getLineHeightPadding(){
return 0;
}
public int getBorderPadding(){
return 0;
}
public int getLineHeight(){
return 0;
}
public int getBorderWidth(){
return 0;
}
public Layout.Alignment getAlignmentOfLayout(){
return getLayoutAlignment();
}
public boolean hasPasswordTransformationMethod(){
return false;
}
public java.util.Locale getTextLocale(){
return null;
}
public boolean isTypePhone(){
return false;
}
public String[] getDigitStrings(java.text.DecimalFormatSymbols symbols){
return null;
}
interface Editor {
void invalidateTextDisplayList();
}
public class TextPaint {
public int linkColor;
}
public final ColorStateList getTextColors(){
return mTextColor;
}
interface Drawables {
java.util.List<Drawable> mShowing=null;
}
private void invalidateDrawable(Drawable dr){
}
protected void setTextSizeInternal(int unit,float optimalTextSize,boolean b){
}
public boolean suggestedSizeFitsInSpace(int mAutoSizeTextSizeInPx,RectF availableSpace){
float width=availableSpace.width();
float height=availableSpace.height();
setTextSize(mAutoSizeTextSizeInPx * 1f);
int y=computeSize(width);
if (y > height) {
return false;
}
return true;
}
protected float getTextSize(){
return 0;
}
public void setText(String text){
mText=text;
setMyAttribute("text",text);
}
public abstract String getText();
public abstract int computeSize(float width);
private void setTextSize(float f){
setMyAttribute("textSize",f);
}
}
