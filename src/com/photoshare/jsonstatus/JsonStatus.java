package com.photoshare.jsonstatus;

public class JsonStatus
{
	// 用户注册

	public static final int REGIST_USER_EXIST = 1;// 存在
	public static final int REGIST_EXCEPTION = 2;// 异常
	public static final int REGIST_SUCCESS = 3;// 成功

	// 用户登录

	public static final int USER_LOG_SUCCESS = 4;// 成功
	public static final int USER_LOG_FALSE = 5;// 失败
	public static final int USER_LOG_EXCEPTION = 6;// 异常

	// 图片上传
	public static final int UPLOAD_PIC_SUCCESS = 7;// 成功
	public static final int UPLOAD_PIC_FALSE = 8;// 失败
	public static final int UPLOAD_PIC_EXCEPTION = 9;// 异常

	// 获取新鲜事列表
	public static final int GETFORUMS_SUCCESS = 10;// 成功
	public static final int GETFORUMS_FALSE = 11;// 失败
	public static final int GETFORUMS_EXCEPTION = 12;// 异常

	// 赞的状态
	public static final int SUPPORT_EXIST = 13;// 赞 存在
	public static final int SUPPORT_SUCCESS = 14;// 赞 成功
	public static final int SUPPORT_EXCEPTION = 15;// 赞 异常
	// 获取流行
	public static final int GET_POPULAR_FALSE = 16;
	public static final int GET_POPULAR_SUCCESS = 17;

	// 获取朋友
	public static final int GET_FRIENDS_FALSE = 17;
	public static final int GET_FRIENDS_SUCCESS = 18;

	// 查找朋友
	public static final int FIND_FRIENDS_SUCCESS = 19;
	public static final int FIND_FRIENDS_FALSE = 20;

	// 插入朋友
	public static final int ADD_FRIENDS_SUCCESS = 21;
	public static final int ADD_FRIENDS_FALSE = 22;
	public static final int ADD_FRIENDS_EXIST = 23;
	
	public static final int GET_USER_INFO_SUCCESS = 24;
	public static final int GET_USER_INFO_FALSE = 25;

	public static final int UPDATE_USER_INFO_SUCCESS = 26;
	public static final int UPDATE_USER_INFO_FALSE = 27;
	

	public static final int GET_COMMNET_SUCCESS=28;
	public static final int GET_COMMNET_FALSE=29;
	
	public static final int INSERT_COMMNET_SUCCESS = 30;
	public static final int INSERT_COMMNET_FALSE = 31;
	
	
	
}
