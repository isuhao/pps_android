package com.pps.customcrash;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * �����Ƴ�Ӧ�ó��򹤾���
 * @author jiangqingqing
 * @time 2013/10/14 10:38
 */
public class ExitApp {

	
	private static ExitApp instance=new ExitApp();
	private ExitApp(){}
	private List<Activity> activities;
	public static ExitApp getInstance()
	{
		return instance;
	}
	
	
	/**
	 * ���һ��Activity���󵽹�������
	 * @param pActivity
	 */
	public void addActivity(Activity pActivity)
	{
		
		if(null==activities)
		{
			activities=new ArrayList<Activity>();
		}
		activities.add(pActivity);
	}
	
	/**
	 * ��Activity���������쳣һ��Activity
	 * @param pActivity
	 */
	public void removeActivity(Activity pActivity)
	{
			activities.remove(pActivity);
	}
	
	/**
	 * ��ȫ�˳�Ӧ��
	 */
	public void exitAPP()
	{
		for (Activity pActivity : activities) {
			pActivity.finish();
		}
		System.exit(0);
	}
}


