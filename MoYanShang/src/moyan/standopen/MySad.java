package moyan.standopen;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import helper.DBHelper;
import helper.myadapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MySad extends Activity {
    /** Called when the activity is first created. */
	public ListView talkView;
	private ImageButton wordbutton=null;
	private ImageButton backbutton=null;
	public  DBHelper mDBHelper = null;
	public static MySad instance = null;
	private RelativeLayout mylayout=null;
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.mysad);
      createDB1();
      instance=this;
     talkView=(ListView)findViewById(R.id.list);
     wordbutton=(ImageButton)findViewById(R.id.word);
     backbutton=(ImageButton)findViewById(R.id.back);
    mylayout=(RelativeLayout)findViewById(R.id.layout_sad);
     backbutton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
     wordbutton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(MySad.this,worddata.class);
			startActivity(intent);
		}
	});
     ViewRecords();
  }
     
    
  
@SuppressLint("ParserError")
public void createDB1() {
		String DB_NAME = "sqlitedb1";
		mDBHelper = new DBHelper(MySad.this, DB_NAME, null, 1);
		assert(mDBHelper != null);
		
	}
	private void insertSomeRecords() {
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");     
		Date curDate=new Date(System.currentTimeMillis());//获取当前时间     
		String str = formatter.format(curDate);  
		 mDBHelper.addPeople("gjksokjgfolasjgfiojasfiljslajfiojaoigfo",str);
		
	}
	@SuppressWarnings("unused")
	public void ViewRecords() {
		ArrayList<DetailEntity> list = null;
		 list = new ArrayList<DetailEntity>();
		 ArrayList<DetailEntity> list1=null;
		 list1 = new ArrayList<DetailEntity>();
		int num=0;
		Cursor c = mDBHelper.getWritableDatabase().query(
				DBHelper.TB_NAME,null,null,null,null,null,   
		        DBHelper.NAME); 
		if (c.moveToFirst()) {
			talkView.setVisibility(0);
			mylayout.setBackgroundResource(R.drawable.floor);
			int idxID = c.getColumnIndex(DBHelper.ID);
			int idxName = c.getColumnIndex(DBHelper.NAME);
			int idxNumber = c.getColumnIndex(DBHelper.NUMBER1);
			do {
				
				if(num%2==0)
				{
					
					DetailEntity temp= new DetailEntity(c.getInt(idxID),""+c.getInt(idxID)+":"+c.getString(idxNumber),c.getString(idxName),R.layout.list_say_me_item);
					list.add(temp);
				}
				else
				{
					
					DetailEntity temp= new DetailEntity(c.getInt(idxID),""+c.getInt(idxID)+":"+c.getString(idxNumber),c.getString(idxName),R.layout.list_say_he_item);
					list.add(temp);
				}
				num++;
			} while (c.moveToNext());
			
			
			
			Collections.sort(list,new Comparator<DetailEntity>() {

				public int compare(DetailEntity lhs, DetailEntity rhs) {
					// TODO Auto-generated method stub
					 if(lhs.getDate()>rhs.getDate())
					 {
						 return 1;
					 }
					 else
					 {
						 return -1;
					 }
				
					 
				}
			});
			
			
			for(int i=0;i<list.size();i++)
			{
				if(i%2==0)
				{
					
					DetailEntity temp= new DetailEntity(list.get(i).getDate(),list.get(i).getName(),list.get(i).getText(),R.layout.list_say_me_item);
					list1.add(temp);
				}
				else
				{
					
					DetailEntity temp= new DetailEntity(list.get(i).getDate(),list.get(i).getName(),list.get(i).getText(),R.layout.list_say_he_item);
					list1.add(temp);
				}
				
				
				
			}
			talkView.setAdapter(new myadapter(MySad.this, list1));
			
			
			
		}
		else
		{
			mylayout.setBackgroundResource(R.drawable.floor_tip);
			talkView.setVisibility(8);
		}
		
		c.close();

	}
	
	
	
}


