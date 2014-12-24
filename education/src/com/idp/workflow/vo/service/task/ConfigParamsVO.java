package com.idp.workflow.vo.service.task;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 工作流参数vo<br>
 * 放弃map结构，采用此结构
 * 
 * @author panfei
 * 
 */
public class ConfigParamsVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7810815678070617953L;

	private String keyCode;

	private String keyName;

	private String[] values;

	private String[] valueNames;

	public ConfigParamsVO() {
		super();
	}

	public ConfigParamsVO(String keyCode, String keyName, String[] values,
			String[] valueNames) {
		this.keyCode = keyCode;
		this.keyName = keyName;
		this.values = values;
		this.valueNames = valueNames;
	}

	public ConfigParamsVO(String keyCode, String keyName, String[] values) {
		this(keyCode, keyName, values, null);
	}

	public ConfigParamsVO(String keyCode, String[] values) {
		this(keyCode, null, values);
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String[] getValueNames() {
		return valueNames;
	}

	public void setValueNames(String[] valueNames) {
		this.valueNames = valueNames;
	}
}
