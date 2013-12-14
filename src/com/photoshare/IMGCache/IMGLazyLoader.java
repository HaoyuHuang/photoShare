package com.photoshare.IMGCache;

import java.lang.Thread.State;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class IMGLazyLoader
{

	private IMGDownLoadManager _FIMGDLM ;

	private BlockingQueue<String> _BQ = new ArrayBlockingQueue<String>(20);

	private DownLoadThread _DLT = new DownLoadThread();

	private IMGCallBackManager _FCBM = new IMGCallBackManager();

	private static final int HAMDLE_IMG = 1;

	private static final String EXTRA_URL = "EXTRA_URL";
	private static final String EXTRA_IMG = "EXTRA_IMG";
	
	
	
	
	
	
	public IMGLazyLoader(Context context)
	{
		
		_FIMGDLM=new IMGDownLoadManager(context);
		
	}
	
	
	
	
	
	
	
	

	private Handler _handler = new Handler()
	{

		public void handleMessage(Message msg)
		{

			int what = msg.what;
			Bundle bundle = msg.getData();

			Bitmap bitmap = (Bitmap) bundle
					.getParcelable(IMGLazyLoader.EXTRA_IMG);
			String url = (String) bundle.getString(IMGLazyLoader.EXTRA_URL);

			switch (what)
			{

			case IMGLazyLoader.HAMDLE_IMG:
			{
				_FCBM.executeCallBack(url, bitmap);
				break;
			}
			default:
				break;
			}

		}

	};

	public Bitmap get(String url, IMGCallBack callBack)
	{

		Bitmap bitmap = null;

		bitmap = _FIMGDLM.getIMGFromCache(url);

		if (null == bitmap)
		{
			
			_FCBM.addCallBack(url, callBack);//加入接口
			startDownLoadThread(url);//开始线程
		}
		else
		{
			callBack.refresh(url, bitmap);//有的话就直接刷新
		}
		return bitmap;

	}

	
	
	
	
	
	private void putURLToBlockQueue(String url)
	{
		try
		{
			if (!this._BQ.contains(url))
			{
				this._BQ.put(url);
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

	private void startDownLoadThread(String url)
	{
		putURLToBlockQueue(url);// 加入队列
		State state = _DLT.getState();
		if (state == state.NEW)
		{
			this._DLT.start();
		}
		else if (state == State.TERMINATED)
		{

			this._DLT = new DownLoadThread();
			this._DLT.start();
		}

	}

	private class DownLoadThread extends Thread
	{

		private boolean isRun = true;

		@Override
		public void run()
		{

			while (isRun)
			{

				String url = IMGLazyLoader.this._BQ.poll();
				if (null == url)
				{
					break;
				}
				else
				{
					/*
					 * 从网络获取数据，当数据获取完成后，调用handler进行处理
					 */

					Bitmap bitmap = _FIMGDLM.getIMGFromInterent(url);
					Message message = new Message();
					message.what = IMGLazyLoader.HAMDLE_IMG;
					Bundle bundle = message.getData();
					bundle.putSerializable(EXTRA_URL, url);
					bundle.putParcelable(EXTRA_IMG, bitmap);
					IMGLazyLoader.this._handler.sendMessage(message);

				}
			}
		}
	}
}
