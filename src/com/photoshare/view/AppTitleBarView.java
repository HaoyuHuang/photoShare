/**
 * 
 */
package com.photoshare.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 *         This View class encapsulate the views contained in the application
 *         title bars
 * 
 */
public class AppTitleBarView {

	private Context context;
	private View baseView;
	private Button titlebarLeftButton;
	private TextView titlebarTextView;
	private Button titlebarRightButton;

	private View errorView;
	private TextView errorText;

	public static String LEFT_BTN_TEXT = "leftBtnText";
	public static String RIGHT_BTN_TEXT = "rightBtnText";
	public static String TITLE_BAR_TEXT = "titlebarText";

	private String leftBtnText;
	private String rightBtnText;
	private String titlebarText;
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.VISIBLE;

	/**
	 * @param context
	 * @param baseView
	 * @param leftBtnText
	 * @param rightBtnText
	 * @param titlebarText
	 */
	public AppTitleBarView(Context context, View baseView, String leftBtnText,
			String rightBtnText, String titlebarText) {
		super();
		this.context = context;
		this.baseView = baseView;
		this.leftBtnText = leftBtnText;
		this.rightBtnText = rightBtnText;
		this.titlebarText = titlebarText;
	}

	/**
	 * @param context
	 * @param baseView
	 * @param leftBtnText
	 * @param rightBtnText
	 * @param titlebarText
	 * @param leftBtnVisibility
	 * @param rightBtnVisibility
	 */
	public AppTitleBarView(Context context, View baseView, String leftBtnText,
			String rightBtnText, String titlebarText, int leftBtnVisibility,
			int rightBtnVisibility) {
		super();
		this.context = context;
		this.baseView = baseView;
		this.leftBtnText = leftBtnText;
		this.rightBtnText = rightBtnText;
		this.titlebarText = titlebarText;
		this.leftBtnVisibility = leftBtnVisibility;
		this.rightBtnVisibility = rightBtnVisibility;
	}

	public void applyView() {

		titlebarLeftButton = (Button) baseView
				.findViewById(R.id.titlebar_left_button);
		titlebarRightButton = (Button) baseView
				.findViewById(R.id.titlebar_right_button);
		titlebarTextView = (TextView) baseView
				.findViewById(R.id.titlebar_title_text);

		titlebarLeftButton.setText(leftBtnText);
		titlebarLeftButton.setVisibility(leftBtnVisibility);
		titlebarLeftButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (titleBarBtnClickedListener != null) {
					titleBarBtnClickedListener.OnLeftBtnClick();
				}

			}
		});

		titlebarRightButton.setText(rightBtnText);
		titlebarRightButton.setVisibility(rightBtnVisibility);
		titlebarRightButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (titleBarBtnClickedListener != null) {
					titleBarBtnClickedListener.OnRightBtnClick();
				}
			}
		});

		titlebarTextView.setText(titlebarText);

		errorView = baseView.findViewById(R.id.errorViewLayoutId);
		errorText = (TextView) baseView.findViewById(R.id.errorView);
	}

	public void setLeftBtnText(String leftBtnText) {
		this.leftBtnText = leftBtnText;
		this.titlebarLeftButton.setText(this.leftBtnText);
	}

	public void setRightBtnText(String rightBtnText) {
		this.rightBtnText = rightBtnText;
		this.titlebarRightButton.setText(this.rightBtnText);
	}

	public void setTitlebarText(String titlebarText) {
		this.titlebarText = titlebarText;
		this.titlebarTextView.setText(this.titlebarText);
	}
	
	public void setTitleLeftButtonVisibility(int visibility) {
		this.titlebarLeftButton.setVisibility(visibility);
	}
	
	public void setTitleRightButtonVisibility(int visibility) {
		this.titlebarRightButton.setVisibility(visibility);
	}

	public void setTitleLeftButtonBackground(int rid) {
		this.titlebarLeftButton.setBackgroundResource(rid);
	}

	public void setTitleRightButtonBackground(int rid) {
		this.titlebarRightButton.setBackgroundResource(rid);
	}

	public void displayErrorView(String errorMsg) {
		errorText.setText(errorMsg);
		Log.e("displayError", errorMsg);
		Animation anim = AnimationUtils.loadAnimation(context,
				R.anim.error_pop_out);
		anim.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationEnd(Animation animation) {
				Log.i("animationOver", "over");
				errorView.setVisibility(View.GONE);
			}
		});
		errorView.setVisibility(View.VISIBLE);
		errorText.setVisibility(View.VISIBLE);
		errorView.startAnimation(anim);
	}

	public interface OnTitleBarBtnClickedListener {
		public void OnLeftBtnClick();

		public void OnRightBtnClick();
	}

	private OnTitleBarBtnClickedListener titleBarBtnClickedListener;

	public void registerListener(
			OnTitleBarBtnClickedListener titleBarBtnClickedListener) {
		this.titleBarBtnClickedListener = titleBarBtnClickedListener;
	}

}
