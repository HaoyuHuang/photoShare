package com.photoshare.IMGCache;

import com.photoshare.app.PhotoShareApplication;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class IMGSimpleLoader
{

	public static void showIMG(String url, final ImageView view)
	{

		PhotoShareApplication._lazyLorder.get(url, new IMGCallBack()
		{

			public void refresh(String URL, Bitmap bitmap)
			{

				view.setImageBitmap(bitmap);

			}
		});
	}
}
