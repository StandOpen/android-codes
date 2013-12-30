package contact.standopen;

import java.util.ArrayList;

import adapter.standopen.ImageInfo;
import adapter.standopen.MyPagerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MakeContactHead extends Activity {
	ArrayList<ImageInfo> data;
    private ImageView test=null;
    private ImageButton search=null;
    private Dialog mDialog;
    private RelativeLayout layout_net=null;
    private ViewPager vpager;
    public static MakeContactHead instance;
    
    private String cate_names[]={"I T / ���� / ����","�鱦 / ��Ʒ","��� / Ьñ","ͯװ / ��� / ĸӤ","ʳƷ / ��Ʒ","���� / �칫 / ����","�Ҿ� / ��װ / ����","��װ / ����","���õ���","������Ʒ / ���"};
   // private int cate_images[]={R.drawable.it_normal,R.drawable.jewlery_normal,R.drawable.box_normal,
   // 		R.drawable.child_normal,R.drawable.food_normal,R.drawable.work_normal,R.drawable.gear_normal,R.drawable.underwear_normal,R.drawable.electrical_normal,R.drawable.car_normal};
  @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	instance=this;
	data = new ArrayList<ImageInfo>();
	vpager = (ViewPager) findViewById(R.id.vPager);
		initData();
}
  
  private void initData() {
		
	 
	   for(int i=0;i<cate_names.length;i++)
	   {
		   data.add(new ImageInfo(cate_names[i],R.drawable.ic_launcher));
	   }
	    vpager.setAdapter(new MyPagerAdapter(MakeContactHead.this, data));
		
		
		
	}
}