package com.photoshare.utils.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import com.photoshare.service.photos.factory.BitmapDisplayConfig;

public class AsyncPhotoQueue {

	private List<AsyncPhotoBean> list = new ArrayList<AsyncPhotoBean>();

	private int queueSize;

	private int aliveSize;

	private static final int MAX_SIZE = 2;

	public void deque(AsyncPhotoBean asyncPhotoBean) {
		list.remove(asyncPhotoBean);
		queueSize--;
	}

	public synchronized void enque(final String url,
			final AsyncImageCallback callback, final BitmapDisplayConfig config) {
		while (aliveSize == MAX_SIZE) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		list.add(new AsyncPhotoBean(url, callback, config));
		aliveSize++;
		System.out.println("produce number -- " + aliveSize);
		notify();
	}

	public synchronized void consumePhoto() {
		while (aliveSize == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (AsyncPhotoBean bean : list) {
			if (bean.getCallback() != null) {
				AsyncUtils.getInstance().consumeAsyncPhotoBean(bean);
			}
		}
		aliveSize--;
		System.out.println("consume number -- " + aliveSize);
		notify();
	}

	public void init(Executor executor) {
		// executor.execute(getPhotoThread);
	}

	public void evictAll() {
		list.clear();
	}

}
