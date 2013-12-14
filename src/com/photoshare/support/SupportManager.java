package com.photoshare.support;

import java.util.HashMap;
import java.util.Map;

import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.bean.json.JsonGetForumsBean;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.service.MainService;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class SupportManager
{

	public static Context context;
	
	
	
	
	
	public static String SUPPORT_PIC_ID = "SUPPORT_PIC_ID";

	public static String SUPPORT_PIC_CALLBACK = "SUPPORT_PIC_CALLBACK";

	private static void postSupport(int picid, SupportCallback callback)
	{
		Log.d("WCH", "------------------->do support post!3");
		Map<String, Object> t = new HashMap<String, Object>();
		t.put(SUPPORT_PIC_ID, picid);
		t.put(SUPPORT_PIC_CALLBACK, callback);
		MainServiceTask MST = new MainServiceTask(TaskType.SUPPORT, t, null);

		MainService.addTaskQueue(MST);
		Log.d("WCH", "------------------->do support post!4");

	}

	public static void execSupport(int picid, final TextView tView,final JsonGetForumsBean forumsBean)
	{
		Log.d("WCH", "------------------->do support post!2");
		postSupport(picid, new SupportCallback()
		{

			public void refrush(int picid, int state)
			{
				
				
				
				Log.d("WCH", "------------------->do support post!9");
				switch (state)
				
				
				
				{
				case JsonStatus.SUPPORT_SUCCESS:

					String str = tView.getText().toString().substring(1);

					Log.d("WCH", "------------------->do support post!10"+(Integer.parseInt(str) + 1));
					forumsBean.setPandC((forumsBean.getPandC()+1));
					tView.setText("赞"+(Integer.parseInt(str) + 1));

					
					Toast.makeText(context, "赞成功！", Toast.LENGTH_SHORT).show();
					
					break;

				case JsonStatus.SUPPORT_EXIST:

					Toast.makeText(context, "您已经赞过了~", Toast.LENGTH_SHORT).show();
					
					
					break;
				case JsonStatus.SUPPORT_EXCEPTION:

					Toast.makeText(context, "赞失败！", Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
				}

			}
		});

		Log.d("WCH", "------------------->do support post! finsh");
	}

}
