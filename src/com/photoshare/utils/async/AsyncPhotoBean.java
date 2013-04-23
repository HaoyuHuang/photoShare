package com.photoshare.utils.async;

import com.photoshare.service.photos.factory.BitmapDisplayConfig;

public final class AsyncPhotoBean {
	private final String url;

	private final AsyncImageCallback callback;

	private final BitmapDisplayConfig config;

	private boolean success;

	public AsyncPhotoBean(String url, AsyncImageCallback callback,
			BitmapDisplayConfig config) {
		super();
		this.url = url;
		this.callback = callback;
		this.config = config;
	}

	public String getUrl() {
		return url;
	}

	public BitmapDisplayConfig getConfig() {
		return config;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	public AsyncImageCallback getCallback() {
		return callback;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AsyncPhotoBean other = (AsyncPhotoBean) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
