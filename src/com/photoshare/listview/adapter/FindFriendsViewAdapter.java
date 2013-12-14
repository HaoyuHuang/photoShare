package com.photoshare.listview.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.raw;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.R;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.bean.json.JsonFindFriendsBean;
import com.photoshare.service.MainService;
import com.photoshare.ui.tab.FriendsFindActivity;

public class FindFriendsViewAdapter extends BaseAdapter
{

	private List<JsonFindFriendsBean> _list;
	private Context _context;
	private LayoutInflater _LayoutInflater;

	public static String ADD_FRIENDS_ID = "ADD_FRIENDS_ID";

	public FindFriendsViewAdapter(Context context,
			List<JsonFindFriendsBean> list)
	{
		this._list = list;
		this._context = context;
		this._LayoutInflater = LayoutInflater.from(this._context);

	}

	public int getCount()
	{
		// TODO Auto-generated method stub
		return null != this._list ? this._list.size() : 0;
	}

	public Object getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return null != this._list ? this._list.get(arg0) : null;
	}

	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(final int position, View view, ViewGroup arg2)
	{
		View newContentView = view;

		if (null == newContentView)
		{

			newContentView = this._LayoutInflater.inflate(
					R.layout.find_friend_result_listview, null);

		}

		template t = new template();

		JsonFindFriendsBean bean = _list.get(position);

		t.add = (Button) newContentView.findViewById(R.id.find_friend_add);
		t.email = (TextView) newContentView
				.findViewById(R.id.find_friend_email);
		t.img = (ImageView) newContentView.findViewById(R.id.find_friend_img);
		t.name = (TextView) newContentView.findViewById(R.id.find_friend_name);

		t.add.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{

				Map<String, Object> paras = new HashMap<String, Object>();

				paras.put(ADD_FRIENDS_ID, FindFriendsViewAdapter.this._list
						.get(position).getUserID());

				MainServiceTask MST = new MainServiceTask(TaskType.ADD_FRIENDS,
						paras, FriendsFindActivity.class.getName());
				MainService.addTaskQueue(MST);

			}
		});
		t.name.setText(bean.getUserName());
		t.email.setText(bean.getEmail());
		if (bean.getSex().equals(this._context.getString(R.string.user_sex_m)))
		{
			t.img.setImageDrawable(this._context.getResources().getDrawable(
					R.drawable.man));
		}
		else
		{
			t.img.setImageDrawable(this._context.getResources().getDrawable(
					R.drawable.woman));
		}

		return newContentView;
	}

	public static class template
	{

		ImageView img;
		TextView name;
		TextView email;
		Button add;

	}

}
