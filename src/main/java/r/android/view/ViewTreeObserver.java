package r.android.view;
import r.android.content.Context;
import java.util.ArrayList;
public final class ViewTreeObserver {
  private CopyOnWriteArray<OnScrollChangedListener> mOnScrollChangedListeners;
  private boolean mAlive=true;
public interface OnScrollChangedListener {
    public void onScrollChanged();
  }
  public void addOnScrollChangedListener(  OnScrollChangedListener listener){
    checkIsAlive();
    if (mOnScrollChangedListeners == null) {
      mOnScrollChangedListeners=new CopyOnWriteArray<OnScrollChangedListener>();
    }
    mOnScrollChangedListeners.add(listener);
  }
  public void removeOnScrollChangedListener(  OnScrollChangedListener victim){
    checkIsAlive();
    if (mOnScrollChangedListeners == null) {
      return;
    }
    mOnScrollChangedListeners.remove(victim);
  }
  private void checkIsAlive(){
    if (!mAlive) {
      throw new IllegalStateException("This ViewTreeObserver is not alive, call " + "getViewTreeObserver() again");
    }
  }
  private void kill(){
    mAlive=false;
  }
  public void dispatchOnScrollChanged(){
    final CopyOnWriteArray<OnScrollChangedListener> listeners=mOnScrollChangedListeners;
    if (listeners != null && listeners.size() > 0) {
      Access<OnScrollChangedListener> access=listeners.start();
      try {
        int count=access.size();
        for (int i=0; i < count; i++) {
          access.get(i).onScrollChanged();
        }
      }
  finally {
        listeners.end();
      }
    }
  }
static class CopyOnWriteArray<T> {
    private ArrayList<T> mData=new ArrayList<T>();
    private ArrayList<T> mDataCopy;
    private final Access<T> mAccess=new Access<T>();
    private boolean mStart;
    CopyOnWriteArray(){
    }
    private ArrayList<T> getArray(){
      if (mStart) {
        if (mDataCopy == null)         mDataCopy=new ArrayList<T>(mData);
        return mDataCopy;
      }
      return mData;
    }
    Access<T> start(){
      if (mStart)       throw new IllegalStateException("Iteration already started");
      mStart=true;
      mDataCopy=null;
      mAccess.mData=mData;
      mAccess.mSize=mData.size();
      return mAccess;
    }
    void end(){
      if (!mStart)       throw new IllegalStateException("Iteration not started");
      mStart=false;
      if (mDataCopy != null) {
        mData=mDataCopy;
        mAccess.mData.clear();
        mAccess.mSize=0;
      }
      mDataCopy=null;
    }
    int size(){
      return getArray().size();
    }
    void add(    T item){
      getArray().add(item);
    }
    void addAll(    CopyOnWriteArray<T> array){
      getArray().addAll(array.mData);
    }
    void remove(    T item){
      getArray().remove(item);
    }
    void clear(){
      getArray().clear();
    }
  }
static class Access<T> {
    private ArrayList<T> mData;
    private int mSize;
    T get(    int index){
      return mData.get(index);
    }
    int size(){
      return mSize;
    }
  }
  public ViewTreeObserver(  Context mContext){
  }
  void merge(  ViewTreeObserver observer){
    if (observer.mOnScrollChangedListeners != null) {
      if (mOnScrollChangedListeners != null) {
        mOnScrollChangedListeners.addAll(observer.mOnScrollChangedListeners);
      }
 else {
        mOnScrollChangedListeners=observer.mOnScrollChangedListeners;
      }
    }
  }
}
