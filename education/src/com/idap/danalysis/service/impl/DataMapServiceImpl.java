package com.idap.danalysis.service.impl;

import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.danalysis.dao.DataMapDao;
import com.idap.danalysis.service.IDataMapService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

@Transactional
@Service("dataMapService")
public class DataMapServiceImpl extends DefaultBaseService<Map<String, Object>, String> implements IDataMapService{
    @Resource(name = "dataMapDao")
    private DataMapDao dataMapDao;

    @Resource(name = "dataMapDao")
    public void setBaseDao(IBaseDao<Map<String, Object>, String> baseDao) {
        super.setBaseDao(baseDao);
    }

    @Resource(name = "dataMapDao")
    public void setPagerDao(IPagerDao<Map<String, Object>> pagerDao) {
        super.setPagerDao(pagerDao);
    }

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Map<String, Object>> findColumns(String key,Map<String, Object> params) {
		return this.dataMapDao.find(key, params);
	}

	@Override
	public String executeScript(String executeScript, Map<String, Object> params) {
		this.dataMapDao.unique(executeScript, params);
		return (String) params.get("tableName");
	}

	@Override
	public void copyScript(String executeScript, Map<String, Object> params) {
		this.dataMapDao.unique(executeScript, params);
	}

	@Override
	public void executeSQL(String sql, List<Object[]> params) {
		this.jdbcTemplate.batchUpdate(sql, params);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int queryForInt(String sql, Object obj) {
		return this.jdbcTemplate.queryForInt(sql, obj);
	}

	@Override
	public List<Map<String, Object>> qeuryForList(String sql, Object... obj) {
		return this.jdbcTemplate.queryForList(sql, obj);
	}
    
}
