package r.android.widget;
import r.android.graphics.Canvas;
import r.android.graphics.drawable.Drawable;
import r.android.view.Gravity;
import r.android.view.View;
import r.android.view.ViewGroup;
public class LinearLayout extends ViewGroup {
  public static final int HORIZONTAL=0;
  public static final int VERTICAL=1;
  public static final int SHOW_DIVIDER_NONE=0;
  public static final int SHOW_DIVIDER_BEGINNING=1;
  public static final int SHOW_DIVIDER_MIDDLE=2;
  public static final int SHOW_DIVIDER_END=4;
  private final boolean mAllowInconsistentMeasurement;
  private boolean mBaselineAligned=true;
  private int mBaselineAlignedChildIndex=-1;
  private int mBaselineChildTop=0;
  private int mOrientation;
  private int mGravity=Gravity.START | Gravity.TOP;
  private int mTotalLength;
  private float mWeightSum;
  private boolean mUseLargestChild;
  private int[] mMaxAscent;
  private int[] mMaxDescent;
  private static final int VERTICAL_GRAVITY_COUNT=4;
  private static final int INDEX_CENTER_VERTICAL=0;
  private static final int INDEX_TOP=1;
  private static final int INDEX_BOTTOM=2;
  private static final int INDEX_FILL=3;
  private Drawable mDivider;
  private int mDividerWidth;
  private int mDividerHeight;
  private int mShowDividers;
  private int mDividerPadding;
  private int mLayoutDirection=View.LAYOUT_DIRECTION_UNDEFINED;
  private static boolean sCompatibilityDone=false;
  private static boolean sRemeasureWeightedChildren=true;
  private boolean isShowingDividers(){
    return (mShowDividers != SHOW_DIVIDER_NONE) && (mDivider != null);
  }
  public void setShowDividers(  int showDividers){
    if (showDividers == mShowDividers) {
      return;
    }
    mShowDividers=showDividers;
    setWillNotDraw(!isShowingDividers());
    requestLayout();
  }
  public int getShowDividers(){
    return mShowDividers;
  }
  public Drawable getDividerDrawable(){
    return mDivider;
  }
  public void setDividerDrawable(  Drawable divider){
    if (divider == mDivider) {
      return;
    }
    mDivider=divider;
    if (divider != null) {
      mDividerWidth=divider.getIntrinsicWidth();
      mDividerHeight=divider.getIntrinsicHeight();
    }
 else {
      mDividerWidth=0;
      mDividerHeight=0;
    }
    setWillNotDraw(!isShowingDividers());
    requestLayout();
  }
  public void setDividerPadding(  int padding){
    if (padding == mDividerPadding) {
      return;
    }
    mDividerPadding=padding;
    if (isShowingDividers()) {
      requestLayout();
      invalidate();
    }
  }
  public int getDividerPadding(){
    return mDividerPadding;
  }
  protected void onDraw(  Canvas canvas){
    if (mDivider == null) {
      return;
    }
    if (mOrientation == VERTICAL) {
      drawDividersVertical(canvas);
    }
 else {
      drawDividersHorizontal(canvas);
    }
  }
  void drawDividersVertical(  Canvas canvas){
    final int count=getVirtualChildCount();
    for (int i=0; i < count; i++) {
      final View child=getVirtualChildAt(i);
      if (child != null && child.getVisibility() != GONE) {
        if (hasDividerBeforeChildAt(i)) {
          final LayoutParams lp=(LayoutParams)child.getLayoutParams();
          final int top=child.getTop() - lp.topMargin - mDividerHeight;
          drawHorizontalDivider(canvas,top);
        }
      }
    }
    if (hasDividerBeforeChildAt(count)) {
      final View child=getLastNonGoneChild();
      int bottom=0;
      if (child == null) {
        bottom=getHeight() - getPaddingBottom() - mDividerHeight;
      }
 else {
        final LayoutParams lp=(LayoutParams)child.getLayoutParams();
        bottom=child.getBottom() + lp.bottomMargin;
      }
      drawHorizontalDivider(canvas,bottom);
    }
  }
  private View getLastNonGoneChild(){
    for (int i=getVirtualChildCount() - 1; i >= 0; i--) {
      final View child=getVirtualChildAt(i);
      if (child != null && child.getVisibility() != GONE) {
        return child;
      }
    }
    return null;
  }
  void drawDividersHorizontal(  Canvas canvas){
    final int count=getVirtualChildCount();
    final boolean isLayoutRtl=isLayoutRtl();
    for (int i=0; i < count; i++) {
      final View child=getVirtualChildAt(i);
      if (child != null && child.getVisibility() != GONE) {
        if (hasDividerBeforeChildAt(i)) {
          final LayoutParams lp=(LayoutParams)child.getLayoutParams();
          final int position;
          if (isLayoutRtl) {
            position=child.getRight() + lp.rightMargin;
          }
 else {
            position=child.getLeft() - lp.leftMargin - mDividerWidth;
          }
          drawVerticalDivider(canvas,position);
        }
      }
    }
    if (hasDividerBeforeChildAt(count)) {
      final View child=getLastNonGoneChild();
      int position;
      if (child == null) {
        if (isLayoutRtl) {
          position=getPaddingLeft();
        }
 else {
          position=getWidth() - getPaddingRight() - mDividerWidth;
        }
      }
 else {
        final LayoutParams lp=(LayoutParams)child.getLayoutParams();
        if (isLayoutRtl) {
          position=child.getLeft() - lp.leftMargin - mDividerWidth;
        }
 else {
          position=child.getRight() + lp.rightMargin;
        }
      }
      drawVerticalDivider(canvas,position);
    }
  }
  void drawHorizontalDivider(  Canvas canvas,  int top){
    mDivider.setBounds(getPaddingLeft() + mDividerPadding,top,getWidth() - getPaddingRight() - mDividerPadding,top + mDividerHeight);
    mDivider.draw(canvas);
  }
  void drawVerticalDivider(  Canvas canvas,  int left){
    mDivider.setBounds(left,getPaddingTop() + mDividerPadding,left + mDividerWidth,getHeight() - getPaddingBottom() - mDividerPadding);
    mDivider.draw(canvas);
  }
  public boolean isBaselineAligned(){
    return mBaselineAligned;
  }
  public void setBaselineAligned(  boolean baselineAligned){
    mBaselineAligned=baselineAligned;
  }
  public boolean isMeasureWithLargestChildEnabled(){
    return mUseLargestChild;
  }
  public void setMeasureWithLargestChildEnabled(  boolean enabled){
    mUseLargestChild=enabled;
  }
  public int getBaseline(){
    if (mBaselineAlignedChildIndex < 0) {
      return super.getBaseline();
    }
    if (getChildCount() <= mBaselineAlignedChildIndex) {
      throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout " + "set to an index that is out of bounds.");
    }
    final View child=getChildAt(mBaselineAlignedChildIndex);
    final int childBaseline=child.getBaseline();
    if (childBaseline == -1) {
      if (mBaselineAlignedChildIndex == 0) {
        return -1;
      }
      throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout " + "points to a View that doesn't know how to get its baseline.");
    }
    int childTop=mBaselineChildTop;
    if (mOrientation == VERTICAL) {
      final int majorGravity=mGravity & Gravity.VERTICAL_GRAVITY_MASK;
      if (majorGravity != Gravity.TOP) {
switch (majorGravity) {
case Gravity.BOTTOM:
          childTop=mBottom - mTop - mPaddingBottom- mTotalLength;
        break;
case Gravity.CENTER_VERTICAL:
      childTop+=((mBottom - mTop - mPaddingTop- mPaddingBottom) - mTotalLength) / 2;
    break;
}
}
}
LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)child.getLayoutParams();
return childTop + lp.topMargin + childBaseline;
}
public int getBaselineAlignedChildIndex(){
return mBaselineAlignedChildIndex;
}
public void setBaselineAlignedChildIndex(int i){
if ((i < 0) || (i >= getChildCount())) {
throw new IllegalArgumentException("base aligned child index out " + "of range (0, " + getChildCount() + ")");
}
mBaselineAlignedChildIndex=i;
}
View getVirtualChildAt(int index){
return getChildAt(index);
}
int getVirtualChildCount(){
return getChildCount();
}
public float getWeightSum(){
return mWeightSum;
}
public void setWeightSum(float weightSum){
mWeightSum=Math.max(0.0f,weightSum);
}
protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
if (mOrientation == VERTICAL) {
measureVertical(widthMeasureSpec,heightMeasureSpec);
}
 else {
measureHorizontal(widthMeasureSpec,heightMeasureSpec);
}
}
protected boolean hasDividerBeforeChildAt(int childIndex){
if (childIndex == getVirtualChildCount()) {
return (mShowDividers & SHOW_DIVIDER_END) != 0;
}
boolean allViewsAreGoneBefore=allViewsAreGoneBefore(childIndex);
if (allViewsAreGoneBefore) {
return (mShowDividers & SHOW_DIVIDER_BEGINNING) != 0;
}
 else {
return (mShowDividers & SHOW_DIVIDER_MIDDLE) != 0;
}
}
private boolean allViewsAreGoneBefore(int childIndex){
for (int i=childIndex - 1; i >= 0; i--) {
final View child=getVirtualChildAt(i);
if (child != null && child.getVisibility() != GONE) {
return false;
}
}
return true;
}
void measureVertical(int widthMeasureSpec,int heightMeasureSpec){
mTotalLength=0;
int maxWidth=0;
int childState=0;
int alternativeMaxWidth=0;
int weightedMaxWidth=0;
boolean allFillParent=true;
float totalWeight=0;
final int count=getVirtualChildCount();
final int widthMode=MeasureSpec.getMode(widthMeasureSpec);
final int heightMode=MeasureSpec.getMode(heightMeasureSpec);
boolean matchWidth=false;
boolean skippedMeasure=false;
final int baselineChildIndex=mBaselineAlignedChildIndex;
final boolean useLargestChild=mUseLargestChild;
int largestChildHeight=Integer.MIN_VALUE;
int consumedExcessSpace=0;
int nonSkippedChildCount=0;
for (int i=0; i < count; ++i) {
final View child=getVirtualChildAt(i);
if (child == null) {
mTotalLength+=measureNullChild(i);
continue;
}
if (child.getVisibility() == View.GONE) {
i+=getChildrenSkipCount(child,i);
continue;
}
nonSkippedChildCount++;
if (hasDividerBeforeChildAt(i)) {
mTotalLength+=mDividerHeight;
}
final LayoutParams lp=(LayoutParams)child.getLayoutParams();
totalWeight+=lp.weight;
final boolean useExcessSpace=lp.height == 0 && lp.weight > 0;
if (heightMode == MeasureSpec.EXACTLY && useExcessSpace) {
final int totalLength=mTotalLength;
mTotalLength=Math.max(totalLength,totalLength + lp.topMargin + lp.bottomMargin);
skippedMeasure=true;
}
 else {
if (useExcessSpace) {
  lp.height=LayoutParams.WRAP_CONTENT;
}
final int usedHeight=totalWeight == 0 ? mTotalLength : 0;
measureChildBeforeLayout(child,i,widthMeasureSpec,0,heightMeasureSpec,usedHeight);
final int childHeight=child.getMeasuredHeight();
if (useExcessSpace) {
  lp.height=0;
  consumedExcessSpace+=childHeight;
}
final int totalLength=mTotalLength;
mTotalLength=Math.max(totalLength,totalLength + childHeight + lp.topMargin+ lp.bottomMargin+ getNextLocationOffset(child));
if (useLargestChild) {
  largestChildHeight=Math.max(childHeight,largestChildHeight);
}
}
if ((baselineChildIndex >= 0) && (baselineChildIndex == i + 1)) {
mBaselineChildTop=mTotalLength;
}
if (i < baselineChildIndex && lp.weight > 0) {
throw new RuntimeException("A child of LinearLayout with index " + "less than mBaselineAlignedChildIndex has weight > 0, which " + "won't work.  Either remove the weight, or don't set "+ "mBaselineAlignedChildIndex.");
}
boolean matchWidthLocally=false;
if (widthMode != MeasureSpec.EXACTLY && lp.width == LayoutParams.MATCH_PARENT) {
matchWidth=true;
matchWidthLocally=true;
}
final int margin=lp.leftMargin + lp.rightMargin;
final int measuredWidth=child.getMeasuredWidth() + margin;
maxWidth=Math.max(maxWidth,measuredWidth);
childState=combineMeasuredStates(childState,child.getMeasuredState());
allFillParent=allFillParent && lp.width == LayoutParams.MATCH_PARENT;
if (lp.weight > 0) {
weightedMaxWidth=Math.max(weightedMaxWidth,matchWidthLocally ? margin : measuredWidth);
}
 else {
alternativeMaxWidth=Math.max(alternativeMaxWidth,matchWidthLocally ? margin : measuredWidth);
}
i+=getChildrenSkipCount(child,i);
}
if (nonSkippedChildCount > 0 && hasDividerBeforeChildAt(count)) {
mTotalLength+=mDividerHeight;
}
if (useLargestChild && (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED)) {
mTotalLength=0;
for (int i=0; i < count; ++i) {
final View child=getVirtualChildAt(i);
if (child == null) {
  mTotalLength+=measureNullChild(i);
  continue;
}
if (child.getVisibility() == GONE) {
  i+=getChildrenSkipCount(child,i);
  continue;
}
final LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)child.getLayoutParams();
final int totalLength=mTotalLength;
mTotalLength=Math.max(totalLength,totalLength + largestChildHeight + lp.topMargin+ lp.bottomMargin+ getNextLocationOffset(child));
}
}
mTotalLength+=mPaddingTop + mPaddingBottom;
int heightSize=mTotalLength;
heightSize=Math.max(heightSize,getSuggestedMinimumHeight());
int heightSizeAndState=resolveSizeAndState(heightSize,heightMeasureSpec,0);
heightSize=heightSizeAndState & MEASURED_SIZE_MASK;
int remainingExcess=heightSize - mTotalLength + (mAllowInconsistentMeasurement ? 0 : consumedExcessSpace);
if (skippedMeasure || ((sRemeasureWeightedChildren || remainingExcess != 0) && totalWeight > 0.0f)) {
float remainingWeightSum=mWeightSum > 0.0f ? mWeightSum : totalWeight;
mTotalLength=0;
for (int i=0; i < count; ++i) {
final View child=getVirtualChildAt(i);
if (child == null || child.getVisibility() == View.GONE) {
  continue;
}
final LayoutParams lp=(LayoutParams)child.getLayoutParams();
final float childWeight=lp.weight;
if (childWeight > 0) {
  final int share=(int)(childWeight * remainingExcess / remainingWeightSum);
  remainingExcess-=share;
  remainingWeightSum-=childWeight;
  final int childHeight;
  if (mUseLargestChild && heightMode != MeasureSpec.EXACTLY) {
    childHeight=largestChildHeight;
  }
 else   if (lp.height == 0 && (!mAllowInconsistentMeasurement || heightMode == MeasureSpec.EXACTLY)) {
    childHeight=share;
  }
 else {
    childHeight=child.getMeasuredHeight() + share;
  }
  final int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(Math.max(0,childHeight),MeasureSpec.EXACTLY);
  final int childWidthMeasureSpec=getChildMeasureSpec(widthMeasureSpec,mPaddingLeft + mPaddingRight + lp.leftMargin+ lp.rightMargin,lp.width);
  child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
  childState=combineMeasuredStates(childState,child.getMeasuredState() & (MEASURED_STATE_MASK >> MEASURED_HEIGHT_STATE_SHIFT));
}
final int margin=lp.leftMargin + lp.rightMargin;
final int measuredWidth=child.getMeasuredWidth() + margin;
maxWidth=Math.max(maxWidth,measuredWidth);
boolean matchWidthLocally=widthMode != MeasureSpec.EXACTLY && lp.width == LayoutParams.MATCH_PARENT;
alternativeMaxWidth=Math.max(alternativeMaxWidth,matchWidthLocally ? margin : measuredWidth);
allFillParent=allFillParent && lp.width == LayoutParams.MATCH_PARENT;
final int totalLength=mTotalLength;
mTotalLength=Math.max(totalLength,totalLength + child.getMeasuredHeight() + lp.topMargin+ lp.bottomMargin+ getNextLocationOffset(child));
}
mTotalLength+=mPaddingTop + mPaddingBottom;
}
 else {
alternativeMaxWidth=Math.max(alternativeMaxWidth,weightedMaxWidth);
if (useLargestChild && heightMode != MeasureSpec.EXACTLY) {
for (int i=0; i < count; i++) {
  final View child=getVirtualChildAt(i);
  if (child == null || child.getVisibility() == View.GONE) {
    continue;
  }
  final LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)child.getLayoutParams();
  float childExtra=lp.weight;
  if (childExtra > 0) {
    child.measure(MeasureSpec.makeMeasureSpec(child.getMeasuredWidth(),MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(largestChildHeight,MeasureSpec.EXACTLY));
  }
}
}
}
if (!allFillParent && widthMode != MeasureSpec.EXACTLY) {
maxWidth=alternativeMaxWidth;
}
maxWidth+=mPaddingLeft + mPaddingRight;
maxWidth=Math.max(maxWidth,getSuggestedMinimumWidth());
setMeasuredDimension(resolveSizeAndState(maxWidth,widthMeasureSpec,childState),heightSizeAndState);
if (matchWidth) {
forceUniformWidth(count,heightMeasureSpec);
}
}
private void forceUniformWidth(int count,int heightMeasureSpec){
int uniformMeasureSpec=MeasureSpec.makeMeasureSpec(getMeasuredWidth(),MeasureSpec.EXACTLY);
for (int i=0; i < count; ++i) {
final View child=getVirtualChildAt(i);
if (child != null && child.getVisibility() != GONE) {
LinearLayout.LayoutParams lp=((LinearLayout.LayoutParams)child.getLayoutParams());
if (lp.width == LayoutParams.MATCH_PARENT) {
  int oldHeight=lp.height;
  lp.height=child.getMeasuredHeight();
  measureChildWithMargins(child,uniformMeasureSpec,0,heightMeasureSpec,0);
  lp.height=oldHeight;
}
}
}
}
void measureHorizontal(int widthMeasureSpec,int heightMeasureSpec){
mTotalLength=0;
int maxHeight=0;
int childState=0;
int alternativeMaxHeight=0;
int weightedMaxHeight=0;
boolean allFillParent=true;
float totalWeight=0;
final int count=getVirtualChildCount();
final int widthMode=MeasureSpec.getMode(widthMeasureSpec);
final int heightMode=MeasureSpec.getMode(heightMeasureSpec);
boolean matchHeight=false;
boolean skippedMeasure=false;
if (mMaxAscent == null || mMaxDescent == null) {
mMaxAscent=new int[VERTICAL_GRAVITY_COUNT];
mMaxDescent=new int[VERTICAL_GRAVITY_COUNT];
}
final int[] maxAscent=mMaxAscent;
final int[] maxDescent=mMaxDescent;
maxAscent[0]=maxAscent[1]=maxAscent[2]=maxAscent[3]=-1;
maxDescent[0]=maxDescent[1]=maxDescent[2]=maxDescent[3]=-1;
final boolean baselineAligned=mBaselineAligned;
final boolean useLargestChild=mUseLargestChild;
final boolean isExactly=widthMode == MeasureSpec.EXACTLY;
int largestChildWidth=Integer.MIN_VALUE;
int usedExcessSpace=0;
int nonSkippedChildCount=0;
for (int i=0; i < count; ++i) {
final View child=getVirtualChildAt(i);
if (child == null) {
mTotalLength+=measureNullChild(i);
continue;
}
if (child.getVisibility() == GONE) {
i+=getChildrenSkipCount(child,i);
continue;
}
nonSkippedChildCount++;
if (hasDividerBeforeChildAt(i)) {
mTotalLength+=mDividerWidth;
}
final LayoutParams lp=(LayoutParams)child.getLayoutParams();
totalWeight+=lp.weight;
final boolean useExcessSpace=lp.width == 0 && lp.weight > 0;
if (widthMode == MeasureSpec.EXACTLY && useExcessSpace) {
if (isExactly) {
  mTotalLength+=lp.leftMargin + lp.rightMargin;
}
 else {
  final int totalLength=mTotalLength;
  mTotalLength=Math.max(totalLength,totalLength + lp.leftMargin + lp.rightMargin);
}
if (baselineAligned) {
  final int freeWidthSpec=MeasureSpec.makeSafeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.UNSPECIFIED);
  final int freeHeightSpec=MeasureSpec.makeSafeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec),MeasureSpec.UNSPECIFIED);
  child.measure(freeWidthSpec,freeHeightSpec);
}
 else {
  skippedMeasure=true;
}
}
 else {
if (useExcessSpace) {
  lp.width=LayoutParams.WRAP_CONTENT;
}
final int usedWidth=totalWeight == 0 ? mTotalLength : 0;
measureChildBeforeLayout(child,i,widthMeasureSpec,usedWidth,heightMeasureSpec,0);
final int childWidth=child.getMeasuredWidth();
if (useExcessSpace) {
  lp.width=0;
  usedExcessSpace+=childWidth;
}
if (isExactly) {
  mTotalLength+=childWidth + lp.leftMargin + lp.rightMargin+ getNextLocationOffset(child);
}
 else {
  final int totalLength=mTotalLength;
  mTotalLength=Math.max(totalLength,totalLength + childWidth + lp.leftMargin+ lp.rightMargin+ getNextLocationOffset(child));
}
if (useLargestChild) {
  largestChildWidth=Math.max(childWidth,largestChildWidth);
}
}
boolean matchHeightLocally=false;
if (heightMode != MeasureSpec.EXACTLY && lp.height == LayoutParams.MATCH_PARENT) {
matchHeight=true;
matchHeightLocally=true;
}
final int margin=lp.topMargin + lp.bottomMargin;
final int childHeight=child.getMeasuredHeight() + margin;
childState=combineMeasuredStates(childState,child.getMeasuredState());
if (baselineAligned) {
final int childBaseline=child.getBaseline();
if (childBaseline != -1) {
  final int gravity=(lp.gravity < 0 ? mGravity : lp.gravity) & Gravity.VERTICAL_GRAVITY_MASK;
  final int index=((gravity >> Gravity.AXIS_Y_SHIFT) & ~Gravity.AXIS_SPECIFIED) >> 1;
  maxAscent[index]=Math.max(maxAscent[index],childBaseline);
  maxDescent[index]=Math.max(maxDescent[index],childHeight - childBaseline);
}
}
maxHeight=Math.max(maxHeight,childHeight);
allFillParent=allFillParent && lp.height == LayoutParams.MATCH_PARENT;
if (lp.weight > 0) {
weightedMaxHeight=Math.max(weightedMaxHeight,matchHeightLocally ? margin : childHeight);
}
 else {
alternativeMaxHeight=Math.max(alternativeMaxHeight,matchHeightLocally ? margin : childHeight);
}
i+=getChildrenSkipCount(child,i);
}
if (nonSkippedChildCount > 0 && hasDividerBeforeChildAt(count)) {
mTotalLength+=mDividerWidth;
}
if (maxAscent[INDEX_TOP] != -1 || maxAscent[INDEX_CENTER_VERTICAL] != -1 || maxAscent[INDEX_BOTTOM] != -1 || maxAscent[INDEX_FILL] != -1) {
final int ascent=Math.max(maxAscent[INDEX_FILL],Math.max(maxAscent[INDEX_CENTER_VERTICAL],Math.max(maxAscent[INDEX_TOP],maxAscent[INDEX_BOTTOM])));
final int descent=Math.max(maxDescent[INDEX_FILL],Math.max(maxDescent[INDEX_CENTER_VERTICAL],Math.max(maxDescent[INDEX_TOP],maxDescent[INDEX_BOTTOM])));
maxHeight=Math.max(maxHeight,ascent + descent);
}
if (useLargestChild && (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED)) {
mTotalLength=0;
for (int i=0; i < count; ++i) {
final View child=getVirtualChildAt(i);
if (child == null) {
  mTotalLength+=measureNullChild(i);
  continue;
}
if (child.getVisibility() == GONE) {
  i+=getChildrenSkipCount(child,i);
  continue;
}
final LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)child.getLayoutParams();
if (isExactly) {
  mTotalLength+=largestChildWidth + lp.leftMargin + lp.rightMargin+ getNextLocationOffset(child);
}
 else {
  final int totalLength=mTotalLength;
  mTotalLength=Math.max(totalLength,totalLength + largestChildWidth + lp.leftMargin+ lp.rightMargin+ getNextLocationOffset(child));
}
}
}
mTotalLength+=mPaddingLeft + mPaddingRight;
int widthSize=mTotalLength;
widthSize=Math.max(widthSize,getSuggestedMinimumWidth());
int widthSizeAndState=resolveSizeAndState(widthSize,widthMeasureSpec,0);
widthSize=widthSizeAndState & MEASURED_SIZE_MASK;
int remainingExcess=widthSize - mTotalLength + (mAllowInconsistentMeasurement ? 0 : usedExcessSpace);
if (skippedMeasure || ((sRemeasureWeightedChildren || remainingExcess != 0) && totalWeight > 0.0f)) {
float remainingWeightSum=mWeightSum > 0.0f ? mWeightSum : totalWeight;
maxAscent[0]=maxAscent[1]=maxAscent[2]=maxAscent[3]=-1;
maxDescent[0]=maxDescent[1]=maxDescent[2]=maxDescent[3]=-1;
maxHeight=-1;
mTotalLength=0;
for (int i=0; i < count; ++i) {
final View child=getVirtualChildAt(i);
if (child == null || child.getVisibility() == View.GONE) {
  continue;
}
final LayoutParams lp=(LayoutParams)child.getLayoutParams();
final float childWeight=lp.weight;
if (childWeight > 0) {
  final int share=(int)(childWeight * remainingExcess / remainingWeightSum);
  remainingExcess-=share;
  remainingWeightSum-=childWeight;
  final int childWidth;
  if (mUseLargestChild && widthMode != MeasureSpec.EXACTLY) {
    childWidth=largestChildWidth;
  }
 else   if (lp.width == 0 && (!mAllowInconsistentMeasurement || widthMode == MeasureSpec.EXACTLY)) {
    childWidth=share;
  }
 else {
    childWidth=child.getMeasuredWidth() + share;
  }
  final int childWidthMeasureSpec=MeasureSpec.makeMeasureSpec(Math.max(0,childWidth),MeasureSpec.EXACTLY);
  final int childHeightMeasureSpec=getChildMeasureSpec(heightMeasureSpec,mPaddingTop + mPaddingBottom + lp.topMargin+ lp.bottomMargin,lp.height);
  child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
  childState=combineMeasuredStates(childState,child.getMeasuredState() & MEASURED_STATE_MASK);
}
if (isExactly) {
  mTotalLength+=child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin+ getNextLocationOffset(child);
}
 else {
  final int totalLength=mTotalLength;
  mTotalLength=Math.max(totalLength,totalLength + child.getMeasuredWidth() + lp.leftMargin+ lp.rightMargin+ getNextLocationOffset(child));
}
boolean matchHeightLocally=heightMode != MeasureSpec.EXACTLY && lp.height == LayoutParams.MATCH_PARENT;
final int margin=lp.topMargin + lp.bottomMargin;
int childHeight=child.getMeasuredHeight() + margin;
maxHeight=Math.max(maxHeight,childHeight);
alternativeMaxHeight=Math.max(alternativeMaxHeight,matchHeightLocally ? margin : childHeight);
allFillParent=allFillParent && lp.height == LayoutParams.MATCH_PARENT;
if (baselineAligned) {
  final int childBaseline=child.getBaseline();
  if (childBaseline != -1) {
    final int gravity=(lp.gravity < 0 ? mGravity : lp.gravity) & Gravity.VERTICAL_GRAVITY_MASK;
    final int index=((gravity >> Gravity.AXIS_Y_SHIFT) & ~Gravity.AXIS_SPECIFIED) >> 1;
    maxAscent[index]=Math.max(maxAscent[index],childBaseline);
    maxDescent[index]=Math.max(maxDescent[index],childHeight - childBaseline);
  }
}
}
mTotalLength+=mPaddingLeft + mPaddingRight;
if (maxAscent[INDEX_TOP] != -1 || maxAscent[INDEX_CENTER_VERTICAL] != -1 || maxAscent[INDEX_BOTTOM] != -1 || maxAscent[INDEX_FILL] != -1) {
final int ascent=Math.max(maxAscent[INDEX_FILL],Math.max(maxAscent[INDEX_CENTER_VERTICAL],Math.max(maxAscent[INDEX_TOP],maxAscent[INDEX_BOTTOM])));
final int descent=Math.max(maxDescent[INDEX_FILL],Math.max(maxDescent[INDEX_CENTER_VERTICAL],Math.max(maxDescent[INDEX_TOP],maxDescent[INDEX_BOTTOM])));
maxHeight=Math.max(maxHeight,ascent + descent);
}
}
 else {
alternativeMaxHeight=Math.max(alternativeMaxHeight,weightedMaxHeight);
if (useLargestChild && widthMode != MeasureSpec.EXACTLY) {
for (int i=0; i < count; i++) {
  final View child=getVirtualChildAt(i);
  if (child == null || child.getVisibility() == View.GONE) {
    continue;
  }
  final LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)child.getLayoutParams();
  float childExtra=lp.weight;
  if (childExtra > 0) {
    child.measure(MeasureSpec.makeMeasureSpec(largestChildWidth,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(),MeasureSpec.EXACTLY));
  }
}
}
}
if (!allFillParent && heightMode != MeasureSpec.EXACTLY) {
maxHeight=alternativeMaxHeight;
}
maxHeight+=mPaddingTop + mPaddingBottom;
maxHeight=Math.max(maxHeight,getSuggestedMinimumHeight());
setMeasuredDimension(widthSizeAndState | (childState & MEASURED_STATE_MASK),resolveSizeAndState(maxHeight,heightMeasureSpec,(childState << MEASURED_HEIGHT_STATE_SHIFT)));
if (matchHeight) {
forceUniformHeight(count,widthMeasureSpec);
}
}
private void forceUniformHeight(int count,int widthMeasureSpec){
int uniformMeasureSpec=MeasureSpec.makeMeasureSpec(getMeasuredHeight(),MeasureSpec.EXACTLY);
for (int i=0; i < count; ++i) {
final View child=getVirtualChildAt(i);
if (child != null && child.getVisibility() != GONE) {
LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)child.getLayoutParams();
if (lp.height == LayoutParams.MATCH_PARENT) {
  int oldWidth=lp.width;
  lp.width=child.getMeasuredWidth();
  measureChildWithMargins(child,widthMeasureSpec,0,uniformMeasureSpec,0);
  lp.width=oldWidth;
}
}
}
}
int getChildrenSkipCount(View child,int index){
return 0;
}
int measureNullChild(int childIndex){
return 0;
}
void measureChildBeforeLayout(View child,int childIndex,int widthMeasureSpec,int totalWidth,int heightMeasureSpec,int totalHeight){
measureChildWithMargins(child,widthMeasureSpec,totalWidth,heightMeasureSpec,totalHeight);
}
int getLocationOffset(View child){
return 0;
}
int getNextLocationOffset(View child){
return 0;
}
protected void onLayout(boolean changed,int l,int t,int r,int b){
if (mOrientation == VERTICAL) {
layoutVertical(l,t,r,b);
}
 else {
layoutHorizontal(l,t,r,b);
}
}
void layoutVertical(int left,int top,int right,int bottom){
final int paddingLeft=mPaddingLeft;
int childTop;
int childLeft;
final int width=right - left;
int childRight=width - mPaddingRight;
int childSpace=width - paddingLeft - mPaddingRight;
final int count=getVirtualChildCount();
final int majorGravity=mGravity & Gravity.VERTICAL_GRAVITY_MASK;
final int minorGravity=mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
switch (majorGravity) {
case Gravity.BOTTOM:
childTop=mPaddingTop + bottom - top - mTotalLength;
break;
case Gravity.CENTER_VERTICAL:
childTop=mPaddingTop + (bottom - top - mTotalLength) / 2;
break;
case Gravity.TOP:
default :
childTop=mPaddingTop;
break;
}
for (int i=0; i < count; i++) {
final View child=getVirtualChildAt(i);
if (child == null) {
childTop+=measureNullChild(i);
}
 else if (child.getVisibility() != GONE) {
final int childWidth=child.getMeasuredWidth();
final int childHeight=child.getMeasuredHeight();
final LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)child.getLayoutParams();
int gravity=lp.gravity;
if (gravity < 0) {
gravity=minorGravity;
}
final int layoutDirection=getLayoutDirection();
final int absoluteGravity=Gravity.getAbsoluteGravity(gravity,layoutDirection);
switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
case Gravity.CENTER_HORIZONTAL:
childLeft=paddingLeft + ((childSpace - childWidth) / 2) + lp.leftMargin - lp.rightMargin;
break;
case Gravity.RIGHT:
childLeft=childRight - childWidth - lp.rightMargin;
break;
case Gravity.LEFT:
default :
childLeft=paddingLeft + lp.leftMargin;
break;
}
if (hasDividerBeforeChildAt(i)) {
childTop+=mDividerHeight;
}
childTop+=lp.topMargin;
setChildFrame(child,childLeft,childTop + getLocationOffset(child),childWidth,childHeight);
childTop+=childHeight + lp.bottomMargin + getNextLocationOffset(child);
i+=getChildrenSkipCount(child,i);
}
}
}
void layoutHorizontal(int left,int top,int right,int bottom){
final boolean isLayoutRtl=isLayoutRtl();
final int paddingTop=mPaddingTop;
int childTop;
int childLeft;
final int height=bottom - top;
int childBottom=height - mPaddingBottom;
int childSpace=height - paddingTop - mPaddingBottom;
final int count=getVirtualChildCount();
final int majorGravity=mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
final int minorGravity=mGravity & Gravity.VERTICAL_GRAVITY_MASK;
final boolean baselineAligned=mBaselineAligned;
final int[] maxAscent=mMaxAscent;
final int[] maxDescent=mMaxDescent;
final int layoutDirection=getLayoutDirection();
switch (Gravity.getAbsoluteGravity(majorGravity,layoutDirection)) {
case Gravity.RIGHT:
childLeft=mPaddingLeft + right - left - mTotalLength;
break;
case Gravity.CENTER_HORIZONTAL:
childLeft=mPaddingLeft + (right - left - mTotalLength) / 2;
break;
case Gravity.LEFT:
default :
childLeft=mPaddingLeft;
break;
}
int start=0;
int dir=1;
if (isLayoutRtl) {
start=count - 1;
dir=-1;
}
for (int i=0; i < count; i++) {
final int childIndex=start + dir * i;
final View child=getVirtualChildAt(childIndex);
if (child == null) {
childLeft+=measureNullChild(childIndex);
}
 else if (child.getVisibility() != GONE) {
final int childWidth=child.getMeasuredWidth();
final int childHeight=child.getMeasuredHeight();
int childBaseline=-1;
final LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)child.getLayoutParams();
if (baselineAligned && lp.height != LayoutParams.MATCH_PARENT) {
childBaseline=child.getBaseline();
}
int gravity=lp.gravity;
if (gravity < 0) {
gravity=minorGravity;
}
switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
case Gravity.TOP:
childTop=paddingTop + lp.topMargin;
if (childBaseline != -1) {
childTop+=maxAscent[INDEX_TOP] - childBaseline;
}
break;
case Gravity.CENTER_VERTICAL:
childTop=paddingTop + ((childSpace - childHeight) / 2) + lp.topMargin - lp.bottomMargin;
break;
case Gravity.BOTTOM:
childTop=childBottom - childHeight - lp.bottomMargin;
if (childBaseline != -1) {
int descent=child.getMeasuredHeight() - childBaseline;
childTop-=(maxDescent[INDEX_BOTTOM] - descent);
}
break;
default :
childTop=paddingTop;
break;
}
if (hasDividerBeforeChildAt(childIndex)) {
childLeft+=mDividerWidth;
}
childLeft+=lp.leftMargin;
setChildFrame(child,childLeft + getLocationOffset(child),childTop,childWidth,childHeight);
childLeft+=childWidth + lp.rightMargin + getNextLocationOffset(child);
i+=getChildrenSkipCount(child,childIndex);
}
}
}
private void setChildFrame(View child,int left,int top,int width,int height){
child.layout(left,top,left + width,top + height);
}
public void setOrientation(int orientation){
if (mOrientation != orientation) {
mOrientation=orientation;
requestLayout();
}
}
public int getOrientation(){
return mOrientation;
}
public void setGravity(int gravity){
if (mGravity != gravity) {
if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
gravity|=Gravity.START;
}
if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
gravity|=Gravity.TOP;
}
mGravity=gravity;
requestLayout();
}
}
public int getGravity(){
return mGravity;
}
protected LayoutParams generateDefaultLayoutParams(){
if (mOrientation == HORIZONTAL) {
return new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
}
 else if (mOrientation == VERTICAL) {
return new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
}
return null;
}
protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp){
if (sPreserveMarginParamsInLayoutParamConversion) {
if (lp instanceof LayoutParams) {
return new LayoutParams((LayoutParams)lp);
}
 else if (lp instanceof MarginLayoutParams) {
return new LayoutParams((MarginLayoutParams)lp);
}
}
return new LayoutParams(lp);
}
protected boolean checkLayoutParams(ViewGroup.LayoutParams p){
return p instanceof LinearLayout.LayoutParams;
}
public static class LayoutParams extends ViewGroup.MarginLayoutParams {
public float weight;
public int gravity=-1;
public LayoutParams(int width,int height){
super(width,height);
weight=0;
}
public LayoutParams(ViewGroup.LayoutParams p){
super(p);
}
public LayoutParams(LayoutParams source){
super(source);
this.weight=source.weight;
this.gravity=source.gravity;
}
}
public LinearLayout(r.android.content.Context context){
mAllowInconsistentMeasurement=false;
}
public LinearLayout(){
mAllowInconsistentMeasurement=false;
}
public void updateDividerHeight(int mDividerHeight){
this.mDividerHeight=mDividerHeight;
if (mDivider != null) {
mDivider.setMinimumHeight(mDividerHeight);
}
requestLayout();
}
}
