package com.apps.developer.mobile.ws.model.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthenticationDetails {

	private String id;
	private String token;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
