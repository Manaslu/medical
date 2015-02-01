package com.idap.clinic.service.impl;


import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.clinic.entity.ClinicInformation;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.impl.DefaultBaseService;



/**
 * @###################################################
 * @创建日期：2015-1-9 
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("clinicInformationService")
public class ClinicInformationServiceImpl extends DefaultBaseService<ClinicInformation, String>
		  {
	@Resource(name = "clinicInformationDao")
	public void setBaseDao(IBaseDao<ClinicInformation, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "clinicInformationDao")
	public void setPagerDao(IPagerDao<ClinicInformation> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;
	
	 @Override
	public ClinicInformation save(ClinicInformation entity) {
		 String id =  generateKeyService.getNextGeneratedKey(null).getNextKey();//produce an id
		 entity.setClinicId(id);
		 entity.setClinicDate(new Date());
	 	 return   this.getBaseDao().save(entity);
	    }
		 
	 @Override
     public ClinicInformation update(ClinicInformation entity) {
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
