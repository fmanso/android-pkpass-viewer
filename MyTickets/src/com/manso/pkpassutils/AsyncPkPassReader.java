package com.manso.pkpassutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.parser.ParseException;

import com.google.zxing.WriterException;
import com.manso.mytickets.ViewTicketFrontActivity;
import com.manso.mytickets.domain.EventTicket;
import com.manso.mytickets.services.PassStorageService;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

public class AsyncPkPassReader extends AsyncTask<Intent, Void, EventTicket> {
	PkpassReader innerReader = new PkpassReader();
	private ViewTicketFrontActivity activity;
	private InputStream stream;
	
	public AsyncPkPassReader(ViewTicketFrontActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected EventTicket doInBackground(Intent... arg0) {
		Intent intent = arg0[0];		
		Uri u = intent.getData();
		String scheme = u.getScheme();		
		if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			try {
				this.stream = this.activity.getContentResolver().openInputStream(u);				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (scheme.equals("http")) {
			try {
				URL url = new URL(intent.getDataString());
				URLConnection urlConnection = url.openConnection();
				this.stream = urlConnection.getInputStream();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		EventTicket t = null;
		
		try {
			PassStorageService storageService = new PassStorageService(this.activity);
			File dir = storageService.InflatePkPassInTempDir(this.stream);
			this.stream.close();
			t = innerReader.readTicket(dir);
		} catch (IOException e) { 
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}		
		
		return t;
	}
	
	@Override
	protected void onPostExecute(EventTicket ticket) {
		try {
			this.stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		activity.setTicket(ticket);
	}

}
