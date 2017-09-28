package com.hassdata.survey.util;

import java.io.FileOutputStream;
import java.io.InputStream;


public class FileUploadUtils {

	public static boolean uploadSingleImage(String path,String fileName,InputStream in) {
		FileOutputStream out = null;
		boolean b=false;
		try {
			out=new FileOutputStream(path+"/"+fileName);
			byte[] bt=new byte[1024];
			int len=0;
			while((len=in.read(bt))!=-1) {
				out.write(bt,0,len);
			}
			b=true;
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}finally {
			try {
				in.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return b;
	}
}
