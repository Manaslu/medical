package com.idap.danalysis.entity;



import com.idp.pub.entity.annotation.MetaTable;


@MetaTable
public class AnalysisType implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

 
	
	private String   anaMethodId; //ANA_METHOD_ID  
	private String   anaMethodName; //ANA_METHOD_NAME  
	
	
	public String getAnaMethodId() {
		return anaMethodId;
	}
	public void setAnaMethodId(String anaMethodId) {
		this.anaMethodId = anaMethodId;
	}
	public String getAnaMethodName() {
		return anaMethodName;
	}
	public void setAnaMethodName(String anaMethodName) {
		this.anaMethodName = anaMethodName;
	}
	
 
}
