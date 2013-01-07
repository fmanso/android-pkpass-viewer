package com.manso.mytickets;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.manso.mytickets.services.PassReadingService;
import com.manso.mytickets.utils.BarcodeEncoder;

public class TicketFrontFragment extends Fragment {
	
	public static TicketFrontFragment newInstance(String ticketPath) {
		TicketFrontFragment ticketFront = new TicketFrontFragment();
		
		Bundle args = new Bundle();
		args.putString("ticketPath", ticketPath);
		ticketFront.setArguments(args);		
		
		return ticketFront;
	}
	
	private RelativeLayout ticketRelativeLayout;
	private TextView logoTextView;
	private ImageView barcodeImageView;
	private LinearLayout primaryFieldsLayout;
	private LinearLayout secondaryFieldsLayout;
	private LinearLayout auxiliarFieldsLayout;
	private String path;
	private PassReadingService passReadingService;
	private ImageView thumbnailImageView;	
	
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
	
		this.path = this.getArguments().getString("ticketPath");	
		try {
			this.passReadingService = new PassReadingService(this.path, this.getActivity());			
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		this.ticketRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_ticket_front, container, false);
		this.logoTextView = (TextView)this.ticketRelativeLayout.findViewById(R.id.headerText);
		this.barcodeImageView = (ImageView)this.ticketRelativeLayout.findViewById(R.id.barcodeImageView);
		this.thumbnailImageView = (ImageView)this.ticketRelativeLayout.findViewById(R.id.thumbnailImageView);
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
			this.ticketRelativeLayout.setBackgroundDrawable(new BitmapDrawable(this.getResources(), this.passReadingService.getBackground()));			
		} else {
			this.ticketRelativeLayout.setBackground(new BitmapDrawable(this.getResources(), this.passReadingService.getBackground()));			
		}
		
		this.logoTextView.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(this.getResources(), this.passReadingService.getLogo()), null, null, null);		
		this.logoTextView.setText(this.passReadingService.getValue("logoText"));
		
		JSONObject eventTicket = this.passReadingService.getJSONObject("eventTicket");
		
		if (eventTicket.containsKey("primaryFields")) {
			JSONArray primaryFields = (JSONArray) eventTicket.get("primaryFields");		
			for (Object obj : primaryFields) {
				JSONObject primaryField = (JSONObject) obj;
				TextView t = new TextView(this.getActivity());
				t.setTextSize(20);
				t.setText(primaryField.get("value").toString());
				this.primaryFieldsLayout.addView(t);
			}
		}
		
		if (eventTicket.containsKey("secondaryFields")) {
			JSONArray secondaryFields = (JSONArray) eventTicket.get("secondaryFields");
			for (Object obj : secondaryFields) {
				JSONObject secondaryField = (JSONObject) obj;
				TextView label = new TextView(this.getActivity());
				label.setTextSize(10);
				label.setText(secondaryField.get("label").toString());
				TextView value = new TextView(this.getActivity());
				value.setTextSize(15);
				value.setText(secondaryField.get("value").toString());
				this.secondaryFieldsLayout.addView(label);
				this.secondaryFieldsLayout.addView(value);
			}
		}
		
		if (eventTicket.containsKey("auxiliaryFields")) {
			JSONArray auxiliaryFields = (JSONArray) eventTicket.get("auxiliaryFields");
			for (int i = 0; i < auxiliaryFields.size(); i = i + 2) {
				JSONObject auxField1 = (JSONObject) auxiliaryFields.get(i);
				JSONObject auxField2 = (JSONObject) ((i + 1 < auxiliaryFields.size()) ? auxiliaryFields.get(i + 1) : null);
				
				RelativeLayout relativeLayoutLabel = new RelativeLayout(this.getActivity());		
				relativeLayoutLabel.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
				RelativeLayout relativeLayoutValue = new RelativeLayout(this.getActivity());
				relativeLayoutValue.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
				
				TextView label = new TextView(this.getActivity());
				label.setTextSize(10);
				label.setText(auxField1.get("label").toString());
				TextView value = new TextView(this.getActivity());
				value.setTextSize(14);
				value.setText(auxField1.get("value").toString());
				
				LayoutParams labelParams1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				labelParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				relativeLayoutLabel.addView(label, labelParams1);
				
				LayoutParams valueParams1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				valueParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				relativeLayoutValue.addView(value, valueParams1);								
				
				if (auxField2 != null)
				{
					TextView label2 = new TextView(this.getActivity());
					label2.setTextSize(10);
					label2.setText(auxField2.get("label").toString());
					TextView value2 = new TextView(this.getActivity());
					value2.setTextSize(14);
					value2.setText(auxField2.get("value").toString());
					
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
		}
				
		Bitmap barcode;
		try {
			barcode = this.passReadingService.getBarcode();
			this.barcodeImageView.setImageBitmap(barcode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Bitmap icon;
		icon = this.passReadingService.getThumbnail();
		if (icon != null) {
			this.thumbnailImageView.setImageBitmap(icon);
		}
	}
	
}
