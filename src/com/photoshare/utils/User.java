package com.photoshare.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.msg.RequestMsg;
import com.photoshare.pipeline.PipelineMsgHandler;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;
import com.renren.api.connect.android.Util;

/*
 * 单例
 * 用来登陆并且封装用户操作
 * */
public class User {

	public static final String KEY_MAIL = "mail";
	public static final String KEY_PWD = "pwd";
	public static final String KEY_CONFIGURE = "configur";
	public static final String KEY_UID = "userid";
	/**
	 * 登陆账号,登录后保存在SharePreference中
	 * */
	private String mail;
	/**
	 * 登陆密码
	 * */
	private String pwd;

	private boolean configured;

	private PipelineMsgHandler handler = new PipelineMsgHandler();
	/** 服务器地址 */
	private final String SERVER_URL = "http://222.94.219.153:8080/Spring3Struts2/photoShare-mobile";
	/** 响应形式为Json */
	public static final String RESPONSE_FORMAT_JSON = "json";
	private final String LOG_TAG_REQUEST = "request";
	private final String LOG_TAG_RESPONSE = "response";
	private UserInfo userInfo = new UserInfo.UserInfoBuilder().ID(2L).build();
	private boolean Logging = false;

	private static User user = new User();

	public static User getInstance() {
		if (user == null)
			user = new User();
		return user;
	}

	private User() {

	}

	/** 应该用不到的登出 */
	public boolean logout(Context context) {
		Utils.clearCookies(context);
		return true;
	}

	public void init(String mail, String pwd) {
		this.mail = mail;
		this.Logging = false;
		this.pwd = pwd;
	}

	public boolean isFieldsEmpty() {
		if (mail != null && pwd != null) {
			if (!"".equals(mail) && !"".equals(pwd)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据文件类型设置contentType
	 * 
	 * @param fileName
	 *            文件名
	 * @return contentType
	 * @throws RuntimeException
	 *             文件有误
	 * */
	private String parseContentType(String fileName) {
		String contentType = "image/jpg";
		fileName = fileName.toLowerCase();
		if (fileName.endsWith(".jpg"))
			contentType = "image/jpg";
		else if (fileName.endsWith(".png"))
			contentType = "image/png";
		else if (fileName.endsWith(".jpeg"))
			contentType = "image/jpeg";
		else if (fileName.endsWith(".gif"))
			contentType = "image/gif";
		else if (fileName.endsWith(".bmp"))
			contentType = "image/bmp";
		else
			throw new RuntimeException("不支持的文件类型'" + fileName + "'(或没有文件扩展名)");
		return contentType;
	}

	/**
	 * 以JSON方式上传照片至服务器
	 * 
	 * @param photo
	 * @param fileName
	 * @param description
	 * @param format
	 *            JSON
	 * @return
	 */
	public String publishPhoto(String method, byte[] photo, String fileName,
			String description, String format) {
		Bundle params = new Bundle();
		params.putString("method", method);
		params.putString(PhotoBean.KEY_PHOTO + "." + PhotoBean.KEY_CAPTION,
				description);
		params.putLong(PhotoBean.KEY_PHOTO + "." + PhotoBean.KEY_UID,
				userInfo.getUid());
		String contentType = parseContentType(fileName);
		params.putString("format", format);

		return Utils.uploadFile(SERVER_URL + method, params, "upload",
				fileName, contentType, photo);
	}

	/**
	 * 记录请求log
	 */
	private void logRequest(Bundle params) {
		if (params != null) {
			String method = params.getString("method");
			if (method != null) {
				StringBuffer sb = new StringBuffer();
				sb.append("method=").append(method).append("&")
						.append(params.toString());
				Log.i(LOG_TAG_REQUEST, sb.toString());
			} else {
				Log.i(LOG_TAG_REQUEST, params.toString());
			}
		}
	}

	/**
	 * 记录响应log
	 * 
	 * @param response
	 */
	private void logResponse(String method, String response) {
		if (method != null && response != null) {
			StringBuffer sb = new StringBuffer();
			sb.append("method=").append(method).append("&").append(response);
			Log.i(LOG_TAG_RESPONSE, sb.toString());
		}
	}

	/**
	 * 以JSON方式访问服务器
	 * 
	 * @param parameters
	 * 
	 * @return
	 */
	public String request(String action, Bundle parameters) {
		parameters.putString("format", RESPONSE_FORMAT_JSON);

		logRequest(parameters);
		String response = Utils
				.openUrl(SERVER_URL + action, "POST", parameters);
		logResponse(parameters.getString("method"), response);
		return response;
	}

	public void addMsg(final RequestMsg<? extends RequestParam> AMsg,
			final AbstractRequestListener<String> listener) {
		handler.addMsg(AMsg, listener);
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isLogging() {
		return true;
	}

	public void setLogging(boolean logging) {
		Logging = logging;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	public boolean isCurrentUser(UserInfo info) {
		Util.logger("currentUser");
		if (info != null) {
			if (user.getUserInfo().getUid() == info.getUid()) {
				Util.logger("isCurrentUser");
				return true;
			}
		}
		return false;
	}

}
