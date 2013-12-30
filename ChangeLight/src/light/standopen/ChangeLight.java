package light.standopen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;



@SuppressLint({ "HandlerLeak", "HandlerLeak" })
public class ChangeLight extends Activity implements OnTouchListener{
    /** Called when the activity is first created. */
	private int num=0;
	private int []colors={Color.WHITE,Color.BLUE,Color.GRAY,Color.GREEN,Color.CYAN,Color.RED,Color.YELLOW};
	private int []images={R.drawable.bagua_05,R.drawable.bagua_07,R.drawable.bagua_02,
			R.drawable.ba_01,R.drawable.ba_02,R.drawable.ba_03,R.drawable.ba_07,R.drawable.nangua,
			R.drawable.nangua_01,R.drawable.fangxiangpan};
	private roateview myview=null;;
	private SensorManager sensormanager;
	private Sensor sensoracess;
	private float[] gravity=new float[3];
	private float[]liner=new float[3];
	private int imagenum=0;
	private Handler handler = new Handler();  
	  
	private Runnable runnable = new Runnable() {  
	  
	    public void run() {  
	        update();  
	        handler.postDelayed(this,3000);  
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
         myview=new roateview(this);
        setContentView(myview);
        myview.setOnTouchListener(this);
        int brightness=250;
		 LayoutParams lp=getWindow().getAttributes();
	       lp.screenBrightness=brightness/255.0f;
	       getWindow().setAttributes(lp);
	       sensormanager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
	         sensoracess=sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	        
	        sensormanager.registerListener(new SensorEventListener() {
				
				
				public void onSensorChanged(SensorEvent event) {
					// TODO Auto-generated method stub
					final float alpha=0.8f;
					gravity[0]=alpha*gravity[0]+(1-alpha)*event.values[0];
					gravity[1]=alpha*gravity[1]+(1-alpha)*event.values[1];
					gravity[2]=alpha*gravity[2]+(1-alpha)*event.values[2];

					liner[0]=event.values[0]-gravity[0];
					liner[1]=event.values[1]-gravity[1];
					liner[2]=event.values[2]-gravity[2];
					 if(liner[0]>6)
					 {
						 imagenum++;
						 int temp=imagenum%10;
						 myview.setbmp(images[temp]);
					 }
					
				}
				
				public void onAccuracyChanged(Sensor sensor, int accuracy) {
					// TODO Auto-generated method stub
					
				}
			}, sensoracess,SensorManager.SENSOR_DELAY_NORMAL);
    }
    
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		
	       
		num++;
		int temp=num%7;
		//setlight.setBackgroundColor(colors[temp]);
		myview.setColor(colors[temp]);
		return false;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,1,1,"自动更换颜色");
		menu.add(0,2,2,"关闭自动更换");
		menu.add(0,3,3,"加速");
		menu.add(0,4,4,"减速");
		return super.onCreateOptionsMenu(menu);
		
	}


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==1)
		{
			
			 handler.postDelayed(runnable,1000);
		
			
		}
		else if(item.getItemId()==2)
		{
			
			handler.removeCallbacks(runnable);
		}
		else if(item.getItemId()==3)
		{
			myview.AddDigree();
		}
		else if(item.getItemId()==4)
		{
			myview.DecDigree();
		}
		return super.onMenuItemSelected(featureId, item);
	}
    public void update()
    {
    	num++;
		int temp=num%7;
		myview.setColor(colors[temp]);
    }
    
    
}