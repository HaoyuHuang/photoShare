package com.photoshare.fragments.stacktrace;

public enum TracePhase {
	HOME(0), POPULAR(1), CAMERA(2), NEWS(3), HOME_PAGE(4), MAIN(5), USER_HOME(6);

	private int phase;

	TracePhase(int phase) {
		this.phase = phase;
	}

	public int getPhase() {
		return phase;
	}

}
