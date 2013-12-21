package com.standopen.test;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Data data = new Data();
		//data.callhellofromjava();
		//data.calladd();
		data.callprintstring();
	}



}
