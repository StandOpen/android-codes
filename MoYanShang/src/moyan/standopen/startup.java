package moyan.standopen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class startup extends Activity {
    /** Called when the activity is first created. */
	
	
	private Handler handler = new Handler();  
	  
	private Runnable runnable = new Runnable() {  
	  
	    public void run() {  
	    	Intent intent=new Intent();
			intent.setClass(startup.this,index.class);
			startActivity(intent);
			handler.removeCallbacks(runnable);
			finish();
			  
	    }  
	  
	};  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);   
                requestWindowFeature(Window.FEATURE_NO_TITLE);  
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.startup);
        handler.postDelayed(runnable,2000);
    }
}