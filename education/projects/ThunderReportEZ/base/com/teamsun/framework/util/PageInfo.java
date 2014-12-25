/*
 * PageInfo.java

 * ����.�й�.
 * ��Ȩ����.
 */
/*
 * PageInfo.java

 * 北京.中国.
 * 版权所有.
 */
package com.teamsun.framework.util;


/**
 * 分页信息类。该类包装所有信息，在Controller层和Model层传递分页查询信息时使用，在JSP页面层显示分页信息时也使用PageInfo
 * 
 * @author jiangyb
 *  
 */
public class PageInfo {
    /**
     * PageInfo的常量，当分页查询传入PageInfo.PAGE_ALL时，表明要返回所有的查询结果，即不分页
     */
    public static final PageInfo PAGE_ALL = null;

    private int pageSize;

    private int pageCount;

    private int recordCount;

    private int pageIndex;

    /**
     * 
     * 构造函数，把pageIndex设置为1，pageSize设置为10
     */
    public PageInfo() {
        this.pageIndex = 1;
        this.pageSize = 10;
    }

    /**
     * 
     * @return pageCount 页面的总数
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * 
     * @return recordCount 记录的总数
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * 设置记录的总数时，根据recordCount、pageSize计算并设置pageCount。pageCount是只读属性
     * 
     * @param recordCount
     *            要设置的记录的总数
     */
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
        this.pageCount = this.recordCount / pageSize
                + (this.recordCount % pageSize == 0 ? 0 : 1);
    }

    /**
     * pageIndex 从1开始计算
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * pageIndex 从1开始计算
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 
     * @return pageSize 分页大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 在构造函数自动设置pageSize为10，在Controller层或其它层创建分页大小时可以手工的设置分页的大小，不设置则采用默认值10
     * 
     * @param pageSize
     *            要设置的分页的大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public boolean hasNext(){
        if(this.getPageCount() > this.getPageIndex())
            return true;
        else
            return false;
    }

    public boolean hasPrevious(){
        if(this.getPageIndex()==1)
            return false;
        else
            return true;
    }
}