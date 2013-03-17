/**
 * 
 */
package com.photoshare.service.findfriends.views;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.history.SearchHistory;
import com.photoshare.service.findfriends.FindFriendsRequestParam;
import com.photoshare.service.findfriends.FindFriendsResponseBean;
import com.photoshare.service.findfriends.FriendsBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.tabHost.UserHomeActivity;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 */
public class FindFriendsFragment extends BaseFragment {

	private FindFriendsView mFriendsView;
	private ArrayList<UserInfo> mFriendsList = new ArrayList<UserInfo>();
	private AsyncUtils async = AsyncUtils.getInstance();
	private SearchHistory searchHistory = SearchHistory.getInstance();
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;

	public static FindFriendsFragment newInstance(int fragmentViewId) {
		FindFriendsFragment fff = new FindFriendsFragment();
		fff.setFragmentViewId(fragmentViewId);
		return fff;
	}

	private String getFindFriendsFragment() {
		return getString(R.string.ffindFriendsFragment);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(FriendsBean.KEY_FRIENDS,
					mFriendsList);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.find_friends_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(FriendsBean.KEY_FRIENDS)) {
				mFriendsList = savedInstanceState
						.getParcelableArrayList(FriendsBean.KEY_FRIENDS);
			}
		}
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	private void initViews() {
		leftBtnText = getPreferencesText();
		titlebarText = getFriendsText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		mFriendsView = new FindFriendsView(getActivity(), getActivity()
				.findViewById(R.id.findFriendsLayoutId), mFriendsList, async);
		mFriendsView.registerListener(onSearchClick);
		mFriendsView.applyView();
	}

	private String getPreferencesText() {
		return getString(R.string.preferences);
	}

	private String getFriendsText() {
		return getString(R.string.friends);
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

	private String getPreferenceSettingsFragment() {
		return getString(R.string.fpreferenceSettingsFragment);
	}

	private void search(final String name) throws NetworkException {
		FindFriendsRequestParam param = new FindFriendsRequestParam();
		param.setuName(name);
		final AbstractRequestListener<FindFriendsResponseBean> listener = new AbstractRequestListener<FindFriendsResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_REFRESH_DATA)
									.sendToTarget();
						}
					});
				}
			}

			@Override
			public void onFault(Throwable fault) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_NETWORK).sendToTarget();
						}
					});
				}

			}

			@Override
			public void onComplete(final FindFriendsResponseBean bean) {
				if (bean != null) {
					searchHistory.put(name, bean.getFriends());
				}
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							if (bean != null) {
								mFriendsView.setmFriendsList(bean.getFriends());
							}
						}
					});
				}

			}
		};
		async.getFriendsInfo(param, listener);
	}

	private FindFriendsView.OnSearchClick onSearchClick = new FindFriendsView.OnSearchClick() {

		@SuppressWarnings("unchecked")
		public void OnSearch(View view, String str) {
			try {
				if (searchHistory.containsKey(str)) {
					mFriendsView
							.setmFriendsList((ArrayList<UserInfo>) searchHistory
									.get(str));
				} else {
					search(str);
				}
			} catch (NetworkException e) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_SIGN_IN_NULL)
						.sendToTarget();
			}
		}

		public void OnItemClicked(UserInfo info) {
			Bundle args = new Bundle();
			args.putParcelable(UserInfo.KEY_USER_INFO, info);
			Command.UserHome(getActivity(), args);
		}

		public void OnDisplay(final ImageView view, final Drawable drawable,
				String url) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						view.setImageDrawable(drawable);
					}
				});
			}
		}

		public void OnDefault(final ImageView view) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						view.setImageResource(R.drawable.icon);
					}
				});
			}
		}
	};

	private String getUserHomeFragment() {
		return getString(R.string.fuserHomeFragment);
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}

}
