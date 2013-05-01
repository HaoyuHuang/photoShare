package com.photoshare.service.photos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.photoshare.common.Builder;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.comments.CommentInfo;

/**
 * 对相片实体Bean的封装<br>
 * 
 * 注：不继承ResponseBean
 * 
 */
public class PhotoBean implements Parcelable {
	/**
	 * 字段常量
	 */
	public static final String KEY_PID = "pid";
	public static final String KEY_FILE_NAME = "fileName";
	public static final String KEY_UID = "uid";
	public static final String KEY_UNAME = "uname";
	public static final String KEY_UHEAD_URL = "tinyUrl";
	public static final String KEY_CAPTION = "caption";
	public static final String KEY_CREATE_TIME = "createTime";
	public static final String KEY_LIKES_COUNT = "likesCount";
	public static final String KEY_IS_LIKE = "isLike";
	public static final String KEY_COMMENT_COUNT = "commentCount";
	public static final String KEY_TINY_URL = "tinyurl";
	public static final String KEY_MIDDLE_URL = "url";
	public static final String KEY_LARGE_URL = "largeurl";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_COMMENTS = "comments";
	public static final String KEY_PHOTO = "photo";
	public static final String KEY_SRC_PHOTO = "bitmapPhoto";
	public static final String KEY_PHOTOS = "photos";
	public static final String KEY_PHOTO_TYPE = "type";
	public static final String ABSOLUTE_PATH_TAG = "path";

	/**
	 * 照片id
	 */
	private long pid;
	/**
	 * 照片文件名 注：此名代表上传到服务器上的建议文件名，系统实际上可忽略，这个参数的主要作用是在上传的时候识别文件类型，所以名字可以随便取，
	 * 但类型一定要设置正确
	 */
	private String fileName;
	/**
	 * 一张照片的所有者用户id
	 */
	private long uid;
	/**
	 * 所有者的姓名
	 * */
	private String uname;

	/**
	 * 
	 */
	private String tinyHeadUrl;
	/**
	 * 照片的描述
	 */
	private String caption;
	/**
	 * 照片的创建时间
	 */
	private String createTime;
	/**
	 * 照片的浏览数
	 */
	private int likesCount;
	/**
	 * 照片的评论数
	 */
	private int commentCount;
	/**
	 * 这几个都是同意图片不同的尺寸的图片的url地址
	 * 
	 * 100*150相册列表中的大小，url源地址
	 */
	private String urlTiny;
	/**
	 * 200*300 封面大小，url源地址
	 */
	private String urlHead;
	/**
	 * 600*900正常相片，url源地址
	 */
	private String urlLarge;

	private String absolutePath;

	private boolean isLike;

	private ArrayList<CommentInfo> comments = new ArrayList<CommentInfo>();

	/**
	 * 图片的二进制数据
	 */
	private byte[] content;

	public PhotoBean() {

	}

	public PhotoBean(PhotoBeanBuilder builder) {
		this.pid = builder.pid;
		this.fileName = builder.fileName;
		this.uid = builder.uid;
		this.uname = builder.uname;
		this.tinyHeadUrl = builder.tinyHeadUrl;
		this.caption = builder.caption;
		this.createTime = builder.createTime;
		this.likesCount = builder.likesCount;
		this.commentCount = builder.commentCount;
		this.urlTiny = builder.urlTiny;
		this.urlHead = builder.urlHead;
		this.urlLarge = builder.urlLarge;
		this.absolutePath = builder.absolutePath;
		this.isLike = builder.isLike;
		this.comments = builder.comments;
		this.content = builder.content;
	}

	public static class PhotoBeanBuilder implements Builder<PhotoBean> {

		private long pid;

		private String fileName;

		private long uid;

		private String uname;

		private String tinyHeadUrl;

		private String caption;

		private String createTime;

		private int likesCount;

		private int commentCount;

		private String urlTiny;

		private String urlHead;

		private String urlLarge;

		private String absolutePath;

		private boolean isLike;

		private ArrayList<CommentInfo> comments = new ArrayList<CommentInfo>();

		private byte[] content;

		public PhotoBeanBuilder UserId(long uid) {
			this.uid = uid;
			return this;
		}

		public PhotoBeanBuilder PhotoId(long pid) {
			this.pid = pid;
			return this;
		}

		public PhotoBeanBuilder FileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public PhotoBeanBuilder UserName(String userName) {
			this.uname = userName;
			return this;
		}

		public PhotoBeanBuilder tinyHeadUrl(String tinyHeadUrl) {
			this.tinyHeadUrl = tinyHeadUrl;
			return this;
		}

		public PhotoBeanBuilder Caption(String caption) {
			this.caption = caption;
			return this;
		}

		public PhotoBeanBuilder CreateTime(String createTime) {
			this.createTime = createTime;
			return this;
		}

		public PhotoBeanBuilder LikesCount(int likesCount) {
			this.likesCount = likesCount;
			return this;
		}

		public PhotoBeanBuilder CommentsCount(int commentsCount) {
			this.commentCount = commentsCount;
			return this;
		}

		public PhotoBeanBuilder TinyPhotoUrl(String tinyUrl) {
			this.urlTiny = tinyUrl;
			return this;
		}

		public PhotoBeanBuilder PhotoUrl(String url) {
			this.urlHead = url;
			return this;
		}

		public PhotoBeanBuilder LargePhotoUrl(String largePhotoUrl) {
			this.urlLarge = largePhotoUrl;
			return this;
		}

		public PhotoBeanBuilder AbsolutePath(String absolutePath) {
			this.absolutePath = absolutePath;
			return this;
		}

		public PhotoBeanBuilder IsLike(boolean isLike) {
			this.isLike = isLike;
			return this;
		}

		public PhotoBeanBuilder Comments(ArrayList<CommentInfo> comments) {
			this.comments = comments;
			return this;
		}

		public PhotoBeanBuilder Content(byte[] content) {
			this.content = content;
			return this;
		}

		public PhotoBean build() {
			return new PhotoBean(this);
		}

	}

	public PhotoBean(long pid) {
		this.pid = pid;
	}

	public PhotoBean parse(JSONObject object) throws NetworkException {

		if (object == null) {
			return null;
		}
		pid = object.optLong(KEY_PID);
		uid = object.optLong(KEY_UID);
		uname = object.optString(KEY_UNAME);
		fileName = object.optString(KEY_FILE_NAME);
		tinyHeadUrl = object.optString(KEY_UHEAD_URL);
		caption = object.optString(KEY_CAPTION);
		content = (byte[]) object.opt(KEY_CONTENT);
		createTime = object.optString(KEY_CREATE_TIME);
		commentCount = object.optInt(KEY_COMMENT_COUNT);
		likesCount = object.optInt(KEY_LIKES_COUNT);
		isLike = object.optBoolean(KEY_IS_LIKE);
		urlTiny = object.optString(KEY_TINY_URL);
		urlHead = object.optString(KEY_MIDDLE_URL);
		urlLarge = object.optString(KEY_LARGE_URL);

		JSONArray array = object.optJSONArray(KEY_COMMENTS);
		if (array == null) {
			return this;
		}
		for (int i = 0; i < array.length(); i++) {
			comments.add(new CommentInfo().parse(array.optJSONObject(i)));
		}
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		sb.append(KEY_PID).append(" = ").append(pid).append("\r\n");
		sb.append(KEY_UID).append(" = ").append(uid).append("\r\n");
		sb.append(KEY_UNAME).append(" = ").append(uname).append("\r\n");
		sb.append(KEY_CAPTION).append(" = ").append(caption).append("\r\n");
		// sb.append(KEY_CREATE_TIME).append(" = ").append(sdf.format(createTime))
		// .append("\r\n");
		sb.append(KEY_CREATE_TIME).append(" = ").append(createTime)
				.append("\r\n");
		sb.append(KEY_LIKES_COUNT).append(" = ").append(likesCount)
				.append("\r\n");
		sb.append(KEY_COMMENT_COUNT).append(" = ").append(commentCount)
				.append("\r\n");
		sb.append(KEY_TINY_URL).append(" = ").append(urlTiny).append("\r\n");
		sb.append(KEY_MIDDLE_URL).append(" = ").append(urlHead).append("\r\n");
		sb.append(KEY_LARGE_URL).append(" = ").append(urlLarge).append("\r\n");

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
		if (fileName != null) {
			bundle.putString(KEY_FILE_NAME, fileName);
		}
		if (uid != 0) {
			bundle.putLong(KEY_UID, uid);
		}
		if (uname != null) {
			bundle.putString(KEY_UNAME, uname);
		}
		if (tinyHeadUrl != null) {
			bundle.putString(KEY_UHEAD_URL, tinyHeadUrl);
		}
		if (caption != null) {
			bundle.putString(KEY_CAPTION, caption);
		}
		if (createTime != null) {
			bundle.putSerializable(KEY_CREATE_TIME, createTime);
		}
		bundle.putInt(KEY_LIKES_COUNT, likesCount);
		bundle.putBoolean(KEY_IS_LIKE, isLike);
		bundle.putInt(KEY_COMMENT_COUNT, commentCount);
		if (urlTiny != null) {
			bundle.putString(KEY_TINY_URL, urlTiny);
		}
		if (urlHead != null) {
			bundle.putString(KEY_MIDDLE_URL, urlHead);
		}
		if (urlLarge != null) {
			bundle.putString(KEY_LARGE_URL, urlLarge);
		}
		if (comments != null) {
			bundle.putParcelableArrayList(KEY_COMMENTS, comments);
		}
		// 一般不传递content，因为内容可能太大
		if (content != null) {
			bundle.putByteArray(KEY_CONTENT, content);
		}
		dest.writeBundle(bundle);
	}

	public static final Parcelable.Creator<PhotoBean> CREATOR = new Parcelable.Creator<PhotoBean>() {
		public PhotoBean createFromParcel(Parcel in) {
			return new PhotoBean(in);
		}

		public PhotoBean[] newArray(int size) {
			return new PhotoBean[size];
		}
	};

	/**
	 * 构造函数，根据序列化对象构造实例
	 * 
	 * @param in
	 */
	public PhotoBean(Parcel in) {
		Bundle bundle = in.readBundle();
		if (bundle.containsKey(KEY_PID)) {
			pid = bundle.getLong(KEY_PID);
		}
		if (bundle.containsKey(KEY_FILE_NAME)) {
			fileName = bundle.getString(KEY_FILE_NAME);
		}
		if (bundle.containsKey(KEY_UID)) {
			uid = bundle.getLong(KEY_UID);
		}
		if (bundle.containsKey(KEY_UNAME)) {
			uname = bundle.getString(KEY_UNAME);
		}
		if (bundle.containsKey(KEY_CAPTION)) {
			caption = bundle.getString(KEY_CAPTION);
		}
		if (bundle.containsKey(KEY_CREATE_TIME)) {
			createTime = bundle.getString(KEY_CREATE_TIME);
		}
		likesCount = bundle.getInt(KEY_LIKES_COUNT);
		commentCount = bundle.getInt(KEY_COMMENT_COUNT);
		isLike = bundle.getBoolean(KEY_IS_LIKE);
		if (bundle.containsKey(KEY_TINY_URL)) {
			urlTiny = bundle.getString(KEY_TINY_URL);
		}
		if (bundle.containsKey(KEY_MIDDLE_URL)) {
			urlHead = bundle.getString(KEY_MIDDLE_URL);
		}
		if (bundle.containsKey(KEY_LARGE_URL)) {
			urlLarge = bundle.getString(KEY_LARGE_URL);
		}
		if (bundle.containsKey(KEY_CONTENT)) {
			content = bundle.getByteArray(KEY_CONTENT);
		}
		if (bundle.containsKey(KEY_UHEAD_URL)) {
			tinyHeadUrl = bundle.getString(KEY_UHEAD_URL);
		}
	}

	public Bundle params() {
		Bundle param = new Bundle();
		param.putParcelable(PhotoBean.KEY_PHOTO, this);
		return param;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getUrlTiny() {
		return urlTiny;
	}

	public void setUrlTiny(String urlTiny) {
		this.urlTiny = urlTiny;
	}

	public String getUrlHead() {
		return urlHead;
	}

	public void setUrlHead(String urlHead) {
		this.urlHead = urlHead;
	}

	public String getUrlLarge() {
		return urlLarge;
	}

	public void setUrlLarge(String urlLarge) {
		this.urlLarge = urlLarge;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public ArrayList<CommentInfo> getComments() {
		return comments;
	}

	public void setComments(ArrayList<CommentInfo> comments) {
		this.comments = comments;
	}

	public String getTinyHeadUrl() {
		return tinyHeadUrl;
	}

	public void setTinyHeadUrl(String tinyHeadUrl) {
		this.tinyHeadUrl = tinyHeadUrl;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

}