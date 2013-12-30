package game10.standopen;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements Callback, Runnable {

	private Resources res;//资源
	private SurfaceHolder sfh;//surfaceview管理
	private Thread th;//线程
	private Canvas canvas;//画布
	private Paint paint;//画笔
	private boolean thread_flag ;//线程运行的标志
	
	private  final float FLOOR_HEIGHT=55f;//地面高度
	private float ScreenH;
	private float ScreenW;//屏幕大小
	private float gameWidth;//游戏的宽度
	
	private float move_X;//画布移动
	private float position_X;
//游戏中用到的图片
	private Bitmap background_bottom;
	private Bitmap background_top;
	private Bitmap squirrel_1;
	private Bitmap squirrel_2;
	private Bitmap catapult_base_1;
	private Bitmap catapult_base_2;
	
	private final float RATE = 30; //屏幕到现实世界的比例 30px：1m;
	private World world; //物理世界
	private Vec2 gravity;// 声明一个重力向量对象
	private float timeStep = 1f / 60f;// 物理世界模拟的的频率
	private int iterations = 10;// 迭代值，迭代越大模拟越精确，但性能越低
	
	private Body floorBody;//地面物体
	private Body catapultArmBody;
	private Bitmap catapultArmBitmap;
	
	RevoluteJointDef rjd; //旋转关节
	
	private final TouchCallback callback = new TouchCallback();
	private MouseJoint mj;//鼠标关节
	private Vec2 touchPoint; //手指触摸屏幕点的位置
	private final AABB queryAABB = new AABB();  //物理模拟世界的范围
	private Body curBody;   //手指触摸的对象
	private boolean withMouse = false; //判断是否创建了鼠标关节
	
	private Set<Body> bullet = new HashSet<Body>();  //用于存放子弹对象的集合
	private final int BulletCount = 4; //子弹数量
	private Bitmap bulletBitmap; //子弹图片
	
	private WeldJoint wj;//焊接关节
	private boolean catapult_flag = false;//推动发射器标示
	private boolean moveing_flag = false;//子弹是否处于移动状态
	
	private Set<Body> targetBodies = new HashSet<Body>();//创建存放目标的集合
	
	public MainView(Context context) {
		super(context);
		res = this.getResources();
		sfh = this.getHolder();
		sfh.addCallback(this); // 备注1
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		
		 //背景图片
        background_top = BitmapFactory.decodeResource(res, R.drawable.bg);
        background_bottom = BitmapFactory.decodeResource(res, R.drawable.fg);
        
        //两个松鼠图片
        squirrel_1 = BitmapFactory.decodeResource(res, R.drawable.squirrel_1);
        squirrel_2 = BitmapFactory.decodeResource(res, R.drawable.squirrel_2);
        
        //发射器底座图片
        catapult_base_1 = BitmapFactory.decodeResource(res, R.drawable.catapult_base_1);
        catapult_base_2 = BitmapFactory.decodeResource(res, R.drawable.catapult_base_2);
        
        //发射器图片
        catapultArmBitmap = BitmapFactory.decodeResource(res, R.drawable.catapult_arm);
        
        //子弹图片
        bulletBitmap = BitmapFactory.decodeResource(res, R.drawable.acorn);

        gravity = new Vec2(0f, 10f);//重力
		world = new World(gravity, true);//世界
		
		this.setKeepScreenOn(true);//让屏幕保持常亮
        setClickable(true);//获得焦点
     
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	public void surfaceCreated(SurfaceHolder arg0) {
		ScreenH = this.getHeight();
		ScreenW = this.getWidth();//获取屏幕大小
		gameWidth = background_top.getWidth();//游戏的宽度与背景图片的宽度一致
		thread_flag = true;
		
		floorBody = CreateFloorBody(0, ScreenH-FLOOR_HEIGHT, gameWidth, FLOOR_HEIGHT);
		
		catapultArmBody = CreateCatapultBody(catapultArmBitmap, 290, ScreenH-FLOOR_HEIGHT-catapultArmBitmap.getHeight()-catapult_base_2.getHeight()/2, catapultArmBitmap.getWidth(), catapultArmBitmap.getHeight());
		
		createRevoluteJoint();
		createBulletBody(BulletCount);
		
		wj = createWeldJoint();
		
		th = new Thread(this);
		th.start();
	}
	
	private Body CreateFloorBody(float x,float y,float width,float height)
	{
		PolygonShape ps = new PolygonShape();//创建形状
    	ps.setAsBox(width/2/RATE, height/2/RATE);//形状为一个矩形
    	
    	FixtureDef fd = new FixtureDef();//创建夹具
    	fd.shape = ps;//为夹具绑定形状
    	fd.density = 0;//设置密度,当密度为0时，这个物体为静止状态。
    	fd.friction = 0.8f;//设置摩擦力
    	fd.restitution = 0.3f;//设置恢复力
    	
    	BodyDef bd = new BodyDef();//创建刚体
    	bd.position.set((x+width/2)/RATE, (y+height/2)/RATE);//创建刚体范围
    	bd.type = BodyType.STATIC;//设置刚体类型，设置为静态刚体
    	
    	Body body = world.createBody(bd);//创建物体并制定刚体
    	body.createFixture(fd);//为物体制定夹具
    	
    	return body;
	}
	
	public Body CreateCatapultBody(Bitmap bitmap,float x,float y,float width,float height)
    {
    	PolygonShape ps = new PolygonShape();
    	ps.setAsBox(width/2/RATE, (height/2-40)/RATE);
    	
    	FixtureDef fd = new FixtureDef();
    	fd.shape = ps;
    	fd.density = 1;
    	fd.friction = 0.8f;
    	fd.restitution = 0.3f;
    	
    	BodyDef bd = new BodyDef();
    	bd.position.set((x+width/2)/RATE, (y+height/2)/RATE);
    	bd.type = BodyType.DYNAMIC;
    	
    	Body body = world.createBody(bd);
    	body.m_userData = new CatapultArm(bitmap, x, y, width,height,0);
    	body.createFixture(fd);
    	
    	return body;
    }
	
	 /**
     * 创建旋转关节
     * @return
     */
    public RevoluteJoint createRevoluteJoint() {
		//创建一个旋转关节的数据实例 
		rjd =new RevoluteJointDef();
		//初始化旋转关节数据
		rjd.initialize(catapultArmBody,floorBody ,new Vec2(catapultArmBody.getWorldCenter().x, catapultArmBody.getWorldCenter().y+catapultArmBitmap.getHeight()/2/RATE) );
		rjd.maxMotorTorque = 8000;// 马达的预期最大扭矩
		rjd.motorSpeed =-1000;//马达最终扭矩
		rjd.enableMotor = true;// 启动马达
		
		rjd.enableLimit = true; 
		rjd.lowerAngle = (float) (10f*Math.PI/180);
		rjd.upperAngle = (float) (75f*Math.PI/180);
		
		//利用world创建一个旋转关节
		RevoluteJoint rj = (RevoluteJoint)world.createJoint(rjd);
		return rj;
	}
    
    /**
     * 创建子弹
     * @param bitmap
     * @param x
     * @param y
     * @param width
     * @param height
     * @param isStatic
     * @return
     */
    public void createBulletBody(int count)
    {
    	float pos = 62.0f; //子弹距离场景左端的位置
    	if(count >0)
    	{
    		float delta = 50f; //子弹之间的间隔
    		
    		for(int i=0;i<count;i++, pos += delta)
    		{
		    	PolygonShape pd = new PolygonShape();
		    	
		    	//创建一个多边形的物体
		    	Vec2[] vec2s = new Vec2[6];
		    	vec2s[0] = new Vec2(-0.6f, 0.66f);
		    	vec2s[1] = new Vec2(-0.8f, -0.2f);
		    	vec2s[2] = new Vec2(-0.28f,-0.88f);
		    	vec2s[3] = new Vec2(0.4f,-0.6f);
		    	vec2s[4] = new Vec2(0.88f,-0.1f);
		    	vec2s[5] = new Vec2(0.4f,0.82f);
		    	pd.set(vec2s, 6);
		    	
		    	FixtureDef fd = new FixtureDef();
		    	fd.shape = pd;
		    	fd.density = 1;
		    	fd.friction = 0.8f;
		    	fd.restitution = 0.3f;
		    	
		    	BodyDef db= new BodyDef();
		    	db.position.set(pos/RATE, (ScreenH-FLOOR_HEIGHT-50f)/RATE);
		    	db.type = BodyType.DYNAMIC;
		    	db.bullet = true; //表示这是个高速运转的物体，需要精细的模拟
		    	
		    	Body body = world.createBody(db);
		    	body.m_userData = new Bullet(bulletBitmap, pos, FLOOR_HEIGHT+15.0f,30,30, 0);
		    	body.createFixture(fd);
		    	
		    	bullet.add(body);
    		}
    	}
    }
    
  //创建焊接关节
    //让子弹在发射时能够随着发射器旋转
	public WeldJoint createWeldJoint() {
		Body body;
		Iterator<Body> aIterator = bullet.iterator(); //使用迭代器取出set集合中的数据
		while (aIterator.hasNext()) {	//判断时候还有数据
			body = aIterator.next();	//获取下一个数据，第一次是获取第一个数据
			body.setTransform(new Vec2((360f/RATE), (ScreenH-FLOOR_HEIGHT-bulletBitmap.getHeight()-227)/RATE), 0);
	  		WeldJointDef wd = new WeldJointDef();
	  		wd.initialize(body, catapultArmBody, catapultArmBody.getWorldCenter());
	  		return (WeldJoint) world.createJoint(wd);
		}

		return null;
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		thread_flag = false;

	}
	
	public void logic() {
		//唤醒世界
		world.step(timeStep, iterations, 6);
		//物体位置参数
		Vec2 positionBuller;
		//世界中的所有物体的集合
		Body body = world.getBodyList();

		//遍历所有的物体
		for (int i = 0; i < world.getBodyCount(); i++) {
			//获取当前物体的位置
			positionBuller = body.getPosition();
			//判断当前物体是否为发射器
			if ((body.m_userData) instanceof CatapultArm) {
				//获取发射器对象
				CatapultArm catapultArm = (CatapultArm) body.m_userData;
				//设置发射器的x轴
				catapultArm.setX(positionBuller.x * RATE - catapultArm.width/ 2);
				//设置发射器的y轴
				catapultArm.setY(positionBuller.y * RATE - catapultArm.height/ 2);
				//设置发射器的角度
				catapultArm.setAngle((float) (body.getAngle() * 180 / Math.PI));
			}else if((body.m_userData) instanceof Bullet)
			{
				Bullet bullet = (Bullet)body.m_userData;
    			bullet.setX(positionBuller.x*RATE - bullet.width/2);
    	    	bullet.setY(positionBuller.y*RATE - bullet.height/2);
    	    	bullet.setAngle((float) (body.getAngle() * 180 / Math.PI));
    	    	if(moveing_flag)//判断子弹是否处于运动状态
    	    	{
    	    		//获取到移动的偏移量。(ScreenW/2.0f用于使子弹处于屏幕中间)
	    	    	float ww=this.bullet.iterator().next().getPosition().x * RATE - ScreenW / 2.0f;
	    	    	//防止镜头移出游戏场景
	    	    	move_X=(ww<5)?10:(ww>580)?586:ww;
    	    	}
			}
			//遍历下一个对象
			body = body.m_next;
		}
		
		//推动发射器到一定一角度
		if(catapultArmBody.getAngle()< - (float) (40f*Math.PI/180))
    	{ 
    		catapult_flag=true;
    	}
		
		if(bullet.iterator().hasNext())//首先判断时候还有为发射的子弹
		{
			//只要发射的子弹静止或者超出了场景范围就执行
			if(!bullet.iterator().next().isAwake()||bullet.iterator().next().getPosition().x*RATE>background_bottom.getWidth())
	    	{
				moveing_flag = false;
	    	}
		}
		if(wj != null&&catapult_flag)
        {
			moveing_flag = true;
    		if(catapultArmBody.getAngle()>=-(float) (10f*Math.PI/180)){
	        	world.destroyJoint(wj);//销毁焊接关节
	        	wj = null;
    		}
        }
		
		//子弹续膛
    	if(wj == null&&!bullet.isEmpty()&&!moveing_flag)
    	{
    		//销毁已经发出去的子弹
    		world.destroyBody(bullet.iterator().next());
    		bullet.remove(bullet.iterator().next());
    		catapult_flag = false;
    		wj = createWeldJoint();//创建新的焊接关节
    	}
	}

	public void draw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
	            canvas.drawColor(Color.BLACK);// 刷屏
	            canvas.drawBitmap(background_top, 0-move_X, 0, paint);//画背景
	            
	            canvas.drawBitmap(catapult_base_2,260-move_X,ScreenH-FLOOR_HEIGHT-catapult_base_2.getHeight()-catapult_base_2.getHeight()/4,paint);//画发射器底座
	            
	            ((CatapultArm)catapultArmBody.m_userData).draw(canvas, paint, move_X);
	            
	            canvas.drawBitmap(catapult_base_1,265-move_X,ScreenH-FLOOR_HEIGHT-catapult_base_1.getHeight()-catapult_base_1.getHeight()/4,paint);//画发射器底座
	            
	            canvas.drawBitmap(squirrel_1, 50-move_X, ScreenH-FLOOR_HEIGHT-squirrel_1.getHeight(), paint);
	            canvas.drawBitmap(squirrel_2, 350-move_X, ScreenH-FLOOR_HEIGHT-squirrel_2.getHeight(), paint);//画两个松鼠
	            
	            Body body = world.getBodyList();
	            for(int i=0;i<world.getBodyCount();i++)
	            {
	            	if ((body.m_userData) instanceof Bullet) {
	            		((Bullet)body.m_userData).draw(canvas, paint, move_X);
	            	}
	            	body = body.m_next;
	            }
	            
	            canvas.drawBitmap(background_bottom, 0-move_X, ScreenH-background_bottom.getHeight(), paint);//画地面
        	}


		} catch (Exception e) {
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		touchPoint = new Vec2((event.getX()+move_X)/RATE, event.getY()/RATE);
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			position_X =move_X+ event.getX();
			if (mj != null) { //如果已经创建了鼠标关节，就直接返回
                return true;  
            }  
			
			//得到当前模拟世界中的最小范围
            queryAABB.lowerBound.set(touchPoint.x - .001f, touchPoint.y - .001f);
			//得到当前模拟世界中的最大范围
            queryAABB.upperBound.set(touchPoint.x + .001f, touchPoint.y + .001f);
            callback.point.set(touchPoint);  
            callback.fixture = null;  
            world.queryAABB(callback, queryAABB);  
              
            if(callback.fixture != null){  
                    curBody = callback.fixture.getBody();
                    if(curBody.m_userData instanceof CatapultArm){
                    	withMouse = true;
	                    MouseJointDef def = new MouseJointDef();  
	                    def.bodyA = floorBody;  
	                    def.bodyB = curBody;  
	                    def.target.set(touchPoint);  
	                    def.maxForce = 3000;  //鼠标关节的马力
	                    mj = (MouseJoint) world.createJoint(def);  
	                    curBody.setAwake(true);
                    }
            }
		}else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(withMouse)
			{
				withMouse = false;
				//同时删除鼠标关节
                if (mj != null) {
                    world.destroyJoint(mj);//在世界中销毁鼠标关节
                    mj = null;
                }
			}
			
		}else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if (mj != null) {  
				mj.setTarget(touchPoint); //设置鼠标关节的变化位置
            }
			if(!withMouse)
			{
				move_X = position_X-event.getX();
				move_X = move_X<0?0:(move_X>gameWidth-ScreenW?gameWidth-ScreenW:move_X);
			}
		}
		return super.onTouchEvent(event);
	}

	public void run() {
		while (thread_flag) {
			logic();
			draw();
		try {
			Thread.sleep((long) timeStep * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	}

}
