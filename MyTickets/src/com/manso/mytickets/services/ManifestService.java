package com.manso.mytickets.services;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.manso.mytickets.utils.FileReader;
import com.manso.mytickets.utils.Sha1;

public class ManifestService {
	
	public boolean check(File dir) {			
		String json;
		Sha1 sha1 = new Sha1();
		try {
			json = new FileReader().readTextFile(new File(dir, "manifest.json"));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		JSONParser jsonParser = new JSONParser();
		
		JSONObject root;
		try {
			Object rootAux = jsonParser.parse(json);
			root = (JSONObject) rootAux;
		
			for (Object keyAux : root.keySet()) {
				String key = (String)keyAux;
				File file = new File(dir, key);
				String hash = sha1.fromFile(file);
				String hashFromManifest = (String) root.get(key);
				if (!hash.equals(hashFromManifest))
				{
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
