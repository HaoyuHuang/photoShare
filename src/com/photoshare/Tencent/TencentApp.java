package com.photoshare.Tencent;

import android.util.Log;

import com.tencent.weibo.oauthv1.OAuthV1;
import com.tencent.weibo.oauthv1.OAuthV1Client;
import com.tencent.weibo.utils.QHttpClient;



public class TencentApp
{

	public static final String APP_KEY = "801443892";
	public static final String APP_SECRET = "9d26a52641fe5d490eb5d1c47595f5d6";
	public static OAuthV1 oAuth;
	static
	{
		
		
		Log.d("WCH", "------------->"+"tencent init");
		
		oAuth = new OAuthV1("null");
		oAuth.setOauthConsumerKey(APP_KEY);
		oAuth.setOauthConsumerSecret(APP_SECRET);
		// 关闭OAuthV1Client中的默认开启的QHttpClient。
		OAuthV1Client.getQHttpClient().shutdownConnection();
		// 为OAuthV1Client配置自己定义QHttpClient。
		OAuthV1Client.setQHttpClient(new QHttpClient());
	}
}
