package r.android.widget;
import static r.android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static r.android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import r.android.content.Context;
import r.android.graphics.Rect;
import r.android.graphics.drawable.Drawable;
import r.android.os.Build;
import r.android.os.IBinder;
import r.android.view.Gravity;
import r.android.view.View;
import r.android.view.View.OnAttachStateChangeListener;
import r.android.view.ViewGroup;
import r.android.view.ViewParent;
import r.android.view.ViewTreeObserver;
import r.android.view.ViewTreeObserver.OnScrollChangedListener;
import r.android.view.WindowManager;
import r.android.widget.RelativeLayout .LayoutParams;
import java.lang.ref.WeakReference;
public class PopupWindow {
  public static final int INPUT_METHOD_FROM_FOCUSABLE=0;
  public static final int INPUT_METHOD_NEEDED=1;
  public static final int INPUT_METHOD_NOT_NEEDED=2;
  private static final int DEFAULT_ANCHORED_GRAVITY=Gravity.TOP | Gravity.START;
  private static final int ANIMATION_STYLE_DEFAULT=-1;
  private final int[] mTmpDrawingLocation=new int[2];
  private final int[] mTmpScreenLocation=new int[2];
  private final int[] mTmpAppLocation=new int[2];
  private final Rect mTempRect=new Rect();
  private Context mContext;
  private WeakReference<View> mParentRootView;
  private boolean mIsShowing;
  private boolean mIsTransitioningToDismiss;
  private boolean mIsDropdown;
  private View mDecorView;
  private View mBackgroundView;
  private View mContentView;
  private boolean mFocusable;
  private int mInputMethodMode=INPUT_METHOD_FROM_FOCUSABLE;
  //private int mSoftInputMode=RelativeLayout.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
  private boolean mTouchable=true;
  private boolean mOutsideTouchable=false;
  private boolean mClippingEnabled=true;
  private int mSplitTouchEnabled=-1;
  private boolean mLayoutInScreen;
  private boolean mClipToScreen;
  private boolean mAllowScrollingAnchorParent=true;
  private boolean mLayoutInsetDecor=false;
  private boolean mNotTouchModal;
  private boolean mAttachedInDecor=true;
  private boolean mAttachedInDecorSet=false;
  private int mWidthMode;
  private int mWidth=LayoutParams.WRAP_CONTENT;
  private int mLastWidth;
  private int mHeightMode;
  private int mHeight=LayoutParams.WRAP_CONTENT;
  private int mLastHeight;
  private float mElevation;
  private Drawable mBackground;
  private Drawable mAboveAnchorBackgroundDrawable;
  private Drawable mBelowAnchorBackgroundDrawable;
  private Transition mEnterTransition;
  private boolean mAboveAnchor;
  //private int mWindowLayoutType=RelativeLayout.LayoutParams.TYPE_APPLICATION_PANEL;
  private OnDismissListener mOnDismissListener;
  private boolean mIgnoreCheekPress=false;
  private int mAnimationStyle=ANIMATION_STYLE_DEFAULT;
  private int mGravity=Gravity.NO_GRAVITY;
  private final OnAttachStateChangeListener mOnAnchorDetachedListener=new OnAttachStateChangeListener(){
    public void onViewAttachedToWindow(    View v){
      safeAlignToAnchor();
    }
    public void onViewDetachedFromWindow(    View v){
    }
  }
;
  private final OnAttachStateChangeListener mOnAnchorRootDetachedListener=new OnAttachStateChangeListener(){
    public void onViewAttachedToWindow(    View v){
    }
    public void onViewDetachedFromWindow(    View v){
      mIsAnchorRootAttached=false;
    }
  }
;
  private WeakReference<View> mAnchor;
  private WeakReference<View> mAnchorRoot;
  private boolean mIsAnchorRootAttached;
  private final OnScrollChangedListener mOnScrollChangedListener=this::safeAlignToAnchor;
  private final View.OnLayoutChangeListener mOnLayoutChangeListener=(v,left,top,right,bottom,oldLeft,oldTop,oldRight,oldBottom) -> safeAlignToAnchor();
  private int mAnchorXoff;
  private int mAnchorYoff;
  private int mAnchoredGravity;
  private boolean mOverlapAnchor;
  private boolean mPopupViewInitialLayoutDirectionInherited;
  public void setContentView(  View contentView){initOutParams(contentView);
    if (isShowing()) {
      return;
    }
    mContentView=contentView;
    if (mContext == null && mContentView != null) {
      mContext=mContentView.getContext();
    }
    if (false) {
      //mWindowManager=(WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
    }
    if (mContext != null && !mAttachedInDecorSet) {
      setAttachedInDecor(mContext.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.LOLLIPOP_MR1);
    }
  }
  public void setAttachedInDecor(  boolean enabled){
    mAttachedInDecor=enabled;
    mAttachedInDecorSet=true;
  }
  public void setHeight(  int height){
    mHeight=height;mOutParams.height = height;
  }
  public void setWidth(  int width){
    mWidth=width;mOutParams.width = width;
  }
  public void setOverlapAnchor(  boolean overlapAnchor){
    mOverlapAnchor=overlapAnchor;
  }
  public boolean isShowing(){
    return mIsShowing;
  }
  public void showAtLocation(  View parent,  int gravity,  int x,  int y){
    mParentRootView=new WeakReference<>(parent.getRootView());
    showAtLocation(parent.getWindowToken(),gravity,x,y);
  }
  public void showAtLocation(  IBinder token,  int gravity,  int x,  int y){
    if (isShowing() || mContentView == null) {
      return;
    }
    TransitionManager.endTransitions(mDecorView);
    detachFromAnchor();
    mIsShowing=true;
    mIsDropdown=false;
    mGravity=gravity;
    final RelativeLayout.LayoutParams p=createPopupLayoutParams(token);
    preparePopup(p);
    p.leftMargin=x;
    p.topMargin=y;
    invokePopup(p);
  }
  public void showAsDropDown(  View anchor){
    showAsDropDown(anchor,0,0);
  }
  public void showAsDropDown(  View anchor,  int xoff,  int yoff){
    showAsDropDown(anchor,xoff,yoff,DEFAULT_ANCHORED_GRAVITY);
  }
  public void showAsDropDown(  View anchor,  int xoff,  int yoff,  int gravity){
    if (isShowing() || !hasContentView()) {
      return;
    }
    TransitionManager.endTransitions(mDecorView);
    attachToAnchor(anchor,xoff,yoff,gravity);
    mIsShowing=true;
    mIsDropdown=true;
    final RelativeLayout.LayoutParams p=createPopupLayoutParams(anchor.getApplicationWindowToken());
    preparePopup(p);
    final boolean aboveAnchor=findDropDownPosition(anchor,p,xoff,yoff,p.width,p.height,gravity,mAllowScrollingAnchorParent);
    updateAboveAnchor(aboveAnchor);
    //p.accessibilityIdOfAnchor=(anchor != null) ? anchor.getAccessibilityViewId() : -1;
    invokePopup(p);
  }
  protected final void updateAboveAnchor(  boolean aboveAnchor){
    if (aboveAnchor != mAboveAnchor) {
      mAboveAnchor=aboveAnchor;
      if (mBackground != null && mBackgroundView != null) {
        if (mAboveAnchorBackgroundDrawable != null) {
          if (mAboveAnchor) {
            mBackgroundView.setBackground(mAboveAnchorBackgroundDrawable);
          }
 else {
            mBackgroundView.setBackground(mBelowAnchorBackgroundDrawable);
          }
        }
 else {
          mBackgroundView.refreshDrawableState();
        }
      }
    }
  }
  public boolean isAboveAnchor(){
    return mAboveAnchor;
  }
  private void preparePopup(  RelativeLayout.LayoutParams p){
    if (mContentView == null || mContext == null || false) {
      throw new IllegalStateException("You must specify a valid content view by " + "calling setContentView() before attempting to show the popup.");
    }
    if (false) {
      //p.accessibilityTitle=mContext.getString(R.string.popup_window_default_title);
    }
    if (mDecorView != null) {
      //mDecorView.cancelTransitions();
    }
    if (mBackground != null) {
      mBackgroundView=createBackgroundView(mContentView);
      mBackgroundView.setBackground(mBackground);
    }
 else {
      mBackgroundView=mContentView;
    }
    mDecorView=createDecorView(mBackgroundView);
    //mDecorView.setIsRootNamespace(true);
    mBackgroundView.setElevation(mElevation);
    //p.setSurfaceInsets(mBackgroundView,true,true);
    mPopupViewInitialLayoutDirectionInherited=(mContentView.getRawLayoutDirection() == View.LAYOUT_DIRECTION_INHERIT);
  }
  private void invokePopup(  RelativeLayout.LayoutParams p){
    if (mContext != null) {
      //p.packageName=mContext.getPackageName();
    }
    final View decorView=mDecorView;
    //decorView.setFitsSystemWindows(mLayoutInsetDecor);
    setLayoutDirectionFromAnchor();
    //mWindowManager.addView(decorView,p);
    if (mEnterTransition != null) {
      //decorView.requestEnterTransition(mEnterTransition);
    }
  }
  private void setLayoutDirectionFromAnchor(){
    if (mAnchor != null) {
      View anchor=mAnchor.get();
      if (anchor != null && mPopupViewInitialLayoutDirectionInherited) {
        mDecorView.setLayoutDirection(anchor.getLayoutDirection());
      }
    }
  }
  private int computeGravity(){
    int gravity=mGravity == Gravity.NO_GRAVITY ? Gravity.START | Gravity.TOP : mGravity;
    if (mIsDropdown && (mClipToScreen || mClippingEnabled)) {
      gravity|=Gravity.DISPLAY_CLIP_VERTICAL;
    }
    return gravity;
  }
  protected boolean findDropDownPosition(  View anchor,  RelativeLayout.LayoutParams outParams,  int xOffset,  int yOffset,  int width,  int height,  int gravity,  boolean allowScroll){
    final int anchorHeight=anchor.getHeight();
    final int anchorWidth=anchor.getWidth();
    if (mOverlapAnchor) {
      yOffset-=anchorHeight;
    }
    final int[] appScreenLocation=mTmpAppLocation;
    final View appRootView=getAppRootView(anchor);
    appRootView.getLocationOnScreen(appScreenLocation);
    final int[] screenLocation=mTmpScreenLocation;
    anchor.getLocationOnScreen(screenLocation);
    final int[] drawingLocation=mTmpDrawingLocation;
    drawingLocation[0]=screenLocation[0] - appScreenLocation[0];
    drawingLocation[1]=screenLocation[1] - appScreenLocation[1];
    outParams.leftMargin=drawingLocation[0] + xOffset;
    outParams.topMargin=drawingLocation[1] + anchorHeight + yOffset;
    final Rect displayFrame=new Rect();
    appRootView.getWindowVisibleDisplayFrame(displayFrame);
    if (width == MATCH_PARENT) {
      width=displayFrame.right - displayFrame.left;
    }
    if (height == MATCH_PARENT) {
      height=displayFrame.bottom - displayFrame.top;
    }
    //outParams.gravity=computeGravity();
    outParams.width=width;
    outParams.height=height;
    final int hgrav=Gravity.getAbsoluteGravity(gravity,anchor.getLayoutDirection()) & Gravity.HORIZONTAL_GRAVITY_MASK;
    if (hgrav == Gravity.RIGHT) {
      outParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
    }
    final boolean fitsVertical=tryFitVertical(outParams,yOffset,height,anchorHeight,drawingLocation[1],screenLocation[1],displayFrame.top,displayFrame.bottom,false);
    final boolean fitsHorizontal=tryFitHorizontal(outParams,xOffset,width,anchorWidth,drawingLocation[0],screenLocation[0],displayFrame.left,displayFrame.right,false);
    if (!fitsVertical || !fitsHorizontal) {
      final int scrollX=anchor.getScrollX();
      final int scrollY=anchor.getScrollY();
      final Rect r=new Rect(scrollX,scrollY,scrollX + width + xOffset,scrollY + height + anchorHeight+ yOffset);
      if (allowScroll && anchor.requestRectangleOnScreen(r,true)) {
        anchor.getLocationOnScreen(screenLocation);
        drawingLocation[0]=screenLocation[0] - appScreenLocation[0];
        drawingLocation[1]=screenLocation[1] - appScreenLocation[1];
        outParams.leftMargin=drawingLocation[0] + xOffset;
        outParams.topMargin=drawingLocation[1] + anchorHeight + yOffset;
        if (hgrav == Gravity.RIGHT) {
          outParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        }
      }
      tryFitVertical(outParams,yOffset,height,anchorHeight,drawingLocation[1],screenLocation[1],displayFrame.top,displayFrame.bottom,mClipToScreen);
      tryFitHorizontal(outParams,xOffset,width,anchorWidth,drawingLocation[0],screenLocation[0],displayFrame.left,displayFrame.right,mClipToScreen);
    }
    return outParams.topMargin < drawingLocation[1];
  }
  private boolean tryFitVertical(  LayoutParams outParams,  int yOffset,  int height,  int anchorHeight,  int drawingLocationY,  int screenLocationY,  int displayFrameTop,  int displayFrameBottom,  boolean allowResize){
    final int winOffsetY=screenLocationY - drawingLocationY;
    final int anchorTopInScreen=outParams.topMargin + winOffsetY;
    final int spaceBelow=displayFrameBottom - anchorTopInScreen;if (height < 0) {height = 0;} 
    if (anchorTopInScreen >= displayFrameTop && height <= spaceBelow) {
      return true;
    }
    final int spaceAbove=anchorTopInScreen - anchorHeight - displayFrameTop;
    if (height <= spaceAbove) {
      if (mOverlapAnchor) {
        yOffset+=anchorHeight;
      }
      outParams.topMargin=drawingLocationY - height + yOffset;
      return true;
    }
    if (positionInDisplayVertical(outParams,height,drawingLocationY,screenLocationY,displayFrameTop,displayFrameBottom,allowResize)) {
      return true;
    }
    return false;
  }
  private boolean positionInDisplayVertical(  LayoutParams outParams,  int height,  int drawingLocationY,  int screenLocationY,  int displayFrameTop,  int displayFrameBottom,  boolean canResize){
    boolean fitsInDisplay=true;
    final int winOffsetY=screenLocationY - drawingLocationY;
    outParams.topMargin+=winOffsetY;
    outParams.height=height;
    final int bottom=outParams.topMargin + height;
    if (bottom > displayFrameBottom) {
      outParams.topMargin-=bottom - displayFrameBottom;
    }
    if (outParams.topMargin < displayFrameTop) {
      outParams.topMargin=displayFrameTop;
      final int displayFrameHeight=displayFrameBottom - displayFrameTop;
      if (canResize && height > displayFrameHeight) {
        outParams.height=displayFrameHeight;
      }
 else {
        fitsInDisplay=false;
      }
    }
    outParams.topMargin-=winOffsetY;
    return fitsInDisplay;
  }
  private boolean tryFitHorizontal(  LayoutParams outParams,  int xOffset,  int width,  int anchorWidth,  int drawingLocationX,  int screenLocationX,  int displayFrameLeft,  int displayFrameRight,  boolean allowResize){
    final int winOffsetX=screenLocationX - drawingLocationX;
    final int anchorLeftInScreen=outParams.leftMargin + winOffsetX;
    final int spaceRight=displayFrameRight - anchorLeftInScreen;
    if (anchorLeftInScreen >= displayFrameLeft && width <= spaceRight) {
      return true;
    }
    if (positionInDisplayHorizontal(outParams,width,drawingLocationX,screenLocationX,displayFrameLeft,displayFrameRight,allowResize)) {
      return true;
    }
    return false;
  }
  private boolean positionInDisplayHorizontal(  LayoutParams outParams,  int width,  int drawingLocationX,  int screenLocationX,  int displayFrameLeft,  int displayFrameRight,  boolean canResize){
    boolean fitsInDisplay=true;
    final int winOffsetX=screenLocationX - drawingLocationX;
    outParams.leftMargin+=winOffsetX;
    final int right=outParams.leftMargin + width;
    if (right > displayFrameRight) {
      outParams.leftMargin-=right - displayFrameRight;
    }
    if (outParams.leftMargin < displayFrameLeft) {
      outParams.leftMargin=displayFrameLeft;
      final int displayFrameWidth=displayFrameRight - displayFrameLeft;
      if (canResize && width > displayFrameWidth) {
        outParams.width=displayFrameWidth;
      }
 else {
        fitsInDisplay=false;
      }
    }
    outParams.leftMargin-=winOffsetX;
    return fitsInDisplay;
  }
  public int getMaxAvailableHeight(  View anchor){
    return getMaxAvailableHeight(anchor,0);
  }
  public int getMaxAvailableHeight(  View anchor,  int yOffset){
    return getMaxAvailableHeight(anchor,yOffset,false);
  }
  public int getMaxAvailableHeight(  View anchor,  int yOffset,  boolean ignoreBottomDecorations){
    Rect displayFrame=null;
    final Rect visibleDisplayFrame=new Rect();
    final View appView=getAppRootView(anchor);
    appView.getWindowVisibleDisplayFrame(visibleDisplayFrame);
    if (ignoreBottomDecorations) {
      displayFrame=new Rect();
      anchor.getWindowDisplayFrame(displayFrame);
      displayFrame.top=visibleDisplayFrame.top;
      displayFrame.right=visibleDisplayFrame.right;
      displayFrame.left=visibleDisplayFrame.left;
    }
 else {
      displayFrame=visibleDisplayFrame;
    }
    final int[] anchorPos=mTmpDrawingLocation;
    anchor.getLocationOnScreen(anchorPos);
    final int bottomEdge=displayFrame.bottom;
    final int distanceToBottom;
    if (mOverlapAnchor) {
      distanceToBottom=bottomEdge - anchorPos[1] - yOffset;
    }
 else {
      distanceToBottom=bottomEdge - (anchorPos[1] + anchor.getHeight()) - yOffset;
    }
    final int distanceToTop=anchorPos[1] - displayFrame.top + yOffset;
    int returnedHeight=Math.max(distanceToBottom,distanceToTop);
    if (mBackground != null) {
      mBackground.getPadding(mTempRect);
      returnedHeight-=mTempRect.top + mTempRect.bottom;
    }
    return returnedHeight;
  }
  public void dismiss(){
    if (!isShowing() || isTransitioningToDismiss()) {
      return;
    }
    final View decorView=mDecorView;
    final View contentView=mContentView;
    //unregisterBackCallback(decorView.findOnBackInvokedDispatcher());
    final ViewGroup contentHolder;
    final ViewParent contentParent=contentView.getParent();
    if (contentParent instanceof ViewGroup) {
      contentHolder=((ViewGroup)contentParent);
    }
 else {
      contentHolder=null;
    }
    //decorView.cancelTransitions();
    mIsShowing=false;
    mIsTransitioningToDismiss=true;
    final Transition exitTransition=null;
    if (exitTransition != null && decorView.isLaidOut() && (mIsAnchorRootAttached || mAnchorRoot == null)) {
      final LayoutParams p=(LayoutParams)decorView.getLayoutParams();
      //p.flags|=LayoutParams.FLAG_NOT_TOUCHABLE;
      //p.flags|=LayoutParams.FLAG_NOT_FOCUSABLE;
      //p.flags&=~LayoutParams.FLAG_ALT_FOCUSABLE_IM;
      this.mContentView.requestLayout();this.mContentView.getRootView().remeasure();
      final View anchorRoot=mAnchorRoot != null ? mAnchorRoot.get() : null;
      //final Rect epicenter=getTransitionEpicenter();
      //
    }
 else {
      dismissImmediate(decorView,contentHolder,contentView);
    }
    detachFromAnchor();
    if (mOnDismissListener != null) {
      mOnDismissListener.onDismiss();
    }
  }
  private void dismissImmediate(  View decorView,  ViewGroup contentHolder,  View contentView){
    if (decorView.getParent() != null) {
      decorView.removeFromParent();
    }
    if (contentHolder != null) {
      contentHolder.removeView(contentView);
    }
    mDecorView=null;
    mBackgroundView=null;
    mIsTransitioningToDismiss=false;
  }
  public void setOnDismissListener(  OnDismissListener onDismissListener){
    mOnDismissListener=onDismissListener;
  }
  public void update(){
    if (!isShowing() || !hasContentView()) {
      return;
    }
    final RelativeLayout.LayoutParams p=getDecorViewLayoutParams();
    boolean update=false;
    final int newAnim=computeAnimationResource();
    if (false) {
      //p.windowAnimations=newAnim;
      update=true;
    }
    //final int newFlags=computeFlags(p.flags);
    if (false) {
      //p.flags=newFlags;
      update=true;
    }
    final int newGravity=computeGravity();
    if (false) {
      //p.gravity=newGravity;
      update=true;
    }
    if (update) {
      update(mAnchor != null ? mAnchor.get() : null,p);
    }
  }
  protected void update(  View anchor,  RelativeLayout.LayoutParams params){
    setLayoutDirectionFromAnchor();
    this.remeasure();
  }
  public void update(  int width,  int height){
    final RelativeLayout.LayoutParams p=getDecorViewLayoutParams();
    update(p.leftMargin,p.topMargin,width,height,false);
  }
  public void update(  int x,  int y,  int width,  int height){
    update(x,y,width,height,false);
  }
  public void update(  int x,  int y,  int width,  int height,  boolean force){
    if (width >= 0) {
      mLastWidth=width;
      setWidth(width);
    }
    if (height >= 0) {
      mLastHeight=height;
      setHeight(height);
    }
    if (!isShowing() || !hasContentView()) {
      return;
    }
    final RelativeLayout.LayoutParams p=getDecorViewLayoutParams();
    boolean update=force;
    final int finalWidth=mWidthMode < 0 ? mWidthMode : mLastWidth;
    if (width != -1 && p.width != finalWidth) {
      p.width=mLastWidth=finalWidth;
      update=true;
    }
    final int finalHeight=mHeightMode < 0 ? mHeightMode : mLastHeight;
    if (height != -1 && p.height != finalHeight) {
      p.height=mLastHeight=finalHeight;
      update=true;
    }
    if (p.leftMargin != x) {
      p.leftMargin=x;
      update=true;
    }
    if (p.topMargin != y) {
      p.topMargin=y;
      update=true;
    }
    final int newAnim=computeAnimationResource();
    if (false) {
      //p.windowAnimations=newAnim;
      update=true;
    }
    //final int newFlags=computeFlags(p.flags);
    if (false) {
      //p.flags=newFlags;
      update=true;
    }
    final int newGravity=computeGravity();
    if (false) {
      //p.gravity=newGravity;
      update=true;
    }
    View anchor=null;
    int newAccessibilityIdOfAnchor=-1;
    if (mAnchor != null && mAnchor.get() != null) {
      anchor=mAnchor.get();
      newAccessibilityIdOfAnchor=anchor.getAccessibilityViewId();
    }
    if (false) {
      //p.accessibilityIdOfAnchor=newAccessibilityIdOfAnchor;
      update=true;
    }
    if (update) {
      update(anchor,p);
    }
  }
  protected boolean hasContentView(){
    return mContentView != null;
  }
  protected boolean hasDecorView(){
    return mDecorView != null;
  }
  protected RelativeLayout.LayoutParams getDecorViewLayoutParams(){
    return (RelativeLayout.LayoutParams)mDecorView.getLayoutParams();
  }
  public void update(  View anchor,  int width,  int height){
    update(anchor,false,0,0,width,height);
  }
  public void update(  View anchor,  int xoff,  int yoff,  int width,  int height){
    update(anchor,true,xoff,yoff,width,height);
  }
  private void update(  View anchor,  boolean updateLocation,  int xoff,  int yoff,  int width,  int height){
    if (!isShowing() || !hasContentView()) {
      return;
    }
    final WeakReference<View> oldAnchor=mAnchor;
    final int gravity=mAnchoredGravity;
    final boolean needsUpdate=updateLocation && (mAnchorXoff != xoff || mAnchorYoff != yoff);
    if (oldAnchor == null || oldAnchor.get() != anchor || (needsUpdate && !mIsDropdown)) {
      attachToAnchor(anchor,xoff,yoff,gravity);
    }
 else     if (needsUpdate) {
      mAnchorXoff=xoff;
      mAnchorYoff=yoff;
    }
    final RelativeLayout.LayoutParams p=getDecorViewLayoutParams();
    //final int oldGravity=p.gravity;
    final int oldWidth=p.width;
    final int oldHeight=p.height;
    final int oldX=p.leftMargin;
    final int oldY=p.topMargin;
    if (width < 0) {
      width=mWidth;
    }
    if (height < 0) {
      height=mHeight;
    }
    final boolean aboveAnchor=findDropDownPosition(anchor,p,mAnchorXoff,mAnchorYoff,width,height,gravity,mAllowScrollingAnchorParent);
    updateAboveAnchor(aboveAnchor);
    final boolean paramsChanged=true || oldX != p.leftMargin || oldY != p.topMargin || oldWidth != p.width || oldHeight != p.height;
    final int newWidth=width < 0 ? width : p.width;
    final int newHeight=height < 0 ? height : p.height;
    update(p.leftMargin,p.topMargin,newWidth,newHeight,paramsChanged);
  }
public interface OnDismissListener {
    public void onDismiss();
  }
  protected void detachFromAnchor(){
    final View anchor=getAnchor();
    if (anchor != null) {
      final ViewTreeObserver vto=anchor.getViewTreeObserver();
      vto.removeOnScrollChangedListener(mOnScrollChangedListener);
      anchor.removeOnAttachStateChangeListener(mOnAnchorDetachedListener);
    }
    final View anchorRoot=mAnchorRoot != null ? mAnchorRoot.get() : null;
    if (anchorRoot != null) {
      anchorRoot.removeOnAttachStateChangeListener(mOnAnchorRootDetachedListener);
      anchorRoot.removeOnLayoutChangeListener(mOnLayoutChangeListener);
    }
    mAnchor=null;
    mAnchorRoot=null;
    mIsAnchorRootAttached=false;
  }
  protected void attachToAnchor(  View anchor,  int xoff,  int yoff,  int gravity){
    detachFromAnchor();
    final ViewTreeObserver vto=anchor.getViewTreeObserver();
    if (vto != null) {
      vto.addOnScrollChangedListener(mOnScrollChangedListener);
    }
    anchor.addOnAttachStateChangeListener(mOnAnchorDetachedListener);
    final View anchorRoot=anchor.getRootView();
    anchorRoot.addOnAttachStateChangeListener(mOnAnchorRootDetachedListener);
    anchorRoot.addOnLayoutChangeListener(mOnLayoutChangeListener);
    mAnchor=new WeakReference<>(anchor);
    mAnchorRoot=new WeakReference<>(anchorRoot);
    mIsAnchorRootAttached=anchorRoot.isAttachedToWindow();
    mParentRootView=mAnchorRoot;
    mAnchorXoff=xoff;
    mAnchorYoff=yoff;
    mAnchoredGravity=gravity;
  }
  protected View getAnchor(){
    return mAnchor != null ? mAnchor.get() : null;
  }
  private void alignToAnchor(){
    final View anchor=mAnchor != null ? mAnchor.get() : null;
    if (anchor != null && anchor.isAttachedToWindow() && hasDecorView()) {
      final RelativeLayout.LayoutParams p=getDecorViewLayoutParams();
      updateAboveAnchor(findDropDownPosition(anchor,p,mAnchorXoff,mAnchorYoff,p.width,p.height,mAnchoredGravity,false));
      update(p.leftMargin,p.topMargin,-1,-1,true);
    }
  }
static class TransitionManager {
    public static void endTransitions(    View mDecorView){
    }
  }
  void initOutParams(  View contentView){
    if (contentView != null) {
      ViewParent parent=contentView.getParent();
      while (!(((ViewGroup)parent).getLayoutParams() instanceof LayoutParams)) {
        parent=parent.getParent();
      }
      mOutParams=(LayoutParams)((ViewGroup)parent).getLayoutParams();
    }
  }
  public void remeasure(){
    if (this.mContentView != null) {
      this.mContentView.getRootView().forceLayout();
      this.mContentView.requestLayout();
      this.mContentView.getRootView().remeasure();
    }
  }
  private RelativeLayout.LayoutParams mOutParams;
  public void showAsDropDown(  View decorView,  View anchor,  RelativeLayout.LayoutParams outParams,  int xOffset,  int yOffset,  int gravity){
    mDecorView=decorView;
    mOutParams=outParams;
    showAsDropDown(anchor,xOffset,yOffset,gravity);
  }
  public void showAtLocation(  View decorView,  RelativeLayout.LayoutParams outParams,  int x,  int y,  int gravity){
    mDecorView=decorView;
    mOutParams=outParams;
    showAtLocation(decorView,x,y,gravity);
  }
  protected final RelativeLayout.LayoutParams createPopupLayoutParams(  IBinder token){
    return mOutParams;
  }
  private View createDecorView(  View mBackgroundView){
    return mDecorView;
  }
  private View createBackgroundView(  View mContentView){
    return mBackgroundView;
  }
  private boolean isTransitioningToDismiss(){
    return false;
  }
  private boolean aligningAnchor;
  private void safeAlignToAnchor(){
    if (!aligningAnchor) {
      aligningAnchor=true;
      alignToAnchor();
      aligningAnchor=false;
    }
  }
  public PopupWindow(){
  }
  private View getAppRootView(  View anchor){
    return anchor.getRootView();
  }
  public int computeFlags(  int curFlags){
    return curFlags;
  }
  private int computeAnimationResource(){
    return 0;
  }
class Transition {
  }
}
