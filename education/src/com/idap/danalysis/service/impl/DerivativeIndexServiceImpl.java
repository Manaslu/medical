package com.idap.danalysis.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.danalysis.entity.DerivativeIndex;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-5-14 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("derivativeIndexService")
public class DerivativeIndexServiceImpl extends DefaultBaseService<DerivativeIndex, String>
		  {
	
	@Resource(name = "dervativeIndexDao")
	public void setBaseDao(IBaseDao<DerivativeIndex, String> baseDao) {
		super.setBaseDao(baseDao);
	}

 
	
	
    

}
