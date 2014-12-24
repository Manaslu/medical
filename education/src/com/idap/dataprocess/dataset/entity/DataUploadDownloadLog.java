package com.idap.dataprocess.dataset.entity;

import java.util.Date;

import com.idp.pub.utils.DateUtil;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-5-15 19:18:03
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public class DataUploadDownloadLog implements java.io.Serializable {
    private Long id; //
    private String dataSetId; //
    private String opType; //
    private String opUser; //
    private Date opStartDate; //
    private String opStartDateStr;// [附加属性]
    private Date opEndDate; //
    private String opEndDateStr;// [附加属性]
    private Long dataCount; //
    private String opStats; //
    private String errFileName; //
    private String errFileDir; //
    private String sourFileName; //
    private String sourFileDir; //
    private Integer succDataCnt; //
    private Integer failDataCnt; //
    private String dataDefName;// [附加属性]
    private String startTime;// [附加属性]开始时间
    private String endTime;// [附加属性]结束时间

    private String dataSetName;// [附加属性]结束时间
    private String opUserName;// [附加属性]结束时间

    public Integer getSuccDataCnt() {
        return succDataCnt;
    }

    public void setSuccDataCnt(Integer succDataCnt) {
        this.succDataCnt = succDataCnt;
    }

    public Integer getFailDataCnt() {
        return failDataCnt;
    }

    public String getDataDefName() {
        return dataDefName;
    }

    public void setDataDefName(String dataDefName) {
        this.dataDefName = dataDefName;
    }

    public void setFailDataCnt(Integer failDataCnt) {
        this.failDataCnt = failDataCnt;
    }

    public void setOpStartDateStr(String opStartDateStr) {
        this.opStartDateStr = opStartDateStr;
    }

    public void setOpEndDateStr(String opEndDateStr) {
        this.opEndDateStr = opEndDateStr;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    /** default constructor */
    public DataUploadDownloadLog() {
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

    public String getOpType() {
        return this.opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpUser() {
        return this.opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getOpStartDateStr() {
        return this.opStartDate != null ? DateUtil.dateTimeToStrDefault(this.opStartDate) : "";
    }

    public Date getOpStartDate() {
        return this.opStartDate;
    }

    public void setOpStartDate(Date opStartDate) {
        this.opStartDate = opStartDate;
        this.opStartDateStr = this.getOpStartDateStr();
    }

    public String getOpEndDateStr() {
        return this.opEndDate != null ? DateUtil.dateTimeToStrDefault(this.opEndDate) : "";
    }

    public Date getOpEndDate() {
        return this.opEndDate;
    }

    public void setOpEndDate(Date opEndDate) {
        this.opEndDate = opEndDate;
        this.opEndDateStr = this.getOpEndDateStr();
    }

    public Long getDataCount() {
        return this.dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public String getOpStats() {
        return this.opStats;
    }

    public void setOpStats(String opStats) {
        this.opStats = opStats;
    }

    public String getErrFileName() {
        return this.errFileName;
    }

    public void setErrFileName(String errFileName) {
        this.errFileName = errFileName;
    }
    

    public String getErrFileDir() {
        return this.errFileDir;
    }

    public void setErrFileDir(String errFileDir) {
        this.errFileDir = errFileDir;
    }

    public String getSourFileName() {
        return this.sourFileName;
    }

    public void setSourFileName(String sourFileName) {
        this.sourFileName = sourFileName;
    }

    public String getSourFileDir() {
        return this.sourFileDir;
    }

    public void setSourFileDir(String sourFileDir) {
        this.sourFileDir = sourFileDir;
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