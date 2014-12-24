package com.idap.danalysis.entity;


 

import com.idp.pub.entity.annotation.MetaTable;


@MetaTable
public class SourceIndexs implements java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

 
	
	private String   indexThemeId; //index_theme_id
	private String   indexId; //index_id
	private String   indexName; //index_name
	private String   sourceColumn; //source_column
	
	
	public String getIndexThemeId() {
		return indexThemeId;
	}
	public void setIndexThemeId(String indexThemeId) {
		this.indexThemeId = indexThemeId;
	}
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getSourceColumn() {
		return sourceColumn;
	}
	public void setSourceColumn(String sourceColumn) {
		this.sourceColumn = sourceColumn;
	}
	
	
 
 
}
