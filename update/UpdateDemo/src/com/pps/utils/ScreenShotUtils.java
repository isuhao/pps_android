package com.pps.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 * ���н���������
 * @author jiangqingqing
 *
 */

public class ScreenShotUtils {

	
	/**
	 * ���н�ȡ��Ļ  
	 * @param pActivity
	 * @return bitmap
	 */
	public static Bitmap takeScreenShot(Activity pActivity)
	{
		Bitmap bitmap=null;
		View view=pActivity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
	     
		bitmap=view.getDrawingCache();
		
		// ��ȡ״̬���߶�
		Rect frame=new Rect();
		view.getWindowVisibleDisplayFrame(frame);
		int stautsHeight=frame.top;
		Log.d("jiangqq", "״̬���ĸ߶�Ϊ:"+stautsHeight);
		
		int width=pActivity.getWindowManager().getDefaultDisplay().getWidth();
		int height=pActivity.getWindowManager().getDefaultDisplay().getHeight();
		bitmap=Bitmap.createBitmap(bitmap, 0, stautsHeight, width, height-stautsHeight);
		return bitmap;
	}
	
	
	/**
	 * ����ͼƬ��sdcard��
	 * @param pBitmap
	 */
	private static boolean savePic(Bitmap pBitmap,String strName)
	{
	  FileOutputStream fos=null;
	  try {
		fos=new FileOutputStream(strName);
		if(null!=fos)
		{
			pBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
			fos.flush();
			fos.close();
			return true;
		}
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();
	}
	  return false;
	} 
	
	public static boolean shotBitmap(Activity pActivity)
	{
		
		return  ScreenShotUtils.savePic(takeScreenShot(pActivity), "sdcard/"+System.currentTimeMillis()+".png");
	}
	
	
}
