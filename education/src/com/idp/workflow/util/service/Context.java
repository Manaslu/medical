package com.idp.workflow.util.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;

import com.idp.workflow.event.action.ActionEventDispatcher;
import com.idp.workflow.itf.dao.DaoMapperCreater;
import com.idp.workflow.itf.dao.IDaoMapper;
import com.idp.workflow.itf.service.ILocater;

/**
 * 全局变量
 * 
 * @author panfei
 * 
 */
public class Context {

	private static Map<String, Object> cachemap = new HashMap<String, Object>();

	private static ThreadLocal<IDaoMapper> localcache = new ThreadLocal<IDaoMapper>();

	public static void putSources(Class<?> cl, Object obj) {
		putSources(cl.getName(), obj);
	}

	public static void putSources(String key, Object obj) {
		cachemap.put(key, obj);
	}

	public static Object getSources(String key) {
		return cachemap.get(key);
	}

	@SuppressWarnings("unchecked")
	public static <E> E getSources(Class<E> cl) {
		return (E) getSources(cl.getName());
	}

	public static void buildMapperCreater(SqlSessionFactory dbSqlSessionFactory) {
		putSources(DaoMapperCreater.class, new DaoMapperCreater(
				dbSqlSessionFactory));
	}

	public static IDaoMapper createBaseDaoMapper() {
		if (localcache.get() != null) {
			return localcache.get();
		}
		IDaoMapper mapper = Context.getSources(DaoMapperCreater.class)
				.createDaoMapper();
		localcache.set(mapper);
		return mapper;
	}

	public static ILocater getILocater() {
		return LocaterServiceFactory.GetInstance();
	}

	public static ActionEventDispatcher getActionEventDispatcher() {
		return ActionEventDispatcher.getInstance();
	}
}
