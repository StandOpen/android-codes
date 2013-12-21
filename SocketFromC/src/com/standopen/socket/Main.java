package com.standopen.socket;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		SendMsg();
	}

	static {
		System.loadLibrary("socket");
	}
	
	public native String SendMsg();

}
