package com.teamsun.service.action;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResultToDataAction implements DataAction {

	private static final Log log = LogFactory.getLog(ResultToDataAction.class);

	private Connection connection;

	private int rowCount = 0;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void closeConnection() {
		try {
			if (this.connection != null)
				this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Map parseParam(String sql) {
		Map paras = new HashMap();
		Pattern p = null, p1 = null;
		Matcher m = null, m1 = null;

		// 处理false部分
		p = Pattern.compile("\\[[^\\]]*\\]");
		m = p.matcher(sql);
		String temp = "", varible = "";

		while (m.find()) {
			temp = m.group(0);
			p1 = Pattern.compile("#(\\w)+#");
			m1 = p1.matcher(temp);
			while (m1.find()) {
				varible = m1.group(0);
				varible = varible.substring(1, varible.length() - 1);
				paras.put(varible, "false");
			}

			sql = sql.replaceAll(temp.substring(1, temp.length() - 1), " ");
		}

		// 处理true的情况
		p1 = Pattern.compile("#(\\w)+#");
		m1 = p1.matcher(sql);
		while (m1.find()) {
			varible = m1.group(0);
			varible = varible.substring(1, varible.length() - 1);
			paras.put(varible, "true");
		}
		return paras;
	}

	private String replaceSQL(String cons, String oldSql) {

		if (cons == null)
			return oldSql;

		JSONObject json = JSONObject.fromString(cons);
		String result = oldSql;
		Map para = this.parseParam(oldSql);
		Iterator paraIt = para.keySet().iterator();
		while (paraIt.hasNext()) {
			String paraKey = (String) paraIt.next();
			String paraValue = (String) para.get(paraKey);

			if ("true".equals(paraValue)) {
				// 必选的参数处理
				if (json.has(paraKey) && !"".equals((String) json.get(paraKey))
						&& null != json.get(paraKey)) {
					result = result.replaceAll("#" + paraKey + "#",
							(String) json.get(paraKey));
				} else {
					throw new RuntimeException(paraKey + ":是必须输入的参数");
				}
			}

			if ("false".equals(paraValue)) {
				// 可选的参数处理
				if (json.has(paraKey) && !"".equals((String) json.get(paraKey))
						&& null != json.get(paraKey)) {
					result = result.replaceAll("#" + paraKey + "#",
							(String) json.get(paraKey));
				} else {
					result = result.replaceAll("\\[[^\\[]*#" + paraKey
							+ "#[^]]*\\]", "");
				}
			}
		}

		result = result.replaceAll("\\[", "").replaceAll("\\]", "");
		log.info("数据库执行SQL：[" + result + "]");
//		System.out.println(result);
		return result;
	}

	private String replaceSQL(String cons, String oldSql, int flag) {

		if (cons == null)
			return oldSql;

		JSONObject json = JSONObject.fromString(cons);
		String result = oldSql;
		Map para = this.parseParam(oldSql);
		Iterator paraIt = para.keySet().iterator();
		while (paraIt.hasNext()) {
			String paraKey = (String) paraIt.next();
			String paraValue = (String) para.get(paraKey);

			if ("true".equals(paraValue)) {
				// 必选的参数处理
				if (json.has(paraKey) && !"".equals((String) json.get(paraKey))
						&& null != json.get(paraKey)) {
					result = result.replaceAll("#" + paraKey + "#",
							(String) json.get(paraKey));
				} else {
					throw new RuntimeException(paraKey + ":是必须输入的参数");
				}
			}

			if ("false".equals(paraValue)) {
				// 可选的参数处理
				if (json.has(paraKey) && !"".equals((String) json.get(paraKey))
						&& null != json.get(paraKey)) {
					result = result.replaceAll("#" + paraKey + "#",
							(String) json.get(paraKey));
				} else {
					result = result.replaceAll("\\[[^\\[]*#" + paraKey
							+ "#[^]]*\\]", "''");
				}
			}
		}
		result = result.replaceAll("\\[", "").replaceAll("\\]", "");
		log.info("数据库执行SQL：[" + result + "]");
		return result;
	}

	public SqlResult getSQL(String id) {

		String sql = "select t.ds_sql,t.DS_SQLTYPE from me_dsinstance t where t.ds_id=?";
		SqlResult sqlresult = new SqlResult();
		ResultSet resultset = null;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = this.connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			resultset = preparedStatement.executeQuery();
			if (resultset.next()) {
				String sqls = resultset.getString(1);
				int flag = resultset.getInt(2);
				sqlresult.setFlag(flag);
				sqlresult.setSql(sqls);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultset != null) {
					resultset.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (Exception e) {
			}
		}
		return sqlresult;
	}

	public String execToXML(String osql, String para, String page) {
		CallableStatement cstmt = null;
		String proc = osql;
		ResultSet rs = null;
		int num = 0;
		StringBuffer temp = new StringBuffer(1024 * 4);
		StringBuffer buffer = new StringBuffer(1024 * 4);
		try {
			try {
				proc = this.replaceSQL(para, proc, 1);
			} catch (RuntimeException e) {
				buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				buffer.append("<Result success=\"0\">\n");
				buffer.append("<Message>").append(e.getMessage()).append(
						"</Message>\n");
				buffer.append("</Result>");
				return buffer.toString();
			}
			cstmt = this.connection.prepareCall(proc);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			if (null != page && !"".equals(page) && !"{}".equals(page)) {
				JSONObject json = JSONObject.fromString(page);
				int start = Integer.parseInt(json.getString("start"));
				int pagesize = Integer.parseInt(json.getString("pagesize"));
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				for (int i = 1; i <= colCount; i++) {// 元数据描述部分
					temp.append("<Column name=\"");
					temp.append(rsmd.getColumnName(i).toLowerCase());// 获得列的名字
					temp.append("\" type=\"");
					temp.append(rsmd.getColumnTypeName(i).toLowerCase())
							.append("\"/>\n");// 获得列的类型
				}
				temp.deleteCharAt(temp.length() - 1);
				temp.append("\n</Metadata>\n<Rowset>\n");
				while (rs.next()) {
					num++;
					if (num >= start && num < start + pagesize) {
						temp.append("<Row>\n");
						for (int i = 1; i <= colCount; i++) {// 元数据描述部分
							temp.append("<");
							temp.append(rsmd.getColumnName(i).toLowerCase());// 获得列的名字
							temp.append(">");
							temp.append(rs.getObject(i) == null ? "" : rs
									.getObject(i));
							temp.append("</");
							temp.append(rsmd.getColumnName(i).toLowerCase());
							temp.append(">\n");
						}
						temp.append("</Row>\n");
					}
				}
				buffer
						.append(
								"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Result success=\"1\">\n<Data totalCount=\"")
						.append(num);
				buffer.append("\">\n<Metadata>\n").append(temp).append(
						"\n</Rowset>\n</Data>\n</Result>");
			} else {
				buffer.append(rsToXml(rs, 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstmt != null)
					cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return buffer.toString();
	} 

	public String execToJSON(String osql, String para, String page) {
		CallableStatement cstmt = null;
		String proc = osql;
		ResultSet rs = null;
		int num = 0;
		StringBuffer temp = new StringBuffer(1024 * 4);
		StringBuffer buffer = new StringBuffer(1024 * 4);
		try {
			try {
				proc = this.replaceSQL(para, proc, 1);
			} catch (RuntimeException e) {
				buffer.append("{success:'0',Message:'").append(e.getMessage());
				buffer.append("'}");
				return buffer.toString();
			}
			cstmt = this.connection.prepareCall(proc);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			//TODO metadata:[{code:'metrics_id',type:'number'}]
			if (null != page && !"".equals(page) && !"{}".equals(page)) {
				JSONObject json = JSONObject.fromString(page);
				int start = Integer.parseInt(json.getString("start"));
				int pagesize = Integer.parseInt(json.getString("pagesize"));
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				for (int i = 1; i <= colCount; i++) {// 元数据描述部分
					temp.append("{code:'");
					temp.append(rsmd.getColumnName(i).toLowerCase());// 获得列的名字
					temp.append("',type:'");
					temp.append(rsmd.getColumnTypeName(i).toLowerCase())
							.append("'},");// 获得列的类型
				}
				temp.deleteCharAt(temp.length() - 1);
				temp.append("],data:[");
				while (rs.next()) {
					num++;
					if (num >= start && num < start + pagesize) {
						temp.append("{");
						for (int i = 1; i <= colCount; i++) {// 元数据描述部分
							temp.append(rsmd.getColumnName(i).toLowerCase());// 获得列的名字
							temp.append(":'");
							temp.append(
									rs.getObject(i) == null ? "" : rs
											.getObject(i)).append("',");
						}
						temp.deleteCharAt(temp.length() - 1);
						temp.append("},");
					}
				}
				temp.deleteCharAt(temp.length() - 1);
				buffer.append("{success:'1',totalCount:'").append(num);
				buffer.append("',metadata:[").append(temp).append("]}");
			} else {
				buffer.append(rsToJson(rs, 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstmt != null)
					cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return buffer.toString();
	}

	public String toXML(String osql, String para, String page) {

		PreparedStatement preparedStatement = null;
		// Statement statement = null;
		ResultSet rs = null;
		String sql = "";
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<Result success=\"0\">\n");
		try {
			sql = this.replaceSQL(para, osql);
			System.out.println("final chart SQL>>>>>>>>>: "+sql);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			sb.append("<Message>").append(e.getMessage())
					.append("</Message>\n");
			sb.append("</Result>");
			return sb.toString();
		}
		final StringBuffer buffer = new StringBuffer(1024 * 4);
		try {
			preparedStatement = connection.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (null != page && !"".equals(page) && !"{}".equals(page)) {
				JSONObject json = JSONObject.fromString(page);
				rowCount = getCount(preparedStatement); // 得到当前行号，也就是记录数
				int start = Integer.parseInt(json.getString("start"));
				int pagesize = Integer.parseInt(json.getString("pagesize"));
				preparedStatement.setMaxRows(start + pagesize); // 最大查询到第几条记
				rs = preparedStatement.executeQuery();
				rs.absolute(start);
				rs.previous();
			} else {
				rs = preparedStatement.executeQuery();
				rs.last();
				rowCount = rs.getRow();
				rs.first();
				rs.previous();
			}
			if (rs == null) {
				return "";
			}
			buffer.append(rsToXml(rs, rowCount));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	public String toCSV(String osql, String para, String page) {

		Statement statement = null;
		ResultSet rs = null;
		String sql;
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<Result success=\"0\">\n");
		try {
			sql = this.replaceSQL(para, osql);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			sb.append("<Message>").append(e.getMessage())
					.append("</Message>\n");
			sb.append("</Result>");
			return sb.toString();
		}
		final StringBuffer buffer = new StringBuffer(1024 * 4);
		try {
			statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (null != page && !"".equals(page) && !"{}".equals(page)) {
//				JSONObject json = JSONObject.fromString(page);
//				int start = json.getInt("start");
//				int pagesize = json.getInt("pagesize");
//				statement
//				if (pageNo.matches("[0-9]+")) {
//					no = Integer.valueOf(pageNo).intValue();
//				}
//				statement.setMaxRows(no * getPageSize()); // 最大查询到第几条记录
//				rs = statement.executeQuery(sql);
//				if (no == 1) {
//					rs.first();
//					rs.previous();
//				} else {
//				 	rs.absolute((no - 1) * getPageSize());
//				}
			} else {
				rs = statement.executeQuery(sql);
			}
			if (rs == null) {
				return "";
			}
			buffer.append(rsToCSV(rs));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	public String toJSON(String osql, String para, String page) {

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String sql = "";
		StringBuffer sb = new StringBuffer("{success:'0',");
		try {
			sql = this.replaceSQL(para, osql);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			sb.append("Message:").append(e.getMessage());
			sb.append("}");
			return sb.toString();
		}
		final StringBuffer buffer = new StringBuffer(1024 * 4);
		try {
			preparedStatement = connection.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (null != page && !"".equals(page) && !"{}".equals(page)) {
				JSONObject json = JSONObject.fromString(page);
				rowCount = getCount(preparedStatement); // 得到当前行号，也就是记录数
				int start = Integer.parseInt(json.getString("start"));
				int pagesize = Integer.parseInt(json.getString("pagesize"));
				preparedStatement.setMaxRows(start + pagesize); // 最大查询到第几条记
				rs = preparedStatement.executeQuery();
				rs.absolute(start);
				rs.previous();

			} else {
				rs = preparedStatement.executeQuery();
				rs.last();
				rowCount = rs.getRow();
				rs.first();
				rs.previous();
			}

			if (rs == null) {
				return "";
			}
			buffer.append(rsToJson(rs, rowCount));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	private int getCount(PreparedStatement s) {
		int count = 0;
		ResultSet resultSet = null;
		try {
			resultSet = s.executeQuery();
			resultSet.last();
			count = resultSet.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(resultSet!=null){
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
		}

		return count;
	}

	public StringBuffer rsToCSV(ResultSet rs) {

		final StringBuffer buffer = new StringBuffer(1024 * 4);
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			for (int i = 1; i <= colCount; i++) {// 元数据描述部分
				buffer.append(rsmd.getColumnName(i).toLowerCase());// 获得列的名字
				buffer.append(",");
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append("\n");
			while (rs.next()) {
				for (int i = 1; i <= colCount; i++) {
					buffer.append(rs.getObject(i) == null ? "" : rs
							.getObject(i));
					buffer.append(",");
				}
				buffer.deleteCharAt(buffer.length() - 1);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return buffer;
	};

	public StringBuffer rsToJson(ResultSet rs, int rowCount) {
		final StringBuffer buffer = new StringBuffer(1024 * 4);
		int count = rowCount;
		buffer.append("{success:'1',totalCount:'").append(count); // XML的头部信息
		buffer.append("',metadata:[");
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			for (int i = 1; i <= colCount; i++) {// 元数据描述部分
				//TODO metadata:[{code:'metrics_id',type:'number'}]
//				System.out.println("--------"+rsmd.getColumnDisplaySize(i));
				buffer.append("{code:'");
				buffer.append(rsmd.getColumnName(i).toLowerCase());// 获得列的名字
				buffer.append("',type:'");
				buffer.append(rsmd.getColumnTypeName(i).toLowerCase()).append(
						"'},");// 获得列的类型
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append("],");
			buffer.append("data:[");
			int num = 0;
			while (rs.next()) {
				num++;
				buffer.append("{");
				for (int i = 1; i <= colCount; i++) {
					buffer.append(rsmd.getColumnName(i).toLowerCase()).append(
							":'");
					buffer.append(rs.getObject(i) == null ? "" : rs
							.getObject(i));
					buffer.append("',");
				}
				buffer.deleteCharAt(buffer.length() - 1);
				buffer.append("},");
			}
			if (count == 1) {
				int index = buffer.indexOf("totalCount:'");
				int offset = buffer.indexOf("'", index + 12);
				buffer.delete(index + 12, offset);
				buffer.insert(index + 12, num);
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append("]}");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return buffer;
	};

	public StringBuffer rsToXml(ResultSet rs, int rowCount) {
		final StringBuffer buffer = new StringBuffer(1024 * 4);
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); // XML的头部信息
		buffer.append("<Result success=\"1\">\n");
		buffer.append("<Data totalCount='").append(rowCount).append("'>\n");
		buffer.append("<Metadata>\n");
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			for (int i = 1; i <= colCount; i++) {// 元数据描述部分

				buffer.append("   <Column name=\""); // <Column name="zip"
				// type="string" />
				buffer.append(rsmd.getColumnName(i).toLowerCase())
						.append("\" ");// 获得列的名字
				buffer.append("type=\"");
				buffer.append(rsmd.getColumnTypeName(i).toLowerCase()).append(
						"\" />\n");// 获得列的类型
			}
			buffer.append("</Metadata>\n");
			buffer.append("<Rowset>\n");
			int num = 0;
			while (rs.next()) {
				num++;
				buffer.append("   <Row>\n");
				for (int i = 1; i <= colCount; i++) {
					buffer.append("      <").append(
							rsmd.getColumnName(i).toLowerCase()).append(">");
					buffer.append(rs.getObject(i) == null ? "" : rs
							.getObject(i));
					buffer.append("</").append(
							rsmd.getColumnName(i).toLowerCase()).append(">\n");
				}
				buffer.append("   </Row>\n");
			}
			if (rowCount == 1) {
				int index = buffer.indexOf("totalCount='");
				int offset = buffer.indexOf("'", index + 12);
				buffer.delete(index + 12, offset);
				buffer.insert(index + 12, num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		buffer.append("</Rowset>\n");
		buffer.append("</Data>\n");
		buffer.append("</Result>");
		return buffer;
	}

	public static void main(String[] args) {

		// String sql = "SELECT t.dept_id,t.dept,sum(t.total_num) AS total_num"
		// + " FROM tb_report t "
		// + " WHERE t.rpt_id='RP003' AND t.look_for='#look_for#' AND
		// t.term_year='#year#'"
		// + " GROUP BY t.dept_id,t.dept"
		// + " HAVING sum(t.total_num) IS NOT NULL"
		// + " ORDER BY total_num DESC";
		//
		// Map cons = new HashMap();
		// cons.put("look_for", "beijing");
		// cons.put("year", "2009");
		//
		// String para = "{look_for:\"beijing\",year:\"2009\"}";
		try {
			// OutputStreamWriter writer = new OutputStreamWriter(new
			// FileOutputStream("d:\\data.xml"), "GB2312");
			// writer.write(toXML(sql));
			// writer.flush();
			// writer.close();
			// BufferedWriter out = new BufferedWriter(new
			// FileWriter("d:\\data.csv"));
			// out.write(sQL2CSV(sql));
			// out.flush();
			// out.close();
			// ResultToDataAction action = new ResultToDataAction();
			// System.out.println(action.replaceSQL(para, sql));
			String url = ResultToDataAction.class.getClass().getResource("")
					.getPath().replaceAll("%20", " ");
			String path = url.substring(0, url.indexOf("WEB-INF"))
					+ "WEB-INF/dataaction.properties";
			Properties config = new Properties();
			config.load(new FileInputStream(path));
			System.out.println(config.getProperty("pagesize"));
		//	System.out.println(getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
