package standopen.activity;

import standopen.net.HttpDownloader;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DownloadActivity extends Activity {
    /** Called when the activity is first created. */
	private Button get=null;
	private EditText text=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        get=(Button)findViewById(R.id.get);
        text=(EditText)findViewById(R.id.text);
        get.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			HttpDownloader file=new HttpDownloader();
			String str=file.download("http://www.lrc123.com/download/lrc/273048-307.aspx");
			text.setText(str);
			}
		});
    }
}