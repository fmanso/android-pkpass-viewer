package com.manso.mytickets.domain;

import java.util.ArrayList;
import java.util.List;

public class ManifestFile {
	private List<ManifestEntry> entries;

	public ManifestFile() {
		super();		
		this.setEntries(new ArrayList<ManifestEntry>());
	}

	public List<ManifestEntry> getEntries() {
		return entries;
	}

	private void setEntries(List<ManifestEntry> entries) {
		this.entries = entries;
	}	
}
