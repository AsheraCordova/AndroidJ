package r.android.widget;
import r.android.view.View;
public final class Space extends View {
  private static int getDefaultSize2(  int size,  int measureSpec){
    int result=size;
    int specMode=MeasureSpec.getMode(measureSpec);
    int specSize=MeasureSpec.getSize(measureSpec);
switch (specMode) {
case MeasureSpec.UNSPECIFIED:
      result=size;
    break;
case MeasureSpec.AT_MOST:
  result=Math.min(size,specSize);
break;
case MeasureSpec.EXACTLY:
result=specSize;
break;
}
return result;
}
protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
setMeasuredDimension(getDefaultSize2(getSuggestedMinimumWidth(),widthMeasureSpec),getDefaultSize2(getSuggestedMinimumHeight(),heightMeasureSpec));
}
}
