/**
 * 
 */
package com.photoshare.service.photos;

import android.graphics.Bitmap;

import com.photoshare.service.photos.factory.PhotoFactory;
import com.photoshare.tabHost.R;

/**
 * @author czj_yy
 * 
 */
public enum DecoratePhotoType implements DecoratePhotoAction {
	OLDMEMORY() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.oldmemorylogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.oldRemeber(raw);
		}
	},
	REFLECTION() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.reflectionlogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.createReflectionImageWithOrigin(raw);
		}
	},
	ICE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icelogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.iceEffect(raw);
		}

	},
	LOMO() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.lomologo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.lomo(raw);
		}

	},
	FILM() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.filmlogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.filmBitmap(raw, 45f);
		}

	},

	FEATHER() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.featherlogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.featherBitmap(raw);
		}

	},

	GLOWINGEDGE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.glowingedgelogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.glowingEdge(raw);
		}
	},
	GREY() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.greylogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.toGrayscale(raw);
		}

	},

	NOISE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.noiselogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.noise(raw);
		}

	},

	ROUNDEDCORNER() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.roundedcornerlogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.getRoundedCornerBitmap(raw, 50f);
		}
	},

	BRIGHTCONTRAST() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.brightcontrastlogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.brightContrastBitmap(raw);
		}
	},

	COMIC() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.comiclogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.comicBitmap(raw);
		}
	},

	SOFTGLOW() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.softglowlogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.softGlow(raw);
		}
	},

	MOLTEN() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.moltenlogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.molten(raw);
		}
	},

	VIGNETTE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.vignettelogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.vignette(raw);
		}
	},

	GAUSSIANBLUR() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.gaussianblurlogo;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.gaussianBlur(raw);
		}
	};

}

interface DecoratePhotoAction {
	public int getImageId();

	public Bitmap Decorate(Bitmap raw);
}
