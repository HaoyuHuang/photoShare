package com.photoshare.bean;

public class TencentUpLoadContentBean
{


	
	
	
	private String text;
	private String fileName;

	public TencentUpLoadContentBean(String text, String fileName)
	{

		this.text = text;
		this.fileName = fileName;

	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

}
