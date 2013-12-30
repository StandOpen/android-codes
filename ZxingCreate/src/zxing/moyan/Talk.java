package zxing.moyan;

import java.util.ArrayList;

import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdView;
import cn.domob.android.ads.DomobAdManager.ErrorCode;
import zxing.standopen.R;
import ad.standopen.contact;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Talk extends Activity {
	private ListView talkView;
	private Button backbutton=null;
	private ArrayList<DetailEntity> list = null;
	private int images[]={R.drawable.bk_01,R.drawable.bk_10,R.drawable.bk_13,R.drawable.bk_16};
	RelativeLayout layout=null;
	private String []array;
	private TextView title=null;
	RelativeLayout mAdContainer;
	DomobAdView mAdview320x50;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.talk);
        
            

        showbaner();
      
                
                
                
               
                
                
         talkView = (ListView)findViewById(R.id.list);
                   
        layout=(RelativeLayout)findViewById(R.id.talk_layout);
        title=(TextView)findViewById(R.id.title);
        Resources res =getResources();
        int type=this.getIntent().getIntExtra("type",1);
        layout.setBackgroundResource(images[type-1]);
        switch(type){
        case 1:
        	array=res.getStringArray(R.array.qianming);
        	title.setText("个性签名");
        	break;
        case 2:
        	array=res.getStringArray(R.array.love);
        	title.setText("爱情疗伤");
        	break;
        case 3:
        	array=res.getStringArray(R.array.beautiful);
        	title.setText("唯美语句");
        	break;
        case 4:
        	array=res.getStringArray(R.array.old);
        	title.setText("婉约古风");
        	break;
        }
        
        
        Toast toast = Toast.makeText(getApplicationContext(),
       	     "点击文字分享", Toast.LENGTH_LONG);
       	   toast.setGravity(Gravity.CENTER, 0, 0);
       	   LinearLayout toastView = (LinearLayout) toast.getView();
       	   ImageView imageCodeProject = new ImageView(getApplicationContext());
       	   imageCodeProject.setImageResource(R.drawable.fun);
       	   toastView.addView(imageCodeProject, 0);
       	   toast.show();
        
        
       
        list = new ArrayList<DetailEntity>();
		
        

        backbutton=(Button)findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
        int number=0;
		
		for(int i=0;i<array.length;i++)
		{
			if(i%2==0)
			{
				number=i+1;
				DetailEntity temp= new DetailEntity(number,""+number+":"+"  伤感只是情绪一时的宣泄",array[i],R.layout.list_say_me_item);
				list.add(temp);
			}
			else
			{
				number=i+1;
				DetailEntity temp= new DetailEntity(number,""+number+":"+"  而永远不能是生活的态度",array[i],R.layout.list_say_he_item);
				list.add(temp);
			}
		}
		talkView.setAdapter(new DetailAdapter(Talk.this, list));
		
        
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
					return Talk.this;
				}
			});
			
			mAdContainer.addView(mAdview320x50);
	 }
	
}