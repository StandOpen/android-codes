package game10.standopen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public interface BodyInterface {
	float x=0; //x轴
	float y=0; //y轴
	float width=0; //宽度
	float height=0; //高度
	float angle=0; //角度
	Bitmap bitmap = null; //显示图片
	
	void draw(Canvas canvas,Paint paint,float move_X); //画图
	public void setX(float x); //设置x轴
	public void setY(float y); //设置y轴
	public void setAngle(float angle); //设置角度
	public float getX(); //获取x轴
	public float getY(); //获取y轴
	public float getWidth(); //获取宽度
	public float getHeight(); //获取高度
}
