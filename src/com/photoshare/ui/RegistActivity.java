package com.photoshare.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.photoshare.R;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.http.post.paras.ServerHttpRegistPostParas;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.service.MainService;
import com.photoshare.ui.dlg.RegistAlertDialog;
import com.photoshare.ui.dlg.RegistSuccessDialog;
import com.photoshare.ui.interfaces.BaseActivity;

public class RegistActivity extends Activity implements BaseActivity
{

	private Button _registbutton;
	private EditText _account;
	private EditText _password;
	private EditText _password_sure;
	private EditText _name;
	private EditText _nickname;
	private EditText _email;
	private RadioGroup _sex;

	private String _user_sex;
	private ProgressDialog _pd;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
		this.setContentView(R.layout.regist);

		_account = (EditText) this.findViewById(R.id.regist_edtv_username);
		_email = (EditText) this.findViewById(R.id.regist_edtv_email);
		_name = (EditText) this.findViewById(R.id.regist_edtv_name);
		_nickname = (EditText) this.findViewById(R.id.regist_edtv_nickname);
		_password = (EditText) this.findViewById(R.id.regist_edtv_password);
		_password_sure = (EditText) this
				.findViewById(R.id.regist_edtv_password_sure);
		_sex = (RadioGroup) this.findViewById(R.id.regist_radiogroup_sex);
		_registbutton = (Button) this
				.findViewById(R.id.regist_btn_regist_start);
		_user_sex = this.getResources().getString(R.string.user_sex_m);

		this._registbutton.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				RegistAlertDialog UD = new RegistAlertDialog(
						RegistActivity.this);
				Resources RS = RegistActivity.this.getResources();

				if (_account.getText().toString().equals("") == true)
				{

					UD.setInfo(RS.getString(R.string.regist_null_account),
							R.id.text_info);
					UD.show();

				}
				else if (_password.getText().toString().equals("") == true)
				{
					UD.setInfo(RS.getString(R.string.regist_null_password),
							R.id.text_info);
					UD.show();

				}
				else if (_password_sure.getText().toString().equals("") == true)
				{
					UD.setInfo(
							RS.getString(R.string.regist_null_password_sure),
							R.id.text_info);
					UD.show();

				}

				else if (_name.getText().toString().equals("") == true)
				{
					UD.setInfo(RS.getString(R.string.regist_null_name),
							R.id.text_info);
					UD.show();

				}
				else if (_nickname.getText().toString().equals("") == true)
				{
					UD.setInfo(RS.getString(R.string.regist_null_nickname),
							R.id.text_info);
					UD.show();
				}
				else if (_email.getText().toString().equals("") == true)
				{
					UD.setInfo(RS.getString(R.string.regist_null_email),
							R.id.text_info);
					UD.show();
				}
				else if (_password.getText().toString()
						.equals(_password_sure.getText().toString()) == false)
				{
					UD.setInfo(RS.getString(R.string.password_sure_noequal),
							R.id.text_info);
					UD.show();
				}
				else
				{

					Map<String, Object> TaskContent = new HashMap<String, Object>();
					TaskContent.put(ServerHttpRegistPostParas.ACCOUNT, _account
							.getText().toString());
					TaskContent.put(ServerHttpRegistPostParas.EMAIL, _email
							.getText().toString());
					TaskContent.put(ServerHttpRegistPostParas.NAME, _name
							.getText().toString());
					TaskContent.put(ServerHttpRegistPostParas.NICKNAME,
							_nickname.getText().toString());
					TaskContent.put(ServerHttpRegistPostParas.PASSWORD,
							_password.getText().toString());
					TaskContent.put(ServerHttpRegistPostParas.SEX,
							RegistActivity.this._user_sex);

					Log.d("WCH", "Sex-------->" + RegistActivity.this._user_sex);

					MainServiceTask MST = new MainServiceTask(
							TaskType.USER_REGIST, TaskContent,
							RegistActivity.class.getName());
					
					
					MainService.addActivityMap(RegistActivity.class.getName(), RegistActivity.this);
					
					MainService.addTaskQueue(MST);
					if (RegistActivity.this._pd == null)
					{

						RegistActivity.this._pd = new ProgressDialog(
								RegistActivity.this);
					}

					RegistActivity.this._pd.setMessage(RegistActivity.this
							.getResources().getString(
									R.string.on_loding_please_wait));
					RegistActivity.this._pd.show();

				}

			}
		});

		this._sex.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			public void onCheckedChanged(RadioGroup arg0, int arg1)
			{

				Resources rs = RegistActivity.this.getResources();

				switch (arg1)
				{
				case R.id.regist_Radionbtn_m:
					_user_sex = rs.getString(R.string.user_sex_m);
					Log.d("WCH", _user_sex);
					break;
				case R.id.regist_Radionbtn_w:
					_user_sex = rs.getString(R.string.user_sex_w);
					Log.d("WCH", _user_sex);
					break;
				default:
					break;
				}
			}
		});
	}

	public void init()
	{
		if (_pd == null)
		{

			this._pd = new ProgressDialog(this);
		}

	}

	public void refresh(int taskID, Object... paras)
	{

		Log.d("WCH", "Regist Activity ReFresh!");
		switch (taskID)
		{
		case TaskType.USER_REGIST:
			RegistActivity.this._pd.dismiss();

			Integer status = (Integer) paras[0];
			if (status.equals(Integer.valueOf(JsonStatus.REGIST_SUCCESS)))
			{
				RegistSuccessDialog RSD = new RegistSuccessDialog(this);
				RSD.setInfo(
						this.getResources().getString(
								R.string.regist_success_info), R.id.text_info);
				RSD.show();
			}
			else if (status.equals(Integer
					.valueOf(JsonStatus.REGIST_USER_EXIST)))
			{
				RegistAlertDialog UD = new RegistAlertDialog(
						RegistActivity.this);
				UD.setInfo(
						this.getResources().getString(
								R.string.regist_user_exist_info),
						R.id.text_info);
				UD.show();
			}
			else
			{

				RegistAlertDialog UD = new RegistAlertDialog(
						RegistActivity.this);
				UD.setInfo(
						this.getResources().getString(
								R.string.regist_exception_info), R.id.text_info);
				UD.show();
			}

			break;
		default:
			break;
		}
	}
}
