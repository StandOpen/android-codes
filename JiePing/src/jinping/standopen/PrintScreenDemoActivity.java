package jinping.standopen;

import android.app.Activity;
import android.os.Bundle;

import java.io.File; 
import java.io.FileOutputStream; 
 
import android.graphics.Bitmap; 
import android.graphics.Bitmap.Config; 
import android.os.Environment; 
import android.view.Display; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.view.WindowManager; 
import android.widget.Button; 
import android.widget.LinearLayout; 
import android.widget.Toast; 
 
public class PrintScreenDemoActivity extends Activity { 
    private Button mButton; 
    private LinearLayout mLayout; 
    private int mPrintNum; 
    /** Called when the activity is first created. */ 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        LayoutInflater inf = this.getLayoutInflater(); 
        mLayout = (LinearLayout)inf.inflate(R.layout.main, null); 
        setContentView(mLayout); 
        mButton = (Button)findViewById(R.id.print_btn); 
        mButton.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                GetandSaveCurrentImage(); 
                mPrintNum++; 
                mButton.setText("截屏次数:"+mPrintNum); 
            } 
        }); 
    } 
    private void GetandSaveCurrentImage()    
    {    
        //1.构建Bitmap    
        WindowManager windowManager = getWindowManager();    
        Display display = windowManager.getDefaultDisplay();    
        int w = display.getWidth();    
        int h = display.getHeight();    
            
        Bitmap Bmp = Bitmap.createBitmap( w, h, Config.ARGB_8888 );        
            
        //2.获取屏幕    
        View decorview = this.getWindow().getDecorView();   
        decorview.setDrawingCacheEnabled(true);     
        Bmp = decorview.getDrawingCache();     
        String SavePath = getSDCardPath()+"/PrintScreenDemo/ScreenImage";  
        
        //3.保存Bitmap     
        try {    
            File path = new File(SavePath);    
            //文件    
            String filepath = SavePath + "/Screen_"+mPrintNum+".png";    
            File file = new File(filepath);    
            if(!path.exists()){    
                path.mkdirs();    
            }    
            if (!file.exists()) {    
                file.createNewFile();    
            }    
                
            FileOutputStream fos = null;    
            fos = new FileOutputStream(file);    
            if (null != fos) {    
                Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);    
                fos.flush();    
                fos.close();      
                    
                Toast.makeText(this, "截屏文件已保存至SDCard/PrintScreenDemo/ScreenImage/下", Toast.LENGTH_LONG).show();    
            }    
        
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }    
      
       /**  
        * 获取SDCard的目录路径功能  
        * @return  
        */  
    private String getSDCardPath(){  
        File sdcardDir = null;  
        //判断SDCard是否存在  
        boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);  
        if(sdcardExist){  
            sdcardDir = Environment.getExternalStorageDirectory();  
        }  
         
        return sdcardDir.toString();  
    }  
} 