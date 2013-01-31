/**
 * 
 */
package com.photoshare.service.likes;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;

/**
 * @author czj_yy
 * 
 */
public class LikeGetInfoResponseBean extends ResponseBean {

	private ArrayList<LikeBean> likesBean;

	/**
	 * @param response
	 */
	public LikeGetInfoResponseBean(String response) {
		super(response);

		if (response == null) {
			return;
		}

		try {
			JSONObject obj = new JSONObject(response);
			JSONArray array = obj.optJSONArray(LikeBean.KEY_LIKES);
			likesBean = new ArrayList<LikeBean>();
			for (int i = 0; i < array.length(); i++) {
				likesBean.add(new LikeBean().parse(array.optJSONObject(i)));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<LikeBean> getLikesBean() {
		return likesBean;
	}

}
