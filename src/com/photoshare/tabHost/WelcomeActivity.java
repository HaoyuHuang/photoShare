/**
 * 
 */
package com.photoshare.tabHost;

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.service.users.UserInfo;
import com.photoshare.service.users.UserInfoReader;
import com.photoshare.utils.UserReader;

/**
 * @author Aron
 * 
 *         The WelcomActivity doesn't hold a content view. This activity do the
 *         simplest work of reading data from config.xml} and dispatch
 *         subsequent activities. They are {@link MainActivity} and
 *         {@link TabHostActivity}
 * 
 * 
 */
public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		initUserInfo();
		super.onResume();
	}

	private void initUserInfo() {
		AbstractRequestListener<UserInfo> listener = new AbstractRequestListener<UserInfo>() {

			@Override
			public void onComplete(final UserInfo bean) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						if (user.isFieldsEmpty()) {
							startMain();
							return;
						}
						if (bean != null) {
							startTabHost();
						}
					}

				});
			}

			@Override
			public void onNetworkError(NetworkError networkError) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						startMain();
					}
				});
			}

			@Override
			public void onFault(Throwable fault) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						startMain();
					}
				});
			}
		};
		async.readUserInfo(new UserReader(), new UserInfoReader(), listener);
	}

	private void startTabHost() {
		Command.TabHost(this);
	}

	private void startMain() {
		Command.Main(this);
	}
}
