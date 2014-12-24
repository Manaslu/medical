package com.idap.drad.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.idap.drad.entity.EsbSend;
import com.idap.drad.service.IEsbSendService;
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
@Transactional                
@Service("esbSendService")  
public class EsbSendServiceImpl    extends DefaultBaseService<EsbSend,String> implements
		IEsbSendService {

	@Resource(name = "EsbSendDao")  
	public void setBaseDao(IBaseDao<EsbSend, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "EsbSendDao")
	public void setPagerDao(IPagerDao<EsbSend> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	  
	            
	
	public Pager<EsbSend> query(Pager<EsbSend>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}

	    
	  
}


 