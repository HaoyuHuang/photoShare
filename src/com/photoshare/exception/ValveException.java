package com.photoshare.exception;

public class ValveException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final String LOGGING_EXCEPTION = "No Valve ";
	public static final int KEY_LOGGING_EXCEPTION = 1;

	/**
	 * 服务器返回的错误代码
	 */
	private int errorCode;

	private String orgResponse;

	public ValveException(String errorMessage) {
		super(errorMessage);
	}

	public ValveException(int errorCode, String errorMessage,
			String orgResponse) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.orgResponse = orgResponse;
	}

	public ValveException(ValveException error) {
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
