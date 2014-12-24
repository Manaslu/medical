package com.idap.intextservice.repository.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.intextservice.repository.entity.KnowledgebaseLabel; 
import com.idap.intextservice.repository.service.IKnowledgebaseLabelService;
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

@Service("knowledgebaseLabelService")
@Transactional
public class KnowledgebaseLabelServiceImpl extends
		DefaultBaseService<KnowledgebaseLabel, String> implements
		IKnowledgebaseLabelService { 
	@Resource(name = "KnowledgebaseLabelDao")
	public void setBaseDao(IBaseDao<KnowledgebaseLabel, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "KnowledgebaseLabelDao")
	public void setPagerDao(IPagerDao<KnowledgebaseLabel> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	public Pager<KnowledgebaseLabel> query(Pager<KnowledgebaseLabel> pager,
			Map<String, Object> params) {
		return this.findByPager(pager, params);

	}

	public KnowledgebaseLabel save(KnowledgebaseLabel vo) {
		return super.save(vo);

	}

	public KnowledgebaseLabel updae(KnowledgebaseLabel vo) {
		return super.update(vo);

	}

	public Integer delete(Map<String, Object> params) {
		return super.delete(params);

	}

 
 
}
