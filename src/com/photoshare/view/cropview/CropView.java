package com.photoshare.view.cropview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CropView extends ImageView {
	
	Paint paint = new Paint();
	int width;
	int startY = 50;
	int height = startY + 300;
	
	public CropView(Context context, AttributeSet attrs){
		super(context, attrs);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
	}
	public CropView(Context context){
		
		 super(context);
		 paint.setColor(Color.WHITE);
		 paint.setStyle(Paint.Style.STROKE);
		 paint.setStrokeWidth(3);
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public void setStartY(int startY){
		this.startY = startY;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawLine(0, startY, 0, height, paint);
		canvas.drawLine(0, startY, width, startY, paint);
		canvas.drawLine(width, startY, width, height, paint);
		canvas.drawLine(0, height, width, height, paint);
		
	}

}
