/**
 * 
 */
package com.photoshare.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 *         UserHomeTitleBarView contains views should be displayed on the home
 *         title.
 * 
 */
public class UserHomeTitleBarView {
	private View baseView;
	private ImageView mUserHomeHeadView;
	private TextView mUserHomePhotosCntView;
	private UserTextView mUserHomeFollowersCntView;
	private UserTextView mUserHomeFollowingCntView;
	private UserInfo userInfo;
	private AsyncUtils async;

	/**
	 * @param baseView
	 * @param userInfo
	 * @param async
	 */
	public UserHomeTitleBarView(View baseView, UserInfo userInfo,
			AsyncUtils async) {
		super();
		this.baseView = baseView;
		this.userInfo = userInfo;
		this.async = async;
	}

	public void applyView() {

		mUserHomeFollowersCntView = new UserTextView(
				(TextView) baseView
						.findViewById(R.id.userHomeTitleBarFollowersCnt),
				userInfo, userInfo.getFollowersCnt() + "\r\n 跟随者");

		mUserHomeFollowersCntView.registerListener(followerCntListener);

		mUserHomeFollowingCntView = new UserTextView(
				(TextView) baseView
						.findViewById(R.id.userHomeTitlebarFollowingCnt),
				userInfo, userInfo.getFollowingCnt() + "\r\n 跟随");

		mUserHomeFollowingCntView.registerListener(followingCntListener);

		mUserHomeHeadView = (ImageView) baseView
				.findViewById(R.id.userHomeTitleBarHead);

		mUserHomePhotosCntView = (TextView) baseView
				.findViewById(R.id.userHomeTitleBarPhotosCnt);
		mUserHomePhotosCntView.setText(userInfo.getPhotosCnt() + "\r\n 照片");

		async.loadDrawableFromWeb(userInfo.getTinyurl(), new ImageCallback() {

			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				if (mCallback != null) {
					mCallback.OnUserHeadLoaded(mUserHomeHeadView,
							imageDrawable, imageUrl);
				}
			}

			public void imageDefault() {
				if (mCallback != null) {
					mCallback.OnDefault(mUserHomeHeadView);
				}
			}
		});

	}

	private UserTextView.UserTextOnClickListener followerCntListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnFollowerCntClick();
			}
		}
	};

	private UserTextView.UserTextOnClickListener followingCntListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnFollowingCntClick();
			}
		}
	};

	private ICallback mCallback;

	public void registerCallback(ICallback callback) {
		this.mCallback = callback;
	}

	public interface ICallback {
		public void OnFollowerCntClick();

		public void OnFollowingCntClick();

		public void OnPhotosCntClick();

		public void OnEditInfoClick();

		public void OnUserHeadLoaded(ImageView imageView, Drawable photo,
				String url);

		public void OnDefault(ImageView imageView);

	}
}
