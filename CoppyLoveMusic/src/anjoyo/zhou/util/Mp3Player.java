package anjoyo.zhou.util;


import android.media.MediaPlayer;
import anjoyo.zhou.thread.Mp3Thread;

public class Mp3Player {
	static MediaPlayer media=getMedia();

	public static MediaPlayer getMedia() {
		if (media==null) {
		//	System.out.println("321321");
			media=new MediaPlayer();
		//	System.out.println("123123");
		}
		return media;
	}
	/**
	 * ���ز��ŷ���
	 * @param path
	 */
	public void Play(String path){
		
		try {
		//	System.out.println("aaaaa");
			media.reset();
			media.setDataSource(path);
			media.prepare();
			media.start();
			/**
			 * �Զ�������һ��
			 */
			media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					Mp3Thread.state=Final.DOWN;//���߳����state���ò���Ϊ��һ��
					
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	/**���߲���
	 * 
	 * @param path
	 */
	public void zaixianplay(String path){
		
		try {
			media.reset();
			media.setDataSource(path);
			media.prepare();
			media.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	/**
	 * ��ͣ����
	 * @return
	 */
	public boolean Pause(){
		if (media.isPlaying()) {
			media.pause();
			return true;
		}else {
			media.start();
			return false;
		}
	}
	/**
	 * �õ���ǰ�Ĳ���ʱ��
	 */
	public int GetPlayerTime(){
		return media.getCurrentPosition();//�õ���ǰ�����Ĳ���ʱ��
	}
	public void SeektoMusic(int time){
		media.seekTo(time);
		
		media.start();
	}
	

}
