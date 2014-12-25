package com.teamsun.framework.ui;

public class HtmlHeader {

	private static String html = "";

	static {
		html = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
		html += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n";
		html += "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
	}

	private String cssBasePath = "/ThunderReportBW/css/report.css";

	private String scriptBasePath = "/ThunderReportBW/include/js/report.js";

	public String getCssBasePath() {
		return cssBasePath;
	}

	public void setCssBasePath(String cssBasePath) {
		this.cssBasePath = cssBasePath;
	}

	public String getScriptBasePath() {
		return scriptBasePath;
	}

	public void setScriptBasePath(String scriptBasePath) {
		this.scriptBasePath = scriptBasePath;
	}

	public String getHeader() {
		String meta = html
				+ "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
		String cssLink = "<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ cssBasePath + "\"/>\n";
		cssLink += "<script src=\"/ThunderReportBW/include/js/ext-base.js\"></script>\n";
		cssLink += "<script src=\"/ThunderReportBW/include/js/ext-all.js\"></script>\n";
		String scriptLink = "<script src=\"" + scriptBasePath + "\"></script>";
		return meta + "\n" + cssLink + "\n" + scriptLink + "\n</head>\n";
	}

	public static void main(String[] args) {

		HtmlHeader hh = new HtmlHeader();
		System.out.println(hh.getFullHtmlText("测试"));

	}

	public String getFullHtmlText(String htmlBody) {
		return this.getHeader() + htmlBody + "\n</" + "html>";
	}
}
