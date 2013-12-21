package moyan.standopen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class CloseDialog extends Activity {
    /** Called when the activity is first created. */
      private Button cancelbutton=null;
      private Button okbutton=null;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.closedialog);
        
       okbutton=(Button)findViewById(R.id.ok);
       cancelbutton=(Button)findViewById(R.id.cancel);
       okbutton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			index.instance.finish();
		}
	});
       cancelbutton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
    }
    
    

}
