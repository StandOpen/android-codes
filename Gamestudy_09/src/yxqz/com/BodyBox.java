package yxqz.com;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class BodyBox {
	public float x,y,w,h;
	
	public void setType(int type) {
		this.type = type;
	}

	public int type=0;  //0 ÎªµØ°å
	
	public BodyBox(float x,float y,float w,float h){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
	}
	
	public void onDraw(Canvas canvas,Paint paint){
		canvas.drawRect(new RectF(x,y,x+w,y+h), paint);
	}
	
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public int getType() {
		return type;
	}

}
