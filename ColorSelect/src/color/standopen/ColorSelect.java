package color.standopen;

import color.standopen.ColorPickerDialog.OnColorChangedListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ColorSelect extends Activity implements OnColorChangedListener{
    /** Called when the activity is first created. */
	private Button openbutton=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        openbutton=(Button)findViewById(R.id.button1);
        openbutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new ColorPickerDialog(ColorSelect.this, "ÎÄ¼þÑ¡Ôñ",new OnColorChangedListener() {
					
					public void colorChanged(int color) {
						// TODO Auto-generated method stub
						
					}
				}).show();
			}
		});
    }
	public void colorChanged(int color) {
		// TODO Auto-generated method stub
		
	}
}