package com.idap.dataprocess.assess.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.idap.dataprocess.assess.dao.DataSetContourDao;
import com.idap.dataprocess.assess.entity.DataSetContour;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-22 9:12:45
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Repository("dataSetContourDao")
public class DataSetContourDaoImpl extends DefaultBaseDao<DataSetContour, String> implements DataSetContourDao {
    private static final Log logger = LogFactory.getLog(DataSetContourDaoImpl.class);
    public static final String NAME_SPACE = DataSetContour.class.getName();

    public static final String EXECUTE_ASSESS = "executeAssess";

    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：执行评估存储过程
     * @param dataSetId 数据集主键
     * @return 
     *          in dataSetId 数据集ID VARCHAR2 
     *          out executeState INTEGER --执行结果码 0:成功 非0 ：失败
     *          out executeStep VARCHAR2 --执行结果描述 'OK':成功 否则为错误描述
     */
    public Map<String, Object> executeAssess(String dataSetId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataSetId", dataSetId);
        this.getSqlSession().selectOne(EXECUTE_ASSESS, params);
        return params;
    };

    @Override
    public String getNamespace() {
        return NAME_SPACE;
    }

}
