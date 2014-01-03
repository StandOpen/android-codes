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
import java.util.List;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.slidingmenu.R;
import com.example.slidingmenu.util.IChangeFragment;

public class LeftFragment extends Fragment implements OnItemClickListener{
	
	private static final String TAG = "LeftFragment";
	
	private ListView mListView;
	
	private MyAdapter myAdapter;
	private List<String> data = new ArrayList<String>();
	private  FragmentManager mFragmentManager;
	public LeftFragment(FragmentManager fragmentManager){
		mFragmentManager = fragmentManager;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.left, null);
		mListView = (ListView) view.findViewById(R.id.left_listview);
		
		data.add(getResources().getString(R.string.tab_news));
		data.add(getResources().getString(R.string.tab_local_news));
		data.add(getResources().getString(R.string.tab_ties));
		data.add(getResources().getString(R.string.tab_pics));
		data.add(getResources().getString(R.string.tab_ugc));
		data.add(getResources().getString(R.string.tab_vote)) ;
		data.add(getResources().getString(R.string.tab_mirco)) ;
		
		myAdapter = new MyAdapter(data);
		mListView.setAdapter(myAdapter);
		mListView.setOnItemClickListener(this);
		myAdapter.setSelectPosition(0);
		
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private class MyAdapter extends BaseAdapter{

		private List<String> data;
		
		private int selectPosition;
		
		
		
		MyAdapter (List<String> list){
			this.data = list;
			
		}
		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void setSelectPosition(int position){
			selectPosition = position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = LayoutInflater.from(getActivity()).inflate(R.layout.left_list, null);
			TextView textView = (TextView) row.findViewById(R.id.left_list_text);
			textView.setText(data.get(position));
			ImageView img = (ImageView) row.findViewById(R.id.left_list_image);
			switch(position){
			case 0 :
				img.setBackgroundResource(R.drawable.biz_navigation_tab_news);
				break;
			case 1 :
				img.setBackgroundResource(R.drawable.biz_navigation_tab_local_news);
				break;
			case 2 :
				img.setBackgroundResource(R.drawable.biz_navigation_tab_ties);
				break;
			case 3 :
				img.setBackgroundResource(R.drawable.biz_navigation_tab_pics);
				break;
			case 4 :
				img.setBackgroundResource(R.drawable.biz_navigation_tab_ugc);
				break;
			case 5 :
				img.setBackgroundResource(R.drawable.biz_navigation_tab_vote);
				break;
			case 6 :
				img.setBackgroundResource(R.drawable.biz_navigation_tab_micro);
				break;
			default:
					break;
			}
			
			if(position == selectPosition){
				row.setBackgroundResource(R.drawable.biz_navigation_tab_bg_pressed);
				textView.setSelected(true);
			}
			return row;
		}
		
	}
	
	private IChangeFragment iChangeFragment;
	
	public void setChangeFragmentListener(IChangeFragment listener){
		iChangeFragment = listener;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
//		View childView = null;
//		View text = null;
//		int length = mListView.getChildCount();
//		for(int i = 0,pos = 0; i <length; i++,pos++){
//			childView = mListView.getChildAt(i);
//			text  = childView.findViewById(R.id.left_list_text);
//			if(pos == position){
//				text.setSelected(true);
//			}
//		}
		
		if(iChangeFragment != null){
			iChangeFragment.changeFragment(position);
		}
		
		myAdapter.setSelectPosition(position);
		myAdapter.notifyDataSetChanged();
		
//		FragmentTransaction t = mFragmentManager
//				.beginTransaction();
//				LocalFragment leftFragment = new LocalFragment();
//				t.replace(R.id.center_frame, leftFragment);
//				t.commit();
	}

}
