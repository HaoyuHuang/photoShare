package com.photoshare.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.photoshare.R;
import com.photoshare.bean.LogUserBean;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.http.post.paras.ServerHttpUserLogPostParas;
import com.photoshare.jsonstatus.JsonKeys;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.service.MainService;
import com.photoshare.ui.dlg.UserLogAlertDialog;
import com.photoshare.ui.interfaces.BaseActivity;

public class LogInActivity extends Activity implements BaseActivity
{

	private Button _btnlog = null;

	private Button _btnreg = null;

	private CheckBox _cbremberpassword = null;

	private TextView _account = null;
	private TextView _password = null;

	private ProgressDialog _pd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login);

		_btnlog = (Button) this.findViewById(R.id.btn_log);
		_btnreg = (Button) this.findViewById(R.id.btn_reg);
		_cbremberpassword = (CheckBox) this.findViewById(R.id.remberpassword);

		_account = (TextView) this.findViewById(R.id.account);
		_password = (TextView) this.findViewById(R.id.password);

		// 获取reuqest token
		MainService.addActivityMap(LogInActivity.class.getName(),
				LogInActivity.this);

		MainServiceTask MST = new MainServiceTask(
				TaskType.WEBVIEW_REQUEST_TOKEN_TENCENT, null,
				LogInActivity.class.getName());
		MainService.addTaskQueue(MST);
		Log.d("WCH", "login--------->start");
		this._btnlog.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{

//				 LogInActivity.this.startActivity(new
//				 Intent(LogInActivity.this,
//				 MainActivity.class));

				MainService.addActivityMap(LogInActivity.class.getName(),
						LogInActivity.this);
				Map<String, Object> T = new HashMap<String, Object>();

				T.put(ServerHttpUserLogPostParas.ACCOUNT, _account.getText()
						.toString());
				T.put(ServerHttpUserLogPostParas.PASSWORD, _password.getText()
						.toString());

				Log.d("WCH", "log user acconunt------>"
						+ _account.getText().toString());

				Log.d("WCH", "log user password------>"
						+ _password.getText().toString());

				MainServiceTask MST = new MainServiceTask(TaskType.USER_LOGIN,
						T, LogInActivity.class.getName());
				MainService.addTaskQueue(MST);
				if (LogInActivity.this._pd == null)
				{

					LogInActivity.this._pd = new ProgressDialog(
							LogInActivity.this);
				}
				LogInActivity.this._pd.setMessage(LogInActivity.this
						.getResources().getString(
								R.string.prodlg_user_log_wait_info));
				LogInActivity.this._pd.setCancelable(false);
				LogInActivity.this._pd.show();
				Log.d("WCH", "------------------>finsh!");

			}
		});

		this._btnreg.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{
				Intent IT = new Intent(LogInActivity.this, RegistActivity.class);
				LogInActivity.this.startActivity(IT);
			}
		});

	}

	public void init()
	{

	}

	public void refresh(int taskID, Object... paras)
	{
		// TODO Auto-generated method stub

		switch (taskID)
		{

		case TaskType.USER_LOGIN:
		{

			Resources rs = this.getResources();
			if (LogInActivity.this._pd != null)
			{
				LogInActivity.this._pd.dismiss();
			}
			Integer Status = (Integer) paras[0];
			if (Status.equals(Integer.valueOf(JsonStatus.USER_LOG_SUCCESS)))
			{
				LogUserBean.LOG_USER_ID = (Integer) paras[1] + "";
				LogUserBean.LOG_USER_ACCOUNT = (String) paras[2];
				LogUserBean.LOG_USER_PASSWORD = (String) paras[3];
				LogUserBean.LOG_USER_NAME = (String) paras[4];
				LogUserBean.LOG_USER_SEX = (String) paras[5];
				LogUserBean.LOG_USER_NICKNAME = (String) paras[6];
				LogUserBean.LOG_USER_EMAIL = (String) paras[7];

				Log.d("WCH", "user info--------->"
						+ LogUserBean.LOG_USER_ACCOUNT);
				Log.d("WCH", "user info--------->" + LogUserBean.LOG_USER_ID);
				Log.d("WCH", "user info--------->"
						+ LogUserBean.LOG_USER_PASSWORD);
				Log.d("WCH", "user info--------->" + LogUserBean.LOG_USER_NAME);
				Log.d("WCH", "user info--------->" + LogUserBean.LOG_USER_EMAIL);

				LogInActivity.this.startActivity(new Intent(LogInActivity.this,
						MainActivity.class));

			}
			else if (Status.equals(Integer.valueOf(JsonStatus.USER_LOG_FALSE)))
			{

				UserLogAlertDialog ULAD = new UserLogAlertDialog(this);
				ULAD.setInfo(rs
						.getString(R.string.user_log_alertdig_false_info));
				ULAD.show();

			}
			else
			{
				UserLogAlertDialog ULAD = new UserLogAlertDialog(this);
				ULAD.setInfo(rs
						.getString(R.string.user_log_alertdig_excption_info));
				ULAD.show();
			}
			break;
		}
		default:
			break;
		}

	}
}
