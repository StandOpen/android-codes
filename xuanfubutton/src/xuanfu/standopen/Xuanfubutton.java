package xuanfu.standopen;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Xuanfubutton extends Activity {
	 private WindowManager wm=null;
	    private WindowManager.LayoutParams wmParams=null;
		
	    private ImageView leftbtn=null;
	    private ImageView rightbtn=null;
		
	    // ImageView��alphaֵ   
	    private int mAlpha = 0;
	    private boolean isHide;
		
	    private ViewFlipper viewFlipper = null;
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	        
	        //��ʼ��������ť
	         initFloatView();
	    
	    }
	    /**
	     * ��ʼ��������ť
	     */
	    private void initFloatView(){
	        //��ȡWindowManager
	        wm=(WindowManager)getApplicationContext().getSystemService("window");
	        //����LayoutParams(ȫ�ֱ�������ز���
	        wmParams = new WindowManager.LayoutParams();
	    	
	        wmParams.type=LayoutParams.TYPE_PHONE;   //����window type
	        wmParams.format=PixelFormat.RGBA_8888;   //����ͼƬ��ʽ��Ч��Ϊ����͸��
	         //����Window flag
	        wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL 
	                               | LayoutParams.FLAG_NOT_FOCUSABLE;

	        //����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
	         wmParams.x=0;
	        wmParams.y=0;
	        //�����������ڳ�������
	         wmParams.width=50;
	        wmParams.height=50;
	    	
	        //����������ť
	         createLeftFloatView();
	        createRightFloatView();
	    }
	    
	    /**
	     * �������������ť
	     */
	    private void createLeftFloatView(){
	    	leftbtn=new ImageView(this);
	    	leftbtn.setImageResource(R.drawable.ic_launcher);
	    	leftbtn.setAlpha(0);
	    	leftbtn.setOnClickListener(new View.OnClickListener() {	
	        	    public void onClick(View arg0) {
			
		    }
		});
	    	//������������
	          wmParams.gravity=Gravity.LEFT|Gravity.CENTER_VERTICAL;
	         //��ʾmyFloatViewͼ��
	          wm.addView(leftbtn, wmParams);
	    }
	    /**
	     * �����ұ�������ť
	     */
	    private void createRightFloatView(){
	    	rightbtn=new ImageView(this);
	    	rightbtn.setImageResource(R.drawable.ic_launcher);
	    	rightbtn.setAlpha(0);
	         rightbtn.setOnClickListener(new View.OnClickListener() {	
		    public void onClick(View arg0) {
			//��һƪ
			
		    }
		});
	    	//������������
	          wmParams.gravity=Gravity.RIGHT|Gravity.CENTER_VERTICAL;
	         //��ʾmyFloatViewͼ��
	          wm.addView(rightbtn, wmParams);
	    }
	    /**
	     * ͼƬ������ʾ����
	     */
	    private Handler mHandler = new Handler()
	    {
		public void handleMessage(Message msg) {
	        	    if(msg.what==1 && mAlpha<255){   
			//System.out.println("---"+mAlpha);					
			mAlpha += 50;
			if(mAlpha>255)
		    	    mAlpha=255;
			leftbtn.setAlpha(mAlpha);
			leftbtn.invalidate();
			rightbtn.setAlpha(mAlpha);
			rightbtn.invalidate();
			if(!isHide && mAlpha<255)
			    mHandler.sendEmptyMessageDelayed(1, 100);
		    }else if(msg.what==0 && mAlpha>0){
			//System.out.println("---"+mAlpha);
			mAlpha -= 10;
			if(mAlpha<0)
			    mAlpha=0;
			leftbtn.setAlpha(mAlpha);
			leftbtn.invalidate();
			rightbtn.setAlpha(mAlpha);
			rightbtn.invalidate();
			if(isHide && mAlpha>0)
			    mHandler.sendEmptyMessageDelayed(0, 100);
		    }			
		}
	    };
		
	    private void showFloatView(){
	    	isHide = false;
	    	mHandler.sendEmptyMessage(1);
	    }
	    
	    private void hideFloatView(){
		new Thread(){
	        	    public void run() {
		        try {
		             Thread.sleep(1500);
		             isHide = true;
		             mHandler.sendEmptyMessage(0);
		        } catch (Exception e) {
		             ;
		        }
		    }
		}.start();
	    }
	    
	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	    	switch (event.getAction()) {
	    	    case MotionEvent.ACTION_MOVE:
		    case MotionEvent.ACTION_DOWN:
			//System.out.println("========ACTION_DOWN");
			showFloatView();			
			break;
		    case MotionEvent.ACTION_UP:
			//System.out.println("========ACTION_UP");
			hideFloatView();				
			break;
		}
		return true;
	    }

	    @Override
	    public void onDestroy(){
	    	super.onDestroy();
	    	//�ڳ����˳�(Activity���٣�ʱ������������
	    	wm.removeView(leftbtn);
	    	wm.removeView(rightbtn);
	    }
	}