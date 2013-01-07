package com.manso.mytickets.services;

import java.io.File;

import com.manso.mytickets.TicketBackFragment;
import com.manso.mytickets.TicketFrontFragment;

import android.support.v4.app.Fragment;

public class EventTicketPassStrategy implements IPassStrategy {

	private File dir;

	public EventTicketPassStrategy(File dir) {
		this.dir = dir;
	}
	
	@Override
	public Fragment getFrontFragment() {
		return TicketFrontFragment.newInstance(this.dir.getPath());		
	}

	@Override
	public Fragment getBackFragment() {		
		return TicketBackFragment.newInstance(this.dir.getPath());
	}

}
