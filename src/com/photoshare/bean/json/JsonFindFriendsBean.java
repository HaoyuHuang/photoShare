package com.photoshare.bean.json;

import org.json.JSONException;
import org.json.JSONObject;



public class JsonFindFriendsBean
{

	private int userID;
	private String userName;
	private String sex;
	private String nickName;
	private String email;

	
	public JsonFindFriendsBean(JSONObject jo)
	{

		try
		{
			this.userID = jo.getInt("userID");
			this.userName = jo.getString("userName");
			this.sex = jo.getString("sex");
			this.nickName = jo.getString("nickName");
			this.email = jo.getString("email");
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public JsonFindFriendsBean(int userID, String userName, String sex,
			String nickName, String email)
	{
		super();
		this.userID = userID;
		this.userName = userName;
		this.sex = sex;
		this.nickName = nickName;
		this.email = email;
	}

	public int getUserID()
	{
		return userID;
	}

	public void setUserID(int userID)
	{
		this.userID = userID;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

}
