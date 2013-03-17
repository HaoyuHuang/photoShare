package com.photoshare.service.users;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.photoshare.common.Builder;
import com.photoshare.exception.NetworkException;

public class UserInfo implements Parcelable {

	public static final String KEY_USER_INFOS = "users";
	public static final String KEY_USER_INFO = "userInfo";
	public static final String KEY_UID = "uid";
	public static final String KEY_NAME = "name";
	public static final String KEY_PSEUDO_NAME = "pseudoname";
	public static final String KEY_MAIL = "mail";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_BIRTHDAY = "birthday";
	public static final String KEY_WEBSITE = "website";
	public static final String KEY_BIO = "bio";
	public static final String KEY_PHONE_NUMBER = "phone";
	public static final String KEY_PRIVACY = "privacy";
	public static final String KEY_TINY_HEAD_URL = "tinyurl";
	public static final String KEY_MIDDLE_HEAD_URL = "headurl";
	public static final String KEY_LARGE_HEAD_URL = "largeurl";
	public static final String KEY_FOLLOWER_CNT = "follower";
	public static final String KEY_FOLLOWING_CNT = "following";
	public static final String KEY_IS_FOLLOWING = "isFollowing";
	public static final String KEY_FOLLOW = "follow";
	public static final String KEY_PHOTOS_CNT = "photosCnt";
	public static final String KEY_LIKES_CNT = "likesCnt";
	public static final String KEY_FOLLOW_TYPE = "type";

	private long uid;

	private String name;

	private String pseudoName;
	// 男male, 女female
	private String gender;

	private String mail;

	private String birthday = "未填写";

	private String website = "未填写";

	private String bio = "未填写";

	private String phoneNumber = "未填写";
	// 0表示禁止跟随，1表示允许跟随
	private boolean privacy;

	public static final int PRIVACY_ON = 0;
	public static final int PRIVACY_OFF = 1;
	/*
	 * 50*50
	 */
	private String tinyurl;

	/*
	 * 100*100
	 */
	private String headurl;

	/*
	 * 200*200
	 */
	private String largeurl;

	private int photosCnt;

	private int followersCnt;

	private boolean isFollowing;

	private int followingCnt;

	private long likesCnt;

	/**
	 * @param in
	 */
	public UserInfo(Parcel in) {
		// TODO Auto-generated constructor stub
		Bundle bundle = in.readBundle();
		uid = bundle.getLong(KEY_UID);
		birthday = bundle.getString(KEY_BIRTHDAY);
		followersCnt = bundle.getInt(KEY_FOLLOWER_CNT);
		followingCnt = bundle.getInt(KEY_FOLLOWING_CNT);
		gender = bundle.getString(KEY_GENDER);
		largeurl = bundle.getString(KEY_LARGE_HEAD_URL);
		mail = bundle.getString(KEY_MAIL);
		headurl = bundle.getString(KEY_MIDDLE_HEAD_URL);
		name = bundle.getString(KEY_NAME);
		pseudoName = bundle.getString(KEY_PSEUDO_NAME);
		phoneNumber = bundle.getString(KEY_PHONE_NUMBER);
		photosCnt = bundle.getInt(KEY_PHOTOS_CNT);
		privacy = bundle.getBoolean(KEY_PRIVACY);
		tinyurl = bundle.getString(KEY_TINY_HEAD_URL);
		website = bundle.getString(KEY_WEBSITE);
		bio = bundle.getString(KEY_BIO);
		likesCnt = bundle.getLong(KEY_LIKES_CNT);
	}

	public UserInfo() {

	}

	/**
	 * @param userInfoBuilder
	 */
	public UserInfo(UserInfoBuilder userInfoBuilder) {
		// TODO Auto-generated constructor stub
		this.bio = userInfoBuilder.bio;
		this.birthday = userInfoBuilder.birthday;
		this.followersCnt = userInfoBuilder.followerCnt;
		this.gender = userInfoBuilder.gender;
		this.followingCnt = userInfoBuilder.followingCnt;
		this.headurl = userInfoBuilder.headurl;
		this.largeurl = userInfoBuilder.largeurl;
		this.mail = userInfoBuilder.mail;
		this.name = userInfoBuilder.name;
		this.phoneNumber = userInfoBuilder.phoneNumber;
		this.photosCnt = userInfoBuilder.photosCnt;
		this.privacy = userInfoBuilder.privacy;
		this.tinyurl = userInfoBuilder.tinyurl;
		this.uid = userInfoBuilder.uid;
		this.website = userInfoBuilder.website;
		this.likesCnt = userInfoBuilder.likesCnt;
		this.pseudoName = userInfoBuilder.pseudoName;
		this.isFollowing = userInfoBuilder.isFollowing;
	}

	public static class UserInfoBuilder implements Builder<UserInfo> {
		private long uid;
		private String name;
		private String gender;
		private String mail;
		private String birthday;
		private String website;
		private String bio;
		private String phoneNumber;
		private boolean privacy;
		private String tinyurl;
		private String headurl;
		private String largeurl;
		private int followerCnt;
		private int followingCnt;
		private int photosCnt;
		private long likesCnt;
		private String pseudoName;
		private boolean isFollowing;

		public UserInfoBuilder isFollowing(boolean isFollowing) {
			this.isFollowing = isFollowing;
			return this;
		}

		public UserInfoBuilder ID(long uid) {
			this.uid = uid;
			return this;
		}

		public UserInfoBuilder Name(String name) {
			this.name = name;
			return this;
		}

		public UserInfoBuilder PseudoName(String pseudoName) {
			this.pseudoName = pseudoName;
			return this;
		}

		public UserInfoBuilder Gender(String gender) {
			this.gender = gender;
			return this;
		}

		public UserInfoBuilder Mail(String mail) {
			this.mail = mail;
			return this;
		}

		public UserInfoBuilder Birthday(String birthday) {
			this.birthday = birthday;
			return this;
		}

		public UserInfoBuilder Bio(String bio) {
			this.bio = bio;
			return this;
		}

		public UserInfoBuilder FollowerCnt(int followerCnt) {
			this.followerCnt = followerCnt;
			return this;
		}

		public UserInfoBuilder FollowingCnt(int followingCnt) {
			this.followingCnt = followingCnt;
			return this;
		}

		public UserInfoBuilder HeadUrl(String url) {
			this.headurl = url;
			return this;
		}

		public UserInfoBuilder LargeHeadUrl(String url) {
			this.largeurl = url;
			return this;
		}

		public UserInfoBuilder Phone(String phone) {
			this.phoneNumber = phone;
			return this;
		}

		public UserInfoBuilder PhotosCnt(int photosCnt) {
			this.photosCnt = photosCnt;
			return this;
		}

		public UserInfoBuilder Privacy(boolean privacy) {
			this.privacy = privacy;
			return this;
		}

		public UserInfoBuilder TinyHeadUrl(String url) {
			this.tinyurl = url;
			return this;
		}

		public UserInfoBuilder Website(String url) {
			this.website = url;
			return this;
		}

		public UserInfoBuilder LikesCnt(long likesCnt) {
			this.likesCnt = likesCnt;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.photoshare.common.Builder#build()
		 */
		public UserInfo build() {
			// TODO Auto-generated method stub
			return new UserInfo(this);
		}

	}

	public UserInfo parse(JSONObject object) throws NetworkException {

		if (object == null)
			return null;

		uid = object.optLong(KEY_UID);
		name = object.optString(KEY_NAME);
		pseudoName = object.optString(KEY_PSEUDO_NAME);
		mail = object.optString(KEY_MAIL);
		gender = object.optString(KEY_GENDER);
		phoneNumber = object.optString(KEY_PHONE_NUMBER);
		birthday = object.optString(KEY_BIRTHDAY);
		website = object.optString(KEY_WEBSITE);
		bio = object.optString(KEY_BIO);
		privacy = object.optBoolean(KEY_PRIVACY);
		tinyurl = object.optString(KEY_TINY_HEAD_URL);
		headurl = object.optString(KEY_MIDDLE_HEAD_URL);
		largeurl = object.optString(KEY_LARGE_HEAD_URL);
		likesCnt = object.optLong(KEY_LIKES_CNT);
		followersCnt = object.optInt(KEY_FOLLOWER_CNT);
		followingCnt = object.optInt(KEY_FOLLOWING_CNT);
		photosCnt = object.optInt(KEY_PHOTOS_CNT);
		isFollowing = object.optBoolean(KEY_IS_FOLLOWING);
		return this;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		StringBuffer sb = new StringBuffer();
		sb.append(KEY_UID).append(" = ").append(uid).append("\r\n");
		sb.append(KEY_NAME).append(" = ").append(name).append("\r\n");
		sb.append(KEY_MAIL).append(" = ").append(mail).append("\r\n");
		sb.append(KEY_GENDER).append(" = ").append(gender).append("\r\n");
		sb.append(KEY_PHONE_NUMBER).append(" = ").append(phoneNumber)
				.append("\r\n");
		sb.append(KEY_BIRTHDAY).append(" = ").append(birthday).append("\r\n");
		sb.append(KEY_WEBSITE).append(" = ").append(website).append("\r\n");
		sb.append(KEY_BIO).append(" = ").append(bio).append("\r\n");
		sb.append(KEY_PRIVACY).append(" = ").append(privacy).append("\r\n");
		sb.append(KEY_TINY_HEAD_URL).append(" = ").append(tinyurl)
				.append("\r\n");
		sb.append(KEY_MIDDLE_HEAD_URL).append(" = ").append(headurl)
				.append("\r\n");
		sb.append(KEY_LARGE_HEAD_URL).append(" = ").append(largeurl)
				.append("\r\n");
		sb.append(KEY_FOLLOWER_CNT).append(" = ").append(followersCnt)
				.append("\r\n");
		sb.append(KEY_FOLLOWING_CNT).append(" = ").append(followingCnt)
				.append("\r\n");
		sb.append(KEY_LIKES_CNT).append(" = ").append(likesCnt).append("\r\n");
		sb.append(KEY_PHOTOS_CNT).append(" = ").append(photosCnt)
				.append("\r\n");
		return sb.toString();

	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	public String getTinyurl() {
		return tinyurl;
	}

	public void setTinyurl(String tinyurl) {
		this.tinyurl = tinyurl;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getLargeurl() {
		return largeurl;
	}

	public void setLargeurl(String largeurl) {
		this.largeurl = largeurl;
	}

	public int getFollowersCnt() {
		return followersCnt;
	}

	public void setFollowersCnt(int followersCnt) {
		this.followersCnt = followersCnt;
	}

	public boolean isFollowing() {
		return isFollowing;
	}

	public void setFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}

	public int getFollowingCnt() {
		return followingCnt;
	}

	public void setFollowingCnt(int followingCnt) {
		this.followingCnt = followingCnt;
	}

	public int getPhotosCnt() {
		return photosCnt;
	}

	public void setPhotosCnt(int photosCnt) {
		this.photosCnt = photosCnt;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public long getLikesCnt() {
		return likesCnt;
	}

	public void setLikesCnt(long likesCnt) {
		this.likesCnt = likesCnt;
	}

	public String getPseudoName() {
		return pseudoName;
	}

	public void setPseudoName(String pseudoName) {
		this.pseudoName = pseudoName;
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
		bundle.putLong(KEY_UID, uid);
		bundle.putString(KEY_BIRTHDAY, birthday);
		bundle.putBoolean(KEY_IS_FOLLOWING, isFollowing);
		bundle.putInt(KEY_FOLLOWER_CNT, followersCnt);
		bundle.putInt(KEY_FOLLOWING_CNT, followingCnt);
		bundle.putString(KEY_GENDER, gender);
		bundle.putString(KEY_LARGE_HEAD_URL, largeurl);
		bundle.putString(KEY_MAIL, mail);
		bundle.putString(KEY_MIDDLE_HEAD_URL, headurl);
		bundle.putString(KEY_NAME, name);
		bundle.putString(KEY_PHONE_NUMBER, phoneNumber);
		bundle.putInt(KEY_PHOTOS_CNT, photosCnt);
		bundle.putBoolean(KEY_PRIVACY, privacy);
		bundle.putString(KEY_TINY_HEAD_URL, tinyurl);
		bundle.putString(KEY_WEBSITE, website);
		bundle.putString(KEY_BIO, bio);
		bundle.putLong(KEY_LIKES_CNT, likesCnt);
		bundle.putString(KEY_PSEUDO_NAME, pseudoName);
		dest.writeBundle(bundle);
	}

	public Bundle params() {
		Bundle param = new Bundle();
		param.putParcelable(UserInfo.KEY_USER_INFO, this);
		return param;
	}

	public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
		public UserInfo createFromParcel(Parcel in) {
			return new UserInfo(in);
		}

		public UserInfo[] newArray(int size) {
			return new UserInfo[size];
		}
	};

}
