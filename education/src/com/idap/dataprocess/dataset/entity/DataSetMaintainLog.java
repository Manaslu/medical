package com.idap.dataprocess.dataset.entity;

import java.util.Date;

import com.idp.pub.utils.DateUtil;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-21 16:45:30
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public class DataSetMaintainLog implements java.io.Serializable {
    private Long id; //
    private String dataSetId; //
    private String columnName; //
    private String rowNumber; //
    private String oldVal; //
    private String newVal; //
    private Date opDate; //
    private String opDateStr;// [附加属性]
    private String opUser; //
    private String opStats; //
    private String startTime;// [附加属性]开始时间
    private String endTime;// [附加属性]结束时间

    /** default constructor */
    public DataSetMaintainLog() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataSetId() {
        return this.dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getRowNumber() {
        return this.rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getOldVal() {
        return this.oldVal;
    }

    public void setOldVal(String oldVal) {
        this.oldVal = oldVal;
    }

    public String getNewVal() {
        return this.newVal;
    }

    public void setNewVal(String newVal) {
        this.newVal = newVal;
    }

    public String getOpDateStr() {
        return this.opDate != null ? DateUtil.dateTimeToStrDefault(this.opDate) : "";
    }

    public Date getOpDate() {
        return this.opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
        this.opDateStr = this.getOpDateStr();
    }

    public String getOpUser() {
        return this.opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getOpStats() {
        return this.opStats;
    }

    public void setOpStats(String opStats) {
        this.opStats = opStats;
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