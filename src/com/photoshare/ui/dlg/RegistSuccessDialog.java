package com.photoshare.ui.dlg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.photoshare.R;
import com.photoshare.ui.LogInActivity;

public class RegistSuccessDialog extends Dialog
{

	private TextView info;
	private View v;
	private Button confirmbutton;
	private Context context;

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

						RegistSuccessDialog.this.dismiss();

						context.startActivity(new Intent(context,
								LogInActivity.class));

					}
				});
	}

	public RegistSuccessDialog(Context context)
	{

		super(context, R.style.dialog);
		this.context = context;
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

}
