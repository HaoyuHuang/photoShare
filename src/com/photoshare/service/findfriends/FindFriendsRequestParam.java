/**
 * 
 */
package com.photoshare.service.findfriends;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

/**
 * @author czj_yy
 * 
 */
public class FindFriendsRequestParam extends RequestParam {

	private String uName;

	public static final String KEY_NAME = "friendName";

	@Deprecated
	private static final String METHOD = "findFriends.do";

	private static final String ACTION = "/FriendsAction_getUserInfo";
	
	public String getAction() {
		return ACTION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.RequestParam#getParams()
	 */
	@Override
	public Bundle getParams() throws NetworkException {
		Bundle bundle = new Bundle();
		bundle.putString("method", METHOD);
		bundle.putString(FriendsBean.KEY_FRIENDS + "." + KEY_NAME, uName);
		return bundle;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

}
