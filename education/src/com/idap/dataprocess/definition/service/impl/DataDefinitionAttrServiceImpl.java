package com.idap.dataprocess.definition.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.definition.dao.CountDao;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.definition.service.DataDefinitionAttrService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-4-4 14:58:30
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
@Repository("dataDefinitionAttrService")
@Transactional
public class DataDefinitionAttrServiceImpl extends DefaultBaseService<DataDefinitionAttr, String> implements
        DataDefinitionAttrService<DataDefinitionAttr, String> {
    private static final Log logger = LogFactory.getLog(DataDefinitionAttrServiceImpl.class);
    public static final String KEY_FIND_FOR_DIC_COLUMN = "findForDicColumn";
    @Resource(name = "dataDefinitionAttrDao")
    private CountDao countDao;

    @Resource(name = "generateKeyServcie")
    private IGenerateKeyMangerService generateKeyService;

    @Resource(name = "dataDefinitionAttrDao")
    public void setBaseDao(IBaseDao<DataDefinitionAttr, String> baseDao) {
        super.setBaseDao(baseDao);
    }

    @Resource(name = "dataDefinitionAttrDao")
    public void setPagerDao(IPagerDao<DataDefinitionAttr> pagerDao) {
        super.setPagerDao(pagerDao);
    }

    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：字段信息保存时 [如果是第一次保存时,默认增加一个ID字段]
     * @传入参数：
     * @param entity 字段信息
     * @return 字段信息
     */
    @Override
    public DataDefinitionAttr save(DataDefinitionAttr entity) {
        Integer count = columnsCount(entity.getDataDefId());
        if (count == null || count.intValue() == 0) {
            DataDefinitionAttr idBO = new DataDefinitionAttr();
            idBO = DataSetUtils.buildIdColumn(generateKeyService, entity.getDataDefId());
            this.getBaseDao().save(idBO);
        }
        entity.setColNum(null);
        entity.setIsDisplay(DataSetUtils.SWITCH_TRUE);
        entity.setIsPk(DataSetUtils.SWITCH_FALSE);
        DataDefinitionAttr bo = this.getBaseDao().save(entity);
        return bo;
    }

    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：批量保存[主动增加主键字段]
     * @传入参数：
     * @param entity 字段信息
     * @return 主键字段+参数字段列表 的合集
     */
    public List<DataDefinitionAttr> saveAll(List<DataDefinitionAttr> items) {
        DataDefinitionAttr tempObj = items.get(0);
        Integer count = columnsCount(tempObj.getDataDefId());
        DataDefinitionAttr idBO=null;
        if (count == null || count.intValue() == 0) {
            idBO = new DataDefinitionAttr();
            idBO = DataSetUtils.buildIdColumn(generateKeyService, tempObj.getDataDefId());
            this.getBaseDao().save(idBO);
        }
        for(DataDefinitionAttr col:items){
            col.setColNum(null);
            col.setIsDisplay(DataSetUtils.SWITCH_TRUE);
            col.setIsPk(DataSetUtils.SWITCH_FALSE);
             DataDefinitionAttr bo = this.getBaseDao().save(col);
        }
        if(idBO!=null){
            items.add(0, idBO);
        }
        return items;
    }
    
    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：将字段信息转储为Map形式 key:ColumnName val:实体对像
     * @传入参数：
     * @param dataDefId 数据定义主键
     * @return 字段信息
     */
    public Map<String, DataDefinitionAttr> getColumnsMap(String dataDefId) {
        Map<String, DataDefinitionAttr> itemsMap = new HashMap<String, DataDefinitionAttr>();
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("dataDefId", dataDefId);
        params.put("orderBy", "DISPLAY_COL_NUM");
        List<DataDefinitionAttr> list = this.findList(params);
        for (DataDefinitionAttr attr : list) {
            itemsMap.put(attr.getColumnName().toUpperCase(), attr);
        }
        return itemsMap;
    }
    
    //工具方法：查询字段数量
    private Integer  columnsCount(String dataDefId){
        Map<String, Object> countParams = new HashMap<String, Object>();
        countParams.put("dataDefId", dataDefId);
        return  countDao.count(countParams);
    }

    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：通过接口表的字典来初始化 字段信息
     * @传入参数：
     * @param tableName 表名称
     * @return 字段信息列表
     */
	@Override
	public List<DataDefinitionAttr> findForDicColumn(String tableName) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("tableName", tableName);
		return this.getBaseDao().find(KEY_FIND_FOR_DIC_COLUMN, params);
	}
}
