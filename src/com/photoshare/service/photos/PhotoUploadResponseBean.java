package com.photoshare.service.photos;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.photoshare.common.ResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;

/**
 * 上传图片请求的返回结果包装类
 * 
 * 
 */
public class PhotoUploadResponseBean extends ResponseBean implements Parcelable {

	/**
	 * 字段常量
	 */
	private static final String KEY_PID = PhotoBean.KEY_PID;
	private static final String KEY_UID = PhotoBean.KEY_UID;
	private static final String KEY_SRC = PhotoBean.KEY_MIDDLE_URL;
	private static final String KEY_SRC_SMALL = PhotoBean.KEY_TINY_URL;
	private static final String KEY_SRC_BIG = PhotoBean.KEY_LARGE_URL;
	private static final String KEY_CAPTION = PhotoBean.KEY_CAPTION;

	/**
	 * 照片pid
	 */
	private long pid;
	/**
	 * 照片所有者uid
	 */
	private long uid;
	/**
	 * 正常尺寸照片源url
	 */
	private String src;
	/**
	 * 小尺寸照片源url
	 */
	private String src_small;
	/**
	 * 大尺寸照片源url
	 */
	private String src_big;
	/**
	 * 照片描述
	 */
	private String caption;

	/**
	 * 构造函数，将请求返回的json串格式数据解析成对象
	 * 
	 * @param response
	 */
	public PhotoUploadResponseBean(String response) {
		this(response, User.RESPONSE_FORMAT_JSON);
	}

	/**
	 * 构造函数，将请求返回的json串格式数据解析成对象
	 * 
	 * @param response
	 *            服务器返回的请求结果串
	 * @param format
	 *            服务器返回结果的格式
	 */
	private PhotoUploadResponseBean(String response, String format) {
		super(response);

		if (response == null) {
			return;
		}

		// 暂时只提供json格式的数据解析
		if (format.toLowerCase().endsWith("json")) {
			try {
				JSONObject object = new JSONObject(response);
				if (object != null) {
					pid = object.optLong(KEY_PID);
					uid = object.optLong(KEY_UID);
					src = object.optString(KEY_SRC);
					src_small = object.optString(KEY_SRC_SMALL);
					src_big = object.optString(KEY_SRC_BIG);
					caption = object.optString(KEY_CAPTION);
				}
			} catch (JSONException e) {
				Utils.logger("exception in parsing json data:" + e.getMessage());
			}
		}
	}

	public PhotoBean get() {
		PhotoBean photo = new PhotoBean(pid);
		photo.setUid(uid);
		photo.setCaption(caption);
		photo.setUrlHead(src);
		photo.setUrlTiny(src_small);
		photo.setUrlLarge(src_big);
		return photo;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(KEY_PID).append(" = ").append(pid).append("\r\n");
		sb.append(KEY_UID).append(" = ").append(uid).append("\r\n");
		sb.append(KEY_SRC).append(" = ").append(src).append("\r\n");
		sb.append(KEY_SRC_SMALL).append(" = ").append(src_small).append("\r\n");
		sb.append(KEY_SRC_BIG).append(" = ").append(src_big).append("\r\n");
		sb.append(KEY_CAPTION).append(" = ").append(caption).append("\r\n");

		return sb.toString();
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flag) {
		Bundle bundle = new Bundle();
		if (pid != 0) {
			bundle.putLong(KEY_PID, pid);
		}
		if (uid != 0) {
			bundle.putLong(KEY_UID, uid);
		}
		if (caption != null) {
			bundle.putString(KEY_CAPTION, caption);
		}
		if (src != null) {
			bundle.putString(KEY_SRC, src);
		}
		if (src_small != null) {
			bundle.putString(KEY_SRC_SMALL, src_small);
		}
		if (src_big != null) {
			bundle.putString(KEY_SRC_BIG, src_big);
		}

		dest.writeBundle(bundle);
	}

	public static final Parcelable.Creator<PhotoUploadResponseBean> CREATOR = new Parcelable.Creator<PhotoUploadResponseBean>() {
		public PhotoUploadResponseBean createFromParcel(Parcel in) {
			return new PhotoUploadResponseBean(in);
		}

		public PhotoUploadResponseBean[] newArray(int size) {
			return new PhotoUploadResponseBean[size];
		}
	};

	/**
	 * 构造函数，根据序列化对象构造实例
	 * 
	 * @param in
	 */
	public PhotoUploadResponseBean(Parcel in) {
		super(null);

		Bundle bundle = in.readBundle();
		if (bundle.containsKey(KEY_PID)) {
			pid = bundle.getLong(KEY_PID);
		}
		if (bundle.containsKey(KEY_UID)) {
			uid = bundle.getLong(KEY_UID);
		}
		if (bundle.containsKey(KEY_CAPTION)) {
			caption = bundle.getString(KEY_CAPTION);
		}
		if (bundle.containsKey(KEY_SRC)) {
			src = bundle.getString(KEY_SRC);
		}
		if (bundle.containsKey(KEY_SRC_SMALL)) {
			src_small = bundle.getString(KEY_SRC_SMALL);
		}
		if (bundle.containsKey(KEY_SRC_BIG)) {
			src_big = bundle.getString(KEY_SRC_BIG);
		}
	}

}
