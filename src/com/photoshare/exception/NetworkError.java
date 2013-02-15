package com.photoshare.exception;

/**
 * 封装服务器返回的错误结果
 */
public class NetworkError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** 错误码：参数为空 */
	public static final int ERROR_CODE_NULL_PARAMETER = -1;

	/**
	 * 错误码：无法获取数据
	 */
	public static final int ERROR_REFRESH_DATA = -2;

	/**
	 * 错误码：注册失败
	 */
	public static final int ERROR_SIGN_UP = -3;

	/**
	 * 错误码：登陆失败
	 */
	public static final int ERROR_SIGN_IN = -5;
	/**
	 * 错误码：邮件为空
	 */
	public static final int ERROR_MAIL_NULL = -7;
	/**
	 * 错误码：密码为空
	 */
	public static final int ERROR_PWD_NULL = -11;
	/**
	 * 错误码：用户名为空
	 */
	public static final int ERROR_NAME_NULL = -13;
	/**
	 * 错误码：笔名为空
	 */
	public static final int ERROR_PSEUDO_NAME_NULL = -17;
	/**
	 * 错误码：跟随失败
	 */
	public static final int ERROR_FOLLOW = -19;
	/**
	 * 错误码：赞失败
	 */
	public static final int ERROR_LIKE = -23;
	/**
	 * 错误码：发布照片失败
	 */
	public static final int ERROR_PHOTO = -29;
	/**
	 * 错误码：评论失败
	 */
	public static final int ERROR_COMMENT = -31;
	/**
	 * 错误码：修改资料失败
	 */
	public static final int ERROR_EDIT_PROFILE = -37;

	/**
	 * 错误码：网络不通
	 */
	public static final int ERROR_NETWORK = -41;

	/**
	 * 错误码：未登陆
	 */
	public static final int ERROR_SIGN_IN_NULL = -47;

	public static final int ERROR_RENREN_AUTHORIZE = -53;

	public static final int ERROR_RENREN_PUBLISH_PHOTO = -57;

	public static final int ERROR_SINA_WEIBO_AUTHORIZE = -61;

	public static final int ERROR_SINA_WEIBO_PUBLISH_PHOTO = -67;

	public static final int ERROR_TX_WEIBO_AUTHORIZE = -71;

	public static final int ERROR_TX_WEIBO_PUBLISH_PHOTO = -73;

	public static final int ERROR_SIGN_UP_MAIL = -83;

	/** 错误码：参数超出了限制 <br> */
	public static final int ERROR_CODE_PARAMETER_EXTENDS_LIMIT = 2;

	/** 错误码：非法参数 */
	public static final int ERROR_CODE_ILLEGAL_PARAMETER = 3;

	/** 错误码：无法解析服务器响应字符串 */
	public static final int ERROR_CODE_UNABLE_PARSE_RESPONSE = 5;

	/** 错误码：未知错误 */
	public static final int ERROR_CODE_UNKNOWN_ERROR = 9;

	/** */
	public static final int ERROR_CODE_LOG_ERROR = 13;

	/**
	 * 服务器返回的错误代码
	 */
	private int errorCode;

	/** 原始响应URL */
	private String orgResponse;

	public NetworkError() {
		super();
	}

	public NetworkError(NetworkError error) {
		this.errorCode = error.getErrorCode();
		this.orgResponse = error.getOrgResponse();
	}

	public NetworkError(String errorMessage) {
		super(errorMessage);
	}

	public NetworkError(int errorCode, String errorMessage, String orgResponse) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.orgResponse = orgResponse;
	}

	public String getOrgResponse() {
		return orgResponse;
	}

	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		return "errorCode:" + this.errorCode + "\nerrorMessage:"
				+ this.getMessage() + "\norgResponse:" + this.orgResponse;
	}

	/**
	 * 将服务器返回的errorMessage转换为字符串
	 * 
	 * @param errorCode
	 *            服务器返回的错误代码
	 * @param errorMessage
	 *            服务器返回的错误字符串，和错误代码一一对应
	 * @return
	 */
	public static String interpretErrorMessage(int errorCode,
			String errorMessage) {
		switch (errorCode) {
		// 图片尺寸太小，暂时将这种情况直接返回上传失败，日后可能会引起问题
		case 300:
			errorMessage = "";
			break;
		// 上传照片失败
		case 20101:
			// 这里对文案做了适当修改，使提示信息更加简洁易懂
			// errorMessage = "上传照片失败，请稍后重试";
			errorMessage = "请稍后重试";
			break;
		// 上传照片类型错误或未知
		case 20102:
		case 20103:
			errorMessage = "暂不支持此格式照片，请重新选择";
			break;
		default:
			break;
		}
		return errorMessage;
	}
}
