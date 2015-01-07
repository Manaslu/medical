package com.idap.clinic.service.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.clinic.entity.HealthFood;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
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
	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;
	
	 @Override
	public HealthFood save(HealthFood entity) {
		 String eatid =  generateKeyService.getNextGeneratedKey(null).getNextKey();//produce an id
		 HealthFood hf = new HealthFood();
		 hf.setEatId(eatid);
		 hf.setEatTitle(entity.getEatTitle());
		 hf.setEatContent(entity.getEatContent());
		 hf.setEatPic(entity.getEatPic());
		 hf.setEatDate(new Date());
		 hf.setClinicId(entity.getClinicId());
	 	 return  this.getBaseDao().save(hf);
	    }
		 
	 @Override
     public HealthFood update(HealthFood entity) {
	    	entity.setEatDate(new Date());
			return  this.getBaseDao().update(entity);
	    }		 
		 
    /**
     * @创建日期：2015-1-6 12:01:15
     * @开发人员：王威
     * @功能描述：删除申请
     * @param params 只需要 申请id
     * @return 操作记录条数
     */
    @Override
    public Integer delete(Map<String, Object> params) {
        return this.getBaseDao().delete(params); 
    }
    

}
