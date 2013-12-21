package helper;

import android.app.Activity;
import android.content.Intent;

public class Test {
	public void start(Activity a, String ms) {
		// TODO Auto-generated method stub
		String start = ms;
		Class c=null;
		try {
			c=Class.forName(start);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		a.startActivity(new Intent(a,c));		
		
	}
}
