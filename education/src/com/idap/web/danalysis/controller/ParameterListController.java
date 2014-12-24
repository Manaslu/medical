package com.idap.web.danalysis.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.constants.Constants;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import com.idap.danalysis.entity.ParameterList;
import com.idap.danalysis.service.IParameterList;
import com.idap.dataprocess.assess.entity.DataSetContour;
import com.idap.dataprocess.assess.service.AssessService;
import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.web.dataprocess.assess.controller.AssessController;

@Controller
@RequestMapping(value = "/parameterList")
public class ParameterListController extends BaseController<ParameterList, String> {
	 private static final Log logger = LogFactory.getLog(AssessController.class);
	@Resource(name = "parameterListService")
	public void setBaseService(IBaseService<ParameterList, String> baseService) {
		super.setBaseService(baseService);
	}

	
    /**
     * @创建日期：2014-6-21 12:01:15
     * @开发人员：王威
     * @功能描述：执行数据分析存储过程
     */
    @RequestMapping(method = RequestMethod.POST, params = "execute=1")
    @ResponseBody
    @SuppressWarnings("unused")
    public void execute(@RequestParam String excuteThemeid) {
        Map<String, Object> results = Constants.MAP();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("excuteThemeid", excuteThemeid);
        try {
              this.parameterListService.executeDataAnasis(excuteThemeid);
        } catch (Exception e) {
            logger.error(e);
        }
        
    }
    
//    @Resource(name = "parameterListService")
//    public void setBaseService(IBaseService<Map, String> baseService) {
//        super.setBaseService(baseService);
//    } 

    @Resource(name = "parameterListService")
    private IParameterList parameterListService;
    
     
}
