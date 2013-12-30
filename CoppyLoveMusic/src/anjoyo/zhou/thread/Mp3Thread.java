package anjoyo.zhou.thread;



import android.os.Message;
import anjoyo.zhou.model.MP3Model;
import anjoyo.zhou.ui.BoFangYeMian;
import anjoyo.zhou.ui.OneActivity;
import anjoyo.zhou.util.Final;
import anjoyo.zhou.util.Mp3Player;

public class Mp3Thread extends Thread {
	public static int state = 0;// �˱����ǲ���״̬��
	int index;
	public static boolean isStop = true;// �˱����ǿ����߳�ѭ��״̬�ģ���Ϊfalseʱ��ѭ���������߳��˳�

	public static String musicPath = "";
	static Mp3Player mp3Player = new Mp3Player();// ʵ����һ��Mp3Player����

	/**
	 * ���캯�����洴��һ�����̣߳������ù��캯��ʱ�߳̾Ϳ�ʼ����
	 */
	public Mp3Thread(){
		Thread thread = new Thread(this);
		thread.start();
	}


	public void run() {
		// TODO Auto-generated method stub
		try {
			while (isStop) {
				switch (state) {
				case Final.WAIT:
					break;
				case Final.PLAY:
					mp3Player.Play(musicPath);// ����Mp3Playe�����ص�Play�������������ݲ���
					state = Final.WAIT;// ���õ�ǰ��״̬Ϊ�ȴ�
					break;
				case Final.PLAYER_PLAY://��ҳ���������
					mp3Player.Play(musicPath);
					UpdatePage();
					state = Final.SEEKBAR_PROGRESS;
					
					break;
				case Final.PAUSE:
					mp3Player.Pause();
					
					break;
				case Final.UP:
					Up();
					break;
				case Final.DOWN:
					Down();
					
					break;
				case Final.ZAIXIAN_PLAY:
					mp3Player.zaixianplay(musicPath);
					state = Final.WAIT;// ���õ�ǰ��״̬Ϊ�ȴ�
					break;
				case Final.SEEKBAR_PROGRESS:
					
					SetSeekBar();


				default:
					break;
				}
				Thread.sleep(200);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void SetSeekBar(){
		
		Message msg=BoFangYeMian.handler.obtainMessage();
		int a=mp3Player.GetPlayerTime();
		msg.arg1=a/1000;
		msg.what=1;
		
		
		msg.sendToTarget();
		}
	public void Up() {
		if (--OneActivity.index < 0) {
			OneActivity.index = OneActivity.listData.size() - 1;
		}
		SetMusic();
		updateMusic();
	}
	
	public void updateMusic(){
		String lrcPath=OneActivity.listData.get(OneActivity.index).getFilename();
		OneActivity.sendLrc(lrcPath);
	}
	public static boolean Pause(){
		return mp3Player.Pause();
	}
	private void SetMusic() {
		// TODO Auto-generated method stub
		MP3Model hs =  OneActivity.listData.get(OneActivity.index);
		//�õ���  �о�����ѭ��ʱÿһ�ε�put  �������ܻ�ȡ�����׸�����·���� ��·���ı�ʾΪ/mnt/sdcard/XXX.mp3
		String path = hs.getMusicPath();
		Mp3Thread.musicPath = path;
		Mp3Thread.state = Final.PLAYER_PLAY;
	}


	public void Down() {
		if (++OneActivity.index == OneActivity.listData.size()) {
			OneActivity.index = 0;
		}
		SetMusic();
		updateMusic();
		
	}
	/**
	 * ֪ͨ����ҳ����������ˣ�����ҳ��
	 */
	private void UpdatePage(){
		Message msg = BoFangYeMian.handler.obtainMessage();
		msg.what = 0;
		msg.sendToTarget();
	}

}
