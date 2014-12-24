package com.idap.danalysis.entity;

import com.idp.pub.entity.annotation.MetaTable;


@MetaTable
public class SyncAuditor implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

 
	
	private String   userId; //USER_ID
	private String   usrName; //USR_NAME
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
 
	
 
 
}
