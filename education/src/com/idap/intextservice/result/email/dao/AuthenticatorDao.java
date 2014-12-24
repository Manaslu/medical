package com.idap.intextservice.result.email.dao;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class AuthenticatorDao extends Authenticator {
	String userName = null;
	String password = null;

	public AuthenticatorDao() {
	}

	public AuthenticatorDao(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
