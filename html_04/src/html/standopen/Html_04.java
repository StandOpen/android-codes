package html.standopen;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;



import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Html_04 extends Activity {
    /** Called when the activity is first created. */
	private TextView text=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text=(TextView)findViewById(R.id.text);
       login();
    }
    
    
    
    
    private void login() {  
    	
    	//��½ Url
        String loginUrl = "http://222.206.65.12/reader/login.php";
 
        //���½����ʵ� Url
        String dataUrl = "http://222.206.65.12/reader/redr_info.php";
 
        HttpClient httpClient = new HttpClient();
 
        //ģ���½����ʵ�ʷ�������Ҫ��ѡ�� Post �� Get ����ʽ
        PostMethod postMethod = new PostMethod(loginUrl);
 
        //���õ�½ʱҪ�����Ϣ��һ����û��������룬��֤���Լ�������
        NameValuePair[] data = {
                new NameValuePair("number", "1011122008"),
                new NameValuePair("passwd", "3608061"),
                new NameValuePair("returnUrl", ""),new NameValuePair("select", "cern_no")
        };
        postMethod.setRequestBody(data);
 
        try {
            //���� HttpClient ���� Cookie,���������һ���Ĳ���
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(postMethod);
 
            //��õ�½��� Cookie
            Cookie[] cookies=httpClient.getState().getCookies();
            String tmpcookies= "";
            for(Cookie c:cookies){
                tmpcookies += c.toString()+";";
            }
 
            //���е�½��Ĳ���
            GetMethod getMethod = new GetMethod(dataUrl);
 
            //ÿ�η�������Ȩ����ַʱ�����ǰ��� cookie ��Ϊͨ��֤
            getMethod.setRequestHeader("cookie",tmpcookies);
 
            httpClient.executeMethod(getMethod);
 
            //��ӡ���������ݣ�����һ���Ƿ�ɹ�
            String text1 = getMethod.getResponseBodyAsString();
            byte[] responseBody = getMethod.getResponseBody();
            String str=new String(responseBody);
            text.setText(str);
 
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
       
    }  
  
    
  
   
  
    
    
    
    
    
