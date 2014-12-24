package com.idp.pub.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.idp.pub.constants.Constants;

public abstract class JsonUtils {

	/**
	 * 将json的字符串转换为map
	 * 
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String values) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> results = Constants.MAP();
		try {
			Map<String, Object> tmps = mapper.readValue(values, Map.class);
			// 清除值为空的参数
			for (Entry<String, Object> en : tmps.entrySet()) {
				if (!StringUtils.isEmpty(en.getValue())) {
					results.put(en.getKey(), en.getValue());
				}
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * 将实体对象转为json的字符串
	 * 
	 * @param entity
	 * @return
	 */
	public static String toJson(Object entity) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(entity);
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将实体对象的属性转为key-value的键值对
	 * 
	 * @param entity
	 * @return
	 */
	public static Map<String, Object> entityToMap(Object entity) {
		return toMap(toJson(entity));
	}
	
	public static Object jsonToEntity(String json,Class entity){
		ObjectMapper mapper = new ObjectMapper();
		Object result=null;
		try {
			result= mapper.readValue(json, entity);
		} catch (JsonParseException e) {
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static void main(String[]args){
		
	}
	
}