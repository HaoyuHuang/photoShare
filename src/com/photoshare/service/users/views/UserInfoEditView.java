/**
 * 
 */
package com.photoshare.service.users.views;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;

/**
 * @author czj_yy
 * 
 */
public class UserInfoEditView {

	private UserInfo userInfo;
	private View baseView;
	private EditText mUserPseudoName;
	private EditText mUserMailView;
	private EditText mUserWebsiteView;
	private EditText mUserBioView;
	private EditText mUserPhoneView;
	private EditText mUserBirthdayView;
	private Button mUserSubmitBtn;

	/**
	 * @param userInfo
	 * @param baseView
	 */
	public UserInfoEditView(UserInfo userInfo, View baseView) {
		super();
		this.userInfo = userInfo;
		this.baseView = baseView;
	}

	public void applyView() {
		mUserBioView = (EditText) baseView.findViewById(R.id.mEditBio);
		mUserBirthdayView = (EditText) baseView
				.findViewById(R.id.mEditBirthday);
		mUserMailView = (EditText) baseView.findViewById(R.id.mEditMail);
		mUserPhoneView = (EditText) baseView.findViewById(R.id.mEditPhone);
		mUserPseudoName = (EditText) baseView
				.findViewById(R.id.mEditPseudoName);
		mUserWebsiteView = (EditText) baseView.findViewById(R.id.mEditWebsite);
		mUserSubmitBtn = (Button) baseView.findViewById(R.id.mEditSubmit);

		mUserBioView.setText(userInfo.getBio());
		mUserBirthdayView.setText(userInfo.getBirthday());
		mUserMailView.setText(userInfo.getMail());
		mUserPhoneView.setText(userInfo.getPhoneNumber());
		mUserPseudoName.setText(userInfo.getName());
		mUserWebsiteView.setText(userInfo.getWebsite());

		mUserSubmitBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.AsyncSubmit(gatherUserInfo());
				}
			}

		});
	}

	private UserInfo gatherUserInfo() {
		UserInfo info = new UserInfo.UserInfoBuilder()
				.Bio(mUserBioView.getText().toString())
				.Birthday(mUserBirthdayView.getText().toString())
				.Mail(mUserMailView.getText().toString())
				.Phone(mUserPhoneView.getText().toString())
				.Website(mUserWebsiteView.getText().toString())
				.ID(userInfo.getUid()).build();
		return info;
	}

	private OnAsyncClickListener listener;

	public void registerListener(OnAsyncClickListener listener) {
		this.listener = listener;
	}

	public interface OnAsyncClickListener {
		public void AsyncSubmit(UserInfo info);
	}
}
