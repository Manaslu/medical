package com.idap.drad.entity;

import java.io.Serializable;

public class SystemCode implements Serializable {
	private static final long serialVersionUID = -4591618950647309204L;

	private String portcode;

	private String name;

	public String getPortcode() {
		return portcode;
	}

	public void setPortcode(String portcode) {
		this.portcode = portcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
