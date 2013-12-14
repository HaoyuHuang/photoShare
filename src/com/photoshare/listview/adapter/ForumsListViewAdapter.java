package com.photoshare.listview.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.R;
import com.photoshare.IMGCache.IMGSimpleLoader;
import com.photoshare.bean.json.JsonGetForumsBean;
import com.photoshare.http.ServerHttpConfig;
import com.photoshare.support.SupportManager;

public class ForumsListViewAdapter extends BaseAdapter
{

	private Context _content;
	private List<JsonGetForumsBean> _forumslist;

	public ForumsListViewAdapter(Context context,
			List<JsonGetForumsBean> forumslist)
	{

		this._content = context;
		this._forumslist = forumslist;
	}

	public int getCount()
	{
		// TODO Auto-generated method stub
		if (_forumslist != null)
		{
			return _forumslist.size();
		}
		else
		{
			return 0;
		}

	}

	public Object getItem(int arg0)
	{
		return null != _forumslist ? _forumslist.get(arg0) : null;
	}

	public long getItemId(int arg0)
	{
		return 0;
	}

	public View getView(final int position, View view, ViewGroup parent)
	{

		Log.d("WCH", "list length----->" + _forumslist.size());

		View contentView = view;

		final JsonGetForumsBean forumsBean = _forumslist.get(position);
		final ForumsTempLate FT = new ForumsTempLate();
		if (view == null)
		{

			contentView = LayoutInflater.from(this._content).inflate(
					R.layout.forums_listview, null);

		}

		FT._comment = (TextView) contentView
				.findViewById(R.id.forums_list_comment);
		FT._img = (ImageView) contentView.findViewById(R.id.forums_list_img);
		FT._support = (TextView) contentView
				.findViewById(R.id.forums_list_support);
		FT._text = (TextView) contentView
				.findViewById(R.id.forums_list_content);
		FT._time = (TextView) contentView.findViewById(R.id.forums_list_time);
		FT._username = (TextView) contentView
				.findViewById(R.id.forums_list_username);

		Log.d("WCH", ServerHttpConfig.URL + forumsBean.getPicURL());

		IMGSimpleLoader.showIMG(ServerHttpConfig.URL + forumsBean.getPicURL(),
				FT._img);

		Log.d("WCH", forumsBean.getTime());
		FT._comment.setText(_content.getString(R.string.forums_list_comment)
				+ forumsBean.getComment());
		FT._support.setText(_content.getString(R.string.forums_list_support)
				+ forumsBean.getPandC());
		FT._text.setText(forumsBean.getText());
		FT._time.setText(forumsBean.getTime());
		FT._username.setText(forumsBean.getUserName());

		final Animation animation = new AlphaAnimation(1.0f, 0.1f);
		animation.setDuration(1500);

		animation.setAnimationListener(new AnimationListener()
		{

			public void onAnimationStart(Animation animation)
			{
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation animation)
			{
				// TODO Auto-generated method stub

			}

			public void onAnimationEnd(Animation animation)
			{
				// TODO Auto-generated method stub

				Log.d("WCH", "------------------->do support post!1");

				SupportManager.execSupport(
						ForumsListViewAdapter.this._forumslist.get(position)
								.getPicID(), FT._support,forumsBean);

			}
		});

		

		FT._support.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{

				FT._support.startAnimation(animation);

			}
		});

		return contentView;

	}

	public static class ForumsTempLate
	{
		public TextView _username;
		public TextView _time;
		public TextView _text;
		public ImageView _img;
		public TextView _support;
		public TextView _comment;

	}

	public void addForums()
	{

	}

}
