/**
 * 
 */
package com.photoshare.service.likes;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.photoshare.exception.NetworkException;

/**
 * @author czj_yy
 * 
 */
public class LikeBean implements Parcelable {
	public static final String KEY_LIKE = "like";
	public static final String KEY_LIKES = "likes";
	public static final String KEY_LID = "lid";
	public static final String KEY_PID = "pid";
	public static final String KEY_UID = "uid";
	public static final String KEY_UNAME = "uname";
	public static final String KEY_TINY_UHEAD = "tinyHead";
	public static final String KEY_CREATE_TIME = "createTime";
	public static final String KEY_IS_LIKE = "isLike";
	public static final String KEY_LIKE_ACTION = "likeAction";

	private long lid;

	private long pid;

	private long uid;

	private String uname;

	private String tinyHead;

	private String createTime;

	public LikeBean() {

	}

	/**
	 * @param in
	 */
	public LikeBean(Parcel in) {
		Bundle read = in.readBundle();
		if (read.containsKey(KEY_LID)) {
			this.lid = read.getLong(KEY_LID);
		}
		if (read.containsKey(KEY_CREATE_TIME)) {
			this.createTime = read.getString(KEY_CREATE_TIME);
		}
		if (read.containsKey(KEY_PID)) {
			this.pid = read.getLong(KEY_PID);
		}
		if (read.containsKey(KEY_UID)) {
			this.uid = read.getLong(KEY_UID);
		}
		if (read.containsKey(KEY_UNAME)) {
			this.uname = read.getString(KEY_UNAME);
		}
		if (read.containsKey(KEY_TINY_UHEAD)) {
			this.tinyHead = read.getString(KEY_TINY_UHEAD);
		}
	}

	public LikeBean parse(JSONObject object) throws NetworkException {

		if (object == null) {
			return null;
		}

		if (this == null) {

		}
		lid = object.optLong(KEY_LID);
		pid = object.optLong(KEY_PID);
		uid = object.optLong(KEY_UID);
		uname = object.optString(KEY_UNAME);
		tinyHead = object.optString(KEY_TINY_UHEAD);
		createTime = object.optString(KEY_CREATE_TIME);
		return this;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		StringBuffer sb = new StringBuffer();
		sb.append(KEY_PID).append(" = ").append(pid).append("\r\n");
		sb.append(KEY_UID).append(" = ").append(uid).append("\r\n");
		sb.append(KEY_UNAME).append(" = ").append(uname).append("\r\n");
		sb.append(KEY_CREATE_TIME).append(" = ").append(createTime)
				.append("\r\n");

		return sb.toString();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putLong(KEY_LID, lid);
		bundle.putLong(KEY_PID, pid);
		bundle.putLong(KEY_UID, uid);
		bundle.putString(KEY_CREATE_TIME, createTime);
		bundle.putString(KEY_TINY_UHEAD, tinyHead);
		bundle.putString(KEY_UNAME, uname);
		dest.writeBundle(bundle);
	}

	public static final Parcelable.Creator<LikeBean> CREATOR = new Parcelable.Creator<LikeBean>() {
		public LikeBean createFromParcel(Parcel in) {
			return new LikeBean(in);
		}

		public LikeBean[] newArray(int size) {
			return new LikeBean[size];
		}
	};

	public long getLid() {
		return lid;
	}

	public void setLid(long lid) {
		this.lid = lid;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getTinyHead() {
		return tinyHead;
	}

	public void setTinyHead(String tinyHead) {
		this.tinyHead = tinyHead;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
