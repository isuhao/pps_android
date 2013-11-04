package tv.pps.bi.config;

import android.content.Context;


/**
 * ���ø���ʱ����
 * @author jiangqingqing
 * @time 2013/10/24
 */
public class IntervalTimeConstance {

	/**�������㲥ʱ��ÿ��һ��Сʱ���п���һ�η���*/
	public static final int  START_LISTEN_SERVICE_TIME 	= 1*60*60*1000;//1Сʱ
	
	/**ÿ������Сʱ����Ͷ������service*/
	public static  int START_DELIVER_SERVICE_TIME 	= 1*60*60*1000;//1Сʱ
	
	/**��һ����������ʱ��һ���Ӻ�㿪ʼͶ��*/
	public static  int START_DELIVER_SERVICE_TIME_FIRST 	= 1*60*1000;//һ����
	
	/**ÿ����ʮ���ӽ���һ���û���Ϊ��ѯ*/
	public static  int START_USERINFO_SEARCH_TIME 	= 30*60*1000;//��ʮ����
	
	/**ʮ���н���һ���û���Ϊ�Ĳ�ѯ*/
	public static final int START_APPUSEINFO_SEARCH_TIME= 1*60*1000;//60����
	
	/**Ĭ��Ϊһ���ӽ���һ��APP�µ���ѯ*/
	public static  int START_APP_POLL_TIME= 1*60*1000;  //һ����

	/**
	 * �������߹ر��û���Ϊͳ�ƵĿ���
	 */
	public  static boolean START_SERVICE_SWITCH = false; //������ر��û���Ϊͳ�ƵĿ���
	
	
	
	public static int PRECURSOR_DELIVER_TIME=10*60*1000; //10���� 
	public static long PRECURSOR_DELIVER_INIT=0L;
	
	
	public static int getStartAppPollTime() {
		return START_APP_POLL_TIME;
	}

	public static void setStartAppPollTime(int sTART_APP_POLL_TIME) {
		START_APP_POLL_TIME = sTART_APP_POLL_TIME;
	}

    
	public static void setPrecursor_Deliver_Init(long pPrecursor_Deliver_Init) {
		PRECURSOR_DELIVER_INIT = pPrecursor_Deliver_Init;
	}

	public static void setPrecursor_Deliver_Time(int pPrecursor_Deliver_Time) {
		PRECURSOR_DELIVER_TIME = pPrecursor_Deliver_Time;
	}

	public static boolean isSTART_SERVICE_SWITCH() {
		return START_SERVICE_SWITCH;
	}

	public static void setStartServiceSwitch(Context context,boolean sTART_SERVICE_SWITCH) {
		
//		SharedPreferences sp = context.getSharedPreferences("bi4sdk",Context.MODE_PRIVATE);
//		Editor edit = sp.edit();
//		edit.putBoolean("switch",sTART_SERVICE_SWITCH );
//		edit.commit();
		START_SERVICE_SWITCH = sTART_SERVICE_SWITCH;
	}

	/**
	 * @return the sTART_DELIVER_SERVICE_TIME
	 */
	public static int getSTART_DELIVER_SERVICE_TIME() {
		return START_DELIVER_SERVICE_TIME;
	}

	/**
	 * @return the sTART_USERINFO_SEARCH_TIME
	 */
	public static int getSTART_USERINFO_SEARCH_TIME() {
		return START_USERINFO_SEARCH_TIME;
	}

	/**
	 * @param sTART_DELIVER_SERVICE_TIME the sTART_DELIVER_SERVICE_TIME to set
	 */
	public static void setStartDeliverServiceTime(int sTART_DELIVER_SERVICE_TIME) {
		START_DELIVER_SERVICE_TIME = sTART_DELIVER_SERVICE_TIME;
	}

	/**
	 * @param sTART_USERINFO_SEARCH_TIME the sTART_USERINFO_SEARCH_TIME to set
	 */
	public static void setStartUserInfoSearchTime(int sTART_USERINFO_SEARCH_TIME) {
		START_USERINFO_SEARCH_TIME = sTART_USERINFO_SEARCH_TIME;
	}
}
