/**
 * 
 */
package com.photoshare.service.photos;

import android.graphics.Bitmap;

import com.photoshare.service.photos.factory.DecoratePhotoWrapper;
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

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.oldRemeber(raw.getRaw());
		}
	},
	REFLECTION() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.reflectionlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.createReflectionImageWithOrigin(raw.getRaw());
		}
	},
	ICE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icelogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.createReflectionImageWithOrigin(raw.getRaw());
		}

	},
	LOMO() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.lomologo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.lomo(raw.getRaw());
		}

	},
	FILM() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.filmlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.filmBitmap(raw.getRaw(), 45f);
		}

	},

	FEATHER() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.featherlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.featherBitmap(raw.getRaw());
		}

	},

	ANAGLYPH() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.anaglyphlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.anaglyphBitmap(raw.getRaw());
		}
	},

	PENCIL() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.pencillogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.createPencilBitmap(raw.getRaw());
		}

	},

	OILPAINTING() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.oillogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.createOilPaintingBitmap(raw.getRaw());
		}

	},

	HALFTONE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.halftonelogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.createHalfToneBitmap(raw.getRaw(), raw
					.getTexture().getTexture());
		}

	},

	MEANSHIFT() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.meanlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.meanShift(raw.getRaw());
		}

	},

	SWIRL() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.swirllogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.swirl(raw.getRaw());
		}

	},

	GLOWINGEDGE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.glowingedgelogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.glowingEdge(raw.getRaw());
		}
	},
	GREY() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.greylogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.toGrayscale(raw.getRaw());
		}

	},

	NOISE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.noiselogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.noise(raw.getRaw());
		}

	},

	ROUNDEDCORNER() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.roundedcornerlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.getRoundedCornerBitmap(raw.getRaw(), 50f);
		}
	},

	BRIGHTCONTRAST() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.brightcontrastlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.brightContrastBitmap(raw.getRaw());
		}
	},

	COMIC() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.comiclogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.comicBitmap(raw.getRaw());
		}
	},

	SOFTGLOW() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.softglowlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.softGlow(raw.getRaw());
		}
	},

	MOLTEN() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.moltenlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.molten(raw.getRaw());
		}
	},

	VIGNETTE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.vignettelogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.vignette(raw.getRaw());
		}
	},

	GAUSSIANBLUR() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.gaussianblurlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.gaussianBlur(raw.getRaw());
		};
	}
}

interface DecoratePhotoAction {
	public int getImageId();

	public Bitmap Decorate(DecoratePhotoWrapper raw);
}
