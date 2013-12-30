package game11.standopen;



import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MainGameView extends SurfaceView implements Callback,Runnable{

	private Paint paint;
	private Paint p_text;
	private SurfaceHolder sfh;
	private Canvas can;
	private Thread th;
	private Resources res;
	private Bitmap floor;
	private Bitmap floor_Top;
	
	  private int bmp_x = 100, bmp_y = 100;
	private Bitmap bmp;
	private boolean UP, DOWN, LEFT, RIGHT;  
    private int animation_up[] = { 3, 4, 5 };  
    private int animation_down[] = { 0, 1, 2 };  
    private int animation_left[] = { 6, 7, 8 };  
    private int animation_right[] = { 9, 10, 11 };  
    private int animation_init[] = animation_down;  
    private int frame_count;  
	
	private int ScreenWidth;
	private int ScreenHeight;
	private int GameWidth;
	
	private boolean th_tag;
	private float Move_x;
	private float length;
	
	private final float RATE = 30; //屏幕到现实世界的比例 30px：1m;
	private World world; //物理世界
	private Vec2 gravity;// 声明一个重力向量对象
	private float timeStep = 1f/60f;// 物理世界模拟的的频率
	private int iterations = 10;// 迭代值，迭代越大模拟越精确，但性能越低
	private Body floorBody;//地面物体
	private Body bird;
	private Body person;
	float   polygonX=500;
	float   polygonY=10;
	float   polygonWidth=50;
	float   polygonHeight=50;
	 public static MainGameView instance=null;
	
	private boolean Iscan;
	private Handler han_can=new Handler();
	private Runnable run_can=new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stub
			if(Iscan)
			{
			Move_x-=2;
			if(Move_x<=10)
			{
				Iscan=false;
				han_can.removeCallbacks(run_can);
			}
			Move_x = Move_x<0?0:(Move_x>GameWidth-ScreenWidth?GameWidth-ScreenWidth:Move_x);
			if(Move_x==0)
			{
				han_can.removeCallbacks(run_can);
			}
			draw();
			han_can.postDelayed(this, 1);
			}
		}
	};
	public MainGameView(Context context ,AttributeSet attrs)
	{
		super(context,attrs);
		sfh=this.getHolder();
		sfh.addCallback(this);
		paint=new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		res=this.getResources();
		sfh=this.getHolder();
		sfh.addCallback(this);
		paint=new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		p_text=new Paint();
		p_text.setColor(Color.BLACK);
		p_text.setAntiAlias(true);
		floor=BitmapFactory.decodeResource(res, R.drawable.fg);
		floor_Top=BitmapFactory.decodeResource(res, R.drawable.bg);
		bmp=BitmapFactory.decodeResource(res, R.drawable.enemy1);
		gravity = new Vec2(0f, 5f);//重力
		world = new World(gravity, true);//世界
		bird=CreateBird();
		person=CreatePerson();
		this.setKeepScreenOn(true);//让屏幕保持常亮
        setClickable(true);//获得焦点
        instance=this;
	}
	public void run() {
		// TODO Auto-generated method stub
		while(th_tag)
		{
			draw();
			cycle();
			Logic();
			try{
				th.sleep((long)timeStep*1500);
			}catch(Exception e)
			{
				
			}
		}
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	
		
	}
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		ScreenHeight=this.getHeight();
		ScreenWidth=this.getWidth();
		GameWidth=floor_Top.getWidth();
		Move_x=GameWidth-ScreenWidth;
		floorBody = CreateFloorBody(0, ScreenHeight-floor.getHeight(), GameWidth,floor.getHeight());
		th_tag=true;
		th = new Thread(this);
		th.start();
		Iscan=true;
	    han_can.postDelayed(run_can, 10);
	    bmp_x=ScreenWidth-GameWidth;
		
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
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
	
	public void draw()
	{
		try{
			can=sfh.lockCanvas();
			if(can!=null)
			{
		   can.save();
		    can.drawColor(Color.WHITE);
		    can.drawBitmap(floor_Top,0-Move_x,0, paint);
			can.drawBitmap(floor,0-Move_x,ScreenHeight-floor.getHeight(), paint);
			can.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bird),300, polygonY, paint);
			can.drawText("StandOpen", bmp_x-20, ScreenHeight-floor.getHeight()-10, p_text);  
			can.clipRect(bmp_x,ScreenHeight-floor.getHeight(), bmp_x + bmp.getWidth() / 13,ScreenHeight-floor.getHeight()+bmp.getHeight());  
	        if (animation_init == animation_up) {  
	        	can.drawBitmap(bmp, bmp_x - animation_up[frame_count] * (bmp.getWidth() / 13),ScreenHeight-floor.getHeight(), paint);  
	        } else if (animation_init == animation_down) {  
	        	can.drawBitmap(bmp, bmp_x - animation_down[frame_count] * (bmp.getWidth() / 13),ScreenHeight-floor.getHeight(), paint);  
	        } else if (animation_init == animation_left) {  
	        	can.drawBitmap(bmp, bmp_x - animation_left[frame_count] * (bmp.getWidth() / 13),ScreenHeight-floor.getHeight(), paint);  
	        } else if (animation_init == animation_right) {  
	        	can.drawBitmap(bmp, bmp_x - animation_right[frame_count] * (bmp.getWidth() / 13),ScreenHeight-floor.getHeight(), paint);  
	        }  
	        
			can.restore();
			//sfh.unlockCanvasAndPost(can);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally { // 备注3  
            if (can != null)  
                sfh.unlockCanvasAndPost(can);  // 将画好的画布提交  
        }  
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
				length=Move_x+event.getX();
		}else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			
			
		}else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			Move_x = length-event.getX();
			Move_x = Move_x<0?0:(Move_x>GameWidth-ScreenWidth?GameWidth-ScreenWidth:Move_x);
		}
		return super.onTouchEvent(event);
	}
	
    public  void Buttonup(int i)
    {
    	switch(i)
    	{
    	case 1:
    	{
    		if (LEFT) {  
    			LEFT=false;
            }  
            
            break;
    	}
    	case 2:
    	{
    		  if (RIGHT) {  
    			  RIGHT=false;
              }  
           
              break;
    	}
    	case 3:
    	{
    		if (UP) {  
    			UP=false;
            }  
            
            break;
    	}
    	case 4:
    	{
    		  if (DOWN) {  
    			  DOWN=false;
              }  
           
              break;
    	}
    	}
    	
    }
    public  void Buttondown(int i)
    {
    	switch(i)
    	{
    	case 1:
    	{
    		if (LEFT == false) {  
                animation_init = animation_left;  
            }  
            LEFT = true;  
            break;
    	}
    	case 2:
    	{
    		  if (RIGHT == false) {  
                  animation_init = animation_right;  
              }  
              RIGHT = true; 
              break;
    	}
    	case 3:
    	{
    		if (UP == false) {  
                animation_init = animation_up;  
            }  
            UP = true;  
            break;
    	}
    	case 4:
    	{
    		  if (DOWN == false) {  
                  animation_init = animation_down;  
              }  
              DOWN = true; 
              break;
    	}
    	}
    	
    }    public  void ButtonUp(int i)
    {
    	switch(i)
    	{
    	case 1:
    	{
    		if (LEFT) {  
    			LEFT=false;
            }  
            
            break;
    	}
    	case 2:
    	{
    		  if (RIGHT) {  
    			  RIGHT=false;
              }  
           
              break;
    	}
    	case 3:
    	{
    		if (UP) {  
    			UP=false;
            }  
            
            break;
    	}
    	case 4:
    	{
    		  if (DOWN) {  
    			  DOWN=false;
              }  
           
              break;
    	}
    	}
    	
    }
    public  void ButtonDown(int i)
    {
    	switch(i)
    	{
    	case 1:
    	{
    		if (LEFT == false) {  
                animation_init = animation_left;  
            }  
            LEFT = true;  
            break;
    	}
    	case 2:
    	{
    		  if (RIGHT == false) {  
                  animation_init = animation_right;  
              }  
              RIGHT = true; 
              break;
    	}
    	case 3:
    	{
    		if (UP == false) {  
                animation_init = animation_up;  
            }  
            UP = true;  
            break;
    	}
    	case 4:
    	{
    		  if (DOWN == false) {  
                  animation_init = animation_down;  
              }  
              DOWN = true; 
              break;
    	}
    	}
    	
    }
    public void cycle() {  
        if (DOWN) {  
            bmp_y += 3;  
        } else if (UP) {  
            bmp_y -= 3;  
        } else if (LEFT) {  
            bmp_x -= 3;  
        } else if (RIGHT) {  
            bmp_x += 3;  
        }  
        if(bmp_x<0)
        {
        	bmp_x=0;
        }
        if(bmp_x>ScreenWidth)
        {
        	bmp_x-=3;
        	Move_x+=6;
        }
        if (DOWN || UP || LEFT || RIGHT) {  
        	   if(!Iscan)
               {
               if(bmp_x>ScreenWidth/2&&RIGHT)
               {
               	Move_x+=2;
               	bmp_x-=2;
               }
               else if(LEFT)
               {
               	Move_x-=2;
               	bmp_x+=2;
               }
               Move_x = Move_x<0?0:(Move_x>GameWidth-ScreenWidth?GameWidth-ScreenWidth:Move_x);
               }
            if (frame_count < 2) {  
                frame_count++;  
            } else {  
                frame_count = 0;  
            }  
        }  
        if (DOWN == false && UP == false && LEFT == false && RIGHT == false) {  
            frame_count = 0;  
        }  
     
    }  
    
    public Body CreateBird()
    {
    	PolygonShape ps = new PolygonShape();
    	ps.setAsBox(50/2/RATE, (50/2-40)/RATE);
    	
    	FixtureDef fd = new FixtureDef();
    	fd.shape = ps;
    	fd.density = 1;
    	fd.friction = 0.8f;
    	fd.restitution = 0.8f;
    	
    	BodyDef bd = new BodyDef();
    	bd.position.set((55/2)/RATE, (55/2)/RATE);
    	bd.type = BodyType.DYNAMIC;
    	
    	Body body = world.createBody(bd);
    	body.createFixture(fd);
    	
    	return body;
    }
    public void Logic(){
       // world.step(timeStep, iterations);
    	world.step(timeStep, iterations, 6);
        Vec2 position=bird.getPosition();
        polygonX=position.x*RATE-polygonWidth/2;
        polygonY=position.y*RATE-polygonHeight/2;
        Vec2 position1=person.getPosition();
       // bmp_x=(int)(position1.x*RATE-20/2);
        polygonY=position.y*RATE-polygonHeight/2;
        
        }
    public Body CreatePerson()
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
    	db.position.set(bmp_x/RATE, (ScreenHeight-floor.getHeight()-50f)/RATE);
    	db.type = BodyType.DYNAMIC;
    	db.bullet = true; //表示这是个高速运转的物体，需要精细的模拟
    	
    	Body body = world.createBody(db);
    	body.createFixture(fd);
    	return body;
    	
    }
}
