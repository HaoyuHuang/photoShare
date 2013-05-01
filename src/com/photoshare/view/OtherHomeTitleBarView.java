/**
 * 
 */
package com.photoshare.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.common.IObserver;
import com.photoshare.msg.MsgType;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 *         OtherHomeTitleBarView displays views other users home titlebar.
 * 
 */
public class OtherHomeTitleBarView {

	private View baseView;
	private ImageView mUserHomeHeadView;
	private TextView mUserHomePhotosCntView;
	private UserTextView mUserHomeLikesCntView;
	private UserTextView mUserHomeFollowersCntView;
	private UserTextView mUserHomeFollowingCntView;
	private UserTextView mUserNameView;
	private TextView mUserBioView;
	private UserTextView mUserWebsiteView;
	private UserStateBtn mUserFollowBtn;
	private UserInfo userInfo;
	private AsyncUtils async;
	private BitmapDisplayConfig config;

	/**
	 * @param baseView
	 * @param userInfo
	 * @param async
	 */
	public OtherHomeTitleBarView(View baseView, UserInfo userInfo,
			AsyncUtils async) {
		super();
		this.baseView = baseView;
		this.userInfo = userInfo;
		this.async = async;
	}

	public void applyData(UserInfo info) {
		this.userInfo = info;
		applyView();
	}

	public void applyView() {

		mUserFollowBtn = new UserStateBtn(baseView, R.id.otherHomeFollowBtn,
				userInfo.isFollowing(), MsgType.FOLLOW.getStartText(),
				MsgType.FOLLOW.getPendingText(),
				MsgType.FOLLOW.getSuccessText(), MsgType.FOLLOW.getFailText());
		mUserFollowBtn.registerListener(onObserverClickListener);
		mUserFollowBtn.applyView();

		mUserHomeFollowersCntView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeFollowerCnt),
				userInfo, userInfo.getFollowersCnt() + "\r\n 跟随者");
		mUserHomeFollowersCntView.registerListener(followerCntListener);
		mUserHomeFollowersCntView.apply();

		mUserHomeFollowingCntView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeFollowingCnt),
				userInfo, userInfo.getFollowingCnt() + "\r\n 跟随他");
		mUserHomeFollowingCntView.registerListener(followingCntListener);
		mUserHomeFollowingCntView.apply();

		mUserHomeHeadView = (ImageView) baseView
				.findViewById(R.id.otherHomeHead);

		mUserHomeLikesCntView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeLikesCnt),
				userInfo, userInfo.getLikesCnt() + "\r\n 喜欢");
		mUserHomeLikesCntView.registerListener(likeCntListener);
		mUserHomeLikesCntView.apply();

		mUserHomePhotosCntView = (TextView) baseView
				.findViewById(R.id.otherHomePhotoCnt);
		mUserHomePhotosCntView.setText(userInfo.getPhotosCnt() + "\r\n 照片");

		mUserNameView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeName), userInfo,
				userInfo.getName());
		mUserNameView.registerListener(nameListener);
		mUserNameView.apply();

		mUserBioView = (TextView) baseView.findViewById(R.id.otherHomeBio);
		mUserBioView.setText("简介:" + userInfo.getBio());

		mUserWebsiteView = new UserTextView(
				(TextView) baseView.findViewById(R.id.otherHomeWebsite),
				userInfo, userInfo.getWebsite());
		mUserWebsiteView.registerListener(websiteListener);
		mUserWebsiteView.apply();

		config = new BitmapDisplayConfig(PhotoType.SMALL);

		if (userInfo.getTinyurl() != null) {
			async.loadDrawableFromWeb(userInfo.getTinyurl(),
					new ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {
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
					}, config);
		}

	}

	private UserStateBtn.OnObserverClickListener onObserverClickListener = new UserStateBtn.OnObserverClickListener() {

		public void OnClick(IObserver<State> observer) {
			if (mCallback != null) {
				mCallback.OnFollowClick(userInfo, observer);
			}
		}
	};

	private UserTextView.UserTextOnClickListener followerCntListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnFollowerCntClick(info);
			}
		}
	};

	private UserTextView.UserTextOnClickListener followingCntListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnFollowingCntClick(info);
			}
		}
	};

	private UserTextView.UserTextOnClickListener likeCntListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnLikesCntClick(info);
			}
		}
	};

	private UserTextView.UserTextOnClickListener nameListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnNameClick(info);
			}
		}
	};

	private UserTextView.UserTextOnClickListener websiteListener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnWebsiteClick(info);
			}
		}
	};

	private ICallback mCallback;

	public void registerCallback(ICallback callback) {
		this.mCallback = callback;
	}

	public interface ICallback {
		public void OnFollowerCntClick(UserInfo info);

		public void OnFollowingCntClick(UserInfo info);

		public void OnLikesCntClick(UserInfo info);

		public void OnNameClick(UserInfo info);

		public void OnWebsiteClick(UserInfo info);

		public void OnFollowClick(UserInfo info, IObserver<State> observer);

		public void OnUserHeadLoaded(ImageView imageView, Drawable photo,
				String url);

		public void OnDefault(ImageView imageView);
	}

}
