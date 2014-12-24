package com.idap.web.common.controller;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.idp.pub.utils.JsonUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.idp.pub.constants.Constants;

@Controller
@RequestMapping(value = "/upload")
public class CommonUploadController {

	@Resource(name = "commons")
	private Commons commons;

//	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public   void upload(HttpServletRequest request,HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		MultipartFile multipartFile = null;
		Map<String, Object> results = Constants.MAP();
		for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
			multipartFile = set.getValue();// 文件名
		}
		String orgname = multipartFile.getOriginalFilename();// 获取原始文件名
		String fileType = orgname.substring(orgname.lastIndexOf(".") + 1,
				orgname.length());
		String fileName = System.currentTimeMillis() + "." + fileType;
		String filePath = commons.getFileUploadPath() + "/" + fileName;
		String mkpath = commons.getFileUploadPath();
		File file = new File(filePath);
		try {
			File mkdir = new File(mkpath);
			if (!mkdir.exists()) {
				mkdir.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			multipartFile.transferTo(file);
			results.put(Constants.SUCCESS, Constants.TRUE);

			results.put("fileName", fileName);
			results.put("fileType", fileType.toLowerCase());
			results.put("filePath", filePath);
			results.put("orgFileName", orgname);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.getWriter().write(JsonUtils.toJson(results));
		} catch (IllegalStateException e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		} catch (IOException e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
//		return JsonUtils.toJson(results);
	}
	
	    //生成报表图片
		@ResponseBody
		@RequestMapping(method = RequestMethod.POST, params = "image=true")
		public Map<String, Object> imageUpload(HttpServletRequest request) {
			String type = request.getParameter("type");
			String svg = request.getParameter("svg");
			String filename = request.getParameter("filename");
			filename = filename == null ? "chart" : filename;
			OutputStream out = null;
			Map<String, Object> results = Constants.MAP();
			try {
				if (null != type && null != svg) {
					svg = svg.replaceAll(":rect", "rect");
					String ext = "";
					Transcoder t = null;
					if (type.equals("image/png")) {
						ext = "png";
						t = new PNGTranscoder();
					} else if (type.equals("image/jpeg")) {
						ext = "jpg";
						t = new JPEGTranscoder();
					} else if (type.equals("application/pdf")) {
						ext = "pdf";
					} else if (type.equals("image/svg+xml")) {
						ext = "svg";
					}
					String fileName = System.currentTimeMillis() + "." + ext;
					String filePath = commons.getFileUploadPath() + "/" + fileName;
					String mkpath = commons.getFileUploadPath();
					File file = new File(filePath);
					File mkdir = new File(mkpath);
					if (!mkdir.exists()) {
						mkdir.mkdirs();
					}
					if (!file.exists()) {
						file.createNewFile();
					}
					out = new FileOutputStream(file);

					if (null != t) {
						TranscoderInput input = new TranscoderInput(
								new StringReader(svg));
						TranscoderOutput output = new TranscoderOutput(out);

						try {
							t.transcode(input, output);
						} catch (TranscoderException e) {
							e.printStackTrace();
						}
					}
					results.put(Constants.SUCCESS, Constants.TRUE);

					results.put("fileName", fileName);
					results.put("fileType", ext.toLowerCase());
					results.put("filePath", filePath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {

				}
			}
			return results;
		}

		//读取图片
		@RequestMapping(params = "drawiamge=true", method = RequestMethod.GET)
		@ResponseBody
		public ResponseEntity<byte[]> image(String fileDir, String fileType,
				HttpServletRequest request) throws IOException {

			HttpHeaders headers = new HttpHeaders();
			headers.setCacheControl("no-cache");
			headers.setPragma("headers");
			headers.setExpires(-1);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			if (null != fileType) {
				if (fileType.equalsIgnoreCase("jpg")) {
					headers.setContentType(MediaType.IMAGE_JPEG);
				}
				if (fileType.equalsIgnoreCase("gif")) {
					headers.setContentType(MediaType.IMAGE_GIF);
				}
				if (fileType.equalsIgnoreCase("png")) {
					headers.setContentType(MediaType.IMAGE_PNG);
				}
			}

			return new ResponseEntity<byte[]>(
					FileUtils.readFileToByteArray(new File(fileDir)), headers,
					HttpStatus.OK);

		}
	
}
