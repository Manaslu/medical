package com.idap.drad.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.idap.drad.entity.SourceDataDic;
import com.idap.drad.service.ISourceDataDicService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
  
/**
 * ###################################################
 * 
 * @创建日期：2014-4-9 16:38:42
 * @开发人员：bai
 * @功能描述：
 * @修改日志： ###################################################
 */
@Transactional
@Service("sourceDataDicService")
public class SourceDataDicServiceImpl extends
		DefaultBaseService<SourceDataDic, String> implements
		ISourceDataDicService {

	@Resource(name = "SourceDataDicDao")
	public void setBaseDao(IBaseDao<SourceDataDic, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "SourceDataDicDao")
	public void setPagerDao(IPagerDao<SourceDataDic> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	public Pager<SourceDataDic> query(Pager<SourceDataDic> pager,
			Map<String, Object> params) {
		return this.findByPager(pager, params);

	}
 
 
}
