package com.idap.danalysis.entity;

 

import com.idp.pub.entity.annotation.MetaTable;


@MetaTable
public class TableColumns implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

 //用于地图导航
	
	private String   name;  
	private int   value;  
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	 
	 
  
 
}
