/**
 * 
 */
package com.photoshare.service.likes;

import android.os.Bundle;

import com.photoshare.common.Builder;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

/**
 * @author Aron
 * 
 */
public class PhotoLikeRequestParam extends RequestParam {

	@Deprecated
	private static final String METHOD = "photoLike.do";

	private static final String ACTION = "/LikeAction";

	public String getAction() {
		return ACTION;
	}

	private long userId;

	private long photoId;

	private boolean isLike;

	private String tinyUrl;

	public PhotoLikeRequestParam(LikeBuilder builder) {
		this.userId = builder.userId;
		this.photoId = builder.photoId;
		this.isLike = builder.isLike;
		this.tinyUrl = builder.tinyUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.RequestParam#getParams()
	 */
	@Override
	public Bundle getParams() throws NetworkException {
		Bundle parameters = new Bundle();
		parameters.putString("method", METHOD);
		parameters.putString(LikeBean.KEY_LIKE + "." + LikeBean.KEY_UID,
				userId + "");
		parameters.putString(LikeBean.KEY_LIKE + "." + LikeBean.KEY_PID,
				photoId + "");
		parameters.putString(LikeBean.KEY_LIKE + "." + LikeBean.KEY_IS_LIKE,
				isLike + "");
		return parameters;
	}

	public static class LikeBuilder implements Builder<PhotoLikeRequestParam> {

		private long userId;

		private long photoId;

		private boolean isLike;

		private String tinyUrl;

		public LikeBuilder UserId(long userId) {
			this.userId = userId;
			return this;
		}

		public LikeBuilder PhotoId(long photoId) {
			this.photoId = photoId;
			return this;
		}

		public LikeBuilder isLike(boolean isLike) {
			this.isLike = isLike;
			return this;
		}

		public LikeBuilder TinyUrl(String url) {
			this.tinyUrl = url;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.photoshare.common.Builder#build()
		 */
		public PhotoLikeRequestParam build() {
			return new PhotoLikeRequestParam(this);
		}

	}

	public long getUserId() {
		return userId;
	}

	public long getPhotoId() {
		return photoId;
	}

	public boolean isLike() {
		return isLike;
	}

	public String getTinyUrl() {
		return tinyUrl;
	}

}
