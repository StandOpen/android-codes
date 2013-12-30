package zxing.moyan;

import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdView;
import cn.domob.android.ads.DomobAdManager.ErrorCode;
import zxing.standopen.R;
import zxing.standopen.about;
import ad.standopen.contact;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;


public class index extends Activity{
	
	RelativeLayout mAdContainer;
	DomobAdView mAdview320x50;
	

	private mybutton first=null;
	private mybutton second=null;
	private mybutton three=null;
	private mybutton four=null;
    private mybutton mysad=null;
	public static index instance = null;
	static final private int ABOUT=Menu.FIRST;
	static final private int CLOSE=Menu.FIRST+1;
	private Button back=null;
	
	 public  String msg,seat;
	 ApplicationInfo appInfo;
	 int i;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       requestWindowFeature(Window.FEATURE_NO_TITLE);  
    
        setContentView(R.layout.index);
        showbaner();
       back=(Button)findViewById(R.id.back);
        instance=this;

        first=(mybutton)findViewById(R.id.second);
        second=(mybutton)findViewById(R.id.first);
        three=(mybutton)findViewById(R.id.three);
        four=(mybutton)findViewById(R.id.four);
       mysad=(mybutton)findViewById(R.id.mysad);
        first.SetBmp(R.drawable.welcome_01);
        second.SetBmp(R.drawable.welcome_02);
        three.SetBmp(R.drawable.welcome_03);
        four.SetBmp(R.drawable.welcome_06);
        mysad.SetBmp(R.drawable.welcome_05);
        back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
       mysad.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(index.this,about.class);
			startActivity(intent);
		}
	});
        first.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("type",1);
				intent.setClass(index.this,Talk.class);
				startActivity(intent);
				
			}
		});
       second.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("type",2);
				intent.setClass(index.this,Talk.class);
				startActivity(intent);
				
			}
		});
     three.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.putExtra("type",3);
		intent.setClass(index.this,Talk.class);
		startActivity(intent);
		
	}
});
    four.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.putExtra("type",4);
		intent.setClass(index.this,Talk.class);
		startActivity(intent);
	
	}
});
	}
	
	
		
		 @Override
			protected void onResume() {

				super.onResume();
			}

			@Override
			protected void onDestroy() {
				
				super.onDestroy();
			}
			
		
		 public void showbaner()
		 {
			 mAdContainer = (RelativeLayout) findViewById(R.id.adcontainer);
				// Create ad view
				mAdview320x50 = new DomobAdView(this,contact.PUBLISHER_ID,contact.InlinePPID, DomobAdView.INLINE_SIZE_320X50);
				mAdview320x50.setKeyword("game");
				mAdview320x50.setUserGender("male");
				mAdview320x50.setUserBirthdayStr("2000-08-08");
				mAdview320x50.setUserPostcode("123456");

				mAdview320x50.setAdEventListener(new DomobAdEventListener() {
								
					public void onDomobAdReturned(DomobAdView adView) {
						Log.i("DomobSDKDemo", "onDomobAdReturned");				
					}

					public void onDomobAdOverlayPresented(DomobAdView adView) {
						Log.i("DomobSDKDemo", "overlayPresented");
					}

					public void onDomobAdOverlayDismissed(DomobAdView adView) {
						Log.i("DomobSDKDemo", "Overrided be dismissed");				
					}

					public void onDomobAdClicked(DomobAdView arg0) {
						Log.i("DomobSDKDemo", "onDomobAdClicked");				
					}

					public void onDomobAdFailed(DomobAdView arg0, ErrorCode arg1) {
						Log.i("DomobSDKDemo", "onDomobAdFailed");				
					}

					public void onDomobLeaveApplication(DomobAdView arg0) {
						Log.i("DomobSDKDemo", "onDomobLeaveApplication");				
					}

					public Context onDomobAdRequiresCurrentContext() {
						return index.this;
					}
				});
				
				mAdContainer.addView(mAdview320x50);
		 }
			
			
}
