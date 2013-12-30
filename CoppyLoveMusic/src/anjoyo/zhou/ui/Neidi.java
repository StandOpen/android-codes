package anjoyo.zhou.ui;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import anjoyo.zhou.IntenterModel.IntertModel;
import anjoyo.zhou.neidiadapter.MyAdapter;
import anjoyo.zhou.thread.Mp3Thread;
import anjoyo.zhou.util.AndroidToWs;
import anjoyo.zhou.util.Final;
import anjoyo.zhou.util.Mp3Player;

public class Neidi extends Activity implements OnScrollListener {
	Context myContent;
	ProgressDialog pd;
	public static ArrayList<IntertModel> listData1 = new ArrayList<IntertModel>();
	ListView neidiListView;
	PopupWindow popWindow;
	ProgressDialog progressDialog;

	ProgressDialog bt;
	String spath;
	ImageView listImageView;
	View lodView;
	SeekBar downSeekBar;
	View listview;

	JSONArray array;
	JSONObject json;
	int count;

	int currentPage;
	private LinearLayout loadingLayout;
	private ProgressBar progressBar;
	private Thread mThread;
	String result;

	static MediaPlayer mpPlayer = Mp3Player.getMedia();
	public static int index = -1;

	MyAdapter myAdapter;

	private LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);

	private LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.FILL_PARENT);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.neidi);

		// Mp3Thread thread = new Mp3Thread();
		
		myContent = this;
		neidiListView = (ListView) findViewById(R.id.neidilist);
		// progressDialog = ProgressDialog.show(Neidi.this, "���ڼ��أ����Ժ�...", null,
		// true);
		// new Thread(runnable).start();

		listview = getLayoutInflater().inflate(R.layout.neidilistitem, null);
		listImageView = (ImageView) listview.findViewById(R.id.listimage);

		LinearLayout layout = new LinearLayout(this);
		// ���ò��� ˮƽ����
		layout.setOrientation(LinearLayout.HORIZONTAL);
		// ������
		progressBar = new ProgressBar(this);
		// ��������ʾλ��
		progressBar.setPadding(0, 0, 15, 0);
		// �ѽ��������뵽layout��
		layout.addView(progressBar, mLayoutParams);
		// �ı�����
		TextView textView = new TextView(this);
		textView.setText("������...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		// ���ı����뵽layout��
		layout.addView(textView, FFlayoutParams);
		// ����layout���������򣬼����뷽ʽ��
		layout.setGravity(Gravity.CENTER);
		loadingLayout = new LinearLayout(this);
		loadingLayout.addView(layout, mLayoutParams);
		loadingLayout.setGravity(Gravity.CENTER);

		myAdapter = new MyAdapter(myContent, listData1);
		neidiListView.setAdapter(myAdapter);

		// ��ListViewע���������
		neidiListView.setOnScrollListener(this);

		/**
		 * ���߲���
		 * 
		 */
		neidiListView
				.setOnItemClickListener(new ListView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String name = listData1.get(arg2).getMp3FileName();

						String path = Final.MUSIC_INTENT_DIR + name;
						if (spath == null) {
							Mp3Thread.musicPath = path;
							Mp3Thread.state = Final.ZAIXIAN_PLAY;
						} else if (spath.equals(path)) {
							if (mpPlayer.isPlaying()) {
								mpPlayer.pause();
							} else {
								mpPlayer.start();
							}

						} else if (!spath.equals(path)) {
							Mp3Thread.musicPath = path;
							Mp3Thread.state = Final.ZAIXIAN_PLAY;
						}
						spath = path;
						index = arg2;
						/*
						 * ��ת����ʽ���ҳ��
						 */
//						 Intent intent=new Intent();
//						 intent.setClass(Neidi.this, asd.class);
//						 intent.putExtra(Final.PLAY_INTENTVALUE, listData1);
//						 intent.putExtra(Final.PLAY_INDEX, arg2);
//						 startActivity(intent);
					}
				});
	}

	private String BindToListView(int pageIndex) {
		AndroidToWs callToWs = new AndroidToWs();
		int[] pl = new int[1];
		pl[0] = pageIndex;
		// ����
		String result = callToWs.GetUserWS("ListXs", pl);
		return result;
	}

	public void jiexi() {

		if (result != null) {
			try {
				array = new JSONArray(result);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��1����Ϊjson���ݺ��һ�����š���Ĭ�϶��һ�������ݣ����Լ�1�Ż���ʵ������
			for (int i = 0; i < array.length() - 1; i++) {
				// hm = new HashMap<String, Object>();
				IntertModel intertModel = new IntertModel();
				try {
					json = (JSONObject) array.get(i);

					String mp3FileName = json.getString(Final.MP3_FILENAME);
					String mp3Name = json.getString(Final.MP3_NAME);
					String mp3Artist = json.getString(Final.MP3_ARTIST);
					String mp3LrcName = json.getString(Final.LRC_NAME);
					String mp3LrcSize = json.getString(Final.LRC_SIZE);
					String mp3Image = json.getString(Final.MP3_IMAGE);
					intertModel.setMp3Name(mp3Name);
					intertModel.setMp3Artist(mp3Artist);
					intertModel.setMp3FileName(mp3FileName);
					intertModel.setMp3lrcName(mp3LrcName);
					intertModel.setMp3lrcSize(mp3LrcSize);
					intertModel.setMp3Image(mp3Image);
					listData1.add(intertModel);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	// /**
	// * �¿�һ���̣߳���ʼ������������
	// *
	// */
	//
	// Runnable runnable = new Runnable() {
	// public void run() {
	//
	// FileDown fileDown = new FileDown();
	// String resultVl = fileDown.DownLoad(Final.MUSIC_INTENT_PATH);
	// // ����SAXParserFactory����������
	// SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	// try {
	// // ����XMLReader����xml�ļ�������
	// XMLReader reader = saxParserFactory.newSAXParser()
	// .getXMLReader();
	// XMLSax xmlSax = new XMLSax();
	// reader.setContentHandler(xmlSax);
	// // ע�������¼�������������xml�ļ��������Ľ�����ʽ��
	// reader.parse(new InputSource(new StringReader(resultVl)));
	// listData1 = xmlSax.listData;
	// Message message = myhHandler.obtainMessage();
	// message.sendToTarget();
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }
	// };
	//
	// /**
	// * ������ɺ��������ʧ
	// *
	// */
	// Handler myhHandler = new Handler() {
	// public void handleMessage(Message msg) {
	// progressDialog.dismiss();
	//
	// MyAdapter myAdapter = new MyAdapter(Neidi.this, listData1);
	// neidiListView.setAdapter(myAdapter);
	// };
	// };

	/**
	 * ���ذ�ť����ǰҳ����ʧ
	 * 
	 * @param v
	 */
	public void fanhui(View v) {
		finish();

	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (firstVisibleItem + visibleItemCount == totalItemCount) {
			// ���߳�ȥ������������
			if (mThread == null || !mThread.isAlive()) {
				mThread = new Thread() {
					@Override
					public void run() {

						 count += 10;
						 currentPage = count / 10;

						result = BindToListView(currentPage);
						Message message = new Message();
						message.what = 1;
						message.obj = result;
						handler.sendMessage(message);

					}
				};
				mThread.start();
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

//				AndroidToWs callWs = new AndroidToWs();
//				String result2 = callWs.GetUserWS("countview", null);
//				int a = Integer.parseInt(result2);
//System.out.println("aaaaaaaaaa"+a);
//				a = a - a % 10;
//				if (listData1.size() < a) {
//System.out.println("sssssssssss"+listData1.size());
//					count += 10;
//					currentPage = count / 10;
//					Toast.makeText(Neidi.this, "��" + currentPage + "ҳ", 2000)
//							.show();
//					jiexi();
//				} else {
//					neidiListView.removeFooterView(loadingLayout);
//				}

				jiexi();
				// if (count==listData1.size()) {
				// neidiListView.removeFooterView(loadingLayout);
				// }

				// ����ˢ��Listview��adapter��������
				myAdapter.notifyDataSetChanged();

				break;
			default:
				break;
			}
		}

	};
}
