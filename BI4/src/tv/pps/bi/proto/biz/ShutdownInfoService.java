package tv.pps.bi.proto.biz;

import java.util.Date;

import tv.pps.bi.proto.model.Bootup;
import tv.pps.bi.proto.model.Shutdown;
import tv.pps.bi.utils.Utils;

import android.content.Context;
import android.content.SharedPreferences;


public class ShutdownInfoService {
	
	public Shutdown getShutdownTime(Context context,long timestamp) {// ��ȡϵͳ�ػ�ʱ��
		Shutdown shut = new Shutdown();
		
		SharedPreferences sp = context.getSharedPreferences("time", 1);
		long shutdown = sp.getLong("shutdown", 0);
		if(shutdown>timestamp){
		String shutdownStr = Utils.formatTimeStamp(shutdown, "yyyyMMddhhmmss");
		shut.setShutdowntime(shutdownStr);
		shut.setTimestamp(shutdown);
		return shut;
		}else{
			return null;
		}
	}
	public Bootup getBootUpTime(long timestamp) {// ��ȡϵͳ����ʱ��
		Bootup boot= new Bootup();
		String tempStr = Utils.runCommand("cat /proc/uptime");
		String passedTimeStr = tempStr.split("\\.")[0];
		long passedTime = Long.parseLong(passedTimeStr) * 1000;// ��ȡ���������ڵ�ʱ�䣬��sΪ��λ
		long nowTime = new Date().getTime();// ��ȡ��ǰʱ��,��msΪ��λ
		long startTime = nowTime - passedTime;
		
		if(startTime>timestamp){
			//Log.i("billsong", "startTime = " + startTime);
			String startTimeStr = Utils.formatTimeStamp(startTime, "yyyyMMddhhmm");
			boot.setBoottime(startTimeStr);
			boot.setTimestamp(startTime);
			return boot;
		}
		else{
			return null;
		}
		
	
	}

}
