package com.photoshare.bean.json;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonGetForumsBean implements Serializable
{

	private String userName;
	private String time;
	private String text;
	private String picURL;
	private int PandC;
	private int comment;
	private int picID;

	public JsonGetForumsBean(JSONObject jo)
	{

		Log.d("WCH", jo.toString());

		try
		{
			this.userName = jo.getString("userName");
			this.time = jo.getString("time");
			this.text = jo.getString("text");
			this.picURL = jo.getString("picURL");
			Log.d("WCH", "url---------->" + jo.getString("picURL"));
			this.PandC = jo.getInt("pandC");
			this.comment = jo.getInt("comment");
			this.picID = jo.getInt("picID");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public JsonGetForumsBean(int picID, String userName, String time,
			String text, String picURL, int pandC, int comment)
	{

		this.picID = picID;
		this.userName = userName;
		this.time = time;
		this.text = text;
		this.picURL = picURL;
		this.PandC = pandC;
		this.comment = comment;
	}

	public int getPicID()
	{
		return picID;
	}

	public void setPicID(int picID)
	{
		this.picID = picID;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getPicURL()
	{
		return picURL;
	}

	public void setPicURL(String picURL)
	{
		this.picURL = picURL;
	}

	public int getPandC()
	{
		return PandC;
	}

	public void setPandC(int pandC)
	{
		PandC = pandC;
	}

	public int getComment()
	{
		return comment;
	}

	public void setComment(int comment)
	{
		this.comment = comment;
	}

}
