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
 * ��Ϸ���� �������һ������
 * @author mahaile
 *
 */
public class WorldBodySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	
	final static int FPS=30;       //����֡��
	MyThread myThread;  //ui�߳�
	SurfaceHolder surfaceHolder;  
	Canvas canvas;
	Paint paint;
	int screenW,screenH;
	
	private final float RATE=10.0f; //������������Ļ�������ű���
	World world;   //��Ϸ�������  
	float timeStep = 1f / 60f;;  //ģ���Ƶ��
	// ����ֵ������Խ��ģ��Խ��ȷ��������Խ�� 
	int velocityIterations = 10;	
	int positionIterations = 8;
	
	public WorldBodySurfaceView(Context context) {
		super(context);
		surfaceHolder=this.getHolder();
		surfaceHolder.addCallback(this);  //��ӻص�
		
		Vec2 vect=new Vec2(0f,10.0f); //���� ������ʾ��ǰ������������򣬵�һ������Ϊˮƽ���򣬸���Ϊ������Ϊ�ҡ��ڶ���������ʾ��ֱ����
		world=new World(vect,true);  //��ʼ����Ϸ����  ����ע��һ�� jbox2d 2.1 �Ժ�û�� aabb��Ϸ��Χ ������

		//���û���
		paint=new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.BLUE);
	}

	
	public void onDraw(Canvas canvas){
		//Log.d("888","11111111111111111111");
		canvas.drawColor(Color.WHITE);  //����
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
		/**����6�����Σ�isStatic����Ϊfalse�����������������Ƕ�̬������������Ӱ�� */
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
		// --��ʼģ����������--->>
		world.step(timeStep,velocityIterations, positionIterations);
		
		/**�������������е�body������������������ֵ��������Ļ��
		 * �ı�bird��rect�Ĳ���
		 * */
		
		Body body = world.getBodyList();	
		for (int i = 1; i < world.getBodyCount(); i++) {
			if ((body.m_userData) instanceof BodyBox) {
				BodyBox rect = (BodyBox) (body.m_userData);
				rect.setX(body.getPosition().x * RATE - (rect.getW()/2));
				rect.setY(body.getPosition().y * RATE - (rect.getH()/2));
				//rect.setAngle((float)(body.getAngle()*180/Math.PI));
			}else // body.m_userData==nullʱ����body���٣���ʾ������
			{
				world.destroyBody(body);
			}
			body = body.m_next;
		}
	}
	
	/**
	 *  ����Բ��
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic �Ƿ�Ϊ��̬
	 * @return body
	 */
	public Body createCircle(float x,float y,float r,boolean isStatic){
		CircleShape shape=new CircleShape();  //����һ��Բ�ζ���
		shape.m_radius=r/RATE;  //����Բ�εİ뾶 ���ð뾶��Ҫ����Ļ�Ĵ�С����ת���������С�Ĳ���
		
		FixtureDef fDef=new FixtureDef();  //�����������Զ��� ��Ϸ�������ݴ˶��� ��������
		if(isStatic){
			fDef.density=0;  //������Ʒ���ܶ� ���ܶ�Ϊ0 ʱ ��Ʒ��������������Ӱ��
		}else{
			fDef.density=1;  //������Ʒ���ܶ� ���ܶȲ�Ϊ0 ʱ ��Ʒ������������Ӱ��
		}
		fDef.friction=1.0f;  //������Ʒ��Ħ����  0~1
		fDef.restitution=0.5f;  //��������ĵ���  ֵԽ�� ���Ծ�Խǿ  ֵ��ΧΪ 0~1
		fDef.shape=shape;
		
		BodyDef bodyDef=new BodyDef();//����һ����������� ���� ���� ��̬��λ�ã���ת�Ƕȵ�
		bodyDef.type=isStatic?BodyType.STATIC:BodyType.DYNAMIC;
		bodyDef.position.set(x/RATE, y/RATE);
		
		//��������
		Body body=world.createBody(bodyDef);   //���ݸ������Զ�������Ϸ��������Ӹ���
		body.createFixture(fDef);          //���ø������������
		return body;
	}
	
	/**
	 *  ����������
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param isStatic �Ƿ�Ϊ��̬
	 * @return body
	 */
	public Body createPolygon(float x,float y,float width,float height,float restitution,boolean isStatic){
		PolygonShape shape=new PolygonShape();  //����һ��Բ�ζ���
		shape.setAsBox(width/2/RATE, height/2/RATE);  //����Բ�εİ뾶 ���ð뾶��Ҫ����Ļ�Ĵ�С����ת���������С�Ĳ���
		
		FixtureDef fDef=new FixtureDef();  //�����������Զ��� ��Ϸ�������ݴ˶��� ��������
		if(isStatic){
			fDef.density=0;  //������Ʒ���ܶ� ���ܶ�Ϊ0 ʱ ��Ʒ��������������Ӱ��
		}else{
			fDef.density=1;  //������Ʒ���ܶ� ���ܶȲ�Ϊ0 ʱ ��Ʒ������������Ӱ��
		}
		fDef.friction=0.0f;  //������Ʒ��Ħ����  0~1
		Log.d("--------", "***"+restitution);
		fDef.restitution=restitution;  //��������ĵ���  ֵԽ�� ���Ծ�Խǿ  ֵ��ΧΪ 0~1
		fDef.shape=shape;
		
		BodyDef bodyDef=new BodyDef();//����һ����������� ���� ���� ��̬��λ�ã���ת�Ƕȵ�
		bodyDef.type=isStatic?BodyType.STATIC:BodyType.DYNAMIC;
		bodyDef.position.set((x+width/2)/RATE,(y+height/2)/RATE);
		//��������
		Body body=world.createBody(bodyDef);   //���ݸ������Զ�������Ϸ��������Ӹ���
		body.createFixture(fDef);          //���ø������������
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
		/**����6�����Σ�isStatic����Ϊfalse�����������������Ƕ�̬������������Ӱ�� */
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
