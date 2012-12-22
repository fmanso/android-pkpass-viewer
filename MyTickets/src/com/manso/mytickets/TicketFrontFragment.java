package com.manso.mytickets;

import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.google.zxing.WriterException;
import com.manso.mytickets.domain.EventTicket;
import com.manso.mytickets.domain.StandardField;
import com.manso.pkpassutils.PkpassReader;

import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class TicketFrontFragment extends Fragment {
	
	public static TicketFrontFragment newInstance(String ticketPath) {
		TicketFrontFragment ticketFront = new TicketFrontFragment();
		
		Bundle args = new Bundle();
		args.putString("ticketPath", ticketPath);
		ticketFront.setArguments(args);
		
		return ticketFront;
	}
		
	private EventTicket ticket;
	private RelativeLayout ticketRelativeLayout;
	private TextView logoTextView;
	private ImageView barcodeImageView;
	private LinearLayout primaryFieldsLayout;
	private LinearLayout secondaryFieldsLayout;
	private LinearLayout auxiliarFieldsLayout;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
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
		
		this.ticketRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_ticket_front, container, false);
		this.logoTextView = (TextView)this.ticketRelativeLayout.findViewById(R.id.headerText);
		this.barcodeImageView = (ImageView)this.ticketRelativeLayout.findViewById(R.id.barcodeImageView);
		this.primaryFieldsLayout = (LinearLayout) this.ticketRelativeLayout.findViewById(R.id.primaryFieldsLinearLayout);
		this.secondaryFieldsLayout = (LinearLayout) this.ticketRelativeLayout.findViewById(R.id.secondaryFieldsLinearLayout);
		this.auxiliarFieldsLayout = (LinearLayout) this.ticketRelativeLayout.findViewById(R.id.auxiliaryFieldsLinearLayout);				
		
		try {
			this.drawTicket();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return this.ticketRelativeLayout;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("deprecation")
	private void drawTicket() throws WriterException {		
		if (Build.VERSION.SDK_INT < 16) {
			this.ticketRelativeLayout.setBackgroundDrawable(new BitmapDrawable(this.getResources(), this.ticket.getBackgroundBitmap()));			
		} else {
			this.ticketRelativeLayout.setBackground(new BitmapDrawable(this.getResources(), this.ticket.getBackgroundBitmap()));			
		}
		
		this.logoTextView.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(this.getResources(), this.ticket.getLogoBitmap()), null, null, null);
		// this.logoImageView.setImageBitmap(this.ticket.getLogoBitmap());	
		this.logoTextView.setText(this.ticket.getLogoText());
//		
		StandardField[] primaryFields = this.ticket.getPrimaryFields();
		for (int i = 0; i < primaryFields.length; i++) {
			StandardField stdField = primaryFields[i];			
			TextView t = new TextView(this.getActivity());
			t.setTextSize(30);
			t.setText(stdField.getValue());
			this.primaryFieldsLayout.addView(t);
		}
		
		StandardField[] secondaryFields = this.ticket.getSecondaryFields();
		for (int i = 0; i < secondaryFields.length; i++) {
			StandardField stdField = secondaryFields[i];			
			TextView label = new TextView(this.getActivity());
			label.setTextSize(15);
			label.setText(stdField.getLabel());
			TextView value = new TextView(this.getActivity());
			value.setTextSize(20);
			value.setText(stdField.getValue());
			this.secondaryFieldsLayout.addView(label);
			this.secondaryFieldsLayout.addView(value);
		}
//		
		StandardField[] auxiliarFields = this.ticket.getAuxiliarFields();
		for (int i = 0; i < auxiliarFields.length; i = i + 2) {
			StandardField stdField1 = auxiliarFields[i];
			StandardField stdField2 = (i + 1 < auxiliarFields.length) ? auxiliarFields[i + 1] : null;
			
			RelativeLayout relativeLayoutLabel = new RelativeLayout(this.getActivity());		
			relativeLayoutLabel.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
			RelativeLayout relativeLayoutValue = new RelativeLayout(this.getActivity());
			relativeLayoutValue.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
			
			TextView label = new TextView(this.getActivity());
			label.setTextSize(15);
			label.setText(stdField1.getLabel());
			TextView value = new TextView(this.getActivity());
			value.setTextSize(20);
			value.setText(stdField1.getValue());
			
			LayoutParams labelParams1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			labelParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			relativeLayoutLabel.addView(label, labelParams1);
			
			LayoutParams valueParams1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			valueParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			relativeLayoutValue.addView(value, valueParams1);								
			
			if (stdField2 != null)
			{
				TextView label2 = new TextView(this.getActivity());
				label2.setTextSize(15);
				label2.setText(stdField2.getLabel());
				TextView value2 = new TextView(this.getActivity());
				value2.setTextSize(20);
				value2.setText(stdField2.getValue());
				
				LayoutParams labelParams2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				labelParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				relativeLayoutLabel.addView(label2, labelParams2);
				
				LayoutParams valueParams2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				valueParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				relativeLayoutValue.addView(value2, valueParams2);
			}
			
			this.auxiliarFieldsLayout.addView(relativeLayoutLabel);
			this.auxiliarFieldsLayout.addView(relativeLayoutValue);
		}
		
		this.barcodeImageView.setImageBitmap(this.ticket.getBarcodeBitmap());
	}
	
}
