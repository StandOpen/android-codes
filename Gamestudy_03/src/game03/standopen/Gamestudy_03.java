package game03.standopen;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Gamestudy_03 extends Activity implements OnTouchListener{
   private Button left=null;
   private Button right=null;
   private Button up=null;
   private Button down=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐去电池等图标和一切修饰部分（状态栏部分） 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐去标题栏（程序的名字）
        setContentView(R.layout.main);
        left=(Button)findViewById(R.id.left);
        right=(Button)findViewById(R.id.right);
        up=(Button)findViewById(R.id.up);
        down=(Button)findViewById(R.id.down);
        left.setOnTouchListener(this);
        right.setOnTouchListener(this);
        up.setOnTouchListener(this);
        down.setOnTouchListener(this);
       
    }

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
		switch(v.getId())
		{
		case R.id.up:
		{
			MySurfaceView.instance.ButtonDown(3);
			break;
		}
		case R.id.down:
		{
			MySurfaceView.instance.ButtonDown(4);
			break;
		}
		case R.id.left:
		{
			MySurfaceView.instance.ButtonDown(1);
			break;
		}
		case R.id.right:
		{
			MySurfaceView.instance.ButtonDown(2);
			break;
		}
		}
		}
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
		switch(v.getId())
		{
		case R.id.up:
		{
			MySurfaceView.instance.ButtonUp(3);
			break;
		}
		case R.id.down:
		{
			MySurfaceView.instance.ButtonUp(4);
			break;
		}
		case R.id.left:
		{
			MySurfaceView.instance.ButtonUp(1);
			break;
		}
		case R.id.right:
		{
			MySurfaceView.instance.ButtonUp(2);
			break;
		}
		}
		}
		return false;
	}
}