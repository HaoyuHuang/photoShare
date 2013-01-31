/**
 * 
 */
package com.photoshare.msg;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.photoshare.service.comments.PutCommentRequestParam;
import com.photoshare.service.follow.UserFollowRequestParam;
import com.photoshare.service.likes.LikeGetInfoRequestParam;
import com.photoshare.service.photos.PhotoUploadRequestParam;

/**
 * @author Aron
 * 
 *         MessageItem is a java bean. It contains info of what a message bean
 *         should have. It describes requests of {@link LikeGetInfoRequestParam}
 *         , {@link PutCommentRequestParam} , {@link PhotoUploadRequestParam} ,
 *         {@link UserFollowRequestParam}.
 * 
 */
public class MessageItem implements Parcelable {
	private String msgName;
	private String msgDescription;
	private String msgPhotoUrl;
	private MsgType msgType;
	private long eventId;
	private boolean btnStatus;

	/**
	 * @param msgName
	 * @param msgDescription
	 * @param msgPhotoUrl
	 * @param msgType
	 */
	public MessageItem(String msgName, String msgDescription,
			String msgPhotoUrl, MsgType msgType, long eventId, boolean btnStatus) {
		super();
		this.msgName = msgName;
		this.msgDescription = msgDescription;
		this.msgPhotoUrl = msgPhotoUrl;
		this.msgType = msgType;
		this.eventId = eventId;
		this.btnStatus = btnStatus;
	}

	public MessageItem() {

	}

	/**
	 * @param source
	 */
	public MessageItem(Parcel source) {
		Bundle bundle = source.readBundle();
		if (bundle.containsKey(MSG_BTN_STATUS)) {
			this.btnStatus = Boolean.parseBoolean(bundle
					.getString(MSG_BTN_STATUS));
		}
		if (bundle.containsKey(MSG_DESCRIPTION)) {
			this.msgDescription = bundle.getString(MSG_DESCRIPTION);
		}
		if (bundle.containsKey(MSG_EVENT_ID)) {
			this.eventId = Long.parseLong(MSG_EVENT_ID);
		}
		if (bundle.containsKey(MSG_IMAGE_URL)) {
			this.msgPhotoUrl = bundle.getString(MSG_IMAGE_URL);
		}
		if (bundle.containsKey(MSG_NAME)) {
			this.msgName = bundle.getString(MSG_NAME);
		}
		if (bundle.containsKey(MSG_TYPE)) {
			this.msgType = MsgType.SWITCH(bundle.getString(MSG_TYPE));
		}
	}

	public static final String MSG_TAG = "item";
	public static final String MSG_NAME = "name";
	public static final String MSG_DESCRIPTION = "content";
	public static final String MSG_IMAGE_URL = "url";
	public static final String MSG_TYPE = "type";
	public static final String MSG_EVENT_ID = "id";
	public static final String MSG_BTN_STATUS = "status";

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public String getMsgDescription() {
		return msgDescription;
	}

	public void setMsgDescription(String msgDescription) {
		this.msgDescription = msgDescription;
	}

	public String getMsgPhotoUrl() {
		return msgPhotoUrl;
	}

	public void setMsgPhotoUrl(String msgPhotoUrl) {
		this.msgPhotoUrl = msgPhotoUrl;
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public boolean isBtnStatus() {
		return btnStatus;
	}

	public void setBtnStatus(boolean btnStatus) {
		this.btnStatus = btnStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<MessageItem> CREATOR = new Parcelable.Creator<MessageItem>() {

		public MessageItem[] newArray(int size) {
			return new MessageItem[size];
		}

		public MessageItem createFromParcel(Parcel source) {
			return new MessageItem(source);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	public void writeToParcel(Parcel dest, int flags) {
		Bundle param = new Bundle();
		param.putBoolean(MSG_BTN_STATUS, btnStatus);
		param.putLong(MSG_EVENT_ID, eventId);
		param.putString(MSG_DESCRIPTION, msgDescription);
		param.putString(MSG_NAME, msgName);
		param.putString(MSG_IMAGE_URL, msgPhotoUrl);
		param.putString(MSG_TYPE, msgType.toString());
		param.writeToParcel(dest, flags);
	}

}
