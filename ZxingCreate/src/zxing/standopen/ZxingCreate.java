package zxing.standopen;

import com.zijunlin.Zxing.Demo.CaptureActivity;

import zxing.moyan.index;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ZxingCreate extends Activity {
    private EditText input=null;
    private Button ok=null;
    private Button clear=null;
    private Button more=null;
    private Button scanner=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        input=(EditText)findViewById(R.id.input);
        ok=(Button)findViewById(R.id.make);
        clear=(Button)findViewById(R.id.clear);
        more=(Button)findViewById(R.id.btn_more);
        scanner=(Button)findViewById(R.id.btn_scanner);
        ok.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(input.getText().toString().length()>0)
				{
				Intent intent=new Intent();
				intent.putExtra("content", input.getText().toString());
				intent.setClass(ZxingCreate.this, Input.class);
				startActivity(intent);
				}
				else
				{
					Toast toast = Toast.makeText(getApplicationContext(),
							"输入不能为空！", Toast.LENGTH_LONG);
				      	   toast.setGravity(Gravity.CENTER, 0, 0);
				      	   LinearLayout toastView = (LinearLayout) toast.getView();
				      	   ImageView imageCodeProject = new ImageView(getApplicationContext());
				      	   imageCodeProject.setImageResource(R.drawable.fun);
				      	   toastView.addView(imageCodeProject, 0);
				      	   toast.show();
				}
			}
		});
        clear.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				input.setText("");
			}
		});
        more.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ZxingCreate.this,index.class);
				startActivity(intent);
			}
		});
        scanner.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ZxingCreate.this,CaptureActivity.class);
				startActivity(intent);
			}
		});
    }
   
}
