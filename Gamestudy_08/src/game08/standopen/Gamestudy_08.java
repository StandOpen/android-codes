package game08.standopen;

import android.app.Activity;
import android.os.Bundle;

public class Gamestudy_08 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }
}