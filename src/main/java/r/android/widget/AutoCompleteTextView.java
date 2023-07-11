package r.android.widget;
import r.android.database.DataSetObserver;
import r.android.text.Editable;
import r.android.util.Log;
import r.android.view.KeyEvent;
import r.android.view.View;
import java.lang.ref.WeakReference;
public abstract class AutoCompleteTextView extends EditText implements r.android.widget.Filter.FilterListener {
  static final boolean DEBUG=false;
  static final String TAG="AutoCompleteTextView";
  static final int EXPAND_MAX=3;
  private ListPopupWindow mPopup;
  private int mHintResource;
  private ListAdapter mAdapter;
  private Filter mFilter;
  private int mThreshold;
  private int mDropDownAnchorId;
  private boolean mDropDownDismissedOnCompletion=true;
  private int mLastKeyCode=KeyEvent.KEYCODE_UNKNOWN;
  private MyWatcher mAutoCompleteTextWatcher;
  private boolean mBlockCompletion;
  private boolean mPopupCanBeUpdated=true;
  private PopupDataSetObserver mObserver;
  private boolean mBackCallbackRegistered;
  public int getDropDownWidth(){
    return mPopup.getWidth();
  }
  public void setDropDownWidth(  int width){
    mPopup.setWidth(width);
  }
  public int getDropDownHeight(){
    return mPopup.getHeight();
  }
  public void setDropDownHeight(  int height){
    mPopup.setHeight(height);
  }
  public void setDropDownVerticalOffset(  int offset){
    mPopup.setVerticalOffset(offset);
  }
  public int getDropDownVerticalOffset(){
    return mPopup.getVerticalOffset();
  }
  public void setDropDownHorizontalOffset(  int offset){
    mPopup.setHorizontalOffset(offset);
  }
  public int getDropDownHorizontalOffset(){
    return mPopup.getHorizontalOffset();
  }
  public int getThreshold(){
    return mThreshold;
  }
  public void setThreshold(  int threshold){
    if (threshold <= 0) {
      threshold=1;
    }
    mThreshold=threshold;
  }
  public void setOnDismissListener(  final OnDismissListener dismissListener){
    PopupWindow.OnDismissListener wrappedListener=null;
    if (dismissListener != null) {
      wrappedListener=new PopupWindow.OnDismissListener(){
        public void onDismiss(){
          dismissListener.onDismiss();
          unregisterOnBackInvokedCallback();
        }
      }
;
    }
    mPopup.setOnDismissListener(wrappedListener);
  }
  public <T extends ListAdapter & Filterable>void setAdapter(  T adapter){
    if (mObserver == null) {
      mObserver=new PopupDataSetObserver(this);
    }
 else     if (mAdapter != null) {
      mAdapter.unregisterDataSetObserver(mObserver);
    }
    mAdapter=adapter;
    if (mAdapter != null) {
      mFilter=((Filterable)mAdapter).getFilter();
      adapter.registerDataSetObserver(mObserver);
    }
 else {
      mFilter=null;
    }
    //mPopup.setAdapter(mAdapter);
  }
  public boolean onKeyDown(  int keyCode,  KeyEvent event){
    if (mPopup.onKeyDown(keyCode,event)) {
      return true;
    }
    if (!isPopupShowing()) {
switch (keyCode) {
case KeyEvent.KEYCODE_DPAD_DOWN:
        if (event.hasNoModifiers()) {
          performValidation();
        }
    }
  }
  if (isPopupShowing() && keyCode == KeyEvent.KEYCODE_TAB && event.hasNoModifiers()) {
    return true;
  }
  mLastKeyCode=keyCode;
  boolean handled=super.onKeyDown(keyCode,event);
  mLastKeyCode=KeyEvent.KEYCODE_UNKNOWN;
  if (handled && isPopupShowing()) {
    clearListSelection();
  }
  return handled;
}
public boolean enoughToFilter(){
  if (DEBUG)   Log.v(TAG,"Enough to filter: len=" + getText().length() + " threshold="+ mThreshold);
  return getText().length() >= mThreshold;
}
private class MyWatcher implements r.android.text.TextWatcher {
  private boolean mOpenBefore;
  public void beforeTextChanged(  CharSequence s,  int start,  int count,  int after){
    if (mBlockCompletion)     return;
    mOpenBefore=isPopupShowing();
    if (DEBUG)     Log.v(TAG,"before text changed: open=" + mOpenBefore);
  }
  public void afterTextChanged(  Editable s){
    if (mBlockCompletion)     return;
    if (DEBUG) {
      Log.v(TAG,"after text changed: openBefore=" + mOpenBefore + " open="+ isPopupShowing());
    }
    if (mOpenBefore && !isPopupShowing())     return;
    refreshAutoCompleteResults();
  }
  public void onTextChanged(  CharSequence s,  int start,  int before,  int count){
  }
}
public final void refreshAutoCompleteResults(){
  if (enoughToFilter()) {
    if (mFilter != null) {
      mPopupCanBeUpdated=true;
      performFiltering(getText(),mLastKeyCode);
    }
  }
 else {
    if (!mPopup.isDropDownAlwaysVisible()) {
      dismissDropDown();
    }
    if (mFilter != null) {
      mFilter.filter(null);
    }
  }
}
public boolean isPopupShowing(){
  return mPopup.isShowing();
}
public void clearListSelection(){
  mPopup.clearListSelection();
}
protected void performFiltering(CharSequence text,int keyCode){
  mFilter.filter(text,this);
}
public void onFilterComplete(int count){
  updateDropDownForFilter(count);
}
private void updateDropDownForFilter(int count){
  if (getWindowVisibility() == View.GONE)   return;
  final boolean dropDownAlwaysVisible=mPopup.isDropDownAlwaysVisible();
  final boolean enoughToFilter=enoughToFilter();
  if ((count > 0 || dropDownAlwaysVisible) && enoughToFilter) {
    if (true && mPopupCanBeUpdated) {
      showDropDown();
    }
  }
 else   if (!dropDownAlwaysVisible && isPopupShowing()) {
    dismissDropDown();
    mPopupCanBeUpdated=true;
  }
}
public void dismissDropDown(){
  //InputMethodManager imm=getContext().getSystemService(InputMethodManager.class);
  if (false) {
    //imm.displayCompletions(this,null);
  }
  mPopup.dismiss();
  mPopupCanBeUpdated=false;
}
public void showDropDown(){
  //buildImeCompletions();
  if (mPopup.getAnchorView() == null) {
    if (mDropDownAnchorId != View.NO_ID) {
      mPopup.setAnchorView(getRootView().findViewById(mDropDownAnchorId));
    }
 else {
      mPopup.setAnchorView(this);
    }
  }
  if (!isPopupShowing()) {
    //mPopup.setInputMethodMode(ListPopupWindow.INPUT_METHOD_NEEDED);
    //mPopup.setListItemExpandMax(EXPAND_MAX);
  }
  mPopup.show();
  if (!mPopup.isDropDownAlwaysVisible()) {
    registerOnBackInvokedCallback();
  }
  //mPopup.getListView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
}
public interface OnDismissListener {
  void onDismiss();
}
private static class PopupDataSetObserver extends DataSetObserver {
  private final WeakReference<AutoCompleteTextView> mViewReference;
  private PopupDataSetObserver(  AutoCompleteTextView view){
    mViewReference=new WeakReference<AutoCompleteTextView>(view);
  }
  public void onChanged(){
    final AutoCompleteTextView textView=mViewReference.get();
    if (textView != null && textView.mAdapter != null) {
      textView.post(updateRunnable);
    }
  }
  private final Runnable updateRunnable=new Runnable(){
    public void run(){
      final AutoCompleteTextView textView=mViewReference.get();
      if (textView == null) {
        return;
      }
      final ListAdapter adapter=textView.mAdapter;
      if (adapter == null) {
        return;
      }
      textView. updateDropDownForFilter(adapter.getCount());textView.mPopup.mPopup.remeasure();
    }
  }
;
}
public void init(View decorView,View contentView,TextView promptView,RelativeLayout.LayoutParams params){
  mPopup=new ListPopupWindow(decorView,contentView,params);
  mPopup.setAnchorView(this);
  mPopup.setPromptView(promptView);
}
public AutoCompleteTextView(com.ashera.widget.IWidget widget){
  super(widget);
  mThreshold=2;
}
public void performValidation(){
}
private void registerOnBackInvokedCallback(){
}
private void unregisterOnBackInvokedCallback(){
}
public r.android.text.TextWatcher getTextWatcher(){
  if (mAutoCompleteTextWatcher == null) {
    mAutoCompleteTextWatcher=new MyWatcher();
  }
  return mAutoCompleteTextWatcher;
}
public void setAnchorView(View anchorView){
  mPopup.setAnchorView(anchorView);
}
public DataSetObserver getObserver(){
  return mObserver;
}
}
