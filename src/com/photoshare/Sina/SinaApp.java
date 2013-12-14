package com.photoshare.Sina;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;

public class SinaApp
{

	public static final String APP_KEY = "805325784";
	
	
	
	public static final String APP_SECRET = "91a79ddb18a8be39cfa2ad768757b3a3";

	public static Oauth2AccessToken mAccessToken;
	
	
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";

	public static final String REDIRECT_URL = "http://www.sina.com";
	
	public static  String CODE;
	
    /** 通过 code 获取 Token 的 URL */
    public static final String OAUTH2_ACCESS_TOKEN_URL = "https://open.weibo.cn/oauth2/access_token";
    

}
