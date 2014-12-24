package com.idap.dataprocess.rule.service.handler.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.idap.dataprocess.addrmatch.service.MatchFileService;
import com.idap.dataprocess.addrmatch.service.impl.AddrMatchMonitor;
import com.idap.dataprocess.addrmatch.service.impl.AddresMatchServer;
import com.idap.dataprocess.rule.dao.DataInteLogDao;
import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.utils.JsonUtils;

@Service("addressRuleHandler")
public class AddressRuleHandler extends DefaultRuleHanlder {
	private Log logger = LogFactory.getLog(getClass());

	@Resource(name = "matchFileService")
	private MatchFileService matchFileService;

	@Resource(name = "addresMatchServer")
	private AddresMatchServer addresMatchServer;

	@Resource(name = "tableInteRuleDao")
	private IBaseDao<TableInteRule, String> tableInteRuleDao;

	@Resource(name = "dataInteLogDao")
	private DataInteLogDao dataInteLogDao;

	@Override
	public RuleType getRuleType() {
		return RuleType.ADDRESS_RULE;
	}

	@Override
	public synchronized void execute() throws BusinessException {
		logger.info("数据整合地址匹配规则开始执行,start...");
		logger.info("参数列表,{ruleId:" + ruleId + "}");
		TableInteRule inteRule = this.tableInteRuleDao.get(this.ruleId);
		inteRule.setInteLog(this.dataInteLogDao.findByRuleId(inteRule
				.getRuleId()));
		JsonUtils.toJson(inteRule);
		AddrMatchMonitor matchMonitor = new AddrMatchMonitor();
		matchMonitor.setMatchFileService(matchFileService);
		matchMonitor.setAddresMatchServer(addresMatchServer);
		matchMonitor.setInteRule(inteRule);
		matchMonitor.execute();
	}
}