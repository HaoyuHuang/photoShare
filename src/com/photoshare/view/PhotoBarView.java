/**
 * 
 */
package com.photoshare.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;

/**
 * @author czj_yy
 * 
 */
public class PhotoBarView {

	private View baseView;
	private Button mShowPopularBtn;
	private Button mShowFeedsBtn;
	private Button mShowProfileBtn;
	private int fragmentViewId;
	private UserInfo userInfo;
	private PhotoType type;

	/**
	 * @param baseView
	 */
	public PhotoBarView(View baseView, PhotoType type, UserInfo userInfo) {
		super();
		this.baseView = baseView;
		this.type = type;
		this.userInfo = userInfo;
	}

	public void applyView() {
		mShowFeedsBtn = (Button) baseView.findViewById(R.id.showFeedsPhotoBtn);
		mShowPopularBtn = (Button) baseView
				.findViewById(R.id.showPopularPhotoBtn);
		mShowProfileBtn = (Button) baseView.findViewById(R.id.showProfileBtn);
		fragmentViewId = R.id.showPhotoFrameLayoutHolderId;

		mShowFeedsBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (onActionListener != null) {
					onActionListener.ShowFeedsItem();
				}
			}
		});

		mShowPopularBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (onActionListener != null) {
					onActionListener.ShowPopularItems();
				}
			}
		});

		switch (type) {
		case MyPhotos:
			mShowProfileBtn.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if (onActionListener != null) {
						onActionListener.ShowProfile(userInfo);
					}
				}
			});
			break;
		case MyLikedPhotos:
			break;
		case PopularPhotos:
			break;
		case MyFeeds:
			break;
		default:
			break;
		}

	}

	public int getFragmentViewId() {
		return fragmentViewId;
	}

	private OnActionListener onActionListener;

	public void registerListener(OnActionListener actionListener) {
		this.onActionListener = actionListener;
	}

	public interface OnActionListener {
		public void ShowPopularItems();

		public void ShowFeedsItem();

		public void ShowProfile(UserInfo info);
	}

}
