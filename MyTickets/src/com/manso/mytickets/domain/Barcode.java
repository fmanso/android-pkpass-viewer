package com.manso.mytickets.domain;

import java.io.Serializable;

public class Barcode implements Serializable {	
	private String altText;
	private String format;
	private String message;
	private String messageEncoding;
	
	public Barcode(String altText, String format, String message,
			String messageEncoding) {
		super();
		this.altText = altText;
		this.format = format;
		this.message = message;
		this.messageEncoding = messageEncoding;
	}
	
	public String getAltText() {
		return altText;
	}
	public void setAltText(String altText) {
		this.altText = altText;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageEncoding() {
		return messageEncoding;
	}
	public void setMessageEncoding(String messageEncoding) {
		this.messageEncoding = messageEncoding;
	}
}
