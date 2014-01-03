package com.renren.android.newsfeed;

import sliding.standopen.FlipperLayout.OnOpenListener;
import sliding.standopen.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;



public class NewsFeed {
	//private BaseApplication mApplication;
	private Context mContext;
	private Activity mActivity;
	private View mNewsFeed;
	private OnOpenListener mOnOpenListener;
	private ImageView more=null;
	public NewsFeed(Context context,
			Activity activity) {
		mContext = context;
		mActivity = activity;
		mNewsFeed = LayoutInflater.from(context).inflate(R.layout.main,null);
		Init();
	}
	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}

	public View getView() {
		//mDisplay.setSelection(0);
		return mNewsFeed;
	}
	
	public void Init()
	{
		more=(ImageView)mNewsFeed.findViewById(R.id.newsfeed_flip);
		more.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
	}

}
