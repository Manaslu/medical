package com.idap.dataprocess.rule.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.rule.dao.DataInteLogDao;
import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idap.dataprocess.rule.entity.DataLogState;
import com.idap.dataprocess.rule.service.DataInteLogService;
import com.idp.pub.constants.Constants;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.impl.DefaultBaseService;

@Service("dataInteLogService")
@Transactional
public class DataInteLogServiceImpl extends
		DefaultBaseService<DataInteLog, String> implements DataInteLogService {
	@Resource(name = "dataInteLogDao")
	public void setBaseDao(IBaseDao<DataInteLog, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "dataInteLogDao")
	public void setPagerDao(IPagerDao<DataInteLog> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	@Resource(name = "dataInteLogDao")
	private DataInteLogDao dataInteLogDao;

	@Override
	public List<DataInteLog> findReport(DataInteLog entity)
			throws BusinessException {
		List<DataInteLog> inteLogs = dataInteLogDao.getReport(entity);
		return inteLogs;
	}
	
	@Override
	public Pager<DataInteLog> findByPager(Pager<DataInteLog> pager,
			Map<String, Object> params) {
		params.put("processType", DataInteLog.PROCESS_TYPE_MANUAL);
		return super.findByPager(pager, params);
	}

	@Override
	public List<DataSet> findGoalSet(String osetId, String processType)
			throws BusinessException {
		Map<String, Object> params = Constants.MAP();
		params.put("osetId", osetId);
		params.put("processType", DataInteLog.PROCESS_TYPE_MANUAL);
		params.put("opStatus", DataLogState.LOG_FINISHED.getState());// 默认增加参数为已经完成的整合日志

		List<DataInteLog> inteLogs = dataInteLogDao.findByTree(params);

		List<DataSet> dsets = new ArrayList<DataSet>();

		for (DataInteLog log : inteLogs) {
			dsets.add(log.getResultDataSet());
		}

		return dsets;
	}

	@Override
	public List<DataInteLog> findByResult(String gsetId, String processType)
			throws BusinessException {
		Map<String, Object> params = Constants.MAP();
		params.put("gsetId", gsetId);
		params.put("processType", processType);
		// params.put("opStatus", DataLogState.LOG_FINISHED.getState());//
		// 默认增加参数为已经完成的整合日志

		return this.dataInteLogDao.findByResult(params);
	}
}
