package com.photoshare.bean.json;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonGetPopularBean
{


	private int picID;
	private String picURL;

	private String userName;
	private String time;
	private String text;
	private int pandC;
	private int comment;

	
	public JsonGetPopularBean(JSONObject jo)
	{

		Log.d("WCH", "jo------------>content" + jo.toString());

		try
		{
			this.picID = jo.getInt("picID");
			this.picURL = jo.getString("picURL");
			this.userName=jo.getString("userName");
			this.time=jo.getString("time");
			this.text=jo.getString("text");
			this.pandC=jo.getInt("pandC");
			this.comment=jo.getInt("comment");
			
			
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public int getPandC()
	{
		return pandC;
	}

	public void setPandC(int pandC)
	{
		pandC = pandC;
	}

	public int getComment()
	{
		return comment;
	}

	public void setComment(int comment)
	{
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

	public String getPicURL()
	{
		return picURL;
	}

	public void setPicURL(String picURL)
	{
		this.picURL = picURL;
	}


	



}
