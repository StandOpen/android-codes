package ad.standopen;
import zxing.standopen.R;
import zxing.standopen.ZxingCreate;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.domob.android.ads.DomobRTSplashAd;
import cn.domob.android.ads.DomobRTSplashAdListener;
import cn.domob.android.ads.DomobSplashAd;
import cn.domob.android.ads.DomobSplashAd.DomobSplashMode;
import cn.domob.android.ads.DomobSplashAdListener;

public class SplashScreen extends Activity {
	DomobSplashAd splashAd;
	DomobRTSplashAd rtSplashAd;
	private boolean isSplash = false;
   private RelativeLayout layout=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);       	    
		   requestWindowFeature(Window.FEATURE_NO_TITLE);  
		    
		setContentView(R.layout.splash);
	
	
	
		if (isSplash) {
			splashAd = new DomobSplashAd(this,contact.PUBLISHER_ID,contact.SplashPPID,
					DomobSplashMode.DomobSplashModeFullScreen);
			splashAd.setSplashAdListener(new DomobSplashAdListener() {
				public void onSplashPresent() {
					Log.i("DomobSDKDemo", "onSplashStart");
				}
	
				public void onSplashDismiss() {
					Log.i("DomobSDKDemo", "onSplashClosed");
					jump();
				}
	
				public void onSplashLoadFailed() {
					Log.i("DomobSDKDemo", "onSplashLoadFailed");
				}
			});
	
			new Handler().postDelayed(new Runnable() {
				public void run() {
					if (splashAd.isSplashAdReady()) {
						splashAd.splash(SplashScreen.this, SplashScreen.this.findViewById(R.id.splash_holder));
					} else {
						Toast.makeText(SplashScreen.this, "Splash ad is NOT ready.", Toast.LENGTH_SHORT).show();
						jump();
					}
				}
			}, 1);
		} else {
			rtSplashAd = new DomobRTSplashAd(this,contact.PUBLISHER_ID,contact.SplashPPID,
					DomobSplashMode.DomobSplashModeFullScreen);
			rtSplashAd.setRTSplashAdListener(new DomobRTSplashAdListener() {
				public void onRTSplashDismiss() {
					Log.i("DomobSDKDemo", "onRTSplashClosed");
					jump();
		
				}

				public void onRTSplashLoadFailed() {
					Log.i("DomobSDKDemo", "onRTSplashLoadFailed");
				}

				public void onRTSplashPresent() {
					Log.i("DomobSDKDemo", "onRTSplashStart");
				}
				
			});
			
			new Handler().postDelayed(new Runnable() {
				public void run() {
					rtSplashAd.splash(SplashScreen.this, SplashScreen.this.findViewById(R.id.splash_holder));
				}
			}, 1);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("DomobSDKDemo", "Splash onPause");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("DomobSDKDemo", "Splash onDestroy");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void jump() {
		startActivity(new Intent(SplashScreen.this,ZxingCreate.class));
		finish();
	}
}
