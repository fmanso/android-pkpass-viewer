package com.manso.mytickets;

import com.manso.mytickets.domain.EventTicket;
import com.manso.mytickets.domain.StandardField;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.Space;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewTicketBackActivity extends Activity {

	LinearLayout linearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_ticket_back);
		
		this.linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
		
		drawBackFields();
	}

	private void drawBackFields() {
		Bundle b = this.getIntent().getExtras();;
		EventTicket ticket = (EventTicket) b.getSerializable("ticket");
		
		StandardField[] backFields = ticket.getBackFields();
		for(int i = 0; i < backFields.length; i++)
		{
			StandardField backField = backFields[i];
			TextView title = new TextView(this);
			title.setTypeface(null, Typeface.BOLD);
			title.setText(backField.getLabel());
			
			TextView body = new TextView(this);
			body.setText(backField.getValue());
			
			this.linearLayout.addView(title);
			this.linearLayout.addView(body);
			Space space = new Space(this);
			this.linearLayout.addView(space);
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_ticket_back, menu);
		return true;
	}

}
