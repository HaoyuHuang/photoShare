package com.photoshare.service.photos.views;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.photoshare.tabHost.R;
import com.photoshare.view.OnItemClickListener;
import com.photoshare.view.PopMenu;

public class FeedsItemPopMenu implements OnItemClickListener {
	private Context context;

	private PopMenu popMenu;

	public FeedsItemPopMenu(Context context) {
		super();
		this.context = context;
	}

	public void init() {
		popMenu = new PopMenu(context);
		popMenu.setOnItemClickListener(this);
		popMenu.addItems(new String[] { getDecorateText(), getCropText(),
				getSaveText() });
	}

	public void onItemClick(int index) {
		String text = "";
		switch (index) {
		case 0:
			if (itemMenuClickListener != null) {
				itemMenuClickListener.onDecorateImageClick();
			}
			text = "decorate";
			break;
		case 1:
			if (itemMenuClickListener != null) {
				itemMenuClickListener.onCropImageClick();
			}
			text = "crop";
			break;
		case 2:
			if (itemMenuClickListener != null) {
				itemMenuClickListener.onSavetoImageStoreClick();
			}
			text = "save";
			break;
		}
		Toast.makeText(context, "item clicked " + text + "!",
				Toast.LENGTH_SHORT).show();
	}

	public void display(View parent) {
		popMenu.showAsDropDown(parent);
	}

	private String getDecorateText() {
		return context.getString(R.string.popMenuDecorate);
	}

	private String getSaveText() {
		return context.getString(R.string.popMenuSave);
	}

	private String getCropText() {
		return context.getString(R.string.popMenuCrop);
	}

	private FeedsItemMenuClickListener itemMenuClickListener;

	public void registerItemMenuClickListener(
			FeedsItemMenuClickListener itemMenuClickListener) {
		this.itemMenuClickListener = itemMenuClickListener;
	}

	public interface FeedsItemMenuClickListener {
		public void onSavetoImageStoreClick();

		public void onDecorateImageClick();

		public void onCropImageClick();
	}

}
