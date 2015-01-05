package com.idap.clinic.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.clinic.entity.HealthFood;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2015-1-5 
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("healthFoodService")
public class HealthFoodServiceImpl extends DefaultBaseService<HealthFood, String>
		  {
	@Resource(name = "healthFoodDao")
	public void setBaseDao(IBaseDao<HealthFood, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "healthFoodDao")
	public void setPagerDao(IPagerDao<HealthFood> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
