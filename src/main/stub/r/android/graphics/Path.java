package r.android.graphics;

public class Path {
	private BezierPath path;
	public Path(BezierPath path) {
		this.path = path;
	}

	public boolean isEmpty() {
		return false;
	}

	public float[] approximate(float error) {
		return path.approximate(error);
	}

	public static class PathParser {
		
	}

	public static Path createPathFromPathData(String pathData) {
		BezierPath path = new BezierPath();
		path.parsePathString(pathData);

		return new Path(path);
	}
}
