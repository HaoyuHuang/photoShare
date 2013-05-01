package com.photoshare.utils.async;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.service.photos.factory.DecoratePhotoWrapper;
import com.photoshare.service.photos.factory.PhotoFactory;
import com.photoshare.utils.Utils;

public class AsyncImageLoader {

	private Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

	public void loadImageFromFileUrl(final Executor pool,
			final String imageUrl, final ImageCallback mCallback,
			final BitmapDisplayConfig config) {

		Drawable drawable = get(imageUrl, config);

		if (drawable != null) {
			drawable = PhotoFactory.createConfiguredDrawable(drawable, config);
			mCallback.imageLoaded(drawable, imageUrl);
			return;
		}

		pool.execute(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				Bitmap bit = null;
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				bit = BitmapFactory.decodeFile(imageUrl);
				if (bit != null) {
					bit = PhotoFactory.createConfiguredBitmap(bit, config);
				}
				Drawable drawable = new BitmapDrawable(bit);
				if (imageCache.size() > 5) {
					imageCache.clear();
				}
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				mCallback.imageLoaded(drawable, imageUrl);
			}

		});
	}

	public void consumeAsyncPhotoBean(final AsyncPhotoBean photoBean) {
		Bitmap bit = null;
		try {
			bit = getBitMapFromWeb(photoBean.getUrl());
			if (bit != null) {
				bit = PhotoFactory.createConfiguredBitmap(bit,
						photoBean.getConfig());
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
			photoBean.getCallback().imageDefault();
		}
		// imageCache.put(photoBean.getUrl(),
		// new SoftReference<Bitmap>(bit));
		Drawable drawable = new BitmapDrawable(bit);
		imageCache.put(photoBean.getUrl(),
				new SoftReference<Drawable>(drawable));
		photoBean.getCallback().imageLoaded(drawable, photoBean.getUrl());
		photoBean.setSuccess(true);
	}

	public void loadImageFromWebUrl(final Executor pool, final String imageUrl,
			final ImageCallback mCallback, final BitmapDisplayConfig config) {

		Drawable drawable = get(imageUrl, config);

		if (drawable != null) {
			mCallback.imageLoaded(drawable, imageUrl);
			return;
		}

		pool.execute(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				Bitmap bit = null;
				try {
					bit = getBitMapFromWeb(imageUrl);
					if (bit != null) {
						bit = PhotoFactory.createConfiguredBitmap(bit, config);
						// BitmapDiskCache.cache(bit, imageUrl);
					}
				} catch (RuntimeException re) {
					re.printStackTrace();
					mCallback.imageDefault();
				}
				Drawable drawable = new BitmapDrawable(bit);
				clearCache();
				PhotoFactory.savePhotoURLtoDiskCache(bit, imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				mCallback.imageLoaded(drawable, imageUrl);
			}
		});
	}

	private void clearCache() {
		if (imageCache.size() > 2) {
			imageCache.clear();
		}
	}
	
	public void docorateImage(final Executor pool,
			final DecoratePhotoWrapper photoWrapper,
			final ImageCallback mCallback) {
		pool.execute(new Runnable() {

			public void run() {
				// // TODO Auto-generated method stub
				Bitmap photo = photoWrapper.getRenderedPhoto();
				if (mCallback != null) {
					BitmapDrawable drawable = new BitmapDrawable(photo);
					mCallback.imageLoaded(drawable, null);
				}
			}
		});
	}

	private Drawable get(final String imageUrl, final BitmapDisplayConfig config) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		}
		return PhotoFactory.getDrawableFromDisk(imageUrl, config);
	}

	private Bitmap getBitMapFromWeb(String path) throws RuntimeException {
		if (path == null) {
			return null;
		}
		try {
			byte[] bytes = getBytes(path, null);
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
					bytes.length);
			return bitmap;
		} catch (RuntimeException re) {
			throw new RuntimeException(re.getMessage(), re);
		}
	}

	/**
	 * 将指定的url中的数据读入字节数组
	 * */
	private byte[] getBytes(String url, Bundle params) {

		try {
			HttpURLConnection conn = Utils.openConn(url, "post", params);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			InputStream is = conn.getInputStream();
			for (int i = 0; (i = is.read(buf)) > 0;) {
				os.write(buf, 0, i);
			}
			is.close();
			os.close();
			return os.toByteArray();
		} catch (Exception e) {
			Log.e(Utils.LOG_TAG, e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void Destroy() {

		Set<String> url = imageCache.keySet();
		Object[] urlStr = url.toArray();
		for (int i = 0; i < urlStr.length; i++) {
			SoftReference<Drawable> sd = imageCache
					.remove(urlStr[i].toString());
			if (sd != null) {
				sd = null;
			}
		}
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);

		public void imageDefault();
	}

}
