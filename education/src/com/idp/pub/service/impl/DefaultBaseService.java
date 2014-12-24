package com.idp.pub.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;

/**
 * DefaultBaseService业务类的默认实现
 * <p>
 * 包括：1. 新增加一个业务实体类方法（save）
 * <p>
 * 2. 通过主键Id获取业务实体方法(getById)
 * <p>
 * 3. 更新一个业务实体方法(update)
 * <p>
 * 4. 通过参数删除业务实体(delete)
 * <p>
 * 5. 简单查询一个列表(findList)
 * <p>
 * 6. 默认的分页查询(findByPager)
 * 
 * @author Raoxiaoyan
 * 
 * @param <T>
 * @param <PK>
 */
public abstract class DefaultBaseService<T, PK> implements IBaseService<T, PK> {

	private IBaseDao<T, PK> baseDao;

	private IPagerDao<T> pagerDao;

	@Override
	public T save(T entity) {
		Assert.notNull(baseDao, "必须设置基础接口IBaseDao的实现类");
		T ent = this.baseDao.save(entity);
		return ent;
	}

	@Override
	public T getById(PK id) {
		Assert.notNull(baseDao, "必须设置基础接口IBaseDao的实现类");
		return this.baseDao.get(id);
	}

	@Override
	public T update(T entity) {
		Assert.notNull(baseDao, "必须设置基础接口IBaseDao的实现类");
		return this.baseDao.update(entity);
	}

	@Override
	public Integer delete(Map<String, Object> params) {
		Assert.notNull(baseDao, "必须设置基础接口IBaseDao的实现类");
		return this.baseDao.delete(params);
	}

	@Override
	public List<T> findList(Map<String, Object> params) {
		Assert.notNull(baseDao, "必须设置基础接口IBaseDao的实现类");
		return this.baseDao.find(params);
	}

	@Override
	public Pager<T> findByPager(Pager<T> pager, Map<String, Object> params) {
		Assert.notNull(pagerDao, "必须设置基础分页接口IPagerDao的实现类");
		return this.pagerDao.findByPager(pager, params);
	}

	public IBaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	public IPagerDao<T> getPagerDao() {
		return pagerDao;
	}

	public void setPagerDao(IPagerDao<T> pagerDao) {
		this.pagerDao = pagerDao;
	}

}
