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

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.comments.CommentAction;
import com.photoshare.service.comments.CommentInfo;
import com.photoshare.service.follow.FollowAction;
import com.photoshare.service.likes.LikeAction;
import com.photoshare.service.likes.LikeBean;
import com.photoshare.service.news.EventBean;
import com.photoshare.service.news.EventType;
import com.photoshare.service.news.FollowGetNewsRequestParam;
import com.photoshare.service.news.FollowGetNewsResponseBean;
import com.photoshare.service.news.NewsAction;
import com.photoshare.service.news.NewsBean;
import com.photoshare.service.news.NewsBeanConverter;
import com.photoshare.service.news.NewsViewBean;
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

	private ArrayList<NewsViewBean> newsUserViewBeans;
	private ArrayList<NewsViewBean> newsFollowingViewBeans;
	private ArrayList<NewsBean> userNews = new ArrayList<NewsBean>();
	private ArrayList<NewsBean> followingNews = new ArrayList<NewsBean>();
	private NewsView mNewsView;
	private NewsAction currentNewsAction = NewsAction.MY_NEWS;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";

	public static NewsFragment newInstance(int fragmentViewId) {
		NewsFragment news = new NewsFragment();
		news.setFragmentViewId(fragmentViewId);
		return news;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		leftBtnText = getMyNewsText();
		titlebarText = getNewsText();
		rightBtnText = getFollowingNewsText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText);
		Bundle bundle = getArguments();
		if (bundle != null) {
			// if (bundle.containsKey(NewsBean.KEY_USER_VIEW_NEWS)) {
			// newsUserViewBeans = bundle
			// .getParcelableArrayList(NewsBean.KEY_USER_VIEW_NEWS);
			// }
			// if (bundle.containsKey(NewsBean.KEY_USER_FOLLOWING_VIEW_NEWS)) {
			// newsFollowingViewBeans = bundle
			// .getParcelableArrayList(NewsBean.KEY_USER_FOLLOWING_VIEW_NEWS);
			// }
			if (bundle.containsKey(NewsBean.KEY_USER_NEWS)) {
				userNews = bundle
						.getParcelableArrayList(NewsBean.KEY_USER_NEWS);
			}
			if (bundle.containsKey(NewsBean.KEY_USER_FOLLOWING_NEWS)) {
				followingNews = bundle
						.getParcelableArrayList(NewsBean.KEY_USER_FOLLOWING_NEWS);
			}
		}
		try {
			if (userNews != null && userNews.size() != 0) {
				newsUserViewBeans = NewsBeanConverter.toUserNewsViewBean(
						newsUserViewBeans, userNews);
				initUserNewsView();
			} else {
				AsyncGetNews(currentNewsAction);
			}
		} catch (NetworkException e) {
			AsyncSignIn();
		}
	}

	private void initUserNewsView() {
		mNewsView = new NewsView(newsUserViewBeans, getActivity().findViewById(
				R.id.newsLayoutId), getActivity(), async);
		mNewsView.setNewsList(newsUserViewBeans);
		mNewsView.registerNewsClickListener(onNewsClickListener);
		mNewsView.applyView();
		setTitleBarDrawable(R.drawable.titlebar_right_button,
				R.drawable.titlebar_right_button);
	}

	private void initUserFollowNewsView() {
		mNewsView = new NewsView(newsFollowingViewBeans, getActivity()
				.findViewById(R.id.newsLayoutId), getActivity(), async);
		mNewsView.registerNewsClickListener(onNewsClickListener);
		mNewsView.applyView();
		setTitleBarDrawable(R.drawable.titlebar_right_button,
				R.drawable.titlebar_right_button);
	}

	private void initViews() {
		Tag = getNewsFragment();
		setTitleBarDrawable(R.drawable.titlebar_right_button,
				R.drawable.titlebar_right_button);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("NewsFragment", "createTitleView");
		if (!hideTitleBarView()) {
			container.addView(super.onCreateView(inflater, container,
					savedInstanceState));
		}
		return inflater.inflate(R.layout.news_layout, container, false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(NewsBean.KEY_USER_VIEW_NEWS,
					newsUserViewBeans);
			outState.putParcelableArrayList(
					NewsBean.KEY_USER_FOLLOWING_VIEW_NEWS,
					newsFollowingViewBeans);
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

	private String getMyNewsTitleText() {
		return getString(R.string.myNewsTitle);
	}

	private String getMyFollowingNewsTitleText() {
		return getString(R.string.myFollowerNewsTitle);
	}

	@Deprecated
	private void AsyncGetFollowerNews() throws NetworkException {
		long uid = user.getUserInfo().getUid();

		FollowGetNewsRequestParam param = new FollowGetNewsRequestParam(uid);
		AbstractRequestListener<FollowGetNewsResponseBean> listener = new AbstractRequestListener<FollowGetNewsResponseBean>() {

			@Override
			public void onComplete(final FollowGetNewsResponseBean bean) {
				if (bean != null) {
					userNews = bean.getNews();
				}
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							initViews();
						}

					});
				}
			}

			@Override
			public void onNetworkError(final NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_REFRESH_DATA)
						.sendToTarget();
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}

					});
				}
			}

			@Override
			public void onFault(final Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}

					});
				}
			}

		};
		async.getFollowNews(param, listener);
	}

	private void AsyncGetNews(final NewsAction type) throws NetworkException {
		long uid = user.getUserInfo().getUid();
		currentNewsAction = type;
		UserGetNewsRequestParam param = new UserGetNewsRequestParam.UserGetNewsBuilder()
				.DateDiff(15).UserId(uid).NewsAction(type).build();

		AbstractRequestListener<UserGetNewsResponseBean> listener = new AbstractRequestListener<UserGetNewsResponseBean>() {

			@Override
			public void onComplete(final UserGetNewsResponseBean bean) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							if (bean != null && bean.getNews() != null) {
								ArrayList<NewsBean> rinder = null;

								switch (type) {
								case FOLLOWING_NEWS:
									rinder = NewsBeanConverter.shelf(
											followingNews, bean.getNews());
									newsFollowingViewBeans = NewsBeanConverter
											.toUserFollowNewsViewBean(
													newsFollowingViewBeans,
													rinder);
									initUserFollowNewsView();
									break;
								case MY_NEWS:
									rinder = NewsBeanConverter.shelf(userNews,
											bean.getNews());
									newsUserViewBeans = NewsBeanConverter
											.toUserNewsViewBean(
													newsUserViewBeans, rinder);
									initUserNewsView();
									break;
								default:
									break;
								}
							}
						}

					});
				}
			}

			@Override
			public void onNetworkError(final NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_REFRESH_DATA)
						.sendToTarget();
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}

					});
				}
			}

			@Override
			public void onFault(final Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}

					});
				}
			}

		};
		async.getUserNews(param, listener);
	}

	private NewsView.OnNewsClickListener onNewsClickListener = new NewsView.OnNewsClickListener() {

		public void OnNewsImageClick(PhotoBean photo) {
			Bundle args = new Bundle();
			args.putParcelableArrayList(NewsBean.KEY_USER_VIEW_NEWS,
					newsUserViewBeans);
			args.putParcelableArrayList(NewsBean.KEY_USER_FOLLOWING_VIEW_NEWS,
					newsFollowingViewBeans);
			args.putParcelableArrayList(NewsBean.KEY_NEWS, userNews);
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			forward(getFeedsItemFragment(), args);
		}

		public void OnNewsUserNameClick(UserInfo info) {
			Bundle args = new Bundle();
			args.putParcelableArrayList(NewsBean.KEY_NEWS, userNews);
			args.putParcelable(UserInfo.KEY_USER_INFO, info);
			args.putParcelableArrayList(NewsBean.KEY_USER_VIEW_NEWS,
					newsUserViewBeans);
			args.putParcelableArrayList(NewsBean.KEY_USER_FOLLOWING_VIEW_NEWS,
					newsFollowingViewBeans);
			Command.UserHome(getActivity(), args);
		}

		public void OnUserHeadLoaded(final ImageView image,
				final Drawable drawable, String url) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						image.setImageDrawable(drawable);
					}
				});
			}
		}

		public void OnImageDefault(final ImageView image) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						image.setImageResource(R.drawable.icon);
					}
				});
			}
		}

		public void onNewsItemClick(NewsViewBean bean) {
			EventType eventType = bean.getEventType();
			Bundle args = new Bundle();
			switch (eventType) {
			case COMMENT:
				int eventId = 0;
				ArrayList<EventBean> eventBeans = bean.getEventBean();
				for (EventBean eventBean : eventBeans) {
					eventId = eventBean.getEventId();
				}
				PhotoBean photo = new PhotoBean.PhotoBeanBuilder()
						.UserId(bean.getUserInfo().getUid()).PhotoId(eventId)
						.build();

				args.putParcelableArrayList(NewsBean.KEY_USER_NEWS, userNews);
				args.putParcelableArrayList(NewsBean.KEY_USER_VIEW_NEWS,
						newsUserViewBeans);
				args.putParcelableArrayList(
						NewsBean.KEY_USER_FOLLOWING_VIEW_NEWS,
						newsFollowingViewBeans);
				args.putParcelable(PhotoBean.KEY_PHOTO, photo);
				args.putInt(CommentInfo.KEY_COMMENT_ACTION,
						CommentAction.DATED_COMMENTS.getCode());
				forward(getCommentsFragment(), args);
				break;
			case FOLLOW:
				args.putParcelableArrayList(NewsBean.KEY_USER_NEWS, userNews);
				args.putParcelableArrayList(NewsBean.KEY_USER_VIEW_NEWS,
						newsUserViewBeans);
				args.putParcelableArrayList(
						NewsBean.KEY_USER_FOLLOWING_VIEW_NEWS,
						newsFollowingViewBeans);
				args.putParcelable(UserInfo.KEY_USER_INFO, bean.getUserInfo());
				switch (currentNewsAction) {
				case FOLLOWING_NEWS:
					args.putInt(UserInfo.KEY_FOLLOW_ACTION,
							FollowAction.DATED_FOLLOWING.getCode());
					break;
				case MY_NEWS:
					args.putInt(UserInfo.KEY_FOLLOW_ACTION,
							FollowAction.DATED_FOLLOWER.getCode());
					break;
				default:
					break;
				}
				forward(getFollowFragment(), args);
				break;
			case LIKE:
				eventId = 0;
				eventBeans = bean.getEventBean();
				for (EventBean eventBean : eventBeans) {
					eventId = eventBean.getEventId();
				}
				photo = new PhotoBean.PhotoBeanBuilder()
						.UserId(bean.getUserInfo().getUid()).PhotoId(eventId)
						.build();
				args.putParcelableArrayList(NewsBean.KEY_USER_NEWS, userNews);
				args.putParcelableArrayList(NewsBean.KEY_USER_VIEW_NEWS,
						newsUserViewBeans);
				args.putParcelableArrayList(
						NewsBean.KEY_USER_FOLLOWING_VIEW_NEWS,
						newsFollowingViewBeans);
				args.putParcelable(PhotoBean.KEY_PHOTO, photo);
				args.putInt(LikeBean.KEY_LIKE_ACTION,
						LikeAction.DATED_LIKE.getCode());
				forward(getLikeFragment(), args);
				break;
			case NULL:
				break;
			case PHOTO:
				break;
			default:
				break;
			}
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

	private String getCommentsFragment() {
		return getString(R.string.fcommentsFragment);
	}

	private String getLikeFragment() {
		return getString(R.string.flikeFragment);
	}

	private String getFollowFragment() {
		return getString(R.string.ffollowInfoFragment);
	}

	private String getFeedFragment() {
		return getString(R.string.ffeedsFragment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked(View view) {
		try {
			titlebarText = getMyFollowingNewsTitleText();
			setTitleBarText(leftBtnText, rightBtnText, titlebarText);
			AsyncGetNews(NewsAction.MY_NEWS);
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
	protected void onLeftBtnClicked(View view) {
		try {
			titlebarText = getMyNewsTitleText();
			setTitleBarText(leftBtnText, rightBtnText, titlebarText);
			AsyncGetNews(NewsAction.FOLLOWING_NEWS);
		} catch (NetworkException e) {
			AsyncSignIn();
		}
	}

	@Override
	protected void onLoginSuccess() {

	}
}
