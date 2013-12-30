package video.standopen;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MyVideo extends Activity implements OnClickListener,SurfaceHolder.Callback{    
    String path = "/sdcard/222.3gp";
    Button play_button;
    Button pause_button;
    Button record_button;
    boolean isPause = false;
    SurfaceView mySurfaceView;
    MediaPlayer myMediaPlayer;
    SurfaceHolder surfaceHolder;
    private static final int REQUEST_CODE_TAKE_VIDEO = 2;// 摄像的照相的requestCode
    String strVideoPath="/sdcard/222.3gp";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);   
                requestWindowFeature(Window.FEATURE_NO_TITLE);  
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        play_button = (Button)findViewById(R.id.myButton);
        play_button.setOnClickListener(this);
        pause_button = (Button)findViewById(R.id.myButton2);
        pause_button.setOnClickListener(this);
        record_button=(Button)findViewById(R.id.myButton3);
        
        getWindow().setFormat(PixelFormat.UNKNOWN);
        mySurfaceView = (SurfaceView)findViewById(R.id.mySurfaceView);
        surfaceHolder = mySurfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFixedSize(176,144);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        myMediaPlayer = new MediaPlayer();
        record_button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				videoMethod();
			}
		});
        
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==play_button)
		{
			isPause = false;
			System.out.println(strVideoPath);
			playVideo(strVideoPath);
		}
		else if(v==pause_button){
			if(isPause == false){
				myMediaPlayer.pause();
				isPause = true;
			}
			else{
				myMediaPlayer.start();
				isPause = false;
			}
		}		
	}
	private void playVideo(String strPath){
		if(myMediaPlayer.isPlaying()==true){
			myMediaPlayer.reset();
		}
		myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		myMediaPlayer.setDisplay(surfaceHolder);//设置Video影片以SurfaceHolder播放
		try{
			myMediaPlayer.setDataSource(strPath);
			myMediaPlayer.prepare();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		myMediaPlayer.start();
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
	
	 private void videoMethod() {
         Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
         intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
         startActivityForResult(intent, REQUEST_CODE_TAKE_VIDEO);
 }
	 
	 @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
             super.onActivityResult(requestCode, resultCode, data);
             switch (requestCode) {
             
             case REQUEST_CODE_TAKE_VIDEO://拍摄视频
                     if (resultCode == RESULT_OK) {
                             Uri uriVideo = data.getData();
                             Cursor cursor=this.getContentResolver().query(uriVideo, null, null, null, null);
                             if (cursor.moveToNext()) {
                                     /** _data：文件的绝对路径 ，_display_name：文件名 */
                                     strVideoPath = cursor.getString(cursor.getColumnIndex("_data"));
                                     Toast.makeText(this, strVideoPath, Toast.LENGTH_SHORT).show();
                             }
                     }
                     break;
         
             }
     }
}
