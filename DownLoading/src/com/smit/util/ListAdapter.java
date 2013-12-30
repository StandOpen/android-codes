package com.smit.util;

import java.util.List;

import com.smit.MainActivity;
import com.smit.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListAdapter extends BaseAdapter {

	private Context mcontext;
	private List<String> mLists;
	
	public ListAdapter(Context context,List<String> lists){
		this.mcontext=context;
		this.mLists=lists;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return mLists.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}


	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		int itemLayout =R.layout.list_item6;
		LinearLayout layout = new LinearLayout(mcontext);
		LayoutInflater vi = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vi.inflate(itemLayout, layout,true);
		final TextView content = (TextView) layout.findViewById(R.id.content);
		content.setText(mLists.get(arg0));
		layout.setClickable(true);
		content.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("infofjlasjfldajflajdsfjdas", "fjsaljflaskjflkds");
				Toast.makeText(mcontext, content.getText(), Toast.LENGTH_SHORT).show();
			}
		});
		return layout;
	}




}
