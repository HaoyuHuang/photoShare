package com.photoshare.exception;

/**
 * 
 * 社交网络平台异常类
 */
public class NetworkException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final String LOGGING_EXCEPTION = "Please  Log in ";
	public static final int KEY_LOGGING_EXCEPTION = 1;

	/**
	 * 服务器返回的错误代码
	 */
	private int errorCode;

	private String orgResponse;

	public NetworkException(String errorMessage) {
		super(errorMessage);
	}

	public NetworkException(int errorCode, String errorMessage,
			String orgResponse) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.orgResponse = orgResponse;
	}

	public NetworkException(NetworkError error) {
		super(error.getMessage());
		this.errorCode = error.getErrorCode();
		this.orgResponse = error.getOrgResponse();
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

}
