package game2.standopen;

import android.content.Context;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.view.SurfaceHolder;  
import android.view.SurfaceView;  
import android.view.SurfaceHolder.Callback;  
import android.view.animation.Animation;  
/** 
 * @author Himi 
 */  
public class MySurfaceView extends SurfaceView implements Callback, Runnable {// 备注1  
    private SurfaceHolder sfh;  
    private Thread th;  
    private Canvas canvas;  
    private Paint paint;  
    private int ScreenW, ScreenH;  
    public MySurfaceView(Context context) {  
        super(context);  
        th = new Thread(this);  
        sfh = this.getHolder();  
        sfh.addCallback(this); // 备注1  
        paint = new Paint();  
        paint.setAntiAlias(true);  
        paint.setColor(Color.RED);  
        this.setKeepScreenOn(true);// 保持屏幕常亮  
    }  
    @Override  
    public void startAnimation(Animation animation) {  
        super.startAnimation(animation);  
    }  
    public void surfaceCreated(SurfaceHolder holder) {  
        ScreenW = this.getWidth();// 备注2  
        ScreenH = this.getHeight();  
        th.start();  
    }  
    private void draw() {  
        try {  
            canvas = sfh.lockCanvas(); // 得到一个canvas实例  
            canvas.drawColor(Color.WHITE);// 刷屏  
            canvas.drawText("Himi", 100, 100, paint);// 画文字文本  
            canvas.drawText("这就是简单的一个游戏框架", 100, 130, paint);  
        } catch (Exception ex) {  
        } finally { // 备注3  
            if (canvas != null)  
                sfh.unlockCanvasAndPost(canvas);  // 将画好的画布提交  
        }  
    }   
    public void run() {  
        while (true) {  
            draw();  
            try {  
                Thread.sleep(100);  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    }  
    public void surfaceChanged(SurfaceHolder holder, int format, int width,  
            int height) {  
    }  
    public void surfaceDestroyed(SurfaceHolder holder) {  
        // TODO Auto-generated method stub  
    }  
}  
