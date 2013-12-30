package yxqz.com;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ��Ϸ����
 * @author mahaile
 *
 */
public class WorldSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	
	final static int FPS=30;       //����֡��
	MyThread myThread;  //ui�߳�
	SurfaceHolder surfaceHolder;  
	Canvas canvas;
	Paint paint;
	
	private final float RATE=10.0f; //������������Ļ�������ű���
	World world;   //��Ϸ�������  
	float timeStep = 1f / 60f;;  //ģ���Ƶ��
	// ����ֵ������Խ��ģ��Խ��ȷ��������Խ�� 
	int velocityIterations = 10;	
	int positionIterations = 8;
	
	public WorldSurfaceView(Context context) {
		super(context);
		surfaceHolder=this.getHolder();
		surfaceHolder.addCallback(this);  //��ӻص�
		Vec2 vect=new Vec2(0f,10.0f); //���� ������ʾ��ǰ������������򣬵�һ������Ϊˮƽ���򣬸���Ϊ������Ϊ�ҡ��ڶ���������ʾ��ֱ����
		world=new World(vect,true);  //��ʼ����Ϸ����  ����ע��һ�� jbox2d 2.1 �Ժ�û�� aabb��Ϸ��Χ ������
	}

	public void onDraw(Canvas canvas){
		Log.d("888","11111111111111111111");
		canvas.drawColor(Color.WHITE);  //����
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		myThread=new MyThread();
		myThread.flag=true;
		myThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		myThread.flag=false;
	}
	
	public class MyThread extends Thread{
		public boolean flag;
		public void run(){
			long lastTime=SystemClock.uptimeMillis();
			Canvas canvas;
			while(flag){
				long firstTime=SystemClock.uptimeMillis()-lastTime;
				if(firstTime>FPS){
					canvas=surfaceHolder.lockCanvas();
					try{
						Logic();
						onDraw(canvas);
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}else{
					try {
						Thread.sleep(Math.max(2,FPS-firstTime));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void Logic() {
		// --��ʼģ����������--->>
		world.step(timeStep,velocityIterations, positionIterations);
	}
	
}
