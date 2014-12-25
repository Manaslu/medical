package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

public class ImgIcon extends AbstractComponent {

	private String imgName;

	private String imgSrc;

	public ImgIcon(String name, String src) {
		this.imgName = name;
		this.imgSrc = src;
	}

	public String toHtmlString() {
		String str = "";
		String propString = "";
		if (this.propMap.size() != 0) {
			Set keys = this.propMap.keySet();
			Iterator propIter = keys.iterator();
			while (propIter.hasNext()) {
				String propName = (String) propIter.next();
				String propValue = (String) this.propMap.get(propName);
				propString += " " + propName + "=\"" + propValue + "\"";
			}
		}
		str += "<img id=\"" + this.imgName + "\" name=\"" + this.imgName
				+ "\" src=\"" + this.imgSrc + "\"" + propString + ">";
		return str;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

}
