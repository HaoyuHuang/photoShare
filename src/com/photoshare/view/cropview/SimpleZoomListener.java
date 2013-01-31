package com.photoshare.view.cropview;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.photoshare.polygonfill.Point;
import com.photoshare.utils.Config;

public class SimpleZoomListener implements View.OnTouchListener {

	public enum ControlType {
		PAN, ZOOM
	}

	private ControlType mControlType = ControlType.PAN;

	private ZoomState mState;

	private float mX;
	private float mY;
	private float mGap;
	public Bitmap mBitmap = null;
	private TouchUpPaint tUp = new TouchUpPaint();

	public void setZoomState(ZoomState state) {
		mState = state;
	}

	public void setControlType(ControlType controlType) {
		mControlType = controlType;
	}

	int out = 0;
	int init = 0;

	private void touch_start(float x, float y) {

		mState.izview.mPath.reset();
		mState.izview.mPath.moveTo(x, y);
		mState.izview.mX = x;
		mState.izview.mY = y;

		x = Function.getBitDis(x, mState.izview.mRectDst.left,
				mState.izview.mRectDst.right, mState.izview.mRectSrc.right,
				mState.izview.mRectSrc.left);
		y = Function.getBitDis(y, mState.izview.mRectDst.top,
				mState.izview.mRectDst.bottom, mState.izview.mRectSrc.bottom,
				mState.izview.mRectSrc.top);

		if (Function.pointDistance(x, y, tUp.mx, tUp.my) > 10 && init != 0) {
			System.out.println("超出范围");
			out = 1;
			return;

		}
		init = 1;
		out = 0;

		tUp.mPath.reset();
		tUp.mPath.moveTo(x, y);
		tUp.mx = x;
		tUp.my = y;

		Point p = new Point((int) x, mBitmap.getHeight() - (int) y);
		Config.pointList.add(p);

	}

	private static final float TOUCH_TOLERANCE = 4;

	private void touch_move(float x, float y) {
		if (out == 1)
			return;
		move(x, y);
	}

	private void move(float x, float y) {
		float dx = Math.abs(x - mState.izview.mX);
		float dy = Math.abs(y - mState.izview.mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mState.izview.mPath.quadTo(mState.izview.mX, mState.izview.mY,
					(x + mState.izview.mX) / 2, (y + mState.izview.mY) / 2);
			mState.izview.mX = x;
			mState.izview.mY = y;

		}

		x = Function.getBitDis(x, mState.izview.mRectDst.left,
				mState.izview.mRectDst.right, mState.izview.mRectSrc.right,
				mState.izview.mRectSrc.left);
		y = Function.getBitDis(y, mState.izview.mRectDst.top,
				mState.izview.mRectDst.bottom, mState.izview.mRectSrc.bottom,
				mState.izview.mRectSrc.top);
		Point p = new Point((int) x, mBitmap.getHeight() - (int) y);
		Config.pointList.add(p);

		dx = Math.abs(x - tUp.mx);
		dy = Math.abs(y - tUp.my);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			tUp.mPath
					.quadTo(tUp.mx, tUp.my, (x + tUp.mx) / 2, (y + tUp.my) / 2);
			tUp.mx = x;
			tUp.my = y;
		}

	}

	private void touch_up() {
		if (out == 1)
			return;
		tUp.mPath.lineTo(tUp.mx, tUp.my);
		// commit the path to our offscreen
		mState.izview.mCanvas.drawPath(tUp.mPath, tUp.mPaint);
		// kill this so we don't double draw
		tUp.mPath.reset();
	}

	public boolean onTouch(View v, MotionEvent event) {
		final int action = event.getAction();
		int pointCount = event.getPointerCount();
		mState.izview.touch = pointCount;
		if (pointCount == 1) {
			final float x = (int) event.getX();
			final float y = (int) event.getY();

			if (mState.izview.operateType == 1) {
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					mX = x;
					mY = y;
					break;
				case MotionEvent.ACTION_MOVE: {

					final float dx = (x - mX) / v.getWidth();
					final float dy = (y - mY) / v.getHeight();
					mState.setPanX(mState.getPanX() - dx);
					mState.setPanY(mState.getPanY() - dy);
					mState.notifyObservers();
					mX = x;
					mY = y;

					break;
				}
				}
			} else if (mState.izview.operateType == 2) {
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					touch_start(x, y);
					mState.izview.invalidate();
					break;
				case MotionEvent.ACTION_MOVE:
					touch_move(x, y);
					mState.izview.invalidate();
					break;
				case MotionEvent.ACTION_UP:
					touch_up();
					mState.izview.invalidate();
					break;

				}
			}
		}
		if (pointCount == 2) {
			final float x0 = event.getX(event.getPointerId(0));
			final float y0 = event.getY(event.getPointerId(0));

			final float x1 = event.getX(event.getPointerId(1));
			final float y1 = event.getY(event.getPointerId(1));

			final float gap = getGap(x0, x1, y0, y1);
			switch (action) {
			case MotionEvent.ACTION_POINTER_2_DOWN:
			case MotionEvent.ACTION_POINTER_1_DOWN:
				mGap = gap;
				break;
			case MotionEvent.ACTION_POINTER_1_UP:
				mX = x1;
				mY = y1;
				break;
			case MotionEvent.ACTION_POINTER_2_UP:
				mX = x0;
				mY = y0;
				break;
			case MotionEvent.ACTION_MOVE: {
				final float dgap = (gap - mGap) / mGap;
				// Log.d("Gap", String.valueOf(dgap));
				Log.d("Gap", String.valueOf((float) Math.pow(20, dgap)));
				mState.setZoom(mState.getZoom() * (float) Math.pow(5, dgap));
				mState.notifyObservers();
				mGap = gap;
				break;
			}
			}
		}
		return true;
	}

	private float getGap(float x0, float x1, float y0, float y1) {
		return (float) Math.pow(
				Math.pow((x0 - x1), 2) + Math.pow((y0 - y1), 2), 0.5);
	}

}
