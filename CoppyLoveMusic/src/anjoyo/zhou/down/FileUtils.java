package anjoyo.zhou.down;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class FileUtils {
	private String SDPATH;

	public FileUtils() {
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}

	/**
	 * ��õ�ǰϵͳ��SDPATH·���ַ�
	 * */
	public String GetSDPATH() {
		return SDPATH;
	}

	/**
	 * ��SD���ϴ����ļ�
	 * */
	public File CreateSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		boolean isCreate = file.createNewFile();
		return file;
	}

	/**
	 * ��SD���ϴ����ļ���
	 * */
	public File CreateSDDir(String dirName) {
		File file = new File(SDPATH + dirName);
		boolean isCreateDir = file.mkdir();
		return file;
	}

	/**
	 * �ж��ļ��Ƿ����
	 * */
	public boolean IsFileExists(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	
}
