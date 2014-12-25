package com.teamsun.thunderreport.database;

public class DmiSampleValue implements DimDataService {

	private String ref_name;

	private String key;

	private String value;

	public String getRef_name() {
		return this.ref_name;
	}

	public String[][] loadData() {
		if (key.split("\\,").length != value.split("\\,").length) {
			return new String[0][0];
		}

		String[][] result = new String[2][key.split("\\,").length];
		result[0] = key.split("\\,");
		result[1] = value.split("\\,");
		return result;

	}

	public void setRef_name(String ref_name) {
		this.ref_name = ref_name;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
