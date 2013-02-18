/**
 * 
 */
package com.photoshare.camera;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.photoshare.service.photos.EditPhotoType;
import com.photoshare.tabHost.R;
import com.photoshare.utils.FileTools;
import com.photoshare.utils.Utils;

/**
 * @author czj_yy
 * 
 */
public class CameraView {

	private Context context;
	private View baseView;
	private Bitmap photo;
	private Bitmap decoratedPhoto;

	/** Crop View */
	private ImageView photoView;

	// private ImageView mSubmitView;
	// private ImageView mCancleView;
	private LinearLayout photoToolsView;
	private static final String CAMERA_DIR = "/photoShare/";

	/**
	 * @param context
	 * @param baseView
	 */
	public CameraView(Context context, View baseView) {
		super();
		this.context = context;
		this.baseView = baseView;
	}

	public CameraView() {

	}

	public void applyView() {
		photoToolsView = (LinearLayout) baseView
				.findViewById(R.id.editPhotoToolsGallery);
		photoView = (ImageView) baseView.findViewById(R.id.editPhoto);
		// mSubmitView = (ImageView) baseView
		// .findViewById(R.id.editPhotoSubmitBtn);
		// mCancleView = (ImageView) baseView
		// .findViewById(R.id.editPhotoCancleBtn);
		//
		// mSubmitView.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (onCameraViewClick != null) {
		// onCameraViewClick.OnSubmitClick();
		// }
		// }
		// });
		//
		// mCancleView.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (onCameraViewClick != null) {
		// onCameraViewClick.OnCancleClick();
		// }
		// }
		// });
		applyImageTool();
	}

	private void applyImageTool() {
		Collection<EditPhotoType> values = Arrays
				.asList(EditPhotoType.values());
		for (EditPhotoType type : values) {
			photoToolsView.addView(insertImage(type));
		}
	}

	public void DisappearPhotoTools() {
		photoToolsView.setVisibility(View.GONE);
		// mSubmitView.setVisibility(View.GONE);
		// mCancleView.setVisibility(View.GONE);
	}

	public void DisplayPhotoTools() {
		photoToolsView.setVisibility(View.VISIBLE);
		// mSubmitView.setVisibility(View.VISIBLE);
		// mCancleView.setVisibility(View.VISIBLE);
	}

	private View insertImage(final EditPhotoType type) {
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(320, 320));
		layout.setGravity(Gravity.CENTER);

		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new LayoutParams(300, 300));
		imageView.setBackgroundResource(type.getImageId());

		imageView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (onCameraViewClick != null) {
					onCameraViewClick.OnToolsClick(type, photo);
				}
			}
		});
		layout.addView(imageView);
		return layout;
	}

	private String mCurrentPhotoPath;

	private String storageDir = Utils.SDCARD_ABSOLUTE_PATH + File.separator
			+ Utils.APP_NAME + Utils.DIR_USR;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	private File getAlbumDir() {
		File destDir = FileTools.makeDir(storageDir);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			destDir = getFroyoAlbumStorageDir(Utils.APP_NAME);
		} else {
			destDir = getBaseAlbumStorageDir(Utils.APP_NAME);
		}

		return destDir;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
				albumF);
		return imageF;
	}

	public File setUpPhotoFile() throws IOException {
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();
		return f;
	}

	public void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	public void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = photoView.getWidth();
		int targetH = photoView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		photo = bitmap;

		/* Associate the Bitmap to the ImageView */
		photoView.setImageBitmap(photo);
		photoView.setVisibility(View.VISIBLE);
	}

	public void setmCurrentPhotoPath(String mCurrentPhotoPath) {
		this.mCurrentPhotoPath = mCurrentPhotoPath;
	}

	public String getmCurrentPhotoPath() {
		return mCurrentPhotoPath;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setBaseView(View baseView) {
		this.baseView = baseView;
	}

	public boolean isCurrentPhotoPathNull() {
		if (mCurrentPhotoPath != null && !mCurrentPhotoPath.equals("")) {
			return false;
		}
		return true;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
		photoView.setImageBitmap(photo);
		photoView.setVisibility(View.VISIBLE);
	}

	public void setPhoto(Drawable photo) {
		BitmapDrawable dP = (BitmapDrawable) photo;
		if (dP != null) {
			this.photo = dP.getBitmap();
			photoView.setImageBitmap(this.photo);
			photoView.setVisibility(View.VISIBLE);
		}
	}

	public void setDecoratedPhoto(Drawable photo) {
		BitmapDrawable dP = (BitmapDrawable) photo;
		if (dP != null) {
			this.decoratedPhoto = dP.getBitmap();
			photoView.setImageBitmap(dP.getBitmap());
			photoView.setVisibility(View.VISIBLE);
		}
	}

	private File getFroyoAlbumStorageDir(String albumName) {
		return new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				albumName);
	}

	private File getBaseAlbumStorageDir(String albumName) {
		return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ albumName);
	}

	private OnCameraViewClick onCameraViewClick;

	public void registerCameraClickListener(OnCameraViewClick onCameraViewClick) {
		this.onCameraViewClick = onCameraViewClick;
	}

	public interface OnCameraViewClick {
		public void OnSubmitClick();

		public void OnCancleClick();

		public void OnToolsClick(EditPhotoType type, Bitmap map);
	}
}
