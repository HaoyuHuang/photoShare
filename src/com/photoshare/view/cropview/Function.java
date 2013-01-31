package com.photoshare.view.cropview;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Function {
	public static Bitmap zoomBitmap(Bitmap bit, int w, int h) {
		int width = bit.getWidth();
		int height = bit.getHeight();

		Matrix matrix = new Matrix(); // 创建操作图片用的Matrix对象
		float scaleWidth = ((float) w / width); // 计算缩放比例
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight); // 设置缩放比例
		Bitmap newbmp = Bitmap.createBitmap(bit, 0, 0, width, height, matrix,
				true);
		bit.recycle();
		return newbmp;
	}

	public static float pointDistance(float x1, float y1, float x2, float y2) {
		float dis = 0;
		dis = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return dis;
	}

	public static float getBitDis(float xy, int a1, int a2, int a3, int a4) {
		float res = 0;
		res = (int) (float) ((((xy - a1) * 1.0 / (a2 - a1)) * (a3 - a4)) + a4);
		return res;
	}

}
