package linux.standopen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class LinuxShellActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
			String i = LinuxShell("date");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   
		}
    }
    
    
    
    
    public String LinuxShell(String input) throws InterruptedException
    {
    	String out="";
    	try {
    		//��ȡrootȨ��
			Process localProcess = Runtime.getRuntime().exec("su");
			//��ȡ���������ִ��linux����
			OutputStream localOutputStream = localProcess.getOutputStream();
			DataOutputStream localDataOutputStream = new DataOutputStream(localOutputStream);
			localDataOutputStream.writeBytes(input+"\n");
			//��ȡ����ֵ
			InputStream localInputStream = localProcess.getInputStream();
			DataInputStream localDataInputStream = new DataInputStream(localInputStream);
			out=localDataInputStream.readLine();
			if(out.length()>0)
			{
			System.out.println(out);
			}
			else
			{
				System.out.println("���ֻ���û�л�ȡrootȨ��");	
			}
			localDataOutputStream.flush();
			localProcess.waitFor();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
    	return out;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}