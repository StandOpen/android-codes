package standopen.zhendong;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;

public class ZhenDong_01Activity extends Activity {
	Vibrator vibrator;   
	/** Called when the activity is first created. */   
	@Override   
	public void onCreate(Bundle savedInstanceState) {   
	super.onCreate(savedInstanceState);   
	setContentView(R.layout.main); 
	vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); 
	}   
	@Override   
	protected void onStop() {  
		System.out.print("fnkjsafks");
	if(null!=vibrator){   
	vibrator.cancel();  
	System.out.print("fnkjsafks");
	}   
	super.onStop();   
	}   
	@Override   
	public boolean onTouchEvent(MotionEvent event) {   
	if(event.getAction() == MotionEvent.ACTION_DOWN){   
	  
	long[] pattern = {800, 50, 400, 30}; // OFF/ON/OFF/ON...   
	vibrator.vibrate(pattern, -1);
	//-1不重复，非-1为从pattern的指定下标开始重复   
	}   
	return super.onTouchEvent(event);   
	}   
}