package com.idap.web.result.subscriptions.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.intextservice.result.subscriptions.entity.Content;
import com.idap.intextservice.result.subscriptions.entity.Subscribe;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅管理controller
 * @修改日志： ###################################################
 */

@Controller
@RequestMapping(value = "/subscribe")
public class SubscribeController extends BaseController<Subscribe, String> {
	@Resource(name = "subscribeService")
	public void setBaseService(IBaseService<Subscribe, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "subscribeService")
	public IBaseService<Subscribe, String> subscribeService;

	@Resource(name = "contentService")
	public IBaseService<Content, String> contentService;

	// 订阅信息管理新增
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> create(Subscribe subscribe) {
		Map<String, Object> results = Constants.MAP();
		this.subscribeService.save(subscribe);
		Content content = new Content();
		content.setSubscribeId(subscribe.getSubscribeId());
		content.setAnaThemeId(subscribe.getAnaThemeId());
		content.setAnaThemeRepId(subscribe.getAnaThemeRepId());
		this.contentService.save(content);
		return results;
	}
	
	// 订阅信息管理修改
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> update(Subscribe subscribe) {
		Map<String, Object> results = Constants.MAP();
		try {
			this.subscribeService.update(subscribe);
			String anaThemeId=subscribe.getAnaThemeId();
			if(null==anaThemeId||"".equals(anaThemeId)){
				
			}else{
				Content content = new Content();
				content.setId(subscribe.getId());
				content.setAnaThemeId(subscribe.getAnaThemeId());
				content.setAnaThemeRepId(subscribe.getAnaThemeRepId());
				this.contentService.update(content);
			}
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	// 订阅信息管理删除
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	protected Map<String, Object> remove(@RequestParam("params") String values) {
		Map<String, Object> results = Constants.MAP();
		try {
			this.contentService.delete(JsonUtils.toMap(values));
			this.subscribeService.delete(JsonUtils.toMap(values));
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
}
