/**
 * 
 */
package com.photoshare.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.photoshare.fragments.BaseFragment;
import com.photoshare.polygonfill.DrawLineDelegate;
import com.photoshare.polygonfill.PolygonFill;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.factory.PhotoFactory;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Config;
import com.photoshare.utils.ImageTool;
import com.photoshare.utils.QuartzUtils;
import com.photoshare.utils.Utils;
import com.photoshare.view.cropview.ImageZoomView;
import com.photoshare.view.cropview.SimpleZoomListener;
import com.photoshare.view.cropview.ZoomState;

/**
 * @author Aron
 * 
 */
public class CropPhotoFragment extends BaseFragment {

	private ImageZoomView mZoomView;
	private ImageView mImageView;
	private ZoomState mZoomState;
	private Bitmap mBitmap, bmp, oBitmap;
	private PhotoBean photoBean;
	private SimpleZoomListener mZoomListener;

	private final String KEY_PHOTO_CROP = "CROP_PHOTO";
	private final String KEY_CROP = "isCroping";

	private static final String TAG = "Touch";
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist;

	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = ZOOM;
	private boolean isCroping = false;
	private int startX;
	private int startY;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";

	public static CropPhotoFragment newInstance(int fragmentViewId) {
		CropPhotoFragment dpf = new CropPhotoFragment();
		dpf.setFragmentViewId(fragmentViewId);
		return dpf;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(PhotoBean.KEY_SRC_PHOTO, oBitmap);
			outState.putParcelable(KEY_PHOTO_CROP, mBitmap);
			outState.putBoolean(KEY_CROP, isCroping);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.crop_photo_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Bundle params = getArguments();
		if (params != null) {
			Bitmap bmp = params.getParcelable(PhotoBean.KEY_SRC_PHOTO);
			if (bmp != null) {
				mBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
				oBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
			}
			photoBean = params.getParcelable(PhotoBean.KEY_PHOTO);
			isCroping = params.getBoolean(KEY_CROP);
			// if (isCroping) {
			// startCroping();
			// } else {
			// resetCroping();
			// }
		}
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {

		leftBtnText = getBackText();
		titlebarText = getCropingText();
		rightBtnText = getStartText();

		initTitleBar(leftBtnText, rightBtnText, titlebarText);
		mZoomView = (ImageZoomView) getActivity().findViewById(
				R.id.cropZoomView);

		mZoomView.setImage(mBitmap);
		mZoomState = new ZoomState();
		mZoomView.setZoomState(mZoomState);

		mZoomState.setImageZoomView(mZoomView);

		mZoomListener = new SimpleZoomListener();
		mZoomListener.setZoomState(mZoomState);
		mZoomListener.mBitmap = mBitmap;
		mZoomView.setOnTouchListener(mZoomListener);
		mImageView = (ImageView) getActivity().findViewById(R.id.cropImageView);
		resetZoomState();
	}

	private PolygonFill pf = null;

	class DrawLine implements DrawLineDelegate {

		public void line(int startX, int startY, int endX, int endY) {
			// TODO Auto-generated method stub
			for (int i = startX; i < endX; i++) {
				mBitmap.setPixel(i, mBitmap.getHeight() - startY - 1,
						Color.argb(0, 0, 0, 0));
			}
		}

	}

	private String getBackText() {
		return getString(R.string.back);
	}

	private String getSaveText() {
		return getString(R.string.save);
	}

	private String getCancleText() {
		return getString(R.string.cancel);
	}

	private String getCropingText() {
		return getString(R.string.croping);
	}

	private String getToastStartCroping() {
		return getString(R.string.toastStartCroping);
	}

	private String getToastStopCroping() {
		return getString(R.string.toastStopCroping);
	}

	private String getEditPhotoCropCompleteText() {
		return getString(R.string.editPhotoCropComplete);
	}

	private String getStartText() {
		return getString(R.string.start);
	}

	public void initImageView() {
		mImageView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				ImageView view = (ImageView) v;
				int x = (int) event.getRawX();
				int y = (int) event.getRawY();
				// Handle touch events here...
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				// 设置拖拉模式
				case MotionEvent.ACTION_DOWN:
					savedMatrix.set(matrix);
					start.set(event.getX(), event.getY());
					Log.d(TAG, "action_down:mode=DRAG;" + event.getX() + ";"
							+ event.getY());
					mode = DRAG;
					startX = (int) event.getX();
					startY = y - v.getTop();
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					Log.d(TAG, "mode=NONE");
					break;
				// 设置多点触摸模式
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					Log.d(TAG, "oldDist=" + oldDist);
					if (oldDist > 10f) {
						savedMatrix.set(matrix);
						midPoint(mid, event);
						mode = ZOOM;
						Log.d(TAG, "mode=ZOOM");
					}
					break;
				// 若为DRAG模式，则点击移动图片
				case MotionEvent.ACTION_MOVE:
					Log.d(TAG, "action_move:" + x + ";" + y);
					if (mode == DRAG) {
						v.layout(x - startX, y - startY, x + v.getWidth()
								- startX, y - startY + v.getHeight());
						v.bringToFront();
						v.postInvalidate();
					}
					// 若为ZOOM模式，则多点触摸缩放
					else if (mode == ZOOM) {
						float newDist = spacing(event);
						Log.d(TAG, "newDist=" + newDist);
						if (newDist > 10f) {
							matrix.set(savedMatrix);
							float scale = newDist / oldDist;
							// 设置缩放比例和图片中点位置
							matrix.postScale(scale, scale, mid.x, mid.y);
						}
					}
					break;
				}
				// Perform the transformation
				view.setImageMatrix(matrix);
				return true;
			}

		});
		// 取得drawable中图片，放大，缩小，多点触摸的作用对象
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
		bmp = ImageTool.imageZoom(bmp, 50, 50);
		mImageView.setImageBitmap(bmp);
		mImageView.setVisibility(View.GONE);

	}

	// 计算移动距离
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	// 计算中点位置
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	private void resetZoomState() {
		mZoomState.setPanX(0.5f);
		mZoomState.setPanY(0.5f);
		mZoomState.setZoom(1f);
		mZoomState.notifyObservers();
	}

	private void resetCroping() {
		Toast.makeText(getActivity(), getToastStopCroping(), Toast.LENGTH_SHORT)
				.show();
		resetZoomState();
		if (mBitmap != null) {
			// mBitmap.recycle();
		}
		if (bmp != null) {
			// bmp.recycle();
		}
		mZoomView.operateType = 1;
		mBitmap = oBitmap;
		mZoomView.setImage(mBitmap);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mZoomView != null) {
			mZoomView.Destroy();
		}
		if (mBitmap != null) {
			// mBitmap.recycle();
		}
		if (bmp != null) {
			// bmp.recycle();
		}
	}

	private void savePhoto() {
		PhotoFactory.savePhototoImageStore(getActivity(), mBitmap,
				Utils.APP_NAME + "-" + QuartzUtils.formattedNow(),
				photoBean.getCaption());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked(View view) {
		if (!isCroping) {
			startCroping();
			isCroping = true;
			return;
		}
		pf = new PolygonFill(mBitmap.getWidth(), mBitmap.getHeight(),
				new DrawLine());
		pf.polygonFill(Config.pointList);

		Utils.showOptionWindow(getActivity(), "裁剪完成?", photoBean.getCaption(),
				new Utils.OnOptionListener() {

					public void onOK() {
						savePhoto();
						backward(null);
					}

					public void onCancel() {

					}
				});
	}

	/**
	 * 
	 */
	private void startCroping() {
		Toast.makeText(this.getActivity(), getToastStartCroping(),
				Toast.LENGTH_SHORT).show();
		leftBtnText = getCancleText();
		rightBtnText = getEditPhotoCropCompleteText();
		setTitleBarText(leftBtnText, rightBtnText, titlebarText);
		mZoomView.operateType = 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void onLeftBtnClicked(View view) {
		if (isCroping) {
			leftBtnText = getBackText();
			rightBtnText = getStartText();
			setTitleBarText(leftBtnText, rightBtnText, titlebarText);
			resetCroping();
			isCroping = false;
			return;
		}
		backward(null);
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}

}
