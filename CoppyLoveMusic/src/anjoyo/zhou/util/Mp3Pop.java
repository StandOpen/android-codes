package anjoyo.zhou.util;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;

public class Mp3Pop {
	public static String FIRST_COLUMN="first_column";
	
	public static PopupWindow popWin;
	public static Button btn;
	public static View view;
	LinearLayout linear;
	
	 public static void initPopWindow() {
			if (null == popWin) {// (popwin�Զ��岼���ļ�,popwin���,popwin�߶�)(ע������ָ��λ��������������������ֵ����ΪWRAP_CONTENT)
				popWin = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			}
			if (popWin.isShowing()) {// �����ǰ������ʾ���򽫱�����
				popWin.dismiss();
			}
		}
}
