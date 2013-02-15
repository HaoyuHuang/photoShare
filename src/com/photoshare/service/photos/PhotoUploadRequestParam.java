package com.photoshare.service.photos;

import java.io.File;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

public class PhotoUploadRequestParam extends RequestParam {

	/**
	 * 照片描述的最大长度
	 */
	public static final int CAPTION_MAX_LENGTH = 140;

	/**
	 * 调用上传照片API传入的method参数，必须参数
	 */
	@Deprecated
	public static final String METHOD = "/uploadPhoto";

	private static final String ACTION = "/UploadFileAction";

	public String getAction() {
		return ACTION;
	}

	/**
	 * 文件的数据，必须参数 目前支持的文件类型有：image/bmp, image/png, image/gif, image/jpeg,
	 * image/jpg
	 */
	private File file;
	/**
	 * 照片的描述信息，可选参数
	 */
	private String caption;

	private long uid;

	public PhotoUploadRequestParam() {

	}

	public PhotoUploadRequestParam(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public Bundle getParams() throws NetworkException {
		// 上传照片不调用requestXML或requestJSON数据接口，故此方法不实现
		return null;
	}

}
