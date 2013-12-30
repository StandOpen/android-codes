package install.standopen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyInstalledReceiver extends BroadcastReceiver { 
  

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {     // install 
            String packageName = intent.getDataString(); 
 
            Log.i("homer", "��װ�� :" + packageName); 
            Toast.makeText(context, "��װ", Toast.LENGTH_SHORT).show();
        } 
 
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {   // uninstall 
            String packageName = intent.getDataString(); 
 
            Log.i("homer", "ж���� :" + packageName); 
            Toast.makeText(context, "ж����", Toast.LENGTH_SHORT).show();
        } 
	} 
} 
