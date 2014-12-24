package com.idap.danalysis.entity;

import com.idp.pub.entity.annotation.MetaTable;
 

@MetaTable
public class ParaImportantClient implements java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

	 
	private String   clientId; // CLIENT_ID
	private String   clientName; // CLIENT_NAME
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
  
 
 
  
}
