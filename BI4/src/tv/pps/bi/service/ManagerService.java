package tv.pps.bi.service;

import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.utils.LogUtils;
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
		LogUtils.i(TagConstance.TAG_SERVICE, "��һ����������service");
		Intent intent = new Intent();
		intent.setClass(context, ListenService.class);
		context.startService(intent);
	}
	
	/**
	 * ֹͣ����
	 * @param context
	 */
	public static void stopService(Context context){
		LogUtils.i(TagConstance.TAG_SERVICE, "�ֶ�ֹͣ����service");
		Intent intent = new Intent();
		intent.setClass(context, ListenService.class);
		context.stopService(intent);
	}
	
}
