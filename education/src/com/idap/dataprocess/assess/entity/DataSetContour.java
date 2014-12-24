package com.idap.dataprocess.assess.entity;

import java.util.Date;

import com.idp.pub.utils.DateUtil;

/**
 * /**
 * 
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-22 9:12:45
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public class DataSetContour implements java.io.Serializable {
    private String dataSetId; //
    private String colName; //
    private String dataType; //
    private Long rowCount; //
    private Long uniqueCount; //
    private Long nullCount; //
    private Long noValueCount; //
    private Long zeroCount; //
    private Long positiveCount; //
    private Long negativeCount; //
    private Date statDate; //
    private String statDateStr;// [附加属性]
    private String startTime;// [附加属性]开始时间
    private String endTime;// [附加属性]结束时间

    private String colShowName; //

    /** default constructor */
    public DataSetContour() {
    }

    public String getColShowName() {
        return colShowName;
    }

    public void setColShowName(String colShowName) {
        this.colShowName = colShowName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataSetId() {
        return this.dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    public String getColName() {
        return this.colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Long getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
    }

    public Long getUniqueCount() {
        return this.uniqueCount;
    }

    public void setUniqueCount(Long uniqueCount) {
        this.uniqueCount = uniqueCount;
    }

    public Long getNullCount() {
        return this.nullCount;
    }

    public void setNullCount(Long nullCount) {
        this.nullCount = nullCount;
    }

    public Long getNoValueCount() {
        return this.noValueCount;
    }

    public void setNoValueCount(Long noValueCount) {
        this.noValueCount = noValueCount;
    }

    public Long getZeroCount() {
        return this.zeroCount;
    }

    public void setZeroCount(Long zeroCount) {
        this.zeroCount = zeroCount;
    }

    public Long getPositiveCount() {
        return this.positiveCount;
    }

    public void setPositiveCount(Long positiveCount) {
        this.positiveCount = positiveCount;
    }

    public Long getNegativeCount() {
        return this.negativeCount;
    }

    public void setNegativeCount(Long negativeCount) {
        this.negativeCount = negativeCount;
    }

    public String getStatDateStr() {
        return this.statDate != null ? DateUtil.dateTimeToStrDefault(this.statDate) : "";
    }

    public Date getStatDate() {
        return this.statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
        this.statDateStr = this.getStatDateStr();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}