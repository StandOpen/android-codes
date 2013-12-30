package kaiji.standopen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver { 
  

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
	       if(arg1.getAction().equals("android.intent.action.BOOT_COMPLETED")) {     // boot 
	            Intent intent2 = new Intent(arg0,KaiJiQiDong.class); 
//	          intent2.setAction("android.intent.action.MAIN"); 
//	          intent2.addCategory("android.intent.category.LAUNCHER"); 
	            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	            arg0.startActivity(intent2); 
	        } 
	} 
} 