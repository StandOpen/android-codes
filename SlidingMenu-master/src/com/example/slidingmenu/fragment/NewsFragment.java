/*
 * Copyright (C) 2012 yueyueniao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.slidingmenu.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.slidingmenu.R;
import com.example.slidingmenu.activity.SlidingActivity;
import com.example.slidingmenu.adapter.ListViewPagerAdapter;
import com.example.slidingmenu.adapter.ScrollingTabsAdapter;
import com.example.slidingmenu.view.ScrollableTabView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class NewsFragment extends Fragment implements OnRefreshListener<ListView>,ViewPager.OnPageChangeListener{
	private static final String TAG = "NewsFragment";

	private View showLeft;
	private View showRight;
	private TextView mTopTitleView;
	private ImageView mTopBackView;
	private MyAdapter mAdapter;
	private ViewPager mPager;
	private ArrayList<Fragment> pagerItemList =null;
	
	private ListViewPagerAdapter listViewPagerAdapter;
	private Activity mActivity;
	private 	ScrollableTabView mScrollableTabView;
	private ScrollingTabsAdapter mScrollingTabsAdapter;
	
	public NewsFragment (){}
	public NewsFragment (Activity activity){
		this.mActivity = activity;
	}

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "onCreate");
	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}



	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e(TAG, "onCreateView");
		View mView = inflater.inflate(R.layout.view_pager, null);
		
		showLeft = (View) mView.findViewById(R.id.head_layout_showLeft);
		showRight = (View) mView.findViewById(R.id.head_layout_showRight);
		
		mTopTitleView = (TextView) showLeft.findViewById(R.id.head_layout_text);
		mTopTitleView.setText(getString(R.string.tab_news));
		mTopBackView = (ImageView) showLeft.findViewById(R.id.head_layout_back);
		mTopBackView.setBackgroundResource(R.drawable.biz_news_main_back_normal);
		
		mPager = (ViewPager) mView.findViewById(R.id.vp_list);
		
		listViewPagerAdapter = new ListViewPagerAdapter(this);
		mPager.setAdapter(listViewPagerAdapter);
		
		pagerItemList = new ArrayList<Fragment>() ;
		pagerItemList.add(new Fragment());
		pagerItemList.add(new Fragment());
		pagerItemList.add(new Fragment());
		pagerItemList.add(new Fragment());
		pagerItemList.add(new Fragment());
		pagerItemList.add(new Fragment());
		pagerItemList.add(new Fragment());
		
//		PageFragment1 page1 = new PageFragment1();
//		PageFragment2 page2 = new PageFragment2();
//		pagerItemList.add(page1);
//		pagerItemList.add(page2);
//		mAdapter = new MyAdapter(getFragmentManager());
//		mPager.setAdapter(mAdapter);
		
		mPager.setOnPageChangeListener(this);
		initScrollableTabs(mView, mPager);
		return mView;
	}

    private void initScrollableTabs(View view ,ViewPager mViewPager){
    	mScrollableTabView  = (ScrollableTabView)view.findViewById(R.id.scrollabletabview);
    	mScrollingTabsAdapter = new ScrollingTabsAdapter(mActivity);
    	mScrollableTabView.setAdapter(mScrollingTabsAdapter);
    	mScrollableTabView.setViewPage(mViewPager);
    }
    
	public ViewPager getViewPage(){
		return mPager;
	}
    
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		showLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((SlidingActivity) getActivity()).showLeft();
			}
		});

		showRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((SlidingActivity) getActivity()).showRight();
			}
		});
	}

	public boolean isFirst() {
		Log.e(TAG, "isFirst: " + mPager.getCurrentItem() +", pagerItemList "+  pagerItemList.size());
		if (mPager.getCurrentItem() == 0)
			return true;
		else
			return false;
	}

	public boolean isEnd() {
		Log.e(TAG, "isEnd: " + mPager.getCurrentItem() +", pagerItemList "+  pagerItemList.size());
		if (mPager.getCurrentItem() == pagerItemList.size() - 1)
			return true;
		else
			return false;
	}

	public class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return pagerItemList.size();
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = null;
			if (position < pagerItemList.size())
				fragment = pagerItemList.get(position);
			else
				fragment = pagerItemList.get(0);

			return fragment;

		}
	}

	private MyPageChangeListener myPageChangeListener;

	public void setMyPageChangeListener(MyPageChangeListener l) {

		myPageChangeListener = l;

	}

	public interface MyPageChangeListener {
		public void onPageSelected(int position);
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		new GetDataTask(refreshView).execute();
	}
	
	private static class GetDataTask extends AsyncTask<Void, Void, Void> {

		PullToRefreshBase<?> mRefreshedView;

		public GetDataTask(PullToRefreshBase<?> refreshedView) {
			mRefreshedView = refreshedView;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mRefreshedView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	@Override
	public void onPageScrollStateChanged(int position) {
		
	}

	@Override
	public void onPageScrolled(int position, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		if (myPageChangeListener != null){
			myPageChangeListener.onPageSelected(position);
		}
		if(mScrollableTabView != null){
			mScrollableTabView.selectTab(position);
		}

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.e(TAG, "onDestroyView" );
		listViewPagerAdapter = null;
		pagerItemList.clear();
		pagerItemList = null;
		mScrollableTabView = null;
		mScrollingTabsAdapter = null;
		mActivity = null;
	}
	
	

}
