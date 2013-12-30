package wyd.test.network.music.play;

import static wyd.test.network.music.play.Constant.*;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Filterable;

/**
 * 实现功能:通过音乐播放地址，播放音乐
 * 
 * @author 汪渝栋
 * 
 */
public class PlayNetworkMusicActivity extends Activity
{

	private static final String TAG = "PlayNetworkMusicActivity";

	/**
	 * 显示信息的界面
	 */
	DisplayView displayView;

	/**
	 * 消息处理器 处理音乐开始和暂停
	 */
	public Handler musicHandler;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 设置屏幕纵向显示
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// 设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 设置填满屏幕
		getWindow().setLayout(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		displayView = new DisplayView(this);
		initScreenResolution();
		setContentView(displayView);

		musicHandler = new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				Intent service = new Intent(PlayMusicSevice.PLAY_MUCIC_SERVICE);
				switch (msg.what) {
				case 0:
					Log.d(TAG, "0");
					service.putExtra("state", 0);
					startService(service);
					break;
				case 1:
					Log.d(TAG, "1");
					service.putExtra("state", 1);
					startService(service);
					break;
				}
			};
		};
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		// 注册3个对应的广播接收器
		PreparedBroadReceiver preparedReceiver = new PreparedBroadReceiver();
		IntentFilter preparedFilter = new IntentFilter(
				PlayMusicSevice.PLAY_MUCIC_PREPARED);
		registerReceiver(preparedReceiver, preparedFilter);

		BufferingUpdateBroadReceiver bufferingUpdateReceiver = new BufferingUpdateBroadReceiver();
		IntentFilter bufferingUpdatefilter = new IntentFilter(
				PlayMusicSevice.PLAY_MUCIC_BUFFERINGUPDATE);
		registerReceiver(bufferingUpdateReceiver, bufferingUpdatefilter);

		CompletionBroadReceiver completionReceiver = new CompletionBroadReceiver();
		IntentFilter completionFilter = new IntentFilter(
				PlayMusicSevice.PLAY_MUCIC_COMPLETION);
		registerReceiver(completionReceiver, completionFilter);

	}

	/**
	 * 初始化屏幕分辨率
	 */
	private void initScreenResolution()
	{
		// 分辨率对象
		DisplayMetrics metrics = new DisplayMetrics();

		// 获取当前屏幕分辨率
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int tempWidth = metrics.widthPixels;
		int tempHeight = metrics.heightPixels;

		Log.d(TAG, "屏幕分辨率:tempWidth=" + tempWidth + ",tempHeight=" + tempHeight);
		if (tempWidth < tempHeight)
		{
			WIDTH = tempWidth;
			HEIGHT = tempHeight;
		} else
		{
			WIDTH = tempHeight;
			HEIGHT = tempWidth;
		}

		// 得到纵向比例
		float zoomx = WIDTH / CURRENT_WIDTH;
		float zoomy = HEIGHT / CURRENT_HEIGHT;
		Log.d(TAG, "纵向比例:zoomx=" + zoomx + ",zoomy=" + zoomy);

		// 判断的意思是宽度比例和高度比例谁小就用谁的比例为基准
		// 缩放比例按照纵向
		if (zoomx < zoomy)
		{
			xZoom = yZoom = zoomy;
		}
		// 缩放比例按照横向
		else
		{
			xZoom = yZoom = zoomx;
		}

		// 得到界面左上角的坐标
		START_X = (WIDTH - xZoom * 480) / 2;
		START_Y = (HEIGHT - yZoom * 800) / 2;
		Log.d(TAG, "游戏界面左上角坐标:START_X=" + START_X + ",START_Y=" + START_Y);
	}

	/**
	 * 音频准备播放广播接收器
	 * 
	 * @author 汪渝栋
	 * 
	 */
	class PreparedBroadReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			displayView.drawPrepared();
		}
	}

	/**
	 * 音频缓冲广播接收器
	 * 
	 * @author 汪渝栋
	 * 
	 */
	class BufferingUpdateBroadReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String percentValue = intent.getExtras().getString("percentValue");
			displayView.percentDraw(percentValue);
		}

	}

	/**
	 * 音频播放完毕广播接收器
	 * 
	 * @author 汪渝栋
	 * 
	 */
	class CompletionBroadReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			displayView.completionDraw();
		}
	}
}