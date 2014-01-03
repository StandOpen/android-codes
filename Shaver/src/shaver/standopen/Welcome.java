package shaver.standopen;

import cn.domob.android.ads.DomobAdListener;
import cn.domob.android.ads.DomobAdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

public class Welcome extends Activity implements OnTouchListener,
OnGestureListener, OnDoubleTapListener,SensorEventListener{
	private RelativeLayout mylayout=null;
	private DomobAdView mAdview320x50;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);   
                requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.welcome);
        mylayout=(RelativeLayout)findViewById(R.id.welcomelayout);
        mylayout.setOnTouchListener(this);
        mAdview320x50 = (DomobAdView) findViewById(R.id.domobAdXML);
          mAdview320x50.setOnAdListener(new DomobAdListener() {
			
			public void onReceivedFreshAd(DomobAdView adview) {
				Log.i("DomobSDKDemo", "onReceivedFreshAd");
			}
			
			public void onFailedToReceiveFreshAd(DomobAdView adview) {
				Log.i("DomobSDKDemo", "onFailedToReceiveFreshAd");
			}
			
			public void onLandingPageOpening() {
				Log.i("DomobSDKDemo", "onLandingPageOpening");
			}
			
			public void onLandingPageClose() {
				Log.i("DomobSDKDemo", "onLandingPageClose");
			}
		});
		
	}
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	public boolean onDoubleTap(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onDoubleTapEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onSingleTapConfirmed(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.setClass(Welcome.this, Shaver.class);
		startActivity(intent);
		finish();
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		//return super.onKeyDown(keyCode, event);
		if(keyCode ==KeyEvent.KEYCODE_BACK){
			dialog();
			return false;
		}
		return false;
	}
	
	protected void dialog(){
		AlertDialog.Builder builder=new Builder(Welcome.this);
		builder.setTitle("提示");
		builder.setMessage("确定要退出吗?");
		builder.setPositiveButton("确认",  new DialogInterface.OnClickListener(){
 
	
			public void onClick(DialogInterface dialog, int which)
			{
		  
				finish();
			}
			
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
 
			
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
			
		});
		builder.create().show();
	}
}
