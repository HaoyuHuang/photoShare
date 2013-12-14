package com.photoshare.ui.tab;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.photoshare.R;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.bean.json.JsonGetFriendsBean;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.listview.adapter.FriendsListViewAdapter;
import com.photoshare.service.MainService;
import com.photoshare.ui.interfaces.BaseActivity;
import com.photoshare.viewgroup.FriendsViewGroup;

public class FriendsActivity extends Activity implements BaseActivity
{

	FriendsListViewAdapter _adapter = null;
	ListView _listview = null;
	List<JsonGetFriendsBean> _infolist;

	Button _findFriends = null;
	Button _refrushFriends = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		Log.d("WCHHHH", "--------------->ocreate");

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.friends_activity);

		_listview = (ListView) this.findViewById(R.id.friend_listview);

		getFriends();
		this._findFriends = (Button) this.findViewById(R.id.friend_find);
		this._refrushFriends = (Button) this.findViewById(R.id.friend_refrush);
		this._refrushFriends.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{

				Log.d("WCHH", "------------refrush-----------friends");
				getFriends();

			}
		});
		this._findFriends.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{

				Intent intent = new Intent(FriendsActivity.this,
						FriendsFindActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Window w = FriendsViewGroup._manager.startActivity(
						FriendsActivity.class.getName(), intent);
				View view = w.getDecorView();
				FriendsViewGroup._frindsGroup.setContentView(view);

			}
		});

	}

	public void init()
	{

	}

	private void getFriends()
	{

		MainServiceTask MST = new MainServiceTask(TaskType.GET_FRIEND, null,
				FriendsActivity.class.getName());
		MainService.addActivityMap(FriendsActivity.class.getName(), this);
		MainService.addTaskQueue(MST);

	}

	public void refresh(int taskID, Object... paras)
	{

		switch (taskID)
		{
		case TaskType.GET_FRIEND:
		{

			int state = (Integer) paras[0];
			if (state == JsonStatus.GET_FRIENDS_SUCCESS)
			{

				int size = (Integer) paras[1];
				JSONArray ja = (JSONArray) paras[2];

				if (this._infolist == null)
				{
					this._infolist = new ArrayList<JsonGetFriendsBean>();
				}
				else
				{
					this._infolist.clear();
				}

				this._adapter = new FriendsListViewAdapter(this, this._infolist);

				try
				{
					for (int i = 0; i < size; i++)
					{

						this._infolist.add(new JsonGetFriendsBean(ja
								.getJSONObject(i)));
						Log.d("WCHH",
								"-----------------------------set adaptor--->");
					}
					Log.d("WCHH", "-----------------------------set adaptor");
					this._listview = (ListView) this
							.findViewById(R.id.friend_listview);
					this._listview.setAdapter(this._adapter);

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
			else
			{
				Toast.makeText(this, getString(R.string.get_friend_false),
						Toast.LENGTH_SHORT).show();
			}

			break;
		}

		default:
			break;
		}

	}

	@Override
	protected void onDestroy()
	{
		Log.d("WCHH", "FriendsActivity-----------destroy");
		MainService.removeActivity(FriendsActivity.class.getName());
		super.onDestroy();
	}

}
