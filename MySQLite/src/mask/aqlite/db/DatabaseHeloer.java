package mask.aqlite.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHeloer extends SQLiteOpenHelper{

	private static final int VERSION=1;
	//��SQLiteOpenHelper�����б����������Ĺ��캯��
	public DatabaseHeloer(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHeloer(Context context,String name){
		this(context,name, VERSION);
	}
	public DatabaseHeloer(Context context,String name,int version){
		this(context,name,null,version);
	}
	//�ú������ڵ�һ�δ������ݿ��ʱ��ִ�У�ʵ�������ڵ�һ�εõ�SQLiteDatabase�����ʱ�����
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("Create a Database");
		db.execSQL("CREATE TABLE  user(id int,name varchar(20))");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void Close() {  
        this.getWritableDatabase().close();  
    }  
	

}
