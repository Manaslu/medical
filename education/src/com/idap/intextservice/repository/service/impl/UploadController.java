package com.idap.intextservice.repository.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.idap.intextservice.repository.entity.KnowledgeBaseAtt;

/**
 * @###################################################
 * @创建日期：2014-4-10 16:09:36
 * @开发人员：bai
 * @功能描述：上传文件
 * @修改日志：
 * @###################################################
 */

@Controller
@Transactional
public class UploadController extends KnowledgeBaseAttServiceImpl {

	private static final Log logger = LogFactory
			.getLog(KnowledgeBaseAttServiceImpl.class);

	@RequestMapping(value = "/upload.do")
	@ResponseBody
	public Map<String, String> upload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {
		logger.debug("开始");
		String path = request.getSession().getServletContext()
				.getRealPath("upload");
		String fileName = file.getOriginalFilename();
		// String fileName = new Date().getTime()+".jpg";

		logger.debug("fileName : " + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();// 创建目录
		}

		Map<String, String> results = new HashMap<String, String>();

		//保存
		try {
			logger.debug("开始保存文件");
			file.transferTo(targetFile);

			// 存附件实体
			KnowledgeBaseAtt knowledgeAtt = new KnowledgeBaseAtt();
			super.save(knowledgeAtt);

			logger.debug("保存文件           结束");
			results.put("success", "true");
			results.put("filePath", request.getContextPath() + "/upload/"
					+ fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
}
