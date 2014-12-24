package com.idp.pub.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author panfei
 * 
 */
public class ClassObjUtil {

	/**
	 * 深度克隆 深拷贝 复制
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Object cloneObject(Object obj) throws Exception {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(obj);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(
				byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);

		return in.readObject();
	}
}
