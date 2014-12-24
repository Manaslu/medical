package com.idap.dataprocess.assess.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.assess.dao.DataSetContourDao;
import com.idap.dataprocess.assess.entity.CompleteSituation;
import com.idap.dataprocess.assess.entity.DataSetContour;
import com.idap.dataprocess.assess.service.AssessService;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-04-01 12:01:15
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
@Service("assessService")
@Transactional
public class AssessServiceImpl extends DefaultBaseService<DataSetContour, String> implements AssessService {

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：执行数据轮廓的存储过程 写 1、数据集轮廓表 2、整合日志表
     * @param dataSetId 数据集名称
     * @return 返回数据轮廓报告主键 成功1 失败0
     * @throws Exception
     */
    public Map<String, Object> executeAssess(String dataSetId) {
        return this.executor.executeAssess(dataSetId);
    };

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：字段分项统计数据
     * @param dataSetName 数据集名称
     * @return
     * @throws Exception
     */
    public List<DataSetContour> queryColAssessInfo(String dataSetId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataSetId", dataSetId);
        params.put("noColName", DataSetUtils.ID_COL_NAME);
        return this.findList(params);
    };

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：评估统计 完整度整理
     * @param dataSetName 数据集名称
     * @return
     * @throws Exception
     */
    public List<CompleteSituation> assessStatistics(String dataSetId) {
        List<CompleteSituation> result = new ArrayList<CompleteSituation>();
        List<DataSetContour> items = this.queryColAssessInfo(dataSetId);
        CompleteSituation cs10095 = new CompleteSituation(items.size());
        CompleteSituation cs9590 = new CompleteSituation(items.size());
        CompleteSituation cs9080 = new CompleteSituation(items.size());
        CompleteSituation cs8030 = new CompleteSituation(items.size());
        CompleteSituation cs3000 = new CompleteSituation(items.size());
        CompleteSituation cs0 = new CompleteSituation(items.size());
        Long reslutCount = 0l, totalCount = 0l;
        double ratio = 0;
        for (DataSetContour dsc : items) {
            totalCount = dsc.getRowCount();
            reslutCount = dsc.getNullCount();
            ratio = (totalCount - reslutCount) * 100 / totalCount;
            if (ratio <= 100 && ratio > 95) {
                cs10095.addTotalCount(totalCount);
                cs10095.addCount(totalCount - reslutCount);
                cs10095.addColumns(dsc.getColName());
            }
            if (ratio <= 95 && ratio > 90) {
                cs9590.addTotalCount(totalCount);
                cs9590.addCount(totalCount - reslutCount);
                cs9590.addColumns(dsc.getColName());
            }
            if (ratio <= 90 && ratio > 80) {
                cs9080.addTotalCount(totalCount);
                cs9080.addCount(totalCount - reslutCount);
                cs9080.addColumns(dsc.getColName());
            }
            if (ratio <= 80 && ratio > 30) {
                cs8030.addTotalCount(totalCount);
                cs8030.addCount(totalCount - reslutCount);
                cs8030.addColumns(dsc.getColName());
            }
            if (ratio <= 30 && ratio > 0) {
                cs3000.addTotalCount(totalCount);
                cs3000.addCount(totalCount - reslutCount);
                cs3000.addColumns(dsc.getColName());
            }
            if (ratio == 0) {
                cs0.addTotalCount(totalCount);
                cs0.addCount(totalCount - reslutCount);
                cs0.addColumns(dsc.getColName());
            }
        }

        result.add(cs10095);
        result.add(cs9590);
        result.add(cs9080);
        result.add(cs8030);
        result.add(cs3000);
        result.add(cs0);
        return result;
    };

    // --------------------------------------注入---------------------------------------

    @Resource(name = "dataSetContourDao")
    public void setBaseDao(IBaseDao<DataSetContour, String> baseDao) {
        super.setBaseDao(baseDao);
    }

    @Resource(name = "dataSetContourDao")
    private DataSetContourDao executor;

    @Resource(name = "dataSetContourDao")
    public void setPagerDao(IPagerDao<DataSetContour> pagerDao) {
        super.setPagerDao(pagerDao);
    }

}
