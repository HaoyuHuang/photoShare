package com.photoshare.Tencent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.photoshare.R;
import com.photoshare.ui.RegistActivity;
import com.photoshare.ui.interfaces.BaseActivity;
import com.tencent.weibo.constants.OAuthConstants;

public class TencentOAuthWebView extends Activity implements BaseActivity
{

	public final static int RESULT_CODE = 1;

	private ProgressDialog _PD;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(this);
		WebView webView = new WebView(this);
		linearLayout.addView(webView, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		setContentView(linearLayout);

		String urlStr = OAuthConstants.OAUTH_V1_AUTHORIZE_URL + "?oauth_token="
				+ TencentApp.oAuth.getOauthToken();
		Log.d("WCH",
				"request token-------->" + TencentApp.oAuth.getOauthToken());
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webView.requestFocus();
		webView.loadUrl(urlStr);
		WebViewClient client = new WebViewClient()
		{
			/**
			 * 回调方法，当页面开始加载时执行
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{

				Log.i("WCH", "WebView onPageStarted...");
				Log.i("WCH", "URL = " + url);

				if (TencentOAuthWebView.this._PD == null)
				{
					TencentOAuthWebView.this._PD = new ProgressDialog(
							TencentOAuthWebView.this);
				}
				_PD.setCancelable(false);
				TencentOAuthWebView.this._PD
						.setMessage(TencentOAuthWebView.this.getResources()
								.getString(R.string.on_loding_please_wait));
				TencentOAuthWebView.this._PD.show();

				if (url.indexOf("checkType=verifycode") != -1)
				{
					int start = url.indexOf("checkType=verifycode&v=") + 23;
					String verifyCode = url.substring(start, start + 6);
					// 设定验证码
					Log.d("WCH", "verifyCode------>" + verifyCode);

					TencentApp.oAuth.setOauthVerifier(verifyCode);

					Log.d("WCH",
							"oauth state------------->"
									+ TencentApp.oAuth.getStatus());
					Intent intent = new Intent();
					setResult(RESULT_CODE, intent);
					view.destroyDrawingCache();
					view.destroy();
					finish();
				}
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url)
			{
				// TODO Auto-generated method stub

				super.onPageFinished(view, url);

				if (TencentOAuthWebView.this._PD != null)
				{

					TencentOAuthWebView.this._PD.dismiss();
				}

			}

			/*
			 * TODO Android2.2及以上版本才能使用该方法
			 * 目前https://open.t.qq.com中存在http资源会引起sslerror，待网站修正后可去掉该方法
			 */
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error)
			{
				if (((null != view.getUrl()) && (view.getUrl()
						.startsWith("https://open.t.qq.com")))
						|| ((null != view.getUrl()) && (view.getUrl()
								.startsWith("https://ssl.ptlogin2.t.qq.com"))))
				{
					handler.proceed();// 接受证书
				}
				else
				{
					handler.cancel(); // 默认的处理方式，WebView变成空白页
				}

			}
		};

		webView.setWebViewClient(client);
	}

	public void init()
	{
		// TODO Auto-generated method stub

	}

	public void refresh(int taskID, Object... paras)
	{
		// TODO Auto-generated method stub

	}
}
