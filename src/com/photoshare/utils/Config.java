package com.photoshare.utils;

import java.util.ArrayList;

import com.photoshare.polygonfill.Point;

public class Config {

	public static final String API_KEY = "989d893cd06e4c9da30b0f28161361fd";
	public static final String APP_SECRET = "f270c6d5e0b54c4aab379574c5247f26";
	public static final String APP_ID = "188602";

	// sd卡 图片路径
	public static String pose_Path = "";
	public static String clothes_HasCroped_Path = "";
	public static String clothes_NotCrope_Path = "";
	public static String clothes_Like_Path = "";

	// 命令 code
	public static int login_id = 10000;
	public static int register_id = 10001;

	//
	public static String posePicture = "10002";
	public static String clothesPicture = "10003";
	public static String choosePicture = "10004";

	// 用户信息
	public static User user = null;

	// 和服务器端的连接地址
	public static String serverRegisterUrl = "http://10.2.63.71:8080/YiBan/servlet/Register";
	public static String serverLoginUrl = "http://10.2.63.71:8080/YiBan/servlet/Login";

	public static ArrayList<Point> pointList = new ArrayList<Point>();

}
