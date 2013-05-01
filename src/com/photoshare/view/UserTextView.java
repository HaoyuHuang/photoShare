/**
 * 
 */
package com.photoshare.view;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 * 
 */
public class UserTextView {

	private TextView textView;
	private UserInfo userInfo;
	private String text;
	private boolean disableListener = false;

	public UserTextView(TextView view, UserInfo userInfo, String text) {
		this.textView = view;
		this.userInfo = userInfo;
		this.text = text;
	}

	public synchronized void apply() {

		while (text.length() <= 8) {
			text = text.concat(" ");
		}

		SpannableString spStr = new SpannableString(text);

		DecoratedClickableSpan clickSpan = new DecoratedClickableSpan();
		spStr.setSpan(clickSpan, 0, text.length(),
				Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		if (!disableListener) {
			clickSpan.registerListener(listener);
		}
		textView.setText(spStr);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
	}

	public void invisible() {
		textView.setVisibility(View.GONE);
	}

	public void visible() {
		textView.setVisibility(View.VISIBLE);
	}

	public void disableListener() {
		this.disableListener = true;
	}

	private DecoratedClickableSpan.OnHyperLineClickListener listener = new DecoratedClickableSpan.OnHyperLineClickListener() {

		public void processHyperLinkClick() {
			// TODO Auto-generated method stub
			if (UserTextOnClickListener != null) {
				UserTextOnClickListener.OnClick(userInfo);
			}
		}
	};

	public interface UserTextOnClickListener {
		public void OnClick(UserInfo info);
	}

	private UserTextOnClickListener UserTextOnClickListener;

	public void registerListener(UserTextOnClickListener userTextOnClickListener) {
		this.UserTextOnClickListener = userTextOnClickListener;
	}

}
