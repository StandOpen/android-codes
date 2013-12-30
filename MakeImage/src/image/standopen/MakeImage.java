package image.standopen;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MakeImage extends Activity {
  private ImageView test=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        test=(ImageView)findViewById(R.id.test);
        Bitmap src=BitmapFactory.decodeResource(this.getResources(), R.drawable.wordbg);
        Bitmap mark=BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
        test.setImageBitmap(createBitmap(src,"Á¢"));
    }
    
    
    



private Bitmap createBitmap( Bitmap src,String name)

{
	
if( src == null )
{
return null;
}
Paint paint=new Paint();
paint.setColor(Color.BLACK);
paint.setAntiAlias(true);
paint.setTextSize(40);
int w = src.getWidth();
int h = src.getHeight();
Bitmap newb = Bitmap.createBitmap( w, h, Config.ARGB_8888 );
Canvas cv = new Canvas( newb );
cv.drawBitmap( src, 0, 0, null );
FontMetrics fontMetrics = paint.getFontMetrics();
cv.drawText(name, w/2-20, h/2+15, paint);
cv.save( Canvas.ALL_SAVE_FLAG );
cv.restore();//´æ´¢
return newb;

}


}