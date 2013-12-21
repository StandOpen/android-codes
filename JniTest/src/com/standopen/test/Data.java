package com.standopen.test;

import android.util.Log;

public class Data {

	static {
		System.loadLibrary("hello");
	}
	
	public native void callhellofromjava();
	public native void calladd();
	public native void callprintstring();
	
	public void hellofromc()
	{
		Log.i("info", "void");
	}
	
	public int add(int x,int y)
	{
		Log.i("info int", x+"");
		return x+y;
	}
	
	
	public void printstring(String s)
	{
		Log.i("info", s);
	}

}
