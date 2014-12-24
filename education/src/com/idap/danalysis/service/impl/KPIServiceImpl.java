package com.idap.danalysis.service.impl;

 
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.idap.danalysis.entity.KPI;
import com.idap.danalysis.entity.IndexMaintain;
import com.idap.danalysis.entity.DerivativeIndex;
import com.idap.danalysis.service.IKPIservice;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;

/**
 * @###################################################
 * @创建日期：2014-5-14 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("kpiService")
public class KPIServiceImpl extends DefaultBaseService<KPI, String>
		 {
	private static final Log logger = LogFactory.getLog(IKPIservice.class);
	
    @Resource(name = "generateKeyServcie")
    private IGenerateKeyMangerService generateKeyService;
    
	@Resource(name = "kpiDao")
	public void setBaseDao(IBaseDao<KPI, String> baseDao) {
		super.setBaseDao(baseDao);
	}
	
 
	
	@Resource(name = "indexMaintainDao")
	IBaseDao<IndexMaintain, String> indexMaintainDao ;
	
	@Resource(name = "dervativeIndexDao")
	IBaseDao< DerivativeIndex, String> dervativeIndexDao ;
	
	@Resource(name = "kpiDao")
	IBaseDao< KPI, String> kpiDao ;
	
	

	@Resource(name = "kpiDao")
	public void setPagerDao(IPagerDao<KPI> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	public Pager<KPI> query(Pager<KPI> pager, Map<String, Object> params) {
		return this.findByPager(pager, params);

	}
	
	 @Override
	    public KPI update(KPI entity) {
		 
  	   KPI kpi = new KPI();
  	   IndexMaintain dndexMaintain = new IndexMaintain();
  	   DerivativeIndex derivativeIndex = new DerivativeIndex();
		 
		 if(entity.getFailFlag().endsWith("0")){//删除衍生指标

			kpi.setIndexId(entity.getIndexId()); 
            kpi.setFailFlag("0");
			dndexMaintain.setUserId(entity.getUserId());
			dndexMaintain.setIndexId(entity.getIndexId());
			dndexMaintain.setIndexTheme_id(entity.getIndexTheme_id());
			dndexMaintain.setIndexVersion(entity.getIndexVersion());
			dndexMaintain.setOpType("删除");

			this.indexMaintainDao.save(dndexMaintain);
		 }else{//修改指标

			kpi.setIndexTheme_id(entity.getIndexTheme_id());
			kpi.setIndexId(entity.getIndexId()); 
			kpi.setIndexName(entity.getIndexName());
			kpi.setIndexDesc(entity.getIndexDesc());
			kpi.setIndexVersion(entity.getIndexVersion());

			dndexMaintain.setUserId(entity.getUserId());
			dndexMaintain.setIndexId(entity.getIndexId());
			dndexMaintain.setIndexTheme_id(entity.getIndexTheme_id());
			dndexMaintain.setIndexVersion(entity.getIndexVersion());
			dndexMaintain.setOpType("修改");
			 

			this.indexMaintainDao.save(dndexMaintain);
			
			derivativeIndex.setIndexId(entity.getIndexId());
			derivativeIndex.setCalMethod(entity.getCalMethod());
			derivativeIndex.setIndexVersion(entity.getIndexVersion());
			derivativeIndex.setCreateTime(new Date());
			derivativeIndex.setCalMethodDesc(entity.getCalMethodDesc());

			
			
			this.dervativeIndexDao.update(derivativeIndex);
			 
		 }

			return this.kpiDao.update(kpi);
			
	 	
	    
	    }
	
	//新增
    @Override
    public KPI save(KPI entity) {
    	   String indexid;
    	   KPI kpi = new KPI();
    	   IndexMaintain dndexMaintain = new IndexMaintain();
    	   DerivativeIndex derivativeIndex = new DerivativeIndex();
		try {
			indexid = generateKeyService.getNextGeneratedKey(null).getNextKey();
			
			kpi.setIndexTheme_id(entity.getIndexTheme_id());
        	kpi.setIndexId(indexid); 
            kpi.setIndexName(entity.getIndexName());
			kpi.setIndexDesc(entity.getIndexDesc());
			kpi.setDerIndex_flag("1");
			kpi.setIndexVersion(entity.getIndexVersion());
			kpi.setFailFlag("1");
			
			dndexMaintain.setUserId(entity.getUserId());
			dndexMaintain.setIndexId(indexid);
			dndexMaintain.setIndexTheme_id(entity.getIndexTheme_id());
			dndexMaintain.setIndexVersion(entity.getIndexVersion());
			dndexMaintain.setOpType("新增");

			this.indexMaintainDao.save(dndexMaintain);//日志表增加记录
			
			derivativeIndex.setIndexId(indexid);
			derivativeIndex.setCalMethod(entity.getCalMethod());
			derivativeIndex.setCreateUser(entity.getCreateUser());
			derivativeIndex.setIndexVersion(entity.getIndexVersion());
			derivativeIndex.setCreateTime(new Date());
			derivativeIndex.setCalMethodDesc(entity.getCalMethodDesc());
			derivativeIndex.setIndexDirectionId(entity.getIndexDirectionId());
			this.dervativeIndexDao.save(derivativeIndex);	//衍生指标表增加记录
			
		} catch (BusinessException e) {
		   logger.error(e);
		}
		return this.kpiDao.save(kpi);//指标表增加记录
    
    }
    

}
