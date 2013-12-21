package moyan.standopen;

import java.sql.Date;
import java.text.SimpleDateFormat;

import helper.DBHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class worddata extends Activity {
    /** Called when the activity is first created. */
    private Button cancelbutton=null;
    private Button okbutton=null;
    private DBHelper mDBHelper = null;
    private EditText inputtext=null;
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.worddata);
      okbutton=(Button)findViewById(R.id.ok);
      inputtext=(EditText)findViewById(R.id.input);
      cancelbutton=(Button)findViewById(R.id.cancel);
      createDB();
      okbutton.setOnClickListener(new View.OnClickListener() {
		
		@SuppressLint("ParserError")
		public void onClick(View v) {
			// TODO Auto-generated method stub
			insertSomeRecords(inputtext.getText().toString());
			
			MySad.instance.ViewRecords();
			finish();
		}
	});
      cancelbutton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
  }
  
  private void insertSomeRecords(String input) {
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");     
		Date curDate=new Date(System.currentTimeMillis());//获取当前时间     
		String str = formatter.format(curDate);  
		 mDBHelper.addPeople(input,str);
		
	}
  
  private void createDB() {
		String DB_NAME = "sqlitedb1";
		mDBHelper = new DBHelper(worddata.this, DB_NAME, null, 1);
		assert(mDBHelper != null);
		
	}
  }

  
