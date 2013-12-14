package com.photoshare.bean;

public class LogUserBean
{

	public static String LOG_USER_ACCOUNT;
	public static String LOG_USER_PASSWORD;
	public static String LOG_USER_ID;
	public static String LOG_USER_NAME;
	public static String LOG_USER_SEX;
	public static String LOG_USER_EMAIL;
	public static String LOG_USER_NICKNAME;

	public LogUserBean(String LOG_USER_ACCOUNT, String LOG_USER_PASSWORD,
			String LOG_USER_ID, String LOG_USER_NAME, String LOG_USER_SEX,
			String LOG_USER_EMAIL, String LOG_USER_NICKNAME)
	{

		LogUserBean.LOG_USER_ACCOUNT = LOG_USER_ACCOUNT;
		LogUserBean.LOG_USER_PASSWORD = LOG_USER_PASSWORD;
		LogUserBean.LOG_USER_ID = LOG_USER_ID;
		LogUserBean.LOG_USER_NAME = LOG_USER_NAME;
		LogUserBean.LOG_USER_SEX = LOG_USER_SEX;
		LogUserBean.LOG_USER_EMAIL = LOG_USER_EMAIL;
		LogUserBean.LOG_USER_NICKNAME = LOG_USER_NICKNAME;

	}
}
