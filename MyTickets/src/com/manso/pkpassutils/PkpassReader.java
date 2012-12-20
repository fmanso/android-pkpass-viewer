package com.manso.pkpassutils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.manso.mytickets.domain.EventTicket;
import com.manso.mytickets.domain.StandardField;
import com.manso.mytickets.utils.FileReader;

public class PkpassReader {
	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;
	
	public EventTicket readTicket(File dir) throws ParseException, WriterException, IOException {
		EventTicket et = new EventTicket();
		FileReader fileReader = new FileReader();
		
		String json = fileReader.readTextFile(new File(dir, "pass.json"));
		this.parseJson(json, et);
		
		byte[] backgroundBytes = fileReader.readBinaryFile(new File(dir, "background.png"));
		et.setBackgroundBitmap(BitmapFactory.decodeByteArray(backgroundBytes, 0, backgroundBytes.length));
		
		byte[] logoBytes = fileReader.readBinaryFile(new File(dir, "logo.png"));
		et.setLogoBitmap(BitmapFactory.decodeByteArray(logoBytes, 0, logoBytes.length));
		
		return et;
	}	

	private void parseJson(String json, EventTicket et) throws ParseException,
			WriterException {
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(json);
		JSONObject root = (JSONObject) obj;

		et.setLogoText(root.get("logoText").toString());

		JSONObject eventTicket = (JSONObject) root.get("eventTicket");
		
		et.setPrimaryFields(getStandardFields("primaryFields", eventTicket));
		
		et.setSecondaryFields(getStandardFields("secondaryFields", eventTicket));
		
		et.setAuxiliarFields(getStandardFields("auxiliaryFields", eventTicket));

		et.setBackFields(getStandardFields("backFields", eventTicket));

		com.google.zxing.Writer writer = new MultiFormatWriter();
		BitMatrix result = writer.encode("999331763667",
				com.google.zxing.BarcodeFormat.PDF_417, 600, 600);

		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}
		
		Bitmap barcode = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		barcode.setPixels(pixels, 0, width, 0, 0, width, height);
		et.setBarcodeBitmap(barcode);	    
	}
	
	private StandardField[] getStandardFields(String name, JSONObject json) {
		JSONArray fields = (JSONArray) json.get(name);
		StandardField[] output = new StandardField[fields.size()];
		for (int i = 0; i < fields.size(); i++) {
			JSONObject auxiliarField = (JSONObject) fields.get(i);
			String key = auxiliarField.get("key").toString();
			String label = auxiliarField.get("label").toString();
			String value = auxiliarField.get("value").toString();
			StandardField f = new StandardField(null, key, label, null, value);
			output[i] = f;
		}
		return output;
	}
	
}
