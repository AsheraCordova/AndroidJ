package r.android.view;
import static java.lang.Math.max;
import r.android.content.Context;
import r.android.content.Intent;
import r.android.content.res.ColorStateList;
import r.android.graphics.Insets;
import r.android.graphics.Rect;
import r.android.graphics.drawable.Drawable;
import r.android.os.Build;
import r.android.os.Handler;
import r.android.os.IBinder;
import r.android.util.LayoutDirection;
import r.android.util.Log;
import r.android.util.LongSparseLongArray;
import r.android.util.SparseArray;
import r.android.util.StateSet;
import r.android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
public class View {
  private static final boolean DBG=false;
  public static boolean DEBUG_DRAW=false;
  protected static final String VIEW_LOG_TAG="View";
  public static boolean mDebugViewAttributes=false;
  public static final int NO_ID=-1;
  public static final int LAST_APP_AUTOFILL_ID=Integer.MAX_VALUE / 2;
  private static boolean sCompatibilityDone=false;
  private static boolean sUseBrokenMakeMeasureSpec=false;
  static boolean sUseZeroUnspecifiedMeasureSpec=false;
  private static boolean sIgnoreMeasureCache=false;
  private static boolean sAlwaysRemeasureExactly=false;
  private static boolean sLayoutParamsAlwaysChanged=false;
  static boolean sTextureViewIgnoresDrawableSetters=false;
  protected static boolean sPreserveMarginParamsInLayoutParamConversion;
  static boolean sCascadedDragDrop;
  static boolean sHasFocusableExcludeAutoFocusable;
  private static boolean sAutoFocusableOffUIThreadWontNotifyParents;
  private static boolean sThrowOnInvalidFloatProperties;
  private static boolean sAcceptZeroSizeDragShadow;
  public static final int NOT_FOCUSABLE=0x00000000;
  public static final int FOCUSABLE=0x00000001;
  public static final int FOCUSABLE_AUTO=0x00000010;
  private static final int FOCUSABLE_MASK=0x00000011;
  private static final int FITS_SYSTEM_WINDOWS=0x00000002;
  public static final int VISIBLE=0x00000000;
  public static final int INVISIBLE=0x00000004;
  public static final int GONE=0x00000008;
  static final int VISIBILITY_MASK=0x0000000C;
  public static final int AUTOFILL_TYPE_NONE=0;
  public static final int AUTOFILL_TYPE_TEXT=1;
  public static final int AUTOFILL_TYPE_TOGGLE=2;
  public static final int AUTOFILL_TYPE_LIST=3;
  public static final int AUTOFILL_TYPE_DATE=4;
  public static final int IMPORTANT_FOR_AUTOFILL_AUTO=0x0;
  public static final int IMPORTANT_FOR_AUTOFILL_YES=0x1;
  public static final int IMPORTANT_FOR_AUTOFILL_NO=0x2;
  public static final int IMPORTANT_FOR_AUTOFILL_YES_EXCLUDE_DESCENDANTS=0x4;
  public static final int IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS=0x8;
  public static final int AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS=0x1;
  static final int ENABLED=0x00000000;
  static final int DISABLED=0x00000020;
  static final int ENABLED_MASK=0x00000020;
  static final int WILL_NOT_DRAW=0x00000080;
  static final int DRAW_MASK=0x00000080;
  static final int SCROLLBARS_NONE=0x00000000;
  static final int SCROLLBARS_HORIZONTAL=0x00000100;
  static final int SCROLLBARS_VERTICAL=0x00000200;
  static final int SCROLLBARS_MASK=0x00000300;
  static final int FILTER_TOUCHES_WHEN_OBSCURED=0x00000400;
  static final int OPTIONAL_FITS_SYSTEM_WINDOWS=0x00000800;
  static final int FADING_EDGE_NONE=0x00000000;
  static final int FADING_EDGE_HORIZONTAL=0x00001000;
  static final int FADING_EDGE_VERTICAL=0x00002000;
  static final int FADING_EDGE_MASK=0x00003000;
  static final int CLICKABLE=0x00004000;
  static final int DRAWING_CACHE_ENABLED=0x00008000;
  static final int SAVE_DISABLED=0x000010000;
  static final int SAVE_DISABLED_MASK=0x000010000;
  static final int WILL_NOT_CACHE_DRAWING=0x000020000;
  static final int FOCUSABLE_IN_TOUCH_MODE=0x00040000;
  public static final int DRAWING_CACHE_QUALITY_LOW=0x00080000;
  public static final int DRAWING_CACHE_QUALITY_HIGH=0x00100000;
  public static final int DRAWING_CACHE_QUALITY_AUTO=0x00000000;
  static final int DRAWING_CACHE_QUALITY_MASK=0x00180000;
  static final int LONG_CLICKABLE=0x00200000;
  static final int DUPLICATE_PARENT_STATE=0x00400000;
  static final int CONTEXT_CLICKABLE=0x00800000;
  public static final int SCROLLBARS_INSIDE_OVERLAY=0;
  public static final int SCROLLBARS_INSIDE_INSET=0x01000000;
  public static final int SCROLLBARS_OUTSIDE_OVERLAY=0x02000000;
  public static final int SCROLLBARS_OUTSIDE_INSET=0x03000000;
  static final int SCROLLBARS_INSET_MASK=0x01000000;
  static final int SCROLLBARS_OUTSIDE_MASK=0x02000000;
  static final int SCROLLBARS_STYLE_MASK=0x03000000;
  public static final int KEEP_SCREEN_ON=0x04000000;
  public static final int SOUND_EFFECTS_ENABLED=0x08000000;
  public static final int HAPTIC_FEEDBACK_ENABLED=0x10000000;
  static final int PARENT_SAVE_DISABLED=0x20000000;
  static final int PARENT_SAVE_DISABLED_MASK=0x20000000;
  static final int TOOLTIP=0x40000000;
  public static final int FOCUSABLES_ALL=0x00000000;
  public static final int FOCUSABLES_TOUCH_MODE=0x00000001;
  public static final int FOCUS_BACKWARD=0x00000001;
  public static final int FOCUS_FORWARD=0x00000002;
  public static final int FOCUS_LEFT=0x00000011;
  public static final int FOCUS_UP=0x00000021;
  public static final int FOCUS_RIGHT=0x00000042;
  public static final int FOCUS_DOWN=0x00000082;
  public static final int MEASURED_SIZE_MASK=0x00ffffff;
  public static final int MEASURED_STATE_MASK=0xff000000;
  public static final int MEASURED_HEIGHT_STATE_SHIFT=16;
  public static final int MEASURED_STATE_TOO_SMALL=0x01000000;
  static final int DEBUG_CORNERS_SIZE_DIP=8;
  private SparseArray<Object> mKeyedTags;
  private static int sNextAccessibilityViewId;
  int mMeasuredWidth;
  int mMeasuredHeight;
  boolean mRecreateDisplayList=false;
  int mID=NO_ID;
  private int mAutofillViewId=NO_ID;
  private int mAccessibilityViewId=NO_ID;
  private int mAccessibilityCursorPosition=ACCESSIBILITY_CURSOR_POSITION_UNDEFINED;
  protected @com.google.j2objc.annotations.Weak Object mTag=null;
  static final int PFLAG_WANTS_FOCUS=0x00000001;
  static final int PFLAG_FOCUSED=0x00000002;
  static final int PFLAG_SELECTED=0x00000004;
  static final int PFLAG_IS_ROOT_NAMESPACE=0x00000008;
  static final int PFLAG_HAS_BOUNDS=0x00000010;
  static final int PFLAG_DRAWN=0x00000020;
  static final int PFLAG_DRAW_ANIMATION=0x00000040;
  static final int PFLAG_SKIP_DRAW=0x00000080;
  static final int PFLAG_REQUEST_TRANSPARENT_REGIONS=0x00000200;
  static final int PFLAG_DRAWABLE_STATE_DIRTY=0x00000400;
  static final int PFLAG_MEASURED_DIMENSION_SET=0x00000800;
  static final int PFLAG_FORCE_LAYOUT=0x00001000;
  static final int PFLAG_LAYOUT_REQUIRED=0x00002000;
  private static final int PFLAG_PRESSED=0x00004000;
  static final int PFLAG_DRAWING_CACHE_VALID=0x00008000;
  static final int PFLAG_ANIMATION_STARTED=0x00010000;
  private static final int PFLAG_SAVE_STATE_CALLED=0x00020000;
  static final int PFLAG_ALPHA_SET=0x00040000;
  static final int PFLAG_SCROLL_CONTAINER=0x00080000;
  static final int PFLAG_SCROLL_CONTAINER_ADDED=0x00100000;
  static final int PFLAG_DIRTY=0x00200000;
  static final int PFLAG_DIRTY_OPAQUE=0x00400000;
  static final int PFLAG_DIRTY_MASK=0x00600000;
  static final int PFLAG_OPAQUE_BACKGROUND=0x00800000;
  static final int PFLAG_OPAQUE_SCROLLBARS=0x01000000;
  static final int PFLAG_OPAQUE_MASK=0x01800000;
  private static final int PFLAG_PREPRESSED=0x02000000;
  static final int PFLAG_CANCEL_NEXT_UP_EVENT=0x04000000;
  private static final int PFLAG_AWAKEN_SCROLL_BARS_ON_ATTACH=0x08000000;
  private static final int PFLAG_HOVERED=0x10000000;
  private static final int PFLAG_NOTIFY_AUTOFILL_MANAGER_ON_CLICK=0x20000000;
  static final int PFLAG_ACTIVATED=0x40000000;
  static final int PFLAG_INVALIDATED=0x80000000;
  static final int PFLAG2_DRAG_CAN_ACCEPT=0x00000001;
  static final int PFLAG2_DRAG_HOVERED=0x00000002;
  public static final int LAYOUT_DIRECTION_UNDEFINED=LayoutDirection.UNDEFINED;
  public static final int LAYOUT_DIRECTION_LTR=LayoutDirection.LTR;
  public static final int LAYOUT_DIRECTION_RTL=LayoutDirection.RTL;
  public static final int LAYOUT_DIRECTION_INHERIT=LayoutDirection.INHERIT;
  public static final int LAYOUT_DIRECTION_LOCALE=LayoutDirection.LOCALE;
  static final int PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT=2;
  static final int PFLAG2_LAYOUT_DIRECTION_MASK=0x00000003 << PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT;
  static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL=4 << PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT;
  static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED=8 << PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT;
  static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_MASK=0x0000000C << PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT;
  private static final int LAYOUT_DIRECTION_DEFAULT=LAYOUT_DIRECTION_INHERIT;
  static final int LAYOUT_DIRECTION_RESOLVED_DEFAULT=LAYOUT_DIRECTION_LTR;
  public static final int TEXT_DIRECTION_INHERIT=0;
  public static final int TEXT_DIRECTION_FIRST_STRONG=1;
  public static final int TEXT_DIRECTION_ANY_RTL=2;
  public static final int TEXT_DIRECTION_LTR=3;
  public static final int TEXT_DIRECTION_RTL=4;
  public static final int TEXT_DIRECTION_LOCALE=5;
  public static final int TEXT_DIRECTION_FIRST_STRONG_LTR=6;
  public static final int TEXT_DIRECTION_FIRST_STRONG_RTL=7;
  private static final int TEXT_DIRECTION_DEFAULT=TEXT_DIRECTION_INHERIT;
  static final int TEXT_DIRECTION_RESOLVED_DEFAULT=TEXT_DIRECTION_FIRST_STRONG;
  static final int PFLAG2_TEXT_DIRECTION_MASK_SHIFT=6;
  static final int PFLAG2_TEXT_DIRECTION_MASK=0x00000007 << PFLAG2_TEXT_DIRECTION_MASK_SHIFT;
  static final int PFLAG2_TEXT_DIRECTION_RESOLVED=0x00000008 << PFLAG2_TEXT_DIRECTION_MASK_SHIFT;
  static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT=10;
  static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK=0x00000007 << PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT;
  static final int PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT=TEXT_DIRECTION_RESOLVED_DEFAULT << PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT;
  public static final int TEXT_ALIGNMENT_INHERIT=0;
  public static final int TEXT_ALIGNMENT_GRAVITY=1;
  public static final int TEXT_ALIGNMENT_TEXT_START=2;
  public static final int TEXT_ALIGNMENT_TEXT_END=3;
  public static final int TEXT_ALIGNMENT_CENTER=4;
  public static final int TEXT_ALIGNMENT_VIEW_START=5;
  public static final int TEXT_ALIGNMENT_VIEW_END=6;
  private static final int TEXT_ALIGNMENT_DEFAULT=TEXT_ALIGNMENT_GRAVITY;
  static final int TEXT_ALIGNMENT_RESOLVED_DEFAULT=TEXT_ALIGNMENT_GRAVITY;
  static final int PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT=13;
  static final int PFLAG2_TEXT_ALIGNMENT_MASK=0x00000007 << PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT;
  static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED=0x00000008 << PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT;
  static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT=17;
  static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK=0x00000007 << PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT;
  private static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT=TEXT_ALIGNMENT_RESOLVED_DEFAULT << PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT;
  static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_SHIFT=20;
  public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO=0x00000000;
  public static final int IMPORTANT_FOR_ACCESSIBILITY_YES=0x00000001;
  public static final int IMPORTANT_FOR_ACCESSIBILITY_NO=0x00000002;
  public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS=0x00000004;
  static final int IMPORTANT_FOR_ACCESSIBILITY_DEFAULT=IMPORTANT_FOR_ACCESSIBILITY_AUTO;
  static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK=(IMPORTANT_FOR_ACCESSIBILITY_AUTO | IMPORTANT_FOR_ACCESSIBILITY_YES | IMPORTANT_FOR_ACCESSIBILITY_NO| IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS) << PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_SHIFT;
  static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_SHIFT=23;
  public static final int ACCESSIBILITY_LIVE_REGION_NONE=0x00000000;
  public static final int ACCESSIBILITY_LIVE_REGION_POLITE=0x00000001;
  public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE=0x00000002;
  static final int ACCESSIBILITY_LIVE_REGION_DEFAULT=ACCESSIBILITY_LIVE_REGION_NONE;
  static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_MASK=(ACCESSIBILITY_LIVE_REGION_NONE | ACCESSIBILITY_LIVE_REGION_POLITE | ACCESSIBILITY_LIVE_REGION_ASSERTIVE) << PFLAG2_ACCESSIBILITY_LIVE_REGION_SHIFT;
  static final int PFLAG2_ACCESSIBILITY_FOCUSED=0x04000000;
  static final int PFLAG2_SUBTREE_ACCESSIBILITY_STATE_CHANGED=0x08000000;
  static final int PFLAG2_VIEW_QUICK_REJECTED=0x10000000;
  static final int PFLAG2_PADDING_RESOLVED=0x20000000;
  static final int PFLAG2_DRAWABLE_RESOLVED=0x40000000;
  static final int PFLAG2_HAS_TRANSIENT_STATE=0x80000000;
  static final int ALL_RTL_PROPERTIES_RESOLVED=PFLAG2_LAYOUT_DIRECTION_RESOLVED | PFLAG2_TEXT_DIRECTION_RESOLVED | PFLAG2_TEXT_ALIGNMENT_RESOLVED| PFLAG2_PADDING_RESOLVED| PFLAG2_DRAWABLE_RESOLVED;
  static final int PFLAG3_VIEW_IS_ANIMATING_TRANSFORM=0x1;
  static final int PFLAG3_VIEW_IS_ANIMATING_ALPHA=0x2;
  static final int PFLAG3_IS_LAID_OUT=0x4;
  static final int PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT=0x8;
  static final int PFLAG3_CALLED_SUPER=0x10;
  static final int PFLAG3_APPLYING_INSETS=0x20;
  static final int PFLAG3_FITTING_SYSTEM_WINDOWS=0x40;
  static final int PFLAG3_NESTED_SCROLLING_ENABLED=0x80;
  static final int PFLAG3_SCROLL_INDICATOR_TOP=0x0100;
  static final int PFLAG3_SCROLL_INDICATOR_BOTTOM=0x0200;
  static final int PFLAG3_SCROLL_INDICATOR_LEFT=0x0400;
  static final int PFLAG3_SCROLL_INDICATOR_RIGHT=0x0800;
  static final int PFLAG3_SCROLL_INDICATOR_START=0x1000;
  static final int PFLAG3_SCROLL_INDICATOR_END=0x2000;
  static final int DRAG_MASK=PFLAG2_DRAG_CAN_ACCEPT | PFLAG2_DRAG_HOVERED;
  static final int SCROLL_INDICATORS_NONE=0x0000;
  static final int SCROLL_INDICATORS_PFLAG3_MASK=PFLAG3_SCROLL_INDICATOR_TOP | PFLAG3_SCROLL_INDICATOR_BOTTOM | PFLAG3_SCROLL_INDICATOR_LEFT| PFLAG3_SCROLL_INDICATOR_RIGHT| PFLAG3_SCROLL_INDICATOR_START| PFLAG3_SCROLL_INDICATOR_END;
  static final int SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT=8;
  public static final int SCROLL_INDICATOR_TOP=PFLAG3_SCROLL_INDICATOR_TOP >> SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT;
  public static final int SCROLL_INDICATOR_BOTTOM=PFLAG3_SCROLL_INDICATOR_BOTTOM >> SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT;
  public static final int SCROLL_INDICATOR_LEFT=PFLAG3_SCROLL_INDICATOR_LEFT >> SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT;
  public static final int SCROLL_INDICATOR_RIGHT=PFLAG3_SCROLL_INDICATOR_RIGHT >> SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT;
  public static final int SCROLL_INDICATOR_START=PFLAG3_SCROLL_INDICATOR_START >> SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT;
  public static final int SCROLL_INDICATOR_END=PFLAG3_SCROLL_INDICATOR_END >> SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT;
  static final int PFLAG3_ASSIST_BLOCKED=0x4000;
  private static final int PFLAG3_CLUSTER=0x8000;
  private static final int PFLAG3_IS_AUTOFILLED=0x10000;
  private static final int PFLAG3_FINGER_DOWN=0x20000;
  private static final int PFLAG3_FOCUSED_BY_DEFAULT=0x40000;
  static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_SHIFT=19;
  static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK=(IMPORTANT_FOR_AUTOFILL_AUTO | IMPORTANT_FOR_AUTOFILL_YES | IMPORTANT_FOR_AUTOFILL_NO| IMPORTANT_FOR_AUTOFILL_YES_EXCLUDE_DESCENDANTS| IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS) << PFLAG3_IMPORTANT_FOR_AUTOFILL_SHIFT;
  private static final int PFLAG3_OVERLAPPING_RENDERING_FORCED_VALUE=0x800000;
  private static final int PFLAG3_HAS_OVERLAPPING_RENDERING_FORCED=0x1000000;
  static final int PFLAG3_TEMPORARY_DETACH=0x2000000;
  private static final int PFLAG3_NO_REVEAL_ON_FOCUS=0x4000000;
  static final int PFLAG3_NOTIFY_AUTOFILL_ENTER_ON_LAYOUT=0x8000000;
  private static final int PFLAG3_SCREEN_READER_FOCUSABLE=0x10000000;
  private static final int PFLAG3_AGGREGATED_VISIBLE=0x20000000;
  private static final int PFLAG3_AUTOFILLID_EXPLICITLY_SET=0x40000000;
  private static final int PFLAG3_ACCESSIBILITY_HEADING=0x80000000;
  public static final int OVER_SCROLL_ALWAYS=0;
  public static final int OVER_SCROLL_IF_CONTENT_SCROLLS=1;
  public static final int OVER_SCROLL_NEVER=2;
  public static final int SYSTEM_UI_FLAG_VISIBLE=0;
  public static final int SYSTEM_UI_FLAG_LOW_PROFILE=0x00000001;
  public static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION=0x00000002;
  public static final int SYSTEM_UI_FLAG_FULLSCREEN=0x00000004;
  public static final int SYSTEM_UI_FLAG_LAYOUT_STABLE=0x00000100;
  public static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION=0x00000200;
  public static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN=0x00000400;
  public static final int SYSTEM_UI_FLAG_IMMERSIVE=0x00000800;
  public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY=0x00001000;
  public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR=0x00002000;
  private static final int SYSTEM_UI_RESERVED_LEGACY1=0x00004000;
  private static final int SYSTEM_UI_RESERVED_LEGACY2=0x00010000;
  public static final int SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR=0x00000010;
  public static final int STATUS_BAR_HIDDEN=SYSTEM_UI_FLAG_LOW_PROFILE;
  public static final int STATUS_BAR_VISIBLE=SYSTEM_UI_FLAG_VISIBLE;
  public static final int STATUS_BAR_DISABLE_EXPAND=0x00010000;
  public static final int STATUS_BAR_DISABLE_NOTIFICATION_ICONS=0x00020000;
  public static final int STATUS_BAR_DISABLE_NOTIFICATION_ALERTS=0x00040000;
  public static final int STATUS_BAR_DISABLE_NOTIFICATION_TICKER=0x00080000;
  public static final int STATUS_BAR_DISABLE_SYSTEM_INFO=0x00100000;
  public static final int STATUS_BAR_DISABLE_HOME=0x00200000;
  public static final int STATUS_BAR_DISABLE_BACK=0x00400000;
  public static final int STATUS_BAR_DISABLE_CLOCK=0x00800000;
  public static final int STATUS_BAR_DISABLE_RECENT=0x01000000;
  public static final int STATUS_BAR_DISABLE_SEARCH=0x02000000;
  public static final int STATUS_BAR_TRANSIENT=0x04000000;
  public static final int NAVIGATION_BAR_TRANSIENT=0x08000000;
  public static final int STATUS_BAR_UNHIDE=0x10000000;
  public static final int NAVIGATION_BAR_UNHIDE=0x20000000;
  public static final int STATUS_BAR_TRANSLUCENT=0x40000000;
  public static final int NAVIGATION_BAR_TRANSLUCENT=0x80000000;
  public static final int NAVIGATION_BAR_TRANSPARENT=0x00008000;
  public static final int STATUS_BAR_TRANSPARENT=0x00000008;
  public static final int SYSTEM_UI_TRANSPARENT=NAVIGATION_BAR_TRANSPARENT | STATUS_BAR_TRANSPARENT;
  public static final int PUBLIC_STATUS_BAR_VISIBILITY_MASK=0x00003FF7;
  public static final int SYSTEM_UI_CLEARABLE_FLAGS=SYSTEM_UI_FLAG_LOW_PROFILE | SYSTEM_UI_FLAG_HIDE_NAVIGATION | SYSTEM_UI_FLAG_FULLSCREEN;
  public static final int SYSTEM_UI_LAYOUT_FLAGS=SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
  public static final int FIND_VIEWS_WITH_TEXT=0x00000001;
  public static final int FIND_VIEWS_WITH_CONTENT_DESCRIPTION=0x00000002;
  public static final int FIND_VIEWS_WITH_ACCESSIBILITY_NODE_PROVIDERS=0x00000004;
  public static final int ACCESSIBILITY_CURSOR_POSITION_UNDEFINED=-1;
  public static final int SCREEN_STATE_OFF=0x0;
  public static final int SCREEN_STATE_ON=0x1;
  public static final int SCROLL_AXIS_NONE=0;
  public static final int SCROLL_AXIS_HORIZONTAL=1 << 0;
  public static final int SCROLL_AXIS_VERTICAL=1 << 1;
  private int mOverScrollMode;
  @com.google.j2objc.annotations.Weak protected ViewParent mParent;
  public int mPrivateFlags;
  int mPrivateFlags2;
  int mPrivateFlags3;
  int mSystemUiVisibility;
  int mTransientStateCount=0;
  int mWindowAttachCount;
  protected ViewGroup.LayoutParams mLayoutParams;
  int mViewFlags;
  private boolean mLastIsOpaque;
  protected int mLeft;
  protected int mRight;
  protected int mTop;
  protected int mBottom;
  protected int mScrollX;
  protected int mScrollY;
  protected int mPaddingLeft=0;
  protected int mPaddingRight=0;
  protected int mPaddingTop;
  protected int mPaddingBottom;
  private Insets mLayoutInsets;
  private int mLabelForId=View.NO_ID;
  private int mAccessibilityTraversalBeforeId=NO_ID;
  private int mAccessibilityTraversalAfterId=NO_ID;
  protected int mUserPaddingRight;
  protected int mUserPaddingBottom;
  protected int mUserPaddingLeft;
  int mUserPaddingStart;
  int mUserPaddingEnd;
  int mUserPaddingLeftInitial;
  int mUserPaddingRightInitial;
  private static final int UNDEFINED_PADDING=Integer.MIN_VALUE;
  private boolean mLeftPaddingDefined=false;
  private boolean mRightPaddingDefined=false;
  int mOldWidthMeasureSpec=Integer.MIN_VALUE;
  int mOldHeightMeasureSpec=Integer.MIN_VALUE;
  private LongSparseLongArray mMeasureCache;
  private Drawable mBackground;
  private TintInfo mBackgroundTint;
  private ForegroundInfo mForegroundInfo;
  private int mBackgroundResource;
  private boolean mBackgroundSizeChanged;
  private Drawable mDefaultFocusHighlight;
  private boolean mDefaultFocusHighlightSizeChanged;
  private static boolean sUseDefaultFocusHighlight;
  private static boolean sCanFocusZeroSized;
  private static boolean sAlwaysAssignFocus;
private static class ForegroundInfo {
    private Drawable mDrawable;
    private TintInfo mTintInfo;
    private int mGravity=Gravity.FILL;
    private boolean mInsidePadding=true;
    private boolean mBoundsChanged=true;
    private final Rect mSelfBounds=new Rect();
    private final Rect mOverlayBounds=new Rect();
  }
  protected Context mContext;
  private int[] mDrawableState=null;
  private int mNextFocusLeftId=View.NO_ID;
  private int mNextFocusRightId=View.NO_ID;
  private int mNextFocusUpId=View.NO_ID;
  private int mNextFocusDownId=View.NO_ID;
  int mNextFocusForwardId=View.NO_ID;
  int mNextClusterForwardId=View.NO_ID;
  boolean mDefaultFocusHighlightEnabled=true;
  private boolean mHasPerformedLongPress;
  private boolean mInContextButtonPress;
  private boolean mIgnoreNextUpEvent;
  private int mMinHeight;
  private int mMinWidth;
  private int mDrawingCacheBackgroundColor=0;
  private ViewTreeObserver mFloatingTreeObserver;
  private int mTouchSlop;
  public static final int DRAG_FLAG_GLOBAL=1 << 8;
  public static final int DRAG_FLAG_GLOBAL_URI_READ=Intent.FLAG_GRANT_READ_URI_PERMISSION;
  public static final int DRAG_FLAG_GLOBAL_URI_WRITE=Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
  public static final int DRAG_FLAG_GLOBAL_PERSISTABLE_URI_PERMISSION=Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
  public static final int DRAG_FLAG_GLOBAL_PREFIX_URI_PERMISSION=Intent.FLAG_GRANT_PREFIX_URI_PERMISSION;
  public static final int DRAG_FLAG_OPAQUE=1 << 9;
  private int mVerticalScrollbarPosition;
  public static final int SCROLLBAR_POSITION_DEFAULT=0;
  public static final int SCROLLBAR_POSITION_LEFT=1;
  public static final int SCROLLBAR_POSITION_RIGHT=2;
  public static final int LAYER_TYPE_NONE=0;
  public static final int LAYER_TYPE_SOFTWARE=1;
  public static final int LAYER_TYPE_HARDWARE=2;
  int mLayerType=LAYER_TYPE_NONE;
  public boolean mCachingFailed;
  private boolean mSendingHoverAccessibilityEvents;
  private RoundScrollbarRenderer mRoundScrollbarRenderer;
  ListenerInfo getListenerInfo(){
    if (mListenerInfo != null) {
      return mListenerInfo;
    }
    mListenerInfo=new ListenerInfo();
    return mListenerInfo;
  }
  public void addOnLayoutChangeListener(  OnLayoutChangeListener listener){
    ListenerInfo li=getListenerInfo();
    if (li.mOnLayoutChangeListeners == null) {
      li.mOnLayoutChangeListeners=new ArrayList<OnLayoutChangeListener>();
    }
    if (!li.mOnLayoutChangeListeners.contains(listener)) {
      li.mOnLayoutChangeListeners.add(listener);
    }
  }
  public void removeOnLayoutChangeListener(  OnLayoutChangeListener listener){
    ListenerInfo li=mListenerInfo;
    if (li == null || li.mOnLayoutChangeListeners == null) {
      return;
    }
    li.mOnLayoutChangeListeners.remove(listener);
  }
  public void addOnAttachStateChangeListener(  OnAttachStateChangeListener listener){
    ListenerInfo li=getListenerInfo();
    if (li.mOnAttachStateChangeListeners == null) {
      li.mOnAttachStateChangeListeners=new CopyOnWriteArrayList<OnAttachStateChangeListener>();
    }
    li.mOnAttachStateChangeListeners.add(listener);
  }
  public void removeOnAttachStateChangeListener(  OnAttachStateChangeListener listener){
    ListenerInfo li=mListenerInfo;
    if (li == null || li.mOnAttachStateChangeListeners == null) {
      return;
    }
    li.mOnAttachStateChangeListeners.remove(listener);
  }
  public void setOnKeyListener(  OnKeyListener l){
    getListenerInfo().mOnKeyListener=l;
  }
  public boolean isFocused(){
    return (mPrivateFlags & PFLAG_FOCUSED) != 0;
  }
  public boolean isShown(){
    View current=this;
    do {
      if ((current.mViewFlags & VISIBILITY_MASK) != VISIBLE) {
        return false;
      }
      ViewParent parent=current.mParent;
      if (parent == null) {
        return false;
      }
      if (!(parent instanceof View)) {
        return true;
      }
      current=(View)parent;
    }
 while (current != null);
    return false;
  }
  public int getVisibility(){
    return mViewFlags & VISIBILITY_MASK;
  }
  public void setVisibility(  int visibility){
    setFlags(visibility,VISIBILITY_MASK);
  }
  public boolean isEnabled(){
    return (mViewFlags & ENABLED_MASK) == ENABLED;
  }
  public void setEnabled(  boolean enabled){
    if (enabled == isEnabled())     return;
    setFlags(enabled ? ENABLED : DISABLED,ENABLED_MASK);
    refreshDrawableState();
    invalidate(true);
    if (!enabled) {
      cancelPendingInputEvents();
    }
  }
  public void setFocusable(  boolean focusable){
    setFocusable(focusable ? FOCUSABLE : NOT_FOCUSABLE);
  }
  public void setFocusable(  int focusable){
    if ((focusable & (FOCUSABLE_AUTO | FOCUSABLE)) == 0) {
      setFlags(0,FOCUSABLE_IN_TOUCH_MODE);
    }
    setFlags(focusable,FOCUSABLE_MASK);
  }
  public int getRawLayoutDirection(){
    return (mPrivateFlags2 & PFLAG2_LAYOUT_DIRECTION_MASK) >> PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT;
  }
  public void setLayoutDirection(  int layoutDirection){
    if (getRawLayoutDirection() != layoutDirection) {
      mPrivateFlags2&=~PFLAG2_LAYOUT_DIRECTION_MASK;
      resetRtlProperties();
      mPrivateFlags2|=((layoutDirection << PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT) & PFLAG2_LAYOUT_DIRECTION_MASK);
      resolveRtlPropertiesIfNeeded();
      requestLayout();
      invalidate(true);
    }
  }
  public int getLayoutDirection(){
    final int targetSdkVersion=getContext().getApplicationInfo().targetSdkVersion;
    if (targetSdkVersion < Build.VERSION_CODES.JELLY_BEAN_MR1) {
      mPrivateFlags2|=PFLAG2_LAYOUT_DIRECTION_RESOLVED;
      return LAYOUT_DIRECTION_RESOLVED_DEFAULT;
    }
    return ((mPrivateFlags2 & PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL) == PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL) ? LAYOUT_DIRECTION_RTL : LAYOUT_DIRECTION_LTR;
  }
  public boolean isLayoutRtl(){
    return (getLayoutDirection() == LAYOUT_DIRECTION_RTL);
  }
  public boolean isAttachedToWindow(){
    return mAttachInfo != null;
  }
  public boolean isLaidOut(){
    return (mPrivateFlags3 & PFLAG3_IS_LAID_OUT) == PFLAG3_IS_LAID_OUT;
  }
  boolean isLayoutValid(){
    return isLaidOut() && ((mPrivateFlags & PFLAG_FORCE_LAYOUT) == 0);
  }
  public void setWillNotDraw(  boolean willNotDraw){
    setFlags(willNotDraw ? WILL_NOT_DRAW : 0,DRAW_MASK);
  }
  public boolean isClickable(){
    return (mViewFlags & CLICKABLE) == CLICKABLE;
  }
  public void setClickable(  boolean clickable){
    setFlags(clickable ? CLICKABLE : 0,CLICKABLE);
  }
  public boolean isLongClickable(){
    return (mViewFlags & LONG_CLICKABLE) == LONG_CLICKABLE;
  }
  public void setLongClickable(  boolean longClickable){
    setFlags(longClickable ? LONG_CLICKABLE : 0,LONG_CLICKABLE);
  }
  private void setPressed(  boolean pressed,  float x,  float y){
    if (pressed) {
      drawableHotspotChanged(x,y);
    }
    setPressed(pressed);
  }
  public void setPressed(  boolean pressed){
    final boolean needsRefresh=pressed != ((mPrivateFlags & PFLAG_PRESSED) == PFLAG_PRESSED);
    if (pressed) {
      mPrivateFlags|=PFLAG_PRESSED;
    }
 else {
      mPrivateFlags&=~PFLAG_PRESSED;
    }
    if (needsRefresh) {
      refreshDrawableState();
    }
    dispatchSetPressed(pressed);
  }
  protected void dispatchSetPressed(  boolean pressed){
  }
  public boolean isPressed(){
    return (mPrivateFlags & PFLAG_PRESSED) == PFLAG_PRESSED;
  }
  public final boolean isFocusable(){
    return FOCUSABLE == (mViewFlags & FOCUSABLE);
  }
  void clearParentsWantFocus(){
    if (mParent instanceof View) {
      ((View)mParent).mPrivateFlags&=~PFLAG_WANTS_FOCUS;
      ((View)mParent).clearParentsWantFocus();
    }
  }
  protected void onVisibilityChanged(  View changedView,  int visibility){
  }
  public int getWindowVisibility(){
    return mAttachInfo != null ? mAttachInfo.mWindowVisibility : GONE;
  }
  public boolean isHovered(){
    return (mPrivateFlags & PFLAG_HOVERED) != 0;
  }
  public void setHovered(  boolean hovered){
    if (hovered) {
      if ((mPrivateFlags & PFLAG_HOVERED) == 0) {
        mPrivateFlags|=PFLAG_HOVERED;
        refreshDrawableState();
        onHoverChanged(true);
      }
    }
 else {
      if ((mPrivateFlags & PFLAG_HOVERED) != 0) {
        mPrivateFlags&=~PFLAG_HOVERED;
        refreshDrawableState();
        onHoverChanged(false);
      }
    }
  }
  public void onHoverChanged(  boolean hovered){
  }
  private boolean hasSize(){
    return (mBottom > mTop) && (mRight > mLeft);
  }
  private boolean canTakeFocus(){
    return ((mViewFlags & VISIBILITY_MASK) == VISIBLE) && ((mViewFlags & FOCUSABLE) == FOCUSABLE) && ((mViewFlags & ENABLED_MASK) == ENABLED)&& (sCanFocusZeroSized || !isLayoutValid() || hasSize());
  }
public interface OnScrollChangeListener {
    void onScrollChange(    View v,    int scrollX,    int scrollY,    int oldScrollX,    int oldScrollY);
  }
public interface OnLayoutChangeListener {
    void onLayoutChange(    View v,    int left,    int top,    int right,    int bottom,    int oldLeft,    int oldTop,    int oldRight,    int oldBottom);
  }
  protected void onSizeChanged(  int w,  int h,  int oldw,  int oldh){
  }
  public final ViewParent getParent(){
    return mParent;
  }
  public final int getWidth(){
    return mRight - mLeft;
  }
  public final int getHeight(){
    return mBottom - mTop;
  }
  public final int getMeasuredWidth(){
    return mMeasuredWidth & MEASURED_SIZE_MASK;
  }
  public final int getMeasuredWidthAndState(){
    return mMeasuredWidth;
  }
  public final int getMeasuredHeight(){
    return mMeasuredHeight & MEASURED_SIZE_MASK;
  }
  public final int getMeasuredState(){
    return (mMeasuredWidth & MEASURED_STATE_MASK) | ((mMeasuredHeight >> MEASURED_HEIGHT_STATE_SHIFT) & (MEASURED_STATE_MASK >> MEASURED_HEIGHT_STATE_SHIFT));
  }
  public final int getTop(){
    return mTop;
  }
  public final int getBottom(){
    return mBottom;
  }
  public final int getLeft(){
    return mLeft;
  }
  public final int getRight(){
    return mRight;
  }
  private static final int PROVIDER_BACKGROUND=0;
  private static final int PROVIDER_NONE=1;
  private static final int PROVIDER_BOUNDS=2;
  private static final int PROVIDER_PADDED_BOUNDS=3;
  public void offsetTopAndBottom(  int offset){
    if (offset != 0) {
      final boolean matrixIsIdentity=hasIdentityMatrix();
      if (matrixIsIdentity) {
        if (isHardwareAccelerated()) {
          invalidateViewProperty(false,false);
        }
 else {
          final ViewParent p=mParent;
          if (p != null && mAttachInfo != null) {
            final Rect r=mAttachInfo.mTmpInvalRect;
            int minTop;
            int maxBottom;
            int yLoc;
            if (offset < 0) {
              minTop=mTop + offset;
              maxBottom=mBottom;
              yLoc=offset;
            }
 else {
              minTop=mTop;
              maxBottom=mBottom + offset;
              yLoc=0;
            }
            r.set(0,yLoc,mRight - mLeft,maxBottom - minTop);
            p.invalidateChild(this,r);
          }
        }
      }
 else {
        invalidateViewProperty(false,false);
      }
      mTop+=offset;
      mBottom+=offset;
      mRenderNode.offsetTopAndBottom(offset);
      if (isHardwareAccelerated()) {
        invalidateViewProperty(false,false);
        invalidateParentIfNeededAndWasQuickRejected();
      }
 else {
        if (!matrixIsIdentity) {
          invalidateViewProperty(false,true);
        }
        invalidateParentIfNeeded();
      }
      notifySubtreeAccessibilityStateChangedIfNeeded();
    }
  }
  public void offsetLeftAndRight(  int offset){
    if (offset != 0) {
      final boolean matrixIsIdentity=hasIdentityMatrix();
      if (matrixIsIdentity) {
        if (isHardwareAccelerated()) {
          invalidateViewProperty(false,false);
        }
 else {
          final ViewParent p=mParent;
          if (p != null && mAttachInfo != null) {
            final Rect r=mAttachInfo.mTmpInvalRect;
            int minLeft;
            int maxRight;
            if (offset < 0) {
              minLeft=mLeft + offset;
              maxRight=mRight;
            }
 else {
              minLeft=mLeft;
              maxRight=mRight + offset;
            }
            r.set(0,0,maxRight - minLeft,mBottom - mTop);
            p.invalidateChild(this,r);
          }
        }
      }
 else {
        invalidateViewProperty(false,false);
      }
      mLeft+=offset;
      mRight+=offset;
      mRenderNode.offsetLeftAndRight(offset);
      if (isHardwareAccelerated()) {
        invalidateViewProperty(false,false);
        invalidateParentIfNeededAndWasQuickRejected();
      }
 else {
        if (!matrixIsIdentity) {
          invalidateViewProperty(false,true);
        }
        invalidateParentIfNeeded();
      }
      notifySubtreeAccessibilityStateChangedIfNeeded();
    }
  }
  public ViewGroup.LayoutParams getLayoutParams(){
    return mLayoutParams;
  }
  public void setLayoutParams(  ViewGroup.LayoutParams params){
    if (params == null) {
      throw new NullPointerException("Layout parameters cannot be null");
    }
    mLayoutParams=params;
    resolveLayoutParams();
    if (mParent instanceof ViewGroup) {
      ((ViewGroup)mParent).onSetLayoutParams(this,params);
    }
    requestLayout();
  }
  public void resolveLayoutParams(){
    if (mLayoutParams != null) {
      mLayoutParams.resolveLayoutDirection(getLayoutDirection());
    }
  }
  private boolean skipInvalidate(){
    return (mViewFlags & VISIBILITY_MASK) != VISIBLE && mCurrentAnimation == null && (!(mParent instanceof ViewGroup) || !((ViewGroup)mParent).isViewTransitioning(this));
  }
  public void invalidate(  Rect dirty){
    final int scrollX=mScrollX;
    final int scrollY=mScrollY;
    invalidateInternal(dirty.left - scrollX,dirty.top - scrollY,dirty.right - scrollX,dirty.bottom - scrollY,true,false);
  }
  public void invalidate(  int l,  int t,  int r,  int b){
    final int scrollX=mScrollX;
    final int scrollY=mScrollY;
    invalidateInternal(l - scrollX,t - scrollY,r - scrollX,b - scrollY,true,false);
  }
  public void invalidate(){
    invalidate(true);
  }
  public void invalidate(  boolean invalidateCache){
    invalidateInternal(0,0,mRight - mLeft,mBottom - mTop,invalidateCache,true);
  }
  void invalidateInternal(  int l,  int t,  int r,  int b,  boolean invalidateCache,  boolean fullInvalidate){
    if (mGhostView != null) {
      mGhostView.invalidate(true);
      return;
    }
    if (skipInvalidate()) {
      return;
    }
    if ((mPrivateFlags & (PFLAG_DRAWN | PFLAG_HAS_BOUNDS)) == (PFLAG_DRAWN | PFLAG_HAS_BOUNDS) || (invalidateCache && (mPrivateFlags & PFLAG_DRAWING_CACHE_VALID) == PFLAG_DRAWING_CACHE_VALID) || (mPrivateFlags & PFLAG_INVALIDATED) != PFLAG_INVALIDATED || (fullInvalidate && isOpaque() != mLastIsOpaque)) {
      if (fullInvalidate) {
        mLastIsOpaque=isOpaque();
        mPrivateFlags&=~PFLAG_DRAWN;
      }
      mPrivateFlags|=PFLAG_DIRTY;
      if (invalidateCache) {
        mPrivateFlags|=PFLAG_INVALIDATED;
        mPrivateFlags&=~PFLAG_DRAWING_CACHE_VALID;
      }
      final AttachInfo ai=mAttachInfo;
      final ViewParent p=mParent;
      if (p != null && ai != null && l < r && t < b) {
        final Rect damage=ai.mTmpInvalRect;
        damage.set(l,t,r,b);
        p.invalidateChild(this,damage);
      }
      if (mBackground != null && mBackground.isProjected()) {
        final View receiver=getProjectionReceiver();
        if (receiver != null) {
          receiver.damageInParent();
        }
      }
    }
  }
  private View getProjectionReceiver(){
    ViewParent p=getParent();
    while (p != null && p instanceof View) {
      final View v=(View)p;
      if (v.isProjectionReceiver()) {
        return v;
      }
      p=p.getParent();
    }
    return null;
  }
  private boolean isProjectionReceiver(){
    return mBackground != null;
  }
  void invalidateViewProperty(  boolean invalidateParent,  boolean forceRedraw){
    if (!isHardwareAccelerated() || !mRenderNode.isValid() || (mPrivateFlags & PFLAG_DRAW_ANIMATION) != 0) {
      if (invalidateParent) {
        invalidateParentCaches();
      }
      if (forceRedraw) {
        mPrivateFlags|=PFLAG_DRAWN;
      }
      invalidate(false);
    }
 else {
      damageInParent();
    }
  }
  protected void invalidateParentCaches(){
    if (mParent instanceof View) {
      ((View)mParent).mPrivateFlags|=PFLAG_INVALIDATED;
    }
  }
  protected void invalidateParentIfNeeded(){
    if (isHardwareAccelerated() && mParent instanceof View) {
      ((View)mParent).invalidate(true);
    }
  }
  protected void invalidateParentIfNeededAndWasQuickRejected(){
    if ((mPrivateFlags2 & PFLAG2_VIEW_QUICK_REJECTED) != 0) {
      invalidateParentIfNeeded();
    }
  }
  public boolean isOpaque(){
    return (mPrivateFlags & PFLAG_OPAQUE_MASK) == PFLAG_OPAQUE_MASK && getFinalAlpha() >= 1.0f;
  }
  public boolean post(  Runnable action){
    final AttachInfo attachInfo=mAttachInfo;
    if (attachInfo != null) {
      return attachInfo.mHandler.post(action);
    }
    getRunQueue().post(action);
    return true;
  }
  public boolean removeCallbacks(  Runnable action){
    if (action != null) {
      final AttachInfo attachInfo=mAttachInfo;
      if (attachInfo != null) {
        attachInfo.mHandler.removeCallbacks(action);
        //attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(Choreographer.CALLBACK_ANIMATION,action,null);
      }
      getRunQueue().removeCallbacks(action);
    }
    return true;
  }
  protected int computeHorizontalScrollRange(){
    return getWidth();
  }
  protected int computeHorizontalScrollOffset(){
    return mScrollX;
  }
  protected int computeHorizontalScrollExtent(){
    return getWidth();
  }
  protected int computeVerticalScrollRange(){
    return getHeight();
  }
  protected int computeVerticalScrollOffset(){
    return mScrollY;
  }
  protected int computeVerticalScrollExtent(){
    return getHeight();
  }
  public boolean canScrollHorizontally(  int direction){
    final int offset=computeHorizontalScrollOffset();
    final int range=computeHorizontalScrollRange() - computeHorizontalScrollExtent();
    if (range == 0)     return false;
    if (direction < 0) {
      return offset > 0;
    }
 else {
      return offset < range - 1;
    }
  }
  public boolean canScrollVertically(  int direction){
    final int offset=computeVerticalScrollOffset();
    final int range=computeVerticalScrollRange() - computeVerticalScrollExtent();
    if (range == 0)     return false;
    if (direction < 0) {
      return offset > 0;
    }
 else {
      return offset < range - 1;
    }
  }
  void assignParent(  ViewParent parent){
    if (mParent == null) {
      mParent=parent;
    }
 else     if (parent == null) {
      mParent=null;
    }
 else {
      throw new RuntimeException("view " + this + " being added, but"+ " it already has a parent");
    }
  }
  public boolean resolveRtlPropertiesIfNeeded(){
    if (!needRtlPropertiesResolution())     return false;
    if (!isLayoutDirectionResolved()) {
      resolveLayoutDirection();
      resolveLayoutParams();
    }
    if (!isTextDirectionResolved()) {
      resolveTextDirection();
    }
    if (!isTextAlignmentResolved()) {
      resolveTextAlignment();
    }
    if (!areDrawablesResolved()) {
      resolveDrawables();
    }
    if (!isPaddingResolved()) {
      resolvePadding();
    }
    onRtlPropertiesChanged(getLayoutDirection());
    return true;
  }
  public void resetRtlProperties(){
    resetResolvedLayoutDirection();
    resetResolvedTextDirection();
    resetResolvedTextAlignment();
    resetResolvedPadding();
    resetResolvedDrawables();
  }
  private boolean needRtlPropertiesResolution(){
    return (mPrivateFlags2 & ALL_RTL_PROPERTIES_RESOLVED) != ALL_RTL_PROPERTIES_RESOLVED;
  }
  public void onRtlPropertiesChanged(  int layoutDirection){
  }
  public boolean resolveLayoutDirection(){
    mPrivateFlags2&=~PFLAG2_LAYOUT_DIRECTION_RESOLVED_MASK;
    if (hasRtlSupport()) {
switch ((mPrivateFlags2 & PFLAG2_LAYOUT_DIRECTION_MASK) >> PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT) {
case LAYOUT_DIRECTION_INHERIT:
        if (!canResolveLayoutDirection())         return false;
      try {
        if (!mParent.isLayoutDirectionResolved())         return false;
        if (mParent.getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
          mPrivateFlags2|=PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL;
        }
      }
 catch (      AbstractMethodError e) {
        Log.e(VIEW_LOG_TAG,mParent.getClass().getSimpleName() + " does not fully implement ViewParent",e);
      }
    break;
case LAYOUT_DIRECTION_RTL:
  mPrivateFlags2|=PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL;
break;
case LAYOUT_DIRECTION_LOCALE:
if ((LAYOUT_DIRECTION_RTL == TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()))) {
mPrivateFlags2|=PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL;
}
break;
default :
}
}
mPrivateFlags2|=PFLAG2_LAYOUT_DIRECTION_RESOLVED;
return true;
}
public boolean canResolveLayoutDirection(){
switch (getRawLayoutDirection()) {
case LAYOUT_DIRECTION_INHERIT:
if (mParent != null) {
try {
return mParent.canResolveLayoutDirection();
}
 catch (AbstractMethodError e) {
Log.e(VIEW_LOG_TAG,mParent.getClass().getSimpleName() + " does not fully implement ViewParent",e);
}
}
return false;
default :
return true;
}
}
public void resetResolvedLayoutDirection(){
mPrivateFlags2&=~PFLAG2_LAYOUT_DIRECTION_RESOLVED_MASK;
}
public boolean isLayoutDirectionInherited(){
return (getRawLayoutDirection() == LAYOUT_DIRECTION_INHERIT);
}
public boolean isLayoutDirectionResolved(){
return (mPrivateFlags2 & PFLAG2_LAYOUT_DIRECTION_RESOLVED) == PFLAG2_LAYOUT_DIRECTION_RESOLVED;
}
boolean isPaddingResolved(){
return (mPrivateFlags2 & PFLAG2_PADDING_RESOLVED) == PFLAG2_PADDING_RESOLVED;
}
public void resolvePadding(){
final int resolvedLayoutDirection=getLayoutDirection();
if (!isRtlCompatibilityMode()) {
if (mBackground != null && (!mLeftPaddingDefined || !mRightPaddingDefined)) {
Rect padding=sThreadLocal.get();
if (padding == null) {
padding=new Rect();
sThreadLocal.set(padding);
}
mBackground.getPadding(padding);
if (!mLeftPaddingDefined) {
mUserPaddingLeftInitial=padding.left;
}
if (!mRightPaddingDefined) {
mUserPaddingRightInitial=padding.right;
}
}
switch (resolvedLayoutDirection) {
case LAYOUT_DIRECTION_RTL:
if (mUserPaddingStart != UNDEFINED_PADDING) {
mUserPaddingRight=mUserPaddingStart;
}
 else {
mUserPaddingRight=mUserPaddingRightInitial;
}
if (mUserPaddingEnd != UNDEFINED_PADDING) {
mUserPaddingLeft=mUserPaddingEnd;
}
 else {
mUserPaddingLeft=mUserPaddingLeftInitial;
}
break;
case LAYOUT_DIRECTION_LTR:
default :
if (mUserPaddingStart != UNDEFINED_PADDING) {
mUserPaddingLeft=mUserPaddingStart;
}
 else {
mUserPaddingLeft=mUserPaddingLeftInitial;
}
if (mUserPaddingEnd != UNDEFINED_PADDING) {
mUserPaddingRight=mUserPaddingEnd;
}
 else {
mUserPaddingRight=mUserPaddingRightInitial;
}
}
mUserPaddingBottom=(mUserPaddingBottom >= 0) ? mUserPaddingBottom : mPaddingBottom;
}
internalSetPadding(mUserPaddingLeft,mPaddingTop,mUserPaddingRight,mUserPaddingBottom);
onRtlPropertiesChanged(resolvedLayoutDirection);
mPrivateFlags2|=PFLAG2_PADDING_RESOLVED;
}
public void resetResolvedPadding(){
resetResolvedPaddingInternal();
}
void resetResolvedPaddingInternal(){
mPrivateFlags2&=~PFLAG2_PADDING_RESOLVED;
}
protected void onDetachedFromWindow(){
}
void invalidateInheritedLayoutMode(int layoutModeOfRoot){
}
int combineVisibility(int vis1,int vis2){
return Math.max(vis1,vis2);
}
void dispatchAttachedToWindow(AttachInfo info,int visibility){
mAttachInfo=info;
if (false) {
//mOverlay.  getOverlayView().dispatchAttachedToWindow(info,visibility);
}
mWindowAttachCount++;
mPrivateFlags|=PFLAG_DRAWABLE_STATE_DIRTY;
if (mFloatingTreeObserver != null) {
info.mTreeObserver.merge(mFloatingTreeObserver);
mFloatingTreeObserver=null;
}
registerPendingFrameMetricsObservers();
if ((mPrivateFlags & PFLAG_SCROLL_CONTAINER) != 0) {
mAttachInfo.mScrollContainers.add(this);
mPrivateFlags|=PFLAG_SCROLL_CONTAINER_ADDED;
}
if (false) {
//mRunQueue.executeActions(info.mHandler);
//mRunQueue=null;
}
performCollectViewAttributes(mAttachInfo,visibility);
onAttachedToWindow();
ListenerInfo li=mListenerInfo;
final CopyOnWriteArrayList<OnAttachStateChangeListener> listeners=li != null ? li.mOnAttachStateChangeListeners : null;
if (listeners != null && listeners.size() > 0) {
for (OnAttachStateChangeListener listener : listeners) {
listener.onViewAttachedToWindow(this);
}
}
int vis=info.mWindowVisibility;
if (vis != GONE) {
onWindowVisibilityChanged(vis);
if (isShown()) {
onVisibilityAggregated(vis == VISIBLE);
}
}
onVisibilityChanged(this,visibility);
if ((mPrivateFlags & PFLAG_DRAWABLE_STATE_DIRTY) != 0) {
refreshDrawableState();
}
needGlobalAttributesUpdate(false);
notifyEnterOrExitForAutoFillIfNeeded(true);
}
void dispatchDetachedFromWindow(){
AttachInfo info=mAttachInfo;
if (info != null) {
int vis=info.mWindowVisibility;
if (vis != GONE) {
onWindowVisibilityChanged(GONE);
if (isShown()) {
onVisibilityAggregated(false);
}
}
}
onDetachedFromWindow();
onDetachedFromWindowInternal();
//InputMethodManager imm=InputMethodManager.peekInstance();
if (false) {
//imm.onViewDetachedFromWindow(this);
}
ListenerInfo li=mListenerInfo;
final CopyOnWriteArrayList<OnAttachStateChangeListener> listeners=li != null ? li.mOnAttachStateChangeListeners : null;
if (listeners != null && listeners.size() > 0) {
for (OnAttachStateChangeListener listener : listeners) {
listener.onViewDetachedFromWindow(this);
}
}
if ((mPrivateFlags & PFLAG_SCROLL_CONTAINER_ADDED) != 0) {
mAttachInfo.mScrollContainers.remove(this);
mPrivateFlags&=~PFLAG_SCROLL_CONTAINER_ADDED;
}
mAttachInfo=null;
if (false) {
//mOverlay.  getOverlayView().dispatchDetachedFromWindow();
}
notifyEnterOrExitForAutoFillIfNeeded(false);
}
public void setDuplicateParentStateEnabled(boolean enabled){
setFlags(enabled ? DUPLICATE_PARENT_STATE : 0,DUPLICATE_PARENT_STATE);
}
public boolean isDuplicateParentStateEnabled(){
return (mViewFlags & DUPLICATE_PARENT_STATE) == DUPLICATE_PARENT_STATE;
}
public boolean isLayoutRequested(){
return (mPrivateFlags & PFLAG_FORCE_LAYOUT) == PFLAG_FORCE_LAYOUT;
}
public static boolean isLayoutModeOptical(Object o){
return o instanceof ViewGroup && ((ViewGroup)o).isLayoutModeOptical();
}
private boolean setOpticalFrame(int left,int top,int right,int bottom){
Insets parentInsets=mParent instanceof View ? ((View)mParent).getOpticalInsets() : Insets.NONE;
Insets childInsets=getOpticalInsets();
return setFrame(left + parentInsets.left - childInsets.left,top + parentInsets.top - childInsets.top,right + parentInsets.left + childInsets.right,bottom + parentInsets.top + childInsets.bottom);
}
public void layout(int l,int t,int r,int b){
if ((mPrivateFlags3 & PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT) != 0) {
onMeasure(mOldWidthMeasureSpec,mOldHeightMeasureSpec);
mPrivateFlags3&=~PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT;
}
int oldL=mLeft;
int oldT=mTop;
int oldB=mBottom;
int oldR=mRight;
boolean changed=isLayoutModeOptical(mParent) ? setOpticalFrame(l,t,r,b) : setFrame(l,t,r,b);
if (changed || (mPrivateFlags & PFLAG_LAYOUT_REQUIRED) == PFLAG_LAYOUT_REQUIRED) {
onLayout(changed,l,t,r,b);
if (shouldDrawRoundScrollbar()) {
if (mRoundScrollbarRenderer == null) {
mRoundScrollbarRenderer=new RoundScrollbarRenderer(this);
}
}
 else {
mRoundScrollbarRenderer=null;
}
mPrivateFlags&=~PFLAG_LAYOUT_REQUIRED;
ListenerInfo li=mListenerInfo;
if (li != null && li.mOnLayoutChangeListeners != null) {
ArrayList<OnLayoutChangeListener> listenersCopy=(ArrayList<OnLayoutChangeListener>)li.mOnLayoutChangeListeners.clone();
int numListeners=listenersCopy.size();
for (int i=0; i < numListeners; ++i) {
listenersCopy.get(i).onLayoutChange(this,l,t,r,b,oldL,oldT,oldR,oldB);
}
}
}
final boolean wasLayoutValid=isLayoutValid();
mPrivateFlags&=~PFLAG_FORCE_LAYOUT;
mPrivateFlags3|=PFLAG3_IS_LAID_OUT;
if (!wasLayoutValid && isFocused()) {
mPrivateFlags&=~PFLAG_WANTS_FOCUS;
if (canTakeFocus()) {
clearParentsWantFocus();
}
 else if (getViewRootImpl() == null || !getViewRootImpl().isInLayout()) {
clearFocusInternal(null,true,false);
clearParentsWantFocus();
}
 else if (!hasParentWantsFocus()) {
clearFocusInternal(null,true,false);
}
}
 else if ((mPrivateFlags & PFLAG_WANTS_FOCUS) != 0) {
mPrivateFlags&=~PFLAG_WANTS_FOCUS;
View focused=findFocus();
if (focused != null) {
if (!restoreDefaultFocus() && !hasParentWantsFocus()) {
focused.clearFocusInternal(null,true,false);
}
}
}
if ((mPrivateFlags3 & PFLAG3_NOTIFY_AUTOFILL_ENTER_ON_LAYOUT) != 0) {
mPrivateFlags3&=~PFLAG3_NOTIFY_AUTOFILL_ENTER_ON_LAYOUT;
notifyEnterOrExitForAutoFillIfNeeded(true);
}
}
private boolean hasParentWantsFocus(){
ViewParent parent=mParent;
while (parent instanceof ViewGroup) {
ViewGroup pv=(ViewGroup)parent;
if ((pv.mPrivateFlags & PFLAG_WANTS_FOCUS) != 0) {
return true;
}
parent=pv.mParent;
}
return false;
}
protected void onLayout(boolean changed,int left,int top,int right,int bottom){
}
protected boolean setFrame(int left,int top,int right,int bottom){
boolean changed=false;
if (DBG) {
Log.d(VIEW_LOG_TAG,this + " View.setFrame(" + left+ ","+ top+ ","+ right+ ","+ bottom+ ")");
}
if (mLeft != left || mRight != right || mTop != top || mBottom != bottom) {
changed=true;
int drawn=mPrivateFlags & PFLAG_DRAWN;
int oldWidth=mRight - mLeft;
int oldHeight=mBottom - mTop;
int newWidth=right - left;
int newHeight=bottom - top;
boolean sizeChanged=(newWidth != oldWidth) || (newHeight != oldHeight);
invalidate(sizeChanged);
mLeft=left;
mTop=top;
mRight=right;
mBottom=bottom;
mRenderNode.setLeftTopRightBottom(mLeft,mTop,mRight,mBottom);
mPrivateFlags|=PFLAG_HAS_BOUNDS;
if (sizeChanged) {
sizeChange(newWidth,newHeight,oldWidth,oldHeight);
}
if ((mViewFlags & VISIBILITY_MASK) == VISIBLE || mGhostView != null) {
mPrivateFlags|=PFLAG_DRAWN;
invalidate(sizeChanged);
invalidateParentCaches();
}
mPrivateFlags|=drawn;
mBackgroundSizeChanged=true;
mDefaultFocusHighlightSizeChanged=true;
if (mForegroundInfo != null) {
mForegroundInfo.mBoundsChanged=true;
}
notifySubtreeAccessibilityStateChangedIfNeeded();
}
return changed;
}
protected void resolveDrawables(){
if (!isLayoutDirectionResolved() && getRawLayoutDirection() == View.LAYOUT_DIRECTION_INHERIT) {
return;
}
final int layoutDirection=isLayoutDirectionResolved() ? getLayoutDirection() : getRawLayoutDirection();
if (mBackground != null) {
mBackground.setLayoutDirection(layoutDirection);
}
if (mForegroundInfo != null && mForegroundInfo.mDrawable != null) {
mForegroundInfo.mDrawable.setLayoutDirection(layoutDirection);
}
if (mDefaultFocusHighlight != null) {
mDefaultFocusHighlight.setLayoutDirection(layoutDirection);
}
mPrivateFlags2|=PFLAG2_DRAWABLE_RESOLVED;
onResolveDrawables(layoutDirection);
}
boolean areDrawablesResolved(){
return (mPrivateFlags2 & PFLAG2_DRAWABLE_RESOLVED) == PFLAG2_DRAWABLE_RESOLVED;
}
protected void resetResolvedDrawables(){
resetResolvedDrawablesInternal();
}
void resetResolvedDrawablesInternal(){
mPrivateFlags2&=~PFLAG2_DRAWABLE_RESOLVED;
}
public void refreshDrawableState(){
mPrivateFlags|=PFLAG_DRAWABLE_STATE_DIRTY;
drawableStateChanged();
ViewParent parent=mParent;
if (parent != null) {
parent.childDrawableStateChanged(this);
}
}
public final int[] getDrawableState(){
if ((mDrawableState != null) && ((mPrivateFlags & PFLAG_DRAWABLE_STATE_DIRTY) == 0)) {
return mDrawableState;
}
 else {
mDrawableState=onCreateDrawableState(0);
mPrivateFlags&=~PFLAG_DRAWABLE_STATE_DIRTY;
return mDrawableState;
}
}
protected int[] onCreateDrawableState(int extraSpace){
if ((mViewFlags & DUPLICATE_PARENT_STATE) == DUPLICATE_PARENT_STATE && mParent instanceof View) {
return ((View)mParent).onCreateDrawableState(extraSpace);
}
int[] drawableState;
int privateFlags=mPrivateFlags;
int viewStateIndex=0;
if ((privateFlags & PFLAG_PRESSED) != 0) viewStateIndex|=StateSet.VIEW_STATE_PRESSED;
if ((mViewFlags & ENABLED_MASK) == ENABLED) viewStateIndex|=StateSet.VIEW_STATE_ENABLED;
if (isFocused()) viewStateIndex|=StateSet.VIEW_STATE_FOCUSED;
if ((privateFlags & PFLAG_SELECTED) != 0) viewStateIndex|=StateSet.VIEW_STATE_SELECTED;
if (hasWindowFocus()) viewStateIndex|=StateSet.VIEW_STATE_WINDOW_FOCUSED;
if ((privateFlags & PFLAG_ACTIVATED) != 0) viewStateIndex|=StateSet.VIEW_STATE_ACTIVATED;
if (mAttachInfo != null && mAttachInfo.mHardwareAccelerationRequested && ThreadedRenderer.isAvailable()) {
viewStateIndex|=StateSet.VIEW_STATE_ACCELERATED;
}
if ((privateFlags & PFLAG_HOVERED) != 0) viewStateIndex|=StateSet.VIEW_STATE_HOVERED;
final int privateFlags2=mPrivateFlags2;
if ((privateFlags2 & PFLAG2_DRAG_CAN_ACCEPT) != 0) {
viewStateIndex|=StateSet.VIEW_STATE_DRAG_CAN_ACCEPT;
}
if ((privateFlags2 & PFLAG2_DRAG_HOVERED) != 0) {
viewStateIndex|=StateSet.VIEW_STATE_DRAG_HOVERED;
}
drawableState=StateSet.get(viewStateIndex);
if (false) {
Log.i("View","drawableStateIndex=" + viewStateIndex);
Log.i("View",toString() + " pressed=" + ((privateFlags & PFLAG_PRESSED) != 0)+ " en="+ ((mViewFlags & ENABLED_MASK) == ENABLED)+ " fo="+ hasFocus()+ " sl="+ ((privateFlags & PFLAG_SELECTED) != 0)+ " wf="+ hasWindowFocus()+ ": "+ Arrays.toString(drawableState));
}
if (extraSpace == 0) {
return drawableState;
}
final int[] fullState;
if (drawableState != null) {
fullState=new int[drawableState.length + extraSpace];
System.arraycopy(drawableState,0,fullState,0,drawableState.length);
}
 else {
fullState=new int[extraSpace];
}
return fullState;
}
protected static int[] mergeDrawableStates(int[] baseState,int[] additionalState){
final int N=baseState.length;
int i=N - 1;
while (i >= 0 && baseState[i] == 0) {
i--;
}
System.arraycopy(additionalState,0,baseState,i + 1,additionalState.length);
return baseState;
}
public void setBackgroundTintList(ColorStateList tint){
if (mBackgroundTint == null) {
mBackgroundTint=new TintInfo();
}
mBackgroundTint.mTintList=tint;
mBackgroundTint.mHasTintList=true;
applyBackgroundTint();
}
public ColorStateList getBackgroundTintList(){
return mBackgroundTint != null ? mBackgroundTint.mTintList : null;
}
public Drawable getForeground(){
return mForegroundInfo != null ? mForegroundInfo.mDrawable : null;
}
public void setForeground(Drawable foreground){
if (mForegroundInfo == null) {
if (foreground == null) {
return;
}
mForegroundInfo=new ForegroundInfo();
}
if (foreground == mForegroundInfo.mDrawable) {
return;
}
if (mForegroundInfo.mDrawable != null) {
if (isAttachedToWindow()) {
mForegroundInfo.mDrawable.setVisible(false,false);
}
mForegroundInfo.mDrawable.setCallback(null);
unscheduleDrawable(mForegroundInfo.mDrawable);
}
mForegroundInfo.mDrawable=foreground;
mForegroundInfo.mBoundsChanged=true;
if (foreground != null) {
if ((mPrivateFlags & PFLAG_SKIP_DRAW) != 0) {
mPrivateFlags&=~PFLAG_SKIP_DRAW;
}
foreground.setLayoutDirection(getLayoutDirection());
if (foreground.isStateful()) {
foreground.setState(getDrawableState());
}
applyForegroundTint();
if (isAttachedToWindow()) {
foreground.setVisible(getWindowVisibility() == VISIBLE && isShown(),false);
}
foreground.setCallback(this);
}
 else if ((mViewFlags & WILL_NOT_DRAW) != 0 && mBackground == null && (mDefaultFocusHighlight == null)) {
mPrivateFlags|=PFLAG_SKIP_DRAW;
}
requestLayout();
invalidate();
}
public boolean isForegroundInsidePadding(){
return mForegroundInfo != null ? mForegroundInfo.mInsidePadding : true;
}
public int getForegroundGravity(){
return mForegroundInfo != null ? mForegroundInfo.mGravity : Gravity.START | Gravity.TOP;
}
public void setForegroundGravity(int gravity){
if (mForegroundInfo == null) {
mForegroundInfo=new ForegroundInfo();
}
if (mForegroundInfo.mGravity != gravity) {
if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
gravity|=Gravity.START;
}
if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
gravity|=Gravity.TOP;
}
mForegroundInfo.mGravity=gravity;
requestLayout();
}
}
public void setForegroundTintList(ColorStateList tint){
if (mForegroundInfo == null) {
mForegroundInfo=new ForegroundInfo();
}
if (mForegroundInfo.mTintInfo == null) {
mForegroundInfo.mTintInfo=new TintInfo();
}
mForegroundInfo.mTintInfo.mTintList=tint;
mForegroundInfo.mTintInfo.mHasTintList=true;
applyForegroundTint();
}
public ColorStateList getForegroundTintList(){
return mForegroundInfo != null && mForegroundInfo.mTintInfo != null ? mForegroundInfo.mTintInfo.mTintList : null;
}
public void setPadding(int left,int top,int right,int bottom){
resetResolvedPaddingInternal();
mUserPaddingStart=UNDEFINED_PADDING;
mUserPaddingEnd=UNDEFINED_PADDING;
mUserPaddingLeftInitial=left;
mUserPaddingRightInitial=right;
mLeftPaddingDefined=true;
mRightPaddingDefined=true;
internalSetPadding(left,top,right,bottom);
}
protected void internalSetPadding(int left,int top,int right,int bottom){
mUserPaddingLeft=left;
mUserPaddingRight=right;
mUserPaddingBottom=bottom;
final int viewFlags=mViewFlags;
boolean changed=false;
if ((viewFlags & (SCROLLBARS_VERTICAL | SCROLLBARS_HORIZONTAL)) != 0) {
if ((viewFlags & SCROLLBARS_VERTICAL) != 0) {
final int offset=(viewFlags & SCROLLBARS_INSET_MASK) == 0 ? 0 : getVerticalScrollbarWidth();
switch (mVerticalScrollbarPosition) {
case SCROLLBAR_POSITION_DEFAULT:
if (isLayoutRtl()) {
left+=offset;
}
 else {
right+=offset;
}
break;
case SCROLLBAR_POSITION_RIGHT:
right+=offset;
break;
case SCROLLBAR_POSITION_LEFT:
left+=offset;
break;
}
}
if ((viewFlags & SCROLLBARS_HORIZONTAL) != 0) {
bottom+=(viewFlags & SCROLLBARS_INSET_MASK) == 0 ? 0 : getHorizontalScrollbarHeight();
}
}
if (mPaddingLeft != left) {
changed=true;
mPaddingLeft=left;
}
if (mPaddingTop != top) {
changed=true;
mPaddingTop=top;
}
if (mPaddingRight != right) {
changed=true;
mPaddingRight=right;
}
if (mPaddingBottom != bottom) {
changed=true;
mPaddingBottom=bottom;
}
if (changed) {
requestLayout();
invalidateOutline();
}
}
public void setPaddingRelative(int start,int top,int end,int bottom){
resetResolvedPaddingInternal();
mUserPaddingStart=start;
mUserPaddingEnd=end;
mLeftPaddingDefined=true;
mRightPaddingDefined=true;
switch (getLayoutDirection()) {
case LAYOUT_DIRECTION_RTL:
mUserPaddingLeftInitial=end;
mUserPaddingRightInitial=start;
internalSetPadding(end,top,start,bottom);
break;
case LAYOUT_DIRECTION_LTR:
default :
mUserPaddingLeftInitial=start;
mUserPaddingRightInitial=end;
internalSetPadding(start,top,end,bottom);
}
}
public int getPaddingTop(){
return mPaddingTop;
}
public int getPaddingBottom(){
return mPaddingBottom;
}
public int getPaddingLeft(){
if (!isPaddingResolved()) {
resolvePadding();
}
return mPaddingLeft;
}
public int getPaddingStart(){
if (!isPaddingResolved()) {
resolvePadding();
}
return (getLayoutDirection() == LAYOUT_DIRECTION_RTL) ? mPaddingRight : mPaddingLeft;
}
public int getPaddingRight(){
if (!isPaddingResolved()) {
resolvePadding();
}
return mPaddingRight;
}
public int getPaddingEnd(){
if (!isPaddingResolved()) {
resolvePadding();
}
return (getLayoutDirection() == LAYOUT_DIRECTION_RTL) ? mPaddingLeft : mPaddingRight;
}
Insets computeOpticalInsets(){
return (mBackground == null) ? Insets.NONE : mBackground.getOpticalInsets();
}
public Insets getOpticalInsets(){
if (mLayoutInsets == null) {
mLayoutInsets=computeOpticalInsets();
}
return mLayoutInsets;
}
public void setSelected(boolean selected){
if (((mPrivateFlags & PFLAG_SELECTED) != 0) != selected) {
mPrivateFlags=(mPrivateFlags & ~PFLAG_SELECTED) | (selected ? PFLAG_SELECTED : 0);
if (!selected) resetPressedState();
invalidate(true);
refreshDrawableState();
dispatchSetSelected(selected);
if (selected) {
sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
}
 else {
notifyViewAccessibilityStateChangedIfNeeded(AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED);
}
}
}
protected void dispatchSetSelected(boolean selected){
}
public boolean isSelected(){
return (mPrivateFlags & PFLAG_SELECTED) != 0;
}
public void setActivated(boolean activated){
if (((mPrivateFlags & PFLAG_ACTIVATED) != 0) != activated) {
mPrivateFlags=(mPrivateFlags & ~PFLAG_ACTIVATED) | (activated ? PFLAG_ACTIVATED : 0);
invalidate(true);
refreshDrawableState();
dispatchSetActivated(activated);
}
}
protected void dispatchSetActivated(boolean activated){
}
public boolean isActivated(){
return (mPrivateFlags & PFLAG_ACTIVATED) != 0;
}
public ViewTreeObserver getViewTreeObserver(){
if (mAttachInfo != null) {
return mAttachInfo.mTreeObserver;
}
if (mFloatingTreeObserver == null) {
mFloatingTreeObserver=new ViewTreeObserver(mContext);
}
return mFloatingTreeObserver;
}
public View getRootView(){
if (mAttachInfo != null) {
final View v=mAttachInfo.mRootView;
if (v != null) {
return v;
}
}
View parent=this;
while (parent.mParent != null && parent.mParent instanceof View) {
parent=(View)parent.mParent;
}
return parent;
}
protected <T extends View>T findViewTraversal(int id){
if (id == mID) {
return (T)this;
}
return null;
}
public final <T extends View>T findViewById(int id){
if (id == NO_ID) {
return null;
}
return findViewTraversal(id);
}
public Object getTag(){
return mTag;
}
public void setTag(final Object tag){
mTag=tag;
}
public Object getTag(int key){
if (mKeyedTags != null) return mKeyedTags.get(key);
return null;
}
public void setTag(int key,final Object tag){
if ((key >>> 24) < 2) {
throw new IllegalArgumentException("The key must be an application-specific " + "resource id.");
}
setKeyedTag(key,tag);
}
private void setKeyedTag(int key,Object tag){
if (mKeyedTags == null) {
mKeyedTags=new SparseArray<Object>(2);
}
mKeyedTags.put(key,tag);
}
public int getBaseline(){
return -1;
}
public void requestLayout(){
if (mMeasureCache != null) mMeasureCache.clear();
if (mAttachInfo != null && mAttachInfo.mViewRequestingLayout == null) {
ViewRootImpl viewRoot=getViewRootImpl();
if (viewRoot != null && viewRoot.isInLayout()) {
if (!viewRoot.requestLayoutDuringLayout(this)) {
return;
}
}
mAttachInfo.mViewRequestingLayout=this;
}
mPrivateFlags|=PFLAG_FORCE_LAYOUT;
mPrivateFlags|=PFLAG_INVALIDATED;
if (mParent != null && !mParent.isLayoutRequested()) {
mParent.requestLayout();
}
if (mAttachInfo != null && mAttachInfo.mViewRequestingLayout == this) {
mAttachInfo.mViewRequestingLayout=null;
}
}
public void forceLayout(){
if (mMeasureCache != null) mMeasureCache.clear();
mPrivateFlags|=PFLAG_FORCE_LAYOUT;
mPrivateFlags|=PFLAG_INVALIDATED;
}
public final void measure(int widthMeasureSpec,int heightMeasureSpec){
boolean optical=isLayoutModeOptical(this);
if (optical != isLayoutModeOptical(mParent)) {
Insets insets=getOpticalInsets();
int oWidth=insets.left + insets.right;
int oHeight=insets.top + insets.bottom;
widthMeasureSpec=MeasureSpec.adjust(widthMeasureSpec,optical ? -oWidth : oWidth);
heightMeasureSpec=MeasureSpec.adjust(heightMeasureSpec,optical ? -oHeight : oHeight);
}
long key=(long)widthMeasureSpec << 32 | (long)heightMeasureSpec & 0xffffffffL;
if (mMeasureCache == null) mMeasureCache=new LongSparseLongArray(2);
final boolean forceLayout=(mPrivateFlags & PFLAG_FORCE_LAYOUT) == PFLAG_FORCE_LAYOUT;
final boolean specChanged=widthMeasureSpec != mOldWidthMeasureSpec || heightMeasureSpec != mOldHeightMeasureSpec;
final boolean isSpecExactly=MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY;
final boolean matchesSpecSize=getMeasuredWidth() == MeasureSpec.getSize(widthMeasureSpec) && getMeasuredHeight() == MeasureSpec.getSize(heightMeasureSpec);
final boolean needsLayout=specChanged && (sAlwaysRemeasureExactly || !isSpecExactly || !matchesSpecSize);
if (forceLayout || needsLayout) {
mPrivateFlags&=~PFLAG_MEASURED_DIMENSION_SET;
resolveRtlPropertiesIfNeeded();
int cacheIndex=forceLayout ? -1 : mMeasureCache.indexOfKey(key);
if (cacheIndex < 0 || sIgnoreMeasureCache) {
onMeasure(widthMeasureSpec,heightMeasureSpec);
mPrivateFlags3&=~PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT;
}
 else {
long value=mMeasureCache.valueAt(cacheIndex);
setMeasuredDimensionRaw((int)(value >> 32),(int)value);
mPrivateFlags3|=PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT;
}
if ((mPrivateFlags & PFLAG_MEASURED_DIMENSION_SET) != PFLAG_MEASURED_DIMENSION_SET) {
throw new IllegalStateException("View with id " + getId() + ": "+ getClass().getName()+ "#onMeasure() did not set the"+ " measured dimension by calling"+ " setMeasuredDimension()");
}
mPrivateFlags|=PFLAG_LAYOUT_REQUIRED;
}
mOldWidthMeasureSpec=widthMeasureSpec;
mOldHeightMeasureSpec=heightMeasureSpec;
mMeasureCache.put(key,((long)mMeasuredWidth) << 32 | (long)mMeasuredHeight & 0xffffffffL);
}
protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec));
}
protected final void setMeasuredDimension(int measuredWidth,int measuredHeight){
boolean optical=isLayoutModeOptical(this);
if (optical != isLayoutModeOptical(mParent)) {
Insets insets=getOpticalInsets();
int opticalWidth=insets.left + insets.right;
int opticalHeight=insets.top + insets.bottom;
measuredWidth+=optical ? opticalWidth : -opticalWidth;
measuredHeight+=optical ? opticalHeight : -opticalHeight;
}
setMeasuredDimensionRaw(measuredWidth,measuredHeight);
}
private void setMeasuredDimensionRaw(int measuredWidth,int measuredHeight){
mMeasuredWidth=measuredWidth;
mMeasuredHeight=measuredHeight;
mPrivateFlags|=PFLAG_MEASURED_DIMENSION_SET;
}
public static int combineMeasuredStates(int curState,int newState){
return curState | newState;
}
public static int resolveSize(int size,int measureSpec){
return resolveSizeAndState(size,measureSpec,0) & MEASURED_SIZE_MASK;
}
public static int resolveSizeAndState(int size,int measureSpec,int childMeasuredState){
final int specMode=MeasureSpec.getMode(measureSpec);
final int specSize=MeasureSpec.getSize(measureSpec);
final int result;
switch (specMode) {
case MeasureSpec.AT_MOST:
if (specSize < size) {
result=specSize | MEASURED_STATE_TOO_SMALL;
}
 else {
result=size;
}
break;
case MeasureSpec.EXACTLY:
result=specSize;
break;
case MeasureSpec.UNSPECIFIED:
default :
result=size;
}
return result | (childMeasuredState & MEASURED_STATE_MASK);
}
public static int getDefaultSize(int size,int measureSpec){
int result=size;
int specMode=MeasureSpec.getMode(measureSpec);
int specSize=MeasureSpec.getSize(measureSpec);
switch (specMode) {
case MeasureSpec.UNSPECIFIED:
result=size;
break;
case MeasureSpec.AT_MOST:
case MeasureSpec.EXACTLY:
result=specSize;
break;
}
return result;
}
protected int getSuggestedMinimumHeight(){
return (mBackground == null) ? mMinHeight : max(mMinHeight,mBackground.getMinimumHeight());
}
protected int getSuggestedMinimumWidth(){
return (mBackground == null) ? mMinWidth : max(mMinWidth,mBackground.getMinimumWidth());
}
public int getMinimumHeight(){
return mMinHeight;
}
public void setMinimumHeight(int minHeight){
mMinHeight=minHeight;
requestLayout();
}
public int getMinimumWidth(){
return mMinWidth;
}
public void setMinimumWidth(int minWidth){
mMinWidth=minWidth;
requestLayout();
}
public int getRawTextDirection(){
return (mPrivateFlags2 & PFLAG2_TEXT_DIRECTION_MASK) >> PFLAG2_TEXT_DIRECTION_MASK_SHIFT;
}
public void setTextDirection(int textDirection){
if (getRawTextDirection() != textDirection) {
mPrivateFlags2&=~PFLAG2_TEXT_DIRECTION_MASK;
resetResolvedTextDirection();
mPrivateFlags2|=((textDirection << PFLAG2_TEXT_DIRECTION_MASK_SHIFT) & PFLAG2_TEXT_DIRECTION_MASK);
resolveTextDirection();
onRtlPropertiesChanged(getLayoutDirection());
requestLayout();
invalidate(true);
}
}
public int getTextDirection(){
return (mPrivateFlags2 & PFLAG2_TEXT_DIRECTION_RESOLVED_MASK) >> PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT;
}
public boolean resolveTextDirection(){
mPrivateFlags2&=~(PFLAG2_TEXT_DIRECTION_RESOLVED | PFLAG2_TEXT_DIRECTION_RESOLVED_MASK);
if (hasRtlSupport()) {
final int textDirection=getRawTextDirection();
switch (textDirection) {
case TEXT_DIRECTION_INHERIT:
if (!canResolveTextDirection()) {
mPrivateFlags2|=PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT;
return false;
}
try {
if (!mParent.isTextDirectionResolved()) {
mPrivateFlags2|=PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT;
return false;
}
}
 catch (AbstractMethodError e) {
Log.e(VIEW_LOG_TAG,mParent.getClass().getSimpleName() + " does not fully implement ViewParent",e);
mPrivateFlags2|=PFLAG2_TEXT_DIRECTION_RESOLVED | PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT;
return true;
}
int parentResolvedDirection;
try {
parentResolvedDirection=mParent.getTextDirection();
}
 catch (AbstractMethodError e) {
Log.e(VIEW_LOG_TAG,mParent.getClass().getSimpleName() + " does not fully implement ViewParent",e);
parentResolvedDirection=TEXT_DIRECTION_LTR;
}
switch (parentResolvedDirection) {
case TEXT_DIRECTION_FIRST_STRONG:
case TEXT_DIRECTION_ANY_RTL:
case TEXT_DIRECTION_LTR:
case TEXT_DIRECTION_RTL:
case TEXT_DIRECTION_LOCALE:
case TEXT_DIRECTION_FIRST_STRONG_LTR:
case TEXT_DIRECTION_FIRST_STRONG_RTL:
mPrivateFlags2|=(parentResolvedDirection << PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT);
break;
default :
mPrivateFlags2|=PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT;
}
break;
case TEXT_DIRECTION_FIRST_STRONG:
case TEXT_DIRECTION_ANY_RTL:
case TEXT_DIRECTION_LTR:
case TEXT_DIRECTION_RTL:
case TEXT_DIRECTION_LOCALE:
case TEXT_DIRECTION_FIRST_STRONG_LTR:
case TEXT_DIRECTION_FIRST_STRONG_RTL:
mPrivateFlags2|=(textDirection << PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT);
break;
default :
mPrivateFlags2|=PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT;
}
}
 else {
mPrivateFlags2|=PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT;
}
mPrivateFlags2|=PFLAG2_TEXT_DIRECTION_RESOLVED;
return true;
}
public boolean canResolveTextDirection(){
switch (getRawTextDirection()) {
case TEXT_DIRECTION_INHERIT:
if (mParent != null) {
try {
return mParent.canResolveTextDirection();
}
 catch (AbstractMethodError e) {
Log.e(VIEW_LOG_TAG,mParent.getClass().getSimpleName() + " does not fully implement ViewParent",e);
}
}
return false;
default :
return true;
}
}
public void resetResolvedTextDirection(){
mPrivateFlags2&=~(PFLAG2_TEXT_DIRECTION_RESOLVED | PFLAG2_TEXT_DIRECTION_RESOLVED_MASK);
mPrivateFlags2|=PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT;
}
public boolean isTextDirectionResolved(){
return (mPrivateFlags2 & PFLAG2_TEXT_DIRECTION_RESOLVED) == PFLAG2_TEXT_DIRECTION_RESOLVED;
}
public int getRawTextAlignment(){
return (mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_MASK) >> PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT;
}
public void setTextAlignment(int textAlignment){
if (textAlignment != getRawTextAlignment()) {
mPrivateFlags2&=~PFLAG2_TEXT_ALIGNMENT_MASK;
resetResolvedTextAlignment();
mPrivateFlags2|=((textAlignment << PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT) & PFLAG2_TEXT_ALIGNMENT_MASK);
resolveTextAlignment();
onRtlPropertiesChanged(getLayoutDirection());
requestLayout();
invalidate(true);
}
}
public int getTextAlignment(){
return (mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK) >> PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT;
}
public boolean resolveTextAlignment(){
mPrivateFlags2&=~(PFLAG2_TEXT_ALIGNMENT_RESOLVED | PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK);
if (hasRtlSupport()) {
final int textAlignment=getRawTextAlignment();
switch (textAlignment) {
case TEXT_ALIGNMENT_INHERIT:
if (!canResolveTextAlignment()) {
mPrivateFlags2|=PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT;
return false;
}
try {
if (!mParent.isTextAlignmentResolved()) {
mPrivateFlags2|=PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT;
return false;
}
}
 catch (AbstractMethodError e) {
Log.e(VIEW_LOG_TAG,mParent.getClass().getSimpleName() + " does not fully implement ViewParent",e);
mPrivateFlags2|=PFLAG2_TEXT_ALIGNMENT_RESOLVED | PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT;
return true;
}
int parentResolvedTextAlignment;
try {
parentResolvedTextAlignment=mParent.getTextAlignment();
}
 catch (AbstractMethodError e) {
Log.e(VIEW_LOG_TAG,mParent.getClass().getSimpleName() + " does not fully implement ViewParent",e);
parentResolvedTextAlignment=TEXT_ALIGNMENT_GRAVITY;
}
switch (parentResolvedTextAlignment) {
case TEXT_ALIGNMENT_GRAVITY:
case TEXT_ALIGNMENT_TEXT_START:
case TEXT_ALIGNMENT_TEXT_END:
case TEXT_ALIGNMENT_CENTER:
case TEXT_ALIGNMENT_VIEW_START:
case TEXT_ALIGNMENT_VIEW_END:
mPrivateFlags2|=(parentResolvedTextAlignment << PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT);
break;
default :
mPrivateFlags2|=PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT;
}
break;
case TEXT_ALIGNMENT_GRAVITY:
case TEXT_ALIGNMENT_TEXT_START:
case TEXT_ALIGNMENT_TEXT_END:
case TEXT_ALIGNMENT_CENTER:
case TEXT_ALIGNMENT_VIEW_START:
case TEXT_ALIGNMENT_VIEW_END:
mPrivateFlags2|=(textAlignment << PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT);
break;
default :
mPrivateFlags2|=PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT;
}
}
 else {
mPrivateFlags2|=PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT;
}
mPrivateFlags2|=PFLAG2_TEXT_ALIGNMENT_RESOLVED;
return true;
}
public boolean canResolveTextAlignment(){
switch (getRawTextAlignment()) {
case TEXT_DIRECTION_INHERIT:
if (mParent != null) {
try {
return mParent.canResolveTextAlignment();
}
 catch (AbstractMethodError e) {
Log.e(VIEW_LOG_TAG,mParent.getClass().getSimpleName() + " does not fully implement ViewParent",e);
}
}
return false;
default :
return true;
}
}
public void resetResolvedTextAlignment(){
mPrivateFlags2&=~(PFLAG2_TEXT_ALIGNMENT_RESOLVED | PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK);
mPrivateFlags2|=PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT;
}
public boolean isTextAlignmentResolved(){
return (mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_RESOLVED) == PFLAG2_TEXT_ALIGNMENT_RESOLVED;
}
public static class MeasureSpec {
private static final int MODE_SHIFT=30;
private static final int MODE_MASK=0x3 << MODE_SHIFT;
public static final int UNSPECIFIED=0 << MODE_SHIFT;
public static final int EXACTLY=1 << MODE_SHIFT;
public static final int AT_MOST=2 << MODE_SHIFT;
public static int makeMeasureSpec(int size,int mode){
if (sUseBrokenMakeMeasureSpec) {
return size + mode;
}
 else {
return (size & ~MODE_MASK) | (mode & MODE_MASK);
}
}
public static int makeSafeMeasureSpec(int size,int mode){
if (sUseZeroUnspecifiedMeasureSpec && mode == UNSPECIFIED) {
return 0;
}
return makeMeasureSpec(size,mode);
}
public static int getMode(int measureSpec){
return (measureSpec & MODE_MASK);
}
public static int getSize(int measureSpec){
return (measureSpec & ~MODE_MASK);
}
static int adjust(int measureSpec,int delta){
final int mode=getMode(measureSpec);
int size=getSize(measureSpec);
if (mode == UNSPECIFIED) {
return makeMeasureSpec(size,UNSPECIFIED);
}
size+=delta;
if (size < 0) {
Log.e(VIEW_LOG_TAG,"MeasureSpec.adjust: new size would be negative! (" + size + ") spec: "+ toString(measureSpec)+ " delta: "+ delta);
size=0;
}
return makeMeasureSpec(size,mode);
}
public static String toString(int measureSpec){
int mode=getMode(measureSpec);
int size=getSize(measureSpec);
StringBuilder sb=new StringBuilder("MeasureSpec: ");
if (mode == UNSPECIFIED) sb.append("UNSPECIFIED ");
 else if (mode == EXACTLY) sb.append("EXACTLY ");
 else if (mode == AT_MOST) sb.append("AT_MOST ");
 else sb.append(mode).append(" ");
sb.append(size);
return sb.toString();
}
}
public interface OnKeyListener {
boolean onKey(View v,int keyCode,KeyEvent event);
}
public interface OnTouchListener {
boolean onTouch(View v,MotionEvent event);
}
public interface OnHoverListener {
boolean onHover(View v,MotionEvent event);
}
public interface OnLongClickListener {
boolean onLongClick(View v);
}
public interface OnDragListener {
boolean onDrag(View v,DragEvent event);
}
public interface OnFocusChangeListener {
void onFocusChange(View v,boolean hasFocus);
}
public interface OnClickListener {
void onClick(View v);
}
public interface OnContextClickListener {
boolean onContextClick(View v);
}
public interface OnAttachStateChangeListener {
public void onViewAttachedToWindow(View v);
public void onViewDetachedFromWindow(View v);
}
private Object mCurrentAnimation;
private View mGhostView=null;
protected RenderNode mRenderNode=new RenderNode();
private int verticalScrollbarWidth;
private int horizontalScrollbarHeight;
public View(){
mViewFlags=SOUND_EFFECTS_ENABLED | HAPTIC_FEEDBACK_ENABLED;
mPrivateFlags2=(LAYOUT_DIRECTION_DEFAULT << PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT) | (TEXT_DIRECTION_DEFAULT << PFLAG2_TEXT_DIRECTION_MASK_SHIFT) | (PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT)| (TEXT_ALIGNMENT_DEFAULT << PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT)| (PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT)| (IMPORTANT_FOR_ACCESSIBILITY_DEFAULT << PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_SHIFT);
}
public int getX(){
return mLeft;
}
public int getY(){
return mTop;
}
public void remeasure(){
throw new RuntimeException("Implemented by subclass. ");
}
public void removeFromParent(){
throw new RuntimeException("Implemented by subclass. ");
}
public void initAttachInfo(){
mAttachInfo=new AttachInfo();
mAttachInfo.mTreeObserver=new ViewTreeObserver(mContext);
}
public IBinder getApplicationWindowToken(){
return null;
}
public int getAccessibilityViewId(){
return 0;
}
public void getLocationOnScreen(int[] appScreenLocation){
}
public void getHitRect(Rect outRect){
}
public int getScrollX(){
return 0;
}
public int getScrollY(){
return 0;
}
public void getWindowVisibleDisplayFrame(Rect displayFrame){
}
public void getWindowDisplayFrame(Rect displayFrame){
}
public boolean requestRectangleOnScreen(Rect r,boolean b){
return false;
}
protected void onDraw(r.android.graphics.Canvas canvas){
}
public r.android.content.res.Resources getResources(){
return new r.android.content.res.Resources();
}
public boolean isInEditMode(){
return false;
}
public float getAlpha(){
return 1;
}
public float getRotation(){
return 0;
}
public float getRotationX(){
return 0;
}
public float getRotationY(){
return 0;
}
public float getScaleX(){
return 1;
}
public float getScaleY(){
return 1;
}
public float getPivotX(){
return 0;
}
public float getPivotY(){
return 0;
}
public float getTranslationX(){
return 0;
}
public float getTranslationY(){
return 0;
}
public float getTranslationZ(){
return 0;
}
public float getElevation(){
return 0;
}
public void setAlpha(float alpha){
setMyAttribute("alpha",alpha);
}
public void setRotation(float rotation){
setMyAttribute("rotation",rotation);
}
public void setRotationX(float rotationX){
setMyAttribute("rotationX",rotationX);
}
public void setRotationY(float rotationY){
setMyAttribute("rotationY",rotationY);
}
public void setScaleX(float scaleX){
setMyAttribute("scaleX",scaleX);
}
public void setScaleY(float scaleY){
setMyAttribute("scaleY",scaleY);
}
public void setPivotX(float transformPivotX){
setMyAttribute("transformPivotX",transformPivotX);
}
public void setPivotY(float transformPivotY){
setMyAttribute("transformPivotY",transformPivotY);
}
public void setTranslationX(float translationX){
setMyAttribute("translationX",translationX);
}
public void setTranslationY(float translationY){
setMyAttribute("translationY",translationY);
}
public void setTranslationZ(float translationZ){
setMyAttribute("translationZ",translationZ);
}
public void setElevation(float elevation){
setMyAttribute("elevation",elevation);
}
public boolean hasUnhandledKeyListener(){
return false;
}
public boolean hasDefaultFocus(){
return false;
}
void clearFocusInternal(View focused,boolean propagate,boolean refocus){
}
private boolean restoreDefaultFocus(){
return false;
}
private void notifyEnterOrExitForAutoFillIfNeeded(boolean b){
}
public boolean shouldDrawRoundScrollbar(){
return false;
}
public void setBackgroundColor(int backgroundColor){
}
public void requestFocusFromTouch(){
}
public boolean requestFocus(){
return false;
}
public void setSystemUiVisibility(int uiOptions){
}
public void setOverScrollMode(int overScrollNever){
}
public void cancelPendingInputEvents(){
}
public int getVerticalScrollbarWidth(){
return this.verticalScrollbarWidth;
}
public void setVerticalScrollbarWidth(int verticalScrollbarWidth){
this.verticalScrollbarWidth=verticalScrollbarWidth;
}
public int getHorizontalScrollbarHeight(){
return this.horizontalScrollbarHeight;
}
public void setHorizontalScrollbarHeight(int horizontalScrollbarHeight){
this.horizontalScrollbarHeight=horizontalScrollbarHeight;
}
private float getFinalAlpha(){
return 1;
}
void setFlags(int flags,int mask){
int old=mViewFlags;
mViewFlags=(mViewFlags & ~mask) | (flags & mask);
int changed=mViewFlags ^ old;
if (changed == 0) {
return;
}
int privateFlags=mPrivateFlags;
}
public void invalidateOutline(){
}
public boolean isHardwareAccelerated(){
return false;
}
public int getZ(){
return 0;
}
void damageShadowReceiver(){
}
void damageInParent(){
}
private boolean hasRtlSupport(){
return true;
}
private boolean isRtlCompatibilityMode(){
return !hasRtlSupport();
}
public void notifySubtreeAccessibilityStateChangedIfNeeded(){
}
private void sizeChange(int newWidth,int newHeight,int oldWidth,int oldHeight){
onSizeChanged(newWidth,newHeight,oldWidth,oldHeight);
}
AttachInfo mAttachInfo;
static class ThreadedRenderer {
public static boolean isAvailable(){
return false;
}
}
public static class AttachInfo {
public Handler mHandler=new Handler();
public java.util.List<View> mScrollContainers;
public ViewTreeObserver mTreeObserver;
public View mRootView;
public boolean mHardwareAccelerationRequested;
public int mWindowVisibility=View.VISIBLE;
Rect mTmpInvalRect=new Rect();
Object mViewRequestingLayout;
public boolean mKeepScreenOn;
}
void onResolveDrawables(int layoutDirection){
}
ThreadLocal sThreadLocal=new ThreadLocal();
class ThreadLocal {
Rect obj;
Rect get(){
return obj;
}
void set(Rect obj){
this.obj=obj;
}
}
static class TextUtils {
static int getLayoutDirectionFromLocale(Locale l){
return 0;
}
}
class ViewRootImpl {
boolean isInLayout(){
return false;
}
boolean requestLayoutDuringLayout(View view){
return false;
}
}
ViewRootImpl getViewRootImpl(){
return null;
}
ListenerInfo mListenerInfo;
class ListenerInfo {
public CopyOnWriteArrayList<OnAttachStateChangeListener> mOnAttachStateChangeListeners;
ArrayList<OnLayoutChangeListener> mOnLayoutChangeListeners;
OnKeyListener mOnKeyListener;
public Object clone(){
return null;
}
}
public int getId(){
return mID;
}
public void setId(int id){
this.mID=id;
}
public r.android.content.Context getContext(){
return new r.android.content.Context();
}
public boolean hasFocus(){
return false;
}
public View findFocus(){
return null;
}
public void unFocus(Object obj){
}
public void registerPendingFrameMetricsObservers(){
}
public void needGlobalAttributesUpdate(boolean b){
}
protected void onWindowVisibilityChanged(int visibility){
}
public void onVisibilityAggregated(boolean isVisible){
}
private void performCollectViewAttributes(AttachInfo mAttachInfo2,int visibility){
}
public boolean hasTransientState(){
return false;
}
public void clearAccessibilityFocus(){
}
public Object getAnimation(){
return null;
}
protected void onDetachedFromWindowInternal(){
}
public void setBackground(Drawable background){
mBackground=background;
}
public Drawable getBackground(){
return mBackground;
}
boolean hasIdentityMatrix(){
return false;
}
public void setAccessibilityDelegate(Object delegate){
}
public Object getAccessibilityDelegate(){
return null;
}
public void dispatchStartTemporaryDetach(){
}
public void dispatchFinishTemporaryDetach(){
}
public void setDrawingCacheBackgroundColor(Object color){
}
public int getImportantForAccessibility(){
return 999;
}
public void setImportantForAccessibility(int test){
}
public void onStartTemporaryDetach(){
}
public boolean clearFocus(){
return false;
}
public static class AccessibilityNodeInfo {
public static final int ACTION_ACCESSIBILITY_FOCUS=0;
public Object getSourceNodeId(){
return null;
}
public static int getVirtualDescendantId(Object sourceNodeId){
return 0;
}
}
public class AccessibilityNodeProvider {
public void performAction(int virtualViewId,int actionAccessibilityFocus,Object object){
}
}
public AccessibilityNodeProvider getAccessibilityNodeProvider(){
return null;
}
public void requestAccessibilityFocus(){
}
public void onFinishTemporaryDetach(){
}
public IBinder getWindowToken(){
return null;
}
public boolean isDrawingCacheEnabled(){
return false;
}
public void setDrawingCacheEnabled(boolean b){
}
public void jumpDrawablesToCurrentState(){
}
class RoundScrollbarRenderer {
public RoundScrollbarRenderer(View view){
}
}
public static class DragEvent {
private int x;
private int y;
private int action;
private String clipData;
public static final int ACTION_DRAG_STARTED=1;
public static final int ACTION_DRAG_LOCATION=2;
public static final int ACTION_DROP=3;
public static final int ACTION_DRAG_ENDED=4;
public static final int ACTION_DRAG_ENTERED=5;
public static final int ACTION_DRAG_EXITED=6;
public void setClipData(String clipData){
this.clipData=clipData;
}
public int getAction(){
return action;
}
public void setAction(int action){
this.action=action;
}
public int getX(){
return x;
}
public void setX(int x){
this.x=x;
}
public int getY(){
return y;
}
public void setY(int y){
this.y=y;
}
public String getClipData(){
return clipData;
}
}
public boolean dispatchKeyEvent(KeyEvent event){
return false;
}
public void applyForegroundTint(){
}
public void unscheduleDrawable(Object obj){
}
protected void drawableStateChanged(){
}
public void drawableHotspotChanged(float x,float y){
}
public boolean hasWindowFocus(){
return false;
}
public void setFocused(boolean hovered){
if (hovered) {
if ((mPrivateFlags & PFLAG_FOCUSED) == 0) {
mPrivateFlags|=PFLAG_FOCUSED;
refreshDrawableState();
onFocusChanged(true);
}
}
 else {
if ((mPrivateFlags & PFLAG_FOCUSED) != 0) {
mPrivateFlags&=~PFLAG_FOCUSED;
refreshDrawableState();
onFocusChanged(false);
}
}
}
public void onFocusChanged(boolean focus){
}
public void setDragHovered(boolean hovered){
if (hovered) {
if ((mPrivateFlags2 & PFLAG2_DRAG_HOVERED) == 0) {
mPrivateFlags2|=PFLAG2_DRAG_HOVERED;
refreshDrawableState();
onDragHovered(true);
}
}
 else {
if ((mPrivateFlags2 & PFLAG2_DRAG_HOVERED) != 0) {
mPrivateFlags2&=~PFLAG2_DRAG_HOVERED;
refreshDrawableState();
onDragHovered(false);
}
}
}
public void onDragHovered(boolean focus){
}
public void setDragCanAccept(boolean hovered){
if (hovered) {
if ((mPrivateFlags2 & PFLAG2_DRAG_CAN_ACCEPT) == 0) {
mPrivateFlags2|=PFLAG2_DRAG_CAN_ACCEPT;
refreshDrawableState();
onDragCanAccept(true);
}
}
 else {
if ((mPrivateFlags2 & PFLAG2_DRAG_CAN_ACCEPT) != 0) {
mPrivateFlags2&=~PFLAG2_DRAG_CAN_ACCEPT;
refreshDrawableState();
onDragCanAccept(false);
}
}
}
public void onDragCanAccept(boolean focus){
}
public Rect getForegroundBounds(int imageWidth,int imageHeight){
final Drawable foreground=mForegroundInfo != null ? mForegroundInfo.mDrawable : null;
if (foreground != null) {
mForegroundInfo.mBoundsChanged=false;
final Rect selfBounds=mForegroundInfo.mSelfBounds;
final Rect overlayBounds=mForegroundInfo.mOverlayBounds;
if (mForegroundInfo.mInsidePadding) {
selfBounds.set(0,0,getWidth(),getHeight());
}
 else {
selfBounds.set(getPaddingLeft(),getPaddingTop(),getWidth() - getPaddingRight(),getHeight() - getPaddingBottom());
}
final int ld=getLayoutDirection();
Gravity.apply(mForegroundInfo.mGravity,imageWidth,imageHeight,selfBounds,overlayBounds,ld);
return mForegroundInfo.mOverlayBounds;
}
return null;
}
public void onAttachedToWindow(){
}
public class TintInfo {
public boolean mHasTintList;
public ColorStateList mTintList;
}
public void applyBackgroundTint(){
}
public boolean hasOnKeyListener(){
return getOnKeyListener() != null;
}
public OnKeyListener getOnKeyListener(){
if (mListenerInfo != null) {
return mListenerInfo.mOnKeyListener;
}
return null;
}
public void invokeKeyListenerDown(int keyCode){
OnKeyListener onKeyListener=getOnKeyListener();
if (onKeyListener != null) {
KeyEvent keyEvent=new KeyEvent();
keyEvent.setAction(KeyEvent.ACTION_DOWN);
keyEvent.setKeyCode(keyCode);
onKeyListener.onKey(this,keyCode,keyEvent);
}
}
public void invokeKeyListenerUp(int keyCode){
}
private void resetPressedState(){
}
public void sendAccessibilityEvent(int eventType){
}
public void notifyViewAccessibilityStateChangedIfNeeded(int changeType){
}
private Handler getRunQueue(){
return new Handler();
}
public View inflateView(String layout){
throw new RuntimeException("Implemented by subclass.");
}
public void setMyAttribute(String name,Object value){
throw new RuntimeException("Implemented by subclass.");
}
public void state0(){
throw new RuntimeException("Implemented by subclass.");
}
public void state1(){
throw new RuntimeException("Implemented by subclass.");
}
public void state2(){
throw new RuntimeException("Implemented by subclass.");
}
public void state3(){
throw new RuntimeException("Implemented by subclass.");
}
public void state4(){
throw new RuntimeException("Implemented by subclass.");
}
public void stateYes(){
throw new RuntimeException("Implemented by subclass.");
}
public void stateNo(){
throw new RuntimeException("Implemented by subclass.");
}
public void relayout() {
	mPrivateFlags|=PFLAG_LAYOUT_REQUIRED;
	layout(mLeft, mTop, mRight, mBottom);
}
}
