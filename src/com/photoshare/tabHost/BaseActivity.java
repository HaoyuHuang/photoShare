/**
 * 
 */
package com.photoshare.tabHost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.fragments.stacktrace.TraceStack;
import com.photoshare.service.signin.UserSignInRequestParam;
import com.photoshare.service.signin.UserSignInResponseBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 *         The base activity contains all the basic info for each sub activity
 *         to use. They are {@link User} , {@link AsyncUtils} ,
 *         {@code mExceptionHandler}.
 * 
 */
public class BaseActivity extends Activity {
	private ProgressDialog progressDialog;

	protected User user = User.Instance();

	protected AsyncUtils async = AsyncUtils.getInstance();

	protected TraceStack stack = TraceStack.getInstance();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissProgress();
		if (user.isLogging()) {

		}
	}

	/**
	 * 显示等待框
	 * 
	 * @param title
	 * @param message
	 */
	protected void showProgress(String title, String message) {
		progressDialog = ProgressDialog.show(this, title, message);
	}

	/**
	 * 取消等待框
	 */
	protected void dismissProgress() {
		if (progressDialog != null) {
			try {
				progressDialog.dismiss();
			} catch (Exception e) {

			}
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.i("Restore", "start...");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.i("Save", "start...");
		super.onSaveInstanceState(outState);
	}

	/**
	 * 解析逗号分割的字符串
	 * 
	 * @return
	 */
	public String[] parseCommaIds(String s) {
		if (s == null) {
			return null;
		}
		String[] ids = s.split(",");
		return ids;
	}

	/**
	 * 显示等待框
	 */
	protected void showProgress() {
		showProgress("Please wait", "progressing");
	}

	protected final Handler mExceptionHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case NetworkError.ERROR_CODE_ILLEGAL_PARAMETER:
				break;
			case NetworkError.ERROR_CODE_LOG_ERROR:
				break;
			case NetworkError.ERROR_CODE_NULL_PARAMETER:
				break;
			case NetworkError.ERROR_CODE_PARAMETER_EXTENDS_LIMIT:
				break;
			case NetworkError.ERROR_CODE_UNABLE_PARSE_RESPONSE:
				break;
			case NetworkError.ERROR_CODE_UNKNOWN_ERROR:
				break;
			case NetworkError.ERROR_COMMENT:
				break;
			case NetworkError.ERROR_EDIT_PROFILE:
				break;
			case NetworkError.ERROR_FOLLOW:
				break;
			case NetworkError.ERROR_MAIL_NULL:
				break;
			case NetworkError.ERROR_NAME_NULL:
				break;
			case NetworkError.ERROR_PHOTO:
				break;
			case NetworkError.ERROR_PSEUDO_NAME_NULL:
				break;
			case NetworkError.ERROR_PWD_NULL:
				break;
			case NetworkError.ERROR_REFRESH_DATA:
				break;
			case NetworkError.ERROR_SIGN_IN:
				break;
			case NetworkError.ERROR_SIGN_UP:
				break;
			case NetworkError.ERROR_LIKE:
				break;
			default:
				super.handleMessage(msg);
			}
		}

	};

	protected void showAlertDialog(String title, String text,
			Utils.OnOptionListener listener) {
		Utils.showOptionWindow(this, title, text, listener);
	}
	
	protected void onLoginSuccess() {
		
	}
	
	/**
	 * 显示Toast提示
	 * 
	 * @param message
	 */
	public void showTip(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	protected void AsyncSignIn() {
		UserSignInRequestParam param = new UserSignInRequestParam(
				user.getMail(), user.getPwd());
		AbstractRequestListener<UserSignInResponseBean> listener = new AbstractRequestListener<UserSignInResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub

					}

				});
			}

			@Override
			public void onFault(Throwable fault) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub

					}

				});
			}

			@Override
			public void onComplete(UserSignInResponseBean bean) {
				if (bean != null) {
					UserInfo info = bean.getUserInfo();
					if (info != null) {
						user.setUserInfo(info);
						user.setLogging(true);
					}
				}
				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						onLoginSuccess();
					}

				});
			}
		};
		async.SignIn(param, listener);
	}

}
