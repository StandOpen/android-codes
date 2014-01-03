package com.example.slidingmenu.fragment;

import com.example.slidingmenu.R;
import com.example.slidingmenu.activity.SlidingActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LocalFragment extends BaseFragment {
	
	
	private View showLeft;
	private TextView mTopTitleView;
	private ImageView mTopBackView;
	private View showRight;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.local, null);
		showLeft = (View) view.findViewById(R.id.head_layout_showLeft);
		showRight = (View) view.findViewById(R.id.head_layout_showRight);
		mTopTitleView = (TextView) showLeft.findViewById(R.id.head_layout_text);
		mTopTitleView.setText(getString(R.string.tab_local_news));
		mTopBackView = (ImageView) showLeft.findViewById(R.id.head_layout_back);
		mTopBackView.setBackgroundResource(R.drawable.biz_local_news_main_back_normal);
		return view;
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
}
