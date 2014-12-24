package com.idap.intextservice.repository.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.intextservice.repository.dao.KnowledgeBaseDao;
import com.idap.intextservice.repository.entity.KnowledgeBase;
import com.idap.intextservice.repository.service.IKnowledgeBaseService;
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

@Service("knowledgeBaseService")
@Transactional
public class KnowledgeBaseServiceImpl extends
		DefaultBaseService<KnowledgeBase, String> implements
		IKnowledgeBaseService {
	@Resource(name = "KnowledgeBaseDao")
	public void setBaseDao(IBaseDao<KnowledgeBase, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "KnowledgeBaseDao")
	public void setPagerDao(IPagerDao<KnowledgeBase> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	@Resource(name = "KnowledgeBaseDao")
	private KnowledgeBaseDao knowledgeBaseDao;

	public Pager<KnowledgeBase> query(Pager<KnowledgeBase> pager,
			Map<String, Object> params) {
		return this.findByPager(pager, params);

	}

	public KnowledgeBase save(KnowledgeBase vo) {
		return super.save(vo);

	}

	public KnowledgeBase updae(KnowledgeBase vo) {
		return super.update(vo);

	}

	public KnowledgeBase getById(String id) {
		return super.getById(id);

	}

	public Integer delete(Map<String, Object> params) {
		return super.delete(params);

	}

	@Override
	public List<KnowledgeBase> queryLabelCascade(String id) {
		return this.knowledgeBaseDao.queryLabelCascade(id);

	}

}
