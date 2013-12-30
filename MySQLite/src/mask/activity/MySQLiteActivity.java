package mask.activity;

import mask.aqlite.db.DatabaseHeloer;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MySQLiteActivity extends Activity {
    /** Called when the activity is first created. */
	private Button create=null;
	private Button insert=null;
	private Button update=null;
	private Button query=null;
	private Button up=null;
	private EditText mytext=null;
	private int num=9;
	private TextView myresult=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        create=(Button)findViewById(R.id.create);
        insert=(Button)findViewById(R.id.insert);
        update=(Button)findViewById(R.id.update);
        query=(Button)findViewById(R.id.query);
        up=(Button)findViewById(R.id.up);
        mytext=(EditText)findViewById(R.id.text);
        myresult=(TextView)findViewById(R.id.result);
        create.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHeloer dbHelper=new DatabaseHeloer(MySQLiteActivity.this, "test_standopen_db",1);
				SQLiteDatabase db=dbHelper.getWritableDatabase();
			}
		});
 up.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHeloer dbHelper=new DatabaseHeloer(MySQLiteActivity.this, "test_standopen_db",2);
				SQLiteDatabase db=dbHelper.getWritableDatabase();
			}
		});
insert.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues values=new ContentValues();
				values.put("id", num);
				values.put("name", mytext.getText().toString());
				DatabaseHeloer dbHelper=new DatabaseHeloer(MySQLiteActivity.this, "test_standopen_db");
				SQLiteDatabase db=dbHelper.getWritableDatabase();
				db.insert("user",null, values);
				
				num++;
			}
		});
update.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		DatabaseHeloer dbHelper=new DatabaseHeloer(MySQLiteActivity.this, "test_standopen_db");
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("name","zhangli");
		db.update("user", values,"id=?",new String[]{"1"});
	}
});
query.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		DatabaseHeloer dbHelper=new DatabaseHeloer(MySQLiteActivity.this, "test_standopen_db");
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		Cursor cursor=db.query("user", new String[]{"id","name"}, "id=?", new String[]{"1"}, null, null, null);
		String str="查询结果为：";
		while(cursor.moveToNext()){
			String name=cursor.getString(cursor.getColumnIndex("name"));
			System.out.println("query----"+name);
			str+=name;
		}
		myresult.setText(str);
	}
});
    }
}