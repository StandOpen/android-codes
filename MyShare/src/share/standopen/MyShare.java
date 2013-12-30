package share.standopen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyShare extends Activity {
  private Button share=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        share=(Button)findViewById(R.id.button1);
        final Activity mActivity=this;
        share.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  try 
			        { 
					  final String str1="分享标题";
					  final String str2="要分享的内容哦。呵呵";
					  
			            ShareSample sample =new ShareSample(); 
			           sample.getShareList(str1,str2 ,mActivity); 
			        }catch (Exception e) { 
			            e.printStackTrace(); 
			            // TODO: handle exception 
			        } 
			}
		});
      
    }
}