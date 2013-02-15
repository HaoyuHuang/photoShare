/**
 * 
 */
package com.photoshare.service.news.view;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.service.news.NewsBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.MiddlePhotoImageView;
import com.photoshare.view.UserTextView;

/**
 * @author czj_yy
 * 
 */
public class NewsItemView {

	private NewsBean news;
	private UserTextView mNewsOwner;
	private TextView mNewsDesc;
	private UserTextView mNewsHolder;
	private GridView mNewsPhotos;
	private ImageView mNewsPhoto;
	private TextView mNewsDate;
	private View baseView;
	private AsyncUtils async;
	private Context context;

	/**
	 * @param news
	 * @param view
	 * @param async
	 * @param context
	 */
	public NewsItemView(NewsBean news, View view, AsyncUtils async,
			Context context) {
		super();
		this.news = news;
		this.baseView = view;
		this.async = async;
		this.context = context;
	}

	public void applyView() {
		switch (news.getEventType()) {
		case FOLLOW:
		case LIKE:
			applyLikeView();
			break;
		case NULL:
		case POPULAR:
			applyPopularView();
			break;
		case COMMENT:
			break;
		default:
			break;
		}
	}

	private void applyLikeView() {

		mNewsOwner = new UserTextView(
				(TextView) baseView.findViewById(R.id.newsPopularName),
				new UserInfo.UserInfoBuilder().ID(news.getUserId())
						.Name(news.getUserName()).build(), news.getUserName());
		mNewsOwner.registerListener(listener);

		mNewsDesc = (TextView) baseView.findViewById(R.id.newsPopularDesc);
		mNewsDate = (TextView) baseView.findViewById(R.id.newsPopularTime);
		mNewsPhotos = (GridView) baseView.findViewById(R.id.newsPopularPhotos);
		mNewsDesc.setText(news.getEventType().toString());
		mNewsDate.setText(news.getEventTime());
		try {
			PhotoBean url = news.getPhotoUrls().get(0);
			mNewsHolder = new UserTextView(
					(TextView) baseView.findViewById(R.id.newsPopularHolder),
					new UserInfo.UserInfoBuilder().ID(url.getUid())
							.Name(url.getUname()).build(), url.getUname());
			mNewsHolder.registerListener(listener);
			mNewsPhotos.setAdapter(new NewsItemAdapter(news.getPhotoUrls()));
			mNewsHolder.apply();
			async.loadDrawableFromWeb(url.getUrlHead(), new ImageCallback() {

				public void imageLoaded(Drawable imageDrawable, String imageUrl) {
					// TODO Auto-generated method stub
					mNewsPhoto.setImageDrawable(imageDrawable);
				}

				public void imageDefault() {
					// TODO Auto-generated method stub
					mNewsPhoto.setImageResource(R.drawable.icon);
				}

			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mNewsPhoto.setImageResource(R.drawable.icon);
		} finally {
			mNewsOwner.apply();
		}
	}

	private void applyPopularView() {
		mNewsOwner = new UserTextView(
				(TextView) baseView.findViewById(R.id.newsUserName),
				new UserInfo.UserInfoBuilder().ID(news.getUserId())
						.Name(news.getUserName()).build(), news.getUserName());
		mNewsOwner.registerListener(listener);
		mNewsDesc = (TextView) baseView.findViewById(R.id.newsDescription);
		mNewsDate = (TextView) baseView.findViewById(R.id.newsTime);
		mNewsPhoto = (ImageView) baseView.findViewById(R.id.newsPhoto);

		mNewsDesc.setText(news.getEventType().toString());
		mNewsDate.setText(news.getEventTime());
		try {
			PhotoBean url = news.getPhotoUrls().get(0);
			async.loadDrawableFromWeb(url.getUrlHead(), new ImageCallback() {

				public void imageLoaded(Drawable imageDrawable, String imageUrl) {
					// TODO Auto-generated method stub
					mNewsPhoto.setImageDrawable(imageDrawable);
				}

				public void imageDefault() {
					// TODO Auto-generated method stub
					mNewsPhoto.setImageResource(R.drawable.icon);
				}

			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mNewsPhoto.setImageResource(R.drawable.icon);
		} finally {
			mNewsOwner.apply();
		}
	}

	class NewsItemAdapter extends ArrayAdapter<PhotoBean> {

		/**
		 * @param context
		 * @param textViewResourceId
		 * @param objects
		 */
		public NewsItemAdapter(List<PhotoBean> urls) {
			super(context, 0, urls);
			// TODO Auto-generated constructor stub
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			View rowView = convertView;

			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.simple_grid_item, null);
			} else {

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
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.OnNewsImageClick(photo);
			}
		}

		public void OnImageLoaded(ImageView image, Drawable drawable, String url) {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.OnUserHeadLoaded(mNewsPhoto, drawable, url);
			}
		}

		public void OnImageDefaule(ImageView image) {
			if (mCallback != null) {
				mCallback.OnImageDefaule(mNewsPhoto);
			}
		}
	};

	private UserTextView.UserTextOnClickListener listener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.OnNameClick(info);
			}
		}
	};

	private ICallback mCallback;

	public void registerCallback(ICallback mCallback) {
		this.mCallback = mCallback;
	}

	public interface ICallback {
		public void OnNameClick(UserInfo info);

		public void OnNewsImageClick(PhotoBean photo);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefaule(ImageView image);
	}
}
