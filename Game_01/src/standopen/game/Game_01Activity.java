package standopen.game;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Game_01Activity extends Activity {
    /** Called when the activity is first created. */
	GameView myview;
	private SensorManager sensormanager;
	private Sensor sensoracess;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myview=new GameView(this);
        setContentView(myview);
        myview.setvalues(50, 50);
        sensormanager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
sensoracess=sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        sensormanager.registerListener(new SensorEventListener() {
			
			
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				int xp=-(int)event.values[0];
				int yp=(int)event.values[1];
				if(xp<0)
					xp=0;
				if(yp<0)
					yp=0;
					myview.setvalues(30*xp, 30*yp);
				myview.invalidate();
			}
			
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		}, sensoracess,SensorManager.SENSOR_DELAY_NORMAL);
    }
}