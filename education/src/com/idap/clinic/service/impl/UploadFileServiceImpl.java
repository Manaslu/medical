package com.idap.clinic.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.clinic.entity.UploadFile;
import com.idap.clinic.service.IUploadFile;
import com.idap.intextservice.repository.service.IKnowledgeBaseAttService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.impl.DefaultBaseService;



/**
 * @###################################################
 * @创建日期：2015-1-22 
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("uploadFileService")
public class UploadFileServiceImpl extends DefaultBaseService<UploadFile, String> implements IUploadFile
		  {
	@Resource(name = "uploadFileDao")
	public void setBaseDao(IBaseDao<UploadFile, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "uploadFileDao")
	public void setPagerDao(IPagerDao<UploadFile> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;
	
	 @Override
	public UploadFile save(UploadFile entity) {
		 
	 	 return  this.getBaseDao().save(entity);
	    }
		 
	 @Override
     public UploadFile update(UploadFile entity) {
			return  this.getBaseDao().update(entity);
	    }		 
		 
    /**
     * @创建日期：2015-1-22 12:01:15
     * @开发人员：王威
     * @功能描述： 
     * @param params 只需要 申请id
     * @return 操作记录条数
     */
    @Override
    public Integer delete(Map<String, Object> params) {
        return this.getBaseDao().delete(params); 
    }

	@Override
	public List<UploadFile> query(Map<String, Object> params) {
		return super.findList( params); 
	}
    

}
