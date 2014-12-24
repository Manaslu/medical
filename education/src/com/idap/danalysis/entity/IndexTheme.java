package com.idap.danalysis.entity;

 
	import com.idp.pub.entity.SmartEntity;
	import com.idp.pub.entity.annotation.MetaTable;
	import com.idp.pub.entity.annotation.PrimaryKey;
	import com.idp.pub.entity.annotation.PrimaryKey.PK;

	@MetaTable
	public class IndexTheme extends SmartEntity implements
			java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

	@PrimaryKey(createType = PK.useIDP)
	private String   indexThemeId; //指标主题代码
	private String   indexThemeName; //主题名称
	private String   tableName; //表名
	private String   colName; //字段
	
	
	public String getIndexThemeId() {
		return indexThemeId;
	}
	public void setIndexThemeId(String indexThemeId) {
		this.indexThemeId = indexThemeId;
	}
	public String getIndexThemeName() {
		return indexThemeName;
	}
	public void setIndexThemeName(String indexThemeName) {
		this.indexThemeName = indexThemeName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}

 
	 
	
 
 


 
}
