package com.idap.danalysis.entity;

import com.idp.pub.entity.annotation.MetaTable;
 

@MetaTable
public class ParaIndustry implements java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

	 
	private String   industTypeCd; // INDUST_TYPE_CD
	private String   idstryTypeName; // IDSTRY_TYPE_NAME
	
	
	public String getIndustTypeCd() {
		return industTypeCd;
	}
	public void setIndustTypeCd(String industTypeCd) {
		this.industTypeCd = industTypeCd;
	}
	public String getIdstryTypeName() {
		return idstryTypeName;
	}
	public void setIdstryTypeName(String idstryTypeName) {
		this.idstryTypeName = idstryTypeName;
	}
	
 
 
  
}
