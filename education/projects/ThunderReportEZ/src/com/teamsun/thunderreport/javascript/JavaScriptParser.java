package com.teamsun.thunderreport.javascript;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

/**
 * 服务端解析javascript
 * 
 */
public class JavaScriptParser {

	private static final Log log = LogFactory.getLog(JavaScriptParser.class);

	private static ContextFactory cf;

	private static ThreadLocal threadLocal = new ThreadLocal();

	private Map cachejs = new HashMap();

	private Context cx;

	private Scriptable scope;

	private static String filename;

	static {
		cf = ContextFactory.getGlobal();
	}

	private Map functions = new HashMap();

	private JavaScriptParser() {
		cx = cf.enterContext();
		scope = cx.initStandardObjects();
		InputStreamReader rd = null;
		try {
			rd = new InputStreamReader(new FileInputStream(filename), "utf-8");
			cx.evaluateReader(scope, rd, "", 1, null);
			Object[] fs = scope.getIds();

			for (int i = 0; i < fs.length; i++) {
				String fname = fs[i].toString();
				Object function = scope.get(fname, scope);
				if (function instanceof Function) {
					Function f = (Function) function;
					this.functions.put(fname, f);
					log.info("加载脚本函数:[" + fname + "]");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (rd != null)
				try {
					rd.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 通过ThreadLocal为每一个线程保持一个线程范围内的变量

	 * 
	 * 
	 * @return
	 */
	public static JavaScriptParser getInstance() {

		Object local = threadLocal.get();
		if (local == null) {
			threadLocal.set(new JavaScriptParser());
			return (JavaScriptParser) threadLocal.get();
		} else {
			return (JavaScriptParser) local;
		}
	}

	/**
	 * 初始化 js文件，只能有一个js文件？

	 * 
	 * 
	 * @param filename
	 */
	public static void init(String filename) {
		JavaScriptParser.filename = filename;
	}

	public Object evalFunctionFullText(String text) {
		if (text.indexOf("<script>") > 0 && text.indexOf("</script>") > 0) {
			String s = text.substring(text.indexOf("<script>"), text
					.indexOf("</script>") + 9);

			String key = s;
			String value = key.substring(8, s.length() - 9);

			String newText = text.substring(0, text.indexOf("<script>"))
					+ this.evalFunction(value).toString()
					+ text.substring(text.indexOf("</script>") + 9, text
							.length());

			return this.evalFunctionFullText(newText);
		} else {
			return text;
		}

	}

	/**
	 * 解析页面传递来的js函数 functions('aa',343,'bbs',34.4)
	 * 
	 * @param fname
	 * @return
	 */
	public Object evalFunction(String fname) {

		fname = fname.trim();
		if (cachejs.containsKey(fname))
			return cachejs.get(fname).toString();

		String function_name = fname.substring(0, fname.indexOf("("));
		String values = fname.substring(fname.indexOf("(") + 1, fname
				.lastIndexOf(")"));
		Object res = null;
		try {
			res = cx.evaluateString(scope, "[" + values + "]", "", 1, null);
		} catch (Exception e) {
			log.error(fname);
		}
		if (res instanceof org.mozilla.javascript.NativeArray) {
			org.mozilla.javascript.NativeArray ary = (org.mozilla.javascript.NativeArray) res;

			Object[] os = new Object[(int) ary.getLength()];
			for (int i = 0; i < os.length; i++) {
				os[i] = ary.get(i, scope);
			}
			Object text = this.evalFunction(function_name, os);
			cachejs.put(fname, text);
			return text;
		}
		return values;
	}

	/**
	 * 调用函数
	 * 
	 * @param fname
	 *            js函数名

	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	public Object evalFunction(String fname, Object[] params) {
		if (functions.containsKey(fname)) {
			Function f = (Function) functions.get(fname);
			try {
				Object result = f.call(cx, scope, scope, params);
				if (result instanceof org.mozilla.javascript.NativeJavaObject) {
					org.mozilla.javascript.NativeJavaObject new_name = (org.mozilla.javascript.NativeJavaObject) result;
					return new_name.unwrap();

				} else
					return result;
			} catch (Exception e) {
				log.error(fname);
				for (int i = 0; i < params.length; i++) {
					log.error(params[i]);
				}

			}
		}
		return "";
	}

	public void close() {
		Context.exit();
	}

	public static void main(String[] args) {
		JavaScriptParser.init("web/include/js/userreport.js");
		JavaScriptParser parser = JavaScriptParser.getInstance();
		String s = parser
				.evalFunction(
						"doHref({'file':'Product_Month_Develop_Income.xml',"
								+ "'Latn_Id':'99999',"
								+ "'Bil_Month':'200801','Prd_Id':'110012','Prd_Name':'电话伴我行(11800)'},"
								+ "'电话伴我行(11800)')").toString();
		System.out.println(s);
		Object o = parser.evalFunction("DrawColorFormatNumber", new Object[]{new Integer(2), "0"});
		System.out.println(o);
	}

}
