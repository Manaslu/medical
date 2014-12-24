package com.idp.pub.web.binding;

import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;


public class SpringMvcDateBinding implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		SimpleDateFormat datetimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		datetimeFormat.setLenient(false);

		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
				dateFormat, true));
		binder.registerCustomEditor(java.sql.Timestamp.class,
				new CustomTimestampEditor(datetimeFormat, true));
	}

}
