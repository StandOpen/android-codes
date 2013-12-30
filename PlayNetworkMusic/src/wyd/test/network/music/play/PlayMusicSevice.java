package wyd.test.network.music.play;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.util.Log;
/**
 * 音乐播放、停止服务类
 * 
 * @author 汪渝栋
 * 
 */
public class PlayMusicSevice extends Service implements OnCompletionListener,
		OnErrorListener, OnBufferingUpdateListener, OnPreparedListener
{
	private static final String TAG = "PlayMusicSevice";
	/**
	 * 服务标示
	 */
	public static final String PLAY_MUCIC_SERVICE = "wyd.network.music.play.sevice";
	/**
	 * 音频准备播放广播
	 */
	public static final String PLAY_MUCIC_PREPARED = "wyd.network.music.play.prepared";

	/**
	 * 音频缓冲区加载广播
	 */
	public static final String PLAY_MUCIC_BUFFERINGUPDATE = "wyd.network.music.play.BufferingUpdate";

	/**
	 * 音频播放完毕广播
	 */
	public static final String PLAY_MUCIC_COMPLETION = "wyd.network.music.play.completion";

	/**
	 * mp3音乐下载路径
	 */
	String musicUri = "http://192.168.0.107/old/upload/test.mp3";

	/**
	 * 音乐播放对象
	 */
	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		Log.d(TAG, "我是第一个被调用的方法");

	}


	public int onStartCommand(Intent intent, int flags, int startId)
	{
		int stateVl = (Integer) intent.getExtras().get("state");
		int state = Integer.valueOf(stateVl);

		switch (state) {
		// 播放音乐
		case 0:
			mediaPlayer = new MediaPlayer();
			try
			{
				mediaPlayer.setDataSource(musicUri);
				// TODO 继续完善
				// 搞不懂这个位置用异步加载缓冲为什么到100%了也不能播放
				// 请高手指点 我是新人，刚学android，我的qq号码：120182051 邮箱:120182051@qq.com
				// mediaPlayer.prepareAsync();
				mediaPlayer.prepare();
			} catch (IllegalStateException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnErrorListener(this);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			if (!mediaPlayer.isPlaying())
			{
				mediaPlayer.start();
			}
			break;
		// 停止音乐
		case 1:
			if (mediaPlayer.isPlaying())
			{
				mediaPlayer.stop();
			}
			break;
		default:
			break;
		}

		// START_STICKY 如果服务结束，将重启该服务
		return 1;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	
	public void onPrepared(MediaPlayer mp)
	{
		Log.d(TAG, "0");
		// 音频准备的时候发送广播
		Intent prepared = new Intent(PLAY_MUCIC_PREPARED);
		sendBroadcast(prepared);
	}


	public void onBufferingUpdate(MediaPlayer mp, int percent)
	{
		Log.d(TAG, percent + "%");
		// 加载音频缓冲区的时候发送广播
		StringBuilder builder = new StringBuilder();
		String percentValue = builder.append(percent).append("%").toString();
		Intent bufferingUpdate = new Intent(PLAY_MUCIC_BUFFERINGUPDATE);
		bufferingUpdate.putExtra("percentValue", percentValue);
		sendBroadcast(bufferingUpdate);
	}
	public boolean onError(MediaPlayer mp, int what, int extra)
	{
		Log.d(TAG, "ERROR");
		return false;
	}
	public void onCompletion(MediaPlayer mp)
	{
		Log.d(TAG, "completion");
		if (mp != null)
		{
			Intent completion = new Intent(PLAY_MUCIC_COMPLETION);
			sendBroadcast(completion);
		}
	}
}
