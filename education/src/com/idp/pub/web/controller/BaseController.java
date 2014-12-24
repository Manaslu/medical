package com.idp.pub.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.constants.Constants;
import com.idp.pub.entity.Pager;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.IBaseService;
import com.idp.pub.service.entity.IUser;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.utils.StringUtils;

/**
 * 
 * @author Snail
 * 
 */
public class BaseController<T, PK> {

	private IBaseService<T, PK> baseService;

	@Resource(name = "generateKeyServcie")
	private IGenerateKeyMangerService generateKeyService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> update(T entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			this.baseService.update(entity);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	protected Map<String, Object> create(T entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			this.baseService.save(entity);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	@RequestMapping(params = "paging=true", method = RequestMethod.GET)
	@ResponseBody
	protected Pager<T> findByPager(Pager<T> pager,
			@RequestParam("params") String values) {
		pager = this.getBaseService().findByPager(pager,
				JsonUtils.toMap(values));
		return pager;
	}

	@RequestMapping(params = "isArray=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<T> findByList(@RequestParam("params") String values) {
		return this.getBaseService().findList(JsonUtils.toMap(values));
	}

	protected IUser getUser(HttpServletRequest request) {
		return (IUser) request.getSession().getAttribute(Constants.USER_INFO);
	}

	/**
	 * 获取主键ID
	 * 
	 * @param prifix
	 * @return
	 * @throws BusinessException
	 */
	protected String buildId(String prifix) {
		String pk = "";
		try {
			pk = this.generateKeyService.getNextGeneratedKey(prifix)
					.getNextKey();
			if (StringUtils.isEmpty(pk)) {
				throw new RuntimeException(prifix + ",主键生成失败");
			}
		} catch (BusinessException e) {
			throw new RuntimeException(e.getMessage());
		}
		return pk;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	protected Map<String, Object> remove(@RequestParam("params") String values) {
		Map<String, Object> results = Constants.MAP();
		try {
			this.baseService.delete(JsonUtils.toMap(values));
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	public IBaseService<T, PK> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<T, PK> baseService) {
		this.baseService = baseService;
	}

	/*
	 * 已经转移到spring-mvc 配置文件中，此处已经作废 注册自定义数据类型转换器
	 * 
	 * @author panfei
	 * 
	 * @param binder
	 * 
	 * @throws Exception
	 * 
	 * @InitBinder public void initBinder(WebDataBinder binder) throws Exception
	 * { SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 * dateFormat.setLenient(false);
	 * 
	 * SimpleDateFormat datetimeFormat = new SimpleDateFormat(
	 * "yyyy-MM-dd HH:mm:ss"); datetimeFormat.setLenient(false);
	 * 
	 * binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
	 * dateFormat, true)); binder.registerCustomEditor(java.sql.Timestamp.class,
	 * new CustomTimestampEditor(datetimeFormat, true)); }
	 */
}
