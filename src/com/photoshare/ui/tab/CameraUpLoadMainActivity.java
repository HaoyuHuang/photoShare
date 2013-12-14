package com.photoshare.ui.tab;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.photoshare.R;
import com.photoshare.RenRen.RenRenApp;
import com.photoshare.Sina.SinaApp;
import com.photoshare.Tencent.TencentApp;
import com.photoshare.Tencent.TencentOAuthWebView;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.bean.TencentUpLoadContentBean;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.service.MainService;
import com.photoshare.ui.interfaces.BaseActivity;
import com.renn.rennsdk.RennClient;
import com.renn.rennsdk.RennClient.LoginListener;
import com.renn.rennsdk.RennExecutor.CallBack;
import com.renn.rennsdk.RennResponse;
import com.renn.rennsdk.exception.RennException;
import com.renn.rennsdk.param.UploadPhotoParam;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.WeiboParameters;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.utils.UIUtils;



public class CameraUpLoadMainActivity extends Activity implements BaseActivity,
		OnClickListener

{

	public final static int TECENT_REQUEST_CODE = 1;
	public final static String FILENAME = "FILENAME";// 用于map传递参数
	public final static String IMGTEXT = "IMGTEXT";
	private static final int MSG_FETCH_TOKEN_SUCCESS = 1;
	private static final int MSG_FETCH_TOKEN_FAILED = 2;
	private ImageView _IV;
	private Button _share;
	private Button _back;
	private String conmment;
	private String _fileName;// 包括路径的
	private RennClient rennClient;
	private ProgressDialog _PD;
	private Button _PS;
	private Button _Tencent;
	private Button _Sina;
	private Button _RenRen;
	private int _shareType = R.id.shareto_PS;

	private WeiboAuth mWeiboAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		renrenInit();
		setContentView(R.layout.go_share);

		

		this._back = (Button) this.findViewById(R.id.go_upload_back);
		this._share = (Button) this.findViewById(R.id.go_upload_share);

		init();

		/*
		 * 四大天王 start
		 */
		this._PS = (Button) this.findViewById(R.id.shareto_PS);
		this._Tencent = (Button) this.findViewById(R.id.shareto_qq);
		this._RenRen = (Button) this.findViewById(R.id.shareto_renren);
		this._Sina = (Button) this.findViewById(R.id.shareto_xinlang);
		this._PS.setOnClickListener(this);
		this._RenRen.setOnClickListener(this);
		this._Sina.setOnClickListener(this);
		this._Tencent.setOnClickListener(this);
		/*
		 * 四大天王 end
		 */
		this.mWeiboAuth = new WeiboAuth(CameraUpLoadMainActivity.this, SinaApp.APP_KEY,
				SinaApp.REDIRECT_URL, SinaApp.SCOPE);
		this._back.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{

				CameraUpLoadMainActivity.this.finish();

			}
		});
		this._share.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{

				switch (_shareType)
				{
				case R.id.shareto_qq:
				/*
				 * 腾讯图片分享
				 */
				{
					if (_PD == null)
					{
						_PD = new ProgressDialog(CameraUpLoadMainActivity.this);
						_PD.setCancelable(false);

						_PD.setMessage(CameraUpLoadMainActivity.this
								.getString(R.string.on_uploding_plase_wait));
						_PD.show();
					}
					String temptip = CameraUpLoadMainActivity.this
							.getString(R.string.empty_upload_text);
					String finalstring = conmment.equals("") == true ? temptip
							: conmment;
					Map<String, Object> temptask = new HashMap<String, Object>();
					temptask.put(TencentUpLoadContentBean.class.getName(),
							new TencentUpLoadContentBean(finalstring,
									CameraUpLoadMainActivity.this._fileName));
					MainServiceTask MST = new MainServiceTask(
							TaskType.UPLOAD_TEXT_WITH_PIC_TENCENT, temptask,
							CameraUpLoadMainActivity.class.getName());
					MainService.addActivityMap(
							CameraUpLoadMainActivity.class.getName(),
							CameraUpLoadMainActivity.this);
					MainService.addTaskQueue(MST);
					break;
				}

				case R.id.shareto_renren:
				/*
				 * 人人分享图片
				 */
				{

					UploadPhotoParam param = new UploadPhotoParam();
					try
					{
						param.setFile(new File(
								CameraUpLoadMainActivity.this._fileName));
						param.setDescription(conmment);
					}
					catch (Exception e)
					{
					}
					if (_PD == null)
					{
						_PD = new ProgressDialog(CameraUpLoadMainActivity.this);
						_PD.setCancelable(false);

						_PD.setMessage(CameraUpLoadMainActivity.this
								.getString(R.string.on_uploding_plase_wait));
						_PD.show();
					}
					try
					{
						rennClient.getRennService().sendAsynRequest(param,
								new CallBack()
								{

									public void onSuccess(RennResponse response)
									{

										Toast.makeText(
												CameraUpLoadMainActivity.this,
												CameraUpLoadMainActivity.this
														.getString(R.string.upload_success),
												Toast.LENGTH_SHORT).show();
										if (_PD != null)
										{
											_PD.dismiss();
											_PD = null;
										}
									}

									public void onFailed(String errorCode,
											String errorMessage)
									{
										Toast.makeText(
												CameraUpLoadMainActivity.this,
												CameraUpLoadMainActivity.this
														.getString(R.string.upload_success),
												Toast.LENGTH_SHORT).show();
										if (_PD != null)
										{
											_PD.dismiss();
											_PD = null;
										}
									}
								});
					}
					catch (RennException e1)
					{
						e1.printStackTrace();
					}
					break;
				}

				case R.id.shareto_PS:
				{

					String temptip = CameraUpLoadMainActivity.this
							.getString(R.string.empty_upload_text);
					String finalstring = conmment.equals("") == true ? temptip
							: conmment;

					Map<String, Object> Paras = new HashMap<String, Object>();
					Paras.put(FILENAME, _fileName);
					Paras.put(IMGTEXT, finalstring);

					MainServiceTask MST = new MainServiceTask(
							TaskType.UPLOAD_TEXT_WITH_PIC_PHOTOSHARE, Paras,
							CameraUpLoadMainActivity.class.getName());
					MainService.addActivityMap(
							CameraUpLoadMainActivity.class.getName(),
							CameraUpLoadMainActivity.this);
					MainService.addTaskQueue(MST);

					break;
				}

				case R.id.shareto_xinlang:
				{

					Map<String, Object> paras = new HashMap<String, Object>();

					paras.put(FILENAME, CameraUpLoadMainActivity.this._fileName);
					paras.put(IMGTEXT, conmment);

					MainServiceTask MST = new MainServiceTask(
							TaskType.SHARE_TO_SINA, paras,
							CameraUpLoadMainActivity.class.getName());

					MainService.addActivityMap(
							CameraUpLoadMainActivity.class.getName(),
							CameraUpLoadMainActivity.this);

					MainService.addTaskQueue(MST);

					break;
				}
				default:
					break;
				}
			}
		});

	}

	private void initButton()
	{
		this._PS.setBackgroundResource(R.drawable.ps);
		this._RenRen.setBackgroundResource(R.drawable.renren);
		this._Tencent.setBackgroundResource(R.drawable.tengxun);
		this._Sina.setBackgroundResource(R.drawable.xinlang);

	}

	private void renrenInit()
	{
		rennClient = RennClient.getInstance(this);
		rennClient.init(RenRenApp.APP_ID, RenRenApp.APP_KEY,
				RenRenApp.APP_SECRET);
		rennClient
				.setScope("read_user_blog read_user_photo read_user_status read_user_album "
						+ "read_user_comment read_user_share publish_blog publish_share "
						+ "send_notification photo_upload status_update create_album "
						+ "publish_comment publish_feed");
		rennClient.setTokenType("bearer");
	}

	public void init()
	{
		// TODO Auto-generated method stub

		Intent intent = this.getIntent();
		_fileName = intent.getStringExtra(CameraActivity.CRAMERA_FILE_NAME);

		conmment = intent.getStringExtra(CameraActivity.CRAMERA_COMMENT);

	}

	public void refresh(int taskID, Object... paras)
	{
		// TODO Auto-generated method stub

		switch (taskID)
		{
		case TaskType.UPLOAD_TEXT_WITH_PIC_TENCENT:
		{

			Toast.makeText(
					CameraUpLoadMainActivity.this,
					CameraUpLoadMainActivity.this
							.getString(R.string.upload_success),
					Toast.LENGTH_SHORT).show();
			if (_PD != null)
			{
				_PD.dismiss();
				_PD = null;
			}
			break;
		}
		case TaskType.UPLOAD_TEXT_WITH_PIC_PHOTOSHARE:
		{

			if (((Integer) paras[0]).equals(Integer
					.valueOf(JsonStatus.UPLOAD_PIC_SUCCESS)) == true)
			{
				Toast.makeText(this, getString(R.string.upload_success),
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(this, getString(R.string.upload_false),
						Toast.LENGTH_SHORT).show();
			}
			break;
		}

		case TaskType.SHARE_TO_SINA:
		{

			if ((((String) paras[0]).equals(MainService.SHARE_TO_SINA_FALSE)))
			{
				Toast.makeText(this, "上传成功！", Toast.LENGTH_SHORT).show();

			}
			else
			{
				Toast.makeText(this, "上传失败！", Toast.LENGTH_SHORT).show();
			}

		}

		default:
			break;
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == TECENT_REQUEST_CODE)
		{
			if (resultCode == TencentOAuthWebView.RESULT_CODE)
			{
				try
				{

					MainServiceTask MST = new MainServiceTask(
							TaskType.GET_ASSCESS_TOKEN_TENCENT, null, null);
					MainService.addTaskQueue(MST);

					Log.d("WCH", "before access token  statue----->"
							+ TencentApp.oAuth.getStatus());

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		}
	}

	public void onClick(View v)
	{
		// TODO Auto-generated method stub

		_shareType = v.getId();

		initButton();

		switch (_shareType)
		{
		case R.id.shareto_PS:
			Log.d("WCH", "share to----->PS");

			this._PS.setBackgroundResource(R.drawable.ps_s);

			break;
		case R.id.shareto_qq:
			this._Tencent.setBackgroundResource(R.drawable.tengxun_s);
			Log.d("WCH", "share to----->qq");
			Intent intent = new Intent(CameraUpLoadMainActivity.this,
					TencentOAuthWebView.class);// 创建Intent，使用WebView让用户授权

			startActivityForResult(intent, TECENT_REQUEST_CODE);
			break;
		case R.id.shareto_renren:
		{
			Log.d("WCH", "share to----->renren");
			this._RenRen.setBackgroundResource(R.drawable.renren_s);
			rennClient.setLoginListener(new LoginListener()
			{

				public void onLoginSuccess()
				{
					// TODO Auto-generated method stub
					Toast.makeText(
							CameraUpLoadMainActivity.this,
							CameraUpLoadMainActivity.this
									.getString(R.string.oather_success),
							Toast.LENGTH_SHORT).show();

				}

				public void onLoginCanceled()
				{
					Toast.makeText(
							CameraUpLoadMainActivity.this,
							CameraUpLoadMainActivity.this
									.getString(R.string.oather_cancle),
							Toast.LENGTH_SHORT).show();

				}
			});
			rennClient.login(CameraUpLoadMainActivity.this);
		}

			break;
		case R.id.shareto_xinlang:
			Log.d("WCH", "share to----->xinlang");

			this._Sina.setBackgroundResource(R.drawable.xinlang_s);

			mWeiboAuth
					.authorize(new AuthListener(), WeiboAuth.OBTAIN_AUTH_CODE);

			break;
		default:
			break;
		}
	}

	/**
	 * 微博认证授权回调类。
	 */
	class AuthListener implements WeiboAuthListener
	{

		public void onComplete(Bundle values)
		{
			if (null == values)
			{
				Toast.makeText(CameraUpLoadMainActivity.this, "授权失败！",
						Toast.LENGTH_SHORT).show();
				return;
			}

			String code = values.getString("code");
			if (TextUtils.isEmpty(code))
			{
				Toast.makeText(CameraUpLoadMainActivity.this, "授权失败",
						Toast.LENGTH_SHORT).show();
				return;
			}

			SinaApp.CODE = code;
			fetchTokenAsync(code, SinaApp.APP_SECRET);
			Toast.makeText(CameraUpLoadMainActivity.this, "授权成功",
					Toast.LENGTH_SHORT).show();
		}

		public void onCancel()
		{
			Toast.makeText(CameraUpLoadMainActivity.this, "授权取消",
					Toast.LENGTH_LONG).show();
		}

		public void onWeiboException(WeiboException e)
		{
			UIUtils.showToast(CameraUpLoadMainActivity.this, "授权异常",
					Toast.LENGTH_LONG);
		}
	}

	/**
	 * 异步获取 Token。
	 * 
	 * @param authCode
	 *            授权 Code，该 Code 是一次性的，只能被获取一次 Token
	 * @param appSecret
	 *            应用程序的 APP_SECRET，请务必妥善保管好自己的 APP_SECRET，
	 *            不要直接暴露在程序中，此处仅作为一个DEMO来演示。
	 */
	public void fetchTokenAsync(String authCode, String appSecret)
	{

		WeiboParameters requestParams = new WeiboParameters();
		requestParams.add(WBConstants.AUTH_PARAMS_CLIENT_ID, SinaApp.APP_KEY);
		requestParams.add(WBConstants.AUTH_PARAMS_CLIENT_SECRET, appSecret);
		requestParams.add(WBConstants.AUTH_PARAMS_GRANT_TYPE,
				"authorization_code");
		requestParams.add(WBConstants.AUTH_PARAMS_CODE, authCode);
		requestParams.add(WBConstants.AUTH_PARAMS_REDIRECT_URL,
				SinaApp.REDIRECT_URL);

		/**
		 * 请注意： {@link RequestListener} 对应的回调是运行在后台线程中的， 因此，需要使用 Handler 来配合更新
		 * UI。
		 */
		AsyncWeiboRunner.request(SinaApp.OAUTH2_ACCESS_TOKEN_URL,
				requestParams, "POST", new RequestListener()
				{
					public void onComplete(String response)
					{

						Oauth2AccessToken token = Oauth2AccessToken
								.parseAccessToken(response);
						if (token != null && token.isSessionValid())
						{

							SinaApp.mAccessToken = token;
							mHandler.obtainMessage(MSG_FETCH_TOKEN_SUCCESS)
									.sendToTarget();
						}
						else
						{
							Toast.makeText(CameraUpLoadMainActivity.this,
									"授权失败！", Toast.LENGTH_SHORT).show();
						}
					}

					public void onComplete4binary(
							ByteArrayOutputStream responseOS)
					{

						mHandler.obtainMessage(MSG_FETCH_TOKEN_FAILED)
								.sendToTarget();
					}

					public void onIOException(IOException e)
					{
						mHandler.obtainMessage(MSG_FETCH_TOKEN_FAILED)
								.sendToTarget();
					}

					public void onError(WeiboException e)
					{
						mHandler.obtainMessage(MSG_FETCH_TOKEN_FAILED)
								.sendToTarget();
					}
				});
	}

	/**
	 * 该 Handler 配合 {@link RequestListener} 对应的回调来更新 UI。
	 */
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case MSG_FETCH_TOKEN_SUCCESS:
				// 显示 Token
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.format(new java.util.Date(SinaApp.mAccessToken
								.getExpiresTime()));

				Toast.makeText(CameraUpLoadMainActivity.this, "授权成功！",
						Toast.LENGTH_SHORT).show();
				break;

			case MSG_FETCH_TOKEN_FAILED:
				Toast.makeText(CameraUpLoadMainActivity.this, "授权失败！",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};

}
