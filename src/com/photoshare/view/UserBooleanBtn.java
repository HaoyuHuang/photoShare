/**
 * 
 */
package com.photoshare.view;

import android.view.View;
import android.widget.Button;

import com.photoshare.common.IObserver;

/**
 * @author czj_yy
 * 
 */
public class UserBooleanBtn implements IObserver<Boolean> {

	private Button btn;
	private View baseView;
	private int BtnRid;
	private boolean initValue;

	private String enabledStr;
	private String intermediateStr;
	private String disabledStr;

	/**
	 * @param baseView
	 * @param btnRid
	 * @param initValue
	 * @param enabledStr
	 * @param disabledStr
	 */
	public UserBooleanBtn(View baseView, int btnRid, boolean initValue,
			String enabledStr, String intermediateStr, String disabledStr) {
		super();
		this.baseView = baseView;
		BtnRid = btnRid;
		this.initValue = initValue;
		this.enabledStr = enabledStr;
		this.intermediateStr = intermediateStr;
		this.disabledStr = disabledStr;
	}

	public void applyView() {
		btn = (Button) baseView.findViewById(BtnRid);
		update(initValue);
		btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub'
				btn.setText(intermediateStr);
				btn.setEnabled(false);
				OnClick();
			}

		});

	}

	/**
			 * 
			 */
	private void OnClick() {
		if (onClickListener != null) {
			onClickListener.OnClick(this);
		}
	}

	public interface OnObserverClickListener {
		public void OnClick(IObserver<Boolean> observer);
	}

	private OnObserverClickListener onClickListener;

	public void registerListener(OnObserverClickListener clickListener) {
		this.onClickListener = clickListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.IObserver#update(java.lang.Object)
	 */
	public void update(Boolean subject) {
		// TODO Auto-generated method stub
		if (subject) {
			btn.setText(enabledStr);
		} else {
			btn.setText(disabledStr);
		}
		btn.setEnabled(true);
	}

}
