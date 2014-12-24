package com.idap.drad.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
   
import com.idap.drad.entity.PortExcp;
import com.idap.drad.service.IPortExcpService;
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
@Service("portExcpService") 
public class PortExcpServiceImpl   extends DefaultBaseService<PortExcp,String> implements
		IPortExcpService {

	@Resource(name = "PortExcpDao")  
	public void setBaseDao(IBaseDao<PortExcp, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "PortExcpDao")
	public void setPagerDao(IPagerDao<PortExcp> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	   
	
	public Pager<PortExcp> query(Pager<PortExcp>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}

	@Override
	public PortExcp save(PortExcp entity) {
		// TODO Auto-generated method stub
		return null;
	}
	  
}
