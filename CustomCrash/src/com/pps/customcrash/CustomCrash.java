package com.pps.customcrash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

/**
 * �Զ����쳣��׽������
 * @author jiangqingqing
 * @time 2013/10/14 13:14
 *
 */
public class CustomCrash implements UncaughtExceptionHandler {
     
	private static CustomCrash instance=new CustomCrash();
	private Context mContext;	
	
	private CustomCrash(){}
	
	/**
	 * 
	 * @return
	 */
	public static CustomCrash getInstance()
	{
		return instance;
	}
	
	/*
	 * (non-Javadoc) ������д��׽�쳣 
	 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
        //1,������Ϣ��sdcard��
		saveToSdcard(mContext,ex);
	    //2,Ӧ��׼���˳�
		showToast(mContext, "�ܱ�Ǹ,�������쳣,�����Ƴ�.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
            e.printStackTrace();
		}
		ExitApp.getInstance().exitAPP();
	}
	
	/**
	 * �����Զ��쳣������
	 * @param pContext
	 */
	public void setCustomCrashInfo(Context pContext){
       this.mContext=pContext;
       Thread.setDefaultUncaughtExceptionHandler(this);
	}

	
	/**
	 * �����쳣��Ϣ��sdcard��
	 * @param pContext  
	 * @param ex  �쳣��Ϣ����
	 */
	private void saveToSdcard(Context pContext,Throwable ex) {
		StringBuffer sBuffer=new StringBuffer(); 
		//����쳣��Ϣ
		sBuffer.append(getExceptionInfo(ex));
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
	    	File file1=new File("sdcard/carsh/");
	    	if(!file1.exists()){
	    		file1.mkdir();
	    	}
	    	File file2=new File("sdcard/carsh/carsh.txt");
	    	FileOutputStream fos;
	    	try {
	    		fos=new FileOutputStream(file2);
	    		fos.write(sBuffer.toString().getBytes());
	    		fos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * ��ȡ����ת���쳣��Ϣ
	 * @param ex 
	 * @return �쳣��Ϣ���ַ�����ʽ
	 */
	private String getExceptionInfo(Throwable ex)
	{
		StringWriter sw=new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();	
	}
	
	/**
	 * ���е�������ʾ
	 * @param pContext
	 * @param msg
	 */
	private void showToast(final Context pContext, final String msg)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
			  Looper.prepare();
              Toast.makeText(pContext, msg, Toast.LENGTH_SHORT).show();		
              Looper.loop();
			}
		}).start();
	}
}
