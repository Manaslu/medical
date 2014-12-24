package com.idap.intextservice.repository.entity;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * 
 * 
 * @###################################################
 * @功能描述：关联知只和标签库           
 * @创建日期：2014-6-18 15:34:52
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */
   
@MetaTable
public class KnowledgebaseLabel  extends SmartEntity  implements java.io.Serializable {

	                 

	private static final long serialVersionUID = 66711926578100139L;

	@PrimaryKey(createType = PK.useIDP)
	private String id; // 附件 32位ID

	private String labelId; // label_id
	private String knowledgeId; // 知识库ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

}