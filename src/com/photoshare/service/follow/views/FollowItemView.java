/**
 * 
 */
package com.photoshare.service.follow.views;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.common.IObserver;
import com.photoshare.msg.MsgType;
import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.State;
import com.photoshare.view.UserStateBtn;
import com.photoshare.view.UserStateBtn.OnObserverClickListener;
import com.photoshare.view.UserTextView;

/**
 * @author czj_yy
 * 
 */
public class FollowItemView {

	private UserTextView userNameView;
	private TextView userPseudoName;
	private ImageView userHead;
	private UserStateBtn userFollow;
	private UserInfo followerInfo;
	private View baseView;
	private AsyncUtils async;

	/**
	 * @param followerInfo
	 * @param baseView
	 * @param async
	 */
	public FollowItemView(UserInfo followerInfo, View baseView, AsyncUtils async) {
		super();
		this.followerInfo = followerInfo;
		this.baseView = baseView;
		this.async = async;
	}

	public void applyView() {
		userHead = (ImageView) baseView.findViewById(R.id.followItemUserHead);
		userNameView = new UserTextView(
				(TextView) baseView.findViewById(R.id.followItemUserName),
				followerInfo, followerInfo.getName());
		userPseudoName = (TextView) baseView
				.findViewById(R.id.followItemPseudoName);

		userFollow = new UserStateBtn(baseView, R.id.followItembtn,
				followerInfo.isFollowing(), MsgType.FOLLOW.getStartText(),
				MsgType.FOLLOW.getPendingText(),
				MsgType.FOLLOW.getSuccessText(), MsgType.FOLLOW.getFailText());
		userFollow.registerListener(new OnObserverClickListener() {

			public void OnClick(IObserver<State> observer) {
				// TODO Auto-generated method stub
				if (mCallback != null) {
					mCallback.OnFollowClick(followerInfo, observer);
				}
			}
		});
		userFollow.applyView();

		async.loadDrawableFromWeb(followerInfo.getTinyurl(),
				new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						if (mCallback != null) {
							mCallback.OnUserHeadLoaded(userHead, imageDrawable,
									imageUrl);
						}
					}

					public void imageDefault() {
						if (mCallback != null) {
							mCallback.OnImageDefaule(userHead);
						}
					}
				}, BitmapDisplayConfig.SMALL);
		userPseudoName.setText(followerInfo.getPseudoName());
		userNameView.registerListener(listener);
		userNameView.apply();
	}

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

		public void OnFollowClick(UserInfo info, IObserver<State> observer);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefaule(ImageView image);
	}
}
