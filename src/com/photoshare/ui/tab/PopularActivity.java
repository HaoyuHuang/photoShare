package com.photoshare.ui.tab;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.photoshare.R;
import com.photoshare.IMGCache.IMGSimpleLoader;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.bean.json.JsonGetForumsBean;
import com.photoshare.bean.json.JsonGetPopularBean;
import com.photoshare.http.ServerHttpConfig;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.service.MainService;
import com.photoshare.ui.interfaces.BaseActivity;
import com.photoshare.viewgroup.ForumsViewGroup;
import com.photoshare.viewgroup.PopularViewGroup;

public class PopularActivity extends Activity implements BaseActivity,
		OnClickListener
{

	List<ImageView> _imgList = new ArrayList<ImageView>();

	List<JsonGetPopularBean> _infoList = null;

	Button _refrushbtn = null;

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
		this.setContentView(R.layout.popular_activity);

		_infoList = new ArrayList<JsonGetPopularBean>();

		this._refrushbtn = (Button) this.findViewById(R.id.popular_refrush);

		this._refrushbtn.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{

				getPopularImage();
				Toast.makeText(PopularActivity.this, "正在刷新，请稍等……",
						Toast.LENGTH_SHORT).show();
			}
		});

		initWidget();
		getPopularImage();

	}

	private void initWidget()
	{
		_imgList.add((ImageView) this.findViewById(R.id.popular_img01));
		_imgList.add((ImageView) this.findViewById(R.id.popular_img02));
		_imgList.add((ImageView) this.findViewById(R.id.popular_img03));
		_imgList.add((ImageView) this.findViewById(R.id.popular_img04));
		_imgList.add((ImageView) this.findViewById(R.id.popular_img05));
		_imgList.add((ImageView) this.findViewById(R.id.popular_img06));
		_imgList.add((ImageView) this.findViewById(R.id.popular_img07));
		_imgList.add((ImageView) this.findViewById(R.id.popular_img08));
		_imgList.add((ImageView) this.findViewById(R.id.popular_img09));
		_imgList.add((ImageView) this.findViewById(R.id.popular_img10));

		for (ImageView i : _imgList)
		{
			i.setOnClickListener(this);

		}

	}

	private void getPopularImage()
	{

		MainServiceTask MST = new MainServiceTask(TaskType.GET_POPULAE, null,
				PopularActivity.class.getName());

		MainService.addActivityMap(PopularActivity.class.getName(), this);
		MainService.addTaskQueue(MST);

	}

	public void init()
	{
		// TODO Auto-generated method stub

	}

	public void refresh(int taskID, Object... paras)
	{

		switch (taskID)
		{
		case TaskType.GET_POPULAE:

			int state = (Integer) paras[0];
			if (state == JsonStatus.GET_POPULAR_SUCCESS)
			{
				int size = (Integer) paras[1];
				JSONArray ja = (JSONArray) paras[2];

				ImageView tempimgview = null;
				JsonGetPopularBean bean;
				if (this._infoList == null)
				{
					this._infoList = new ArrayList<JsonGetPopularBean>();

				}
				else
				{
					this._infoList.clear();
				}
				for (int i = 0; i < size; i++)
				{

					try
					{
						tempimgview = this._imgList.get(i);

						bean = new JsonGetPopularBean(ja.getJSONObject(i));

						_infoList.add(bean);

						Log.d("WCH",
								"bean content--------->url" + bean.getPicURL());

						IMGSimpleLoader.showIMG(
								ServerHttpConfig.URL + bean.getPicURL(),
								tempimgview);
					}
					catch (JSONException e)
					{

						e.printStackTrace();
					}

				}

			}
			else
			{

				Toast.makeText(this, getString(R.string.get_popular_false),
						Toast.LENGTH_SHORT).show();

			}

			break;

		default:
			break;
		}

	}

	public void onClick(View widget)
	{

		JsonGetPopularBean bean = null;

		int position = 0;
		switch (widget.getId())
		{
		case R.id.popular_img01:
			position = 0;
			break;
		case R.id.popular_img02:
			position = 1;
			break;
		case R.id.popular_img03:
			position = 2;
			break;
		case R.id.popular_img04:
			position = 3;
			break;
		case R.id.popular_img05:
			position = 4;
			break;
		case R.id.popular_img06:
			position = 5;
			break;
		case R.id.popular_img07:
			position = 6;
			break;
		case R.id.popular_img08:
			position = 7;
			break;
		case R.id.popular_img09:
			position = 8;
			break;
		case R.id.popular_img10:
			position = 9;
			break;
		default:
			break;
		}

		if (position >= this._infoList.size())
		{
			Toast.makeText(this, "这边没有图片哦~请戳其他图片吧！^_^", Toast.LENGTH_SHORT)
					.show();
		}
		else
		{
			bean = this._infoList.get(position);

			Bundle bundle = new Bundle();

			Log.d("WCHH", "position------>" + position);

			bundle.putString(DETILE_USERNAME, bean.getUserName());
			bundle.putString(DETILE_TIME, bean.getTime());
			bundle.putString(DETILE_CONTENT, bean.getText());
			bundle.putString(DETILE_IMG_URL, bean.getPicURL());
			bundle.putString(DETILE_SUPPORT_NUMB, bean.getPandC() + "");
			bundle.putString(DETILE_REMARK_NUMB, bean.getComment() + "");
			bundle.putString(DETILE_PICID, bean.getPicID() + "");

			Intent intent = new Intent(PopularActivity.this,
					PopularDetailActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtras(bundle);
			Window w = PopularViewGroup._manager.startActivity(
					PopularDetailActivity.class.getName(), intent);
			View view = w.getDecorView();
			PopularViewGroup._PopularGroup.setContentView(view);

		}

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		MainService.removeActivity(PopularActivity.class.getName());

	}

}
