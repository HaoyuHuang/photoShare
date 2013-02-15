package com.photoshare.cache;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ImageLoaderTask extends AsyncTask<TaskParam, Void, Bitmap> {

	private TaskParam param;
	private final WeakReference<ImageView> imageViewReference; // 防止内存溢出

	public ImageLoaderTask(ImageView imageView) {
		imageViewReference = new WeakReference<ImageView>(imageView);

	}

	@Override
	protected Bitmap doInBackground(TaskParam... params) {

		param = params[0];
		return loadImageBytes(param.getByteBitmap(),param.getName());
	}
	
	private Bitmap loadImageBytes(byte[] bytes,String name){
		return BitmapCache.getInstance().getBitmap(bytes,name);
	}
	
	private Bitmap loadImageFile(final String filename,
			final AssetManager manager) {
		InputStream is = null;
		try {
			
			Bitmap bmp = BitmapCache.getInstance().getBitmap(filename,
					param.getAssetManager());
			return bmp;
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			bitmap = null;
		}

		if (imageViewReference != null) {
			ImageView imageView = imageViewReference.get();
			if (imageView != null) {
				if (bitmap != null) {
					int width = bitmap.getWidth();// 获取真实宽高
					int height = bitmap.getHeight();
					LayoutParams lp = imageView.getLayoutParams();
					lp.height = (height * param.getItemWidth()) / width;// 调整高度

					imageView.setLayoutParams(lp);

					imageView.setImageBitmap(bitmap);

				}

			}
		}
	}
}