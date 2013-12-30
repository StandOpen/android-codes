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
	LocationListener mLocationListener = null;//onResumeʱע���listener��onPauseʱ��ҪRemove
	MyLocationOverlay mLocationOverlay = null;	//��λͼ��
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
				// ����View�˳���Ļʱ��ʹ�õĶ���
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
        //���������Ŷ���������Ҳ��ʾoverlay,Ĭ��Ϊ������
        mMapView.setDrawOverlayWhenZooming(true);
        
        
        Info=(TextView)findViewById(R.id.address_text);
        
        // ��ʼ��MKSearch
        mMKSearch = new MKSearch();
        mMKSearch.init(mBMapMan, this);
 
		// ��Ӷ�λͼ��
        mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);
		
        // ע�ᶨλ�¼�
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
       /** * ������Ҳ����Ķ���Ч�� * @return */
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

      	/** * ���������˳��Ķ���Ч�� * @return */
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

      	/** * �����������Ķ���Ч�� * @return */
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

      	/** * ������Ҳ��˳�ʱ�Ķ���Ч�� * @return */
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
        mLocationOverlay.disableCompass(); // �ر�ָ����
		 mBMapMan.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		 
		// ע�ᶨλ�¼�����λ�󽫵�ͼ�ƶ�����λ��
         mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableCompass(); // ��ָ����
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
        // ��γ������Ӧ��λ��
        sb.append(arg0.strAddr).append("/n");

        // �жϸõ�ַ�����Ƿ���POI��Point of Interest,����Ȥ�㣩
        if (null != arg0.poiList) {
            // �������е���Ȥ����Ϣ
            for (MKPoiInfo poiInfo : arg0.poiList) {
                sb.append("----------------------------------------")
                        .append("/n");
                sb.append("���ƣ�").append(poiInfo.name).append("/n");
                sb.append("��ַ��").append(poiInfo.address).append("/n");
                sb.append("���ȣ�")
                        .append(poiInfo.pt.getLongitudeE6() / 1000000.0f)
                        .append("/n");
                sb.append("γ�ȣ�")
                        .append(poiInfo.pt.getLatitudeE6() / 1000000.0f)
                        .append("/n");
                sb.append("�绰��").append(poiInfo.phoneNum).append("/n");
                sb.append("�ʱࣺ").append(poiInfo.postCode).append("/n");
                // poi���ͣ�0����ͨ�㣬1������վ��2��������·��3������վ��4��������·
                sb.append("���ͣ�").append(poiInfo.ePoiType).append("/n");
            }
        }
        // ����ַ��Ϣ����Ȥ����Ϣ��ʾ��TextView��
        Info.setText(sb.toString());
        String str=sb.toString();
        int num=str.indexOf("�Ͳ����ŵ���");
        if(num>0)
        {
        	Info.append("�ѵ���");
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