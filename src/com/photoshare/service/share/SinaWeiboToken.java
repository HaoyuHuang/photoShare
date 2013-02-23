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

	private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

	public static Oauth2AccessToken accessToken;

	/**
	 * 保存accesstoken到SharedPreferences
	 * 
	 * @param context
	 *            Activity 上下文环境
	 * @param token
	 *            Oauth2AccessToken
	 */
	public static void keepAccessToken(Context context, Oauth2AccessToken token) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString("token", token.getToken());
		editor.putLong("expiresTime", token.getExpiresTime());
		editor.commit();
	}

	/**
	 * 清空sharepreference
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
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
	public static Oauth2AccessToken readAccessToken(Context context) {
		accessToken = new Oauth2AccessToken();
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_APPEND);
		accessToken.setToken(pref.getString("token", ""));
		accessToken.setExpiresTime(pref.getLong("expiresTime", 0));
		return accessToken;
	}

	public static boolean isSeesionValid() {
		if (accessToken != null) {
			return accessToken.isSessionValid();
		}
		return false;
	}

	public static Oauth2AccessToken createToken(String accessToken,
			String expires_in) {
		return new Oauth2AccessToken(accessToken, expires_in);
	}

}
