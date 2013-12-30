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
    	
    	//登陆 Url
        String loginUrl = "http://222.206.65.12/reader/login.php";
 
        //需登陆后访问的 Url
        String dataUrl = "http://222.206.65.12/reader/redr_info.php";
 
        HttpClient httpClient = new HttpClient();
 
        //模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);
 
        //设置登陆时要求的信息，一般就用户名和密码，验证码自己处理了
        NameValuePair[] data = {
                new NameValuePair("number", "1011122008"),
                new NameValuePair("passwd", "3608061"),
                new NameValuePair("returnUrl", ""),new NameValuePair("select", "cern_no")
        };
        postMethod.setRequestBody(data);
 
        try {
            //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(postMethod);
 
            //获得登陆后的 Cookie
            Cookie[] cookies=httpClient.getState().getCookies();
            String tmpcookies= "";
            for(Cookie c:cookies){
                tmpcookies += c.toString()+";";
            }
 
            //进行登陆后的操作
            GetMethod getMethod = new GetMethod(dataUrl);
 
            //每次访问需授权的网址时需带上前面的 cookie 作为通行证
            getMethod.setRequestHeader("cookie",tmpcookies);
 
            httpClient.executeMethod(getMethod);
 
            //打印出返回数据，检验一下是否成功
            String text1 = getMethod.getResponseBodyAsString();
            byte[] responseBody = getMethod.getResponseBody();
            String str=new String(responseBody);
            text.setText(str);
 
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
       
    }  
  
    
  
   
  
    
    
    
    
    
