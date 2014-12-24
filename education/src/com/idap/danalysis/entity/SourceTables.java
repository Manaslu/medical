package com.idap.danalysis.entity;


 

import com.idp.pub.entity.annotation.MetaTable;


@MetaTable
public class SourceTables implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

 
	
	private String   sourceSystem; //流水source_system
	private String   tableName; //table_name
	private String   tableNameen; //table_nameen
	 
	
	
 
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableNameen() {
		return tableNameen;
	}
	public void setTableNameen(String tableNameen) {
		this.tableNameen = tableNameen;
	}
	 
	
}
