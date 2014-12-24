package com.idap.danalysis.entity;

 
	import java.util.Date;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

	@MetaTable
	public class IndexMaintain extends SmartEntity implements
			java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

	@PrimaryKey(createType = PK.useIDP)
	private String   id; //ID
	private String   userId; //USER_ID
	private Date     opDate = new Date(); //OP_DATE
	private String   opType; //OP_TYPE
	private String   indexTheme_id; //INDEX_THEME_ID
	private String   indexId; //INDEX_ID
	private String   indexVersion; //INDEX_VERSION
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
 
 
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getIndexTheme_id() {
		return indexTheme_id;
	}
	public void setIndexTheme_id(String indexTheme_id) {
		this.indexTheme_id = indexTheme_id;
	}
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public String getIndexVersion() {
		return indexVersion;
	}
	public void setIndexVersion(String indexVersion) {
		this.indexVersion = indexVersion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
	
 
	
	
 

 
	 
	
 
 


 
}
