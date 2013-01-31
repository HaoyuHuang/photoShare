package com.photoshare.fragments.stacktrace;

import java.util.ArrayList;
import java.util.List;

public final class TraceFootprint {

	private int currentIndex = 0;

	/**
	 * The currentSize will not outweigh the TRACE_MAX_SIZE defined in the
	 * TraceConfig class. If so, after the client back to the previous fragments
	 * or change the tabs, the oversized elements will automatically be
	 * destroyed. {@link TraceConfig#getTraceMaxSize()}
	 */
	private int currentSize;

	private List<TraceElement> elements = new ArrayList<TraceElement>();

	public void forward(TraceElement element) {
		currentIndex++;
		currentSize++;
		element.setForward();
		elements.add(element);
		System.out.println("TraceElementSize Forward" + elements.size()
				+ element.getOriginFragment());
	}

	public TraceElement back() {
		if (currentIndex == 0) {
			throw new IllegalStateException("There is no backwards elements");
		}

		currentIndex--;
		TraceElement element = elements.get(currentIndex);
		element.setBackward();
		TraceElement copy = element.copy();
		clearBackwards();
		return copy;
	}

	/**
	 * Clear footprints while the size outweigh the size defined in TraceConfig
	 * at changing tab events. {@link TraceConfig#getTraceMaxSize()}
	 */
	public void clear() {
		if (currentSize > TraceConfig.getTraceMaxSize()) {
			currentIndex = TraceConfig.getTraceMaxSize();
			currentSize = TraceConfig.getTraceMaxSize();
			for (int i = 0; i < elements.size(); i++) {
				if (i > TraceConfig.getTraceMaxSize()) {
					TraceElement element = elements.get(i);
					element.destroy();
					elements.remove(i);
				}
			}
		}
	}

	/**
	 * Clear footprints at backwards events.
	 * {@link TraceConfig#getTraceMaxSize()}
	 */
	private void clearBackwards() {
		currentSize--;
		System.out.println("TraceElementSizeRemove: " + elements.size());
		TraceElement element = elements.get(elements.size() - 1);
		elements.remove(elements.size() - 1);
		element.destroy();
	}

	/**
	 * Destory footprints
	 */
	public void destory() {
		for (TraceElement element : elements) {
			element.destroy();
		}
		currentIndex = 0;
		currentSize = 0;
		elements.clear();
	}

	public boolean isDestoryed() {
		return elements.size() == 0;
	}

}
