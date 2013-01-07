package com.manso.mytickets;

import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;

import com.manso.mytickets.services.IPassStrategy;
import com.manso.mytickets.services.ManifestService;
import com.manso.mytickets.services.PassStrategyService;

public class ViewTicketFrontActivity extends FragmentActivity {
	private GestureDetector gestureDetector;	
	
	GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			SingleTapUp();
			return true;			
		}
	};

	private IPassStrategy strategy;	
		
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
	
	private void SingleTapUp() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		Fragment ticketBack = this.strategy.getBackFragment();
		fragmentTransaction.replace(R.id.containerLayout, ticketBack);		
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		this.gestureDetector = new GestureDetector(this, simpleOnGestureListener);		
		setContentView(R.layout.activity_view_ticket);				
		this.handleIntent(this.getIntent());
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_view_ticket, menu);
		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		this.handleIntent(intent);
	}
		
	private void handleIntent(Intent intent) {							
		HandleIntentAsyncTask pkpassReader = new HandleIntentAsyncTask(this);		
		pkpassReader.execute(intent);		
	}	

	public void OnPassReady(String path) {
		
		ManifestService ms = new ManifestService();
		
		if (!ms.check(new File(path))) 
		{
			Context ctx = getApplicationContext();
			CharSequence text = "Sanity check doest not pass";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(ctx, text, duration);
			toast.show();
			return;
		}		
		
		PassStrategyService pss = new PassStrategyService();
		this.strategy = null;
		try {
			strategy = pss.getStrategy(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (strategy == null) 
		{
			Context ctx = getApplicationContext();
			CharSequence text = "Unexpected error while loading the pass";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(ctx, text, duration);
			toast.show();
			return;
		}
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		Fragment fragment = strategy.getFrontFragment();
		fragmentTransaction.add(R.id.containerLayout, fragment);
		fragmentTransaction.commit();
	}	
}
