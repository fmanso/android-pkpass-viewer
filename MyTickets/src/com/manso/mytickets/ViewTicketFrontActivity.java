package com.manso.mytickets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.parser.ParseException;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.manso.mytickets.domain.EventTicket;
import com.manso.mytickets.domain.StandardField;
import com.manso.pkpassutils.AsyncPkPassReader;
import com.manso.pkpassutils.PkpassReader;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTicketFrontActivity extends Activity {
	private GestureDetector gestureDetector;
	private LinearLayout linearLayout;	
	private ImageView logoImageView;
	private EventTicket ticket;
	private TextView logoTextView;
	private ImageView barcodeImageView;
	private LinearLayout primaryFieldsLayout;
	private LinearLayout secondaryFieldsLayout;
	private LinearLayout auxiliarFieldsLayout;
	private RelativeLayout ticketRelativeLayout;
	
	GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			SingleTapUp();
			return true;			
		}
	};	
	
	private void SingleTapUp() {
		Intent intent = new Intent(this, ViewTicketBackActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("ticket", this.ticket);
		intent.putExtras(b);
		startActivity(intent);		
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_ticket_front);
		
		this.ticketRelativeLayout = (RelativeLayout) this.findViewById(R.id.ticketRelativeLayout);
		this.linearLayout = (LinearLayout) this.findViewById(R.id.linearLayout);		
		this.logoImageView = (ImageView) this.findViewById(R.id.logoImageView);
		this.logoTextView = (TextView)this.findViewById(R.id.logoTextView);
		this.barcodeImageView = (ImageView)this.findViewById(R.id.barcodeImageView);
		this.primaryFieldsLayout = (LinearLayout) this.findViewById(R.id.primaryFieldsLinearLayout);
		this.secondaryFieldsLayout = (LinearLayout) this.findViewById(R.id.secondaryFieldsLinearLayout);
		this.auxiliarFieldsLayout = (LinearLayout) this.findViewById(R.id.auxiliarFieldsLinearLayout);
		this.gestureDetector = new GestureDetector(this, simpleOnGestureListener);
		
		this.handleIntent(this.getIntent());
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_ticket, menu);
		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		this.handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {				
		AsyncPkPassReader pkpassReader = new AsyncPkPassReader(this);		
		pkpassReader.execute(intent);		
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("deprecation")
	private void drawTicket() throws WriterException {
		if (Build.VERSION.SDK_INT < 16) {
			this.ticketRelativeLayout.setBackgroundDrawable(new BitmapDrawable(this.getResources(), this.ticket.getBackgroundBitmap()));
		} else {
			this.ticketRelativeLayout.setBackground(new BitmapDrawable(this.getResources(), this.ticket.getBackgroundBitmap()));
		}
		this.logoImageView.setImageBitmap(this.ticket.getLogoBitmap());	
		this.logoTextView.setText(this.ticket.getLogoText());
		
		StandardField[] primaryFields = this.ticket.getPrimaryFields();
		for (int i = 0; i < primaryFields.length; i++) {
			StandardField stdField = primaryFields[i];			
			TextView t = new TextView(this);
			t.setTextSize(30);
			t.setText(primaryFields[i].getValue());
			this.primaryFieldsLayout.addView(t);
		}
		
		StandardField[] secondaryFields = this.ticket.getSecondaryFields();
		for (int i = 0; i < secondaryFields.length; i++) {
			StandardField stdField = secondaryFields[i];
			TextView label = new TextView(this);
			label.setTextSize(15);
			label.setText(stdField.getLabel());
			TextView value = new TextView(this);
			value.setTextSize(20);
			value.setText(stdField.getValue());
			this.secondaryFieldsLayout.addView(label);
			this.secondaryFieldsLayout.addView(value);
		}
		
		StandardField[] auxiliarFields = this.ticket.getAuxiliarFields();
		for (int i = 0; i < auxiliarFields.length; i = i + 2) {
			StandardField stdField1 = auxiliarFields[i];
			StandardField stdField2 = (i + 1 < auxiliarFields.length) ? auxiliarFields[i + 1] : null;
			
			RelativeLayout relativeLayoutLabel = new RelativeLayout(this);		
			relativeLayoutLabel.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
			RelativeLayout relativeLayoutValue = new RelativeLayout(this);
			relativeLayoutValue.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
			
			TextView label = new TextView(this);
			label.setTextSize(15);
			label.setText(stdField1.getLabel());
			TextView value = new TextView(this);
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
				TextView label2 = new TextView(this);
				label2.setTextSize(15);
				label2.setText(stdField2.getLabel());
				TextView value2 = new TextView(this);
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

	public void setTicket(EventTicket ticket2) {		
		
		this.ticket = ticket2;				
		
		try {
			this.drawTicket();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}
