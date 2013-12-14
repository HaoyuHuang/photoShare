package com.photoshare.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.photoshare.R;
import com.photoshare.service.MainService;

public class LogoActivity extends Activity
{

	ImageView IV = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.logo);

		IV = (ImageView) this.findViewById(R.id.animation_logo_id);

		this.startService(new Intent(this, MainService.class));

		Animation animation = new AlphaAnimation(0.1f, 1.0f);

		animation.setDuration(1000);
		animation.setAnimationListener(new AnimationListener()
		{

			public void onAnimationStart(Animation animation)
			{

			}

			public void onAnimationRepeat(Animation animation)
			{

			}

			public void onAnimationEnd(Animation animation)
			{
				Intent intent = new Intent(LogoActivity.this,
						LogInActivity.class);
				LogoActivity.this.startActivity(intent);

			}
		});

		IV.setAnimation(animation);

	}

}
