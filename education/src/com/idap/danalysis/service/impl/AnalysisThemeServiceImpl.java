package com.idap.danalysis.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.danalysis.entity.AnalysisTheme;
import com.idap.danalysis.entity.ParaHistory;
import com.idap.danalysis.entity.SynchronizationApply;
import com.idp.pub.constants.Constants;
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
@Service("analysisThemeService")
public class AnalysisThemeServiceImpl extends DefaultBaseService<AnalysisTheme, String>
		 {
	@Resource(name = "analysisThemeDao")
	public void setBaseDao(IBaseDao<AnalysisTheme, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "analysisThemeDao")
	public void setPagerDao(IPagerDao<AnalysisTheme> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	@Resource(name = "paraHistoryDao")
	IBaseDao<ParaHistory, String> paraHistoryDao ;
    /**
     * @创建日期：2014-7-10 
     * @开发人员：王威
     * @功能描述：修改分析主题
     * @param params 只需要 themeId
     * @return 操作记录条数
     */	 
	@Override
	public AnalysisTheme update(AnalysisTheme entity) {
		
			if(  entity.getThemeValid() !=null && !entity.getThemeValid().equals("")){//删除
				Map<String, Object> params = Constants.MAP();
				params.put("removalThemeid", entity.getAnaThemeId());
				this.paraHistoryDao.delete(params);//删除参数
			}
			return  this.getBaseDao().update(entity);//非物理删除
			
	    }
    

}
