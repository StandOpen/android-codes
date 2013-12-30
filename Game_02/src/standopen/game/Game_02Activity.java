package standopen.game;

import android.app.Activity;
import android.os.Bundle;

public class Game_02Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovingPictureView myview=new MovingPictureView(this);
        setContentView(myview);
    }
}