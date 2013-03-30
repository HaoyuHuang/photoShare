/**
 * 
 */
package com.photoshare.service.findfriends.views;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author czj_yy
 * 
 */
public class FindFriendsItemView {

	private View baseView;
	private ImageView mHeadView;
	private TextView mNameView;
	private TextView mPseudoNameView;
	private AsyncUtils async;
	private UserInfo info;

	/**
	 * @param baseView
	 * @param async
	 * @param info
	 */
	public FindFriendsItemView(View baseView, AsyncUtils async, UserInfo info) {
		super();
		this.baseView = baseView;
		this.async = async;
		this.info = info;
	}

	public void applyView() {
		mHeadView = (ImageView) baseView.findViewById(R.id.findFriendsHead);
		mNameView = (TextView) baseView.findViewById(R.id.findFriendsName);
		mPseudoNameView = (TextView) baseView
				.findViewById(R.id.findFriendsPseudoName);
		mNameView.setText(info.getName());
		mPseudoNameView.setText(info.getPseudoName());

		async.loadDrawableFromWeb(info.getTinyurl(),
				new AsyncImageLoader.ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						// TODO Auto-generated method stub
						if (onFriendsViewDisplay != null) {
							onFriendsViewDisplay.OnDisplay(mHeadView,
									imageDrawable, imageUrl);
						}
					}

					public void imageDefault() {
						// TODO Auto-generated method stub
						if (onFriendsViewDisplay != null) {
							onFriendsViewDisplay.OnDefault(mHeadView);
						}
					}
				}, BitmapDisplayConfig.SMALL);
	}

	private OnFriendsViewDisplay onFriendsViewDisplay;

	public void registerListener(OnFriendsViewDisplay onFriendsViewDisplay) {
		this.onFriendsViewDisplay = onFriendsViewDisplay;
	}

	public interface OnFriendsViewDisplay {
		public void OnDisplay(ImageView view, Drawable drawable, String url);

		public void OnDefault(ImageView view);
	}

}
