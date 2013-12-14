package com.photoshare.ui.tab;

import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.photoshare.R;
import com.photoshare.bean.LogUserBean;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.service.MainService;
import com.photoshare.ui.interfaces.BaseActivity;

public class ProfileActivity extends Activity implements BaseActivity
{
	private EditText _account;
	private EditText _password;
	private EditText _password_sure;
	private EditText _name;
	private EditText _nickname;
	private EditText _email;
	private RadioGroup _sex;
	private RadioButton _Radionbtn_m;
	private RadioButton _Radionbtn_w;
	private Button _refrush;
	private Button _submit;
	private String sex;

	public static String UPDATE_DATA = "UPDATE_DATA";

	public class UpdateUserInfoBean
	{

		String name;
		String password;
		String nickname;
		String email;
		String sex;

		public UpdateUserInfoBean(String name, String password,
				String nickname, String email, String sex)
		{

			this.name = name;
			this.password = password;
			this.nickname = nickname;
			this.email = email;
			this.sex = sex;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getPassword()
		{
			return password;
		}

		public void setPassword(String password)
		{
			this.password = password;
		}

		public String getNickname()
		{
			return nickname;
		}

		public void setNickname(String nickname)
		{
			this.nickname = nickname;
		}

		public String getEmail()
		{
			return email;
		}

		public void setEmail(String email)
		{
			this.email = email;
		}

		public String getSex()
		{
			return sex;
		}

		public void setSex(String sex)
		{
			this.sex = sex;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);
		initwidget();

		_refrush = (Button) this.findViewById(R.id.profile_refrush);
		_submit = (Button) this.findViewById(R.id.profile_submit);

		_refrush.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{
				MainServiceTask MST = new MainServiceTask(
						TaskType.GET_USER_INFO, null, ProfileActivity.class
								.getName());
				MainService.addActivityMap(ProfileActivity.class.getName(),
						ProfileActivity.this);
				MainService.addTaskQueue(MST);

			}
		});
		_submit.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				String name = ProfileActivity.this._name.getText().toString();
				String password = ProfileActivity.this._password.getText()
						.toString();
				String password_sure = ProfileActivity.this._password_sure
						.getText().toString();
				String nickname = ProfileActivity.this._nickname.getText()
						.toString();
				String email = ProfileActivity.this._email.getText().toString();
				if (password.equals("") == true)
				{

					Toast.makeText(
							ProfileActivity.this,
							ProfileActivity.this
									.getString(R.string.regist_null_password),
							Toast.LENGTH_SHORT).show();
				}
				else if (password_sure.equals("") == true)
				{

					Toast.makeText(
							ProfileActivity.this,
							ProfileActivity.this
									.getString(R.string.regist_null_password_sure),
							Toast.LENGTH_SHORT).show();
				}
				else if (name.equals("") == true)
				{
					Toast.makeText(
							ProfileActivity.this,
							ProfileActivity.this
									.getString(R.string.regist_null_name),
							Toast.LENGTH_SHORT).show();

				}
				else if (nickname.equals("") == true)
				{
					Toast.makeText(
							ProfileActivity.this,
							ProfileActivity.this
									.getString(R.string.regist_null_nickname),
							Toast.LENGTH_SHORT).show();
				}
				else if (email.equals("") == true)
				{
					Toast.makeText(
							ProfileActivity.this,
							ProfileActivity.this
									.getString(R.string.regist_null_email),
							Toast.LENGTH_SHORT).show();
				}
				else
				{

					Map<String, Object> paras = new HashMap<String, Object>();

					paras.put(ProfileActivity.UPDATE_DATA,
							new UpdateUserInfoBean(ProfileActivity.this._name
									.getText().toString(),
									ProfileActivity.this._password.getText()
											.toString(),
									ProfileActivity.this._nickname.getText()
											.toString(),
									ProfileActivity.this._email.getText()
											.toString(),
									ProfileActivity.this.sex));

					MainServiceTask MST = new MainServiceTask(
							TaskType.UPDATE_USER_INFO, paras,
							ProfileActivity.class.getName());
					MainService.addActivityMap(ProfileActivity.class.getName(),
							ProfileActivity.this);
					MainService.addTaskQueue(MST);

				}
			}
		});

	}

	private void initwidget()
	{
		_account = (EditText) this.findViewById(R.id.profile_edtv_username);
		_email = (EditText) this.findViewById(R.id.profile_edtv_email);
		_name = (EditText) this.findViewById(R.id.profile_edtv_name);
		_nickname = (EditText) this.findViewById(R.id.profile_edtv_nickname);
		_password = (EditText) this.findViewById(R.id.profile_edtv_password);
		_password_sure = (EditText) this
				.findViewById(R.id.profile_edtv_password_sure);
		_sex = (RadioGroup) this.findViewById(R.id.profile_radiogroup_sex);
		_Radionbtn_m = (RadioButton) this
				.findViewById(R.id.profile_Radionbtn_m);
		_Radionbtn_w = (RadioButton) this
				.findViewById(R.id.profile_Radionbtn_w);
		setInfo();
		_sex.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			public void onCheckedChanged(RadioGroup group, int checkedId)
			{

				Resources rs = ProfileActivity.this.getResources();

				switch (checkedId)
				{
				case R.id.profile_Radionbtn_m:
					sex = rs.getString(R.string.user_sex_m);
					Log.d("WCH", sex);
					break;
				case R.id.profile_Radionbtn_w:
					sex = rs.getString(R.string.user_sex_w);
					Log.d("WCH", sex);
					break;
				default:
					break;
				}
			}
		});
	}

	private void setInfo()
	{
		_account.setText(LogUserBean.LOG_USER_ACCOUNT);
		_email.setText(LogUserBean.LOG_USER_EMAIL);
		_name.setText(LogUserBean.LOG_USER_NAME);
		_nickname.setText(LogUserBean.LOG_USER_NICKNAME);
		_password.setText(LogUserBean.LOG_USER_PASSWORD);
		_password_sure.setText(LogUserBean.LOG_USER_PASSWORD);
		if (LogUserBean.LOG_USER_SEX.equals("男") == true)
		{

			this.sex = "男";
			_Radionbtn_m.setChecked(true);

		}
		else
		{
			this.sex = "女";
			_Radionbtn_w.setChecked(true);
		}
	}

	public void init()
	{

	}

	public void refresh(int taskID, Object... paras)
	{

		switch (taskID)
		{
		case TaskType.GET_USER_INFO:
		{
			int state = (Integer) paras[0];

			if (state == JsonStatus.GET_USER_INFO_SUCCESS)
			{

				LogUserBean.LOG_USER_PASSWORD = (String) paras[2];
				LogUserBean.LOG_USER_NAME = (String) paras[1];
				LogUserBean.LOG_USER_SEX = (String) paras[5];
				LogUserBean.LOG_USER_EMAIL = (String) paras[3];
				LogUserBean.LOG_USER_NICKNAME = (String) paras[4];
				setInfo();
				Toast.makeText(this,
						getString(R.string.profile_refursh_userinfo_success),
						Toast.LENGTH_SHORT).show();
			}
			else
			{

				Toast.makeText(this,
						getString(R.string.profile_refursh_userinfo_false),
						Toast.LENGTH_SHORT).show();
			}

			break;
		}
		case TaskType.UPDATE_USER_INFO:
		{

			int state = (Integer) paras[0];
			if (state == JsonStatus.UPDATE_USER_INFO_SUCCESS)
			{
				Toast.makeText(this,
						getString(R.string.profile_update_userinfo_success),
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(this,
						getString(R.string.profile_update_userinfo_false),
						Toast.LENGTH_SHORT);
			}

			break;
		}

		default:
			break;
		}

	}
}
