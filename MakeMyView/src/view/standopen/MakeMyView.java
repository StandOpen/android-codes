package view.standopen;

import java.util.ArrayList;

import adapter.standopen.Search_Adapter;
import adapter.standopen.Search_Item;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MakeMyView extends Activity {
    private ListView list=null;
    private TextView top=null;
    private ArrayList<String> items;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }
    
    
    public void init()
    {
    list=(ListView)findViewById(R.id.list);
    top=(TextView)findViewById(R.id.top);
    items=new ArrayList<String>();
    
    
    for(int i=0;i<10;i++)
    {
    	
    	items.add("²âÊÔ");
    }
    
    list.setAdapter(new Search_Adapter(MakeMyView.this, items));
    
    
    
    
    
    Resources dr=this.getResources();
    list.setOverscrollHeader(dr.getDrawable(R.drawable.ic_launcher));
    	
    	
    	
    }
}