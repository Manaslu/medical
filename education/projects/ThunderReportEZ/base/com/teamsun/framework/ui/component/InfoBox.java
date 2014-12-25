package com.teamsun.framework.ui.component;

public class InfoBox
{
	public String title;

	public String[] oprArr;

	public String content;

	public String id;

	public InfoBox(String title, String[] oprArr, String content, String id)
	{
		this.title = title;
		this.oprArr = oprArr;
		this.content = content;
		this.id = id;
	}

	public String toHtmlString()
	{
		StringBuffer htmlString = new StringBuffer();
		htmlString.append("<table id=\"").append(this.id).append(
				"\" width=\"100%\" cellpadding=0 cellspacing=0>");
		htmlString
				.append("<tr>")
		.append("<td class=\"infoBoxtriangle\" block=\"1\" title=\"折叠\" onclick=\"infoboxdis()\">6</td>")
		.append("<td noWrap=\"true\" class=\"infoBoxtitle\" align=\"center\">")
				.append(title).append("</td>");

		htmlString
				.append("<td width=\"100%\" style=\"border-bottom:1px solid #999999\" align=\"right\">");
		if (oprArr != null)
		{
			htmlString.append("<table border=\"0\"><tr>");

			for (int i = 0; i < oprArr.length; i++)
			{
				htmlString.append("<td>");
				htmlString.append(oprArr[i]);
				htmlString.append("</td>");
			}
			htmlString.append("</tr></table>");
		} else
		{
			htmlString.append("&nbsp;");
		}
		htmlString.append("</td></tr>\n<tr><td colSpan=\"3\" border=\"1px solid #808080\" width=\"100%\">");
		htmlString.append("<div class=\"infoBoxContent\">").append(content)
				.append("</div>\n</td></tr>");
		htmlString.append("</table>");

		return htmlString.toString();
	}

	public static void main(String[] args)
	{
		String[] stringArray =
		{ "<a href=#>a</a>", "<a href=#>g</a>", "<a href=#>c</a>" };
		InfoBox ui_infobox = new InfoBox(
				"zhu",
				stringArray,
				"<table style=\"background:red height:20px width:40px\"></table>",
				"infobox");
		String htmlString = ui_infobox.toHtmlString();
		System.out.println(htmlString);

	}
}
