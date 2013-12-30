package slider.standopen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class first extends Activity
{

	private Button mybutton=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		        super.onCreate(savedInstanceState);
		
		        setContentView(R.layout.main);  
		
		         mybutton=(Button)findViewById(R.id.button1);
		       mybutton.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent intent = new Intent();
					
					  intent.setClass(first.this,second.class);
					
					   startActivity(intent);
					
					                //设置切换动画，从右边进入，左边退出
					
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);           
				}
			});
		       
		     
		
	}
}
