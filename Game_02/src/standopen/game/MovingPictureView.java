package standopen.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class MovingPictureView extends View implements Runnable{
	 
	 //������ʾ��ͼƬ
	 Bitmap bitmap;
	 Bitmap bmp;
	 //ͼƬ����ת�����߳��Ƿ����У�false����ֹͣ����
	 boolean isRuning = true;
	 
	 //ͼƬ��Lfet��Topֵ
	 int left = 0;
	 int top = 0;
	 
	 //����ͬ���߳�
	 Handler handler;
	 
	 //����������ͨ�����ڴ��������������ƶ��ٶ�
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
	  //��ͼ����������
	  canvas.drawBitmap(bitmap, left,top, null);
	  
	 }
	 
	 @Override
	 public boolean onTouchEvent(MotionEvent event) {
	  isRuning = false;//�������Ļ����ͼƬ����ֹͣ
	  return true;
	 }
	 
	 public void run() {
	  while(isRuning){
	   
	   //ͨ�����������������Ʒ���
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

