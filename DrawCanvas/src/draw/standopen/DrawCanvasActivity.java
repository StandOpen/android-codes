package draw.standopen;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class DrawCanvasActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }
    
    private void init() {  
        LinearLayout layout=(LinearLayout) findViewById(R.id.root);  
        final DrawView view=new DrawView(this);  
        view.setMinimumHeight(500);  
        view.setMinimumWidth(300);  
        //通知view组件重绘    
        view.invalidate();  
        layout.addView(view);  
          
    }  
}