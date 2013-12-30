package zxing.moyan;

import zxing.standopen.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

@SuppressLint({ "ParserError", "DrawAllocation", "DrawAllocation" })
public class mybutton extends Button{

	
	

	private Paint margainPaint;
	private Paint linePaint;
	@SuppressWarnings("unused")
	private int paperColor;
	@SuppressWarnings("unused")
	private float margain;
	private Bitmap bmp;
	public mybutton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public mybutton(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        // TODO Auto-generated constructor stub

        init();

}



public mybutton(Context context, AttributeSet attrs) {

        super(context, attrs);

        // TODO Auto-generated constructor stub

        init();

}

@Override
public boolean onTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	if(event.getAction()==MotionEvent.ACTION_DOWN)
	{
	linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
	linePaint.setColor(Color.GRAY);
	}
	if(event.getAction()==MotionEvent.ACTION_UP)
	{
	linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
	linePaint.setColor(Color.WHITE);
	}
	
	invalidate();
	return super.onTouchEvent(event);
}
	public void init()
	{
		Resources mtRes=getResources();
		bmp=BitmapFactory.decodeResource(mtRes, R.drawable.head_01);
		margainPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		margainPaint.setColor(Color.BLACK);
		linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(Color.WHITE);
		paperColor=Color.WHITE;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//canvas.drawColor(paperColor);
		//canvas.drawRect(10, 0, getMeasuredHeight(), getMeasuredHeight(), linePaint);
		
        linePaint.setAntiAlias(true);// 设置画笔的锯齿效果   
        RectF oval3 = new RectF(0, 0,getMeasuredWidth(), getMeasuredHeight());// 设置个新的长方形  
        canvas.drawRoundRect(oval3, 5, 5, linePaint);//第二个参数是x半径，第三个参数是y半径  
		canvas.drawText("＞",getMeasuredWidth()-30,getMeasuredHeight()/2,margainPaint);
		Rect rect=new Rect(2,2,getMeasuredHeight()-2,getMeasuredHeight()-2);
		canvas.drawBitmap(bmp,null, rect, linePaint);
		canvas.save();
		canvas.translate(30, 0);
		super.onDraw(canvas);
		canvas.restore();
	}

	@Override
	public boolean isPressed() {
		// TODO Auto-generated method stub
		return super.isPressed();
	}
   public void SetBmp(int bitmap)
   {
	   Resources mtRes=getResources();
		bmp=BitmapFactory.decodeResource(mtRes,bitmap);
     	invalidate();
	}
	
	
	
}
