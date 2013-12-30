package html.standopen;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.jsoup.Connection;

import org.jsoup.Connection.Method;

import org.jsoup.Jsoup;

public class Html_01 extends Activity {
    /** Called when the activity is first created. */
	private TextView result=null;
	private EditText input=null;
	private Button Go=null;
	protected static int timeout = 1000*60;

	protected final static String user_agent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)";

	protected static String LOGIN_FLAG = "PHPSESSID";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        result=(TextView)findViewById(R.id.result);
        input=(EditText)findViewById(R.id.input);
        Go=(Button)findViewById(R.id.ok);
        Go.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String input_utf="";
				String input_gbk="";
				try {
				input_utf = URLEncoder.encode(input.getText().toString(), "utf-8");
					input_gbk=new String(input_utf.getBytes( "utf-8" ), "gbk");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String url="http://222.206.65.12/opac/openlink.php?historyCount=1&strText="+input_gbk+"&doctype=ALL&strSearchType=title&match_flag=forward&displaypg=50&sort=CATA_DATE&orderby=desc&showmode=list&dept=ALL";
				Document doc;
				try {
					doc = Jsoup.connect(url)
							.data("jquery", "java")
							.userAgent("Mozilla")
							.cookie("auth", "token")
							.timeout(50000)
							.get();
					Elements divs = doc.select("div.list_books");
					
					
					StringBuilder linkBuffer = new StringBuilder();
					if (divs != null) {
					    for (Element div : divs) {
					        Elements links = div.select("a[href]");
					        if (null != links) {
					            for (Element link : links) {
					                linkBuffer.append(link.attr("abs:href"));//相对地址会自动转成绝对url地址
					                linkBuffer.append(" \n");
					               
					            }                                    
					        }                                
					    }
					}
					
					result.setText(linkBuffer.substring(0).toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
		        
		        
			}
		});
        

    
    }
}
	
	










