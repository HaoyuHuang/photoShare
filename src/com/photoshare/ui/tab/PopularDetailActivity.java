package com.photoshare.ui.tab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.photoshare.R;
import com.photoshare.IMGCache.IMGSimpleLoader;
import com.photoshare.bean.LogUserBean;
import com.photoshare.bean.MainServiceTask;
import com.photoshare.bean.TaskType;
import com.photoshare.bean.json.JsonGetCommentsBean;
import com.photoshare.http.ServerHttpConfig;
import com.photoshare.jsonstatus.JsonStatus;
import com.photoshare.service.MainService;
import com.photoshare.ui.interfaces.BaseActivity;
import com.photoshare.viewgroup.ForumsViewGroup;
import com.photoshare.viewgroup.PopularViewGroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PopularDetailActivity extends Activity implements BaseActivity
{
	public static String PIC_ID = "PIC_ID";
	public static String COMMENT_CONTENT = "COMMENT_CONTENT";

	private TextView name;

	private TextView time;
	private TextView content;
	private TextView support;
	private TextView remarknumb;
	private TextView remarkcontent;
	private EditText remark_submit_content;
	private Button submint;
	private Button back;
	private ImageView Img;

	private String picid;

	private List<JsonGetCommentsBean> _list = new ArrayList<JsonGetCommentsBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.forums_detail);
		initWidgit();
	}

	private void initWidgit()
	{

		name = (TextView) this.findViewById(R.id.forums_detali_username);
		time = (TextView) this.findViewById(R.id.forums_detail_time);
		content = (TextView) this.findViewById(R.id.forums_detail_content);
		support = (TextView) this.findViewById(R.id.forums_detail_support);
		remarknumb = (TextView) this.findViewById(R.id.forums_detail_comment);
		remarkcontent = (TextView) this.findViewById(R.id.forums_detail_remark);
		remark_submit_content = (EditText) this
				.findViewById(R.id.forums_detail_sent_remark_text);
		submint = (Button) this.findViewById(R.id.forums_detail_sent_submit);
		back = (Button) this.findViewById(R.id.forums_detail_backpage);
		Img = (ImageView) this.findViewById(R.id.forums_detail_img);

		Bundle bundle = this.getIntent().getExtras();

		time.setText(bundle.getString(ForumsActivity.DETILE_TIME));
		name.setText(bundle.getString(ForumsActivity.DETILE_USERNAME));
		content.setText(bundle.getString(ForumsActivity.DETILE_CONTENT));
		IMGSimpleLoader.showIMG(
				ServerHttpConfig.URL
						+ bundle.getString(ForumsActivity.DETILE_IMG_URL), Img);
		support.setText("赞"
				+ bundle.getString(ForumsActivity.DETILE_SUPPORT_NUMB));
		remarknumb.setText("评论"
				+ bundle.getString(ForumsActivity.DETILE_REMARK_NUMB));

		picid = bundle.getString(ForumsActivity.DETILE_PICID);

		getPicRemark(picid);

		back.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{

				Intent intent = new Intent(PopularDetailActivity.this,
						PopularActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				Window w = PopularViewGroup._manager.startActivity(
						PopularActivity.class.getName(), intent);
				View view = w.getDecorView();
				PopularViewGroup._PopularGroup.setContentView(view);

			}
		});

		submint.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				if (remark_submit_content.getText().toString().equals(""))
				{

					Toast.makeText(PopularDetailActivity.this, "评论不能为空！",
							Toast.LENGTH_SHORT).show();
				}
				else
				{

					Map<String, Object> paras = new HashMap<String, Object>();
					paras.put(ForumsDetailActivity.PIC_ID, picid);// 取得id
					paras.put(ForumsDetailActivity.COMMENT_CONTENT,
							remark_submit_content.getText().toString());
					MainServiceTask MST = new MainServiceTask(
							TaskType.INSERT_COMMENTS, paras,
							PopularDetailActivity.class.getName());
					MainService.addActivityMap(
							PopularDetailActivity.class.getName(),
							PopularDetailActivity.this);
					MainService.addTaskQueue(MST);

				}
			}
		});

	}

	private void getPicRemark(String picid)
	{

		Map<String, Object> paras = new HashMap<String, Object>();

		paras.put(ForumsDetailActivity.PIC_ID, picid);

		MainServiceTask MST = new MainServiceTask(TaskType.GET_COMMENTS, paras,
				PopularDetailActivity.class.getName());
		MainService.addActivityMap(PopularDetailActivity.class.getName(), this);
		MainService.addTaskQueue(MST);

	}

	private void setComment()
	{

		StringBuilder sb = new StringBuilder();

		for (JsonGetCommentsBean c : this._list)
		{
			sb.append("<font color=\"#ff0000\">" + c.getUsername() + "</font>:"
					+ c.getCommentcontent() + "<font color=\"#FFA500\">(      "
					+ c.getTime() + ")</font><br/>");
		}

		this.remarkcontent.setText(Html.fromHtml(sb.toString()));

	}

	public void init()
	{
		// TODO Auto-generated method stub

	}

	public void refresh(int taskID, Object... paras)
	{
		switch (taskID)
		{
		case TaskType.GET_COMMENTS:
		{

			int state = (Integer) paras[0];
			if (state == JsonStatus.GET_COMMNET_SUCCESS)
			{

				if (this._list == null)
				{
					this._list = new ArrayList<JsonGetCommentsBean>();

				}
				else
				{
					this._list.clear();
				}
				int size = (Integer) paras[1];
				JSONArray ja = (JSONArray) paras[2];
				for (int i = 0; i < size; i++)
				{
					try
					{
						this._list.add(new JsonGetCommentsBean(ja
								.getJSONObject(i)));
					}
					catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				remarknumb.setText("评论" + size);
				setComment();

			}
			else
			{

				Toast.makeText(this, "获取评论失败！", Toast.LENGTH_SHORT).show();
			}

			break;
		}

		case TaskType.INSERT_COMMENTS:
		{

			int state = (Integer) paras[0];

			if (state == JsonStatus.INSERT_COMMNET_SUCCESS)
			{

				this._list.add(new JsonGetCommentsBean(
						LogUserBean.LOG_USER_NAME, new SimpleDateFormat(
								"yyyy-MM-dd HH:mm").format(new Date())
								.toString(), remark_submit_content.getText()
								.toString()));

				setComment();

				this.remarknumb.setText("评论"
						+ (Integer.parseInt(this.remarknumb.getText()
								.toString().substring(2)) + 1));

				Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(this, "评论失败", Toast.LENGTH_SHORT).show();
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
		MainService.removeActivity(PopularDetailActivity.class.getName());
		super.onDestroy();
	}

}
