package com.manso.mytickets;

import java.io.File;
import java.io.IOException;

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

import com.google.zxing.WriterException;
import com.manso.mytickets.domain.EventTicket;
import com.manso.mytickets.domain.StandardField;
import com.manso.pkpassutils.PkpassReader;

public class TicketBackFragment extends Fragment {

	public static TicketBackFragment newInstance(String ticketPath) {
		TicketBackFragment ticketBack = new TicketBackFragment();
		
		Bundle args = new Bundle();
		args.putString("ticketPath", ticketPath);
		ticketBack.setArguments(args);
		
		return ticketBack;
	}

	private EventTicket ticket;
	private LinearLayout linearLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		
		try {
			String path = this.getArguments().getString("ticketPath");
			this.ticket = new PkpassReader().readTicket(new File(path));
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (WriterException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}			
		
		View view = inflater.inflate(R.layout.fragment_ticket_back, container, false);		
		this.linearLayout = (LinearLayout) view.findViewById(R.id.containerLayout);
		
		this.drawBackFields();
		
		return view;
	}
	
	private void drawBackFields() {		
		
		StandardField[] backFields = ticket.getBackFields();
		for(int i = 0; i < backFields.length; i++)
		{
			StandardField backField = backFields[i];
			TextView title = new TextView(this.getActivity());
			title.setTypeface(null, Typeface.BOLD);
			title.setText(backField.getLabel());
			
			TextView body = new TextView(this.getActivity());
			body.setText(backField.getValue());
			
			this.linearLayout.addView(title);
			this.linearLayout.addView(body);
			Space space = new Space(this.getActivity());
			this.linearLayout.addView(space);
			
		}
	}

}
