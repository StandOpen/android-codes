package game.standopen;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameTest_01 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
     		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
     		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(new GameView(this));
    }
}