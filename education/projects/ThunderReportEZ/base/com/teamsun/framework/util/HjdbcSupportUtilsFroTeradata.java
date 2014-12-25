package com.teamsun.framework.util;

import java.util.ArrayList;
import java.util.List;

/**
 * jDBC查询操作的工具类:针对TERADATA使用
 * 
 * @author marshal
 * 
 */
public abstract class HjdbcSupportUtilsFroTeradata {
	/**
	 * Logger for this class
	 */
	// private static final Log logger = LogFactory
	// .getLog(HjdbcSupportUtilsFroTeradata.class);
	/**
	 * 
	 * 得到分页的结构
	 * 
	 * 
	 * @param session
	 *            hibernate session
	 * @param sql
	 *            hsql语句
	 * @param items
	 *            参数列表
	 * @param pageSize
	 *            页面记录数
	 * 
	 * @param pageNo
	 *            页号
	 * @return PageImpl
	 */

	public static PageImpl getResults(List result, String sql, Object[] param,
			int pageSize, int pageNo, String orderName, boolean desc, int count) {

		PageImpl page = new PageImpl();// 返回的分页信息

		List pageList = new ArrayList();// 分页信息中的信息列表。

		if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize <= 0) {
			pageSize = 1;
		}

		int resultsCount = count;// 总的获得记录数

		int _pageNo = pageNo;

		if (resultsCount > 0) {

			if (!hasResults(resultsCount, pageSize, pageNo)) {
				_pageNo = ((int) Math.ceil(resultsCount / pageSize * 1.0f));
			}

			StringBuffer buffer = new StringBuffer(32);
			buffer.append(sql);
			// orderName必须是表名+列名
			if (orderName != null) {
				buffer.append(" order by " + orderName);

				if (desc) {
					buffer.append(" desc");
				}
			}
			// 其始记录号

			int cirl = pageSize * (_pageNo - 1);

			// 处理当最后一页的记录数不满足每页要显示的记录数时，_PAGESIZE循环次数要减少。

			int _pageSize = pageSize;// PAGESIZE循环中间变量
			if (resultsCount - cirl < pageSize)
				_pageSize = resultsCount - cirl;
			for (int k = cirl; k < cirl + _pageSize; k++) {
				pageList.add(result.get(k));

			}
			page = new PageImpl(pageList, _pageNo, pageSize, resultsCount);
		}

		return page;
	}

	private static boolean hasResults(int resultsCount, int pageSize, int pageNo) {
		return resultsCount > 0 && resultsCount > pageSize * (pageNo - 1);
	}

	private HjdbcSupportUtilsFroTeradata() {
	}
}