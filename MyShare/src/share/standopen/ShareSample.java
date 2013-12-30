package share.standopen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

public class ShareSample { 
	 
    /** 
     * 获得分享列表（简单实现，只是把核心的东西写了写。不是太实用） 
     * **/ 
    public void getShareList(final String title,final String content,final Activity mActivity) 
    { 
        final Map<String,ResolveInfo> appInfo =new HashMap<String, ResolveInfo>(); 
        List<ResolveInfo> appList=getShareTargets(mActivity); 
        final String[] items; 
        if(appList.size()>0){ 
            for (int i = 0; i < appList.size(); i++) { 
                ResolveInfo tmp_ri=(ResolveInfo)appList.get(i); 
                //ActivityInfo tmp_ai=tmp_ri.activityInfo; 
                ApplicationInfo apinfo=tmp_ri.activityInfo.applicationInfo; 
                String tmp_appName = apinfo.loadLabel(mActivity.getPackageManager()).toString(); 
                //这里就做了一个简单的得到应用名称判断， 实际情况根据 ResolveInfo 这东东 可以得到很多很多有用的东东哦。 
                if(!tmp_appName.equals("微博")) 
                { 
                    continue; 
                } 
                appInfo.put(tmp_appName,tmp_ri); 
            } 
            items=new String[appInfo.size()]; 
            int j=0; 
            for (Map.Entry<String,ResolveInfo> myEntry : appInfo.entrySet()) {  
                items[j]=myEntry.getKey(); 
                j++; 
             }  
             
        } 
        else{ 
            Toast.makeText(mActivity.getApplicationContext(),"暂无分享应用", Toast.LENGTH_SHORT).show(); 
            return; 
        } 
        if(appInfo.size()<1) 
        { 
            Toast.makeText(mActivity.getApplicationContext(),"暂无分享应用", Toast.LENGTH_SHORT).show(); 
            return; 
        } 
        /** 
         * 这里用对话框简单的一个实现，实际开发中应该要单独抽取出来封装成一个自定义的列表。 
         *  
         * **/ 
        new AlertDialog.Builder(mActivity).setTitle("选择分享")  
        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {  
        public void onClick(DialogInterface dialog, int item) {  
             
            CreateShare(title,content,mActivity,appInfo.get(items[item])); 
            //CreateShare(title,content,mActivity); 
            dialog.cancel();  
        }  
 
        }).show();//显示对话框 
         
    } 
     
    /** 
     * 实现自定义分享功能(主要就是启动对应的App) 
     * **/ 
    private void CreateShare(String title,String content ,Activity activity,ResolveInfo appInfo) {   
         
        try 
        { 
            Intent shareIntent=new Intent(Intent.ACTION_SEND); 
             shareIntent.setComponent(new ComponentName(appInfo.activityInfo.packageName, appInfo.activityInfo.name));   
             //这里就是组织内容了，   
            // shareIntent.setType("text/plain");  
             shareIntent.setType("image/*");  
             shareIntent.putExtra(Intent.EXTRA_TEXT, content);   
             shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
             activity.startActivity(shareIntent);   
             
        }catch (Exception e) { 
            e.printStackTrace(); 
            // TODO: handle exception 
        } 
         
         
        
    }   
    /** 
     * 默认分享列表（网上一大堆） 
     * **/ 
    private void CreateShare(String title,String content ,Activity activity) {   
        Intent intent=new Intent(Intent.ACTION_SEND); 
        intent.setType("image/*");    
        intent.putExtra(Intent.EXTRA_SUBJECT, title);    
        intent.putExtra(Intent.EXTRA_TEXT, content);     
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
        activity.startActivity(Intent.createChooser(intent, title)); 
         
    } 
    /** 
     * 获得所有带Intent.ACTION_SEND的应用列表。 ResolveInfo 这个东东真不错。 
     * **/ 
    private List<ResolveInfo> getShareTargets(Activity activity){ 
        Intent intent=new Intent(Intent.ACTION_SEND,null); 
        intent.addCategory(Intent.CATEGORY_DEFAULT); 
        intent.setType("text/plain"); 
        PackageManager pm=activity.getPackageManager(); 
        return pm.queryIntentActivities(intent,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT); 
        } 
} 