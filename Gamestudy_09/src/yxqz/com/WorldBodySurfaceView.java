package yxqz.com;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 游戏世界 并且添加一个刚体
 * @author mahaile
 *
 */
public class WorldBodySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	
	final static int FPS=30;       //设置帧数
	MyThread myThread;  //ui线程
	SurfaceHolder surfaceHolder;  
	Canvas canvas;
	Paint paint;
	int screenW,screenH;
	
	private final float RATE=10.0f; //物理世界与屏幕环境缩放比列
	World world;   //游戏世界对象  
	float timeStep = 1f / 60f;;  //模拟的频率
	// 迭代值，迭代越大模拟越精确，但性能越低 
	int velocityIterations = 10;	
	int positionIterations = 8;
	
	public WorldBodySurfaceView(Context context) {
		super(context);
		surfaceHolder=this.getHolder();
		surfaceHolder.addCallback(this);  //添加回调
		
		Vec2 vect=new Vec2(0f,10.0f); //向量 用来标示当前世界的重力方向，第一个参数为水平方向，负数为左，正数为右。第二个参数表示垂直方向
		world=new World(vect,true);  //初始化游戏世界  这里注意一点 jbox2d 2.1 以后没有 aabb游戏范围 对象了

		//设置画笔
		paint=new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.BLUE);
	}

	
	public void onDraw(Canvas canvas){
		//Log.d("888","11111111111111111111");
		canvas.drawColor(Color.WHITE);  //清屏
		//Body body=world.getBodyList();
		
		//canvas.drawRect(r, paint)
		Body body=world.getBodyList();
		for(int i=0;i<world.getBodyCount();i++){
			BodyBox bodyBox=(BodyBox)body.m_userData;
			bodyBox.onDraw(canvas, paint);
			body=body.m_next;
		}
	}
	
/*	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW=this.getWidth();
		screenH=this.getHeight();
		createPolygon(5, this.getHeight() - 10, this.getWidth() - 10, 2,0.5f, true);
		/**创建6个方形，isStatic设置为false，即在物理世界中是动态，收外力作用影响 */
	/*	float [] restitution={0.1f,0.3f,0.5f,0.7f,0.8f,1.0f};
		for(int i=0;i<6;i++)
		{
			createPolygon(screenW-150-30*i,50,20,20,restitution[i],false);
		}

		myThread=new MyThread();
		myThread.flag=true;
		myThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		myThread.flag=false;
	}
	*/
	public class MyThread extends Thread{
		public boolean flag;
		public void run(){
			long lastTime=SystemClock.uptimeMillis();
			Canvas canvas;
			while(flag){
				long firstTime=SystemClock.uptimeMillis()-lastTime;
				
				if(firstTime>FPS){
					//Log.d("**", "-- "+firstTime +" firstTime is :"+firstTime +" lastTime is :"+lastTime);
					lastTime+=FPS;
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
						//Log.d("88","FPS-firstTime is :"+(FPS-firstTime)+" firstTime is :"+firstTime +" lastTime is :"+lastTime);
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
		
		/**遍历物理世界中的body，将物理世界仿真出的值反馈给屏幕，
		 * 改变bird和rect的参数
		 * */
		
		Body body = world.getBodyList();	
		for (int i = 1; i < world.getBodyCount(); i++) {
			if ((body.m_userData) instanceof BodyBox) {
				BodyBox rect = (BodyBox) (body.m_userData);
				rect.setX(body.getPosition().x * RATE - (rect.getW()/2));
				rect.setY(body.getPosition().y * RATE - (rect.getH()/2));
				//rect.setAngle((float)(body.getAngle()*180/Math.PI));
			}else // body.m_userData==null时，将body销毁，表示被击毁
			{
				world.destroyBody(body);
			}
			body = body.m_next;
		}
	}
	
	/**
	 *  创建圆型
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic 是否为动态
	 * @return body
	 */
	public Body createCircle(float x,float y,float r,boolean isStatic){
		CircleShape shape=new CircleShape();  //创建一个圆形对象
		shape.m_radius=r/RATE;  //设置圆形的半径 设置半径需要把屏幕的大小参数转换成世界大小的参数
		
		FixtureDef fDef=new FixtureDef();  //设置物体属性对象 游戏世界会根据此对象 创建刚体
		if(isStatic){
			fDef.density=0;  //设置物品的密度 当密度为0 时 物品不会受世界重力影响
		}else{
			fDef.density=1;  //设置物品的密度 当密度不为0 时 物品会受世界重力影响
		}
		fDef.friction=1.0f;  //设置物品的摩擦力  0~1
		fDef.restitution=0.5f;  //设置物体的弹力  值越大 弹性就越强  值范围为 0~1
		fDef.shape=shape;
		
		BodyDef bodyDef=new BodyDef();//创建一个刚体的属性 对象 比如 动态，位置，旋转角度等
		bodyDef.type=isStatic?BodyType.STATIC:BodyType.DYNAMIC;
		bodyDef.position.set(x/RATE, y/RATE);
		
		//创建刚体
		Body body=world.createBody(bodyDef);   //根据刚体属性对象在游戏世界中添加刚体
		body.createFixture(fDef);          //设置刚体的物理属性
		return body;
	}
	
	/**
	 *  创建正方形
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic 是否为动态
	 * @return body
	 */
	public Body createPolygon(float x,float y,float width,float height,float restitution,boolean isStatic){
		PolygonShape shape=new PolygonShape();  //创建一个圆形对象
		shape.setAsBox(width/2/RATE, height/2/RATE);  //设置圆形的半径 设置半径需要把屏幕的大小参数转换成世界大小的参数
		
		FixtureDef fDef=new FixtureDef();  //设置物体属性对象 游戏世界会根据此对象 创建刚体
		if(isStatic){
			fDef.density=0;  //设置物品的密度 当密度为0 时 物品不会受世界重力影响
		}else{
			fDef.density=1;  //设置物品的密度 当密度不为0 时 物品会受世界重力影响
		}
		fDef.friction=0.0f;  //设置物品的摩擦力  0~1
		Log.d("--------", "***"+restitution);
		fDef.restitution=restitution;  //设置物体的弹力  值越大 弹性就越强  值范围为 0~1
		fDef.shape=shape;
		
		BodyDef bodyDef=new BodyDef();//创建一个刚体的属性 对象 比如 动态，位置，旋转角度等
		bodyDef.type=isStatic?BodyType.STATIC:BodyType.DYNAMIC;
		bodyDef.position.set((x+width/2)/RATE,(y+height/2)/RATE);
		//创建刚体
		Body body=world.createBody(bodyDef);   //根据刚体属性对象在游戏世界中添加刚体
		body.createFixture(fDef);          //设置刚体的物理属性
		body.m_userData=new BodyBox(x,y,width,height);
		return body;
	}


	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}


	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screenW=this.getWidth();
		screenH=this.getHeight();
		createPolygon(5, this.getHeight() - 10, this.getWidth() - 10, 2,0.5f, true);
		/**创建6个方形，isStatic设置为false，即在物理世界中是动态，收外力作用影响 */
		float [] restitution={0.1f,0.3f,0.5f,0.7f,0.8f,1.0f};
		for(int i=0;i<6;i++)
		{
			createPolygon(screenW-150-30*i,50,20,20,restitution[i],false);
		}

		myThread=new MyThread();
		myThread.flag=true;
		myThread.start();
	}


	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		myThread.flag=false;
	}
}
