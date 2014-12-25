package com.teamsun.framework.mail;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MailEngine {
	
	protected static final Log log = LogFactory.getLog(MailEngine.class);

	private MailSender mailSender;

	private VelocityEngine velocityEngine;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void sendMessage(SimpleMailMessage msg, String templateName,
			Map model) {
		String result = null;

		try {
			result = VelocityEngineUtils.mergeTemplateIntoString(
					velocityEngine, templateName, model);
		} catch (VelocityException e) {
			e.printStackTrace();
		}

		msg.setText(result);
		send(msg);
	}

	public void send(SimpleMailMessage msg) {
		try {
			mailSender.send(msg);
		} catch (MailException ex) {
			log.error(ex.getMessage());
		}
	}

	public void sendHtmlMessage(String[] emailAddresses, String bodyText,
			String subject, String encoding) throws MessagingException {
		MimeMessage message = ((JavaMailSenderImpl) mailSender)
				.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true,
				encoding);
		helper.setTo(emailAddresses);
		helper.setText(bodyText, true);
		helper.setSubject(subject);

		((JavaMailSenderImpl) mailSender).send(message);
	}

	// -- Modified by DingLin --
	/**
	 * 
	 * 发送HTML格式的邮件
	 * 
	 * 
	 * @param from
	 *            发件人地址
	 * @param personal
	 *            发件人显示名称
	 * 
	 * @param emailAddresses
	 *            邮件地址
	 * @param bodyText
	 *            邮件内容
	 * @param subject
	 *            邮件标题
	 * @param encoding
	 *            编码格式
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void sendHtmlMessage(String from, String personal,
			String[] emailAddresses, String bodyText, String subject,
			String encoding) throws MessagingException,
			UnsupportedEncodingException {
		MimeMessage message = ((JavaMailSenderImpl) mailSender)
				.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true,
				encoding);

		helper.setFrom(from, personal);
		helper.setTo(emailAddresses);
		helper.setText(bodyText, true);
		helper.setSubject(subject);

		((JavaMailSenderImpl) mailSender).send(message);
	}

	public void sendMessage(String[] emailAddresses,
			ClassPathResource resource, String bodyText, String subject,
			String attachmentName) throws MessagingException {
		MimeMessage message = ((JavaMailSenderImpl) mailSender)
				.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(emailAddresses);
		helper.setText(bodyText);
		helper.setSubject(subject);

		helper.addAttachment(attachmentName, resource);

		((JavaMailSenderImpl) mailSender).send(message);
	}

}
