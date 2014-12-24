package com.idap.intextservice.repository.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.intextservice.repository.entity.KnowledgeBaseAtt;
import com.idap.intextservice.repository.service.IKnowledgeLogService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-4-10 16:09:36
 * @开发人员：bai
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
 
@Service("knowledgeLogService")
@Transactional
public class KnowledgeLogServiceImpl extends
		DefaultBaseService<KnowledgeBaseAtt, String> implements
		IKnowledgeLogService {
	@Resource(name = "KnowledgeLogDao")
	public void setBaseDao(IBaseDao<KnowledgeBaseAtt, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "KnowledgeLogDao")
	public void setPagerDao(IPagerDao<KnowledgeBaseAtt> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	public Pager<KnowledgeBaseAtt> query(Pager<KnowledgeBaseAtt> pager,
			Map<String, Object> params) {
		return this.findByPager(pager, params);

	}

}
