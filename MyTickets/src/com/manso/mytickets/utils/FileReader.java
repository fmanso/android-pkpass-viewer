package com.manso.mytickets.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader {
	private static final int BUFFER_SIZE = 8192;

	public String readTextFile(File file) throws IOException {			
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"),BUFFER_SIZE);		
		try {
			StringBuffer stringBuffer = new StringBuffer();			
			String str;
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}			
			return stringBuffer.toString();
		} finally {			
			br.close();
		}
	}
	
	public byte[] readBinaryFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			
			int len;
			while ((len = fis.read(buffer)) > 0) {
				stream.write(buffer, 0, len);
			}
			
			byte[] output = stream.toByteArray();
			stream.close();
			return output;
		} finally {
			fis.close();
		}
	}
}
