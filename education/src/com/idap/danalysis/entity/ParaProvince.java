package com.idap.danalysis.entity;

import com.idp.pub.entity.annotation.MetaTable;
 

@MetaTable
public class ParaProvince implements java.io.Serializable {
		private static final long serialVersionUID = 4736511926578194639L;

	 
	private String   provinceId; // PROVINCE_ID
	private String   provinceName; // PROVINCE_NAME
	
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
 
 
}
