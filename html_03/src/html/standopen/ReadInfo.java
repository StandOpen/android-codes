package html.standopen;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.widget.Toast;

public class ReadInfo extends Activity {
    /** Called when the activity is first created. */
	private HttpClient httpClient=null;
	private TextView text=null;
	static final String LOGON_SITE = "localhost";
    static final int    LOGON_PORT = 8080;
    Cookie[] cookies;
    String tmpcookies= "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text=(TextView)findViewById(R.id.result);
      httpClient=Html_03.instance.httpClient;
      ReadInfo();
    }
    
    
    
    
    public void ReadInfo()
    {
    
    	GetMethod get = new GetMethod("http://222.206.65.12/reader/preg.php");
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
    	Elements ele=doc.getElementsByTag("div");
    	Document doc1=Jsoup.parse(ele.toString());
    	Elements info=doc1.getElementsByTag("td");
    	Document doc2=Jsoup.parse(info.toString());
    	Element div=doc1.getElementById("1");
    	String str=div.toString();
//    	
//    	String temp="";
//    	for(int i=0;i<str.length();i++)
//    	{
//    		if(str.substring(i, i+1)=="(")
//    		{
//    			temp=str.substring(i+1, str.length());
//    		}
//    	}
//    	String result=null;
//    	
//    	for(int j=0;j<temp.length();j++)
//    	{
//    		if(temp.substring(j, j+1)==",")
//    		{
//    			result=temp.substring(0, j);
//    			temp=temp.substring(j+1, temp.length());
//    			j=0;
//    		}
//    	}
    	
    	
    	String patternString = "'.+'"; //href
		Pattern pattern = Pattern.compile(patternString,Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(str);
     //  String result=null;
		while (matcher.find()) {

			//int link= matcher.groupCount();
			String link=matcher.group();
		  //  result+=link;
		
			str=link;
		}
		
		ArrayList<String> list=new ArrayList<String>();
		String []result=str.split("','");
		str="";
		for(int i=0;i<result.length;i++)
		{
			if(i==0)
			{
				str+="\n"+result[i].substring(1, result[i].length());
				list.add(result[i].substring(1, result[i].length()));
			}
			else if(i==result.length-1)
			{
				str+="\n"+result[i].substring(0, result[i].length()-1);
				list.add(result[i].substring(0, result[i].length()-1));
			}
			else
			{
			str+="\n"+result[i];
			list.add(result[i]);
			}
		}
		text.setText(str);
    	
		
		//http://222.206.65.12/reader/ajax_preg.php?call_no=F724.6/15&marc_no=0000565214&loca=00005&time=1363769875438
		String url="http://222.206.65.12/reader/ajax_preg.php?call_no=";
		int j=0;
		
		url+=list.get(j+1).toString()+"&marc_no="+list.get(j).toString()+"&loca="+list.get(j+3).toString()+"&time=1363769875438";
		XuJie(url);

//    	Elements geted=div.select("input");
//    	StringBuffer sff  =   new  StringBuffer();
//    	for(Element linkStr:geted){
//    		if(linkStr.attr("name").toString().equals("callno"+(geted.size()/5+1)))
//    		sff.append(linkStr.attr("value").toString()).append("\n");
//    		else if(linkStr.attr("name").toString().equals("location"+(geted.size()/5+1)))
//        		sff.append(linkStr.attr("value").toString()).append("\n");
    		
//    		sff.append(linkStr.attr("onclick").toString()).append("\n");
//    	
//    	}
    	
//    	ArrayList<String> list=new ArrayList<String>();
//    	for(int i=0;i<geted.size();i++)
//    	{
//    		
//    		if(geted.get(i).attr("name").toString().indexOf("callno")>=0)
//    		{
//        		sff.append(geted.get(i).attr("value").toString()).append("\n");
//        		list.add(geted.get(i).attr("value").toString());
//    		}
//        else if(geted.get(i).attr("name").toString().indexOf("location")>=0)
//        		{
//            		sff.append(geted.get(i).attr("value").toString()).append("\n");
//            		list.add(geted.get(i).attr("value").toString());
//        		}
//    	}
//    	text.setText(sff.toString());
//    	
//    	
//    	
//    	int number;
//    	String url="http://222.206.65.12/opac/userpreg_result.php?marc_no=0000605058&count=1";
//    	  //String url="http://222.206.65.12/opac/userpreg_result.php?marc_no=0000599391&count=6&preg_days1=7&take_loca1=02001&callno1=I247.5%2F964%3D7%3A1&location1=02001&pregKeepDays1=7&preg_days2=7&take_loca2=00006&callno2=I247.5%2F964%3D7%3A3&location2=00006&pregKeepDays2=7&preg_days3=7&take_loca3=02001&callno3=I247.5%2F964%3D7%3A2&location3=02001&pregKeepDays3=7&preg_days4=7&take_loca4=00006&callno4=I247.5%2F964%3D7%3A2&location4=00006&pregKeepDays4=7&preg_days5=7&take_loca5=00006&callno5=I247.5%2F964%3D7%3A1&location5=00006&pregKeepDays5=7&preg_days6=7&take_loca6=02001&callno6=I247.5%2F964%3D7%3A3&location6=02001&pregKeepDays6=7&check=6";
//    	for(int j=0;j<list.size()/2;)
//    	{
//    		
//    		String input_utf;
//    		String input_gbk = null;
//			try {
//				input_utf = URLEncoder.encode(list.get(j).toString(), "utf-8");
//				input_gbk=new String(input_utf.getBytes( "utf-8" ), "gbk");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//    		number=j/2+1;
//    		String str="&preg_days"+number+"=7&take_loca"+number+"="+list.get(j+1).toString()+"&callno"+number+"="+input_gbk+"&location"+number+"="+list.get(j+1).toString()+"&pregKeepDays"+number+"=7";
//    	    url+=str;
//    	    j+=2;
//    	}
//    	url+="&check=1";
//    	text.setText(url);
//    	XuJie(url);
    	
    }
    
    public void XuJie(String code)
	{
		
	    	 httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (Windows NT 6.1");
	         httpClient.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);
	         httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
	         httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
	         PostMethod postMethod = new PostMethod(code);     
	        
	      try {
	        int statusCode = httpClient.executeMethod(postMethod);
	       if(statusCode == HttpStatus.SC_MOVED_PERMANENTLY|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
	                       Header locationHeader = postMethod.getResponseHeader("location");
	                       String location = null;
	                       if (locationHeader != null) {
	                              location = locationHeader.getValue();
	                           
	                         
	                       } else {
	                           //   text.setText("Location field value is null.");
	                    	
	                       }
	                       return;
	                }                        
	                if (statusCode != HttpStatus.SC_OK) {
	                      // text.setText("Method failed: "+ postMethod.getStatusLine());
	                
	                }                    
	                
	         } catch (HttpException e) {
	                // text.setText("Please check your provided http    address!");
	        	 
	                e.printStackTrace();
	         } catch (IOException e) {
	                e.printStackTrace();
	         } finally {
	        	    postMethod.releaseConnection();
	         	
	 }
	    }
}
