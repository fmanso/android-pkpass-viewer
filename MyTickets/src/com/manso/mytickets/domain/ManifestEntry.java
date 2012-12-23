package com.manso.mytickets.domain;

public class ManifestEntry {
	private String filePath;
	private String sha1;	
	
	public ManifestEntry(String filePath, String sha1) {
		super();
		this.setFilePath(filePath);
		this.setSha1(sha1);
	}
	public String getFilePath() {
		return filePath;
	}
	
	private void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getSha1() {
		return sha1;
	}
	
	private void setSha1(String sha1) {
		this.sha1 = sha1;
	}
}
