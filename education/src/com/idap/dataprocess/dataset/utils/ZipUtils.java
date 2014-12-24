package com.idap.dataprocess.dataset.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @###################################################
 * @创建日期：2014-4-4 14:58:30
 * @开发人员：李广彬
 * @功能描述：zip 压缩工具类
 * @修改日志： 
 * @###################################################
 */
public class ZipUtils {
	public static final String EXT = ".zip";//扩展名
	private static final String BASE_DIR = ""; //压缩文件的根目录
	private static final String PATH = File.separator;// 符号"/"用来作为目录标识判断符  
	private static final int BUFFER = 1024;//缓存区大小
	
	/** 
	 * 压缩 
	 *  
	 * @param srcPath 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static File compress(String srcPath) throws IOException {
		File srcFile = new File(srcPath);
		return compress(srcFile);
	}
	
	/** 
	 * 压缩文件 
	 *  
	 * @param srcFile 要压缩文件或目录地址
	 * @param destPath  压缩文件存入地址（全路径）
	 * @return 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static File compress(String srcFile, String destPath) throws IOException {
		return compress(new File(srcFile), new File(destPath));
	}
	
	/** 
	 * 压缩 指定的目录或文件 
	 * 压缩文件默认存放在压缩目录的上级 
	 * 文件名与目录名相同
	 *  
	 * @param srcFile 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static File compress(File srcFile) throws IOException {
		String name = srcFile.getName();//目录或文件名
		String basePath = srcFile.getParent();//上级目录
		String destPath = basePath +PATH+ name + EXT;//d:\zip\name.zip
		return compress(srcFile, destPath);
	}
	
	/** 
	 * 压缩文件 
	 *  
	 * @param srcFile 要压缩文件或目录地址
	 * @param destPath  压缩文件存入地址（全路径）
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static File compress(File srcFile, String destPath) throws IOException {
		return compress(srcFile, new File(destPath));
	}
	
	/** 
	 * 压缩文件 到指定目录
	 *  
	 * @param srcFile 源路径 
	 * @param destPath 目标路径 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static File compress(File srcFile, File destFile) throws IOException {
		// 对输出文件做CRC32校验  
	    CheckedOutputStream cos =null;
	    ZipOutputStream zos =null;
		try {
		     cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());
	         zos = new ZipOutputStream(cos);
            compress(srcFile, zos, BASE_DIR);
        } finally{
            zos.flush();
            zos.close();
        }
		return destFile;
	}
	
	/** 
	 * 压缩 
	 *  
	 * @param srcFile 源路径 
	 * @param zos  ZipOutputStream 
	 * @param basePath  压缩包内相对路径 
	 * @throws IOException 
	 * @throws Exception 
	 */
	private static void compress(File srcFile, ZipOutputStream zos, String basePath) throws IOException {
		if (srcFile.isDirectory()) {
			compressDir(srcFile, zos, basePath);
		} else {
			compressFile(srcFile, zos, basePath);
		}
	}
	
	/** 
	 * 压缩目录 
	 *  
	 * @param dir 
	 * @param zos 
	 * @param basePath 
	 * @throws IOException 
	 * @throws Exception 
	 */
	private static void compressDir(File dir, ZipOutputStream zos, String basePath) throws IOException {
		File[] files = dir.listFiles();
		// 构建空目录  
		if (files.length < 1) {
			ZipEntry entry = new ZipEntry(basePath + dir.getName() + PATH);
			System.out.println(entry.getName());
			zos.putNextEntry(entry);
			zos.closeEntry();
		}
		
		for (File file : files) {
			// 递归压缩  
			compress(file, zos, basePath + dir.getName() + PATH);
		}
	}
	
	/** 
	 * 压缩包内文件名定义
	 * 如果有多级目录，那么这里就需要给出包含目录的文件名 
	 * 如果用WinRAR打开压缩包，中文名将显示为乱码 
	 *  
	 * @param file   待压缩文件 
	 * @param zos  ZipOutputStream 
	 * @param dir  压缩文件中的当前路径 
	 * @throws IOException 
	 * @throws Exception 
	 */
	private static void compressFile(File file, ZipOutputStream zos, String dir) throws IOException {
		ZipEntry entry = new ZipEntry(dir + file.getName());
		zos.putNextEntry(entry);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = bis.read(data, 0, BUFFER)) != -1) {
			zos.write(data, 0, count);
		}
		bis.close();
		zos.closeEntry();
	}
	
	
	
	/**
	     * 解压缩zip包
	     * 
	     * @param zipFilePath zip文件路径
	     * @param destPath 解压缩到的位置，如果为null或空字符串则默认解压缩到跟zip包同目录跟zip包同名的文件夹下
	     * @throws IOException
	     */
    public static void unCompress(String zipFilePath, String destPath) throws IOException {
        OutputStream os = null;
        InputStream is = null;
        ZipFile zipFile = null;
        ZipEntry zipEntry = null;
        String directoryPath = "";
        try {
            zipFile = new ZipFile(zipFilePath);
            if (null == destPath || "".equals(destPath.trim())) {
                directoryPath = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
            } else {
                directoryPath = destPath;
            }
            Enumeration entryEnum = zipFile.entries();
            if (null != entryEnum) {
                while (entryEnum.hasMoreElements()) {
                    zipEntry = (ZipEntry) entryEnum.nextElement();
                    System.out.println(zipEntry.getName());
                    if (zipEntry.isDirectory()) {//目录
                        directoryPath = directoryPath + File.separator+ zipEntry.getName();
                        continue;
                    }
                    if (zipEntry.getSize() > 0) {// 文件
                        File destFile=new File(directoryPath,zipEntry.getName());  
                        if(!destFile.exists()){  
                            (new File(destFile.getParent())).mkdirs();  
                        }  
                        os = new BufferedOutputStream(new FileOutputStream(destFile));
                        is = zipFile.getInputStream(zipEntry);
                        byte[] buffer = new byte[4096];
                        int readLen = 0;
                        while ((readLen = is.read(buffer, 0, 4096)) >= 0) {
                            os.write(buffer, 0, readLen);
                        }

                        os.flush();
                        os.close();
                    } else {// 空目录
                    	File targetFile=new File(directoryPath,zipEntry.getName());  
                        targetFile.mkdirs();
                    }
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if(null != zipFile){
                zipFile = null;
            }
            if (null != is) {
                is.close();
            }
            if (null != os) {
                os.close();
            }
        }
    }


	
	public static void main(String args[]){
		try {
			compress("D:\\Workspaces\\.metadata","D:\\Workspaces\\aa.zip");
			unCompress("D:\\Workspaces\\aa.zip","d:\\kk");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}



