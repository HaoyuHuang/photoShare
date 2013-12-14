package com.photoshare.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.photoshare.R;
import com.photoshare.Sina.SinaApp;
import com.photoshare.Tencent.TencentApp;
import com.photoshare.bean.LogUserBean;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.bean.TencentUpLoadContentBean;
import com.photoshare.http.ServerHttpConfig;
import com.photoshare.http.ServerHttpUtil;
import com.photoshare.http.post.paras.ServerHttpAddFriendsParas;
import com.photoshare.http.post.paras.ServerHttpDoSupportParas;
import com.photoshare.http.post.paras.ServerHttpFindFriendsParas;
import com.photoshare.http.post.paras.ServerHttpGetCommentParas;
import com.photoshare.http.post.paras.ServerHttpGetForumsParas;
import com.photoshare.http.post.paras.ServerHttpGetFriendsParas;
import com.photoshare.http.post.paras.ServerHttpGetUserInfoParas;
import com.photoshare.http.post.paras.ServerHttpInsertCommentParas;
import com.photoshare.http.post.paras.ServerHttpUpdateUserInfoParas;
import com.photoshare.jsonstatus.JsonKeys;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.listview.adapter.FindFriendsViewAdapter;
import com.photoshare.support.SupportCallback;
import com.photoshare.support.SupportManager;
import com.photoshare.ui.interfaces.BaseActivity;
import com.photoshare.ui.tab.CameraUpLoadMainActivity;
import com.photoshare.ui.tab.ForumsActivity;
import com.photoshare.ui.tab.ForumsDetailActivity;
import com.photoshare.ui.tab.FriendsFindActivity;
import com.photoshare.ui.tab.ProfileActivity;
import com.photoshare.ui.tab.ProfileActivity.UpdateUserInfoBean;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv1.OAuthV1Client;

public class MainService extends Service implements Runnable
{

	private static Queue<MainServiceTask> taskQueue = new LinkedList<MainServiceTask>();
	private static Map<String, Activity> activityMap = new HashMap<String, Activity>();
	private boolean _isRunable = false;

	private static String debug_Tag = "MainService";

	public static String SHARE_TO_SINA_SUCCESS = "SHARE_TO_SINA_SUCCESS";
	public static String SHARE_TO_SINA_FALSE = "SHARE_TO_SINA_FALSE";

	private Handler _handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{

			// 获取参数列表的内容

			Map<String, Object> map = (Map<String, Object>) msg.obj;

			// 要刷新的activity
			BaseActivity BA = (BaseActivity) activityMap.get(map
					.get(MainServiceTask.ACTIVITY_NAME));

			switch (msg.what)
			{
			case TaskType.USER_LOGIN:
			{

				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.USER_LOG_RETURN_INFO);
				Log.d("WCH", "H->Json content" + jo.toString());

				try
				{

					if (jo.get(JsonKeys.JSON_STATUS).equals(
							Integer.valueOf(JsonStatus.USER_LOG_SUCCESS)))
					{

						BA.refresh(msg.what, jo.get(JsonKeys.JSON_STATUS),
								jo.get(JsonKeys.LOG_USER_ID),
								jo.get(JsonKeys.LOG_USER_ACCOUNR),
								jo.get(JsonKeys.LOG_USER_PASSWORD),
								jo.get(JsonKeys.LOG_USER_NAME),
								jo.get(JsonKeys.LOG_USER_SEX),
								jo.get(JsonKeys.LOG_USER_NICKNAME),
								jo.get(JsonKeys.LOG_USER_EMAIL));
					}
					else
					{
						BA.refresh(msg.what, jo.get(JsonKeys.JSON_STATUS));
					}
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

			case TaskType.USER_REGIST:
			{
				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.REGIST_RETURN_INFO);
				Log.d("WCH", "H->Json content" + jo.toString());
				try
				{
					BA.refresh(msg.what, jo.get(JsonKeys.JSON_STATUS),
							jo.get(JsonKeys.REGIST_JSON_INFO));
				}
				catch (JSONException e)
				{

					e.printStackTrace();
				}
				break;
			}

			case TaskType.UPLOAD_TEXT_WITH_PIC_TENCENT:
			{

				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.TECENT_UPLOAD_RESPONSE);
				/*
				 * 刷新UI的信息
				 */

				Log.d("WCH", "resoponse------->" + jo.toString());

				BA.refresh(msg.what);
				break;
			}

			case TaskType.UPLOAD_TEXT_WITH_PIC_PHOTOSHARE:
			{

				/*
				 * 上传返回后刷新UI
				 */

				Log.d("WCH", "resoponse------->refresh");

				int response = (Integer) map
						.get(MainServiceKey.PC_UPLOAD_RESPONSE);

				BA.refresh(msg.what, response);

				break;
			}

			case TaskType.REFEUSH_FOEUMS:// 刷新列表和获取列表用的都是相同的函数方法
			case TaskType.GET_FORUMS:// 刷新列表和获取列表用的都是相同的函数方法
			{
				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.GET_FORUMS_RESPONSE);

				try
				{
					BA.refresh(msg.what, jo.get(JsonKeys.JSON_STATUS),
							jo.get(JsonKeys.GET_FORUMS_SIZE),
							jo.get(JsonKeys.GET_FORUMS_INFO));

				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			}

			case TaskType.SUPPORT:
			{

				try
				{
					SupportCallback callback = (SupportCallback) map
							.get(SupportManager.SUPPORT_PIC_CALLBACK);

					int state = ((JSONObject) map
							.get(MainServiceKey.DO_SUPPORT_RESPONSE))
							.getInt(JsonKeys.JSON_STATUS);

					int picid = (Integer) map
							.get(SupportManager.SUPPORT_PIC_ID);
					Log.d("WCH", "------------------->do support post!7");
					callback.refrush(picid, state);
					Log.d("WCH", "------------------->do support post!8");
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			}
			case TaskType.GET_POPULAE:
			{

				try
				{
					JSONObject jo = (JSONObject) map
							.get(MainServiceKey.GET_POPULAR_RESPONSE);

					int state = jo.getInt(JsonKeys.JSON_STATUS);
					Integer size = null;
					JSONArray ja = null;
					if (state != JsonStatus.GET_POPULAR_FALSE)
					{
						size = jo.getInt(JsonKeys.GET_POPULAE_SIZE);
						ja = jo.getJSONArray(JsonKeys.GET_POPULAE_INFO);
					}

					BA.refresh(TaskType.GET_POPULAE, state, size, ja);

				}
				catch (JSONException e)
				{

					e.printStackTrace();
				}

				break;
			}

			case TaskType.GET_FRIEND:
			{
				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.GET_FRIEND_RESPONSE);

				try
				{
					int state = jo.getInt(JsonKeys.JSON_STATUS);

					int size = 0;
					JSONArray ja = null;

					if (state == JsonStatus.GET_FRIENDS_SUCCESS)
					{
						size = jo.getInt(JsonKeys.GET_FRIEND_SIZE);
						ja = jo.getJSONArray(JsonKeys.GET_FRIEND_INFO);
					}

					BA.refresh(TaskType.GET_FRIEND, state, size, ja);
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			}

			case TaskType.FIND_FRIEND:
			{

				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.FIND_FRIEND_RESPONSE);

				JSONArray ja = null;
				int size = 0;

				if (jo != null)
				{

					try
					{
						int state = jo.getInt(JsonKeys.JSON_STATUS);

						if (state == JsonStatus.FIND_FRIENDS_SUCCESS)
						{
							size = jo.getInt(JsonKeys.FIND_FRIEND_SIZE);
							ja = jo.getJSONArray(JsonKeys.FIND_FRIEND_INFO);
							BA.refresh(TaskType.FIND_FRIEND, state, size, ja);
						}
						else
						{

							BA.refresh(TaskType.FIND_FRIEND, state);
						}
					}
					catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				else
				{
					BA.refresh(TaskType.FIND_FRIEND,
							JsonStatus.FIND_FRIENDS_FALSE);
				}
				break;
			}
			case TaskType.ADD_FRIENDS:
			{

				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.ADD_FRIEND_RESPONSE);

				if (jo != null)
				{

					try
					{
						int state = jo.getInt(JsonKeys.JSON_STATUS);
						BA.refresh(TaskType.ADD_FRIENDS, state);
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}
				else
				{

					BA.refresh(TaskType.ADD_FRIENDS,
							JsonStatus.ADD_FRIENDS_FALSE);
				}

				break;
			}

			case TaskType.GET_USER_INFO:
			{
				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.GET_USR_INFO_RESPONSE);

				try
				{
					int state = jo.getInt(JsonKeys.JSON_STATUS);

					JSONObject jodata = null;

					if (state == JsonStatus.GET_USER_INFO_SUCCESS)
					{
						jodata = jo.getJSONObject(JsonKeys.GET_USER_INFO);

					}

					BA.refresh(TaskType.GET_USER_INFO, state,
							jodata.getString("name"),
							jodata.getString("password"),
							jodata.getString("email"),
							jodata.getString("nickname"),
							jodata.getString("sex"));

				}
				catch (Exception e)
				{
					e.printStackTrace();
					BA.refresh(TaskType.GET_USER_INFO,
							JsonStatus.GET_USER_INFO_FALSE);
				}
				break;
			}

			case TaskType.UPDATE_USER_INFO:
			{

				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.UPDATE_USER_INFO_RESPONSE);

				try
				{
					int state = jo.getInt(JsonKeys.JSON_STATUS);
					BA.refresh(TaskType.UPDATE_USER_INFO, state);
				}
				catch (JSONException e)
				{
					e.printStackTrace();
					BA.refresh(TaskType.UPDATE_USER_INFO,
							JsonStatus.UPDATE_USER_INFO_FALSE);
				}
				break;
			}
			case TaskType.GET_COMMENTS:
			{

				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.GET_COMMENT_RESPONSE);

				try
				{
					int state = jo.getInt(JsonKeys.JSON_STATUS);

					if (state == JsonStatus.GET_COMMNET_SUCCESS)
					{

						int size = jo.getInt(JsonKeys.GET_COMMENT_SIZE);
						JSONArray ja = jo
								.getJSONArray(JsonKeys.GET_COMMENT_INFO);
						BA.refresh(TaskType.GET_COMMENTS, state, size, ja);

					}
					else
					{
						BA.refresh(TaskType.GET_COMMENTS,
								JsonStatus.GET_COMMNET_FALSE);
					}

				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					BA.refresh(TaskType.GET_COMMENTS,
							JsonStatus.GET_COMMNET_FALSE);
				}
				break;

			}
			case TaskType.INSERT_COMMENTS:
			{

				JSONObject jo = (JSONObject) map
						.get(MainServiceKey.INSERT_COMMENT_RESPONSE);
				try
				{
					int state = jo.getInt(JsonKeys.JSON_STATUS);
					BA.refresh(TaskType.INSERT_COMMENTS, state);
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					BA.refresh(TaskType.INSERT_COMMENTS,
							JsonStatus.INSERT_COMMNET_FALSE);
				}
				break;
			}

			case TaskType.SHARE_TO_SINA:
			{

				String res = (String) map
						.get(MainServiceKey.SHARE_TO_SINA_RESPONSE);
				if (res != null)
				{

					if (res.equals(SHARE_TO_SINA_FALSE))
					{
						BA.refresh(TaskType.SHARE_TO_SINA, SHARE_TO_SINA_FALSE);

					}
					else
					{
						BA.refresh(TaskType.SHARE_TO_SINA,
								SHARE_TO_SINA_SUCCESS);
					}

				}

				break;
			}

			default:
				break;
			}

		}

	};

	public static void removeActivity(String activityName)
	{

		if (activityMap.containsKey(activityName) == true)
		{
			activityMap.remove(activityName);
		}

	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		this._isRunable = true;
		new Thread(this).start();
		Log.d("WCH", "Service Start------>");

	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	/**
	 * 加入优先队列
	 * 
	 * @param T
	 */
	public static void addTaskQueue(MainServiceTask T)
	{
		taskQueue.add(T);
	}

	public static void addActivityMap(String activityname, Activity activity)
	{

		if (activityMap.containsKey(activityname) == true)
		{
			// do nothing!!
		}
		else
		{
			activityMap.put(activityname, activity);
		}
	}

	public void run()
	{
		// TODO Auto-generated method stub
		Log.d("WCH", "login run--------->start");
		try
		{
			MainServiceTask task = null;
			while (this._isRunable)
			{

				if (taskQueue.isEmpty() == false)
				{

					task = taskQueue.poll();
					if (null != task)
					{
						doTask(task);
					}
				}
				Thread.sleep(1000);
			}
		}
		catch (InterruptedException e)
		{
			Log.d("WCH" + debug_Tag, e.toString());
		}
	}

	private void doTask(final MainServiceTask T)
	{

		Message message = new Message();
		message.what = T.get_taskID();// 获取任务id

		/*
		 * 向handler传递的信息 包括类名字
		 */
		Map<String, Object> messageObjectMap = new HashMap<String, Object>();
		messageObjectMap.put(MainServiceTask.ACTIVITY_NAME,
				T.get_activityName());// 设置类的名字

		switch (T.get_taskID())
		{
		case TaskType.USER_LOGIN:// 用户登录
		{

			List<NameValuePair> postParas = new ArrayList<NameValuePair>();

			Iterator it = T.get_task().entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry entry = (Map.Entry) it.next();
				postParas.add(new BasicNameValuePair((String) entry.getKey(),
						(String) entry.getValue()));

			}
			JSONObject jObject = ServerHttpUtil.userLogHttpPost(postParas);
			Log.d("WCH", "json content------>" + jObject.toString());// error in
																		// this
			messageObjectMap.put(MainServiceKey.USER_LOG_RETURN_INFO, jObject);
			break;
		}

		case TaskType.USER_REGIST:// 用户注册
		{
			List<NameValuePair> postParas = new ArrayList<NameValuePair>();

			Iterator it = T.get_task().entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry entry = (Map.Entry) it.next();

				Log.d("WCH", "S------>user account" + (String) entry.getKey());
				Log.d("WCH",
						"S------>user password" + (String) entry.getValue());

				postParas.add(new BasicNameValuePair((String) entry.getKey(),
						(String) entry.getValue()));
			}
			JSONObject jObject = ServerHttpUtil.registHttpPost(postParas);
			Log.d("WCH", "json content------>" + jObject.toString());
			messageObjectMap.put(MainServiceKey.REGIST_RETURN_INFO, jObject);
			break;
		}

		case TaskType.WEBVIEW_REQUEST_TOKEN_TENCENT:// 腾讯获取授权

			Log.d("WCH", "requestToken---------------->start"
					+ TencentApp.oAuth.getOauthToken() + "----->state"
					+ TencentApp.oAuth.getStatus());

			try
			{
				TencentApp.oAuth = OAuthV1Client.requestToken(TencentApp.oAuth);

				Log.d("WCH", "requestToken---------------->end"
						+ TencentApp.oAuth.getOauthToken() + "------>state"
						+ TencentApp.oAuth.getStatus());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				Log.d("WCH", "requestToken---------------->exception");
			}
			break;

		case TaskType.UPLOAD_TEXT_WITH_PIC_TENCENT:
		{
			TAPI tAPI = null;
			try
			{
				tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_1);
				Map<String, Object> info = T.get_task();
				TencentUpLoadContentBean bean = (TencentUpLoadContentBean) info
						.get(TencentUpLoadContentBean.class.getName());

				String fileName = bean.getFileName();
				String text = bean.getText();
				Log.d("WCH", "text-------->");
				Log.d("WCH", fileName);
				Log.d("WCH", "text-------->" + text);
				Log.d("WCH", text);

				String response = tAPI
						.addPic(TencentApp.oAuth,
								"json",
								("".equals(text) == true ? getString(R.string.empty_upload_text)
										: text), "127.0.0.1", fileName);
				Log.d("WCH", "腾讯response------>" + response);
				messageObjectMap.put(MainServiceKey.TECENT_UPLOAD_RESPONSE,
						new JSONObject(response));
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tAPI.shutdownConnection();
			break;
		}

		case TaskType.UPLOAD_TEXT_WITH_PIC_PHOTOSHARE:
		{

			Map<String, Object> info = T.get_task();
			String fileName = (String) info
					.get(CameraUpLoadMainActivity.FILENAME);
			String imgtext = (String) info
					.get(CameraUpLoadMainActivity.IMGTEXT);
			int response = ServerHttpUtil.userUpLoadPic(new File(fileName),
					imgtext, ServerHttpConfig.HTTP_UPLOAD_TEXT_WITH_PIC);
			messageObjectMap.put(MainServiceKey.PC_UPLOAD_RESPONSE, response);

			break;
		}
		case TaskType.REFEUSH_FOEUMS:// 刷新新鲜事和获取新鲜事都是用的相同的方法
		case TaskType.GET_FORUMS:// 刷新新鲜事和获取新鲜事都是用的相同的方法
		{

			List<NameValuePair> postParas = new ArrayList<NameValuePair>();

			postParas.add(new BasicNameValuePair(
					ServerHttpGetForumsParas.NOW_PAGE, ForumsActivity.NEXT_PAGE
							+ ""));
			postParas.add(new BasicNameValuePair(
					ServerHttpGetForumsParas.USER_ID, LogUserBean.LOG_USER_ID));

			JSONObject jo = ServerHttpUtil.getForums(postParas);

			messageObjectMap.put(MainServiceKey.GET_FORUMS_RESPONSE, jo);

			break;
		}

		case TaskType.SUPPORT:
		{
			// 点赞后执行结果post服务器

			Map<String, Object> p = T.get_task();
			Log.d("WCH", "------------------->do support post!5");
			List<NameValuePair> postParas = new ArrayList<NameValuePair>();

			postParas.add(new BasicNameValuePair(
					ServerHttpDoSupportParas.PICID, p
							.get(SupportManager.SUPPORT_PIC_ID) + ""));
			postParas.add(new BasicNameValuePair(
					ServerHttpGetForumsParas.USER_ID, LogUserBean.LOG_USER_ID));

			JSONObject jo = ServerHttpUtil.doSupport(postParas);
			Log.d("WCH", "------------------->do support post!6");
			messageObjectMap.put(MainServiceKey.DO_SUPPORT_RESPONSE, jo);

			messageObjectMap.put(SupportManager.SUPPORT_PIC_CALLBACK,
					p.get(SupportManager.SUPPORT_PIC_CALLBACK));
			messageObjectMap.put(SupportManager.SUPPORT_PIC_ID,
					p.get(SupportManager.SUPPORT_PIC_ID));

			break;
		}

		case TaskType.GET_POPULAE:
		{

			JSONObject jo = ServerHttpUtil
					.getPopular(new ArrayList<NameValuePair>());

			messageObjectMap.put(MainServiceKey.GET_POPULAR_RESPONSE, jo);

			break;
		}

		case TaskType.GET_FRIEND:
		{

			List<NameValuePair> postParas = new ArrayList<NameValuePair>();
			postParas
					.add(new BasicNameValuePair(
							ServerHttpGetFriendsParas.OWNERID,
							LogUserBean.LOG_USER_ID));

			JSONObject jo = ServerHttpUtil.getFriends(postParas);
			messageObjectMap.put(MainServiceKey.GET_FRIEND_RESPONSE, jo);
			break;
		}

		case TaskType.FIND_FRIEND:
		{

			Map<String, Object> map = T.get_task();

			String findcontent = (String) map
					.get(FriendsFindActivity.FIND_NAME);

			Log.d("WCHH", "------>text" + findcontent);
			List<NameValuePair> postParas = new ArrayList<NameValuePair>();
			postParas.add(new BasicNameValuePair(
					ServerHttpFindFriendsParas.FRIENDS_NAME, findcontent));
			JSONObject jo = ServerHttpUtil.findFriends(postParas);
			messageObjectMap.put(MainServiceKey.FIND_FRIEND_RESPONSE, jo);
			break;
		}

		case TaskType.ADD_FRIENDS:
		{

			Map<String, Object> map = T.get_task();

			int friendid = (Integer) map
					.get(FindFriendsViewAdapter.ADD_FRIENDS_ID);

			List<NameValuePair> postParas = new ArrayList<NameValuePair>();
			postParas.add(new BasicNameValuePair(
					ServerHttpAddFriendsParas.JOINID, friendid + ""));
			postParas
					.add(new BasicNameValuePair(
							ServerHttpAddFriendsParas.OWNERID,
							LogUserBean.LOG_USER_ID));
			JSONObject jo = ServerHttpUtil.addFriends(postParas);
			messageObjectMap.put(MainServiceKey.ADD_FRIEND_RESPONSE, jo);
			break;

		}
		case TaskType.GET_USER_INFO:
		{

			List<NameValuePair> postParas = new ArrayList<NameValuePair>();
			postParas
					.add(new BasicNameValuePair(
							ServerHttpGetUserInfoParas.USERID,
							LogUserBean.LOG_USER_ID));
			JSONObject jo = ServerHttpUtil.getUserInfo(postParas);
			messageObjectMap.put(MainServiceKey.GET_USR_INFO_RESPONSE, jo);
			break;

		}

		case TaskType.UPDATE_USER_INFO:
		{

			Map<String, Object> map = T.get_task();

			ProfileActivity.UpdateUserInfoBean bean = (UpdateUserInfoBean) map
					.get(ProfileActivity.UPDATE_DATA);

			List<NameValuePair> postParas = new ArrayList<NameValuePair>();
			postParas.add(new BasicNameValuePair(
					ServerHttpUpdateUserInfoParas.USERID,
					LogUserBean.LOG_USER_ID));
			postParas.add(new BasicNameValuePair(
					ServerHttpUpdateUserInfoParas.EMAIL, bean.getEmail()));
			postParas
					.add(new BasicNameValuePair(
							ServerHttpUpdateUserInfoParas.NICKNAME, bean
									.getNickname()));
			postParas
					.add(new BasicNameValuePair(
							ServerHttpUpdateUserInfoParas.PASSWORD, bean
									.getPassword()));

			postParas.add(new BasicNameValuePair(
					ServerHttpUpdateUserInfoParas.SEX, bean.getSex()));
			postParas.add(new BasicNameValuePair(
					ServerHttpUpdateUserInfoParas.USER_NAME, bean.getName()));

			JSONObject jo = ServerHttpUtil.updateUserInfo(postParas);
			messageObjectMap.put(MainServiceKey.UPDATE_USER_INFO_RESPONSE, jo);
			break;

		}

		case TaskType.GET_COMMENTS:
		{

			Map<String, Object> map = T.get_task();

			String picid = (String) map.get(ForumsDetailActivity.PIC_ID);

			List<NameValuePair> para = new ArrayList<NameValuePair>();
			para.add(new BasicNameValuePair(ServerHttpGetCommentParas.PICID,
					picid));
			JSONObject jo = ServerHttpUtil.getComment(para);
			messageObjectMap.put(MainServiceKey.GET_COMMENT_RESPONSE, jo);
			break;

		}

		case TaskType.INSERT_COMMENTS:
		{

			Map<String, Object> map = T.get_task();

			String picid = (String) map.get(ForumsDetailActivity.PIC_ID);

			String remark_content = (String) map
					.get(ForumsDetailActivity.COMMENT_CONTENT);

			List<NameValuePair> para = new ArrayList<NameValuePair>();
			para.add(new BasicNameValuePair(ServerHttpInsertCommentParas.PICID,
					picid));
			para.add(new BasicNameValuePair(
					ServerHttpInsertCommentParas.CONTENT, remark_content));
			para.add(new BasicNameValuePair(
					ServerHttpInsertCommentParas.USERID,
					LogUserBean.LOG_USER_ID));
			JSONObject jo = ServerHttpUtil.insertComment(para);
			messageObjectMap.put(MainServiceKey.INSERT_COMMENT_RESPONSE, jo);
			break;
		}
		case TaskType.GET_ASSCESS_TOKEN_TENCENT:
		{

			try
			{
				TencentApp.oAuth = OAuthV1Client.accessToken(TencentApp.oAuth);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			Log.d("WCH",
					"------------>access token"
							+ TencentApp.oAuth.getOauthToken());
			Log.d("WCH",
					"after access token  statue----->"
							+ TencentApp.oAuth.getStatus());

			break;
		}
		case TaskType.SHARE_TO_SINA:
		{
			Map<String, Object> map = T.get_task();

			String file = (String) map.get(CameraUpLoadMainActivity.FILENAME);
			String text = (String) map.get(CameraUpLoadMainActivity.IMGTEXT);

			new StatusesAPI(SinaApp.mAccessToken).uploadPic(text, file,
					new RequestListener()
					{

						public void onComplete(String arg0)
						{
							Message message = new Message();
							message.what = TaskType.SHARE_TO_SINA;// 获取任务id

							/*
							 * 向handler传递的信息 包括类名字
							 */
							Map<String, Object> messageObjectMap = new HashMap<String, Object>();
							messageObjectMap.put(MainServiceTask.ACTIVITY_NAME,
									T.get_activityName());// 设置类的名字
							messageObjectMap.put(
									MainServiceKey.SHARE_TO_SINA_RESPONSE,
									SHARE_TO_SINA_SUCCESS);

							message.obj = messageObjectMap;
							_handler.sendMessage(message);
						}

						public void onComplete4binary(ByteArrayOutputStream arg0)
						{
							Message message = new Message();
							message.what = TaskType.SHARE_TO_SINA;// 获取任务id

							/*
							 * 向handler传递的信息 包括类名字
							 */
							Map<String, Object> messageObjectMap = new HashMap<String, Object>();
							messageObjectMap.put(MainServiceTask.ACTIVITY_NAME,
									T.get_activityName());// 设置类的名字
							messageObjectMap.put(
									MainServiceKey.SHARE_TO_SINA_RESPONSE,
									SHARE_TO_SINA_FALSE);

							message.obj = messageObjectMap;
							_handler.sendMessage(message);

						}

						public void onError(WeiboException arg0)
						{
							Message message = new Message();
							message.what = TaskType.SHARE_TO_SINA;// 获取任务id

							/*
							 * 向handler传递的信息 包括类名字
							 */
							Map<String, Object> messageObjectMap = new HashMap<String, Object>();
							messageObjectMap.put(MainServiceTask.ACTIVITY_NAME,
									T.get_activityName());// 设置类的名字
							messageObjectMap.put(
									MainServiceKey.SHARE_TO_SINA_RESPONSE,
									SHARE_TO_SINA_FALSE);

							message.obj = messageObjectMap;
							_handler.sendMessage(message);

						}

						public void onIOException(IOException arg0)
						{
							Message message = new Message();
							message.what = TaskType.SHARE_TO_SINA;// 获取任务id

							/*
							 * 向handler传递的信息 包括类名字
							 */
							Map<String, Object> messageObjectMap = new HashMap<String, Object>();
							messageObjectMap.put(MainServiceTask.ACTIVITY_NAME,
									T.get_activityName());// 设置类的名字
							messageObjectMap.put(
									MainServiceKey.SHARE_TO_SINA_RESPONSE,
									SHARE_TO_SINA_FALSE);

							message.obj = messageObjectMap;
							_handler.sendMessage(message);

						}

					});

			break;
		}

		default:
			break;
		}
		message.obj = messageObjectMap;
		_handler.sendMessage(message);
	}

}
