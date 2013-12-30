package com.smit;

import java.util.ArrayList;
import java.util.List;

import com.smit.util.ListAdapter;
import com.smit.util.MyListView;
import com.smit.util.MyListView.OnRefreshListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity {

	private ArrayList<String> lists;
	MyListView listView;
	private ListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		listView = (MyListView) findViewById(R.id.listView);
	
		getData();
	}

	public void getData() {

		lists = new ArrayList<String>();
		for(int i=0;i<10;i++)
		{
		lists.add("this is a");
		lists.add("this is b");
		lists.add("this is c");
		}
		adapter = new ListAdapter(this, lists);
		listView.setAdapter(adapter);
		// 调用MyListView中自定义接口
		listView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				// TODO Auto-generated method stub
				// new Thread(){
				// public void run(){
				// try {
				// Thread.sleep(1000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// lists.add("刷新后添加内容!");
				// }
				//
				// }.start();

				new AsyncTask<Void, Void, Void>() {

					// 运行在UI线程中，在调用doInBackground()之前执行
					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}

					// 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						lists.add("刷新数据");
						return null;
					}

				}.execute();

			}
		});

	}

}
