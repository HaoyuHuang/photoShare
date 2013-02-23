/**
 * 
 */
package com.photoshare.service.share;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Aron
 * 
 */
public class ShareBean implements Parcelable {
	private ShareType mShareType;
	private String mShareAccount;
	private String mSharePwd;
	
	public static final String KEY_SINA_WEIBO_REDIRECT_URL = "http://www.sina.com";

	public static final String KEY_SHARE_BEANS = "shares";
	public static final String KEY_SHARE_BEAN = "share";
	public static final String KEY_SHARE_TYPE = "type";
	public static final String KEY_SHARE_ACCOUNT = "account";
	public static final String KEY_SHARE_PWD = "pwd";

	/**
	 * @param in
	 */
	public ShareBean(Parcel in) {
		// TODO Auto-generated constructor stub
		Bundle read = in.readBundle();
		if (read.containsKey(KEY_SHARE_ACCOUNT)) {
			mShareAccount = read.getString(KEY_SHARE_ACCOUNT);
		}
		if (read.containsKey(KEY_SHARE_PWD)) {
			mSharePwd = read.getString(KEY_SHARE_PWD);
		}
		if (read.containsKey(KEY_SHARE_TYPE)) {
			mShareType = ShareType.Switch(read.getInt(KEY_SHARE_TYPE));
		}
	}

	/**
	 * 
	 */
	public ShareBean() {
		// TODO Auto-generated constructor stub
	}

	public ShareType getmShareType() {
		return mShareType;
	}

	public void setmShareType(ShareType mShareType) {
		this.mShareType = mShareType;
	}

	public String getmShareAccount() {
		return mShareAccount;
	}

	public void setmShareAccount(String mShareAccount) {
		this.mShareAccount = mShareAccount;
	}

	public String getmSharePwd() {
		return mSharePwd;
	}

	public void setmSharePwd(String mSharePwd) {
		this.mSharePwd = mSharePwd;
	}

	public boolean isValid() {
		if (mShareAccount != null && mSharePwd != null) {
			if (!mShareAccount.equals("") && !mSharePwd.equals("")) {
				return true;
			}
		}
		return false;
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
		bundle.putString(KEY_SHARE_ACCOUNT, mShareAccount);
		bundle.putString(KEY_SHARE_PWD, mSharePwd);
		bundle.putInt(KEY_SHARE_TYPE, mShareType.getType());
		dest.writeBundle(bundle);
	}

	public static final Parcelable.Creator<ShareBean> CREATOR = new Parcelable.Creator<ShareBean>() {
		public ShareBean createFromParcel(Parcel in) {
			return new ShareBean(in);
		}

		public ShareBean[] newArray(int size) {
			return new ShareBean[size];
		}
	};

	/**
	 * @return
	 */
	public Bundle param() {
		Bundle param = new Bundle();
		param.putParcelable(KEY_SHARE_BEAN, this);
		return param;
	}

}
