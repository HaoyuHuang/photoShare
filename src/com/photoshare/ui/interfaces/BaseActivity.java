package com.photoshare.ui.interfaces;

public interface BaseActivity
{

	/**
	 * 初始化Activity
	 */
	public void init();
	
	
	
	/**
	 * 
	 * 
	 * 刷新ActivityUI
	 * @param taskID
	 * @param paras
	 */
	public void refresh(int taskID,Object... paras);
	
	
	
}
