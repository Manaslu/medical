package com.idp.pub.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.util.Assert;

import com.idp.pub.constants.Constants;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.utils.ColumnAttribute;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.entity.GeneratedKeyHolder;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
 
/**
 * 
 * @author Raoxiaoyan
 *         <p>
 *         提供新增，修改，删除，查询，分页等基本操作功能
 *         <p>
 *         该类内置的key为,即在mybatis的配置所使用的key
 *         <p>
 *         getById : 通过主键ID查询的key
 *         <p>
 *         unique:
 *         <p>
 *         query : 查询列表的默认key
 *         <p>
 *         findByPager,findByPagerCount:分页查询的默认key
 *         <p>
 *         delete:删除实体所使用的key
 *         <p>
 *         insert:插入实体所使用的key
 *         <p>
 *         batchInsert:插入实体所使用的key
 *         <p>
 *         update:更新实体所使用的key
 *         <p>
 *         batchUPdate:批量更新时所使用的key
 * @param <T>
 *            实体对象
 * @param <PK>
 *            主键ID的类型
 */
public abstract class DefaultBaseDao<T, PK> extends SqlSessionDaoSupport
		implements IBaseDao<T, PK>, IPagerDao<T> {

	/**
	 * @since panfei add
	 */
	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;

	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	public static final String KEY_CONNECTOR = ".";

	public static final String KEY_GET_BY_ID = "getById";

	public static final String KEY_UNIQUE = "unique";

	public static final String KEY_QUERY = "query";

	public static final String KEY_FIND_BY_PAGER = "findByPager";

	public static final String KEY_FIND_BY_PAGER_COUNT = "findByPagerCount";

	public static final String KEY_DELETE = "delete";

	public static final String KEY_DELETE_BY_ID = "deleteById";

	public static final String KEY_INSERT = "insert";

	public static final String KEY_INSERT_BATCH = "batchInsert";

	public static final String KEY_UPDATE = "update";

	public static final String KEY_UPDATE_BATCH = "batchUpdate";

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	public T save(T entity) {
		this.prepareInsertPkValues(entity);
		this.save(KEY_INSERT, entity);
		return entity;
	}

	public T save(String key, T entity) {
		this.prepareInsertPkValues(entity);
		this.getSqlSession().insert(sqlKey(KEY_INSERT), entity);
		return entity;
	}

	public T get(PK id) {
		return get(KEY_GET_BY_ID, id);

	}

	@SuppressWarnings("unchecked")
	public T get(String key, PK id) {
		return (T) this.getSqlSession().selectOne(sqlKey(key), id);

	}

	public T unique(Map<String, Object> params) {
		return this.unique(KEY_UNIQUE, params);
	}

	public T unique(String key, Map<String, Object> params) {
		return this.getSqlSession().selectOne(this.sqlKey(key), params);
	}

	public T load(PK id) {
		return load(KEY_GET_BY_ID, id);
	}

	@SuppressWarnings("unchecked")
	public T load(String key, PK id) {
		return (T) this.getSqlSession().selectOne(sqlKey(key), id);
	}

	public T update(T entity) {
		update(KEY_UPDATE, entity);
		return entity;
	}

	@Override
	public T update(String key, T entity) {
		this.getSqlSession().update(this.sqlKey(key), entity);
		return entity;
	}

	public Integer delete(Map<String, Object> params) {
		return delete(KEY_DELETE, params);

	}

	public Integer delete(String key, Map<String, Object> params) {
		return this.getSqlSession().delete(this.sqlKey(key), params);

	}

	public List<T> find(Map<String, Object> params) {
		return find(KEY_QUERY, params);
	}

	public List<T> find(String key, Map<String, Object> params) {
		return this.getSqlSession().selectList(this.sqlKey(key), params);
	}

	public Pager<T> findByPager(Pager<T> pager, Map<String, Object> params) {
		return this.findByPager(KEY_FIND_BY_PAGER, pager, params);
	}

	public Pager<T> findByPager(String key, Pager<T> pager,
			Map<String, Object> params) {

		if (pager.isReload()) {// 如果总条数发生变化，请重新加载
			Map<String, Object> cparams = Constants.MAP();
			cparams.putAll(params);
			int total = (Integer) this.getSqlSession().selectOne(
					this.sqlKey(key + "Count"), cparams);
			pager.setTotal(total);
			pager.setReload(false);
		}
		if (pager.getCurrent() > pager.getTotalPage() && pager.getTotal() > 0) {
			pager.setCurrent(pager.getCurrent() - 1);
		}

		int sno = pager.getCurrent() * pager.getLimit() + 1;
		int eno = (pager.getCurrent() + 1) * pager.getLimit();

		params.put("sno", sno);// 查询开始条数
		params.put("eno", eno);// 查询结束条数

		List<T> dataList = this.getSqlSession().selectList(this.sqlKey(key),
				params);

		pager.setData(dataList);
		return pager;
	}

	public int getCount(String key) {

		return this.getSqlSession().selectOne(this.sqlKey(key));

	}

	public void execute(String sql, final Map<String, Object> param) {
		Assert.notNull(jdbcTemplate, "需要对JdbcTemplate的进行注入");
		this.jdbcTemplate.execute(sql, new PreparedStatementCallback<T>() {
			@Override
			public T doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				int parameterIndex = 0;
				for (Entry<String, Object> en : param.entrySet()) {
					ps.setObject(parameterIndex++, en.getValue());
				}
				return null;
			}
		});
	}

	public int[] batchUpdate(String sql, final List<Map<String, Object>> lists) {
		Assert.notNull(jdbcTemplate, "需要对JdbcTemplate的进行注入");
		return this.jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					@Override
					public int getBatchSize() {
						return lists.size();
					}

					@Override
					public void setValues(PreparedStatement ps, int index)
							throws SQLException {
						Map<String, Object> entity = lists.get(index);

						int parameterIndex = 0;
						for (Entry<String, Object> en : entity.entrySet()) {
							ps.setObject(parameterIndex++, en.getValue());
						}
					}
				});
	}

	public abstract String getNamespace();

	protected String sqlKey(String key) {
		return this.getNamespace().concat(KEY_CONNECTOR).concat(key);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 主键自动生成赋值策略实现方法
	 * 
	 * @param entity
	 * @since panfei add
	 */
	private void prepareInsertPkValues(T entity) {
		if (entity instanceof SmartEntity) {
			SmartEntity smartentity = (SmartEntity) entity;
			ColumnAttribute[] colattrs = smartentity.getPKFieldAttributes();
			if (colattrs != null) {
				for (ColumnAttribute colatr : colattrs) {
					if (com.idp.pub.entity.annotation.PrimaryKey.PK.useIDP
							.equals(colatr.getPkCreateType())) {
						try {
							GeneratedKeyHolder keyholder = this.generateKeyService
									.getNextGeneratedKey(smartentity
											.getEntityName());
							smartentity.setAttributeValue(
									colatr.getPropertyName(),
									keyholder.getNextKey());
						} catch (BusinessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
