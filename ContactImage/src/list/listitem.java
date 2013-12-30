package list;

import android.graphics.Bitmap;

public class listitem {

	public String getContactid() {
		return contactid;
	}
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getLayoutID() {
		return layoutID;
	}
	public void setLayoutID(int layoutID) {
		this.layoutID = layoutID;
	}

	public String contactid;
	public Bitmap imageid;
	public String name;
	public String number;
	private int layoutID;
	public Bitmap getimageid() {
		return imageid;
	}
	public void setImageid(Bitmap imageid) {
		this.imageid = imageid;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	
	public listitem(Bitmap imageid1,String contactid1,String name1,String number1,int layout)
	{
		this.imageid=imageid1;
		this.contactid=contactid1;
		this.name=name1;
		this.number=number1;
		this.layoutID=layout;
	}
	
	
}
