package com.photoshare.ui.tab;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.photoshare.R;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.bean.json.JsonGetForumsBean;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.listview.adapter.ForumsListViewAdapter;
import com.photoshare.service.MainService;
import com.photoshare.ui.interfaces.BaseActivity;
import com.photoshare.viewgroup.ForumsViewGroup;
import com.photoshare.viewgroup.FriendsViewGroup;
import com.photoshare.widget.ForumsListView;
import com.photoshare.widget.interfaces.onForumsListViewRefreshListener;

public class ForumsActivity extends Activity implements BaseActivity
{

	public static int NEXT_PAGE = 0;

	public static int PAGE = 6;

	ForumsListView _listview = null;

	private List<JsonGetForumsBean> _forumsList = new ArrayList<JsonGetForumsBean>();

	private View _moreView = null;

	private TextView _text_more = null;

	private View _waitimg = null;

	private ForumsListViewAdapter _adapter;

	public static String DETILE_USERNAME = "DETILE_USERNAME";
	public static String DETILE_TIME = "DETILE_TIME";
	public static String DETILE_CONTENT = "DETILE_CONTENT";
	public static String DETILE_IMG_URL = "DETILE_IMG_URL";
	public static String DETILE_SUPPORT_NUMB = "DETILE_SUPPORT_NUMB";
	public static String DETILE_REMARK_NUMB = "DETILE_REMARK_NUMB";
	public static String DETILE_PICID = "DETILE_PICID";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.forums_activity);
		this._listview = (ForumsListView) this
				.findViewById(R.id.forums_listview);
		_moreView = LayoutInflater.from(this).inflate(R.layout.listview_more,
				null);
		_text_more = (TextView) _moreView.findViewById(R.id.text_more);
		_waitimg = _moreView.findViewById(R.id.more_wait_view);

		getForums();// 请求第一页
		init();
		_text_more.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{

				switchfoot(true);// 表示正在加载
				getForums();

			}
		});
		_listview.setOnRefreshListener(new onForumsListViewRefreshListener()
		{

			public void onRefresh()
			{
				refrushForums();
			}
		});

		_listview.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id)
			{

				Bundle bundle = new Bundle();

				Log.d("WCHH", "position------>" + position);

				JsonGetForumsBean bean = _forumsList.get(position - 1);

				bundle.putString(DETILE_USERNAME, bean.getUserName());
				bundle.putString(DETILE_TIME, bean.getTime());
				bundle.putString(DETILE_CONTENT, bean.getText());
				bundle.putString(DETILE_IMG_URL, bean.getPicURL());
				bundle.putString(DETILE_SUPPORT_NUMB, bean.getPandC() + "");
				bundle.putString(DETILE_REMARK_NUMB, bean.getComment() + "");
				bundle.putString(DETILE_PICID, bean.getPicID() + "");

				Intent intent = new Intent(ForumsActivity.this,
						ForumsDetailActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(bundle);
				Window w = ForumsViewGroup._manager.startActivity(
						ForumsDetailActivity.class.getName(), intent);
				View view = w.getDecorView();
				ForumsViewGroup._FroumsGroup.setContentView(view);

			}
		});
	}

	public void init()
	{

		this._listview.addFooterView(_moreView);

	}

	/**
	 * 
	 * list foot 的转换函数，当设置为true时候，表示正在加载 当为false的时候为 更多
	 * 
	 * 
	 * 
	 * @param state
	 */

	private void switchfoot(boolean state)
	{
		this._waitimg.setVisibility(state ? View.VISIBLE : View.GONE);
		this._text_more.setVisibility(state ? View.GONE : View.VISIBLE);

	}

	private void getForums()
	{
		MainServiceTask MST = new MainServiceTask(TaskType.GET_FORUMS, null,
				ForumsActivity.class.getName());

		MainService.addActivityMap(ForumsActivity.class.getName(), this);
		MainService.addTaskQueue(MST);
	}

	private void refrushForums()
	{

		ForumsActivity.NEXT_PAGE = 0;

		MainServiceTask MST = new MainServiceTask(TaskType.REFEUSH_FOEUMS,
				null, ForumsActivity.class.getName());

		MainService.addActivityMap(ForumsActivity.class.getName(), this);
		MainService.addTaskQueue(MST);
	}

	public void refresh(int taskID, Object... paras)
	{

		switch (taskID)
		{
		case TaskType.GET_FORUMS:
		{

			try
			{

				int state = (Integer) paras[0];

				if (state == JsonStatus.GETFORUMS_SUCCESS)
				{

					int size = (Integer) paras[1];

					if (size > 0)
					{

						JSONArray ja = (JSONArray) paras[2];

						int length = ja.length();

						Log.d("WCH", "json array length------->" + length);

						for (int i = 0; i < length; i++)
						{
							this._forumsList.add(new JsonGetForumsBean(ja
									.getJSONObject(i)));
						}

						if (this._adapter == null)
						{
							this._adapter = new ForumsListViewAdapter(this,
									this._forumsList);
							this._listview.setAdapter(_adapter);
						}
						else
						{
							this._adapter.notifyDataSetChanged();
							this._listview.setSelection(this._adapter
									.getCount() - PAGE);
							switchfoot(false);
						}

						ForumsActivity.NEXT_PAGE++;
					}
					else
					{
						Toast.makeText(this, getString(R.string.no_more),
								Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(this,
							this.getString(R.string.get_forums_false),
							Toast.LENGTH_SHORT).show();
				}
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		}
		case TaskType.REFEUSH_FOEUMS:
		{

			try
			{

				int state = (Integer) paras[0];

				if (state == JsonStatus.GETFORUMS_SUCCESS)
				{

					int size = (Integer) paras[1];

					if (size > 0)
					{

						JSONArray ja = (JSONArray) paras[2];

						int length = ja.length();

						Log.d("WCH", "json array length------->" + length);

						this._forumsList.clear();
						for (int i = 0; i < length; i++)
						{
							this._forumsList.add(new JsonGetForumsBean(ja
									.getJSONObject(i)));
						}

						if (this._adapter == null)
						{
							this._adapter = new ForumsListViewAdapter(this,
									this._forumsList);
							this._listview.setAdapter(_adapter);
						}
						else
						{
							this._adapter.notifyDataSetChanged();
							this._listview.onRefreshComplete();

						}

						ForumsActivity.NEXT_PAGE++;
					}
					else
					{
						Toast.makeText(this,
								getString(R.string.refrush_exception),
								Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(this,
							this.getString(R.string.refrush_exception),
							Toast.LENGTH_SHORT).show();
				}
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		MainService.removeActivity(ForumsActivity.class.getName());
		NEXT_PAGE = 0;
		super.onDestroy();
	}

}
