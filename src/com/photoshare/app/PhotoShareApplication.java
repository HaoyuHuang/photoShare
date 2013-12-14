package com.photoshare.app;

import android.app.Application;

import com.photoshare.IMGCache.IMGLazyLoader;
import com.photoshare.support.SupportManager;

public class PhotoShareApplication extends Application
{

	public static IMGLazyLoader _lazyLorder;

	public void onCreate()
	{
		_lazyLorder = new IMGLazyLoader(this.getApplicationContext());
		SupportManager.context=this.getApplicationContext();
	}

}
