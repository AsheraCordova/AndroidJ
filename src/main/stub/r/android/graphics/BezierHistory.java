package r.android.graphics;

public class BezierHistory
{

	PointF startPoint = new PointF();
	PointF lastPoint = new PointF();
	PointF lastKnot = new PointF();

    public BezierHistory()
    {
    }
    
    public void setStartPoint(float x, float y)
    {
        startPoint.set(x, y);
    }
    
    public void setLastPoint(float x, float y)
    {
        lastPoint.set(x, y);
    }
    
    public void setLastKnot(float x, float y)
    {
        lastKnot.set(x, y);
    }
}
