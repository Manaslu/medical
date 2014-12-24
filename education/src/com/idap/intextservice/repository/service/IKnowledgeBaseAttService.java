package com.idap.intextservice.repository.service;

import java.util.List;
import java.util.Map;

import com.idap.intextservice.repository.entity.KnowledgeBaseAtt;

/**
 * @###################################################
 * @创建日期：2014-4-10 16:09:37
 * @开发人员：bai
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

public interface IKnowledgeBaseAttService {

	public KnowledgeBaseAtt save(KnowledgeBaseAtt entity);
	public List<KnowledgeBaseAtt> query(Map<String, Object> params);   
		
}
