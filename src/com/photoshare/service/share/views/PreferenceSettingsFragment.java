package com.photoshare.service.share.views;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.fragments.stacktrace.TraceConfig;
import com.photoshare.history.SearchHistory;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.users.UserInfo;
import com.photoshare.service.users.UserPrivacyRequestParam;
import com.photoshare.service.users.UserPrivacyResponseBean;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Utils;

/**
 * @author Aron
 * 
 */
public class PreferenceSettingsFragment extends BaseFragment {

	private PreferenceSettingsView view;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;
	private ArrayList<PhotoBean> likesPhoto;

	public static PreferenceSettingsFragment newInstance(int fragmentViewId) {
		PreferenceSettingsFragment ps = new PreferenceSettingsFragment();
		ps.setFragmentViewId(fragmentViewId);
		return ps;
	}

	private void initViews() {
		leftBtnText = getHomeText();
		titlebarText = getPreferencesText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		view = new PreferenceSettingsView(getActivity().findViewById(
				R.id.preferenceSettingsLayoutId), getActivity());
		view.registerCallback(mCallback);
		view.applyView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(PhotoBean.KEY_PHOTOS)) {
				likesPhoto = savedInstanceState
						.getParcelableArrayList(PhotoBean.KEY_PHOTOS);
			}
		}
		initViews();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.preference_settings_layout, container,
				false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(PhotoBean.KEY_PHOTOS, likesPhoto);
		}
		super.onSaveInstanceState(outState);
	}

	private String getPreferencesText() {
		return getString(R.string.preferences);
	}

	private String getHomeText() {
		return getString(R.string.home);
	}

	private void setPrivacy() throws NetworkException {
		UserPrivacyRequestParam param = new UserPrivacyRequestParam();
		UserInfo info = user.getUserInfo();
		param.setPrivacy(info.isPrivacy());
		param.setUid(info.getUid());
		AbstractRequestListener<UserPrivacyResponseBean> listener = new AbstractRequestListener<UserPrivacyResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_REFRESH_DATA)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}
				});
			}

			@Override
			public void onFault(Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}
				});
			}

			@Override
			public void onComplete(UserPrivacyResponseBean bean) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}
				});
			}
		};
		async.setPrivacy(param, listener);
	}

	private PreferenceSettingsView.ICallback mCallback = new PreferenceSettingsView.ICallback() {

		public void OnUserPrivacyClicked() {
			Utils.showOptionWindow(getActivity(), "Privacy", user.getUserInfo()
					.isPrivacy() + "", new Utils.OnOptionListener() {

				public void onOK() {
					try {
						setPrivacy();
					} catch (NetworkException e) {
						AsyncSignIn();
					}
				}

				public void onCancel() {

				}
			});
		}

		public void OnSharePreferenceClicked() {
			forward(getPreferencesText(), null);
		}

		public void OnLogoutClicked() {
			Utils.showOptionWindow(getActivity(), "SignOut", "",
					new Utils.OnOptionListener() {

						public void onOK() {
							// TODO Auto-generated method stub 清理XML文件
							Command.Main(getActivity());
						}

						public void onCancel() {

						}
					});
		}

		public void OnLikedPhotosClicked() {
			Bundle param = new Bundle();
			param.putString(PhotoBean.KEY_PHOTO_TYPE,
					PhotoType.MyLikedPhotos.toString());
			param.putParcelable(UserInfo.KEY_USER_INFO, user.getUserInfo());
			param.putParcelableArrayList(PhotoBean.KEY_PHOTOS, likesPhoto);
			param.putBoolean(TraceConfig.getTrackBackward(), true);
			forward(getFeedFragment(), param);
		}

		public void OnFindFriendClicked() {
			forward(getFindFriendsFragment(), null);
		}

		public void OnClearHistoryClicked() {
			Utils.showOptionWindow(getActivity(), "History", "Clear",
					new Utils.OnOptionListener() {

						public void onOK() {
							SearchHistory.getInstance().evictAll();
						}

						public void onCancel() {

						}
					});

		}

		public void OnInviteFriendClicked() {
			// TODO Auto-generated method stub

		}

		public void OnEditProfileClicked() {
			forward(getProfileFragment(), null);
		}

		public void OnMessagesClicked() {
			// TODO Auto-generated method stub

		}
	};

	// private String getUserHomeFragment() {
	// return getString(R.string.fuserHomeFragment);
	// }

	private String getProfileFragment() {
		return getString(R.string.fpersonalProfileFragment);
	}

	private String getFindFriendsFragment() {
		return getString(R.string.ffindFriendsFragment);
	}

	private String getFeedFragment() {
		return getString(R.string.ffeedsFragment);
	}

	// private String getPhotoBarFragment() {
	// return getString(R.string.fphotoBarFragment);
	// }

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