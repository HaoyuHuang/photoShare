package com.photoshare.service.news;

import java.util.ArrayList;
import java.util.List;

import com.photoshare.service.users.UserInfo;

public class NewsBeanConverter {

	public static ArrayList<NewsBean> shelf(ArrayList<NewsBean> source,
			ArrayList<NewsBean> dest) {
		ArrayList<NewsBean> rinder = new ArrayList<NewsBean>();
		boolean exist = false;
		for (NewsBean bean : dest) {
			for (NewsBean src : source) {
				if (bean.simpleEquals(src)) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				source.add(bean);
				rinder.add(bean);
				exist = false;
			}
		}
		for (NewsBean bean : rinder) {
			System.out.println(bean);
		}
		return rinder;
	}

	public static ArrayList<NewsViewBean> toUserFollowNewsViewBean(
			ArrayList<NewsViewBean> newsViewBeans, List<NewsBean> news) {
		if (newsViewBeans == null)
			newsViewBeans = new ArrayList<NewsViewBean>();
		// NewsViewBean photoNews = new NewsViewBean();
		// NewsViewBean commentNews = new NewsViewBean();
		NewsViewBean followNews = new NewsViewBean();
		NewsViewBean likeNews = new NewsViewBean();
		boolean success = false;
		for (NewsBean bean : news) {
			switch (bean.getEventType()) {
			case COMMENT:
				break;
			case FOLLOW:
				success = mergeEventBeanByUserId(followNews, bean,
						newsViewBeans);
				if (!success) {
					followNews = new NewsViewBean();
				}
				break;
			case LIKE:
				success = mergeEventBeanByEventId(likeNews, bean, newsViewBeans);
				if (!success) {
					likeNews = new NewsViewBean();
				}
				break;
			case NULL:
				break;
			case PHOTO:
				// success = mergeEventBeanByEventId(photoNews, bean,
				// newsViewBeans);
				// if (!success) {
				// photoNews = new NewsViewBean();
				// }
				break;
			default:
				break;

			}
		}

		// for (NewsViewBean newsViewBean : newsViewBeans) {
		// System.out.println(newsViewBean);
		// }

		return newsViewBeans;
	}

	public static ArrayList<NewsViewBean> toUserNewsViewBean(
			ArrayList<NewsViewBean> newsViewBeans, List<NewsBean> news) {
		System.out.println("toUserNewsViewBean");
		if (newsViewBeans == null) {
			System.out.println("instantiate newsViewBeans");
			newsViewBeans = new ArrayList<NewsViewBean>();
		}
		// NewsViewBean photoNews = new NewsViewBean();
		NewsViewBean commentNews = new NewsViewBean();
		NewsViewBean followNews = new NewsViewBean();
		NewsViewBean likeNews = new NewsViewBean();
		for (NewsBean bean : news) {
			System.out.println(bean);
			boolean success = false;
			switch (bean.getEventType()) {
			case COMMENT:
				System.out.println("comment news");
				success = mergeEventBeanByEventId(commentNews, bean,
						newsViewBeans);
				if (!success) {
					commentNews = new NewsViewBean();
				}
				break;
			case FOLLOW:
				System.out.println("follow news");
				if (followNews.getUserInfo() == null) {
					System.out.println("initialize follow news");
					UserInfo userInfo = new UserInfo.UserInfoBuilder()
							.ID(bean.getUserId())
							.TinyHeadUrl(bean.getTinyHeadUrl())
							.Name(bean.getUserName()).build();
					ArrayList<EventBean> eventBeans = new ArrayList<EventBean>();
					followNews.setMerge(true);
					followNews.setEventBean(eventBeans);
					followNews.setUserInfo(userInfo);
					followNews.setEventType(bean.getEventType());
					newsViewBeans.add(followNews);
				}
				mergeAll(followNews, bean);
				break;
			case LIKE:
				System.out.println("like news");
				success = mergeEventBeanByEventId(likeNews, bean, newsViewBeans);
				if (!success) {
					likeNews = new NewsViewBean();
				}
				break;
			case NULL:
				break;
			case PHOTO:
				break;
			default:
				break;
			}
		}

		for (NewsViewBean newsViewBean : newsViewBeans) {
			System.out.println(newsViewBean);
		}

		return newsViewBeans;
	}

	private static boolean mergeAll(NewsViewBean viewBean, NewsBean newsBean) {
		ArrayList<EventBean> eventBeans = viewBean.getEventBean();
		eventBeans.add(new EventBean(newsBean.getEventUserId(), newsBean
				.getEventId(), newsBean.getEventUserName(), newsBean
				.getEventDescription()));
		return true;
	}

	/**
	 * @param viewBean
	 *            the current News View Bean
	 * @param bean
	 *            the current News Bean to convert
	 * @param newsViewBeans
	 * @return
	 */
	private static boolean mergeEventBeanByEventId(NewsViewBean viewBean,
			NewsBean bean, List<NewsViewBean> newsViewBeans) {

		int eventId = bean.getEventId();

		// merge into the existing News View Beans
		for (NewsViewBean newsViewBean : newsViewBeans) {
			if (newsViewBean.getEventType().equals(bean.getEventType())) {
				ArrayList<EventBean> eventBeans = newsViewBean.getEventBean();
				for (EventBean eventBean : eventBeans) {
					if (eventBean.getEventId() == eventId) {
						eventBeans.add(new EventBean(bean.getEventUserId(),
								bean.getEventId(), bean.getEventUserName(),
								bean.getEventDescription()));
						return true;
					}
				}
			}
		}

		UserInfo userInfo = viewBean.getUserInfo();
		ArrayList<EventBean> eventBeans = viewBean.getEventBean();
		// initialize
		if (userInfo == null) {
			userInfo = new UserInfo.UserInfoBuilder().ID(bean.getUserId())
					.TinyHeadUrl(bean.getTinyHeadUrl())
					.Name(bean.getUserName()).build();
			eventBeans = new ArrayList<EventBean>();

			EventBean eventBean = new EventBean(bean.getEventUserId(),
					bean.getEventId(), bean.getEventUserName(),
					bean.getEventDescription());
			eventBeans.add(eventBean);
			viewBean.setMerge(true);
			viewBean.setEventBean(eventBeans);
			viewBean.setUserInfo(userInfo);
			viewBean.setEventType(bean.getEventType());
			newsViewBeans.add(viewBean);
			return true;
		}

		// judge if the eventId changed
		for (EventBean eventBean : eventBeans) {
			if (eventBean.getEventId() != bean.getEventId()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param viewBean
	 *            the current News View Bean
	 * @param bean
	 *            the current News Bean to convert
	 * @param newsViewBeans
	 * @return
	 */
	private static boolean mergeEventBeanByUserId(NewsViewBean viewBean,
			NewsBean bean, List<NewsViewBean> newsViewBeans) {

		int userId = bean.getUserId();

		// merge into the existing News View Beans
		for (NewsViewBean newsViewBean : newsViewBeans) {
			if (newsViewBean.getEventType().equals(bean.getEventType())) {
				if (newsViewBean.getUserInfo().getUid() == userId) {
					newsViewBean.getEventBean().add(
							new EventBean(bean.getEventUserId(), bean
									.getEventId(), bean.getEventUserName(),
									bean.getEventDescription()));
					return true;
				}
			}
		}

		UserInfo userInfo = viewBean.getUserInfo();
		ArrayList<EventBean> eventBeans = viewBean.getEventBean();
		// initialize
		if (userInfo == null) {
			userInfo = new UserInfo.UserInfoBuilder().ID(bean.getUserId())
					.TinyHeadUrl(bean.getTinyHeadUrl())
					.Name(bean.getUserName()).build();
			eventBeans = new ArrayList<EventBean>();

			EventBean eventBean = new EventBean(bean.getEventUserId(),
					bean.getEventId(), bean.getEventUserName(),
					bean.getEventDescription());
			eventBeans.add(eventBean);
			viewBean.setMerge(true);
			viewBean.setEventBean(eventBeans);
			viewBean.setUserInfo(userInfo);
			viewBean.setEventType(bean.getEventType());
			newsViewBeans.add(viewBean);
			return true;
		}

		// judge if the userId changed
		if (viewBean.getUserInfo().getUid() != userId) {
			return false;
		}
		return true;
	}
}
