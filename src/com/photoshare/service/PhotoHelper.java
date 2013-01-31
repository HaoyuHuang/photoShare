package com.photoshare.service;

import java.io.File;
import java.util.concurrent.Executor;

import android.os.Bundle;
import android.util.Log;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.photos.PhotoGetInfoRequestParam;
import com.photoshare.service.photos.PhotoGetInfoResponseBean;
import com.photoshare.service.photos.PhotoUploadRequestParam;
import com.photoshare.service.photos.PhotoUploadResponseBean;
import com.photoshare.service.photos.PhotosGetInfoRequestParam;
import com.photoshare.service.photos.PhotosGetInfoResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;

public class PhotoHelper {

	/**
	 * User对象
	 */
	private User user;

	public PhotoHelper(User user) {
		this.user = user;
	}

	/**
	 * 上传照片
	 * 
	 * @param file
	 *            上传的文件
	 * @return 成功返回请求结果，失败返回null
	 * @throws NetworkException
	 * 
	 */
	public PhotoUploadResponseBean uploadPhoto(File file, String caption)
			throws NetworkException, Throwable {
		PhotoUploadRequestParam photoRequest = new PhotoUploadRequestParam();
		photoRequest.setFile(file);
		photoRequest.setCaption(caption);
		return uploadPhoto(photoRequest);
	}

	/**
	 * 上传照片
	 * 
	 * @param photoRequest
	 *            请求参数
	 * @return 成功返回请求结果，失败返回null
	 * @throws NetworkException
	 * 
	 */
	public PhotoUploadResponseBean uploadPhoto(
			PhotoUploadRequestParam photoRequest) throws NetworkException,
			Throwable {

		if (!user.isLogging()) {
			// 若用户没有登录，则直接抛出异常
			Utils.logger("exception in uploading photo: no login!");
			throw new NetworkException(NetworkError.ERROR_CODE_LOG_ERROR,
					"没有登录", "没有登录");
		}

		if (photoRequest == null) {
			photoRequest = new PhotoUploadRequestParam();
		}

		if (photoRequest.getFile() == null) {
			Utils.logger("exception in uploading photo: no upload photo file!");
			throw new NetworkException(NetworkError.ERROR_CODE_NULL_PARAMETER,
					"上传失败，没有文件！", "上传失败，没有文件！");
		}

		// 检查文件类型是否合法
		String fileName = photoRequest.getFile().getName();

		if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")
				&& !fileName.endsWith(".png") && !fileName.endsWith(".bmp")
				&& !fileName.endsWith("gif")) {
			Utils.logger("exception in uploading photo: file format is invalid! only jpg/jpeg,png,bmp,gif is supported!");
			throw new NetworkException(
					NetworkError.ERROR_CODE_ILLEGAL_PARAMETER,
					"暂不支持此格式照片，请重新选择", "暂不支持此格式照片，请重新选择");
		}

		// 获取文件内容字节数组
		byte[] content = Utils.fileToByteArray(photoRequest.getFile());
		if (content == null) {
			Utils.logger("exception in uploading photo: file can't be empty");
			throw new NetworkException(NetworkError.ERROR_CODE_NULL_PARAMETER,
					"上传失败，文件内容为空！", "上传失败，文件内容为空！");
		}

		// caption如果为null的话，会将caption的值设为"null"，所以要处理下为空的情况
		if (photoRequest.getCaption() == null) {
			photoRequest.setCaption("");
		}

		// 如果照片描述的字数超过25个字，则抛出NetworkException异常
		if (photoRequest.getCaption().trim().length() > 140) {
			Utils.logger("exception in uploading photo: the length of photo caption should no more than 140 words!");
			throw new NetworkException(
					NetworkError.ERROR_CODE_PARAMETER_EXTENDS_LIMIT,
					"照片描述不能超过25个字", "照片描述不能超过25个字");
		}

		String result = null;
		try {
			result = user.publishPhoto(photoRequest.getAction(), content,
					photoRequest.getFile().getName(),
					photoRequest.getCaption(), User.RESPONSE_FORMAT_JSON);
		} catch (RuntimeException e) {
			Utils.logger("exception in uploading photo:error in internet requesting\t"
					+ e.getMessage());
			throw new Throwable(e);
		}

		if (result == null) {
			return null;
		}

		// 检查请求返回值是否错误信息
		Utils.checkResponse(result);

		// 用请求结果构造返回实体
		PhotoUploadResponseBean photoResponse = new PhotoUploadResponseBean(
				result);

		// 照片已经上传成功
		Log.i(Utils.LOG_TAG, "success uploading photo! \n" + photoResponse);

		return photoResponse;
	}

	/**
	 * 异步上传照片
	 * 
	 * @param photoRequest
	 *            请求参数
	 * @param listener
	 *            处理返回结果
	 * @return
	 */
	public void asyncUploadPhoto(final Executor pool,
			final PhotoUploadRequestParam photoRequest,
			final AbstractRequestListener<PhotoUploadResponseBean> listener) {

		pool.execute(new Runnable() {
			public void run() {
				try {
					PhotoUploadResponseBean photoResponse = uploadPhoto(photoRequest);

					if (photoResponse != null) {
						Utils.logger("success uploading photo! \n"
								+ photoResponse);
						if (listener != null) {
							listener.onComplete(photoResponse);
						}
					}
				} catch (NetworkException e) {
					Utils.logger("exception in uploading photo: "
							+ e.getMessage());

					if (listener != null) {
						listener.onNetworkError(new NetworkError(e
								.getErrorCode(), e.getMessage(), e
								.getOrgResponse()));
					}
				} catch (Throwable e) {
					Utils.logger("fault in uploading photo: " + e.getMessage());

					if (listener != null) {
						listener.onFault(e);
					}
				}
			}

		});
	}

	/**
	 * 获取指定请求对象的PhotoResponseBean<br>
	 * 
	 * @param param
	 *            请求对象
	 * @return 返回服务器响应的PhotoBean
	 * @throws NetworkException
	 */
	public PhotosGetInfoResponseBean getPhotosInfo(
			PhotosGetInfoRequestParam param) throws NetworkException, Throwable {

		Bundle parameters = param.getParams();
		PhotosGetInfoResponseBean photosBean = null;
		try {
			String response = user.request(param.getAction(), parameters);
			if (response != null) {
				Utils.checkResponse(response);
			} else {
				Utils.logger("null response");
				throw new NetworkException(
						NetworkError.ERROR_CODE_UNKNOWN_ERROR, "null response",
						"null response");
			}
			photosBean = new PhotosGetInfoResponseBean(response);
		} catch (RuntimeException re) {
			Utils.logger("runtime exception " + re.getMessage());
			throw new Throwable(re);
		}
		return photosBean;
	}

	/**
	 * 异步获取指定请求对象的PhotoResponseBean<br>
	 * 
	 * @param pool
	 *            线程池
	 * @param param
	 *            请求对象
	 * @param listener
	 *            回调
	 */
	public void asyncGetPhotosInfo(Executor pool,
			final PhotosGetInfoRequestParam param,
			final AbstractRequestListener<PhotosGetInfoResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					PhotosGetInfoResponseBean usersBean = getPhotosInfo(param);
					if (listener != null) {
						listener.onComplete(usersBean);
					}
				} catch (NetworkException e) {
					Utils.logger("network exception " + e.getMessage());
					if (listener != null) {
						listener.onNetworkError(new NetworkError(e.getMessage()));
						e.printStackTrace();
					}
				} catch (Throwable e) {
					Utils.logger("on fault " + e.getMessage());
					if (listener != null) {
						listener.onFault(e);
					}
				}

			}
		});

	}

	/**
	 * 获取指定请求对象的PhotoResponseBean<br>
	 * 
	 * @param param
	 *            请求对象
	 * @return 返回服务器响应的PhotoBean
	 * @throws NetworkException
	 */
	public PhotoGetInfoResponseBean getPhotoInfo(PhotoGetInfoRequestParam param)
			throws NetworkException, Throwable {

		Bundle parameters = param.getParams();
		PhotoGetInfoResponseBean photosBean = null;
		try {
			String response = user.request(param.getAction(), parameters);
			if (response != null) {
				Utils.checkResponse(response);

			} else {
				Utils.logger("null response");
				throw new NetworkException(
						NetworkError.ERROR_CODE_UNKNOWN_ERROR, "null response",
						"null response");
			}
			photosBean = new PhotoGetInfoResponseBean(response);
		} catch (RuntimeException re) {
			Utils.logger("runtime exception " + re.getMessage());
			throw new Throwable(re);
		}
		return photosBean;
	}

	/**
	 * 异步获取指定请求对象的PhotoResponseBean<br>
	 * 
	 * @param pool
	 *            线程池
	 * @param param
	 *            请求对象
	 * @param listener
	 *            回调
	 */
	public void asyncGetPhotoInfo(Executor pool,
			final PhotoGetInfoRequestParam param,
			final AbstractRequestListener<PhotoGetInfoResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					PhotoGetInfoResponseBean usersBean = getPhotoInfo(param);
					if (listener != null) {
						listener.onComplete(usersBean);
					}
				} catch (NetworkException e) {
					Utils.logger("network exception " + e.getMessage());
					if (listener != null) {
						listener.onNetworkError(new NetworkError(e.getMessage()));
						e.printStackTrace();
					}
				} catch (Throwable e) {
					Utils.logger("on fault " + e.getMessage());
					if (listener != null) {
						listener.onFault(e);
					}
				}

			}
		});

	}

}