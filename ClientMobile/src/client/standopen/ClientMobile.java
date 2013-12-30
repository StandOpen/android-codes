package client.standopen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ClientMobile extends Activity {
    /** Called when the activity is first created. */
	private Socket client=null;
	private Button connect=null;
	private Button send=null;
	private EditText ip=null;
	private EditText message=null;
	private TextView get=null;
	private ScrollView scv=null;
	BufferedReader in=null;
	PrintWriter out=null;
	private int num=0;
	private int type=0;
	 private ImageButton play=null;
	 private ImageButton prebutton=null;
	 private ImageButton nextbutton=null;
	 private boolean isconnect=false;
	 private int scvnum=10;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        	     WindowManager.LayoutParams.FLAG_FULLSCREEN);    
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        connect=(Button)findViewById(R.id.connect);
        send=(Button)findViewById(R.id.send);
        ip=(EditText)findViewById(R.id.ip);
        message=(EditText)findViewById(R.id.msg);
        get=(TextView)findViewById(R.id.get);
        play=(ImageButton)findViewById(R.id.play);
        prebutton=(ImageButton)findViewById(R.id.left);
        nextbutton=(ImageButton)findViewById(R.id.right);
        scv=(ScrollView)findViewById(R.id.sv);
     
        prebutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isconnect)
				{
				String str="pre";
				try {
					out=new PrintWriter(client.getOutputStream(),true);
					out.println(str);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					get.append("发送失败!\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
				}
				}
				else
				{
					get.append("没有连接!\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
				}
			}
		});
        nextbutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isconnect)
				{
				String str="next";
				try {
					out=new PrintWriter(client.getOutputStream(),true);
					out.println(str);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					get.append("发送失败\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
				}
				}
				else
				{
					get.append("没有连接！\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
				}
			}
		});
        play.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(isconnect)
				{
				if(type==0)
				{
					String str="play";
					try {
						out=new PrintWriter(client.getOutputStream(),true);
						out.println(str);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						get.append("发送失败\n");
						scvnum+=50;
						 scv.scrollTo(0,scvnum);
					}
					
					type++;
					play.setBackgroundResource(R.drawable.stopbutton);
				}
				else if(type==1)
				{
					String str="stop";
					try {
						out=new PrintWriter(client.getOutputStream(),true);
						out.println(str);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						get.append("发送失败\n");
						scvnum+=50;
						 scv.scrollTo(0,scvnum);
					}
					
					type=0;
					play.setBackgroundResource(R.drawable.playbutton);
				}
				}
				else
				{
					get.append("没有连接！\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
				}
			}
		});
        connect.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(num==0)
			{
				String str=ip.getText().toString();
				if(str.equals(""))
				{
					get.append("ip不能为空！\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
					return;
				}
				try
				{
					client=new Socket(str,7788);
					get.append("已连接\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
					GetMessageThread th=new GetMessageThread();
					th.start();
					//in=new BufferedReader(new InputStreamReader(client.getInputStream()));
					isconnect=true;
					
				}catch(IOException e)
				{
					get.append("连接失败\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
				}
			
				num++;
				connect.setText("断开连接\n");
				scvnum+=50;
				 scv.scrollTo(0,scvnum);
				ip.setEnabled(false);
			}
			else if(num==1)
			{
				try {
					in.close();
					out.close();
					client.close();
					get.append("已下线\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					get.append("下线失败\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
				}
				num=0;
				connect.setText("连接\n");
				scvnum+=50;
				 scv.scrollTo(0,scvnum);
				ip.setEnabled(true);
			}
			}
		});
 send.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isconnect)
				{
				String str=message.getText().toString();
				if(str==null)
					return;
				message.append("");
				try {
					out=new PrintWriter(client.getOutputStream(),true);
					out.println(str);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					get.append("发送失败\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
					
				}
				}
				else
				{
					get.append("没有连接！\n");
					scvnum+=50;
					 scv.scrollTo(0,scvnum);
				}
				
			}
		});
    }
	
    
    
    public class GetMessageThread extends Thread
    {

    	//private ClientMobile cm=null;
    	public GetMessageThread()
    	{
    	//cm=new ClientMobile();
    	//get.append("接收程序开始运行！");
    	}
    	
    	Handler handler=new Handler(){

    		@Override
    		public void handleMessage(Message msg) {
    			// TODO Auto-generated method stub
    			super.handleMessage(msg);
    			get.append(msg.obj.toString());
    			scvnum+=50;
				 scv.scrollTo(0,scvnum);
    			//handler.post(new GetMessageThread());
    		}
    		
    	};
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			while(true)
			{
			try {
				in=new BufferedReader(new InputStreamReader(client.getInputStream()));
				String str=in.readLine();
				str="服务器："+str+"\n";
				Message msg=handler.obtainMessage();
				msg.obj=str;
				
				handler.sendMessage(msg);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//get.append("接收消息失败！");
			}
			
			}
		}
    	
    }
    
    @Override
   	public boolean onKeyDown(int keyCode, KeyEvent event)
   	{
   		// TODO Auto-generated method stub
   		//return super.onKeyDown(keyCode, event);
   		if(keyCode ==KeyEvent.KEYCODE_BACK){
   			finish();
   			return false;
   		}
   		return false;
   	}
}