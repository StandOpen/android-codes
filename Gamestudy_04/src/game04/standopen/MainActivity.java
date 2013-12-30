package game04.standopen;

import android.app.Activity;
import android.os.Bundle; 
import android.os.SystemClock;
import android.provider.Settings.System;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    public static Activity instance;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance =this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //闅愬幓鐢垫睜绛夊浘鏍囧拰涓�垏淇グ閮ㄥ垎锛堢姸鎬佹爮閮ㄥ垎锛�
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 闅愬幓鏍囬鏍忥紙绋嬪簭鐨勫悕瀛楋級
        setContentView(new MySurfaceView(this)); 
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());  
		
	}
    
}