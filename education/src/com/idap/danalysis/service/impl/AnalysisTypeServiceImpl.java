package com.idap.danalysis.service.impl;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.AnalysisType;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-6-10 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("analysisTypeService")
public class AnalysisTypeServiceImpl extends DefaultBaseService<AnalysisType, String>
		  {
	@Resource(name = "analysisTypeDao")
	public void setBaseDao(IBaseDao<AnalysisType, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "analysisTypeDao")
	public void setPagerDao(IPagerDao<AnalysisType> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
