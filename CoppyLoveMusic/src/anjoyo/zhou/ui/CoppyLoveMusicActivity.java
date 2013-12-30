package anjoyo.zhou.ui;

import android.R.integer;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CoppyLoveMusicActivity extends TabActivity implements
		OnCheckedChangeListener {
	/** Called when the activity is first created. */
	private TabHost mHost;
	private Toast toast;
	private View secondLayout, thirdLayout;
	private RadioGroup radioderGroup;
	RadioButton radioButtonbendi, radioButton2, radioButton3, radioButton4;
	int wid,hei;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		
		//�õ�ƽļ���
		WindowManager wManager=(WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		wid=wManager.getDefaultDisplay().getWidth();
		hei=wManager.getDefaultDisplay().getHeight();
		
		radioButtonbendi = (RadioButton) findViewById(R.id.radio_button1);
		radioButton2 = (RadioButton) findViewById(R.id.radio_button2);
		radioButton3 = (RadioButton) findViewById(R.id.radio_button3);
		radioButton4 = (RadioButton) findViewById(R.id.radio_button4);
		// ʵ����TabHost
		mHost = this.getTabHost();

		// ���ѡ�
		mHost.addTab(mHost.newTabSpec("ONE").setIndicator("ONE")
				.setContent(new Intent(this, OneActivity.class)));
		mHost.addTab(mHost.newTabSpec("TWO").setIndicator("TWO")
				.setContent(new Intent(this, TwoActivity.class)));
		mHost.addTab(mHost.newTabSpec("THREE").setIndicator("THREE")
				.setContent(new Intent(this, ThreeActivity.class)));
		mHost.addTab(mHost.newTabSpec("FOUR").setIndicator("FOUR")
				.setContent(new Intent(this, FourActivity.class)));
		// ��xml�ļ�ת��ΪJava����
		secondLayout = getLayoutInflater().inflate(R.layout.toast1, null);
		thirdLayout = getLayoutInflater().inflate(R.layout.toast2, null);

		radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioderGroup.setOnCheckedChangeListener(this);
		radioButtonbendi.setChecked(true);

	}
	

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_button1:
			mHost.setCurrentTabByTag("ONE");
			break;
		case R.id.radio_button2:
			mHost.setCurrentTabByTag("TWO");
			/*
			 * �Զ���Toast����ʽ
			 */
			toast = new Toast(getApplicationContext()); // �Զ���Toast��ʽ
			toast.setGravity(0, wid,hei*1/5); // �Զ���Toastλ��
			toast.setView(secondLayout);
			TextView tv = (TextView) secondLayout.findViewById(R.id.tv);
			tv.setText("K���б��ǿյ�" + "\n" + "�Ͽ������Ը��ֿ⡱" + "\n" + "��ʼ�Ը��");

			toast.show();
			break;
		case R.id.radio_button3:
			mHost.setCurrentTabByTag("THREE");

			toast = new Toast(getApplicationContext()); // �Զ���Toast��ʽ
			toast.setGravity(0, 0, hei*1/5); // �Զ���Toastλ��
			toast.setView(thirdLayout);
			TextView tv2 = (TextView) thirdLayout.findViewById(R.id.tv);
			tv2.setText("¼���б��ǿյ�" + "\n" + "�Ͽ�������ҪK�衱" + "\n" + "��ʼ¼����");

			toast.show();
			break;
		case R.id.radio_button4:
			mHost.setCurrentTabByTag("FOUR");
			break;

		}
	}
	

}