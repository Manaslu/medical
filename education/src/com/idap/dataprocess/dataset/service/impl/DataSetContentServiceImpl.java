package com.idap.dataprocess.dataset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.dataset.dao.DataSetContentDao;
import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.service.DataSetContentService;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-04-01 12:01:15
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
@SuppressWarnings("rawtypes")
@Service("dataSetContentService")
@Transactional
public class DataSetContentServiceImpl extends DefaultBaseService<Map<String, Object>, String> implements
        DataSetContentService {
    @Resource(name = "dataSetContentDao")
    private DataSetContentDao contentDao;

    public Pager<Map<String, Object>> queryMergeContent(Map<String, Object> params, Pager<Map<String, Object>> pager) {
        pager = this.getPagerDao().findByPager(pager, params);
        return pager;
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：分页查询 数据集内容
     * @param page 分页信息
     * @param dataSetName 数据集名称
     * @param params 查询条件
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation=Propagation.NOT_SUPPORTED)   
    public Pager<List> queryContent(String tableName, Map<String, Object> params, Pager<Map<String, Object>> pager) {
        // 1.查数据集信息
        Map<String, Object> dsParams = new HashMap<String, Object>();
        dsParams.put("tableName", tableName);
        DataSet ds = (DataSet) dataSetDao.find(dsParams).get(0);
        
        // 2.查所有的字段
        Map<String, Object> attrParams = new HashMap<String, Object>();
        attrParams.put("dataDefId", ds.getDataDefId());
        attrParams.put("isPk", DataSetUtils.SWITCH_TRUE);
        List<DataDefinitionAttr> attrs = attrDao.find(attrParams);
        attrParams.put("isPk", DataSetUtils.SWITCH_FALSE);
        attrParams.put("orderBy", "DISPLAY_COL_NUM asc");
        attrs.addAll(attrDao.find(attrParams));
        // 3.查询数据集内容
        params.put("tableName", tableName);
        params.put("columns", attrs);
        pager = this.getPagerDao().findByPager(pager, params);

        // 数据Map转List
        List data = new ArrayList();
        List<Map<String, Object>> orgData = pager.getData();
        for (Map<String, Object> item : orgData) {
            List items = new ArrayList();
            for (DataDefinitionAttr attr : attrs) {
                items.add(item.get(attr.getColumnName().toUpperCase()));
            }
            data.add(items);
        }

        Pager<List> pagerNew = new Pager<List>();
        pagerNew.setData(data);
        pagerNew.setCurrent(pager.getCurrent());
        pagerNew.setLimit(pager.getLimit());
        pagerNew.setReload(pager.isReload());
        pagerNew.setTotal(pager.getTotal());
        pagerNew.setTotalPage(pager.getTotalPage());
        return pagerNew;
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：物理删除数据集
     * @param tableName 表名
     */
    public int dropTable(String tableName) {
        return this.contentDao.dropTable(tableName);
    };

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：插入指定字段、指定数据值
     * @param columns 所有字段
     * @param values 字段对应的值
     */
    @Override
    public Integer save(List<DataDefinitionAttr> columns, List<Object> values) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("columns", columns);
        params.put("values", values);
        super.save(params);
        return values.size();
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：统计表记录数
     * @param tableName 表名
     */
    @Override
    public int count(String tableName) {
        return this.contentDao.count(tableName);
    }

    // -----------------------------------------------------------------
    @Resource(name = "dataDefinitionAttrDao")
    private IBaseDao<DataDefinitionAttr, String> attrDao;

    @Resource(name = "dataSetDao")
    private IBaseDao<DataSet, String> dataSetDao;

    @Resource(name = "dataSetContentDao")
    public void setBaseDao(IBaseDao<Map<String, Object>, String> baseDao) {
        super.setBaseDao(baseDao);
    }

    @Resource(name = "dataSetContentDao")
    public void setPagerDao(IPagerDao<Map<String, Object>> pagerDao) {
        super.setPagerDao(pagerDao);
    }

}
