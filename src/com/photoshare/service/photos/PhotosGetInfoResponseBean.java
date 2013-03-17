package com.photoshare.service.photos;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;
import com.photoshare.utils.Utils;

public class PhotosGetInfoResponseBean extends ResponseBean {

	private ArrayList<PhotoBean> photos = null;

	public ArrayList<PhotoBean> getPhotos() {
		return photos;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (photos != null) {
			for (PhotoBean bean : photos) {
				sb.append(bean.toString()).append("\r\n");
			}
		}
		return sb.toString();
	}

	public PhotosGetInfoResponseBean(String response) throws JSONException,
			NetworkException {
		super(response);
		// TODO Auto-generated constructor stub
		if (response == null) {
			return;
		}
		JSONObject obj = new JSONObject(response);
		JSONArray array = obj.optJSONArray(PhotoBean.KEY_PHOTOS);
		if (array != null) {
			photos = new ArrayList<PhotoBean>();
			int size = array.length();
			for (int i = 0; i < size; i++) {
				PhotoBean bean = new PhotoBean();
				bean.parse(array.optJSONObject(i));
				Utils.logger(bean.toString());
				if (bean != null) {
					photos.add(bean);
				}
			}
		}

	}

}
