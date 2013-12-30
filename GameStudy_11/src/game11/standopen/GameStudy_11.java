package game11.standopen;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class GameStudy_11 extends Activity implements OnTouchListener{
	   private Button left=null;
	   private Button right=null;
	   private Button up=null;
	   private Button down=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	// 隐去电池等图标和一切修饰部分（状态栏部分）
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 隐去标题栏（程序的名字）
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
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
			MainGameView.instance.Buttondown(3);
			break;
		}
		case R.id.down:
		{
			MainGameView.instance.Buttondown(4);
			break;
		}
		case R.id.left:
		{
			MainGameView.instance.Buttondown(1);
			break;
		}
		case R.id.right:
		{
			MainGameView.instance.Buttondown(2);
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
			MainGameView.instance.Buttonup(3);
			break;
		}
		case R.id.down:
		{
			MainGameView.instance.Buttonup(4);
			break;
		}
		case R.id.left:
		{
			MainGameView.instance.Buttonup(1);
			break;
		}
		case R.id.right:
		{
			MainGameView.instance.Buttonup(2);
			break;
		}
		}
		}
		return false;
		}
}