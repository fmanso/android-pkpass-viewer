package com.manso.mytickets;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manso.mytickets.services.PassReadingService;

public class TicketBackFragment extends Fragment {

	public static TicketBackFragment newInstance(String ticketPath) {
		TicketBackFragment ticketBack = new TicketBackFragment();
		
		Bundle args = new Bundle();
		args.putString("ticketPath", ticketPath);
		ticketBack.setArguments(args);
		
		return ticketBack;
	}

	
	private LinearLayout linearLayout;
	private PassReadingService passReadingService;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {				
		
		String path = this.getArguments().getString("ticketPath");
		try {
			this.passReadingService = new PassReadingService(path, this.getActivity());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		View view = inflater.inflate(R.layout.fragment_ticket_back, container, false);		
		this.linearLayout = (LinearLayout) view.findViewById(R.id.containerLayout);
		
		this.drawBackFields();
		
		return view;
	}
	
	private void drawBackFields() {		
		
		JSONObject eventTicket = this.passReadingService.getJSONObject("eventTicket");
		JSONArray backFields = (JSONArray) eventTicket.get("backFields");
		
		for (Object obj : backFields) {
			JSONObject jsonBackfield = (JSONObject)obj;
			
			TextView title = new TextView(this.getActivity());
			title.setTypeface(null, Typeface.BOLD);
			title.setText(jsonBackfield.get("label").toString());
			
			TextView body = new TextView(this.getActivity());
			body.setText(jsonBackfield.get("value").toString());
			
			this.linearLayout.addView(title);
			this.linearLayout.addView(body);
			Space space = new Space(this.getActivity());
			this.linearLayout.addView(space);
		}
	}

}
