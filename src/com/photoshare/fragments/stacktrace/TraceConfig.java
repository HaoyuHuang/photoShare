package com.photoshare.fragments.stacktrace;

public final class TraceConfig {
	private static final int TRACE_MAX_SIZE = 3;

	private static final String TRACK_FORWARD = "forward";

	private static final String TRACK_BACKWARD = "backward";

	public static final int getTraceMaxSize() {
		return TRACE_MAX_SIZE;
	}

	public static String getTrackForward() {
		return TRACK_FORWARD;
	}

	public static String getTrackBackward() {
		return TRACK_BACKWARD;
	}

}
