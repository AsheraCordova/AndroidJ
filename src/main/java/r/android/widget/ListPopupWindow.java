package r.android.widget;
import r.android.content.Context;
import r.android.graphics.Rect;
import r.android.graphics.drawable.Drawable;
import r.android.os.Build;
import r.android.util.Log;
import r.android.view.Gravity;
import r.android.view.KeyEvent;
import r.android.view.View;
import r.android.view.View.MeasureSpec;
import r.android.view.ViewGroup;
import r.android.view.ViewParent;
public class ListPopupWindow {
  private static final String TAG="ListPopupWindow";
  private static final boolean DEBUG=false;
  private static final int EXPAND_LIST_TIMEOUT=250;
  private Context mContext;
  private ListAdapter mAdapter;
  private int mDropDownHeight=ViewGroup.LayoutParams.WRAP_CONTENT;
  private int mDropDownWidth=ViewGroup.LayoutParams.WRAP_CONTENT;
  private int mDropDownHorizontalOffset;
  private int mDropDownVerticalOffset;
  //private int mDropDownWindowLayoutType=WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;
  private boolean mDropDownVerticalOffsetSet;
  private boolean mIsAnimatedFromAnchor=true;
  private boolean mOverlapAnchor;
  private boolean mOverlapAnchorSet;
  private int mDropDownGravity=Gravity.NO_GRAVITY;
  private boolean mDropDownAlwaysVisible=false;
  private boolean mForceIgnoreOutsideTouch=false;
  int mListItemExpandMaximum=Integer.MAX_VALUE;
  private View mPromptView;
  private int mPromptPosition=POSITION_PROMPT_ABOVE;
  private View mDropDownAnchorView;
  private Runnable mShowDropDownRunnable;
  private final Rect mTempRect=new Rect();
  private boolean mModal;
  PopupWindow mPopup;
  public static final int POSITION_PROMPT_ABOVE=0;
  public static final int POSITION_PROMPT_BELOW=1;
  public static final int MATCH_PARENT=ViewGroup.LayoutParams.MATCH_PARENT;
  public static final int WRAP_CONTENT=ViewGroup.LayoutParams.WRAP_CONTENT;
  public static final int INPUT_METHOD_FROM_FOCUSABLE=PopupWindow.INPUT_METHOD_FROM_FOCUSABLE;
  public static final int INPUT_METHOD_NEEDED=PopupWindow.INPUT_METHOD_NEEDED;
  public static final int INPUT_METHOD_NOT_NEEDED=PopupWindow.INPUT_METHOD_NOT_NEEDED;
  public boolean isDropDownAlwaysVisible(){
    return mDropDownAlwaysVisible;
  }
  public View getAnchorView(){
    return mDropDownAnchorView;
  }
  public void setAnchorView(  View anchor){
    mDropDownAnchorView=anchor;
  }
  public int getHorizontalOffset(){
    return mDropDownHorizontalOffset;
  }
  public void setHorizontalOffset(  int offset){
    mDropDownHorizontalOffset=offset;
  }
  public int getVerticalOffset(){
    if (!mDropDownVerticalOffsetSet) {
      return 0;
    }
    return mDropDownVerticalOffset;
  }
  public void setVerticalOffset(  int offset){
    mDropDownVerticalOffset=offset;
    mDropDownVerticalOffsetSet=true;
  }
  public int getWidth(){
    return mDropDownWidth;
  }
  public void setWidth(  int width){
    mDropDownWidth=width;
  }
  public int getHeight(){
    return mDropDownHeight;
  }
  public void setHeight(  int height){
    if (height < 0 && ViewGroup.LayoutParams.WRAP_CONTENT != height && ViewGroup.LayoutParams.MATCH_PARENT != height) {
      if (mContext.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.O) {
        Log.e(TAG,"Negative value " + height + " passed to ListPopupWindow#setHeight"+ " produces undefined results");
      }
 else {
        throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
      }
    }
    mDropDownHeight=height;
  }
  public void show(){
    int height=buildDropDown();
    final boolean noInputMethod=isInputMethodNotNeeded();
    //mPopup.setAllowScrollingAnchorParent(!noInputMethod);
    //mPopup.setWindowLayoutType(mDropDownWindowLayoutType);
    if (mPopup.isShowing()) {
      if (!getAnchorView().isAttachedToWindow()) {
        return;
      }
      final int widthSpec;
      if (mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
        widthSpec=-1;
      }
 else       if (mDropDownWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
        widthSpec=getAnchorView().getWidth();
      }
 else {
        widthSpec=mDropDownWidth;
      }
      final int heightSpec;
      if (mDropDownHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
        heightSpec=noInputMethod ? height : ViewGroup.LayoutParams.MATCH_PARENT;
        if (noInputMethod) {
          mPopup.setWidth(mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT ? ViewGroup.LayoutParams.MATCH_PARENT : 0);
          mPopup.setHeight(0);
        }
 else {
          mPopup.setWidth(mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT ? ViewGroup.LayoutParams.MATCH_PARENT : 0);
          mPopup.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        }
      }
 else       if (mDropDownHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
        heightSpec=height;
      }
 else {
        heightSpec=mDropDownHeight;
      }
      //mPopup.setOutsideTouchable(!mForceIgnoreOutsideTouch && !mDropDownAlwaysVisible);
      mPopup.update(getAnchorView(),mDropDownHorizontalOffset,mDropDownVerticalOffset,(widthSpec < 0) ? -1 : widthSpec,(heightSpec < 0) ? -1 : heightSpec);
      //mPopup.getContentView().restoreDefaultFocus();
    }
 else {
      final int widthSpec;
      if (mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
        widthSpec=ViewGroup.LayoutParams.MATCH_PARENT;
      }
 else {
        if (mDropDownWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
          widthSpec=getAnchorView().getWidth();
        }
 else {
          widthSpec=mDropDownWidth;
        }
      }
      final int heightSpec;
      if (mDropDownHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
        heightSpec=ViewGroup.LayoutParams.MATCH_PARENT;
      }
 else {
        if (mDropDownHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
          heightSpec=height;
        }
 else {
          heightSpec=mDropDownHeight;
        }
      }
      mPopup.setWidth(widthSpec);
      mPopup.setHeight(heightSpec);
      //mPopup.setIsClippedToScreen(true);
      //mPopup.setOutsideTouchable(!mForceIgnoreOutsideTouch && !mDropDownAlwaysVisible);
      //mPopup.setTouchInterceptor(mTouchInterceptor);
      //mPopup.setEpicenterBounds(mEpicenterBounds);
      if (mOverlapAnchorSet) {
        mPopup.setOverlapAnchor(mOverlapAnchor);
      }
      mPopup.showAsDropDown(decorView, getAnchorView(), params,mDropDownHorizontalOffset,mDropDownVerticalOffset,mDropDownGravity);
      //mDropDownList.setSelection(ListView.INVALID_POSITION);
      //mPopup.getContentView().restoreDefaultFocus();
      if (!mModal || false) {
        clearListSelection();
      }
      if (!mModal) {
        //mHandler.post(mHideSelector);
      }
    }
  }
  public void dismiss(){
    mPopup.dismiss();
    removePromptView();
    mPopup.setContentView(null);
    mDropDownList=null;
    //mHandler.removeCallbacks(mResizePopupRunnable);
  }
  public void setOnDismissListener(  PopupWindow.OnDismissListener listener){
    mPopup.setOnDismissListener(listener);
  }
  private void removePromptView(){
    if (mPromptView != null) {
      final ViewParent parent=mPromptView.getParent();
      if (parent instanceof ViewGroup) {
        final ViewGroup group=(ViewGroup)parent;
        group.removeView(mPromptView);
      }
    }
  }
  public void clearListSelection(){
    final ViewGroup list=mDropDownList;
    if (list != null) {
      //list.setListSelectionHidden(true);
      //list.hideSelector();
      list.requestLayout();
    }
  }
  public boolean isShowing(){
    return mPopup.isShowing();
  }
  public ViewGroup getListView(){
    return mDropDownList;
  }
  public boolean onKeyDown(  int keyCode,  KeyEvent event){
    if (isShowing()) {
      if (keyCode != KeyEvent.KEYCODE_SPACE && (true || !KeyEvent.isConfirmKey(keyCode))) {
        int curIndex=0;
        boolean consumed;
        final boolean below=!mPopup.isAboveAnchor();
        final ListAdapter adapter=mAdapter;
        boolean allEnabled;
        int firstItem=Integer.MAX_VALUE;
        int lastItem=Integer.MIN_VALUE;
        if (adapter != null) {
          allEnabled=adapter.areAllItemsEnabled();
          firstItem=allEnabled ? 0 : 0;
          lastItem=allEnabled ? adapter.getCount() - 1 : adapter.getCount() - 1;
        }
        if ((below && keyCode == KeyEvent.KEYCODE_DPAD_UP && curIndex <= firstItem) || (!below && keyCode == KeyEvent.KEYCODE_DPAD_DOWN && curIndex >= lastItem)) {
          clearListSelection();
          //mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
          show();
          return true;
        }
 else {
          //mDropDownList.setListSelectionHidden(false);
        }
        consumed=false;
        if (DEBUG)         Log.v(TAG,"Key down: code=" + keyCode + " list consumed="+ consumed);
        if (consumed) {
          //mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
          mDropDownList.requestFocusFromTouch();
          show();
switch (keyCode) {
case KeyEvent.KEYCODE_ENTER:
case KeyEvent.KEYCODE_DPAD_CENTER:
case KeyEvent.KEYCODE_DPAD_DOWN:
case KeyEvent.KEYCODE_DPAD_UP:
case KeyEvent.KEYCODE_NUMPAD_ENTER:
            return true;
        }
      }
 else {
        if (below && keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
          if (curIndex == lastItem) {
            return true;
          }
        }
 else         if (!below && keyCode == KeyEvent.KEYCODE_DPAD_UP && curIndex == firstItem) {
          return true;
        }
      }
    }
  }
  return false;
}
private int buildDropDown(){
  ViewGroup dropDownView;
  int otherHeights=0;
  if (mDropDownList == null) {
    Context context=mContext;
    mShowDropDownRunnable=new Runnable(){
      public void run(){
        View view=getAnchorView();
        if (view != null && view.getWindowToken() != null) {
          show();
        }
      }
    }
;
    mDropDownList=createDropDownListView(context,!mModal);
    if (false) {
      //mDropDownList.setSelector(mDropDownListHighlight);
    }
    //mDropDownList.setAdapter(mAdapter);
    //mDropDownList.setOnItemClickListener(mItemClickListener);
    mDropDownList.setFocusable(true);
    //mDropDownList.setFocusableInTouchMode(true);
    //
    //mDropDownList.setOnScrollListener(mScrollListener);
    if (false) {
      //mDropDownList.setOnItemSelectedListener(mItemSelectedListener);
    }
    dropDownView=mDropDownList;
    View hintView=mPromptView;
    if (hintView != null) {
      LinearLayout hintContainer=new LinearLayout(context);
      hintContainer.setOrientation(LinearLayout.VERTICAL);
      LinearLayout.LayoutParams hintParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1.0f);
switch (mPromptPosition) {
case POSITION_PROMPT_BELOW:
        hintContainer.addView(dropDownView,hintParams);
      hintContainer.addView(hintView);
    break;
case POSITION_PROMPT_ABOVE:
  hintContainer.addView(hintView);
hintContainer.addView(dropDownView,hintParams);
break;
default :
Log.e(TAG,"Invalid hint position " + mPromptPosition);
break;
}
final int widthSize;
final int widthMode;
if (mDropDownWidth >= 0) {
widthMode=MeasureSpec.AT_MOST;
widthSize=mDropDownWidth;
}
 else {
widthMode=MeasureSpec.UNSPECIFIED;
widthSize=0;
}
final int widthSpec=MeasureSpec.makeMeasureSpec(widthSize,widthMode);
final int heightSpec=MeasureSpec.UNSPECIFIED;
hintView.measure(widthSpec,heightSpec);
hintParams=(LinearLayout.LayoutParams)hintView.getLayoutParams();
otherHeights=hintView.getMeasuredHeight() + hintParams.topMargin + hintParams.bottomMargin;
dropDownView=hintContainer;
}
mPopup.setContentView(dropDownView);
}
 else {
final View view=mPromptView;
if (view != null) {measureHintView(view);
LinearLayout.LayoutParams hintParams=(LinearLayout.LayoutParams)view.getLayoutParams();
otherHeights=view.getMeasuredHeight() + hintParams.topMargin + hintParams.bottomMargin;
}
}
final int padding;
final Drawable background=null;
if (background != null) {
background.getPadding(mTempRect);
padding=mTempRect.top + mTempRect.bottom;
if (!mDropDownVerticalOffsetSet) {
mDropDownVerticalOffset=-mTempRect.top;
}
}
 else {
mTempRect.setEmpty();
padding=0;
}
final boolean ignoreBottomDecorations=false;
final int maxHeight=mPopup.getMaxAvailableHeight(getAnchorView(),mDropDownVerticalOffset,ignoreBottomDecorations);
if (mDropDownAlwaysVisible || mDropDownHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
return maxHeight + padding;
}
final int childWidthSpec;
switch (mDropDownWidth) {
case ViewGroup.LayoutParams.WRAP_CONTENT:
childWidthSpec=MeasureSpec.makeMeasureSpec(com.ashera.widget.PluginInvoker.getScreenWidth() - (mTempRect.left + mTempRect.right),MeasureSpec.AT_MOST);
break;
case ViewGroup.LayoutParams.MATCH_PARENT:
childWidthSpec=MeasureSpec.makeMeasureSpec(com.ashera.widget.PluginInvoker.getScreenWidth() - (mTempRect.left + mTempRect.right),MeasureSpec.EXACTLY);
break;
default :
childWidthSpec=MeasureSpec.makeMeasureSpec(mDropDownWidth,MeasureSpec.EXACTLY);
break;
}
final int listContent=mDropDownList.measureHeightOfChildren(childWidthSpec,0,-1,maxHeight - otherHeights,-1);
if (listContent > 0) {
final int listPadding=mDropDownList.getPaddingTop() + mDropDownList.getPaddingBottom();
otherHeights+=padding + listPadding;
}
return listContent + otherHeights;
}
private ViewGroup mDropDownList;
private boolean isInputMethodNotNeeded(){
return false;
}
private ViewGroup createDropDownListView(Context context,boolean b){
return getListView();
}
private View decorView;
private RelativeLayout.LayoutParams params;
public ListPopupWindow(View decorView,View contentView,RelativeLayout.LayoutParams params){
mPopup=new PopupWindow();
this.decorView=decorView;
this.params=params;
mDropDownList=(ViewGroup)contentView;
mPopup.setContentView(contentView);
}
public void setPromptView(TextView promptView){
mPromptView=promptView;
}
public void measureHintView(final View view){
final int widthSize;
final int widthMode;
if (mDropDownWidth >= 0) {
widthMode=MeasureSpec.AT_MOST;
widthSize=mDropDownWidth;
}
 else {
widthMode=MeasureSpec.UNSPECIFIED;
widthSize=0;
}
final int widthSpec=MeasureSpec.makeMeasureSpec(widthSize,widthMode);
final int heightSpec=MeasureSpec.UNSPECIFIED;
view.measure(widthSpec,heightSpec);
}
}
