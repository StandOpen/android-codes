package game06.standopen;

import java.util.Random;
import java.util.Vector;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
public class MySurfaceViee extends SurfaceView implements Callback, Runnable {
	private Thread th;
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	public static int screenW, screenH;
	private Vector<MyArc> vc;//���ﶨ��װ�����Զ���Բ�ε�����
	private Random ran;//�漴��
	public MySurfaceViee(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		vc = new Vector<MyArc>();
		ran = new Random();//��ע1
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
	}
	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;//���ﶼ����һƪ�ս����ġ�����
		th = new Thread(this);
		screenW = this.getWidth();
		screenH = this.getHeight();
		th.start();
		vc.addElement(new MyArc(ran.nextInt(this.getWidth()), ran.nextInt(100), ran.nextInt(50)));
	}
	public void draw() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK);
			if (vc != null) {//��������Ϊ�գ���������������Բ�λ�����
				for (int i = 0; i < vc.size(); i++) {
					vc.elementAt(i).drawMyArc(canvas, paint);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			} catch (Exception e2) {
			}
		}
	}
	private void logic() {//���߼�
		if (vc != null) {//��������Ϊ�գ���������������Բ���߼�
			for (int i = 0; i < vc.size(); i++) {
				vc.elementAt(i).logic();
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//�������¼���Ӧ���������������Ը����ǵ�Բ��ʵ��
		vc.addElement(new MyArc(ran.nextInt(this.getWidth()), ran.nextInt(100), ran.nextInt(50)));
		return true;
	}
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			logic();
			draw();
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
			}
		}
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.v("Himi", "surfaceChanged");
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}
}
