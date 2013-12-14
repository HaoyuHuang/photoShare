package com.photoshare.ui.tab;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.photoshare.R;
import com.photoshare.file.FileConfig;
import com.photoshare.ui.interfaces.BaseActivity;

public class CameraActivity extends Activity implements BaseActivity
{

	private Button _caremaOpean;
	private Button _caremaClose;
	private Button _caremaTake;
	private SurfaceView _surfaceView;
	private SurfaceHolder _SH;
	private Camera _camera;
	private int _previewWidth = 320;
	private int _previewHeight = 480;
	public static final String CRAMERA_FILE_NAME = "CRAMERA_FILE_NAME";
	private String _fileName;

	private boolean jurdge = false;

	public static final String CRAMERA_COMMENT = "CRAMERA_COMMENT";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.camera_activity);
		jurdge = false;

		this._caremaOpean = (Button) this.findViewById(R.id.camera_opean);
		this._caremaTake = (Button) this.findViewById(R.id.camera_take);
		this._surfaceView = (SurfaceView) this
				.findViewById(R.id.camera_surfaceview_id);
		this._SH = this._surfaceView.getHolder();
		this._SH.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this._SH.addCallback(new Callback()
		{

			public void surfaceDestroyed(SurfaceHolder arg0)
			{
				// TODO Auto-generated method stub

			}

			public void surfaceCreated(SurfaceHolder arg0)
			{
				// TODO Auto-generated method stub

			}

			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub

			}
		});

		this._caremaOpean.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				opeanCarema();
				jurdge = true;

			}
		});
		this._caremaTake.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{

				if (jurdge == false)
				{
					Toast.makeText(CameraActivity.this, "请先打开相机！",
							Toast.LENGTH_SHORT).show();

				}
				else
				{
					takePicture();
					jurdge = false;
				}

			}
		});

	}

	private PictureCallback jpeg = new PictureCallback()
	{

		public void onPictureTaken(byte[] data, Camera camera)
		{
			try
			{
				_fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(
						new Date()).toString()
						+ ".jpg";
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				Matrix m = new Matrix();
				m.setRotate(90, (float) bitmap.getWidth() / 2,
						(float) bitmap.getHeight() / 2);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), m, true);
				FileOutputStream fos = new FileOutputStream(FileConfig.FILE_URL
						+ _fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
				bos.flush();
				bos.close();
				fos.close();
				Log.d("WCH", "start take photo--->3");
				Intent intent = new Intent(CameraActivity.this,
						GoShareActivity.class);
				intent.putExtra(CameraActivity.CRAMERA_FILE_NAME,
						FileConfig.FILE_URL + _fileName);
				CameraActivity.this.startActivity(intent);
				closeCarema();

			}
			catch (Exception e)
			{
				Log.d("camera", "Error accessing file: " + e.getMessage());
			}
		}

	};

	protected void takePicture()
	{
		// TODO Auto-generated method stub

		Log.d("WCH", "-------------------->start take photo");
		_camera.takePicture(null, null, jpeg);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 注意了！不能再这里关闭相机！！！！

	}

	protected void opeanCarema()
	{
		// TODO Auto-generated method stub

		try
		{
			if (this._camera == null)
			{

				this._camera = Camera.open();
				this._camera.setDisplayOrientation(90);
				Parameters paras = this._camera.getParameters();
				paras.setPictureSize(this._previewWidth, this._previewHeight);
				paras.setPictureFormat(PixelFormat.JPEG);
				this._camera.setParameters(paras);
				this._camera.setPreviewDisplay(this._SH);
				this._camera.startPreview();

			}

		}
		catch (Exception e)
		{

			e.printStackTrace();
			Log.d("WCH", e.toString());

			Toast.makeText(
					this,
					CameraActivity.this.getResources().getString(
							R.string.carema_opean_false), Toast.LENGTH_SHORT)
					.show();
		}

	}

	private void closeCarema()
	{
		// TODO Auto-generated method stub
		if (this._camera != null)
		{
			this._camera.stopPreview();
			this._camera.release();
			this._camera = null;
		}
	}

	public void init()
	{
		// TODO Auto-generated method stub

	}

	public void refresh(int taskID, Object... paras)
	{
		// TODO Auto-generated method stub

	}

}
