/**
 * 
 */
package com.photoshare.camera;

import java.util.Arrays;
import java.util.Collection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.photoshare.service.photos.DecoratePhotoType;
import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.service.photos.factory.PhotoFactory;
import com.photoshare.tabHost.R;

/**
 * @author czj_yy
 * 
 */
public class PhotoFilterView {

	private Context context;
	private View baseView;
	private Bitmap photo;
	private Bitmap decoratedPhoto;
	private BitmapDisplayConfig config = BitmapDisplayConfig.SMALL;

	/** Crop View */
	private ImageView photoView;

	private LinearLayout photoToolsView;

	/**
	 * @param context
	 * @param baseView
	 */
	public PhotoFilterView(Context context, View baseView) {
		super();
		this.context = context;
		this.baseView = baseView;
	}

	public PhotoFilterView() {

	}

	public void applyView() {
		photoToolsView = (LinearLayout) baseView
				.findViewById(R.id.editPhotoToolsGallery);
		photoView = (ImageView) baseView.findViewById(R.id.editPhoto);
		applyImageTool();
	}

	private void applyImageTool() {
		Collection<DecoratePhotoType> values = Arrays.asList(DecoratePhotoType
				.values());
		for (DecoratePhotoType type : values) {
			photoToolsView.addView(insertImage(type));
		}
	}

	public void destroy() {
		photoToolsView.destroyDrawingCache();
		photoToolsView.removeAllViewsInLayout();
	}

	public void disappearPhotoTools() {
		photoToolsView.setVisibility(View.GONE);
	}

	public void displayPhotoTools() {
		photoToolsView.setVisibility(View.VISIBLE);
	}

	private View insertImage(final DecoratePhotoType type) {
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(220, 220));
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.VERTICAL);

		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new LayoutParams(180, 180));
		imageView.setImageDrawable(PhotoFactory.getDrawable(context,
				type.getImageId(), config));
		// imageView.setBackgroundResource(type.getImageId());

		imageView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (photoFilterViewClickListener != null) {
					photoFilterViewClickListener.OnToolsClick(type, photo);
				}
			}
		});
		TextView textView = new TextView(context);
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		textView.setText(type.getDescription());
		textView.setTextColor(Color.BLACK);
		layout.addView(imageView);
		layout.addView(textView);
		return layout;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setBaseView(View baseView) {
		this.baseView = baseView;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
		photoView.setImageBitmap(photo);
		photoView.setVisibility(View.VISIBLE);
	}

	public void setPhoto(Drawable photo) {
		BitmapDrawable dP = (BitmapDrawable) photo;
		if (dP != null) {
			this.photo = dP.getBitmap();
			photoView.setImageBitmap(this.photo);
			photoView.setVisibility(View.VISIBLE);
		}
	}

	public void setDecoratedPhoto(Drawable photo) {
		BitmapDrawable dP = (BitmapDrawable) photo;
		if (dP != null) {
			this.decoratedPhoto = dP.getBitmap();
			photoView.setImageBitmap(dP.getBitmap());
			photoView.setVisibility(View.VISIBLE);
		}
	}

	private PhotoFilterViewClickListener photoFilterViewClickListener;

	public void registerPhotoFilterViewClickListener(
			PhotoFilterViewClickListener photoFilterViewClickListener) {
		this.photoFilterViewClickListener = photoFilterViewClickListener;
	}

	public interface PhotoFilterViewClickListener {
		public void OnSubmitClick();

		public void OnCancleClick();

		public void OnToolsClick(DecoratePhotoType type, Bitmap map);
	}
}
