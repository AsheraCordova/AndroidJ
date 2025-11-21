//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
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
