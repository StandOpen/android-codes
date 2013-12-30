package hkp.dirchooser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button) findViewById(R.id.btn_open);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String path = null;
				String [] fileType = {"dst"};//要过滤的文件类型列表
				DirChooserDialog dlg = new DirChooserDialog(MainActivity.this,2,fileType,path);
				dlg.setTitle("Choose dst file dir");
				dlg.show();
				
			}
		});
    }
}