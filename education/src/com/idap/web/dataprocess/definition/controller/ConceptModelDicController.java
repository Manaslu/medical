package com.idap.web.dataprocess.definition.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.dataprocess.definition.entity.ConceptModelDic;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

/**
 * @###################################################
 * @功能描述：
 * @开发人员：李广彬
 * @创建日期：2014-4-24 11:12:46
 * @修改日志：
 * @###################################################
 */
@Controller
@RequestMapping("/conceptModelDic")
public class ConceptModelDicController extends BaseController<ConceptModelDic, String> {
    private static final Log logger = LogFactory.getLog(ConceptModelDicController.class);

    @Resource(name = "conceptModelDicService")
    public void setBaseService(IBaseService<ConceptModelDic, String> baseService) {
        super.setBaseService(baseService);
    }

    // ========================= get/set ===========================

}