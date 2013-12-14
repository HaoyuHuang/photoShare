package com.photoshare.IMGCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

public class IMGCallBackManager
{

	private Map<String, List<IMGCallBack>> _callback = new HashMap<String, List<IMGCallBack>>();

	public void addCallBack(String url, IMGCallBack callback)
	{
		if (_callback.containsKey(url) == false)
		{

			List<IMGCallBack> list = new ArrayList<IMGCallBack>();
			list.add(callback);

			_callback.put(url, list);
		}
		else
		{
			List<IMGCallBack> list = _callback.get(url);

			list.add(callback);

		}

	}

	public void executeCallBack(String url, Bitmap bitmap)
	{

		if (this._callback.containsKey(url) == false)
		{
			// do nothing!
		}
		else
		{
			List<IMGCallBack> list = this._callback.get(url);

			for (IMGCallBack c : list)
			{

				c.refresh(url, bitmap);

			}
			list.clear();
			this._callback.remove(url);

		}

	}
}
