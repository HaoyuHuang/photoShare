package com.photoshare.view.cropview;

import android.graphics.Paint;
import android.graphics.Path;

public class TouchUpPaint {
	public Path mPath;
	public Paint mPaint;
	public float mx = 0, my = 0;

	public TouchUpPaint() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFFF0F0F0);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(1);

		mPath = new Path();
	}

}
