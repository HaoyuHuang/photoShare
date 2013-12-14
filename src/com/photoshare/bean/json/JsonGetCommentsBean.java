package com.photoshare.bean.json;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonGetCommentsBean
{

	private String username;
	private String time;
	private String commentcontent;

	public JsonGetCommentsBean(JSONObject jo)
	{
		try
		{
			username = jo.getString("username");
			time = jo.getString("time");
			commentcontent = jo.getString("commentcontent");
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public JsonGetCommentsBean(String username, String time,
			String commentcontent)
	{

		this.username = username;
		this.time = time;
		this.commentcontent = commentcontent;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getCommentcontent()
	{
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent)
	{
		this.commentcontent = commentcontent;
	}

}
