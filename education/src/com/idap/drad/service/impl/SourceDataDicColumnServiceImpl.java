package com.idap.drad.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.idap.drad.entity.SourceDataDicColumn;
import com.idap.drad.service.ISourceDataDicColumnService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
* ###################################################
* @创建日期：2014-4-4 16:39:06
* @开发人员：bai
* @功能描述：
* @修改日志：
* ###################################################
*/ 

@Transactional  
@Service("sourceDataDicColumnService") 
public class SourceDataDicColumnServiceImpl   extends DefaultBaseService<SourceDataDicColumn,String> implements
		ISourceDataDicColumnService {

	@Resource(name = "SourceDataDicColumnDao")  
	public void setBaseDao(IBaseDao<SourceDataDicColumn, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "SourceDataDicColumnDao")
	public void setPagerDao(IPagerDao<SourceDataDicColumn> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	             
	
	public Pager<SourceDataDicColumn> query(Pager<SourceDataDicColumn>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}
     
	  
}


