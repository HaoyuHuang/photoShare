package com.photoshare.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.tabHost.R;

/**
 * 常用工具类
 */
public final class Utils {

	public static final String LOG_TAG = "SNS";
	public static final String APP_NAME = "photoShare";
	public static final String DIR_HOME = "home";
	public static final String DIR_FOLLOWER = "follower";
	public static final String DIR_NEWS = "news";
	public static final String DIR_USR = "usr";
	public static final String DIR_FEED = "feed";
	public static final String DIR_MSG = "message";
	public static final String DIR_MY_PHOTOS = "myPhotos";
	public static final String DIR_USER_INFO = "userinfo";

	public static final String ENCODE_UTF_8 = "utf-8";

	public static final String SDCARD_ABSOLUTE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	public static void logger(String message) {
		Log.i(LOG_TAG, message);
	}

	/**
	 * 将Key-value转换成用&号链接的URL查询参数形式。
	 * 
	 * @param parameters
	 * @return
	 */
	public static String encodeUrl(Bundle parameters) {
		if (parameters == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String key : parameters.keySet()) {
			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			sb.append(key + "=" + URLEncoder.encode(parameters.getString(key)));
		}
		return sb.toString();
	}

	/**
	 * 将用&号链接的URL参数转换成key-value形式。
	 * 
	 * @param s
	 * @return
	 */
	public static Bundle decodeUrl(String s) {
		Bundle params = new Bundle();
		if (s != null) {
			params.putString("url", s);
			String array[] = s.split("&");
			for (String parameter : array) {
				String p[] = parameter.split("=");
				if (p.length > 1) {
					params.putString(p[0], URLDecoder.decode(p[1]));
				}
			}
		}
		return params;
	}

	/**
	 * 发送http请求
	 * 
	 * @param url
	 * @param method
	 *            GET 或 POST
	 * @param params
	 * @return
	 */
	public static String openUrl(String url, String method, Bundle params) {
		if (method.equals("GET")) {
			url = url + "?" + encodeUrl(params);
		}
		String response = "";
		try {
			Log.d(LOG_TAG, method + " URL: " + url);
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();

			if (!method.equals("GET")) {
				conn.setRequestMethod("POST");
				conn.setConnectTimeout(5000);// （单位：毫秒）jdk
				conn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
				conn.setDoOutput(true);
				conn.setRequestProperty(
						"Accept",
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-silverlight, */*");
				conn.setRequestProperty("Referer", url);
				conn.setRequestProperty("Accept-Language", "zh-cn");
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
				conn.setRequestProperty("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.1; CIBA)");
				conn.setRequestProperty("connection", "keep-alive");
				conn.connect();
				conn.getOutputStream().write(
						encodeUrl(params).getBytes("UTF-8"));
			}

			InputStream is = null;
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				is = conn.getInputStream();
			} else {
				is = conn.getErrorStream();
			}
			response = read(is);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return response;
	}

	public static HttpURLConnection openConn(String url, String method,
			Bundle params) {
		if (method.equals("GET")) {
			url = url + "?" + encodeUrl(params);
		}
		try {
			Log.d(LOG_TAG, method + " URL: " + url);
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();

			if (!method.equals("GET")) {
				conn.setRequestMethod("POST");
				conn.setConnectTimeout(5000);// （单位：毫秒）jdk
				conn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
				conn.setDoOutput(true);
				conn.setRequestProperty(
						"Accept",
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-silverlight, */*");
				conn.setRequestProperty("Referer", url);
				conn.setRequestProperty("Accept-Language", "zh-cn");
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
				conn.setRequestProperty("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.1; CIBA)");
				conn.setRequestProperty("connection", "keep-alive");
				conn.connect();
				conn.getOutputStream().write(
						encodeUrl(params).getBytes("UTF-8"));
			}
			return conn;
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将输入流读入字符串中
	 * */
	private static String read(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}

	/**
     * 
     * 
     * */
	public static File getFileFromBytes(byte[] b, String OutputFile) {
		BufferedOutputStream bos = null;
		File file = null;
		try {
			file = new File(OutputFile);
			FileOutputStream fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(b);
		} catch (Exception e) {

		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {

				}
			}
		}
		return file;
	}

	/**
	 * 上传文件
	 * 
	 * @param reqUrl
	 * 
	 * @param parameters
	 * 
	 * @param fileParamName
	 * 
	 * @param filename
	 * 
	 * @param contentType
	 * 
	 * @param data
	 * 
	 * @return
	 * */
	public static String uploadFile(String reqUrl, Bundle parameters,
			String fileParamName, String filename, String contentType,
			byte[] data) {
		HttpURLConnection urlConn = null;
		try {
			urlConn = sendFormdata(reqUrl, parameters, fileParamName, filename,
					contentType, data);
			Log.i("send", "ok");
			String responseContent = read(urlConn.getInputStream());
			return responseContent;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
	}

	/**
	 * Post到指定Url
	 * */
	private static HttpURLConnection sendFormdata(String reqUrl,
			Bundle parameters, String fileParamName, String filename,
			String contentType, byte[] data) {
		HttpURLConnection urlConn = null;
		try {
			Log.i("startSend", "okok");
			URL url = new URL(reqUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setConnectTimeout(5000);// （单位：毫秒）jdk
			urlConn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty(
					"Accept",
					"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-silverlight, */*");
			urlConn.setRequestProperty("Referer", reqUrl);
			urlConn.setRequestProperty("Accept-Language", "zh-cn");
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			urlConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; InfoPath.1; CIBA)");
			urlConn.setRequestProperty("connection", "keep-alive");

			String boundary = "-----------------------------114975832116442893661388290519"; // 分隔符
			urlConn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + boundary);

			boundary = "--" + boundary;
			StringBuffer params = new StringBuffer();
			if (parameters != null) {
				for (Iterator<String> iter = parameters.keySet().iterator(); iter
						.hasNext();) {
					String name = iter.next();
					String value = parameters.getString(name);
					params.append(boundary + "\r\n");
					params.append("Content-Disposition: form-data; name=\""
							+ name + "\"\r\n\r\n");
					// params.append(URLEncoder.encode(value, "UTF-8"));
					params.append(value);
					params.append("\r\n");
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data; name=\"" + fileParamName
					+ "\"; filename=\"" + filename + "\"\r\n");
			sb.append("Content-Type: " + contentType + "\r\n\r\n");
			byte[] fileDiv = sb.toString().getBytes();
			byte[] endData = ("\r\n" + boundary + "--\r\n").getBytes();
			byte[] ps = params.toString().getBytes();
			Log.i("startSend2", "okok");
			urlConn.connect();
			OutputStream os = urlConn.getOutputStream();
			Log.i("startSend3", "okok");
			os.write(ps);
			os.write(fileDiv);
			os.write(data);
			os.write(endData);
			Log.i("output", params.toString() + sb.toString() + data + "\r\n"
					+ boundary + "--\r\n");
			os.flush();
			os.close();
			Log.i("startSend4", "okok");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
		return urlConn;
	}

	public static void clearCookies(Context context) {
		@SuppressWarnings("unused")
		CookieSyncManager cookieSyncMngr = CookieSyncManager
				.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
	}

	/**
	 * 判断服务器返回消息中是否有异常抛出
	 * */
	public static void checkResponse(String response) throws NetworkException {
		if (response != null) {
			if (response.indexOf("error_code") < 0) {
				return;
			}
			NetworkError error = null;
			error = parseJson(response);
			if (error != null) {
				throw new NetworkException(error);
			}
		}
	}

	/**
	 * 将服务器返回的错误JSON串，转化成NetworkError.
	 * 
	 * @param JSON串
	 * @return
	 */
	private static NetworkError parseJson(String jsonResponse) {
		try {
			JSONObject json = new JSONObject(jsonResponse);

			int errorCode = json.getInt("error_code");
			String errorMessage = json.getString("error_msg");
			errorMessage = NetworkError.interpretErrorMessage(errorCode,
					errorMessage);
			return new NetworkError(errorCode, errorMessage, jsonResponse);
		} catch (JSONException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 响应的内容是错误信息时，被转化成NetworkError，否则返回NULL
	 * 
	 * @param response
	 * 
	 * @return
	 */
	public static NetworkError parseNetworkError(String response) {
		if (response.indexOf("error_code") < 0)
			return null;
		return parseJson(response);
	}

	public static String md5(String string) {
		if (string == null || string.trim().length() < 1) {
			return null;
		}
		try {
			return getMD5(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static String getMD5(byte[] source) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			StringBuffer result = new StringBuffer();
			for (byte b : md5.digest(source)) {
				result.append(Integer.toHexString((b & 0xf0) >>> 4));
				result.append(Integer.toHexString(b & 0x0f));
			}
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 检查当前网络连接状态
	 * 
	 * @param 调用此方法的Context
	 * @return true - 有可用的网络连接（3G/GSM、wifi等） false - 没有可用的网络连接，或传入的context为null
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context == null) {
			return false;
		}
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobileState = connManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState();
		State wifiState = connManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).getState();
		if (mobileState == State.DISCONNECTED
				&& wifiState == State.DISCONNECTED) {
			return false;
		}
		return true;
	}

	/**
	 * 显示征询“确认”以及“取消”的对话框
	 * 
	 * @param activity
	 *            显示此对话框的Activity对象
	 * @param title
	 *            对话框的标题
	 * @param text
	 *            对话框显示的内容
	 * @param listener
	 *            用户选择的监听器
	 */
	public static void showOptionWindow(Activity activity, String title,
			String text, OnOptionListener listener) {
		AlertDialog dialog = new AlertDialog.Builder(activity).create();
		if (title != null) {
			dialog.setTitle(title);
		}

		if (text != null) {
			dialog.setMessage(text);
		}

		final OnOptionListener oListener = listener;
		dialog.setButton(AlertDialog.BUTTON_POSITIVE,
				activity.getString(R.string.submit),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						oListener.onOK();
					}
				});
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE,
				activity.getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						oListener.onCancel();
					}
				});
		dialog.show();
	}

	/**
	 * 二元选择的监听器
	 */
	public static interface OnOptionListener {
		/**
		 * 对确认选择的响应
		 */
		public void onOK();

		/**
		 * 对取消选择的响应
		 */
		public void onCancel();
	}

	/**
	 * 读取文件二进制数据
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] fileToByteArray(File file) {
		try {
			return streamToByteArray(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger(e.getMessage());
			return null;
		}
	}

	/**
	 * 将输入流转换成字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] streamToByteArray(InputStream inputStream) {
		byte[] content = null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedInputStream bis = new BufferedInputStream(inputStream);

		try {
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = bis.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}

			content = baos.toByteArray();
			if (content.length == 0) {
				content = null;
			}

			baos.close();
			bis.close();
		} catch (IOException e) {
			logger(e.getMessage());
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					logger(e.getMessage());
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					logger(e.getMessage());
				}
			}
		}
		return content;
	}

	public static List<String> getSDImage(List<String> it, String path) {
		File f = new File(path);
		if (f.list() != null) {
			File[] file = f.listFiles();
			for (int i = 0; i < file.length; i++) {
				getSDImage(it, file[i].getPath());
			}
		} else {
			if (getImageFile(f.getPath()))
				it.add(f.getPath());
		}
		return it;
	}

	private static boolean getImageFile(String fName) {
		boolean re;

		/* 取得扩展名 */
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		/* 按扩展名的类型决定MimeType */
		if (end.equals("gif") || end.equals("png") || end.equals("jpeg")
				|| end.equals("bmp") || end.equals("jpg")) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

	public static boolean isBlank(String str) {
		if (str == null)
			return true;
		if (str.isEmpty())
			return true;
		if ("".equals(str))
			return true;
		return false;
	}

}
