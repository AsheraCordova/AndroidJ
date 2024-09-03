package r.android.view;
import r.android.annotation.NonNull;
import r.android.content.Context;
import r.android.graphics.drawable.Drawable;
public class ViewGroupOverlay extends ViewOverlay {
  ViewGroupOverlay(  Context context,  View hostView){
    super(context,hostView);
  }
  public void add(  View view){
    mOverlayViewGroup.add(view);
  }
  public void remove(  View view){
    mOverlayViewGroup.remove(view);
  }
}
