package com.idap.danalysis.entity;

 
 
import com.idp.pub.entity.annotation.MetaTable;
 

@MetaTable
public class ExportTables implements java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

 
	private String   name; //ana_theme_id
	private String   value; // report_id
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    
	
	
	
	
 
 
 
	
	
  
 
}
