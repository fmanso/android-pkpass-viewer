package com.manso.mytickets.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

public class BarcodeEncoder {
	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;
	private Context context;
	
	public BarcodeEncoder(Context context) {
		this.context = context;
	}

	public Bitmap getBitmap(String toEncode, String format) throws WriterException {
		
		com.google.zxing.BarcodeFormat zxingFormat = null;
		
		if (format.equals("PKBarcodeFormatPDF417")) {
			zxingFormat = BarcodeFormat.PDF_417;
		} else if (format.equals("PKBarcodeFormatQR")) {
			zxingFormat = BarcodeFormat.QR_CODE;
		}
		
		com.google.zxing.Writer writer = new MultiFormatWriter();
		
		DisplayMetrics metric = this.context.getResources().getDisplayMetrics();
		float xdpi = metric.xdpi;
		
		BitMatrix result = writer.encode(toEncode, zxingFormat, (int)(xdpi * 0.625), (int)(xdpi * 0.625));

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
		return barcode;    
	}
}
