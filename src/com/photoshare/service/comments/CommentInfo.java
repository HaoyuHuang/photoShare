package com.photoshare.service.comments;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.photoshare.common.Builder;
import com.photoshare.exception.NetworkException;

public class CommentInfo implements Parcelable {
	
	public static final String KEY_COMMENTS = "comments";
	public static final String KEY_COMMENT = "comment";
	public static final String KEY_CID = "cid";
	public static final String KEY_PID = "pid";
	public static final String KEY_UID = "uid";
	public static final String KEY_UNAME = "uname";
	public static final String KEY_TINY_UHEAD = "tinyurl";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_CREATE_TIME = "createTime";

	private long cid;

	private long pid;

	private long uid;

	private String uname;

	private String tinyHead;

	private String comment;

	private String createTime;

	public CommentInfo(CommentBuilder builder) {
		this.cid = builder.cid;
		this.pid = builder.pid;
		this.uid = builder.uid;
		this.uname = builder.uname;
		this.comment = builder.comment;
		this.createTime = builder.createTime;
		this.tinyHead = builder.tinyHead;
	}

	public static class CommentBuilder implements Builder<CommentInfo> {
		private long cid;

		private long pid;

		private long uid;

		private String uname;

		private String tinyHead;

		private String comment;

		private String createTime;

		public CommentBuilder Cid(long cid) {
			this.cid = cid;
			return this;
		}

		public CommentBuilder Pid(long pid) {
			this.pid = pid;
			return this;
		}

		public CommentBuilder UName(String name) {
			this.uname = name;
			return this;
		}

		public CommentBuilder Comment(String comment) {
			this.comment = comment;
			return this;
		}

		public CommentBuilder CreateTime(String time) {
			this.createTime = time;
			return this;
		}

		public CommentBuilder Uid(long uid) {
			this.uid = uid;
			return this;
		}

		public CommentBuilder Uhead(String uhead) {
			this.tinyHead = uhead;
			return this;
		}

		public CommentInfo build() {
			return new CommentInfo(this);
		}

	}

	public CommentInfo parse(JSONObject object) throws NetworkException {

		if (object == null) {
			return null;
		}

		if (this == null) {

		}

		cid = object.optLong(KEY_CID);
		pid = object.optLong(KEY_PID);
		uid = object.optLong(KEY_UID);
		uname = object.optString(KEY_UNAME);
		tinyHead = object.optString(KEY_TINY_UHEAD);
		comment = object.optString(KEY_CONTENT);
		createTime = object.optString(KEY_CREATE_TIME);

		return this;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		StringBuffer sb = new StringBuffer();
		sb.append(KEY_CID).append(" = ").append(cid).append("\r\n");
		sb.append(KEY_PID).append(" = ").append(pid).append("\r\n");
		sb.append(KEY_UID).append(" = ").append(uid).append("\r\n");
		sb.append(KEY_UNAME).append(" = ").append(uname).append("\r\n");
		sb.append(KEY_CONTENT).append(" = ").append(comment).append("\r\n");
		sb.append(KEY_CREATE_TIME).append(" = ").append(createTime)
				.append("\r\n");

		return sb.toString();
	}

	public long getCid() {
		return cid;
	}

	public long getPid() {
		return pid;
	}

	public long getUid() {
		return uid;
	}

	public String getUname() {
		return uname;
	}

	public String getTinyHead() {
		return tinyHead;
	}

	public String getComment() {
		return comment;
	}

	public String getCreateTime() {
		return createTime;
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
		bundle.putLong(KEY_CID, cid);
		bundle.putLong(KEY_PID, pid);
		bundle.putLong(KEY_UID, uid);
		bundle.putString(KEY_CONTENT, comment);
		bundle.putString(KEY_CREATE_TIME, createTime);
		bundle.putString(KEY_TINY_UHEAD, tinyHead);
		bundle.putString(KEY_UNAME, uname);
		dest.writeBundle(bundle);
	}

	public static final Parcelable.Creator<CommentInfo> CREATOR = new Parcelable.Creator<CommentInfo>() {
		public CommentInfo createFromParcel(Parcel in) {
			return new CommentInfo(in);
		}

		public CommentInfo[] newArray(int size) {
			return new CommentInfo[size];
		}
	};

	public CommentInfo(Parcel in) {
		Bundle read = in.readBundle();
		if (read.containsKey(KEY_CID)) {
			this.cid = read.getLong(KEY_CID);
		}
		if (read.containsKey(KEY_CONTENT)) {
			this.comment = read.getString(KEY_CONTENT);
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

	/**
	 * 
	 */
	public CommentInfo() {
		// TODO Auto-generated constructor stub
	}

}
