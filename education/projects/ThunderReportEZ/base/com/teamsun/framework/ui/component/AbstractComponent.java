package com.teamsun.framework.ui.component;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractComponent {
	public Map propMap = new HashMap();

	public String content;

	public void addProp(String name, String value) {
		if (propMap.containsKey(name)) {
			propMap.remove(name);
		}
		this.propMap.put(name, value);

	}

	public AbstractComponent() {

	}

	public AbstractComponent(String content) {
		this.content = content;
	}

	public abstract String toHtmlString();

}
