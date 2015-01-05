package com.idp.pub.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.BaseEntity;
import com.idp.pub.entity.Pager;
import com.idp.pub.entity.SmartEntity;
import com.idp.pub.entity.utils.ColumnAttribute;
import com.idp.pub.entity.utils.DataAttribute;
import com.idp.pub.entity.utils.SQLAdapter;
import com.idp.pub.entity.utils.SqlBuilder;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.entity.GeneratedKeyHolder;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.sqlresolver.entity.PageParams;
import com.idp.pub.sqlresolver.service.ISqlPartResolverService;
import com.idp.pub.utils.StringUtils;

/**
 * 智能数据访问层，实现根据实体属性读写数据库 必须和SmartEntity搭配使用
 * 
 * @see SmartEntity
 * @author panfei
 * 
 */
public abstract class SmartBaseDao<T extends BaseEntity> extends
		SqlSessionDaoSupport implements IBaseDao<T, String>, IPagerDao<T>,
		InitializingBean {

	private static final String deleteActionName = ".delete_original";
	private static final String updateActionName = ".update_original";
	private static final String insertActionName = ".insert_original";
	private static final String selectActionName = ".select_original";

	private static final String nameSpace = "com.idp.pub.entity.SuperEntity";

	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;

	@Resource(name = "sqlResolverService")
	private ISqlPartResolverService sqlPartResolverService;

	public SmartBaseDao() {
		this.setMybatisNamespace(this.getUserDefineNamespace());
	}

	protected String mybatisNamespace;

	private DefaultBaseDao<T, String> defBaseDao = new DefaultBaseDao<T, String>() {
		@Override
		public String getNamespace() {
			return getMybatisNamespace();
		}
	};

	public String getMybatisNamespace() {
		return mybatisNamespace;
	}

	public void setMybatisNamespace(String namespace) {
		this.mybatisNamespace = namespace;
	}

	private boolean useSmartStrategy = true;

	public boolean isUseSmartStrategy() {
		return useSmartStrategy;
	}

	public void setUseSmartStrategy(boolean useSmartStrategy) {
		this.useSmartStrategy = useSmartStrategy;
	}

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
		this.defBaseDao.setSqlSessionFactory(sqlSessionFactory);
	}

	@Resource(name = "jdbcTemplate")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.defBaseDao.setJdbcTemplate(jdbcTemplate);
	}

	/**
	 * 用户自定义命名空间
	 * 
	 * @return
	 */
	public abstract String getUserDefineNamespace();

	/**
	 * 获取数据库厂商名称
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDataBaseProductName() throws BusinessException {
		try {
			return this.getSqlSession().getConfiguration().getEnvironment()
					.getDataSource().getConnection().getMetaData()
					.getDatabaseProductName();
		} catch (SQLException e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * 获取实体范型类型
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getEntityClassType() {
		Type genType = this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return ((Class<T>) params[0]);
	}

	/**
	 * 获取主键范型类型
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public Class<T> getPkClassType() {
		Type genType = this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return ((Class<T>) params[1]);
	}

	/**
	 * 原生态更新查询语句
	 * 
	 * @param sqlPart
	 */
	protected int updateSql(String sqlPart) {
		return this.getSqlSession().update(nameSpace + updateActionName,
				new SQLAdapter(sqlPart));
	}

	/**
	 * 原生态数据插入
	 * 
	 * @param sqlPart
	 * @return
	 */
	protected int insertSql(String sqlPart) {
		return this.getSqlSession().insert(nameSpace + insertActionName,
				new SQLAdapter(sqlPart));
	}

	/**
	 * 原生态数据删除
	 * 
	 * @param sqlPart
	 * @return
	 */
	protected int deleteSql(String sqlPart) {
		return this.getSqlSession().delete(nameSpace + deleteActionName,
				new SQLAdapter(sqlPart));
	}

	/**
	 * 原生态查询语句
	 * 
	 * @param sqlPart
	 * @return
	 */
	protected List<Map<String, Object>> selectSql(String sqlPart) {
		return this.getSqlSession().selectList(nameSpace + selectActionName,
				new SQLAdapter(sqlPart));
	}

	/**
	 * 刷新时间戳
	 * 
	 * @param supervo
	 */
	private void refreshTimeStamp(BaseEntity supervo) {
		if (supervo != null) {
			SimpleDateFormat formater = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			supervo.setTimeStamp(formater.format(new Date()));
		}
	}

	/**
	 * 获取元数据属性共用方法
	 * 
	 * @param pksmap
	 * @param supervo
	 * @throws BusinessException
	 */
	private String fillDataAttributeMap(Map<String, DataAttribute> pksmap,
			BaseEntity supervo) throws BusinessException {
		String TableName = null;
		TableName = supervo.getTableName();
		ColumnAttribute[] pknames = supervo.getPKFieldAttributes();
		for (int i = 0; i < pknames.length; i++) {
			DataAttribute dataatr = new DataAttribute(
					supervo.getAttributeValue(pknames[i].getPropertyName()));
			pksmap.put(pknames[i].getName(), dataatr);
		}
		return TableName;
	}

	/**
	 * 删除业务VO实体
	 * 
	 * @param supervo
	 */
	public void deleteBaseEntity(BaseEntity supervo) throws BusinessException {
		Map<String, DataAttribute> pksMap = new HashMap<String, DataAttribute>();
		String TableName = this.fillDataAttributeMap(pksMap, supervo);
		String delSql = SqlBuilder.createDeleteSql(TableName, pksMap);
		this.deleteSql(delSql);
	}

	/**
	 * 插入业务VO实体 可以设置主键生成策略
	 * 
	 * @param supervo
	 * @param isAuto
	 *            为false必须由数据库表自动生成，为true若主键为空由外部服务生成
	 * @throws BusinessException
	 */
	public void insertBaseEntity(BaseEntity supervo) throws BusinessException {

		String TableName = null;
		String[] keynames = null;

		TableName = supervo.getTableName();
		keynames = supervo.getPKFieldNames();
		// 填充主键
		boolean isAuto = this.prepareInsertPkValues(supervo);
		// 填充时间戳
		this.refreshTimeStamp(supervo);

		Map<String, DataAttribute> colsMap = new HashMap<String, DataAttribute>();
		// String[] colnames = supervo.getAttributeNames();
		ColumnAttribute[] colattrs = supervo.findColFieldAttributes();

		for (ColumnAttribute tmp : colattrs) {
			if (supervo.getAttributeValue(tmp.getPropertyName()) != null) {
				DataAttribute coldata = new DataAttribute(
						supervo.getAttributeValue(tmp.getPropertyName()));
				colsMap.put(tmp.getName(), coldata);
			}
		}
		// 忽略列名
		String[] ignoreCols = null;
		if (!isAuto) {
			ignoreCols = keynames;
		}
		// 构建插入语句
		String insertSql = SqlBuilder.createInsertSql(TableName, colsMap,
				ignoreCols);
		this.insertSql(insertSql);
	}

	/**
	 * 修改更新业务VO实体
	 * 
	 * @param supervo
	 * @throws BusinessException
	 */
	public void updateBaseEntity(BaseEntity supervo) throws BusinessException {

		Map<String, DataAttribute> pksmap = new HashMap<String, DataAttribute>();
		String TableName = this.fillDataAttributeMap(pksmap, supervo);
		this.refreshTimeStamp(supervo);

		Map<String, DataAttribute> colsmap = new HashMap<String, DataAttribute>();
		// String[] colnames = supervo.getAttributeNames();
		ColumnAttribute[] colattrs = supervo.findColFieldAttributes();

		for (ColumnAttribute tmp : colattrs) {
			if (supervo.getAttributeValue(tmp.getPropertyName()) != null) {
				DataAttribute coldata = new DataAttribute(
						supervo.getAttributeValue(tmp.getPropertyName()));
				colsmap.put(tmp.getName(), coldata);
			}
		}

		String updatesql = SqlBuilder.createUpdateSql(TableName, colsmap,
				SqlBuilder.createWherePksSql(pksmap),
				pksmap.keySet().toArray(new String[pksmap.keySet().size()]));
		this.updateSql(updatesql);
	}

	/**
	 * 建立
	 * 
	 * @param colNameList
	 * @param colRelationMap
	 */
	private String[] fillColumnRelation(ColumnAttribute[] colattrs,
			Map<String, String> colRelationMap) {
		if (colattrs == null) {
			throw new BusinessException("column property is null");
		}

		String[] colNamesArr = new String[colattrs.length];
		for (int i = 0; i < colattrs.length; i++) {
			colNamesArr[i] = colattrs[i].getName();
			colRelationMap.put(colattrs[i].getName(),
					colattrs[i].getPropertyName());
		}

		return colNamesArr;
	}

	/**
	 * 根据VO实体主键查询业务VO
	 * 
	 * @param supervo
	 * @return
	 * @throws BusinessException
	 */
	public <E extends BaseEntity> E selectBaseEntityByPk(Class<E> classType,
			String... pk_vos) throws BusinessException {

		E supervo = null;
		try {
			supervo = classType.newInstance();
		} catch (InstantiationException e) {
			throw new BusinessException(e);
		} catch (IllegalAccessException e) {
			throw new BusinessException(e);
		}

		Map<String, DataAttribute> pksmap = new HashMap<String, DataAttribute>();
		supervo.setPrimaryKeys(pk_vos);
		String TableName = this.fillDataAttributeMap(pksmap, supervo);
		ColumnAttribute[] colattrs = supervo.findColFieldAttributes();
		Map<String, String> colRelationMap = new HashMap<String, String>();
		String[] colNames = this.fillColumnRelation(colattrs, colRelationMap);

		String selectsql = SqlBuilder.createSelectSql(TableName, colNames,
				SqlBuilder.createWherePksSql(pksmap));

		List<Map<String, Object>> result = this.selectSql(selectsql);

		if (result != null && result.size() > 0) {

			if (result.size() > 1) {
				throw new BusinessException("根据主键查询的VO不唯一！");
			}

			Set<String> keys = result.get(0).keySet();
			E tmpSupervo = null;
			try {
				tmpSupervo = classType.newInstance();
			} catch (InstantiationException e) {
				throw new BusinessException(e);
			} catch (IllegalAccessException e) {
				throw new BusinessException(e);
			}
			for (String tmpkey : keys) {
				tmpSupervo.setAttributeValue(colRelationMap.get(tmpkey), result
						.get(0).get(tmpkey));
			}
			return tmpSupervo;
		}
		return null;
	}

	/**
	 * 支持分页、列排序的自定义条件查询
	 * 
	 * @param classType
	 * @param wherePart
	 *            自定义where条件
	 * @param orderPart
	 *            自定义order by条件
	 * @param page
	 *            分页信息
	 * @return
	 * @throws BusinessException
	 */
	public <E extends BaseEntity> List<E> selectBaseEntityByCondition(
			Class<E> classType, String wherePart, String orderPart,
			Pager<E> page) throws BusinessException {
		E supervo = null;
		try {
			supervo = classType.newInstance();
		} catch (InstantiationException e) {
			throw new BusinessException(e);
		} catch (IllegalAccessException e) {
			throw new BusinessException(e);
		}
		String TableName = supervo.getTableName();
		ColumnAttribute[] colattrs = supervo.findColFieldAttributes();
		Map<String, String> colRelationMap = new HashMap<String, String>();
		String[] colNames = this.fillColumnRelation(colattrs, colRelationMap);

		PageParams params = new PageParams();
		params.setTableName(TableName);
		params.setColNames(colNames);
		params.setWherePartStr(wherePart);
		params.setOrderPartStr(orderPart);

		if (page != null && (page.isReload() || page.getTotal() == 0)) {
			StringBuilder countsqlwhere = new StringBuilder();
			countsqlwhere.append(wherePart);
			countsqlwhere.append(" ");
			countsqlwhere.append(orderPart);
			String pageTotalSql = SqlBuilder.createSelectCountSql(TableName,
					countsqlwhere.toString());
			List<Map<String, Object>> totalRows = this.selectSql(pageTotalSql);
			if (totalRows != null && totalRows.size() > 0) {
				page.setTotal(Integer.valueOf(totalRows.get(0).get(Pager.TOTAL)
						.toString()));
			}
			page.setReload(false);
		}

		List<E> retlist = new ArrayList<E>();
		String selectsql = sqlPartResolverService.createPageQuerySql(page,
				params);

		List<Map<String, Object>> result = this.selectSql(selectsql);
		if (result != null && result.size() > 0) {
			for (Map<String, Object> tmpmap : result) {
				Set<String> keys = tmpmap.keySet();
				E tmpSupervo = null;
				try {
					tmpSupervo = classType.newInstance();
				} catch (InstantiationException e) {
					throw new BusinessException(e);
				} catch (IllegalAccessException e) {
					throw new BusinessException(e);
				}
				for (String tmpkey : keys) {
					tmpSupervo.setAttributeValue(colRelationMap.get(tmpkey),
							tmpmap.get(tmpkey));
				}
				retlist.add(tmpSupervo);
			}
		}
		return retlist;
	}

	/**
	 * 支持分页查询的自定义条件查询
	 * 
	 * @param classType
	 *            返回结果类型
	 * @param wherePart
	 *            自定义where条件
	 * @param page
	 *            分页信息
	 * @return
	 * @throws BusinessException
	 */
	public <E extends BaseEntity> List<E> selectBaseEntityByCondition(
			Class<E> classType, String wherePart, Pager<E> page)
			throws BusinessException {
		return this.selectBaseEntityByCondition(classType, wherePart, page, -1,
				"");
	}

	/**
	 * 支持分页、列排序的自定义条件查询
	 * 
	 * @param classType
	 * @param wherePart
	 *            自定义where条件
	 * @param page
	 *            分页信息
	 * @param orderType
	 *            排序方式
	 * @param orderName
	 *            排序列名
	 * @return
	 * @throws BusinessException
	 */
	public <E extends BaseEntity> List<E> selectBaseEntityByCondition(
			Class<E> classType, String wherePart, Pager<E> page, int orderType,
			String... orderName) throws BusinessException {
		String orderPartStr = SqlBuilder.createOrderBySql(orderName, orderType);
		return this.selectBaseEntityByCondition(classType, wherePart,
				orderPartStr, page);
	}

	@Override
	public T save(T entity) {
		if (this.isUseSmartStrategy()) {
			if (entity instanceof BaseEntity) {
				try {
					this.insertBaseEntity((BaseEntity) entity);
				} catch (BusinessException e) {
					throw new RuntimeException(e);
				}
				return entity;
			}
		}
		return this.defBaseDao.save(entity);
	}

	@Override
	public T save(String key, T entity) {
		return this.defBaseDao.save(key, entity);
	}

	@Override
	public T get(String id) {
		if (this.isUseSmartStrategy()) {
			Class<? extends BaseEntity> entityclass = (Class<? extends BaseEntity>) this
					.getEntityClassType();
			try {
				this.selectBaseEntityByPk(entityclass, id);
			} catch (BusinessException e) {
				throw new RuntimeException(e);
			}
		}
		return this.defBaseDao.get(id);
	}

	@Override
	public T get(String key, String id) {
		return this.defBaseDao.get(key, id);
	}

	@Override
	public T unique(Map<String, Object> params) {
		return this.defBaseDao.unique(params);
	}

	@Override
	public T unique(String key, Map<String, Object> params) {
		return this.defBaseDao.unique(key, params);
	}

	@Override
	public T load(String id) {
		return this.defBaseDao.load(id);
	}

	@Override
	public T load(String key, String id) {
		return this.defBaseDao.load(key, id);
	}

	@Override
	public T update(T entity) {
		if (this.isUseSmartStrategy()) {
			if (entity instanceof BaseEntity) {
				try {
					this.updateBaseEntity((BaseEntity) entity);
				} catch (BusinessException e) {
					throw new RuntimeException(e);
				}
				return entity;
			}
		}
		return this.defBaseDao.update(entity);
	}

	@Override
	public T update(String key, T entity) {
		return this.defBaseDao.update(key, entity);
	}

	@Override
	public Integer delete(Map<String, Object> params) {
		return this.defBaseDao.delete(params);
	}

	@Override
	public Integer delete(String key, Map<String, Object> params) {
		return this.defBaseDao.delete(key, params);
	}

	@Override
	public List<T> find(Map<String, Object> params) {
		return this.defBaseDao.find(params);
	}

	@Override
	public List<T> find(String key, Map<String, Object> params) {
		return this.defBaseDao.find(key, params);
	}

	@Override
	public void execute(String sql, Map<String, Object> params) {
		this.defBaseDao.execute(sql, params);
	}

	@Override
	public int[] batchUpdate(String sql, List<Map<String, Object>> lists) {
		return this.defBaseDao.batchUpdate(sql, lists);
	}

	@Override
	public Pager<T> findByPager(Pager<T> pager, Map<String, Object> params) {
		return this.defBaseDao.findByPager(pager, params);
	}

	@Override
	public Pager<T> findByPager(String key, Pager<T> pager,
			Map<String, Object> params) {
		return this.defBaseDao.findByPager(pager, params);
	}

	/**
	 * 主键自动生成赋值策略实现方法
	 * 
	 * @param entity
	 * @since panfei add
	 */
	private boolean prepareInsertPkValues(BaseEntity entity) {
		boolean isAutoPk = false;
		if (entity instanceof SmartEntity) {
			SmartEntity smartentity = (SmartEntity) entity;
			ColumnAttribute[] colattrs = smartentity.getPKFieldAttributes();
			if (colattrs != null) {
				for (ColumnAttribute colatr : colattrs) {
					if (com.idp.pub.entity.annotation.PrimaryKey.PK.useIDP
							.equals(colatr.getPkCreateType())) {
						isAutoPk = true;
						if (!StringUtils.isEmpty(smartentity
								.getAttributeValue(colatr.getPropertyName()))) {
							continue;
						}
						GeneratedKeyHolder keyholder = this.generateKeyService
								.getNextGeneratedKey(smartentity
										.getEntityName());
						smartentity.setAttributeValue(colatr.getPropertyName(),
								keyholder.getNextKey());
					}
				}
			}
		}
		return isAutoPk;
	}
}
