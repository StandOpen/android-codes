package game12.standopen;

import android.content.Context;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.view.SurfaceHolder;  
import android.view.SurfaceView;  
import android.view.SurfaceHolder.Callback;  
import android.view.animation.Animation;  

public class MySurfaceView extends SurfaceView implements Callback, Runnable {
// ��ע1  
    private SurfaceHolder sfh;  
    private Thread th;  
    private Canvas canvas;  
    private Paint paint;  
    private int ScreenW, ScreenH;  
    public MySurfaceView(Context context) {  
        super(context);  
        th = new Thread(this);  
        sfh = this.getHolder();  
        sfh.addCallback(this); // ��ע1  
        paint = new Paint();  
        paint.setAntiAlias(true);  
        paint.setColor(Color.RED);  
        this.setKeepScreenOn(true);// ������Ļ����  
    }  
    @Override  
    public void startAnimation(Animation animation) {  
        super.startAnimation(animation);  
    }  
    public void surfaceCreated(SurfaceHolder holder) {  
        ScreenW = this.getWidth();// ��ע2  
        ScreenH = this.getHeight();  
        th.start();  
        
    }  
    private void draw() {  
        try {  
            canvas = sfh.lockCanvas(); // �õ�һ��canvasʵ��  
            canvas.drawColor(Color.WHITE);// ˢ��  
            canvas.drawText("Himi", 100, 100, paint);// �������ı�  
            canvas.drawText("����Ǽ򵥵�һ����Ϸ���", 100, 130, paint);  
        } catch (Exception ex) {  
        } finally { // ��ע3  
            if (canvas != null)  
                sfh.unlockCanvasAndPost(canvas);  // �����õĻ����ύ  
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