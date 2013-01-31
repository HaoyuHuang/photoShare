package com.photoshare.cache;

import android.content.res.AssetManager;


public class TaskParam {
	private byte[] byteBitmap;
	private AssetManager assetManager;
	private int ItemWidth;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getByteBitmap() {
		return byteBitmap;
	}

	public void setByteBitmap(byte[] byteBitmap) {
		this.byteBitmap = byteBitmap;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public int getItemWidth() {
		return ItemWidth;
	}

	public void setItemWidth(int itemWidth) {
		ItemWidth = itemWidth;
	}
}
