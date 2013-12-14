package com.photoshare.ui.tab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.photoshare.R;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.bean.json.JsonFindFriendsBean;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.listview.adapter.FindFriendsViewAdapter;
import com.photoshare.service.MainService;
import com.photoshare.ui.interfaces.BaseActivity;
import com.photoshare.viewgroup.FriendsViewGroup;

public class FriendsFindActivity extends Activity implements BaseActivity
{

	Button _startFindFriend;
	EditText _text;

	public static String FIND_NAME = "FIND_NAME";

	List<JsonFindFriendsBean> _list = new ArrayList<JsonFindFriendsBean>();

	private ListView listview = null;

	private Button _back_btn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.friends_find);
		this._startFindFriend = (Button) this
				.findViewById(R.id.start_find_friends);
		this._text = (EditText) this.findViewById(R.id.find_friend_text);

		this.listview = (ListView) this.findViewById(R.id.find_friends_result);

		_back_btn = (Button) this.findViewById(R.id.find_friend_back);

		_back_btn.setOnClickListener(new OnClickListener()
		{

			@SuppressWarnings("deprecation")
			public void onClick(View arg0)
			{

				Intent intent = new Intent(FriendsFindActivity.this,
						FriendsActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// 把一个Activity转换成一个View
				Window w = FriendsViewGroup._manager.startActivity(
						FriendsActivity.class.getName(), intent);
				View view = w.getDecorView();
				// 把View添加大ActivityGroup中
				FriendsViewGroup._frindsGroup.setContentView(view);

			}
		});

		this._startFindFriend.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{

				Map<String, Object> paras = new HashMap<String, Object>();

				paras.put(FIND_NAME, FriendsFindActivity.this._text.getText()
						.toString());

				MainServiceTask MST = new MainServiceTask(TaskType.FIND_FRIEND,
						paras, FriendsFindActivity.class.getName());

				MainService.addActivityMap(FriendsFindActivity.class.getName(),
						FriendsFindActivity.this);
				MainService.addTaskQueue(MST);

			}
		});

	}

	public void init()
	{

	}

	public void refresh(int taskID, Object... paras)
	{

		Log.d("WCHH", "find-------------refrsh=========");
		switch (taskID)
		{
		case TaskType.FIND_FRIEND:
		{

			if (this._list != null)
			{
				this._list.clear();
			}
			else
			{
				this._list = new ArrayList<JsonFindFriendsBean>();
			}

			int state = (Integer) paras[0];
			if (state == JsonStatus.FIND_FRIENDS_SUCCESS)
			{

				JSONArray ja = (JSONArray) paras[2];
				int size = (Integer) paras[1];

				try
				{
					for (int i = 0; i < size; i++)
					{
						this._list.add(new JsonFindFriendsBean(ja
								.getJSONObject(i)));

						Log.d("WCHH",
								"find-----------------setadapter finsh----->>>>>>"
										+ size);
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}

				this.listview.setAdapter(new FindFriendsViewAdapter(this,
						this._list));

				Log.d("WCHH", "find-----------------setadapter finsh");

			}
			else
			{
				Toast.makeText(this, getString(R.string.find_friends_false),
						Toast.LENGTH_SHORT).show();
			}

			break;
		}

		case TaskType.ADD_FRIENDS:
		{

			int state = (Integer) paras[0];

			switch (state)
			{
			case JsonStatus.ADD_FRIENDS_EXIST:
				Toast.makeText(this, getString(R.string.add_friends_exist),
						Toast.LENGTH_SHORT).show();

				break;

			case JsonStatus.ADD_FRIENDS_FALSE:
				Toast.makeText(this, getString(R.string.add_friends_false),
						Toast.LENGTH_SHORT).show();

				break;
			case JsonStatus.ADD_FRIENDS_SUCCESS:
				Toast.makeText(this, getString(R.string.add_friends_success),
						Toast.LENGTH_SHORT).show();

				break;
			default:
				break;
			}

		}
		default:
			break;
		}

	}
	@Override
	protected void onDestroy()
	{

		Log.d("WCHH", "findactivity--------------->destroy");
		MainService.removeActivity(FriendsFindActivity.class.getName());
		super.onDestroy();
	}

}
