package com.idap.dataprocess.definition.service;

import java.util.List;
import java.util.Map;

/**
 * @###################################################
 * 
 * @创建日期：2014-4-4 14:58:30
 * @开发人员：李广彬
 * @功能描述：数据定义-数据项功能
 * @修改日志：
 * @###################################################
 */
public interface DataDefinitionAttrService<DataDefinitionAttr, String> {

    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：将字段信息转储为Map形式   key:ColumnName     val:实体对像
     * @传入参数：
     * @param dataDefId 数据定义主键
     * @return 字段信息
     */
    public Map<String, DataDefinitionAttr> getColumnsMap(String dataDefId);
    
    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：批量保存 [隐式增加 主键 字段]
     * @传入参数：
     * @param entity 字段信息
     * @return 返回列表 包含 主键、传入的字段列表
     */
    public List<DataDefinitionAttr> saveAll(List<DataDefinitionAttr> items) ;
    
    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：通过接口表的字典来初始化 字段信息
     * @传入参数：
     * @param tableName 表名称
     * @return 字段信息列表
     */
    public List<DataDefinitionAttr> findForDicColumn(String tableName) ;

}
