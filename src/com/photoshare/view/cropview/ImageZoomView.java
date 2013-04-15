package com.photoshare.view.cropview;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ImageZoomView extends View implements Observer {

	public final Paint mPaint;// = new Paint(Paint.FILTER_BITMAP_FLAG);
	public final Rect mRectSrc = new Rect();
	public final Rect mRectDst = new Rect();
	private float mAspectQuotient;

	private Bitmap mBitmap;
	private ZoomState mState;

	public int operateType = 1;
	Context c;

	public Canvas mCanvas;

	public void Destroy() {
		if (mCanvas != null) {
			mCanvas = null;
		}
		if (mBitmap != null) {
//			mBitmap.recycle();
		}

	}

	public ImageZoomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		c = context;

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFFF0F0F0);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(3);

		mPath = new Path();
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		mBitmapPaint.setColor(0xFFF0F0F0);
		mBitmapPaint.setStrokeWidth(3);
	}

	public void setZoomState(ZoomState state) {
		if (mState != null) {
			mState.deleteObserver(this);
		}
		mState = state;
		mState.addObserver(this);
		invalidate();
	}

	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xFFAAAAAA);
		if (mBitmap != null && mState != null && touch == 2 || operateType == 1) {
			final int viewWidth = getWidth();
			final int viewHeight = getHeight();
			final int bitmapWidth = mBitmap.getWidth();
			final int bitmapHeight = mBitmap.getHeight();

			final float panX = mState.getPanX();
			final float panY = mState.getPanY();
			final float zoomX = mState.getZoomX(mAspectQuotient) * viewWidth
					/ bitmapWidth;
			final float zoomY = mState.getZoomY(mAspectQuotient) * viewHeight
					/ bitmapHeight;

			// Setup source and destination rectangles
			mRectSrc.left = (int) (panX * bitmapWidth - viewWidth / (zoomX * 2));
			mRectSrc.top = (int) (panY * bitmapHeight - viewHeight
					/ (zoomY * 2));
			mRectSrc.right = (int) (mRectSrc.left + viewWidth / zoomX);
			mRectSrc.bottom = (int) (mRectSrc.top + viewHeight / zoomY);
			mRectDst.left = getLeft();
			mRectDst.top = getTop();
			mRectDst.right = getRight();
			mRectDst.bottom = getBottom();

			// Adjust source rectangle so that it fits within the source image.
			if (mRectSrc.left < 0) {
				mRectDst.left += -mRectSrc.left * zoomX;
				mRectSrc.left = 0;
			}
			if (mRectSrc.right > bitmapWidth) {
				mRectDst.right -= (mRectSrc.right - bitmapWidth) * zoomX;
				mRectSrc.right = bitmapWidth;
			}
			if (mRectSrc.top < 0) {
				mRectDst.top += -mRectSrc.top * zoomY;
				mRectSrc.top = 0;
			}
			if (mRectSrc.bottom > bitmapHeight) {
				mRectDst.bottom -= (mRectSrc.bottom - bitmapHeight) * zoomY;
				mRectSrc.bottom = bitmapHeight;
			}

			canvas.drawBitmap(mBitmap, mRectSrc, mRectDst, mPaint);

		}
		if (mBitmap != null && mState != null && touch == 1 && operateType == 2) {

			canvas.drawBitmap(mBitmap, mRectSrc, mRectDst, mBitmapPaint);
			canvas.drawPath(mPath, mPaint);

		}

		// canvas.restore();
	}

	public void update(Observable observable, Object data) {
		invalidate();
	}

	private void calculateAspectQuotient() {
		if (mBitmap != null) {
			mAspectQuotient = (((float) mBitmap.getWidth()) / mBitmap
					.getHeight()) / (((float) getWidth()) / getHeight());
		}
	}

	public void setImage(Bitmap bitmap) {
		mBitmap = bitmap;
		mCanvas = new Canvas(mBitmap);
		calculateAspectQuotient();
		invalidate();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		calculateAspectQuotient();
	}

	// public Paint mPaint;
	public Path mPath;
	public Paint mBitmapPaint;
	public float mX, mY;
	public static final float TOUCH_TOLERANCE = 4;
	public int touch = 1;
	public int isUp = 0;
}
