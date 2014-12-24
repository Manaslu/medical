package com.idap.web.dataprocess.definition.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idp.pub.constants.Constants;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
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
@RequestMapping("/dataDefinitionAttr")
public class DataDefinitionAttrController extends BaseController<DataDefinitionAttr, String> {
    private static final Log logger = LogFactory.getLog(DataDefinitionAttrController.class);

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> create(DataDefinitionAttr entity) {
        Map<String, Object> results = Constants.MAP();
        try {
            entity.setId(buildId(null));
            entity.setColumnId(buildId(null));
            this.getBaseService().save(entity);
            results.put("id", entity.getId());
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
        }
        return results;
    }

    @RequestMapping(params = "isArray=true", method = RequestMethod.GET)
    @ResponseBody
    protected List<DataDefinitionAttr> findByList(@RequestParam("params") String values) {
    	List<DataDefinitionAttr> resultSort=new ArrayList<DataDefinitionAttr>();
        Map params = JsonUtils.toMap(values);
        String hasId = (String) params.get("hasId");
        if (StringUtils.isBlank(hasId) ) {// 去主键
            params.put("isDisplay", DataSetUtils.SWITCH_TRUE);
        }
        params.put("orderBy", "DISPLAY_COL_NUM");
        List<DataDefinitionAttr> result = this.getBaseService().findList(params);
       
        if (StringUtils.isNotBlank(hasId) ) {// 去主键
        	for(DataDefinitionAttr attr:result){
        		if(DataSetUtils.ID_COL_NAME.equals(attr.getColumnName())){
        			resultSort.add(attr);
        			result.remove(attr);
        			break;
        		}
        	}
        }
        resultSort.addAll(result);
        return resultSort;
    }

    // ========================= get/set ===========================

    @Resource(name = "dataDefinitionAttrService")
    public void setBaseService(IBaseService<DataDefinitionAttr, String> baseService) {
        super.setBaseService(baseService);
    }

    @Resource(name = "generateKeyServcie")
    private IGenerateKeyMangerService generateKeyService;

}