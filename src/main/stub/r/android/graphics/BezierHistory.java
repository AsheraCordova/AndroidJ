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
