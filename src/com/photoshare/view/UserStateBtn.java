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
public class UserStateBtn implements IObserver<State> {

	private Button btn;
	private View baseView;
	private int BtnRid;
	private boolean initValue;

	private String successText;
	private String pendingText;
	private String startText;
	private String failText;

	/**
	 * @param baseView
	 * @param btnRid
	 * @param initValue
	 * @param enabledStr
	 * @param disabledStr
	 */
	public UserStateBtn(View baseView, int btnRid, boolean initValue,
			String enabledStr, String intermediateStr, String disabledStr,
			String failStr) {
		super();
		this.baseView = baseView;
		BtnRid = btnRid;
		this.initValue = initValue;
		this.successText = enabledStr;
		this.pendingText = intermediateStr;
		this.startText = disabledStr;
		System.out.println(intermediateStr);
		System.out.println(disabledStr);
	}

	public void applyView() {
		btn = (Button) baseView.findViewById(BtnRid);
		if (initValue) {
			update(State.SUCCESS);
		} else {
			update(State.START);
		}
		btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub'
				btn.setText(pendingText);
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
		public void OnClick(IObserver<State> observer);
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
	public void update(State state) {
		// TODO Auto-generated method stub
		switch (state) {
		case FAIL:
			btn.setText(failText);
			btn.setEnabled(true);
			break;
		case PENDING:
			btn.setText(pendingText);
			btn.setEnabled(false);
			break;
		case START:
			btn.setText(startText);
			btn.setEnabled(true);
			break;
		case SUCCESS:
			btn.setText(successText);
			btn.setEnabled(true);
			break;
		default:
			break;
		}
	}

}
