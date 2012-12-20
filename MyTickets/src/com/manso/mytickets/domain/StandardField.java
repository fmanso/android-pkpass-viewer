package com.manso.mytickets.domain;

import java.io.Serializable;

public class StandardField implements Serializable {
	private String changeMessage;
	private String key;
	private String label;
	private String textAlignment;
	private String value;
	
	public StandardField(String changeMessage, String key, String label,
			String textAlignment, String value) {
		super();
		this.changeMessage = changeMessage;
		this.key = key;
		this.label = label;
		this.textAlignment = textAlignment;
		this.value = value;
	}
	
	public String getChangeMessage() {
		return changeMessage;
	}
	public void setChangeMessage(String changeMessage) {
		this.changeMessage = changeMessage;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTextAlignment() {
		return textAlignment;
	}
	public void setTextAlignment(String textAlignment) {
		this.textAlignment = textAlignment;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
