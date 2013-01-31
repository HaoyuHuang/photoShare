/**
 * 
 */
package com.photoshare.service.news.view;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.news.FollowGetNewsRequestParam;
import com.photoshare.service.news.FollowGetNewsResponseBean;
import com.photoshare.service.news.NewsBean;
import com.photoshare.service.news.NewsType;
import com.photoshare.service.news.UserGetNewsRequestParam;
import com.photoshare.service.news.UserGetNewsResponseBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 */
public class NewsFragment extends BaseFragment {

	private ArrayList<NewsBean> news;
	private NewsView mNewsView;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";

	public static NewsFragment newInstance(int fragmentViewId) {
		NewsFragment news = new NewsFragment();
		news.setFragmentViewId(fragmentViewId);
		return news;
	}

	public ArrayList<NewsBean> getNews() {
		return news;
	}

	public void setNews(ArrayList<NewsBean> news) {
		this.news = news;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(NewsBean.KEY_NEWS)) {
				news = savedInstanceState
						.getParcelableArrayList(NewsBean.KEY_NEWS);
			}
		}
		super.onActivityCreated(savedInstanceState);
		leftBtnText = getMyNewsText();
		titlebarText = getNewsText();
		rightBtnText = getFollowingNewsText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText);
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(NewsBean.KEY_NEWS)) {
				news = bundle.getParcelableArrayList(NewsBean.KEY_NEWS);
			}
		}
		try {
			if (news != null) {
				initViews();
			} else {
				AsyncGetUserNews(NewsType.MY_NEWS);
				AsyncGetFollowerNews();
			}
		} catch (NetworkException e) {
			AsyncSignIn();
		}
	}

	private void initViews() {
		Tag = getNewsFragment();
		mNewsView = new NewsView(news, getActivity().findViewById(
				R.id.newsLayoutId), getActivity(), async);
		mNewsView.registerNewsClickListener(onNewsClickListener);
		mNewsView.applyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("NewsFragment", "createTitleView");
		if (!processArguments()) {
			container.addView(super.onCreateView(inflater, container,
					savedInstanceState));
		}
		return inflater.inflate(R.layout.news_layout, container, false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(NewsBean.KEY_NEWS, news);
		}
		super.onSaveInstanceState(outState);
	}

	private String getNewsText() {
		return getString(R.string.news);
	}

	private String getMyNewsText() {
		return getString(R.string.myNews);
	}

	private String getFollowingNewsText() {
		return getString(R.string.followingNews);
	}

	@Deprecated
	private void AsyncGetFollowerNews() throws NetworkException {
		long uid = user.getUserInfo().getUid();

		FollowGetNewsRequestParam param = new FollowGetNewsRequestParam(uid);
		AbstractRequestListener<FollowGetNewsResponseBean> listener = new AbstractRequestListener<FollowGetNewsResponseBean>() {

			@Override
			public void onComplete(final FollowGetNewsResponseBean bean) {
				if (bean != null) {
					news = bean.getNews();
				}
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						initViews();
					}

				});
			}

			@Override
			public void onNetworkError(final NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_REFRESH_DATA)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

			@Override
			public void onFault(final Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

		};
		async.getFollowNews(param, listener);
	}

	private void AsyncGetUserNews(NewsType type) throws NetworkException {
		long uid = user.getUserInfo().getUid();

		UserGetNewsRequestParam param = new UserGetNewsRequestParam(uid);
		param.setType(type);
		AbstractRequestListener<UserGetNewsResponseBean> listener = new AbstractRequestListener<UserGetNewsResponseBean>() {

			@Override
			public void onComplete(final UserGetNewsResponseBean bean) {
				if (bean != null) {
					news = bean.getNews();
				}
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						initViews();
					}

				});
			}

			@Override
			public void onNetworkError(final NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_REFRESH_DATA)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

			@Override
			public void onFault(final Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

		};
		async.getUserNews(param, listener);
	}

	private NewsView.OnNewsClickListener onNewsClickListener = new NewsView.OnNewsClickListener() {

		public void OnNewsImageClick(PhotoBean photo) {
			Bundle args = new Bundle();
			args.putParcelableArrayList(NewsBean.KEY_NEWS, news);
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			forward(getFeedsItemFragment(), args);
		}

		public void OnNameClick(UserInfo info) {
			Bundle args = new Bundle();
			args.putParcelableArrayList(NewsBean.KEY_NEWS, news);
			args.putParcelable(UserInfo.KEY_USER_INFO, info);
			forward(getUserHomeFragment(), args);
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

	private String getFeedsItemFragment() {
		return getString(R.string.ffeedsItemFragment);
	}

	private String getUserHomeFragment() {
		return getString(R.string.fuserHomeFragment);
	}

	private String getNewsFragment() {
		return getString(R.string.fnewsFragment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void OnRightBtnClicked() {
		try {
			AsyncGetUserNews(NewsType.MY_NEWS);
		} catch (NetworkException e) {
			AsyncSignIn();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void OnLeftBtnClicked() {
		try {
			AsyncGetUserNews(NewsType.FOLLOWING_NEWS);
		} catch (NetworkException e) {
			AsyncSignIn();
		}
	}
}
