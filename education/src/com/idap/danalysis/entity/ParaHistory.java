package com.idap.danalysis.entity;


import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;


@MetaTable
public class ParaHistory extends SmartEntity implements
		java.io.Serializable {
	private static final long serialVersionUID = 4736511926578194639L;

		@PrimaryKey(createType = PK.useIDP)
		private String  id;//ID流水号
		
		private String  anaThemeId;//ANA_THEME_ID
		private String  indexId;//INDEX_ID
		private String  indexFlag;//INDEX_FLAG
		private String  indexVal;//INDEX_VAL
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAnaThemeId() {
			return anaThemeId;
		}
		public void setAnaThemeId(String anaThemeId) {
			this.anaThemeId = anaThemeId;
		}
		public String getIndexId() {
			return indexId;
		}
		public void setIndexId(String indexId) {
			this.indexId = indexId;
		}
		public String getIndexFlag() {
			return indexFlag;
		}
		public void setIndexFlag(String indexFlag) {
			this.indexFlag = indexFlag;
		}
		public String getIndexVal() {
			return indexVal;
		}
		public void setIndexVal(String indexVal) {
			this.indexVal = indexVal;
		}
		
		 

}
