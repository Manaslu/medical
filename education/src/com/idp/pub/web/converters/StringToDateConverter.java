package com.idp.pub.web.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.idp.pub.utils.StringUtils;

/**
 * 字符串转换为日期(年月日)
 * 
 * @author panfei
 * 
 */
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		if (!StringUtils.isEmpty(source)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);
			try {
				return dateFormat.parse(source);
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
		return null;
	}

}
