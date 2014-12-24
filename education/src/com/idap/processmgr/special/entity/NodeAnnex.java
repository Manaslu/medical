package com.idap.processmgr.special.entity;

import java.io.Serializable;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;
@MetaTable
public class NodeAnnex extends SmartEntity implements Serializable{
	private static final long serialVersionUID = 4065229118138308396L;
	
	@PrimaryKey(createType = PK.useIDP)
	private String fileId;//附件id
	private String id;//实例id
	private String fruitCode;//成果编号
	private String fileDir;//文件路径
	private String fileName;//文件名称
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFruitCode() {
		return fruitCode;
	}
	public void setFruitCode(String fruitCode) {
		this.fruitCode = fruitCode;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
