package com.manso.mytickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;

import com.manso.pkpassutils.AsyncPkPassReader;

public class ViewTicketFrontActivity extends FragmentActivity {
	private GestureDetector gestureDetector;	
	
	GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			SingleTapUp();
			return true;			
		}
	};	
	
	private void SingleTapUp() {
		
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_ticket_front);			
		
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

	public void setTicket(String ticket) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		TicketFrontFragment ticketFront = TicketFrontFragment.newInstance(ticket);
		fragmentTransaction.add(R.id.containerLayout, ticketFront);
		fragmentTransaction.commit();
	}	
}
