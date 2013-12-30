package game03.standopen;

import android.content.Context;  
import android.content.res.Resources;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.util.AttributeSet;
import android.view.KeyEvent;  
import android.view.SurfaceHolder;  
import android.view.SurfaceView;  
import android.view.SurfaceHolder.Callback;  
public class MySurfaceView extends SurfaceView implements Callback, Runnable {  
    private Thread th = new Thread(this);  
    private SurfaceHolder sfh;  
    private int SH, SW;  
    private Canvas canvas;  
    private Paint p;  
    private Paint p2;  
    private Resources res;  
    private Bitmap bmp;  
    private Bitmap bg;
    private int bmp_x = 100, bmp_y = 100;  
    private boolean UP, DOWN, LEFT, RIGHT;  
    private int animation_up[] = { 3, 4, 5 };  
    private int animation_down[] = { 0, 1, 2 };  
    private int animation_left[] = { 6, 7, 8 };  
    private int animation_right[] = { 9, 10, 11 };  
    private int animation_init[] = animation_down;  
    private int frame_count;  
    public static MySurfaceView instance=null;
    private boolean IsRun;
	public MySurfaceView(Context context, AttributeSet attrs) {//备注1
		
		super(context, attrs);
        this.setKeepScreenOn(true);  
        res = this.getResources();  
        bmp = BitmapFactory.decodeResource(res, R.drawable.enemy1);  
        bg=BitmapFactory.decodeResource(res, R.drawable.floor);
        sfh = this.getHolder();  
        sfh.addCallback(this);  
        p = new Paint();  
        p.setColor(Color.WHITE);  
        p2 = new Paint();  
        p2.setColor(Color.RED);  
        p.setAntiAlias(true);  
        setFocusable(true);  //备注1
        instance=this;
    }  
    public void surfaceCreated(SurfaceHolder holder) {  
        SH = this.getHeight();  
        SW = this.getWidth();  
        IsRun=true;
        th.start();  
        
    }  
    public void draw() {  
    	try{
        canvas = sfh.lockCanvas();  
        canvas.drawRect(0, 0, SW, SH, p);   //备注2
        canvas.save();   //备注3
        canvas.drawBitmap(bg,0,0, p);
        canvas.drawText("StandOpen", bmp_x-20, bmp_y-10, p2);  
        canvas.clipRect(bmp_x, bmp_y, bmp_x + bmp.getWidth() / 13, bmp_y+bmp.getHeight());  
        if (animation_init == animation_up) {  
            canvas.drawBitmap(bmp, bmp_x - animation_up[frame_count] * (bmp.getWidth() / 13), bmp_y, p);  
        } else if (animation_init == animation_down) {  
            canvas.drawBitmap(bmp, bmp_x - animation_down[frame_count] * (bmp.getWidth() / 13), bmp_y, p);  
        } else if (animation_init == animation_left) {  
            canvas.drawBitmap(bmp, bmp_x - animation_left[frame_count] * (bmp.getWidth() / 13), bmp_y, p);  
        } else if (animation_init == animation_right) {  
            canvas.drawBitmap(bmp, bmp_x - animation_right[frame_count] * (bmp.getWidth() / 13), bmp_y, p);  
        }  
        canvas.restore();  //备注3
        sfh.unlockCanvasAndPost(canvas);  
    	}
    	catch(Exception e)
    	{
    		IsRun=false;
    	}
    }  
    public void cycle() {  
        if (DOWN) {  
            bmp_y += 5;  
        } else if (UP) {  
            bmp_y -= 5;  
        } else if (LEFT) {  
            bmp_x -= 5;  
        } else if (RIGHT) {  
            bmp_x += 5;  
        }  
        if (DOWN || UP || LEFT || RIGHT) {  
            if (frame_count < 2) {  
                frame_count++;  
            } else {  
                frame_count = 0;  
            }  
        }  
        if (DOWN == false && UP == false && LEFT == false && RIGHT == false) {  
            frame_count = 0;  
        }  
    }  
    @Override  
    public boolean onKeyDown(int key, KeyEvent event) {  
        if (key == KeyEvent.KEYCODE_DPAD_UP) {  
            if (UP == false) {  
                animation_init = animation_up;  
            }  
            UP = true;  
        } else if (key == KeyEvent.KEYCODE_DPAD_DOWN) {  
            if (DOWN == false) {  
                animation_init = animation_down;  
            }  
            DOWN = true;  
        } else if (key == KeyEvent.KEYCODE_DPAD_LEFT) {  
            if (LEFT == false) {  
                animation_init = animation_left;  
            }  
            LEFT = true;  
        } else if (key == KeyEvent.KEYCODE_DPAD_RIGHT) {  
            if (RIGHT == false) {  
                animation_init = animation_right;  
            }  
            RIGHT = true;  
        }  
        return super.onKeyDown(key, event);  
    }  
    /* (non-Javadoc) 
     * @see android.view.View#onKeyUp(int, android.view.KeyEvent) 
     */  
    @Override  
    public boolean onKeyUp(int keyCode, KeyEvent event) {  
        if (DOWN) {  
            DOWN = false;  
        } else if (UP) {  
            UP = false;  
        } else if (LEFT) {  
            LEFT = false;  
        } else if (RIGHT) {  
            RIGHT = false;  
        }  
        return super.onKeyUp(keyCode, event);  
    }  
    public void run() {  
        // TODO Auto-generated method stub  
        while (IsRun) {  
            draw();  
            cycle();  
            try {  
                Thread.sleep(100);  
            } catch (Exception ex) {  
            }  
        }  
    }  
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {  
        // TODO Auto-generated method stub  
    }  
    public void surfaceDestroyed(SurfaceHolder holder) {  
        // TODO Auto-generated method stub  
    }  
    public  void ButtonUp(int i)
    {
    	switch(i)
    	{
    	case 1:
    	{
    		if (LEFT) {  
    			LEFT=false;
            }  
            
            break;
    	}
    	case 2:
    	{
    		  if (RIGHT) {  
    			  RIGHT=false;
              }  
           
              break;
    	}
    	case 3:
    	{
    		if (UP) {  
    			UP=false;
            }  
            
            break;
    	}
    	case 4:
    	{
    		  if (DOWN) {  
    			  DOWN=false;
              }  
           
              break;
    	}
    	}
    	
    }
    public  void ButtonDown(int i)
    {
    	switch(i)
    	{
    	case 1:
    	{
    		if (LEFT == false) {  
                animation_init = animation_left;  
            }  
            LEFT = true;  
            break;
    	}
    	case 2:
    	{
    		  if (RIGHT == false) {  
                  animation_init = animation_right;  
              }  
              RIGHT = true; 
              break;
    	}
    	case 3:
    	{
    		if (UP == false) {  
                animation_init = animation_up;  
            }  
            UP = true;  
            break;
    	}
    	case 4:
    	{
    		  if (DOWN == false) {  
                  animation_init = animation_down;  
              }  
              DOWN = true; 
              break;
    	}
    	}
    	
    }
}  