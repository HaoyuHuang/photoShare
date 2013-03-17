/**
 * 
 */
package com.photoshare.service.follow;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 * 
 */
public class UserGetFollowInfoResponseBean extends ResponseBean {

	private ArrayList<UserInfo> followInfos = new ArrayList<UserInfo>();

	/**
	 * @param response
	 */
	public UserGetFollowInfoResponseBean(String response) {
		super(response);
		try {
			JSONObject obj = new JSONObject(response);
			JSONArray array = obj.optJSONArray(UserInfo.KEY_USER_INFOS);
			for (int i = 0; i < array.length(); i++) {
				followInfos.add(new UserInfo().parse(array.optJSONObject(i)));
			}
		} catch (JSONException e) {
			
		} catch (NetworkException e) {
			
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		for (UserInfo info : followInfos) {
			sb.append(info.toString()).append("\r\n");
		}
		return sb.toString();
	}

	public ArrayList<UserInfo> getFollowInfos() {
		return followInfos;
	}

}
