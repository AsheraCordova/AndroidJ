package r.android.view;

import r.android.content.res.ColorStateList;
import r.android.graphics.drawable.Drawable;

public interface MenuItem {
	/*
     * These should be kept in sync with attrs.xml enum constants for showAsAction
     */
    /** Never show this item as a button in an Action Bar. */
    public static final int SHOW_AS_ACTION_NEVER = 0;
    /** Show this item as a button in an Action Bar if the system decides there is room for it. */
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    /**
     * Always show this item as a button in an Action Bar.
     * Use sparingly! If too many items are set to always show in the Action Bar it can
     * crowd the Action Bar and degrade the user experience on devices with smaller screens.
     * A good rule of thumb is to have no more than 2 items set to always show at a time.
     */
    public static final int SHOW_AS_ACTION_ALWAYS = 2;

    /**
     * When this item is in the action bar, always show it with a text label even if
     * it also has an icon specified.
     */
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;

    /**
     * This item's action view collapses to a normal menu item.
     * When expanded, the action view temporarily takes over
     * a larger segment of its container.
     */
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    
    public Drawable getIcon();
    public String getTitle();
    public MenuItem setIcon(Drawable icon);
    public MenuItem setIconTintList(ColorStateList tint);
    public void setShowAsAction(int actionEnum);
    public int getItemId();
}
