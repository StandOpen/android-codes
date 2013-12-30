package jtapp.standopen;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SqliteSample extends Activity {
	private DBHelper mDBHelper = null;
	private Button btCreateDb = null;
	private Button btInsertData = null;
	private Button btViewData = null;
	private Button btDelOne = null;
	private Button btClearAll = null;
	private int num=0;
	private EditText myinput=null;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btCreateDb = (Button)findViewById(R.id.Button01);
        btCreateDb.setOnClickListener(new ClickViewHandler());
        btInsertData = (Button)findViewById(R.id.Button02);
        btInsertData.setOnClickListener(new ClickViewHandler());
        btInsertData.setEnabled(false);
        btViewData = (Button)findViewById(R.id.Button03);
        btViewData.setOnClickListener(new ClickViewHandler());
        btViewData.setEnabled(false);
        btDelOne = (Button)findViewById(R.id.Button04);
        btDelOne.setOnClickListener(new ClickViewHandler());
        btDelOne.setEnabled(false);
        btClearAll = (Button)findViewById(R.id.Button05);
        btClearAll.setOnClickListener(new ClickViewHandler());
        btClearAll.setEnabled(false);
        myinput=(EditText)findViewById(R.id.input);
   
    }

    public class ClickViewHandler implements OnClickListener {
    	public void onClick(View v) {
    		if (v == btCreateDb) {
    			createDB();
    		} else if (v == btInsertData) {
    			insertSomeRecords();
    		} else if (v == btViewData) {
    			ViewRecords();
    		} else if (v == btDelOne) {
    			DelOne();
    		} else if (v == btClearAll) {
    			mDBHelper.delAllPeople();
    		}
    	}
    }
    
	private void createDB() {
		String DB_NAME = "sqlitedb1";
		mDBHelper = new DBHelper(
				SqliteSample.this, DB_NAME, null, 1);
		assert(mDBHelper != null);
		btCreateDb.setEnabled(false);
		btInsertData.setEnabled(true);
		btViewData.setEnabled(true);
		btDelOne.setEnabled(true);
        btClearAll.setEnabled(true);
	}
	private void insertSomeRecords() {
//		if(num==0)
//		{
//		mDBHelper.addPeople("一休", "18602155856");
//		num++;
//		}
//		else
//		{
//		mDBHelper.addPeople("巴巴", "13368565525");
//        mDBHelper.addPeople("项羽", "057156856225");
//		}
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");     
		Date curDate=new Date(System.currentTimeMillis());//获取当前时间     
		String str = formatter.format(curDate);  
		 mDBHelper.addPeople(myinput.getText().toString(),str);
		
	}
	private void ViewRecords() {
		// Make the query
		Cursor c = mDBHelper.getWritableDatabase().query(
				DBHelper.TB_NAME,null,null,null,null,null,   
		        DBHelper.NAME + " ASC"); 
		StringBuilder sbRecords = new StringBuilder("");
		if (c.moveToFirst()) {
			int idxID = c.getColumnIndex(DBHelper.ID);
			int idxName = c.getColumnIndex(DBHelper.NAME);
			int idxNumber = c.getColumnIndex(DBHelper.NUMBER1);
			// Iterator the records
			do {
				sbRecords.append(c.getInt(idxID));
				sbRecords.append(". ");
				sbRecords.append(c.getString(idxName));
				sbRecords.append(", ");
				sbRecords.append(c.getString(idxNumber));
				sbRecords.append("\n");
			} while (c.moveToNext());
		}
		c.close();
		// Refresh the content of TextView
		((TextView)(findViewById(
				R.id.TextView01))).setText(sbRecords);
	}
	private void DelOne() {
		int id;
		Cursor c = mDBHelper.getWritableDatabase().query(
				DBHelper.TB_NAME,null,null,null,null,null,   
		        DBHelper.NAME + " ASC");
		if (c.moveToFirst()) {
			int idxID = c.getColumnIndex(DBHelper.ID);
			id = c.getInt(idxID);
			mDBHelper.delPeople(id);
		}
	}
}