package anjoyo.zhou.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import anjoyo.zhou.XMLSax.XMLSax;
import anjoyo.zhou.bendiadapter.MyAdapter;
import anjoyo.zhou.lrc.LrcContent;
import anjoyo.zhou.model.MP3Model;
import anjoyo.zhou.thread.MainShuaXin;
import anjoyo.zhou.thread.Mp3Thread;
import anjoyo.zhou.util.Final;
import anjoyo.zhou.util.Mp3Player;

public class OneActivity extends Activity {
	String spath;
	static Button btn1;
	ViewFlipper viewFlipper;
	ListView listView;
	TextView MusicLength;
	public static int index = 0;
	static MediaPlayer mpPlayer = Mp3Player.getMedia();
	MediaPlayer mediaPlayer;
	MediaPlayer aaa;
	static TextView allmusic;
	static View oldView;
	static View view1;
	ImageView imageView1;
	public static int musicIndex = -1;
	ProgressDialog pb;
	ImageView imageView;
	MainShuaXin mainShuaXin = new MainShuaXin();
	public static ArrayList<MP3Model> listData;
	static Mp3Player mp3Player=new Mp3Player();
	View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.oneactivity);
		Mp3Thread thread = new Mp3Thread();
		btn1 = (Button) findViewById(R.id.toplayer);
		listView = (ListView) findViewById(R.id.listview);
		allmusic = (TextView) findViewById(R.id.allmusic);
		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		listData = new ArrayList<MP3Model>();
		btn1.setVisibility(Button.GONE);
		Binder();// �����б���ʾ����

		/**
		 * �������
		 * 
		 */

		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				MP3Model hs = listData.get(arg2);
				// �õ��� �о�����ѭ��ʱÿһ�ε�put �������ܻ�ȡ�����׸�����·���� ��·���ı�ʾΪ/mnt/sdcard/XXX.mp3
				String path = hs.getMusicPath();
				index = arg2;
				btn1.setVisibility(Button.VISIBLE);
				Mp3Thread.musicPath = path;
				Mp3Thread.state = Final.PLAY;
				Intent intent = new Intent();
				intent.setClass(OneActivity.this, BoFangYeMian.class);
				intent.putExtra(Final.PLAY_INTENTVALUE, listData);
				intent.putExtra(Final.PLAY_INDEX, index);
				startActivity(intent);
				musicIndex = arg2;
				sendLrc(listData.get(arg2).getFilename());
				
			}

		});

		/**
		 * ����ɾ��
		 * 
		 */
		listView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				final File file = new File(listData.get(arg2).getMusicPath());
				Builder dialog = new AlertDialog.Builder(OneActivity.this);
				dialog.setTitle("��ܰ��ʾ");
				dialog.setMessage("�Ƿ�ɾ��<" + listData.get(arg2).getMusicName()
						+ ">���׸�");
				dialog.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								delete(file);
								if (mpPlayer.isPlaying()) {
									mpPlayer.stop();
								}
							
								scanSdCard();
							}
						});
				dialog.setNeutralButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				dialog.show();
				return false;
			}
		});

	}


	/**
	 * ɾ������
	 * 
	 * 
	 * @param path
	 */
	public void delete(File path) {
		if (path.delete()) {
			Toast.makeText(this, "�ɹ�", 2000).show();
		} else {
			Toast.makeText(this, "ʧ��", 2000).show();
		}
	}

	/**
	 * ˢ��ȫ������
	 * 
	 */

	public static Handler hand = new Handler() {
		public void handleMessage(Message message) {
			message.arg1 = 1;
			allmusic.setText(listData.size() + "�׸�");
		}

	};

	/**
	 * ���ò˵�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, R.string.menu1).setIcon(R.drawable.menu_item_scanner);
		menu.add(0, 2, 1, R.string.menu2).setIcon(R.drawable.menu_item_exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			scanSdCard();
			break;
		case 2:
			System.exit(0);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 1 ��ѯý�����ݿ�õ�һ��Cursor���� 2 ����ѭ���󶨵�ArrayList�� 3 ʹ��Adapter��ListView
	 */
	private void Binder() {
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null, null, null, null);
		try {
			listData.clear();// ����ˢ��ʱ�Ȱ��ϴΰ���ArrayList������������
			if (cur.getCount() > 0) {// ���ɨ�赽�����ݴ�����ͽ�ifѭ��
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {// ����������ý�����ݿ���б���
					MP3Model model = new MP3Model();
					// ����
					model.setMusicAlum(cur.getString(cur
							.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
					// �������ܲ���ʱ��
					model.setMusicLength(cur.getString(cur
							.getColumnIndex(MediaStore.Audio.Media.DURATION)));
					// ������·��
					model.setMusicPath(cur.getString(cur
							.getColumnIndex(MediaStore.Audio.Media.DATA)));
					// ������
					model.setMusicName(cur.getString(cur
							.getColumnIndex(MediaStore.Audio.Media.TITLE)));
					// ����id
					model.setMusicId(cur.getString(cur
							.getColumnIndex(MediaStore.Audio.Media._ID)));
//					model.setMusicLrcname(cur.getString(cur.getColumnIndex()))
					//�ļ���
					model.setFilename(cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
					
					listData.add(model);
				}
			} else {
				Toast.makeText(OneActivity.this, "û�в�ѯ��������Ϣ������Ӹ���", 3000)
						.show();
			}
			MyAdapter myAdapter = new MyAdapter(OneActivity.this, listData);
			listView.setAdapter(myAdapter);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (!cur.isClosed()) {// �����ѯ������cur��û�йرգ��͹رյ�cur
				cur.close();
			}
		}
	}

	/**
	 * ʱ��ת���ķ���
	 * 
	 * @param time
	 * @return
	 */
	public static String GetFormatTime(int time) {
		SimpleDateFormat sim = new SimpleDateFormat("mm:ss");
		return sim.format(time);

	}

	ScanSdFilesReceiver scanReceiver;

	/**
	 * ����ϵͳapiɨ��sd��
	 */
	private void scanSdCard() {
		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		intentFilter.addDataScheme("file");
		scanReceiver = new ScanSdFilesReceiver();
		registerReceiver(scanReceiver, intentFilter);
		sendBroadcast(new Intent(
				Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}
	/**��MP3��ת��Ϊ�����
	 * 
	 * @param fileName
	 */
	  public static void sendLrc(String fileName){
			Message mg=BoFangYeMian.mmHandler.obtainMessage();
			mg.obj=fileName.replaceAll("mp3", "lrc");
//System.out.println(fileName.replaceAll("mp3", "lrc")+"aaaaaaaaaa");
			mg.sendToTarget();
		}


	/**
	 * ע��ɨ�迪ʼ/��ɵĹ㲥
	 */
	private class ScanSdFilesReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {
				// ��ϵͳ��ʼɨ��sd��ʱ��Ϊ���û����飬���Լ���һ���ȴ���
				if (pb == null) {
					pb = ProgressDialog.show(OneActivity.this, "����ɨ��", null,
							true);
				}
				scanHandler.sendEmptyMessage(0);
			}
			if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
				// ��ϵͳɨ�����ʱ��ֹͣ��ʾ�ȴ��򣬲����²�ѯContentProvider
				scanHandler.sendEmptyMessage(1);
			}
		}

	}

	private Handler scanHandler = new Handler() { // ����һ����Ϣ���ж��� ���Է������ݵ����̣߳����´���
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:

				break;
			case 1: // ����Ϣ���ݵ�����Ϊ 1ʱ����ʾ�Ѿ�ɨ����ϣ����²�ѯ����ȡ����
				pb.dismiss();
				Toast.makeText(OneActivity.this, "ɨ�����", 2000).show();
				Binder();
				break;
			default:

				break;

			}
		}
	};

	/**
	 * �����ת������ҳ��
	 * 
	 * @param v
	 */

	public void toplayer(View v) {

		//������ڲ��ţ��Ͱ�listdata������һ��ҳ�棬���±괫��ȥ
		if (mpPlayer.isPlaying()) {
			Intent intent = new Intent();
			intent.setClass(this, BoFangYeMian.class);
			intent.putExtra(Final.PLAY_INTENTVALUE, listData);
			intent.putExtra(Final.PLAY_INDEX, index);
			startActivity(intent);
			//���û�в���
		}else {
			//�����û�е���б��ʹӵ�һ�׿�ʼ����
			if (index!=-1) {
				Intent intent = new Intent();
				intent.setClass(this, BoFangYeMian.class);
				intent.putExtra(Final.PLAY_INTENTVALUE, listData);
				intent.putExtra(Final.PLAY_INDEX, 0);
				startActivity(intent);
				//�������ͣ���ʹ���ȥֵ
			}else {
				MP3Model hs = listData.get(index);
				String path = hs.getMusicPath();
				Mp3Thread.musicPath = path;
				Mp3Thread.state = Final.PLAY;
				Intent intent = new Intent();
				intent.setClass(this, BoFangYeMian.class);
				intent.putExtra(Final.PLAY_INTENTVALUE, listData);
				intent.putExtra(Final.PLAY_INDEX, index);
				startActivity(intent);
			}
		}
	}

	/**
	 * ����back�����˳�����
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("ȷ���˳�������");
			builder.setPositiveButton("ȷ��", new AlertDialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			builder.setNeutralButton("ȡ��", new AlertDialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			builder.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �����ת����ת��ViewFlipper��������һ��ҳ��
	 * 
	 * @param v
	 */
	public void aaa(View v) {
		viewFlipper.showNext();
	}

	/**
	 * �����ת����ת��ViewFlipper��������һ��ҳ��
	 * 
	 * @param v
	 */
	public void fanhui(View v) {
		viewFlipper.showPrevious();
	}
}
