package com.idap.dataprocess.dataset.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.idap.dataprocess.dataset.dao.DataSetContentDao;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-18 11:47:20
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Repository("dataSetContentDao")
public class DataSetContentDaoImpl extends DefaultBaseDao<Map, String> implements DataSetContentDao {
    private static final Log logger = LogFactory.getLog(DataSetContentDaoImpl.class);
    public static final String NAME_SPACE = "com.idap.dataprocess.dataset.entity.DataSetContent";
    public static final String KEY_TABLE_EXIST = "tableExist";
    public static final String KEY_CREATE = "create";
    public static final String KEY_CREATE_SEQ = "createSequence";
    public static final String KEY_DROP_SEQ = "dropSequence";
    public static final String KEY_DROP = "dropTable";
    public static final String KEY_COUNT = "count";
    public static final String KEY_MAX_ID = "maxId";

    @Override
    public String getNamespace() {
        return NAME_SPACE;
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：查看表是否存在
     * @传入参数：表名称  t_xxx_xxx
     */
    @Override
    public boolean tableExist(String tableName) {
        int count = this.getSqlSession().selectOne(this.sqlKey(KEY_TABLE_EXIST), tableName.toUpperCase());
        return count > 0 ? true : false;
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：创建物理表
     * @传入参数：
     * @param tableName 表名
     * @param attrs 表所有的字段信息
     */
    @Override
    public int createTable(String tableName, List<DataDefinitionAttr> attrs) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tableName", tableName);
        params.put("columns", attrs);

        this.getSqlSession().selectOne(this.sqlKey(KEY_CREATE), params);
        return 0;
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：物理删除表
     * @传入参数： 表名
     */
    public int dropTable(String tableName) {
        this.getSqlSession().delete(this.sqlKey(KEY_DROP), tableName);
        return 0;
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：创建 Sequence  Sequence名称规则：SEQ_tableName
     * @传入参数：表名
     */
    @Override
    public int createSequence(String tableName) {
        this.getSqlSession().selectOne(this.sqlKey(KEY_CREATE_SEQ), tableName);
        return 0;
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：删除 Sequence   Sequence名称规则：SEQ_tableName
     * @传入参数：
     */
    @Override
    public int dropSequence(String tableName) {
        this.getSqlSession().selectOne(this.sqlKey(KEY_DROP_SEQ), tableName);
        return 0;
    }

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：统计表记录数
     * @传入参数：
     */
    @Override
    public int count(String tableName) {
        return this.getSqlSession().selectOne(this.sqlKey(KEY_COUNT), tableName);
    }

	@Override
	public int maxId(String tableName) {
		return this.getSqlSession().selectOne(this.sqlKey(KEY_MAX_ID), tableName);
	}

}
