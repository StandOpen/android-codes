package game.standopen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

@SuppressLint("ParserError")
public class GameView extends SurfaceView implements Callback, Runnable {

	private Resources res;
	private Paint paint;
	private Thread th;
	private SurfaceHolder sur;
	private Canvas can;
	private Bitmap bmp_bg;
	private float Sreen_Height;
	private float Sreen_Width;
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		res=this.getResources();
		paint=new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		sur=this.getHolder();
		sur.addCallback(this);
		this.setKeepScreenOn(true);
		LoadImage();
		draw();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Sreen_Height=this.getHeight();
		Sreen_Width=this.getWidth();
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}
	public void run() {
		// TODO Auto-generated method stub

	}
	public void LoadImage()
	{
		bmp_bg=BitmapFactory.decodeResource(res, R.drawable.bg);
	}
	
	
	public void draw()
	{
		can=sur.lockCanvas();
		if(can!=null)
		{
			can.drawColor(Color.WHITE);
			can.drawBitmap(bmp_bg,0,0,paint);
		}
	}
}
