package com.manso.mytickets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.parser.ParseException;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.manso.mytickets.services.PassStorageService;

public class HandleIntentAsyncTask extends AsyncTask<Intent, Void, String> {	
	private ViewTicketFrontActivity activity;
	private InputStream stream;
	
	public HandleIntentAsyncTask(ViewTicketFrontActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected String doInBackground(Intent... arg0) {
		Intent intent = arg0[0];		
		Uri u = intent.getData();
		String scheme = u.getScheme();		
		if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			try {
				this.stream = this.activity.getContentResolver().openInputStream(u);				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else if (scheme.equals("http")) {
			try {
				URL url = new URL(intent.getDataString());
				URLConnection urlConnection = url.openConnection();
				this.stream = urlConnection.getInputStream();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			try {
				this.stream = this.activity.getContentResolver().openInputStream(u);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		String t = null;
		
		try {
			PassStorageService storageService = new PassStorageService(this.activity);
			File dir = storageService.InflatePkPassInTempDir(this.stream);
			this.stream.close();
			t = dir.getPath();
		} catch (IOException e) { 
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return t;
	}
	
	@Override
	protected void onPostExecute(String path) {
		try {
			this.stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		activity.OnPassReady(path);
	}

}
