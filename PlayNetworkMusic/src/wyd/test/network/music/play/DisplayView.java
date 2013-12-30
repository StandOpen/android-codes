package wyd.test.network.music.play;

import static wyd.test.network.music.play.Constant.*;

import java.io.IOException;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class DisplayView extends SurfaceView implements SurfaceHolder.Callback
{

	private static final String TAG = "DisplayView";
	/**
	 * 主控界面
	 */
	PlayNetworkMusicActivity activity;

	/**
	 * 画笔
	 */
	private Paint paint;

	/**
	 * 画布
	 */
	private Canvas canvas;

	/**
	 * 音乐播放标记 false表示音乐没有播放/true反之
	 */
	private boolean playMusicFlag;

	/**
	 * 音乐准备完毕标记
	 */
	private boolean preparedFlag;

	/**
	 * 音乐播放完毕标记
	 */
	private boolean completionFlag;

	/**
	 * 缓冲比例标记
	 */
	private boolean percentFlag;

	/**
	 * 缓冲比例值
	 */
	private String percentValue;

	/**
	 * 构造函数初始化操作
	 * 
	 * @param activity
	 *            主控类
	 */
	public DisplayView(PlayNetworkMusicActivity activity)
	{
		super(activity);
		this.activity = activity;

		// 设置在当前View中执行surfaceCreated、surfaceChanged、surfaceDestroyed回调操作
		this.getHolder().addCallback(this);

	}


	public void surfaceCreated(SurfaceHolder holder)
	{
		drawView(holder);
	}

	/**
	 * 绘制界面方法
	 * 
	 * @param holder
	 *            回调接口对象
	 */
	private void drawView(SurfaceHolder holder)
	{
		// 获取画布
		canvas = holder.lockCanvas();
		try
		{
			synchronized (holder)
			{
				// 绘制屏幕
				onDraw(canvas);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			// 解锁画布
			if (canvas != null)
				holder.unlockCanvasAndPost(canvas);
		}
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{

	}


	public void surfaceDestroyed(SurfaceHolder holder)
	{

	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// 获取当前x,y坐标
		float currentX = event.getX();
		float currentY = event.getY();
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 获取开始和停止按钮的范围
			float left = 170 * xZoom;
			float right = WIDTH / 2 + 70 * xZoom;
			float top = 50 * yZoom;
			float bottom = HEIGHT / 8 + 20 * yZoom;
			if (currentX > left && currentX < right && currentY > top
					&& currentY < bottom)
			{
				Log.d(TAG, "目前在按钮范围内");
				SurfaceHolder holder = getHolder();

				if (!playMusicFlag)
				{
					Log.d(TAG, "音乐正在播放");
					// 0表示播放音乐
					activity.musicHandler.sendEmptyMessage(0);
					playMusicFlag = true;
					// 重绘
					drawView(holder);
				} else
				{
					Log.d(TAG, "音乐停止了");
					// 1表示停止音乐
					activity.musicHandler.sendEmptyMessage(1);
					// 重绘
					drawView(holder);
					playMusicFlag = false;
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		default:
			break;
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 绘图方法(注意:这个方法需要手动调用，系统不会自动调用)
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		paint = new Paint();

		// 设置抗锯齿
		paint.setAntiAlias(true);

		// 设置透明度(不透明)
		paint.setAlpha(80);

		// 设置画笔颜色(绿色)
		paint.setColor(Color.GREEN);

		// 绘制矩形(做为背景)
		canvas.drawRect(START_X, START_Y, WIDTH, HEIGHT, paint);

		// 重置画笔
		paint.reset();

		paint.setAlpha(150);

		paint.setColor(Color.WHITE);

		// 设置矩形显示位置
		RectF oval1 = new RectF(170 * xZoom, 50 * yZoom,
				WIDTH / 2 + 70 * xZoom, HEIGHT / 8 + 20 * yZoom);

		// 绘制带圆角的矩形
		canvas.drawRoundRect(oval1, 15, 15, paint);

		RectF oval2 = new RectF(150 * xZoom, 200 * yZoom, WIDTH / 2 + 70
				* xZoom, HEIGHT / 8 + 20 * yZoom);

		canvas.drawRoundRect(oval2, 15, 15, paint);

		// 设置Start颜色
		paint.setColor(Color.BLACK);

		// 自定义字体样式
		Typeface typeface = Typeface.createFromAsset(activity.getAssets(),
				"JandaSafeandSound.ttf");

		// 设置字体
		paint.setTypeface(typeface);

		// 设置字体大小
		paint.setTextSize(40 * xZoom);

		// 绘制文字 开始/停止
		if (playMusicFlag)
		{
			String stop = activity.getResources().getString(R.string.stop);
			canvas.drawText(stop, 190 * xZoom, 100 * yZoom, paint);

		} else
		{
			String start = activity.getResources().getString(R.string.start);
			canvas.drawText(start, 190 * xZoom, 100 * yZoom, paint);
		}

		// 准备完毕标题
		String prepared = activity.getResources().getString(R.string.Prepared);
		canvas.drawText(prepared, 20 * xZoom, 200 * yZoom, paint);

		// 贮备完毕值
		if (preparedFlag)
		{
			String preparedValue = activity.getResources().getString(
					R.string.prepared_value);
			canvas.drawText(preparedValue, 20 * xZoom + 225 * xZoom,
					200 * yZoom, paint);
			// preparedFlag = false;
		}

		// 缓冲标题
		String percent = activity.getResources().getString(R.string.percent);
		canvas.drawText(percent, 20 * xZoom, 300 * yZoom, paint);

		// 缓冲值
		if (percentFlag)
		{
			canvas.drawText(percentValue, 20 * xZoom + 190 * xZoom,
					300 * yZoom, paint);
			// completionFlag = false;
		}

		// 音乐播放完毕标题
		String completion = activity.getResources().getString(
				R.string.completion);
		canvas.drawText(completion, 20 * xZoom, 400 * yZoom, paint);

		// 音乐播放完毕值
		if (completionFlag)
		{
			String completionValue = activity.getResources().getString(
					R.string.completion_value);
			canvas.drawText(completionValue, 20 * xZoom + 240 * xZoom,
					400 * yZoom, paint);
			// completionFlag = false;
		}

	}

	/**
	 * 绘制音频准备完毕
	 */
	public void drawPrepared()
	{
		preparedFlag = true;
		drawView(getHolder());
	}

	/**
	 * 音乐播放完毕
	 */
	public void completionDraw()
	{
		completionFlag = true;
		drawView(getHolder());
	}

	/**
	 * 绘制缓冲比例
	 * 
	 * @param percentValue
	 *            缓冲比例值
	 */
	public void percentDraw(String percentValue)
	{
		percentFlag = true;
		this.percentValue = percentValue;
		drawView(getHolder());
	}

}
