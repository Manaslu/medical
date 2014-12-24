package com.idap.web.dataprocess.dataset.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.dataset.entity.DataUploadDownloadLog;
import com.idap.dataprocess.dataset.utils.DataSetUtils;
import com.idap.dataprocess.dataset.utils.AntZipUtils;
import com.idap.web.common.controller.Commons;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.DateUtil;
import com.idp.pub.web.controller.BaseController;

/**
 * @###################################################
 * @功能描述：
 * @开发人员：李广彬
 * @创建日期：2014-5-15 19:28:30
 * @修改日志：
 * @###################################################
 */
@Controller
@RequestMapping("/dataUploadDownloadLog")
public class DataUploadDownloadLogController extends BaseController<DataUploadDownloadLog, String> {
	private static final Log logger = LogFactory.getLog(DataUploadDownloadLogController.class);
	private static final int BUFFER_SIZE = 32 * 1024;

	@Resource(name = "dataUploadDownloadLogService")
	public void setBaseService(IBaseService<DataUploadDownloadLog, String> baseService) {
		super.setBaseService(baseService);
	}

	/**
	 * 文件下载
	 * 
	 * @createData 2014-3-3 12:01:15
	 * @param fileType:1正常数据 2错误数据
	 * @author 李广彬
	 */
	@RequestMapping(params = "method=download", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> download(@RequestParam String id, @RequestParam String fileType) {
		String orgFileName = null;// 原文件名带扩展名
		String orgFileName1 = null;// 去扩展名
		String zipFileName = null;// 显示给前能的文件名称
		String zipFilePath = null;// 压缩文件路径
		File file = null;
		File zipFile = null;
		try {
			DataUploadDownloadLog log = this.getBaseService().getById(id);
			// 文件下载
			if (fileType.equals("1")) {// 正常文件
				orgFileName = log.getSourFileName();
				file = new File(log.getSourFileDir());
				zipFilePath = log.getSourFileDir().substring(0, log.getSourFileDir().lastIndexOf("."))+ DataSetUtils.SUFFIX_ZIP;
				zipFileName = log.getSourFileName().substring(0, log.getSourFileName().lastIndexOf("."))+ DataSetUtils.SUFFIX_ZIP;
			} else {// 错误文件下载 原始文件名：汇兑_0628114127_错误.txt
				orgFileName = log.getErrFileName();
				file = new File(log.getErrFileDir());
				zipFilePath = log.getErrFileDir().substring(0, log.getErrFileDir().lastIndexOf("."))+ DataSetUtils.SUFFIX_ZIP;
				zipFileName = log.getErrFileName().substring(0, log.getErrFileName().lastIndexOf("."))+ DataSetUtils.SUFFIX_ZIP;
			}
			if (!orgFileName.toLowerCase().endsWith(DataSetUtils.SUFFIX_ZIP)) {// 不为zip包时
				orgFileName1 = orgFileName.substring(0, orgFileName.lastIndexOf("."));
				String cpFileName = DataSetUtils.buildNewFileName(orgFileName, orgFileName1);
				String cpFilePath = file.getParent() + File.separator + DataSetUtils.getUUID() + File.separator
						+ cpFileName;// 文本文件路径
				copyFile(file, new File(cpFilePath));
				zipFile = AntZipUtils.compress(cpFilePath, zipFilePath);
			} else {
				zipFile = file;
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setCacheControl("no-cache");
			headers.setPragma("headers");
			headers.setExpires(-1);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", new String(zipFileName.getBytes("GBK"),"iso8859-1"));
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(zipFile), headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

	/**
	 * 多次上传的错误文件 文件下载
	 * 
	 * @createData 2014-3-3 12:01:15
	 * @param fileType:1正常数据 2错误数据
	 * @author 李广彬
	 */
	@RequestMapping(params = "method=errordown", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> errorFileDownload(@RequestParam String dataSetId, @RequestParam String fileType) {
		String orgFileName = null;// 原文件名带扩展名
		String zipFileName = null;// 显示给前能的文件名称
		String zipFilePath = null;// 压缩文件路径
		String dsName = null;//数据集名称 
		String cpFilePath=null;
		String cpFilePathBase=null;
		File file = null;
		File zipFile = null;
		List<DataUploadDownloadLog> logs = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataSetId", dataSetId);
		params.put("opType", DataSetUtils.DS_LOG_TYPE_UPLOAD);
		try {
			logs = this.getBaseService().findList(params);
			cpFilePathBase=commons.getFileDownPath()+ File.separator + 
					DataSetUtils.fileDirClean(logs.get(0).getDataSetName())+"_"+
					DateUtil.format(new Date(),DateUtil.MMDDHHMMSS);
			for (DataUploadDownloadLog log : logs) {
				if(StringUtils.isNotBlank(log.getErrFileName())){//存在错误的文件
					dsName=log.getDataSetName();
					orgFileName = log.getErrFileName();
					file = new File(log.getErrFileDir());
					cpFilePath =cpFilePathBase+ File.separator+ orgFileName;// 文本文件路径
					copyFile(file, new File(cpFilePath));
				}
			}
			zipFileName=DataSetUtils.buildErrorFileName("a.zip", dsName);
			zipFilePath = commons.getFileDownPath()+file.separator+zipFileName;
			zipFile = AntZipUtils.compress(cpFilePathBase, zipFilePath);
					
			HttpHeaders headers = new HttpHeaders();
			headers.setCacheControl("no-cache");
			headers.setPragma("headers");
			headers.setExpires(-1);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", new String(zipFileName.getBytes("GBK"),"iso8859-1"));
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(zipFile), headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

	/**
	 * @创建日期：2013-5-26
	 * @开发人员：李广彬
	 * @功能描述：文件对拷
	 * @传入参数：
	 * @param src 源文件
	 * @param dst 目标文件
	 */
	private void copyFile(File src, File dst) throws IOException {
		BufferedReader lnReader = null;
		OutputStreamWriter fw = null;
		try {
			if (!dst.exists()) {
				dst.getParentFile().mkdirs();
				dst.createNewFile();
			}
			lnReader = new BufferedReader(new InputStreamReader(new FileInputStream(src)));
			fw = new OutputStreamWriter(new FileOutputStream(dst));
			String temp = lnReader.readLine();
			while (temp != null) {
				fw.write(temp);
				fw.write("\r\n");
				temp = lnReader.readLine();
			}
			fw.flush();
		} finally {
			if (null != lnReader) {
				lnReader.close();
			}
			if (null != fw) {
				fw.close();
			}
		}
	}

	// ========================= get/set ===========================
	@Resource(name = "commons")
	private Commons commons;
}