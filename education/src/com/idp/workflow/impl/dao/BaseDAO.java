package com.idp.workflow.impl.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.idp.workflow.itf.dao.IDaoMapper;
import com.idp.workflow.itf.dao.SqlPartHandler;
import com.idp.workflow.util.common.StringUtil;

/**
 * 基础持久化基础实现
 * 
 * @author panfei
 * 
 */
public class BaseDAO implements IDaoMapper {

	private static final String deleteActionName = ".delete";
	private static final String updateActionName = ".update";
	private static final String insertActionName = ".insert";
	private static final String selectActionName = ".select";
	private static final String selectSqLActionName = ".selectbysql";

	protected SqlSession sqlSession;
	protected SqlSessionFactory dbSqlSessionFactory;

	public BaseDAO(SqlSessionFactory dbSqlSessionFactory) {
		this.dbSqlSessionFactory = dbSqlSessionFactory;
		// this.sqlSession = dbSqlSessionFactory.openSession();
		this.sqlSession = new SqlSessionTemplate(dbSqlSessionFactory);
	}

	@Override
	public <E> List<E> queryVOsByWherePart(Class<E> c, String wheresql) {
		List<E> retlist = new ArrayList<E>();
		if (!StringUtil.isEmpty(wheresql)) {
			if (wheresql.toLowerCase().contains("where")) {
				wheresql = wheresql.substring(wheresql.toLowerCase().indexOf(
						"where") + 5);
			}
		}
		fillListVOs(
				retlist,
				queryVOs(c.getName() + selectSqLActionName, new SqlPartHandler(
						wheresql)));
		return retlist;
	}

	@Override
	public <E> void insertVO(E obj) {
		String classname = "";
		if (obj != null) {
			classname = obj.getClass().getName();
		}
		this.sqlSession.insert(classname + insertActionName, obj);
	}

	@Override
	public <E> void updateVO(E obj) {
		String classname = "";
		if (obj != null) {
			classname = obj.getClass().getName();
		}
		this.sqlSession.update(classname + updateActionName, obj);
	}

	@Override
	public void deleteVO(Class<?> classname, String pk) {
		this.sqlSession.delete(classname.getName() + deleteActionName, pk);
	}

	@Override
	public void close() {
		this.sqlSession.close();
	}

	@Override
	public void commit() {
		this.sqlSession.commit();
	}

	@Override
	public void rollback() {
		this.sqlSession.rollback();
	}

	@Override
	public <E> List<E> queryVOs(E obj) {
		String classname = "";
		if (obj != null) {
			classname = obj.getClass().getName();
		}
		List<E> retlist = new ArrayList<E>();
		fillListVOs(retlist, queryVOs(classname + selectActionName, obj));
		return retlist;
	}

	@Override
	public List<?> queryVOs(String statement, Object parameter) {
		return sqlSession.selectList(statement, parameter);
	}

	@SuppressWarnings({ "unchecked" })
	private static <E> void fillListVOs(List<E> to, List<?> from) {
		if (from != null) {
			for (Object tempone : from) {
				to.add((E) tempone);
			}
		}
	}

	@Override
	public SqlSession getSqlSession() {
		return sqlSession;
	}
}
