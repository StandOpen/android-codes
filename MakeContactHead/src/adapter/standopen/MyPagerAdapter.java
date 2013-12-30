package adapter.standopen;

import java.util.ArrayList;

import contact.standopen.R;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MyPagerAdapter extends PagerAdapter {

	

	ArrayList<ImageInfo> data;
	Activity activity;
	LayoutParams params;

	public MyPagerAdapter(Activity activity, ArrayList<ImageInfo> data) {
		this.activity = activity;
		this.data = data;
	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int index) {
		Log.v("test", index + "index");

		View view = activity.getLayoutInflater().inflate(R.layout.grid, null);
		GridView gridView = (GridView) view.findViewById(R.id.gridView1);
		gridView.setNumColumns(2);
		gridView.setVerticalSpacing(10);
		gridView.setHorizontalSpacing(10);
		gridView.setAdapter(new BaseAdapter() {

			public int getCount() {
					return data.size();
			}

			public Object getItem(int position) {
				return position;
			}

			public long getItemId(int position) {
				return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				View item = LayoutInflater.from(activity).inflate(
						R.layout.grid_item, null);
				ImageView iv = (ImageView) item.findViewById(R.id.imageView1);
				iv.setImageResource((data.get(position)).imageId);
				TextView tv = (TextView) item.findViewById(R.id.msg);
				tv.setText((data.get(position)).imageMsg);
				return item;
			}
		});

		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			//	View view = arg1;
			//	arg1.setVisibility(View.INVISIBLE);

			//	params = new WindowManager.LayoutParams();
			
				return true;
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("number",arg2);
				//intent.setClass(activity, categorys.class);
				activity.startActivity(intent);
			}
		});
	
		((ViewPager) container).addView(view);

		return view;
	}
}
