package standopen.test;



import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class TestGuiActivity extends Activity {
    /** Called when the activity is first created. */
	private SensorManager sensormanager;
	private Sensor sensoracess;
	private float[] gravity=new float[3];
	private float[]liner=new float[3];
	private ImageView myimage=null;
	int type=1;
	int num=1;
	MediaPlayer mediaPlayer = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
         	     WindowManager.LayoutParams.FLAG_FULLSCREEN);  
         	     /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效*/  
         requestWindowFeature(Window.FEATURE_NO_TITLE);  
         //设置全屏  
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        myimage=(ImageView)findViewById(R.id.gui);
        sensormanager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
         sensoracess=sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
         mediaPlayer = MediaPlayer.create(TestGuiActivity.this, R.raw.rolling);
			mediaPlayer.setLooping(false);
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
 				if(liner[0]>0.5){
 				if(type==1){
 					myimage.setBackgroundResource(R.drawable.gui_2);
 					type=2;
 				}
 				else{
 					myimage.setBackgroundResource(R.drawable.gui);
 					type=1;
 				}
 				
				mediaPlayer.start();
				
				
 				}
 				
 				
 		
 			}
 			
 			public void onAccuracyChanged(Sensor sensor, int accuracy) {
 				// TODO Auto-generated method stub
 				
 			}
 		}, sensoracess,SensorManager.SENSOR_DELAY_NORMAL);
     }
}