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

	private Resources res;//��Դ
	private SurfaceHolder sfh;//surfaceview����
	private Thread th;//�߳�
	private Canvas canvas;//����
	private Paint paint;//����
	private boolean thread_flag ;//�߳����еı�־
	
	private  final float FLOOR_HEIGHT=55f;//����߶�
	private float ScreenH;
	private float ScreenW;//��Ļ��С
	private float gameWidth;//��Ϸ�Ŀ��
	
	private float move_X;//�����ƶ�
	private float position_X;
//��Ϸ���õ���ͼƬ
	private Bitmap background_bottom;
	private Bitmap background_top;
	private Bitmap squirrel_1;
	private Bitmap squirrel_2;
	private Bitmap catapult_base_1;
	private Bitmap catapult_base_2;
	
	private final float RATE = 30; //��Ļ����ʵ����ı��� 30px��1m;
	private World world; //��������
	private Vec2 gravity;// ����һ��������������
	private float timeStep = 1f / 60f;// ��������ģ��ĵ�Ƶ��
	private int iterations = 10;// ����ֵ������Խ��ģ��Խ��ȷ��������Խ��
	
	private Body floorBody;//��������
	private Body catapultArmBody;
	private Bitmap catapultArmBitmap;
	
	RevoluteJointDef rjd; //��ת�ؽ�
	
	private final TouchCallback callback = new TouchCallback();
	private MouseJoint mj;//���ؽ�
	private Vec2 touchPoint; //��ָ������Ļ���λ��
	private final AABB queryAABB = new AABB();  //����ģ������ķ�Χ
	private Body curBody;   //��ָ�����Ķ���
	private boolean withMouse = false; //�ж��Ƿ񴴽������ؽ�
	
	private Set<Body> bullet = new HashSet<Body>();  //���ڴ���ӵ�����ļ���
	private final int BulletCount = 4; //�ӵ�����
	private Bitmap bulletBitmap; //�ӵ�ͼƬ
	
	private WeldJoint wj;//���ӹؽ�
	private boolean catapult_flag = false;//�ƶ���������ʾ
	private boolean moveing_flag = false;//�ӵ��Ƿ����ƶ�״̬
	
	private Set<Body> targetBodies = new HashSet<Body>();//�������Ŀ��ļ���
	
	public MainView(Context context) {
		super(context);
		res = this.getResources();
		sfh = this.getHolder();
		sfh.addCallback(this); // ��ע1
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		
		 //����ͼƬ
        background_top = BitmapFactory.decodeResource(res, R.drawable.bg);
        background_bottom = BitmapFactory.decodeResource(res, R.drawable.fg);
        
        //��������ͼƬ
        squirrel_1 = BitmapFactory.decodeResource(res, R.drawable.squirrel_1);
        squirrel_2 = BitmapFactory.decodeResource(res, R.drawable.squirrel_2);
        
        //����������ͼƬ
        catapult_base_1 = BitmapFactory.decodeResource(res, R.drawable.catapult_base_1);
        catapult_base_2 = BitmapFactory.decodeResource(res, R.drawable.catapult_base_2);
        
        //������ͼƬ
        catapultArmBitmap = BitmapFactory.decodeResource(res, R.drawable.catapult_arm);
        
        //�ӵ�ͼƬ
        bulletBitmap = BitmapFactory.decodeResource(res, R.drawable.acorn);

        gravity = new Vec2(0f, 10f);//����
		world = new World(gravity, true);//����
		
		this.setKeepScreenOn(true);//����Ļ���ֳ���
        setClickable(true);//��ý���
     
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	public void surfaceCreated(SurfaceHolder arg0) {
		ScreenH = this.getHeight();
		ScreenW = this.getWidth();//��ȡ��Ļ��С
		gameWidth = background_top.getWidth();//��Ϸ�Ŀ���뱳��ͼƬ�Ŀ��һ��
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
		PolygonShape ps = new PolygonShape();//������״
    	ps.setAsBox(width/2/RATE, height/2/RATE);//��״Ϊһ������
    	
    	FixtureDef fd = new FixtureDef();//�����о�
    	fd.shape = ps;//Ϊ�о߰���״
    	fd.density = 0;//�����ܶ�,���ܶ�Ϊ0ʱ���������Ϊ��ֹ״̬��
    	fd.friction = 0.8f;//����Ħ����
    	fd.restitution = 0.3f;//���ûָ���
    	
    	BodyDef bd = new BodyDef();//��������
    	bd.position.set((x+width/2)/RATE, (y+height/2)/RATE);//�������巶Χ
    	bd.type = BodyType.STATIC;//���ø������ͣ�����Ϊ��̬����
    	
    	Body body = world.createBody(bd);//�������岢�ƶ�����
    	body.createFixture(fd);//Ϊ�����ƶ��о�
    	
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
     * ������ת�ؽ�
     * @return
     */
    public RevoluteJoint createRevoluteJoint() {
		//����һ����ת�ؽڵ�����ʵ�� 
		rjd =new RevoluteJointDef();
		//��ʼ����ת�ؽ�����
		rjd.initialize(catapultArmBody,floorBody ,new Vec2(catapultArmBody.getWorldCenter().x, catapultArmBody.getWorldCenter().y+catapultArmBitmap.getHeight()/2/RATE) );
		rjd.maxMotorTorque = 8000;// ����Ԥ�����Ť��
		rjd.motorSpeed =-1000;//�������Ť��
		rjd.enableMotor = true;// �������
		
		rjd.enableLimit = true; 
		rjd.lowerAngle = (float) (10f*Math.PI/180);
		rjd.upperAngle = (float) (75f*Math.PI/180);
		
		//����world����һ����ת�ؽ�
		RevoluteJoint rj = (RevoluteJoint)world.createJoint(rjd);
		return rj;
	}
    
    /**
     * �����ӵ�
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
    	float pos = 62.0f; //�ӵ����볡����˵�λ��
    	if(count >0)
    	{
    		float delta = 50f; //�ӵ�֮��ļ��
    		
    		for(int i=0;i<count;i++, pos += delta)
    		{
		    	PolygonShape pd = new PolygonShape();
		    	
		    	//����һ������ε�����
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
		    	db.bullet = true; //��ʾ���Ǹ�������ת�����壬��Ҫ��ϸ��ģ��
		    	
		    	Body body = world.createBody(db);
		    	body.m_userData = new Bullet(bulletBitmap, pos, FLOOR_HEIGHT+15.0f,30,30, 0);
		    	body.createFixture(fd);
		    	
		    	bullet.add(body);
    		}
    	}
    }
    
  //�������ӹؽ�
    //���ӵ��ڷ���ʱ�ܹ����ŷ�������ת
	public WeldJoint createWeldJoint() {
		Body body;
		Iterator<Body> aIterator = bullet.iterator(); //ʹ�õ�����ȡ��set�����е�����
		while (aIterator.hasNext()) {	//�ж�ʱ��������
			body = aIterator.next();	//��ȡ��һ�����ݣ���һ���ǻ�ȡ��һ������
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
		//��������
		world.step(timeStep, iterations, 6);
		//����λ�ò���
		Vec2 positionBuller;
		//�����е���������ļ���
		Body body = world.getBodyList();

		//�������е�����
		for (int i = 0; i < world.getBodyCount(); i++) {
			//��ȡ��ǰ�����λ��
			positionBuller = body.getPosition();
			//�жϵ�ǰ�����Ƿ�Ϊ������
			if ((body.m_userData) instanceof CatapultArm) {
				//��ȡ����������
				CatapultArm catapultArm = (CatapultArm) body.m_userData;
				//���÷�������x��
				catapultArm.setX(positionBuller.x * RATE - catapultArm.width/ 2);
				//���÷�������y��
				catapultArm.setY(positionBuller.y * RATE - catapultArm.height/ 2);
				//���÷������ĽǶ�
				catapultArm.setAngle((float) (body.getAngle() * 180 / Math.PI));
			}else if((body.m_userData) instanceof Bullet)
			{
				Bullet bullet = (Bullet)body.m_userData;
    			bullet.setX(positionBuller.x*RATE - bullet.width/2);
    	    	bullet.setY(positionBuller.y*RATE - bullet.height/2);
    	    	bullet.setAngle((float) (body.getAngle() * 180 / Math.PI));
    	    	if(moveing_flag)//�ж��ӵ��Ƿ����˶�״̬
    	    	{
    	    		//��ȡ���ƶ���ƫ������(ScreenW/2.0f����ʹ�ӵ�������Ļ�м�)
	    	    	float ww=this.bullet.iterator().next().getPosition().x * RATE - ScreenW / 2.0f;
	    	    	//��ֹ��ͷ�Ƴ���Ϸ����
	    	    	move_X=(ww<5)?10:(ww>580)?586:ww;
    	    	}
			}
			//������һ������
			body = body.m_next;
		}
		
		//�ƶ���������һ��һ�Ƕ�
		if(catapultArmBody.getAngle()< - (float) (40f*Math.PI/180))
    	{ 
    		catapult_flag=true;
    	}
		
		if(bullet.iterator().hasNext())//�����ж�ʱ����Ϊ������ӵ�
		{
			//ֻҪ������ӵ���ֹ���߳����˳�����Χ��ִ��
			if(!bullet.iterator().next().isAwake()||bullet.iterator().next().getPosition().x*RATE>background_bottom.getWidth())
	    	{
				moveing_flag = false;
	    	}
		}
		if(wj != null&&catapult_flag)
        {
			moveing_flag = true;
    		if(catapultArmBody.getAngle()>=-(float) (10f*Math.PI/180)){
	        	world.destroyJoint(wj);//���ٺ��ӹؽ�
	        	wj = null;
    		}
        }
		
		//�ӵ�����
    	if(wj == null&&!bullet.isEmpty()&&!moveing_flag)
    	{
    		//�����Ѿ�����ȥ���ӵ�
    		world.destroyBody(bullet.iterator().next());
    		bullet.remove(bullet.iterator().next());
    		catapult_flag = false;
    		wj = createWeldJoint();//�����µĺ��ӹؽ�
    	}
	}

	public void draw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
	            canvas.drawColor(Color.BLACK);// ˢ��
	            canvas.drawBitmap(background_top, 0-move_X, 0, paint);//������
	            
	            canvas.drawBitmap(catapult_base_2,260-move_X,ScreenH-FLOOR_HEIGHT-catapult_base_2.getHeight()-catapult_base_2.getHeight()/4,paint);//������������
	            
	            ((CatapultArm)catapultArmBody.m_userData).draw(canvas, paint, move_X);
	            
	            canvas.drawBitmap(catapult_base_1,265-move_X,ScreenH-FLOOR_HEIGHT-catapult_base_1.getHeight()-catapult_base_1.getHeight()/4,paint);//������������
	            
	            canvas.drawBitmap(squirrel_1, 50-move_X, ScreenH-FLOOR_HEIGHT-squirrel_1.getHeight(), paint);
	            canvas.drawBitmap(squirrel_2, 350-move_X, ScreenH-FLOOR_HEIGHT-squirrel_2.getHeight(), paint);//����������
	            
	            Body body = world.getBodyList();
	            for(int i=0;i<world.getBodyCount();i++)
	            {
	            	if ((body.m_userData) instanceof Bullet) {
	            		((Bullet)body.m_userData).draw(canvas, paint, move_X);
	            	}
	            	body = body.m_next;
	            }
	            
	            canvas.drawBitmap(background_bottom, 0-move_X, ScreenH-background_bottom.getHeight(), paint);//������
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
			if (mj != null) { //����Ѿ����������ؽڣ���ֱ�ӷ���
                return true;  
            }  
			
			//�õ���ǰģ�������е���С��Χ
            queryAABB.lowerBound.set(touchPoint.x - .001f, touchPoint.y - .001f);
			//�õ���ǰģ�������е����Χ
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
	                    def.maxForce = 3000;  //���ؽڵ�����
	                    mj = (MouseJoint) world.createJoint(def);  
	                    curBody.setAwake(true);
                    }
            }
		}else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(withMouse)
			{
				withMouse = false;
				//ͬʱɾ�����ؽ�
                if (mj != null) {
                    world.destroyJoint(mj);//���������������ؽ�
                    mj = null;
                }
			}
			
		}else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if (mj != null) {  
				mj.setTarget(touchPoint); //�������ؽڵı仯λ��
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
