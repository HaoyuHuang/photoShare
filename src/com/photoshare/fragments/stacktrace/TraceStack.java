package com.photoshare.fragments.stacktrace;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TraceStack {

	private static TraceStack stack = new TraceStack();

	public static TraceStack getInstance() {
		return stack;
	}

	private Map<TracePhase, TraceFootprint> traces = new HashMap<TracePhase, TraceFootprint>();

	private TracePhase currentPhase;

	private TraceStack() {
		init();
	}

	public void forward(TraceElement element) {
		TraceFootprint footprint = traces.get(currentPhase);
		if (footprint != null) {
			footprint.forward(element);
		}
	}

	public TraceElement backward() {
		TraceFootprint footprint = traces.get(currentPhase);
		if (footprint != null) {
			return footprint.back();
		}
		return null;
	}

	private void init() {
		for (TracePhase phase : TracePhase.values()) {
			TraceFootprint footprint = new TraceFootprint();
			traces.put(phase, footprint);
		}
		currentPhase = TracePhase.HOME;
	}

	private void clearPhases() {
		Set<TracePhase> sets = traces.keySet();
		for (TracePhase phase : sets) {
			TraceFootprint footprint = traces.get(phase);

			if (phase == currentPhase) {
				continue;
			}

			switch (phase) {
			case CAMERA:
				break;
			case HOME:
				footprint.destory();
				break;
			case HOME_PAGE:
				footprint.destory();
				break;
			case NEWS:
				footprint.destory();
				break;
			case POPULAR:
				footprint.destory();
				break;
			default:
				break;
			}
		}
	}

	public void setCurrentPhase(TracePhase currentPhase) {
		if (this.currentPhase != currentPhase) {
			this.currentPhase = currentPhase;
			clearPhases();
		}
	}

	public TracePhase getCurrentPhase() {
		return currentPhase;
	}

	public void destroy() {
		Set<TracePhase> sets = traces.keySet();
		for (TracePhase phase : sets) {
			TraceFootprint footprint = traces.get(phase);
			footprint.destory();
		}
		traces.clear();
	}

}
