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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "怀旧";
		}
	},
	// REFLECTION() {
	//
	// public int getImageId() {
	// // TODO Auto-generated method stub
	// return R.drawable.reflectionlogo;
	// }
	//
	// public Bitmap Decorate(DecoratePhotoWrapper raw) {
	// // TODO Auto-generated method stub
	// return PhotoFactory.createReflectionImageWithOrigin(raw.getRaw());
	// }
	// },
	// ICE() {
	//
	// public int getImageId() {
	// // TODO Auto-generated method stub
	// return R.drawable.icelogo;
	// }
	//
	// public Bitmap Decorate(DecoratePhotoWrapper raw) {
	// // TODO Auto-generated method stub
	// return PhotoFactory.ice(raw.getRaw());
	// }
	//
	// },
	FEATHER() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.featherlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.featherBitmap(raw.getRaw());
		}

		public String getDescription() {
			// TODO Auto-generated method stub
			return "羽化";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "浮雕";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "素描";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "油画";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "办调色";
		}

	},
	// MEANSHIFT() {
	//
	// public int getImageId() {
	// // TODO Auto-generated method stub
	// return R.drawable.meanlogo;
	// }
	//
	// public Bitmap Decorate(DecoratePhotoWrapper raw) {
	// // TODO Auto-generated method stub
	// return PhotoFactory.meanShift(raw.getRaw());
	// }
	//
	// },

	// SWIRL() {
	//
	// public int getImageId() {
	// // TODO Auto-generated method stub
	// return R.drawable.swirllogo;
	// }
	//
	// public Bitmap Decorate(DecoratePhotoWrapper raw) {
	// // TODO Auto-generated method stub
	// return PhotoFactory.swirl(raw.getRaw());
	// }
	//
	// public String getDescription() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// },

	GLOWINGEDGE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.glowingedgelogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.glowingEdge(raw.getRaw());
		}

		public String getDescription() {
			// TODO Auto-generated method stub
			return "边缘亮化";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "灰度";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "噪音";
		}

	},
	//
	// ROUNDEDCORNER() {
	//
	// public int getImageId() {
	// // TODO Auto-generated method stub
	// return R.drawable.roundedcornerlogo;
	// }
	//
	// public Bitmap Decorate(DecoratePhotoWrapper raw) {
	// // TODO Auto-generated method stub
	// return PhotoFactory.getRoundedCornerBitmap(raw.getRaw(), 50f);
	// }
	// },

	BRIGHTCONTRAST() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.brightcontrastlogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.brightContrastBitmap(raw.getRaw());
		}

		public String getDescription() {
			// TODO Auto-generated method stub
			return "明亮";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "漫画";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "lomo";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "电影";
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

		public String getDescription() {
			// TODO Auto-generated method stub
			return "柔和";
		}
	},

	// MOLTEN() {
	//
	// public int getImageId() {
	// // TODO Auto-generated method stub
	// return R.drawable.moltenlogo;
	// }
	//
	// public Bitmap Decorate(DecoratePhotoWrapper raw) {
	// // TODO Auto-generated method stub
	// return PhotoFactory.molten(raw.getRaw());
	// }
	// },

	VIGNETTE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.vignettelogo;
		}

		public Bitmap Decorate(DecoratePhotoWrapper raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.vignette(raw.getRaw());
		}

		public String getDescription() {
			// TODO Auto-generated method stub
			return "浪漫";
		}
	},

	// GAUSSIANBLUR() {
	//
	// public int getImageId() {
	// // TODO Auto-generated method stub
	// return R.drawable.gaussianblurlogo;
	// }
	//
	// public Bitmap Decorate(DecoratePhotoWrapper raw) {
	// // TODO Auto-generated method stub
	// return PhotoFactory.gaussianBlur(raw.getRaw());
	// }
	//
	// public String getDescription() {
	// // TODO Auto-generated method stub
	// return "模糊";
	// };
	// }
}

interface DecoratePhotoAction {
	public int getImageId();

	public Bitmap Decorate(DecoratePhotoWrapper raw);

	public String getDescription();
}
