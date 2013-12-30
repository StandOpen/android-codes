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
     * ��÷����б���ʵ�֣�ֻ�ǰѺ��ĵĶ���д��д������̫ʵ�ã� 
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
                //���������һ���򵥵ĵõ�Ӧ�������жϣ� ʵ��������� ResolveInfo �ⶫ�� ���Եõ��ܶ�ܶ����õĶ���Ŷ�� 
                if(!tmp_appName.equals("΢��")) 
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
            Toast.makeText(mActivity.getApplicationContext(),"���޷���Ӧ��", Toast.LENGTH_SHORT).show(); 
            return; 
        } 
        if(appInfo.size()<1) 
        { 
            Toast.makeText(mActivity.getApplicationContext(),"���޷���Ӧ��", Toast.LENGTH_SHORT).show(); 
            return; 
        } 
        /** 
         * �����öԻ���򵥵�һ��ʵ�֣�ʵ�ʿ�����Ӧ��Ҫ������ȡ������װ��һ���Զ�����б� 
         *  
         * **/ 
        new AlertDialog.Builder(mActivity).setTitle("ѡ�����")  
        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {  
        public void onClick(DialogInterface dialog, int item) {  
             
            CreateShare(title,content,mActivity,appInfo.get(items[item])); 
            //CreateShare(title,content,mActivity); 
            dialog.cancel();  
        }  
 
        }).show();//��ʾ�Ի��� 
         
    } 
     
    /** 
     * ʵ���Զ��������(��Ҫ����������Ӧ��App) 
     * **/ 
    private void CreateShare(String title,String content ,Activity activity,ResolveInfo appInfo) {   
         
        try 
        { 
            Intent shareIntent=new Intent(Intent.ACTION_SEND); 
             shareIntent.setComponent(new ComponentName(appInfo.activityInfo.packageName, appInfo.activityInfo.name));   
             //���������֯�����ˣ�   
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
     * Ĭ�Ϸ����б�����һ��ѣ� 
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
     * ������д�Intent.ACTION_SEND��Ӧ���б� ResolveInfo ��������治�� 
     * **/ 
    private List<ResolveInfo> getShareTargets(Activity activity){ 
        Intent intent=new Intent(Intent.ACTION_SEND,null); 
        intent.addCategory(Intent.CATEGORY_DEFAULT); 
        intent.setType("text/plain"); 
        PackageManager pm=activity.getPackageManager(); 
        return pm.queryIntentActivities(intent,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT); 
        } 
} 