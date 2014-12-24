package com.idap.intextservice.result.subscriptions.entity;

import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.PrimaryKey.PK;

/**
 * ###############################
 * 
 * @创建日期：2014-6-25
 * @开发人员：yao
 * @功能描述：订阅内容明细实体类
 * @修改日志： #####################
 */

@MetaTable
public class Content extends SmartEntity {
	private static final long serialVersionUID = 2342901748503200854L;

	@PrimaryKey(createType = PK.useIDP)
	private String Id;// 序列号

	private String subscribeId;// 订阅代码

	private String anaThemeId;// 分析主题标识

	private String anaThemeRepId;// 分析主题报表标识

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
	}

	public String getAnaThemeId() {
		return anaThemeId;
	}

	public void setAnaThemeId(String anaThemeId) {
		this.anaThemeId = anaThemeId;
	}

	public String getAnaThemeRepId() {
		return anaThemeRepId;
	}

	public void setAnaThemeRepId(String anaThemeRepId) {
		this.anaThemeRepId = anaThemeRepId;
	}

}
