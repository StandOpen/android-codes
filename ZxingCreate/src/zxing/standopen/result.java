package zxing.standopen;

import cn.domob.android.ads.DomobInterstitialAd;
import cn.domob.android.ads.DomobInterstitialAdListener;
import cn.domob.android.ads.DomobAdManager.ErrorCode;

import com.zijunlin.Zxing.Demo.CaptureActivity;

import zxing.moyan.index;
import ad.standopen.contact;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class result extends Activity {
    private EditText input=null;
    private Button ok=null;
    private Button clear=null;
    private Button more=null;
    private Button back=null;
    DomobInterstitialAd mInterstitialAd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.result);
        showad();
        input=(EditText)findViewById(R.id.input);
        ok=(Button)findViewById(R.id.make);
        clear=(Button)findViewById(R.id.clear);
        back=(Button)findViewById(R.id.btn_scanner);
        String info=this.getIntent().getStringExtra("info");
        input.setText(info);
        ok.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(input.getText().toString().length()>0)
				{
				Intent intent=new Intent();
				intent.putExtra("content", input.getText().toString());
				intent.setClass(result.this, Input.class);
				startActivity(intent);
				}
				else
				{
					Toast toast = Toast.makeText(getApplicationContext(),
							"输入不能为空！", Toast.LENGTH_LONG);
				      	   toast.setGravity(Gravity.CENTER, 0, 0);
				      	   LinearLayout toastView = (LinearLayout) toast.getView();
				      	   ImageView imageCodeProject = new ImageView(getApplicationContext());
				      	   imageCodeProject.setImageResource(R.drawable.fun);
				      	   toastView.addView(imageCodeProject, 0);
				      	   toast.show();
				}
			}
		});
        
        clear.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mInterstitialAd.isInterstitialAdReady()){
					mInterstitialAd.showInterstitialAd(result.this);
				} else {
					Log.i("DomobSDKDemo", "Interstitial Ad is not ready");
					mInterstitialAd.loadInterstitialAd();
				}
				try
				{
				ClipboardManager cmb = (ClipboardManager)
						 getSystemService(Context.CLIPBOARD_SERVICE);
						 cmb.setText(input.getText().toString());
						 Toast.makeText(result.this, "复制成功！", Toast.LENGTH_SHORT).show();
						 
				}
				catch(Exception e)
				{
					Toast.makeText(result.this, "复制失败！", Toast.LENGTH_SHORT).show();
				}
			}
		});
     
        back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }
    public void showad()
    {
    	mInterstitialAd = new DomobInterstitialAd(this,contact.PUBLISHER_ID,
				contact.InterstitialPPID, DomobInterstitialAd.INTERSITIAL_SIZE_300X250);	
		mInterstitialAd.setInterstitialAdListener(new DomobInterstitialAdListener() {
			public void onInterstitialAdReady() {
				Log.i("DomobSDKDemo", "onAdReady");
			}

			public void onLandingPageOpen() {
				Log.i("DomobSDKDemo", "onLandingPageOpen");
			}

			public void onLandingPageClose() {
				Log.i("DomobSDKDemo", "onLandingPageClose");
			}

			public void onInterstitialAdPresent() {
				Log.i("DomobSDKDemo", "onInterstitialAdPresent");
			}

			public void onInterstitialAdDismiss() {
				// Request new ad when the previous interstitial ad was closed.
				mInterstitialAd.loadInterstitialAd();
				Log.i("DomobSDKDemo", "onInterstitialAdDismiss");
			}

			public void onInterstitialAdFailed(ErrorCode arg0) {
				Log.i("DomobSDKDemo", "onInterstitialAdFailed");				
			}

			public void onInterstitialAdLeaveApplication() {
				Log.i("DomobSDKDemo", "onInterstitialAdLeaveApplication");
				
			}
		});
		
		mInterstitialAd.loadInterstitialAd();
	
    }
}
