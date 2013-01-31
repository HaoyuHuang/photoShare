package com.photoshare.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.photoshare.polygonfill.DrawLineDelegate;
import com.photoshare.polygonfill.PolygonFill;
import com.photoshare.tabHost.R;
import com.photoshare.tabHost.TabHostActivity;
import com.photoshare.utils.Config;
import com.photoshare.utils.ImageTool;
import com.photoshare.view.cropview.ImageZoomView;
import com.photoshare.view.cropview.ParcelableObject;
import com.photoshare.view.cropview.SimpleZoomListener;
import com.photoshare.view.cropview.ZoomState;

public class ShowWardrobeActivity extends TabHostActivity {
	/** Called when the activity is first created. */
	// zoomview变量
	private ImageZoomView mZoomView;
	private ZoomState mZoomState;
	private Bitmap mBitmap, bmp, oBitmap;
	private SimpleZoomListener mZoomListener;
	/* private ProgressBar progressBar; */
	private Button btnHome, btnOperate, btnMove, btnCrop, btnRotate, btnCancel,
			btnSave, btnUpload;
	private String url = "";
	private ImageView imageView;

	// quickAction变量
	/*
	 * private final int mID_back = 1; private final int mID_draw = 2; private
	 * final int mID_move = 3; private final int mID_save = 4; private final int
	 * mID_upload = 5; private final int mID_cancel = 6;
	 */

	// 剪刀变量
	private static final String TAG = "Touch";
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist;
	private float scaleWidth = 1;
	private float scaleHeight = 1;
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = ZOOM;
	private boolean startMove;
	private int startX;
	private int startY;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// progressBar.setVisibility(View.GONE);

			mZoomView.setImage(mBitmap);
			mZoomState = new ZoomState();
			mZoomView.setZoomState(mZoomState);

			mZoomState.setImageZoomView(mZoomView);// 可能需要修改

			mZoomListener = new SimpleZoomListener();
			mZoomListener.setZoomState(mZoomState);
			mZoomListener.mBitmap = mBitmap;
			mZoomView.setOnTouchListener(mZoomListener);

			resetZoomState();
		}
	};
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_image);

		mZoomView = (ImageZoomView) findViewById(R.id.zoomView);
		// progressBar = (ProgressBar) findViewById(R.id.progress_large);
		btnHome = (Button) findViewById(R.id.btnHome);
		btnOperate = (Button) findViewById(R.id.btnOperate);
		btnMove = (Button) findViewById(R.id.btnMove);
		btnCrop = (Button) findViewById(R.id.btnCrop);
		btnRotate = (Button) findViewById(R.id.btnRotate);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnUpload = (Button) findViewById(R.id.btnUpload);

		imageView = (ImageView) findViewById(R.id.imageView);
		// progressBar.setVisibility(View.VISIBLE);
		getImage();

		handler.sendEmptyMessage(0);

		initButton();
		// initZoomControl();
		initImageView();

	}

	public void getImage() {
		ParcelableObject passObject = this.getIntent()
				.getParcelableExtra("key");
		mBitmap = passObject.getBitmap();
		url = passObject.getUrl();
		mBitmap = ImageTool.imageZoom(mBitmap, 320, 500);

		/*
		 * try { saveMyBitmap("haha"); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		oBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);

	}

	public void initImageView() {
		imageView.setOnTouchListener(new OnTouchListener() {

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
					startMove = true;
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
		bmp = BitmapFactory.decodeResource(
				ShowWardrobeActivity.this.getResources(), R.drawable.arrow);
		bmp = ImageTool.imageZoom(bmp, 50, 50);
		imageView.setImageBitmap(bmp);
		imageView.setVisibility(View.GONE);

	}

	/*
	 * public void saveMyBitmap(String bitName) throws IOException { File f =
	 * new File("/sdcard/YiBan/" + bitName + ".png"); if (f.exists()) {
	 * f.delete(); } f.createNewFile(); FileOutputStream fOut = null; try { fOut
	 * = new FileOutputStream(f); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } mBitmap.compress(Bitmap.CompressFormat.PNG, 100,
	 * fOut);
	 * 
	 * try { fOut.flush(); } catch (IOException e) { e.printStackTrace(); } try
	 * { fOut.close(); } catch (IOException e) { e.printStackTrace(); } }
	 */

	/*
	 * public void initZoomControl() { ZoomControls zoomCtrl = (ZoomControls)
	 * findViewById(R.id.zoomCtrl); zoomCtrl.setOnZoomInClickListener(new
	 * OnClickListener() { public void onClick(View v) { float z =
	 * mZoomState.getZoom() + 0.25f; mZoomState.setZoom(z);
	 * mZoomState.notifyObservers(); } });
	 * zoomCtrl.setOnZoomOutClickListener(new OnClickListener() {
	 * 
	 * public void onClick(View v) { float z = mZoomState.getZoom() - 0.25f;
	 * mZoomState.setZoom(z); mZoomState.notifyObservers(); } });
	 * zoomCtrl.setVisibility(View.GONE); }
	 */

	public void initButton() {
		/*
		 * btnHome.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { // TODO Auto-generated method stub
		 * onBackPressed(); }
		 * 
		 * });
		 */
		btnOperate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mZoomView.operateType = 2;
				Toast.makeText(getApplicationContext(), "指尖轻动，一气呵成",
						Toast.LENGTH_SHORT).show();
			}

		});
		btnMove.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mZoomView.operateType = 1;
				Toast.makeText(getApplicationContext(), "单点移动图片",
						Toast.LENGTH_SHORT).show();
			}

		});
		btnCrop.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// mQuickAction.show(v);
				// mQuickAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);
				pf = new PolygonFill(mBitmap.getWidth(), mBitmap.getHeight(),
						new DrawLine());
				pf.polygonFill(Config.pointList);
				mZoomView.invalidate();
				mZoomView.operateType = 1;
				Config.pointList.clear();

			}

		});

		btnRotate.setOnClickListener(new OnClickListener() {

			public void onClick(View v) { // TODO Auto-generated method stub
				mBitmap = ImageTool.rotateBitmap(mBitmap, 90);
				handler.sendEmptyMessage(0);
				mZoomView.invalidate();
			}
		});

		/*
		 * btnCancel.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { // TODO Auto-generated method stub
		 * 
		 * // mZoomView.setImage(oBitmap); mBitmap = oBitmap;
		 * handler.sendEmptyMessage(0); mZoomView.invalidate(); }
		 * 
		 * }); btnSave.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { // TODO Auto-generated method stub
		 * 
		 * String path = getResources().getString(R.string.cropedDirStr);
		 * ImageTool.saveImage(mBitmap, path, "", 0); FileTools.deleteFile(url);
		 * Toast.makeText(getApplicationContext(), "保存成功",
		 * Toast.LENGTH_SHORT).show(); }
		 * 
		 * }); btnUpload.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * });
		 */
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

	/*
	 * @Override public void finish() { super.finish(); if (mZoomView != null) {
	 * mZoomView.Destroy(); } if (mBitmap != null) { mBitmap.recycle(); } if
	 * (oBitmap != null) { oBitmap.recycle(); }
	 * 
	 * if (bmp != null) { bmp.recycle(); } }
	 */

	/*
	 * @Override protected void onDestroy() { super.onDestroy(); if (mZoomView
	 * != null) { mZoomView.Destroy(); } if (mBitmap != null) {
	 * mBitmap.recycle(); } if (bmp != null) { bmp.recycle(); }
	 * 
	 * }
	 */

	/*
	 * @Override public void onBackPressed() { super.onBackPressed(); }
	 */

	private void resetZoomState() {
		mZoomState.setPanX(0.5f);
		mZoomState.setPanY(0.5f);
		mZoomState.setZoom(1f);
		mZoomState.notifyObservers();
	}
}