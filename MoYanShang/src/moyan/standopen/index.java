package moyan.standopen;


import helper.Test;

import com.pandain.util.PandaMessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


@SuppressLint("ParserError")
public class index extends Activity{
	

	

	private mybutton first=null;
	private mybutton second=null;
	private mybutton three=null;
	private mybutton four=null;
    private mybutton mysad=null;
	public static index instance = null;
	static final private int ABOUT=Menu.FIRST;
	static final private int CLOSE=Menu.FIRST+1;
	
	
	 public  String msg,seat;
	 ApplicationInfo appInfo;
	 int i;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
   
        
        
        
        
        
        
        
        
        
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
       	 WindowManager.LayoutParams.FLAG_FULLSCREEN);  
       	    
       requestWindowFeature(Window.FEATURE_NO_TITLE);  
        
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
       
        setContentView(R.layout.index);
        PandaMessage.Showpanda(this,PandaMessage.LEFT_UP);
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
       mysad.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(index.this,MySad.class);
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
		public boolean onKeyDown(int keyCode, KeyEvent event)
		{
			// TODO Auto-generated method stub
			//return super.onKeyDown(keyCode, event);
			if(keyCode ==KeyEvent.KEYCODE_BACK){
				//dialog();
				Intent intent=new Intent();
				intent.setClass(index.this,CloseDialog.class);
				startActivity(intent);
				return false;
			}
			return false;
		}
		
		protected void dialog(){
			AlertDialog.Builder builder=new Builder(index.this);
			builder.setTitle("提示");
			builder.setMessage("确定要退出吗?");
			builder.setPositiveButton("确认",  new DialogInterface.OnClickListener(){
	 
		
				public void onClick(DialogInterface dialog, int which)
				{                
					finish();
				}
				
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
	 
				
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
				
			});
			builder.create().show();
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
			public boolean onCreateOptionsMenu(Menu menu) {
				// TODO Auto-generated method stub
				MenuItem itemAdd=menu.add(0, ABOUT, Menu.NONE,"关于");
				itemAdd.setIcon(R.drawable.about);
				itemAdd.setShortcut('0', 'a');
				MenuItem itemAdd1=menu.add(0,CLOSE, Menu.NONE,"退出");
				itemAdd1.setIcon(R.drawable.menu_icon_exit);
				itemAdd1.setShortcut('0', 'b');
				return super.onCreateOptionsMenu(menu);
				
			}
		 
			
			
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				// TODO Auto-generated method stub
				if(item.getItemId()==ABOUT)
				{
					Intent intentabout=new Intent();
					intentabout.setClass(index.this, about.class);
					startActivity(intentabout);
				}
				else
				{
					finish();
				}
				return super.onOptionsItemSelected(item);
			}
}
