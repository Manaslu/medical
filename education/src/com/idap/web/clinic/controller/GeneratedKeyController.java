package com.idap.web.clinic.controller;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.rule.entity.ColumnInteRule;
import com.idap.dataprocess.rule.entity.RuleType;
import com.idap.dataprocess.rule.entity.TableInteRule;
import com.idp.pub.constants.Constants;
import com.idp.pub.generatekey.entity.GeneratedKeyHolder;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.utils.JsonUtils;

@Controller
@RequestMapping(value = "/generatedKey")  
public class GeneratedKeyController  {
 

	@Resource(name = "generateKeyServcie")
	public IGenerateKeyMangerService generateKeyServcie;
	
	@RequestMapping(params = "method=genKey", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> generateKey() {
		GeneratedKeyHolder keyHolder = generateKeyServcie.getNextGeneratedKey("");
		Map<String, Object> results = Constants.MAP();
		results.put("newId", keyHolder.getNextKey());
		return results;
	}
	
	
    @RequestMapping(method = RequestMethod.POST, params = "optype=genKeyN")
    @ResponseBody
    public Map<String, Object> generateKeyN(@RequestParam int n) {
		
		Map<String, Object> results = Constants.MAP();
		String res="";
		for(int i =0;i<n;i++){
			GeneratedKeyHolder keyHolder = generateKeyServcie.getNextGeneratedKey(null);
			res=res+keyHolder.getNextKey();
			if(i!=n-1){
				res=res+",";
			}
		}
		results.put("newIds", res);
		return results;
	}
	
 
	
    
	
	
	

}
