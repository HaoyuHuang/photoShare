/**
 * 
 */
package com.photoshare.service.likes.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.service.likes.LikeBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.UserTextView;

/**
 * @author czj_yy
 * 
 */
public class LikeItemView {

	private View baseView;
	private UserTextView mUserNameView;
	private TextView mLikeDespView;
	private TextView mLikesDateView;
	private ImageView mUserHead;
	private AsyncUtils async;
	private LikeBean bean;

	/**
	 * @param baseView
	 * @param async
	 * @param bean
	 */
	public LikeItemView(View baseView, AsyncUtils async, LikeBean bean) {
		super();
		this.baseView = baseView;
		this.async = async;
		this.bean = bean;
	}

	public void applyView() {
		mUserNameView = new UserTextView(
				(TextView) baseView.findViewById(R.id.likeItemUserName),
				new UserInfo.UserInfoBuilder().ID(bean.getUid())
						.Name(bean.getUname()).build(), bean.getUname());
		mUserNameView.registerListener(OnNameClickListener);
		mUserNameView.apply();

		mLikeDespView = (TextView) baseView.findViewById(R.id.likeItemDesc);
		mLikesDateView = (TextView) baseView.findViewById(R.id.likeItemDate);
		mUserHead = (ImageView) baseView.findViewById(R.id.likeUserHead);
		mLikeDespView.setText("喜爱这张照片");
		mLikesDateView.setText(bean.getCreateTime());
		async.loadDrawableFromWeb(bean.getTinyHead(), new ImageCallback() {

			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				if (onLikeClickListener != null) {
					onLikeClickListener.OnUserHeadLoaded(mUserHead,
							imageDrawable, imageUrl);
				}
			}

			public void imageDefault() {
				if (onLikeClickListener != null) {
					onLikeClickListener.OnImageDefaule(mUserHead);
				}
			}
		}, BitmapDisplayConfig.SMALL);

	}

	private UserTextView.UserTextOnClickListener OnNameClickListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (onLikeClickListener != null) {
				onLikeClickListener.OnNameClick(info);
			}
		}
	};

	private OnLikeClickListener onLikeClickListener;

	public void registerListener(OnLikeClickListener onLikeClickListener) {
		this.onLikeClickListener = onLikeClickListener;
	}

	public interface OnLikeClickListener {
		public void OnNameClick(UserInfo info);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefaule(ImageView image);
	}

}
