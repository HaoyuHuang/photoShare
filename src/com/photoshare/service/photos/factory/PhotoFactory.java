package com.photoshare.service.photos.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.Log;

import com.photoshare.service.photos.filter.BrightContrastFilter;
import com.photoshare.service.photos.filter.ComicFilter;
import com.photoshare.service.photos.filter.FeatherFilter;
import com.photoshare.service.photos.filter.FilmFilter;
import com.photoshare.service.photos.filter.GaussianBlurFilter;
import com.photoshare.service.photos.filter.GlowingEdgeFilter;
import com.photoshare.service.photos.filter.HalfToneFilter;
import com.photoshare.service.photos.filter.IceFilter;
import com.photoshare.service.photos.filter.ImageData;
import com.photoshare.service.photos.filter.LomoFilter;
import com.photoshare.service.photos.filter.MeanShiftFilter;
import com.photoshare.service.photos.filter.MoltenFilter;
import com.photoshare.service.photos.filter.NoiseFilter;
import com.photoshare.service.photos.filter.OilPaintingFilter;
import com.photoshare.service.photos.filter.SoftGlowFilter;
import com.photoshare.service.photos.filter.SwirlFilter;
import com.photoshare.service.photos.filter.VignetteFilter;

public class PhotoFactory {

	public static Bitmap oldRemeber(Bitmap raw) {
		Bitmap bmp = raw.copy(Bitmap.Config.RGB_565, true);
		// 速度测试
		long start = System.currentTimeMillis();
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		int pixColor = 0;
		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int newR = 0;
		int newG = 0;
		int newB = 0;
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < width; k++) {
				pixColor = pixels[width * i + k];
				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
				newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
				newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
				int newColor = Color.argb(255, newR > 255 ? 255 : newR,
						newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
				pixels[width * i + k] = newColor;
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		long end = System.currentTimeMillis();
		Log.d("may", "used time=" + (end - start));
		return bitmap;
	}

	public static Bitmap createHalfToneBitmap(Bitmap bitmap, Bitmap texture) {
		HalfToneFilter filter = new HalfToneFilter(bitmap, texture);
		ImageData data = filter.process();
		return data.getDstBitmap();
	}

	public static Bitmap createBitmapWithWatermark(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	public static Bitmap createConfiguredBitmap(Bitmap bitmap,
			BitmapDisplayConfig config) {
		return Bitmap.createScaledBitmap(bitmap, config.getPhotoType()
				.getWidth(), config.getPhotoType().getHeight(), true);
	}

	/**
	 * Ice Bitmap
	 * 
	 * @param bmp
	 * @return
	 */
	public static Bitmap ice(Bitmap bmp) {

		int width = bmp.getWidth();
		int height = bmp.getHeight();

		int dst[] = new int[width * height];
		bmp.getPixels(dst, 0, width, 0, 0, width, height);

		int R, G, B, pixel;
		int pos, pixColor;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pos = y * width + x;
				pixColor = dst[pos]; // 获取图片当前点的像素值

				R = Color.red(pixColor); // 获取RGB三原色
				G = Color.green(pixColor);
				B = Color.blue(pixColor);

				pixel = R - G - B;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				R = pixel; // 计算后重置R值，以下类同

				pixel = G - B - R;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				pixel = B - R - G;
				pixel = pixel * 3 / 2;
				if (pixel < 0)
					pixel = -pixel;
				if (pixel > 255)
					pixel = 255;
				B = pixel;
				dst[pos] = Color.rgb(R, G, B); // 重置当前点的像素值
			} // x
		} // y
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(dst, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 将Bitmap转换成指定大小
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

	/**
	 * 保存图片为PNG
	 * 
	 * @param bitmap
	 * @param name
	 */
	public static void saveAsPNG(Bitmap bitmap, String name) {
		File file = new File(name);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片为JPEG
	 * 
	 * @param bitmap
	 * @param path
	 */
	public static void saveAsJPGE(Bitmap bitmap, String path) {
		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图片反转
	 * 
	 * @param bm
	 * @param flag
	 *            0为水平反转，1为垂直反转
	 * @return
	 */
	public static Bitmap reverseBitmap(Bitmap bmp, int flag) {
		float[] floats = null;
		switch (flag) {
		case 0: // 水平反转
			floats = new float[] { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
			break;
		case 1: // 垂直反转
			floats = new float[] { 1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f };
			break;
		}

		if (floats != null) {
			Matrix matrix = new Matrix();
			matrix.setValues(floats);
			return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
					bmp.getHeight(), matrix, true);
		}

		return null;
	}

	/**
	 * 获得圆角图片的方法
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 获得带倒影的图片方法
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap raw) {
		Bitmap bitmap = raw.copy(Bitmap.Config.RGB_565, true);
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}

	private static int[] getGray(int[] pixels, int width, int height) {
		int gray[] = new int[width * height];
		for (int i = 0; i < width - 1; i++) {
			for (int j = 0; j < height - 1; j++) {
				int index = width * j + i;
				int rgba = pixels[index];
				int g = ((rgba & 0x00FF0000) >> 16) * 3
						+ ((rgba & 0x0000FF00) >> 8) * 6
						+ ((rgba & 0x000000FF)) * 1;
				gray[index] = g / 10;
			}
		}
		return gray;
	}

	private static int[] getInverse(int[] gray) {
		int[] inverse = new int[gray.length];

		for (int i = 0, size = gray.length; i < size; i++) {
			inverse[i] = 255 - gray[i];
		}
		return inverse;
	}

	private static int[] guassBlur(int[] inverse, int width, int height) {
		int[] guassBlur = new int[inverse.length];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int temp = width * (j) + (i);
				if ((i == 0) || (i == width - 1) || (j == 0)
						|| (j == height - 1)) {
					guassBlur[temp] = 0;
				} else {
					int i0 = width * (j - 1) + (i - 1);
					int i1 = width * (j - 1) + (i);
					int i2 = width * (j - 1) + (i + 1);
					int i3 = width * (j) + (i - 1);
					int i4 = width * (j) + (i);
					int i5 = width * (j) + (i + 1);
					int i6 = width * (j + 1) + (i - 1);
					int i7 = width * (j + 1) + (i);
					int i8 = width * (j + 1) + (i + 1);

					int sum = inverse[i0] + 2 * inverse[i1] + inverse[i2] + 2
							* inverse[i3] + 4 * inverse[i4] + 2 * inverse[i5]
							+ inverse[i6] + 2 * inverse[i7] + inverse[i8];

					sum /= 16;

					guassBlur[temp] = sum;
				}
			}
		}
		return guassBlur;
	}

	private static int[] deceasecolorCompound(int[] guassBlur, int[] gray,
			int width, int height) {
		int a, b, temp;
		float ex;
		int[] output = new int[guassBlur.length];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int index = j * width + i;
				b = guassBlur[index];
				a = gray[index];

				temp = a + a * b / (256 - b);
				ex = temp * temp * 1.0f / 255 / 255;
				temp = (int) (temp * ex);

				a = Math.min(temp, 255);

				output[index] = a;
			}
		}
		return output;
	}

	private static Bitmap create(int[] pixels, int[] output, int width,
			int height) {
		for (int i = 0, size = pixels.length; i < size; i++) {
			int gray = output[i];
			int pixel = (pixels[i] & 0xff000000) | (gray << 16) | (gray << 8)
					| gray;// 注意加上原图的 alpha通道

			output[i] = pixel;
		}

		return Bitmap.createBitmap(output, width, height, Config.ARGB_8888);
	}

	public static Bitmap createPencilBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		int[] gray = getGray(pixels, width, height);
		int[] inverse = getInverse(gray);

		int[] guassBlur = guassBlur(inverse, width, height);

		int[] output = deceasecolorCompound(guassBlur, gray, width, height);

		return create(pixels, output, width, height);
	}

	/**
	 * @param bitmap
	 * @return
	 */
	public static Bitmap anaglyphBitmap(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		// bitmap = toGray(bitmap);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = height - 1; i > 0; i--) {
			for (int j = width - 1; j > 0; j--) {
				int pixel = pixels[i * width + j];
				int leftUpPixel = pixels[(i - 1) * width + j - 1];
				int r = (pixel & 0x00ff0000) >> 16;
				int g = (pixel & 0x0000ff00) >> 8;
				int b = (pixel & 0x000000ff);

				int leftUpR = (leftUpPixel & 0x00ff0000) >> 16;
				int leftUpG = (leftUpPixel & 0x0000ff00) >> 8;
				int leftUpB = (leftUpPixel & 0x000000ff);

				r = r - leftUpR;
				g = g - leftUpG;
				b = b - leftUpB;

				int maxDiff = r;
				if (Math.abs(maxDiff) < Math.abs(g)) {
					maxDiff = g;
				}

				if (Math.abs(maxDiff) < Math.abs(b)) {
					maxDiff = b;
				}

				int gray = maxDiff + 128;
				if (gray > 255)
					gray = 255;
				if (gray < 0)
					gray = 0;

				pixels[i * width + j] = (pixel & 0xff000000) + (gray << 16)
						+ (gray << 8) + gray;
			}
		}
		return Bitmap.createBitmap(pixels, width, height, Config.ARGB_8888);
	}

	/**
	 * @param bmpOriginal
	 * @return
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0); // ��ɫ
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/**
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * @param path
	 * @return
	 */
	public static Bitmap readBitMap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.ARGB_8888;
		Bitmap bm = BitmapFactory.decodeFile(path, options);
		return bm;
	}

	/**
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidht = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}

	/**
	 * @param bitmap
	 * @return
	 */
	public static Bitmap brightContrastBitmap(Bitmap bitmap) {
		BrightContrastFilter brightContrastFilter = new BrightContrastFilter(
				bitmap);
		ImageData image = brightContrastFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap comicBitmap(Bitmap bmp) {
		ComicFilter comicFilter = new ComicFilter(bmp);
		ImageData image = comicFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap featherBitmap(Bitmap bmp) {
		FeatherFilter featherFilter = new FeatherFilter(bmp);
		ImageData image = featherFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @param angle
	 * @return
	 */
	public static Bitmap filmBitmap(Bitmap bmp, float angle) {
		FilmFilter filmFilter = new FilmFilter(bmp, angle);
		ImageData image = filmFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap gaussianBlur(Bitmap bmp) {
		GaussianBlurFilter blurFilter = new GaussianBlurFilter(bmp);
		ImageData image = blurFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap glowingEdge(Bitmap bmp) {
		GlowingEdgeFilter edgeFilter = new GlowingEdgeFilter(bmp);
		ImageData image = edgeFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap iceEffect(Bitmap bmp) {
		IceFilter iceFilter = new IceFilter(bmp);
		ImageData image = iceFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap lomo(Bitmap bmp) {
		LomoFilter lomoFilter = new LomoFilter(bmp);
		ImageData image = lomoFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap softGlow(Bitmap bmp) {
		SoftGlowFilter softGlowFilter = new SoftGlowFilter(bmp);
		ImageData image = softGlowFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap vignette(Bitmap bmp) {
		VignetteFilter vignetteFilter = new VignetteFilter(bmp);
		ImageData image = vignetteFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createOilPaintingBitmap(Bitmap bitmap) {
		OilPaintingFilter oilPaintingFilter = new OilPaintingFilter(bitmap);
		ImageData imageData = oilPaintingFilter.process();
		return imageData.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap noise(Bitmap bmp) {
		NoiseFilter noiseFilter = new NoiseFilter(bmp);
		ImageData image = noiseFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap molten(Bitmap bmp) {
		MoltenFilter moltenFilter = new MoltenFilter(bmp);
		ImageData image = moltenFilter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap meanShift(Bitmap bmp) {
		MeanShiftFilter filter = new MeanShiftFilter(bmp);
		ImageData image = filter.process();
		return image.getDstBitmap();
	}

	/**
	 * @param bmp
	 * @return
	 */
	public static Bitmap swirl(Bitmap bmp) {
		SwirlFilter filter = new SwirlFilter(bmp);
		ImageData image = filter.process();
		return image.getDstBitmap();
	}
}
