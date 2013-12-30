package standopen.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class MovingPictureView extends View implements Runnable{
	 
	 //用于显示的图片
	 Bitmap bitmap;
	 Bitmap bmp;
	 //图片坐标转化的线程是否运行，false，则停止运行
	 boolean isRuning = true;
	 
	 //图片的Lfet，Top值
	 int left = 0;
	 int top = 0;
	 
	 //用于同步线程
	 Handler handler;
	 
	 //向量，可以通过调节此两个变量调节移动速度
	 int dx = 1;
	 int dy = 1;
	 
	 public MovingPictureView(Context context) {
	  super(context);
	  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	  bmp=BitmapFactory.decodeResource(getResources(), R.drawable.beauty_01);
	  handler = new Handler();
	  new Thread(this).start();
	 }
	 
	 @Override
	 protected void onDraw(Canvas canvas) {
		 canvas.drawBitmap(bmp, 0,0, null);
	  //将图画到画板上
	  canvas.drawBitmap(bitmap, left,top, null);
	  
	 }
	 
	 @Override
	 public boolean onTouchEvent(MotionEvent event) {
	  isRuning = false;//当点击屏幕，则将图片浮动停止
	  return true;
	 }
	 
	 public void run() {
	  while(isRuning){
	   
	   //通过调节向量，来控制方向
	   dx = left < 0 || left > (getWidth() - bitmap.getWidth()) ? -dx : dx;
	   dy = top < 0 || top > (getHeight() - bitmap.getHeight()) ? -dy : dy;
	   
	   left = left+dx;
	   top = top+dy;
	   
	   handler.post(new Runnable() {
	    public void run() {
	     invalidate();
	    }
	   });
	   
	   try {
	    Thread.sleep(10);
	   } catch (InterruptedException e) {
	    e.printStackTrace();
	   }
	  }
	 }
	}

