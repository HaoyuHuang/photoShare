package com.photoshare.utils;

import com.photoshare.exception.NetworkError;

public interface RequestListener {

	/**
	 * 当请求完成后调用
	 * 
	 * @param response
	 *            服务器返回的结果JSON串
	 */
	public void onComplete(String response);

	/**
	 * 服务器返回了错误结果
	 * 
	 * @param renrenError
	 */
	public void onNetworkError(NetworkError renrenError);

	/**
	 * 在请求期间发生了严重问题（如：网络故障、访问的地址不存在等）
	 * 
	 * @param fault
	 */
	public void onFault(Throwable fault);
}
