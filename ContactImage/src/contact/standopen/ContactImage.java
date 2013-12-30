package contact.standopen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import list.MyAdapter;
import list.listitem;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.FontMetrics;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
@SuppressWarnings("deprecation")
public class ContactImage extends Activity {
	private ArrayList<listitem> items = null;
	private ListView list=null;
	private Button get=null;
	private Button set=null;
	private Button delete=null;
	Bitmap src;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        get=(Button)findViewById(R.id.getinfo);
        set=(Button)findViewById(R.id.setphoto);
        delete=(Button)findViewById(R.id.deletephoto);
        list=(ListView)findViewById(R.id.list);
        src=BitmapFactory.decodeResource(this.getResources(), R.drawable.popu_bg);
        get.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				items=new ArrayList<listitem>();
				 ReadContact();
			}
		});
       set.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//setwordPhoto();
			setwordthread th=new setwordthread();
			th.start();
		}
	});
     delete.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			deletePhoto();
		}
	});
     list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//setwordPhoto(items.get(arg2).getContactid());
			//Toast.makeText(ContactImage.this,items.get(arg2).getContactid(), Toast.LENGTH_SHORT).show();
			
			
		}
	});
    }
    
	public void ReadContact()
    {
    	Uri uri =  ContactsContract.Contacts.CONTENT_URI;
    	ContentResolver contentResolver = this.getBaseContext().getContentResolver();
    	Cursor cursor = contentResolver.query(uri, null, null, null, null);
    	String contactId;
    	String name;
    	String phoneNumber = null;
    	@SuppressWarnings("unused")
		String emailAddress;
    	Bitmap ContactImage;
    	while(cursor.moveToNext()){
  
    	
    		contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
    		name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    		Long pid=cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            ContactImage=getContactPhoto(this,pid,R.drawable.ic_launcher);
    		Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
    			       null,
    			       ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
    			       null, null);
    			while(phones.moveToNext()){
    			phoneNumber = phones.getString(phones.getColumnIndex(
    			ContactsContract.CommonDataKinds.Phone.NUMBER));
    			listitem it=new listitem(ContactImage, contactId, name, phoneNumber, R.layout.list_item);
				items.add(it);
				list.setAdapter(new MyAdapter(this, items));
    			}
    			phones.close();
    			
                    
    			
    			
    	}
    	cursor.close();
    	
    	
    }
	
	
	
	public void setwordPhoto()
	{
		
		
		
		String name;
		Uri uri =  ContactsContract.Contacts.CONTENT_URI;
    	ContentResolver contentResolver = this.getBaseContext().getContentResolver();
    	Cursor cursor = contentResolver.query(uri, null, null, null, null);
    	while(cursor.moveToNext()){
    	name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    	Log.d("正在设置", name);
    //	Toast.makeText(ContactImage.this, "正在设置"+name, Toast.LENGTH_SHORT);
		Long pid=cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
		Bitmap bmp=createBitmap(src, name.substring(name.length()-1, name.length()));
		ByteArrayOutputStream baos =new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
		baos.toByteArray();
		setContactPhoto(this,baos.toByteArray(),pid,false);
    	
    	
		}
    	//Toast.makeText(ContactImage.this, "s", Toast.LENGTH_SHORT);
		
	
    	
	}
	public void setPhoto(String id)
	{
		String contactId;
		Uri uri =  ContactsContract.Contacts.CONTENT_URI;
    	ContentResolver contentResolver = this.getBaseContext().getContentResolver();
    	Cursor cursor = contentResolver.query(uri, null, null, null, null);
    	while(cursor.moveToNext()){
    	contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
    	if(contactId.equalsIgnoreCase(id))
    	{
		Long pid=cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
		Bitmap bmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
		ByteArrayOutputStream baos =new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
		baos.toByteArray();
		setContactPhoto(this,baos.toByteArray(),pid,false);
    	}
		}
	}
	
	public void deletePhoto()
	{
		String contactId;
		Uri uri =  ContactsContract.Contacts.CONTENT_URI;
    	ContentResolver contentResolver = this.getBaseContext().getContentResolver();
    	Cursor cursor = contentResolver.query(uri, null, null, null, null);
    	while(cursor.moveToNext()){
    	contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
    	Long pid=cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
		deleteContactPhoto(this,pid);
		}
	}
	
	
	
	

	 
	    private static void setContactPhoto(Context context, byte[] bytes,long personId, boolean Sync) {
	    	ContentResolver c=context.getContentResolver();
	        ContentValues values = new ContentValues();
	        Uri u = Uri.parse("content://com.android.contacts/data");
	        int photoRow = -1;
	        String where ="raw_contact_id = " + personId + " AND mimetype ='vnd.android.cursor.item/photo'";
	        Cursor cursor = c.query(u, null, where, null, null);
	        int idIdx = cursor.getColumnIndexOrThrow("_id");
	        if (cursor.moveToFirst()) {
	            photoRow = cursor.getInt(idIdx);
	        }
	        cursor.close();
	        values.put("raw_contact_id", personId);
	        values.put("is_super_primary", 1);
	        values.put("data15", bytes);
	        values.put("mimetype","vnd.android.cursor.item/photo");
	        if (photoRow >= 0) {
	            c.update(u, values, " _id= " + photoRow, null);
	         
	        } else {
	            c.insert(u, values);
	        }
	        if (! Sync){
	            u = Uri.withAppendedPath(Uri.parse("content://com.android.contacts/raw_contacts"), String.valueOf(personId));
	            values = new ContentValues();
	            values.put("dirty", 0);
	            c.update(u, values, null, null);
	        }
	    }

	
	    private static void deleteContactPhoto(Context context,long personId) {
	    	ContentResolver c=context.getContentResolver();
	        ContentValues values = new ContentValues();
	        Uri u = Uri.parse("content://com.android.contacts/data");
	        int photoRow = -1;
	        String where ="raw_contact_id = " + personId + " AND mimetype ='vnd.android.cursor.item/photo'";
	        Cursor cursor = c.query(u, null, where, null, null);
	        int idIdx = cursor.getColumnIndexOrThrow("_id");
	        if (cursor.moveToFirst()) {
	            photoRow = cursor.getInt(idIdx);
	        }
	        cursor.close();
	
	        if (photoRow >= 0) {
	     
	            c.delete(u, where, null);
	        } else {
	            c.insert(u, values);
	        }
	      
	    }
	 

	 
	    private static Bitmap getContactPhoto(Context c, long personId,int defaultIco){
	        byte[] data = new byte[0];
	        Uri u = Uri.parse("content://com.android.contacts/data");// AND (is_super_primary =1 or is_primary=1)
	        String where ="raw_contact_id = " + personId + " AND mimetype ='vnd.android.cursor.item/photo'";
	        Cursor cursor = c.getContentResolver().query(u, null, where,    null, null);
	        if (cursor.moveToFirst()) {
	          data = cursor.getBlob(cursor.getColumnIndex("data15"));
	        }
	        cursor.close();
	        if (data ==null ||data.length == 0){
	            return BitmapFactory.decodeResource(c.getResources(), defaultIco);
	        }
	        else
	            return BitmapFactory.decodeByteArray(data, 0, data.length);
	    }
	
	
	
	
	
	
	
	
	    private Bitmap createBitmap( Bitmap src,String name)

	    {
	    	
	    if( src == null )
	    {
	    return null;
	    }
	    Paint paint=new Paint();
	    paint.setColor(Color.WHITE);
	    paint.setAntiAlias(true);
	    paint.setTextSize(50);
	    int w = src.getWidth();
	    int h = src.getHeight();
	    Bitmap newb = Bitmap.createBitmap( w, h, Config.ARGB_8888 );
	    Canvas cv = new Canvas( newb );
	    cv.drawBitmap( src, 0, 0, null );
	    FontMetrics fontMetrics = paint.getFontMetrics();
	    cv.drawText(name, w/2-25, h/2+15, paint);
	    cv.save( Canvas.ALL_SAVE_FLAG );
	    cv.restore();//存储
	    return newb;

	    }
	
	    public class setwordthread extends Thread
	    {
	       
	       
	       Handler handler=new Handler(){

	   		@SuppressLint("HandlerLeak")
			@Override
	   		public void handleMessage(Message msg) {
	   		
	   		}
	   		
	   	};
	       
	       
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				setwordPhoto();
				
				
			}

			@Override
			public synchronized void start() {
				// TODO Auto-generated method stub
				super.start();
			}
	    	
	    }
	
}