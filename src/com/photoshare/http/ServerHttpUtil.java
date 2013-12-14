package com.photoshare.http;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.photoshare.bean.LogUserBean;
import com.photoshare.http.post.paras.ServerHttpUpLoadPicParas;
import com.photoshare.jsonstatus.JsonStatus;

import android.util.Log;

public class ServerHttpUtil
{

	/**
	 * 
	 * 
	 * 
	 * 用户注册http post
	 */
	public static JSONObject registHttpPost(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(
					ServerHttpConfig.HTTP_REGIST_USER_URL);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());

			// 构造json
			result = new JSONObject(retSrc);
			Log.d("WCH", "return json----->>>" + retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	/**
	 * 用户登录
	 */

	public static JSONObject userLogHttpPost(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_USER_LOG_URL);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	/**
	 * android上传文件到服务器
	 * 
	 * @param file
	 *            需要上传的文件
	 * @param RequestURL
	 *            请求的rul
	 * @return 返回响应的内容
	 */

	public static int userUpLoadPic(File imgFile, String imgtext,
			String targetURL)
	{

		PostMethod filePost = new PostMethod(targetURL);//
		try
		{
			// 设置post参数
			Part[] paras = {
					new StringPart(ServerHttpUpLoadPicParas.USER_ID,
							LogUserBean.LOG_USER_ID),
					new StringPart(ServerHttpUpLoadPicParas.USER_ACCOUNT,
							LogUserBean.LOG_USER_ACCOUNT),
					new StringPart(ServerHttpUpLoadPicParas.USER_NAME,
							LogUserBean.LOG_USER_NAME),
					new StringPart(ServerHttpUpLoadPicParas.IMG_TEXT, imgtext),
					new FilePart(ServerHttpUpLoadPicParas.IMG_FILE, imgFile) };
			// 设置传输实体
			filePost.setRequestEntity(new MultipartRequestEntity(paras,
					new HttpMethodParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);// 请求超时设定
			int status = client.executeMethod(filePost);// 执行post方法
			if (status == HttpStatus.SC_OK)
			{

				Log.d("WCH", "上传--------成功");
				return JsonStatus.UPLOAD_PIC_SUCCESS;
				// 上传成功
			}
			else
			{
				Log.d("WCH", "上传--------失败");
				return JsonStatus.UPLOAD_PIC_FALSE;
				// 上传失败
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return JsonStatus.UPLOAD_PIC_EXCEPTION;

		}
		finally
		{
			filePost.releaseConnection();
		}

	}

	/**
	 * 
	 * 获取新鲜事
	 * 
	 * 
	 * 
	 * 
	 * @param postParameters
	 * @return
	 */

	public static JSONObject getForums(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_GET_FORUMS);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	/**
	 * 
	 * 点赞时候的发送
	 * 
	 * 
	 * 
	 * @param postParameters
	 * @return
	 */

	public static JSONObject doSupport(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_DO_SUPPORT);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	/**
	 * 
	 * 
	 * 用户获取最流行的10副图片
	 * 
	 * @param postParameters
	 * @return
	 */

	public static JSONObject getPopular(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_GET_POPULAR);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	public static JSONObject getFriends(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_GET_FRIENDS);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	public static JSONObject findFriends(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_FIND_FRIENDS);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	public static JSONObject addFriends(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_ADD_FRIENDS);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	public static JSONObject getUserInfo(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_GET_USER_INFO);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	public static JSONObject updateUserInfo(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_UPDATE_USER_INFO);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}
	
	public static JSONObject getComment(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(ServerHttpConfig.HTTP_GET_COMMENT);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

	public static JSONObject insertComment(List<NameValuePair> postParameters)
	{

		JSONObject result = null;
		try
		{

			// http 请求
			HttpPost request = new HttpPost(
					ServerHttpConfig.HTTP_INSERT_COMMENT);
			// 加入请求参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			// 设置请求实体

			request.setEntity(formEntity);

			// 执行请求
			HttpResponse httpResponse = new DefaultHttpClient()

			.execute(request);
			// 获取返回字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());// error
																			// in
																			// this
			Log.d("WCH", "return json----->>>" + retSrc);
			// 构造json
			result = new JSONObject(retSrc);

		}
		catch (Exception e)
		{
			Log.d("WCH", e.toString());
		}
		finally
		{
			return result;
		}

	}

}
