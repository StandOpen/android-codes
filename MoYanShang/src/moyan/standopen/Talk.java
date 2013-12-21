package moyan.standopen;

import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Talk extends Activity {
	private ListView talkView;
	private ImageButton sharebutton=null;
	private ImageButton backbutton=null;
	private ArrayList<DetailEntity> list = null;
	private int images[]={R.drawable.bk_01,R.drawable.bk_10,R.drawable.bk_13,R.drawable.bk_16};
	RelativeLayout layout=null;
	private String []array;
	private TextView title=null;
	RelativeLayout populayout=null;
	private String texts[]  = null;
    private int    gridimages[] = null;
    @SuppressLint("ParserError")
	private boolean isopen=false;
    @SuppressLint("ParserError")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.talk);
        
            

        
        gridimages=new int[]
                {
        		R.drawable.home, 
        		R.drawable.about,
        		R.drawable.share,
        		R.drawable.more 
        		
                };

                texts = new String[]
                { 
        		"欢迎页", 
        		"关于",
        		"分享",
        		"更多"
                 };
        
                GridView gridview = (GridView) findViewById(R.id.gridview);
                
                
                
                
                ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        		
            	for (int i = 0; i < 4; i++)
            	{		
            	    HashMap<String, Object> map = new HashMap<String, Object>();
            	    map.put("itemImage", gridimages[i]);		
            	    map.put("itemText",  texts[i]);
            			
            	    lstImageItem.add(map);
            	}

                    SimpleAdapter saImageItems = new SimpleAdapter(this, 
            						lstImageItem,// 数据源
            						R.layout.item,// 显示布局
            						new String[] { "itemImage", "itemText" }, 
            						new int[] { R.id.itemImage, R.id.itemText }); 
                    
                     gridview.setAdapter(saImageItems);

                     gridview.setOnItemClickListener(new ItemClickListener());
                
                
                     talkView = (ListView)findViewById(R.id.list);
                   
        layout=(RelativeLayout)findViewById(R.id.talk_layout);
        title=(TextView)findViewById(R.id.title);
		populayout=(RelativeLayout)findViewById(R.id.popumenu);
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
       	   imageCodeProject.setImageResource(R.drawable.menu_about);
       	   toastView.addView(imageCodeProject, 0);
       	   toast.show();
        
        
       
        list = new ArrayList<DetailEntity>();
		
        
sharebutton=(ImageButton)findViewById(R.id.share);
        
        sharebutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isopen)
				{
				populayout.setVisibility(1);
				sharebutton.setBackgroundResource(R.drawable.menu);
				isopen=true;
				}
				else
				{
					populayout.setVisibility(8);
					sharebutton.setBackgroundResource(R.drawable.sharebutton);
					isopen=false;
				}
				}
			
		});
        backbutton=(ImageButton)findViewById(R.id.back);
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
		talkView.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(isopen)
				{
					populayout.setVisibility(8);
					sharebutton.setBackgroundResource(R.drawable.sharebutton);
					isopen=false;
				}
				return false;
			}
		});
        
    }
    
    
    
    class ItemClickListener implements OnItemClickListener
    {


	public void onItemClick(AdapterView<?> parent, View view, int position, long rowid) 
	{     
		//根据图片进行相应的跳转
		switch (position) 
		{
		case 0: 
			Intent intenthome=new Intent();
			intenthome.setClass(Talk.this, Welcome.class);
			startActivity(intenthome);
			populayout.setVisibility(8);
			sharebutton.setBackgroundResource(R.drawable.sharebutton);
			isopen=false;
			break;
		case 1: 
			Intent intentabout=new Intent();
			intentabout.setClass(Talk.this, about.class);
			startActivity(intentabout);
			populayout.setVisibility(8);
			sharebutton.setBackgroundResource(R.drawable.sharebutton);
			isopen=false;
			break;
		case 2: 
			Intent intent=new Intent(Intent.ACTION_SEND);  
            intent.setType("text/*");  
            intent.putExtra(Intent.EXTRA_SUBJECT, "share");  
            intent.putExtra(Intent.EXTRA_TEXT,"莫言殇：伤感只是一时情绪的发泄，而不是生活的态度。欢迎下载阅读。");  
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
           startActivity(Intent.createChooser(intent, "分享给好友："));
           populayout.setVisibility(8);
			sharebutton.setBackgroundResource(R.drawable.sharebutton);
			isopen=false;
			break;
		case 3:
			Toast.makeText(Talk.this, "该功能在建设中，敬请期待",Toast.LENGTH_SHORT).show();
			populayout.setVisibility(8);
			sharebutton.setBackgroundResource(R.drawable.sharebutton);
			isopen=false;
			break;
		}
	}
      }
    
    
    
    @Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	
	
 
    
		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		//return super.onKeyDown(keyCode, event);
		if(keyCode ==KeyEvent.KEYCODE_MENU){
			if(!isopen)
			{
			populayout.setVisibility(1);
			sharebutton.setBackgroundResource(R.drawable.menu);
			isopen=true;
			}
			else
			{
				populayout.setVisibility(8);
				sharebutton.setBackgroundResource(R.drawable.sharebutton);
				isopen=false;
			}
			
		}
		if(keyCode ==KeyEvent.KEYCODE_BACK){
			
			finish();
		}
		return false;
	}
}