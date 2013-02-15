/**
 * 
 */
package com.photoshare.service.photos.views;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.photoshare.service.photos.PhotoBean;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.MiddlePhotoImageView;

/**
 * @author czj_yy
 * 
 */
public class PopularPhotosView {

	private View baseView;
	private GridView gridView;
	private ArrayList<PhotoBean> photos = new ArrayList<PhotoBean>();
	private AsyncUtils async;
	private Context context;
	LayoutInflater mInflater;

	/**
	 * @param baseView
	 * @param photos
	 * @param async
	 * @param context
	 */
	public PopularPhotosView(View baseView, ArrayList<PhotoBean> photos,
			AsyncUtils async, Context context) {
		super();
		this.baseView = baseView;
		this.photos = photos;
		this.async = async;
		this.context = context;
	}

	public void apply() {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		gridView = (GridView) baseView.findViewById(R.id.popularView);
		gridView.setAdapter(new PopularItemAdapter());
	}

	private class PopularItemAdapter extends ArrayAdapter<PhotoBean> {

		/**
		 * @param context
		 * @param textViewResourceId
		 * @param objects
		 */
		public PopularItemAdapter() {
			super(context, R.layout.simple_grid_item, photos);
			// TODO Auto-generated constructor stub
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			View rowView = convertView;

			if (rowView == null) {
				LinearLayout layout = new LinearLayout(context);
				rowView = mInflater.inflate(R.layout.simple_grid_item, layout,
						true);
			}

			ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
			PhotoBean imageUrl = getItem(position);
			MiddlePhotoImageView photo = new MiddlePhotoImageView(imageUrl,
					imageView, async);
			photo.registerListener(OnImageClickListener);
			photo.apply();
			return rowView;
		}
	}

	private MiddlePhotoImageView.OnImageClickListener OnImageClickListener = new MiddlePhotoImageView.OnImageClickListener() {

		public void OnImageClick(PhotoBean photo) {
			if (mCallback != null) {
				mCallback.OnImageClick(photo);
			}
		}

		public void OnImageLoaded(ImageView image, Drawable drawable, String url) {
			if (mCallback != null) {
				mCallback.OnImageLoaded(image, drawable, url);
			}
		}

		public void OnImageDefaule(ImageView image) {
			if (mCallback != null) {
				mCallback.OnImageDefaule(image);
			}
		}
	};

	private ICallback mCallback;

	public void registerCallback(ICallback mCallback) {
		this.mCallback = mCallback;
	}

	public interface ICallback {
		public void OnImageClick(PhotoBean photo);

		public void OnImageLoaded(ImageView image, Drawable drawable, String url);

		public void OnImageDefaule(ImageView image);
	}
}
