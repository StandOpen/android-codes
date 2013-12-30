package anjoyo.zhou.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;
import anjoyo.zhou.IntenterModel.IntertModel;
import anjoyo.zhou.down.FileUtils;
import anjoyo.zhou.ui.OneActivity;
import anjoyo.zhou.ui.R;
import anjoyo.zhou.util.Final;

public class DownLoadService extends Service {

	// ֪ͨ��
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
	RemoteViews view = null;
	// ֪ͨ����תIntent
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;

	public class AAA extends Binder {
		public DownLoadService Getservice() {
			return DownLoadService.this;

		}
	}

	// HashMap<String, String> musicHs;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new AAA();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

	public void aaa(final IntertModel intertModel) {
		// musicHs = (HashMap<String, String>) intent
		// .getSerializableExtra(Final.MP3_INFO);
		this.updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.updateNotification = new Notification();
		// �������ع����У����֪ͨ�����ص�������
		updateIntent = new Intent(this, OneActivity.class);
		updateIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent,
				0);
		updateNotification.icon = R.drawable.amtplayer;
		updateNotification.contentIntent = updatePendingIntent;

		view = new RemoteViews(getPackageName(), R.layout.downitem);
		view.setProgressBar(R.id.DownSeekBar, 100, 0, false);
		view.setTextViewText(R.id.DownThisLength, "����0%");
		view.setTextViewText(R.id.DownMusicName,
				"�������أ�" + intertModel.getMp3Name());
		updateNotification.contentView = view;
		// ����֪ͨ����ʾ����
		updateNotification.tickerText = "�������أ�" + intertModel.getMp3Name();
		// updateNotification.setLatestEventInfo(this,musicHs.get(Mp3Final.SAX_MP3NAME),"0%",updatePendingIntent);
		// ����֪ͨ
		updateNotificationManager.notify(0, updateNotification);
		new Thread() {
			public void run() {
				/*���ظ���
				 * 
				 */
				String downPath = Final.MUSIC_INTENT_DIR
						+ intertModel.getMp3FileName();
				int reuslt = DownloadFile(downPath, "down",
						intertModel.getMp3FileName());
				/*���ظ��
				 * 
				 */
				String lrcpath = Final.MUSIC_INTENT_DIR
						+ intertModel.getMp3lrcName();
				int lrc = DownloadFile(lrcpath, "down",
						intertModel.getMp3lrcName());

				Message msg = downHandler.obtainMessage();
				msg.what = 0;
				msg.arg1 = reuslt;
				msg.sendToTarget();
			}
		}.start();
		// new Thread(runn).start();// ��������ص��ص㣬�����صĹ���
	}

	Handler downHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (msg.arg1 == 0) {
					updateNotificationManager.cancel(0);
					Toast.makeText(getApplicationContext(), "�������", 3000)
							.show();
				} else if (msg.arg1 == 1) {
					updateNotificationManager.cancel(0);
					Toast.makeText(getApplicationContext(), "�ļ��Ѵ��ڣ���������", 3000)
							.show();
				}
				break;
			case 1:
				view.setProgressBar(R.id.DownSeekBar, 100, msg.arg1, false);
				view.setTextViewText(R.id.DownThisLength, msg.arg1 + "%");
				updateNotification.contentView = view;
				updateNotification.contentIntent = updatePendingIntent;
				updateNotificationManager.notify(0, updateNotification);
				break;

			default:
				break;
			}

		};
	};

	/**
	 * �����ļ� parame1 ����·�� parame2 Ҫ������SD���е�Ŀ¼λ�� parame3 Ҫ������ļ�����
	 * 
	 * 
	 * 
	 * return 1 �ļ��Ѵ��� -1 ����ʧ�� 0 �ɹ�
	 * 
	 * 
	 * ����˼· 1 ��Ҫ����֤�ļ��Ƿ���� (�������Ҫ��sd���ϴ����ļ��У����ڵ���һ�δ����ļ��еĺ���) 2
	 * ��Ҫ��sd������ָ�����ļ����´���һ���ļ�
	 * 
	 * 
	 * */
	public int DownloadFile(String fileurl, String path, String fileName) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		File file = null;
		try {
			FileUtils fileUtils = new FileUtils();
			if (fileUtils.IsFileExists(path + "/" + fileName)) {
				return 1;
			}
			URL url = new URL(fileurl);
			HttpURLConnection httpconn = (HttpURLConnection) url
					.openConnection();
			inputStream = httpconn.getInputStream();

			fileUtils.CreateSDDir(path);

			file = fileUtils.CreateSDFile(path + "/" + fileName);

			outputStream = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int length = 0;
			int tol = 0;
			int totalSize = httpconn.getContentLength();
			boolean isSend = true;
			while ((length = inputStream.read(buffer)) != -1) {
				tol += length;
				outputStream.write(buffer, 0, length);
				int downPer = tol * 100 / totalSize;
				if (downPer % 2 == 0 && isSend) {
					Message msg = downHandler.obtainMessage();
					msg.arg1 = downPer;
					msg.what = 1;
					msg.sendToTarget();
					isSend = false;
				}
				if (downPer % 2 == 1) {
					isSend = true;
				}
				Thread.sleep(100);
			}

			outputStream.flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return 0;

	}

}
