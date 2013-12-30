package game06.standopen;

import java.util.Random;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
/**
 * @author Himi
 * @�Զ���Բ����
 */
public class MyArc {
	private int arc_x, arc_y, arc_r;//Բ�ε�X,Y����Ͱ뾶
	private float speed_x = 1.2f, speed_y = 1.2f;//С���x��y���ٶ�
	private float vertical_speed;//���ٶ�
	private float horizontal_speed;//ˮƽ���ٶ�,����Լ�������Ӱ�
	private final float ACC = 0.135f;//Ϊ��ģ����ٶȵ�ƫ��ֵ
	private final float RECESSION = 0.2f;//ÿ�ε����˥��ϵ�� 
	private boolean isDown = true;//�Ƿ�������  ״̬
	private Random ran;//�漴����
	/**
	 * @����Բ�εĹ��캯��
	 * @param x Բ��X����
	 * @param y Բ��Y����
	 * @param r Բ�ΰ뾶
	 */
	public MyArc(int x, int y, int r) {
		ran = new Random();
		this.arc_x = x;
		this.arc_y = y;
		this.arc_r = r;
	}
	public void drawMyArc(Canvas canvas, Paint paint) {//ÿ��Բ�ζ�Ӧ��ӵ��һ�׻滭����
		paint.setColor(getRandomColor());//���ϵĻ�ȡ�漴��ɫ����Բ�ν������(ʵ��Բ����˸Ч��)
		canvas.drawArc(new RectF(arc_x + speed_x, arc_y + speed_y, arc_x + 2 *
				arc_r + speed_x, arc_y + 2 * arc_r + speed_y), 0, 360, true, paint);
	}
	/**
	 * @return
	 * @����һ���漴��ɫ
	 */
	public int getRandomColor() {
		int ran_color = ran.nextInt(8);
		int temp_color = 0;
		switch (ran_color) {
		case 0:
			temp_color = Color.WHITE;
			break;
		case 1:
			temp_color = Color.BLUE;
			break;
		case 2:
			temp_color = Color.CYAN;
			break;
		case 3:
			temp_color = Color.DKGRAY;
			break;
		case 4:
			temp_color = Color.RED;
			break;
		case 6:
			temp_color = Color.GREEN;
		case 7:
			temp_color = Color.GRAY;
		case 8:
			temp_color = Color.YELLOW;
			break;
		}
		return temp_color;
	}
	/**
	 * Բ�ε��߼�
	 */
	public void logic() {//ÿ��Բ�ζ�Ӧ��ӵ��һ���߼�
		if (isDown) {//Բ�������߼�
/*--��ע1-*/speed_y += vertical_speed;//Բ�ε�Y���ٶȼ��ϼ��ٶ�
			int count = (int) vertical_speed++;
			//����������һ���������µ�ǰ�ٶ�ƫ����
			//��������for (int i = 0; i < vertical_speed++; i++) {}�����;���ѭ���� - -
			for (int i = 0; i < count; i++) {//��ע1
/*--��ע2-*/	vertical_speed += ACC;
			}
		} else {//Բ�η����߼�
			speed_y -= vertical_speed;
			int count = (int) vertical_speed--;
			for (int i = 0; i < count; i++) {
				vertical_speed -= ACC;
			}
		}
		if (isCollision()) {
			isDown = !isDown;//��������ײ˵��Բ�εķ���Ҫ�ı�һ���ˣ�
			vertical_speed -= vertical_speed * RECESSION;//ÿ����ײ����˥�������ļ��ٶ�
		}
	}
	/**
	 * Բ������Ļ�ײ�����ײ
	 * @return
	 * @����true ������ײ
	 */
	public boolean isCollision() {
		return arc_y + 2 * arc_r + speed_y >= MySurfaceViee.screenH;
	}
}

