package game.stndopen;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Gamestudy_01 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //��ȥ��ص�ͼ���һ�����β��֣�״̬�����֣� 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // ��ȥ����������������֣�
        setContentView(new gameview(this));
    }
}