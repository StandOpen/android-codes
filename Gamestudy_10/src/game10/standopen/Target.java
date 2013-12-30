package game10.standopen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Target implements BodyInterface {

	public float x, y, width, height, angle;
	// ×Óµ¯Í¼Æ¬
	private Bitmap bmp;
	
	public Target(Bitmap bitmap, float x, float y, float width,float height, float angle) {
		this.bmp = bitmap;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angle = angle;
	}
	public void draw(Canvas canvas, Paint paint, float move_X) {
		canvas.save();
		canvas.rotate(this.angle, x - move_X + width / 2, y + height / 2);
		canvas.drawBitmap(this.bmp, this.x - move_X, this.y, paint);
		canvas.restore();
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

}
