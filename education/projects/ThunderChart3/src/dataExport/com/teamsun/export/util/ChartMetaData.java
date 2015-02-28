package com.teamsun.export.util;

import java.io.ByteArrayOutputStream;

public class ChartMetaData {
	private String domId;
	private String bgColor;
	private String caption;

	private int height;
	private int width;
	private String stream;
	private ByteArrayOutputStream baos;
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getDomId() {
		return domId;
	}
	public void setDomId(String domId) {
		this.domId = domId;
	}

	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public ByteArrayOutputStream getBaos() {
		return baos;
	}
	public void setBaos(ByteArrayOutputStream baos) {
		this.baos = baos;
	}


}
