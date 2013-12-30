package draw.standopen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

public class DrawView extends View {  
	  
    public DrawView(Context context) {  
        super(context);  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
        /* 
         * ���� ˵�� drawRect ���ƾ��� drawCircle ����Բ�� drawOval ������Բ drawPath ������������ 
         * drawLine ����ֱ�� drawPoin ���Ƶ� 
         */  
        // ��������  
        Paint p = new Paint();  
        p.setColor(Color.RED);// ���ú�ɫ  
  
        canvas.drawText("��Բ��", 10, 20, p);// ���ı�  
        canvas.drawCircle(60, 20, 10, p);// СԲ  
        p.setAntiAlias(true);// ���û��ʵľ��Ч���� true��ȥ�������һ��Ч����������  
        canvas.drawCircle(120, 20, 20, p);// ��Բ  
  
        canvas.drawText("���߼����ߣ�", 10, 60, p);  
        p.setColor(Color.GREEN);// ������ɫ  
        canvas.drawLine(60, 40, 100, 40, p);// ����  
        canvas.drawLine(110, 40, 190, 80, p);// б��  
        //��Ц������  
        p.setStyle(Paint.Style.STROKE);//���ÿ���  
        RectF oval1=new RectF(150,20,180,40);  
        canvas.drawArc(oval1, 180, 180, false, p);//С����  
        oval1.set(190, 20, 220, 40);  
        canvas.drawArc(oval1, 180, 180, false, p);//С����  
        oval1.set(160, 30, 210, 60);  
        canvas.drawArc(oval1, 0, 180, false, p);//С����  
  
        canvas.drawText("�����Σ�", 10, 80, p);  
        p.setColor(Color.GRAY);// ���û�ɫ  
        p.setStyle(Paint.Style.FILL);//��������  
        canvas.drawRect(60, 60, 80, 80, p);// ������  
        canvas.drawRect(60, 90, 160, 100, p);// ������  
  
        canvas.drawText("�����κ���Բ:", 10, 120, p);  
        /* ���ý���ɫ ��������ε���ɫ�Ǹı�� */  
        Shader mShader = new LinearGradient(0, 0, 100, 100,  
                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,  
                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // һ������,�����һ�������ݶ�����һ���ߡ�  
        p.setShader(mShader);  
        // p.setColor(Color.BLUE);  
        RectF oval2 = new RectF(60, 100, 200, 240);// ���ø��µĳ����Σ�ɨ�����  
        canvas.drawArc(oval2, 200, 130, true, p);  
        // ��������һ��������RectF�������ǵڶ��������ǽǶȵĿ�ʼ�������������Ƕ��ٶȣ����ĸ����������ʱ�����Σ��Ǽٵ�ʱ�򻭻���  
        //����Բ����oval��һ��  
        oval2.set(210,100,250,130);  
        canvas.drawOval(oval2, p);  
  
        canvas.drawText("�������Σ�", 10, 200, p);  
        // �������������,����Ի�����������  
        Path path = new Path();  
        path.moveTo(80, 200);// �˵�Ϊ����ε����  
        path.lineTo(120, 250);  
        path.lineTo(80, 250);  
        path.close(); // ʹ��Щ�㹹�ɷ�յĶ����  
        canvas.drawPath(path, p);  
  
        // ����Ի��ƺܶ��������Σ��������滭������  
        p.reset();//����  
        p.setColor(Color.LTGRAY);  
        p.setStyle(Paint.Style.STROKE);//���ÿ���  
        Path path1=new Path();  
        path1.moveTo(180, 200);  
        path1.lineTo(200, 200);  
        path1.lineTo(210, 210);  
        path1.lineTo(200, 220);  
        path1.lineTo(180, 220);  
        path1.lineTo(170, 210);  
        path1.close();//���  
        canvas.drawPath(path1, p);  
        /* 
         * Path���װ����(����������ͼ�ε�·�� 
         * ��ֱ�߶�*����������,�����η����ߣ�Ҳ�ɻ����ͻ���drawPath(·��������),Ҫô�����Ļ��� 
         * (��������ķ��),���߿������ڼ��ϻ򻭻����ı���·���� 
         */  
          
        //��Բ�Ǿ���  
        p.setStyle(Paint.Style.FILL);//����  
        p.setColor(Color.LTGRAY);  
        p.setAntiAlias(true);// ���û��ʵľ��Ч��  
        canvas.drawText("��Բ�Ǿ���:", 10, 260, p);  
        RectF oval3 = new RectF(80, 260, 200, 300);// ���ø��µĳ�����  
        canvas.drawRoundRect(oval3, 20, 15, p);//�ڶ���������x�뾶��������������y�뾶  
          
        //������������  
        canvas.drawText("������������:", 10, 310, p);  
        p.reset();  
        p.setStyle(Paint.Style.STROKE);  
        p.setColor(Color.GREEN);  
        Path path2=new Path();  
        path2.moveTo(100, 320);//����Path�����  
        path2.quadTo(150, 310, 170, 400); //���ñ��������ߵĿ��Ƶ�������յ�����  
        canvas.drawPath(path2, p);//��������������  
          
        //����  
        p.setStyle(Paint.Style.FILL);  
        canvas.drawText("���㣺", 10, 390, p);  
        canvas.drawPoint(60, 390, p);//��һ����  
        canvas.drawPoints(new float[]{60,400,65,400,70,400}, p);//�������  
          
        //��ͼƬ��������ͼ  
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
        canvas.drawBitmap(bitmap, 250,360, p);  
    }  
}  
