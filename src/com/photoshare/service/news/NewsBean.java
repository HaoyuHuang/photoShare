/**
 * 
 */
package com.photoshare.service.news;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 * 
 */
public class NewsBean implements Parcelable {

	public static final String KEY_USER_VIEW_NEWS = "userNewsView";
	public static final String KEY_USER_FOLLOWING_VIEW_NEWS = "followingNewsView";
	public static final String KEY_USER_NEWS = "userNews";
	public static final String KEY_USER_FOLLOWING_NEWS = "followingNews";
	public static final String KEY_NEWS = "news";
	public static final String KEY_USER_ID = UserInfo.KEY_UID;
	public static final String KEY_USER_NAME = "userName";
	public static final String KEY_EVENT_USER_NAME = "eventUserName";
	public static final String KEY_EVENT_DESC = "eventDescription";
	public static final String KEY_EVENT_TIME = "time";
	public static final String KEY_EVENT_TYPE = "newsType";
	public static final String KEY_EVENT_USER_ID = "eventUserId";
	public static final String KEY_EVENT_ID = "eventId";
	public static final String KEY_TINY_HEAD_URL = "tinyHeadUrl";
	public static final String KEY_EVENT_KEY_ID = "eventKeyId";

	private int eventKeyId;
	private int userId;
	private EventType eventType;
	private int eventUserId;
	private int eventId;
	private String eventDescription;
	private String userName;
	private String eventUserName;
	private String tinyHeadUrl;

	public NewsBean parse(JSONObject json) throws NetworkException,
			JSONException {
		if (json == null)
			return null;
		this.userId = json.optInt(KEY_USER_ID);
		this.userName = json.optString(KEY_USER_NAME);
		this.eventDescription = json.optString(KEY_EVENT_DESC);
		this.eventId = json.optInt(KEY_EVENT_ID);
		int type = json.optInt(KEY_EVENT_TYPE);
		eventType = EventType.Switch(type);
		this.tinyHeadUrl = json.optString(KEY_TINY_HEAD_URL);
		this.eventUserId = json.optInt(KEY_EVENT_USER_ID);
		this.eventUserName = json.optString(KEY_EVENT_USER_NAME);
		this.eventKeyId = json.optInt(KEY_EVENT_KEY_ID);
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

		}
		if (bundle.containsKey(KEY_EVENT_TYPE)) {
			this.eventType = EventType.Switch(bundle.getInt(KEY_EVENT_TYPE));
		}
		if (bundle.containsKey(KEY_USER_ID)) {
			this.userId = bundle.getInt(KEY_USER_ID);
		}
		if (bundle.containsKey(KEY_USER_NAME)) {
			this.userName = bundle.getString(KEY_USER_NAME);
		}
		if (bundle.containsKey(KEY_EVENT_DESC)) {
			this.eventDescription = bundle.getString(KEY_EVENT_DESC);
		}
		if (bundle.containsKey(KEY_EVENT_USER_ID)) {
			this.eventUserId = bundle.getInt(KEY_EVENT_USER_ID);
		}
		if (bundle.containsKey(KEY_EVENT_USER_NAME)) {
			this.eventUserName = bundle.getString(KEY_EVENT_USER_NAME);
		}
		if (bundle.containsKey(KEY_EVENT_KEY_ID)) {
			this.eventKeyId = bundle.getInt(KEY_EVENT_KEY_ID);
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
		// bundle.putString(KEY_EVENT_TIME, eventTime);
		bundle.putInt(KEY_USER_ID, userId);
		// bundle.putParcelableArrayList(KEY_URLS, photoUrls);
		bundle.putString(KEY_TINY_HEAD_URL, tinyHeadUrl);
		bundle.putInt(KEY_EVENT_ID, eventId);
		bundle.putInt(KEY_EVENT_TYPE, eventType.getType());
		bundle.putString(KEY_EVENT_DESC, eventDescription);
		bundle.putInt(KEY_EVENT_USER_ID, eventUserId);
		bundle.putString(KEY_EVENT_USER_NAME, eventUserName);
		bundle.putInt(KEY_EVENT_KEY_ID, eventKeyId);
		dest.writeBundle(bundle);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTinyHeadUrl() {
		return tinyHeadUrl;
	}

	public void setTinyHeadUrl(String tinyHeadUrl) {
		this.tinyHeadUrl = tinyHeadUrl;
	}

	public int getEventUserId() {
		return eventUserId;
	}

	public void setEventUserId(int eventUserId) {
		this.eventUserId = eventUserId;
	}

	public String getEventUserName() {
		return eventUserName;
	}

	public void setEventUserName(String eventUserName) {
		this.eventUserName = eventUserName;
	}

	public int getEventKeyId() {
		return eventKeyId;
	}

	public void setEventKeyId(int eventKeyId) {
		this.eventKeyId = eventKeyId;
	}

	@Override
	public String toString() {
		return "NewsBean [eventKeyId=" + eventKeyId + ", userId=" + userId
				+ ", eventType=" + eventType + ", eventUserId=" + eventUserId
				+ ", eventId=" + eventId + ", eventDescription="
				+ eventDescription + ", userName=" + userName
				+ ", eventUserName=" + eventUserName + ", tinyHeadUrl="
				+ tinyHeadUrl + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((eventDescription == null) ? 0 : eventDescription.hashCode());
		result = prime * result + eventId;
		result = prime * result + eventKeyId;
		result = prime * result
				+ ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + eventUserId;
		result = prime * result
				+ ((eventUserName == null) ? 0 : eventUserName.hashCode());
		result = prime * result
				+ ((tinyHeadUrl == null) ? 0 : tinyHeadUrl.hashCode());
		result = prime * result + userId;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	public boolean simpleEquals(NewsBean bean) {
		if (bean == null)
			return false;
		if (eventType.equals(bean.getEventType())
				&& eventKeyId == bean.getEventKeyId()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsBean other = (NewsBean) obj;
		if (eventDescription == null) {
			if (other.eventDescription != null)
				return false;
		} else if (!eventDescription.equals(other.eventDescription))
			return false;
		if (eventId != other.eventId)
			return false;
		if (eventKeyId != other.eventKeyId)
			return false;
		if (eventType != other.eventType)
			return false;
		if (eventUserId != other.eventUserId)
			return false;
		if (eventUserName == null) {
			if (other.eventUserName != null)
				return false;
		} else if (!eventUserName.equals(other.eventUserName))
			return false;
		if (tinyHeadUrl == null) {
			if (other.tinyHeadUrl != null)
				return false;
		} else if (!tinyHeadUrl.equals(other.tinyHeadUrl))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
