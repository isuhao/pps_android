package tv.pps.bi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.os.Environment;

/**
 * 
 * �ļ���������������
 */
public class FileUtils {
/**
 * д�ַ����͵��ļ�
 * @param str  �ַ�
 * @param filename �ļ���
 */
	public final static String TAG = "FileUtils";
	public static void stringToFile(String str[], String filename) {
		File sdCardDir = null;
		StringBuffer sb =new StringBuffer();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			sdCardDir = Environment.getExternalStorageDirectory();
		} else {
			LogUtils.i(TAG, "SD��������");
			return;
		}
		  FileOutputStream fos;
		  File file =null;
		  try {
			   file = new File(sdCardDir, filename);
			 
			   if(file.exists()){
				   LogUtils.i(TAG, "�ļ��Ѵ���,�����κδ���");
				  return;
			   }
			   
			  fos= new FileOutputStream(file);
			  for(int i=0;i<str.length;i++){
				  if(i==str.length-1){
					  sb.append(str[i]);
				  }
				  else{
				  sb.append(str[i]+"#");
				  }
			  }
			  fos.write(sb.toString().getBytes());
			  fos.flush();
			  fos.close();
		} catch (Exception e) {
			  e.printStackTrace();
		}
		  LogUtils.i(TAG, "�ɹ�����UUID��Platform��"+file.toString());
		
//		try {
//			File file = new File(sdCardDir, filename);
//			RandomAccessFile raf = new RandomAccessFile(file, "rw");
//			raf.seek(file.length());
//			raf.write(str.getBytes());
//			raf.close();
//		} catch (Exception e) {
//			return;
//		}

	}
	
	public static String[] fileToStrings(String filename){
	StringBuffer sb = new StringBuffer();
	File sdCardDir = null;
	File file =null;
	if (Environment.getExternalStorageState().equals(
			Environment.MEDIA_MOUNTED)) {
		sdCardDir = Environment.getExternalStorageDirectory();
	} else {
		return null ;
	}
		try {
			 file = new File(sdCardDir, filename);
			if(!file.exists()||file.length()==0){
				LogUtils.i(TAG, filename+"�ļ�������");
				return null;
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
		    String line = "";
			try {
				while((line=br.readLine())!=null){
					sb.append(line);
					LogUtils.i(TAG, line);
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String []str = sb.toString().split("#");
		return str;
		
	}

}
