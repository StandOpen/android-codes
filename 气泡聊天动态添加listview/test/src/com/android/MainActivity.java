package com.android;



import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView talkView;
	Button addbutton=null;
	private ArrayList<DetailEntity> list = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        talkView = (ListView)findViewById(R.id.list);
        addbutton=(Button)findViewById(R.id.add);
        
        
        
        addbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DetailEntity d5 = new DetailEntity("我","2010-04-26","豫州镇县人!",R.layout.list_say_me_item);
				list.add(d5);
		        talkView.setAdapter(new DetailAdapter(MainActivity.this, list));
		       
			}
		});
        
        
        
        
        
        list = new ArrayList<DetailEntity>();
		DetailEntity d1 = new DetailEntity("我","2010-04-26","你好!",R.layout.list_say_me_item);
		list.add(d1);
		DetailEntity d2 = new DetailEntity("范彬","2010-04-26","你好!",R.layout.list_say_he_item);
		list.add(d2);
		DetailEntity d3 = new DetailEntity("范彬","2010-04-26","哪里人氏?",R.layout.list_say_he_item);
		list.add(d3);
		DetailEntity d4 = new DetailEntity("我","2010-04-26","豫州镇县人!",R.layout.list_say_me_item);
		list.add(d4);
        
        talkView.setAdapter(new DetailAdapter(MainActivity.this, list));
        
        
    }
}