package com.idap.danalysis.service.impl;

 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.idap.danalysis.entity.AnalysisProcessSolidify;
import com.idap.danalysis.entity.KPI;
import com.idap.danalysis.service.IAnalysisProcessSolidifyService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-6-23 16:09:36
 * @开发人员：heying
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("analysisProcessSolidifyService")
public class AnalysisProcessSolidifyServiceImpl extends DefaultBaseService<AnalysisProcessSolidify, String>
		implements IAnalysisProcessSolidifyService {
	@Resource(name = "analysisProcessSolidifyDao")
	public void setBaseDao(IBaseDao<AnalysisProcessSolidify, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "analysisProcessSolidifyDao")
	public void setPagerDao(IPagerDao<AnalysisProcessSolidify> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    
    @Resource(name = "generateKeyServcie")
    private IGenerateKeyMangerService generateKeyService;
    
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	 @Override
	 public AnalysisProcessSolidify save(AnalysisProcessSolidify entity) {
		 String id = generateKeyService.getNextGeneratedKey(null).getNextKey();
		 entity.setId(id);
		 return super.save(entity);
	 }

	@Override
	public Integer delete(Map<String, Object> params) {
		String id = (String) params.get("id");
		String sql1 = "delete from t04_analysis_result_output where id = '"+id+"'";
		String sql2 = "delete from t04_analysis_log where id = '"+id+"'";
		
		this.jdbcTemplate.execute(sql1);
		this.jdbcTemplate.execute(sql2);
		return super.delete(params);
	}
}
