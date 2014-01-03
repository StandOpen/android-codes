package shaver.standopen;


import java.util.Timer;
import java.util.TimerTask;

import cn.domob.android.ads.DomobAdListener;
import cn.domob.android.ads.DomobAdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Shaver extends Activity implements SensorEventListener {
    /** Called when the activity is first created. */
	private RelativeLayout mylayout=null;
	private ImageView myimage=null;
	private int type=1;
	private SensorManager sensormanager;
	private Sensor sensoracess;
	private MediaPlayer mediaPlayer1 = null;
	private MediaPlayer mediaPlayer2 = null;
	private MediaPlayer mediaPlayer3 = null;
	private boolean IsOn=false;
	private boolean Isshave=false;
	private Vibrator vibrator; 
	private DomobAdView mAdview320x50;
	private int batterynumber=0;
	private ImageButton onoff=null;
	private ImageView battery=null;
	private int []imagesbattery={R.drawable.battery_01,R.drawable.battery_02,R.drawable.battery_03,R.drawable.battery_04,R.drawable.battery_05};
	private int []imagesshaver={R.drawable.phips_06,R.drawable.phips_07,R.drawable.plisps_02,
			R.drawable.plisps_03,R.drawable.pslipd_04,R.drawable.pslipos_05,R.drawable.tixudao};
	
	private Timer timer = new Timer(true);
	private int runnum=0;
	private ImageButton refresh=null;
	private int shavernumber=0;
	
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        WindowManager.LayoutParams.FLAG_FULLSCREEN);   
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.main);
        
        
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。  
        
        t.setToNow(); // 取得系统时间。  
        int year = t.year;  
        int month = t.month;  
        int date = t.monthDay;  
        int hour = t.hour; 
        int minute = t.minute;  
        int second = t.second;  
        int a1=getWeekB(year,7, month,date);
        myimage=(ImageView)findViewById(R.id.shaverimage);
        myimage.setImageResource(imagesshaver[a1]);
        shavernumber=a1;
        
        refresh=(ImageButton)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shavernumber++;
				int num1=shavernumber%7;
				myimage.setImageResource(imagesshaver[num1]);
			}
		});
        
        
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        
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
		battery=(ImageView)findViewById(R.id.battery);
        mylayout=(RelativeLayout)findViewById(R.id.shaverlayout);
        
        onoff=(ImageButton)findViewById(R.id.buttonkaiguan);
        sensormanager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensoracess=sensormanager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        
            mediaPlayer3 = MediaPlayer.create(Shaver.this, R.raw.begin);
			mediaPlayer3.setLooping(true);
			mediaPlayer2 = MediaPlayer.create(Shaver.this, R.raw.shaverlong);
			mediaPlayer2.setLooping(true);
			vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); 
        
        
        
        onoff.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mediaPlayer1 = MediaPlayer.create(Shaver.this, R.raw.on_01);
				mediaPlayer1.setLooping(false);
				mediaPlayer1.start();
					if(type==1)
					{
						onoff.setBackgroundResource(R.drawable.button_on_01);
					//onoff.setImageResource(R.drawable.button_on);
					mediaPlayer2.start();
					type++;
					IsOn=true;
					}
					else{
						//onoff.setBackgroundDrawable();
						onoff.setBackgroundResource(R.drawable.button_off_01);
						//onoff.setImageResource(R.drawable.button_off);
						mediaPlayer2.pause();
						vibrator.cancel();
						if(Isshave){
							mediaPlayer3.pause();
						}
						type--;
						IsOn=false;
					}
			
			}
		});
        
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
			AlertDialog.Builder builder=new Builder(Shaver.this);
			builder.setTitle("提示");
			builder.setMessage("确定要退出吗?");
			builder.setPositiveButton("确认",  new DialogInterface.OnClickListener(){
	 
		
				public void onClick(DialogInterface dialog, int which)
				{
			  
					if(Isshave==true)
						mediaPlayer3.stop();
					mediaPlayer2.pause();
					vibrator.cancel();
					if(Isshave){
						mediaPlayer3.pause();
					}
					
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

		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
		   float num=event.values[0];
			if(IsOn==true)
			{
			if(num==0)
			{
			   mediaPlayer2.pause();
				if(IsOn==true)
				{
				Isshave=true;
				long[] pattern = {10,10000,10,10000};   
				vibrator.vibrate(pattern, -1);
				mediaPlayer3.start();
				
				}
			}
			else
			{
			if(Isshave==true)
			{
			mediaPlayer3.pause();
			vibrator.cancel();
			mediaPlayer2.start();
			}
			
			}
			}
			
		}
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
		sensormanager.unregisterListener(this);
		}
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			
			super.onResume();
			sensormanager.registerListener( this,sensoracess,SensorManager.SENSOR_DELAY_NORMAL);
		}

		
		  private BroadcastReceiver batteryReceiver=new BroadcastReceiver(){  
		    	 
		        @Override 
		        public void onReceive(Context context, Intent intent) {  
		           int level = intent.getIntExtra("level", 0);  
		            int status=intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
		            if(status==BatteryManager.BATTERY_STATUS_CHARGING)
		            {
		            	if(runnum==0)
		            	{
		            	timer.schedule(task,0,1000);
		            	runnum=1;
		            	}
		            }
		            else
		            {
		          int num=level/20;
		          if(level==100)
		          battery.setImageResource(imagesbattery[num-1]);
		          else
		        	  battery.setImageResource(imagesbattery[num]);
		            }
		        	  
		    }  
		    };
		    private int getWeekB(int y, int c, int m, int d)
		    {
		            if(m == 1)
		            {
		                    m = 13;
		                    y = y-1;
		            }
		            else if(m == 2)
		            {
		                    m = 14;
		                    y = y-1;
		            }
		            int tempDate = (y+(y/4)+(c/4)-2*c+(26*(m+1)/10)+d-1)%7;
		            if(tempDate < 0)
		            {
		                    return 7+tempDate;
		            }
		            return tempDate;
		    }
		    
		    

		    Handler handler = new Handler(){

				 public void handleMessage(Message msg)

				 {
					 
					 battery.setImageResource(imagesbattery[msg.arg1]);
				 

				 super.handleMessage(msg);

				 }

				 };
				 TimerTask task=new TimerTask() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							int num=batterynumber;
							if(batterynumber==4)
								batterynumber=0;
							 Message message = new Message();

							 message.arg1 = num;
							 batterynumber++;
							

							 handler.sendMessage(message);

							 }

							 };
			
			


		    
}