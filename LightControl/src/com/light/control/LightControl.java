package com.light.control;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class LightControl extends Activity {
	private WebView myweb = null;
	private Dialog mDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myweb = (WebView) findViewById(R.id.wv);
        
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.loadUrl("http://www.jijie.cc/jijiemobile");
        showRoundProcessDialog(LightControl.this,R.layout.loading_process_dialog_icon);
		MyWebViewClient client = new MyWebViewClient();
		myweb.setWebViewClient(client);
    }
    
    
    
    
    
    
    
    
    
    
    
    class MyWebViewClient extends WebViewClient {

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			mDialog.dismiss();
			//Toast.makeText(Mywebview.this, "加载失败", 1000).show();
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			mDialog.dismiss();
		}
	}
	//等待dialog
	public void showRoundProcessDialog(Context mContext, int layout) {
		OnKeyListener keyListener = new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_HOME
						|| keyCode == KeyEvent.KEYCODE_SEARCH) {
					return true;
				}
				return false;
			}
		};
		mDialog = new AlertDialog.Builder(mContext).create();
		mDialog.setOnKeyListener(keyListener);
		mDialog.show();
		// 注意此处要放在show之后 否则会报异常
		mDialog.setContentView(layout);
	}
}