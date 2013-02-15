package com.photoshare.view.cropview;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableObject implements Parcelable {

	private Bitmap bitmap;
	private String url;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int describeContents() {

		// TODO Auto-generated method stub
		return 0;

	}

	public void writeToParcel(Parcel dest, int flag) {

		// TODO Auto-generated method stub

		dest.writeValue(bitmap);
		dest.writeString(url);

	}

	public static final Parcelable.Creator CREATOR = new Creator() {

		public ParcelableObject createFromParcel(Parcel source) {

			// TODO Auto-generated method stub
			ParcelableObject parcelableObj = new ParcelableObject();
			parcelableObj.bitmap = (Bitmap) source
					.readValue(ParcelableObject.class.getClassLoader());
			parcelableObj.url = source.readString();
			return parcelableObj;

		}

		public ParcelableObject[] newArray(int size) {

			// TODO Auto-generated method stub
			return new ParcelableObject[size];

		}

	};
}