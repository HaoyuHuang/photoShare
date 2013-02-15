/**
 * 
 */
package com.photoshare.service.likes;

import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.utils.Utils;

/**
 * @author czj_yy
 * 
 */
public class PhotoLikeResponseBean extends ResponseBean {

	private boolean isLike;

	private long UserId;

	private long PhotoId;

	public static final String KEY_USER_ID = "uid";

	public static final String KEY_PHOTO_ID = "pid";

	public static final String KEY_LIKE = "like";

	/**
	 * @param response
	 */
	public PhotoLikeResponseBean(String response) {
		super(response);
		// TODO Auto-generated constructor stub

		if (response == null)
			return;

		try {
			JSONObject obj = new JSONObject(response);
			if (obj != null) {
				isLike = obj.optBoolean(KEY_LIKE);
				UserId = obj.optLong(KEY_USER_ID);
				PhotoId = obj.optLong(KEY_PHOTO_ID);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Utils.logger("exception in parsing json data:" + e.getMessage());
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();

		sb.append(KEY_USER_ID).append(" = ").append(UserId).append("\r\n");
		sb.append(KEY_PHOTO_ID).append(" = ").append(PhotoId).append("\r\n");
		sb.append(KEY_LIKE).append(" = ").append(isLike).append("\r\n");

		return sb.toString();
	}
}
