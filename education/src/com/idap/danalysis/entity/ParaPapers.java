package com.idap.danalysis.entity;

import com.idp.pub.entity.annotation.MetaTable;
 

@MetaTable
public class ParaPapers implements java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

	 
	private String   paperCode; // PAPER_CODE
	private String   paperName; // PAPER_NAME
	private String   cpfl; // CPFL
 
	 
	
	
	public String getPaperCode() {
		return paperCode;
	}
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
 
	public String getCpfl() {
		return cpfl;
	}
	public void setCpfl(String cpfl) {
		this.cpfl = cpfl;
	}
 
	
	
  
}
