package com.idp.pub.service;

import java.util.List;
import java.util.Map;

import com.idp.pub.entity.Pager;

public interface IBaseService<T, PK> {
	T save(T entity);

	T getById(PK id);

	T update(T entity);

	Integer delete(Map<String, Object> params);

	List<T> findList(Map<String, Object> params);

	Pager<T> findByPager(Pager<T> pager, Map<String, Object> params);

}