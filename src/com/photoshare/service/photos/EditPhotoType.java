/**
 * 
 */
package com.photoshare.service.photos;

import android.graphics.Bitmap;

import com.photoshare.factory.PhotoFactory;
import com.photoshare.tabHost.R;

/**
 * @author czj_yy
 * 
 */
public enum EditPhotoType implements EditPhotoId {
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
	};

}

interface EditPhotoId {
	public int getImageId();

	public Bitmap Decorate(Bitmap raw);
}
