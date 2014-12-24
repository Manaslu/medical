package com.idap.dataprocess.dataset.dao;

import java.util.List;

import com.idap.dataprocess.definition.entity.DataDefinitionAttr;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-18 11:47:20
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public interface DataSetContentDao {

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：查看表是否存在
     * @传入参数：表名称 t_xxx_xxx
     */
    public boolean tableExist(String tableName);

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：创建物理表
     * @传入参数：
     * @param tableName 表名
     * @param attrs 表所有的字段信息
     */
    public int createTable(String tableName, List<DataDefinitionAttr> attrs);

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：物理删除表
     * @传入参数： 表名
     */
    public int dropTable(String tableName);

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：创建 Sequence  Sequence名称规则：SEQ_tableName
     * @传入参数：表名
     */
    public int createSequence(String tableName);

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：删除 Sequence   Sequence名称规则：SEQ_tableName
     * @传入参数：
     */
    public int dropSequence(String tableName);

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：统计表记录数
     * @传入参数：
     */
    public int count(String tableName);
    

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：最大ID
     * @传入参数：
     */
    public int maxId(String tableName);

}
