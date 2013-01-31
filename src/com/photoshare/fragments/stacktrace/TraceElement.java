/**
 * 
 */
package com.photoshare.fragments.stacktrace;

import android.os.Bundle;

/**
 * @author Aron
 * 
 */
public class TraceElement {
	private String originFragment;

	private Bundle params;

	private String destFragment;

	public TraceElement() {

	}

	public TraceElement(String originFragment, Bundle params) {
		super();
		this.originFragment = originFragment;
		this.params = params;
	}

	public TraceElement(String originFragment, Bundle params,
			String destFragment) {
		super();
		this.originFragment = originFragment;
		this.params = params;
		this.destFragment = destFragment;
	}

	public String getOriginFragment() {
		return originFragment;
	}

	public void setOriginFragment(String originFragment) {
		this.originFragment = originFragment;
	}

	public Bundle getParams() {
		return params;
	}

	public void setParams(Bundle params) {
		this.params = params;
	}

	public String getDestFragment() {
		return destFragment;
	}

	public void setDestFragment(String destFragment) {
		this.destFragment = destFragment;
	}

	public void destroy() {
		if (params != null) {
			// params.clear();
		}
	}

	private void clearAction() {
		if (params != null) {
			if (params.containsKey(TraceConfig.getTrackBackward())) {
				params.putBoolean(TraceConfig.getTrackBackward(), false);
			}
			if (params.containsKey(TraceConfig.getTrackForward())) {
				params.putBoolean(TraceConfig.getTrackForward(), false);
			}
		}
	}

	public void setBackward() {
		clearAction();
		if (params == null) {
			params = new Bundle();
		}
		params.putBoolean(TraceConfig.getTrackBackward(), true);
	}

	public void setForward() {
		clearAction();
		if (params == null) {
			params = new Bundle();
		}
		params.putBoolean(TraceConfig.getTrackForward(), true);
	}

	public TraceElement copy() {
		TraceElement copy = new TraceElement();
		copy.setOriginFragment(getOriginFragment());
		copy.setParams(getParams());
		copy.setDestFragment(getDestFragment());
		return copy;
	}
}
