package com.idap.danalysis.entity;


 

import com.idp.pub.entity.annotation.MetaTable;


@MetaTable
public class ParaYearList implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

 
	
	private String   paraYear; //PARA_YEAR



	public String getParaYear() {
		return paraYear;
	}



	public void setParaYear(String paraYear) {
		this.paraYear = paraYear;
	}
 
}
