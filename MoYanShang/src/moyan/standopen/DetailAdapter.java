package moyan.standopen;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.DataSetObserver;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailAdapter implements ListAdapter{
	private ArrayList<DetailEntity> coll;
	private Context ctx;
	TextView tvText=null;
	public DetailAdapter(Context context ,ArrayList<DetailEntity> coll) {
		ctx = context;
		this.coll = coll;
	}
	
	public boolean areAllItemsEnabled() {
		return false;
	}
	public boolean isEnabled(int arg0) {
		return false;
	}
	public int getCount() {
		return coll.size();
	}
	public Object getItem(int position) {
		return coll.get(position);
	}
	public long getItemId(int position) {
		return position;
	}
	public int getItemViewType(int position) {
		return position;
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		DetailEntity entity = coll.get(position);
		int itemLayout = entity.getLayoutID();
		
		final RelativeLayout layout = new RelativeLayout(ctx);
		LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vi.inflate(itemLayout, layout,true);
		
		final TextView tvName = (TextView) layout.findViewById(R.id.messagedetail_row_name);
		tvName.setText(entity.getName());
		@SuppressWarnings("unused")
		ImageView image=(ImageView)layout.findViewById(R.id.messagegedetail_rov_icon);
		final LinearLayout mylayout = (LinearLayout)layout.findViewById(R.id.chat_layout);
		tvText = (TextView) layout.findViewById(R.id.messagedetail_row_text);
		tvText.setText(entity.getText());
		mylayout.getBackground().setAlpha(200);
		mylayout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Items(position);
			}
		});
		return layout;
	}
	public int getViewTypeCount() {
		return coll.size();
	}
	public boolean hasStableIds() {
		return false;
	}
	public boolean isEmpty() {
		return false;
	}
	public void registerDataSetObserver(DataSetObserver observer) {
	}
	public void unregisterDataSetObserver(DataSetObserver observer) {
	}
	public void Items(final int position)
	{
		AlertDialog.Builder builder=new Builder(ctx);
		builder.setTitle("����ѡ��");
		builder.setItems( new String[] { "���Ƶ�������", "���������" ,"�ر�"}, new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(which==0)
				{
					try
					{
					ClipboardManager cmb = (ClipboardManager) ctx
							 .getSystemService(Context.CLIPBOARD_SERVICE);
							 cmb.setText("Ī����:"+coll.get(position).getText().toString());
							 Toast.makeText(ctx, "���Ƴɹ���", Toast.LENGTH_SHORT).show();
							 
					}
					catch(Exception e)
					{
						Toast.makeText(ctx, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
					}
				}
				else if(which==1)
				{
					   Intent intent=new Intent(Intent.ACTION_SEND);  
		               intent.setType("text/*");  
		               intent.putExtra(Intent.EXTRA_SUBJECT, "share");  
		               intent.putExtra(Intent.EXTRA_TEXT,"Ī����:"+coll.get(position).getText().toString());  
		               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		              ctx.startActivity(Intent.createChooser(intent, "�������ݵ�����"));
				}
			}
		});
		builder.create().show();
	}
    
}
