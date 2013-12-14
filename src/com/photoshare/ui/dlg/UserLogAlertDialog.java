package com.photoshare.ui.dlg;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.photoshare.R;

public class UserLogAlertDialog extends Dialog
{

	private TextView info;
	private View v;
	private Button confirmbutton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		confirmbutton = (Button) v
				.findViewById(R.id.regist_alert_dlg_btn_confirm);
		this.confirmbutton
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View arg0)
					{

						UserLogAlertDialog.this.dismiss();

					}
				});
	}

	public UserLogAlertDialog(Context context)
	{

		super(context, R.style.dialog);
		v = View.inflate(context, R.layout.alert_dialog, null);
		this.setContentView(v);

	}

	@Override
	public void show()
	{

		super.show();
	}

	public void setInfo(String info, int textViewID)
	{
		((TextView) this.v.findViewById(textViewID)).setText(info);
	}

	public void setInfo(String info)
	{
		((TextView) this.v.findViewById(R.id.text_info)).setText(info);
	}

}
