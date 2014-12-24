package com.idp.workflow.itf.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

/**
 * 数据访问层
 * 
 * @author panfei
 * 
 */
public interface IDaoMapper {

	/**
	 * 根据条件SQL查询
	 * 
	 * @param c
	 * @param wheresql
	 * @return
	 */
	<E> List<E> queryVOsByWherePart(Class<E> c, String wheresql);

	/**
	 * 根据条件VO查询
	 * 
	 * @param obj
	 * @return
	 */
	<E> List<E> queryVOs(E obj);

	/**
	 * 原生态查询方法
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	List<?> queryVOs(String statement, Object parameter);

	/**
	 * 新增VO
	 * 
	 * @param obj
	 */
	<E> void insertVO(E obj);

	/**
	 * 更新VO
	 * 
	 * @param obj
	 */
	<E> void updateVO(E obj);

	/**
	 * 根据主键删除VO
	 * 
	 * @param 主键
	 */
	void deleteVO(Class<?> classname, String pk);

	/**
	 * 关闭资源
	 */
	void close();

	/**
	 * 提交事务
	 */
	void commit();

	/**
	 * 回滚事务
	 */
	void rollback();

	/**
	 * 实际session
	 * 
	 * @return
	 */
	SqlSession getSqlSession();

}
