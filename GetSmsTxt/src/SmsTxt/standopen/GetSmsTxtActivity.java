package SmsTxt.standopen;


import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class GetSmsTxtActivity extends Activity {
    /** Called when the activity is first created. */
	private TextView infotext=null;
	private Button sendbutton=null;
	private Button frombutton=null;
	private Button allbutton=null;
	private ViewFlipper mFlipper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        infotext=(TextView)findViewById(R.id.info);
        sendbutton=(Button)findViewById(R.id.send);
        frombutton=(Button)findViewById(R.id.from);
        allbutton=(Button)findViewById(R.id.all);
        infotext.setText("这里显示的是短信信息");
        mFlipper=(ViewFlipper)findViewById(R.id.details);
   		mFlipper.setLongClickable(true);
   		sendbutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFlipper.setInAnimation(inFromRightAnimation());
				mFlipper.setOutAnimation(outToLeftAnimation());
				mFlipper.showNext();
			}
		});
    }
    /** * 定义从右侧进入的动画效果 * @return */
   	protected Animation inFromRightAnimation() {
   		Animation inFromRight = new TranslateAnimation(
   				Animation.RELATIVE_TO_PARENT, +1.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f);
   		inFromRight.setDuration(500);
   		inFromRight.setInterpolator(new AccelerateInterpolator());
   		return inFromRight;
   	}

   	/** * 定义从左侧退出的动画效果 * @return */
   	protected Animation outToLeftAnimation() {
   		Animation outtoLeft = new TranslateAnimation(
   				Animation.RELATIVE_TO_PARENT, 0.0f,
   				Animation.RELATIVE_TO_PARENT, -1.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f);
   		outtoLeft.setDuration(500);
   		outtoLeft.setInterpolator(new AccelerateInterpolator());
   		return outtoLeft;
   	}

   	/** * 定义从左侧进入的动画效果 * @return */
   	protected Animation inFromLeftAnimation() {
   		Animation inFromLeft = new TranslateAnimation(
   				Animation.RELATIVE_TO_PARENT, -1.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f);
   		inFromLeft.setDuration(500);
   		inFromLeft.setInterpolator(new AccelerateInterpolator());
   		return inFromLeft;
   	}

   	/** * 定义从右侧退出时的动画效果 * @return */
   	protected Animation outToRightAnimation() {
   		Animation outtoRight = new TranslateAnimation(
   				Animation.RELATIVE_TO_PARENT, 0.0f,
   				Animation.RELATIVE_TO_PARENT, +1.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f,
   				Animation.RELATIVE_TO_PARENT, 0.0f);
   		outtoRight.setDuration(500);
   		outtoRight.setInterpolator(new AccelerateInterpolator());
   		return outtoRight;
   	}
}