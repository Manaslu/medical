package com.idap.clinic.entity;
 

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class FullCalendar implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	 
	private String     id;     //ID 
	private String     title;  //title TITLE    
	private String     start;  //start START
	private String     end;    //end END
 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
 
 
}
