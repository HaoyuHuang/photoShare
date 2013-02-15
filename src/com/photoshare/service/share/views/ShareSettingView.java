/**
 * 
 */
package com.photoshare.service.share.views;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.photoshare.service.share.ShareBean;
import com.photoshare.tabHost.R;

/**
 * @author czj_yy
 * 
 */
public class ShareSettingView {

	private View baseView;
	private EditText mShareAccount;
	private EditText mSharePwd;
	private Button mShareBtn;
	private Button mCancleBtn;
	private ShareBean mShareBean;

	/**
	 * @param baseView
	 * @param context
	 */
	public ShareSettingView(View baseView, ShareBean bean) {
		super();
		this.baseView = baseView;
		this.mShareBean = bean;
	}

	public void applyView() {
		mShareAccount = (EditText) baseView.findViewById(R.id.shareAccount);
		mSharePwd = (EditText) baseView.findViewById(R.id.sharePassword);
		mShareAccount.setText(mShareBean.getmShareAccount());
		mSharePwd.setText(mShareBean.getmSharePwd());
		mShareBtn = (Button) baseView.findViewById(R.id.shareSubmitBtn);
		mShareBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (listener != null) {
					listener.AsyncSubmit(gatherInfo());
				}
			}
		});
		mCancleBtn = (Button) baseView.findViewById(R.id.shareCancleBtn);
		mCancleBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (listener != null) {
					listener.AsyncCancle(gatherInfo());
				}
			}
		});
	}

	private ShareBean gatherInfo() {
		ShareBean bean = new ShareBean();
		bean.setmShareAccount(mShareAccount.getText().toString());
		bean.setmSharePwd(mSharePwd.getText().toString());
		bean.setmShareType(mShareBean.getmShareType());
		return bean;
	}

	private OnAsyncClickListener listener;

	public void registerListener(OnAsyncClickListener listener) {
		this.listener = listener;
	}

	public interface OnAsyncClickListener {
		public void AsyncSubmit(ShareBean info);

		public void AsyncCancle(ShareBean info);
	}
}
