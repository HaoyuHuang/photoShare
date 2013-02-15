/**
 * 
 */
package com.photoshare.service;

import java.io.File;

import com.renren.api.connect.android.Renren;
import com.renren.api.connect.android.common.AbstractRequestListener;
import com.renren.api.connect.android.photos.PhotoHelper;
import com.renren.api.connect.android.photos.PhotoUploadRequestParam;
import com.renren.api.connect.android.photos.PhotoUploadResponseBean;

/**
 * @author Aron
 * 
 */
public class ShareHelper {

	private PhotoUploadRequestParam mRenrenParams;
	
	public void uploadPhotoToRenRen(final File file, final String caption,
			final AbstractRequestListener<PhotoUploadResponseBean> listener,
			final Renren renren) {
		// // 读取assets文件夹下的图片，保存在手机中
		// String fileName = "renren.png";
		// // 获取文件后缀，构造本地文件名
		// int index = fileName.lastIndexOf('.');
		// // 文件保存在/sdcard目录下，以renren_前缀加系统毫秒数构造文件名
		// final String realName = "renren_" + System.currentTimeMillis()
		// + fileName.substring(index, fileName.length());
		// try {
		// InputStream is = activity.getResources().getAssets().open(fileName);
		// BufferedOutputStream bos = new BufferedOutputStream(
		// activity.openFileOutput(realName, Context.MODE_PRIVATE));
		// int length = 0;
		// byte[] buffer = new byte[1024];
		// while ((length = is.read(buffer)) != -1) {
		// bos.write(buffer, 0, length);
		// }
		// is.close();
		// bos.close();
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// String filePath = activity.getFilesDir().getAbsolutePath() + "/"
		// + realName;
		// // 以上准备好了File参数
		// // 下面调用SDK接口
		// renren.publishPhoto(activity, new File(filePath), "传入的默认参数");
		final PhotoHelper helper = new PhotoHelper(renren);
		mRenrenParams = new PhotoUploadRequestParam();

		if (caption != null) {
			if (!"".equals(caption)) {
				mRenrenParams.setCaption(caption);
			}
		}
		if (file != null) {
			mRenrenParams.setFile(file);
		}
		helper.asyncUploadPhoto(mRenrenParams, listener);
	}
}
