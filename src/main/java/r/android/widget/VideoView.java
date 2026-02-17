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
/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package r.android.widget;
public class VideoView extends r.android.view.View {
  private int mVideoWidth;
  private int mVideoHeight;
  protected void onMeasure(  int widthMeasureSpec,  int heightMeasureSpec){
    int width=getDefaultSize(mVideoWidth,widthMeasureSpec);
    int height=getDefaultSize(mVideoHeight,heightMeasureSpec);
    if (mVideoWidth > 0 && mVideoHeight > 0) {
      int widthSpecMode=MeasureSpec.getMode(widthMeasureSpec);
      int widthSpecSize=MeasureSpec.getSize(widthMeasureSpec);
      int heightSpecMode=MeasureSpec.getMode(heightMeasureSpec);
      int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);
      if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
        width=widthSpecSize;
        height=heightSpecSize;
        if (mVideoWidth * height < width * mVideoHeight) {
          width=height * mVideoWidth / mVideoHeight;
        }
 else         if (mVideoWidth * height > width * mVideoHeight) {
          height=width * mVideoHeight / mVideoWidth;
        }
      }
 else       if (widthSpecMode == MeasureSpec.EXACTLY) {
        width=widthSpecSize;
        height=width * mVideoHeight / mVideoWidth;
        if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
          height=heightSpecSize;
        }
      }
 else       if (heightSpecMode == MeasureSpec.EXACTLY) {
        height=heightSpecSize;
        width=height * mVideoWidth / mVideoHeight;
        if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
          width=widthSpecSize;
        }
      }
 else {
        width=mVideoWidth;
        height=mVideoHeight;
        if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
          height=heightSpecSize;
          width=height * mVideoWidth / mVideoHeight;
        }
        if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
          width=widthSpecSize;
          height=width * mVideoHeight / mVideoWidth;
        }
      }
    }
 else {
    }
    setMeasuredDimension(width,height);
  }
  public void setVideoWidth(  int mVideoWidth){
    this.mVideoWidth=mVideoWidth;
  }
  public void setVideoHeight(  int mVideoHeight){
    this.mVideoHeight=mVideoHeight;
  }
}
