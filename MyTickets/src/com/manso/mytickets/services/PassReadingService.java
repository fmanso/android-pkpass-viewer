package com.manso.mytickets.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.zxing.WriterException;
import com.manso.mytickets.utils.BarcodeEncoder;
import com.manso.mytickets.utils.FileReader;
import com.manso.mytickets.utils.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class PassReadingService {
	
	private String path;
	private FileReader fileReader;
	private JSONParser parser;
	private JSONObject root;
	private Context context;


	public PassReadingService(String path, Context context) throws ParseException, IOException {
		this.path = path;
		this.context = context;
		this.fileReader = new FileReader();
		this.parser = new JSONParser();
		this.root = (JSONObject) this.parser.parse(this.fileReader.readTextFile(new File(path, "pass.json")));
	}
		
	public Bitmap getBackground() {
		
		File blurredBitmapFile = new File(this.path, "background-blur.png");
		byte[] bitmapBytes;
		Bitmap bitmap;
		
		try {
			if (blurredBitmapFile.exists())
			{			
				bitmapBytes = this.fileReader.readBinaryFile(new File(this.path, "background-blur.png"));
				bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
				return bitmap;
			}				
			else {
				File background = new File(this.path, "background@2x.png");
				if (!background.exists()) {
					background = new File(this.path, "background.png");
				}
				bitmapBytes = this.fileReader.readBinaryFile(background);
				bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
				Bitmap blurred = ImageUtils.fastblur(bitmap, 10);
				FileOutputStream out = new FileOutputStream(blurredBitmapFile);
				blurred.compress(CompressFormat.PNG, 0, out);
				out.close();
				return blurred;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}				
	}
	
	public Bitmap getLogo() {
		return getImage("logo");
	}

	private Bitmap getImage(String name) {
		byte[] bitmapBytes;
		try {
			File image = new File(this.path, name + "@2x.png");
			if (!image.exists()) {
				image = new File(this.path, name + ".png");
			}
			bitmapBytes = this.fileReader.readBinaryFile(image);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
		return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
	}
	
	public Bitmap getBarcode() throws WriterException, IOException {
		File barcodeFile = new File(this.path, "barcode.png");
		
		if (!barcodeFile.exists()) {
			JSONObject barcodeJson = this.getJSONObject("barcode");
			Bitmap barcodeAux = new BarcodeEncoder(this.context).getBitmap(barcodeJson.get("message").toString(), barcodeJson.get("format").toString());
			FileOutputStream out = new FileOutputStream(barcodeFile);
			barcodeAux.compress(CompressFormat.PNG, 0, out);
			out.close();
		}

		Bitmap barcode = BitmapFactory.decodeFile(barcodeFile.getPath());
		return barcode;
	}
	
	public String getValue(String key) {
		return this.root.get(key).toString();
	}
	
	public JSONObject getJSONObject(String key) {
		return (JSONObject) this.root.get(key);
	}

	public Bitmap getThumbnail() {
		Bitmap thumbnail =  getImage("thumbnail");
		
		if (thumbnail == null)
		{
			thumbnail = getImage("background");
		}
		
		return thumbnail;
	}
}
