package com.idap.danalysis.entity;

import com.idp.pub.entity.annotation.MetaTable;
 

@MetaTable
public class ParaDefinitionBigClient implements java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

	 
	private String   definitionId; // DEFINITION_ID
	private String   definitionName; // DEFINITION_NAME
	
	public String getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	
 
 
 
  
}
