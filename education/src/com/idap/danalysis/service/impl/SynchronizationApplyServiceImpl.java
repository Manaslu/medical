package com.idap.danalysis.service.impl;

 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.danalysis.entity.SynchronizationApply;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-6-7 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("synchronizationApplyService")
public class SynchronizationApplyServiceImpl extends DefaultBaseService<SynchronizationApply, String>
		 {
	@Resource(name = "synchronizationApplyDao")
	public void setBaseDao(IBaseDao<SynchronizationApply, String> baseDao) {
		super.setBaseDao(baseDao);
	}
	
 
	@Resource(name = "synchronizationApplyDao")
	public void setPagerDao(IPagerDao<SynchronizationApply> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	 @Override
	  
	public SynchronizationApply save(SynchronizationApply entity) {
	  
	    	   SynchronizationApply sa = new SynchronizationApply();
	    	   SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
	    	   
	    	   try {
				 
	 
				   sa.setRequId(entity.getRequId()); 
		    	   sa.setDataStartTime(dfm.parse(entity.getDataStartTimes())); 
		    	   if(entity.getDataEndTimes()!= null &&  !entity.getDataEndTimes().equals("")){//周期性的同步则没有结束时间
		    		  sa.setDataEndTime(dfm.parse(entity.getDataEndTimes())); 
		    	   }  
		    	   sa.setCheanMethod(entity.getCheanMethod());  
		    	   sa.setSyncTablename(entity.getSyncTablename());  
		    	   sa.setSyncState(entity.getSyncState()); 
		    	   sa.setApplyId(entity.getApplyId());
		    	   sa.setApplyUser(entity.getApplyUser());  
		    	   sa.setTheme(entity.getTheme());  
		    	   sa.setSourceTableName(entity.getSourceTableName()); 
		    	   sa.setTableComment(entity.getTableComment()); 
		    	   sa.setSourceSystem(entity.getSourceSystem()); 
		    	   sa.setSaveCycle(entity.getSaveCycle()); 
		    	   sa.setApplyDate(new Date()); 
		    	   sa.setDemandDate(dfm.parse(entity.getDemandDates()));  
		    	   sa.setApplyUni(entity.getApplyUni()); 
		    	   sa.setApplyOrg(entity.getApplyOrg()); 
		    	   sa.setReceiveUser(entity.getReceiveUser());  
		    	   sa.setApplyPhone(entity.getApplyPhone());  
		    	 
		    	   sa.setApprovalUser(entity.getApprovalUser());  
		    	   sa.setApprRoleId(entity.getApprRoleId()); 
 
		    	   sa.setFailureDate(dfm.parse(entity.getFailureDates())) ; 
		    	   sa.setAutoManu(entity.getAutoManu()); 
		    	   sa.setTableNameen( entity.getTableNameen()); 
		    	   sa.setApprovalStats("43");//新增
		    	   
		    	   return  this.getBaseDao().save(sa);
				
			} catch (ParseException e) {
				return null;
				 
			}
	    			    
	    
	    }
	 
	 @Override
	  
	public SynchronizationApply update(SynchronizationApply entity) {
	    	   entity.setApprovalDate(new Date());
	    	   SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
	    	   try {
	    		   if(entity.getDataStartTimes()!=null && !entity.getDataStartTimes().equals("")){
	    			   entity.setDataStartTime(dfm.parse(entity.getDataStartTimes()));
	    		   }
				
		    	   if(entity.getDataEndTimes()!= null && !entity.getDataEndTimes().equals("")){//周期性的同步则没有结束时间
		    		   entity.setDataEndTime(dfm.parse(entity.getDataEndTimes())); 
		    	   }  
	    		   if(entity.getDemandDates()!=null && !entity.getDemandDates().equals("")){
	    			   entity.setDemandDate(dfm.parse(entity.getDemandDates()));
	    		   }
	    		   if(entity.getFailureDates()!=null && !entity.getFailureDates().equals("")){
	    			   entity.setFailureDate(dfm.parse(entity.getFailureDates())) ;
	    		   }
		    	     
		    	   
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			return  this.getBaseDao().update(entity);
	    }
	 
	    /**
	     * @创建日期：2014-6-24 12:01:15
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
