package com.idap.web.sysmgr.userlog.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.idp.pub.constants.Constants;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.utils.RequestUtils;
import com.idp.sysmgr.user.entity.User;

/**
 * T02_SYS_FUNCTION, T02_USER_LOG;
 * 
 * @author Administrator
 * 
 */
public class LogInterceptor implements HandlerInterceptor {

	private Log logger = LogFactory.getLog(getClass());

	private List<SysFunction> sysfuns;

	public void init() {
		String sql = "SELECT FNAME,FURL,METHOD,REMARK,MENUNAME,CONTROLLER FROM T02_SYS_FUNCTION";
		sysfuns = this.jdbcTemplate.query(sql, new RowMapper<SysFunction>() {
			@Override
			public SysFunction mapRow(ResultSet rs, int arg1)
					throws SQLException {
				SysFunction temp = new SysFunction();
				int index = 1;
				temp.setFname(rs.getString(index++));
				temp.setFurl(rs.getString(index++));
				temp.setMethod(rs.getString(index++));
				temp.setRemark(rs.getString(index++));
				temp.setMenuname(rs.getString(index++));
				temp.setController(rs.getString(index++));

				return temp;
			}
		});
	}

	private SysFunction filter(String url, String method) {
		if(sysfuns!=null)
		for (SysFunction sf : sysfuns) {
			if (sf.getMethod().equalsIgnoreCase(method)
					&& inUrl(url, sf.getFurl())) {
				return sf;
			}
		}
		return null;
	}

	private boolean inUrl(String url, String furl) {
		String[] params = furl.split("\\?");
	
		if (url.indexOf(params[0]) < 0) {
			return false;
		}
		if(params.length<2){
			return true;
		}
		params = params[1].split("&");
		boolean exists = true;
		for (String s : params) {
			if (url.indexOf(s) < 0) {
				exists = false;
				break;
			}
		}
		return exists;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
		StringBuffer url = new StringBuffer(request.getRequestURL());
		url.append("?").append(request.getQueryString());
		String method = request.getMethod();
		SysFunction sf = this.filter(url.toString(), method);
		if (sf == null) {
			//logger.debug("系统功能未注册日志功能,[" + url.toString() + "]");
			return;
		}
		final Object[] values = new Object[8];
		int index = 0;
		//values[index++] = System.currentTimeMillis();
		values[index++] = url.toString();
		values[index++] = sf.getMenuname();
		User user = (User) request.getSession().getAttribute(
				Constants.USER_INFO);
		values[index++] = user.getId();
		values[index++] = user.getUserName();
		values[index++] = request.getSession().getId();
		values[index++] = RequestUtils.getIP(request);
		values[index++] = method;
		values[index++] = sf.getRemark();
		new Thread(new LogRunner(values)).start();
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object ModelAndView) throws Exception {
		return true;
	}

	@Resource(name = "txManager")
	private PlatformTransactionManager transactionManager;

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	class LogRunner implements Runnable {
		private Object[] values;

		public LogRunner(Object[] values) {
			this.values = values;
		}

		@Override
		public void run() {
			TransactionTemplate transaction = new TransactionTemplate(
					transactionManager);
			transaction.execute(new TransactionCallback<Object>() {
				@Override
				public Object doInTransaction(TransactionStatus status) {
					String sql = "INSERT INTO T02_USER_LOG(V_URL,MENUNAME,USER_ID,USER_NAME,SESSION_ID,USER_IP,METHOD,REMARK) VALUES(?,?,?,?,?,?,?,?)";
					jdbcTemplate.execute(sql,
							new PreparedStatementCallback<Object[]>() {
								public Object[] doInPreparedStatement(
										PreparedStatement ps)
										throws SQLException,
										DataAccessException {
									int index = 1;
									for (Object value : values) {
										ps.setObject(index++, value);
									}
									ps.execute();
									return values;
								}
							});
					return null;
				}

			});
		}

	}

	class SysFunction {
		private String fname;

		private String furl;

		private String remark;

		private String method;

		private String menuname;

		private String controller;

		public String getFname() {
			return fname;
		}

		public void setFname(String fname) {
			this.fname = fname;
		}

		public String getFurl() {
			return furl;
		}

		public void setFurl(String furl) {
			this.furl = furl;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getMenuname() {
			return menuname;
		}

		public void setMenuname(String menuname) {
			this.menuname = menuname;
		}

		public String getController() {
			return controller;
		}

		public void setController(String controller) {
			this.controller = controller;
		}
	}
}
