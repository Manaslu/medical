package com.idap.danalysis.service.impl;

 
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.TableColumns;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-6-10  
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("tableColumnsService")
public class TableColumnsServiceImpl extends DefaultBaseService<TableColumns, String>
		 {
	@Resource(name = "tableColumnsDao")
	public void setBaseDao(IBaseDao<TableColumns, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "tableColumnsDao")
	public void setPagerDao(IPagerDao<TableColumns> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
