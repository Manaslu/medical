package com.idap.web.dataprocess.definition.controller;

import java.util.Date;
import java.util.HashMap;
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

import com.idap.dataprocess.dataset.service.DataSetService;
import com.idap.dataprocess.definition.entity.DataDefinition;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idap.dataprocess.definition.service.DataDefinitionService;
import com.idp.pub.constants.Constants;
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
@RequestMapping("/dataDefinition")
public class DataDefinitionController extends BaseController<DataDefinition, String> {
    private static final Log logger = LogFactory.getLog(DataDefinitionController.class);

    /**
     * @功能描述：保存 生成数据定义表名
     * @创建日期：2014-5-22 14:17:27
     * @开发人员：李广彬
     * @传入参数：
     */
    @RequestMapping(method = RequestMethod.PUT,params="optType=saveAll")
    @ResponseBody
    public Map<String, Object> saveAll(@RequestParam("params") String values) {
        Map<String, Object> results = Constants.MAP();
        DataDefinition entity=(DataDefinition) JsonUtils.jsonToEntity(values, DataDefinition.class);
        try {
            entity.setDataDefId(buildId(null));
            entity.setGroupId(buildId(null));
            entity.setCreateDate(new Date());
            for(DataDefinitionAttr col:entity.getColumns()){
                col.setColumnId(buildId(null));
                col.setId(buildId(null));
                col.setDataDefId(entity.getDataDefId());
            }
            this.definitionService.saveDefinitionCascade(entity);
            results.put("id", entity.getId());
            results.put("dataDefId", entity.getDataDefId());
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
            logger.error(e);
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
        }
        return results;
    }
    
    @RequestMapping(method = RequestMethod.POST,params="optType=updateAll")
    @ResponseBody
    protected Map<String, Object> updateAll(@RequestParam("params") String values) {
        Map<String, Object> results = Constants.MAP();
        String errorCode=null;
        DataDefinition entity=(DataDefinition) JsonUtils.jsonToEntity(values, DataDefinition.class);
        try {
            //1.
            if(StringUtils.isBlank(entity.getDataDefId())){
                throw new RuntimeException("更新数据定义的主键不可为空");
            }
            Map<String, Object> attrParmas = new HashMap<String, Object>();
            attrParmas.put("dataDefId",entity.getDataDefId());
            Integer dsCount=this.dsService.count(attrParmas);
            if(dsCount>0){
                errorCode="001";
                throw new RuntimeException("数据集已被使用");
            }
            
            for(DataDefinitionAttr col:entity.getColumns()){
                col.setColumnId(buildId(null));
                col.setId(buildId(null));
                col.setDataDefId(entity.getDataDefId());
            }
            this.definitionService.updateDefinitionCascade(entity);
            results.put("id", entity.getId());
            results.put("dataDefId", entity.getDataDefId());
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
            logger.error(e);
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
            if(errorCode.equals("001")){
                results.put(Constants.MESSAGE, "数据集已被使用");
            }
            
        }
        return results;
    }

    /**
     * @功能描述：删除 删除前确认无关联的数据集
     * @创建日期：2014-5-22 14:17:27
     * @开发人员：李广彬
     * @传入参数：
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    protected Map<String, Object> remove(@RequestParam("params") String values) {
        // values: {"dataDefId":"K674da1c247014ab3837"}
        Map<String, Object> results = Constants.MAP();
        try {
            Integer count = this.dsService.count(JsonUtils.toMap(values));
            if (count != null && count == 0) {
                this.getBaseService().delete(JsonUtils.toMap(values));
            } else {
                results.put("megCode", "1");// 存在数据集不能
            }
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
        }
        return results;
    }

    /**
     * @功能描述：查看是否存在相同的数据集
     * @创建日期：2014-5-22 14:17:27
     * @开发人员：李广彬
     * @传入参数：
     */
    @RequestMapping(method = RequestMethod.POST, params = "valid=1")
    @ResponseBody
    protected Map<String, Object> eidtValid(String dataDefId) {
        // values: {"dataDefId":"K674da1c247014ab3837"}
        Map<String, Object> results = Constants.MAP();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataDefId", dataDefId);
        try {
            results.put("megCode", "0");// 存在数据集不能
            Integer count = this.dsService.count(params);
            if (count > 0) {
                results.put("megCode", "1");// 存在数据集不能
            }
            results.put(Constants.SUCCESS, Constants.TRUE);
        } catch (Exception e) {
            results.put(Constants.SUCCESS, Constants.FALSE);
            results.put(Constants.MESSAGE, e.getMessage());
            logger.error(e);
        }
        return results;
    }

    // ========================= get/set ===========================
    @Resource(name = "dataSetService")
    private DataSetService dsService;
    
    @Resource(name = "dataDefinitionService")
    private DataDefinitionService definitionService;
    @Resource(name = "dataDefinitionService")
    public void setBaseService(IBaseService<DataDefinition, String> baseService) {
        super.setBaseService(baseService);
    }
}