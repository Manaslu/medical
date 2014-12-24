package com.idap.intextservice.repository.service;

import java.util.List;

import com.idap.intextservice.repository.entity.KnowledgeBase;


/**
* @###################################################
* @功能描述：
* @创建日期：2014-4-24 11:12:35
* @开发人员：BAI
* @修改日志：
* @###################################################
*/
public interface IKnowledgeBaseService   {

	public KnowledgeBase getById(String id);

	// 查询关联标签
	public List<KnowledgeBase> queryLabelCascade(String id) ;

}


 