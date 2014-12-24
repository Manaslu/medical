package com.idap.web.result.subscriptions.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.dataset.utils.AntZipUtils;
import com.idap.intextservice.result.email.entity.MailSenderInfo;
import com.idap.intextservice.result.email.service.MailSenderServiceImpl;
import com.idap.intextservice.result.jpg.service.JpgServiceImpl;
import com.idap.intextservice.result.subscriptions.entity.EmailPush;
import com.idap.web.common.controller.Commons;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/emailPush")
public class EmailPushController extends BaseController<EmailPush, String> {
	@Resource(name = "emailPushService")
	public void setBaseService(IBaseService<EmailPush, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "commons")
	private Commons commons;
	
	@Resource(name = "emailPushService")
	public IBaseService<EmailPush, String> emailPushService;

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> create(EmailPush entity) {
		Map<String, Object> results = Constants.MAP();
		long time=System.currentTimeMillis();
		long startime,endtime,dayCount;//开始日期毫秒数,结束日期毫秒数,日期间隔
		boolean flag=true;
		String zipFilePath = null;// 压缩文件路径
		String cpFilePath  = null;//压缩文件原路径
		zipFilePath=commons.getFileDownPath()+"/"+time+"/"+time+".zip";
		cpFilePath =commons.getFileDownPath()+"/"+time+"/"+time+"/"+time;
		File zipFile = null;
		String emailname=entity.getReportName()+entity.getSubsName();// 邮件标题(报表名称+订阅名称)
		try {
			results.put("subscribeId", entity.getSubscribeId());
			List<EmailPush> emailist = this.emailPushService.findList(results);// 查询用户email
			String stats = "";
			MailSenderInfo mailInfo = MyMailSenderInfo();
			mailInfo.setSubject(emailname);
			String email = "aaaa@163.com";// 多个接受者地址的话以";"隔开
			String url=getLink(entity);//获取报表链接地址
			new File(commons.getFileDownPath()+"/"+time).mkdir();
			new File(commons.getFileDownPath()+"/"+time+"/"+time).mkdir();
			JpgServiceImpl.getJpg(url,cpFilePath);//根据报表链接生成jpg图片
			File file = new File(cpFilePath+".csv");
		    Vector<String> vector = new Vector<String>();
		    vector.add("订阅名称"+"," + "文件名称"+","+ "用户列表" + "," + "用户邮箱");
		    for (EmailPush e : emailist){
		    	vector.add(emailname+"," +time+","+ e.getUsrName() + "," + e.getEmail());
		    }
		    //写入文件
			writeFile(file, vector);
			
			//获取开始毫秒数
			startime=System.currentTimeMillis();
			while(flag){
				//获取结束毫秒数
				endtime=System.currentTimeMillis();
				//程序暂停1分钟
				dayCount= (endtime-startime)/(1000*60);
				if(dayCount>=1){
					flag=false;
				}
			}
			
			//压缩文件
			zipFile = AntZipUtils.compress(commons.getFileDownPath()+"/"+time+"/"+time, zipFilePath);
			mailInfo.setContent(url);// 设置格式
			
			for (EmailPush e : emailist) {
				if (null == e.getEmail() || "".equals(e.getEmail())) {
					entity.setPushStats("0");
				} else {
					mailInfo.setReceivers(email);
					// mailInfo.setReceivers(e.getEmail());
					boolean blean = sendMyEmail(mailInfo);
					if (blean) {
						stats = "1";
					} else {
						stats = "0";
					}
					entity.setPushStats(stats);// 推送状态
				}
				entity.setUserName(e.getUsrName());// 用户列表
				this.emailPushService.save(entity);
			}
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	// 设置邮件相关信息
	private MailSenderInfo MyMailSenderInfo() {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");// 这里填写你邮箱的host
		mailInfo.setMailServerPort("25");// 端口号
		mailInfo.setValidate(true); // 发送邮件时需要打开校验
		mailInfo.setUserName("mywisler"); // 填邮箱@前面的部分
		mailInfo.setPassword("my123456");// 邮箱密码
		mailInfo.setFromAddress("mywisler@163.com"); // 发件人邮箱

		return mailInfo;
	}

	//生成报表链接
	private String getLink(EmailPush entity){
		String Link="";
		Link = "http://localhost:8080/idap/report.shtml?method=report";// html发送
		return Link;
	}
	
	// 发送邮箱
	private boolean sendMyEmail(MailSenderInfo mailInfo) {
		// 发送邮件
		String receivers = mailInfo.getReceivers();
		try {
			for (String receiver : receivers.split(";")) {
				mailInfo.setToAddress(receiver);
				boolean flag=MailSenderServiceImpl.sendHtmlMail(mailInfo);//发送html格式
				return flag;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	  * 写一行数据到文件中，支持中文
	  * @param file  文件
	  * @param vector 数据
	  * @return
	  */
	 public  boolean writeFile(File file,Vector<String> vector){
	    try {
	         
	        BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK")) ;// 附加
	        // 添加新的数据行
	        for (String s :vector) {
	         bw.write(s);
	         bw.newLine();
	     }
	 
	        bw.close();
	        //log.debug("\n wirite file:"+file);
	      } catch (FileNotFoundException e) {
	       //log.error(e.toString(),e.fillInStackTrace());
	       return false;
	      } catch (IOException e) {
	      // log.error(e.toString(),e.fillInStackTrace());
	       return false;
	      }
	   
	  return true;
	 }
}
