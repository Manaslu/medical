package com.idp.pub.web.converters;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.core.convert.converter.Converter;

import com.idp.pub.utils.StringUtils;

/**
 * 字符串转换为时间戳（年月日时分秒）
 * 
 * @author panfei
 * 
 */
public class StringToDateTimeConverter implements Converter<String, Timestamp> {

	@Override
	public Timestamp convert(String source) {
		if (!StringUtils.isEmpty(source)) {
			SimpleDateFormat datetimeFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			datetimeFormat.setLenient(false);
			try {
				return new Timestamp(datetimeFormat.parse(source).getTime());
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
		return null;
	}

}
