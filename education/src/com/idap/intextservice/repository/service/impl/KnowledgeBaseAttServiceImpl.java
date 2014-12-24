package com.idap.intextservice.repository.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.intextservice.repository.entity.KnowledgeBaseAtt;
import com.idap.intextservice.repository.service.IKnowledgeBaseAttService;
import com.idp.pub.constants.Constants;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-4-10 16:09:36
 * @开发人员：bai
 * @功能描述：知识库附件实现类
 * @修改日志：
 * @###################################################
 */


@Service("knowledgeBaseAttService")
@Transactional
public class KnowledgeBaseAttServiceImpl extends
		DefaultBaseService<KnowledgeBaseAtt, String> implements
		IKnowledgeBaseAttService {
	@Resource(name = "KnowledgeBaseAttDao")
	public void setBaseDao(IBaseDao<KnowledgeBaseAtt, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "KnowledgeBaseAttDao")
	public void setPagerDao(IPagerDao<KnowledgeBaseAtt> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	public List<KnowledgeBaseAtt> query(Pager<KnowledgeBaseAtt> pager,
			Map<String, Object> params) {
		return super.findList(Constants.MAP());


	}

	public KnowledgeBaseAtt save(KnowledgeBaseAtt entity) {
		return super.save(entity); 
	}   
	
	public  List<KnowledgeBaseAtt> query( ) {            
		return super.findList(Constants.MAP()); 
	}
	
	public List<KnowledgeBaseAtt> query(	Map<String, Object> params) {
		return super.findList( params); 
	}
	 
}
