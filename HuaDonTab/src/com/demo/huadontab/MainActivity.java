package com.demo.huadontab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
public class MainActivity extends Activity implements OnCheckedChangeListener{
	
	private RadioGroup mRadioGroup;
	private RadioButton mRadioButton1;
	private RadioButton mRadioButton2;
	private RadioButton mRadioButton3;
	private RadioButton mRadioButton4;
	private RadioButton mRadioButton5;
	private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
	private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        iniController();
        iniListener();
        
        mRadioButton1.setChecked(true);
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
        
    }
    
   
    
    /**
	 * RadioGroup点击CheckedChanged监听
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.btn1) {
			
			
	
		}else if (checkedId == R.id.btn2) {
		
		
			
		}else if (checkedId == R.id.btn3) {
			
		
		}else if (checkedId == R.id.btn4) {
			
		
		}else if (checkedId == R.id.btn5) {
			
		
		}
		
		mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();//更新当前蓝色横条距离左边的距离	
		mHorizontalScrollView.smoothScrollTo((int)mCurrentCheckedRadioLeft-(int)getResources().getDimension(R.dimen.rdo2), 0);
	}
    
	/**
     * 获得当前被选中的RadioButton距离左侧的距离
     */
	private float getCurrentCheckedRadioLeft() {
		// TODO Auto-generated method stub
		if (mRadioButton1.isChecked()) {
			return getResources().getDimension(R.dimen.rdo1);
		}else if (mRadioButton2.isChecked()) {
			return getResources().getDimension(R.dimen.rdo2);
		}else if (mRadioButton3.isChecked()) {
			return getResources().getDimension(R.dimen.rdo3);
		}else if (mRadioButton4.isChecked()) {
			return getResources().getDimension(R.dimen.rdo4);
		}else if (mRadioButton5.isChecked()) {
			return getResources().getDimension(R.dimen.rdo5);
		}
		return 0f;
	}

	private void iniListener() {
		// TODO Auto-generated method stub
		mRadioGroup.setOnCheckedChangeListener(this);
	}

	private void iniController() {
		// TODO Auto-generated method stub
		mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
		mRadioButton1 = (RadioButton)findViewById(R.id.btn1);
		mRadioButton2 = (RadioButton)findViewById(R.id.btn2);
		mRadioButton3 = (RadioButton)findViewById(R.id.btn3);
		mRadioButton4 = (RadioButton)findViewById(R.id.btn4);
		mRadioButton5 = (RadioButton)findViewById(R.id.btn5);
		mHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
	}

	


}
