package com.idap.clinic.service.impl;


import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.clinic.entity.DepartmentManagement;
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
@Service("departmentManagementService")
public class DepartmentManagementServiceImpl extends DefaultBaseService<DepartmentManagement, String>
		  {
	@Resource(name = "departmentManagementDao")
	public void setBaseDao(IBaseDao<DepartmentManagement, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "departmentManagementDao")
	public void setPagerDao(IPagerDao<DepartmentManagement> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;
	
	 @Override
	public DepartmentManagement save(DepartmentManagement entity) {
		 String eatid =  generateKeyService.getNextGeneratedKey(null).getNextKey();//produce an id
		 entity.setDepartmentId(eatid);
	 	 return  this.getBaseDao().save(entity);
	    }
		 
	 @Override
     public DepartmentManagement update(DepartmentManagement entity) {
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
