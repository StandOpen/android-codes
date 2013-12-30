package game06.standopen;

import android.app.Activity;
import android.os.Bundle;

public class Gamestudy_06 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceViee(this));
    }
}