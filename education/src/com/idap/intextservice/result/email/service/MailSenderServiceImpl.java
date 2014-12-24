package com.idap.intextservice.result.email.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.idap.intextservice.result.email.dao.AuthenticatorDao;
import com.idap.intextservice.result.email.entity.MailSenderInfo;


public class MailSenderServiceImpl {

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public static boolean sendTextMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		AuthenticatorDao authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new AuthenticatorDao(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		AuthenticatorDao authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new AuthenticatorDao(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以图片加附件格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static boolean sendphoteMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		AuthenticatorDao authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new AuthenticatorDao(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message message = new MimeMessage(sendMailSession);
			// 设置邮件的属性
			// 设置邮件的发件人
			message.setFrom(new InternetAddress(mailInfo.getFromAddress()));
			// 设置邮件的收件人 cc表示抄送 bcc 表示暗送
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					mailInfo.getToAddress()));
			// 设置邮件消息的主题
			message.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			message.setSentDate(new Date());
			// 创建邮件的正文
			MimeBodyPart text = new MimeBodyPart();
			// setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)
			text.setContent(mailInfo.getContent(), "text/html;charset=gb2312");// "这只是一个测试<img src='cid:a'>"
			// 创建图片
			MimeBodyPart img = new MimeBodyPart();
			/*
			 * JavaMail API不限制信息只为文本,任何形式的信息都可能作茧自缚MimeMessage的一部分.
			 * 除了文本信息,作为文件附件包含在电子邮件信息的一部分是很普遍的. JavaMail
			 * API通过使用DataHandler对象,提供一个允许我们包含非文本BodyPart对象的简便方法.
			 */
			DataHandler dh = new DataHandler(new FileDataSource(
					mailInfo.getPhoteAddress()));
			img.setDataHandler(dh);
			// 创建图片的一个表示用于显示在邮件中显示
			img.setContentID("a");

			// 创建附件
			MimeBodyPart attch = new MimeBodyPart();
			DataHandler dh1 = new DataHandler(new FileDataSource(
					mailInfo.getAnnexAddress()));
			attch.setDataHandler(dh1);
			String filename1 = dh1.getName();
			// MimeUtility 是一个工具类，encodeText（）用于处理附件字，防止中文乱码问题
			attch.setFileName(MimeUtility.encodeText(filename1));
			// 关系 正文和图片的
			MimeMultipart mm = new MimeMultipart();
			mm.addBodyPart(text);
			mm.addBodyPart(img);
			mm.setSubType("related");// 设置正文与图片之间的关系
			// 图班与正文的 body
			MimeBodyPart all = new MimeBodyPart();
			all.setContent(mm);
			// 附件与正文（text 和 img）的关系
			MimeMultipart mm2 = new MimeMultipart();
			mm2.addBodyPart(all);
			mm2.addBodyPart(attch);
			mm2.setSubType("mixed");// 设置正文与附件之间的关系

			message.setContent(mm2);
			message.saveChanges(); // 保存修改
			Transport.send(message);// 发送邮件
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
}

