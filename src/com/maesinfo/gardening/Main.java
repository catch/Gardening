package com.maesinfo.gardening;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.view.tab.AnimationTabHost;

public class Main extends TabActivity implements OnTabChangeListener {
	private FrameLayout frameLayout;

	private AnimationTabHost mTabHost;
	private TabWidget mTabWidget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		mTabHost = (AnimationTabHost) findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost.setOnTabChangedListener(this);

		initTabs();
		customTabs();
	}

	private void initTabs() {
		String titleBrowse = Main.this.getString(R.string.main_tab_browse);
		String titleSearch = Main.this.getString(R.string.main_tab_search);
		String titleFavorites = Main.this
				.getString(R.string.main_tab_favorites);
		String titleFeedback = Main.this.getString(R.string.main_tab_feedback);
		setIndicator(R.drawable.browse_selected, titleBrowse, 0, new Intent(this,
				BrowseActivity.class));
		setIndicator(R.drawable.search_selected, titleSearch, 1, new Intent(this,
				SearchActivity.class));
		setIndicator(R.drawable.favorite_selected, titleFavorites, 2, new Intent(this,
				FavoritesActivity.class));
		setIndicator(R.drawable.feedback_selected, titleFeedback, 3, new Intent(this,
				FeedbackActivity.class));

		mTabHost.setOpenAnimation(true);
	}

	private void customTabs() {
		/* 对Tab标签的定制 */
		mTabWidget = mTabHost.getTabWidget();
		for (int i = 0; i < mTabWidget.getChildCount(); i++) {
			/* 得到每个标签的视图 */
			View view = mTabWidget.getChildAt(i);
			TextView title = (TextView) view
					.findViewById(R.id.main_activity_tab_text);

			/* 设置每个标签的背景 */
			if (mTabHost.getCurrentTab() == i) {
				view.setBackgroundResource(R.drawable.number_bg_pressed);
				title.setTextColor(Color.rgb(255, 255, 255));
			} else {
				view.setBackgroundResource(R.drawable.number_bg);
				title.setTextColor(Color.rgb(0,0,0));
			}
		}
	}

	private void setIndicator(int icon, String title, int tabId, Intent intent) {

		View localView = LayoutInflater.from(this.mTabHost.getContext())
				.inflate(R.layout.tab_widget_view, null);
		((ImageView) localView.findViewById(R.id.main_activity_tab_image))
				.setBackgroundResource(icon);
		((TextView) localView.findViewById(R.id.main_activity_tab_text))
				.setText(title);

		String str = String.valueOf(tabId);

		TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(str)
				.setIndicator(localView).setContent(intent);
		mTabHost.addTab(localTabSpec);
	}

	@Override
	public void onTabChanged(String tabId) {
		int tabID = Integer.valueOf(tabId);
		for (int i = 0; i < mTabWidget.getChildCount(); i++) {
			View view = mTabWidget.getChildAt(i);
			TextView title = (TextView) view
					.findViewById(R.id.main_activity_tab_text);
			if (tabID == i) {
				view.setBackgroundResource(R.drawable.number_bg_pressed);
				title.setTextColor(Color.rgb(255, 255, 255));
			} else {
				view.setBackgroundResource(R.drawable.number_bg);
				title.setTextColor(Color.rgb(0,0,0));
			}
		}
	}
}