/**
 * 
 */
package com.photoshare.service.findfriends;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author Aron
 * 
 */
public class FindFriendsResponseBean extends ResponseBean {

	private ArrayList<UserInfo> friends = new ArrayList<UserInfo>();

	/**
	 * @param response
	 */
	public FindFriendsResponseBean(String response) {
		super(response);

		if (response == null) {
			return;
		}

		try {
			JSONObject obj = new JSONObject(response);
			JSONArray array = obj.optJSONArray(FriendsBean.KEY_FRIENDS);
			for (int i = 0; i < array.length(); i++) {
				friends.add(new UserInfo().parse(array.optJSONObject(i)));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<UserInfo> getFriends() {
		return friends;
	}

}
