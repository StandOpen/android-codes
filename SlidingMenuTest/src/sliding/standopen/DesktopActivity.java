package sliding.standopen;

import com.renren.android.desktop.Desktop;
import com.renren.android.desktop.Desktop.onChangeViewListener;
import com.renren.android.newsfeed.Nearby;
import com.renren.android.newsfeed.NewsFeed;

import sliding.standopen.FlipperLayout.OnOpenListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;


public class DesktopActivity extends Activity implements OnOpenListener {
	
	private FlipperLayout mRoot;
	private Desktop mDesktop;

	private NewsFeed mNewsFeed;
    private Nearby near;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		mRoot = new FlipperLayout(DesktopActivity.this);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		mRoot.setLayoutParams(params);
		mDesktop = new Desktop(this);
		mNewsFeed = new NewsFeed(this, this);
		near=new Nearby(this, this);
		mRoot.addView(mDesktop.getView(), params);
		mRoot.addView(mNewsFeed.getView(), params);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(mRoot);
		mNewsFeed.setOnOpenListener(this);
		near.setOnOpenListener(this);
		mDesktop.setOnChangeViewListener(new onChangeViewListener() {
			
			public void onChangeView(int arg0) {
				// TODO Auto-generated method stub
			//	if(arg0==0)
			//		mRoot.close(mNewsFeed.getView());
			//	if(arg0==1)
			//	mRoot.close(near.getView());
				switch(arg0)
				{
				case 0:
					mRoot.close(mNewsFeed.getView());
					break;
				case 1:
					mRoot.close(near.getView());
					break;
				case 2:
					mRoot.close(mNewsFeed.getView());
					break;
				case 3:
					mRoot.close(mNewsFeed.getView());
					break;
				}
			
				Toast.makeText(DesktopActivity.this, ""+arg0, Toast.LENGTH_SHORT).show();
			}
		});
	}


	

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_CLOSE) {
				mRoot.open();
			} else {
				dialog();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
  //退出对话框
	private void dialog() {
		AlertDialog.Builder builder = new Builder(DesktopActivity.this);
		builder.setMessage("您确定要退出吗?");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}

	public void open() {
		if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_CLOSE) {
			mRoot.open();
		}
	}



	
	
}
