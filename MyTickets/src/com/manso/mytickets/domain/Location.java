package com.manso.mytickets.domain;

import java.io.Serializable;

public class Location implements Serializable {
	private float latitude;
	private float longitude;
	private float altitude;
	private String relevantText;
	
	public Location(float latitude, float longitude, float altitude, String relevantText)
	{
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setAltitude(altitude);
		this.setRelevantText(relevantText);
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getAltitude() {
		return altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

	public String getRelevantText() {
		return relevantText;
	}

	public void setRelevantText(String relevantText) {
		this.relevantText = relevantText;
	}
}
