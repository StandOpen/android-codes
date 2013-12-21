package moyan.standopen;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Welcome extends Activity implements OnTouchListener,
OnGestureListener, OnDoubleTapListener{
	private ViewFlipper mFlipper;
	GestureDetector mGestureDetector;
	float xpath=0;
	int num=1;
	private ImageView loader=null;
	private int []Images={R.drawable.loader_frame_1,R.drawable.loader_frame_2,R.drawable.loader_frame_3,
			R.drawable.loader_frame_4,R.drawable.loader_frame_5,R.drawable.loader_frame_6};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
       	 WindowManager.LayoutParams.FLAG_FULLSCREEN);  
       	    
       requestWindowFeature(Window.FEATURE_NO_TITLE);  
        
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
       
        setContentView(R.layout.welcomelayout);
        

       
        
        loader=(ImageView)findViewById(R.id.image_loader);
     
        
        loader.setImageResource(Images[num-1]);
		 
   	 mFlipper=(ViewFlipper)findViewById(R.id.details);
     mGestureDetector = new GestureDetector(this);
		mFlipper.setLongClickable(true);
     mFlipper.setOnTouchListener(new View.OnTouchListener() {
		
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				xpath=event.getX();
				break;
			case MotionEvent.ACTION_UP:
				if(event.getX()-xpath<-5)
				{
					// 当像左侧滑动的时候 //设置View进入屏幕时候使用的动画
					mFlipper.setInAnimation(inFromRightAnimation());
					// 设置View退出屏幕时候使用的动画
					mFlipper.setOutAnimation(outToLeftAnimation());
					if(num<6)
					{
					mFlipper.showNext();
					num++;
					loader.setImageResource(Images[num-1]);
					}
				}
				if(event.getX()-xpath>5)
				{
					// 当像右侧滑动的时候
					mFlipper.setInAnimation(inFromLeftAnimation());
					mFlipper.setOutAnimation(outToRightAnimation());
					if(num>1)
					{
					mFlipper.showPrevious();
					num--;
					loader.setImageResource(Images[num-1]);
					}
				}
				break;
			}
			return false;
		}

	});
    }



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
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	  
	  @Override
		protected void onResume() {

			super.onResume();
		}

		@Override
		protected void onDestroy() {
		
			super.onDestroy();
		}
}