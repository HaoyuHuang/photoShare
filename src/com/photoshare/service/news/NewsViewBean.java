package com.photoshare.service.news;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.photoshare.service.users.UserInfo;

public class NewsViewBean implements Parcelable {

	public static final String KEY_NEWS_USER_INFO = UserInfo.KEY_USER_INFO;
	public static final String KEY_NEWS_EVENT_BEAN = "eventBeans";
	public static final String KEY_NEWS_EVENT_TYPE = "eventType";
	public static final String KEY_NEWS_ACTION = "newsAction";
	public static final String KEY_NEWS_MERGE = "newsMerge";

	private UserInfo userInfo;

	private ArrayList<EventBean> eventBean;

	private EventType eventType;

	private NewsAction newsAction;

	private boolean merge;

	public NewsViewBean() {

	}

	public NewsViewBean(Parcel in) {
		Bundle args = in.readBundle();
		userInfo = args.getParcelable(KEY_NEWS_USER_INFO);
		eventBean = args.getParcelableArrayList(KEY_NEWS_EVENT_BEAN);
		eventType = EventType.Switch(args.getInt(KEY_NEWS_EVENT_TYPE));
		newsAction = NewsAction.SWITCH(args.getString(KEY_NEWS_ACTION));
		merge = args.getBoolean(KEY_NEWS_MERGE);
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

//	public NewsAction getNewsAction() {
//		return newsAction;
//	}

//	public void setNewsAction(NewsAction newsAction) {
//		this.newsAction = newsAction;
//	}

	public ArrayList<EventBean> getEventBean() {
		return eventBean;
	}

	public void setEventBean(ArrayList<EventBean> eventBean) {
		this.eventBean = eventBean;
	}

	public boolean isMerge() {
		return merge;
	}

	public void setMerge(boolean merge) {
		this.merge = merge;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Parcelable.Creator<NewsViewBean> CREATOR = new Parcelable.Creator<NewsViewBean>() {
		public NewsViewBean createFromParcel(Parcel in) {
			return new NewsViewBean(in);
		}

		public NewsViewBean[] newArray(int size) {
			return new NewsViewBean[size];
		}
	};

	public void writeToParcel(Parcel parcel, int arg1) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		if (userInfo != null) {
			bundle.putParcelable(KEY_NEWS_USER_INFO, userInfo);
		}
		if (eventBean != null) {
			bundle.putParcelableArrayList(KEY_NEWS_EVENT_BEAN, eventBean);
		}
		if (eventType != null) {
			bundle.putInt(KEY_NEWS_EVENT_TYPE, eventType.getType());
		}
		if (newsAction != null) {
			bundle.putString(KEY_NEWS_ACTION, newsAction.getTag());
		}
		bundle.putBoolean(KEY_NEWS_MERGE, merge);
		parcel.writeBundle(bundle);
	}

	@Override
	public String toString() {
		return "NewsViewBean [userInfo=" + userInfo + ", eventBean="
				+ eventBean + ", eventType=" + eventType + ", newsAction="
				+ newsAction + ", merge=" + merge + "]";
	}

}
