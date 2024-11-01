package r.android.view;
import r.android.content.res.ColorStateList;
import r.android.graphics.drawable.Drawable;
public interface MenuItem {
  public static final int SHOW_AS_ACTION_NEVER=0;
  public static final int SHOW_AS_ACTION_IF_ROOM=1;
  public static final int SHOW_AS_ACTION_ALWAYS=2;
  public static final int SHOW_AS_ACTION_WITH_TEXT=4;
  public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW=8;
  public int getItemId();
  public int getGroupId();
  public CharSequence getTitle();
  public MenuItem setIcon(  Drawable icon);
  public Drawable getIcon();
  public default MenuItem setIconTintList(  ColorStateList tint){
    return this;
  }
  public MenuItem setCheckable(  boolean checkable);
  public MenuItem setChecked(  boolean checked);
  public boolean isChecked();
  public MenuItem setVisible(  boolean visible);
  public boolean isVisible();
  public MenuItem setEnabled(  boolean enabled);
  public boolean isEnabled();
  public boolean hasSubMenu();
  public SubMenu getSubMenu();
  public void setShowAsAction(  int actionEnum);
  public View getActionView();
}
