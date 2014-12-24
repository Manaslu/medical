package com.idap.drad.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
    
import com.idap.drad.entity.EsbRecv;
import com.idap.drad.service.IEsbRecvService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-4-10 16:09:36
 * @开发人员：bai
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("esbRecvService")
public class EsbRecvServiceImpl extends DefaultBaseService<EsbRecv, String>
		implements IEsbRecvService {
	@Resource(name = "EsbRecvDao")
	public void setBaseDao(IBaseDao<EsbRecv, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "EsbRecvDao")
	public void setPagerDao(IPagerDao<EsbRecv> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	public Pager<EsbRecv> query(Pager<EsbRecv> pager, Map<String, Object> params) {
		return this.findByPager(pager, params);

	}
    

}
