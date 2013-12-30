package slider.standopen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class second extends Activity{

	private Button mybutton=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		        super.onCreate(savedInstanceState);
		
		        setContentView(R.layout.second);  
		
		         mybutton=(Button)findViewById(R.id.button1);
		       mybutton.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
		       
		     
		
	}
}
