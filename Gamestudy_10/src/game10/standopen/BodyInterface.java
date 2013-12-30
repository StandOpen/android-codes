package game10.standopen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public interface BodyInterface {
	float x=0; //x��
	float y=0; //y��
	float width=0; //���
	float height=0; //�߶�
	float angle=0; //�Ƕ�
	Bitmap bitmap = null; //��ʾͼƬ
	
	void draw(Canvas canvas,Paint paint,float move_X); //��ͼ
	public void setX(float x); //����x��
	public void setY(float y); //����y��
	public void setAngle(float angle); //���ýǶ�
	public float getX(); //��ȡx��
	public float getY(); //��ȡy��
	public float getWidth(); //��ȡ���
	public float getHeight(); //��ȡ�߶�
}
