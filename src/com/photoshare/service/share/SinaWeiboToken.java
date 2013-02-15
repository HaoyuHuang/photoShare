/**
 * 
 */
package com.photoshare.service.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.weibo.sdk.android.Oauth2AccessToken;

/**
 * @author Aron
 * 
 */
public class SinaWeiboToken {

	private static SinaWeiboToken token;

	public static SinaWeiboToken getInstance() {
		if (token == null) {
			token = new SinaWeiboToken();
		}
		return token;
	}

	private Oauth2AccessToken accessToken;

	private final String PREFERENCES_NAME = "com_weibo_sdk_android";

	/**
	 * 保存accesstoken到SharedPreferences
	 * 
	 * @param context
	 *            Activity 上下文环境
	 * @param token
	 *            Oauth2AccessToken
	 */
	public void keepAccessToken(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString("token", accessToken.getToken());
		editor.putLong("expiresTime", accessToken.getExpiresTime());
		editor.commit();
	}

	/**
	 * 清空sharepreference
	 * 
	 * @param context
	 */
	public void clear(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	
	/**
	 * 从SharedPreferences读取accessstoken
	 * 
	 * @param context
	 * @return Oauth2AccessToken
	 */
	public void readAccessToken(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		accessToken = new Oauth2AccessToken();
		accessToken.setToken(pref.getString("token", ""));
		accessToken.setExpiresTime(pref.getLong("expiresTime", 0));
	}

	public void newToken(String token, String expire) {
		accessToken = new Oauth2AccessToken(token, expire);
	}

	public boolean isTokenValid() {
		return accessToken.isSessionValid();
	}

	public Oauth2AccessToken getAccessToken() {
		return accessToken;
	}

}
