package cutimage.standopen;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class OpenImageAndCut extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private Button selectImageBtn;
	private ImageView imageView;
	private File sdcardTempFile;
	private AlertDialog dialog;
	private int crop = 180;
	private Button openbtn = null;
	private Button camerabtn = null;
	public HttpClient httpClient = null;
	static final String LOGON_SITE = "localhost";
	static final int LOGON_PORT = 8080;
	Cookie[] cookies;
	String path = "mnt/sdcard/test.jpg";
	private Button upload = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		openbtn = (Button) findViewById(R.id.open);
		camerabtn = (Button) findViewById(R.id.cut);
		imageView = (ImageView) findViewById(R.id.show_image);
		upload = (Button) findViewById(R.id.upload);
		httpClient = new HttpClient();
		sdcardTempFile = new File(path);
		upload.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				uploadFile("http://jijie.cc/jijiemeet/photo_upload.php");
			}
		});
		camerabtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				intent.putExtra("output", Uri.fromFile(sdcardTempFile));
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);// 裁剪框比例
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", crop);// 输出图片大小
				intent.putExtra("outputY", crop);
				startActivityForResult(intent, 101);
			}
		});
		openbtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Intent intent=new Intent(); intent.setType("image/*");
				 * intent.setAction(Intent.ACTION_GET_CONTENT);
				 * startActivityForResult(intent,1);
				 */
				Intent intent = new Intent("android.intent.action.PICK");
				intent.setDataAndType(
						MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
				intent.putExtra("output", Uri.fromFile(sdcardTempFile));
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);// 裁剪框比例
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", crop);// 输出图片大小
				intent.putExtra("outputY", crop);
				startActivityForResult(intent, 100);

			}
		});

	}

	public void onClick(View v) {
		if (v == selectImageBtn) {
			if (dialog == null) {
				dialog = new AlertDialog.Builder(this).setItems(
						new String[] { "相机", "相册" },
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == 0) {
									Intent intent = new Intent(
											"android.media.action.IMAGE_CAPTURE");
									intent.putExtra("output",
											Uri.fromFile(sdcardTempFile));
									intent.putExtra("crop", "true");
									intent.putExtra("aspectX", 1);// 裁剪框比例
									intent.putExtra("aspectY", 1);
									intent.putExtra("outputX", crop);// 输出图片大小
									intent.putExtra("outputY", crop);
									startActivityForResult(intent, 101);
								} else {
									Intent intent = new Intent(
											"android.intent.action.PICK");
									intent.setDataAndType(
											MediaStore.Images.Media.INTERNAL_CONTENT_URI,
											"image/*");
									intent.putExtra("output",
											Uri.fromFile(sdcardTempFile));
									intent.putExtra("crop", "true");
									intent.putExtra("aspectX", 1);// 裁剪框比例
									intent.putExtra("aspectY", 1);
									intent.putExtra("outputX", crop);// 输出图片大小
									intent.putExtra("outputY", crop);
									startActivityForResult(intent, 100);
								}
							}
						}).create();
			}
			if (!dialog.isShowing()) {
				dialog.show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode == RESULT_OK) {
			// Bitmap bmp =
			// BitmapFactory.decodeFile(sdcardTempFile.getAbsolutePath());
			// imageView.setImageBitmap(bmp);
			// Uri uri=intent.getData();
			Uri uri = Uri.fromFile(sdcardTempFile);
			ContentResolver cur = this.getContentResolver();
			InputStream imgstream = null;
			try {
				imgstream = cur.openInputStream(uri);
				Bitmap bitmap = BitmapFactory.decodeStream(imgstream);
				imageView.setImageBitmap(bitmap);
			} catch (Exception e) {

			}
		}
	}

	public void upload() {
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,
				"Mozilla/5.0 (Windows NT 6.1");
		httpClient.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);
		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		PostMethod postMethod = new PostMethod(
				"http://192.168.0.107/old/upload_file.php");
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		NameValuePair[] data = { new NameValuePair("file", path), };
		postMethod.setRequestBody(data);
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == 200) {
				cookies = httpClient.getState().getCookies();
				byte[] b = postMethod.getResponseBody();
				String str = new String(b, "GBK");

			}

		} catch (HttpException e) {
			// text.setText("Please check your provided http    address!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();

		}
	}
	private void uploadFile(String uploadUrl)
	  {
	   String end = "\r\n";
	    String twoHyphens = "--";
	    String boundary = "******";
	    try
	    {
	      URL url = new URL(uploadUrl);
	      HttpURLConnection httpURLConnection = (HttpURLConnection) url
	          .openConnection();
	      // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
	      // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
	      httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
	      // 允许输入输出流
	      httpURLConnection.setDoInput(true);
	      httpURLConnection.setDoOutput(true);
	      httpURLConnection.setUseCaches(false);
	      // 使用POST方法
	      httpURLConnection.setRequestMethod("POST");
	      httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
	      httpURLConnection.setRequestProperty("Charset", "UTF-8");
	      httpURLConnection.setRequestProperty("Content-Type",
	          "multipart/form-data;boundary=" + boundary);

	      DataOutputStream dos = new DataOutputStream(
	          httpURLConnection.getOutputStream());
	      dos.writeBytes(twoHyphens + boundary + end);
	      dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
	          + path.substring(path.lastIndexOf("/") + 1)
	          + "\""
	          + end);
	      dos.writeBytes(end);

	      FileInputStream fis = new FileInputStream(path);
	      byte[] buffer = new byte[8192]; // 8k
	      int count = 0;
	      // 读取文件
	      while ((count = fis.read(buffer)) != -1)
	      {
	        dos.write(buffer, 0, count);
	      }
	      fis.close();

	      dos.writeBytes(end);
	      dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
	      dos.flush();

	      InputStream is = httpURLConnection.getInputStream();
	      InputStreamReader isr = new InputStreamReader(is, "utf-8");
	      BufferedReader br = new BufferedReader(isr);
	      String result = br.readLine();

	      Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	      dos.close();
	      is.close();

	    } catch (Exception e)
	    {
	      e.printStackTrace();
	      setTitle(e.getMessage());
	    }
		
	  }
	  }
