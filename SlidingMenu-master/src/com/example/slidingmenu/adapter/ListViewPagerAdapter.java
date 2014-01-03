package com.example.slidingmenu.adapter;

import java.util.Arrays;

import com.example.slidingmenu.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewPagerAdapter extends PagerAdapter {
	
	private static final String[] STRINGS = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance",
		"Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
		"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
		"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
		"Allgauer Emmentaler" };
	
	private OnRefreshListener<ListView> listener;
	public ListViewPagerAdapter( OnRefreshListener<ListView> listener){
		this.listener = listener;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Context context = container.getContext();

		PullToRefreshListView plv = (PullToRefreshListView) LayoutInflater.from(context).inflate(
				R.layout.layout_listview_in_viewpager, container, false);

		ListAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
				Arrays.asList(STRINGS));
		plv.setAdapter(adapter);

		plv.setOnRefreshListener(listener);

		// Now just add ListView to ViewPager and return it
		container.addView(plv, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		return plv;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public int getCount() {
		return 7;
	}
}
