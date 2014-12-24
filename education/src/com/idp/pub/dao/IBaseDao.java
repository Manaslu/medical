package com.idp.pub.dao;

import java.util.List;
import java.util.Map;

import com.idp.pub.exception.BusinessException;

public interface IBaseDao<T, PK> {
	/**
	 * 保存或修改实体对象,默认实现
	 * 
	 * @param entity
	 * @return
	 */
	public T save(T entity);

	public T save(String key, T entity);

	/**
	 * 通过主键获取对象,默认实现
	 * 
	 * @param id
	 * @return
	 */
	public T get(PK id);

	public T get(String key, PK id);

	/**
	 * 
	 * @param params
	 * @return
	 */
	public T unique(Map<String, Object> params);

	public T unique(String key, Map<String, Object> params)
			throws BusinessException;

	/**
	 * 通过主键加载对象,默认实现
	 * 
	 * @param id
	 * @return
	 */

	public T load(PK id);

	public T load(String key, PK id);

	/**
	 * 更新实体对象,默认实现
	 * 
	 * @param entity
	 * @return
	 */
	public T update(T entity);

	public T update(String key, T entity);

	/**
	 * 删除实体类,默认实现
	 * 
	 * @param params
	 *            参数集合
	 * @return 操作记录条数
	 */
	public Integer delete(Map<String, Object> params);

	public Integer delete(String key, Map<String, Object> params);

	/**
	 * 根据params条件查询列表,默认实现
	 */
	public List<T> find(Map<String, Object> params);

	/**
	 * 根据params条件查询列表
	 */
	public List<T> find(String key, Map<String, Object> params);

	public void execute(String sql, final Map<String, Object> params);

	public int[] batchUpdate(String sql, final List<Map<String, Object>> lists);
}
