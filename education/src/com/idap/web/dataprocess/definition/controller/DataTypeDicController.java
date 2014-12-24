package com.idap.web.dataprocess.definition.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.dataprocess.definition.entity.DataTypeDic;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

/**
 * @###################################################
 * @功能描述：
 * @开发人员：李广彬
 * @创建日期：2014-4-24 11:12:47
 * @修改日志：
 * @###################################################
 */
@Controller
@RequestMapping("/dataTypeDic")
public class DataTypeDicController extends BaseController<DataTypeDic, String> {
    private static final Log logger = LogFactory.getLog(DataTypeDicController.class);

    @Resource(name = "dataTypeDicService")
    public void setBaseService(IBaseService<DataTypeDic, String> baseService) {
        super.setBaseService(baseService);
    }

    // ========================= get/set ===========================

}