package com.photoshare.service.photos.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.UserTextView;

public class FeedItemView {

	private View baseView;
	private UserTextView mFeedUserName;
	private ImageView mFeedUserHead;
	private TextView mFeedDate;
	private TextView mFeedFavor;
	private UserTextView mFeedLike;
	private UserTextView mFeedComment;
	private ImageView mFeedPhoto;
	private AsyncUtils async;
	private PhotoBean photo;

	/**
	 * @param mFeedPhoto
	 * @param async
	 */
	public FeedItemView(View baseView, AsyncUtils async, PhotoBean photo) {
		super();
		this.baseView = baseView;
		this.async = async;
		this.photo = photo;
	}

	public void applyView() {
		mFeedUserName = new UserTextView(
				(TextView) baseView.findViewById(R.id.feedsUserName),
				new UserInfo.UserInfoBuilder().ID(photo.getUid())
						.Name(photo.getUname()).build(), photo.getUname());

		mFeedUserName.registerListener(OnNameClickListener);
		mFeedUserName.apply();

		mFeedUserHead = (ImageView) baseView.findViewById(R.id.feedsUserHead);

		mFeedComment = new UserTextView(
				(TextView) baseView.findViewById(R.id.feedComment), null, "评论"
						+ photo.getCommentCount());
		mFeedComment.registerListener(OnCommentClickListener);
		mFeedComment.apply();

		mFeedDate = (TextView) baseView.findViewById(R.id.feedsDate);

		mFeedLike = new UserTextView(
				(TextView) baseView.findViewById(R.id.feedLikeList), null, "喜欢"
						+ photo.getLikesCount());
		mFeedLike.registerListener(OnLikeClickListener);
		mFeedLike.apply();

		mFeedFavor = (TextView) baseView.findViewById(R.id.feedLike);
		mFeedFavor.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mCallback != null) {
					mCallback.OnLikeClick(photo);
				}
			}
		});

		mFeedPhoto = (ImageView) baseView.findViewById(R.id.feedPhoto);

		mFeedDate.setText(photo.getCreateTime());

		load();
	}

	private void load() {

		String url = photo.getUrlLarge();
		BitmapDisplayConfig config = new BitmapDisplayConfig(PhotoType.LARGE);

		if (url == null) {
			async.loadDrawableFromFile(photo.getAbsolutePath(),
					new AsyncImageLoader.ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {
							if (mCallback != null) {
								mCallback.OnFeedPhotoLoaded(mFeedPhoto,
										imageDrawable, imageUrl);
							}
						}

						public void imageDefault() {
							if (mCallback != null) {
								mCallback.OnFeedPhotoDefault(mFeedPhoto);
							}
						}

					}, config);
		} else {
			async.loadDrawableFromWeb(url,
					new AsyncImageLoader.ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {
							if (mCallback != null) {
								mCallback.OnFeedPhotoLoaded(mFeedPhoto,
										imageDrawable, imageUrl);
							}
						}

						public void imageDefault() {
							if (mCallback != null) {
								mCallback.OnFeedPhotoDefault(mFeedPhoto);
							}
						}

					}, config);
			mFeedPhoto.setOnLongClickListener(new OnLongClickListener() {

				public boolean onLongClick(View arg0) {
					if (mCallback != null) {
						mCallback.OnLikeClick(photo);
					}
//					showLike();
					return true;
				}

			});
		}

		async.loadDrawableFromWeb(photo.getTinyHeadUrl(),
				new AsyncImageLoader.ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						if (mCallback != null) {
							mCallback.OnUserHeadLoaded(mFeedUserHead,
									imageDrawable, imageUrl);
						}
					}

					public void imageDefault() {
						if (mCallback != null) {
							mCallback.OnUserHeadDefault(mFeedUserHead);
						}
					}

				}, new BitmapDisplayConfig(PhotoType.SMALL));

	}

	

	private UserTextView.UserTextOnClickListener OnNameClickListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.OnNameClick(info);
			}
		}
	};

	private UserTextView.UserTextOnClickListener OnCommentClickListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.OnCommentListClick(photo);
			}
		}
	};

	private UserTextView.UserTextOnClickListener OnLikeClickListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.OnLikeListClick(photo);
			}
		}
	};

	private ICallback mCallback;

	public void registerCallback(ICallback mCallback) {
		this.mCallback = mCallback;
	}

	public interface ICallback {
		public void OnNameClick(UserInfo info);

		public void OnCommentListClick(PhotoBean photo);

		public void OnLikeListClick(PhotoBean photo);

		public void OnLikeClick(PhotoBean photo);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnFeedPhotoLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnUserHeadDefault(ImageView image);

		public void OnFeedPhotoDefault(ImageView image);
	}

}