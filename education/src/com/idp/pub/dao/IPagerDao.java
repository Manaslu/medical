package com.idp.pub.dao;

import java.util.Map;

import com.idp.pub.entity.Pager;

/**
 * 分页接口类
 * 
 * @author Raoxiaoyan
 * 
 * @param <T>
 */
public interface IPagerDao<T> {
	/**
	 * 分页查询,默认实现
	 * 
	 * @param pager
	 *            分页具体类
	 * @param params
	 *            参数列表
	 * @return
	 */
	public Pager<T> findByPager(Pager<T> pager, Map<String, Object> params);

	public Pager<T> findByPager(String key, Pager<T> pager,
			Map<String, Object> params);
}
