package standopen.copy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CopyTest extends Activity {
    /** Called when the activity is first created. */
	Button mybutton=null;
	EditText goal=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mybutton=(Button)findViewById(R.id.copy);
        goal=(EditText)findViewById(R.id.content);
        mybutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try
				{
				ClipboardManager cmb = (ClipboardManager) CopyTest.this
						 .getSystemService(Context.CLIPBOARD_SERVICE);
						 cmb.setText(goal.getText().toString().trim());
						 Toast.makeText(CopyTest.this, "复制成功！", Toast.LENGTH_SHORT).show();
				}
				catch(Exception e)
				{
					Toast.makeText(CopyTest.this, "复制失败！", Toast.LENGTH_SHORT).show();
				}
						 
			}
		});
    }
    
    

}