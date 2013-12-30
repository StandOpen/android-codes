package light.standopen;

import java.io.InputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.View;


public class roateview  extends View {
	
	private int num=1;
	private Bitmap bitmap1;
	 private int digree1 = 0;// 扳手图像的当前角度
	   
	  
public roateview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.WHITE);
	    InputStream is = getResources().openRawResource(R.drawable.bagua_05);
	    bitmap1 = BitmapFactory.decodeStream(is);
	}
		
		   
		   @Override
		   protected void onDraw(Canvas canvas) {
			   Matrix matrix = new Matrix();
			    if (digree1 > 360)
			     digree1 = 0;
			    int width = bitmap1.getWidth(); 
		        int height = bitmap1.getHeight(); 
		        int px=getMeasuredWidth();
				int py=getMeasuredHeight();
			    // 设置扳手图像的旋转角度和旋转轴心坐标（后两个参数，注意这个坐标是相对于屏幕的），该轴心也是图像的正中心
				digree1+=num;
			    matrix.setRotate(digree1++,px/2,py/2);
			    canvas.setMatrix(matrix);
		      canvas.drawBitmap(bitmap1,(px-width)/2,(py-height)/2, null);
		    invalidate();
		   }
		   
		   public void setColor(int color)
		   {
			   setBackgroundColor(color);
			   invalidate();
		   }
		   
		   public void setbmp(int bmp)
		   {
			   InputStream is = getResources().openRawResource(bmp);
			    bitmap1 = BitmapFactory.decodeStream(is);
			    invalidate();
		   }
		   
		   public void AddDigree()
		   {
			   num+=2;
			   invalidate();
		   }
		   
		   public void DecDigree()
		   {
			   num-=2;
			   if(num<0)
				   num=0;
			   invalidate();
		   }
		 }
