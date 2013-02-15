/**
 * 
 */
package com.photoshare.view;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * @author czj_yy
 * 
 */
public class DecoratedClickableSpan extends ClickableSpan {

	public DecoratedClickableSpan() {
		super();
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		// TODO Auto-generated method stub
		ds.setColor(ds.linkColor);
		ds.setUnderlineText(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.style.ClickableSpan#onClick(android.view.View)
	 */
	@Override
	public void onClick(View widget) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.processHyperLinkClick();
		}
	}

	public interface OnHyperLineClickListener {
		public void processHyperLinkClick();
	}

	private OnHyperLineClickListener listener;

	public void registerListener(OnHyperLineClickListener listener) {
		this.listener = listener;
	}

}
