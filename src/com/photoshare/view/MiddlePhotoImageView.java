/**
 * 
 */
package com.photoshare.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.utils.async.AsyncImageLoader;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author czj_yy
 * 
 */
public class MiddlePhotoImageView {
	private PhotoBean photo;
	private ImageView imageView;
	private AsyncUtils async;
	private BitmapDisplayConfig config;

	/**
	 * @param photo
	 * @param imageView
	 * @param context
	 * @param async
	 */
	public MiddlePhotoImageView(PhotoBean photo, ImageView imageView,
			AsyncUtils async) {
		super();
		this.photo = photo;
		this.imageView = imageView;
		this.async = async;
	}

	public void apply() {
		config = new BitmapDisplayConfig(PhotoType.MIDDLE);
		
		async.loadDrawableFromWeb(photo.getUrlHead(),
				new AsyncImageLoader.ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						if (ImageClickListener != null) {
							ImageClickListener.OnImageLoaded(imageView,
									imageDrawable, imageUrl);
						}
					}

					public void imageDefault() {
						if (ImageClickListener != null) {
							ImageClickListener.OnImageDefaule(imageView);
						}
					}

				}, config);
		imageView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ImageClickListener != null) {
					ImageClickListener.OnImageClick(photo);
				}
			}

		});
	}

	public void registerListener(OnImageClickListener clickListener) {
		this.ImageClickListener = clickListener;
	}

	private OnImageClickListener ImageClickListener;

	public interface OnImageClickListener {
		public void OnImageClick(PhotoBean photo);

		public void OnImageLoaded(ImageView image, Drawable drawable, String url);

		public void OnImageDefaule(ImageView image);
	}

}
