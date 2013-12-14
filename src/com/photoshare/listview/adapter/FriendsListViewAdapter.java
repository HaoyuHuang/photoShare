package com.photoshare.listview.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.R;
import com.photoshare.bean.json.JsonGetFriendsBean;

public class FriendsListViewAdapter extends BaseAdapter
{

	private Context context;
	private List<JsonGetFriendsBean> infolist;
	private LayoutInflater inflater;

	public FriendsListViewAdapter(Context context,
			List<JsonGetFriendsBean> _infolist)
	{

		this.context = context;
		this.infolist = _infolist;
		inflater = LayoutInflater.from(context);
	}

	public int getCount()
	{
		
		int count=(null != this.infolist ? this.infolist.size() : 0);
		
		return count;
	}

	public Object getItem(int arg0)
	{

		return null != this.infolist ? this.infolist.get(arg0) : null;
	}

	public long getItemId(int arg0)
	{
		return 0;
	}

	public View getView(int position, View veiw, ViewGroup arg2)
	{

		
		Log.d("WCHH", "-----------------------------set adaptor__NULLLLLLLLL");
		
		
		View newView = veiw;
		template t = new template();
		if (newView == null)
		{

			newView = this.inflater.inflate(R.layout.friends_listview, null);

		}

		t.email = (TextView) newView.findViewById(R.id.friend_email);
		t.img = (ImageView) newView.findViewById(R.id.friend_img);
		t.name = (TextView) newView.findViewById(R.id.friend_name);

		JsonGetFriendsBean bean = this.infolist.get(position);

		t.email.setText(bean.getEmail());
		String sex = bean.getSex();
		if (sex.equals(this.context.getString(R.string.user_sex_m)) == true)
		{
			t.img.setImageDrawable(this.context.getResources().getDrawable(
					R.drawable.man));
		}
		else
		{
			t.img.setImageDrawable(this.context.getResources().getDrawable(
					R.drawable.woman));
		}
		t.name.setText(bean.getUserName());
		return newView;

	}

	public static class template
	{

		ImageView img;
		TextView name;
		TextView email;

	}

}
