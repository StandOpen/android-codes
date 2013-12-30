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
 * 游戏世界
 * @author mahaile
 *
 */
public class WorldSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	
	final static int FPS=30;       //设置帧数
	MyThread myThread;  //ui线程
	SurfaceHolder surfaceHolder;  
	Canvas canvas;
	Paint paint;
	
	private final float RATE=10.0f; //物理世界与屏幕环境缩放比列
	World world;   //游戏世界对象  
	float timeStep = 1f / 60f;;  //模拟的频率
	// 迭代值，迭代越大模拟越精确，但性能越低 
	int velocityIterations = 10;	
	int positionIterations = 8;
	
	public WorldSurfaceView(Context context) {
		super(context);
		surfaceHolder=this.getHolder();
		surfaceHolder.addCallback(this);  //添加回调
		Vec2 vect=new Vec2(0f,10.0f); //向量 用来标示当前世界的重力方向，第一个参数为水平方向，负数为左，正数为右。第二个参数表示垂直方向
		world=new World(vect,true);  //初始化游戏世界  这里注意一点 jbox2d 2.1 以后没有 aabb游戏范围 对象了
	}

	public void onDraw(Canvas canvas){
		Log.d("888","11111111111111111111");
		canvas.drawColor(Color.WHITE);  //清屏
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
		// --开始模拟物理世界--->>
		world.step(timeStep,velocityIterations, positionIterations);
	}
	
}
