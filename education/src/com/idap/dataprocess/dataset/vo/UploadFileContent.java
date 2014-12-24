package com.idap.dataprocess.dataset.vo;


import java.util.List;

/**
 * @###################################################
 * @创建日期：2014-4-4 14:58:30
 * @开发人员：李广彬
 * @功能描述：数据集文件读取时信息VO
 * @修改日志： 
 * @###################################################
 */
@SuppressWarnings("rawtypes")
public class UploadFileContent {
    //Map {title:[],conent:[]}
    private List<String> title;//表头信息  字符串序列
    private List content;//内容信息 字符串序列
    private String splitSeparate="\t";//txt文件默认分隔符
    private String firstIsTitle;//1.是 0.否
    public List<String> getTitle() {
        return title;
    }
    public void setTitle(List<String> title) {
        this.title = title;
    }
    public List getContent() {
        return content;
    }
    public void setContent(List conent) {
        this.content = conent;
    }
    public String getSplitSeparate() {
        return splitSeparate;
    }
    public void setSplitSeparate(String splitSeparate) {
        this.splitSeparate = splitSeparate;
    }
    public String getFirstIsTitle() {
        return firstIsTitle;
    }
    public void setFirstIsTitle(String firstIsTitle) {
        this.firstIsTitle = firstIsTitle;
    }
    
}
