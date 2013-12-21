package com.standopen.hello;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity {

	private TextView result = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//result = (TextView)findViewById(R.id.result);
		//result.setText(hello()+getstr("122334555566"));
		//Log.i("x+y", add(10,20)+"");
		
		int[] arr = {1,2,3,4,5,6,7,8,9};
		intMethod(arr);
		
		
	}

	static {
		System.loadLibrary("hello");
	}
	
	public native int add(int x,int y);
	
	public native String getstr(String str);
	
	public native int[] intMethod(int[] iNum);
	public native String hello();

}
