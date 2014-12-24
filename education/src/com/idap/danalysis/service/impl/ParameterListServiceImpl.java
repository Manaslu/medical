package com.idap.danalysis.service.impl;

 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.danalysis.dao.ParameterListDao;
import com.idap.danalysis.entity.AnalysisTheme;
import com.idap.danalysis.entity.ParaHistory;
import com.idap.danalysis.entity.ParameterList;
import com.idap.danalysis.entity.SynchronizationApply;
import com.idap.danalysis.service.IParameterList;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;

/**
 * @###################################################
 * @创建日期：2014-6-21 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("parameterListService")
public class ParameterListServiceImpl extends DefaultBaseService<ParameterList, String>
		implements IParameterList {
	private static final Log logger = LogFactory.getLog(IParameterList.class);
	
	
	@Resource(name = "parameterListDao")
	public void setBaseDao(IBaseDao<ParameterList, String> baseDao) {
		super.setBaseDao(baseDao);
	}
	@Resource(name = "parameterListDao")
	public void setPagerDao(IPagerDao<ParameterList> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
    @Resource(name = "parameterListDao")
    private ParameterListDao parameterListDao;
	
	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;
	
	@Resource(name = "paraHistoryDao")
	IBaseDao<ParaHistory, String> paraHistoryDao ;
	
	@Resource(name = "analysisThemeDao")
	IBaseDao<AnalysisTheme, String> analysisThemeDao ;
	
	 @Override
	  
	public ParameterList save(ParameterList entity)  {
		  AnalysisTheme theme = new AnalysisTheme();
		  ParaHistory parahistory = new ParaHistory();
		  String anaThemeId =  generateKeyService.getNextGeneratedKey(null).getNextKey();//主题id
		  anaThemeId = anaThemeId.substring(10, 20);
		  File targetFile =null;
		  if(entity.getImportantClientFilePath() != null && !entity.getImportantClientFilePath().equals("")  ){
			  targetFile = new File(entity.getImportantClientFilePath());
		  }
	         
	        BufferedReader lnReader = null;
	        try {

	        	//如果某个参数为空,会导致存储过程严重报错
	        	if(       entity.getYear().equals("") || entity.getYear()==null
	        			||entity.getUserProvince().equals("") || entity.getUserProvince()==null
	        			||entity.getObjectPaper().equals("") || entity.getObjectPaper()==null
	        			||entity.getCompetetorIds().equals("") || entity.getCompetetorIds()==null
	        			||entity.getSamePaperIds().equals("") || entity.getSamePaperIds()==null
	        			||entity.getImportantProvince().equals("") || entity.getImportantProvince()==null
	        			
	        	  ){
	        		return null;
	        	}
	        	
	        	//插入主题信息
	        	
	        	theme.setAnaThemeId(anaThemeId);
	        	theme.setAnaMethodId(entity.getAnaMethodId());
	        	theme.setAnaThemeName(entity.getAnaThemeName());
	        	theme.setAnaThemeDesc(entity.getAnaThemeDesc());
	        	theme.setAnaScriptName(entity.getAnaScriptName());
	        	theme.setCreateUser(entity.getCreateUser());
	        	theme.setCreateDate(new Date());
	        	theme.setVersion(entity.getVersion());
	        	theme.setThemeValid("1");//有效标示为1
	        	theme.setAnaStatus("30");//新建
	         
	        	this.analysisThemeDao.save(theme); 
	        	
	        	
	        	if(entity.getYear()!=null && !entity.getYear().equals("") ){//年份
	        		String year= entity.getYear().trim();
		               	 parahistory.setAnaThemeId(anaThemeId);//刚才新建的主题
		               	 parahistory.setIndexId("307");//写死目标报刊年份id
		               	 parahistory.setIndexFlag("year");//存储过程里可以通过这个标记知道这个参数的用途
		               	 parahistory.setIndexVal(year);
		               	this.paraHistoryDao.save(parahistory);
		        }
	        	
	        	if(entity.getUserProvince()!=null  && !entity.getUserProvince().equals("")   ){//当前用户的省份
	        		String userProvince= entity.getUserProvince().trim();
		               	 parahistory.setAnaThemeId(anaThemeId);//刚才新建的主题
		               	 parahistory.setIndexId("308");//写死目标报刊年份id
		               	 parahistory.setIndexFlag("userProvince");//存储过程里可以通过这个标记知道这个参数的用途
		               	 parahistory.setIndexVal(userProvince);
		               	this.paraHistoryDao.save(parahistory);
		        }
	        	
	        	if(entity.getObjectPaper()!=null && !entity.getObjectPaper().equals("")){//目标报刊
	        		
	        		String array[]= entity.getObjectPaper().split(",");
	        		for(int i=0;i<array.length;i++){
		               	 parahistory.setAnaThemeId(anaThemeId);//刚才新建的主题
		               	 parahistory.setIndexId("300");//写死目标报刊id
		               	 parahistory.setIndexFlag("objectPaper");//存储过程里可以通过这个标记知道这个参数的用途
		               	 parahistory.setIndexVal(array[i].trim());
		               	this.paraHistoryDao.save(parahistory);
		        		}
		        }
	        	
	        	if(entity.getCompetetorIds()!=null && !entity.getCompetetorIds().equals("") ){//竞争对手
	        		
	        		String array[]= entity.getCompetetorIds().split(",");
	        		for(int i=0;i<array.length;i++){
		               	 parahistory.setAnaThemeId(anaThemeId);//刚才新建的主题
		               	 parahistory.setIndexId("301");//写死竞争对手参数id
		               	 parahistory.setIndexFlag("competetor");//存储过程里可以通过这个标记知道这个参数的用途
		               	 parahistory.setIndexVal(array[i].trim());
		               	this.paraHistoryDao.save(parahistory);
		        		}
		        }
	        	//同类市场
	        	if(entity.getSamePaperIds()!=null &&!entity.getSamePaperIds().equals("") ){
	        		
	        		String array[]= entity.getSamePaperIds().split(",");
	        		for(int i=0;i<array.length;i++){
		               	 parahistory.setAnaThemeId(anaThemeId); 
		               	 parahistory.setIndexId("302");// 
		               	 parahistory.setIndexFlag("sameMarket"); 
		               	 parahistory.setIndexVal(array[i].trim());
		               	this.paraHistoryDao.save(parahistory);
		        		}
		        }
	        	//行业类型
	        	if(entity.getIndustryIds()!=null && !entity.getIndustryIds().equals("")){
	        		
	        		String array[]= entity.getIndustryIds().split(",");
	        		for(int i=0;i<array.length;i++){
		               	 parahistory.setAnaThemeId(anaThemeId); 
		               	 parahistory.setIndexId("303");
		               	 parahistory.setIndexFlag("industry"); 
		               	 parahistory.setIndexVal(array[i].trim());
		               	this.paraHistoryDao.save(parahistory);
		        		}
		        }
	        	//导入重点客户
	        	if(targetFile != null){
		        	lnReader = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile),this.getFileEncoding(targetFile)));
		             String temp = lnReader.readLine();
		             while (temp != null) {
		                 if (temp.trim().length() > 0) {//非空行,则插入txt文件内容
		                	 if(temp.indexOf("'")==-1 || temp.indexOf("\"")==-1||temp.indexOf(",")==-1){//有异常符号的数据直接扔掉
			                	 parahistory.setAnaThemeId(anaThemeId);//刚才新建的主题
			                	 parahistory.setIndexId("304");//写死重点客户的参数id
			                	 parahistory.setIndexFlag("importantClient");//重点客户的参数标记,存储过程里可以通过这个标记知道这个参数的用途
			                	 parahistory.setIndexVal(temp.trim());
			                	 this.paraHistoryDao.save(parahistory);
		                		 
		                	 }

		                 }
		                 temp = lnReader.readLine();
		             }
		             lnReader.close();
		             
	        	}
	        	//大客户定义
	        	if(  entity.getParaBigClientArrays()!=null && !entity.getParaBigClientArrays().equals("")){
	        		
	        		String array[]= entity.getParaBigClientArrays().split(",");
	        		for(int i=0;i<array.length;i++){
	        			String temp[] = array[i].split("_");
		               	 parahistory.setAnaThemeId(anaThemeId); 
		               	 parahistory.setIndexId("305");
		               	 //大客户的参数标记有: numBiggerThan,sellBiggerThan,numRankTop,sellRankTop,numRankTopPercent,sellRankTopPercent
		               	 parahistory.setIndexFlag(temp[0]); 
		               	 parahistory.setIndexVal(temp[1]);
		               	this.paraHistoryDao.save(parahistory);
		        		}
		        }
	        	//重点省
	        	if(entity.getImportantProvince()!=null && !entity.getImportantProvince().equals("")){
	        		
	        		String array[]= entity.getImportantProvince().split(",");
	        		for(int i=0;i<array.length;i++){
		               	 parahistory.setAnaThemeId(anaThemeId); 
		               	 parahistory.setIndexId("306");// 
		               	 parahistory.setIndexFlag("importantProvince"); 
		               	 parahistory.setIndexVal(array[i].trim());
		               	this.paraHistoryDao.save(parahistory);
		        		}
		        }
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error(e);
	        } 
			return  entity;
	    }
	 
	 
	    /**
	     * @创建日期：2014-6-21 12:01:15
	     * @开发人员：王威
	     * @功能描述：删除参数和主题
	     * @param params 只需要 themeId
	     * @return 操作记录条数
	     */
	    @Override
	    public Integer delete(Map<String, Object> params) {
	    	this.paraHistoryDao.delete(params);
	        return this.analysisThemeDao.delete(params); 
	    }

 
	    /**
	     * @Description 读取txt文件编码
	     * @author
	     * @param {filePath:文件路径}
	     * @return
	     * @version 1.0
	     */
	 public String getFileEncoding(File file) {
	        FileInputStream fis = null;
	        String txtCode = "";
	        try {
	            fis = new FileInputStream(file);
	            int a = fis.read();
	            int b = fis.read();
	            if (a == 0xFF && b == 0xFE) {
	                txtCode = "Unicode";
	            } else if (a == 0xFE && b == 0xFF) {
	                txtCode = "UTF-16BE";
	            } else if (a == 0xEF && b == 0xBB) {
	                txtCode = "UTF-8";
	            } else {
	                txtCode = "GBK";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (fis != null) {
	                    fis.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return txtCode;
	    }

		@Override
 
		
	    public  void executeDataAnasis(String excuteThemeid) {
		       this.parameterListDao.executeDataAnasis(excuteThemeid);
		       
		     
	    };
	 
    

}
