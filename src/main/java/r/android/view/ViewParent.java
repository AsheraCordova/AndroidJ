package r.android.view;
import r.android.graphics.Rect;
public interface ViewParent {
  public void requestLayout();
  public boolean isLayoutRequested();
  public void invalidateChild(  View child,  Rect r);
  public ViewParent getParent();
  public void recomputeViewAttributes(  View child);
  public void focusableViewAvailable(  View v);
  public void childDrawableStateChanged(  View child);
  public boolean canResolveLayoutDirection();
  public boolean isLayoutDirectionResolved();
  public int getLayoutDirection();
  public boolean canResolveTextDirection();
  public boolean isTextDirectionResolved();
  public int getTextDirection();
  public boolean canResolveTextAlignment();
  public boolean isTextAlignmentResolved();
  public int getTextAlignment();
}
