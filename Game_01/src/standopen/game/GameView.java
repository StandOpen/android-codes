package standopen.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class GameView extends View{

	private Paint Drawpaint;
	private Bitmap bmp;
	private Resources res;
	private int xpos=0;
	private int ypos=0;
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		res=context.getResources();
		bmp=BitmapFactory.decodeResource(res, R.drawable.ic_launcher);
		Drawpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		Drawpaint.setColor(Color.RED);
		Drawpaint.setStrokeWidth(1);
		Drawpaint.setStyle(Paint.Style.STROKE);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Rect rect=new Rect(xpos,ypos,xpos+20,ypos+20);
		canvas.drawBitmap(bmp,  null, rect,Drawpaint);
		
	}
   public void setvalues(int x,int y){
	   xpos=x;
	   ypos=y;
   }
	

}
