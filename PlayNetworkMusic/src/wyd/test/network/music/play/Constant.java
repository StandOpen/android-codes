package wyd.test.network.music.play;

import android.graphics.Matrix;
public class Constant
{
	/**
	 * 屏幕起始x坐标
	 */
	public static float START_X = 0;

	/**
	 * 屏幕起始y坐标
	 */
	public static float START_Y = 0;

	/**
	 * 当前开发使用的屏幕分辨率宽
	 */
	public static int CURRENT_WIDTH = 480;

	/**
	 * 当前开发使用的屏幕分辨率高
	 */
	public static int CURRENT_HEIGHT = 800;

	/**
	 * 屏幕的分辨率
	 */
	public static float WIDTH;

	/**
	 * 屏幕的分辨率
	 */
	public static float HEIGHT;

	/**
	 * x缩放比例
	 */
	public static float xZoom;

	/**
	 * y缩放比例
	 */
	public static float yZoom;

	/**
	 * 设置缩放
	 * 
	 * @param ratio
	 *            缩放比率
	 */
	public static void scale(float ratio)
	{
		Matrix matrix = new Matrix();
		matrix.setScale(ratio, ratio);
	}
	
	
	/**
	 * 初始化屏幕分辨率
	 */
}
