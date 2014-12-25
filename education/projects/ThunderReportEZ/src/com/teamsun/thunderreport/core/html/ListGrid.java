package com.teamsun.thunderreport.core.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teamsun.thunderreport.core.DataSourceService;
import com.teamsun.thunderreport.core.LayoutService;
import com.teamsun.thunderreport.core.RepresentContextService;
import com.teamsun.thunderreport.core.bean.Column;
import com.teamsun.thunderreport.core.bean.Footer;
import com.teamsun.thunderreport.core.bean.Header;
import com.teamsun.thunderreport.core.bean.Page;
import com.teamsun.thunderreport.parse.Sql;
import com.teamsun.thunderreport.core.bean.TableTitle;

/**
 * 适用于列数固定的报表
 * 
 */
public class ListGrid extends AbstractGrid implements RepresentContextService, LayoutService {

	private Sql sql;

	private String id;

	private Column[] columns;

	private Header header;

	private Footer footer;

	private TableTitle tabletitle;

	private List data;

	private Page page;

	private String templateVm;

	public void setData(DataSourceService dataSourceService) {
		this.data = dataSourceService.getData(this.sql.getSqlText(), this.page);
	}

	public String getId() {
		return this.id;
	}

	/**
	 * 按group 分组所有字段
	 * 
	 * 
	 * @return
	 */
	private Map groupData() {
		Map column_group = new HashMap();

		for (int i = 0; i < data.size(); i++) {
			Map map = (Map) this.data.get(i);

			for (int j = 0; j < this.columns.length; j++) {
				Column column = this.columns[j];
				// 根据字段名取得实际的值
				Object obj = map.get(column.getCode());
				// 为空表示此字段值不存在于数据库结果集中(可能为计算字段)
				if (obj == null)
					continue;

				if (column.getGroup() > 0) {
					// values key=column value=Map(key=value+value+value
					// value=integer)
					Map values = (Map) column_group.get(column);
					if (values == null) {
						Map mpx = new HashMap();
						column_group.put(column, mpx);
						values = (Map) column_group.get(column);
					}

					// 对column的group字段循环 大小顺序拼状
					int group_int = column.getGroup();
					String key = "";
					for (int k = 1; k <= group_int; k++) {
						for (int l = 0; l < columns.length; l++) {
							if (columns[l].getGroup() == k)
								key = key + "-" + map.get(columns[l].getCode()).toString();
						}
					}

					if (values.containsKey(key)) {
						Integer intx = (Integer) values.get(key);
						values.remove(key);
						values.put(key, new Integer(1 + intx.intValue()));
					} else {
						values.put(key, new Integer(1));
					}
					column_group.remove(column);
					column_group.put(column, values);
				}
			}
		}

		return column_group;
	}

	/**
	 * 说明
	 * 
	 * 对于group>0的字段，拼状html table后会出现 td rowspan=? 的形式。
	 * 
	 * 
	 * 首先对数据集(data)进行循环，抽取所有group>0的字段。 column_group 中key=column value=Map，这个Map的key=group1+group2+group3+...的值，value=出现的记录数 循环完毕，会记录所有group字段出现的次数
	 * 
	 * 
	 * 
	 */
	public String getReportBody() {
		StringBuffer sb = new StringBuffer();

		Map column_group = this.groupData();

		for (int i = 0; i < this.data.size(); i++) {
			sb.append("<tr>");
			Map map = (Map) this.data.get(i);
			for (int j = 0; j < columns.length; j++) {
				Column column = (Column) columns[j];

				if (column.getGroup() > 0) {
					int intx = column.getGroup();
					String key = "";
					for (int k = 1; k <= intx; k++) {
						for (int k2 = 0; k2 < columns.length; k2++) {
							if (columns[k2].getGroup() == k)
								key = key + "-" + map.get(columns[k2].getCode()).toString();
						}
					}

					Map m = (Map) column_group.get(column);
					Integer intx_ = (Integer) m.get(key);
					if (intx_ != null) {
						sb.append("<td rowspan=" + intx_.intValue() + ">").append(map.get(column.getCode())).append("</td>");
						m.remove(key);
					}
				} else {
					sb.append("<td>").append(map.get(column.getCode())).append("</td>");
				}
			}
			sb.append("</tr>\n");
		}
		column_group.clear();

		return sb.toString();
	}

	public String getReportFooter() {
		StringBuffer sb = new StringBuffer();
		sb.append("<tfoot>");
		sb.append("<tr>");

		sb.append("</tr>");
		sb.append("</tfoot>");
		return this.footer.getText();
	}

	public String getReportHeader() {
		return this.header.getText();
	}

	public Sql getSql() {
		return this.sql;
	}

	public void setSql(Sql sql) {
		this.sql = sql;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static void main(String[] args) {

		List list = new ArrayList();

		Map m1 = new HashMap();
		m1.put("A", "A");
		m1.put("B", "B");
		m1.put("C", "C");
		m1.put("D", "10");

		list.add(m1);

		Map m2 = new HashMap();
		m2.put("A", "A");
		m2.put("B", "B");
		m2.put("C", "C");
		m2.put("D", "200");

		list.add(m2);

		Map m3 = new HashMap();
		m3.put("A", "A");
		m3.put("B", "B");
		m3.put("C", "XX");
		m3.put("D", "300");

		list.add(m3);

		Map m4 = new HashMap();
		m4.put("A", "A");
		m4.put("B", "yy");
		m4.put("C", "asdf");
		m4.put("D", "400");

		list.add(m4);

		ListGrid listGrid = new ListGrid();
		listGrid.data = list;

		Column[] cols = new Column[4];
		cols[0] = new Column();
		cols[0].setGroup(1);
		cols[0].setCode("A");

		cols[1] = new Column();
		cols[1].setGroup(2);
		cols[1].setCode("B");

		cols[2] = new Column();
		cols[2].setGroup(3);
		cols[2].setCode("C");

		cols[3] = new Column();
		cols[3].setCode("D");

		listGrid.setColumns(cols);

		System.out.println(listGrid.getReportBody());

	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Footer getFooter() {
		return footer;
	}

	public void setFooter(Footer footer) {
		this.footer = footer;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Column[] getColumns() {
		return columns;
	}

	public String formatContext(List data) {
		this.data = data;
		String html = this.getReportHeader() + this.getTabletitle().getText() + this.getReportBody() + this.getReportFooter();
		return html;
	}

	public TableTitle getTabletitle() {
		return tabletitle;
	}

	public void setTabletitle(TableTitle tabletitle) {
		this.tabletitle = tabletitle;
	}

	public String getTemplateVm() {
		return templateVm;
	}

	public void setTemplateVm(String templateVm) {
		this.templateVm = templateVm;
	}

	public String formatContext(List data, List allList, String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
