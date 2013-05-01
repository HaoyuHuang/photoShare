package com.photoshare.service.news;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class EventBean implements Parcelable {

	public static final String KEY_EVENT_ID = "eventId";
	public static final String KEY_EVENT_USER_ID = "eventUserId";
	public static final String KEY_EVENT_USER_NAME = "eventUserName";
	public static final String KEY_EVENT_DESCRIPTION = "eventDescription";

	private int eventUserId;

	private int eventId;

	private String eventUserName;

	private String eventDescription;

	public EventBean() {

	}

	public EventBean(int eventUserId, int eventId, String eventUserName,
			String eventDescription) {
		super();
		this.eventUserId = eventUserId;
		this.eventId = eventId;
		this.eventUserName = eventUserName;
		this.eventDescription = eventDescription;
	}

	public EventBean(Parcel parcle) {
		Bundle args = parcle.readBundle();
		eventId = args.getInt(KEY_EVENT_ID);
		eventDescription = args.getString(KEY_EVENT_DESCRIPTION);
		eventUserId = args.getInt(KEY_EVENT_USER_ID);
		eventUserName = args.getString(KEY_EVENT_USER_NAME);
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

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Parcelable.Creator<EventBean> CREATOR = new Parcelable.Creator<EventBean>() {
		public EventBean createFromParcel(Parcel in) {
			return new EventBean(in);
		}

		public EventBean[] newArray(int size) {
			return new EventBean[size];
		}
	};

	public void writeToParcel(Parcel dest, int flags) {
		Bundle bundle = new Bundle();
		bundle.putInt(KEY_EVENT_ID, eventId);
		bundle.putString(KEY_EVENT_DESCRIPTION, eventDescription);
		bundle.putString(KEY_EVENT_USER_NAME, eventUserName);
		bundle.putInt(KEY_EVENT_USER_ID, eventUserId);
		dest.writeBundle(bundle);
	}

}
