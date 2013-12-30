package arrive.standopen;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiInfo;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import android.location.Location;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class TipForArrive extends MapActivity implements MKSearchListener{
    /** Called when the activity is first created. */
	MapView mMapView = null;
	LocationListener mLocationListener = null;//onResume时注册此listener，onPause时需要Remove
	MyLocationOverlay mLocationOverlay = null;	//定位图层
	BMapManager mBMapMan = null;
	private MKSearch mMKSearch;
	TextView Info;
	private ViewFlipper mFlipper;
	ImageButton returnbutton=null;
	ImageButton map=null;
	ImageButton add=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
         
        setContentView(R.layout.main);
      
        
        returnbutton=(ImageButton)findViewById(R.id.returnbutton);
        map=(ImageButton)findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFlipper.setInAnimation(inFromRightAnimation());
				// 设置View退出屏幕时候使用的动画
				mFlipper.setOutAnimation(outToLeftAnimation());
				
				mFlipper.showNext();
				
			}
		});
        
        returnbutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFlipper.setInAnimation(inFromLeftAnimation());
				mFlipper.setOutAnimation(outToRightAnimation());
			
				mFlipper.showPrevious();
				
			}
		});
        mBMapMan = new BMapManager(getApplication());
        mBMapMan.init("D34BB8CA4F7C3947ACBCC6A186D4BC03F5916492", null);
        super.initMapActivity(mBMapMan);
        
        mMapView = (MapView)findViewById(R.id.bmapView);
        mMapView.setBuiltInZoomControls(true);
        //设置在缩放动画过程中也显示overlay,默认为不绘制
        mMapView.setDrawOverlayWhenZooming(true);
        
        
        Info=(TextView)findViewById(R.id.address_text);
        
        // 初始化MKSearch
        mMKSearch = new MKSearch();
        mMKSearch.init(mBMapMan, this);
 
		// 添加定位图层
        mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);
		
        // 注册定位事件
        mLocationListener = new LocationListener(){

			public void onLocationChanged(Location location) {
				if (location != null){
					GeoPoint pt = new GeoPoint((int)(location.getLatitude()*1e6),
							(int)(location.getLongitude()*1e6));
					mMapView.getController().animateTo(pt);
					mMKSearch.reverseGeocode(pt);
					
				}
			}
        };
        mFlipper=(ViewFlipper)findViewById(R.id.details);
       
   		mFlipper.setLongClickable(true);

       }
       /** * 定义从右侧进入的动画效果 * @return */
      	protected Animation inFromRightAnimation() {
      		Animation inFromRight = new TranslateAnimation(
      				Animation.RELATIVE_TO_PARENT, +1.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f);
      		inFromRight.setDuration(500);
      		inFromRight.setInterpolator(new AccelerateInterpolator());
      		return inFromRight;
      	}

      	/** * 定义从左侧退出的动画效果 * @return */
      	protected Animation outToLeftAnimation() {
      		Animation outtoLeft = new TranslateAnimation(
      				Animation.RELATIVE_TO_PARENT, 0.0f,
      				Animation.RELATIVE_TO_PARENT, -1.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f);
      		outtoLeft.setDuration(500);
      		outtoLeft.setInterpolator(new AccelerateInterpolator());
      		return outtoLeft;
      	}

      	/** * 定义从左侧进入的动画效果 * @return */
      	protected Animation inFromLeftAnimation() {
      		Animation inFromLeft = new TranslateAnimation(
      				Animation.RELATIVE_TO_PARENT, -1.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f);
      		inFromLeft.setDuration(500);
      		inFromLeft.setInterpolator(new AccelerateInterpolator());
      		return inFromLeft;
      	}

      	/** * 定义从右侧退出时的动画效果 * @return */
      	protected Animation outToRightAnimation() {
      		Animation outtoRight = new TranslateAnimation(
      				Animation.RELATIVE_TO_PARENT, 0.0f,
      				Animation.RELATIVE_TO_PARENT, +1.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f,
      				Animation.RELATIVE_TO_PARENT, 0.0f);
      		outtoRight.setDuration(500);
      		outtoRight.setInterpolator(new AccelerateInterpolator());
      		return outtoRight;
      	}

	@Override
	protected void onPause() {
		
		 mBMapMan.getLocationManager().removeUpdates(mLocationListener);
		mLocationOverlay.disableMyLocation();
        mLocationOverlay.disableCompass(); // 关闭指南针
		 mBMapMan.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		 
		// 注册定位事件，定位后将地图移动到定位点
         mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableCompass(); // 打开指南针
		 mBMapMan.start();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg0== null) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        // 经纬度所对应的位置
        sb.append(arg0.strAddr).append("/n");

        // 判断该地址附近是否有POI（Point of Interest,即兴趣点）
        if (null != arg0.poiList) {
            // 遍历所有的兴趣点信息
            for (MKPoiInfo poiInfo : arg0.poiList) {
                sb.append("----------------------------------------")
                        .append("/n");
                sb.append("名称：").append(poiInfo.name).append("/n");
                sb.append("地址：").append(poiInfo.address).append("/n");
                sb.append("经度：")
                        .append(poiInfo.pt.getLongitudeE6() / 1000000.0f)
                        .append("/n");
                sb.append("纬度：")
                        .append(poiInfo.pt.getLatitudeE6() / 1000000.0f)
                        .append("/n");
                sb.append("电话：").append(poiInfo.phoneNum).append("/n");
                sb.append("邮编：").append(poiInfo.postCode).append("/n");
                // poi类型，0：普通点，1：公交站，2：公交线路，3：地铁站，4：地铁线路
                sb.append("类型：").append(poiInfo.ePoiType).append("/n");
            }
        }
        // 将地址信息、兴趣点信息显示在TextView上
        Info.setText(sb.toString());
        String str=sb.toString();
        int num=str.indexOf("淄博市张店区");
        if(num>0)
        {
        	Info.append("已到达");
        }
       // Toast.makeText(LocationOverlay.this, sb.toString(), Toast.LENGTH_SHORT).show();
	}
	public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	public void onGetRGCShareUrlResult(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	

}