package com.idap.dataprocess.definition.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.dataset.dao.DataSetContentDao;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.definition.dao.DataDefinitionDao;
import com.idap.dataprocess.definition.entity.DataDefinition;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.definition.service.DataDefinitionAttrService;
import com.idap.dataprocess.definition.service.DataDefinitionService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * 
 * @创建日期：2014-4-4 16:16:59
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
@Service("dataDefinitionService")
@Transactional
public class DataDefinitionServiceImpl extends DefaultBaseService<DataDefinition, Long> implements
        DataDefinitionService {
    private static final Log logger = LogFactory.getLog(DataDefinitionServiceImpl.class);

    @Override
    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：级联删除：字段信息、模板表、数据定义记录
     * @传入参数：
     */
    public Integer delete(Map<String, Object> params) {
        Map<String, Object> attrParmas = new HashMap<String, Object>();
        attrParmas.put("dataDefId", params.get("dataDefId"));
        attrDao.delete(attrParmas);// 删除字段
        List<DataDefinition> defs = this.getBaseDao().find(attrParmas);
        DataDefinition def = defs.get(0);
        boolean isExist = tabDao.tableExist(def.getTabName());// 测试模板表是否存在
        if (isExist) {
            tabDao.dropTable(def.getTabName());// 删除模板表
        }
        return this.getBaseDao().delete(params);// 删除数据集记录
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：级联保存数据定义和字段     补全主键字段信息
     * @传入参数：
     */
    public DataDefinition saveDefinitionCascade(DataDefinition bo) {
        this.getBaseDao().save(bo);
        bo.setTabName(DataSetUtils.DATA_DEF_PREFIX + bo.getId());
        this.getBaseDao().update(bo);
        bo.setColumns(columnsService.saveAll(bo.getColumns()));
        return bo;
    }
    

    @Override
    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：级联更新：保存前删除所有的字段重新保存
     * @传入参数：
     */
    public DataDefinition updateDefinitionCascade(DataDefinition bo) {
        //1.清空原有字段
        Map<String, Object> attrParmas = new HashMap<String, Object>();
        attrParmas.put("dataDefId",bo.getDataDefId());
        attrDao.delete(attrParmas);// 删除字段
        //2.查询原有数据集 只更新数据集名称、描述
        List<DataDefinition> defs = this.getBaseDao().find(attrParmas);
        DataDefinition def = defs.get(0);
        def.setDataDefDesc(bo.getDataDefDesc());
        def.setDataDefName(bo.getDataDefName());
        this.getBaseDao().update(def);
        //3.字段保存
        def.setColumns(columnsService.saveAll(bo.getColumns()));
        return def;
    }
    
    

    @Override
    public DataDefinition queryDefinitionCascade(String dataDefId) {
        return this.dataDefinitionDao.queryDefinitionCascade(dataDefId);
    }

    // ---------------------------------依赖注入--------------------------------------
    @Resource(name = "dataDefinitionAttrDao")
    private IBaseDao<DataDefinitionAttr, String> attrDao;
    @Resource(name = "dataDefinitionDao")
    private DataDefinitionDao dataDefinitionDao;
    @Resource(name = "dataSetContentDao")
    private DataSetContentDao tabDao;

    @Resource(name = "dataDefinitionAttrService")
    DataDefinitionAttrService columnsService;

    @Resource(name = "dataDefinitionDao")
    public void setBaseDao(IBaseDao<DataDefinition, Long> baseDao) {
        super.setBaseDao(baseDao);
    }
    @Resource(name = "dataDefinitionDao")
    public void setPagerDao(IPagerDao<DataDefinition> pagerDao) {
        super.setPagerDao(pagerDao);
    }


}
