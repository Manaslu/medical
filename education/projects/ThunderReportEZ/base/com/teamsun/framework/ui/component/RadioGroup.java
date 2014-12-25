package com.teamsun.framework.ui.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class RadioGroup extends AbstractComponent {
	private String name = "";

	private String key;

	private Map valueMap;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map valueMap) {
		this.valueMap = valueMap;
	}

	public RadioGroup(String name, Map arr, String key) {
		this.name = name;
		this.valueMap = arr;
		this.key = key;
	}

	public String toHtmlString() {

		StringBuffer radioString = new StringBuffer();

		Set enterSet = this.valueMap.entrySet();
		Iterator it = enterSet.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			String attr_key = entry.getKey().toString();
			String attr_value = entry.getValue().toString();
			if (this.key != null && attr_key != null
					&& this.key.equals(attr_key)) {
				Radio radio = new Radio(this.name, attr_key, attr_value, true);
				if (!propMap.isEmpty()) {
					Set propSet = propMap.keySet();
					Iterator propIter = propSet.iterator();
					while (propIter.hasNext()) {
						String propName = propIter.next().toString();
						String propValue = propMap.get(propName).toString();
						radio.addProp(propName, propValue);
					}
				}
				radioString.append(radio.toHtmlString()).append("\n");
			} else {
				Radio radio = new Radio(this.name, attr_key, attr_value, false);
				if (!propMap.isEmpty()) {
					Set propSet = propMap.keySet();
					Iterator propIter = propSet.iterator();
					while (propIter.hasNext()) {
						String propName = propIter.next().toString();
						String propValue = propMap.get(propName).toString();
						radio.addProp(propName, propValue);
					}
				}
				radioString.append(radio.toHtmlString()).append("\n");
			}
		}
		return radioString.toString();
	}

	public static void main(String[] args) {
		Map s = new HashMap();
		s.put("1", "w");
		s.put("2", "ee");
		s.put("3", "wq");
		String ss = new RadioGroup("ww", s, "1").toHtmlString();
		System.out.println(ss);
	}
}
