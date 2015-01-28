package com.idap.web.clinic.controller;
 
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.idp.pub.constants.Constants;
import com.idp.pub.generatekey.entity.GeneratedKeyHolder;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;

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
	
    
	
	
	

}
