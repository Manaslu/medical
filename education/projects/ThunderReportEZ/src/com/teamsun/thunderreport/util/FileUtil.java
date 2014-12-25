package com.teamsun.thunderreport.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.teamsun.thunderreport.thread.WorkUnit;
import com.teamsun.thunderreport.parse.Column;

public class FileUtil {
	private static final Log log = LogFactory.getLog(FileUtil.class);
	FileOutputStream dest=null;
	ZipOutputStream out=null;
	public FileUtil(){
		
	}
	public FileUtil(String dir,String fileName){
		if (!new File(dir).exists())
			new File(dir).mkdirs();
		try {
			String absFile=dir+System.getProperty("file.separator")+fileName+".zip";		
			dest = new FileOutputStream(absFile);
			out = new ZipOutputStream(dest);			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void putFile(String data,String fileName){
		ZipEntry entry = new ZipEntry(fileName+".xls");
		try {
			out.putNextEntry(entry);
			out.write(data.getBytes("utf-8"));
			out.closeEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public void flush(String fileName){
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (dest != null) {
				try {
					dest.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}			
		log.info("文件zip压缩创建完毕:--" + fileName);
	}
	
	public String createCvsFile(String dir,String fileName){
		String absFile="";
		if (!new File(dir).exists())
			new File(dir).mkdirs();
		try {
			absFile=dir+System.getProperty("file.separator")+fileName+".cvs";		
			dest = new FileOutputStream(absFile,true);
					
		}catch (IOException e) {
			e.printStackTrace();
		}	
		return absFile;
	}
	public void writeData(String str){
		try {
			dest.write(str.getBytes());			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void flushCvs(){
		try {
			dest.flush();
			dest.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void createZip(String dir,String srcFileName,String fileName){
		byte[] buf = new byte[1024];		
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(srcFileName));
			String absFile = dir + System.getProperty("file.separator")
					+ fileName + ".zip";
			dest = new FileOutputStream(absFile);
			out = new ZipOutputStream(dest);
			ZipEntry entry = new ZipEntry(fileName + ".cvs");
			out.putNextEntry(entry);
			int readLen = -1;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				out.write(buf, 0, readLen);
			}
			is.close();
			out.closeEntry();
			dest.close();
			out.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void addZip(String fileStr,String dir,String fileName,String type){
		if (!new File(dir).exists())
			new File(dir).mkdirs();
		FileOutputStream dest=null;
		ZipOutputStream out=null;
		try {
			String absFile=dir+System.getProperty("file.separator")+fileName+".zip";		
			System.out.println(absFile);
			dest = new FileOutputStream(absFile);
			out = new ZipOutputStream(dest);
			ZipEntry entry = new ZipEntry(fileName+"."+type);			
			out.putNextEntry(entry);					
			out.write(fileStr.getBytes("gb2312"));			
			out.flush();
			out.closeEntry();
			out.close();
			log.info("文件zip压缩创建完毕:--" + absFile);
			//createHtmlFile(dir,fileName);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (dest != null) {
				try {
					dest.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void createHtmlFile(String dir,String fileName){
		if (!new File(dir).exists())
			new File(dir).mkdirs();
		FileOutputStream out=null;
		try {
			String absFile=dir+System.getProperty("file.separator")+fileName+".html";
			out = new FileOutputStream(new File(absFile));
			String html=getZipHtmlCode(fileName);
		    out.write(html.getBytes("gb2312"));
		    out.flush();		    
		    log.info("zip文件对应的html文件创建完毕:--" + absFile);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (out != null) {
				try {
					out.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getCsvExportText(List data,Column[] cols){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<data.size();i++){
			Map m=(Map)data.get(i);
			for(int j=0;j<cols.length;j++){
				if(j>0) sb.append(",");
				sb.append(m.get(cols[j].getCode().toUpperCase()));
			}
			sb.append("\r\n");			
		}
		return sb.toString();
	}
	
	public static String getCsvExportText(List data){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<data.size();i++){
			Map m=(Map)data.get(i);
			Object[] it=m.values().toArray();
			for(int j=0;j<it.length;j++){
				if(j>0) sb.append(",");
				sb.append(it[j].toString());
			}
			sb.append("\r\n");			
		}
		return sb.toString();
	}
	
	public static String getZipHtmlCode(String fileName){
		StringBuffer sb = new StringBuffer();		
		sb.append("<script language=\"javascript\">");
		sb.append("alert(\"文件记录超过"+ApplicationPro.getValue("application.maxdatasize")+"条,开始下载压缩文件\");");
		sb.append("window.location.href=\""+fileName+".zip\";");
		sb.append("</script>");
		return sb.toString();
	}
	public static void main(String argv[]) {
		//FileUtil.addZip("你好 ", "c:", "lkt");			
	}
}
