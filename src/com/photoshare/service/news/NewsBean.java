/**
 * 
 */
package com.photoshare.service.news;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.photoshare.exception.NetworkException;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 * 
 */
public class NewsBean implements Parcelable {

	public static final String KEY_NEWS = "news";
	public static final String KEY_USER_ID = UserInfo.KEY_UID;
	public static final String KEY_USER_NAME = UserInfo.KEY_NAME;
	public static final String KEY_URLS = PhotoBean.KEY_PHOTOS;
	public static final String KEY_EVENT_DESC = "desc";
	public static final String KEY_EVENT_TIME = "time";
	public static final String KEY_EVENT_TYPE = "type";
	private long userId;
	private String userName;
	private ArrayList<PhotoBean> photoUrls = new ArrayList<PhotoBean>();
	private String eventDesc;
	private String eventTime;
	private EventType eventType;

	public NewsBean parse(JSONObject json) throws NetworkException,
			JSONException {
		if (json == null)
			return null;
		this.userId = json.optLong(KEY_USER_ID);
		this.eventTime = json.optString(KEY_EVENT_TIME);
		this.userName = json.optString(KEY_USER_NAME);
		this.eventDesc = json.optString(KEY_EVENT_DESC);
		int type = json.optInt(KEY_EVENT_TYPE);
		eventType = EventType.Switch(type);
		JSONArray array = json.optJSONArray(KEY_URLS);
		for (int i = 0; i < array.length(); i++) {
			photoUrls.add(new PhotoBean().parse(array.getJSONObject(i)));
		}
		return this;
	}

	public NewsBean() {

	}

	/**
	 * @param in
	 */
	public NewsBean(Parcel in) {
		// TODO Auto-generated constructor stub
		Bundle bundle = in.readBundle();
		if (bundle.containsKey(KEY_EVENT_TIME)) {
			this.eventTime = bundle.getString(KEY_EVENT_TIME);
		}
		if (bundle.containsKey(KEY_EVENT_TYPE)) {
			this.eventType = EventType.Switch(bundle.getInt(KEY_EVENT_TYPE));
		}
		if (bundle.containsKey(KEY_USER_ID)) {
			this.userId = bundle.getLong(KEY_USER_ID);
		}
		if (bundle.containsKey(KEY_USER_NAME)) {
			this.userName = bundle.getString(KEY_USER_NAME);
		}
		if (bundle.containsKey(KEY_URLS)) {
			this.photoUrls = bundle.getParcelableArrayList(KEY_URLS);
		}
		if (bundle.containsKey(KEY_EVENT_DESC)) {
			this.eventDesc = bundle.getString(KEY_EVENT_DESC);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Parcelable.Creator<NewsBean> CREATOR = new Parcelable.Creator<NewsBean>() {
		public NewsBean createFromParcel(Parcel in) {
			return new NewsBean(in);
		}

		public NewsBean[] newArray(int size) {
			return new NewsBean[size];
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	public void writeToParcel(Parcel dest, int flag) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putString(KEY_USER_NAME, userName);
		bundle.putString(KEY_EVENT_TIME, eventTime);
		bundle.putLong(KEY_USER_ID, userId);
		bundle.putParcelableArrayList(KEY_URLS, photoUrls);
		bundle.putInt(KEY_EVENT_TYPE, eventType.getType());
		bundle.putString(KEY_EVENT_DESC, eventDesc);
		dest.writeBundle(bundle);
	}

	public ArrayList<PhotoBean> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(ArrayList<PhotoBean> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

}
