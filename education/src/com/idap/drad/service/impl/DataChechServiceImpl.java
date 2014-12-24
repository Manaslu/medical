package com.idap.drad.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
  
import com.idap.drad.entity.DataChech;
import com.idap.drad.service.IDataChechService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;


/**
* @###################################################
* @创建日期：2014-4-10 16:09:37
* @开发人员：bai
* @功能描述：
* @修改日志：
* @###################################################
*/ 
@Transactional 
@Service("dataChechService")  
public class DataChechServiceImpl   extends DefaultBaseService<DataChech,String> implements
		IDataChechService {

	@Resource(name = "DataChechDao")  
	public void setBaseDao(IBaseDao<DataChech, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "DataChechDao")
	public void setPagerDao(IPagerDao<DataChech> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	  
	/*public Pager<T> findByPager(Pager<T> pager, Map<String, Object> params) {
		return this.pagerDao.findByPager(pager, params);
	}*/
	
	public Pager<DataChech> query(Pager<DataChech>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}

	@Override
	public DataChech save(DataChech entity) {
		// TODO Auto-generated method stub
		return null;
	}
	  
}
