package html.standopen;

import java.io.IOException;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Html_03 extends Activity {
 
	public HttpClient httpClient=null;
	private TextView text=null;
	static final String LOGON_SITE = "localhost";
    static final int    LOGON_PORT = 8080;
    Cookie[] cookies;
    String tmpcookies= "";
    public static Html_03 instance = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text=(TextView)findViewById(R.id.result);
        httpClient=new HttpClient();
        instance=this;
          login("1011122008","3608061");
    }
    
    public void login(String number,String password)
    {
    	 httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (Windows NT 6.1");
         httpClient.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);
         httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
         httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
         PostMethod postMethod = new PostMethod("http://222.206.65.12/reader/redr_verify.php");     
         postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
         NameValuePair[] data = { new NameValuePair("number",number),new NameValuePair("passwd",password),new NameValuePair("returnUrl",""),new NameValuePair("select","cert_no")};
         postMethod.setRequestBody(data);
      try {
        int statusCode = httpClient.executeMethod(postMethod);
       if(statusCode == HttpStatus.SC_MOVED_PERMANENTLY|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                       Header locationHeader = postMethod.getResponseHeader("location");
                       String location = null;
                       if (locationHeader != null) {
                              location = locationHeader.getValue();
                            // ReadInfo();
                              Intent intent=new Intent();
                              intent.setClass(Html_03.this, ReadInfo.class);
                              startActivity(intent);
                             cookies =httpClient.getState().getCookies();
                       } else {
                              text.setText("Location field value is null.");
                       }
                       return;
                }                        
                if (statusCode != HttpStatus.SC_OK) {
                       text.setText("Method failed: "+ postMethod.getStatusLine());
                }                    
                
         } catch (HttpException e) {
                 text.setText("Please check your provided http    address!");
                e.printStackTrace();
         } catch (IOException e) {
                e.printStackTrace();
         } finally {
        	    postMethod.releaseConnection();
         	
 }
      String temp = null;
      String str="getInLib('0000599391','I247.5/964=7:1','1','02001')";
      for(int i=0;i<str.length();i++)
      {
    	  char ch=str.charAt(i);
    	  if(ch=='\'')
    		  {
    		      temp+=str.substring(0,i);
    			  str=str.substring(i+1, str.length());
    			 i=0;
    		  }
      }
      
      
      text.setText(temp);
    }
    
    
    public void ReadInfo()
    {
    
    	GetMethod get = new GetMethod("http://222.206.65.12/reader/book_lst.php");
    	httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
    	httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        try {
        	httpClient.getState().addCookies(cookies);
			httpClient.executeMethod(get);
			byte[] responseBody = get.getResponseBody();
            String str=new String(responseBody);
			//text.setText(str);
			GetMsg(str);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			get.releaseConnection();
		}
     
        
    }
    
    
    public void GetMsg(String content)
    {
    	Document doc=Jsoup.parse(content);
    	Elements ele=doc.getElementsByTag("table");
    	Document doc1=Jsoup.parse(ele.toString());
    	Elements info=doc1.getElementsByTag("td");
    	StringBuffer sff  =   new  StringBuffer();
    	for(Element linkStr:info){
    		sff.append(linkStr.text()).append("\n").append("\n");
    	
    	}
    	text.setText(sff.toString());
    	
    }
}