/**
 * 
 */
package com.photoshare.service.likes.view;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.likes.LikeBean;
import com.photoshare.service.likes.LikeGetInfoRequestParam;
import com.photoshare.service.likes.LikeGetInfoResponseBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Utils;
import com.photoshare.view.AppTitleBarView;

/**
 * @author Aron
 * 
 */
public class LikesFragment extends BaseFragment {

	private LikesView likesView;
	private PhotoBean photo;
	private ArrayList<LikeBean> beans;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;

	public static LikesFragment newInstance(int fragmentViewId) {
		LikesFragment lf = new LikesFragment();
		lf.setFragmentViewId(fragmentViewId);
		return lf;
	}

	private String getLikesFragment() {
		return getString(R.string.flikeFragment);
	}

	public PhotoBean getPhoto() {
		return photo;
	}

	private void initViews() {
		try {
			titlebarText = getLikeTitleText();
			initTitleBar(leftBtnText, rightBtnText, titlebarText,
					leftBtnVisibility, rightBtnVisibility);
			if (beans != null) {
				likesView = new LikesView(getActivity(), getActivity()
						.findViewById(R.id.likeListLayoutId), beans, async);
				likesView.registerCallback(mCallback);
				likesView.applyView();
			} else {
				AsyncGetLikeInfo();
			}
		} catch (NetworkException e) {

		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		leftBtnText = getBackText();
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(LikeBean.KEY_LIKES)) {
				beans = savedInstanceState
						.getParcelableArrayList(LikeBean.KEY_LIKES);
			}
			if (savedInstanceState.containsKey(AppTitleBarView.LEFT_BTN_TEXT)) {
				leftBtnText = savedInstanceState
						.getString(AppTitleBarView.LEFT_BTN_TEXT);
			}
		}
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(PhotoBean.KEY_PHOTO)) {
				photo = bundle.getParcelable(PhotoBean.KEY_PHOTO);
			}
			if (bundle.containsKey(AppTitleBarView.LEFT_BTN_TEXT)) {
				leftBtnText = bundle.getString(AppTitleBarView.LEFT_BTN_TEXT);
			}
		}
		initViews();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.likes_layout, container, false);
	}

	private String getLikeTitleText() {
		return getString(R.string.like);
	}

	private String getBackText() {
		return getString(R.string.back);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(LikeBean.KEY_LIKES, beans);
			outState.putString(AppTitleBarView.LEFT_BTN_TEXT, leftBtnText);
		}
		super.onSaveInstanceState(outState);
	}

	private void AsyncGetLikeInfo() throws NetworkException {
		Utils.logger("LikeFragment: " + photo.getPid());
		LikeGetInfoRequestParam param = new LikeGetInfoRequestParam(
				photo.getPid());
		AbstractRequestListener<LikeGetInfoResponseBean> listener = new AbstractRequestListener<LikeGetInfoResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						mExceptionHandler.obtainMessage(
								NetworkError.ERROR_REFRESH_DATA).sendToTarget();
					}

				});
			}

			@Override
			public void onFault(Throwable fault) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						mExceptionHandler.obtainMessage(
								NetworkError.ERROR_NETWORK).sendToTarget();
					}

				});
			}

			@Override
			public void onComplete(LikeGetInfoResponseBean bean) {
				if (bean != null) {
					beans = bean.getLikesBean();
				}
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						initViews();
					}

				});
			}
		};
		async.getLikesInfo(param, listener);
	}

	private LikesView.ICallback mCallback = new LikesView.ICallback() {

		public void OnLikeNameClick(UserInfo info) {
			forward(getUserHomeFragment(), info.params());
		}

		public void OnUserHeadLoaded(final ImageView image,
				final Drawable drawable, String url) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					image.setImageDrawable(drawable);
				}
			});
		}

		public void OnImageDefault(final ImageView image) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					image.setImageResource(R.drawable.icon);
				}
			});
		}
	};

	private String getUserHomeFragment() {
		return getString(R.string.fuserHomeFragment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void onLeftBtnClicked() {
		backward(null);
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub
		
	}
}
