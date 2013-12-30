
package com.sos.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
/**
 * Copyright (C) 2013 南京多拍多通信有限公司
 * 
 * @file: SosActivity.java
 * @brief:  
 *
 * @author: zhiwei.zhao
 * @created: 2013-3-15 15:30:00
 * @version: 
 */
public class SosActivity extends Activity {
	private static final int MAX_COUNT = 100; 
	private Button mback;
	private Button mcomit;
	private ImageButton mcamera;
	private Button msign;
	private TextView mtitle;
	private EditText mSuggest;
	private TextView mTextView = null;  
	private CheckBox msina;
	private CheckBox mtencent;
	private CheckBox mqq;
	private CheckBox mrenren;
	private CheckBox mqzoon;
	private CheckBox mdouban;
	private AlertDialog alertDialog;
	private ImageView imageView01;
	private ImageView imageView02;
	private ImageView imageView03;
	private Bitmap bitmap3;
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	private Bitmap bitmap;
	private ArrayList<String> arrrylist=new ArrayList<String>();
	private String imageFilePath;
	private static final String TAG="SosActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_sos);
		mback=(Button)findViewById(R.id.back);
		mcomit=(Button)findViewById(R.id.comit);
		mtitle=(TextView)findViewById(R.id.title);

		mSuggest=(EditText)findViewById(R.id.suggest);
		mSuggest.setSingleLine(false);
		mSuggest.addTextChangedListener(mTextWatcher); 
		mSuggest.setSelection(mSuggest.length());
		mTextView = (TextView) findViewById(R.id.count);
		setLeftCount();

		mcamera=(ImageButton)findViewById(R.id.camera);

		msign=(Button)findViewById(R.id.sign);

		msina=(CheckBox)findViewById(R.id.sina);
		mtencent=(CheckBox)findViewById(R.id.tencent);
		mqq=(CheckBox)findViewById(R.id.qq);
		mrenren=(CheckBox)findViewById(R.id.renren);
		mqzoon=(CheckBox)findViewById(R.id.qzoon);
		mdouban=(CheckBox)findViewById(R.id.douban);


		imageView01 = (ImageView)findViewById(R.id.img1);
		imageView02 = (ImageView)findViewById(R.id.img2);
		imageView03 = (ImageView)findViewById(R.id.img3);

		mback.setOnClickListener(new OnClickListener() { //activity返回键

			@Override
			public void onClick(View v) {
				SosActivity.this.finish();	
			}
		});

		mcamera.setOnClickListener(new OnClickListener() {   //照相机键

			@Override
			public void onClick(View v) {


				showCustomAlertDialog();
			}

		});


	}

	private void showCustomAlertDialog() {  //  自定义dialog
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.show();
		Window win = alertDialog.getWindow();

		WindowManager.LayoutParams lp = win.getAttributes();        
		win.setGravity(Gravity.LEFT | Gravity.TOP);
		lp.x = 100; // 新位置X坐标      
		lp.y = 600; // 新位置Y坐标        
		//lp.width = 300; // 宽度        
		//lp.height = 300; // 高度        
		lp.alpha = 0.7f; // 透明度
		win.setAttributes(lp);


		//设置自定义的对话框布局
		win.setContentView(R.layout.dialog);

		//关闭对话框按钮事件
		Button enterBtn = (Button)win.findViewById(R.id.camera_cancel);
		enterBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.cancel();
			}
		});
		Button camera_phone=(Button)win.findViewById(R.id.camera_phone);
		camera_phone.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				systemPhoto();
			}


		});
		Button camera_camera = (Button)win.findViewById(R.id.camera_camera);
		camera_camera.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cameraPhoto();
			}


		});


	}
	private void systemPhoto() {  //打开系统相册

		Intent intent = new Intent();

		intent.setType("image/*");

		intent.setAction(Intent.ACTION_GET_CONTENT);

		startActivityForResult(intent, 1);
		alertDialog.cancel();
		//arrrylist.add(imageFilePath);
		//Log.d(TAG, "arrrylist--->0"+arrrylist.get(0));



	}
	private void cameraPhoto(){   //调用相机拍照
		// TODO Auto-generated method stub
		/*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
		startActivityForResult(intent, 1);
		 */

		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");  

		startActivityForResult(intent,Activity.DEFAULT_KEYS_DIALER);  
		cameraCamera(0, 0, intent);  //自定义方法

		//store();
	}


	@Override

	protected void onActivityResult(int requestCode, int resultCode, Intent data){// 获取图像地址并显示到ImageView上
		if(requestCode == 1 && resultCode == RESULT_OK)
		{

			try{
				Uri uri = data.getData();
				Cursor cursor = getContentResolver().query(uri, null, null, null, null);//游标
				cursor.moveToFirst();
				imageFilePath = cursor.getString(1);
				Log.d(TAG, "imageFilePath"+imageFilePath);
				arrrylist.add(imageFilePath);
				//Log.d(TAG, "arrrylist--->0"+arrrylist.get(0)+arrrylist.get(1)+arrrylist.get(2));
				int i= arrrylist.size();
				
				//////////////////////////////////////////////////////////////////////////////第一个ImageView
				if(i==1){FileInputStream fis = new FileInputStream(arrrylist.get(0));
				bitmap = BitmapFactory.decodeStream(fis); 
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				System.out.println("压缩前的高宽----> width: " + width + " height:" + height);
				BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView01.getDrawable();
				if(bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled())
				{
					bitmapDrawable.getBitmap().recycle();
				}
				Bitmap bitTemp = null;
				bitTemp = CompressionBigBitmap(bitmap); //压缩获取的图像
				int widthTemp = bitTemp.getWidth();
				int heightTemp = bitTemp.getHeight();
				System.out.println("压缩后的宽高----> width: " + heightTemp + " height:" + widthTemp);
				imageView01.setImageBitmap(bitTemp); //显示图像
				fis.close();
				cursor.close();
				}
				///////////////////////////////////////////////////////////////////////////2.1
				else if(i==2){FileInputStream fis = new FileInputStream(arrrylist.get(0));
				bitmap = BitmapFactory.decodeStream(fis); 
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				System.out.println("压缩前的高宽----> width: " + width + " height:" + height);
				// TODO bitmap recycle。
				BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView01.getDrawable();
				if(bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled())
				{
					bitmapDrawable.getBitmap().recycle();
				}
				Bitmap bitTemp = null;
				bitTemp = CompressionBigBitmap(bitmap); //压缩获取的图像
				int widthTemp = bitTemp.getWidth();
				int heightTemp = bitTemp.getHeight();
				System.out.println("压缩后的宽高----> width: " + heightTemp + " height:" + widthTemp);
				imageView01.setImageBitmap(bitTemp); //显示图像
				////////////////////////////////////////////////////////////////////////////////////////////2.2
				FileInputStream fis2 = new FileInputStream(arrrylist.get(1));
				bitmap2 = BitmapFactory.decodeStream(fis2); 
				int width2 = bitmap2.getWidth();
				int height2 = bitmap2.getHeight();
				System.out.println("压缩前的高宽----> width: " + width2 + " height:" + height2);
				// TODO bitmap recycle。
				BitmapDrawable bitmapDrawable2 = (BitmapDrawable)imageView02.getDrawable();
				if(bitmapDrawable2 != null && !bitmapDrawable2.getBitmap().isRecycled())
				{
					bitmapDrawable2.getBitmap().recycle();
				}
				Bitmap bitTemp2 = null;
				bitTemp2 = CompressionBigBitmap(bitmap2); //压缩获取的图像
				int widthTemp2 = bitTemp2.getWidth();
				int heightTemp2 = bitTemp2.getHeight();
				System.out.println("压缩后的宽高----> width: " + heightTemp2 + " height:" + widthTemp2);
				imageView02.setImageBitmap(bitTemp2); //显示图像
				fis.close();
				cursor.close();
				}
				/////////////////////////////////////////////////////////////////////////////////////3.1
				else if(i==3){FileInputStream fis = new FileInputStream(arrrylist.get(0));
				bitmap = BitmapFactory.decodeStream(fis); 

				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				System.out.println("压缩前的高宽----> width: " + width + " height:" + height);
				// TODO bitmap recycle。
				BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView01.getDrawable();
				if(bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled())
				{
					bitmapDrawable.getBitmap().recycle();
				}
				Bitmap bitTemp = null;
				bitTemp = CompressionBigBitmap(bitmap); //压缩获取的图像
				int widthTemp = bitTemp.getWidth();
				int heightTemp = bitTemp.getHeight();
				System.out.println("压缩后的宽高----> width: " + heightTemp + " height:" + widthTemp);
				imageView01.setImageBitmap(bitTemp); //显示图像
				//////////////////////////////////////////////////////////////////////////////////////3.3
				FileInputStream fis3 = new FileInputStream(arrrylist.get(2));
				bitmap3 = BitmapFactory.decodeStream(fis3); 
				int width3 = bitmap3.getWidth();
				int height3 = bitmap.getHeight();
				System.out.println("压缩前的高宽----> width: " + width3 + " height:" + height3);
				// TODO bitmap recycle。
				BitmapDrawable bitmapDrawable3 = (BitmapDrawable)imageView03.getDrawable();
				if(bitmapDrawable3 != null && !bitmapDrawable3.getBitmap().isRecycled())
				{
					bitmapDrawable3.getBitmap().recycle();
				}
				Bitmap bitTemp3 = null;
				bitTemp3 = CompressionBigBitmap(bitmap3); //压缩获取的图像
				int widthTemp3 = bitTemp.getWidth();
				int heightTemp3 = bitTemp.getHeight();
				System.out.println("压缩后的宽高----> width: " + heightTemp3 + " height:" + widthTemp3);
				imageView03.setImageBitmap(bitTemp3); //显示图像
				////////////////////////////////////////////////////////////////////////////////////////////3.2
				FileInputStream fis2 = new FileInputStream(arrrylist.get(1));
				bitmap2 = BitmapFactory.decodeStream(fis2); 

				int width2 = bitmap2.getWidth();
				int height2 = bitmap2.getHeight();
				System.out.println("压缩前的高宽----> width: " + width2 + " height:" + height2);
				// TODO bitmap recycle。
				BitmapDrawable bitmapDrawable2 = (BitmapDrawable)imageView02.getDrawable();
				if(bitmapDrawable2 != null && !bitmapDrawable2.getBitmap().isRecycled())
				{
					bitmapDrawable2.getBitmap().recycle();
				}

				Bitmap bitTemp2 = null;

				bitTemp2 = CompressionBigBitmap(bitmap2); //压缩获取的图像
				int widthTemp2 = bitTemp2.getWidth();

				int heightTemp2 = bitTemp2.getHeight();

				System.out.println("压缩后的宽高----> width: " + heightTemp2 + " height:" + widthTemp2);
				imageView02.setImageBitmap(bitTemp2);
				fis.close();
				cursor.close();
				}else{Toast.makeText(SosActivity.this, "最多只能添加三张照片！", Toast.LENGTH_SHORT).show();}

			}catch (Exception e) {
				e.printStackTrace();
			}

		}  
		super.onActivityResult(requestCode, resultCode, data);
	}  
	private Bitmap CompressionBigBitmap(Bitmap bitmap){ // 对图像进行缩放

		Bitmap destBitmap = null;

		if(bitmap.getHeight() > 640){  // 图片宽度调整为640，大于这个比例的，按一定比例缩放到宽度为640

			float scaleValue = (float)(640f / bitmap.getHeight());

			System.out.println("---->" + scaleValue);

			Matrix matrix = new Matrix();

			//matrix.setRotate(90);  // 针对系统拍照，旋转90°

			matrix.postScale(scaleValue, scaleValue);



			destBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

		}else{

			return bitmap;

		}

		return destBitmap;

	}

	protected void cameraCamera(int requestCode, int resultCode, Intent data) { //拍照后获取照片 
		super.onActivityResult(requestCode, resultCode, data);  
		if (resultCode == Activity.RESULT_OK) {  
			String sdStatus = Environment.getExternalStorageState();  
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用   
				Log.i("TestFile",  
						"SD card is not avaiable/writeable right now.");  
				return;  
			}  
			String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";     
			Toast.makeText(this, name, Toast.LENGTH_LONG).show();  
			Bundle bundle = data.getExtras();  
			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式   
			
			FileOutputStream b = null;  
			File file = new File("/sdcard/myImage/");  
			file.mkdirs();// 创建文件夹   
			String fileName = "/sdcard/myImage/"+name;  
			try {  
				b = new FileOutputStream(fileName);  
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件   
			} catch (FileNotFoundException e) {  
				e.printStackTrace();  
			} finally {  
				try {  
					b.flush();  
					b.close();  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  

			ImageView img1=((ImageView) findViewById(R.id.img1));
			img1.setImageBitmap(bitmap);// 将图片显示在ImageView里   
		//	img1.showContextMenu();
			}
	} 


	private TextWatcher mTextWatcher = new TextWatcher() {  

		private int editStart;  

		private int editEnd;  

		public void afterTextChanged(Editable s) {  
			editStart = mSuggest.getSelectionStart();  
			editEnd = mSuggest.getSelectionEnd();  

			// 先去掉监听器，否则会出现栈溢出   
			mSuggest.removeTextChangedListener(mTextWatcher);  

			// 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度   
			// 因为是中英文混合，单个字符而言，calculateLength函数都会返回1   
			while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作   
				s.delete(editStart - 1, editEnd);  
				editStart--;  
				editEnd--;  
			}  
			// mEditText.setText(s);将这行代码注释掉就不会出现后面所说的输入法在数字界面自动跳转回主界面的问题了  
			mSuggest.setSelection(editStart);  

			// 恢复监听器   
			mSuggest.addTextChangedListener(mTextWatcher);  

			setLeftCount();  
		}  

		public void beforeTextChanged(CharSequence s, int start, int count,  
				int after) {  

		}  

		public void onTextChanged(CharSequence s, int start, int before,  
				int count) {  

		}  

	}; 
	private long calculateLength(CharSequence c) {  
		double len = 0;  
		for (int i = 0; i < c.length(); i++) {  
			int tmp = (int) c.charAt(i);  
			if (tmp > 0 && tmp < 127) {  
				len += 0.5;  
			} else {  
				len++;  
			}  
		}  
		return Math.round(len);  
	} 
	private void setLeftCount() {  
		mTextView.setText("还可以输入"+String.valueOf((MAX_COUNT - getInputCount()))+"个字符！");  
	}
	private long getInputCount() {  
		return calculateLength(mSuggest.getText().toString());  
	}  

}

