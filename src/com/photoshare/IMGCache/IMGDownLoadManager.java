package com.photoshare.IMGCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.photoshare.util.md5.MD5Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class IMGDownLoadManager
{

	private Context _content;

	private Map<String, SoftReference<Bitmap>> _imgCache = new HashMap<String, SoftReference<Bitmap>>();

	public IMGDownLoadManager(Context context)
	{

		this._content = context;
	}

	public boolean contains(String url)
	{

		return _imgCache.containsKey(url);

	}

	public Bitmap getIMGFromCache(String url)
	{

		Bitmap bitmap = null;

		if (contains(url) == true)
		{
			bitmap = this.getIMGFromMapCache(url);//从map里
		}
		else
		{

			bitmap = this.getIMGFromFile(url);
		}

		return bitmap;

	}

	public Bitmap getIMGFromFile(String url)
	{

		FileInputStream fis = null;
		try
		{
			fis = this._content.openFileInput(MD5Util.MD5Encrypt(url));
			return BitmapFactory.decodeStream(fis);
		}
		catch (FileNotFoundException e)
		{
			
			/*
			 * 
			 * 当不存在的时候，返回null，通过处理异常来返回
			 */
			
			e.printStackTrace();
			return null;
		}
		finally
		{
			if (null != fis)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 
	 * 
	 * 从map缓存中获取图片资源
	 * @param url 图片网络的URL
 	 * @return bitmap类型的图片
	 */
	
	
	public Bitmap getIMGFromMapCache(String url)
	{

		Bitmap bitmap = null;
		SoftReference<Bitmap> ref = null;
		synchronized (this)
		{
			ref = this._imgCache.get(url);
		}
		if (null != ref)
		{
			bitmap = ref.get();
			if (null != bitmap)
			{
				return bitmap;
			}
		}
		return null;
	}

	
	/**
	 * 经过网络获取图片 并将图片写入到file缓存，和map缓存
	 * @param url 图片的网络url
	 * @return Bitmap类型的数据
	 */
	

	
	
	public Bitmap getIMGFromInterent(String url)
	{

		try
		{
			HttpURLConnection connection = (HttpURLConnection) new URL(url)
					.openConnection();
			String fileName = writeToFile(MD5Util.MD5Encrypt(url),
					connection.getInputStream());//将网络文件写到file缓存
			Bitmap bitmap=BitmapFactory.decodeFile(fileName);
			synchronized (this)
			{
				this._imgCache.put(url, new SoftReference<Bitmap>(bitmap));//将bitmap加入到map缓存
			}
			return bitmap;

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 将网络的图片写入到file缓存中
	 * @param fileName 文件的名字 经过MD5加密
	 * @param is 输入流
	 * @return 是文件的名字 已经过MD5加密
	 */
	
	
	private String writeToFile(String fileName, InputStream is)
	{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try
		{
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(this._content.openFileOutput(
					fileName, Context.MODE_PRIVATE));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = bis.read(buffer)) != -1)
			{
				bos.write(buffer, 0, length);

			}

			return this._content.getFilesDir() + "/" + fileName;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally
		{
			try
			{
				if (null != bis)
				{
					bis.close();// 关闭流
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (bos != null)
					{
						bos.close();// 关闭流
					}
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
