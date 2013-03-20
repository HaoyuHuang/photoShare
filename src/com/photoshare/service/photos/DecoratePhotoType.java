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
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.oldRemeber(raw);
		}
	},
	REFLECTION() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.createReflectionImageWithOrigin(raw);
		}
	},
	ICE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.iceEffect(raw);
		}

	},

	LOMO() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.lomo(raw);
		}

	},

	FILM() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.filmBitmap(raw, 45f);
		}

	},

	FEATHER() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.featherBitmap(raw);
		}

	},

	GLOWINGEDGE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.glowingEdge(raw);
		}
	},

	SOFTGLOW() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.softGlow(raw);
		}
	},

	MOLTEN() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.molten(raw);
		}
	},

	VIGNETTE() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
		}

		public Bitmap Decorate(Bitmap raw) {
			// TODO Auto-generated method stub
			return PhotoFactory.vignette(raw);
		}
	},

	GAUSSIANBLUR() {

		public int getImageId() {
			// TODO Auto-generated method stub
			return R.drawable.icon;
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
