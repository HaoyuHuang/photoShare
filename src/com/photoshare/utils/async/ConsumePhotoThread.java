package com.photoshare.utils.async;

public class ConsumePhotoThread implements Runnable {
	
	private AsyncPhotoQueue queue;
	
	public ConsumePhotoThread(AsyncPhotoQueue queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		while (true) {
			queue.consumePhoto();
		}
	}

}
