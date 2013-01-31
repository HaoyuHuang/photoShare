package com.photoshare.tabHost.tab;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.photoshare.command.Command;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.fragments.stacktrace.TraceElement;
import com.photoshare.fragments.stacktrace.TracePhase;
import com.photoshare.service.news.NewsBean;
import com.photoshare.service.news.view.NewsFragment;
import com.photoshare.tabHost.BaseActivity;
import com.photoshare.tabHost.R;

public class TabNewsActivity extends BaseActivity {

	private ArrayList<NewsBean> news;
	private NewsFragment nf;
	private boolean onPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_news_layout_holder);
		initFragment();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		onPause = true;
	}

	private void initFragment() {
		nf = (NewsFragment) getFragmentManager().findFragmentById(
				R.id.TabNewsLayoutHolderId);
		nf = NewsFragment.newInstance(R.id.TabNewsLayoutHolderId);
		nf.setNews(news);
		nf.setCanonicalTag(getNews());
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(NewsBean.KEY_NEWS, news);
		nf.setArguments(bundle);
		TraceElement element = new TraceElement(getNews(), bundle);
		stack.setCurrentPhase(TracePhase.NEWS);
		stack.forward(element);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.TabNewsLayoutHolderId, nf);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(NewsBean.KEY_NEWS)) {
				news = savedInstanceState
						.getParcelableArrayList(NewsBean.KEY_NEWS);
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (onPause) {
			stack.setCurrentPhase(TracePhase.NEWS);
			Bundle args = new Bundle();
			args.putParcelableArrayList(NewsBean.KEY_NEWS, news);
			Command.forwardTab((BaseFragment) getFragmentManager()
					.findFragmentById(R.id.TabNewsLayoutHolderId), getNews(),
					args);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if (outState != null) {
			outState.putParcelableArrayList(NewsBean.KEY_NEWS, news);
		}
	}

	private String getNews() {
		return getString(R.string.fnewsFragment);
	}

}
