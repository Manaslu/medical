package com.idap.clinic.service.impl;


import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.clinic.entity.UserEvaluate;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.impl.DefaultBaseService;



/**
 * @###################################################
 * @创建日期：2015-2-11
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("userEvaluateService")
public class UserEvaluateServiceImpl extends DefaultBaseService<UserEvaluate, String>
		  {
	@Resource(name = "userEvaluateDao")
	public void setBaseDao(IBaseDao<UserEvaluate, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "userEvaluateDao")
	public void setPagerDao(IPagerDao<UserEvaluate> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;
	
	 @Override
	public UserEvaluate save(UserEvaluate entity) {
		 String id =  generateKeyService.getNextGeneratedKey(null).getNextKey();//produce an id
		 entity.setmUserId(id);
	 	 return  this.getBaseDao().save(entity);
	    }
		 
	 @Override
     public UserEvaluate update(UserEvaluate entity) {
			return  this.getBaseDao().update(entity);
	    }		 
		 
   
    @Override
    public Integer delete(Map<String, Object> params) {
        return this.getBaseDao().delete(params); 
    }
    

}
