package com.manso.mytickets.domain;

import java.io.Serializable;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Color;

public class EventTicket implements Serializable {
	private String description;
	private int formatVersion;
	private String organizationName;
	private String passTypeIdentifier;
	private String serialNumber;
	private String teamIdentifier;	
	private Location[] locations;
	private Date relevantDate;	
	private Barcode barcode;	
	private Color backgroundColor;
	private Color foregroundColor;
	private Color labelColor;
	private String logoText;	
	private StandardField[] headerFields;
	private StandardField[] primaryFields;
	private StandardField[] secondaryFields;
	private StandardField[] auxiliarFields;
	private StandardField[] backFields;
	
	private transient Bitmap backgroundBitmap;
	private transient Bitmap logoBitmap;
	private transient Bitmap barcodeBitmap;
	private long id;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFormatVersion() {
		return formatVersion;
	}
	public void setFormatVersion(int formatVersion) {
		this.formatVersion = formatVersion;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getPassTypeIdentifier() {
		return passTypeIdentifier;
	}
	public void setPassTypeIdentifier(String passTypeIdentifier) {
		this.passTypeIdentifier = passTypeIdentifier;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTeamIdentifier() {
		return teamIdentifier;
	}
	public void setTeamIdentifier(String teamIdentifier) {
		this.teamIdentifier = teamIdentifier;
	}
	public Location[] getLocations() {
		return locations;
	}
	public void setLocations(Location[] locations) {
		this.locations = locations;
	}
	public Date getRelevantDate() {
		return relevantDate;
	}
	public void setRelevantDate(Date relevantDate) {
		this.relevantDate = relevantDate;
	}
	public Barcode getBarcode() {
		return barcode;
	}
	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public Color getForegroundColor() {
		return foregroundColor;
	}
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	public Color getLabelColor() {
		return labelColor;
	}
	public void setLabelColor(Color labelColor) {
		this.labelColor = labelColor;
	}
	public String getLogoText() {
		return logoText;
	}
	public void setLogoText(String logoText) {
		this.logoText = logoText;
	}
	public StandardField[] getHeaderFields() {
		return headerFields;
	}
	public void setHeaderFields(StandardField[] headerFields) {
		this.headerFields = headerFields;
	}
	public StandardField[] getPrimaryFields() {
		return primaryFields;
	}
	public void setPrimaryFields(StandardField[] primaryFields) {
		this.primaryFields = primaryFields;
	}
	public StandardField[] getSecondaryFields() {
		return secondaryFields;
	}
	public void setSecondaryFields(StandardField[] secondaryFields) {
		this.secondaryFields = secondaryFields;
	}
	public StandardField[] getAuxiliarFields() {
		return auxiliarFields;
	}
	public void setAuxiliarFields(StandardField[] auxiliarFields) {
		this.auxiliarFields = auxiliarFields;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public StandardField getStandardFieldByKey(String key)
	{
		for(int i = 0; i < this.primaryFields.length; i++)
		{
			if (this.primaryFields[i].getKey().equals(key))
			{
				return this.primaryFields[i];
			}
		}
		
		for(int i = 0; i < this.secondaryFields.length; i++)
		{
			if (this.secondaryFields[i].getKey().equals(key))
			{
				return this.secondaryFields[i];
			}
		}
		
		for(int i = 0; i < this.auxiliarFields.length; i++)
		{
			if (this.auxiliarFields[i].getKey().equals(key))
			{
				return this.auxiliarFields[i];
			}
		}
		
		return null;
	}
	public Bitmap getBackgroundBitmap() {
		return backgroundBitmap;
	}
	public void setBackgroundBitmap(Bitmap backgroundBitmap) {
		this.backgroundBitmap = backgroundBitmap;
	}
	public Bitmap getLogoBitmap() {
		return logoBitmap;
	}
	public void setLogoBitmap(Bitmap logoBitmap) {
		this.logoBitmap = logoBitmap;
	}
	public Bitmap getBarcodeBitmap() {
		return barcodeBitmap;
	}
	public void setBarcodeBitmap(Bitmap barcodeBitmap) {
		this.barcodeBitmap = barcodeBitmap;
	}
	public StandardField[] getBackFields() {
		return backFields;
	}
	public void setBackFields(StandardField[] backFields) {
		this.backFields = backFields;
	}
	
}
