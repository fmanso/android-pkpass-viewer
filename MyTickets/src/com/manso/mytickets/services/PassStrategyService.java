package com.manso.mytickets.services;

import java.io.File;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.manso.mytickets.utils.FileReader;

public class PassStrategyService {
	public IPassStrategy getStrategy(File dir) throws IOException, ParseException {
		String json = new FileReader().readTextFile(new File(dir, "pass.json"));
		JSONParser parser = new JSONParser();
		JSONObject root = (JSONObject) parser.parse(json);
		
		if (root.containsKey("eventTicket")) {
			return new EventTicketPassStrategy(dir);
		}
		
		return null;
	}
}
