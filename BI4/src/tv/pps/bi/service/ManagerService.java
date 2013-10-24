package tv.pps.bi.service;

import android.content.Context;
import android.content.Intent;

/**
 * ���������
 * @author zhuchengjin
 *
 */
public class ManagerService {

	/**
	 * ����������÷���
	 */
	public static void startService(Context context){
		
		Intent intent = new Intent();
		intent.setClass(context, ListenService.class);
		context.startService(intent);
	}
	
	/**
	 * ֹͣ����
	 * @param context
	 */
	public static void stopService(Context context){
		Intent intent = new Intent();
		intent.setClass(context, ListenService.class);
		context.stopService(intent);
	}
	
}
